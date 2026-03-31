package com.restaurant.quanlydatbannhahang.entity;

public class MonAn {
    private String maMon;
    private String tenMon;
    private double donGia;
    private String donViTinh;
    private LoaiMonAn loaiMonAn;
    private boolean trangThai;
    private String urlHinhAnh;

    // Constructor không tham số
    public MonAn() {
    }

    // Constructor đầy đủ
    public MonAn(String maMon, String tenMon, double donGia, String donViTinh,
            LoaiMonAn loaiMonAn, boolean trangThai, String urlHinhAnh) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.donGia = donGia;
        this.donViTinh = donViTinh;
        this.loaiMonAn = loaiMonAn;
        this.trangThai = trangThai;
        this.urlHinhAnh = urlHinhAnh;
    }

    // Getter và Setter
    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public LoaiMonAn getLoaiMonAn() {
        return loaiMonAn;
    }

    public void setLoaiMonAn(LoaiMonAn loaiMonAn) {
        this.loaiMonAn = loaiMonAn;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getUrlHinhAnh() {
        return urlHinhAnh;
    }

    public void setUrlHinhAnh(String urlHinhAnh) {
        this.urlHinhAnh = urlHinhAnh;
    }

    // Các phương thức cần thiết
    public boolean kiemTraConHang() {
        return trangThai;
    }

    public boolean capNhatGia(double giaMoi) {
        if (giaMoi > 0) {
            this.donGia = giaMoi;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "MonAn{" +
                "maMon='" + maMon + '\'' +
                ", tenMon='" + tenMon + '\'' +
                ", donGia=" + donGia +
                ", donViTinh='" + donViTinh + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}
