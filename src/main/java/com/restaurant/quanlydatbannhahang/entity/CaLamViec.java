package com.restaurant.quanlydatbannhahang.entity;

import java.time.LocalDateTime;

public class CaLamViec {
    private String maCa;
    private NhanVien nhanVien;
    private LocalDateTime thoiGianVaoCa;
    private LocalDateTime thoiGianKetCa;
    private double tienDauCa;
    private Double tienKetCa;
    private TrangThaiCa trangThai;
    private String ghiChu;

    public CaLamViec() {
    }

    public CaLamViec(String maCa, NhanVien nhanVien, LocalDateTime thoiGianVaoCa, LocalDateTime thoiGianKetCa,
            double tienDauCa, Double tienKetCa, TrangThaiCa trangThai, String ghiChu) {
        this.maCa = maCa;
        this.nhanVien = nhanVien;
        this.thoiGianVaoCa = thoiGianVaoCa;
        this.thoiGianKetCa = thoiGianKetCa;
        this.tienDauCa = tienDauCa;
        this.tienKetCa = tienKetCa;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    public String getMaCa() {
        return maCa;
    }

    public void setMaCa(String maCa) {
        this.maCa = maCa;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public LocalDateTime getThoiGianVaoCa() {
        return thoiGianVaoCa;
    }

    public void setThoiGianVaoCa(LocalDateTime thoiGianVaoCa) {
        this.thoiGianVaoCa = thoiGianVaoCa;
    }

    public LocalDateTime getThoiGianKetCa() {
        return thoiGianKetCa;
    }

    public void setThoiGianKetCa(LocalDateTime thoiGianKetCa) {
        this.thoiGianKetCa = thoiGianKetCa;
    }

    public double getTienDauCa() {
        return tienDauCa;
    }

    public void setTienDauCa(double tienDauCa) {
        this.tienDauCa = tienDauCa;
    }

    public Double getTienKetCa() {
        return tienKetCa;
    }

    public void setTienKetCa(Double tienKetCa) {
        this.tienKetCa = tienKetCa;
    }

    public TrangThaiCa getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiCa trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}