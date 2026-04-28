package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.NhanVienDAO;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiNhanVien;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class NhanVienService {
    private NhanVienDAO nhanVienDAO;

    private static final String MANV_PATTERN = "^NV\\d{3}$";
    private static final String HOTEN_PATTERN = "^[\\p{Lu}][\\p{Ll}]*(\\s[\\p{Lu}][\\p{Ll}]*)*$";
    private static final String PHONE_PATTERN = "^0[1-9]\\d{8}$";

    private static final Pattern maNVPattern = Pattern.compile(MANV_PATTERN);
    private static final Pattern hoTenPattern = Pattern.compile(HOTEN_PATTERN);
    private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);

    public NhanVienService() {
        this.nhanVienDAO = new NhanVienDAO();
    }

    /**
     * Validate đối tượng NhanVien
     */
    public void validateNhanVien(NhanVien nhanVien) {
        if (nhanVien == null) {
            throw new IllegalArgumentException("Đối tượng nhân viên không được để trống");
        }
        if (nhanVien.getMaNV() == null || nhanVien.getMaNV().isBlank()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống");
        }
        if (!maNVPattern.matcher(nhanVien.getMaNV()).matches()) {
            throw new IllegalArgumentException("Mã nhân viên phải có dạng NVxxx (ví dụ: NV001)");
        }
        if (nhanVien.getHoTen() == null || nhanVien.getHoTen().isBlank()) {
            throw new IllegalArgumentException("Họ tên nhân viên không được để trống");
        }
        if (!hoTenPattern.matcher(nhanVien.getHoTen()).matches()) {
            throw new IllegalArgumentException("Họ tên nhân viên phải viết hoa chữ cái đầu mỗi từ");
        }
        if (nhanVien.getSdt() == null || nhanVien.getSdt().isBlank()) {
            throw new IllegalArgumentException("Số điện thoại nhân viên không được để trống");
        }
        if (!phonePattern.matcher(nhanVien.getSdt()).matches()) {
            throw new IllegalArgumentException("Số điện thoại nhân viên không hợp lệ (phải 10 chữ số và bắt đầu bằng 0)");
        }
        if (nhanVien.getChucVu() == null) {
            throw new IllegalArgumentException("Vui lòng chọn chức vụ cho nhân viên");
        }
        if (nhanVien.getNgayVaoLam() == null) {
            throw new IllegalArgumentException("Ngày vào làm không được để trống");
        }
        if (nhanVien.getNgayVaoLam().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày vào làm không được nằm trong tương lai");
        }
        if (nhanVien.getLuongCoBan() < 0) {
            throw new IllegalArgumentException("Lương cơ bản không được là số âm");
        }
    }

    /**
     * Lấy nhân viên theo mã
     */
    public NhanVien getNhanVienTheoMa(String maNV) {
        if (maNV == null || maNV.isBlank()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống");
        }
        if (!maNVPattern.matcher(maNV).matches()) {
            throw new IllegalArgumentException("Mã nhân viên phải có dạng NVxxx (ví dụ: NV001)");
        }
        NhanVien nhanVien = nhanVienDAO.getNhanVienTheoMa(maNV);
        if (nhanVien == null) {
            System.out.println(" Không tìm thấy nhân viên với mã: " + maNV);
        }
        return nhanVien;
    }

    /**
     * Lấy tất cả nhân viên
     */
    public List<NhanVien> getAllNhanVien() {
        return nhanVienDAO.getAllNhanVien();
    }

    /**
     * Thêm nhân viên mới
     */
    public void themNhanVien(NhanVien nhanVien) {
        validateNhanVien(nhanVien);
        if (nhanVienDAO.themNhanVien(nhanVien)) {
            System.out.println(" Thêm nhân viên thành công");
        } else {
            System.out.println(" Thêm nhân viên thất bại");
        }
    }

    /**
     * Cập nhật nhân viên
     */
    public void capNhatNhanVien(NhanVien nhanVien) {
        validateNhanVien(nhanVien);
        if (nhanVienDAO.capNhatNhanVien(nhanVien)) {
            System.out.println(" Cập nhật nhân viên thành công");
        } else {
            System.out.println(" Cập nhật nhân viên thất bại");
        }
    }

    /**
     * Xóa nhân viên
     */
    public void xoaNhanVien(String maNV) {
        if (maNV == null || maNV.isBlank()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống");
        }
        if (!maNVPattern.matcher(maNV).matches()) {
            throw new IllegalArgumentException("Mã nhân viên phải có dạng NVxxx (ví dụ: NV001)");
        }
        if (nhanVienDAO.xoaNhanVien(maNV)) {
            System.out.println(" Xóa nhân viên thành công");
        } else {
            System.out.println(" Xóa nhân viên thất bại");
        }
    }

    /**
     * Lấy danh sách nhân viên đang làm việc
     */
    public List<NhanVien> getNhanVienDangLamViec() {
        return nhanVienDAO.getNhanVienDangLamViec();
    }

    /**
     * Cập nhật trạng thái nhân viên
     */
    public void capNhatTrangThaiNhanVien(String maNV, TrangThaiNhanVien trangThai) {
        if (maNV == null || maNV.isBlank()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống");
        }
        if (!maNVPattern.matcher(maNV).matches()) {
            throw new IllegalArgumentException("Mã nhân viên phải có dạng NVxxx (ví dụ: NV001)");
        }
        if (trangThai == null) {
            throw new IllegalArgumentException("Trạng thái nhân viên không được để trống");
        }
        NhanVien nhanVien = getNhanVienTheoMa(maNV);
        if (nhanVien != null) {
            nhanVien.setTrangThai(trangThai);
            capNhatNhanVien(nhanVien);
        }
    }

    /**
     * Kiểm tra nhân viên đang làm việc hay không
     */
    public boolean isNhanVienDangLamViec(String maNV) {
        NhanVien nhanVien = getNhanVienTheoMa(maNV);
        return nhanVien != null && nhanVien.getTrangThai() == TrangThaiNhanVien.DANG_LAM_VIEC;
    }

    /**
     * Kiểm tra nhân viên có thâm niên ít nhất số năm cho trước
     */
    public boolean isNhanVienCoThamNien(String maNV, int nam) {
        if (nam < 0) {
            throw new IllegalArgumentException("Số năm thâm niên phải lớn hơn hoặc bằng 0");
        }
        NhanVien nhanVien = getNhanVienTheoMa(maNV);
        return nhanVien != null && nhanVien.getNgayVaoLam() != null
                && nhanVien.getNgayVaoLam().isBefore(LocalDate.now().minusYears(nam));
    }

    /**
     * Tính tổng số nhân viên
     */
    public int getTotalNhanVien() {
        List<NhanVien> list = getAllNhanVien();
        return list != null ? list.size() : 0;
    }

    /**
     * Tính tổng số nhân viên đang làm việc
     */
    public int getTotalNhanVienDangLamViec() {
        List<NhanVien> list = getNhanVienDangLamViec();
        return list != null ? list.size() : 0;
    }

    /**
     * Lấy mã nhân viên cuối cùng để sinh mã tiếp theo
     *
     * @return Mã nhân viên cuối cùng (VD: NV000) hoặc null nếu bảng rỗng
     */
    public String getLastNhanVienID() {
        return nhanVienDAO.getLastNhanVienID();
    }
}
