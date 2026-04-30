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

    public static TrangThaiNhanVien fromDisplayName(String text) {
        for (TrangThaiNhanVien b : TrangThaiNhanVien.values()) {
            if (b.displayName.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
