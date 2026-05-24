package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.CaLamViecDAO;
import com.restaurant.quanlydatbannhahang.entity.CaLamViec;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiCa;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;

import java.time.LocalDateTime;

public class CaLamViecService {
    private CaLamViecDAO caLamViecDAO;

    public CaLamViecService() {
        this.caLamViecDAO = new CaLamViecDAO();
    }

    private void validateCaLamViec(CaLamViec ca) {
        if (ca == null) {
            throw new IllegalArgumentException("Ca làm việc không được để trống");
        }
        if (ca.getMaCa() == null || ca.getMaCa().isBlank()) {
            throw new IllegalArgumentException("Mã ca không được để trống");
        }
        if (ca.getNhanVien() == null || ca.getNhanVien().getMaNV() == null || ca.getNhanVien().getMaNV().isBlank()) {
            throw new IllegalArgumentException("Mã nhân viên của ca làm việc không được để trống");
        }
        if (ca.getThoiGianVaoCa() == null) {
            throw new IllegalArgumentException("Thời gian vào ca không được để trống");
        }
        if (ca.getTienDauCa() < 0) {
            throw new IllegalArgumentException("Tiền đầu ca không được âm");
        }
        if (ca.getTrangThai() == null) {
            throw new IllegalArgumentException("Trạng thái ca làm việc không được để trống");
        }
    }

    public String getLastMaCa() {
        return caLamViecDAO.getLastMaCa();
    }

    public String generateNextMaCa() {
        String lastMaCa = getLastMaCa();
        if (lastMaCa == null || lastMaCa.isBlank()) {
            return "CA001";
        }
        String numberPart = lastMaCa.replaceAll("[^0-9]", "");
        try {
            int nextNumber = Integer.parseInt(numberPart) + 1;
            return String.format("CA%03d", nextNumber);
        } catch (NumberFormatException e) {
            return "CA001";
        }
    }

    public void themCaLamViec(CaLamViec ca) {
        validateCaLamViec(ca);
        if (!caLamViecDAO.themCaLamViec(ca)) {
            throw new RuntimeException("Thêm ca làm việc thất bại");
        }
    }

    public void capNhatCaLamViec(CaLamViec ca) {
        validateCaLamViec(ca);
        if (!caLamViecDAO.capNhatCaLamViec(ca)) {
            throw new RuntimeException("Cập nhật ca làm việc thất bại");
        }
    }

    public CaLamViec getCaLamViecHienTaiCuaNhanVien(String maNV) {
        if (maNV == null || maNV.isBlank()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống");
        }
        return caLamViecDAO.getCaLamViecHienTaiCuaNhanVien(maNV);
    }
}