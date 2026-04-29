package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.QuyenHanDAO;
import com.restaurant.quanlydatbannhahang.entity.QuyenHan;

import java.util.List;

public class QuyenHanService {
    private QuyenHanDAO quyenHanDAO;

    public QuyenHanService() {
        this.quyenHanDAO = new QuyenHanDAO();
    }

    /**
     * Lấy tất cả quyền hạn
     */
    public List<QuyenHan> getAllQuyenHan() {
        return quyenHanDAO.getAllQuyenHan();
    }

    /**
     * Tìm quyền hạn theo tên
     */
    public QuyenHan findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên quyền hạn không được để trống");
        }
        QuyenHan quyenHan = quyenHanDAO.findByName(name);
        if (quyenHan == null) {
            throw new RuntimeException("Không tìm thấy quyền hạn: " + name);
        }
        return quyenHan;
    }

    /**
     * Kiểm tra quyền hạn tồn tại
     */
    public boolean exists(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return quyenHanDAO.findByName(name) != null;
    }

    /**
     * Kiểm tra người dùng có quyền hay không
     */
    public boolean hasPermission(QuyenHan userPermission, QuyenHan requiredPermission) {
        if (userPermission == null || requiredPermission == null) {
            return false;
        }
        // So sánh quyền hạn
        return userPermission.equals(requiredPermission) || userPermission == QuyenHan.MANAGER;
    }

    /**
     * Tính tổng số loại quyền hạn
     */
    public int getTotalQuyenHan() {
        List<QuyenHan> list = getAllQuyenHan();
        return list != null ? list.size() : 0;
    }

    /**
     * Lấy quyền hạn manager
     */
    public QuyenHan getQuyenHanManager() {
        return QuyenHan.MANAGER;
    }

    /**
     * Lấy quyền hạn nhân viên
     */
    public QuyenHan getQuyenHanStaff() {
        return QuyenHan.STAFF;
    }
}
