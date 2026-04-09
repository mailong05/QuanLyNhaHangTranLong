package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.PhieuGoiMonDAO;
import com.restaurant.quanlydatbannhahang.dao.ChiTietGoiMonDAO;
import com.restaurant.quanlydatbannhahang.entity.PhieuGoiMon;
import com.restaurant.quanlydatbannhahang.entity.ChiTietGoiMon;

import java.util.List;

public class PhieuGoiMonService {
    private PhieuGoiMonDAO phieuGoiMonDAO;
    private ChiTietGoiMonDAO chiTietGoiMonDAO;

    public PhieuGoiMonService() {
        this.phieuGoiMonDAO = new PhieuGoiMonDAO();
        this.chiTietGoiMonDAO = new ChiTietGoiMonDAO();
    }

    /**
     * Lấy phiếu gọi món theo mã
     */
    public PhieuGoiMon getPhieuGoiMonTheoMa(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu gọi món không được để trống");
        }
        PhieuGoiMon phieu = phieuGoiMonDAO.getPhieuGoiMonTheoMa(maPhieu);
        if (phieu == null) {
            System.out.println(" Không tìm thấy phiếu gọi món với mã: " + maPhieu);
        }
        return phieu;
    }

    /**
     * Lấy tất cả phiếu gọi món
     */
    public List<PhieuGoiMon> getAllPhieuGoiMon() {
        return phieuGoiMonDAO.getAllPhieuGoiMon();
    }

    /**
     * Thêm phiếu gọi món mới
     */
    public void themPhieuGoiMon(PhieuGoiMon phieu) {
        if (phieu == null) {
            throw new IllegalArgumentException("Phiếu gọi món không được để trống");
        }
        if (phieu.getMaPhieuGoi() == null || phieu.getMaPhieuGoi().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu gọi món không được để trống");
        }
        if (phieuGoiMonDAO.themPhieuGoiMon(phieu)) {
            System.out.println(" Thêm phiếu gọi món thành công");
        } else {
            System.out.println(" Thêm phiếu gọi món thất bại");
        }
    }

    /**
     * Cập nhật phiếu gọi món
     */
    public void capNhatPhieuGoiMon(PhieuGoiMon phieu) {
        if (phieu == null) {
            throw new IllegalArgumentException("Phiếu gọi món không được để trống");
        }
        if (phieuGoiMonDAO.capNhatPhieuGoiMon(phieu)) {
            System.out.println(" Cập nhật phiếu gọi món thành công");
        } else {
            System.out.println(" Cập nhật phiếu gọi món thất bại");
        }
    }

    /**
     * Xóa phiếu gọi món
     */
    public void xoaPhieuGoiMon(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu gọi món không được để trống");
        }
        if (phieuGoiMonDAO.xoaPhieuGoiMon(maPhieu)) {
            System.out.println(" Xóa phiếu gọi món thành công");
        } else {
            System.out.println(" Xóa phiếu gọi món thất bại");
        }
    }

    /**
     * Lấy danh sách phiếu gọi món theo bàn
     */
    public java.util.List<PhieuGoiMon> getPhieuGoiMonTheoMaBan(String maBan) {
        if (maBan == null || maBan.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã bàn không được để trống");
        }
        return phieuGoiMonDAO.getPhieuGoiMonTheoBan(maBan);
    }

    /**
     * Lấy chi tiết gọi món theo phiếu
     */
    public List<ChiTietGoiMon> getChiTietGoiMon(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu gọi món không được để trống");
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
        if (chiTietGoiMonDAO.themChiTietGoiMon(chiTiet)) {
            System.out.println(" Thêm chi tiết gọi món thành công");
        } else {
            System.out.println(" Thêm chi tiết gọi món thất bại");
        }
    }

    /**
     * Xóa chi tiết gọi món
     */
    public void xoaChiTietGoiMon(String maPhieu, String maMon) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu gọi món không được để trống");
        }
        if (maMon == null || maMon.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã món không được để trống");
        }
        if (chiTietGoiMonDAO.xoaChiTietGoiMon(maPhieu, maMon)) {
            System.out.println(" Xóa chi tiết gọi món thành công");
        } else {
            System.out.println(" Xóa chi tiết gọi món thất bại");
        }
    }

    /**
     * Cập nhật trạng thái chi tiết gọi món
     */
    public void capNhatTrangThaiChiTiet(String maPhieu, String maMon, String trangThai) {
        ChiTietGoiMon chiTiet = getChiTietByMaPhieuAndMaMon(maPhieu, maMon);
        if (chiTiet != null && chiTietGoiMonDAO.capNhatChiTietGoiMon(chiTiet)) {
            System.out.println(" Cập nhật trạng thái thành công");
        } else {
            System.out.println(" Cập nhật trạng thái thất bại");
        }
    }

    /**
     * Đánh dấu món đã xong
     */
    public void markDone(String maPhieu, String maMon) {
        capNhatTrangThaiChiTiet(maPhieu, maMon, "DA_XON");
    }

    /**
     * Tính tổng số phiếu gọi món
     */
    public int getTotalPhieuGoiMon() {
        List<PhieuGoiMon> list = getAllPhieuGoiMon();
        return list != null ? list.size() : 0;
    }

    /**
     * Kiểm tra phiếu có tồn tại
     */
    public boolean existPhieu(String maPhieu) {
        return getPhieuGoiMonTheoMa(maPhieu) != null;
    }
}
