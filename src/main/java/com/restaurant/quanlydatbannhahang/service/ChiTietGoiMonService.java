package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.ChiTietGoiMonDAO;
import com.restaurant.quanlydatbannhahang.entity.ChiTietGoiMon;

import java.util.List;

public class ChiTietGoiMonService {
    private ChiTietGoiMonDAO chiTietGoiMonDAO;

    public ChiTietGoiMonService() {
        this.chiTietGoiMonDAO = new ChiTietGoiMonDAO();
    }

    /**
     * Lấy chi tiết gọi món theo phiếu
     */
    public List<ChiTietGoiMon> getChiTietByMaPhieu(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        return chiTietGoiMonDAO.getChiTietGoiMonByPhieu(maPhieu);
    }

    /**
     * Lấy chi tiết gọi món theo phiếu và món
     */
    public ChiTietGoiMon getChiTietByMaPhieuAndMaMon(String maPhieu, String maMon) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        if (maMon == null || maMon.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã món không được để trống");
        }
        return chiTietGoiMonDAO.getChiTietGoiMonTheoMa(maPhieu, maMon);
    }

    /**
     * Thêm chi tiết gọi món
     */
    public void themChiTietGoiMon(ChiTietGoiMon chiTiet) {
        if (chiTiet == null) {
            throw new IllegalArgumentException("Chi tiết gọi món không được để trống");
        }
        if (chiTiet.getSoLuong() <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
        if (!chiTietGoiMonDAO.themChiTietGoiMon(chiTiet)) {
            throw new RuntimeException("Thêm chi tiết gọi món thất bại");
        }
    }

    /**
     * Cập nhật chi tiết gọi món
     */
    public void capNhatChiTietGoiMon(ChiTietGoiMon chiTiet) {
        if (chiTiet == null) {
            throw new IllegalArgumentException("Chi tiết gọi món không được để trống");
        }
        if (!chiTietGoiMonDAO.capNhatChiTietGoiMon(chiTiet)) {
            throw new RuntimeException("Cập nhật chi tiết gọi món thất bại");
        }
    }

    /**
     * Xóa chi tiết gọi món
     */
    public void xoaChiTietGoiMon(String maPhieu, String maMon) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        if (maMon == null || maMon.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã món không được để trống");
        }
        if (!chiTietGoiMonDAO.xoaChiTietGoiMon(maPhieu, maMon)) {
            throw new RuntimeException("Xóa chi tiết gọi món thất bại");
        }
    }

    /**
     * Cập nhật số lượng
     */
    public void capNhatSoLuong(String maPhieu, String maMon, int soLuongMoi) {
        if (soLuongMoi <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
        ChiTietGoiMon chiTiet = getChiTietByMaPhieuAndMaMon(maPhieu, maMon);
        if (chiTiet != null) {
            chiTiet.setSoLuong(soLuongMoi);
            capNhatChiTietGoiMon(chiTiet);
        }
    }

    /**
     * Cập nhật trạng thái chi tiết
     */
    public void capNhatTrangThai(String maPhieu, String maMon, String trangThai) {
        if (!chiTietGoiMonDAO.capNhatTrangThaiChiTietGoiMon(maPhieu, maMon, trangThai)) {
            throw new RuntimeException("Cập nhật trạng thái thất bại");
        }
    }

    /**
     * Đánh dấu hoàn thành
     */
    public void markDone(String maPhieu, String maMon) {
        capNhatTrangThai(maPhieu, maMon, "DA_XONG");
    }

    /**
     * Đánh dấu đang nấu
     */
    public void markCooking(String maPhieu, String maMon) {
        capNhatTrangThai(maPhieu, maMon, "DANG_NAU");
    }

    /**
     * Tính tổng tiền
     */
    public double getTongTienChiTiet(String maPhieu, String maMon) {
        ChiTietGoiMon chiTiet = getChiTietByMaPhieuAndMaMon(maPhieu, maMon);
        if (chiTiet != null) {
            return chiTiet.getDonGiaLuuTru() * chiTiet.getSoLuong();
        }
        return 0;
    }

    /**
     * Tính tổng tiền phiếu gọi món
     */
    public double getTongTienPhieu(String maPhieu) {
        List<ChiTietGoiMon> list = getChiTietByMaPhieu(maPhieu);
        double tongTien = 0;
        if (list != null) {
            for (ChiTietGoiMon chiTiet : list) {
                tongTien += chiTiet.getDonGiaLuuTru() * chiTiet.getSoLuong();
            }
        }
        return tongTien;
    }

    /**
     * Đếm tổng số chi tiết trong phiếu
     */
    public int countChiTietInPhieu(String maPhieu) {
        List<ChiTietGoiMon> list = getChiTietByMaPhieu(maPhieu);
        return list != null ? list.size() : 0;
    }

    /**
     * Xóa tất cả chi tiết của phiếu
     */
    public void xoaAllChiTietByMaPhieu(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        if (!chiTietGoiMonDAO.xoaAllChiTietByMaPhieu(maPhieu)) {
            throw new RuntimeException("Xóa tất cả chi tiết thất bại");
        }
    }
}
