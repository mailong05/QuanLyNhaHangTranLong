package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.ChucVuDAO;
import com.restaurant.quanlydatbannhahang.entity.ChucVu;

import java.util.List;

public class ChucVuService {
    private ChucVuDAO chucVuDAO;

    public ChucVuService() {
        this.chucVuDAO = new ChucVuDAO();
    }

    /**
     * Lấy tất cả chức vụ
     */
    public List<ChucVu> getAllChucVu() {
        return chucVuDAO.getAllChucVu();
    }

    /**
     * Tìm chức vụ theo tên
     */
    public ChucVu findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên chức vụ không được để trống");
        }
        ChucVu chucVu = chucVuDAO.findByName(name);
        if (chucVu == null) {
            System.out.println(" Không tìm thấy chức vụ: " + name);
        }
        return chucVu;
    }

    /**
     * Kiểm tra chức vụ tồn tại
     */
    public boolean exists(String name) {
        return findByName(name) != null;
    }

    /**
     * Lấy tên hiển thị của chức vụ
     */
    public String getDisplayName(ChucVu chucVu) {
        if (chucVu == null) {
            throw new IllegalArgumentException("Chức vụ không được để trống");
        }
        return chucVu.getDisplayName();
    }

    /**
     * Tính tổng số loại chức vụ
     */
    public int getTotalChucVu() {
        List<ChucVu> list = getAllChucVu();
        return list != null ? list.size() : 0;
    }

    /**
     * Lấy chức vụ quản lý
     */
    public ChucVu getChucVuQuanLy() {
        return ChucVu.QUAN_LY;
    }

    /**
     * Lấy chức vụ bếp
     */
    public ChucVu getChucVuBep() {
        return ChucVu.BEP;
    }

    /**
     * Lấy chức vụ thu ngân
     */
    public ChucVu getChucVuThuNgan() {
        return ChucVu.THU_NGAN;
    }

    /**
     * Lấy chức vụ phục vụ
     */
    public ChucVu getChucVuPhucVu() {
        return ChucVu.PHUC_VU;
    }
}
