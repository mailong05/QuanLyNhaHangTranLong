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
    public Thue getThueTheoMa(String maThue) {
        if (maThue == null || maThue.isBlank()) {
            throw new IllegalArgumentException("Mã thuế không được để trống");
        }
        if (!maThuePattern.matcher(maThue).matches()) {
            throw new IllegalArgumentException("Mã thuế phải có dạng THxxx (ví dụ: TH001)");
        }
        Thue thue = thueDAO.getThueTheoMa(maThue);
        if (thue == null) {
            throw new RuntimeException("Không tìm thấy thuế với mã: " + maThue);
        }
        return thue;
    }
    public List<Thue> getAllThue() {
        return thueDAO.getAllThue();
    }
    public List<Thue> getThueHoatDong() {
        return thueDAO.getThueTheoTrangThai(TrangThaiThue.CON_AP_DUNG);
    }
    public void themThue(Thue thue) {
        validateThue(thue);
        if (!thueDAO.themThue(thue)) {
            throw new RuntimeException("Thêm thuế thất bại");
        }
    }
    public void capNhatThue(Thue thue) {
        validateThue(thue);
        if (!thueDAO.capNhatThue(thue)) {
            throw new RuntimeException("Cập nhật thuế thất bại");
        }
    }
    public void xoaThue(String maThue) {
        if (maThue == null || maThue.isBlank()) {
            throw new IllegalArgumentException("Mã thuế không được để trống");
        }
        if (!maThuePattern.matcher(maThue).matches()) {
            throw new IllegalArgumentException("Mã thuế phải có dạng THxxx (ví dụ: TH001)");
        }
        if (!thueDAO.xoaThue(maThue)) {
            throw new RuntimeException("Xóa thuế thất bại");
        }
    }
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
    public void kichHoat(String maThue) {
        capNhatTrangThaiThue(maThue, TrangThaiThue.CON_AP_DUNG);
    }
    public void voHieuHoa(String maThue) {
        capNhatTrangThaiThue(maThue, TrangThaiThue.NGUNG_AP_DUNG);
    }
    public boolean existThue(String maThue) {
        return getThueTheoMa(maThue) != null;
    }
    public int getTotalThue() {
        List<Thue> list = getAllThue();
        return list != null ? list.size() : 0;
    }
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