package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.MonAn;
import com.restaurant.quanlydatbannhahang.entity.LoaiMonAn;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiMonAn;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MonAnDAO {

    public MonAnDAO() {
    }

    /**
     * Lấy mã món ăn cuối cùng
     * 
     * @return Mã món ăn cuối cùng (VD: MA080) hoặc null
     */
    public String getLastMonAnID() {
        return IDQueryHelper.getLastID("MonAn", "maMon");
    }

    private MonAn buildMonAnFromResultSet(ResultSet rs) {
        try {
            String maMon = rs.getString("maMon");
            String tenMon = rs.getString("tenMon");
            double donGia = rs.getDouble("donGia");
            String donViTinh = rs.getString("donViTinh");
            String tenLoaiStr = rs.getString("tenLoai");
            String trangThaiStr = rs.getString("trangThai");
            String urlHinhAnh = rs.getString("urlHinhAnh");

            LoaiMonAn tenLoai = LoaiMonAn.valueOf(tenLoaiStr);
            TrangThaiMonAn trangThai = TrangThaiMonAn.valueOf(trangThaiStr);

            return new MonAn(maMon, tenMon, donGia, donViTinh, tenLoai, trangThai, urlHinhAnh);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean themMonAn(MonAn monAn) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "insert into MonAn (maMon, tenMon, donGia, donViTinh, tenLoai, trangThai, urlHinhAnh) values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, monAn.getMaMon());
            pstm.setString(2, monAn.getTenMon());
            pstm.setDouble(3, monAn.getDonGia());
            pstm.setString(4, monAn.getDonViTinh());
            pstm.setString(5, monAn.getTenLoai().name());
            pstm.setString(6, monAn.getTrangThai().name());
            pstm.setString(7, monAn.getUrlHinhAnh());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public MonAn getMonAnTheoMa(String maMon) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from MonAn where maMon = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maMon);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildMonAnFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MonAn> getAllMonAn() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from MonAn";
        ArrayList<MonAn> dsMonAn = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                MonAn monAn = buildMonAnFromResultSet(rs);
                if (monAn != null) {
                    dsMonAn.add(monAn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsMonAn;
    }

    public List<MonAn> getMonAnTheoLoai(String maLoai) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from MonAn where tenLoai = ?";
        ArrayList<MonAn> dsMonAn = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maLoai);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                MonAn monAn = buildMonAnFromResultSet(rs);
                if (monAn != null) {
                    dsMonAn.add(monAn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsMonAn;
    }

    public List<MonAn> getMonAnConHang() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from MonAn where trangThai = ?";
        ArrayList<MonAn> dsMonAn = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, TrangThaiMonAn.CON.name());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                MonAn monAn = buildMonAnFromResultSet(rs);
                if (monAn != null) {
                    dsMonAn.add(monAn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsMonAn;
    }

    public boolean capNhatMonAn(MonAn monAn) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update MonAn set tenMon = ?, donGia = ?, donViTinh = ?, tenLoai = ?, trangThai = ?, urlHinhAnh = ? where maMon = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, monAn.getTenMon());
            pstm.setDouble(2, monAn.getDonGia());
            pstm.setString(3, monAn.getDonViTinh());
            pstm.setString(4, monAn.getTenLoai().name());
            pstm.setString(5, monAn.getTrangThai().name());
            pstm.setString(6, monAn.getUrlHinhAnh());
            pstm.setString(7, monAn.getMaMon());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatGiaMonAn(String maMon, double giaMoi) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update MonAn set donGia = ? where maMon = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setDouble(1, giaMoi);
            pstm.setString(2, maMon);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatTrangThaiMonAn(String maMon, String trangThai) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update MonAn set trangThai = ? where maMon = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, trangThai);
            pstm.setString(2, maMon);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaMonAn(String maMon) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "delete from MonAn where maMon = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maMon);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<MonAn> timMonAnTheoTen(String tenMon) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from MonAn where tenMon like ?";
        ArrayList<MonAn> dsMonAn = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, "%" + tenMon + "%");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                MonAn monAn = buildMonAnFromResultSet(rs);
                if (monAn != null) {
                    dsMonAn.add(monAn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsMonAn;
    }
}
