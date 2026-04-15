package com.restaurant.quanlydatbannhahang.util;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class để query ID cuối cùng từ database
 * Dùng cho tất cả các entity cần sinh ID tự động
 * Format: PREFIX + 3 chữ số (VD: B005, KH025, PDB010...)
 */
public class IDQueryHelper {

    /**
     * Lấy ID cuối cùng của một entity từ database
     * Query: SELECT TOP 1 columnName FROM tableName ORDER BY columnName DESC
     * 
     * @param tableName  Tên bảng (VD: Ban, PhieuDatBan, HoaDon, KhachHang...)
     * @param columnName Tên cột ID (VD: MaBan, MaPhieuDatBan, MaHoaDon...)
     * @return ID cuối cùng (VD: B005, PDB010...) hoặc null nếu bảng rỗng/error
     */
    public static String getLastID(String tableName, String columnName) {
        String sql = "SELECT TOP 1 " + columnName + " FROM " + tableName
                + " ORDER BY " + columnName + " DESC";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null || conn.isClosed()) {
                System.err.println("❌ Database connection is closed!");
                return null;
            }

            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql)) {

                if (rs.next()) {
                    return rs.getString(columnName);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error querying last ID from " + tableName);
            e.printStackTrace();
        }

        return null; // Bảng rỗng hoặc có lỗi
    }
}
