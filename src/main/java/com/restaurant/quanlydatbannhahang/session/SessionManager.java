package com.restaurant.quanlydatbannhahang.session;

import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;

/**
 * Quản lý phiên đăng nhập của người dùng
 * Lưu thông tin TaiKhoan hiện tại toàn cục để các module khác có thể truy cập
 */
public class SessionManager {
    private static TaiKhoan currentTaiKhoan = null;
    
    /**
     * Lấy tài khoản hiện tại
     */
    public static TaiKhoan getCurrentTaiKhoan() {
        return currentTaiKhoan;
    }
    
    /**
     * Đặt tài khoản hiện tại
     */
    public static void setCurrentTaiKhoan(TaiKhoan taiKhoan) {
        currentTaiKhoan = taiKhoan;
    }
    
    /**
     * Lấy nhân viên hiện tại
     */
    public static com.restaurant.quanlydatbannhahang.entity.NhanVien getCurrentNhanVien() {
        if (currentTaiKhoan != null) {
            return currentTaiKhoan.getNhanVien();
        }
        return null;
    }
    
    /**
     * Xóa phiên đăng nhập
     */
    public static void clearSession() {
        currentTaiKhoan = null;
    }
    
    /**
     * Kiểm tra đã đăng nhập chưa
     */
    public static boolean isLoggedIn() {
        return currentTaiKhoan != null;
    }
}
