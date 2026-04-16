package com.restaurant.quanlydatbannhahang.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.ChucVu;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiNhanVien;

public class NhanVienDAO {
    private Connection connection;

    public NhanVienDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Map dữ liệu từ ResultSet sang đối tượng NhanVien
     * Chỉnh sửa: Đọc String từ DB và convert sang Enum thay vì dùng Boolean
     */
    public NhanVien buildNhanVienFromResultSet(ResultSet rs) {
        try {
            String chucVuStr = rs.getString("chucVu");
            ChucVu chucVu = ChucVu.valueOf(chucVuStr);

            String trangThaiStr = rs.getString("trangThai");
            TrangThaiNhanVien trangThai = TrangThaiNhanVien.valueOf(trangThaiStr);

            return new NhanVien(
                    rs.getString("maNV"),
                    rs.getString("hoTen"),
                    rs.getString("sdt"),
                    chucVu,
                    rs.getDate("ngayVaoLam").toLocalDate(),
                    rs.getDouble("luongCoBan"),
                    trangThai);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                NhanVien nv = buildNhanVienFromResultSet(rs);
                if (nv != null) list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public NhanVien getNhanVienTheoMa(String maNV) {
        String sql = "SELECT * FROM NhanVien WHERE maNV = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maNV);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) return buildNhanVienFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean themNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (maNV, hoTen, sdt, chucVu, ngayVaoLam, luongCoBan, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, nv.getMaNV());
            pstm.setString(2, nv.getHoTen());
            pstm.setString(3, nv.getSdt());
            pstm.setString(4, nv.getChucVu().name()); 
            pstm.setDate(5, java.sql.Date.valueOf(nv.getNgayVaoLam()));
            pstm.setDouble(6, nv.getLuongCoBan());
            pstm.setString(7, nv.getTrangThai().name()); 
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatNhanVien(NhanVien nv) {
        String sql = "UPDATE NhanVien SET hoTen = ?, sdt = ?, chucVu = ?, ngayVaoLam = ?, luongCoBan = ?, trangThai = ? WHERE maNV = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, nv.getHoTen());
            pstm.setString(2, nv.getSdt());
            pstm.setString(3, nv.getChucVu().name());
            pstm.setDate(4, java.sql.Date.valueOf(nv.getNgayVaoLam()));
            pstm.setDouble(5, nv.getLuongCoBan());
            pstm.setString(6, nv.getTrangThai().name());
            pstm.setString(7, nv.getMaNV());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaNhanVien(String maNV) {
        String sql = "DELETE FROM NhanVien WHERE maNV = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maNV);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<NhanVien> timKiemNhanVien(String keyword) {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE hoTen LIKE ? OR sdt LIKE ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, "%" + keyword + "%");
            pstm.setString(2, "%" + keyword + "%");
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    NhanVien nv = buildNhanVienFromResultSet(rs);
                    if (nv != null) list.add(nv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<NhanVien> filterTheoChucVu(String chucVuName) {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE chucVu = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, chucVuName);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    NhanVien nv = buildNhanVienFromResultSet(rs);
                    if (nv != null) list.add(nv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<NhanVien> getNhanVienDangLamViec() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE trangThai = 'DANG_LAM_VIEC'";
        try (PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                NhanVien nv = buildNhanVienFromResultSet(rs);
                if (nv != null) list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}