package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.KhuVuc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KhuVucDAO {
    private Connection connection;

    public KhuVucDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    private KhuVuc buildKhuVucFromResultSet(ResultSet rs) {
        try {
            String maKhuVuc = rs.getString("maKhuVuc");
            String tenKhuVuc = rs.getString("tenKhuVuc");
            return new KhuVuc(maKhuVuc, tenKhuVuc);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean themKhuVuc(KhuVuc khuVuc) {
        String sql = "insert into KhuVuc (maKhuVuc, tenKhuVuc) values (?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, khuVuc.getMaKhuVuc());
            pstm.setString(2, khuVuc.getTenKhuVuc());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public KhuVuc getKhuVucTheoMa(String maKhuVuc) {
        String sql = "select * from KhuVuc where maKhuVuc = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maKhuVuc);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildKhuVucFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<KhuVuc> getAllKhuVuc() {
        String sql = "select * from KhuVuc";
        ArrayList<KhuVuc> dsKhuVuc = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhuVuc khuVuc = buildKhuVucFromResultSet(rs);
                if (khuVuc != null) {
                    dsKhuVuc.add(khuVuc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhuVuc;
    }

    public boolean capNhatKhuVuc(KhuVuc khuVuc) {
        String sql = "update KhuVuc set tenKhuVuc = ? where maKhuVuc = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, khuVuc.getTenKhuVuc());
            pstm.setString(2, khuVuc.getMaKhuVuc());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaKhuVuc(String maKhuVuc) {
        String sql = "delete from KhuVuc where maKhuVuc = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maKhuVuc);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public KhuVuc getKhuVucTheoTen(String tenKhuVuc) {
        String sql = "select * from KhuVuc where tenKhuVuc = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, tenKhuVuc);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildKhuVucFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
