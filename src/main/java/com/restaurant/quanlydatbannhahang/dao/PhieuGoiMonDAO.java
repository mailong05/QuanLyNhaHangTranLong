package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.PhieuGoiMon;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class PhieuGoiMonDAO {
    private Connection connection;

    public PhieuGoiMonDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean themPhieuGoiMon(PhieuGoiMon phieu) {
        return false;
    }

    public PhieuGoiMon getPhieuGoiMonTheoMa(String maPhieuGoi) {
        return null;
    }

    public List<PhieuGoiMon> getAllPhieuGoiMon() {
        return null;
    }

    public List<PhieuGoiMon> getPhieuGoiMonTheoBan(String maBan) {
        return null;
    }

    public List<PhieuGoiMon> getPhieuGoiMonTheoNhanVien(String maNV) {
        return null;
    }

    public List<PhieuGoiMon> getPhieuGoiMonTheoNgay(LocalDate ngay) {
        return null;
    }

    public List<PhieuGoiMon> getPhieuGoiMonDangMo() {
        return null;
    }

    public boolean capNhatPhieuGoiMon(PhieuGoiMon phieu) {
        return false;
    }

    public boolean capNhatTongTienTamTinh(String maPhieuGoi, double tongTien) {
        return false;
    }

    public boolean xoaPhieuGoiMon(String maPhieuGoi) {
        return false;
    }

    public boolean ketThucPhieu(String maPhieuGoi) {
        return false;
    }
}
