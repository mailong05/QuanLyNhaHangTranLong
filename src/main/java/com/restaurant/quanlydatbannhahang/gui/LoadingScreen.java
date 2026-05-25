package com.restaurant.quanlydatbannhahang.gui;
import com.restaurant.quanlydatbannhahang.service.MonAnService;
import com.restaurant.quanlydatbannhahang.entity.MonAn;
import com.restaurant.quanlydatbannhahang.util.ImageUtil;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
public class LoadingScreen extends javax.swing.JFrame {
        public LoadingScreen() {
                initComponents();
                lblSystemName.setFont(new java.awt.Font("Segoe UI", 0, 14));
                preloadData();
        }
        // <editor-fold
        // Code">//GEN-BEGIN:initComponents
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
                lblRestaurantName.setFont(new java.awt.Font("Segoe UI", 3, 20));
                lblRestaurantName.setForeground(new java.awt.Color(255, 255, 255));
                lblRestaurantName.setText("TRAN LONG RESTAURANT");
                getContentPane().add(lblRestaurantName,
                                new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, 260, 30));
                lblStatus.setText("Loading...");
                getContentPane().add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));
                getContentPane().add(prgLoading, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 600, 10));
                lblSystemName.setForeground(new java.awt.Color(255, 255, 255));
                lblSystemName.setText("RESERVATION MANAGEMENT SYSTEM");
                lblSystemName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                getContentPane().add(lblSystemName,
                                new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, 210, -1));
                lblPercentage.setForeground(new java.awt.Color(255, 255, 255));
                lblPercentage.setText("0%");
                getContentPane().add(lblPercentage,
                                new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 370, -1, -1));
                lblWelcome.setFont(new java.awt.Font("Segoe UI", 1, 20));
                lblWelcome.setForeground(new java.awt.Color(255, 255, 255));
                lblWelcome.setText("WELCOME BACK!");
                getContentPane().add(lblWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 290, -1, -1));
                lblBackground.setForeground(new java.awt.Color(255, 255, 255));
                lblBackground.setIcon(
                                new javax.swing.ImageIcon(getClass().getResource("/images/background_loading.png")));
                getContentPane().add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 390));
                pack();
                setLocationRelativeTo(null);
        }// </editor-fold>//GEN-END:initComponents
        private void preloadData() {
                SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                                try {
                                        publish("Đang tải dữ liệu món ăn...");
                                        setProgress(10);
                                        MonAnService monAnService = new MonAnService();
                                        List<MonAn> allMonAn = monAnService.getAllMonAn();
                                        if (allMonAn != null && !allMonAn.isEmpty()) {
                                                publish("Đang tải hình ảnh món ăn...");
                                                setProgress(30);
                                                List<String> imagePaths = new ArrayList<>();
                                                for (MonAn monAn : allMonAn) {
                                                        if (monAn != null && monAn.getUrlHinhAnh() != null
                                                                        && !monAn.getUrlHinhAnh().trim().isEmpty()) {
                                                                imagePaths.add(monAn.getUrlHinhAnh());
                                                        }
                                                }
                                                int totalImages = imagePaths.size();
                                                if (totalImages > 0) {
                                                        for (int i = 0; i < totalImages; i++) {
                                                                String imagePath = imagePaths.get(i);
                                                                ImageUtil.loadImageIcon(imagePath, 72);
                                                                int progress = 30
                                                                                + (int) ((i + 1) * 60.0 / totalImages);
                                                                setProgress(progress);
                                                                publish("Đã tải " + (i + 1) + "/" + totalImages
                                                                                + " hình ảnh...");
                                                        }
                                                }
                                        }
                                        setProgress(90);
                                        publish("Hoàn thành!");
                                        Thread.sleep(500);
                                        setProgress(100);
                                } catch (Exception e) {
                                        e.printStackTrace();
                                        publish("Lỗi khi tải dữ liệu: " + e.getMessage());
                                }
                                return null;
                        }
                        @Override
                        protected void process(List<String> chunks) {
                                for (String status : chunks) {
                                        lblStatus.setText(status);
                                }
                        }
                        @Override
                        protected void done() {
                                SwingUtilities.invokeLater(() -> {
                                        dispose();
                                });
                        }
                };
                worker.addPropertyChangeListener(evt -> {
                        if ("progress".equals(evt.getPropertyName())) {
                                int progress = (Integer) evt.getNewValue();
                                prgLoading.setValue(progress);
                                lblPercentage.setText(progress + "%");
                        }
                });
                worker.execute();
        }
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