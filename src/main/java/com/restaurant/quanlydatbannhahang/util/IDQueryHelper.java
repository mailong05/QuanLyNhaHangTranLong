package com.restaurant.quanlydatbannhahang.util;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IDQueryHelper {

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

        return null; 
    }
}
