package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.BanDAO;
import com.restaurant.quanlydatbannhahang.entity.Ban;
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

    /**
     * Validate đối tượng Ban
     */
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

    /**
     * Thêm bàn mới
     */
    public void themBan(Ban ban) {
        validateBan(ban);
        if (banDAO.themBan(ban)) {
            System.out.println(" Thêm bàn thành công");
        } else {
            System.out.println(" Thêm bàn thất bại");
        }
    }

    /**
     * Lấy bàn theo mã
     */
    public Ban getBanTheoMa(String maBan) {
        if (maBan == null || maBan.isBlank()) {
            throw new IllegalArgumentException("Mã bàn không được để trống");
        }
        if (!maBanPattern.matcher(maBan).matches()) {
            throw new IllegalArgumentException("Mã bàn phải có dạng Bxxx (ví dụ: B001)");
        }
        Ban ban = banDAO.getBanTheoMa(maBan);
        if (ban == null) {
            System.out.println(" Không tìm thấy bàn với mã: " + maBan);
        }
        return ban;
    }

    /**
     * Lấy tất cả bàn
     */
    public List<Ban> getAllBan() {
        return banDAO.getAllBan();
    }

    /**
     * Lấy bàn theo khu vực
     */
    public List<Ban> getBanTheoKhuVuc(String maKhuVuc) {
        if (maKhuVuc == null || maKhuVuc.isBlank()) {
            throw new IllegalArgumentException("Mã khu vực không được để trống");
        }
        return banDAO.getBanTheoKhuVuc(maKhuVuc);
    }

    /**
     * Lấy bàn trống
     */
    public List<Ban> getBanTrong() {
        return banDAO.getBanTrong();
    }

    /**
     * Lấy bàn đang sử dụng
     */
    public List<Ban> getBanDangSuDung() {
        return banDAO.getBanDangSuDung();
    }

    /**
     * Cập nhật thông tin bàn
     */
    public void capNhatBan(Ban ban) {
        validateBan(ban);
        if (banDAO.capNhatBan(ban)) {
            System.out.println(" Cập nhật bàn thành công");
        } else {
            System.out.println(" Cập nhật bàn thất bại");
        }
    }

    /**
     * Xóa bàn
     */
    public void xoaBan(String maBan) {
        if (maBan == null || maBan.isBlank()) {
            throw new IllegalArgumentException("Mã bàn không được để trống");
        }
        if (!maBanPattern.matcher(maBan).matches()) {
            throw new IllegalArgumentException("Mã bàn phải có dạng Bxxx (ví dụ: B001)");
        }
        if (banDAO.xoaBan(maBan)) {
            System.out.println(" Xóa bàn thành công");
        } else {
            System.out.println(" Xóa bàn thất bại");
        }
    }

    /**
     * Cập nhật trạng thái bàn
     */
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

    /**
     * Kiểm tra bàn trống
     */
    public boolean isBanTrong(String maBan) {
        Ban ban = getBanTheoMa(maBan);
        return ban != null && ban.getTrangThai() == TrangThaiBan.TRONG;
    }

    /**
     * Đặt bàn (đổi trạng thái từ TRONG sang DA_DAT)
     */
    public void datBan(String maBan) {
        if (!isBanTrong(maBan)) {
            throw new IllegalArgumentException("Bàn này không còn trống");
        }
        capNhatTrangThaiBan(maBan, TrangThaiBan.DA_DAT);
    }

    /**
     * Sử dụng bàn (đổi trạng thái từ DA_DAT sang DANG_DUNG)
     */
    public void suDungBan(String maBan) {
        Ban ban = getBanTheoMa(maBan);
        if (ban == null || ban.getTrangThai() == TrangThaiBan.TRONG) {
            throw new IllegalArgumentException("Bàn không hợp lệ để sử dụng");
        }
        capNhatTrangThaiBan(maBan, TrangThaiBan.DANG_DUNG);
    }

    /**
     * Giải phóng bàn (đổi trạng thái về TRONG)
     */
    public void giaiphongBan(String maBan) {
        capNhatTrangThaiBan(maBan, TrangThaiBan.TRONG);
    }
}

   
