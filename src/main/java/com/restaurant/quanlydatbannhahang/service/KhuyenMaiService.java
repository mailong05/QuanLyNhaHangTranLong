package com.restaurant.quanlydatbannhahang.service;
import com.restaurant.quanlydatbannhahang.dao.KhuyenMaiDAO;
import com.restaurant.quanlydatbannhahang.entity.KhuyenMai;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiKhuyenMai;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;
public class KhuyenMaiService {
    private KhuyenMaiDAO khuyenMaiDAO;
    private static final String MAKM_PATTERN = "^KM\\d{3}$";
    private static final Pattern maKMPattern = Pattern.compile(MAKM_PATTERN);
    public KhuyenMaiService() {
        this.khuyenMaiDAO = new KhuyenMaiDAO();
    }
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
    public List<KhuyenMai> getAllKhuyenMai() {
        return khuyenMaiDAO.getAllKhuyenMai();
    }
    public List<KhuyenMai> getKhuyenMaiHoatDong() {
        return khuyenMaiDAO.getKhuyenMaiTheoTrangThai(TrangThaiKhuyenMai.CON_AP_DUNG);
    }
    public void themKhuyenMai(KhuyenMai khuyenMai) {
        validateKhuyenMai(khuyenMai);
        
        LocalDate homNay = LocalDate.now();
        if (khuyenMai.getNgayBatDau().isBefore(homNay)) {
            throw new IllegalArgumentException("Ngày bắt đầu phải từ ngày hiện tại trở về sau!");
        }
        if (khuyenMai.getNgayKetThuc().isBefore(homNay)) {
            throw new IllegalArgumentException("Ngày kết thúc phải từ ngày hiện tại trở về sau!");
        }
        
        if (!khuyenMaiDAO.themKhuyenMai(khuyenMai)) {
            throw new RuntimeException("Thêm khuyến mãi thất bại");
        }
    }

    public void capNhatKhuyenMai(KhuyenMai khuyenMai) {
        validateKhuyenMai(khuyenMai);
        
        LocalDate homNay = LocalDate.now();
        if (khuyenMai.getNgayKetThuc().isBefore(homNay)) {
            throw new IllegalArgumentException("Ngày kết thúc phải từ ngày hiện tại trở về sau!");
        }
        
        if (!khuyenMaiDAO.capNhatKhuyenMai(khuyenMai)) {
            throw new RuntimeException("Cập nhật khuyến mãi thất bại");
        }
    }
    
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
    public void kichHoat(String maKM) {
        capNhatTrangThaiKhuyenMai(maKM, TrangThaiKhuyenMai.CON_AP_DUNG);
    }
    public void dung(String maKM) {
        capNhatTrangThaiKhuyenMai(maKM, TrangThaiKhuyenMai.NGUNG_AP_DUNG);
    }
    public boolean existKhuyenMai(String maKM) {
        return getKhuyenMaiTheoMa(maKM) != null;
    }
    public boolean isKhuyenMaiHieuLuc(String maKM, LocalDateTime ngayHienTai) {
        if (ngayHienTai == null) {
            throw new IllegalArgumentException("Ngày kiểm tra hiệu lực không được để trống");
        }
        KhuyenMai khuyenMai = getKhuyenMaiTheoMa(maKM);
        if (khuyenMai == null) {
            return false;
        }
        return khuyenMai.kiemTraHieuLuc(ngayHienTai);
    }
    public int getTotalKhuyenMai() {
        List<KhuyenMai> list = getAllKhuyenMai();
        return list != null ? list.size() : 0;
    }
    public List<KhuyenMai> getKhuyenMaiTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        if (ngayBatDau == null || ngayKetThuc == null) {
            throw new IllegalArgumentException("Ngày bắt đầu và ngày kết thúc không được để trống");
        }
        return khuyenMaiDAO.getKhuyenMaiTheoNgay(ngayBatDau, ngayKetThuc);
    }
    public String getLastKhuyenMaiID() {
        return khuyenMaiDAO.getLastKhuyenMaiID();
    }
    
    public boolean tuDongCapNhatHetHanKhuyenMai() {
        return khuyenMaiDAO.tuDongCapNhatHetHanKhuyenMai();
    }
}