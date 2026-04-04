package com.restaurant.quanlydatbannhahang.entity;

public enum TrangThaiNhanVien {
    DANG_LAM_VIEC("Đang làm việc"),
    DA_NGHI_VIEC("Đã nghỉ việc");

    private final String displayName;

    TrangThaiNhanVien(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
