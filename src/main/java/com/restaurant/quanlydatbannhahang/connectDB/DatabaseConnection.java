package com.restaurant.quanlydatbannhahang.connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyDatBan;encrypt=true;trustServerCertificate=true";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "sapassword";

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Kết nối Database thành công!");
                return connection;
            }
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("SQL Server Driver không tìm thấy!");
            System.out.println("Hãy thêm JDBC jar vào pom.xml hoặc lib folder");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("Không thể kết nối đến Database!");
            System.out.println("Kiểm tra: URL, username, password, và Service SQL Server");
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Đóng kết nối Database");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi đóng kết nối");
            e.printStackTrace();
        }
    }

    public static boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
