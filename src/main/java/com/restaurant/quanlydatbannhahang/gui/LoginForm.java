package com.restaurant.quanlydatbannhahang.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Color;
import com.restaurant.quanlydatbannhahang.gui.MainForm;
import com.restaurant.quanlydatbannhahang.gui.ForgetPasswordForm;
import com.restaurant.quanlydatbannhahang.gui.UIConfiguration;
import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;
import com.restaurant.quanlydatbannhahang.service.AuthService;

public class LoginForm extends javax.swing.JFrame {

    public LoginForm() {
        initComponents();
        this.setLocationRelativeTo(null); // Căn giữa màn hình

        // Thiết lập Placeholder mặc định
        txtUsername.setText("Username");
        txtPassword.setText("Password");
        txtPassword.setEchoChar((char) 0);
        txtUsername.setForeground(new Color(102, 102, 102));
        txtPassword.setForeground(new Color(102, 102, 102));

        // Đẩy focus ra khỏi các ô input lúc mới mở
        jLabel6.requestFocusInWindow();

        // Xử lý Placeholder cho ô Username
        txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtUsername.getText().equals("Username")) {
                    txtUsername.setText("");
                    txtUsername.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtUsername.getText().isEmpty()) {
                    txtUsername.setForeground(new Color(102, 102, 102));
                    txtUsername.setText("Username");
                }
            }
        });

        // Placeholder cho ô Password
        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                String pass = String.valueOf(txtPassword.getPassword());
                if (pass.equals("Password")) {
                    txtPassword.setText("");
                    txtPassword.setForeground(Color.BLACK);
                    txtPassword.setEchoChar('●');
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                String pass = String.valueOf(txtPassword.getPassword());
                if (pass.isEmpty()) {
                    txtPassword.setForeground(new Color(102, 102, 102));
                    txtPassword.setText("Password");
                    txtPassword.setEchoChar((char) 0);
                }
            }
        });

        // Tắt viền xanh cho các nút
        btnLogin.setFocusPainted(false);
        btnCancel.setFocusPainted(false);
        checkRemember.setFocusPainted(false);

        // Ẩn icon mắt mở lúc đầu (mặc định là ẩn mật khẩu)
        lblIconEye.setVisible(false);
        lblIconHide.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblRestaurantName = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblClose = new javax.swing.JLabel();
        lblMinimize = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        jSeparator2 = new javax.swing.JSeparator();
        lblIconUser = new javax.swing.JLabel();
        lblIconKey = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblForgotPassword = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        checkRemember = new javax.swing.JCheckBox();
        lblIconEye = new javax.swing.JLabel();
        lblIconHide = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(142, 128, 106));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo_restaurantTL.png"))); // NOI18N
        jPanel1.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 220, 200));

        lblRestaurantName.setFont(new java.awt.Font("Segoe UI Semilight", 3, 18)); // NOI18N
        lblRestaurantName.setForeground(new java.awt.Color(255, 255, 255));
        lblRestaurantName.setText("NHÀ HÀNG TRẦN LONG");
        jPanel1.add(lblRestaurantName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 400));

        jPanel2.setBackground(new java.awt.Color(255, 251, 233));
        jPanel2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        lblClose.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblClose.setText("X");
        lblClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
            }
        });

        lblMinimize.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblMinimize.setText("-");
        lblMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinimizeMouseClicked(evt);
            }
        });

        txtUsername.setBackground(new java.awt.Color(255, 251, 233));
        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(102, 102, 102));
        txtUsername.setBorder(null);
        txtUsername.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        txtPassword.setBackground(new java.awt.Color(255, 251, 233));
        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(102, 102, 102));
        txtPassword.setBorder(null);
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(102, 102, 102));

        lblIconUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_user.png"))); // NOI18N

        lblIconKey.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_key.png"))); // NOI18N

        btnLogin.setBackground(new java.awt.Color(250, 249, 235));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnLogin.setText("Đăng nhập");
        btnLogin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        btnLogin.setContentAreaFilled(false);
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(250, 249, 235));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnCancel.setText("Hủy");
        btnCancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        btnCancel.setContentAreaFilled(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lblForgotPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblForgotPassword.setForeground(new java.awt.Color(102, 102, 102));
        lblForgotPassword.setText("Quên mật khẩu?");
        lblForgotPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblForgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblForgotPasswordMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("ĐĂNG NHẬP");

        jSeparator3.setForeground(new java.awt.Color(102, 102, 102));

        checkRemember.setBackground(new java.awt.Color(250, 249, 235));
        checkRemember.setFont(new java.awt.Font("sansserif", 0, 12)); // NOI18N
        checkRemember.setText("Lưu mật khẩu");
        checkRemember.setContentAreaFilled(false);
        checkRemember.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        checkRemember.setFocusPainted(false);
        checkRemember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkRememberActionPerformed(evt);
            }
        });

        lblIconEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_eye.png"))); // NOI18N
        lblIconEye.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblIconEye.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblIconEyeMousePressed(evt);
            }
        });

        lblIconHide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_eyehide.png"))); // NOI18N
        lblIconHide.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblIconHide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblIconHideMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(255, 255, 255)
                                                .addComponent(lblMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 15,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(31, 31, 31)
                                                .addComponent(lblIconUser)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(69, 69, 69)
                                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(69, 69, 69)
                                                .addComponent(checkRemember))
                                        .addGroup(jPanel2Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout
                                                        .createSequentialGroup()
                                                        .addGap(31, 31, 31)
                                                        .addComponent(lblIconKey)
                                                        .addGap(18, 18, 18)
                                                        .addGroup(jPanel2Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(txtPassword,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(jSeparator2,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(1, 1, 1)
                                                        .addGroup(jPanel2Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(lblIconHide,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblIconEye,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addContainerGap()
                                                        .addGroup(jPanel2Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(lblForgotPassword)
                                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                        .addComponent(btnCancel,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                80,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(btnLogin,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                80,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGap(18, 18, 18))))
                                .addGap(4, 4, 4))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(67, 67, 67)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblMinimize)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(lblClose)))
                                .addGap(13, 13, 13)
                                .addComponent(jLabel6)
                                .addGap(29, 29, 29)
                                .addGroup(jPanel2Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblIconUser, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 24,
                                                Short.MAX_VALUE))
                                .addGap(4, 4, 4)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(txtPassword,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblIconKey))
                                                .addGap(3, 3, 3)
                                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(13, 13, 13)
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblIconHide)
                                                        .addComponent(lblIconEye))))
                                .addGap(11, 11, 11)
                                .addComponent(checkRemember)
                                .addGap(13, 13, 13)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(lblForgotPassword)
                                .addContainerGap()));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 300, 400));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblMinimizeMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lblMinimizeMouseClicked
        this.setState(JFrame.ICONIFIED);
    }// GEN-LAST:event_lblMinimizeMouseClicked

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lblCloseMouseClicked
        System.exit(0);
    }// GEN-LAST:event_lblCloseMouseClicked

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtPasswordActionPerformed
        btnLoginActionPerformed(evt);
    }// GEN-LAST:event_txtPasswordActionPerformed

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtUsernameActionPerformed
        txtPassword.requestFocus();
    }// GEN-LAST:event_txtUsernameActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelActionPerformed
        txtUsername.setText("Username");
        txtUsername.setForeground(new Color(102, 102, 102));
        txtPassword.setText("Password");
        txtPassword.setForeground(new Color(102, 102, 102));
        txtPassword.setEchoChar((char) 0);
    }// GEN-LAST:event_btnCancelActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLoginActionPerformed
        String username = getTxtUsername().getText().trim();
        String password = String.valueOf(getTxtPassword().getPassword()).trim();

        AuthService.ValidationResult userValidation = AuthService.validateUsername(username);
        if (!userValidation.success) {
            JOptionPane.showMessageDialog(this,
                    userValidation.message,
                    "Lỗi tên đăng nhập",
                    JOptionPane.ERROR_MESSAGE);
            getTxtUsername().requestFocus();
            return;
        }

        AuthService.ValidationResult passValidation = AuthService.validatePassword(password);
        if (!passValidation.success) {
            JOptionPane.showMessageDialog(this,
                    passValidation.message,
                    "Lỗi mật khẩu",
                    JOptionPane.ERROR_MESSAGE);
            getTxtPassword().requestFocus();
            return;
        }

        // ========== LOGIN ==========
        AuthService authService = new AuthService();
        TaiKhoan taiKhoan = authService.login(username, password);

        if (taiKhoan != null) {
            JOptionPane.showMessageDialog(this,
                    "Đăng nhập thành công!\nXin chào: " + taiKhoan.getNhanVien().getHoTen(),
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

            // Đóng LoginForm
            this.dispose();

            // Mở MainForm
            new MainForm(taiKhoan).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Tên đăng nhập hoặc mật khẩu không đúng!",
                    "Lỗi đăng nhập",
                    JOptionPane.ERROR_MESSAGE);
            getTxtPassword().setText("");
            getTxtPassword().setEchoChar((char) 0);
            getTxtPassword().setText("Password");
            getTxtPassword().setForeground(new Color(102, 102, 102));
            getTxtUsername().requestFocus();
        }
    }// GEN-LAST:event_btnLoginActionPerformed

    private void checkRememberActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_checkRememberActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_checkRememberActionPerformed

    private void lblIconEyeMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lblIconEyeMousePressed
        lblIconHide.setVisible(true);
        lblIconEye.setVisible(false);
        txtPassword.setEchoChar('●');
    }// GEN-LAST:event_lblIconEyeMousePressed

    private void lblIconHideMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lblIconHideMousePressed
        lblIconEye.setVisible(true);
        lblIconHide.setVisible(false);
        txtPassword.setEchoChar((char) 0);
    }// GEN-LAST:event_lblIconHideMousePressed

    private void lblForgotPasswordMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lblForgotPasswordMouseClicked
        new ForgetPasswordForm().setVisible(true);
    }// GEN-LAST:event_lblForgotPasswordMouseClicked

    // Getter Methods
    public javax.swing.JTextField getTxtUsername() {
        return txtUsername;
    }

    public javax.swing.JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public javax.swing.JButton getBtnLogin() {
        return btnLogin;
    }

    public javax.swing.JButton getBtnCancel() {
        return btnCancel;
    }

    public javax.swing.JCheckBox getCheckRemember() {
        return checkRemember;
    }

    public static void main(String args[]) {
        // ========== SETUP UI (FlatLaf) TRƯỚC TIÊN ==========
        UIConfiguration.setupUI();

        // ========== SAU ĐÓ MỚI KHỞI TẠO LOGIN FORM ==========
        java.awt.EventQueue.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnLogin;
    private javax.swing.JCheckBox checkRemember;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblForgotPassword;
    private javax.swing.JLabel lblIconEye;
    private javax.swing.JLabel lblIconHide;
    private javax.swing.JLabel lblIconKey;
    private javax.swing.JLabel lblIconUser;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JLabel lblRestaurantName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
