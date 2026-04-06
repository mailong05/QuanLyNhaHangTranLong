package com.restaurant.quanlydatbannhahang.gui;

import javax.swing.JFrame;

public class PanelDatLaiMatKhau extends javax.swing.JFrame {

    public PanelDatLaiMatKhau() {
        initComponents();

        // khử viền xanh khi click nút
        btnContinue.setFocusPainted(false);
        btnCancel.setFocusPainted(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblRestaurantName = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        clickCLose = new javax.swing.JLabel();
        clickMinimize = new javax.swing.JLabel();
        btnContinue = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        lblNhapLaiMK = new javax.swing.JLabel();
        lblMatKhauMoi = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();

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

        btnContinue.setBackground(new java.awt.Color(250, 249, 235));
        btnContinue.setForeground(new java.awt.Color(0, 0, 0));
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
        jLabel6.setText("ĐẶT LẠI MẬT KHẨU");

        btnCancel.setBackground(new java.awt.Color(250, 249, 235));
        btnCancel.setForeground(new java.awt.Color(0, 0, 0));
        btnCancel.setText("Quay lại");
        btnCancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        btnCancel.setContentAreaFilled(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lblNhapLaiMK.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNhapLaiMK.setForeground(new java.awt.Color(0, 0, 0));
        lblNhapLaiMK.setText("Nhập lại mật khẩu mới:");

        lblMatKhauMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMatKhauMoi.setForeground(new java.awt.Color(0, 0, 0));
        lblMatKhauMoi.setText("Nhập mật khẩu mới:");

        jPasswordField1.setBackground(new java.awt.Color(255, 255, 255));
        jPasswordField1.setForeground(new java.awt.Color(0, 0, 0));

        jPasswordField2.setBackground(new java.awt.Color(255, 255, 255));
        jPasswordField2.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(clickMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(clickCLose))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblNhapLaiMK)
                                .addComponent(lblMatKhauMoi))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPasswordField2)
                                .addComponent(jPasswordField1)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(143, 143, 143)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(btnContinue, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(11, 11, 11))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clickMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clickCLose, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jLabel6)
                .addGap(61, 61, 61)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNhapLaiMK)
                        .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMatKhauMoi)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnContinue, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

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
        // TODO add your handling code here:
        new PanelDatLaiMatKhau();
    }// GEN-LAST:event_btnContinueActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
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

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PanelDatLaiMatKhau().setVisible(true);
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
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMatKhauMoi;
    private javax.swing.JLabel lblNhapLaiMK;
    private javax.swing.JLabel lblRestaurantName;
    // End of variables declaration//GEN-END:variables
}
