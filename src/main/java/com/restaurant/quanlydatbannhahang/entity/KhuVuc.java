package com.restaurant.quanlydatbannhahang.entity;

public class KhuVuc {
    private String maKhuVuc;
    private String tenKhuVuc;

    // Constructor không tham số
    public KhuVuc() {
    }

    // Constructor đầy đủ
    public KhuVuc(String maKhuVuc, String tenKhuVuc) {
        this.maKhuVuc = maKhuVuc;
        this.tenKhuVuc = tenKhuVuc;
    }

    // Getter và Setter
    public String getMaKhuVuc() {
        return maKhuVuc;
    }

    public void setMaKhuVuc(String maKhuVuc) {
        this.maKhuVuc = maKhuVuc;
    }

    public String getTenKhuVuc() {
        return tenKhuVuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
    }

    // Các phương thức cần thiết
    public void themKhuVuc() {
        // Logic thêm khu vực vào database
    }

    public void capNhatKhuVuc() {
        // Logic cập nhật khu vực trong database
    }

    @Override
    public String toString() {
        return "KhuVuc{" +
                "maKhuVuc='" + maKhuVuc + '\'' +
                ", tenKhuVuc='" + tenKhuVuc + '\'' +
                '}';
    }
}
