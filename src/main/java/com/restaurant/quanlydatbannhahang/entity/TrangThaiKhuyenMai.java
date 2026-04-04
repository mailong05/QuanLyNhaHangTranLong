package com.restaurant.quanlydatbannhahang.entity;

public enum TrangThaiKhuyenMai {
    NGUNG_AP_DUNG("Ngừng áp dụng"),
    CON_AP_DUNG("Còn áp dụng");

    private final String displayName;

    TrangThaiKhuyenMai(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
