package com.restaurant.quanlydatbannhahang.entity;

public enum LoaiMonAn {
    KHAI_VI("Khai vị"),
    MON_CHINH("Món chính"),
    HAI_SAN("Hải sản"),
    DO_UONG("Đồ uống"),
    TRANG_MIENG("Tráng miệng");

    private final String displayName;

    LoaiMonAn(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static LoaiMonAn fromDisplayName(String text) {
        for (LoaiMonAn b : LoaiMonAn.values()) {
            if (b.displayName.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
