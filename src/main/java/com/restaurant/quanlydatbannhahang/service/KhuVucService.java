package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.KhuVucDAO;
import com.restaurant.quanlydatbannhahang.entity.KhuVuc;

import java.util.List;
import java.util.regex.Pattern;

public class KhuVucService {
    private KhuVucDAO khuVucDAO;

    private static final String MAKV_PATTERN = "^KV\\d{3}$";
    private static final String TENKV_PATTERN = "^[\\p{Lu}][\\p{Ll}]*(\\s[\\p{Lu}][\\p{Ll}]*)*$";

    private static final Pattern maKVPattern = Pattern.compile(MAKV_PATTERN);
    private static final Pattern tenKVPattern = Pattern.compile(TENKV_PATTERN);

    public KhuVucService() {
        this.khuVucDAO = new KhuVucDAO();
    }

    /**
     * Validate đối tượng KhuVuc
     */
    public void validateKhuVuc(KhuVuc khuVuc) {
        if (khuVuc == null) {
            throw new IllegalArgumentException("Đối tượng khu vực không được để trống");
        }
        if (khuVuc.getMaKhuVuc() == null || khuVuc.getMaKhuVuc().isBlank()) {
            throw new IllegalArgumentException("Mã khu vực không được để trống");
        }
        if (!maKVPattern.matcher(khuVuc.getMaKhuVuc()).matches()) {
            throw new IllegalArgumentException("Mã khu vực phải có dạng KVxxx (ví dụ: KV001)");
        }
        if (khuVuc.getTenKhuVuc() == null || khuVuc.getTenKhuVuc().isBlank()) {
            throw new IllegalArgumentException("Tên khu vực không được để trống");
        }
        if (!tenKVPattern.matcher(khuVuc.getTenKhuVuc()).matches()) {
            throw new IllegalArgumentException("Tên khu vực phải viết hoa chữ cái đầu mỗi từ");
        }
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
            throw new RuntimeException("Không tìm thấy khu vực với mã: " + maKhuVuc);
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
        validateKhuVuc(khuVuc);
        if (!khuVucDAO.themKhuVuc(khuVuc)) {
            throw new RuntimeException("Thêm khu vực thất bại");
        }
    }

    /**
     * Cập nhật khu vực
     */
    public void capNhatKhuVuc(KhuVuc khuVuc) {
        validateKhuVuc(khuVuc);
        if (!khuVucDAO.capNhatKhuVuc(khuVuc)) {
            throw new RuntimeException("Cập nhật khu vực thất bại");
        }
    }

    /**
     * Xóa khu vực
     */
    public void xoaKhuVuc(String maKhuVuc) {
        if (maKhuVuc == null || maKhuVuc.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khu vực không được để trống");
        }
        if (!khuVucDAO.xoaKhuVuc(maKhuVuc)) {
            throw new RuntimeException("Xóa khu vực thất bại");
        }
    }

    /**
     * Kiểm tra khu vực tồn tại
     */
    public boolean existKhuVuc(String maKhuVuc) {
        if (maKhuVuc == null || maKhuVuc.trim().isEmpty()) {
            return false;
        }
        return khuVucDAO.getKhuVucTheoMa(maKhuVuc) != null;
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
