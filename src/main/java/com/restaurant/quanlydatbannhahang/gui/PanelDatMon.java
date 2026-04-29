/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.entity.MonAn;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import com.restaurant.quanlydatbannhahang.session.HoaDonDraftSession;
import com.restaurant.quanlydatbannhahang.service.BanService;
import com.restaurant.quanlydatbannhahang.service.MonAnService;
import com.restaurant.quanlydatbannhahang.service.PhieuDatBanService;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEnumLoader;
import com.restaurant.quanlydatbannhahang.util.ImageUtil;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author nguye
 */
public class PanelDatMon extends javax.swing.JPanel {

    private static final int TABLE_IMAGE_SIZE = 72;
    private static final int TABLE_IMAGE_ROW_HEIGHT = 84;
    private static final int TABLE_IMAGE_VERTICAL_PADDING = 4;
    private static final int PHIEU_COL_MA_MON = 0;
    private static final int PHIEU_COL_TEN_MON = 1;
    private static final int PHIEU_COL_SO_LUONG = 2;
    private static final int PHIEU_COL_DON_GIA = 3;
    private static final int PHIEU_COL_THANH_TIEN = 4;

    private MonAnService monAnService;
    private List<MonAn> allMonAn;
    private final Map<String, OrderItem> phieuGoiMonMap = new LinkedHashMap<>();
    private boolean refreshingPhieuGoiMonTable = false;
    private String datMonContext = "";
    private Set<String> maBanContextSet = new LinkedHashSet<>();
    private static boolean isChanged = false;

