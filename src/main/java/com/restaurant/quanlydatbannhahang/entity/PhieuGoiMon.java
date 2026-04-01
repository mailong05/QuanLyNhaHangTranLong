package com.restaurant.quanlydatbannhahang.entity;

import java.time.LocalTime;

public class PhieuGoiMon {
    private String maPhieuGoi;
    private Ban ban;
    private NhanVien nhanVien;
    private LocalTime gioVao;
    private LocalTime gioRa;
    private double tongTienTamTinh;

    // Constructor không tham số
    public PhieuGoiMon() {
    }

    // Constructor đầy đủ
    public PhieuGoiMon(String maPhieuGoi, Ban ban, NhanVien nhanVien, LocalTime gioVao,
            LocalTime gioRa, double tongTienTamTinh) {
        this.maPhieuGoi = maPhieuGoi;
        this.ban = ban;
        this.nhanVien = nhanVien;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
        this.tongTienTamTinh = tongTienTamTinh;
    }

    // Getter và Setter
    public String getMaPhieuGoi() {
        return maPhieuGoi;
    }

    public void setMaPhieuGoi(String maPhieuGoi) {
        this.maPhieuGoi = maPhieuGoi;
    }

    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public LocalTime getGioVao() {
        return gioVao;
    }

    public void setGioVao(LocalTime gioVao) {
        this.gioVao = gioVao;
    }

    public LocalTime getGioRa() {
        return gioRa;
    }

    public void setGioRa(LocalTime gioRa) {
        this.gioRa = gioRa;
    }

    public double getTongTienTamTinh() {
        return tongTienTamTinh;
    }

    public void setTongTienTamTinh(double tongTienTamTinh) {
        this.tongTienTamTinh = tongTienTamTinh;
    }

    // Các phương thức cần thiết
    public boolean taoPhieuMoi() {
        if (ban != null && nhanVien != null && gioVao != null) {
            return true;
        }
        return false;
    }

    public boolean capNhatPhieu() {
        if (maPhieuGoi != null && !maPhieuGoi.isEmpty()) {
            return true;
        }
        return false;
    }

    public void ketThucPhieu() {
        this.gioRa = LocalTime.now();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maPhieuGoi == null) ? 0 : maPhieuGoi.hashCode());
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
        PhieuGoiMon other = (PhieuGoiMon) obj;
        if (maPhieuGoi == null) {
            if (other.maPhieuGoi != null)
                return false;
        } else if (!maPhieuGoi.equals(other.maPhieuGoi))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PhieuGoiMon{" +
                "maPhieuGoi='" + maPhieuGoi + '\'' +
                ", gioVao=" + gioVao +
                ", tongTienTamTinh=" + tongTienTamTinh +
                '}';
    }
}
