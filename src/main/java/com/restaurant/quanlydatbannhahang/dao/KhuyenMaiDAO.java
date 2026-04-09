package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.KhuyenMai;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiKhuyenMai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiDAO {
    private Connection connection;

    public KhuyenMaiDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    private KhuyenMai buildKhuyenMaiFromResultSet(ResultSet rs) {
        try {
            String maKM = rs.getString("maKM");
            String tenKM = rs.getString("tenKM");
            double giaTriGiam = rs.getDouble("giaTriGiam");
            LocalDate ngayBatDau = rs.getDate("ngayBatDau").toLocalDate();
            LocalDate ngayKetThuc = rs.getDate("ngayKetThuc").toLocalDate();
            double dieuKienToiThieu = rs.getDouble("dieuKienToiThieu");
            String trangThaiStr = rs.getString("trangThai");

            TrangThaiKhuyenMai trangThai = TrangThaiKhuyenMai.valueOf(trangThaiStr);

            return new KhuyenMai(maKM, tenKM, giaTriGiam, ngayBatDau, ngayKetThuc, dieuKienToiThieu, trangThai);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean themKhuyenMai(KhuyenMai km) {
        String sql = "insert into KhuyenMai (maKM, tenKM, giaTriGiam, ngayBatDau, ngayKetThuc, dieuKienToiThieu, trangThai) values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, km.getMaKM());
            pstm.setString(2, km.getTenKM());
            pstm.setDouble(3, km.getGiaTriGiam());
            pstm.setDate(4, java.sql.Date.valueOf(km.getNgayBatDau()));
            pstm.setDate(5, java.sql.Date.valueOf(km.getNgayKetThuc()));
            pstm.setDouble(6, km.getDieuKienToiThieu());
            pstm.setString(7, km.getTrangThai().name());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public KhuyenMai getKhuyenMaiTheoMa(String maKM) {
        String sql = "select * from KhuyenMai where maKM = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maKM);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildKhuyenMaiFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<KhuyenMai> getAllKhuyenMai() {
        String sql = "select * from KhuyenMai";
        ArrayList<KhuyenMai> dsKhuyenMai = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhuyenMai km = buildKhuyenMaiFromResultSet(rs);
                if (km != null) {
                    dsKhuyenMai.add(km);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhuyenMai;
    }

    public List<KhuyenMai> getKhuyenMaiConHieuLuc() {
        String sql = "select * from KhuyenMai where trangThai = ? and ngayBatDau <= getdate() and ngayKetThuc >= getdate()";
        ArrayList<KhuyenMai> dsKhuyenMai = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, TrangThaiKhuyenMai.CON_AP_DUNG.name());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhuyenMai km = buildKhuyenMaiFromResultSet(rs);
                if (km != null) {
                    dsKhuyenMai.add(km);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhuyenMai;
    }

    public List<KhuyenMai> getKhuyenMaiConHieuLuc(LocalDate ngay) {
        String sql = "select * from KhuyenMai where trangThai = ? and ngayBatDau <= ? and ngayKetThuc >= ?";
        ArrayList<KhuyenMai> dsKhuyenMai = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, TrangThaiKhuyenMai.CON_AP_DUNG.name());
            pstm.setDate(2, java.sql.Date.valueOf(ngay));
            pstm.setDate(3, java.sql.Date.valueOf(ngay));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhuyenMai km = buildKhuyenMaiFromResultSet(rs);
                if (km != null) {
                    dsKhuyenMai.add(km);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhuyenMai;
    }

    public boolean capNhatKhuyenMai(KhuyenMai km) {
        String sql = "update KhuyenMai set tenKM = ?, giaTriGiam = ?, ngayBatDau = ?, ngayKetThuc = ?, dieuKienToiThieu = ?, trangThai = ? where maKM = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, km.getTenKM());
            pstm.setDouble(2, km.getGiaTriGiam());
            pstm.setDate(3, java.sql.Date.valueOf(km.getNgayBatDau()));
            pstm.setDate(4, java.sql.Date.valueOf(km.getNgayKetThuc()));
            pstm.setDouble(5, km.getDieuKienToiThieu());
            pstm.setString(6, km.getTrangThai().name());
            pstm.setString(7, km.getMaKM());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaKhuyenMai(String maKM) {
        String sql = "delete from KhuyenMai where maKM = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maKM);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<KhuyenMai> timKhuyenMaiTheoTen(String tenKM) {
        String sql = "select * from KhuyenMai where tenKM like ?";
        ArrayList<KhuyenMai> dsKhuyenMai = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, "%" + tenKM + "%");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhuyenMai km = buildKhuyenMaiFromResultSet(rs);
                if (km != null) {
                    dsKhuyenMai.add(km);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhuyenMai;
    }

    public List<KhuyenMai> getKhuyenMaiTheoTrangThai(TrangThaiKhuyenMai trangThai) {
        String sql = "select * from KhuyenMai where trangThai = ?";
        ArrayList<KhuyenMai> dsKhuyenMai = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, trangThai.name());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhuyenMai km = buildKhuyenMaiFromResultSet(rs);
                if (km != null) {
                    dsKhuyenMai.add(km);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhuyenMai;
    }

    public List<KhuyenMai> getKhuyenMaiTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        String sql = "select * from KhuyenMai where (ngayBatDau between ? and ?) or (ngayKetThuc between ? and ?)";
        ArrayList<KhuyenMai> dsKhuyenMai = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setDate(1, java.sql.Date.valueOf(ngayBatDau));
            pstm.setDate(2, java.sql.Date.valueOf(ngayKetThuc));
            pstm.setDate(3, java.sql.Date.valueOf(ngayBatDau));
            pstm.setDate(4, java.sql.Date.valueOf(ngayKetThuc));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhuyenMai km = buildKhuyenMaiFromResultSet(rs);
                if (km != null) {
                    dsKhuyenMai.add(km);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhuyenMai;
    }
}
