package com.restaurant.quanlydatbannhahang.util;

/**
 * Utility class để sinh ID tự động cho các entity
 * Format: PREFIX + 3 chữ số (VD: PDB001, HD004, KH002)
 */
public class IDGeneratorHelper {

    /**
     * Sinh ID tiếp theo dựa trên ID cuối cùng
     * 
     * @param prefix Tiền tố (VD: PDB, HD, KH, NV, BAN, THUE, KHUYENMAI...)
     * @param lastID ID cuối cùng từ database (VD: PDB005)
     * @return ID tiếp theo (VD: PDB006)
     */
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

    /**
     * Sinh ID mặc định nếu danh sách rỗng
     * 
     * @param prefix Tiền tố (VD: PDB, HD, KH...)
     * @return ID mặc định (VD: PDB001)
     */
    public static String generateDefaultID(String prefix) {
        return prefix + "001";
    }

    /**
     * Sinh ID tiếp theo từ ID đầu đủ (VD: KH025 → KH026)
     * Tách 3 số cuối, cộng 1, format lại
     * 
     * @param lastFullID ID cuối cùng từ database (VD: KH025, PDB010...)
     * @return ID tiếp theo (VD: KH026, PDB011...) hoặc null nếu input không hợp lệ
     */
    public static String generateNextIDFromFullID(String lastFullID) {
        if (lastFullID == null || lastFullID.isEmpty()) {
            return null;
        }

        try {
            // Format cố định: PREFIX + 3 số
            // VD: "KH025" → prefix="KH", number="025"
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
