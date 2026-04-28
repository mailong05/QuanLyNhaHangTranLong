package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.ThueDAO;
import com.restaurant.quanlydatbannhahang.entity.Thue;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiThue;

import java.util.List;
import java.util.regex.Pattern;

public class ThueService {
    private ThueDAO thueDAO;

    private static final String MATHUE_PATTERN = "^TH\\d{3}$";
    private static final Pattern maThuePattern = Pattern.compile(MATHUE_PATTERN);

    public ThueService() {
        this.thueDAO = new ThueDAO();
    }

    /**
     * Validate đối tượng Thue
     */
    public void validateThue(Thue thue) {
        if (thue == null) {
            throw new IllegalArgumentException("Đối tượng thuế không được để trống");
        }
        if (thue.getMaThue() == null || thue.getMaThue().isBlank()) {
            throw new IllegalArgumentException("Mã thuế không được để trống");
        }
        if (!maThuePattern.matcher(thue.getMaThue()).matches()) {
            throw new IllegalArgumentException("Mã thuế phải có dạng THxxx (ví dụ: TH001)");
        }
        if (thue.getTenThue() == null || thue.getTenThue().isBlank()) {
            throw new IllegalArgumentException("Tên thuế không được để trống");
        }
        if (thue.getThueSuat() < 0 || thue.getThueSuat() > 100) {
            throw new IllegalArgumentException("Thuế suất phải nằm trong khoảng 0 đến 100");
        }
        if (thue.getTrangThai() == null) {
            throw new IllegalArgumentException("Trạng thái thuế không được để trống");
        }
    }

    /**
     * Lấy thuế theo mã
     */
    public Thue getThueTheoMa(String maThue) {
        if (maThue == null || maThue.isBlank()) {
            throw new IllegalArgumentException("Mã thuế không được để trống");
        }
        if (!maThuePattern.matcher(maThue).matches()) {
            throw new IllegalArgumentException("Mã thuế phải có dạng THxxx (ví dụ: TH001)");
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
        validateThue(thue);
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
        validateThue(thue);
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
        if (maThue == null || maThue.isBlank()) {
            throw new IllegalArgumentException("Mã thuế không được để trống");
        }
        if (!maThuePattern.matcher(maThue).matches()) {
            throw new IllegalArgumentException("Mã thuế phải có dạng THxxx (ví dụ: TH001)");
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
        if (maThue == null || maThue.isBlank()) {
            throw new IllegalArgumentException("Mã thuế không được để trống");
        }
        if (!maThuePattern.matcher(maThue).matches()) {
            throw new IllegalArgumentException("Mã thuế phải có dạng THxxx (ví dụ: TH001)");
        }
        if (thueSuatMoi < 0 || thueSuatMoi > 100) {
            throw new IllegalArgumentException("Thuế suất phải nằm trong khoảng 0 đến 100");
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
        if (maThue == null || maThue.isBlank()) {
            throw new IllegalArgumentException("Mã thuế không được để trống");
        }
        if (!maThuePattern.matcher(maThue).matches()) {
            throw new IllegalArgumentException("Mã thuế phải có dạng THxxx (ví dụ: TH001)");
        }
        if (trangThai == null) {
            throw new IllegalArgumentException("Trạng thái thuế không được để trống");
        }
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
        if (soTien < 0) {
            throw new IllegalArgumentException("Số tiền để tính thuế không được là số âm");
        }
        Thue thue = getThueTheoMa(maThue);
        if (thue != null) {
            return soTien * (thue.getThueSuat() / 100.0);
        }
        return 0;
    }
}
