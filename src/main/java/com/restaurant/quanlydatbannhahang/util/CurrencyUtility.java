package com.restaurant.quanlydatbannhahang.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class CurrencyUtility {
    private static final Locale localeVN = new Locale("vi", "VN");
    private static final NumberFormat vnFormat = NumberFormat.getCurrencyInstance(localeVN);

    
    public static String formatVND(double amount) {
        return vnFormat.format(amount);
    }

    
    public static double parseVND(String formattedString) {
        if (formattedString == null || formattedString.isBlank()) {
            return 0;
        }

        
        
        String cleaned = formattedString.replaceAll("[^0-9\\-]", "");

        if (cleaned.isBlank() || cleaned.equals("-")) {
            return 0;
        }

        try {
            
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}