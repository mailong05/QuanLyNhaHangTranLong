package com.restaurant.quanlydatbannhahang.service;
import com.restaurant.quanlydatbannhahang.dao.ChucVuDAO;
import com.restaurant.quanlydatbannhahang.entity.ChucVu;
import java.util.List;
public class ChucVuService {
    private ChucVuDAO chucVuDAO;
    public ChucVuService() {
        this.chucVuDAO = new ChucVuDAO();
    }
    public List<ChucVu> getAllChucVu() {
        return chucVuDAO.getAllChucVu();
    }
    public ChucVu findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên chức vụ không được để trống");
        }
        ChucVu chucVu = chucVuDAO.findByName(name);
        if (chucVu == null) {
            throw new RuntimeException("Không tìm thấy chức vụ: " + name);
        }
        return chucVu;
    }
    public boolean exists(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return chucVuDAO.findByName(name) != null;
    }
    public String getDisplayName(ChucVu chucVu) {
        if (chucVu == null) {
            throw new IllegalArgumentException("Chức vụ không được để trống");
        }
        return chucVu.getDisplayName();
    }
    public int getTotalChucVu() {
        List<ChucVu> list = getAllChucVu();
        return list != null ? list.size() : 0;
    }
    public ChucVu getChucVuQuanLy() {
        return ChucVu.QUAN_LY;
    }
    public ChucVu getChucVuBep() {
        return ChucVu.BEP;
    }
    public ChucVu getChucVuThuNgan() {
        return ChucVu.THU_NGAN;
    }
    public ChucVu getChucVuPhucVu() {
        return ChucVu.PHUC_VU;
    }
}