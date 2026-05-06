package com.restaurant.quanlydatbannhahang.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class CurrencyUtility {
    private static final Locale localeVN = new Locale("vi", "VN");
    private static final NumberFormat vnFormat = NumberFormat.getCurrencyInstance(localeVN);

    // Chiều 1: Từ số -> Chuỗi đẹp để hiện lên màn hình
    public static String formatVND(double amount) {
        return vnFormat.format(amount);
    }

    // Chiều 2: Từ chuỗi đẹp -> Số thuần để lưu DB (Fix lỗi format)
    public static double parseVND(String formattedString) {
        if (formattedString == null || formattedString.isBlank()) {
            return 0;
        }

        // 1. Dùng Regex chỉ giữ lại số và dấu trừ (-) 
        // Cách này sẽ biến "500.000 ₫" thành "500000"
        String cleaned = formattedString.replaceAll("[^0-9\\-]", "");

        if (cleaned.isBlank() || cleaned.equals("-")) {
            return 0;
        }

        try {
            // 2. Chuyển trực tiếp về Double, cực kỳ an toàn vì chuỗi giờ chỉ toàn số
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}