package com.restaurant.quanlydatbannhahang.entity;

public enum QuyenHan {
    STAFF("Staff"),
    MANAGER("Manager");

    private String displayName;

    QuyenHan(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
