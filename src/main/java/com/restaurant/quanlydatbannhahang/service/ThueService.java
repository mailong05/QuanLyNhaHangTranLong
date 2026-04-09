package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.ThueDAO;
import com.restaurant.quanlydatbannhahang.entity.Thue;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiThue;

import java.util.List;

public class ThueService {
    private ThueDAO thueDAO;

    public ThueService() {
        this.thueDAO = new ThueDAO();
    }

    /**
     * Lấy thuế theo mã
     */
    public Thue getThueTheoMa(String maThue) {
        if (maThue == null || maThue.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã thuế không được để trống");
        }
        Thue thue = thueDAO.getThueTheoMa(maThue);
        if (thue == null) {
            System.out.println(" Không tìm thấy thuế với mã: " + maThue);
        }
        return thue;
    }

    /**
     * Lấy tất cả thuế
     */
    public List<Thue> getAllThue() {
        return thueDAO.getAllThue();
    }

    /**
     * Lấy thuế đang hoạt động
     */
    public List<Thue> getThueHoatDong() {
        return thueDAO.getThueTheoTrangThai(TrangThaiThue.CON_AP_DUNG);
    }

    /**
     * Thêm thuế mới
     */
    public void themThue(Thue thue) {
        if (thue == null) {
            throw new IllegalArgumentException("Thuế không được để trống");
        }
        if (thue.getMaThue() == null || thue.getMaThue().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã thuế không được để trống");
        }
        if (thue.getTenThue() == null || thue.getTenThue().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên thuế không được để trống");
        }
        if (thue.getThueSuat() < 0 || thue.getThueSuat() > 100) {
            throw new IllegalArgumentException("Thuế suất phải từ 0 đến 100");
        }
        if (thueDAO.themThue(thue)) {
            System.out.println(" Thêm thuế thành công");
        } else {
            System.out.println(" Thêm thuế thất bại");
        }
    }

    /**
     * Cập nhật thuế
     */
    public void capNhatThue(Thue thue) {
        if (thue == null) {
            throw new IllegalArgumentException("Thuế không được để trống");
        }
        if (thueDAO.capNhatThue(thue)) {
            System.out.println(" Cập nhật thuế thành công");
        } else {
            System.out.println(" Cập nhật thuế thất bại");
        }
    }

    /**
     * Xóa thuế
     */
    public void xoaThue(String maThue) {
        if (maThue == null || maThue.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã thuế không được để trống");
        }
        if (thueDAO.xoaThue(maThue)) {
            System.out.println(" Xóa thuế thành công");
        } else {
            System.out.println(" Xóa thuế thất bại");
        }
    }

    /**
     * Cập nhật phần trăm thuế
     */
    public void capNhatThueSuat(String maThue, double thueSuatMoi) {
        if (thueSuatMoi < 0 || thueSuatMoi > 100) {
            throw new IllegalArgumentException("Thuế suất phải từ 0 đến 100");
        }
        Thue thue = getThueTheoMa(maThue);
        if (thue != null) {
            thue.setThueSuat(thueSuatMoi);
            capNhatThue(thue);
        }
    }

    /**
     * Cập nhật trạng thái thuế
     */
    public void capNhatTrangThaiThue(String maThue, TrangThaiThue trangThai) {
        Thue thue = getThueTheoMa(maThue);
        if (thue != null) {
            thue.setTrangThai(trangThai);
            capNhatThue(thue);
        }
    }

    /**
     * Kích hoạt thuế
     */
    public void kichHoat(String maThue) {
        capNhatTrangThaiThue(maThue, TrangThaiThue.CON_AP_DUNG);
        System.out.println(" Thuế đã được kích hoạt");
    }

    /**
     * Vô hiệu hóa thuế
     */
    public void voHieuHoa(String maThue) {
        capNhatTrangThaiThue(maThue, TrangThaiThue.NGUNG_AP_DUNG);
        System.out.println(" Thuế đã được vô hiệu hóa");
    }

    /**
     * Kiểm tra thuế tồn tại
     */
    public boolean existThue(String maThue) {
        return getThueTheoMa(maThue) != null;
    }

    /**
     * Tính tổng số thuế
     */
    public int getTotalThue() {
        List<Thue> list = getAllThue();
        return list != null ? list.size() : 0;
    }

    /**
     * Tính tổng thuế cho một số tiền
     */
    public double tinhThue(String maThue, double soTien) {
        Thue thue = getThueTheoMa(maThue);
        if (thue != null) {
            return soTien * (thue.getThueSuat() / 100.0);
        }
        return 0;
    }
}
