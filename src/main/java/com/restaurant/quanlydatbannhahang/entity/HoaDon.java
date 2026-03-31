package com.restaurant.quanlydatbannhahang.entity;

import java.time.LocalDate;

public class HoaDon {
    private String maHD;
    private PhieuGoiMon phieuGoiMon;
    private NhanVien nhanVien;
    private KhuyenMai khuyenMai;
    private Thue thue;
    private LocalDate ngayTao;
    private double tongTienGoc;
    private double tienGiamGia;
    private double tongThanhToan;
    private String phuongThucTT;

    // Constructor không tham số
    public HoaDon() {
    }

    // Constructor đầy đủ
    public HoaDon(String maHD, PhieuGoiMon phieuGoiMon, NhanVien nhanVien, KhuyenMai khuyenMai,
            Thue thue, LocalDate ngayTao, double tongTienGoc, double tienGiamGia,
            double tongThanhToan, String phuongThucTT) {
        this.maHD = maHD;
        this.phieuGoiMon = phieuGoiMon;
        this.nhanVien = nhanVien;
        this.khuyenMai = khuyenMai;
        this.thue = thue;
        this.ngayTao = ngayTao;
        this.tongTienGoc = tongTienGoc;
        this.tienGiamGia = tienGiamGia;
        this.tongThanhToan = tongThanhToan;
        this.phuongThucTT = phuongThucTT;
    }

    // Getter và Setter
    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public PhieuGoiMon getPhieuGoiMon() {
        return phieuGoiMon;
    }

    public void setPhieuGoiMon(PhieuGoiMon phieuGoiMon) {
        this.phieuGoiMon = phieuGoiMon;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public Thue getThue() {
        return thue;
    }

    public void setThue(Thue thue) {
        this.thue = thue;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }

    public double getTongTienGoc() {
        return tongTienGoc;
    }

    public void setTongTienGoc(double tongTienGoc) {
        this.tongTienGoc = tongTienGoc;
    }

    public double getTienGiamGia() {
        return tienGiamGia;
    }

    public void setTienGiamGia(double tienGiamGia) {
        this.tienGiamGia = tienGiamGia;
    }

    public double getTongThanhToan() {
        return tongThanhToan;
    }

    public void setTongThanhToan(double tongThanhToan) {
        this.tongThanhToan = tongThanhToan;
    }

    public String getPhuongThucTT() {
        return phuongThucTT;
    }

    public void setPhuongThucTT(String phuongThucTT) {
        this.phuongThucTT = phuongThucTT;
    }

    // Các phương thức cần thiết
    public double tinhTongThanhToan() {
        double thuePhaiTra = 0;
        if (thue != null) {
            thuePhaiTra = tongTienGoc * (thue.getThueSuat() / 100);
        }
        double giamGia = 0;
        if (khuyenMai != null && khuyenMai.kiemTraHieuLuc(ngayTao)) {
            giamGia = khuyenMai.tinhSoTienGiam(tongTienGoc);
            tienGiamGia = giamGia;
        }
        this.tongThanhToan = tongTienGoc + thuePhaiTra - giamGia;
        return tongThanhToan;
    }

    public void inHoaDon() {
        // Logic in hóa đơn
        System.out.println("In hóa đơn: " + maHD);
    }

    public void luuDoanhThu() {
        // Logic lưu doanh thu vào database
        System.out.println("Lưu doanh thu cho hóa đơn: " + maHD);
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHD='" + maHD + '\'' +
                ", ngayTao=" + ngayTao +
                ", tongTienGoc=" + tongTienGoc +
                ", tongThanhToan=" + tongThanhToan +
                '}';
    }
}
