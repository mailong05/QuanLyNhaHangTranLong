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
