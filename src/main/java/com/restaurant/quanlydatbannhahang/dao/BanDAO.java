package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class BanDAO {
    private Connection connection;

    public BanDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean themBan(Ban ban) {
        String sql = "insert into Ban (maBan, soGhe, viTri, maKhuVuc, trangThai) values (?,?,?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, ban.getMaBan());
            pstm.setInt(2, ban.getSoGhe());
            pstm.setString(3, ban.getViTri());
            pstm.setString(4, ban.getKhuVuc().getMaKhuVuc());
            pstm.setString(5, ban.getTrangThai().name());
            return pstm.executeUpdate() > 0;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return false;
    }

    public Ban getBanTheoMa(String maBan) {
        return null;
    }

    public List<Ban> getAllBan() {
        return null;
    }

    public List<Ban> getBanTheoKhuVuc(String maKhuVuc) {
        return null;
    }

    public List<Ban> getBanTrong() {
        return null;
    }

    public List<Ban> getBanDangSuDung() {
        return null;
    }

    public boolean capNhatBan(Ban ban) {
        return false;
    }

    public boolean xoaBan(String maBan) {
        return false;
    }

    public boolean capNhatTrangThaiBan(String maBan, boolean trangThai) {
        return false;
    }
}
