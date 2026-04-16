package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.ChiTietPhieuDatBanDAO;
import com.restaurant.quanlydatbannhahang.entity.ChiTietPhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.entity.PhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

public class ChiTietPhieuDatBanService {
    private ChiTietPhieuDatBanDAO chiTietPhieuDatBanDAO;

    public ChiTietPhieuDatBanService() {
        this.chiTietPhieuDatBanDAO = new ChiTietPhieuDatBanDAO();
    }

    /**
     * Lấy chi tiết phiếu đặt bàn theo mã phiếu
     */
    public List<ChiTietPhieuDatBan> getChiTietByMaPhieuDat(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        return chiTietPhieuDatBanDAO.getChiTietByMaPhieuDat(maPhieu);
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
    public void xoaAllChiTietByMaPhieuDat(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        if (chiTietPhieuDatBanDAO.xoaAllChiTietByMaPhieuDat(maPhieu)) {
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
        List<ChiTietPhieuDatBan> list = getChiTietByMaPhieuDat(maPhieu);
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
        List<ChiTietPhieuDatBan> list = getChiTietByMaPhieuDat(maPhieu);
        return list != null ? list.size() : 0;
    }

    public List<ChiTietPhieuDatBan> getAll() {
        // TODO Auto-generated method stub
        return chiTietPhieuDatBanDAO.getAllChiTiet();
    }

    /**
     * Business logic: Cập nhật danh sách bàn trong phiếu
     * Validate + thêm bàn mới + xóa bàn cũ
     * Throw IllegalArgumentException nếu có lỗi validation
     * 
     * @param maPDB     Mã phiếu đặt bàn
     * @param oldBanSet Danh sách bàn cũ
     * @param newBanSet Danh sách bàn mới
     * @throws IllegalArgumentException nếu validation fail
     */
    public void updateBanInPhieu(String maPDB, Set<String> oldBanSet, Set<String> newBanSet)
            throws IllegalArgumentException {
        if (maPDB == null || maPDB.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        if (newBanSet == null || newBanSet.isEmpty()) {
            throw new IllegalArgumentException("Phải chọn ít nhất một bàn");
        }

        // Tính toán bàn cần thêm và xóa
        Set<String> banCanThem = new HashSet<>(newBanSet);
        banCanThem.removeAll(oldBanSet);

        Set<String> banCanXoa = new HashSet<>(oldBanSet);
        banCanXoa.removeAll(newBanSet);

        // Lấy phiếu và chi tiết hiện tại
        PhieuDatBanService pdbService = new PhieuDatBanService();
        PhieuDatBan phieu = pdbService.getPhieuDatBanTheoMa(maPDB);
        if (phieu == null) {
            throw new IllegalArgumentException("Phiếu đặt bàn không tồn tại");
        }

        List<ChiTietPhieuDatBan> chiTietList = getChiTietByMaPhieuDat(maPDB);
        Map<String, ChiTietPhieuDatBan> chiTietMap = new HashMap<>();
        if (chiTietList != null) {
            for (ChiTietPhieuDatBan ct : chiTietList) {
                chiTietMap.put(ct.getBan().getMaBan(), ct);
            }
        }

        BanService banService = new BanService();

        // ===== VALIDATION PHASE: Kiểm tra tất cả bàn cần thêm =====
        for (String maBan : banCanThem) {
            // Check duplicate
            if (chiTietMap.containsKey(maBan)) {
                throw new IllegalArgumentException("Bàn " + maBan + " đã tồn tại trong phiếu này");
            }

            // Check bàn tồn tại
            Ban ban = banService.getBanTheoMa(maBan);
            if (ban == null) {
                throw new IllegalArgumentException("Bàn " + maBan + " không tồn tại");
            }

            // Check trạng thái bàn
            if (ban.getTrangThai() == TrangThaiBan.DA_DAT
                    || ban.getTrangThai() == TrangThaiBan.DANG_DUNG) {
                // Nếu là bàn của phiếu hiện tại thì OK
                if (!oldBanSet.contains(maBan)) {
                    throw new IllegalArgumentException("Bàn " + maBan + " đang được sử dụng hoặc đã đặt");
                }
            }
        }

        // ===== EXECUTION PHASE: Validation pass, thực hiện thêm/xóa =====
        // 1. Thêm bàn mới
        for (String maBan : banCanThem) {
            Ban ban = banService.getBanTheoMa(maBan);
            ChiTietPhieuDatBan chiTietMoi = new ChiTietPhieuDatBan();
            chiTietMoi.setPhieuDatBan(phieu);
            chiTietMoi.setBan(ban);
            chiTietMoi.setGhiChu("");
            themChiTietPhieuDatBan(chiTietMoi);
        }

        // 2. Xóa bàn cũ
        for (String maBan : banCanXoa) {
            xoaChiTietPhieuDatBan(maPDB, maBan);
        }
    }
}
