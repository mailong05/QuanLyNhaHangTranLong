package com.restaurant.quanlydatbannhahang.entity;

public class KhachHang {
    private String maKH;
    private String hoTen;
    private String sdt;
    private int diemTichLuy;
    private LoaiThanhVien loaiThanhVien;

    // Constructor không tham số
    public KhachHang() {
    }

    // Constructor đầy đủ
    public KhachHang(String maKH, String hoTen, String sdt, int diemTichLuy, LoaiThanhVien loaiThanhVien) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.diemTichLuy = diemTichLuy;
        this.loaiThanhVien = loaiThanhVien;
    }

    // Getter và Setter
    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(int diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }

    public LoaiThanhVien getLoaiThanhVien() {
        return loaiThanhVien;
    }

    public void setLoaiThanhVien(LoaiThanhVien loaiThanhVien) {
        this.loaiThanhVien = loaiThanhVien;
    }

    // Các phương thức cần thiết
    public void tichDiem(double tongTien) {
        int diem = (int) (tongTien / 1000);
        this.diemTichLuy += diem;
    }

    public double layPhanTramGiamGia() {
        if (loaiThanhVien == LoaiThanhVien.VIP) {
            return 10.0;
        } else if (loaiThanhVien == LoaiThanhVien.VANG) {
            return 7.0;
        } else if (loaiThanhVien == LoaiThanhVien.BAC)
            return 5.0;
        return 0.0;
    }

    public boolean kiemTraThanhVienVIP() {
        return loaiThanhVien == LoaiThanhVien.VIP;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maKH == null) ? 0 : maKH.hashCode());
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
        KhachHang other = (KhachHang) obj;
        if (maKH == null) {
            if (other.maKH != null)
                return false;
        } else if (!maKH.equals(other.maKH))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "maKH='" + maKH + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", sdt='" + sdt + '\'' +
                ", diemTichLuy=" + diemTichLuy +
                ", loaiThanhVien='" + loaiThanhVien.getDisplayName() + '\'' +
                '}';
    }
}
