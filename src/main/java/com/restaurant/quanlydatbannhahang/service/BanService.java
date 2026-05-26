package com.restaurant.quanlydatbannhahang.service;
import com.restaurant.quanlydatbannhahang.dao.BanDAO;
import com.restaurant.quanlydatbannhahang.dao.PhieuDatBanDAO;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.entity.ChiTietPhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import java.util.List;
import java.util.regex.Pattern;
public class BanService {
    private BanDAO banDAO;
    private static final String MABAN_PATTERN = "^B\\d{3}$";
    private static final Pattern maBanPattern = Pattern.compile(MABAN_PATTERN);
   
    public BanService() {
        this.banDAO = new BanDAO();
    }
    public void validateBan(Ban ban) {
        if (ban == null) {
            throw new IllegalArgumentException("Đối tượng bàn không được để trống");
        }
        if (ban.getMaBan() == null || ban.getMaBan().isBlank()) {
            throw new IllegalArgumentException("Mã bàn không được để trống");
        }
        if (!maBanPattern.matcher(ban.getMaBan()).matches()) {
            throw new IllegalArgumentException("Mã bàn phải có dạng Bxxx (ví dụ: B001)");
        }
        if (ban.getSoGhe() <= 0) {
            throw new IllegalArgumentException("Số ghế phải lớn hơn 0");
        }
    }
    public void themBan(Ban ban) {
        validateBan(ban);
        if (!banDAO.themBan(ban)) {
            throw new RuntimeException("Thêm bàn thất bại");
        }
    }
    public Ban getBanTheoMa(String maBan) {
        if (maBan == null || maBan.isBlank()) {
            throw new IllegalArgumentException("Mã bàn không được để trống");
        }
        if (!maBanPattern.matcher(maBan).matches()) {
            throw new IllegalArgumentException("Mã bàn phải có dạng Bxxx (ví dụ: B001)");
        }
        Ban ban = banDAO.getBanTheoMa(maBan);
        if (ban == null) {
            throw new RuntimeException("Không tìm thấy bàn với mã: " + maBan);
        }
        return ban;
    }
    public List<Ban> getAllBan() {
        return banDAO.getAllBan();
    }
    public List<Ban> getBanTheoKhuVuc(String maKhuVuc) {
        if (maKhuVuc == null || maKhuVuc.isBlank()) {
            throw new IllegalArgumentException("Mã khu vực không được để trống");
        }
        return banDAO.getBanTheoKhuVuc(maKhuVuc);
    }
    public List<Ban> getBanTrong() {
        return banDAO.getBanTrong();
    }
    public List<Ban> getBanDangSuDung() {
        return banDAO.getBanDangSuDung();
    }
    public void capNhatBan(Ban ban) {
        validateBan(ban);
        if (!banDAO.capNhatBan(ban)) {
            throw new RuntimeException("Cập nhật bàn thất bại");
        }
    }
    public void xoaBan(String maBan) {
        if (maBan == null || maBan.isBlank()) {
            throw new IllegalArgumentException("Mã bàn không được để trống");
        }
        if (!maBanPattern.matcher(maBan).matches()) {
            throw new IllegalArgumentException("Mã bàn phải có dạng Bxxx (ví dụ: B001)");
        }
        if (!banDAO.xoaBan(maBan)) {
            throw new RuntimeException("Xóa bàn thất bại");
        }
    }
    public void capNhatTrangThaiBan(String maBan, TrangThaiBan trangThai) {
        if (maBan == null || maBan.isBlank()) {
            throw new IllegalArgumentException("Mã bàn không được để trống");
        }
        if (!maBanPattern.matcher(maBan).matches()) {
            throw new IllegalArgumentException("Mã bàn phải có dạng Bxxx (ví dụ: B001)");
        }
        if (trangThai == null) {
            throw new IllegalArgumentException("Trạng thái bàn không được để trống");
        }
        Ban ban = getBanTheoMa(maBan);
        if (ban != null) {
            ban.setTrangThai(trangThai);
            capNhatBan(ban);
        }
    }
    public boolean isBanTrong(String maBan) {
        Ban ban = getBanTheoMa(maBan);
        return ban != null && ban.getTrangThai() == TrangThaiBan.TRONG;
    }
    public void datBan(String maBan) {
        if (!isBanTrong(maBan)) {
            throw new IllegalArgumentException("Bàn này không còn trống");
        }
        capNhatTrangThaiBan(maBan, TrangThaiBan.DA_DAT);
    }
    public void suDungBan(String maBan) {
        Ban ban = getBanTheoMa(maBan);
        if (ban == null || ban.getTrangThai() == TrangThaiBan.TRONG) {
            throw new IllegalArgumentException("Bàn không hợp lệ để sử dụng");
        }
        capNhatTrangThaiBan(maBan, TrangThaiBan.DANG_DUNG);
    }
    public void giaiphongBan(String maBan) {
        capNhatTrangThaiBan(maBan, TrangThaiBan.TRONG);
    }
    public void capNhatTrangThaiBanTrongChiTietPDB(List<ChiTietPhieuDatBan> list, TrangThaiBan trangThai) {
		for(ChiTietPhieuDatBan ct: list) {
			capNhatTrangThaiThongMinh(ct.getBan().getMaBan());
		}
	}
    
    public void capNhatTrangThaiThongMinh(String maBan) {
        if (maBan == null || maBan.isBlank()) return;
        
        try {
            Ban ban = banDAO.getBanTheoMa(maBan);
            if (ban == null) return;

            if (ban.getTrangThai() == TrangThaiBan.DANG_DUNG) {
                return; 
            }

            PhieuDatBanService pdb_service = new PhieuDatBanService();
            boolean hasFutureReservation = pdb_service.hasReservationToday(maBan);

            if (hasFutureReservation) {
                banDAO.capNhatTrangThaiBan(maBan, TrangThaiBan.DA_DAT);
            } else {
                banDAO.capNhatTrangThaiBan(maBan, TrangThaiBan.TRONG);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi định đoạt trạng thái bàn " + maBan + ": " + e.getMessage());
        }
    }
    
    public void capNhatTrangThaiThongMinhChoChuoiContext(String maBanContext) {
        if (maBanContext == null || maBanContext.isBlank()) return;
        String[] danhSachMaBan = maBanContext.split(",");
        for (String maBan : danhSachMaBan) {
            capNhatTrangThaiThongMinh(maBan.trim());
        }
    }
    
 
}