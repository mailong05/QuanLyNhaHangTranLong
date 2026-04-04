package com.restaurant.quanlydatbannhahang.entity;

public class Ban {
    private String maBan;
    private int soGhe;
    private String viTri;
    private KhuVuc khuVuc;
    private TrangThaiBan trangThai;

    // Constructor không tham số
    public Ban() {
    }

    // Constructor đầy đủ
    public Ban(String maBan, int soGhe, String viTri, KhuVuc khuVuc, TrangThaiBan trangThai) {
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

    public TrangThaiBan getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiBan trangThai) {
        this.trangThai = trangThai;
    }

    // Các phương thức cần thiết
    public boolean kiemTraBanTrong() {
        return trangThai == TrangThaiBan.TRONG;
    }

    public boolean datBan() {
        if (kiemTraBanTrong()) {
            this.trangThai = TrangThaiBan.DA_DAT;
            return true;
        }
        return false;
    }

    public boolean huyDat() {
        if (trangThai != TrangThaiBan.TRONG) {
            this.trangThai = TrangThaiBan.TRONG;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maBan == null) ? 0 : maBan.hashCode());
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
        Ban other = (Ban) obj;
        if (maBan == null) {
            if (other.maBan != null)
                return false;
        } else if (!maBan.equals(other.maBan))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Ban{" +
                "maBan='" + maBan + '\'' +
                ", soGhe=" + soGhe +
                ", viTri='" + viTri + '\'' +
                ", khuVuc=" + (khuVuc != null ? khuVuc.getMaKhuVuc() : "null") +
                ", trangThai=" + trangThai +
                '}';
    }
}
