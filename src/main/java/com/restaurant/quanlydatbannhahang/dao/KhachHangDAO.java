package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.KhachHang;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class KhachHangDAO {
    private Connection connection;

    public KhachHangDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean themKhachHang(KhachHang kh) {
        return false;
    }

    public KhachHang getKhachHangTheoMa(String maKH) {
        return null;
    }

    public KhachHang getKhachHangTheoSDT(String sdt) {
        return null;
    }

    public List<KhachHang> getAllKhachHang() {
        return null;
    }

    public boolean capNhatKhachHang(KhachHang kh) {
        return false;
    }

    public boolean xoaKhachHang(String maKH) {
        return false;
    }

    public List<KhachHang> timKhachHangTheoTen(String hoTen) {
        return null;
    }

    public List<KhachHang> getKhachHangVIP() {
        return null;
    }
}
