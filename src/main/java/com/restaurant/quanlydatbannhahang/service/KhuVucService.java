package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.KhuVucDAO;
import com.restaurant.quanlydatbannhahang.entity.KhuVuc;

import java.util.List;

public class KhuVucService {
    private KhuVucDAO khuVucDAO;

    public KhuVucService() {
        this.khuVucDAO = new KhuVucDAO();
    }

    /**
     * Lấy khu vực theo mã
     */
    public KhuVuc getKhuVucTheoMa(String maKhuVuc) {
        if (maKhuVuc == null || maKhuVuc.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khu vực không được để trống");
        }
        KhuVuc khuVuc = khuVucDAO.getKhuVucTheoMa(maKhuVuc);
        if (khuVuc == null) {
            System.out.println(" Không tìm thấy khu vực với mã: " + maKhuVuc);
        }
        return khuVuc;
    }

    /**
     * Lấy tất cả khu vực
     */
    public List<KhuVuc> getAllKhuVuc() {
        return khuVucDAO.getAllKhuVuc();
    }

    /**
     * Thêm khu vực mới
     */
    public void themKhuVuc(KhuVuc khuVuc) {
        if (khuVuc == null) {
            throw new IllegalArgumentException("Khu vực không được để trống");
        }
        if (khuVuc.getMaKhuVuc() == null || khuVuc.getMaKhuVuc().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khu vực không được để trống");
        }
        if (khuVuc.getTenKhuVuc() == null || khuVuc.getTenKhuVuc().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khu vực không được để trống");
        }
        if (khuVucDAO.themKhuVuc(khuVuc)) {
            System.out.println(" Thêm khu vực thành công");
        } else {
            System.out.println(" Thêm khu vực thất bại");
        }
    }

    /**
     * Cập nhật khu vực
     */
    public void capNhatKhuVuc(KhuVuc khuVuc) {
        if (khuVuc == null) {
            throw new IllegalArgumentException("Khu vực không được để trống");
        }
        if (khuVucDAO.capNhatKhuVuc(khuVuc)) {
            System.out.println(" Cập nhật khu vực thành công");
        } else {
            System.out.println(" Cập nhật khu vực thất bại");
        }
    }

    /**
     * Xóa khu vực
     */
    public void xoaKhuVuc(String maKhuVuc) {
        if (maKhuVuc == null || maKhuVuc.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khu vực không được để trống");
        }
        if (khuVucDAO.xoaKhuVuc(maKhuVuc)) {
            System.out.println(" Xóa khu vực thành công");
        } else {
            System.out.println(" Xóa khu vực thất bại");
        }
    }

    /**
     * Kiểm tra khu vực tồn tại
     */
    public boolean existKhuVuc(String maKhuVuc) {
        return getKhuVucTheoMa(maKhuVuc) != null;
    }

    /**
     * Tính tổng số khu vực
     */
    public int getTotalKhuVuc() {
        List<KhuVuc> list = getAllKhuVuc();
        return list != null ? list.size() : 0;
    }

    /**
     * Lấy mã khu vực cuối cùng để sinh mã tiếp theo
     * 
     * @return Mã khu vực cuối cùng (VD: KV000) hoặc null nếu bảng rỗng
     */
    public String getLastKhuVucID() {
        return khuVucDAO.getLastKhuVucID();
    }

	
}
