package com.restaurant.quanlydatbannhahang.entity;

public class Thue {
    private String maThue;
    private String tenThue;
    private double thueSuat;
    private TrangThaiThue trangThai;

    // Constructor không tham số
    public Thue() {
    }

    // Constructor đầy đủ
    public Thue(String maThue, String tenThue, double thueSuat, TrangThaiThue trangThai) {
        this.maThue = maThue;
        this.tenThue = tenThue;
        this.thueSuat = thueSuat;
        this.trangThai = trangThai;
    }

    // Getter và Setter
    public String getMaThue() {
        return maThue;
    }

    public void setMaThue(String maThue) {
        this.maThue = maThue;
    }

    public String getTenThue() {
        return tenThue;
    }

    public void setTenThue(String tenThue) {
        this.tenThue = tenThue;
    }

    public double getThueSuat() {
        return thueSuat;
    }

    public void setThueSuat(double thueSuat) {
        this.thueSuat = thueSuat;
    }

    public TrangThaiThue getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiThue trangThai) {
        this.trangThai = trangThai;
    }

    // Các phương thức cần thiết
    public boolean updateThueSuat(double suatMoi) {
        if (suatMoi >= 0 && suatMoi <= 100) {
            this.thueSuat = suatMoi;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maThue == null) ? 0 : maThue.hashCode());
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
        Thue other = (Thue) obj;
        if (maThue == null) {
            if (other.maThue != null)
                return false;
        } else if (!maThue.equals(other.maThue))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Thue{" +
                "maThue='" + maThue + '\'' +
                ", tenThue='" + tenThue + '\'' +
                ", thueSuat=" + thueSuat +
                '}';
    }
}
