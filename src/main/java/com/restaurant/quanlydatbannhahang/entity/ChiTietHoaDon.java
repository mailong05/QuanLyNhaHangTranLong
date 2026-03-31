package com.restaurant.quanlydatbannhahang.entity;

public class ChiTietHoaDon {
    private String maHD;
    private String maMonAn;
    private int soLuong;
    private double donGia;
    private double thanhTien;

    // Constructor không tham số
    public ChiTietHoaDon() {
    }

    // Constructor đầy đủ
    public ChiTietHoaDon(String maHD, String maMonAn, int soLuong, double donGia, double thanhTien) {
        this.maHD = maHD;
        this.maMonAn = maMonAn;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    // Getter và Setter
    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaMonAn() {
        return maMonAn;
    }

    public void setMaMonAn(String maMonAn) {
        this.maMonAn = maMonAn;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    // Các phương thức cần thiết
    public void tinhThanhTien() {
        this.thanhTien = soLuong * donGia;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maHD == null) ? 0 : maHD.hashCode());
        result = prime * result + ((maMonAn == null) ? 0 : maMonAn.hashCode());
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
        ChiTietHoaDon other = (ChiTietHoaDon) obj;
        if (maHD == null) {
            if (other.maHD != null)
                return false;
        } else if (!maHD.equals(other.maHD))
            return false;
        if (maMonAn == null) {
            if (other.maMonAn != null)
                return false;
        } else if (!maMonAn.equals(other.maMonAn))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                "maHD='" + maHD + '\'' +
                ", maMonAn='" + maMonAn + '\'' +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", thanhTien=" + thanhTien +
                '}';
    }
}
