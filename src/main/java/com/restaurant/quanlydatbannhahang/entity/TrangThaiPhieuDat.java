package com.restaurant.quanlydatbannhahang.entity;

public enum TrangThaiPhieuDat {
    CHO_XAC_NHAN("Chờ xác nhận"),
    DA_XAC_NHAN("Đã xác nhận"),
    DA_DEN("Đã đến"),
    DA_HUY("Đã hủy");

    private final String displayName;

    TrangThaiPhieuDat(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
