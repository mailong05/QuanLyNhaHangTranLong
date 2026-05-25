package com.restaurant.quanlydatbannhahang.entity;

public enum TrangThaiCa {
    DANG_LAM_VIEC("Đang làm việc"),
    DA_KET_CA("Đã kết ca");

    private final String displayName;

    TrangThaiCa(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static TrangThaiCa fromName(String name) {
        if (name == null) {
            return null;
        }
        for (TrangThaiCa tt : values()) {
            if (tt.name().equalsIgnoreCase(name) || tt.displayName.equalsIgnoreCase(name)) {
                return tt;
            }
        }
        return null;
    }
}