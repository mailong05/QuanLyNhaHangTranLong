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

    public boolean doiMatKhau(String passMoi) {
        if (passMoi != null && !passMoi.isEmpty()) {
            this.password = passMoi;
            return true;
        }
        return false;
    }

    public boolean resetMatKhau() {
        this.password = "123456"; // Mật khẩu mặc định
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
        TaiKhoan other = (TaiKhoan) obj;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TaiKhoan [username=" + username + ", password=" + password + ", nhanVien="
                + (nhanVien != null ? nhanVien.getMaNV() : "null") + ", quyenHan=" + quyenHan + "]";
    }

}
