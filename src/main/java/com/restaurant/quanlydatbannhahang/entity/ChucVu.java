package com.restaurant.quanlydatbannhahang.entity;

public enum ChucVu {
    QUAN_LY("Quản lý"),
    BEP("Bếp"),
    THU_NGAN("Thu ngân"),
    PHUC_VU("Phục vụ");

    private final String displayName;

    ChucVu(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
