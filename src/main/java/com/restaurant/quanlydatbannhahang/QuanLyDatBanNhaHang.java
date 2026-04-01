package com.restaurant.quanlydatbannhahang;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import javax.swing.JOptionPane;
import com.restaurant.quanlydatbannhahang.gui.LoadingScreen;
import com.restaurant.quanlydatbannhahang.gui.LoginForm;

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
        sp.setLocationRelativeTo(null); // Nên thêm dòng này để hiện giữa màn hình
        sp.setVisible(true);

        new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(30);
                    final int p = i;

                    java.awt.EventQueue.invokeLater(() -> {
                        sp.prgLoading.setValue(p);
                        sp.lblPercentage.setText(p + "%");

                        // chữ chạy khi loading
                        if (p == 10) {
                            sp.lblStatus.setText("Turning on Modules...");
                        }
                        if (p == 20) {
                            sp.lblStatus.setText("Loading Modules...");
                        }
                        if (p == 50) {
                            sp.lblStatus.setText("Connecting to Database...");

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
                            sp.lblStatus.setText("Connection Successful !");
                        }
                        if (p == 80) {
                            sp.lblStatus.setText("Launching Application...");
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