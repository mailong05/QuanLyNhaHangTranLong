package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.Thue;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiThue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ThueDAO {

    public ThueDAO() {
    }

    private Thue buildThueFromResultSet(ResultSet rs) {
        try {
            String maThue = rs.getString("maThue");
            String tenThue = rs.getString("tenThue");
            double thueSuat = rs.getDouble("thueSuat");
            String trangThaiStr = rs.getString("trangThai");

            TrangThaiThue trangThai = TrangThaiThue.valueOf(trangThaiStr);

            return new Thue(maThue, tenThue, thueSuat, trangThai);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean themThue(Thue thue) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "insert into Thue (maThue, tenThue, thueSuat, trangThai) values (?,?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, thue.getMaThue());
            pstm.setString(2, thue.getTenThue());
            pstm.setDouble(3, thue.getThueSuat());
            pstm.setString(4, thue.getTrangThai().name());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Thue getThueTheoMa(String maThue) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from Thue where maThue = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maThue);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildThueFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Thue> getAllThue() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from Thue";
        ArrayList<Thue> dsThue = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Thue thue = buildThueFromResultSet(rs);
                if (thue != null) {
                    dsThue.add(thue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsThue;
    }

    public boolean capNhatThue(Thue thue) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update Thue set tenThue = ?, thueSuat = ?, trangThai = ? where maThue = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, thue.getTenThue());
            pstm.setDouble(2, thue.getThueSuat());
            pstm.setString(3, thue.getTrangThai().name());
            pstm.setString(4, thue.getMaThue());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatThueSuat(String maThue, double thueSuatMoi) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update Thue set thueSuat = ? where maThue = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setDouble(1, thueSuatMoi);
            pstm.setString(2, maThue);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaThue(String maThue) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "delete from Thue where maThue = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maThue);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Thue getThueTheoTen(String tenThue) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from Thue where tenThue = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, tenThue);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildThueFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Thue> getThueTheoTrangThai(TrangThaiThue trangThai) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from Thue where trangThai = ?";
        ArrayList<Thue> dsThue = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, trangThai.name());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Thue thue = buildThueFromResultSet(rs);
                if (thue != null) {
                    dsThue.add(thue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsThue;
    }
}
