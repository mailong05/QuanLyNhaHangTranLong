package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.KhuyenMaiDAO;
import com.restaurant.quanlydatbannhahang.entity.KhuyenMai;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiKhuyenMai;

import java.time.LocalDate;
import java.util.List;

public class KhuyenMaiService {
    private KhuyenMaiDAO khuyenMaiDAO;

    public KhuyenMaiService() {
        this.khuyenMaiDAO = new KhuyenMaiDAO();
    }

    /**
     * Lấy khuyến mại theo mã
     */
    public KhuyenMai getKhuyenMaiTheoMa(String maKM) {
        if (maKM == null || maKM.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khuyến mại không được để trống");
        }
        KhuyenMai km = khuyenMaiDAO.getKhuyenMaiTheoMa(maKM);
        if (km == null) {
            System.out.println(" Không tìm thấy khuyến mại với mã: " + maKM);
        }
        return km;
    }

    /**
     * Lấy tất cả khuyến mại
     */
    public List<KhuyenMai> getAllKhuyenMai() {
        return khuyenMaiDAO.getAllKhuyenMai();
    }

    /**
     * Lấy khuyến mại đang hoạt động
     */
    public List<KhuyenMai> getKhuyenMaiHoatDong() {
        return khuyenMaiDAO.getKhuyenMaiTheoTrangThai(TrangThaiKhuyenMai.CON_AP_DUNG);
    }

    /**
     * Thêm khuyến mại mới
     */
    public void themKhuyenMai(KhuyenMai khuyenMai) {
        if (khuyenMai == null) {
            throw new IllegalArgumentException("Khuyến mại không được để trống");
        }
        if (khuyenMai.getMaKM() == null || khuyenMai.getMaKM().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khuyến mại không được để trống");
        }
        if (khuyenMai.getTenKM() == null || khuyenMai.getTenKM().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khuyến mại không được để trống");
        }
        if (khuyenMai.getGiaTriGiam() < 0) {
            throw new IllegalArgumentException("Giá trị giảm phải lớn hơn hoặc bằng 0");
        }
        if (khuyenMaiDAO.themKhuyenMai(khuyenMai)) {
            System.out.println(" Thêm khuyến mại thành công");
        } else {
            System.out.println(" Thêm khuyến mại thất bại");
        }
    }

    /**
     * Cập nhật khuyến mại
     */
    public void capNhatKhuyenMai(KhuyenMai khuyenMai) {
        if (khuyenMai == null) {
            throw new IllegalArgumentException("Khuyến mại không được để trống");
        }
        if (khuyenMaiDAO.capNhatKhuyenMai(khuyenMai)) {
            System.out.println(" Cập nhật khuyến mại thành công");
        } else {
            System.out.println(" Cập nhật khuyến mại thất bại");
        }
    }

    /**
     * Xóa khuyến mại
     */
    public void xoaKhuyenMai(String maKM) {
        if (maKM == null || maKM.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khuyến mại không được để trống");
        }
        if (khuyenMaiDAO.xoaKhuyenMai(maKM)) {
            System.out.println(" Xóa khuyến mại thành công");
        } else {
            System.out.println(" Xóa khuyến mại thất bại");
        }
    }

    /**
     * Cập nhật trạng thái khuyến mại
     */
    public void capNhatTrangThaiKhuyenMai(String maKM, TrangThaiKhuyenMai trangThai) {
        KhuyenMai km = getKhuyenMaiTheoMa(maKM);
        if (km != null) {
            km.setTrangThai(trangThai);
            capNhatKhuyenMai(km);
        }
    }

    /**
     * Kích hoạt khuyến mại
     */
    public void kichHoat(String maKM) {
        capNhatTrangThaiKhuyenMai(maKM, TrangThaiKhuyenMai.CON_AP_DUNG);
        System.out.println(" Khuyến mại đã được kích hoạt");
    }

    /**
     * Dừng khuyến mại
     */
    public void dung(String maKM) {
        capNhatTrangThaiKhuyenMai(maKM, TrangThaiKhuyenMai.NGUNG_AP_DUNG);
        System.out.println(" Khuyến mại đã được dừng");
    }

    /**
     * Kiểm tra khuyến mại tồn tại
     */
    public boolean existKhuyenMai(String maKM) {
        return getKhuyenMaiTheoMa(maKM) != null;
    }

    /**
     * Tính tổng số khuyến mại
     */
    public int getTotalKhuyenMai() {
        List<KhuyenMai> list = getAllKhuyenMai();
        return list != null ? list.size() : 0;
    }

    /**
     * Lấy khuyến mại theo khoảng ngày
     */
    public List<KhuyenMai> getKhuyenMaiTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        if (ngayBatDau == null || ngayKetThuc == null) {
            throw new IllegalArgumentException("Ngày bắt đầu và ngày kết thúc không được để trống");
        }
        return khuyenMaiDAO.getKhuyenMaiTheoNgay(ngayBatDau, ngayKetThuc);
    }
}
