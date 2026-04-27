package com.restaurant.quanlydatbannhahang.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class HoaDon {
    private String maHD;
    private Ban ban;
    private NhanVien nhanVien;
    private KhuyenMai khuyenMai;
    private Thue thue;
    private double thueSuat;
    private double tienThue;
    private double tyLePhiDV;
    private double tienPhiDV;
    private LocalDate ngayTao;
    private LocalTime gioVao;
    private LocalTime gioRa;
    private double tongTienGoc;
    private double tienGiamGia;
    private double tongThanhToan;
    private PhuongThucTT phuongThucTT;
    private TrangThaiHoaDon trangThaiThanhToan;

    // Constructor không tham số
    public HoaDon() {
    }

    // Constructor đầy đủ tham số
    public HoaDon(String maHD, Ban ban, NhanVien nhanVien, KhuyenMai khuyenMai, Thue thue,
            LocalDate ngayTao, LocalTime gioVao, LocalTime gioRa,
            double tongTienGoc, double tienGiamGia, double tongThanhToan,
            PhuongThucTT phuongThucTT, TrangThaiHoaDon trangThaiThanhToan) {
        this.maHD = maHD;
        this.ban = ban;
        this.nhanVien = nhanVien;
        this.khuyenMai = khuyenMai;
        this.thue = thue;
        this.thueSuat = 0;
        this.tienThue = 0;
        this.tyLePhiDV = 0;
        this.tienPhiDV = 0;
        this.ngayTao = ngayTao;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
        this.tongTienGoc = tongTienGoc;
        this.tienGiamGia = tienGiamGia;
        this.tongThanhToan = tongThanhToan;
        this.phuongThucTT = phuongThucTT;
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

    public HoaDon(String maHD, Ban ban, NhanVien nhanVien, KhuyenMai khuyenMai, Thue thue,
            double thueSuat, double tienThue, double tyLePhiDV, double tienPhiDV,
            LocalDate ngayTao, LocalTime gioVao, LocalTime gioRa,
            double tongTienGoc, double tienGiamGia, double tongThanhToan,
            PhuongThucTT phuongThucTT, TrangThaiHoaDon trangThaiThanhToan) {
        this(maHD, ban, nhanVien, khuyenMai, thue,
                ngayTao, gioVao, gioRa,
                tongTienGoc, tienGiamGia, tongThanhToan,
                phuongThucTT, trangThaiThanhToan);
        this.thueSuat = thueSuat;
        this.tienThue = tienThue;
        this.tyLePhiDV = tyLePhiDV;
        this.tienPhiDV = tienPhiDV;
    }

    // Getter và Setter
    public String getMaHD() {
        return maHD;
    }

    public Ban getBan() {
        return ban;
    }

    public void setMaHD(String maHD) {
		this.maHD = maHD;
	}

	public void setBan(Ban ban) {
        this.ban = ban;
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

    public double getThueSuat() {
        return thueSuat;
    }

    public void setThueSuat(double thueSuat) {
        this.thueSuat = thueSuat;
    }

    public double getTienThue() {
        return tienThue;
    }

    public void setTienThue(double tienThue) {
        this.tienThue = tienThue;
    }

    public double getTyLePhiDV() {
        return tyLePhiDV;
    }

    public void setTyLePhiDV(double tyLePhiDV) {
        this.tyLePhiDV = tyLePhiDV;
    }

    public double getTienPhiDV() {
        return tienPhiDV;
    }

    public void setTienPhiDV(double tienPhiDV) {
        this.tienPhiDV = tienPhiDV;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }

    public LocalTime getGioVao() {
        return gioVao;
    }

    public void setGioVao(LocalTime gioVao) {
        this.gioVao = gioVao;
    }

    public LocalTime getGioRa() {
        return gioRa;
    }

    public void setGioRa(LocalTime gioRa) {
        this.gioRa = gioRa;
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

    public PhuongThucTT getPhuongThucTT() {
        return phuongThucTT;
    }

    public void setPhuongThucTT(PhuongThucTT phuongThucTT) {
        this.phuongThucTT = phuongThucTT;
    }

    public TrangThaiHoaDon getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }

    public void setTrangThaiThanhToan(TrangThaiHoaDon trangThaiThanhToan) {
        this.trangThaiThanhToan = trangThaiThanhToan;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maHD == null) ? 0 : maHD.hashCode());
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
        HoaDon other = (HoaDon) obj;
        if (maHD == null) {
            if (other.maHD != null)
                return false;
        } else if (!maHD.equals(other.maHD))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHD='" + maHD + '\'' +
                ", ban=" + (ban != null ? ban.getMaBan() : "null") +
                ", nhanVien=" + (nhanVien != null ? nhanVien.getMaNV() : "null") +
                ", ngayTao=" + ngayTao +
                ", gioVao=" + gioVao +
                ", gioRa=" + gioRa +
                ", thueSuat=" + thueSuat +
                ", tienThue=" + tienThue +
                ", tyLePhiDV=" + tyLePhiDV +
                ", tienPhiDV=" + tienPhiDV +
                ", tongTienGoc=" + tongTienGoc +
                ", tienGiamGia=" + tienGiamGia +
                ", tongThanhToan=" + tongThanhToan +
                ", phuongThucTT=" + phuongThucTT +
                ", trangThaiThanhToan=" + trangThaiThanhToan +
                '}';
    }
}
