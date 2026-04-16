package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.PhieuDatBanDAO;
import com.restaurant.quanlydatbannhahang.dao.ChiTietPhieuDatBanDAO;
import com.restaurant.quanlydatbannhahang.dao.KhachHangDAO;
import com.restaurant.quanlydatbannhahang.dao.BanDAO;
import com.restaurant.quanlydatbannhahang.entity.PhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.entity.ChiTietPhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiPhieuDat;
import com.restaurant.quanlydatbannhahang.session.SessionManager;
import com.restaurant.quanlydatbannhahang.entity.KhachHang;
import com.restaurant.quanlydatbannhahang.entity.LoaiThanhVien;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import com.restaurant.quanlydatbannhahang.util.IDGeneratorHelper;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class PhieuDatBanService {
    private PhieuDatBanDAO phieuDatBanDAO;
    private ChiTietPhieuDatBanDAO chiTietPhieuDatBanDAO;
    private KhachHangDAO khachHangDAO;
    private BanDAO banDAO;
    private IDGeneratorHelper generateHelper;
    private IDQueryHelper querygenerateHelper;

    public PhieuDatBanService() {
        this.phieuDatBanDAO = new PhieuDatBanDAO();
        this.chiTietPhieuDatBanDAO = new ChiTietPhieuDatBanDAO();
        this.khachHangDAO = new KhachHangDAO();
        this.banDAO = new BanDAO();
        this.generateHelper = new IDGeneratorHelper();
        this.querygenerateHelper = new IDQueryHelper();
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
        return chiTietPhieuDatBanDAO.getChiTietByMaPhieuDat(maPhieu);
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

    private KhachHang createNewKhachHang(String tenKhachHang, String soDienThoai) {
        String lastMaKH = querygenerateHelper.getLastID("KhachHang", "maKH");
        String newMaKH = (lastMaKH == null || lastMaKH.isEmpty()) ? generateHelper.generateDefaultID(lastMaKH)
                : generateHelper.generateNextIDFromFullID(lastMaKH);
        String hoTen = (tenKhachHang == null || tenKhachHang.isEmpty()) ? "Khách vãng lai" : tenKhachHang;
        KhachHang kh = new KhachHang(
                newMaKH,
                hoTen,
                soDienThoai,
                0,
                LoaiThanhVien.DONG);
        khachHangDAO.themKhachHang(kh);
        return kh;
    }

    /**
     * Tạo phiếu đặt bàn trước mới từ DatBanTruocDialog
     * Bao gồm: validate, tạo khách hàng (nếu cần), tạo phiếu, tạo chi tiết, cập
     * nhật trạng thái bàn
     * 
     * @param maPhieuDatBan  Mã phiếu đặt bàn
     * @param tenKhachHang   Tên khách hàng
     * @param soDienThoai    Số điện thoại
     * @param soLuongNguoi   Số lượng người
     * @param thoiGianDen    Thời gian đến
     * @param ghiChu         Ghi chú
     * @param selectedTables Tập hợp các bàn đã chọn (mã bàn)
     * @return Mã phiếu đặt bàn đã tạo
     */
    public String taoPhieuDatBanMoi(String maPhieuDatBan, String tenKhachHang, String soDienThoai, int soLuongNguoi,
            LocalDateTime thoiGianDen, String ghiChu, Set<String> selectedTables) {

        String validationError = DatBanTruocService.validatePhieuDatBan(soDienThoai, String.valueOf(soLuongNguoi));
        if (validationError != null) {
            throw new IllegalArgumentException(validationError);
        }

        if (thoiGianDen == null) {
            throw new IllegalArgumentException("Vui lòng chọn thời gian đến!");
        }

        if (LocalDateTime.now().isAfter(thoiGianDen)) {
            throw new IllegalArgumentException("Thời gian đến phải sau ngày hiện tại");
        }

        if (selectedTables == null || selectedTables.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn ít nhất một bàn!");
        }

        try {
            KhachHang khachHang = khachHangDAO.getKhachHangTheoSDT(soDienThoai);

            if (khachHang == null) {
                khachHang = createNewKhachHang(tenKhachHang, soDienThoai);
            }

            PhieuDatBan phieuDatBan = new PhieuDatBan();

            phieuDatBan.setMaPhieuDat(maPhieuDatBan);
            phieuDatBan.setKhachHang(khachHang);
            phieuDatBan.setNhanVien(SessionManager.getCurrentNhanVien());
            phieuDatBan.setThoiGianDen(thoiGianDen);
            phieuDatBan.setSoLuongNguoi(soLuongNguoi);
            phieuDatBan.setGhiChu(ghiChu != null && !ghiChu.isEmpty() ? ghiChu : "");
            phieuDatBan.setTrangThai(TrangThaiPhieuDat.CHO_XAC_NHAN);

            boolean phieuSaved = phieuDatBanDAO.themPhieuDatBan(phieuDatBan);

            if (!phieuSaved) {
                throw new RuntimeException("Không thể lưu phiếu đặt bàn!");
            }

            String maPhieuDat = phieuDatBan.getMaPhieuDat();
            

            // ← BƯỚC 2: Tạo chi tiết phiếu đặt bàn cho mỗi bàn đã chọn
            for (String maBan : selectedTables) {
                Ban ban = banDAO.getBanTheoMa(maBan);
                if (ban == null) {
                    throw new RuntimeException("Bàn " + maBan + " không tồn tại!");
                }

                ChiTietPhieuDatBan chiTiet = new ChiTietPhieuDatBan();
                chiTiet.setPhieuDatBan(phieuDatBan);
                chiTiet.setBan(ban);
                chiTiet.setGhiChu("");

                if (!chiTietPhieuDatBanDAO.themChiTietPhieuDatBan(chiTiet)) {
                    throw new RuntimeException("Không thể lưu chi tiết phiếu cho bàn " + maBan);
                }

                // ← BƯỚC 3: Cập nhật trạng thái bàn thành DA_DAT
                if (!banDAO.capNhatTrangThaiBan(maBan, TrangThaiBan.DA_DAT)) {
                    throw new RuntimeException("Không thể cập nhật trạng thái bàn " + maBan);
                }

                System.out.println("✓ Đã đặt bàn: " + maBan);
            }

            System.out.println("Tạo phiếu đặt bàn thành công! Mã: " + maPhieuDat);
            return maPhieuDat;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo phiếu đặt bàn: " + e.getMessage(), e);
        }
    }

    /**
     * Lấy mã phiếu đặt bàn cuối cùng để sinh mã tiếp theo
     * 
     * @return Mã phiếu cuối cùng (VD: PDB010) hoặc null nếu bảng rỗng
     */
    public String getLastPhieuDatBanID() {
        return phieuDatBanDAO.getLastPhieuDatBanID();
    }

}
