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
}
