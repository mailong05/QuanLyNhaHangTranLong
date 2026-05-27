package com.restaurant.quanlydatbannhahang.service;
import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.dao.ChiTietPhieuDatBanDAO;
import com.restaurant.quanlydatbannhahang.entity.ChiTietPhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.entity.PhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class ChiTietPhieuDatBanService {
    private ChiTietPhieuDatBanDAO chiTietPhieuDatBanDAO;
    public ChiTietPhieuDatBanService() {
        this.chiTietPhieuDatBanDAO = new ChiTietPhieuDatBanDAO();
    }
    public List<ChiTietPhieuDatBan> getChiTietByMaPhieuDat(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        return chiTietPhieuDatBanDAO.getChiTietByMaPhieuDat(maPhieu);
    }
    public void themChiTietPhieuDatBan(ChiTietPhieuDatBan chiTiet) {
        if (chiTiet == null) {
            throw new IllegalArgumentException("Chi tiết phiếu đặt bàn không được để trống");
        }
        if (chiTiet.getPhieuDatBan() == null || chiTiet.getBan() == null) {
            throw new IllegalArgumentException("Phiếu đặt bàn và bàn không được để trống");
        }
        if (!chiTietPhieuDatBanDAO.themChiTietPhieuDatBan(chiTiet)) {
            throw new RuntimeException("Thêm chi tiết phiếu đặt bàn thất bại");
        }
    }
    public void capNhatChiTietPhieuDatBan(ChiTietPhieuDatBan chiTiet) {
        if (chiTiet == null) {
            throw new IllegalArgumentException("Chi tiết phiếu đặt bàn không được để trống");
        }
        if (!chiTietPhieuDatBanDAO.capNhatChiTietPhieuDatBan(chiTiet)) {
            throw new RuntimeException("Cập nhật chi tiết phiếu đặt bàn thất bại");
        }
    }
    public void xoaChiTietPhieuDatBan(String maPhieu, String maBan) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        if (maBan == null || maBan.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã bàn không được để trống");
        }
        if (!chiTietPhieuDatBanDAO.xoaChiTietPhieuDatBan(maPhieu, maBan)) {
            throw new RuntimeException("Xóa chi tiết phiếu đặt bàn thất bại");
        }
    }
    public void xoaAllChiTietByMaPhieuDat(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        if (!chiTietPhieuDatBanDAO.xoaAllChiTietByMaPhieuDat(maPhieu)) {
            throw new RuntimeException("Xóa tất cả chi tiết phiếu thất bại");
        }
    }
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
        throw new RuntimeException("Không tìm thấy chi tiết phiếu với mã bàn: " + maBan);
    }
    public int countBanInPhieu(String maPhieu) {
        List<ChiTietPhieuDatBan> list = getChiTietByMaPhieuDat(maPhieu);
        return list != null ? list.size() : 0;
    }
    public List<ChiTietPhieuDatBan> getAll() {
        return chiTietPhieuDatBanDAO.getAllChiTiet();
    }
    public void updateBanInPhieu(String maPDB, Set<String> oldBanSet, Set<String> newBanSet)
            throws IllegalArgumentException {
        if (maPDB == null || maPDB.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        if (newBanSet == null || newBanSet.isEmpty()) {
            throw new IllegalArgumentException("Phải chọn ít nhất một bàn");
        }
        Set<String> banCanThem = new HashSet<>(newBanSet);
        banCanThem.removeAll(oldBanSet);
        Set<String> banCanXoa = new HashSet<>(oldBanSet);
        banCanXoa.removeAll(newBanSet);
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
        for (String maBan : banCanThem) {
            if (chiTietMap.containsKey(maBan)) {
                throw new IllegalArgumentException("Bàn " + maBan + " đã tồn tại trong phiếu này");
            }
            Ban ban = banService.getBanTheoMa(maBan);
            if (ban == null) {
                throw new IllegalArgumentException("Bàn " + maBan + " không tồn tại");
            }
            
            if (pdbService.kiemTraBanCoPhieuDangSuDung(maBan)) {
                throw new IllegalArgumentException("Bàn " + maBan + " hiện đang có khách ngồi thực tế.");
            }
            
            java.time.LocalDate ngayCuaPhieu = phieu.getThoiGianDen().toLocalDate();
            if (pdbService.kiemTraBanDaDuocDatTrongNgay(maBan, ngayCuaPhieu, maPDB)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                throw new IllegalArgumentException("Bàn " + maBan + " đã được đặt lịch bởi người khác vào ngày " 
                        + ngayCuaPhieu.format(formatter));
            }
            
        }
        for (String maBan : banCanThem) {
            Ban ban = banService.getBanTheoMa(maBan);
            ChiTietPhieuDatBan chiTietMoi = new ChiTietPhieuDatBan();
            chiTietMoi.setPhieuDatBan(phieu);
            chiTietMoi.setBan(ban);
            chiTietMoi.setGhiChu("");
            themChiTietPhieuDatBan(chiTietMoi);
        }
        for (String maBan : banCanXoa) {
            xoaChiTietPhieuDatBan(maPDB, maBan);
        }
    }
    public String getDanhSachBanByMaPhieuDat(String maPhieuDat) {
        if (maPhieuDat == null || maPhieuDat.isBlank()) {
            return "";
        }
        List<String> dsMaBan = chiTietPhieuDatBanDAO.getMaBansByPhieu(maPhieuDat);
        if (dsMaBan == null || dsMaBan.isEmpty()) {
            return "";
        }
        return String.join(", ", dsMaBan);
    }
    
    public void gopBanVaoPhieu(String maPDB, Set<String> oldBanSet, Set<String> newBanSet) {
        Set<String> banCanThem = new HashSet<>(newBanSet);
        banCanThem.removeAll(oldBanSet);
        
        PhieuDatBanService pdbService = new PhieuDatBanService();
        PhieuDatBan phieu = pdbService.getPhieuDatBanTheoMa(maPDB);
        if (phieu == null) throw new IllegalArgumentException("Phiếu đặt bàn gốc không tồn tại");
        
        BanService banService = new BanService();
        for (String maBan : banCanThem) {
            Ban ban = banService.getBanTheoMa(maBan);
            ChiTietPhieuDatBan chiTietMoi = new ChiTietPhieuDatBan();
            chiTietMoi.setPhieuDatBan(phieu);
            chiTietMoi.setBan(ban);
            chiTietMoi.setGhiChu("Bàn được gộp thêm");
            themChiTietPhieuDatBan(chiTietMoi);
        }
    }
}