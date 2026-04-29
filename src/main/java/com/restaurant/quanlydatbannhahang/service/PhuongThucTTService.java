package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.PhuongThucTTDAO;
import com.restaurant.quanlydatbannhahang.entity.PhuongThucTT;

import java.util.List;

public class PhuongThucTTService {
    private PhuongThucTTDAO phuongThucTTDAO;

    public PhuongThucTTService() {
        this.phuongThucTTDAO = new PhuongThucTTDAO();
    }

    /**
     * Lấy tất cả phương thức thanh toán
     */
    public List<PhuongThucTT> getAllPhuongThucTT() {
        return phuongThucTTDAO.getAllPhuongThucTT();
    }

    /**
     * Tìm phương thức thanh toán theo tên
     */
    public PhuongThucTT findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên phương thức không được để trống");
        }
        PhuongThucTT phuongThuc = phuongThucTTDAO.findByName(name);
        if (phuongThuc == null) {
            throw new RuntimeException("Không tìm thấy phương thức thanh toán: " + name);
        }
        return phuongThuc;
    }

    /**
     * Kiểm tra phương thức thanh toán tồn tại
     */
    public boolean exists(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return phuongThucTTDAO.findByName(name) != null;
    }

    /**
     * Tính tổng số loại phương thức thanh toán
     */
    public int getTotalPhuongThucTT() {
        List<PhuongThucTT> list = getAllPhuongThucTT();
        return list != null ? list.size() : 0;
    }

    /**
     * Lấy phương thức thanh toán tiền mặt
     */
    public PhuongThucTT getPhuongThucTienMat() {
        return PhuongThucTT.TIEN_MAT;
    }

    /**
     * Lấy phương thức thanh toán thẻ
     */
    public PhuongThucTT getPhuongThucThe() {
        return PhuongThucTT.THE;
    }

    /**
     * Lấy phương thức thanh toán chuyển khoản
     */
    public PhuongThucTT getPhuongThucChuyenKhoan() {
        return PhuongThucTT.CHUYEN_KHOAN;
    }

    /**
     * Kiểm tra là thanh toán tiền mặt
     */
    public boolean isTienMat(PhuongThucTT phuongThuc) {
        return phuongThuc == PhuongThucTT.TIEN_MAT;
    }

    /**
     * Kiểm tra là thanh toán thẻ
     */
    public boolean isThe(PhuongThucTT phuongThuc) {
        return phuongThuc == PhuongThucTT.THE;
    }

    /**
     * Kiểm tra là thanh toán chuyển khoản
     */
    public boolean isChuyenKhoan(PhuongThucTT phuongThuc) {
        return phuongThuc == PhuongThucTT.CHUYEN_KHOAN;
    }
}
