package com.restaurant.quanlydatbannhahang.entity;

public enum TrangThaiHoaDon {
    CHUA_THANH_TOAN("Chưa thanh toán"),
    DA_THANH_TOAN("Đã thanh toán"),
    DA_HUY("Đã hủy");

    private final String displayName;

    TrangThaiHoaDon(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
