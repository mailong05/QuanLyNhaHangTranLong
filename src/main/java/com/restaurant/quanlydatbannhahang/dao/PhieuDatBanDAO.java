package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.PhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiPhieuDat;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PhieuDatBanDAO {
    private KhachHangDAO khachHangDAO;
    private NhanVienDAO nhanVienDAO;

    public PhieuDatBanDAO() {
        this.khachHangDAO = new KhachHangDAO();
        this.nhanVienDAO = new NhanVienDAO();
    }

    /**
     * Lấy mã phiếu đặt bàn cuối cùng
     * 
     * @return Mã phiếu đặt bàn cuối cùng (VD: PDB010) hoặc null
     */
    public String getLastPhieuDatBanID() {
        return IDQueryHelper.getLastID("PhieuDatBan", "maPhieuDat");
    }

    private PhieuDatBan buildPhieuDatBanFromResultSet(ResultSet rs) {
        try {
            String maPhieuDat = rs.getString("maPhieuDat");
            String maKH = rs.getString("maKH");
            String maNV = rs.getString("maNV");
            LocalDateTime thoiGianDen = rs.getTimestamp("thoiGianDen").toLocalDateTime();
            int soLuongNguoi = rs.getInt("soLuongNguoi");
            String ghiChu = rs.getString("ghiChu");
            String trangThaiStr = rs.getString("trangThai");

            PhieuDatBan phieu = new PhieuDatBan();
            phieu.setMaPhieuDat(maPhieuDat);
            phieu.setKhachHang(khachHangDAO.getKhachHangTheoMa(maKH));
            phieu.setNhanVien(nhanVienDAO.getNhanVienTheoMa(maNV));
            phieu.setThoiGianDen(thoiGianDen);
            phieu.setSoLuongNguoi(soLuongNguoi);
            phieu.setGhiChu(ghiChu);
            phieu.setTrangThai(TrangThaiPhieuDat.valueOf(trangThaiStr));

            return phieu;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean themPhieuDatBan(PhieuDatBan phieu) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "insert into PhieuDatBan (maPhieuDat, maKH, maNV, thoiGianDen, soLuongNguoi, ghiChu, trangThai) values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, phieu.getMaPhieuDat());
            pstm.setString(2, phieu.getKhachHang().getMaKH());
            pstm.setString(3, phieu.getNhanVien().getMaNV());
            pstm.setTimestamp(4, java.sql.Timestamp.valueOf(phieu.getThoiGianDen()));
            pstm.setInt(5, phieu.getSoLuongNguoi());
            pstm.setString(6, phieu.getGhiChu());
            pstm.setString(7, phieu.getTrangThai().name());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public PhieuDatBan getPhieuDatBanTheoMa(String maPhieuDat) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuDatBan where maPhieuDat = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maPhieuDat);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildPhieuDatBanFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PhieuDatBan> getAllPhieuDatBan() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuDatBan";
        ArrayList<PhieuDatBan> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuDatBan phieu = buildPhieuDatBanFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    public List<PhieuDatBan> getPhieuDatBanTheoNgay(LocalDate ngay) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuDatBan where convert(date, thoiGianDen) = ?";
        ArrayList<PhieuDatBan> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setDate(1, java.sql.Date.valueOf(ngay));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuDatBan phieu = buildPhieuDatBanFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    public List<PhieuDatBan> getPhieuDatBanTheoKhachHang(String maKH) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuDatBan where maKH = ?";
        ArrayList<PhieuDatBan> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maKH);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuDatBan phieu = buildPhieuDatBanFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    public List<PhieuDatBan> getPhieuDatBanTheoBan(String maBan) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select pdb.* from PhieuDatBan pdb " +
                "inner join ChiTietPhieuDatBan ctpdb on pdb.maPhieuDat = ctpdb.maPhieuDat " +
                "where ctpdb.maBan = ?";
        ArrayList<PhieuDatBan> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maBan);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuDatBan phieu = buildPhieuDatBanFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    public List<PhieuDatBan> getPhieuDatBanTheoBan(String maBan, LocalDate ngay) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select pdb.* from PhieuDatBan pdb " +
                "inner join ChiTietPhieuDatBan ctpdb on pdb.maPhieuDat = ctpdb.maPhieuDat " +
                "where ctpdb.maBan = ? and convert(date, pdb.thoiGianDen) = ?";
        ArrayList<PhieuDatBan> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maBan);
            pstm.setDate(2, java.sql.Date.valueOf(ngay));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuDatBan phieu = buildPhieuDatBanFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    public boolean capNhatPhieuDatBan(PhieuDatBan phieu) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update PhieuDatBan set maKH = ?, maNV = ?, thoiGianDen = ?, soLuongNguoi = ?, ghiChu = ?, trangThai = ? where maPhieuDat = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, phieu.getKhachHang().getMaKH());
            pstm.setString(2, phieu.getNhanVien().getMaNV());
            pstm.setTimestamp(3, java.sql.Timestamp.valueOf(phieu.getThoiGianDen()));
            pstm.setInt(4, phieu.getSoLuongNguoi());
            pstm.setString(5, phieu.getGhiChu());
            pstm.setString(6, phieu.getTrangThai().name());
            pstm.setString(7, phieu.getMaPhieuDat());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatTrangThaiPhieu(String maPhieuDat, String trangThai) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update PhieuDatBan set trangThai = ? where maPhieuDat = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, trangThai);
            pstm.setString(2, maPhieuDat);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaPhieuDatBan(String maPhieuDat) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "delete from PhieuDatBan where maPhieuDat = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maPhieuDat);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    

    public List<PhieuDatBan> getPhieuDatBanTheoTrangThai(TrangThaiPhieuDat trangThai) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuDatBan where trangThai = ?";
        ArrayList<PhieuDatBan> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, trangThai.name());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuDatBan phieu = buildPhieuDatBanFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }
}
