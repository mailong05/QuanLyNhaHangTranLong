package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TaiKhoanDAO {
    private Connection connection;

    public TaiKhoanDAO() {
        this.connection = DatabaseConnection.getConnection();
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
                    // Tạo object NhanVien
                    NhanVien nhanVien = new NhanVien();
                    nhanVien.setMaNV(rs.getString("maNV"));
                    nhanVien.setHoTen(rs.getString("hoTen"));
                    nhanVien.setSdt(rs.getString("sdt"));
                    nhanVien.setChucVu(rs.getString("chucVu"));
                    nhanVien.setNgayVaoLam(rs.getDate("ngayVaoLam").toLocalDate());
                    nhanVien.setLuongCoBan(rs.getDouble("luongCoBan"));
                    nhanVien.setTrangThai(rs.getBoolean("trangThai"));

                    // Tạo object TaiKhoan
                    TaiKhoan taiKhoan = new TaiKhoan();
                    taiKhoan.setUsername(rs.getString("username"));
                    taiKhoan.setPassword(rs.getString("password"));
                    taiKhoan.setMaNV(rs.getString("maNV"));
                    taiKhoan.setQuyenHan(rs.getString("quyenHan"));
                    taiKhoan.setNhanVien(nhanVien);

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
                    NhanVien nhanVien = new NhanVien();
                    nhanVien.setMaNV(rs.getString("maNV"));
                    nhanVien.setHoTen(rs.getString("hoTen"));
                    nhanVien.setSdt(rs.getString("sdt"));
                    nhanVien.setChucVu(rs.getString("chucVu"));
                    nhanVien.setNgayVaoLam(rs.getDate("ngayVaoLam").toLocalDate());
                    nhanVien.setLuongCoBan(rs.getDouble("luongCoBan"));
                    nhanVien.setTrangThai(rs.getBoolean("trangThai"));

                    TaiKhoan taiKhoan = new TaiKhoan();
                    taiKhoan.setUsername(rs.getString("username"));
                    taiKhoan.setPassword(rs.getString("password"));
                    taiKhoan.setMaNV(rs.getString("maNV"));
                    taiKhoan.setQuyenHan(rs.getString("quyenHan"));
                    taiKhoan.setNhanVien(nhanVien);

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
