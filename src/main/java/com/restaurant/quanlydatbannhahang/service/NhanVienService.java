package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.NhanVienDAO;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiNhanVien;

import java.util.List;

public class NhanVienService {
    private NhanVienDAO nhanVienDAO;

    public NhanVienService() {
        this.nhanVienDAO = new NhanVienDAO();
    }

    /**
     * Lấy nhân viên theo mã
     */
    public NhanVien getNhanVienTheoMa(String maNV) {
        if (maNV == null || maNV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống");
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
        if (nhanVien == null) {
            throw new IllegalArgumentException("Nhân viên không được để trống");
        }
        if (nhanVien.getMaNV() == null || nhanVien.getMaNV().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống");
        }
        if (nhanVien.getHoTen() == null || nhanVien.getHoTen().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống");
        }
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
        if (nhanVien == null) {
            throw new IllegalArgumentException("Nhân viên không được để trống");
        }
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
        if (maNV == null || maNV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống");
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
        if (maNV == null || maNV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống");
        }
        if (trangThai == null) {
            throw new IllegalArgumentException("Trạng thái không được để trống");
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
}
