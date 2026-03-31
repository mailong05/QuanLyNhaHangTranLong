package com.restaurant.quanlydatbannhahang.entity;

public class TaiKhoan {
    private String username;
    private String password;
    private NhanVien nhanVien;
    private String quyenHan;

    // Constructor không tham số
    public TaiKhoan() {
    }

    // Constructor đầy đủ
    public TaiKhoan(String username, String password, NhanVien nhanVien, String quyenHan) {
        this.username = username;
        this.password = password;
        this.nhanVien = nhanVien;
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

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getQuyenHan() {
        return quyenHan;
    }

    public void setQuyenHan(String quyenHan) {
        this.quyenHan = quyenHan;
    }

    // Các phương thức cần thiết
    public boolean kiemTraDangNhap() {
        return username != null && !username.isEmpty() && password != null && !password.isEmpty();
    }

    @Override
    public String toString() {
        return "TaiKhoan [username=" + username + ", password=" + password + ", nhanVien=" + nhanVien + ", quyenHan="
                + quyenHan + "]";
    }

}
