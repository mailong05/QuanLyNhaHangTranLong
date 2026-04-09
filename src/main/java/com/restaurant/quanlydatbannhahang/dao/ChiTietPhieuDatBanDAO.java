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
    private Connection connection;

    public ChiTietPhieuDatBanDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Thêm chi tiết phiếu đặt bàn
     */
    public boolean themChiTietPhieuDatBan(ChiTietPhieuDatBan chiTiet) {
        String sql = "INSERT INTO ChiTietPhieuDatBan (maPhieu, maBan, GhiChu) " +
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

    /**
     * Lấy chi tiết phiếu đặt bàn theo mã phiếu
     */
    public List<ChiTietPhieuDatBan> getChiTietByMaPhieu(String maPhieu) {
        List<ChiTietPhieuDatBan> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhieuDatBan WHERE maPhieu = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maPhieu);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    // Tạo object từ ResultSet (cần liên kết với Ban và PhieuDatBan)
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

    /**
     * Cập nhật chi tiết phiếu đặt bàn
     */
    public boolean capNhatChiTietPhieuDatBan(ChiTietPhieuDatBan chiTiet) {
        String sql = "UPDATE ChiTietPhieuDatBan SET GhiChu = ? " +
                "WHERE maPhieu = ? AND maBan = ?";

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

    /**
     * Xóa chi tiết phiếu đặt bàn
     */
    public boolean xoaChiTietPhieuDatBan(String maPhieu, String maBan) {
        String sql = "DELETE FROM ChiTietPhieuDatBan WHERE maPhieu = ? AND maBan = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maPhieu);
            pstm.setString(2, maBan);

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa chi tiết phiếu đặt bàn: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Build ChiTietPhieuDatBan object từ ResultSet
     */
    private ChiTietPhieuDatBan buildChiTietFromResultSet(ResultSet rs) {
        try {
            // Cần load Ban và PhieuDatBan từ các bảng khác
            BanDAO banDAO = new BanDAO();
            PhieuDatBanDAO phieuDAO = new PhieuDatBanDAO();

            ChiTietPhieuDatBan chiTiet = new ChiTietPhieuDatBan();
            chiTiet.setBan(banDAO.getBanTheoMa(rs.getString("maBan")));
            chiTiet.setPhieuDatBan(phieuDAO.getPhieuDatBanTheoMa(rs.getString("maPhieu")));
            chiTiet.setGhiChu(rs.getString("GhiChu"));

            return chiTiet;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xây dựng object từ ResultSet: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Xóa tất cả chi tiết của một phiếu đặt bàn
     */
    public boolean xoaAllChiTietByMaPhieu(String maPhieu) {
        String sql = "DELETE FROM ChiTietPhieuDatBan WHERE maPhieu = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maPhieu);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa tất cả chi tiết phiếu: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
