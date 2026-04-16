package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.NhanVienDAO;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import java.util.List;

public class NhanVienService {
    private final NhanVienDAO nhanVienDAO;

    public NhanVienService() {
        this.nhanVienDAO = new NhanVienDAO();
    }

    /**
     * Lấy tất cả nhân viên
     */
    public List<NhanVien> getAllNhanVien() {
        return nhanVienDAO.getAllNhanVien();
    }

    /**
     * Lấy nhân viên theo mã
     */
    public NhanVien getNhanVienTheoMa(String maNV) {
        if (maNV == null || maNV.trim().isEmpty()) {
            return null;
        }
        return nhanVienDAO.getNhanVienTheoMa(maNV);
    }

    /**
     * Thêm nhân viên mới
     */
    public boolean themNhanVien(NhanVien nv) throws Exception {
        validateNhanVien(nv);
        
        // Kiểm tra trùng mã
        if (nhanVienDAO.getNhanVienTheoMa(nv.getMaNV()) != null) {
            System.err.println("❌ Thêm " + nv.getMaNV() + " không thành công: Mã đã tồn tại!");
            throw new Exception("Mã nhân viên [" + nv.getMaNV() + "] đã tồn tại!");
        }
        
        boolean result = nhanVienDAO.themNhanVien(nv);
        if (result) {
            System.out.println("✓ Thêm " + nv.getMaNV() + " thành công!");
        } else {
            System.err.println("❌ Thêm " + nv.getMaNV() + " không thành công!");
        }
        return result;
    }

    /**
     * Cập nhật nhân viên
     * Logic: Kiểm tra tồn tại và kiểm tra xem có sự thay đổi dữ liệu hay không
     */
    public boolean capNhatNhanVien(NhanVien nvMoi) throws Exception {
        validateNhanVien(nvMoi);
        
        // 1. Kiểm tra tồn tại trước khi cập nhật
        NhanVien nvCu = nhanVienDAO.getNhanVienTheoMa(nvMoi.getMaNV());
        if (nvCu == null) {
            System.err.println("❌ Cập nhật " + nvMoi.getMaNV() + " không thành công: Không tìm thấy nhân viên!");
            throw new Exception("Không tìm thấy nhân viên mã [" + nvMoi.getMaNV() + "] để cập nhật!");
        }

        // 2. Logic: Kiểm tra nếu không có gì thay đổi so với database
        if (isDữLiệuGiốngNhau(nvCu, nvMoi)) {
            System.out.println("⚠ Cập nhật " + nvMoi.getMaNV() + ": Bạn chưa thay đổi gì.");
            throw new Exception("Bạn chưa thay đổi bất kỳ thông tin nào so với dữ liệu cũ!");
        }
        
        boolean result = nhanVienDAO.capNhatNhanVien(nvMoi);
        if (result) {
            System.out.println("✓ Cập nhật " + nvMoi.getMaNV() + " thành công!");
        } else {
            System.err.println("❌ Cập nhật " + nvMoi.getMaNV() + " không thành công!");
        }
        return result;
    }

    /**
     * Xóa nhân viên theo mã
     */
    public boolean xoaNhanVien(String maNV) throws Exception {
        if (maNV == null || maNV.trim().isEmpty()) {
            throw new Exception("Mã nhân viên không được để trống!");
        }
        
        boolean result = nhanVienDAO.xoaNhanVien(maNV);
        if (result) {
            System.out.println("✓ Xóa " + maNV + " thành công!");
        } else {
            System.err.println("❌ Xóa " + maNV + " không thành công!");
        }
        return result;
    }

    /**
     * So sánh dữ liệu cũ và mới để kiểm tra xem có thay đổi không
     */
    private boolean isDữLiệuGiốngNhau(NhanVien cu, NhanVien moi) {
        return cu.getHoTen().trim().equals(moi.getHoTen().trim()) &&
               cu.getSdt().trim().equals(moi.getSdt().trim()) &&
               cu.getChucVu() == moi.getChucVu() &&
               cu.getNgayVaoLam().equals(moi.getNgayVaoLam()) &&
               Double.compare(cu.getLuongCoBan(), moi.getLuongCoBan()) == 0 &&
               cu.getTrangThai() == moi.getTrangThai();
    }

    /**
     * Tìm kiếm nhân viên theo tên hoặc số điện thoại
     */
    public List<NhanVien> timKiemNhanVien(String keyword) {
        System.out.println("🔍 Đang tìm kiếm với từ khóa: " + keyword);
        if (keyword == null || keyword.trim().isEmpty()) {
            return nhanVienDAO.getAllNhanVien();
        }
        return nhanVienDAO.timKiemNhanVien(keyword.trim());
    }

    /**
     * Lọc nhân viên theo chức vụ
     */
    public List<NhanVien> filterTheoChucVu(String chucVuName) {
        if (chucVuName == null || chucVuName.equals("Tất cả chức vụ")) {
            return nhanVienDAO.getAllNhanVien();
        }
        return nhanVienDAO.filterTheoChucVu(chucVuName);
    }

    /**
     * Lấy danh sách nhân viên đang làm việc
     */
    public List<NhanVien> getNhanVienDangLamViec() {
        return nhanVienDAO.getNhanVienDangLamViec();
    }

    /**
     * Hàm kiểm tra dữ liệu đầu vào (Validation)
     */
    private void validateNhanVien(NhanVien nv) throws Exception {
        if (nv == null) {
            throw new Exception("Dữ liệu nhân viên không hợp lệ!");
        }
        if (nv.getMaNV() == null || nv.getMaNV().trim().isEmpty()) {
            throw new Exception("Mã nhân viên không được để trống!");
        }
        if (nv.getHoTen() == null || nv.getHoTen().trim().isEmpty()) {
            throw new Exception("Họ tên không được để trống!");
        }
        if (nv.getSdt() == null || !nv.getSdt().matches("0\\d{9}")) {
            throw new Exception("Số điện thoại phải bắt đầu bằng số 0 và có 10 chữ số!");
        }
        if (nv.getLuongCoBan() < 0) {
            throw new Exception("Lương cơ bản không được âm!");
        }
        if (nv.getNgayVaoLam() == null) {
            throw new Exception("Ngày vào làm không được để trống!");
        }
    }

    public int getTotalNhanVien() {
        return nhanVienDAO.getAllNhanVien().size();
    }
}   