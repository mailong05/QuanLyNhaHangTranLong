package com.restaurant.quanlydatbannhahang.service;
import com.restaurant.quanlydatbannhahang.dao.PhuongThucTTDAO;
import com.restaurant.quanlydatbannhahang.entity.PhuongThucTT;
import java.util.List;
public class PhuongThucTTService {
    private PhuongThucTTDAO phuongThucTTDAO;
    public PhuongThucTTService() {
        this.phuongThucTTDAO = new PhuongThucTTDAO();
    }
    public List<PhuongThucTT> getAllPhuongThucTT() {
        return phuongThucTTDAO.getAllPhuongThucTT();
    }
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
    public boolean exists(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return phuongThucTTDAO.findByName(name) != null;
    }
    public int getTotalPhuongThucTT() {
        List<PhuongThucTT> list = getAllPhuongThucTT();
        return list != null ? list.size() : 0;
    }
    public PhuongThucTT getPhuongThucTienMat() {
        return PhuongThucTT.TIEN_MAT;
    }
    public PhuongThucTT getPhuongThucThe() {
        return PhuongThucTT.THE;
    }
    public PhuongThucTT getPhuongThucChuyenKhoan() {
        return PhuongThucTT.CHUYEN_KHOAN;
    }
    public boolean isTienMat(PhuongThucTT phuongThuc) {
        return phuongThuc == PhuongThucTT.TIEN_MAT;
    }
    public boolean isThe(PhuongThucTT phuongThuc) {
        return phuongThuc == PhuongThucTT.THE;
    }
    public boolean isChuyenKhoan(PhuongThucTT phuongThuc) {
        return phuongThuc == PhuongThucTT.CHUYEN_KHOAN;
    }
}