package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.TaiKhoanService;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEnumLoader;
import com.restaurant.quanlydatbannhahang.entity.QuyenHan;
import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;
import java.util.List;

public class PanelQuanLyTaiKhoan extends javax.swing.JPanel implements MouseListener {

        private JButton btnTrangChu = new JButton();
        private JPanel pnlButton = new JPanel();
        private JPanel pnlRightButtons = new JPanel();
        private JButton btnXoaTrang = new JButton();
        private JButton btnCapNhat = new JButton();
        private JButton btnXoa = new JButton();
        private JButton btnThem = new JButton();
        private ActionListener cbFilterQuyenHanListener;
        private TaiKhoanService taiKhoanService;
        private List<TaiKhoan> allTaiKhoan;
        private ComboBoxEnumLoader cbEnumLoader;

        public PanelQuanLyTaiKhoan() {
                initComponents();
                taiKhoanService = new TaiKhoanService();
                cbEnumLoader = new ComboBoxEnumLoader();
                customUI();
                loadDataToComboBoxes();
                loadDataToTable();

        }

        private void customUI() {
                // Placeholder cho txtTimKiem
                setupPlaceholder(txtTimKiem, "Nhập tên hoặc mã nhân viên ");

                // ========== DESELECT WHEN CLICK OUTSIDE TABLE ==========
                this.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                                if (evt.getSource() != tableTaiKhoan && !isMouseOverTable(evt)) {
                                        tableTaiKhoan.clearSelection();
                                        refreshData();
                                }
                        }
                });

                // Register mouse listener để populate fields khi click vào row
                tableTaiKhoan.addMouseListener(this);
                tableTaiKhoan.getSelectionModel().addListSelectionListener(e -> {
                        if (!e.getValueIsAdjusting()) {
                                int row = tableTaiKhoan.getSelectedRow();
                                if (row >= 0) {
                                        loadDataFromRow(row);
                                }
                                syncEditStateBySelection();
                        }
                });

                // 1. Tùy chỉnh ScrollPane và Viền bảng
                scrTableTaiKhoan.setBorder(BorderFactory.createLineBorder(new Color(200, 190, 170), 1));
                scrTableTaiKhoan.setViewportBorder(null);

                // 2. Tùy chỉnh Table (Bảng Tài Khoản)
                tableTaiKhoan.setRowHeight(35);

                // Căn giữa nội dung các cột trong bảng
                centerTableColumns(tableTaiKhoan);

                // Hiệu ứng chuột cho nút bấm
                btnTimKiem.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Gắn sự kiện quay về Trang Chủ
                MainForm.attachGoHomeListener(btnTrangChu, this);
                syncEditStateBySelection();
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
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jPanel2 = new javax.swing.JPanel();
                jPanel1 = new javax.swing.JPanel();
                lblTenTaiKhoan = new javax.swing.JLabel();
                txtTenTaiKhoan = new javax.swing.JTextField();
                jLabel2 = new javax.swing.JLabel();
                txtMatKhau = new javax.swing.JPasswordField();
                jLabel3 = new javax.swing.JLabel();
                cbQuyenHan = new javax.swing.JComboBox<>();
                txtTimKiem = new javax.swing.JTextField();
                btnTimKiem = new javax.swing.JButton();
                cbFilterQuyenHan = new javax.swing.JComboBox<>();
                btnResetPassword = new javax.swing.JButton();
                lblMaNhanVien = new javax.swing.JLabel();
                txtMaNhanVien = new javax.swing.JTextField();
                scrTableTaiKhoan = new javax.swing.JScrollPane();
                tableTaiKhoan = new javax.swing.JTable();
                jPanel3 = new javax.swing.JPanel();

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 1002, Short.MAX_VALUE));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 100, Short.MAX_VALUE));

                setBackground(new java.awt.Color(255, 251, 233));
                setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
                setLayout(new java.awt.BorderLayout());

                jPanel1.setBackground(new java.awt.Color(255, 251, 233));
                jPanel1.setPreferredSize(new java.awt.Dimension(1002, 130));

                lblTenTaiKhoan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                lblTenTaiKhoan.setText("Tên tài khoản:");

                txtTenTaiKhoan.setPreferredSize(new java.awt.Dimension(64, 30));

                jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jLabel2.setText("Mật khẩu:");

                txtMatKhau.setBackground(new java.awt.Color(255, 255, 255));
                txtMatKhau.setPreferredSize(new java.awt.Dimension(64, 30));
                txtMatKhau.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtMatKhauActionPerformed(evt);
                        }
                });

                jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jLabel3.setText("Quyền hạn:");

                cbQuyenHan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân Viên", "Quản Lý" }));
                cbQuyenHan.setPreferredSize(new java.awt.Dimension(64, 30));

                txtTimKiem.setPreferredSize(new java.awt.Dimension(0, 30));

                btnTimKiem.setText("Tìm Kiếm");
                btnTimKiem.setPreferredSize(new java.awt.Dimension(0, 30));
                btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnTimKiemActionPerformed(evt);
                        }
                });

                cbFilterQuyenHan.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
                cbFilterQuyenHan.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cbFilterQuyenHanActionPerformed(evt);
                        }
                });

                btnResetPassword.setText("Reset");
                btnResetPassword.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnResetPasswordActionPerformed(evt);
                        }
                });

                lblMaNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                lblMaNhanVien.setText("Mã nhân viên:");

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(cbFilterQuyenHan,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                114,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                jPanel1Layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(lblMaNhanVien)
                                                                                                                .addComponent(lblTenTaiKhoan,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                102,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                                                .addComponent(txtTimKiem,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                258,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addPreferredGap(
                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                .addComponent(btnTimKiem,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                102,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(txtTenTaiKhoan,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                280,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(txtMaNhanVien))
                                                                                                .addGap(48, 48, 48)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(jLabel3,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                78,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(jLabel2,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                65,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(18, 18, 18)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(txtMatKhau,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                102,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                                .addComponent(btnResetPassword,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                61,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addComponent(cbQuyenHan,
                                                                                                                                0,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))
                                                                                                .addGap(0, 428, Short.MAX_VALUE)))
                                                                .addContainerGap()));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(lblTenTaiKhoan)
                                                                                .addComponent(txtTenTaiKhoan,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel3)
                                                                                .addComponent(cbQuyenHan,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                false)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(btnResetPassword,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                31,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(1, 1, 1))
                                                                                .addGroup(jPanel1Layout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(jLabel2)
                                                                                                .addComponent(txtMatKhau,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(lblMaNhanVien))
                                                                                .addComponent(txtMaNhanVien,
                                                                                                javax.swing.GroupLayout.Alignment.LEADING))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(btnTimKiem,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                35,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(cbFilterQuyenHan)
                                                                                                .addGap(1, 1, 1))
                                                                                .addComponent(txtTimKiem,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addGap(10, 10, 10)));

                add(jPanel1, java.awt.BorderLayout.PAGE_START);

                tableTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {

                                },
                                new String[] {
                                                "Tên tài khoản", "Mã nhân viên", "Quyền hạn"
                                }) {
                        Class[] types = new Class[] {
                                        java.lang.String.class, java.lang.String.class, java.lang.String.class
                        };
                        boolean[] canEdit = new boolean[] {
                                        false, false, false
                        };

                        public Class getColumnClass(int columnIndex) {
                                return types[columnIndex];
                        }

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit[columnIndex];
                        }
                });
                scrTableTaiKhoan.setViewportView(tableTaiKhoan);

                add(scrTableTaiKhoan, java.awt.BorderLayout.CENTER);

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
                btnXoa.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnXoaActionPerformed(evt);
                        }
                });
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

        private void btnResetPasswordActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnResetPasswordActionPerformed
                String username = txtTenTaiKhoan.getText().trim();
                if (username.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần reset mật khẩu.");
                        return;
                }

                int choice = JOptionPane.showConfirmDialog(this,
                                "Reset mật khẩu tài khoản này về mặc định 123456?",
                                "Xác nhận reset mật khẩu", JOptionPane.YES_NO_OPTION);
                if (choice != JOptionPane.YES_OPTION) {
                        return;
                }

                try {
                        boolean ok = taiKhoanService.resetMatKhauMacDinh(username);
                        if (ok) {
                                txtMatKhau.setText("123456");
                                JOptionPane.showMessageDialog(this, "Reset mật khẩu thành công.");
                        } else {
                                JOptionPane.showMessageDialog(this, "Reset mật khẩu thất bại.");
                        }
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Reset mật khẩu thất bại: " + ex.getMessage());
                }
        }// GEN-LAST:event_btnResetPasswordActionPerformed

        private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaTrangActionPerformed
                // TODO add your handling code here:
                refreshData();
        }// GEN-LAST:event_btnXoaTrangActionPerformed

        private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCapNhatActionPerformed
                String username = txtTenTaiKhoan.getText().trim();
                String maNV = txtMaNhanVien.getText().trim();
                String quyenHanDisplay = (String) cbQuyenHan.getSelectedItem();
                QuyenHan quyenHan = ComboBoxEnumLoader.getQuyenHanFromDisplay(quyenHanDisplay);

                if (username.isEmpty() || maNV.isEmpty() || quyenHan == null) {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin tài khoản.");
                        return;
                }

                try {
                        boolean ok = taiKhoanService.capNhatTaiKhoan(username, maNV, quyenHan);
                        if (ok) {
                                JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thành công.");
                                refreshData();
                        } else {
                                JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thất bại.");
                        }
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thất bại: " + ex.getMessage());
                }
        }// GEN-LAST:event_btnCapNhatActionPerformed

        private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaActionPerformed
                String username = txtTenTaiKhoan.getText().trim();
                if (username.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần xóa.");
                        return;
                }

                int choice = JOptionPane.showConfirmDialog(this,
                                "Bạn có chắc muốn xóa tài khoản này không?",
                                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (choice != JOptionPane.YES_OPTION) {
                        return;
                }

                try {
                        boolean ok = taiKhoanService.xoaTaiKhoan(username);
                        if (ok) {
                                JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công.");
                                refreshData();
                        } else {
                                JOptionPane.showMessageDialog(this, "Xóa tài khoản thất bại.");
                        }
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Xóa tài khoản thất bại: " + ex.getMessage());
                }
        }// GEN-LAST:event_btnXoaActionPerformed

        private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
                String username = txtTenTaiKhoan.getText().trim();
                String maNV = txtMaNhanVien.getText().trim();
                String password = new String(txtMatKhau.getPassword()).trim();
                String quyenHanDisplay = (String) cbQuyenHan.getSelectedItem();
                QuyenHan quyenHan = ComboBoxEnumLoader.getQuyenHanFromDisplay(quyenHanDisplay);

                if (username.isEmpty() || maNV.isEmpty() || password.isEmpty() || quyenHan == null) {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin tài khoản.");
                        return;
                }

                try {
                        boolean ok = taiKhoanService.themTaiKhoan(username, password, maNV, quyenHan);
                        if (ok) {
                                JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công.");
                                refreshData();
                        } else {
                                JOptionPane.showMessageDialog(this, "Thêm tài khoản thất bại.");
                        }
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Thêm tài khoản thất bại: " + ex.getMessage());
                }
        }// GEN-LAST:event_btnThemActionPerformed

        private void resetPlaceholder(JTextField textField, String placeholder) {
                Color placeholderColor = new Color(153, 153, 153);
                textField.setText(placeholder);
                textField.setForeground(placeholderColor);
        }

        private void loadDataToTable() {
                loadFilteredData();
        }

        private void loadDataToComboBoxes() {
                try {
                        // Save listeners
                        ActionListener[] filterListeners = cbFilterQuyenHan.getActionListeners();

                        // Remove listeners
                        for (ActionListener listener : filterListeners) {
                                cbFilterQuyenHan.removeActionListener(listener);
                        }

                        // Load QuyenHan to both comboboxes
                        cbFilterQuyenHan.removeAllItems();
                        cbFilterQuyenHan.addItem("Quyền hạn");
                        cbEnumLoader.loadQuyenHanToComboBox(cbFilterQuyenHan);

                        cbQuyenHan.removeAllItems();
                        cbEnumLoader.loadQuyenHanToComboBox(cbQuyenHan);
                        // Re-add listeners
                        for (ActionListener listener : filterListeners) {
                                cbFilterQuyenHan.addActionListener(listener);
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu filter: " + e.getMessage());
                }
        }

        private void loadFilteredData() {
                try {
                        allTaiKhoan = taiKhoanService.getAllTaiKhoan();
                        String selectedQuyenHan = (String) cbFilterQuyenHan.getSelectedItem();

                        DefaultTableModel model = (DefaultTableModel) tableTaiKhoan.getModel();
                        model.setRowCount(0);

                        for (TaiKhoan tk : allTaiKhoan) {
                                // Apply QuyenHan filter
                                if (selectedQuyenHan != null && !selectedQuyenHan.equals("Quyền hạn")) {
                                        if (tk.getQuyenHan() == null
                                                        || !tk.getQuyenHan().getDisplayName()
                                                                        .equals(selectedQuyenHan)) {
                                                continue;
                                        }
                                }

                                model.addRow(new Object[] {
                                                tk.getUsername(),
                                                tk.getNhanVien() != null ? tk.getNhanVien().getMaNV() : "",
                                                tk.getQuyenHan().getDisplayName()
                                });
                        }
                        centerTableColumns(tableTaiKhoan);
                } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu: " + e.getMessage());
                }
        }

        private void searchByText() {
                try {
                        String searchText = txtTimKiem.getText().trim().toLowerCase();
                        String selectedQuyenHan = (String) cbFilterQuyenHan.getSelectedItem();

                        DefaultTableModel model = (DefaultTableModel) tableTaiKhoan.getModel();
                        model.setRowCount(0);

                        for (TaiKhoan tk : allTaiKhoan) {
                                // Apply QuyenHan filter
                                if (selectedQuyenHan != null && !selectedQuyenHan.equals("Quyền hạn")) {
                                        if (tk.getQuyenHan() == null
                                                        || !tk.getQuyenHan().getDisplayName()
                                                                        .equals(selectedQuyenHan)) {
                                                continue;
                                        }
                                }

                                // Apply search text filter
                                if (!searchText.isEmpty()) {
                                        String username = tk.getUsername() != null ? tk.getUsername().toLowerCase()
                                                        : "";
                                        if (!username.contains(searchText)) {
                                                continue;
                                        }
                                }

                                model.addRow(new Object[] {
                                                tk.getUsername(),
                                                tk.getNhanVien() != null ? tk.getNhanVien().getMaNV() : "",
                                                tk.getQuyenHan() != null ? tk.getQuyenHan().getDisplayName() : ""
                                });
                        }
                        centerTableColumns(tableTaiKhoan);
                } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm dữ liệu: " + e.getMessage());
                }
        }

        private void loadDataFromRow(int rowIndex) {
                try {
                        String username = (String) tableTaiKhoan.getValueAt(rowIndex, 0);
                        String maNhanVien = (String) tableTaiKhoan.getValueAt(rowIndex, 1);
                        String password = (String) taiKhoanService.getPasswordByUsername(username, maNhanVien);
                        String quyenHan = (String) tableTaiKhoan.getValueAt(rowIndex, 2);

                        txtTenTaiKhoan.setText(username);
                        cbQuyenHan.setSelectedItem(quyenHan);
                        txtMaNhanVien.setText(maNhanVien);
                        txtMatKhau.setText(password);
                } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi khi load dữ liệu từ row: " + e.getMessage());
                }
        }

        private void clearFields() {
                txtTenTaiKhoan.setText("");
                cbQuyenHan.setSelectedIndex(0);
                txtMaNhanVien.setText("");
                txtMatKhau.setText("");
                txtTimKiem.setText("");
        }

        private void syncEditStateBySelection() {
                boolean hasSelection = tableTaiKhoan.getSelectedRow() >= 0;
                btnCapNhat.setEnabled(hasSelection);
                btnResetPassword.setEnabled(hasSelection);
                txtMaNhanVien.setEditable(!hasSelection);
                txtMatKhau.setEditable(!hasSelection);
        }

        public void refreshData() {
                clearFields();
                resetPlaceholder(txtTimKiem, "Nhập tên hoặc mã nhân viên");
                cbFilterQuyenHan.setSelectedIndex(0);
                loadDataToComboBoxes();
                loadDataToTable();
                tableTaiKhoan.clearSelection();
                syncEditStateBySelection();

        }

        private boolean isMouseOverTable(java.awt.event.MouseEvent evt) {
                java.awt.Point p = evt.getPoint();
                java.awt.Point tablePoint = SwingUtilities.convertPoint(this, p, tableTaiKhoan);
                return tableTaiKhoan.getBounds().contains(tablePoint);
        }

        private void centerTableColumns(JTable table) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for (int i = 0; i < table.getColumnCount(); i++) {
                        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }
        }

        private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTrangChuActionPerformed
                java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
                if (parentFrame instanceof MainForm) {
                        ((MainForm) parentFrame).goToTrangChuFromPanel();
                }
        }// GEN-LAST:event_btnTrangChuActionPerformed

        private void txtMatKhauActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMatKhauActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtMatKhauActionPerformed

        private void cbFilterQuyenHanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterQuyenHanActionPerformed
                loadFilteredData();
        }// GEN-LAST:event_cbFilterQuyenHanActionPerformed

        private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
                searchByText();
        }// GEN-LAST:event_btnTimKiemActionPerformed

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

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnResetPassword;
        private javax.swing.JButton btnTimKiem;
        private javax.swing.JComboBox<String> cbFilterQuyenHan;
        private javax.swing.JComboBox<String> cbQuyenHan;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JLabel lblMaNhanVien;
        private javax.swing.JLabel lblTenTaiKhoan;
        private javax.swing.JScrollPane scrTableTaiKhoan;
        private javax.swing.JTable tableTaiKhoan;
        private javax.swing.JTextField txtMaNhanVien;
        private javax.swing.JPasswordField txtMatKhau;
        private javax.swing.JTextField txtTenTaiKhoan;
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
