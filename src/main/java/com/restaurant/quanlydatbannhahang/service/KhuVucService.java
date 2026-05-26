package com.restaurant.quanlydatbannhahang.service;
import com.restaurant.quanlydatbannhahang.dao.KhuVucDAO;
import com.restaurant.quanlydatbannhahang.entity.KhuVuc;
import java.util.List;
import java.util.regex.Pattern;
public class KhuVucService {
    private KhuVucDAO khuVucDAO;
    private static final String MAKV_PATTERN = "^KV\\d{3}$";
    private static final String TENKV_PATTERN = "^[\\p{Lu}][\\p{Ll}\\d\\s]*$";
    private static final Pattern maKVPattern = Pattern.compile(MAKV_PATTERN);
    private static final Pattern tenKVPattern = Pattern.compile(TENKV_PATTERN);
    public KhuVucService() {
        this.khuVucDAO = new KhuVucDAO();
    }
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
    public List<KhuVuc> getAllKhuVuc() {
        return khuVucDAO.getAllKhuVuc();
    }
    public void themKhuVuc(KhuVuc khuVuc) {
        validateKhuVuc(khuVuc);
        if (!khuVucDAO.themKhuVuc(khuVuc)) {
            throw new RuntimeException("Thêm khu vực thất bại");
        }
    }
    public void capNhatKhuVuc(KhuVuc khuVuc) {
        validateKhuVuc(khuVuc);
        if (!khuVucDAO.capNhatKhuVuc(khuVuc)) {
            throw new RuntimeException("Cập nhật khu vực thất bại");
        }
    }
    public void xoaKhuVuc(String maKhuVuc) {
        if (maKhuVuc == null || maKhuVuc.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khu vực không được để trống");
        }
        if (!khuVucDAO.xoaKhuVuc(maKhuVuc)) {
            throw new RuntimeException("Xóa khu vực thất bại");
        }
    }
    public boolean existKhuVuc(String maKhuVuc) {
        if (maKhuVuc == null || maKhuVuc.trim().isEmpty()) {
            return false;
        }
        return khuVucDAO.getKhuVucTheoMa(maKhuVuc) != null;
    }
    public int getTotalKhuVuc() {
        List<KhuVuc> list = getAllKhuVuc();
        return list != null ? list.size() : 0;
    }
    public String getLastKhuVucID() {
        return khuVucDAO.getLastKhuVucID();
    }
    
    public boolean kiemTraKhuVucCoChuaBan(String maKhuVuc) {
        if (maKhuVuc == null || maKhuVuc.isBlank()) {
            throw new IllegalArgumentException("Mã khu vực kiểm tra không được để trống");
        }
        return khuVucDAO.kiemTraKhuVucCoChuaBan(maKhuVuc);
    }
}