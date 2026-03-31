package com.restaurant.quanlydatbannhahang.entity;

public class ChiTietGoiMon {
    private String maPhieuGoi;
    private MonAn monAn;
    private int soLuong;
    private double donGiaLuuTru;
    private double thanhTien;
    private String ghiChu;

    // Constructor không tham số
    public ChiTietGoiMon() {
    }

    // Constructor đầy đủ
    public ChiTietGoiMon(String maPhieuGoi, MonAn monAn, int soLuong, double donGiaLuuTru,
            double thanhTien, String ghiChu) {
        this.maPhieuGoi = maPhieuGoi;
        this.monAn = monAn;
        this.soLuong = soLuong;
        this.donGiaLuuTru = donGiaLuuTru;
        this.thanhTien = thanhTien;
        this.ghiChu = ghiChu;
    }

    // Getter và Setter
    public String getMaPhieuGoi() {
        return maPhieuGoi;
    }

    public void setMaPhieuGoi(String maPhieuGoi) {
        this.maPhieuGoi = maPhieuGoi;
    }

    public MonAn getMonAn() {
        return monAn;
    }

    public void setMonAn(MonAn monAn) {
        this.monAn = monAn;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGiaLuuTru() {
        return donGiaLuuTru;
    }

    public void setDonGiaLuuTru(double donGiaLuuTru) {
        this.donGiaLuuTru = donGiaLuuTru;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    // Các phương thức cần thiết
    public double tinhThanhTien() {
        this.thanhTien = soLuong * donGiaLuuTru;
        return thanhTien;
    }

    public boolean tangSoLuong(int n) {
        if (n > 0) {
            this.soLuong += n;
            tinhThanhTien();
            return true;
        }
        return false;
    }

    public boolean giamSoLuong(int n) {
        if (n > 0 && soLuong - n > 0) {
            this.soLuong -= n;
            tinhThanhTien();
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maPhieuGoi == null) ? 0 : maPhieuGoi.hashCode());
        result = prime * result + ((monAn == null) ? 0 : monAn.hashCode());
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
        ChiTietGoiMon other = (ChiTietGoiMon) obj;
        if (maPhieuGoi == null) {
            if (other.maPhieuGoi != null)
                return false;
        } else if (!maPhieuGoi.equals(other.maPhieuGoi))
            return false;
        if (monAn == null) {
            if (other.monAn != null)
                return false;
        } else if (!monAn.equals(other.monAn))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ChiTietGoiMon{" +
                "maPhieuGoi='" + maPhieuGoi + '\'' +
                ", soLuong=" + soLuong +
                ", donGiaLuuTru=" + donGiaLuuTru +
                ", thanhTien=" + thanhTien +
                '}';
    }
}
