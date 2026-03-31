package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.MonAn;
import java.sql.Connection;
import java.util.List;

public class MonAnDAO {
    private Connection connection;

    public MonAnDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean themMonAn(MonAn monAn) {
        return false;
    }

    public MonAn getMonAnTheoMa(String maMon) {
        return null;
    }

    public List<MonAn> getAllMonAn() {
        return null;
    }

    public List<MonAn> getMonAnTheoLoai(String maLoai) {
        return null;
    }

    public List<MonAn> getMonAnConHang() {
        return null;
    }

    public boolean capNhatMonAn(MonAn monAn) {
        return false;
    }

    public boolean capNhatGiaMonAn(String maMon, double giaMoi) {
        return false;
    }

    public boolean capNhatTrangThaiMonAn(String maMon, boolean trangThai) {
        return false;
    }

    public boolean xoaMonAn(String maMon) {
        return false;
    }

    public List<MonAn> timMonAnTheoTen(String tenMon) {
        return null;
    }
}
