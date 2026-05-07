package com.restaurant.quanlydatbannhahang.session;

import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;

public class SessionManager {
    private static TaiKhoan currentTaiKhoan = null;

    public static TaiKhoan getCurrentTaiKhoan() {
        return currentTaiKhoan;
    }

    public static void setCurrentTaiKhoan(TaiKhoan taiKhoan) {
        currentTaiKhoan = taiKhoan;
    }

    public static com.restaurant.quanlydatbannhahang.entity.NhanVien getCurrentNhanVien() {
        if (currentTaiKhoan != null) {
            return currentTaiKhoan.getNhanVien();
        }
        return null;
    }
    

    public static void clearSession() {
        currentTaiKhoan = null;
    }

    public static boolean isLoggedIn() {
        return currentTaiKhoan != null;
    }
}
