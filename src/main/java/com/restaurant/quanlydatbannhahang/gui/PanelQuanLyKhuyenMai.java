package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.KhuyenMaiService;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEnumLoader;
import com.restaurant.quanlydatbannhahang.util.IDGeneratorHelper;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;
import com.restaurant.quanlydatbannhahang.entity.KhuyenMai;
import java.util.List;
import java.time.LocalDate;
import java.text.DecimalFormat;

public class PanelQuanLyKhuyenMai extends javax.swing.JPanel implements MouseListener {

        private ActionListener cbFilterTrangThaiListener;

        private IDGeneratorHelper idGeneratorHelper;
        private IDQueryHelper idQueryHelper;

        public PanelQuanLyKhuyenMai() {
                idGeneratorHelper = new IDGeneratorHelper();
                idQueryHelper = new IDQueryHelper();
                initComponents();
                customUI();
                loadDataToComboBoxes();
                loadDataToTable();
        }

        private void customUI() {
                // Placeholder cho txtTimKiem
                setupPlaceholder(txtTimKiem, "Nhập tên hoặc mã khuyến mãi");

                // Load enum trạng thái lên ComboBox
                ComboBoxEnumLoader.loadTrangThaiKhuyenMaiToComboBox(cbFilterTrangThai);

                // Set giá trị mặc định cho DatePicker (ngày hôm nay)
                if (dpNgayBatDau != null) {
                        dpNgayBatDau.setDate(LocalDate.now());
                }
                if (dbNgayKetThuc != null) {
                        dbNgayKetThuc.setDate(LocalDate.now());
                }

                // ========== DESELECT WHEN CLICK OUTSIDE TABLE ==========
                this.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                                if (evt.getSource() != tableKhuyenMai && !isMouseOverTable(evt)) {
                                        tableKhuyenMai.clearSelection();
                                        clearFields();
                                }
                        }
                });

                // Register mouse listener để populate fields khi click vào row
                tableKhuyenMai.addMouseListener(this);
                tableKhuyenMai.getSelectionModel().addListSelectionListener(e -> {
                        if (!e.getValueIsAdjusting()) {
                                int row = tableKhuyenMai.getSelectedRow();
                                if (row >= 0) {
                                        loadDataFromRow(row);
                                }
                                syncCapNhatButtonState();
                        }
                });

                // Gắn sự kiện quay về Trang Chủ
                MainForm.attachGoHomeListener(btnTrangChu, this);
                syncCapNhatButtonState();
        }

        private void loadDataToComboBoxes() {
                try {
                        // Save listeners
                        ActionListener[] trangThaiListeners = cbFilterTrangThai.getActionListeners();

                        // Remove listeners
                        for (ActionListener listener : trangThaiListeners) {
                                cbFilterTrangThai.removeActionListener(listener);
                        }

                        // Load TrangThai - note: this reuses the existing combobox setup
                        ComboBoxEnumLoader.loadTrangThaiKhuyenMaiToComboBox(cbTrangThai);

                        // Re-add listeners
                        for (ActionListener listener : trangThaiListeners) {
                                cbFilterTrangThai.addActionListener(listener);
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu filter: " + e.getMessage());
                }
        }

        private void loadDataToTable() {
                loadFilteredData();
        }

        private void loadFilteredData() {
                try {
                        KhuyenMaiService service = new KhuyenMaiService();
                        List<KhuyenMai> list = service.getAllKhuyenMai();
                        String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();

                        DefaultTableModel model = (DefaultTableModel) tableKhuyenMai.getModel();
                        model.setRowCount(0);

                        for (KhuyenMai km : list) {
                                // Apply TrangThai filter
                                if (selectedTrangThai != null && !selectedTrangThai.equals("Trạng thái")) {
                                        if (km.getTrangThai() == null || !km.getTrangThai().getDisplayName()
                                                        .equals(selectedTrangThai)) {
                                                continue;
                                        }
                                }

                                model.addRow(new Object[] {
                                                km.getMaKM(),
                                                km.getTenKM(),
                                                km.getGiaTriGiam(),
                                                km.getNgayBatDau(),
                                                km.getNgayKetThuc(),                                               
                                                km.getDieuKienToiThieu(),
                                                km.getTrangThai() != null ? km.getTrangThai().getDisplayName() : ""
                                });
                        }
                        centerTableColumns(tableKhuyenMai);
                } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu: " + e.getMessage());
                }
        }

        private void searchByText() {
                try {
                        KhuyenMaiService service = new KhuyenMaiService();
                        List<KhuyenMai> list = service.getAllKhuyenMai();
                        String searchText = txtTimKiem.getText().trim().toLowerCase();
                        String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();

                        DefaultTableModel model = (DefaultTableModel) tableKhuyenMai.getModel();
                        model.setRowCount(0);

                        for (KhuyenMai km : list) {
                                // Apply TrangThai filter
                                if (selectedTrangThai != null && !selectedTrangThai.equals("Trạng thái")) {
                                        if (km.getTrangThai() == null || !km.getTrangThai().getDisplayName()
                                                        .equals(selectedTrangThai)) {
                                                continue;
                                        }
                                }

                                // Apply search text filter
                                if (!searchText.isEmpty()) {
                                        String maKM = km.getMaKM() != null ? km.getMaKM().toLowerCase()
                                                        : "";
                                        String tenKM = km.getTenKM() != null ? km.getTenKM().toLowerCase()
                                                        : "";
                                        if (!maKM.contains(searchText) && !tenKM.contains(searchText)) {
                                                continue;
                                        }
                                }

                                model.addRow(new Object[] {
                                                km.getMaKM(),
                                                km.getTenKM(),
                                                km.getNgayBatDau(),
                                                km.getNgayKetThuc(),
                                                km.getGiaTriGiam(),
                                                km.getDieuKienToiThieu(),
                                                km.getTrangThai() != null ? km.getTrangThai().getDisplayName() : ""
                                });
                        }
                        centerTableColumns(tableKhuyenMai);
                } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm dữ liệu: " + e.getMessage());
                }
        }

        private void loadDataFromRow(int rowIndex) {
                try {
                        String maKM = (String) tableKhuyenMai.getValueAt(rowIndex, 0);
                        String tenKM = (String) tableKhuyenMai.getValueAt(rowIndex, 1);
                        Object ngayBDObj = tableKhuyenMai.getValueAt(rowIndex, 3);
                        Object ngayKTObj = tableKhuyenMai.getValueAt(rowIndex, 4);
                        double giaTriGiam = (double) tableKhuyenMai.getValueAt(rowIndex, 2);
                        Object dieuKienObj = tableKhuyenMai.getValueAt(rowIndex, 5);
                        String trangThai = (String) tableKhuyenMai.getValueAt(rowIndex, 6);

                        txtMaKhuyenMai.setText(maKM);
                        txtTenKhuyenMai.setText(tenKM);
                        txtGiaTriGiam.setText(formatCurrency(giaTriGiam));
                        if (ngayBDObj instanceof LocalDate && dpNgayBatDau != null) {
                                dpNgayBatDau.setDate((LocalDate) ngayBDObj);
                        }
                        if (ngayKTObj instanceof LocalDate && dbNgayKetThuc != null) {
                                dbNgayKetThuc.setDate((LocalDate) ngayKTObj);
                        }
                        if (dieuKienObj != null) {
                                txtDieuKienToiThieu.setText(dieuKienObj.toString());
                        } else {
                                txtDieuKienToiThieu.setText("");
                        }
                        cbTrangThai.setSelectedItem(trangThai);
                } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi khi load dữ liệu từ row: " + e.getMessage());
                }
        }

        private void clearFields() {
                txtTenKhuyenMai.setText("");
                txtGiaTriGiam.setText("");
                txtDieuKienToiThieu.setText("");
                cbTrangThai.setSelectedIndex(0);
                if (dpNgayBatDau != null) {
                        dpNgayBatDau.setDate(LocalDate.now());
                }
                if (dbNgayKetThuc != null) {
                        dbNgayKetThuc.setDate(LocalDate.now());
                }
        }

        private void syncCapNhatButtonState() {
            btnCapNhat.setEnabled(tableKhuyenMai.getSelectedRow() >= 0);
            btnXoa.setEnabled(tableKhuyenMai.getSelectedRow() >= 0);
        }


        public void refreshData() {
                clearFields();
                fillTxtMaKhuyenMai();
                resetPlaceholder(txtTimKiem, "Nhập tên hoặc mã khuyến mãi");
                cbFilterTrangThai.setSelectedIndex(0);
                loadDataToComboBoxes();
                loadDataToTable();
                tableKhuyenMai.clearSelection();
                syncCapNhatButtonState();
        }

        private void fillTxtMaKhuyenMai() {
                String lastID = idQueryHelper.getLastID("KhuyenMai", "maKM");
                String maKMNew = (lastID == null || lastID.isEmpty()) ? idGeneratorHelper.generateDefaultID("KM")
                                : idGeneratorHelper.generateNextIDFromFullID(lastID);
                txtMaKhuyenMai.setText(maKMNew);
        }

        private String formatCurrency(double value) {
                DecimalFormat df = new DecimalFormat("#,##0.00");
                return df.format(value);
        }

        private boolean isMouseOverTable(java.awt.event.MouseEvent evt) {
                java.awt.Point p = evt.getPoint();
                java.awt.Point tablePoint = SwingUtilities.convertPoint(this, p, tableKhuyenMai);
                return tableKhuyenMai.getBounds().contains(tablePoint);
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

        private void resetPlaceholder(JTextField textField, String placeholder) {
                Color placeholderColor = new Color(153, 153, 153);
                textField.setText(placeholder);
                textField.setForeground(placeholderColor);
        }

        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        pnlThongTinKhuyenMai = new javax.swing.JPanel();
        lblMaKhuyenMai = new javax.swing.JLabel();
        txtMaKhuyenMai = new javax.swing.JTextField();
        lblTenKhuyenMai = new javax.swing.JLabel();
        txtTenKhuyenMai = new javax.swing.JTextField();
        lblNgayBatDau = new javax.swing.JLabel();
        lblNgayKetThuc = new javax.swing.JLabel();
        lblGiaTriGiam = new javax.swing.JLabel();
        txtGiaTriGiam = new javax.swing.JTextField();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        lblDieuKienToiThieu = new javax.swing.JLabel();
        txtDieuKienToiThieu = new javax.swing.JTextField();
        lblTrangThai = new javax.swing.JLabel();
        cbTrangThai = new javax.swing.JComboBox<>();
        dbNgayKetThuc = new com.github.lgooddatepicker.components.DatePicker();
        dpNgayBatDau = new com.github.lgooddatepicker.components.DatePicker();
        cbFilterTrangThai = new javax.swing.JComboBox<>();
        scrTableKhuyenMai = new javax.swing.JScrollPane();
        tableKhuyenMai = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        pnlRightButtons = new javax.swing.JPanel();
        btnXoaTrang = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout(0, 15));

        pnlHeader.setBackground(new java.awt.Color(255, 251, 233));
        pnlHeader.setLayout(new java.awt.BorderLayout(0, 15));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitle.setText("Quản lý các chương trình khuyến mãi và giảm giá  ");
        pnlHeader.add(lblTitle, java.awt.BorderLayout.WEST);

        pnlThongTinKhuyenMai.setBackground(new java.awt.Color(255, 251, 233));

        lblMaKhuyenMai.setText("Mã khuyến mãi:");
        lblMaKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtMaKhuyenMai.setEditable(false);
        txtMaKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaKhuyenMai.setFocusable(false);
        txtMaKhuyenMai.setPreferredSize(new java.awt.Dimension(64, 35));
        txtMaKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKhuyenMaiActionPerformed(evt);
            }
        });

        lblTenKhuyenMai.setText("Tên khuyến mãi:");
        lblTenKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTenKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenKhuyenMai.setPreferredSize(new java.awt.Dimension(64, 35));

        lblNgayBatDau.setText("Ngày bắt đầu:");
        lblNgayBatDau.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblNgayKetThuc.setText("Ngày kết thúc:");
        lblNgayKetThuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblGiaTriGiam.setText("Giá trị giảm:");
        lblGiaTriGiam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtGiaTriGiam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGiaTriGiam.setPreferredSize(new java.awt.Dimension(64, 35));
        txtGiaTriGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaTriGiamActionPerformed(evt);
            }
        });

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.setPreferredSize(new java.awt.Dimension(64, 35));

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        lblDieuKienToiThieu.setText("Điều kiện tối thiểu:");
        lblDieuKienToiThieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtDieuKienToiThieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDieuKienToiThieu.setPreferredSize(new java.awt.Dimension(64, 35));

        lblTrangThai.setText("Trạng thái:");
        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        cbTrangThai.setPreferredSize(new java.awt.Dimension(72, 35));

        dbNgayKetThuc.setPreferredSize(new java.awt.Dimension(143, 35));

        dpNgayBatDau.setPreferredSize(new java.awt.Dimension(143, 35));

        cbFilterTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbFilterTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterTrangThaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinKhuyenMaiLayout = new javax.swing.GroupLayout(pnlThongTinKhuyenMai);
        pnlThongTinKhuyenMai.setLayout(pnlThongTinKhuyenMaiLayout);
        pnlThongTinKhuyenMaiLayout.setHorizontalGroup(
            pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaKhuyenMai)
                            .addComponent(lblTenKhuyenMai))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblGiaTriGiam)
                            .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblTrangThai)))
                        .addGap(32, 32, 32)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGiaTriGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(42, 42, 42)
                .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                        .addComponent(lblDieuKienToiThieu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDieuKienToiThieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNgayBatDau)
                            .addComponent(lblNgayKetThuc))
                        .addGap(33, 33, 33)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dbNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dpNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 264, Short.MAX_VALUE))
            .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(544, 544, 544)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlThongTinKhuyenMaiLayout.setVerticalGroup(
            pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dpNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNgayKetThuc)
                            .addComponent(dbNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDieuKienToiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDieuKienToiThieu)))
                    .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTenKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGiaTriGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGiaTriGiam))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTrangThai)
                            .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(16, 16, 16)
                .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pnlHeader.add(pnlThongTinKhuyenMai, java.awt.BorderLayout.PAGE_END);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        tableKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã khuyến mãi", "Tên khuyến mãi", "Giá trị giảm", "Ngày bắt đầu", "Ngày kết thúc", "Điều kiện tối thiểu", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableKhuyenMai.setRowHeight(35);
        scrTableKhuyenMai.setViewportView(tableKhuyenMai);

        add(scrTableKhuyenMai, java.awt.BorderLayout.CENTER);

        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setLayout(new java.awt.BorderLayout());

        btnTrangChu.setText("Trang Chủ");
        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlButton.add(btnTrangChu, java.awt.BorderLayout.WEST);

        pnlRightButtons.setBackground(new java.awt.Color(255, 251, 233));
        pnlRightButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 15, 0));

        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnXoaTrang);

        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnCapNhat);

        btnXoa.setText("Xóa");
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlRightButtons.add(btnXoa);

        btnThem.setText("Thêm");
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnThem);

        pnlButton.add(pnlRightButtons, java.awt.BorderLayout.EAST);

        add(pnlButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

        private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaTrangActionPerformed
                // TODO add your handling code here:
                refreshData();
        }// GEN-LAST:event_btnXoaTrangActionPerformed

        private void centerTableColumns(JTable table) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for (int i = 0; i < table.getColumnCount(); i++) {
                        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }
        }

        private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
                loadDataToTable();
        }// GEN-LAST:event_cbFilterTrangThaiActionPerformed

        private void txtMaKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMaKhuyenMaiActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtMaKhuyenMaiActionPerformed

        private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
                searchByText();
        }// GEN-LAST:event_btnTimKiemActionPerformed

        private void txtGiaTriGiamActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtGiaTriGiamActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtGiaTriGiamActionPerformed

        private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCapNhatActionPerformed
                try {
                        String maKM = txtMaKhuyenMai.getText().trim();
                        String tenKM = txtTenKhuyenMai.getText().trim();
                        String giaTriGiamText = txtGiaTriGiam.getText().trim().replace(",", "");
                        String dieuKienText = txtDieuKienToiThieu.getText().trim().replace(",", "");
                        LocalDate ngayBatDau = dpNgayBatDau != null ? dpNgayBatDau.getDate() : null;
                        LocalDate ngayKetThuc = dbNgayKetThuc != null ? dbNgayKetThuc.getDate() : null;
                        String trangThaiDisplay = (String) cbTrangThai.getSelectedItem();

                        com.restaurant.quanlydatbannhahang.entity.TrangThaiKhuyenMai trangThai = ComboBoxEnumLoader
                                        .getTrangThaiKhuyenMaiFromDisplay(trangThaiDisplay);

                        if (maKM.isEmpty() || tenKM.isEmpty() || giaTriGiamText.isEmpty() || dieuKienText.isEmpty()
                                        || ngayBatDau == null || ngayKetThuc == null || trangThai == null) {
                                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin khuyến mãi.");
                                return;
                        }

                        double giaTriGiam = Double.parseDouble(giaTriGiamText);
                        double dieuKienToiThieu = Double.parseDouble(dieuKienText);

                        KhuyenMai km = new KhuyenMai(maKM, tenKM, giaTriGiam, ngayBatDau, ngayKetThuc,
                                        dieuKienToiThieu, trangThai);
                        KhuyenMaiService service = new KhuyenMaiService();
                        service.capNhatKhuyenMai(km);
                        JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công.");
                        refreshData();
                } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Giá trị giảm hoặc điều kiện tối thiểu không hợp lệ.");
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thất bại: " + ex.getMessage());
                }
        }// GEN-LAST:event_btnCapNhatActionPerformed

        private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
                try {
                        String maKM = txtMaKhuyenMai.getText().trim();
                        String tenKM = txtTenKhuyenMai.getText().trim();
                        String giaTriGiamText = txtGiaTriGiam.getText().trim().replace(",", "");
                        String dieuKienText = txtDieuKienToiThieu.getText().trim().replace(",", "");
                        LocalDate ngayBatDau = dpNgayBatDau != null ? dpNgayBatDau.getDate() : null;
                        LocalDate ngayKetThuc = dbNgayKetThuc != null ? dbNgayKetThuc.getDate() : null;
                        String trangThaiDisplay = (String) cbTrangThai.getSelectedItem();

                        com.restaurant.quanlydatbannhahang.entity.TrangThaiKhuyenMai trangThai = ComboBoxEnumLoader
                                        .getTrangThaiKhuyenMaiFromDisplay(trangThaiDisplay);

                        if (maKM.isEmpty() || tenKM.isEmpty() || giaTriGiamText.isEmpty() || dieuKienText.isEmpty()
                                        || ngayBatDau == null || ngayKetThuc == null || trangThai == null) {
                                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin khuyến mãi.");
                                return;
                        }

                        double giaTriGiam = Double.parseDouble(giaTriGiamText);
                        double dieuKienToiThieu = Double.parseDouble(dieuKienText);

                        KhuyenMai km = new KhuyenMai(maKM, tenKM, giaTriGiam, ngayBatDau, ngayKetThuc,
                                        dieuKienToiThieu, trangThai);
                        KhuyenMaiService service = new KhuyenMaiService();
                        service.themKhuyenMai(km);
                        JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công.");
                        refreshData();
                } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Giá trị giảm hoặc điều kiện tối thiểu không hợp lệ.");
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thất bại: " + ex.getMessage());
                }
        }// GEN-LAST:event_btnThemActionPerformed

        private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {
                String maKM = txtMaKhuyenMai.getText().trim();
                if (maKM.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần xóa.");
                        return;
                }

                int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khuyến mãi này không?",
                                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (choice != JOptionPane.YES_OPTION) {
                        return;
                }

                try {
                        KhuyenMaiService service = new KhuyenMaiService();
                        service.xoaKhuyenMai(maKM);
                        JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công.");
                        refreshData();
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thất bại: " + ex.getMessage());
                }
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cbFilterTrangThai;
    private javax.swing.JComboBox<String> cbTrangThai;
    private com.github.lgooddatepicker.components.DatePicker dbNgayKetThuc;
    private com.github.lgooddatepicker.components.DatePicker dpNgayBatDau;
    private javax.swing.JLabel lblDieuKienToiThieu;
    private javax.swing.JLabel lblGiaTriGiam;
    private javax.swing.JLabel lblMaKhuyenMai;
    private javax.swing.JLabel lblNgayBatDau;
    private javax.swing.JLabel lblNgayKetThuc;
    private javax.swing.JLabel lblTenKhuyenMai;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlRightButtons;
    private javax.swing.JPanel pnlThongTinKhuyenMai;
    private javax.swing.JScrollPane scrTableKhuyenMai;
    private javax.swing.JTable tableKhuyenMai;
    private javax.swing.JTextField txtDieuKienToiThieu;
    private javax.swing.JTextField txtGiaTriGiam;
    private javax.swing.JTextField txtMaKhuyenMai;
    private javax.swing.JTextField txtTenKhuyenMai;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
        @Override
        public void mouseClicked(MouseEvent e) {
                // Được xử lý tập trung trong selection listener của bảng
        }

        @Override
        public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

        }

        @Override
        public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

        }
}
