package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.QuyenHan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuyenHanDAO {
    private Connection connection;

    public QuyenHanDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Lấy tất cả quyền hạn
     */
    public List<QuyenHan> getAllQuyenHan() {
        List<QuyenHan> list = new ArrayList<>();
        String sql = "SELECT * FROM QuyenHan";

        try (PreparedStatement pstm = connection.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("tenQuyenHan");
                QuyenHan quyenHan = QuyenHan.valueOf(name);
                list.add(quyenHan);
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy danh sách quyền hạn: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Tìm quyền hạn theo tên
     */
    public QuyenHan findByName(String name) {
        String sql = "SELECT * FROM QuyenHan WHERE tenQuyenHan = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, name);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return QuyenHan.valueOf(rs.getString("tenQuyenHan"));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi tìm quyền hạn: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Kiểm tra quyền hạn tồn tại
     */
    public boolean exists(String name) {
        return findByName(name) != null;
    }
}
