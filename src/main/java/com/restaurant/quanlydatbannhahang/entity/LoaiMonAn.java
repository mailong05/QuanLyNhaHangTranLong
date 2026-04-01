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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maLoai == null) ? 0 : maLoai.hashCode());
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
        LoaiMonAn other = (LoaiMonAn) obj;
        if (maLoai == null) {
            if (other.maLoai != null)
                return false;
        } else if (!maLoai.equals(other.maLoai))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LoaiMonAn{" +
                "maLoai='" + maLoai + '\'' +
                ", tenLoai='" + tenLoai + '\'' +
                '}';
    }
}
