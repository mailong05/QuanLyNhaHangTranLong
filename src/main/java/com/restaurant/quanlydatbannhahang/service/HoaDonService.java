package com.restaurant.quanlydatbannhahang.service;
import com.restaurant.quanlydatbannhahang.dao.HoaDonDAO;
import com.restaurant.quanlydatbannhahang.dao.ChiTietHoaDonDAO;
import com.restaurant.quanlydatbannhahang.entity.HoaDon;
import com.restaurant.quanlydatbannhahang.entity.ChiTietHoaDon;
import com.restaurant.quanlydatbannhahang.entity.PhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiHoaDon;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;
public class HoaDonService {
    private HoaDonDAO hoaDonDAO;
    private ChiTietHoaDonDAO chiTietHoaDonDAO;
    private static final String MAHD_PATTERN = "^HD\\d{3}$";
    private static final String MAPHIEUDAT_PATTERN = "^PD\\d{3}$";
    private static final String MABAN_PATTERN = "^B\\d{3}(?:,B\\d{3})*$";
    private static final String MAMON_PATTERN = "^MA\\d{3}$";
    private static final Pattern maHDPattern = Pattern.compile(MAHD_PATTERN);
    private static final Pattern maPhieuDatPattern = Pattern.compile(MAPHIEUDAT_PATTERN);
    private static final Pattern maBanPattern = Pattern.compile(MABAN_PATTERN);
    private static final Pattern maMonPattern = Pattern.compile(MAMON_PATTERN);
    public HoaDonService() {
        this.hoaDonDAO = new HoaDonDAO();
        this.chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    }
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
        if (hoaDon.getPhieuDatBan() == null || hoaDon.getPhieuDatBan().getMaPhieuDat() == null
                || hoaDon.getPhieuDatBan().getMaPhieuDat().isBlank()) {
            throw new IllegalArgumentException("Mã phiếu đặt bàn không được để trống");
        }
        if (!maPhieuDatPattern.matcher(hoaDon.getPhieuDatBan().getMaPhieuDat()).matches()) {
            throw new IllegalArgumentException("Mã phiếu đặt bàn phải có dạng PDxxx (ví dụ: PD001)");
        }
        if (hoaDon.getTrangThaiThanhToan() == null) {
            throw new IllegalArgumentException("Trạng thái thanh toán không được để trống");
        }
    }
    public HoaDon getHoaDonTheoMa(String maHD) {
        if (maHD == null || maHD.isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(maHD).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
        }
        HoaDon hoaDon = hoaDonDAO.getHoaDonTheoMa(maHD);
        if (hoaDon == null) {
            throw new RuntimeException("Không tìm thấy hóa đơn với mã: " + maHD);
        }
        return hoaDon;
    }
    public HoaDon findHoaDonTheoMa(String maHD) {
        if (maHD == null || maHD.isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(maHD).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
        }
        return hoaDonDAO.getHoaDonTheoMa(maHD);
    }
    public double tinhDoanhThuHomNay() {
        return hoaDonDAO.getTongDoanhThuTheoNgay(LocalDate.now());
    }
    public List<HoaDon> getAllHoaDon() {
        return hoaDonDAO.getAllHoaDon();
    }
    public void themHoaDon(HoaDon hoaDon) {
        validateHoaDon(hoaDon);
        if (!hoaDonDAO.themHoaDon(hoaDon)) {
            throw new RuntimeException("Thêm hóa đơn thất bại");
        }
    }
    public void capNhatHoaDon(HoaDon hoaDon) {
        validateHoaDon(hoaDon);
        if (!hoaDonDAO.capNhatHoaDon(hoaDon)) {
            throw new RuntimeException("Cập nhật hóa đơn thất bại");
        }
    }
    public void xoaHoaDon(String maHD) {
        if (maHD == null || maHD.isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(maHD).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
        }
        if (!hoaDonDAO.xoaHoaDon(maHD)) {
            throw new RuntimeException("Xóa hóa đơn thất bại");
        }
    }
    public List<HoaDon> getHoaDonTheoMaBan(String maBan) {
        if (maBan == null || maBan.isBlank()) {
            throw new IllegalArgumentException("Mã bàn không được để trống");
        }
        if (!maBanPattern.matcher(maBan).matches()) {
            throw new IllegalArgumentException("Mã bàn phải có dạng Bxxx hoặc Bxxx,Byyy (ví dụ: B001 hoặc B001,B002)");
        }
        return hoaDonDAO.getHoaDonTheoMaBan(maBan);
    }
    public void capNhatBanChoHoaDonDraft(String maHD, String maBanMoi) {
        throw new UnsupportedOperationException(
                "Cập nhật bàn cho hóa đơn không còn trực tiếp lưu trong HoaDon. Vui lòng cập nhật thông qua PhieuDatBan hoặc ChiTietPhieuDatBan.");
    }
    public void chuyenMaBanChoHoaDonDraft(String oldMaBan, String newMaBan) {
        chuyenBanChoHoaDonDraft(oldMaBan, newMaBan);
    }
    public void chuyenBanChoHoaDonDraft(String oldMaBan, String newMaBan) {
        if (oldMaBan == null || oldMaBan.isBlank()) {
            throw new IllegalArgumentException("Mã bàn cũ không được để trống");
        }
        if (newMaBan == null || newMaBan.isBlank()) {
            throw new IllegalArgumentException("Mã bàn mới không được để trống");
        }
        if (!maBanPattern.matcher(oldMaBan).matches()) {
            throw new IllegalArgumentException("Mã bàn cũ phải có dạng Bxxx hoặc Bxxx,Byyy");
        }
        if (!maBanPattern.matcher(newMaBan).matches()) {
            throw new IllegalArgumentException("Mã bàn mới phải có dạng Bxxx hoặc Bxxx,Byyy");
        }
        List<HoaDon> dsHoaDon = hoaDonDAO.getHoaDonTheoMaBan(oldMaBan);
        HoaDon hDraft = dsHoaDon.stream()
                .filter(h -> h.getTrangThaiThanhToan() == TrangThaiHoaDon.CHUA_THANH_TOAN)
                .findFirst().orElse(null);
        if (hDraft != null) {
        }
    }
    public List<HoaDon> getHoaDonTheoTrangThai(TrangThaiHoaDon trangThai) {
        if (trangThai == null) {
            throw new IllegalArgumentException("Trạng thái không được để trống");
        }
        return hoaDonDAO.getHoaDonTheoTrangThai(trangThai);
    }
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
    public void themChiTietHoaDon(ChiTietHoaDon chiTiet) {
        if (chiTiet == null) {
            throw new IllegalArgumentException("Chi tiết hóa đơn không được để trống");
        }
        if (chiTiet.getHoaDon().getMaHD() == null || chiTiet.getHoaDon().getMaHD().isBlank()) {
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
        if (!chiTietHoaDonDAO.themChiTietHoaDon(chiTiet)) {
            throw new RuntimeException("Thêm chi tiết hóa đơn thất bại");
        }
    }
    public List<ChiTietHoaDon> getChiTietHoaDon(String maHD) {
        if (maHD == null || maHD.isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(maHD).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
        }
        return chiTietHoaDonDAO.getChiTietByMaHD(maHD);
    }
    public double getTongTienHoaDon(String maHD) {
        if (maHD == null || maHD.isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(maHD).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
        }
        return chiTietHoaDonDAO.getTongTienHoaDon(maHD);
    }
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
        if (!chiTietHoaDonDAO.xoaChiTietHoaDon(maHD, maMon)) {
            throw new RuntimeException("Xóa chi tiết hóa đơn thất bại");
        }
    }
    public void thanhToanHoaDon(String maHD) {
        capNhatTrangThaiHoaDon(maHD, TrangThaiHoaDon.DA_THANH_TOAN);
    }
    public int getTotalHoaDon() {
        List<HoaDon> list = getAllHoaDon();
        return list != null ? list.size() : 0;
    }
    public String getLastHoaDonID() {
        return hoaDonDAO.getLastHoaDonID();
    }
}