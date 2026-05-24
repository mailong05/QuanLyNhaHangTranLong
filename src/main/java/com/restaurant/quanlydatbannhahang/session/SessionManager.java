package com.restaurant.quanlydatbannhahang.session;

import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;

public class SessionManager {
    private static TaiKhoan currentTaiKhoan = null;
    private static com.restaurant.quanlydatbannhahang.entity.CaLamViec currentCaLamViec = null;

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

    public static com.restaurant.quanlydatbannhahang.entity.CaLamViec getCurrentCaLamViec() {
        return currentCaLamViec;
    }

    public static void setCurrentCaLamViec(com.restaurant.quanlydatbannhahang.entity.CaLamViec caLamViec) {
        currentCaLamViec = caLamViec;
    }

    public static void clearSession() {
        currentTaiKhoan = null;
        currentCaLamViec = null;
    }

    public static boolean isLoggedIn() {
        return currentTaiKhoan != null;
    }
}