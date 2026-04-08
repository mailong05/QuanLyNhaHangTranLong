package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    private Connection connection;

    public KhachHangDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    private KhachHang buildKhachHangFromResultSet(ResultSet rs) {
        try {
            String maKH = rs.getString("maKH");
            String hoTen = rs.getString("hoTen");
            String sdt = rs.getString("sdt");
            int diemTichLuy = rs.getInt("diemTichLuy");
            String loaiThanhVien = rs.getString("loaiThanhVien");

            return new KhachHang(maKH, hoTen, sdt, diemTichLuy, loaiThanhVien);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean themKhachHang(KhachHang kh) {
        String sql = "insert into KhachHang (maKH, hoTen, sdt, diemTichLuy, loaiThanhVien) values (?,?,?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, kh.getMaKH());
            pstm.setString(2, kh.getHoTen());
            pstm.setString(3, kh.getSdt());
            pstm.setInt(4, kh.getDiemTichLuy());
            pstm.setString(5, kh.getLoaiThanhVien());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public KhachHang getKhachHangTheoMa(String maKH) {
        String sql = "select * from KhachHang where maKH = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maKH);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildKhachHangFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public KhachHang getKhachHangTheoSDT(String sdt) {
        String sql = "select * from KhachHang where sdt = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, sdt);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildKhachHangFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<KhachHang> getAllKhachHang() {
        String sql = "select * from KhachHang";
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhachHang kh = buildKhachHangFromResultSet(rs);
                if (kh != null) {
                    dsKhachHang.add(kh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }

    public boolean capNhatKhachHang(KhachHang kh) {
        String sql = "update KhachHang set hoTen = ?, sdt = ?, diemTichLuy = ?, loaiThanhVien = ? where maKH = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, kh.getHoTen());
            pstm.setString(2, kh.getSdt());
            pstm.setInt(3, kh.getDiemTichLuy());
            pstm.setString(4, kh.getLoaiThanhVien());
            pstm.setString(5, kh.getMaKH());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaKhachHang(String maKH) {
        String sql = "delete from KhachHang where maKH = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maKH);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<KhachHang> timKhachHangTheoTen(String hoTen) {
        String sql = "select * from KhachHang where hoTen like ?";
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, "%" + hoTen + "%");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhachHang kh = buildKhachHangFromResultSet(rs);
                if (kh != null) {
                    dsKhachHang.add(kh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }

    public List<KhachHang> getKhachHangVIP() {
        String sql = "select * from KhachHang where loaiThanhVien = ?";
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, "VIP");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhachHang kh = buildKhachHangFromResultSet(rs);
                if (kh != null) {
                    dsKhachHang.add(kh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }
}
