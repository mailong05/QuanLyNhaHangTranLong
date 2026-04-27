package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.KhachHangDAO;
import com.restaurant.quanlydatbannhahang.entity.KhachHang;

import java.util.List;

public class KhachHangService {
    private KhachHangDAO khachHangDAO;

    public KhachHangService() {
        this.khachHangDAO = new KhachHangDAO();
    }

    /**
     * Lấy khách hàng theo mã
     */
    public KhachHang getKhachHangTheoMa(String maKH) {
        if (maKH == null || maKH.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống");
        }
        KhachHang khachHang = khachHangDAO.getKhachHangTheoMa(maKH);
        if (khachHang == null) {
            System.out.println(" Không tìm thấy khách hàng với mã: " + maKH);
        }
        return khachHang;
    }

    /**
     * Lấy tất cả khách hàng
     */
    public List<KhachHang> getAllKhachHang() {
        return khachHangDAO.getAllKhachHang();
    }

    /**
     * Thêm khách hàng mới
     */
    public boolean themKhachHang(KhachHang khachHang) {
        if (khachHang == null) {
            throw new IllegalArgumentException("Khách hàng không được để trống");
        }
        if (khachHang.getMaKH() == null || khachHang.getMaKH().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống");
        }
        if (khachHang.getHoTen() == null || khachHang.getHoTen().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống");
        }
        boolean success = khachHangDAO.themKhachHang(khachHang);
        if (success) {
            System.out.println(" Thêm khách hàng thành công");
        } else {
            System.out.println(" Thêm khách hàng thất bại");
        }
        return success;
    }

    /**
     * Cập nhật khách hàng
     */
    public boolean capNhatKhachHang(KhachHang khachHang) {
        if (khachHang == null) {
            throw new IllegalArgumentException("Khách hàng không được để trống");
        }
        boolean success = khachHangDAO.capNhatKhachHang(khachHang);
        if (success) {
            System.out.println(" Cập nhật khách hàng thành công");
        } else {
            System.out.println(" Cập nhật khách hàng thất bại");
        }
        return success;
    }

    /**
     * Xóa khách hàng
     */
    public boolean xoaKhachHang(String maKH) {
        if (maKH == null || maKH.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống");
        }
        boolean success = khachHangDAO.xoaKhachHang(maKH);
        if (success) {
            System.out.println(" Xóa khách hàng thành công");
        } else {
            System.out.println(" Xóa khách hàng thất bại");
        }
        return success;
    }

    /**
     * Lấy khách hàng theo số điện thoại
     */
    public KhachHang getKhachHangTheoSDT(String sdt) {
        if (sdt == null || sdt.trim().isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống");
        }
        return khachHangDAO.getKhachHangTheoSDT(sdt);
    }

    /**
     * Cập nhật điểm tích lũy
     */
    public void capNhatDiemTichLuy(String maKH, int diemThem) {
        KhachHang khachHang = getKhachHangTheoMa(maKH);
        if (khachHang != null) {
            int diemMoi = khachHang.getDiemTichLuy() + diemThem;
            khachHang.setDiemTichLuy(diemMoi);
            capNhatKhachHang(khachHang);
            System.out.println(" Cập nhật điểm tích lũy thành công. Điểm hiện tại: " + diemMoi);
        }
    }

    /**
     * Sử dụng điểm tích lũy
     */
    public boolean suDungDiemTichLuy(String maKH, int diemSuDung) {
        KhachHang khachHang = getKhachHangTheoMa(maKH);
        if (khachHang != null) {
           
                int diemConLai = Math.max(khachHang.getDiemTichLuy() - diemSuDung, 0);
                khachHang.setDiemTichLuy(diemConLai);
                capNhatKhachHang(khachHang);
                System.out.println(" Sử dụng điểm tích lũy thành công. Điểm còn lại: " + diemConLai);
                return true;
           
        }
        return false;
    }

    /**
     * Tính tổng số khách hàng
     */
    public int getTotalKhachHang() {
        List<KhachHang> list = getAllKhachHang();
        return list != null ? list.size() : 0;
    }

    /**
     * Kiểm tra khách hàng tồn tại
     */
    public boolean existKhachHang(String maKH) {
        return getKhachHangTheoMa(maKH) != null;
    }

    /**
     * Lấy mã khách hàng cuối cùng để sinh mã tiếp theo
     * 
     * @return Mã khách hàng cuối cùng (VD: KH000) hoặc null nếu bảng rỗng
     */
    public String getLastKhachHangID() {
        return khachHangDAO.getLastKhachHangID();
    }
}
