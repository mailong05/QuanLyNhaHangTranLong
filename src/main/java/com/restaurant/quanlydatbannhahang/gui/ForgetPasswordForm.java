package com.restaurant.quanlydatbannhahang.gui;

import javax.swing.JFrame;

public class ForgetPasswordForm extends javax.swing.JFrame {

    public ForgetPasswordForm() {
        initComponents();
        this.setLocationRelativeTo(null); // Căn giữa màn hình khi mở
        jLabel6.requestFocusInWindow(); // Đẩy focus ra khỏi các ô nhập liệu ban đầu

        // thiết lập Font chữ đồng bộ
        java.awt.Font fontsize = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12);
        txtUsername.setFont(fontsize);
        txtIdcard.setFont(fontsize);
        txtPhone.setFont(fontsize);
        txtMail.setFont(fontsize);
        btnContinue.setFont(fontsize);
        btnCancel.setFont(fontsize);

        txtUsername.setText("Username");
        txtIdcard.setText("ID Card Number");
        txtPhone.setText("Phone Number");
        txtMail.setText("Email Address");

        java.awt.Color placeholderColor = new java.awt.Color(102, 102, 102);

        // xử lý Focus cho txtUsername
        txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtUsername.getText().equals("Username")) {
                    txtUsername.setText("");
                    txtUsername.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtUsername.getText().isEmpty()) {
                    txtUsername.setForeground(placeholderColor);
                    txtUsername.setText("Username");
                }
            }
        });

        // xử lý Focus cho txtIdcard
        txtIdcard.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtIdcard.getText().equals("ID Card Number")) {
                    txtIdcard.setText("");
                    txtIdcard.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtIdcard.getText().isEmpty()) {
                    txtIdcard.setForeground(placeholderColor);
                    txtIdcard.setText("ID Card Number");
                }
            }
        });

        // xử lý Focus cho txtTelephone
        txtPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtPhone.getText().equals("Phone Number")) {
                    txtPhone.setText("");
                    txtPhone.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtPhone.getText().isEmpty()) {
                    txtPhone.setForeground(placeholderColor);
                    txtPhone.setText("Phone Number");
                }
            }
        });

        // xử lý Focus cho txtEmail
        txtMail.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtMail.getText().equals("Email Address")) {
                    txtMail.setText("");
                    txtMail.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtMail.getText().isEmpty()) {
                    txtMail.setForeground(placeholderColor);
                    txtMail.setText("Email Address");
                }
            }
        });

        // khử viền xanh khi click nút
        btnContinue.setFocusPainted(false);
        btnCancel.setFocusPainted(false);
    }

    @SuppressWarnings("unchecked")
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
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnContinue = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        btnCancel = new javax.swing.JButton();
        txtPhone = new javax.swing.JTextField();
        txtMail = new javax.swing.JTextField();
        txtIdcard = new javax.swing.JTextField();

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

        clickCLose.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        clickCLose.setText("X");
        clickCLose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clickCLose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickCLoseMouseClicked(evt);
            }
        });

        clickMinimize.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        clickMinimize.setText("-");
        clickMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clickMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickMinimizeMouseClicked(evt);
            }
        });

        txtUsername.setBackground(new java.awt.Color(255, 251, 233));
        txtUsername.setForeground(new java.awt.Color(102, 102, 102));
        txtUsername.setBorder(null);
        txtUsername.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(102, 102, 102));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_user.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_id.png"))); // NOI18N

        btnContinue.setBackground(new java.awt.Color(250, 249, 235));
        btnContinue.setText("Tiếp tục");
        btnContinue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        btnContinue.setContentAreaFilled(false);
        btnContinue.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnContinue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinueActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("QUÊN MẬT KHẨU");

        jSeparator3.setForeground(new java.awt.Color(102, 102, 102));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_phone.png"))); // NOI18N

        jSeparator4.setForeground(new java.awt.Color(102, 102, 102));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_mail.png"))); // NOI18N

        jSeparator6.setForeground(new java.awt.Color(102, 102, 102));

        btnCancel.setBackground(new java.awt.Color(250, 249, 235));
        btnCancel.setText("Quay lại");
        btnCancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        btnCancel.setContentAreaFilled(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        txtPhone.setBackground(new java.awt.Color(255, 251, 233));
        txtPhone.setForeground(new java.awt.Color(102, 102, 102));
        txtPhone.setBorder(null);
        txtPhone.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneActionPerformed(evt);
            }
        });

        txtMail.setBackground(new java.awt.Color(255, 251, 233));
        txtMail.setForeground(new java.awt.Color(102, 102, 102));
        txtMail.setBorder(null);
        txtMail.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMailActionPerformed(evt);
            }
        });

        txtIdcard.setBackground(new java.awt.Color(255, 251, 233));
        txtIdcard.setForeground(new java.awt.Color(102, 102, 102));
        txtIdcard.setBorder(null);
        txtIdcard.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIdcard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdcardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addGap(31, 31, 31)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtIdcard, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(143, 143, 143)
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(btnContinue, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtMail, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(clickMinimize,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(clickCLose,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(30, 30, 30)
                                                .addComponent(jLabel6)
                                                .addGap(34, 34, 34)
                                                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtIdcard, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtPhone, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addComponent(jLabel9))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(txtMail, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)))
                                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnContinue, javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 300, 400));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
        // TODO add your handling code here:

    }// GEN-LAST:event_btnContinueActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnCancelActionPerformed

    private void txtPhoneActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtPhoneActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtPhoneActionPerformed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblRestaurantName;
    private javax.swing.JTextField txtIdcard;
    private javax.swing.JTextField txtMail;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
