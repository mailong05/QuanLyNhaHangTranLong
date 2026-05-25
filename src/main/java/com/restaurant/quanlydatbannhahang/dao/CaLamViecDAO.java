package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.CaLamViec;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiCa;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CaLamViecDAO {
    private NhanVienDAO nhanVienDAO;

    public CaLamViecDAO() {
        this.nhanVienDAO = new NhanVienDAO();
    }

    public String getLastMaCa() {
        return IDQueryHelper.getLastID("CaLamViec", "maCa");
    }

    private CaLamViec buildCaLamViecFromResultSet(ResultSet rs) {
        try {
            String maCa = rs.getString("maCa");
            String maNV = rs.getString("maNV");
            Timestamp vaoCaTs = rs.getTimestamp("thoiGianVaoCa");
            Timestamp ketCaTs = rs.getTimestamp("thoiGianKetCa");
            double tienDauCa = rs.getDouble("tienDauCa");
            Double tienKetCa = rs.getObject("tienKetCa") != null ? rs.getDouble("tienKetCa") : null;
            String trangThaiStr = rs.getString("trangThai");
            String ghiChu = rs.getString("ghiChu");

            NhanVien nhanVien = nhanVienDAO.getNhanVienTheoMa(maNV);
            CaLamViec ca = new CaLamViec();
            ca.setMaCa(maCa);
            ca.setNhanVien(nhanVien);
            ca.setThoiGianVaoCa(vaoCaTs != null ? vaoCaTs.toLocalDateTime() : null);
            ca.setThoiGianKetCa(ketCaTs != null ? ketCaTs.toLocalDateTime() : null);
            ca.setTienDauCa(tienDauCa);
            ca.setTienKetCa(tienKetCa);
            ca.setTrangThai(TrangThaiCa.fromName(trangThaiStr));
            ca.setGhiChu(ghiChu);
            return ca;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean themCaLamViec(CaLamViec ca) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "insert into CaLamViec (maCa, maNV, thoiGianVaoCa, thoiGianKetCa, tienDauCa, tienKetCa, trangThai, ghiChu) values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, ca.getMaCa());
            pstm.setString(2, ca.getNhanVien().getMaNV());
            pstm.setTimestamp(3, Timestamp.valueOf(ca.getThoiGianVaoCa()));
            pstm.setTimestamp(4, ca.getThoiGianKetCa() != null ? Timestamp.valueOf(ca.getThoiGianKetCa()) : null);
            pstm.setDouble(5, ca.getTienDauCa());
            if (ca.getTienKetCa() != null) {
                pstm.setDouble(6, ca.getTienKetCa());
            } else {
                pstm.setNull(6, java.sql.Types.FLOAT);
            }
            pstm.setString(7, ca.getTrangThai() != null ? ca.getTrangThai().name() : null);
            pstm.setString(8, ca.getGhiChu());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatCaLamViec(CaLamViec ca) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update CaLamViec set maNV = ?, thoiGianVaoCa = ?, thoiGianKetCa = ?, tienDauCa = ?, tienKetCa = ?, trangThai = ?, ghiChu = ? where maCa = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, ca.getNhanVien().getMaNV());
            pstm.setTimestamp(2, Timestamp.valueOf(ca.getThoiGianVaoCa()));
            pstm.setTimestamp(3, ca.getThoiGianKetCa() != null ? Timestamp.valueOf(ca.getThoiGianKetCa()) : null);
            pstm.setDouble(4, ca.getTienDauCa());
            if (ca.getTienKetCa() != null) {
                pstm.setDouble(5, ca.getTienKetCa());
            } else {
                pstm.setNull(5, java.sql.Types.FLOAT);
            }
            pstm.setString(6, ca.getTrangThai() != null ? ca.getTrangThai().name() : null);
            pstm.setString(7, ca.getGhiChu());
            pstm.setString(8, ca.getMaCa());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public CaLamViec getCaLamViecHienTaiCuaNhanVien(String maNV) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select top 1 * from CaLamViec where maNV = ? and trangThai = N'DANG_LAM_VIEC' order by thoiGianVaoCa desc";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maNV);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildCaLamViecFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CaLamViec> getTatCaCaLamViec() {
        List<CaLamViec> ketQua = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from CaLamViec order by thoiGianVaoCa desc";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                CaLamViec ca = buildCaLamViecFromResultSet(rs);
                if (ca != null) {
                    ketQua.add(ca);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
}