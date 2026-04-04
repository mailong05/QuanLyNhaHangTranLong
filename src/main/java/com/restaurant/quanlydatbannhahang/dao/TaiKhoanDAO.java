package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;
import com.restaurant.quanlydatbannhahang.entity.QuyenHan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoanDAO {
    private Connection connection;
    private NhanVienDAO nv_dao;

    public TaiKhoanDAO() {
        this.connection = DatabaseConnection.getConnection();
        nv_dao = new NhanVienDAO();
    }

    /**
     * Tìm tài khoản theo username và password
     * 
     * @param username
     * @param password
     * @return TaiKhoan object nếu tìm thấy, null nếu không
     */
    public TaiKhoan findByUsernameAndPassword(String username, String password) {
        String query = "SELECT tk.username, tk.password, tk.maNV, tk.quyenHan, " +
                "nv.hoTen, nv.sdt, nv.chucVu, nv.ngayVaoLam, nv.luongCoBan, nv.trangThai " +
                "FROM TaiKhoan tk " +
                "INNER JOIN NhanVien nv ON tk.maNV = nv.maNV " +
                "WHERE tk.username = ? AND tk.password = ?";

        try (PreparedStatement pstm = connection.prepareStatement(query)) {
            pstm.setString(1, username);
            pstm.setString(2, password);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    // Tạo object NhanVien từ ResultSet (sử dụng buildNhanVienFromResultSet)
                    NhanVien nhanVien = nv_dao.buildNhanVienFromResultSet(rs);

                    // Tạo object TaiKhoan
                    TaiKhoan taiKhoan = new TaiKhoan();
                    taiKhoan.setUsername(rs.getString("username"));
                    taiKhoan.setPassword(rs.getString("password"));
                    taiKhoan.setNhanVien(nhanVien);

                    String quyenHanStr = rs.getString("quyenHan");
                    if (quyenHanStr != null && !quyenHanStr.isEmpty()) {
                        try {
                            taiKhoan.setQuyenHan(QuyenHan.valueOf(quyenHanStr));
                        } catch (IllegalArgumentException e) {
                            System.out.println("❌ Giá trị quền hạn không hợp lệ: " + quyenHanStr);
                            taiKhoan.setQuyenHan(QuyenHan.MANAGER); // Mặc định là Manager
                        }
                    }

                    return taiKhoan;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi tìm tài khoản: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lấy tài khoản theo username
     */
    public TaiKhoan findByUsername(String username) {
        String query = "SELECT tk.username, tk.password, tk.maNV, tk.quyenHan, " +
                "nv.hoTen, nv.sdt, nv.chucVu, nv.ngayVaoLam, nv.luongCoBan, nv.trangThai " +
                "FROM TaiKhoan tk " +
                "INNER JOIN NhanVien nv ON tk.maNV = nv.maNV " +
                "WHERE tk.username = ?";

        try (PreparedStatement pstm = connection.prepareStatement(query)) {
            pstm.setString(1, username);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    // Tạo object NhanVien từ ResultSet
                    NhanVien nhanVien = nv_dao.buildNhanVienFromResultSet(rs);

                    TaiKhoan taiKhoan = new TaiKhoan();
                    taiKhoan.setUsername(rs.getString("username"));
                    taiKhoan.setPassword(rs.getString("password"));
                    taiKhoan.setNhanVien(nhanVien);

                    String quyenHanStr = rs.getString("quyenHan");
                    if (quyenHanStr != null && !quyenHanStr.isEmpty()) {
                        try {
                            taiKhoan.setQuyenHan(QuyenHan.valueOf(quyenHanStr));
                        } catch (IllegalArgumentException e) {
                            System.out.println("❌ Giá trị quền hạn không hợp lệ: " + quyenHanStr);
                            taiKhoan.setQuyenHan(QuyenHan.MANAGER); // Mặc định là Manager
                        }
                    }

                    return taiKhoan;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi tìm tài khoản: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Kiểm tra tài khoản tồn tại
     */
    public boolean existUsername(String username) {
        String query = "SELECT COUNT(*) FROM TaiKhoan WHERE username = ?";

        try (PreparedStatement pstm = connection.prepareStatement(query)) {
            pstm.setString(1, username);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi kiểm tra tài khoản: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
