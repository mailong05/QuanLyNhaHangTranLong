
package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;
import com.restaurant.quanlydatbannhahang.gui.LoginForm;
import com.restaurant.quanlydatbannhahang.gui.MainForm;
import com.restaurant.quanlydatbannhahang.service.AuthService;

import javax.swing.JOptionPane;

public class CtrlLoginForm {
    private LoginForm view;
    private AuthService authService;
    private TaiKhoan currentUser; // Lưu thông tin user đăng nhập

    public CtrlLoginForm(LoginForm view) {
        this.view = view;
        this.authService = new AuthService();
        this.currentUser = null;

        // Gắn event listeners
        setupEventListeners();
    }

    /**
     * Thiết lập các event listeners cho các nút
     */
    private void setupEventListeners() {
        // Nút Đăng nhập
        view.getBtnLogin().addActionListener(e -> handleLogin());

        // Nút Hủy
        view.getBtnCancel().addActionListener(e -> handleCancel());

        // Nhấn Enter để đăng nhập
        view.getTxtPassword().addActionListener(e -> handleLogin());
    }

    /**
     * Xử lý đăng nhập
     */
    private void handleLogin() {
        String username = view.getTxtUsername().getText().trim();
        String password = String.valueOf(view.getTxtPassword().getPassword()).trim();

        // Validate input
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

        // Gọi service để đăng nhập
        currentUser = authService.login(username, password);

        if (currentUser != null) {
            JOptionPane.showMessageDialog(view,
                    "Đăng nhập thành công!\nXin chào: " + currentUser.getNhanVien().getHoTen(),
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

            // Đóng LoginForm
            view.dispose();

            // Mở MainForm và truyền thông tin user
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

    /**
     * Xử lý khi nhấn nút Hủy
     */
    private void handleCancel() {
        int confirm = JOptionPane.showConfirmDialog(view,
                "Bạn chắc chắn muốn thoát?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Getter cho thông tin user hiện tại
     */
    public TaiKhoan getCurrentUser() {
        return currentUser;
    }
}