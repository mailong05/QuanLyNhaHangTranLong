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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maKhuVuc == null) ? 0 : maKhuVuc.hashCode());
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
        KhuVuc other = (KhuVuc) obj;
        if (maKhuVuc == null) {
            if (other.maKhuVuc != null)
                return false;
        } else if (!maKhuVuc.equals(other.maKhuVuc))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "KhuVuc{" +
                "maKhuVuc='" + maKhuVuc + '\'' +
                ", tenKhuVuc='" + tenKhuVuc + '\'' +
                '}';
    }
}
