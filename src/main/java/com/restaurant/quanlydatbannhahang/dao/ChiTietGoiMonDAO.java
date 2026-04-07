package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.ChiTietGoiMon;
import java.sql.Connection;
import java.util.List;

public class ChiTietGoiMonDAO {
    private Connection connection;

    public ChiTietGoiMonDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean themChiTietGoiMon(ChiTietGoiMon ct) {
        return false;
    }

    public ChiTietGoiMon getChiTietGoiMonTheoMa(String maPhieuGoi, String maMon) {
        return null;
    }

    public List<ChiTietGoiMon> getChiTietGoiMonByPhieu(String maPhieuGoi) {
        return null;
    }

    public List<ChiTietGoiMon> getChiTietGoiMonByMon(String maMon) {
        return null;
    }

    public boolean capNhatChiTietGoiMon(ChiTietGoiMon ct) {
        return false;
    }

    public boolean capNhatTrangThaiChiTietGoiMon(String maPhieu, String maMon, String trangThai) {
        return false;
    }

    public boolean capNhatSoLuong(String maPhieuGoi, String maMon, int soLuongMoi) {
        return false;
    }

    public boolean xoaChiTietGoiMon(String maPhieuGoi, String maMon) {
        return false;
    }

    public boolean xoaAllChiTietByMaPhieu(String maPhieu) {
        return false;
    }

    public double tinhTongTienPhieu(String maPhieuGoi) {
        return 0;
    }
}
