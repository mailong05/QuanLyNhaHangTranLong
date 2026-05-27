package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import com.restaurant.quanlydatbannhahang.service.ChiTietPhieuDatBanService;
import com.restaurant.quanlydatbannhahang.service.HoaDonService;
import com.restaurant.quanlydatbannhahang.service.PhieuDatBanService;
import com.restaurant.quanlydatbannhahang.util.CurrencyUtility;
import com.restaurant.quanlydatbannhahang.util.IDGeneratorHelper;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;
import com.restaurant.quanlydatbannhahang.service.BanService;
import com.restaurant.quanlydatbannhahang.session.HoaDonDraftSession;
import com.restaurant.quanlydatbannhahang.session.ReservationSession;
import com.restaurant.quanlydatbannhahang.entity.PhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiPhieuDat;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import com.restaurant.quanlydatbannhahang.entity.ChiTietPhieuDatBan;
import java.util.List;

public class PanelQuanLyDatBanTruoc extends javax.swing.JPanel implements MouseListener {
        private PhieuDatBanService pdbService;
        private ChiTietPhieuDatBanService ctpdbService;
        private PanelDatBan panelDatBan;
        private BanService banService;
        private HoaDonService hoaDonService;

        public PanelQuanLyDatBanTruoc() {
                pdbService = new PhieuDatBanService();
                ctpdbService = new ChiTietPhieuDatBanService();
                banService = new BanService();
                hoaDonService = new HoaDonService();
                this.panelDatBan = null;
                initComponents();
                customUI();
                pdbService.tuDongHuyPhieuQuaHan();
                loadDataToTable();
                fillMaDatBan(txtMaPhieuDat);
                pushDataToComboBox(cbFilterTrangThai);
                pushDataToComboBox(cbTrangThai);
                selectDefaultActiveStatus();
                applyFilterAndSearch();
        }

        public void setPanelDatBan(PanelDatBan panelDatBan) {
                this.panelDatBan = panelDatBan;
        }

        public void refreshData() {
                try {
                        clearFields();
                        fillMaDatBan(txtMaPhieuDat);
                        setupPlaceholder(txtTimKiem, "Nhập mã đặt bàn hoặc SĐT khách");
                        loadDataToTable();
                        selectDefaultActiveStatus();
                        applyFilterAndSearch();
                        tableBan.clearSelection();
                        syncCapNhatButtonState();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        private void customUI() {
                setupPlaceholder(txtTimKiem, "Nhập mã đặt bàn hoặc SĐT khách");
                if (dtpThoiGianDen != null) {
                        dtpThoiGianDen.setDateTimeStrict(LocalDateTime.now());
                }
                MainForm.attachGoHomeListener(btnTrangChu, this);
                
               
                tableBan.addMouseListener(this);
                tableBan.getSelectionModel().addListSelectionListener(e -> {
                        if (!e.getValueIsAdjusting()) {
                                int rowSelected = tableBan.getSelectedRow();
                                if (rowSelected >= 0) {
                                        pushDataToFieldsFromRow(rowSelected);
                                }
                                syncCapNhatButtonState();
                        }
                });
                this.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                                if (evt.getSource() != tableBan && !isMouseOverTable(evt)) {
                                        tableBan.clearSelection();
                                        clearFields();
                                        syncCapNhatButtonState();
                                        fillMaDatBan(txtMaPhieuDat);
                                }
                        }
                });
                syncCapNhatButtonState();
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

        private void pushDataToComboBox(JComboBox<String> cb) {
                java.awt.event.ActionListener[] listeners = cb.getActionListeners();
                for (java.awt.event.ActionListener l : listeners) {
                        cb.removeActionListener(l);
                }

                cb.removeAllItems();
                cb.addItem("Trạng thái");
                for (TrangThaiPhieuDat trangThai : TrangThaiPhieuDat.values()) {
                        cb.addItem(trangThai.getDisplayName());
                }

                if (cb == cbFilterTrangThai) {
                        selectDefaultActiveStatus();
                }

                for (java.awt.event.ActionListener l : listeners) {
                        cb.addActionListener(l);
                }
        }

        private void selectDefaultActiveStatus() {
                for (int i = 0; i < cbFilterTrangThai.getItemCount(); i++) {
                        String item = cbFilterTrangThai.getItemAt(i);
                        if (TrangThaiPhieuDat.DANG_CHO.getDisplayName().equals(item)) {
                                cbFilterTrangThai.setSelectedIndex(i);
                                return;
                        }
                }
                if (cbFilterTrangThai.getItemCount() > 0) {
                        cbFilterTrangThai.setSelectedIndex(1);
                }
        }

