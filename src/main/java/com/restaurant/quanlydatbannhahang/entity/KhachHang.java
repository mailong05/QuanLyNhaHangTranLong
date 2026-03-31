package com.restaurant.quanlydatbannhahang.entity;

public class KhachHang {
    private String maKH;
    private String hoTen;
    private String sdt;
    private int diemTichLuy;
    private String loaiThanhVien;

    // Constructor không tham số
    public KhachHang() {
    }

    // Constructor đầy đủ
    public KhachHang(String maKH, String hoTen, String sdt, int diemTichLuy, String loaiThanhVien) {
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

    public String getLoaiThanhVien() {
        return loaiThanhVien;
    }

    public void setLoaiThanhVien(String loaiThanhVien) {
        this.loaiThanhVien = loaiThanhVien;
    }

    // Các phương thức cần thiết
    public void tichDiem(double tongTien) {
        int diem = (int) (tongTien / 1000);
        this.diemTichLuy += diem;
    }

    public double layPhanTramGiamGia() {
        if ("VIP".equals(loaiThanhVien)) {
            return 15.0;
        } else if ("Thường xuyên".equals(loaiThanhVien)) {
            return 10.0;
        }
        return 0.0;
    }

    public boolean kiemTraThanhVienVIP() {
        return "VIP".equals(loaiThanhVien);
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "maKH='" + maKH + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", sdt='" + sdt + '\'' +
                ", diemTichLuy=" + diemTichLuy +
                ", loaiThanhVien='" + loaiThanhVien + '\'' +
                '}';
    }
}
