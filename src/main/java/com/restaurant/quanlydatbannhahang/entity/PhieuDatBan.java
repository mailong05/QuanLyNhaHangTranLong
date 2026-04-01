package com.restaurant.quanlydatbannhahang.entity;

import java.time.LocalDate;

public class PhieuDatBan {
    private String maPhieuDat;
    private KhachHang khachHang;
    private Ban ban;
    private LocalDate thoiGianDen;
    private int soLuongNguoi;
    private String ghiChu;
    private String trangThai;

    // Constructor không tham số
    public PhieuDatBan() {
    }

    // Constructor đầy đủ
    public PhieuDatBan(String maPhieuDat, KhachHang khachHang, Ban ban, LocalDate thoiGianDen,
            int soLuongNguoi, String ghiChu, String trangThai) {
        this.maPhieuDat = maPhieuDat;
        this.khachHang = khachHang;
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

    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
    }

    public LocalDate getThoiGianDen() {
        return thoiGianDen;
    }

    public void setThoiGianDen(LocalDate thoiGianDen) {
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

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    // Các phương thức cần thiết
    public boolean taoPhieuDat() {
        if (khachHang != null && ban != null && ban.kiemTraBanTrong()) {
            this.trangThai = "Chờ xác nhận";
            return true;
        }
        return false;
    }

    public boolean xacNhanDen() {
        if ("Chờ xác nhận".equals(trangThai)) {
            this.trangThai = "Đã xác nhận";
            ban.datBan();
            return true;
        }
        return false;
    }

    public boolean huyPhieu() {
        if (!"Đã hủy".equals(trangThai)) {
            this.trangThai = "Đã hủy";
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
                ", thoiGianDen=" + thoiGianDen +
                ", soLuongNguoi=" + soLuongNguoi +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
