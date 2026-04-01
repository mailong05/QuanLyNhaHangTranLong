package com.restaurant.quanlydatbannhahang.gui;

import javax.swing.JOptionPane;

public class LoadingScreen extends javax.swing.JFrame {

    public LoadingScreen() {
        initComponents();
        lblSystemName.setFont(new java.awt.Font("Segoe UI", 0, 12));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblRestaurantName = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        prgLoading = new javax.swing.JProgressBar();
        lblSystemName = new javax.swing.JLabel();
        lblPercentage = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRestaurantName.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        lblRestaurantName.setForeground(new java.awt.Color(255, 255, 255));
        lblRestaurantName.setText("TRAN LONG RESTAURANT");
        getContentPane().add(lblRestaurantName, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, 260, 30));

        lblStatus.setText("Loading...");
        getContentPane().add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));
        getContentPane().add(prgLoading, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 600, 10));

        lblSystemName.setForeground(new java.awt.Color(255, 255, 255));
        lblSystemName.setText("RESERVATION MANAGEMENT SYSTEM");
        lblSystemName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lblSystemName, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, 210, -1));

        lblPercentage.setForeground(new java.awt.Color(255, 255, 255));
        lblPercentage.setText("0%");
        getContentPane().add(lblPercentage, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 370, -1, -1));

        lblWelcome.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(255, 255, 255));
        lblWelcome.setText("WELCOME BACK!");
        getContentPane().add(lblWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 290, -1, -1));

        lblBackground.setForeground(new java.awt.Color(255, 255, 255));
        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background_loading.png"))); // NOI18N
        getContentPane().add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 390));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblBackground;
    public javax.swing.JLabel lblPercentage;
    private javax.swing.JLabel lblRestaurantName;
    public javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblSystemName;
    private javax.swing.JLabel lblWelcome;
    public javax.swing.JProgressBar prgLoading;
    // End of variables declaration//GEN-END:variables
}

