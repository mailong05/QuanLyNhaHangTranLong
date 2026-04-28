package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.MonAnDAO;
import com.restaurant.quanlydatbannhahang.entity.MonAn;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiMonAn;

import java.util.List;
import java.util.regex.Pattern;

public class MonAnService {
    private MonAnDAO monAnDAO;

    private static final String MAMON_PATTERN = "^MA\\d{3}$";
    private static final String TENMON_PATTERN = "^[\\p{Lu}][\\p{Ll}]+(\\s[\\p{Lu}][\\p{Ll}]+)*$";

    private static final Pattern maMonPattern = Pattern.compile(MAMON_PATTERN);
    private static final Pattern tenMonPattern = Pattern.compile(TENMON_PATTERN);

    public MonAnService() {
        this.monAnDAO = new MonAnDAO();
    }

    /**
     * Validate đối tượng MonAn
     */
    public void validateMonAn(MonAn monAn) {
        if (monAn == null) {
            throw new IllegalArgumentException("Đối tượng món ăn không được để trống");
        }
        if (monAn.getMaMon() == null || monAn.getMaMon().isBlank()) {
            throw new IllegalArgumentException("Mã món không được để trống");
        }
        if (!maMonPattern.matcher(monAn.getMaMon()).matches()) {
            throw new IllegalArgumentException("Mã món phải có dạng MAxxx (ví dụ: MA001)");
        }
        if (monAn.getTenMon() == null || monAn.getTenMon().isBlank()) {
            throw new IllegalArgumentException("Tên món không được để trống");
        }
        if (!tenMonPattern.matcher(monAn.getTenMon()).matches()) {
            throw new IllegalArgumentException("Tên món phải viết hoa chữ cái đầu mỗi từ");
        }
        if (monAn.getDonGia() <= 0) {
            throw new IllegalArgumentException("Đơn giá phải lớn hơn 0");
        }
        if (monAn.getDonViTinh() == null || monAn.getDonViTinh().isBlank()) {
            throw new IllegalArgumentException("Đơn vị tính không được để trống");
        }
        if (monAn.getTenLoai() == null) {
            throw new IllegalArgumentException("Loại món ăn không được để trống");
        }
        if (monAn.getTrangThai() == null) {
            throw new IllegalArgumentException("Trạng thái món ăn không được để trống");
        }
    }

    /**
     * Thêm món ăn mới
     */
    public void themMonAn(MonAn monAn) {
        validateMonAn(monAn);
        if (monAnDAO.themMonAn(monAn)) {
            System.out.println(" Thêm món ăn thành công");
        } else {
            System.out.println(" Thêm món ăn thất bại");
        }
    }

    /**
     * Lấy món ăn theo mã
     */
    public MonAn getMonAnTheoMa(String maMon) {
        if (maMon == null || maMon.isBlank()) {
            throw new IllegalArgumentException("Mã món không được để trống");
        }
        if (!maMonPattern.matcher(maMon).matches()) {
            throw new IllegalArgumentException("Mã món phải có dạng MAxxx (ví dụ: MA001)");
        }
        MonAn monAn = monAnDAO.getMonAnTheoMa(maMon);
        if (monAn == null) {
            System.out.println(" Không tìm thấy món ăn với mã: " + maMon);
        }
        return monAn;
    }

    /**
     * Lấy tất cả món ăn
     */
    public List<MonAn> getAllMonAn() {
        return monAnDAO.getAllMonAn();
    }

    /**
     * Lấy món ăn theo loại
     */
    public List<MonAn> getMonAnTheoLoai(String maLoai) {
        if (maLoai == null || maLoai.isBlank()) {
            throw new IllegalArgumentException("Mã loại không được để trống");
        }
        return monAnDAO.getMonAnTheoLoai(maLoai);
    }

    /**
     * Lấy món ăn còn hàng
     */
    public List<MonAn> getMonAnConHang() {
        return monAnDAO.getMonAnConHang();
    }

    /**
     * Cập nhật thông tin món ăn
     */
    public void capNhatMonAn(MonAn monAn) {
        validateMonAn(monAn);
        if (monAnDAO.capNhatMonAn(monAn)) {
            System.out.println(" Cập nhật món ăn thành công");
        } else {
            System.out.println(" Cập nhật món ăn thất bại");
        }
    }

    /**
     * Cập nhật giá món ăn
     */
    public void capNhatGiaMonAn(String maMon, double giaMoi) {
        if (maMon == null || maMon.isBlank()) {
            throw new IllegalArgumentException("Mã món không được để trống");
        }
        if (!maMonPattern.matcher(maMon).matches()) {
            throw new IllegalArgumentException("Mã món phải có dạng MAxxx (ví dụ: MA001)");
        }
        if (giaMoi <= 0) {
            throw new IllegalArgumentException("Giá phải lớn hơn 0");
        }
        if (monAnDAO.capNhatGiaMonAn(maMon, giaMoi)) {
            System.out.println(" Cập nhật giá món ăn thành công");
        } else {
            System.out.println(" Cập nhật giá món ăn thất bại");
        }
    }

    /**
     * Cập nhật trạng thái món ăn
     */
    public void capNhatTrangThaiMonAn(String maMon, TrangThaiMonAn trangThai) {
        if (maMon == null || maMon.isBlank()) {
            throw new IllegalArgumentException("Mã món không được để trống");
        }
        if (!maMonPattern.matcher(maMon).matches()) {
            throw new IllegalArgumentException("Mã món phải có dạng MAxxx (ví dụ: MA001)");
        }
        if (trangThai == null) {
            throw new IllegalArgumentException("Trạng thái món ăn không được để trống");
        }
        MonAn monAn = getMonAnTheoMa(maMon);
        if (monAn != null) {
            monAn.setTrangThai(trangThai);
            capNhatMonAn(monAn);
        }
    }

    /**
     * Xóa món ăn
     */
    public void xoaMonAn(String maMon) {
        if (maMon == null || maMon.isBlank()) {
            throw new IllegalArgumentException("Mã món không được để trống");
        }
        if (!maMonPattern.matcher(maMon).matches()) {
            throw new IllegalArgumentException("Mã món phải có dạng MAxxx (ví dụ: MA001)");
        }
        if (monAnDAO.xoaMonAn(maMon)) {
            System.out.println(" Xóa món ăn thành công");
        } else {
            System.out.println(" Xóa món ăn thất bại");
        }
    }

    /**
     * Kiểm tra món ăn còn hàng
     */
    public boolean isMonAnConHang(String maMon) {
        MonAn monAn = getMonAnTheoMa(maMon);
        return monAn != null && monAn.getTrangThai() == TrangThaiMonAn.CON;
    }

    /**
     * Tính tổng số lượng các món ăn
     */
    public int getTotalMonAn() {
        List<MonAn> list = getAllMonAn();
        return list != null ? list.size() : 0;
    }

    /**
     * Tính tổng số lượng món ăn còn hàng
     */
    public int getTotalMonAnConHang() {
        List<MonAn> list = getMonAnConHang();
        return list != null ? list.size() : 0;
    }

    /**
     * Lấy mã món ăn cuối cùng để sinh mã tiếp theo
     *
     * @return Mã món ăn cuối cùng (VD: MA000) hoặc null nếu bảng rỗng
     */
    public String getLastMonAnID() {
        return monAnDAO.getLastMonAnID();
    }
}
