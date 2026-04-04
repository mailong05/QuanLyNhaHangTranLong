package com.restaurant.quanlydatbannhahang.entity;

public enum TrangThaiThue {
    NGUNG_AP_DUNG("Ngừng áp dụng"),
    CON_AP_DUNG("Còn áp dụng");

    private final String displayName;

    TrangThaiThue(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
