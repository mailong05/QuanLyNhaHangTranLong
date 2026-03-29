package com.restaurant.quanlydatbannhahang.gui;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import javax.swing.JOptionPane;

public class QuanLyDatBanNhaHang {

    public static void main(String[] args) {
        // setup giao diện windows (chạy cho đẹp)
        try {

            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // khởi tạo loadingform
        LoadingScreen sp = new LoadingScreen();
        sp.setVisible(true);

        new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(30);
                    final int p = i;

                    java.awt.EventQueue.invokeLater(() -> {
                        sp.loadingbar.setValue(p);
                        sp.loadingvalue.setText(p + "%");

                        // chữ chạy khi loading
                        if (p == 10) {
                            sp.loadinglabel.setText("Turning on Modules...");
                        }
                        if (p == 20) {
                            sp.loadinglabel.setText("Loading Modules...");
                        }
                        if (p == 50) {
                            sp.loadinglabel.setText("Connecting to Database...");

                            // Khởi tạo kết nối database
                            if (DatabaseConnection.getConnection() == null) {
                                sp.dispose();
                                JOptionPane.showMessageDialog(null,
                                        "❌ Không thể kết nối đến Database!\n\n" +
                                                "Vui lòng kiểm tra:\n" +
                                                "1. SQL Server Service đang chạy?\n" +
                                                "2. Database 'QuanLyDatBan' đã được tạo?\n" +
                                                "3. Username/Password đúng không?\n" +
                                                "4. JDBC Driver đã được cài đặt?",
                                        "Lỗi Kết Nối Database",
                                        JOptionPane.ERROR_MESSAGE);
                                System.exit(1);
                            }
                        }
                        if (p == 70) {
                            sp.loadinglabel.setText("Connection Successful !");
                        }
                        if (p == 80) {
                            sp.loadinglabel.setText("Launching Application...");
                        }

                        if (p == 100) {
                            sp.dispose();
                            new LoginForm().setVisible(true);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}