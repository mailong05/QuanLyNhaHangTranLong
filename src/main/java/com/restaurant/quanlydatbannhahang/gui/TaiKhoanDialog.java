package com.restaurant.quanlydatbannhahang.gui;

// UIConfiguration để setup FlatLaf L&F

public class TaiKhoanDialog extends javax.swing.JDialog {

    /**
     * Creates new form TaiKhoanDialog
     */
    public TaiKhoanDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadDataToDialog();
    }

    private void loadDataToDialog() {
		// TODO Auto-generated method stub
		
	}

	// <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblMaNhanVien = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblTenTaiKhoan = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblQuyenHan = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblChucVu = new javax.swing.JLabel();
        lblNgayVaoLam = new javax.swing.JLabel();
        btnLuuThongTinCaNhan = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        btnDoiMatKhau = new javax.swing.JButton();
        txtMatKhauHienTai = new javax.swing.JPasswordField();
        txtMatKhauMoi = new javax.swing.JPasswordField();
        txtXacNhanMatKhau = new javax.swing.JPasswordField();
        txtMaNhanVien = new javax.swing.JTextField();
        txtTenTaiKhoan = new javax.swing.JTextField();
        txtQuyenHan = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        dtNgayVaoLam = new com.github.lgooddatepicker.components.DatePicker();
        cbChucVu = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Đổi mật khẩu:");
        jLabel8.setAutoscrolls(true);
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 6, -1, -1));
        jPanel1.add(lblMaNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Thông tin tài khoản:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 6, -1, -1));

        jLabel2.setText("Mã nhân viên:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel16.setText("Mật khẩu hiện tại:");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, -1, -1));

        jLabel3.setText("Tên tài khoản:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));
        jPanel1.add(lblTenTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel6.setText("Quyền hạn:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));
        jPanel1.add(lblQuyenHan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel17.setText("Mật khẩu mới:");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Thông tin cá nhân:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jLabel10.setText("Họ tên:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 44, -1));

        jLabel11.setText("Số điện thoại:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        jLabel12.setText("Ngày vào làm:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        jLabel13.setText("Chức vụ:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));
        jPanel1.add(lblChucVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        jPanel1.add(lblNgayVaoLam, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        btnLuuThongTinCaNhan.setText("Lưu thông tin cá nhân");
        btnLuuThongTinCaNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuThongTinCaNhanActionPerformed(evt);
            }
        });
        jPanel1.add(btnLuuThongTinCaNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, -1, -1));

        jLabel18.setText("Nhập lại mật khẩu mới:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, -1, -1));

        btnDoiMatKhau.setText("Đổi mật khẩu");
        btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhauActionPerformed(evt);
            }
        });
        jPanel1.add(btnDoiMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, -1, -1));

        txtMatKhauHienTai.setPreferredSize(new java.awt.Dimension(80, 22));
        jPanel1.add(txtMatKhauHienTai, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 150, -1));

        txtMatKhauMoi.setPreferredSize(new java.awt.Dimension(80, 22));
        jPanel1.add(txtMatKhauMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, 150, -1));

        txtXacNhanMatKhau.setPreferredSize(new java.awt.Dimension(80, 22));
        jPanel1.add(txtXacNhanMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, 150, -1));

        txtMaNhanVien.setEditable(false);
        jPanel1.add(txtMaNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 150, -1));

        txtTenTaiKhoan.setEditable(false);
        jPanel1.add(txtTenTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 150, -1));

        txtQuyenHan.setEditable(false);
        txtQuyenHan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuyenHanActionPerformed(evt);
            }
        });
        jPanel1.add(txtQuyenHan, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 150, -1));
        jPanel1.add(txtHoTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, 150, -1));
        jPanel1.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 150, -1));

        dtNgayVaoLam.setEnabled(false);
        jPanel1.add(dtNgayVaoLam, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 150, -1));

        cbChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbChucVu.setEnabled(false);
        jPanel1.add(cbChucVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 150, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 320));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtQuyenHanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuyenHanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuyenHanActionPerformed

    private void btnLuuThongTinCaNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuThongTinCaNhanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLuuThongTinCaNhanActionPerformed

    private void txtHoTenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtHoTenActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtHoTenActionPerformed

    private void btnSuaThongTinCaNhanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSuaThongTinCaNhanActionPerformed
        // TODO add your handling code here:
    	luuAction();
    }// GEN-LAST:event_btnSuaThongTinCaNhanActionPerformed

    private void luuAction() {
		// TODO Auto-generated method stub
		
	}

	private void txtSoDienThoaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtSoDienThoaiActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtSoDienThoaiActionPerformed

    private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDoiMatKhauActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnDoiMatKhauActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // ========== SETUP UI (FlatLaf) TRƯỚC TIÊN ==========
        UIConfiguration.setupUI();

        // ========== SAU ĐÓ MỚI KHỞI TẠO DIALOG ==========
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TaiKhoanDialog dialog = new TaiKhoanDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDoiMatKhau;
    private javax.swing.JButton btnLuuThongTinCaNhan;
    private javax.swing.JComboBox<String> cbChucVu;
    private com.github.lgooddatepicker.components.DatePicker dtNgayVaoLam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JLabel lblChucVu;
    private javax.swing.JLabel lblMaNhanVien;
    private javax.swing.JLabel lblNgayVaoLam;
    private javax.swing.JLabel lblQuyenHan;
    private javax.swing.JLabel lblTenTaiKhoan;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JPasswordField txtMatKhauHienTai;
    private javax.swing.JPasswordField txtMatKhauMoi;
    private javax.swing.JTextField txtQuyenHan;
    private javax.swing.JTextField txtTenTaiKhoan;
    private javax.swing.JPasswordField txtXacNhanMatKhau;
    // End of variables declaration//GEN-END:variables
}
