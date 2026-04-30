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

    public static QuyenHan fromDisplayName(String text) {
        for (QuyenHan b : QuyenHan.values()) {
            if (b.displayName.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
