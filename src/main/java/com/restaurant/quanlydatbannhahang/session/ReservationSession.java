package com.restaurant.quanlydatbannhahang.session;

import java.time.LocalDateTime;

/**
 * Lưu trữ thông tin tạm thời trong luồng Đặt Bàn Trước
 */
public class ReservationSession {
    private static LocalDateTime tempSelectedDateTime;

    public static void setTempSelectedDateTime(LocalDateTime dateTime) {
        tempSelectedDateTime = dateTime;
    }

    public static LocalDateTime getTempSelectedDateTime() {
        return tempSelectedDateTime;
    }

    public static void clear() {
        tempSelectedDateTime = null;
    }
}