package com.restaurant.quanlydatbannhahang.service;
import com.restaurant.quanlydatbannhahang.dao.QuyenHanDAO;
import com.restaurant.quanlydatbannhahang.entity.QuyenHan;
import java.util.List;
public class QuyenHanService {
    private QuyenHanDAO quyenHanDAO;
    public QuyenHanService() {
        this.quyenHanDAO = new QuyenHanDAO();
    }
    public List<QuyenHan> getAllQuyenHan() {
        return quyenHanDAO.getAllQuyenHan();
    }
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
    public boolean exists(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return quyenHanDAO.findByName(name) != null;
    }
    public boolean hasPermission(QuyenHan userPermission, QuyenHan requiredPermission) {
        if (userPermission == null || requiredPermission == null) {
            return false;
        }
        return userPermission.equals(requiredPermission) || userPermission == QuyenHan.MANAGER;
    }
    public int getTotalQuyenHan() {
        List<QuyenHan> list = getAllQuyenHan();
        return list != null ? list.size() : 0;
    }
    public QuyenHan getQuyenHanManager() {
        return QuyenHan.MANAGER;
    }
    public QuyenHan getQuyenHanStaff() {
        return QuyenHan.STAFF;
    }
}