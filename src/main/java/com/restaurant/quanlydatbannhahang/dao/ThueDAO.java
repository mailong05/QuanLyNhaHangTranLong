package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.Thue;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiThue;
import java.sql.Connection;
import java.util.List;

public class ThueDAO {
    private Connection connection;

    public ThueDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean themThue(Thue thue) {
        return false;
    }

    public Thue getThueTheoMa(String maThue) {
        return null;
    }

    public List<Thue> getAllThue() {
        return null;
    }

    public boolean capNhatThue(Thue thue) {
        return false;
    }

    public boolean capNhatThueSuat(String maThue, double thueSuatMoi) {
        return false;
    }

    public boolean xoaThue(String maThue) {
        return false;
    }

    public Thue getThueTheoTen(String tenThue) {
        return null;
    }

    public List<Thue> getThueTheoTrangThai(TrangThaiThue trangThai) {
        return null;
    }
}