    /**
     * Creates new form PanelDatMon
     */
    public PanelDatMon() {
        initComponents();
        monAnService = new MonAnService();
        customUI();
        loadDataToComboBoxes();
        loadDataToTable();
        this.addHierarchyListener(new java.awt.event.HierarchyListener() {
            @Override
            public void hierarchyChanged(java.awt.event.HierarchyEvent e) {
                // Kiểm tra xem trạng thái SHOWING có thay đổi không
                if ((e.getChangeFlags() & java.awt.event.HierarchyEvent.SHOWING_CHANGED) != 0) {
                    // Nếu hiện tại Panel KHÔNG còn hiển thị trên màn hình
                    if (!isShowing() && isChanged) {
                        autoSavePhieuGoiMonDraft();
                        isChanged = false;
                    }
                }
            }

            private void autoSavePhieuGoiMonDraft() {
                // TODO Auto-generated method stub
                // Chỉ lưu nếu có món ăn và chưa được lưu (draftSaved == false)
                if (!phieuGoiMonMap.isEmpty()) {
                    try {
                        saveDraftToSession(); // Lưu món vào Session
                        capNhatTrangThaiSauKhiLuu(); // Cập nhật trạng thái bàn xuống DB

                        // Thông báo nhẹ vào Console hoặc một Label trạng thái thay vì JOptionPane
                        JOptionPane.showMessageDialog(null, "Hệ thống: Đã tự động phiếu gọi món vào bộ nhớ tạm",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception e) {
                        // Với Auto-save, chỉ hiện Popup khi có LỖI THỰC SỰ xảy ra
                        JOptionPane.showMessageDialog(null, "Lỗi tự động lưu: " + e.getMessage());
                    }
                }

            }
        });
    }

    private void customUI() {
        // Placeholder cho txtTimKiem
        setupPlaceholder(txtTimKiem, "Nhập tên món ăn");

        // Thêm action listeners cho các button
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnQuayLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLaiActionPerformed(evt);
            }
        });

        initPhieuGoiMonTable();
        setupPhieuGoiMonInteractions();
        setDatMonContext(null);
    }

    public String getDatMonContext() {
        return datMonContext;
    }

    public void setDatMonContext(String maBanContext) {
        String normalized = HoaDonDraftSession.normalizeMaBanContext(maBanContext);
        String resolvedContext = HoaDonDraftSession.resolveMaBanContext(normalized);
        boolean changed = !Objects.equals(this.datMonContext, resolvedContext);

        this.datMonContext = resolvedContext;
        this.maBanContextSet = HoaDonDraftSession.parseMaBanContextToSet(resolvedContext);
        HoaDonDraftSession.setCurrentMaBanContext(resolvedContext);

        if (changed) {
            loadDraftFromSession();
        }
    }

    private void loadDraftFromSession() {
        phieuGoiMonMap.clear();
        List<HoaDonDraftSession.DraftMonItem> draftItems = HoaDonDraftSession.getMonItems(datMonContext);
        for (HoaDonDraftSession.DraftMonItem draft : draftItems) {
            phieuGoiMonMap.put(draft.getMaMon(),
                    new OrderItem(draft.getMaMon(), draft.getTenMon(), draft.getDonGia(), draft.getSoLuong()));
        }
        refreshPhieuGoiMonTable();
    }

    private void initPhieuGoiMonTable() {
        tablePhieuGoiMon.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] { "Mã món", "Tên món ăn", "Số lượng", "Đơn giá", "Thành tiền", "Xóa" }) {
            Class<?>[] types = new Class<?>[] { String.class, String.class, Integer.class, Double.class, Double.class,
                    String.class };
            boolean[] canEdit = new boolean[] { false, false, true, false, false, true };

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        tablePhieuGoiMon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePhieuGoiMon.getColumnModel().getColumn(PHIEU_COL_SO_LUONG)
                .setCellEditor(new QuantitySpinnerEditor(1, 999));
        tablePhieuGoiMon.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        tablePhieuGoiMon.getColumnModel().getColumn(5)
                .setCellEditor(new ButtonEditor(this, phieuGoiMonMap, tablePhieuGoiMon));
        applyPhieuGoiMonRenderers();

        tablePhieuGoiMon.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (refreshingPhieuGoiMonTable || e.getType() != TableModelEvent.UPDATE
                        || e.getColumn() != PHIEU_COL_SO_LUONG) {
                    return;
                }

                int row = e.getFirstRow();
                if (row < 0 || row >= tablePhieuGoiMon.getRowCount()) {
                    return;
                }

                String maMon = String.valueOf(tablePhieuGoiMon.getValueAt(row, PHIEU_COL_MA_MON));
                OrderItem item = phieuGoiMonMap.get(maMon);
                if (item == null) {
                    return;
                }

                Object value = tablePhieuGoiMon.getValueAt(row, PHIEU_COL_SO_LUONG);
                int soLuongMoi = parsePositiveInt(value, item.soLuong);
                if (soLuongMoi != item.soLuong) {
                    item.soLuong = soLuongMoi;
                }

                DefaultTableModel model = (DefaultTableModel) tablePhieuGoiMon.getModel();
                double thanhTien = item.soLuong * item.donGia;

                refreshingPhieuGoiMonTable = true;
                try {
                    model.setValueAt(item.soLuong, row, PHIEU_COL_SO_LUONG);
                    model.setValueAt(thanhTien, row, PHIEU_COL_THANH_TIEN);
                } finally {
                    refreshingPhieuGoiMonTable = false;
                }

                updateTongTienTamTinh();
            }
        });
    }

    private void setupPhieuGoiMonInteractions() {
        // Phim tat: '+' tang, '-' giam, Delete xoa mon
        InputMap im = tablePhieuGoiMon.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = tablePhieuGoiMon.getActionMap();

        im.put(KeyStroke.getKeyStroke('+'), "tangSoLuong");
        im.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ADD, 0), "tangSoLuong");
        im.put(KeyStroke.getKeyStroke('-'), "giamSoLuong");
        im.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SUBTRACT, 0), "giamSoLuong");
        im.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0), "xoaMon");

        am.put("tangSoLuong", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                tangSoLuongSelectedMon();
            }
        });
        am.put("giamSoLuong", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                giamSoLuongSelectedMon();
            }
        });
        am.put("xoaMon", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                xoaMonSelected();
            }
        });
    }

    private void loadDataToComboBoxes() {
        try {
            ActionListener[] loaiMonListeners = cbFilterLoaiMonAn.getActionListeners();

            for (ActionListener listener : loaiMonListeners) {
                cbFilterLoaiMonAn.removeActionListener(listener);
            }

            cbFilterLoaiMonAn.removeAllItems();
            cbFilterLoaiMonAn.addItem("Loại món ăn");
            ComboBoxEnumLoader.loadLoaiMonAnToComboBox(cbFilterLoaiMonAn);

            for (ActionListener listener : loaiMonListeners) {
                cbFilterLoaiMonAn.addActionListener(listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu filter: " + e.getMessage());
        }
    }

    private List<MonAn> ensureMonAnDataLoaded() {
        if (allMonAn == null) {
            allMonAn = monAnService.getAllMonAn();
        }
        return allMonAn;
    }

    private void loadDataToTable() {
        try {
            List<MonAn> monAnList = ensureMonAnDataLoaded();
            String selectedLoai = (String) cbFilterLoaiMonAn.getSelectedItem();
            String searchText = txtTimKiem.getText().trim().toLowerCase();
            if (searchText.equals("nhập tên món ăn")) {
                searchText = "";
            }

            DefaultTableModel model = (DefaultTableModel) tableDanhSachMonAn.getModel();
            model.setRowCount(0);

            for (MonAn monAn : monAnList) {
                if (selectedLoai != null && !selectedLoai.isEmpty() && !selectedLoai.equals("Loại món ăn")) {
                    if (monAn.getTenLoai() == null || !monAn.getTenLoai().getDisplayName().equals(selectedLoai)) {
                        continue;
                    }
                }

                if (!searchText.isEmpty()) {
                    String maMon = monAn.getMaMon() != null ? monAn.getMaMon().toLowerCase() : "";
                    String tenMon = monAn.getTenMon() != null ? monAn.getTenMon().toLowerCase() : "";
                    if (!maMon.contains(searchText) && !tenMon.contains(searchText)) {
                        continue;
                    }
                }

                model.addRow(new Object[] {
                        ImageUtil.loadImageIcon(monAn.getUrlHinhAnh(), TABLE_IMAGE_SIZE),
                        monAn.getMaMon(),
                        monAn.getTenMon(),
                        monAn.getDonViTinh(),
                        monAn.getDonGia()
                });
            }

            centerTableColumns(tableDanhSachMonAn);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu món ăn: " + e.getMessage());
        }
    }

    private void tangSoLuongSelectedMon() {
        int row = tablePhieuGoiMon.getSelectedRow();
        if (row < 0) {
            return;
        }

        String maMon = String.valueOf(tablePhieuGoiMon.getValueAt(row, PHIEU_COL_MA_MON));
        OrderItem item = phieuGoiMonMap.get(maMon);
        if (item == null) {
            return;
        }

        item.soLuong++;
        refreshPhieuGoiMonTable();
    }

    private void giamSoLuongSelectedMon() {
        int row = tablePhieuGoiMon.getSelectedRow();
        if (row < 0) {
            return;
        }
        String maMon = String.valueOf(tablePhieuGoiMon.getValueAt(row, PHIEU_COL_MA_MON));
        OrderItem item = phieuGoiMonMap.get(maMon);
        if (item == null) {
            return;
        }

        if (item.soLuong > 1) {
            item.soLuong--;
            refreshPhieuGoiMonTable();
        }
    }

    private void xoaMonSelected() {
        int row = tablePhieuGoiMon.getSelectedRow();
        if (row < 0) {
            return;
        }

        String maMon = String.valueOf(tablePhieuGoiMon.getValueAt(row, PHIEU_COL_MA_MON));
        phieuGoiMonMap.remove(maMon);
        refreshPhieuGoiMonTable();
    }

    private void addSelectedMonToPhieuGoiMon() {
        int row = tableDanhSachMonAn.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món ăn trong danh sách.");
            return;
        }

        String maMon = String.valueOf(tableDanhSachMonAn.getValueAt(row, 1));
        String tenMon = String.valueOf(tableDanhSachMonAn.getValueAt(row, 2));
        Object donGiaObj = tableDanhSachMonAn.getValueAt(row, 4);
        double donGia = (donGiaObj instanceof Number) ? ((Number) donGiaObj).doubleValue()
                : Double.parseDouble(String.valueOf(donGiaObj));

        OrderItem item = phieuGoiMonMap.get(maMon);
        if (item == null) {
            phieuGoiMonMap.put(maMon, new OrderItem(maMon, tenMon, donGia, 1));
        } else {
            item.soLuong++;
        }

        refreshPhieuGoiMonTable();
    }

    private void refreshPhieuGoiMonTable() {
        refreshingPhieuGoiMonTable = true;
        try {
            if (tablePhieuGoiMon.isEditing() && tablePhieuGoiMon.getCellEditor() != null) {
                tablePhieuGoiMon.getCellEditor().stopCellEditing();
            }

            DefaultTableModel model = (DefaultTableModel) tablePhieuGoiMon.getModel();
            model.setRowCount(0);

            for (OrderItem item : phieuGoiMonMap.values()) {
                double thanhTien = item.soLuong * item.donGia;
                model.addRow(new Object[] {
                        item.maMon,
                        item.tenMon,
                        item.soLuong,
                        item.donGia,
                        thanhTien,
                        "Xóa"
                });
            }
        } finally {
            refreshingPhieuGoiMonTable = false;
        }

        updateTongTienTamTinh();
        applyPhieuGoiMonRenderers();
    }

    private void updateTongTienTamTinh() {
        double tongTien = 0;
        for (OrderItem item : phieuGoiMonMap.values()) {
            tongTien += item.soLuong * item.donGia;
        }
        DecimalFormat df = new DecimalFormat("#,##0.00");
        lblTongTienTamTinh.setText(df.format(tongTien) + " VND");
    }

    private void saveDraftToSession() {
        if (datMonContext != null && !datMonContext.isEmpty()) {
            List<HoaDonDraftSession.DraftMonItem> draftItems = new ArrayList<>();
            for (OrderItem item : phieuGoiMonMap.values()) {
                draftItems.add(new HoaDonDraftSession.DraftMonItem(item.maMon, item.tenMon, item.soLuong, item.donGia));
            }
            HoaDonDraftSession.setCurrentMaBanContext(datMonContext);
            HoaDonDraftSession.setMonItems(datMonContext, draftItems);
        }
    }

    private Set<String> getMaBanSetFromContext() {
        if (maBanContextSet == null || maBanContextSet.isEmpty()) {
            maBanContextSet = HoaDonDraftSession.parseMaBanContextToSet(datMonContext);
        }
        return new LinkedHashSet<>(maBanContextSet);
    }

    private java.util.List<String> getMaBanListFromContext() {
        return new ArrayList<>(getMaBanSetFromContext());
    }

    public void updateMaBanContextForEdit(Set<String> newSelectedTables) {
        if (newSelectedTables == null || newSelectedTables.isEmpty()) {
            return;
        }

        Set<String> oldBanSet = getMaBanSetFromContext();
        String oldContext = datMonContext;
        String newContext = HoaDonDraftSession.normalizeMaBanContext(String.join(",", newSelectedTables));
        if (newContext.isEmpty() || newContext.equals(oldContext)) {
            return;
        }

        saveDraftToSession();
        java.util.List<HoaDonDraftSession.DraftMonItem> draftItems = HoaDonDraftSession.getMonItems(oldContext);
        if (draftItems.isEmpty()) {
            for (OrderItem item : phieuGoiMonMap.values()) {
                draftItems.add(new HoaDonDraftSession.DraftMonItem(item.maMon, item.tenMon, item.soLuong, item.donGia));
            }
        }

        HoaDonDraftSession.setMonItems(newContext, draftItems);

        String maKH = HoaDonDraftSession.getMaKH(oldContext);
        String maKM = HoaDonDraftSession.getMaKM(oldContext);
        int diemDung = HoaDonDraftSession.getDiemDung(oldContext);
        HoaDonDraftSession.setInvoiceMetadata(newContext,
                maKH != null && !maKH.isBlank() ? maKH : null,
                maKM != null && !maKM.isBlank() ? maKM : null,
                diemDung);

        HoaDonDraftSession.clear(oldContext);

        capNhatTrangThaiBanSauKhiDoiBan(oldBanSet, newSelectedTables);

        setDatMonContext(newContext);
        JOptionPane.showMessageDialog(this, "Đã cập nhật bàn phục vụ: " + newContext, "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void capNhatTrangThaiBanSauKhiDoiBan(Set<String> oldBanSet, Set<String> newBanSet) {
        BanService banService = new BanService();

        Set<String> banDaHuyChon = new HashSet<>(oldBanSet);
        banDaHuyChon.removeAll(newBanSet);

        for (String maBan : banDaHuyChon) {
            banService.capNhatTrangThaiBan(maBan, TrangThaiBan.TRONG);
        }

        for (String maBan : newBanSet) {
            banService.capNhatTrangThaiBan(maBan, TrangThaiBan.DANG_DUNG);
        }
    }

    private void capNhatTrangThaiSauKhiLuu() {
        java.util.List<String> maBanList = getMaBanListFromContext();
        if (maBanList.isEmpty()) {
            return;
        }

        BanService banService = new BanService();
        String maPhieuDatContext = HoaDonDraftSession.getCurrentMaPhieuDatContext();

        if (maPhieuDatContext != null && !maPhieuDatContext.isEmpty()) {
            PhieuDatBanService phieuDatBanService = new PhieuDatBanService();
            phieuDatBanService.batDauSuDung(maPhieuDatContext);
            for (String maBan : maBanList) {
                banService.capNhatTrangThaiBan(maBan, TrangThaiBan.DANG_DUNG);
            }
            return;
        }

        for (String maBan : maBanList) {
            banService.capNhatTrangThaiBan(maBan, TrangThaiBan.DANG_DUNG);
        }
    }

    private static class OrderItem {
        private final String maMon;
        private final String tenMon;
        private final double donGia;
        private int soLuong;

        private OrderItem(String maMon, String tenMon, double donGia, int soLuong) {
            this.maMon = maMon;
            this.tenMon = tenMon;
            this.donGia = donGia;
            this.soLuong = soLuong;
        }
    }

    private void searchAndFilter() {
        loadDataToTable();
    }

    private void filterTable() {
        loadDataToTable();
    }

    private int parsePositiveInt(Object value, int fallback) {
        try {
            int parsed = (value instanceof Number) ? ((Number) value).intValue()
                    : Integer.parseInt(String.valueOf(value).trim());
            return Math.max(parsed, 1);
        } catch (Exception ex) {
            return Math.max(fallback, 1);
        }
    }

    private void applyPhieuGoiMonRenderers() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tablePhieuGoiMon.getColumnModel().getColumn(PHIEU_COL_MA_MON).setCellRenderer(centerRenderer);
        tablePhieuGoiMon.getColumnModel().getColumn(PHIEU_COL_SO_LUONG).setCellRenderer(centerRenderer);
        tablePhieuGoiMon.getColumnModel().getColumn(PHIEU_COL_DON_GIA).setCellRenderer(centerRenderer);
        tablePhieuGoiMon.getColumnModel().getColumn(PHIEU_COL_THANH_TIEN).setCellRenderer(centerRenderer);
        tablePhieuGoiMon.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
    }

    private void centerTableColumns(JTable table) {
        ImageRenderer imageRenderer = new ImageRenderer();
        table.getColumnModel().getColumn(0).setCellRenderer(imageRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.setRowHeight(TABLE_IMAGE_ROW_HEIGHT);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private static class QuantitySpinnerEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor {
        private final JSpinner spinner;

        private QuantitySpinnerEditor(int min, int max) {
            spinner = new JSpinner(new SpinnerNumberModel(min, min, max, 1));
            JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");
            spinner.setEditor(editor);
            ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
        }

        @Override
        public Object getCellEditorValue() {
            return ((Number) spinner.getValue()).intValue();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            int current = 1;
            if (value instanceof Number) {
                current = Math.max(((Number) value).intValue(), 1);
            }
            spinner.setValue(current);
            return spinner;
        }
    }

    private static class ButtonRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            JButton btn = new JButton("Xóa");
            btn.setFocusPainted(false);
            btn.setOpaque(true);
            btn.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            btn.setForeground(table.getForeground());
            return btn;
        }
    }

    private static class ButtonEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor {
        private final JButton btn = new JButton("Xóa");
        private final PanelDatMon panel;
        private final Map<String, OrderItem> phieuMap;
        private final JTable table;
        private int currentRow = -1;

        public ButtonEditor(PanelDatMon panel, Map<String, OrderItem> phieuMap, JTable table) {
            this.panel = panel;
            this.phieuMap = phieuMap;
            this.table = table;
            btn.setFocusPainted(false);
            btn.addActionListener(e -> handleDeleteClick());
        }

        private void handleDeleteClick() {
            if (currentRow >= 0 && currentRow < table.getRowCount()) {
                String maMon = String.valueOf(table.getValueAt(currentRow, 0));
                int confirm = JOptionPane.showConfirmDialog(panel,
                        "Bạn có chắc chắn muốn xóa món " + maMon + " khỏi phiếu gọi món không?",
                        "Xác nhận xóa món",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    phieuMap.remove(maMon);
                    panel.refreshPhieuGoiMonTable();
                    isChanged = true;
                }
            }
            fireEditingStopped();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            this.currentRow = row;
            return btn;
        }

        @Override
        public Object getCellEditorValue() {
            return "Xóa";
        }
    }

    private static class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof ImageIcon) {
                JLabel label = new JLabel((ImageIcon) value);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                label.setOpaque(true);
                label.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                label.setBorder(BorderFactory.createEmptyBorder(TABLE_IMAGE_VERTICAL_PADDING, 0,
                        TABLE_IMAGE_VERTICAL_PADDING, 0));
                return label;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    /**
     * Tao placeholder cho TextField
     * Khi focus vao, placeholder bien mat
     * Khi focus out va trong, placeholder xuat hien lai
     */
    private void setupPlaceholder(JTextField textField, String placeholder) {
        Color placeholderColor = new Color(153, 153, 153);
        Color textColor = new Color(0, 0, 0);

        // Set text mac dinh va mau
        textField.setText(placeholder);
        textField.setForeground(placeholderColor);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                // Khi focus vao, neu la placeholder thi xoa
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(textColor);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                // Khi focus out, neu trong thi hien thi placeholder
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(placeholderColor);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblPhieuGoiMon = new javax.swing.JLabel();
        scrTablePhieuGoiMon = new javax.swing.JScrollPane();
        tablePhieuGoiMon = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblTongTienTamTinh = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        cbFilterLoaiMonAn = new javax.swing.JComboBox<>();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        scrDanhSachMonAn = new javax.swing.JScrollPane();
        tableDanhSachMonAn = new javax.swing.JTable();
        btnDoiBan = new javax.swing.JButton();
        btnChonMon = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btnThanhToan = new javax.swing.JButton();
        btnQuayLai = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 251, 233));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 251, 233));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 251, 233));

        lblPhieuGoiMon.setText("Phiếu gọi món");
        lblPhieuGoiMon.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(lblPhieuGoiMon)
                                .addContainerGap(384, Short.MAX_VALUE)));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblPhieuGoiMon)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 509, -1));

        tablePhieuGoiMon.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Tên món ăn", "Số lượng", "Đơn giá", "Thành tiền"
                }) {
            Class[] types = new Class[] {
                    java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        scrTablePhieuGoiMon.setViewportView(tablePhieuGoiMon);

        jPanel2.add(scrTablePhieuGoiMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 510, 480));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tổng tiền tạm tính:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, -1, -1));

        lblTongTienTamTinh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTongTienTamTinh.setForeground(new java.awt.Color(255, 0, 0));
        lblTongTienTamTinh.setText("0 VND");
        jPanel2.add(lblTongTienTamTinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 0, 530, 590));

        jPanel3.setBackground(new java.awt.Color(255, 251, 233));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 251, 233));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 510, Short.MAX_VALUE));
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE));

        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 510, 0));

        jPanel4.setBackground(new java.awt.Color(255, 251, 233));
        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 0, 5));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbFilterLoaiMonAn.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbFilterLoaiMonAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterLoaiMonAnActionPerformed(evt);
            }
        });
        jPanel4.add(cbFilterLoaiMonAn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 130, 30));

        txtTimKiem.setPreferredSize(new java.awt.Dimension(320, 22));
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        jPanel4.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 190, 30));

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        jPanel4.add(btnTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 0, 90, 30));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 580, 40));

        scrDanhSachMonAn.setBackground(new java.awt.Color(255, 251, 233));

        tableDanhSachMonAn.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Hình ảnh", "Mã món ăn", "Tên món ăn", "Đơn vị tính", "Đơn giá"
                }) {
            Class[] types = new Class[] {
                    java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                    java.lang.Double.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        scrDanhSachMonAn.setViewportView(tableDanhSachMonAn);

        jPanel3.add(scrDanhSachMonAn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 560, 480));

        btnDoiBan.setText("Đổi bàn");
        btnDoiBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiBanActionPerformed(evt);
                isChanged = true;
            }
        });
        jPanel3.add(btnDoiBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 90, 30));

        btnChonMon.setText("Chọn");
        btnChonMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonMonActionPerformed(evt);
                isChanged = true;
            }
        });
        jPanel3.add(btnChonMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 550, 90, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 600, 590));

        jPanel7.setBackground(new java.awt.Color(255, 251, 233));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1110, Short.MAX_VALUE));
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 60, Short.MAX_VALUE));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 520, 1110, 60));

        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });
        jPanel1.add(btnThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 620, 134, 33));

        btnQuayLai.setText("Quay lại");
        btnQuayLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLaiActionPerformed(evt);
            }
        });
        jPanel1.add(btnQuayLai, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 620, 97, 36));

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
        filterTable();

    }// GEN-LAST:event_txtTimKiemActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        searchAndFilter();
    }// GEN-LAST:event_btnTimKiemActionPerformed

    private void cbFilterLoaiMonAnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterLoaiMonAnActionPerformed
        filterTable();
    }// GEN-LAST:event_cbFilterLoaiMonAnActionPerformed

    private void btnDoiBanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDoiBanActionPerformed
        try {
            Set<String> oldBanSet = getMaBanSetFromContext();
            if (oldBanSet.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có bàn trong context để đổi.", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int response = JOptionPane.showConfirmDialog(this,
                    "Bạn chắn chắc muốn chọn lại bàn?",
                    "Nhắc nhở",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                MainForm mainForm = (MainForm) SwingUtilities.getWindowAncestor(this);
                if (mainForm != null) {
                    mainForm.startEditBanFromDatMon(oldBanSet, this);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi chọn bàn: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }// GEN-LAST:event_btnDoiBanActionPerformed

    private void btnChonMonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChonMonActionPerformed
        addSelectedMonToPhieuGoiMon();
    }// GEN-LAST:event_btnChonMonActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLuuActionPerformed
        try {
            saveDraftToSession();
            capNhatTrangThaiSauKhiLuu();
            JOptionPane.showMessageDialog(this, "Đã lưu phiếu gọi món tạm thời.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu phiếu gọi món: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }// GEN-LAST:event_btnLuuActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThanhToanActionPerformed
        if (phieuGoiMonMap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một món trước khi thanh toán.");
            return;
        }

        saveDraftToSession();
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentFrame instanceof MainForm) {
            ((MainForm) parentFrame).openPanelLapHoaDon();
        }
    }// GEN-LAST:event_btnThanhToanActionPerformed

    private void btnQuayLaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnQuayLaiActionPerformed
        // TODO add your handling code here:
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentFrame instanceof MainForm) {
            ((MainForm) parentFrame).goBackToPreviousPanel();
        }
    }// GEN-LAST:event_btnQuayLaiActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonMon;
    private javax.swing.JButton btnDoiBan;
    private javax.swing.JButton btnQuayLai;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cbFilterLoaiMonAn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lblPhieuGoiMon;
    private javax.swing.JLabel lblTongTienTamTinh;
    private javax.swing.JScrollPane scrDanhSachMonAn;
    private javax.swing.JScrollPane scrTablePhieuGoiMon;
    private javax.swing.JTable tableDanhSachMonAn;
    private javax.swing.JTable tablePhieuGoiMon;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
