package com.restaurant.quanlydatbannhahang.dao;
import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.ChiTietPhieuDatBan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ChiTietPhieuDatBanDAO {
    public ChiTietPhieuDatBanDAO() {
    }
    public boolean themChiTietPhieuDatBan(ChiTietPhieuDatBan chiTiet) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO ChiTietPhieuDatBan (maPhieuDat, maBan, GhiChu) " +
                "VALUES (?, ?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, chiTiet.getPhieuDatBan().getMaPhieuDat());
            pstm.setString(2, chiTiet.getBan().getMaBan());
            pstm.setString(3, chiTiet.getGhiChu());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm chi tiết phiếu đặt bàn: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    public List<ChiTietPhieuDatBan> getChiTietByMaPhieuDat(String maPhieuDat) {
        Connection connection = DatabaseConnection.getConnection();
        List<ChiTietPhieuDatBan> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhieuDatBan WHERE maPhieuDat = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maPhieuDat);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuDatBan chiTiet = buildChiTietFromResultSet(rs);
                    if (chiTiet != null) {
                        list.add(chiTiet);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy chi tiết phiếu đặt bàn: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
    public List<String> getMaBansByPhieu(String maPhieuDat) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT maBan FROM ChiTietPhieuDatBan WHERE maPhieuDat = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, maPhieuDat);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("maBan"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean capNhatChiTietPhieuDatBan(ChiTietPhieuDatBan chiTiet) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "UPDATE ChiTietPhieuDatBan SET GhiChu = ? " +
                "WHERE maPhieuDat = ? AND maBan = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, chiTiet.getGhiChu());
            pstm.setString(2, chiTiet.getPhieuDatBan().getMaPhieuDat());
            pstm.setString(3, chiTiet.getBan().getMaBan());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi cập nhật chi tiết phiếu đặt bàn: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    public boolean xoaChiTietPhieuDatBan(String maPhieuDat, String maBan) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "DELETE FROM ChiTietPhieuDatBan WHERE maPhieuDat = ? AND maBan = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maPhieuDat);
            pstm.setString(2, maBan);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa chi tiết phiếu đặt bàn: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    private ChiTietPhieuDatBan buildChiTietFromResultSet(ResultSet rs) {
        try {
            BanDAO banDAO = new BanDAO();
            PhieuDatBanDAO phieuDAO = new PhieuDatBanDAO();
            ChiTietPhieuDatBan chiTiet = new ChiTietPhieuDatBan();
            chiTiet.setBan(banDAO.getBanTheoMa(rs.getString("maBan")));
            chiTiet.setPhieuDatBan(phieuDAO.getPhieuDatBanTheoMa(rs.getString("maPhieuDat")));
            chiTiet.setGhiChu(rs.getString("GhiChu"));
            return chiTiet;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xây dựng object từ ResultSet: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public boolean xoaAllChiTietByMaPhieuDat(String maPhieuDat) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "DELETE FROM ChiTietPhieuDatBan WHERE maPhieuDat = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maPhieuDat);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa tất cả chi tiết phiếu: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    public List<ChiTietPhieuDatBan> getAllChiTiet() {
        Connection connection = DatabaseConnection.getConnection();
        List<ChiTietPhieuDatBan> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhieuDatBan";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuDatBan chiTiet = buildChiTietFromResultSet(rs);
                    if (chiTiet != null) {
                        list.add(chiTiet);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách chi tiết phiếu đặt bàn: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}