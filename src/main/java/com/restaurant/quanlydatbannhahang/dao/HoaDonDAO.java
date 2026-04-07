package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.HoaDon;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiHoaDon;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class HoaDonDAO {
    private Connection connection;

    public HoaDonDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean themHoaDon(HoaDon hd) {
        return false;
    }

    public HoaDon getHoaDonTheoMa(String maHD) {
        return null;
    }

    public List<HoaDon> getAllHoaDon() {
        return null;
    }

    public List<HoaDon> getHoaDonTheoNgay(LocalDate ngay) {
        return null;
    }

    public List<HoaDon> getHoaDonTrongKhoangThoi(LocalDate tuNgay, LocalDate denNgay) {
        return null;
    }

    public List<HoaDon> getHoaDonTheoNhanVien(String maNV) {
        return null;
    }

    public List<HoaDon> getHoaDonTheoKhachHang(String maKH) {
        return null;
    }

    public boolean capNhatHoaDon(HoaDon hd) {
        return false;
    }

    public boolean xoaHoaDon(String maHD) {
        return false;
    }

    public double tinhTongDoanhThu() {
        return 0;
    }

    public double tinhTongDoanhThuTheoNgay(LocalDate ngay) {
        return 0;
    }

    public double tinhTongDoanhThuTheoGian(LocalDate tuNgay, LocalDate denNgay) {
        return 0;
    }

    public List<HoaDon> getHoaDonTheoMaBan(String maBan) {
        return null;
    }

    public List<HoaDon> getHoaDonTheoTrangThai(TrangThaiHoaDon trangThai) {
        return null;
    }
}
