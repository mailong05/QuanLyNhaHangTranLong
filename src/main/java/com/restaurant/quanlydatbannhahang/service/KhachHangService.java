package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.KhachHangDAO;
import com.restaurant.quanlydatbannhahang.entity.KhachHang;

import java.util.List;
import java.util.regex.Pattern;

public class KhachHangService {
    private KhachHangDAO khachHangDAO;

    private static final String PHONE_PATTERN = "^0[1-9]\\d{8}$";
    // \p{Lu}: Bất kỳ chữ cái Unicode nào ở dạng VIẾT HOA
    // \p{Ll}: Bất kỳ chữ cái Unicode nào ở dạng VIẾT THƯỜNG
    private static final String HOTEN_PATTERN = "^[\\p{Lu}][\\p{Ll}]*(\\s[\\p{Lu}][\\p{Ll}]*)*$";
    private static final String MAKH_PATTERN = "^KH\\d{3}$";

    private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);
    private static final Pattern hoTenPattern = Pattern.compile(HOTEN_PATTERN);
    private static final Pattern maKHPattern = Pattern.compile(MAKH_PATTERN);

    public KhachHangService() {
        this.khachHangDAO = new KhachHangDAO();
    }

    /**
     * Lấy khách hàng theo mã
     */
    public KhachHang getKhachHangTheoMa(String maKH) {
        if (maKH == null || maKH.isBlank()) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống");
        }

        if (!maKHPattern.matcher(maKH).matches()) {
            throw new IllegalArgumentException("Mã khách hàng phải có dạng KHxxx (ví dụ: KH001)");
        }

        KhachHang khachHang = khachHangDAO.getKhachHangTheoMa(maKH);
        if (khachHang == null) {
            throw new RuntimeException("Không tìm thấy khách hàng với mã: " + maKH);
        }
        return khachHang;
    }

    /**
     * Lấy tất cả khách hàng
     */
    public List<KhachHang> getAllKhachHang() {
        return khachHangDAO.getAllKhachHang();
    }

    /**
     * Thêm khách hàng mới
     */
    public boolean themKhachHang(KhachHang khachHang) {
        validateKhachHang(khachHang);

        boolean success = khachHangDAO.themKhachHang(khachHang);
        if (!success) {
            throw new RuntimeException("Thêm khách hàng thất bại");
        }
        return success;
    }

    public void validateKhachHang(KhachHang kh) {
        if (kh == null) {
            throw new IllegalArgumentException("Đối tượng khách hàng không tồn tại");
        }

        if (kh.getMaKH() == null || kh.getMaKH().isBlank()) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống");
        }
        if (!maKHPattern.matcher(kh.getMaKH()).matches()) {
            throw new IllegalArgumentException("Mã khách hàng phải có dạng KHxxx (ví dụ: KH001)");
        }

        if (kh.getHoTen() == null || kh.getHoTen().isBlank()) {
            throw new IllegalArgumentException("Họ tên khách hàng không được để trống");
        }
        if (!hoTenPattern.matcher(kh.getHoTen()).matches()) {
            throw new IllegalArgumentException("Tên khách hàng phải viết hoa chữ cái đầu mỗi từ");
        }

        if (kh.getSdt() == null || kh.getSdt().isBlank()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống");
        }
        if (!phonePattern.matcher(kh.getSdt()).matches()) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ (phải 10 số và bắt đầu bằng 0)");
        }

        if (kh.getDiemTichLuy() < 0) {
            throw new IllegalArgumentException("Điểm tích lũy không được là số âm");
        }

        if (kh.getLoaiThanhVien() == null) {
            throw new IllegalArgumentException("Vui lòng chọn loại thành viên");
        }

    }

    /**
     * Cập nhật khách hàng
     */
    public boolean capNhatKhachHang(KhachHang khachHang) {
        validateKhachHang(khachHang);
        boolean success = khachHangDAO.capNhatKhachHang(khachHang);
        if (!success) {
            throw new RuntimeException("Cập nhật khách hàng thất bại");
        }
        return true;
    }

    /**
     * Xóa khách hàng
     */
    public boolean xoaKhachHang(String maKH) {
        if (maKH == null || maKH.isBlank()) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống");
        }
        if (!maKHPattern.matcher(maKH).matches()) {
            throw new IllegalArgumentException("Mã khách hàng phải có dạng KHxxx (ví dụ: KH001)");
        }
        boolean success = khachHangDAO.xoaKhachHang(maKH);
        if (!success) {
            throw new RuntimeException("Xóa khách hàng thất bại");
        }
        return true;
    }

    /**
     * Lấy khách hàng theo số điện thoại
     */
    public KhachHang getKhachHangTheoSDT(String sdt) {
        if (sdt == null || sdt.trim().isBlank()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống");
        }
        if (!phonePattern.matcher(sdt).matches()) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ (phải 10 số và bắt đầu bằng 0)");
        }
        return khachHangDAO.getKhachHangTheoSDT(sdt);
    }

    /**
     * Cập nhật điểm tích lũy
     */
    public void capNhatDiemTichLuy(String maKH, int diemThem) {
        KhachHang khachHang = getKhachHangTheoMa(maKH);
        if (khachHang != null) {
            int diemMoi = khachHang.getDiemTichLuy() + diemThem;
            khachHang.setDiemTichLuy(diemMoi);
            capNhatKhachHang(khachHang);
        }
    }

    /**
     * Sử dụng điểm tích lũy
     */
    public boolean suDungDiemTichLuy(String maKH, int diemSuDung) {
        KhachHang khachHang = getKhachHangTheoMa(maKH);
        if (khachHang != null) {
            int diemConLai = Math.max(khachHang.getDiemTichLuy() - diemSuDung, 0);
            khachHang.setDiemTichLuy(diemConLai);
            capNhatKhachHang(khachHang);
            return true;
        }
        return false;
    }

    /**
     * Tính tổng số khách hàng
     */
    public int getTotalKhachHang() {
        List<KhachHang> list = getAllKhachHang();
        return list != null ? list.size() : 0;
    }

    /**
     * Kiểm tra khách hàng tồn tại
     */
    public boolean existKhachHang(String maKH) {
        return getKhachHangTheoMa(maKH) != null;
    }

    /**
     * Lấy mã khách hàng cuối cùng để sinh mã tiếp theo
     * 
     * @return Mã khách hàng cuối cùng (VD: KH000) hoặc null nếu bảng rỗng
     */
    public String getLastKhachHangID() {
        return khachHangDAO.getLastKhachHangID();
    }
}
