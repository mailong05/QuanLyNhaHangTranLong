package com.restaurant.quanlydatbannhahang.entity;

public enum TrangThaiPhieuDat {
    DANG_CHO("Đang chờ"),
    DANG_SU_DUNG("Đang sử dụng"),
    DA_HUY("Đã hủy"),
    DA_SU_DUNG("Đã sử dụng");

    private final String displayName;

    TrangThaiPhieuDat(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static TrangThaiPhieuDat fromDisplayName(String displayName) {
        for (TrangThaiPhieuDat status : values()) {
            if (status.displayName.equals(displayName)) {
                return status;
            }
        }
        return DANG_CHO;
    }
}
