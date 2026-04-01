package com.restaurant.quanlydatbannhahang.gui;

import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;
import com.restaurant.quanlydatbannhahang.service.AuthService;

import javax.swing.JOptionPane;

public class CtrlLoginForm {
    private LoginForm view;
    private AuthService authService;
    private TaiKhoan currentUser;

    public CtrlLoginForm(LoginForm view) {
        this.view = view;
        this.authService = new AuthService();
        this.currentUser = null;

        setupEventListeners();
    }

    private void setupEventListeners() {

        view.getBtnLogin().addActionListener(e -> handleLogin());

        view.getBtnCancel().addActionListener(e -> handleCancel());

        view.getTxtPassword().addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = view.getTxtUsername().getText().trim();
        String password = String.valueOf(view.getTxtPassword().getPassword()).trim();

        if (username.isEmpty() || username.equals("Username")) {
            JOptionPane.showMessageDialog(view,
                    "Vui lòng nhập tên đăng nhập!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            view.getTxtUsername().requestFocus();
            return;
        }

        if (password.isEmpty() || password.equals("Password")) {
            JOptionPane.showMessageDialog(view,
                    "Vui lòng nhập mật khẩu!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            view.getTxtPassword().requestFocus();
            return;
        }

        currentUser = authService.login(username, password);

        if (currentUser != null) {
            JOptionPane.showMessageDialog(view,
                    "Đăng nhập thành công!\nXin chào: " + currentUser.getNhanVien().getHoTen(),
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

            view.dispose();

            new MainForm(currentUser).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(view,
                    "Tên đăng nhập hoặc mật khẩu không đúng!",
                    "Lỗi đăng nhập",
                    JOptionPane.ERROR_MESSAGE);
            view.getTxtPassword().setText("");
            view.getTxtPassword().setEchoChar((char) 0);
            view.getTxtPassword().setText("Password");
            view.getTxtPassword().setForeground(new java.awt.Color(102, 102, 102));
            view.getTxtUsername().requestFocus();
        }
    }

    private void handleCancel() {
        int confirm = JOptionPane.showConfirmDialog(view,
                "Bạn chắc chắn muốn thoát?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public TaiKhoan getCurrentUser() {
        return currentUser;
    }
}