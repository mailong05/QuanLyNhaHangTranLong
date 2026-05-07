package com.restaurant.quanlydatbannhahang.util;
public class IDGeneratorHelper {
    public static String generateNextID(String prefix, String lastID) {
        if (lastID == null || lastID.isEmpty()) {
            return prefix + "001";
        }
        try {
            String numberPart = lastID.substring(prefix.length());
            int lastNumber = Integer.parseInt(numberPart);
            int nextNumber = lastNumber + 1;
            return String.format("%s%03d", prefix, nextNumber);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return prefix + "001";
        }
    }
    public static String generateDefaultID(String prefix) {
        return prefix + "001";
    }
    public static String generateNextIDFromFullID(String lastFullID) {
        if (lastFullID == null || lastFullID.isEmpty()) {
            return null;
        }
        try {
            int prefixLen = lastFullID.length() - 3;
            String prefix = lastFullID.substring(0, prefixLen);
            int lastNumber = Integer.parseInt(lastFullID.substring(prefixLen));
            int nextNumber = lastNumber + 1;
            return String.format("%s%03d", prefix, nextNumber);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return null;
        }
    }
}