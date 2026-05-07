package com.restaurant.quanlydatbannhahang.entity;

public class KhuVuc {
    private String maKhuVuc;
    private String tenKhuVuc;

    
    public KhuVuc() {
    }

    
    public KhuVuc(String maKhuVuc, String tenKhuVuc) {
        this.maKhuVuc = maKhuVuc;
        this.tenKhuVuc = tenKhuVuc;
    }

    
    public String getMaKhuVuc() {
        return maKhuVuc;
    }

    public void setMaKhuVuc(String maKhuVuc) {
        this.maKhuVuc = maKhuVuc;
    }

    public String getTenKhuVuc() {
        return tenKhuVuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
    }

    
    public void themKhuVuc() {
        
    }

    public void capNhatKhuVuc() {
        
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maKhuVuc == null) ? 0 : maKhuVuc.hashCode());
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
        KhuVuc other = (KhuVuc) obj;
        if (maKhuVuc == null) {
            if (other.maKhuVuc != null)
                return false;
        } else if (!maKhuVuc.equals(other.maKhuVuc))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "KhuVuc{" +
                "maKhuVuc='" + maKhuVuc + '\'' +
                ", tenKhuVuc='" + tenKhuVuc + '\'' +
                '}';
    }
}
