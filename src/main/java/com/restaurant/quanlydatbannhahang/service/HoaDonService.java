package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.HoaDonDAO;
import com.restaurant.quanlydatbannhahang.dao.ChiTietHoaDonDAO;
import com.restaurant.quanlydatbannhahang.entity.HoaDon;
import com.restaurant.quanlydatbannhahang.entity.ChiTietHoaDon;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiHoaDon;

import java.util.List;
import java.util.regex.Pattern;

public class HoaDonService {
    private HoaDonDAO hoaDonDAO;
    private ChiTietHoaDonDAO chiTietHoaDonDAO;

    private static final String MAHD_PATTERN = "^HD\\d{3}$";
    private static final String MABAN_PATTERN = "^B\\d{3}$";
    private static final String MAMON_PATTERN = "^MA\\d{3}$";

    private static final Pattern maHDPattern = Pattern.compile(MAHD_PATTERN);
    private static final Pattern maBanPattern = Pattern.compile(MABAN_PATTERN);
    private static final Pattern maMonPattern = Pattern.compile(MAMON_PATTERN);

    public HoaDonService() {
        this.hoaDonDAO = new HoaDonDAO();
        this.chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    }

    /**
     * Validate đối tượng HoaDon
     */
    public void validateHoaDon(HoaDon hoaDon) {
        if (hoaDon == null) {
            throw new IllegalArgumentException("Đối tượng hóa đơn không được để trống");
        }
        if (hoaDon.getMaHD() == null || hoaDon.getMaHD().isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(hoaDon.getMaHD()).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
        }
        if (hoaDon.getBan().getMaBan() == null || hoaDon.getBan().getMaBan().isBlank()) {
            throw new IllegalArgumentException("Mã bàn không được để trống");
        }
        if (!maBanPattern.matcher(hoaDon.getBan().getMaBan()).matches()) {
            throw new IllegalArgumentException("Mã bàn phải có dạng Bxxx (ví dụ: B001)");
        }
        if (hoaDon.getTrangThaiThanhToan() == null) {
            throw new IllegalArgumentException("Trạng thái thanh toán không được để trống");
        }
    }

    /**
     * Lấy hóa đơn theo mã
     */
    public HoaDon getHoaDonTheoMa(String maHD) {
        if (maHD == null || maHD.isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(maHD).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
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
        validateHoaDon(hoaDon);
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
        validateHoaDon(hoaDon);
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
        if (maHD == null || maHD.isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(maHD).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
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
        if (maBan == null || maBan.isBlank()) {
            throw new IllegalArgumentException("Mã bàn không được để trống");
        }
        if (!maBanPattern.matcher(maBan).matches()) {
            throw new IllegalArgumentException("Mã bàn phải có dạng Bxxx (ví dụ: B001)");
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
        if (maHD == null || maHD.isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(maHD).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
        }
        if (trangThai == null) {
            throw new IllegalArgumentException("Trạng thái thanh toán không được để trống");
        }
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
        if (chiTiet.getHoaDon().getMaHD() == null || chiTiet.getHoaDon().getMaHD() .isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(chiTiet.getHoaDon().getMaHD()).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
        }
        if (chiTiet.getMonAn().getMaMon() == null || chiTiet.getMonAn().getMaMon().isBlank()) {
            throw new IllegalArgumentException("Mã món không được để trống");
        }
        if (!maMonPattern.matcher(chiTiet.getMonAn().getMaMon()).matches()) {
            throw new IllegalArgumentException("Mã món phải có dạng MAxxx (ví dụ: MA001)");
        }
        if (chiTiet.getSoLuong() <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
        if (chiTiet.getDonGiaLuuTru() < 0) {
            throw new IllegalArgumentException("Đơn giá không được âm");
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
        if (maHD == null || maHD.isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(maHD).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
        }
        return chiTietHoaDonDAO.getChiTietByMaHD(maHD);
    }

    /**
     * Tính tổng tiền hóa đơn
     */
    public double getTongTienHoaDon(String maHD) {
        if (maHD == null || maHD.isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(maHD).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
        }
        return chiTietHoaDonDAO.getTongTienHoaDon(maHD);
    }

    /**
     * Xóa chi tiết hóa đơn
     */
    public void xoaChiTietHoaDon(String maHD, String maMon) {
        if (maHD == null || maHD.isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(maHD).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
        }
        if (maMon == null || maMon.isBlank()) {
            throw new IllegalArgumentException("Mã món không được để trống");
        }
        if (!maMonPattern.matcher(maMon).matches()) {
            throw new IllegalArgumentException("Mã món phải có dạng MAxxx (ví dụ: MA001)");
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

    /**
     * Lấy mã hóa đơn cuối cùng để sinh mã tiếp theo
     *
     * @return Mã hóa đơn cuối cùng (VD: HD000) hoặc null nếu bảng rỗng
     */
    public String getLastHoaDonID() {
        return hoaDonDAO.getLastHoaDonID();
    }
}
