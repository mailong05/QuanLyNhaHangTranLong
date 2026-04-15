package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.PhuongThucTT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhuongThucTTDAO {

    public PhuongThucTTDAO() {
    }

    /**
     * Lấy tất cả phương thức thanh toán
     */
    public List<PhuongThucTT> getAllPhuongThucTT() {
        Connection connection = DatabaseConnection.getConnection();
        List<PhuongThucTT> list = new ArrayList<>();
        String sql = "SELECT * FROM PhuongThucTT";

        try (PreparedStatement pstm = connection.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("tenPhuongThuc");
                PhuongThucTT phuongThuc = PhuongThucTT.valueOf(name);
                list.add(phuongThuc);
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy danh sách phương thức thanh toán: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Tìm phương thức thanh toán theo tên
     */
    public PhuongThucTT findByName(String name) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM PhuongThucTT WHERE tenPhuongThuc = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, name);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return PhuongThucTT.valueOf(rs.getString("tenPhuongThuc"));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi tìm phương thức thanh toán: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Kiểm tra phương thức thanh toán tồn tại
     */
    public boolean exists(String name) {
        return findByName(name) != null;
    }
}
