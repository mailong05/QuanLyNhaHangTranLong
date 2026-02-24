package com.restaurant.quanlydatbannhahang;

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
        } catch (Exception e) { e.printStackTrace(); }

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