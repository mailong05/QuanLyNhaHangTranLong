package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.KhuyenMaiDAO;
import com.restaurant.quanlydatbannhahang.entity.KhuyenMai;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiKhuyenMai;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class KhuyenMaiService {
    private KhuyenMaiDAO khuyenMaiDAO;

    private static final String MAKM_PATTERN = "^KM\\d{3}$";
    private static final Pattern maKMPattern = Pattern.compile(MAKM_PATTERN);

    public KhuyenMaiService() {
        this.khuyenMaiDAO = new KhuyenMaiDAO();
    }

    /**
     * Validate đối tượng KhuyenMai
     */
    public void validateKhuyenMai(KhuyenMai khuyenMai) {
        if (khuyenMai == null) {
            throw new IllegalArgumentException("Đối tượng khuyến mại không được để trống");
        }
        if (khuyenMai.getMaKM() == null || khuyenMai.getMaKM().isBlank()) {
            throw new IllegalArgumentException("Mã khuyến mại không được để trống");
        }
        if (!maKMPattern.matcher(khuyenMai.getMaKM()).matches()) {
            throw new IllegalArgumentException("Mã khuyến mại phải có dạng KMxxx (ví dụ: KM001)");
        }
        if (khuyenMai.getTenKM() == null || khuyenMai.getTenKM().isBlank()) {
            throw new IllegalArgumentException("Tên khuyến mại không được để trống");
        }
        if (khuyenMai.getGiaTriGiam() < 0) {
            throw new IllegalArgumentException("Giá trị giảm phải lớn hơn hoặc bằng 0");
        }
        if (khuyenMai.getNgayBatDau() == null || khuyenMai.getNgayKetThuc() == null) {
            throw new IllegalArgumentException("Ngày bắt đầu và ngày kết thúc không được để trống");
        }
        if (khuyenMai.getNgayKetThuc().isBefore(khuyenMai.getNgayBatDau())) {
            throw new IllegalArgumentException("Ngày kết thúc phải sau hoặc bằng ngày bắt đầu");
        }
        if (khuyenMai.getDieuKienToiThieu() < 0) {
            throw new IllegalArgumentException("Điều kiện tối thiểu phải lớn hơn hoặc bằng 0");
        }
        if (khuyenMai.getTrangThai() == null) {
            throw new IllegalArgumentException("Trạng thái khuyến mại không được để trống");
        }
    }

    /**
     * Lấy khuyến mại theo mã
     */
    public KhuyenMai getKhuyenMaiTheoMa(String maKM) {
        if (maKM == null || maKM.isBlank()) {
            throw new IllegalArgumentException("Mã khuyến mại không được để trống");
        }
        if (!maKMPattern.matcher(maKM).matches()) {
            throw new IllegalArgumentException("Mã khuyến mại phải có dạng KMxxx (ví dụ: KM001)");
        }
        KhuyenMai km = khuyenMaiDAO.getKhuyenMaiTheoMa(maKM);
        if (km == null) {
            throw new RuntimeException("Không tìm thấy khuyến mãi với mã: " + maKM);
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
        validateKhuyenMai(khuyenMai);
        if (!khuyenMaiDAO.themKhuyenMai(khuyenMai)) {
            throw new RuntimeException("Thêm khuyến mãi thất bại");
        }
    }

    /**
     * Cập nhật khuyến mại
     */
    public void capNhatKhuyenMai(KhuyenMai khuyenMai) {
        validateKhuyenMai(khuyenMai);
        if (!khuyenMaiDAO.capNhatKhuyenMai(khuyenMai)) {
            throw new RuntimeException("Cập nhật khuyến mãi thất bại");
        }
    }

    /**
     * Xóa khuyến mại
     */
    public void xoaKhuyenMai(String maKM) {
        if (maKM == null || maKM.isBlank()) {
            throw new IllegalArgumentException("Mã khuyến mại không được để trống");
        }
        if (!maKMPattern.matcher(maKM).matches()) {
            throw new IllegalArgumentException("Mã khuyến mại phải có dạng KMxxx (ví dụ: KM001)");
        }
        if (!khuyenMaiDAO.xoaKhuyenMai(maKM)) {
            throw new RuntimeException("Xóa khuyến mãi thất bại");
        }
    }

    /**
     * Cập nhật trạng thái khuyến mại
     */
    public void capNhatTrangThaiKhuyenMai(String maKM, TrangThaiKhuyenMai trangThai) {
        if (maKM == null || maKM.isBlank()) {
            throw new IllegalArgumentException("Mã khuyến mại không được để trống");
        }
        if (!maKMPattern.matcher(maKM).matches()) {
            throw new IllegalArgumentException("Mã khuyến mại phải có dạng KMxxx (ví dụ: KM001)");
        }
        if (trangThai == null) {
            throw new IllegalArgumentException("Trạng thái khuyến mại không được để trống");
        }
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
    }

    /**
     * Dừng khuyến mại
     */
    public void dung(String maKM) {
        capNhatTrangThaiKhuyenMai(maKM, TrangThaiKhuyenMai.NGUNG_AP_DUNG);
    }

    /**
     * Kiểm tra khuyến mại tồn tại
     */
    public boolean existKhuyenMai(String maKM) {
        return getKhuyenMaiTheoMa(maKM) != null;
    }

    /**
     * Kiểm tra khuyến mại có hiệu lực tại một thời điểm nhất định
     */
    public boolean isKhuyenMaiHieuLuc(String maKM, LocalDate ngayHienTai) {
        if (ngayHienTai == null) {
            throw new IllegalArgumentException("Ngày kiểm tra hiệu lực không được để trống");
        }
        KhuyenMai khuyenMai = getKhuyenMaiTheoMa(maKM);
        if (khuyenMai == null) {
            return false;
        }
        return khuyenMai.kiemTraHieuLuc(ngayHienTai);
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

    /**
     * Lấy mã khuyến mại cuối cùng để sinh mã tiếp theo
     *
     * @return Mã khuyến mại cuối cùng (VD: KM000) hoặc null nếu bảng rỗng
     */
    public String getLastKhuyenMaiID() {
        return khuyenMaiDAO.getLastKhuyenMaiID();
    }
}
