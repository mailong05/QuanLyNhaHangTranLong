package com.restaurant.quanlydatbannhahang.entity;

public class Thue {
    private String maThue;
    private String tenThue;
    private double thueSuat;

    // Constructor không tham số
    public Thue() {
    }

    // Constructor đầy đủ
    public Thue(String maThue, String tenThue, double thueSuat) {
        this.maThue = maThue;
        this.tenThue = tenThue;
        this.thueSuat = thueSuat;
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

    // Các phương thức cần thiết
    public boolean updateThueSuat(double suatMoi) {
        if (suatMoi >= 0 && suatMoi <= 100) {
            this.thueSuat = suatMoi;
            return true;
        }
        return false;
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
