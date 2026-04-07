package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.KhuyenMai;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiKhuyenMai;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class KhuyenMaiDAO {
    private Connection connection;

    public KhuyenMaiDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean themKhuyenMai(KhuyenMai km) {
        return false;
    }

    public KhuyenMai getKhuyenMaiTheoMa(String maKM) {
        return null;
    }

    public List<KhuyenMai> getAllKhuyenMai() {
        return null;
    }

    public List<KhuyenMai> getKhuyenMaiConHieuLuc() {
        return null;
    }

    public List<KhuyenMai> getKhuyenMaiConHieuLuc(LocalDate ngay) {
        return null;
    }

    public boolean capNhatKhuyenMai(KhuyenMai km) {
        return false;
    }

    public boolean xoaKhuyenMai(String maKM) {
        return false;
    }

    public List<KhuyenMai> timKhuyenMaiTheoTen(String tenKM) {
        return null;
    }

    public List<KhuyenMai> getKhuyenMaiTheoTrangThai(TrangThaiKhuyenMai trangThai) {
        return null;
    }

    public List<KhuyenMai> getKhuyenMaiTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        return null;
    }
}
