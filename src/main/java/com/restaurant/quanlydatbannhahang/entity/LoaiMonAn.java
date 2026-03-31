package com.restaurant.quanlydatbannhahang.entity;

public class LoaiMonAn {
    private String maLoai;
    private String tenLoai;

    // Constructor không tham số
    public LoaiMonAn() {
    }

    // Constructor đầy đủ
    public LoaiMonAn(String maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    // Getter và Setter
    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    @Override
    public String toString() {
        return "LoaiMonAn{" +
                "maLoai='" + maLoai + '\'' +
                ", tenLoai='" + tenLoai + '\'' +
                '}';
    }
}
