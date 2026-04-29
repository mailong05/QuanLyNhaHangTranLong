package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.entity.LoaiMonAn;

import java.util.Arrays;
import java.util.List;

public class LoaiMonAnService {

    /**
     * Lấy loại món ăn theo tên
     */
    public LoaiMonAn getLoaiMonAnTheoMa(String maLoai) {
        if (maLoai == null || maLoai.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã loại không được để trống");
        }
        try {
            return LoaiMonAn.valueOf(maLoai);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Không tìm thấy loại món ăn với mã: " + maLoai);
        }
    }

    /**
     * Lấy tất cả loại món ăn
     */
    public List<LoaiMonAn> getAllLoaiMonAn() {
        return Arrays.asList(LoaiMonAn.values());
    }

    /**
     * Kiểm tra loại món ăn tồn tại
     */
    public boolean existLoaiMonAn(String maLoai) {
        if (maLoai == null || maLoai.trim().isEmpty()) {
            return false;
        }
        try {
            LoaiMonAn.valueOf(maLoai);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Lấy tổng số loại món ăn
     */
    public int getTotalLoaiMonAn() {
        return LoaiMonAn.values().length;
    }

    /**
     * Lấy display name của loại món
     */
    public String getDisplayName(LoaiMonAn loaiMonAn) {
        return loaiMonAn != null ? loaiMonAn.getDisplayName() : null;
    }
}
