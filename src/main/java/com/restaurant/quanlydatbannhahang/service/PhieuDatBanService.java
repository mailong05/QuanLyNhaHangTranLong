package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.PhieuDatBanDAO;
import com.restaurant.quanlydatbannhahang.dao.ChiTietPhieuDatBanDAO;
import com.restaurant.quanlydatbannhahang.dao.KhachHangDAO;
import com.restaurant.quanlydatbannhahang.dao.BanDAO;
import com.restaurant.quanlydatbannhahang.entity.PhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.ChiTietPhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiPhieuDat;
import com.restaurant.quanlydatbannhahang.entity.KhachHang;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.session.SessionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PhieuDatBanService {
    private PhieuDatBanDAO phieuDatBanDAO;
    private ChiTietPhieuDatBanDAO chiTietPhieuDatBanDAO;
    private KhachHangDAO khachHangDAO;
    private BanDAO banDAO;

    public PhieuDatBanService() {
        this.phieuDatBanDAO = new PhieuDatBanDAO();
        this.chiTietPhieuDatBanDAO = new ChiTietPhieuDatBanDAO();
        this.khachHangDAO = new KhachHangDAO();
        this.banDAO = new BanDAO();
    }

    /**
     * Lấy phiếu đặt bàn theo mã
     */
    public PhieuDatBan getPhieuDatBanTheoMa(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        PhieuDatBan phieu = phieuDatBanDAO.getPhieuDatBanTheoMa(maPhieu);
        if (phieu == null) {
            System.out.println(" Không tìm thấy phiếu đặt bàn với mã: " + maPhieu);
        }
        return phieu;
    }

    /**
     * Lấy tất cả phiếu đặt bàn
     */
    public List<PhieuDatBan> getAllPhieuDatBan() {
        return phieuDatBanDAO.getAllPhieuDatBan();
    }

    /**
     * Thêm phiếu đặt bàn mới
     */
    public void themPhieuDatBan(PhieuDatBan phieu) {
        if (phieu == null) {
            throw new IllegalArgumentException("Phiếu đặt bàn không được để trống");
        }
        if (phieu.getMaPhieuDat() == null || phieu.getMaPhieuDat().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        if (phieuDatBanDAO.themPhieuDatBan(phieu)) {
            System.out.println(" Thêm phiếu đặt bàn thành công");
        } else {
            System.out.println(" Thêm phiếu đặt bàn thất bại");
        }
    }

    /**
     * Cập nhật phiếu đặt bàn
     */
    public void capNhatPhieuDatBan(PhieuDatBan phieu) {
        if (phieu == null) {
            throw new IllegalArgumentException("Phiếu đặt bàn không được để trống");
        }
        if (phieuDatBanDAO.capNhatPhieuDatBan(phieu)) {
            System.out.println(" Cập nhật phiếu đặt bàn thành công");
        } else {
            System.out.println(" Cập nhật phiếu đặt bàn thất bại");
        }
    }

    /**
     * Xóa phiếu đặt bàn
     */
    public void xoaPhieuDatBan(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        if (phieuDatBanDAO.xoaPhieuDatBan(maPhieu)) {
            System.out.println(" Xóa phiếu đặt bàn thành công");
        } else {
            System.out.println(" Xóa phiếu đặt bàn thất bại");
        }
    }

    /**
     * Lấy phiếu đặt bàn theo ngày
     */
    public List<PhieuDatBan> getPhieuDatBanTheoNgay(LocalDate ngay) {
        if (ngay == null) {
            throw new IllegalArgumentException("Ngày không được để trống");
        }
        return phieuDatBanDAO.getPhieuDatBanTheoNgay(ngay);
    }

    /**
     * Lấy phiếu đặt bàn theo trạng thái
     */
    public List<PhieuDatBan> getPhieuDatBanTheoTrangThai(TrangThaiPhieuDat trangThai) {
        if (trangThai == null) {
            throw new IllegalArgumentException("Trạng thái không được để trống");
        }
        return phieuDatBanDAO.getPhieuDatBanTheoTrangThai(trangThai);
    }

    /**
     * Cập nhật trạng thái phiếu đặt bàn
     */
    public void capNhatTrangThaiPhieu(String maPhieu, TrangThaiPhieuDat trangThai) {
        PhieuDatBan phieu = getPhieuDatBanTheoMa(maPhieu);
        if (phieu != null) {
            phieu.setTrangThai(trangThai);
            capNhatPhieuDatBan(phieu);
        }
    }

    /**
     * Xác nhận đặt bàn
     */
    public void xacNhanDatBan(String maPhieu) {
        capNhatTrangThaiPhieu(maPhieu, TrangThaiPhieuDat.DA_XAC_NHAN);
        System.out.println(" Đã xác nhận phiếu đặt bàn");
    }

    /**
     * Hủy đặt bàn
     */
    public void huyDatBan(String maPhieu) {
        capNhatTrangThaiPhieu(maPhieu, TrangThaiPhieuDat.DA_HUY);
        System.out.println(" Đã hủy phiếu đặt bàn");
    }

    /**
     * Lấy chi tiết phiếu đặt bàn
     */
    public List<ChiTietPhieuDatBan> getChiTietPhieu(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        return chiTietPhieuDatBanDAO.getChiTietByMaPhieu(maPhieu);
    }

    /**
     * Thêm chi tiết phiếu đặt bàn
     */
    public void themChiTietPhieu(ChiTietPhieuDatBan chiTiet) {
        if (chiTiet == null) {
            throw new IllegalArgumentException("Chi tiết phiếu không được để trống");
        }
        if (chiTietPhieuDatBanDAO.themChiTietPhieuDatBan(chiTiet)) {
            System.out.println(" Thêm chi tiết phiếu đặt bàn thành công");
        } else {
            System.out.println(" Thêm chi tiết phiếu đặt bàn thất bại");
        }
    }

    /**
     * Xóa chi tiết phiếu
     */
    public void xoaChiTietPhieu(String maPhieu, String maBan) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        if (maBan == null || maBan.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã bàn không được để trống");
        }
        if (chiTietPhieuDatBanDAO.xoaChiTietPhieuDatBan(maPhieu, maBan)) {
            System.out.println(" Xóa chi tiết phiếu thành công");
        } else {
            System.out.println(" Xóa chi tiết phiếu thất bại");
        }
    }

    /**
     * Tính tổng số phiếu đặt bàn
     */
    public int getTotalPhieuDatBan() {
        List<PhieuDatBan> list = getAllPhieuDatBan();
        return list != null ? list.size() : 0;
    }

    /**
     * Kiểm tra phiếu có tồn tại
     */
    public boolean existPhieu(String maPhieu) {
        return getPhieuDatBanTheoMa(maPhieu) != null;
    }

    /**
     * Tạo phiếu đặt bàn trước mới từ DatBanTruocDialog
     * Bao gồm: validate, tạo khách hàng (nếu cần), tạo phiếu, tạo chi tiết
     */
    public String taoPhieuDatBanMoi(String soDienThoai, int soLuongNguoi,
            LocalDateTime thoiGianDen, String ghiChu) {

        // ===== BƯỚC 1: VALIDATION =====
        String validationError = DatBanTruocService.validatePhieuDatBan(soDienThoai, String.valueOf(soLuongNguoi));
        if (validationError != null) {
            throw new IllegalArgumentException(validationError);
        }

        if (thoiGianDen == null) {
            throw new IllegalArgumentException("Vui lòng chọn thời gian đến!");
        }

        if (LocalDateTime.now().isAfter(thoiGianDen)) {
            throw new IllegalArgumentException("Thời gian đến không được trong quá khứ!");
        }

        try {
            // ===== BƯỚC 2: TÌM HOẶC TẠO KHÁCH HÀNG =====
            KhachHang khachHang = khachHangDAO.getKhachHangTheoSDT(soDienThoai);

            if (khachHang == null) {
                // Tạo khách hàng mới
                khachHang = new KhachHang();
                khachHang.setHoTen("Khách hàng " + soDienThoai);
                khachHang.setSdt(soDienThoai);
                khachHang.setDiemTichLuy(0);
                khachHang.setLoaiThanhVien("THUONG");
            }

            // ===== BƯỚC 3: TẠO PHIẾU ĐẶT BÀN =====
            PhieuDatBan phieuDatBan = new PhieuDatBan();
            phieuDatBan.setKhachHang(khachHang);
            phieuDatBan.setNhanVien(SessionManager.getCurrentNhanVien());
            phieuDatBan.setThoiGianDen(thoiGianDen);
            phieuDatBan.setSoLuongNguoi(soLuongNguoi);
            phieuDatBan.setGhiChu(ghiChu != null && !ghiChu.isEmpty() ? ghiChu : "");
            phieuDatBan.setTrangThai(TrangThaiPhieuDat.CHO_XAC_NHAN);

            // Lưu phiếu đặt bàn
            boolean phieuSaved = phieuDatBanDAO.themPhieuDatBan(phieuDatBan);

            if (!phieuSaved) {
                throw new RuntimeException("Không thể lưu phiếu đặt bàn!");
            }

            // Lấy mã phiếu đặt vừa tạo
            String maPhieuDat = phieuDatBan.getMaPhieuDat();
            if (maPhieuDat == null || maPhieuDat.isEmpty()) {
                // Nếu chưa có, tìm phiếu mới nhất (fallback)
                List<PhieuDatBan> allPhieus = phieuDatBanDAO.getAllPhieuDatBan();
                if (!allPhieus.isEmpty()) {
                    PhieuDatBan lastPhieu = allPhieus.get(allPhieus.size() - 1);
                    maPhieuDat = lastPhieu.getMaPhieuDat();
                }
            }

            System.out.println("✓ Tạo phiếu đặt bàn thành công! Mã: " + maPhieuDat);
            return maPhieuDat;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo phiếu đặt bàn: " + e.getMessage(), e);
        }
    }
}
