package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.entity.KhuVuc;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BanDAO {

    public BanDAO() {
    }

    /**
     * Lấy mã bàn cuối cùng trong database
     * Dùng để sinh mã bàn tiếp theo
     * 
     * @return Mã bàn cuối cùng (VD: B005) hoặc null nếu bảng rỗng
     */
    public String getLastBanID() {
        return IDQueryHelper.getLastID("Ban", "maBan");
    }

    private Ban buildBanFromResultSet(ResultSet rs) {
        try {
            String maBan = rs.getString("maBan");
            int soGhe = rs.getInt("soGhe");
            String viTri = rs.getString("viTri");
            String maKhuVuc = rs.getString("maKhuVuc");
            String trangThaiStr = rs.getString("trangThai");

            // Tạo KhuVuc
            KhuVuc khuVuc = new KhuVuc(maKhuVuc, "");

            // Tạo TrangThaiBan enum
            TrangThaiBan trangThai = TrangThaiBan.valueOf(trangThaiStr);

            return new Ban(maBan, soGhe, viTri, khuVuc, trangThai);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Ban buildBan(String maBan, int soGhe, String maKV, String trangThai) {
        KhuVuc khuVuc = new KhuVuc(maKV, "");
        TrangThaiBan status = TrangThaiBan.valueOf(trangThai);
        return new Ban(maBan, soGhe, "", khuVuc, status);
    }

    public boolean themBan(Ban ban) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "insert into BanAn (maBan, soGhe, viTri, maKhuVuc, trangThai) values (?,?,?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, ban.getMaBan());
            pstm.setInt(2, ban.getSoGhe());
            pstm.setString(3, ban.getViTri());
            pstm.setString(4, ban.getKhuVuc().getMaKhuVuc());
            pstm.setString(5, ban.getTrangThai().name());
            return pstm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Ban getBanTheoMa(String maBan) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from BanAn where maBan = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maBan);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildBanFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ban> getAllBan() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from BanAn";
        ArrayList<Ban> dsBan = new ArrayList<Ban>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Ban ban = buildBanFromResultSet(rs);
                if (ban != null) {
                    dsBan.add(ban);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsBan;
    }

    public List<Ban> getBanTheoKhuVuc(String maKhuVuc) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from BanAn where maKhuVuc = ?";
        ArrayList<Ban> dsBan = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maKhuVuc);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Ban ban = buildBanFromResultSet(rs);
                if (ban != null) {
                    dsBan.add(ban);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsBan;
    }

    public List<Ban> getBanTrong() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from BanAn where trangThai = ?";
        ArrayList<Ban> dsBan = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, TrangThaiBan.TRONG.name());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Ban ban = buildBanFromResultSet(rs);
                if (ban != null) {
                    dsBan.add(ban);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsBan;
    }

    public List<Ban> getBanDangSuDung() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from BanAn where trangThai = ?";
        ArrayList<Ban> dsBan = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, TrangThaiBan.DANG_DUNG.name());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Ban ban = buildBanFromResultSet(rs);
                if (ban != null) {
                    dsBan.add(ban);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsBan;
    }

    public boolean capNhatBan(Ban ban) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update BanAn set soGhe = ?, viTri = ?, maKhuVuc = ?, trangThai = ? where maBan = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, ban.getSoGhe());
            pstm.setString(2, ban.getViTri());
            pstm.setString(3, ban.getKhuVuc().getMaKhuVuc());
            pstm.setString(4, ban.getTrangThai().name());
            pstm.setString(5, ban.getMaBan());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaBan(String maBan) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "delete from BanAn where maBan = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maBan);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatTrangThaiBan(String maBan, String trangThai) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update BanAn set trangThai = ? where maBan = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, trangThai);
            pstm.setString(2, maBan);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
