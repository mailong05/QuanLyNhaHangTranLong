package com.restaurant.quanlydatbannhahang.entity;

public class MonAn {
    private String maMon;
    private String tenMon;
    private double donGia;
    private String donViTinh;
    private LoaiMonAn tenLoai;
    private TrangThaiMonAn trangThai;
    private String urlHinhAnh;

    // Constructor không tham số
    public MonAn() {
    }

    // Constructor đầy đủ
    public MonAn(String maMon, String tenMon, double donGia, String donViTinh,
            LoaiMonAn tenLoai, TrangThaiMonAn trangThai, String urlHinhAnh) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.donGia = donGia;
        this.donViTinh = donViTinh;
        this.tenLoai = tenLoai;
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

    public LoaiMonAn getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(LoaiMonAn tenLoai) {
        this.tenLoai = tenLoai;
    }

    public TrangThaiMonAn getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiMonAn trangThai) {
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
        return trangThai == TrangThaiMonAn.CON;
    }

    public boolean capNhatGia(double giaMoi) {
        if (giaMoi > 0) {
            this.donGia = giaMoi;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maMon == null) ? 0 : maMon.hashCode());
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
        MonAn other = (MonAn) obj;
        if (maMon == null) {
            if (other.maMon != null)
                return false;
        } else if (!maMon.equals(other.maMon))
            return false;
        return true;
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
