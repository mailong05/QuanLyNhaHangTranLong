package com.restaurant.quanlydatbannhahang.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Color;
import com.restaurant.quanlydatbannhahang.gui.MainForm;
import com.restaurant.quanlydatbannhahang.gui.ForgetPasswordForm;
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
        iconEye.setVisible(false);
        iconHide.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        // ... (Giữ nguyên toàn bộ nội dung trong initComponents của bạn)
        // Lưu ý: Code này quá dài nên mình tóm gọn, bạn hãy giữ lại phần NetBeans tự
        // sinh
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        clickCLose = new javax.swing.JLabel();
        clickMinimize = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        clickForgotpassword = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        checkRemember = new javax.swing.JCheckBox();
        iconEye = new javax.swing.JLabel();
        iconHide = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(142, 128, 106));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo-restaurantTL.png")));
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 220, 200));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 3, 18));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("TRAN LONG RESTAURANT");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 400));

        jPanel2.setBackground(new java.awt.Color(255, 251, 233));
        jPanel2.setFont(new java.awt.Font("sansserif", 0, 14));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        clickCLose.setFont(new java.awt.Font("sansserif", 1, 14));
        clickCLose.setText("X");
        clickCLose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clickCLose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickCLoseMouseClicked(evt);
            }
        });
        jPanel2.add(clickCLose, new org.netbeans.lib.awtextra.AbsoluteConstraints(273, 8, 20, -1));

        clickMinimize.setFont(new java.awt.Font("sansserif", 1, 18));
        clickMinimize.setText("-");
        clickMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clickMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickMinimizeMouseClicked(evt);
            }
        });
        jPanel2.add(clickMinimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 8, 15, -1));

        txtUsername.setBackground(new java.awt.Color(255, 251, 233));
        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtUsername.setForeground(new java.awt.Color(102, 102, 102));
        txtUsername.setBorder(null);
        txtUsername.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });
        jPanel2.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 107, 200, 24));

        txtPassword.setBackground(new java.awt.Color(255, 251, 233));
        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtPassword.setForeground(new java.awt.Color(102, 102, 102));
        txtPassword.setBorder(null);
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        jPanel2.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 175, 200, 24));

        jSeparator2.setForeground(new java.awt.Color(102, 102, 102));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 199, 200, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo-user.png")));
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 107, -1, 31));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo-password.png")));
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 172, -1, -1));

        btnLogin.setBackground(new java.awt.Color(250, 249, 235));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 0, 13));
        btnLogin.setText("Login");
        btnLogin.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        btnLogin.setContentAreaFilled(false);
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        jPanel2.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 283, 60, 26));

        btnCancel.setBackground(new java.awt.Color(250, 249, 235));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 0, 13));
        btnCancel.setText("Cancel");
        btnCancel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        btnCancel.setContentAreaFilled(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(99, 283, 60, 26));

        clickForgotpassword.setFont(new java.awt.Font("Segoe UI", 0, 13));
        clickForgotpassword.setForeground(new java.awt.Color(102, 102, 102));
        clickForgotpassword.setText("Forgot Password?");
        clickForgotpassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clickForgotpassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickForgotpasswordMouseClicked(evt);
            }
        });
        jPanel2.add(clickForgotpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 322, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("LOGIN");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 55, -1, -1));

        jSeparator3.setForeground(new java.awt.Color(102, 102, 102));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 131, 200, -1));

        checkRemember.setBackground(new java.awt.Color(250, 249, 235));
        checkRemember.setFont(new java.awt.Font("sansserif", 0, 12));
        checkRemember.setText("Remember me");
        checkRemember.setContentAreaFilled(false);
        checkRemember.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        checkRemember.setFocusPainted(false);
        checkRemember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkRememberActionPerformed(evt);
            }
        });
        jPanel2.add(checkRemember, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 231, -1, -1));

        iconEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo-eye.png")));
        iconEye.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconEye.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                iconEyeMousePressed(evt);
            }
        });
        jPanel2.add(iconEye, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 175, 20, -1));

        iconHide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo-invisible.png")));
        iconHide.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconHide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                iconHideMousePressed(evt);
            }
        });
        jPanel2.add(iconHide, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 175, 20, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 300, 400));
        pack();
    }
    // </editor-fold>

    private void clickMinimizeMouseClicked(java.awt.event.MouseEvent evt) {
        this.setState(JFrame.ICONIFIED);
    }

    private void clickCLoseMouseClicked(java.awt.event.MouseEvent evt) {
        System.exit(0);
    }

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {
        btnLoginActionPerformed(evt);
    }

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {
        txtPassword.requestFocus();
    }

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        txtUsername.setText("Username");
        txtUsername.setForeground(new Color(102, 102, 102));
        txtPassword.setText("Password");
        txtPassword.setForeground(new Color(102, 102, 102));
        txtPassword.setEchoChar((char) 0);
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        String user = txtUsername.getText().trim();
        String pass = String.valueOf(txtPassword.getPassword()).trim();

        // Validate input
        if (user.isEmpty() || user.equals("Username")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            txtUsername.requestFocus();
            return;
        }

        if (pass.isEmpty() || pass.equals("Password")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtPassword.requestFocus();
            return;
        }

        // Gọi AuthService để lấy dữ liệu từ Database
        AuthService authService = new AuthService();
        TaiKhoan taiKhoan = authService.login(user, pass);

        if (taiKhoan != null) {
            JOptionPane.showMessageDialog(this,
                    "Đăng nhập thành công!\nXin chào: " + taiKhoan.getNhanVien().getHoTen(),
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

            try {
                // Ẩn LoginForm
                this.setVisible(false);
                System.out.println("✓ LoginForm ẩn thành công");

                // Mở MainForm và truyền thông tin user
                System.out.println("Đang tạo MainForm...");
                MainForm mainForm = new MainForm(taiKhoan);
                System.out.println("✓ MainForm khởi tạo xong");

                mainForm.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                System.out.println("✓ Thiết lập setDefaultCloseOperation");

                mainForm.setVisible(true);
                System.out.println("✓ MainForm hiển thị");

                // Đóng LoginForm sau khi MainForm hiển thị
                this.dispose();
                System.out.println("✓ LoginForm đã đóng");
            } catch (RuntimeException e) {
                // Nếu là lỗi khởi tạo MainForm
                if (e.getMessage() != null && e.getMessage().contains("khởi tạo MainForm")) {
                    System.out.println("❌ Lỗi khởi tạo MainForm: " + e.getCause().getMessage());
                    this.setVisible(true);
                    JOptionPane.showMessageDialog(this,
                            "Lỗi: " + e.getCause().getMessage() +
                                    "\n\nVui lòng kiểm tra:\n" +
                                    "1. Các file image trong thư mục resources/images\n" +
                                    "2. Database connection\n" +
                                    "3. Thử đăng nhập lại",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    throw e;
                }
            } catch (Exception e) {
                System.out.println("❌ Lỗi khi mở MainForm: " + e.getMessage());
                e.printStackTrace();
                this.setVisible(true);
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi mở giao diện chính:\n" + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Tên đăng nhập hoặc mật khẩu không đúng!",
                    "Lỗi đăng nhập",
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtPassword.setEchoChar((char) 0);
            txtPassword.setText("Password");
            txtPassword.setForeground(new Color(102, 102, 102));
            txtUsername.requestFocus();
        }
    }

    private void checkRememberActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void iconEyeMousePressed(java.awt.event.MouseEvent evt) {
        iconHide.setVisible(true);
        iconEye.setVisible(false);
        txtPassword.setEchoChar('●');
    }

    private void iconHideMousePressed(java.awt.event.MouseEvent evt) {
        iconEye.setVisible(true);
        iconHide.setVisible(false);
        txtPassword.setEchoChar((char) 0);
    }

    private void clickForgotpasswordMouseClicked(java.awt.event.MouseEvent evt) {
        new ForgetPasswordForm().setVisible(true);
    }

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
        java.awt.EventQueue.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }

    // Variables declaration
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnLogin;
    private javax.swing.JCheckBox checkRemember;
    private javax.swing.JLabel clickCLose;
    private javax.swing.JLabel clickForgotpassword;
    private javax.swing.JLabel clickMinimize;
    private javax.swing.JLabel iconEye;
    private javax.swing.JLabel iconHide;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
}