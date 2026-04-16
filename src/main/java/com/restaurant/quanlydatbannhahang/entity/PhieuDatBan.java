package com.restaurant.quanlydatbannhahang.entity;

import java.time.LocalDateTime;

public class PhieuDatBan {
    private String maPhieuDat;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private Ban ban;
    private LocalDateTime thoiGianDen;
    private int soLuongNguoi;
    private String ghiChu;
    private TrangThaiPhieuDat trangThai;

    // Constructor không tham số
    public PhieuDatBan() {
    }

    // Constructor đầy đủ
    public PhieuDatBan(String maPhieuDat, KhachHang khachHang, NhanVien nhanVien, Ban ban, LocalDateTime thoiGianDen,
            int soLuongNguoi, String ghiChu, TrangThaiPhieuDat trangThai) {
        this.maPhieuDat = maPhieuDat;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.ban = ban;
        this.thoiGianDen = thoiGianDen;
        this.soLuongNguoi = soLuongNguoi;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    // Getter và Setter
    public String getMaPhieuDat() {
        return maPhieuDat;
    }

    public void setMaPhieuDat(String maPhieuDat) {
        this.maPhieuDat = maPhieuDat;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
    }

    public LocalDateTime getThoiGianDen() {
        return thoiGianDen;
    }

    public void setThoiGianDen(LocalDateTime thoiGianDen) {
        this.thoiGianDen = thoiGianDen;
    }

    public int getSoLuongNguoi() {
        return soLuongNguoi;
    }

    public void setSoLuongNguoi(int soLuongNguoi) {
        this.soLuongNguoi = soLuongNguoi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public TrangThaiPhieuDat getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiPhieuDat trangThai) {
        this.trangThai = trangThai;
    }

    // Các phương thức cần thiết
    public boolean taoPhieuDat() {
        if (khachHang != null && ban != null && ban.kiemTraBanTrong()) {
            this.trangThai = TrangThaiPhieuDat.DANG_CHO;
            return true;
        }
        return false;
    }

   

    public boolean huyPhieu() {
        if (trangThai != TrangThaiPhieuDat.DA_HUY) {
            this.trangThai = TrangThaiPhieuDat.DA_HUY;
            if (ban != null) {
                ban.huyDat();
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maPhieuDat == null) ? 0 : maPhieuDat.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PhieuDatBan other = (PhieuDatBan) obj;
        if (maPhieuDat == null) {
            if (other.maPhieuDat != null)
                return false;
        } else if (!maPhieuDat.equals(other.maPhieuDat))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PhieuDatBan{" +
                "maPhieuDat='" + maPhieuDat + '\'' +
                ", khachHang=" + (khachHang != null ? khachHang.getMaKH() : "null") +
                ", nhanVien=" + (nhanVien != null ? nhanVien.getMaNV() : "null") +
                ", ban=" + (ban != null ? ban.getMaBan() : "null") +
                ", thoiGianDen=" + thoiGianDen +
                ", soLuongNguoi=" + soLuongNguoi +
                ", trangThai=" + trangThai +
                '}';
    }
}