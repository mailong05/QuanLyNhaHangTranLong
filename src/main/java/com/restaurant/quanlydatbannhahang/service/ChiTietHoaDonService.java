package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.ChiTietHoaDonDAO;
import com.restaurant.quanlydatbannhahang.entity.ChiTietHoaDon;

import java.util.List;
import java.util.regex.Pattern;

public class ChiTietHoaDonService {
    private ChiTietHoaDonDAO chiTietHoaDonDAO;

    private static final String MAHD_PATTERN = "^HD\\d{3}$";
    private static final String MAMON_PATTERN = "^MA\\d{3}$";

    private static final Pattern maHDPattern = Pattern.compile(MAHD_PATTERN);
    private static final Pattern maMonPattern = Pattern.compile(MAMON_PATTERN);

    public ChiTietHoaDonService() {
        this.chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    }

    /**
     * Validate đối tượng ChiTietHoaDon
     */
    public void validateChiTietHoaDon(ChiTietHoaDon chiTiet) {
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
            throw new IllegalArgumentException("Đơn giá lưu trữ không được âm");
        }
    }

    /**
     * Lấy chi tiết hóa đơn theo mã hóa đơn
     */
    public List<ChiTietHoaDon> getChiTietByMaHD(String maHD) {
        if (maHD == null || maHD.isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(maHD).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
        }
        return chiTietHoaDonDAO.getChiTietByMaHD(maHD);
    }

    /**
     * Lấy chi tiết hóa đơn theo mã hóa đơn và mã món
     */
    public ChiTietHoaDon getChiTietByMaHDAndMaMon(String maHD, String maMon) {
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
        return chiTietHoaDonDAO.getChiTietByMaHDAndMaMon(maHD, maMon);
    }

    /**
     * Thêm chi tiết hóa đơn
     */
    public void themChiTietHoaDon(ChiTietHoaDon chiTiet) {
        validateChiTietHoaDon(chiTiet);
        if (chiTietHoaDonDAO.themChiTietHoaDon(chiTiet)) {
            System.out.println(" Thêm chi tiết hóa đơn thành công");
        } else {
            System.out.println(" Thêm chi tiết hóa đơn thất bại");
        }
    }

    /**
     * Cập nhật chi tiết hóa đơn
     */
    public void capNhatChiTietHoaDon(ChiTietHoaDon chiTiet) {
        validateChiTietHoaDon(chiTiet);
        if (chiTietHoaDonDAO.capNhatChiTietHoaDon(chiTiet)) {
            System.out.println(" Cập nhật chi tiết hóa đơn thành công");
        } else {
            System.out.println(" Cập nhật chi tiết hóa đơn thất bại");
        }
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
     * Xóa tất cả chi tiết của hóa đơn
     */
    public void xoaAllChiTietByMaHD(String maHD) {
        if (maHD == null || maHD.isBlank()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        if (!maHDPattern.matcher(maHD).matches()) {
            throw new IllegalArgumentException("Mã hóa đơn phải có dạng HDxxx (ví dụ: HD001)");
        }
        if (chiTietHoaDonDAO.xoaAllChiTietByMaHD(maHD)) {
            System.out.println(" Xóa tất cả chi tiết hóa đơn thành công");
        } else {
            System.out.println(" Xóa tất cả chi tiết hóa đơn thất bại");
        }
    }

    /**
     * Cập nhật số lượng
     */
    public void capNhatSoLuong(String maHD, String maMon, int soLuongMoi) {
        if (soLuongMoi <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
        ChiTietHoaDon chiTiet = getChiTietByMaHDAndMaMon(maHD, maMon);
        if (chiTiet != null) {
            chiTiet.setSoLuong(soLuongMoi);
            capNhatChiTietHoaDon(chiTiet);
        }
    }

    /**
     * Cập nhật đơn giá
     */
    public void capNhatDonGiaLuuTru(String maHD, String maMon, double donGiaLuuTruMoi) {
        if (donGiaLuuTruMoi < 0) {
            throw new IllegalArgumentException("Đơn giá lưu trữ không được âm");
        }
        ChiTietHoaDon chiTiet = getChiTietByMaHDAndMaMon(maHD, maMon);
        if (chiTiet != null) {
            chiTiet.setDonGiaLuuTru(donGiaLuuTruMoi);
            capNhatChiTietHoaDon(chiTiet);
        }
    }

    /**
     * Tính thành tiền cho một chi tiết
     */
    public double getThanhTien(String maHD, String maMon) {
        ChiTietHoaDon chiTiet = getChiTietByMaHDAndMaMon(maHD, maMon);
        if (chiTiet != null) {
            return chiTiet.tinhThanhTien();
        }
        return 0;
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
     * Đếm tổng số chi tiết trong hóa đơn
     */
    public int countChiTietInHoaDon(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống");
        }
        return chiTietHoaDonDAO.countChiTietByMaHD(maHD);
    }

    /**
     * Kiểm tra hóa đơn có chi tiết không
     */
    public boolean hasChiTiet(String maHD) {
        return countChiTietInHoaDon(maHD) > 0;
    }

    /**
     * Tính số lượng tất cả các món trong hóa đơn
     */
    public int getTongSoLuongMon(String maHD) {
        List<ChiTietHoaDon> list = getChiTietByMaHD(maHD);
        int tongSoLuong = 0;
        if (list != null) {
            for (ChiTietHoaDon chiTiet : list) {
                tongSoLuong += chiTiet.getSoLuong();
            }
        }
        return tongSoLuong;
    }

    /**
     * Lấy chi tiết có giá cao nhất
     */
    public ChiTietHoaDon getChiTietGiaMax(String maHD) {
        List<ChiTietHoaDon> list = getChiTietByMaHD(maHD);
        ChiTietHoaDon maxChiTiet = null;
        double maxGia = 0;

        if (list != null) {
            for (ChiTietHoaDon chiTiet : list) {
                if (chiTiet.tinhThanhTien() > maxGia) {
                    maxGia = chiTiet.tinhThanhTien();
                    maxChiTiet = chiTiet;
                }
            }
        }
        return maxChiTiet;
    }

    /**
     * Lấy chi tiết có giá thấp nhất
     */
    public ChiTietHoaDon getChiTietGiaMin(String maHD) {
        List<ChiTietHoaDon> list = getChiTietByMaHD(maHD);
        ChiTietHoaDon minChiTiet = null;
        double minGia = Double.MAX_VALUE;

        if (list != null && !list.isEmpty()) {
            minGia = list.get(0).tinhThanhTien();
            minChiTiet = list.get(0);

            for (ChiTietHoaDon chiTiet : list) {
                if (chiTiet.tinhThanhTien() < minGia) {
                    minGia = chiTiet.tinhThanhTien();
                    minChiTiet = chiTiet;
                }
            }
        }
        return minChiTiet;
    }

	
    
}
