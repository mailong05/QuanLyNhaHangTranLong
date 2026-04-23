package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;
import com.restaurant.quanlydatbannhahang.entity.QuyenHan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO {
    private Connection connection;
    private NhanVienDAO nv_dao;

    private static final String SELECT_TAIKHOAN_WITH_NHANVIEN = "SELECT tk.username, tk.password, tk.maNV, tk.quyenHan, "
            +
            "nv.hoTen, nv.sdt, nv.chucVu, nv.ngayVaoLam, nv.luongCoBan, nv.trangThai " +
            "FROM TaiKhoan tk " +
            "INNER JOIN NhanVien nv ON tk.maNV = nv.maNV ";

    public TaiKhoanDAO() {
        this.connection = DatabaseConnection.getConnection();
        nv_dao = new NhanVienDAO();
    }

    /**
     * Build TaiKhoan từ ResultSet
     */
    private TaiKhoan buildTaiKhoanFromResultSet(ResultSet rs) throws SQLException {
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
                taiKhoan.setQuyenHan(QuyenHan.MANAGER);
            }
        }

        return taiKhoan;
    }

    /**
     * Tìm tài khoản theo username và password
     */
    public TaiKhoan findByUsernameAndPassword(String username, String password) {
        Connection connection = DatabaseConnection.getConnection();
        String query = SELECT_TAIKHOAN_WITH_NHANVIEN + "WHERE tk.username = ? AND tk.password = ?";

        try (PreparedStatement pstm = connection.prepareStatement(query)) {
            pstm.setString(1, username);
            pstm.setString(2, password);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return buildTaiKhoanFromResultSet(rs);
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
        Connection connection = DatabaseConnection.getConnection();
        String query = SELECT_TAIKHOAN_WITH_NHANVIEN + "WHERE tk.username = ?";

        try (PreparedStatement pstm = connection.prepareStatement(query)) {
            pstm.setString(1, username);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return buildTaiKhoanFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi tìm tài khoản theo username: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Kiểm tra tài khoản tồn tại
     */
    public boolean existUsername(String username) {
        Connection connection = DatabaseConnection.getConnection();
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

    public boolean updatePassword(String username, String newPassWord) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "Update TaiKhoan set password = ? where username = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, newPassWord);
            pstm.setString(2, username);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return false;

    }

    public List<TaiKhoan> getAllTaiKhoan() {
        Connection connection = DatabaseConnection.getConnection();
        String query = SELECT_TAIKHOAN_WITH_NHANVIEN;
        List<TaiKhoan> dsTaiKhoan = new ArrayList<TaiKhoan>();
        try (PreparedStatement pstm = connection.prepareStatement(query)) {

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    dsTaiKhoan.add(buildTaiKhoanFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi tìm tài khoản: " + e.getMessage());
            e.printStackTrace();
        }
        return dsTaiKhoan;
    }
}
