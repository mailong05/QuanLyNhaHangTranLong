package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.ChiTietPhieuDatBanDAO;
import com.restaurant.quanlydatbannhahang.entity.ChiTietPhieuDatBan;

import java.util.List;

public class ChiTietPhieuDatBanService {
    private ChiTietPhieuDatBanDAO chiTietPhieuDatBanDAO;

    public ChiTietPhieuDatBanService() {
        this.chiTietPhieuDatBanDAO = new ChiTietPhieuDatBanDAO();
    }

    /**
     * Lấy chi tiết phiếu đặt bàn theo mã phiếu
     */
    public List<ChiTietPhieuDatBan> getChiTietByMaPhieu(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        return chiTietPhieuDatBanDAO.getChiTietByMaPhieu(maPhieu);
    }

    /**
     * Thêm chi tiết phiếu đặt bàn
     */
    public void themChiTietPhieuDatBan(ChiTietPhieuDatBan chiTiet) {
        if (chiTiet == null) {
            throw new IllegalArgumentException("Chi tiết phiếu đặt bàn không được để trống");
        }
        if (chiTiet.getPhieuDatBan() == null || chiTiet.getBan() == null) {
            throw new IllegalArgumentException("Phiếu đặt bàn và bàn không được để trống");
        }
        if (chiTietPhieuDatBanDAO.themChiTietPhieuDatBan(chiTiet)) {
            System.out.println(" Thêm chi tiết phiếu đặt bàn thành công");
        } else {
            System.out.println(" Thêm chi tiết phiếu đặt bàn thất bại");
        }
    }

    /**
     * Cập nhật chi tiết phiếu đặt bàn
     */
    public void capNhatChiTietPhieuDatBan(ChiTietPhieuDatBan chiTiet) {
        if (chiTiet == null) {
            throw new IllegalArgumentException("Chi tiết phiếu đặt bàn không được để trống");
        }
        if (chiTietPhieuDatBanDAO.capNhatChiTietPhieuDatBan(chiTiet)) {
            System.out.println(" Cập nhật chi tiết phiếu đặt bàn thành công");
        } else {
            System.out.println(" Cập nhật chi tiết phiếu đặt bàn thất bại");
        }
    }

    /**
     * Xóa chi tiết phiếu đặt bàn
     */
    public void xoaChiTietPhieuDatBan(String maPhieu, String maBan) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        if (maBan == null || maBan.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã bàn không được để trống");
        }
        if (chiTietPhieuDatBanDAO.xoaChiTietPhieuDatBan(maPhieu, maBan)) {
            System.out.println(" Xóa chi tiết phiếu đặt bàn thành công");
        } else {
            System.out.println(" Xóa chi tiết phiếu đặt bàn thất bại");
        }
    }

    /**
     * Xóa tất cả chi tiết của phiếu
     */
    public void xoaAllChiTietByMaPhieu(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        if (chiTietPhieuDatBanDAO.xoaAllChiTietByMaPhieu(maPhieu)) {
            System.out.println(" Xóa tất cả chi tiết phiếu thành công");
        } else {
            System.out.println(" Xóa tất cả chi tiết phiếu thất bại");
        }
    }

    /**
     * Cập nhật số khách
     */
    /**
     * Cập nhật ghi chú
     */
    public void capNhatGhiChu(String maPhieu, String maBan, String ghiChuMoi) {
        List<ChiTietPhieuDatBan> list = getChiTietByMaPhieu(maPhieu);
        if (list != null) {
            for (ChiTietPhieuDatBan chiTiet : list) {
                if (chiTiet.getBan().getMaBan().equals(maBan)) {
                    chiTiet.setGhiChu(ghiChuMoi);
                    capNhatChiTietPhieuDatBan(chiTiet);
                    return;
                }
            }
        }
        System.out.println(" Không tìm thấy chi tiết phiếu với mã bàn: " + maBan);
    }

    /**
     * Đếm tổng số bàn trong phiếu
     */
    public int countBanInPhieu(String maPhieu) {
        List<ChiTietPhieuDatBan> list = getChiTietByMaPhieu(maPhieu);
        return list != null ? list.size() : 0;
    }
}
