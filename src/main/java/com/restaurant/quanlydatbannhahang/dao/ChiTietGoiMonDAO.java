package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.ChiTietGoiMon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChiTietGoiMonDAO {
    private MonAnDAO monAnDAO;

    public ChiTietGoiMonDAO() {
        this.monAnDAO = new MonAnDAO();
    }

    private ChiTietGoiMon buildChiTietGoiMonFromResultSet(ResultSet rs) {
        try {
            String maPhieuGoi = rs.getString("maPhieuGoi");
            String maMon = rs.getString("maMon");
            int soLuong = rs.getInt("soLuong");
            double donGiaLuuTru = rs.getDouble("donGiaLuuTru");
            double thanhTien = rs.getDouble("thanhTien");
            String ghiChu = rs.getString("ghiChu");

            ChiTietGoiMon chiTiet = new ChiTietGoiMon();
            chiTiet.setMaPhieuGoi(maPhieuGoi);
            chiTiet.setMonAn(monAnDAO.getMonAnTheoMa(maMon));
            chiTiet.setSoLuong(soLuong);
            chiTiet.setDonGiaLuuTru(donGiaLuuTru);
            chiTiet.setThanhTien(thanhTien);
            chiTiet.setGhiChu(ghiChu);

            return chiTiet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean themChiTietGoiMon(ChiTietGoiMon ct) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "insert into ChiTietGoiMon (maPhieuGoi, maMon, soLuong, donGiaLuuTru, thanhTien, ghiChu) values (?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, ct.getMaPhieuGoi());
            pstm.setString(2, ct.getMonAn().getMaMon());
            pstm.setInt(3, ct.getSoLuong());
            pstm.setDouble(4, ct.getDonGiaLuuTru());
            pstm.setDouble(5, ct.getThanhTien());
            pstm.setString(6, ct.getGhiChu());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ChiTietGoiMon getChiTietGoiMonTheoMa(String maPhieuGoi, String maMon) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from ChiTietGoiMon where maPhieuGoi = ? and maMon = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maPhieuGoi);
            pstm.setString(2, maMon);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildChiTietGoiMonFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ChiTietGoiMon> getChiTietGoiMonByPhieu(String maPhieuGoi) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from ChiTietGoiMon where maPhieuGoi = ?";
        ArrayList<ChiTietGoiMon> dsChiTiet = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maPhieuGoi);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                ChiTietGoiMon chiTiet = buildChiTietGoiMonFromResultSet(rs);
                if (chiTiet != null) {
                    dsChiTiet.add(chiTiet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsChiTiet;
    }

    public List<ChiTietGoiMon> getChiTietGoiMonByMon(String maMon) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from ChiTietGoiMon where maMon = ?";
        ArrayList<ChiTietGoiMon> dsChiTiet = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maMon);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                ChiTietGoiMon chiTiet = buildChiTietGoiMonFromResultSet(rs);
                if (chiTiet != null) {
                    dsChiTiet.add(chiTiet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsChiTiet;
    }

    public boolean capNhatChiTietGoiMon(ChiTietGoiMon ct) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update ChiTietGoiMon set soLuong = ?, donGiaLuuTru = ?, thanhTien = ?, ghiChu = ? where maPhieuGoi = ? and maMon = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, ct.getSoLuong());
            pstm.setDouble(2, ct.getDonGiaLuuTru());
            pstm.setDouble(3, ct.getThanhTien());
            pstm.setString(4, ct.getGhiChu());
            pstm.setString(5, ct.getMaPhieuGoi());
            pstm.setString(6, ct.getMonAn().getMaMon());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatTrangThaiChiTietGoiMon(String maPhieu, String maMon, String trangThai) {
        // This method might need a trangThai column if required; skipping for now
        return true;
    }

    public boolean capNhatSoLuong(String maPhieuGoi, String maMon, int soLuongMoi) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update ChiTietGoiMon set soLuong = ? where maPhieuGoi = ? and maMon = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, soLuongMoi);
            pstm.setString(2, maPhieuGoi);
            pstm.setString(3, maMon);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaChiTietGoiMon(String maPhieuGoi, String maMon) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "delete from ChiTietGoiMon where maPhieuGoi = ? and maMon = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maPhieuGoi);
            pstm.setString(2, maMon);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaAllChiTietByMaPhieu(String maPhieu) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "delete from ChiTietGoiMon where maPhieuGoi = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maPhieu);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public double tinhTongTienPhieu(String maPhieuGoi) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select sum(thanhTien) from ChiTietGoiMon where maPhieuGoi = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maPhieuGoi);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
