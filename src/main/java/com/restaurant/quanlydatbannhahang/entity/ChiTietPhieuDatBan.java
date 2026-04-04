package com.restaurant.quanlydatbannhahang.entity;

public class ChiTietPhieuDatBan {
    private PhieuDatBan phieuDatBan;
    private Ban ban;
    private String ghiChu;

    // Constructor không tham số
    public ChiTietPhieuDatBan() {
    }

    // Constructor đầy đủ
    public ChiTietPhieuDatBan(PhieuDatBan phieuDatBan, Ban ban, String ghiChu) {
        this.phieuDatBan = phieuDatBan;
        this.ban = ban;
        this.ghiChu = ghiChu;
    }

    // Getter và Setter
    public PhieuDatBan getPhieuDatBan() {
        return phieuDatBan;
    }

    public void setPhieuDatBan(PhieuDatBan phieuDatBan) {
        this.phieuDatBan = phieuDatBan;
    }

    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((phieuDatBan == null) ? 0 : phieuDatBan.hashCode());
        result = prime * result + ((ban == null) ? 0 : ban.hashCode());
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
        ChiTietPhieuDatBan other = (ChiTietPhieuDatBan) obj;
        if (phieuDatBan == null) {
            if (other.phieuDatBan != null)
                return false;
        } else if (!phieuDatBan.equals(other.phieuDatBan))
            return false;
        if (ban == null) {
            if (other.ban != null)
                return false;
        } else if (!ban.equals(other.ban))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ChiTietPhieuDatBan{" +
                "phieuDatBan=" + (phieuDatBan != null ? phieuDatBan.getMaPhieuDat() : "null") +
                ", ban=" + (ban != null ? ban.getMaBan() : "null") +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }
}
