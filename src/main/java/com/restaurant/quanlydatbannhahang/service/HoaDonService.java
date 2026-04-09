package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.HoaDonDAO;
import com.restaurant.quanlydatbannhahang.dao.ChiTietHoaDonDAO;
import com.restaurant.quanlydatbannhahang.entity.HoaDon;
import com.restaurant.quanlydatbannhahang.entity.ChiTietHoaDon;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiHoaDon;

import java.util.List;

public class HoaDonService {
    private HoaDonDAO hoaDonDAO;
    private ChiTietHoaDonDAO chiTietHoaDonDAO;

    public HoaDonService() {
        this.hoaDonDAO = new HoaDonDAO();
        this.chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    }

    /**
     * Lấy hóa đơn theo mã
     */
    public HoaDon getHoaDonTheoMa(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        HoaDon hoaDon = hoaDonDAO.getHoaDonTheoMa(maHD);
        if (hoaDon == null) {
            System.out.println(" Không tìm thấy hóa đơn với mã: " + maHD);
        }
        return hoaDon;
    }

    /**
     * Lấy tất cả hóa đơn
     */
    public List<HoaDon> getAllHoaDon() {
        return hoaDonDAO.getAllHoaDon();
    }

    /**
     * Thêm hóa đơn mới
     */
    public void themHoaDon(HoaDon hoaDon) {
        if (hoaDon == null) {
            throw new IllegalArgumentException("Hóa đơn không được để trống");
        }
        if (hoaDon.getMaHD() == null || hoaDon.getMaHD().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (hoaDonDAO.themHoaDon(hoaDon)) {
            System.out.println(" Thêm hóa đơn thành công");
        } else {
            System.out.println(" Thêm hóa đơn thất bại");
        }
    }

    /**
     * Cập nhật hóa đơn
     */
    public void capNhatHoaDon(HoaDon hoaDon) {
        if (hoaDon == null) {
            throw new IllegalArgumentException("Hóa đơn không được để trống");
        }
        if (hoaDonDAO.capNhatHoaDon(hoaDon)) {
            System.out.println(" Cập nhật hóa đơn thành công");
        } else {
            System.out.println(" Cập nhật hóa đơn thất bại");
        }
    }

    /**
     * Xóa hóa đơn
     */
    public void xoaHoaDon(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (hoaDonDAO.xoaHoaDon(maHD)) {
            System.out.println(" Xóa hóa đơn thành công");
        } else {
            System.out.println(" Xóa hóa đơn thất bại");
        }
    }

    /**
     * Lấy hóa đơn theo bàn
     */
    public List<HoaDon> getHoaDonTheoMaBan(String maBan) {
        if (maBan == null || maBan.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã bàn không được để trống");
        }
        return hoaDonDAO.getHoaDonTheoMaBan(maBan);
    }

    /**
     * Lấy danh sách hóa đơn theo trạng thái
     */
    public List<HoaDon> getHoaDonTheoTrangThai(TrangThaiHoaDon trangThai) {
        if (trangThai == null) {
            throw new IllegalArgumentException("Trạng thái không được để trống");
        }
        return hoaDonDAO.getHoaDonTheoTrangThai(trangThai);
    }

    /**
     * Cập nhật trạng thái hóa đơn
     */
    public void capNhatTrangThaiHoaDon(String maHD, TrangThaiHoaDon trangThai) {
        HoaDon hoaDon = getHoaDonTheoMa(maHD);
        if (hoaDon != null) {
            hoaDon.setTrangThaiThanhToan(trangThai);
            capNhatHoaDon(hoaDon);
        }
    }

    /**
     * Thêm chi tiết hóa đơn
     */
    public void themChiTietHoaDon(ChiTietHoaDon chiTiet) {
        if (chiTiet == null) {
            throw new IllegalArgumentException("Chi tiết hóa đơn không được để trống");
        }
        if (chiTietHoaDonDAO.themChiTietHoaDon(chiTiet)) {
            System.out.println(" Thêm chi tiết hóa đơn thành công");
        } else {
            System.out.println(" Thêm chi tiết hóa đơn thất bại");
        }
    }

    /**
     * Lấy chi tiết hóa đơn theo mã hóa đơn
     */
    public List<ChiTietHoaDon> getChiTietHoaDon(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        return chiTietHoaDonDAO.getChiTietByMaHD(maHD);
    }

    /**
     * Tính tổng tiền hóa đơn
     */
    public double getTongTienHoaDon(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        return chiTietHoaDonDAO.getTongTienHoaDon(maHD);
    }

    /**
     * Xóa chi tiết hóa đơn
     */
    public void xoaChiTietHoaDon(String maHD, String maMon) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (maMon == null || maMon.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã món không được để trống");
        }
        if (chiTietHoaDonDAO.xoaChiTietHoaDon(maHD, maMon)) {
            System.out.println(" Xóa chi tiết hóa đơn thành công");
        } else {
            System.out.println(" Xóa chi tiết hóa đơn thất bại");
        }
    }

    /**
     * Thanh toán hóa đơn
     */
    public void thanhToanHoaDon(String maHD) {
        capNhatTrangThaiHoaDon(maHD, TrangThaiHoaDon.DA_THANH_TOAN);
        System.out.println(" Hóa đơn đã được thanh toán");
    }

    /**
     * Tính tổng số hóa đơn
     */
    public int getTotalHoaDon() {
        List<HoaDon> list = getAllHoaDon();
        return list != null ? list.size() : 0;
    }
}
