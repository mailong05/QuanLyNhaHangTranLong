package com.restaurant.quanlydatbannhahang.gui;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEnumLoader;
import com.restaurant.quanlydatbannhahang.service.HoaDonService;
import com.restaurant.quanlydatbannhahang.entity.HoaDon;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiHoaDon;
import java.time.LocalDate;
import java.util.List;
public class PanelLichSuHoaDon extends javax.swing.JPanel {
    private HoaDonService hoaDonService;
    private List<HoaDon> allHoaDon;
    public PanelLichSuHoaDon() {
        initComponents();
        hoaDonService = new HoaDonService();
        customUI();
        loadDataToComboBoxes();
        loadDataToTable();
    }
    private void loadDataToComboBoxes() {
        try {
            ActionListener[] trangThaiListeners = cbFilterTrangThai.getActionListeners();
            for (ActionListener listener : trangThaiListeners) {
                cbFilterTrangThai.removeActionListener(listener);
            }
            cbFilterTrangThai.removeAllItems();
            cbFilterTrangThai.addItem("Trạng thái");
            for (TrangThaiHoaDon trangThai : TrangThaiHoaDon
                    .values()) {
                cbFilterTrangThai.addItem(trangThai.getDisplayName());
            }
            for (ActionListener listener : trangThaiListeners) {
                cbFilterTrangThai.addActionListener(listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu filter: " + e.getMessage());
        }
    }
    private void customUI() {
        setupPlaceholder(txtTimKiem, "Nhập mã hóa đơn");
        ComboBoxEnumLoader.loadTrangThaiHoaDonToComboBox(cbFilterTrangThai);
        cbFilterTrangThai.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTimKiem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTimKiem.setFocusPainted(false);
        scrTableLichSuHoaDon.setBorder(BorderFactory.createLineBorder(new Color(200, 190, 170), 1));
        scrTableLichSuHoaDon.setOpaque(false);
        scrTableLichSuHoaDon.getViewport().setOpaque(false);
        JPanel corner = new JPanel();
        corner.setBackground(new Color(255, 251, 233));
        scrTableLichSuHoaDon.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corner);
        tableLichSuHoaDon.setShowGrid(false);
        tableLichSuHoaDon.setIntercellSpacing(new Dimension(0, 0));
        tableLichSuHoaDon.setRowHeight(35);
        tableLichSuHoaDon.setSelectionBackground(new Color(245, 240, 220));
        tableLichSuHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableLichSuHoaDon.getTableHeader()
                .setPreferredSize(new Dimension(tableLichSuHoaDon.getTableHeader().getWidth(), 45));
        tableLichSuHoaDon.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                label.setBackground(new Color(255, 251, 233));
                label.setForeground(new Color(148, 134, 111));
                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 210, 190)));
                return label;
            }
        });
        for(int i = 7; i<=9; i++) {
        	tableLichSuHoaDon.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                        boolean hasFocus, int row, int column) {
                    if (value != null && value instanceof Number) {
                        value = com.restaurant.quanlydatbannhahang.util.CurrencyUtility
                                .formatVND(((Number) value).doubleValue());
                    }
                    setHorizontalAlignment(JLabel.CENTER);
                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
            });
        }
        centerTableColumns(tableLichSuHoaDon);
        if (dpNgayTao != null) {
            dpNgayTao.setDate(LocalDate.now());
            dpNgayTao.addDateChangeListener(event -> loadFilteredData());
        }
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.getSource() != tableLichSuHoaDon && !isMouseOverTable(evt)) {
                    tableLichSuHoaDon.clearSelection();
                    refreshData();
                }
            }
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlHeader = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        cbFilterTrangThai = new javax.swing.JComboBox<>();
        dpNgayTao = new com.github.lgooddatepicker.components.DatePicker();
        scrTableLichSuHoaDon = new javax.swing.JScrollPane();
        tableLichSuHoaDon = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        btnXoaTrang = new javax.swing.JButton();
        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout(0, 10));
        pnlHeader.setOpaque(false);
        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createSequentialGroup()
                                .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dpNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 170,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20,
                                        Short.MAX_VALUE)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 390,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 129,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)));
        pnlHeaderLayout.setVerticalGroup(
                pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(dpNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)));
        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtTimKiem.addActionListener(this::txtTimKiemActionPerformed);
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnTimKiem.addActionListener(this::btnTimKiemActionPerformed);
        cbFilterTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Tất cả", "Đã thanh toán", "Chưa thanh toán", "Đã Hủy" }));
        cbFilterTrangThai.setPreferredSize(new java.awt.Dimension(150, 35));
        cbFilterTrangThai.addActionListener(this::cbFilterTrangThaiActionPerformed);
        dpNgayTao.setPreferredSize(new java.awt.Dimension(170, 35));
        add(pnlHeader, java.awt.BorderLayout.PAGE_START);
        tableLichSuHoaDon.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Mã hóa đơn", "Mã phiếu đặt bàn", "Mã khuyến mãi", "Mã thuế", "Ngày tạo", "Giờ vào", "Giờ ra",
                        "Tổng tiền gốc", "Tiền giảm giá", "Tổng thanh toán", "Phương thức thanh toán",
                        "Trạng thái thanh toán"
                }) {
            Class[] types = new Class[] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                    java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false, false, false, false, false, false
            };
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tableLichSuHoaDon.setRowHeight(35);
        tableLichSuHoaDon.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableLichSuHoaDon.setSelectionBackground(new java.awt.Color(0, 120, 215));
        tableLichSuHoaDon.setSelectionForeground(java.awt.Color.WHITE);
        scrTableLichSuHoaDon.setViewportView(tableLichSuHoaDon);
        if (tableLichSuHoaDon.getColumnModel().getColumnCount() > 0) {
            tableLichSuHoaDon.getColumnModel().getColumn(0).setPreferredWidth(150);
            tableLichSuHoaDon.getColumnModel().getColumn(1).setPreferredWidth(150);
            tableLichSuHoaDon.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableLichSuHoaDon.getColumnModel().getColumn(3).setPreferredWidth(150);
            tableLichSuHoaDon.getColumnModel().getColumn(4).setPreferredWidth(150);
            tableLichSuHoaDon.getColumnModel().getColumn(5).setPreferredWidth(150);
            tableLichSuHoaDon.getColumnModel().getColumn(6).setPreferredWidth(150);
            tableLichSuHoaDon.getColumnModel().getColumn(7).setPreferredWidth(200);
            tableLichSuHoaDon.getColumnModel().getColumn(8).setPreferredWidth(200);
            tableLichSuHoaDon.getColumnModel().getColumn(9).setPreferredWidth(200);
            tableLichSuHoaDon.getColumnModel().getColumn(10).setPreferredWidth(200);
            tableLichSuHoaDon.getColumnModel().getColumn(11).setPreferredWidth(200);
        }
        add(scrTableLichSuHoaDon, java.awt.BorderLayout.CENTER);
        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setLayout(new java.awt.BorderLayout());
        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnTrangChu.setText("Trang chủ");
        btnTrangChu.addActionListener(this::btnTrangChuActionPerformed);
        pnlButton.add(btnTrangChu, java.awt.BorderLayout.WEST);
        btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.addActionListener(this::btnXoaTrangActionPerformed);
        pnlButton.add(btnXoaTrang, java.awt.BorderLayout.EAST);
        add(pnlButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents
    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaTrangActionPerformed
        refreshData();
    }// GEN-LAST:event_btnXoaTrangActionPerformed
    private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTrangChuActionPerformed
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentFrame instanceof MainForm) {
            ((MainForm) parentFrame).goToTrangChuFromPanel();
        }
    }// GEN-LAST:event_btnTrangChuActionPerformed
    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        searchByText();
    }// GEN-LAST:event_btnTimKiemActionPerformed
    private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
        loadFilteredData();
    }// GEN-LAST:event_cbFilterTrangThaiActionPerformed
    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTimKiemActionPerformed
        searchByText();
    }// GEN-LAST:event_txtTimKiemActionPerformed
    private void loadDataToTable() {
        loadFilteredData();
    }
    private void loadFilteredData() {
        try {
            allHoaDon = hoaDonService.getAllHoaDon();
            String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();
            DefaultTableModel model = (DefaultTableModel) tableLichSuHoaDon.getModel();
            model.setRowCount(0);
            for (HoaDon hd : allHoaDon) {
                if (selectedTrangThai != null && !selectedTrangThai.equals("Trạng thái")) {
                    if (hd.getTrangThaiThanhToan() == null
                            || !hd.getTrangThaiThanhToan().getDisplayName().equals(selectedTrangThai)) {
                        continue;
                    }
                }
                if (dpNgayTao != null && dpNgayTao.getDate() != null) {
                    LocalDate selectedDate = dpNgayTao.getDate();
                    if (hd.getNgayTao() == null || !hd.getNgayTao().isEqual(selectedDate)) {
                        continue;
                    }
                }
                model.addRow(new Object[] {
                        hd.getMaHD(),
                        hd.getPhieuDatBan() != null ? hd.getPhieuDatBan().getMaPhieuDat() : "",
                        hd.getKhuyenMai() != null ? hd.getKhuyenMai().getMaKM() : "",
                        hd.getThue() != null ? hd.getThue().getMaThue() : "",
                        hd.getNgayTao(),
                        hd.getGioVao(),
                        hd.getGioRa(),
                        hd.getTongTienGoc(),
                        hd.getTienGiamGia(),
                        hd.getTongThanhToan(),
                        hd.getPhuongThucTT() != null ? hd.getPhuongThucTT().getDisplayName() : "",
                        hd.getTrangThaiThanhToan() != null ? hd.getTrangThaiThanhToan().getDisplayName() : ""
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu: " + e.getMessage());
        }
    }
    private void searchByText() {
        try {
            String searchText = txtTimKiem.getText().trim().toLowerCase();
            String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();
            DefaultTableModel model = (DefaultTableModel) tableLichSuHoaDon.getModel();
            model.setRowCount(0);
            for (HoaDon hd : allHoaDon) {
                if (selectedTrangThai != null && !selectedTrangThai.equals("Trạng thái")) {
                    if (hd.getTrangThaiThanhToan() == null
                            || !hd.getTrangThaiThanhToan().getDisplayName().equals(selectedTrangThai)) {
                        continue;
                    }
                }
                if (dpNgayTao != null && dpNgayTao.getDate() != null) {
                    LocalDate selectedDate = dpNgayTao.getDate();
                    if (hd.getNgayTao() == null || !hd.getNgayTao().isEqual(selectedDate)) {
                        continue;
                    }
                }
                if (!searchText.isEmpty()) {
                    String maHD = hd.getMaHD() != null ? hd.getMaHD().toLowerCase() : "";
                    if (!maHD.contains(searchText)) {
                        continue;
                    }
                }
                model.addRow(new Object[] {
                        hd.getMaHD(),
                        hd.getPhieuDatBan() != null ? hd.getPhieuDatBan().getMaPhieuDat() : "",
                        hd.getKhuyenMai() != null ? hd.getKhuyenMai().getMaKM() : "",
                        hd.getThue() != null ? hd.getThue().getMaThue() : "",
                        hd.getNgayTao(),
                        hd.getGioVao(),
                        hd.getGioRa(),
                        hd.getTongTienGoc(),
                        hd.getTienGiamGia(),
                        hd.getTongThanhToan(),
                        hd.getPhuongThucTT() != null ? hd.getPhuongThucTT().getDisplayName() : "",
                        hd.getTrangThaiThanhToan() != null ? hd.getTrangThaiThanhToan().getDisplayName() : ""
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm dữ liệu: " + e.getMessage());
        }
    }
    public void refreshData() {
        cbFilterTrangThai.setSelectedIndex(0);
        if (dpNgayTao != null) {
            dpNgayTao.setDate(LocalDate.now());
        }
        loadDataToTable();
        setupPlaceholder(txtTimKiem, "Nhập mã hóa đơn");
    }
    private void setupPlaceholder(JTextField textField, String placeholder) {
        Color placeholderColor = new Color(153, 153, 153);
        Color textColor = new Color(0, 0, 0);
        textField.setText(placeholder);
        textField.setForeground(placeholderColor);
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(textColor);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(placeholderColor);
                }
            }
        });
    }
    private void centerTableColumns(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (i != 7 && i!= 8 && i!= 9) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
    }
    private boolean isMouseOverTable(java.awt.event.MouseEvent evt) {
        java.awt.Point p = evt.getPoint();
        java.awt.Point tablePoint = SwingUtilities.convertPoint(this, p, tableLichSuHoaDon);
        return tableLichSuHoaDon.getBounds().contains(tablePoint);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cbFilterTrangThai;
    private com.github.lgooddatepicker.components.DatePicker dpNgayTao;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JScrollPane scrTableLichSuHoaDon;
    private javax.swing.JTable tableLichSuHoaDon;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}