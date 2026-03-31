package com.restaurant.quanlydatbannhahang.entity;

public class Ban {
    private String maBan;
    private int soGhe;
    private String viTri;
    private KhuVuc khuVuc;
    private boolean trangThai;

    // Constructor không tham số
    public Ban() {
    }

    // Constructor đầy đủ
    public Ban(String maBan, int soGhe, String viTri, KhuVuc khuVuc, boolean trangThai) {
        this.maBan = maBan;
        this.soGhe = soGhe;
        this.viTri = viTri;
        this.khuVuc = khuVuc;
        this.trangThai = trangThai;
    }

    // Getter và Setter
    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public int getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(int soGhe) {
        this.soGhe = soGhe;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public KhuVuc getKhuVuc() {
        return khuVuc;
    }

    public void setKhuVuc(KhuVuc khuVuc) {
        this.khuVuc = khuVuc;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    // Các phương thức cần thiết
    public boolean kiemTraBanTrong() {
        return !trangThai;
    }

    public boolean datBan() {
        if (kiemTraBanTrong()) {
            this.trangThai = true;
            return true;
        }
        return false;
    }

    public boolean huyDat() {
        if (trangThai) {
            this.trangThai = false;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Ban{" +
                "maBan='" + maBan + '\'' +
                ", soGhe=" + soGhe +
                ", viTri='" + viTri + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}
