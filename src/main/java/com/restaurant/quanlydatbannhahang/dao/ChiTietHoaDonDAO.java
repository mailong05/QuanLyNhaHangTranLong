package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.ChiTietHoaDon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDAO {
    private Connection connection;

    public ChiTietHoaDonDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Thêm chi tiết hóa đơn
     */
    public boolean themChiTietHoaDon(ChiTietHoaDon chiTiet) {
        String sql = "INSERT INTO ChiTietHoaDon (maHD, maMon, soLuong, donGiaLuuTru, ghiChu) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, chiTiet.getHoaDon().getMaHD());
            pstm.setString(2, chiTiet.getMonAn().getMaMon());
            pstm.setInt(3, chiTiet.getSoLuong());
            pstm.setDouble(4, chiTiet.getDonGiaLuuTru());
            pstm.setString(5, chiTiet.getGhiChu());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Lấy chi tiết hóa đơn theo mã hóa đơn
     */
    public List<ChiTietHoaDon> getChiTietByMaHD(String maHD) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietHoaDon WHERE maHD = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maHD);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    ChiTietHoaDon chiTiet = buildChiTietFromResultSet(rs);
                    if (chiTiet != null) {
                        list.add(chiTiet);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy chi tiết hóa đơn theo mã hóa đơn và mã món
     */
    public ChiTietHoaDon getChiTietByMaHDAndMaMon(String maHD, String maMon) {
        String sql = "SELECT * FROM ChiTietHoaDon WHERE maHD = ? AND maMon = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maHD);
            pstm.setString(2, maMon);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return buildChiTietFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Cập nhật chi tiết hóa đơn
     */
    public boolean capNhatChiTietHoaDon(ChiTietHoaDon chiTiet) {
        String sql = "UPDATE ChiTietHoaDon SET soLuong = ?, donGiaLuuTru = ?, ghiChu = ? " +
                "WHERE maHD = ? AND maMon = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, chiTiet.getSoLuong());
            pstm.setDouble(2, chiTiet.getDonGiaLuuTru());
            pstm.setString(3, chiTiet.getGhiChu());
            pstm.setString(4, chiTiet.getHoaDon().getMaHD());
            pstm.setString(5, chiTiet.getMonAn().getMaMon());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi cập nhật chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Xóa chi tiết hóa đơn
     */
    public boolean xoaChiTietHoaDon(String maHD, String maMon) {
        String sql = "DELETE FROM ChiTietHoaDon WHERE maHD = ? AND maMon = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maHD);
            pstm.setString(2, maMon);

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Xóa tất cả chi tiết của một hóa đơn
     */
    public boolean xoaAllChiTietByMaHD(String maHD) {
        String sql = "DELETE FROM ChiTietHoaDon WHERE maHD = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maHD);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa tất cả chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Tính tổng tiền của hóa đơn
     */
    public double getTongTienHoaDon(String maHD) {
        String sql = "SELECT SUM(thanhTien) as tongTien FROM ChiTietHoaDon WHERE maHD = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maHD);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("tongTien");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi tính tổng tiền: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Kiểm tra hóa đơn có chi tiết không
     */
    public int countChiTietByMaHD(String maHD) {
        String sql = "SELECT COUNT(*) as soChiTiet FROM ChiTietHoaDon WHERE maHD = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maHD);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("soChiTiet");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi đếm chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Build ChiTietHoaDon object từ ResultSet
     */
    private ChiTietHoaDon buildChiTietFromResultSet(ResultSet rs) {
        try {
            MonAnDAO monDAO = new MonAnDAO();
            HoaDonDAO hdDAO = new HoaDonDAO();

            ChiTietHoaDon chiTiet = new ChiTietHoaDon();
            chiTiet.setHoaDon(hdDAO.getHoaDonTheoMa(rs.getString("maHD")));
            chiTiet.setMonAn(monDAO.getMonAnTheoMa(rs.getString("maMon")));
            chiTiet.setSoLuong(rs.getInt("soLuong"));
            chiTiet.setDonGiaLuuTru(rs.getDouble("donGiaLuuTru"));
            chiTiet.setGhiChu(rs.getString("GhiChu"));
            return chiTiet;
        } catch (Exception e) {
            System.out.println("[ERROR] Loi khi build ChiTietHoaDon tu ResultSet: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}