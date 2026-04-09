package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.ChucVu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChucVuDAO {
    private Connection connection;

    public ChucVuDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Lấy tất cả các chức vụ
     */
    public List<ChucVu> getAllChucVu() {
        List<ChucVu> list = new ArrayList<>();
        String sql = "SELECT * FROM ChucVu";

        try (PreparedStatement pstm = connection.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("tenChucVu");
                ChucVu chucVu = ChucVu.valueOf(name);
                list.add(chucVu);
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy danh sách chức vụ: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Tìm chức vụ theo tên
     */
    public ChucVu findByName(String name) {
        String sql = "SELECT * FROM ChucVu WHERE tenChucVu = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, name);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return ChucVu.valueOf(rs.getString("tenChucVu"));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi tìm chức vụ: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Kiểm tra chức vụ tồn tại
     */
    public boolean exists(String name) {
        return findByName(name) != null;
    }
}
