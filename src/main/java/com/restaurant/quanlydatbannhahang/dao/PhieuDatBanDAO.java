package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.PhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiPhieuDat;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class PhieuDatBanDAO {
    private Connection connection;

    public PhieuDatBanDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean themPhieuDatBan(PhieuDatBan phieu) {
        return false;
    }

    public PhieuDatBan getPhieuDatBanTheoMa(String maPhieuDat) {
        return null;
    }

    public List<PhieuDatBan> getAllPhieuDatBan() {
        return null;
    }

    public List<PhieuDatBan> getPhieuDatBanTheoNgay(LocalDate ngay) {
        return null;
    }

    public List<PhieuDatBan> getPhieuDatBanTheoKhachHang(String maKH) {
        return null;
    }

    public List<PhieuDatBan> getPhieuDatBanTheoBan(String maBan) {
        return null;
    }

    public List<PhieuDatBan> getPhieuDatBanTheoBan(String maBan, LocalDate ngay) {
        return null;
    }

    public boolean capNhatPhieuDatBan(PhieuDatBan phieu) {
        return false;
    }

    public boolean capNhatTrangThaiPhieu(String maPhieuDat, String trangThai) {
        return false;
    }

    public boolean xoaPhieuDatBan(String maPhieuDat) {
        return false;
    }

    public List<PhieuDatBan> getPhieuDatBanChoXacNhan() {
        return null;
    }

    public List<PhieuDatBan> getPhieuDatBanTheoTrangThai(TrangThaiPhieuDat trangThai) {
        return null;
    }
}
