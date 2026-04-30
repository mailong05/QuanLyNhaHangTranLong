package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.HoaDonDAO;
import com.restaurant.quanlydatbannhahang.dao.ChiTietHoaDonDAO;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.entity.HoaDon;
import com.restaurant.quanlydatbannhahang.entity.ChiTietHoaDon;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiHoaDon;

import java.util.List;
import java.util.regex.Pattern;

public class HoaDonService {
    private HoaDonDAO hoaDonDAO;
    private ChiTietHoaDonDAO chiTietHoaDonDAO;

    private static final String MAHD_PATTERN = "^HD\\d{3}$";
    private static final String MABAN_PATTERN = "^B\\d{3}(?:,B\\d{3})*$";
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
            throw new IllegalArgumentException("Mã bàn phải có dạng Bxxx hoặc Bxxx,Byyy (ví dụ: B001 hoặc B001,B002)");
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
        if (!hoaDonDAO.themHoaDon(hoaDon)) {
            throw new RuntimeException("Thêm hóa đơn thất bại");
        }
    }

    /**
     * Cập nhật hóa đơn
     */
    public void capNhatHoaDon(HoaDon hoaDon) {
        validateHoaDon(hoaDon);
        if (!hoaDonDAO.capNhatHoaDon(hoaDon)) {
            throw new RuntimeException("Cập nhật hóa đơn thất bại");
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
        if (!hoaDonDAO.xoaHoaDon(maHD)) {
            throw new RuntimeException("Xóa hóa đơn thất bại");
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
            throw new IllegalArgumentException("Mã bàn phải có dạng Bxxx hoặc Bxxx,Byyy (ví dụ: B001 hoặc B001,B002)");
        }
        return hoaDonDAO.getHoaDonTheoMaBan(maBan);
    }

    public void capNhatBanChoHoaDonDraft(String maHD, String maBanMoi) {
        if (maHD == null || maHD.isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(maHD).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
        }
        if (maBanMoi == null || maBanMoi.isBlank()) {
            throw new IllegalArgumentException("Mã bàn mới không được để trống");
        }
        if (!maBanPattern.matcher(maBanMoi).matches()) {
            throw new IllegalArgumentException("Mã bàn phải có dạng Bxxx hoặc Bxxx,Byyy (ví dụ: B001 hoặc B001,B002)");
        }

        HoaDon hoaDon = getHoaDonTheoMa(maHD);
        if (hoaDon == null) {
            throw new RuntimeException("Không tìm thấy hóa đơn với mã: " + maHD);
        }
        hoaDon.setBan(new Ban(maBanMoi, 0, "", null, null));
        capNhatHoaDon(hoaDon);
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

            // 1. Tìm hóa đơn chưa thanh toán của cụm bàn cũ
            List<HoaDon> dsHoaDon = hoaDonDAO.getHoaDonTheoMaBan(oldMaBan);
            HoaDon hDraft = dsHoaDon.stream()
                .filter(h -> h.getTrangThaiThanhToan() == TrangThaiHoaDon.CHUA_THANH_TOAN)
                .findFirst().orElse(null);

            // 2. Nếu tìm thấy, cập nhật mã bàn mới và lưu xuống DB[cite: 1, 10]
            if (hDraft != null) {
                // Tạo đối tượng bàn ảo với mã context mới (vì DB lưu String)
                hDraft.setBan(new Ban(newMaBan, 0, "", null, null));
                if (!hoaDonDAO.capNhatHoaDon(hDraft)) {
                    throw new RuntimeException("Lỗi cập nhật mã bàn mới trong Database.");
                }
            }
        

      
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
        if (!chiTietHoaDonDAO.xoaChiTietHoaDon(maHD, maMon)) {
            throw new RuntimeException("Xóa chi tiết hóa đơn thất bại");
        }
    }

    /**
     * Thanh toán hóa đơn
     */
    public void thanhToanHoaDon(String maHD) {
        capNhatTrangThaiHoaDon(maHD, TrangThaiHoaDon.DA_THANH_TOAN);
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
