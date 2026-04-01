package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.KhuVuc;
import java.sql.Connection;
import java.util.List;

public class KhuVucDAO {
    private Connection connection;

    public KhuVucDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean themKhuVuc(KhuVuc khuVuc) {
        return false;
    }

    public KhuVuc getKhuVucTheoMa(String maKhuVuc) {
        return null;
    }

    public List<KhuVuc> getAllKhuVuc() {
        return null;
    }

    public boolean capNhatKhuVuc(KhuVuc khuVuc) {
        return false;
    }

    public boolean xoaKhuVuc(String maKhuVuc) {
        return false;
    }

    public KhuVuc getKhuVucTheoTen(String tenKhuVuc) {
        return null;
    }
}
