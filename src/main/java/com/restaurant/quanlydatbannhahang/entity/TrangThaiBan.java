package com.restaurant.quanlydatbannhahang.entity;

public enum TrangThaiBan {
    TRONG("Trống"),
    DANG_DUNG("Đang dùng"),
    DA_DAT("Đã đặt");

    private final String displayName;

    TrangThaiBan(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
