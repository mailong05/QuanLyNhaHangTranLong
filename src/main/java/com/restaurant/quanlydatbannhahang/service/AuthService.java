package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.TaiKhoanDAO;
import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;

public class AuthService {
    private TaiKhoanDAO taiKhoanDAO;

    public AuthService() {
        this.taiKhoanDAO = new TaiKhoanDAO();
    }

    /**
     * Đăng nhập
     * 
     * @param username
     * @param password
     * @return TaiKhoan nếu đăng nhập thành công, null nếu thất bại
     */
    public TaiKhoan login(String username, String password) {
        // Validation
        if (username == null || username.trim().isEmpty()) {
            System.out.println("❌ Tên đăng nhập không được để trống!");
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            System.out.println("❌ Mật khẩu không được để trống!");
            return null;
        }

        // Tìm tài khoản
        TaiKhoan taiKhoan = taiKhoanDAO.findByUsernameAndPassword(username, password);

        if (taiKhoan == null) {
            System.out.println("❌ Tên đăng nhập hoặc mật khẩu không đúng");
            return null;
        }

        // Kiểm tra nhân viên có hoạt động không
        if (!taiKhoan.getNhanVien().isTrangThai()) {
            System.out.println("❌ Nhân viên này đã bị vô hiệu hóa!");
            return null;
        }

        System.out.println("✓ Đăng nhập thành công: " + taiKhoan.getNhanVien().getHoTen());
        return taiKhoan;
    }

    /**
     * Kiểm tra tài khoản tồn tại
     */
    public boolean checkUsernameExists(String username) {
        return taiKhoanDAO.existUsername(username);
    }

    /**
     * Lấy thông tin tài khoản theo username
     */
    public TaiKhoan getTaiKhoanByUsername(String username) {
        return taiKhoanDAO.findByUsername(username);
    }
}

