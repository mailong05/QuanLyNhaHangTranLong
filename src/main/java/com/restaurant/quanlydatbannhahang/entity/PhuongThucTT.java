package com.restaurant.quanlydatbannhahang.entity;

public enum PhuongThucTT {
    TIEN_MAT("Tiền mặt"),
    CHUYEN_KHOAN("Chuyển khoản"),
    THE("Thẻ");

    private final String displayName;

    PhuongThucTT(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    
    public static PhuongThucTT fromDisplayName(String text) {
        for (PhuongThucTT b : PhuongThucTT.values()) {
            if (b.displayName.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null; 
    }
}
