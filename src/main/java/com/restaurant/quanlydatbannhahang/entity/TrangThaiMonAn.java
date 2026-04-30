package com.restaurant.quanlydatbannhahang.entity;

public enum TrangThaiMonAn {
    CON("Còn"),
    HET("Hết");

    private final String displayName;

    TrangThaiMonAn(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static TrangThaiMonAn fromDisplayName(String text) {
        for (TrangThaiMonAn b : TrangThaiMonAn.values()) {
            if (b.displayName.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