        private void loadDataToTable() {
                ArrayList<PhieuDatBan> listPDB = (ArrayList<PhieuDatBan>) pdbService.getAllPhieuDatBan();
                DefaultTableModel model = (DefaultTableModel) tableBan.getModel();
                model.setRowCount(0);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                for (PhieuDatBan p : listPDB) {
                        String ngayLap = p.getNgayLapPhieu().format(formatter);
                        String thoiGian = p.getThoiGianDen().format(formatter);
                        String listMaBan = ctpdbService.getChiTietByMaPhieuDat(p.getMaPhieuDat())
                                        .stream()
                                        .map(ct -> ct.getBan().getMaBan())
                                        .collect(Collectors.joining(", "));
                        String sdtKH = (p.getKhachHang() != null) ? p.getKhachHang().getSdt() : "";
                        model.addRow(new Object[] {
                                        p.getMaPhieuDat(),
                                        sdtKH,
                                        p.getNhanVien().getMaNV(),
                                        listMaBan,
                                        p.getSoLuongNguoi(),
                                        ngayLap,
                                        thoiGian,
                                        p.getTienDatCoc(),
                                        p.getTrangThai().getDisplayName()
                        });
                }
                DefaultTableCellRenderer statusAwareRenderer = new DefaultTableCellRenderer() {
                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value,
                                        boolean isSelected, boolean hasFocus, int row, int column) {
                                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                                int modelRow = table.convertRowIndexToModel(row);
                                Object statusValue = table.getModel().getValueAt(modelRow, 8);
                                String status = statusValue != null ? statusValue.toString() : "";
                                if ("Đã hủy".equals(status) || "Đã sử dụng".equals(status)) {
                                        setForeground(new Color(153, 153, 153));
                                        setFont(getFont().deriveFont(Font.ITALIC));
                                } else {
                                        setForeground(Color.BLACK);
                                        setFont(getFont().deriveFont(Font.PLAIN));
                                }
                                setHorizontalAlignment(JLabel.CENTER);
                                return this;
                        }
                };
                for (int i = 0; i < tableBan.getColumnCount(); i++) {
                        if (i != 7) {
                                tableBan.getColumnModel().getColumn(i).setCellRenderer(statusAwareRenderer);
                        }
                }
                DefaultTableCellRenderer currencyRenderer = new DefaultTableCellRenderer() {
                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value,
                                        boolean isSelected, boolean hasFocus, int row, int column) {
                                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                                                row, column);
                                int modelRow = table.convertRowIndexToModel(row);
                                Object statusValue = table.getModel().getValueAt(modelRow, 8);
                                String status = statusValue != null ? statusValue.toString() : "";
                                if ("Đã hủy".equals(status) || "Đã sử dụng".equals(status)) {
                                        setForeground(new Color(153, 153, 153));
                                        setFont(getFont().deriveFont(Font.ITALIC));
                                } else {
                                        setForeground(Color.BLACK);
                                        setFont(getFont().deriveFont(Font.PLAIN));
                                }
                                return c;
                        }

                        @Override
                        protected void setValue(Object value) {
                                if (value instanceof Double) {
                                        setText(com.restaurant.quanlydatbannhahang.util.CurrencyUtility
                                                        .formatVND((Double) value));
                                } else {
                                        super.setValue(value);
                                }
                        }
                };
                currencyRenderer.setHorizontalAlignment(JLabel.RIGHT);
                tableBan.getColumnModel().getColumn(7).setCellRenderer(currencyRenderer);
        }

        private boolean isMouseOverTable(java.awt.event.MouseEvent evt) {
                java.awt.Point p = evt.getPoint();
                java.awt.Point tablePoint = javax.swing.SwingUtilities.convertPoint(this, p, tableBan);
                return tableBan.getBounds().contains(tablePoint);
        }

        private void clearFields() {
                txtSoDienThoai.setText("");
                txtMaNhanVien.setText("");
                txtMaBan.setText("");
                spSoNguoi.setValue(0);
                dtpThoiGianDen.setDateTimeStrict(LocalDateTime.now());
                cbTrangThai.setSelectedIndex(0);
                txtTienDatCoc.setText("0.0");
        }

        private void syncCapNhatButtonState() {
                int selectedRow = tableBan.getSelectedRow();
                boolean hasSelection = selectedRow >= 0;
                btnCapNhat.setEnabled(hasSelection);
                btnChonMon.setEnabled(hasSelection);
                btnXoa.setEnabled(hasSelection);
                if (!hasSelection) {
                        btnDoiBan.setEnabled(false);
                } else {
                        int modelRow = tableBan.convertRowIndexToModel(selectedRow);
                        Object statusValue = tableBan.getModel().getValueAt(modelRow, 8);
                        String status = statusValue == null ? "" : statusValue.toString();
                        btnDoiBan.setEnabled(TrangThaiPhieuDat.DANG_CHO.getDisplayName().equals(status));
                }
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                pnlHeader = new javax.swing.JPanel();
                lblTitle = new javax.swing.JLabel();
                pnlThongTinBan = new javax.swing.JPanel();
                lblMaPhieuDat = new javax.swing.JLabel();
                txtMaPhieuDat = new javax.swing.JTextField();
                lblMaKhachHang = new javax.swing.JLabel();
                txtSoDienThoai = new javax.swing.JTextField();
                lblThoiGianDen = new javax.swing.JLabel();
                lblGhiChu = new javax.swing.JLabel();
                lblMaNhanVien = new javax.swing.JLabel();
                txtMaNhanVien = new javax.swing.JTextField();
                txtTimKiem = new javax.swing.JTextField();
                btnTimKiem = new javax.swing.JButton();
                lblTienDatCoc = new javax.swing.JLabel();
                dtpThoiGianDen = new com.github.lgooddatepicker.components.DateTimePicker();
                cbTrangThai = new javax.swing.JComboBox<>();
                txtMaBan = new javax.swing.JTextField();
                lblMaBan = new javax.swing.JLabel();
                spSoNguoi = new javax.swing.JSpinner();
                cbFilterTrangThai = new javax.swing.JComboBox<>();
                lblSoLuong = new javax.swing.JLabel();
                txtTienDatCoc = new javax.swing.JTextField();
                scrTableBan = new javax.swing.JScrollPane();
                tableBan = new javax.swing.JTable();
                pnlButton = new javax.swing.JPanel();
                btnTrangChu = new javax.swing.JButton();
                pnlRightButtons = new javax.swing.JPanel();
                btnXoaTrang = new javax.swing.JButton();
                btnCapNhat = new javax.swing.JButton();
                btnXoa = new javax.swing.JButton();
                btnChonMon = new javax.swing.JButton();
                btnDoiBan = new javax.swing.JButton();

                setBackground(new java.awt.Color(255, 251, 233));
                setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
                setLayout(new java.awt.BorderLayout(0, 10));

                pnlHeader.setOpaque(false);
                pnlHeader.setLayout(new java.awt.BorderLayout(0, 15));

                lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 18));
                lblTitle.setText("Quản lý thông tin bàn ăn trong nhà hàng");
                pnlHeader.add(lblTitle, java.awt.BorderLayout.WEST);

                pnlThongTinBan.setBackground(new java.awt.Color(255, 251, 233));

                lblMaPhieuDat.setText("Mã phiếu đặt:");
                lblMaPhieuDat.setFont(new java.awt.Font("Segoe UI", 0, 14));

                txtMaPhieuDat.setEditable(false);
                txtMaPhieuDat.setFont(new java.awt.Font("Segoe UI", 0, 14));
                txtMaPhieuDat.setFocusable(false);
                txtMaPhieuDat.setPreferredSize(new java.awt.Dimension(0, 35));
                txtMaPhieuDat.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtMaPhieuDatActionPerformed(evt);
                        }
                });

                lblMaKhachHang.setText("Số điện thoại");
                lblMaKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14));

                txtSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 14));
                txtSoDienThoai.setPreferredSize(new java.awt.Dimension(0, 35));

                lblThoiGianDen.setText("Thời gian đến:");
                lblThoiGianDen.setFont(new java.awt.Font("Segoe UI", 0, 14));

                lblGhiChu.setText("Trạng thái:");
                lblGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 14));

                lblMaNhanVien.setText("Mã nhân viên:");
                lblMaNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14));

                txtMaNhanVien.setEditable(false);
                txtMaNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14));
                txtMaNhanVien.setFocusable(false);
                txtMaNhanVien.setPreferredSize(new java.awt.Dimension(0, 35));

                txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14));
                txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtTimKiemActionPerformed(evt);
                        }
                });

                btnTimKiem.setText("Tìm kiếm");
                btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14));
                btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnTimKiemActionPerformed(evt);
                        }
                });

                lblTienDatCoc.setText("Tiền đặt cọc:");
                lblTienDatCoc.setFont(new java.awt.Font("Segoe UI", 0, 14));

                dtpThoiGianDen.setPreferredSize(new java.awt.Dimension(228, 35));

                cbTrangThai.setPreferredSize(new java.awt.Dimension(72, 35));

                txtMaBan.setEditable(false);
                txtMaBan.setFocusable(false);
                txtMaBan.setPreferredSize(new java.awt.Dimension(0, 35));
                txtMaBan.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtMaBanActionPerformed(evt);
                        }
                });

                lblMaBan.setText("Mã bàn:");
                lblMaBan.setFont(new java.awt.Font("Segoe UI", 0, 14));

                cbFilterTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
                cbFilterTrangThai.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cbFilterTrangThaiActionPerformed(evt);
                        }
                });

                lblSoLuong.setText("Số người:");
                lblSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14));

                txtTienDatCoc.setEditable(false);
                txtTienDatCoc.setEnabled(false);

                javax.swing.GroupLayout pnlThongTinBanLayout = new javax.swing.GroupLayout(pnlThongTinBan);
                pnlThongTinBan.setLayout(pnlThongTinBanLayout);
                pnlThongTinBanLayout.setHorizontalGroup(
                                pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                                                .addGroup(pnlThongTinBanLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                pnlThongTinBanLayout
                                                                                                                                                .createSequentialGroup()
                                                                                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                .addComponent(lblMaNhanVien,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                .addGap(31, 31, 31))
                                                                                                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                .addComponent(lblMaBan)
                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                Short.MAX_VALUE)))
                                                                                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                                false)
                                                                                                                                                                .addComponent(txtMaNhanVien,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                .addComponent(txtMaBan,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                377,
                                                                                                                                                                                Short.MAX_VALUE)))
                                                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addComponent(lblMaPhieuDat)
                                                                                                                                                .addComponent(lblMaKhachHang))
                                                                                                                                .addGap(33, 33, 33)
                                                                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addComponent(txtSoDienThoai,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                376,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(txtMaPhieuDat,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE))))
                                                                                                .addGap(69, 69, 69)
                                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(lblThoiGianDen,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                102,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(lblGhiChu)
                                                                                                                .addComponent(lblTienDatCoc,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                88,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(lblSoLuong,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                88,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                .createSequentialGroup()
                                                                                                .addContainerGap()
                                                                                                .addComponent(cbFilterTrangThai,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                124,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(pnlThongTinBanLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                pnlThongTinBanLayout
                                                                                                                .createSequentialGroup()
                                                                                                                .addComponent(txtTimKiem)
                                                                                                                .addPreferredGap(
                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                .addComponent(btnTimKiem,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                134,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                false)
                                                                                                                .addComponent(txtTienDatCoc,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(spSoNguoi)
                                                                                                                .addComponent(cbTrangThai,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                0,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(dtpThoiGianDen,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                244,
                                                                                                                                Short.MAX_VALUE))
                                                                                                .addContainerGap(180,
                                                                                                                Short.MAX_VALUE)))));
                pnlThongTinBanLayout.setVerticalGroup(
                                pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                                                .addGroup(pnlThongTinBanLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(lblThoiGianDen,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                23,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(dtpThoiGianDen,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                false)
                                                                                                                .addComponent(txtSoDienThoai,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                pnlThongTinBanLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                .addComponent(lblGhiChu)
                                                                                                                                                .addComponent(cbTrangThai,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE))))
                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(lblMaPhieuDat,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                26,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(txtMaPhieuDat,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(lblMaKhachHang,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                26,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(12, 12, 12)
                                                                .addGroup(pnlThongTinBanLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(txtMaNhanVien,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(spSoNguoi,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                35,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(lblSoLuong))
                                                                                .addComponent(lblMaNhanVien))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(pnlThongTinBanLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(lblMaBan)
                                                                                .addComponent(lblTienDatCoc)
                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(txtMaBan,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                34,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(txtTienDatCoc,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                34,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(15, 15, 15)
                                                                .addGroup(pnlThongTinBanLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(pnlThongTinBanLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(txtTimKiem,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                35,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(cbFilterTrangThai,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                35,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addComponent(btnTimKiem,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                35,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap()));

                pnlHeader.add(pnlThongTinBan, java.awt.BorderLayout.PAGE_END);

                add(pnlHeader, java.awt.BorderLayout.PAGE_START);

                tableBan.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {

                                },
                                new String[] {
                                                "Mã phiếu đặt", "Số điện thoại", "Mã nhân viên", "Mã bàn", "Số người",
                                                "Ngày lập phiếu", "Thời gian đến", "Tiền đặt cọc", "Trạng thái"
                                }) {
                        Class[] types = new Class[] {
                                        java.lang.String.class, java.lang.String.class, java.lang.String.class,
                                        java.lang.String.class, java.lang.Integer.class, java.lang.Object.class,
                                        java.lang.Object.class, java.lang.String.class, java.lang.String.class
                        };
                        boolean[] canEdit = new boolean[] {
                                        false, false, false, false, false, false, false, false, false
                        };

                        public Class getColumnClass(int columnIndex) {
                                return types[columnIndex];
                        }

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit[columnIndex];
                        }
                });
                tableBan.setRowHeight(35);
                scrTableBan.setViewportView(tableBan);

                add(scrTableBan, java.awt.BorderLayout.CENTER);

                pnlButton.setBackground(new java.awt.Color(255, 251, 233));
                pnlButton.setPreferredSize(new java.awt.Dimension(100, 27));
                pnlButton.setLayout(new java.awt.BorderLayout(0, 5));

                btnTrangChu.setText("Trang Chủ");
                btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14));
                btnTrangChu.setPreferredSize(new java.awt.Dimension(100, 27));
                pnlButton.add(btnTrangChu, java.awt.BorderLayout.WEST);

                pnlRightButtons.setBackground(new java.awt.Color(255, 251, 233));
                pnlRightButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 15, 0));

                btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 0, 14));
                btnXoaTrang.setText("Xóa trắng");
                btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnXoaTrangActionPerformed(evt);
                        }
                });
                pnlRightButtons.add(btnXoaTrang);

                btnCapNhat.setText("Cập nhật");
                btnCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 14));
                btnCapNhat.setPreferredSize(new java.awt.Dimension(90, 27));
                btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnCapNhatActionPerformed(evt);
                        }
                });
                pnlRightButtons.add(btnCapNhat);

                btnXoa.setText("Xóa");
                btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14));
                btnXoa.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnXoaActionPerformed(evt);
                        }
                });
                pnlRightButtons.add(btnXoa);

                btnChonMon.setText("Chọn món");
                btnChonMon.setFont(new java.awt.Font("Segoe UI", 0, 14));
                btnChonMon.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnChonMonActionPerformed(evt);
                        }
                });
                pnlRightButtons.add(btnChonMon);

                btnDoiBan.setText("Đổi bàn");
                btnDoiBan.setFont(new java.awt.Font("Segoe UI", 0, 14));
                btnDoiBan.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnDoiBanActionPerformed(evt);
                        }
                });
                pnlRightButtons.add(btnDoiBan);

                pnlButton.add(pnlRightButtons, java.awt.BorderLayout.EAST);

                add(pnlButton, java.awt.BorderLayout.PAGE_END);
        }// </editor-fold>//GEN-END:initComponents

        private void fillMaDatBan(JTextField txtMaPhieuDat) {
                String lastID = IDQueryHelper.getLastID("PhieuDatBan", "maPhieuDat");
                String maPDBNew = (lastID == null || lastID.isEmpty()) ? IDGeneratorHelper.generateDefaultID("PD")
                                : IDGeneratorHelper.generateNextIDFromFullID(lastID);
                txtMaPhieuDat.setText(maPDBNew);
        }

        private void centerTableColumns(JTable table) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for (int i = 0; i < table.getColumnCount(); i++) {
                        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }
        }

        private void applyFilterAndSearch() {
                try {
                        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) tableBan
                                        .getRowSorter();
                        if (sorter == null) {
                                sorter = new TableRowSorter<>((DefaultTableModel) tableBan.getModel());
                                tableBan.setRowSorter(sorter);
                        }

                        java.util.List<RowFilter<DefaultTableModel, Object>> filters = new java.util.ArrayList<>();

                        String selectedStatus = (String) cbFilterTrangThai.getSelectedItem();
                        if (selectedStatus != null && !selectedStatus.equals("Trạng thái")
                                        && !selectedStatus.trim().isEmpty()) {
                                filters.add(RowFilter.regexFilter(
                                                "^" + java.util.regex.Pattern.quote(selectedStatus) + "$", 8));
                        }

                        String searchText = txtTimKiem.getText().trim();
                        if (!searchText.isEmpty() && !searchText.equals("Nhập mã đặt bàn hoặc SĐT khách")) {
                                String pattern = "(?i).*" + java.util.regex.Pattern.quote(searchText) + ".*";
                                java.util.List<RowFilter<DefaultTableModel, Object>> searchFilters = new java.util.ArrayList<>();
                                searchFilters.add(RowFilter.regexFilter(pattern, 0));
                                searchFilters.add(RowFilter.regexFilter(pattern, 1));
                                filters.add(RowFilter.orFilter(searchFilters));
                        }

                        if (filters.isEmpty()) {
                                sorter.setRowFilter(null);
                        } else {
                                sorter.setRowFilter(RowFilter.andFilter(filters));
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        private void capNhatTrangThaiBanThongMinh(String maBan) {
                try {
                        boolean hasToday = pdbService.hasReservationToday(maBan);
                        if (hasToday) {
                                banService.capNhatTrangThaiBan(maBan, TrangThaiBan.DA_DAT);
                        } else {
                                banService.capNhatTrangThaiBan(maBan, TrangThaiBan.TRONG);
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {
                refreshData();
        }

        private void btnDoiBanActionPerformed(java.awt.event.ActionEvent evt) {
                try {
                        String maPDB = txtMaPhieuDat.getText().trim();
                        if (maPDB.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu đặt bàn trước khi chọn bàn",
                                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                                return;
                        }
                        PhieuDatBan phieu = pdbService.getPhieuDatBanTheoMa(maPDB);
                        if (phieu == null) {
                                JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu đặt bàn", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        Set<String> oldBanSet = getTableSetFromDB(maPDB);

                        int response = JOptionPane.showConfirmDialog(this,
                                        "Bạn chắn chắc muốn chọn lại bàn?", "Nhắc nhở",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                        if (response == JOptionPane.YES_OPTION) {
                                if (panelDatBan != null) {
                                        panelDatBan.setPanelQuanLyDatBanTruoc(this);
                                        panelDatBan.setFlowOrigin("QUAN_LY_DAT_TRUOC");
                                        panelDatBan.setSelectedTablesForEdit(oldBanSet, phieu.getThoiGianDen());
                                        storeMaPDBForEditMode(maPDB);

                                        MainForm mainForm = (MainForm) SwingUtilities.getWindowAncestor(this);
                                        if (mainForm != null) {
                                                mainForm.startEditBanFromQuanLyDatTruoc();
                                        }
                                } else {
                                        JOptionPane.showMessageDialog(this, "PanelDatBan không khả dụng", "Lỗi",
                                                        JOptionPane.ERROR_MESSAGE);
                                }
                        }
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi chọn bàn: " + e.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        private String maPDBEditMode = null;
        private Set<String> newSelectedTablesForEdit = null;

        private void storeMaPDBForEditMode(String maPDB) {
                this.maPDBEditMode = maPDB;
        }

        private Set<String> getTableSetFromDB(String maPDB) {
                Set<String> banSet = new HashSet<>();
                List<ChiTietPhieuDatBan> chiTietList = ctpdbService.getChiTietByMaPhieuDat(maPDB);
                if (chiTietList != null) {
                        for (ChiTietPhieuDatBan ct : chiTietList) {
                                banSet.add(ct.getBan().getMaBan());
                        }
                }
                return banSet;
        }

        private void capNhatTrangThaiBan(List<ChiTietPhieuDatBan> list) {
                try {

                        for (ChiTietPhieuDatBan ctpdb : list) {
                                String maBan = ctpdb.getBan().getMaBan();
                                if (!maBan.isEmpty())
                                        capNhatTrangThaiBanThongMinh(maBan);

                        }
                } catch (Exception e) {
                        throw new RuntimeException(
                                        "Không thể cập nhật trạng thái bàn sau khi thanh toán: " + e.getMessage(), e);
                }
        }

        public void updateMaBanForEdit(Set<String> newSelectedTables) {
                if (newSelectedTables != null && !newSelectedTables.isEmpty()) {
                        String banList = String.join(", ", newSelectedTables);
                        txtMaBan.setText(banList);
                        this.newSelectedTablesForEdit = new HashSet<>(newSelectedTables);
                        storeMaPDBForEditMode(txtMaPhieuDat.getText().trim());
                }
        }

        public void finishEditBansFromPanelDatBan(Set<String> newSelectedTables) {
                if (maPDBEditMode == null || maPDBEditMode.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu cần chỉnh sửa", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }
                try {
                        Set<String> oldBanSet = getTableSetFromDB(maPDBEditMode);
                        updateChiTietPhieuDatBan(maPDBEditMode, oldBanSet, newSelectedTables);
                        maPDBEditMode = null;
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật bàn: " + e.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        private void updateChiTietPhieuDatBan(String maPDB, Set<String> oldBanSet, Set<String> newBanSet)
                        throws IllegalArgumentException {
                try {
                        ctpdbService.updateBanInPhieu(maPDB, oldBanSet, newBanSet);
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(e);
                }
                if (panelDatBan != null) {
                        panelDatBan.updateAllTableStatusFromDatabase();
                }
                JOptionPane.showMessageDialog(this, "Cập nhật bàn thành công", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
        }

        private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {
                filterAction();
        }

        @SuppressWarnings("unchecked")
        private void filterAction() {
                applyFilterAndSearch();
        }

        private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {
                try {
                        String maPDB = txtMaPhieuDat.getText().trim();
                        if (maPDB.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu đặt bàn để cập nhật",
                                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                                return;
                        }
                        PhieuDatBan phieu = pdbService.getPhieuDatBanTheoMa(maPDB);
                        if (phieu == null) {
                                JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu đặt bàn", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        Set<String> newBanSet = null;
                        if (newSelectedTablesForEdit != null && !newSelectedTablesForEdit.isEmpty()) {
                                newBanSet = new HashSet<>(newSelectedTablesForEdit);
                        } else {
                                String maBanText = txtMaBan.getText().trim();
                                if (!maBanText.isEmpty()) {
                                        newBanSet = new HashSet<>();
                                        for (String ban : maBanText.split(",")) {
                                                String banTrim = ban.trim();
                                                if (!banTrim.isEmpty()) {
                                                        newBanSet.add(banTrim);
                                                }
                                        }
                                }
                        }

                        java.time.LocalDateTime newThoiGianDen = dtpThoiGianDen.getDateTimeStrict();
                        String trangThaiStr = (String) cbTrangThai.getSelectedItem();
                        TrangThaiPhieuDat trangThaiMoi = TrangThaiPhieuDat.fromDisplayName(trangThaiStr);

                        if (newBanSet != null && !newBanSet.isEmpty() && trangThaiMoi == TrangThaiPhieuDat.DANG_CHO) {
                                java.time.LocalDate targetDate = newThoiGianDen.toLocalDate();
                                java.util.List<String> banTrung = new java.util.ArrayList<>();

                                for (String maBan : newBanSet) {
                                        if (pdbService.kiemTraBanDaDuocDatTrongNgay(maBan, targetDate, maPDB)) {
                                                banTrung.add(maBan);
                                        }
                                }

                                if (!banTrung.isEmpty()) {
                                        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter
                                                        .ofPattern("dd/MM/yyyy");
                                        JOptionPane.showMessageDialog(this,
                                                        "Các bàn sau đã có khách đặt vào ngày "
                                                                        + targetDate.format(formatter) +
                                                                        ":\n" + String.join(", ", banTrung)
                                                                        + "\n\nVui lòng chọn ngày khác hoặc đổi bàn!",
                                                        "Trùng lịch đặt bàn", JOptionPane.WARNING_MESSAGE);
                                        return;
                                }
                        }

                        phieu.setSoLuongNguoi((Integer) spSoNguoi.getValue());
                        phieu.setThoiGianDen(newThoiGianDen);
                        phieu.setTrangThai(trangThaiMoi);
                        double tienDatCoc = CurrencyUtility.parseVND(txtTienDatCoc.getText().trim());
                        phieu.setTienDatCoc(tienDatCoc);

                        pdbService.capNhatPhieuDatBan(phieu);

                        if (newSelectedTablesForEdit != null && !newSelectedTablesForEdit.isEmpty()) {
                                newSelectedTablesForEdit = null;
                        }

                        if (newBanSet != null && !newBanSet.isEmpty()) {
                                Set<String> oldBanSet = getTableSetFromDB(maPDB);
                                if (!oldBanSet.equals(newBanSet)) {
                                        String oldContext = HoaDonDraftSession
                                                        .normalizeMaBanContext(String.join(",", oldBanSet));
                                        String newContext = HoaDonDraftSession
                                                        .normalizeMaBanContext(String.join(",", newBanSet));
                                        if (!oldContext.isEmpty() && !newContext.isEmpty()
                                                        && !oldContext.equals(newContext)
                                                        && HoaDonDraftSession.hasDraft(oldContext)) {
                                                updateChiTietPhieuDatBan(maPDB, oldBanSet, newBanSet);
                                                HoaDonDraftSession.migrateContext(oldContext, newContext);
                                        } else {
                                                updateChiTietPhieuDatBan(maPDB, oldBanSet, newBanSet);
                                        }
                                        Set<String> banCanThem = new HashSet<>(newBanSet);
                                        banCanThem.removeAll(oldBanSet);
                                        Set<String> banCanXoa = new HashSet<>(oldBanSet);
                                        banCanXoa.removeAll(newBanSet);
                                        for (String maBan : banCanXoa) {
                                                capNhatTrangThaiBanThongMinh(maBan);
                                        }
                                        for (String maBan : banCanThem) {
                                                capNhatTrangThaiBanThongMinh(maBan);
                                        }
                                }
                        }
                        if (panelDatBan != null) {
                                panelDatBan.refreshData();
                        }
                        JOptionPane.showMessageDialog(this, "Cập nhật phiếu đặt bàn thành công", "Thành công",
                                        JOptionPane.INFORMATION_MESSAGE);
                        refreshData();
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật: " + e.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {
            try {
                    String maPDB = txtMaPhieuDat.getText().trim();
                    if (maPDB.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu đặt bàn để xóa", "Thông báo",
                                            JOptionPane.WARNING_MESSAGE);
                            return;
                    }
                    int result = JOptionPane.showConfirmDialog(this,
                                    "Bạn có chắc chắn muốn xóa mềm phiếu đặt bàn " + maPDB
                                                    + " bằng cách chuyển thành trạng thái 'Đã hủy' không?",
                                    "Xác nhận xóa",
                                    JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                            List<ChiTietPhieuDatBan> chiTietList = ctpdbService.getChiTietByMaPhieuDat(maPDB);
                            
                            String maBanContextRaw = chiTietList.stream()
                                             .map(ct -> ct.getBan().getMaBan())
                                             .collect(Collectors.joining(","));
                            String normalizedContext = HoaDonDraftSession.normalizeMaBanContext(maBanContextRaw);
                            if (!normalizedContext.isEmpty()) {
                                    HoaDonDraftSession.clear(normalizedContext); 
                            }

                            HoaDonDraftSession.clearByMaPhieu(maPDB);
                            pdbService.capNhatTrangThaiPhieu(maPDB, TrangThaiPhieuDat.DA_HUY);
                            
                            capNhatTrangThaiBan(chiTietList);
                            
                            if (panelDatBan != null) {
                                    panelDatBan.updateAllTableStatusFromDatabase();
                            }
                            JOptionPane.showMessageDialog(this, "Xóa phiếu đặt bàn và giải phóng bàn thành công", "Thành công",
                                            JOptionPane.INFORMATION_MESSAGE);
                            refreshData();
                    }
            } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + e.getMessage(), "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
            }
    }

        private void txtMaPhieuDatActionPerformed(java.awt.event.ActionEvent evt) {
        }

        private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
                applyFilterAndSearch();
        }

        private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
        }

        private void btnChonMonActionPerformed(java.awt.event.ActionEvent evt) {
                chonMonAction();
        }

        private void chonMonAction() {
                int rowSelected = tableBan.getSelectedRow();
                if (rowSelected < 0) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu đặt bàn trước khi chọn món.");
                        return;
                }
                int modelRow = tableBan.convertRowIndexToModel(rowSelected);
                String maPhieuDat = String.valueOf(tableBan.getModel().getValueAt(modelRow, 0));
                String maBanContext = String.valueOf(tableBan.getModel().getValueAt(modelRow, 3));
                String soDienThoai = String.valueOf(tableBan.getModel().getValueAt(modelRow, 1));
                HoaDonDraftSession.luuGioVao(maBanContext, LocalDateTime.now());

                HoaDonDraftSession.setCurrentMaBanContext(maBanContext);
                HoaDonDraftSession.setCurrentMaPhieuDatContext(maPhieuDat);
                HoaDonDraftSession.setCurrentPhoneNumber(soDienThoai);
                pdbService.capNhatThoiGianDen(maPhieuDat, LocalDateTime.now());
                
                java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
                if (parentFrame instanceof MainForm) {
                        ((MainForm) parentFrame).openPanelDatMon(maBanContext);
                }
        }

        private void pushDataToFieldsFromRow(int rowIndex) {
                String maPDB = (String) tableBan.getValueAt(rowIndex, 0);
                String sdt = (String) tableBan.getValueAt(rowIndex, 1);
                String maNV = (String) tableBan.getValueAt(rowIndex, 2);
                String maBan = (String) tableBan.getValueAt(rowIndex, 3);
                Integer soNguoi = (Integer) tableBan.getValueAt(rowIndex, 4);
                String trangThai = (String) tableBan.getValueAt(rowIndex, 8);
                double tienDatCoc = (double) tableBan.getValueAt(rowIndex, 7);
                String thoiGianDenStr = (String) tableBan.getValueAt(rowIndex, 6);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime thoiGianDen = LocalDateTime.parse(thoiGianDenStr, formatter);
                txtMaPhieuDat.setText(maPDB);
                txtSoDienThoai.setText(sdt);
                txtMaNhanVien.setText(maNV);
                txtMaBan.setText(maBan);
                dtpThoiGianDen.setDateTimeStrict(thoiGianDen);
                spSoNguoi.setValue(soNguoi);
                cbTrangThai.setSelectedItem(trangThai);
                txtTienDatCoc.setText(com.restaurant.quanlydatbannhahang.util.CurrencyUtility.formatVND(tienDatCoc));
        }

        private void txtMaBanActionPerformed(java.awt.event.ActionEvent evt) {
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnCapNhat;
        private javax.swing.JButton btnChonMon;
        private javax.swing.JButton btnDoiBan;
        private javax.swing.JButton btnTimKiem;
        private javax.swing.JButton btnTrangChu;
        private javax.swing.JButton btnXoa;
        private javax.swing.JButton btnXoaTrang;
        private javax.swing.JComboBox<String> cbFilterTrangThai;
        private javax.swing.JComboBox<String> cbTrangThai;
        private com.github.lgooddatepicker.components.DateTimePicker dtpThoiGianDen;
        private javax.swing.JLabel lblGhiChu;
        private javax.swing.JLabel lblMaBan;
        private javax.swing.JLabel lblMaKhachHang;
        private javax.swing.JLabel lblMaNhanVien;
        private javax.swing.JLabel lblMaPhieuDat;
        private javax.swing.JLabel lblSoLuong;
        private javax.swing.JLabel lblThoiGianDen;
        private javax.swing.JLabel lblTienDatCoc;
        private javax.swing.JLabel lblTitle;
        private javax.swing.JPanel pnlButton;
        private javax.swing.JPanel pnlHeader;
        private javax.swing.JPanel pnlRightButtons;
        private javax.swing.JPanel pnlThongTinBan;
        private javax.swing.JScrollPane scrTableBan;
        private javax.swing.JSpinner spSoNguoi;
        private javax.swing.JTable tableBan;
        private javax.swing.JTextField txtMaBan;
        private javax.swing.JTextField txtMaNhanVien;
        private javax.swing.JTextField txtMaPhieuDat;
        private javax.swing.JTextField txtSoDienThoai;
        private javax.swing.JTextField txtTienDatCoc;
        private javax.swing.JTextField txtTimKiem;

        // End of variables declaration//GEN-END:variables
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }
}