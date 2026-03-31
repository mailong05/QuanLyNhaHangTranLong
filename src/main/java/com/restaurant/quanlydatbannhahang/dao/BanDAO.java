package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import java.sql.Connection;
import java.util.List;

public class BanDAO {
    private Connection connection;

    public BanDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean themBan(Ban ban) {
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
