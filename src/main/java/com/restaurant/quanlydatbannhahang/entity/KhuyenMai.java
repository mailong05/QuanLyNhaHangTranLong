package com.restaurant.quanlydatbannhahang.entity;

import java.time.LocalDate;

public class KhuyenMai {
    private String maKM;
    private String tenKM;
    private double giaTriGiam;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private double dieuKienToiThieu;

    // Constructor không tham số
    public KhuyenMai() {
    }

    // Constructor đầy đủ
    public KhuyenMai(String maKM, String tenKM, double giaTriGiam,
            LocalDate ngayBatDau, LocalDate ngayKetThuc, double dieuKienToiThieu) {
        this.maKM = maKM;
        this.tenKM = tenKM;
        this.giaTriGiam = giaTriGiam;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.dieuKienToiThieu = dieuKienToiThieu;
    }

    // Getter và Setter
    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    public double getGiaTriGiam() {
        return giaTriGiam;
    }

    public void setGiaTriGiam(double giaTriGiam) {
        this.giaTriGiam = giaTriGiam;
    }

    public double getDieuKienToiThieu() {
        return dieuKienToiThieu;
    }

    public void setDieuKienToiThieu(double dieuKienToiThieu) {
        this.dieuKienToiThieu = dieuKienToiThieu;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    // Các phương thức cần thiết
    public boolean kiemTraHieuLuc(LocalDate ngayHienTai) {
        return !ngayHienTai.isBefore(ngayBatDau) && !ngayHienTai.isAfter(ngayKetThuc);
    }

    public double tinhSoTienGiam(double tongTien) {
        if (tongTien >= dieuKienToiThieu) {
            return giaTriGiam;
        }
        return 0.0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maKM == null) ? 0 : maKM.hashCode());
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
        KhuyenMai other = (KhuyenMai) obj;
        if (maKM == null) {
            if (other.maKM != null)
                return false;
        } else if (!maKM.equals(other.maKM))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "KhuyenMai{" +
                "maKM='" + maKM + '\'' +
                ", tenKM='" + tenKM + '\'' +
                ", giaTriGiam=" + giaTriGiam +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                '}';
    }
}
