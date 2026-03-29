package com.restaurant.quanlydatbannhahang.entity;

public class TaiKhoan {
    private String username;
    private String password;
    private String maNV;
    private String quyenHan;
    private NhanVien nhanVien; // Để lưu thông tin nhân viên

    // Constructor không tham số
    public TaiKhoan() {
    }

    // Constructor đầy đủ
    public TaiKhoan(String username, String password, String maNV, String quyenHan) {
        this.username = username;
        this.password = password;
        this.maNV = maNV;
        this.quyenHan = quyenHan;
    }

    // Getter và Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getQuyenHan() {
        return quyenHan;
    }

    public void setQuyenHan(String quyenHan) {
        this.quyenHan = quyenHan;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "username='" + username + '\'' +
                ", maNV='" + maNV + '\'' +
                ", quyenHan='" + quyenHan + '\'' +
                '}';
    }
}
