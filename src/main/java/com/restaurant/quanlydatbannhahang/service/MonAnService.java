package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.MonAnDAO;
import com.restaurant.quanlydatbannhahang.entity.MonAn;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiMonAn;

import java.util.List;

public class MonAnService {
    private MonAnDAO monAnDAO;

    public MonAnService() {
        this.monAnDAO = new MonAnDAO();
    }

    /**
     * Thêm món ăn mới
     */
    public void themMonAn(MonAn monAn) {
        if (monAn == null) {
            throw new IllegalArgumentException("Món ăn không được để trống");
        }
        if (monAn.getMaMon() == null || monAn.getMaMon().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã món không được để trống");
        }
        if (monAn.getTenMon() == null || monAn.getTenMon().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên món không được để trống");
        }
        if (monAn.getDonGia() <= 0) {
            throw new IllegalArgumentException("Giá phải lớn hơn 0");
        }
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
        if (maMon == null || maMon.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã món không được để trống");
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
        if (maLoai == null || maLoai.trim().isEmpty()) {
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
        if (monAn == null) {
            throw new IllegalArgumentException("Món ăn không được để trống");
        }
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
        if (maMon == null || maMon.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã món không được để trống");
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
        if (maMon == null || maMon.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã món không được để trống");
        }
        if (trangThai == null) {
            throw new IllegalArgumentException("Trạng thái không được để trống");
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
        if (maMon == null || maMon.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã món không được để trống");
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
}
