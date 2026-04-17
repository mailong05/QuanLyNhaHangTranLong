package com.restaurant.quanlydatbannhahang.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.restaurant.quanlydatbannhahang.dao.TaiKhoanDAO;
import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;
import com.restaurant.quanlydatbannhahang.service.AuthService;
import com.restaurant.quanlydatbannhahang.service.AuthService.ValidationResult;

public class ForgetPasswordForm extends javax.swing.JFrame {

    public ForgetPasswordForm() {
        initComponents();

        // khử viền xanh khi click nút
        btnContinue.setFocusPainted(false);
        btnCancel.setFocusPainted(false);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblRestaurantName = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        clickCLose = new javax.swing.JLabel();
        clickMinimize = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        btnContinue = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        btnCancel = new javax.swing.JButton();
        txtPhone = new javax.swing.JTextField();
        lblPhone = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(142, 128, 106));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo_restaurantTL.png"))); // NOI18N
        jPanel1.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 230, 190));

        lblRestaurantName.setFont(new java.awt.Font("Segoe UI Semilight", 3, 18)); // NOI18N
        lblRestaurantName.setForeground(new java.awt.Color(255, 255, 255));
        lblRestaurantName.setText("TRAN LONG RESTAURANT");
        jPanel1.add(lblRestaurantName, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 400));

        jPanel2.setBackground(new java.awt.Color(255, 251, 233));
        jPanel2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        clickCLose.setText("X");
        clickCLose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clickCLose.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        clickCLose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickCLoseMouseClicked(evt);
            }
        });

        clickMinimize.setText("-");
        clickMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clickMinimize.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        clickMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickMinimizeMouseClicked(evt);
            }
        });

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUsername.setBackground(new java.awt.Color(255, 251, 233));
        txtUsername.setBorder(null);
        txtUsername.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtUsername.setForeground(new java.awt.Color(102, 102, 102));
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        btnContinue.setText("Tiếp tục");
        btnContinue.setBackground(new java.awt.Color(250, 249, 235));
        btnContinue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        btnContinue.setContentAreaFilled(false);
        btnContinue.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnContinue.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnContinue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinueActionPerformed(evt);
            }
        });

        jLabel6.setText("QUÊN MẬT KHẨU");
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));

        jSeparator3.setForeground(new java.awt.Color(102, 102, 102));

        jSeparator4.setForeground(new java.awt.Color(102, 102, 102));

        btnCancel.setText("Quay lại");
        btnCancel.setBackground(new java.awt.Color(250, 249, 235));
        btnCancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        btnCancel.setContentAreaFilled(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        txtPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPhone.setBackground(new java.awt.Color(255, 251, 233));
        txtPhone.setBorder(null);
        txtPhone.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtPhone.setForeground(new java.awt.Color(102, 102, 102));
        txtPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneActionPerformed(evt);
            }
        });

        lblPhone.setText("Số điện thoại:");
        lblPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblUsername.setText("Username:");
        lblUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(260, 260, 260)
                                                .addComponent(clickMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(clickCLose))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(60, 60, 60)
                                                .addComponent(jLabel6))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(btnCancel,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 73,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnContinue,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 75,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblPhone)
                                                                        .addComponent(lblUsername))
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        false)
                                                                        .addComponent(txtUsername)
                                                                        .addComponent(jSeparator3)
                                                                        .addComponent(jSeparator4)
                                                                        .addComponent(txtPhone,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                177, Short.MAX_VALUE))))))
                                .addContainerGap(10, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(clickMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(clickCLose, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addComponent(jLabel6)
                                .addGap(47, 47, 47)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblUsername)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 3,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(14, 14, 14)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 3,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(lblPhone))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnContinue, javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap()));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 300, 400));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPhoneActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtPhoneActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtPhoneActionPerformed

    private void clickCLoseMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_clickCLoseMouseClicked
        this.dispose();
    }// GEN-LAST:event_clickCLoseMouseClicked

    private void clickMinimizeMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_clickMinimizeMouseClicked
        this.setState(JFrame.ICONIFIED);
    }// GEN-LAST:event_clickMinimizeMouseClicked

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtUsernameActionPerformed

    private void btnContinueActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnContinueActionPerformed
        String username = txtUsername.getText().trim();
        String phone = txtPhone.getText().trim();

        // ========== VALIDATE USERNAME ==========
        ValidationResult userValidation = AuthService.validateUsername(username);
        if (!userValidation.success) {
            JOptionPane.showMessageDialog(this,
                    userValidation.message,
                    "Lỗi tên đăng nhập",
                    JOptionPane.ERROR_MESSAGE);
            txtUsername.requestFocus();
            return;
        }

        // ========== VALIDATE PHONE ==========
        ValidationResult phoneValidation = AuthService.validatePhoneNumber(phone);
        if (!phoneValidation.success) {
            JOptionPane.showMessageDialog(this,
                    phoneValidation.message,
                    "Lỗi số điện thoại",
                    JOptionPane.ERROR_MESSAGE);
            txtPhone.requestFocus();
            return;
        }

        // ========== CHECK USERNAME EXISTS ==========
        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
        TaiKhoan taiKhoan = taiKhoanDAO.findByUsername(username);

        if (taiKhoan == null) {
            JOptionPane.showMessageDialog(this,
                    "Tên đăng nhập không tồn tại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ========== CHECK PHONE MATCH ==========
        // Kiểm tra số điện thoại có khớp với nhân viên không
        String phoneFromDB = taiKhoan.getNhanVien().getSdt();
        if (!phone.equals(phoneFromDB)) {
            JOptionPane.showMessageDialog(this,
                    "Số điện thoại không khớp với tài khoản này!",
                    "Lỗi xác minh",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ========== EVERYTHING OK ==========
        JOptionPane.showMessageDialog(this,
                "Xác minh thành công! Vui lòng đặt lại mật khẩu.",
                "Thành công",
                JOptionPane.INFORMATION_MESSAGE);

        // Đóng ForgetPasswordForm
        this.dispose();

        // Mở PanelDatLaiMatKhau để đặt lại mật khẩu (truyền username)
        new PanelDatLaiMatKhau(username).setVisible(true);
    }// GEN-LAST:event_btnContinueActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn quay lại đăng nhập?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Đóng ForgetPasswordForm
            this.dispose();
            // Mở lại LoginForm
            new LoginForm().setVisible(true);
        }
    }// GEN-LAST:event_btnCancelActionPerformed

    private void txtMailActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMailActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtMailActionPerformed

    private void txtIdcardActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtIdcardActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtIdcardActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // ========== SETUP UI (FlatLaf) TRƯỚC TIÊN ==========
        UIConfiguration.setupUI();

        // ========== SAU ĐÓ MỚI KHỞI TẠO FORM ==========
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ForgetPasswordForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnContinue;
    private javax.swing.JLabel clickCLose;
    private javax.swing.JLabel clickMinimize;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblRestaurantName;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
