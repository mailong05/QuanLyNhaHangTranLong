package com.restaurant.quanlydatbannhahang.entity;

import java.time.LocalDate;

public class NhanVien {
    private String maNV;
    private String hoTen;
    private String sdt;
    private String chucVu;
    private LocalDate ngayVaoLam;
    private double luongCoBan;
    private boolean trangThai;

    // Constructor không tham số
    public NhanVien() {
    }

    // Constructor đầy đủ
    public NhanVien(String maNV, String hoTen, String sdt, String chucVu,
            LocalDate ngayVaoLam, double luongCoBan, boolean trangThai) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.chucVu = chucVu;
        this.ngayVaoLam = ngayVaoLam;
        this.luongCoBan = luongCoBan;
        this.trangThai = trangThai;
    }

    // Getter và Setter
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public LocalDate getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(LocalDate ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNV='" + maNV + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", chucVu='" + chucVu + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}
