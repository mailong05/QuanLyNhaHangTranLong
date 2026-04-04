package com.restaurant.quanlydatbannhahang.entity;

public class ChiTietHoaDon {
    private HoaDon hoaDon;
    private MonAn monAn;
    private int soLuong;
    private double donGiaLuuTru;
    private String ghiChu;

    // Constructor không tham số
    public ChiTietHoaDon() {
    }

    // Constructor đầy đủ
    public ChiTietHoaDon(HoaDon hoaDon, MonAn monAn, int soLuong, double donGiaLuuTru, String ghiChu) {
        this.hoaDon = hoaDon;
        this.monAn = monAn;
        this.soLuong = soLuong;
        this.donGiaLuuTru = donGiaLuuTru;
        this.ghiChu = ghiChu;
    }

    // Getter và Setter
    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
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

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    // Các phương thức cần thiết
    public double tinhThanhTien() {
        return soLuong * donGiaLuuTru;
    }

    public boolean tangSoLuong(int n) {
        if (n > 0) {
            this.soLuong += n;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((hoaDon == null) ? 0 : hoaDon.hashCode());
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
        ChiTietHoaDon other = (ChiTietHoaDon) obj;
        if (hoaDon == null) {
            if (other.hoaDon != null)
                return false;
        } else if (!hoaDon.equals(other.hoaDon))
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
        return "ChiTietHoaDon{" +
                "hoaDon=" + (hoaDon != null ? hoaDon.getMaHD() : "null") +
                ", monAn=" + (monAn != null ? monAn.getTenMon() : "null") +
                ", soLuong=" + soLuong +
                ", donGiaLuuTru=" + donGiaLuuTru +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }
}
