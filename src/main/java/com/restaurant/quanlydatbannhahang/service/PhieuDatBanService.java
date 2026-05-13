package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.PhieuDatBanDAO;
import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.dao.ChiTietPhieuDatBanDAO;
import com.restaurant.quanlydatbannhahang.entity.PhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.ChiTietPhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.KhachHang;
import com.restaurant.quanlydatbannhahang.entity.LoaiThanhVien;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiPhieuDat;
import com.restaurant.quanlydatbannhahang.session.SessionManager;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.util.IDGeneratorHelper;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class PhieuDatBanService {
    private PhieuDatBanDAO phieuDatBanDAO;
    private ChiTietPhieuDatBanDAO chiTietPhieuDatBanDAO;
    private BanService banService;
    private NhanVienService nhanVienService;
    private static final String PHONE_PATTERN = "^0[1-9]\\d{8}$";
    private static final String HOTEN_PATTERN = "^[\\p{Lu}][\\p{Ll}]*(\\s[\\p{Lu}][\\p{Ll}]*)*$";
    private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);
    private static final Pattern hoTenPattern = Pattern.compile(HOTEN_PATTERN);

    public PhieuDatBanService() {
        this.phieuDatBanDAO = new PhieuDatBanDAO();
        this.chiTietPhieuDatBanDAO = new ChiTietPhieuDatBanDAO();
        this.banService = new BanService();
        this.nhanVienService = new NhanVienService();
    }

    public String taoPhieuDatDungNgay(Set<String> dsMaBan, String maNV) {
        if (dsMaBan == null || dsMaBan.isEmpty()) {
            throw new IllegalArgumentException("Phải chọn ít nhất một bàn để bắt đầu phục vụ");
        }
        String lastID = phieuDatBanDAO.getLastPhieuDatBanID();
        String maPhieuMoi = IDGeneratorHelper.generateNextIDFromFullID(lastID);
        PhieuDatBan phieu = new PhieuDatBan();
        phieu.setMaPhieuDat(maPhieuMoi);
        phieu.setKhachHang(null);
        phieu.setNhanVien(nhanVienService.getNhanVienTheoMa(maNV));
        phieu.setThoiGianDen(LocalDateTime.now());
        phieu.setSoLuongNguoi(0);
        phieu.setTrangThai(TrangThaiPhieuDat.DANG_SU_DUNG);
        if (!phieuDatBanDAO.themPhieuDatBan(phieu)) {
            throw new RuntimeException("Không thể khởi tạo phiếu đặt bàn cho khách");
        }
        for (String maBan : dsMaBan) {
            ChiTietPhieuDatBan ct = new ChiTietPhieuDatBan();
            ct.setPhieuDatBan(phieu);
            ct.setBan(banService.getBanTheoMa(maBan));
            chiTietPhieuDatBanDAO.themChiTietPhieuDatBan(ct);
            banService.capNhatTrangThaiBan(maBan, TrangThaiBan.DANG_DUNG);
        }
        return maPhieuMoi;
    }

    public void validatePhieuDatBan(PhieuDatBan phieu) {
        if (phieu == null) {
            throw new IllegalArgumentException("Dữ liệu phiếu đặt không được để trống");
        }
        if (phieu.getTrangThai() == TrangThaiPhieuDat.DANG_CHO) {
            if (phieu.getKhachHang() == null) {
                throw new IllegalArgumentException("Luồng đặt trước yêu cầu thông tin khách hàng");
            }
            String hoTen = phieu.getKhachHang().getHoTen();
            String sdt = phieu.getKhachHang().getSdt();
            if (hoTen == null || hoTen.isBlank()) {
                throw new IllegalArgumentException("Họ tên khách hàng không được để trống");
            }
            if (!hoTenPattern.matcher(hoTen).matches()) {
                throw new IllegalArgumentException("Tên phải viết hoa chữ cái đầu (VD: Nguyễn Văn A)");
            }
            if (sdt == null || sdt.isBlank()) {
                throw new IllegalArgumentException("Số điện thoại không được để trống");
            }
            if (!phonePattern.matcher(sdt).matches()) {
                throw new IllegalArgumentException("Số điện thoại phải đủ 10 số và bắt đầu bằng số 0");
            }
            if (phieu.getTienDatCoc() < 0) {
                throw new IllegalArgumentException("Tiền đặt cọc phải >= 0");
            }
            if (phieu.getThoiGianDen() == null || phieu.getThoiGianDen().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("Thời gian đến phải ở tương lai");
            }
        }
        if (phieu.getSoLuongNguoi() < 0) {
            throw new IllegalArgumentException("Số lượng người không được là số âm");
        }
    }

    public void capNhatKhachHangChoPhieu(String maPhieu, String maKH) {
        if (maPhieu == null || maPhieu.isBlank()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        boolean success = phieuDatBanDAO.capNhatKhachHangChoPhieu(maPhieu, maKH);
        if (!success) {
            throw new RuntimeException("Cập nhật khách hàng vào phiếu thất bại");
        }
    }

    public void hoanTatPhieuDatBan(String maPhieu) {
        phieuDatBanDAO.capNhatTrangThaiPhieu(maPhieu, TrangThaiPhieuDat.DA_SU_DUNG);
        List<ChiTietPhieuDatBan> chiTiets = chiTietPhieuDatBanDAO.getChiTietByMaPhieuDat(maPhieu);
        for (ChiTietPhieuDatBan ct : chiTiets) {
            banService.capNhatTrangThaiBan(ct.getBan().getMaBan(), TrangThaiBan.TRONG);
        }
    }

    public PhieuDatBan getPhieuDatBanTheoMa(String maPhieu) {
        if (maPhieu == null || maPhieu.isBlank()) {
            throw new IllegalArgumentException("Mã phiếu không được rỗng");
        }
        return phieuDatBanDAO.getPhieuDatBanTheoMa(maPhieu);
    }

    public PhieuDatBan getActivePhieuDatByBan(String maBan) {
        if (maBan == null || maBan.isBlank()) {
            return null;
        }
        List<PhieuDatBan> dsPhieu = phieuDatBanDAO.getPhieuDatBanTheoBan(maBan);
        for (PhieuDatBan phieu : dsPhieu) {
            if (phieu != null && (phieu.getTrangThai() == TrangThaiPhieuDat.DANG_CHO
                    || phieu.getTrangThai() == TrangThaiPhieuDat.DANG_SU_DUNG)) {
                return phieu;
            }
        }
        return null;
    }

    public List<String> getDanhSachBanTrongTheoThoiGian(LocalDateTime selectedTime) {
        List<String> availableBan = new ArrayList<>();
        if (selectedTime == null) {
            return availableBan;
        }
        java.time.LocalDate selectedDate = selectedTime.toLocalDate();
        List<Ban> allBan = banService.getAllBan();
        for (Ban ban : allBan) {
            if (ban == null || ban.getMaBan() == null) {
                continue;
            }
            if (ban.getTrangThai() == TrangThaiBan.TRONG) {
                availableBan.add(ban.getMaBan());
            } else if (ban.getTrangThai() == TrangThaiBan.DA_DAT || ban.getTrangThai() == TrangThaiBan.DANG_DUNG) {
                List<PhieuDatBan> dsPhieu = phieuDatBanDAO.getPhieuDatBanTheoBan(ban.getMaBan(), selectedDate);
                if (dsPhieu == null || dsPhieu.isEmpty()) {
                    availableBan.add(ban.getMaBan());
                }
            }
        }
        return availableBan;
    }

    public void themPhieuDatBan(PhieuDatBan phieuDatBan) {
        validatePhieuDatBan(phieuDatBan);
        if (!phieuDatBanDAO.themPhieuDatBan(phieuDatBan)) {
            throw new RuntimeException("Thêm phiếu đặt bàn thất bại");
        }
    }

    public String getLastPhieuDatBanID() {
        return phieuDatBanDAO.getLastPhieuDatBanID();
    }

    public String themPhieuDatBan(String maPDB, String tenKhachHang, String soDienThoai, int soLuongNguoi,
            LocalDateTime thoiGianDen, String ghiChu, Set<String> selectedTables) {
        KhachHangService khService = new KhachHangService();
        KhachHang kh = khService.getKhachHangTheoSDT(soDienThoai);
        if (kh == null) {
            String lastID = IDQueryHelper.getLastID("KhachHang", "maKH");
            String newID = IDGeneratorHelper.generateNextIDFromFullID(lastID);
            kh = new KhachHang(newID, tenKhachHang, soDienThoai, 0, LoaiThanhVien.DONG);
            khService.themKhachHang(kh);
        }
        PhieuDatBan phieu = new PhieuDatBan();
        phieu.setMaPhieuDat(maPDB);
        phieu.setKhachHang(kh);
        phieu.setNhanVien(SessionManager.getCurrentNhanVien());
        phieu.setThoiGianDen(thoiGianDen);
        phieu.setSoLuongNguoi(soLuongNguoi);
        phieu.setGhiChu(ghiChu);
        phieu.setTrangThai(TrangThaiPhieuDat.DANG_CHO);
        double tienDatCoc = 100000.0 * selectedTables.size();
        phieu.setTienDatCoc(tienDatCoc);
        validatePhieuDatBan(phieu);
        boolean checkPDB = phieuDatBanDAO.themPhieuDatBan(phieu);
        if (!checkPDB) {
            throw new RuntimeException("Lỗi: Không thể lưu phiếu đặt bàn vào hệ thống.");
        }
        for (String maBan : selectedTables) {
            ChiTietPhieuDatBan ct = new ChiTietPhieuDatBan();
            ct.setPhieuDatBan(phieu);
            ct.setBan(banService.getBanTheoMa(maBan));
            ct.setGhiChu("");
            chiTietPhieuDatBanDAO.themChiTietPhieuDatBan(ct);
            banService.capNhatTrangThaiBan(maBan, TrangThaiBan.DA_DAT);
        }
        return "Đặt bàn thành công cho khách hàng " + tenKhachHang;
    }

    public void capNhatTrangThaiPhieu(String maPDB, TrangThaiPhieuDat trangThai) {
        phieuDatBanDAO.capNhatTrangThaiPhieu(maPDB, trangThai);
    }

    public void capNhatPhieuDatBan(PhieuDatBan phieu) {
        validatePhieuDatBan(phieu);
        phieuDatBanDAO.capNhatPhieuDatBan(phieu);
    }

    public void xoaPhieuDatBan(String maPDB) {
        if (maPDB == null || maPDB.isBlank()) {
            throw new IllegalArgumentException("Không thể xóa với mã phiếu đặt bàn trống");
        }
        phieuDatBanDAO.xoaPhieuDatBan(maPDB);
    }

    public ArrayList<PhieuDatBan> getAllPhieuDatBan() {
        return (ArrayList<PhieuDatBan>) phieuDatBanDAO.getAllPhieuDatBan();
    }

    public void batDauSuDung(String maPhieu) {
        PhieuDatBan phieu = getPhieuDatBanTheoMa(maPhieu);
        if (phieu != null && phieu.getTrangThai() == TrangThaiPhieuDat.DANG_CHO) {
            capNhatTrangThaiPhieu(maPhieu, TrangThaiPhieuDat.DANG_SU_DUNG);
        } else {
            throw new IllegalStateException("Không thể bắt đầu sử dụng. Phiếu phải ở trạng thái Đang chờ");
        }
    }

    public List<PhieuDatBan> getDanhSachHoatDongGanDay() {
        return phieuDatBanDAO.getHoatDongGanDay();
    }

    public boolean hasFutureReservation(String maBan) {
        String sql = "SELECT COUNT(*) FROM PhieuDatBan pdb " +
                     "JOIN ChiTietPhieuDatBan ct ON pdb.maPhieuDat = ct.maPhieuDat " +
                     "WHERE ct.maBan = ? AND pdb.trangThai = 'DANG_CHO' AND pdb.thoiGianDen >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maBan);
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay()));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}