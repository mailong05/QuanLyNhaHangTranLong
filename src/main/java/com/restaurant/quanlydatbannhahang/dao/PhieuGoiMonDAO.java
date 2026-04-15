package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.PhieuGoiMon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PhieuGoiMonDAO {
    private BanDAO banDAO;
    private NhanVienDAO nhanVienDAO;

    public PhieuGoiMonDAO() {
        this.banDAO = new BanDAO();
        this.nhanVienDAO = new NhanVienDAO();
    }

    private PhieuGoiMon buildPhieuGoiMonFromResultSet(ResultSet rs) {
        try {
            String maPhieuGoi = rs.getString("maPhieuGoi");
            String maBan = rs.getString("maBan");
            String maNV = rs.getString("maNV");
            LocalTime gioVao = rs.getTime("gioVao") != null ? rs.getTime("gioVao").toLocalTime() : null;
            LocalTime gioRa = rs.getTime("gioRa") != null ? rs.getTime("gioRa").toLocalTime() : null;
            double tongTienTamTinh = rs.getDouble("tongTienTamTinh");

            PhieuGoiMon phieu = new PhieuGoiMon();
            phieu.setMaPhieuGoi(maPhieuGoi);
            phieu.setBan(banDAO.getBanTheoMa(maBan));
            phieu.setNhanVien(nhanVienDAO.getNhanVienTheoMa(maNV));
            phieu.setGioVao(gioVao);
            phieu.setGioRa(gioRa);
            phieu.setTongTienTamTinh(tongTienTamTinh);

            return phieu;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean themPhieuGoiMon(PhieuGoiMon phieu) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "insert into PhieuGoiMon (maPhieuGoi, maBan, maNV, gioVao, gioRa, tongTienTamTinh) values (?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, phieu.getMaPhieuGoi());
            pstm.setString(2, phieu.getBan().getMaBan());
            pstm.setString(3, phieu.getNhanVien().getMaNV());
            pstm.setTime(4, phieu.getGioVao() != null ? java.sql.Time.valueOf(phieu.getGioVao()) : null);
            pstm.setTime(5, phieu.getGioRa() != null ? java.sql.Time.valueOf(phieu.getGioRa()) : null);
            pstm.setDouble(6, phieu.getTongTienTamTinh());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public PhieuGoiMon getPhieuGoiMonTheoMa(String maPhieuGoi) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuGoiMon where maPhieuGoi = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maPhieuGoi);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildPhieuGoiMonFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PhieuGoiMon> getAllPhieuGoiMon() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuGoiMon";
        ArrayList<PhieuGoiMon> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuGoiMon phieu = buildPhieuGoiMonFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    public List<PhieuGoiMon> getPhieuGoiMonTheoBan(String maBan) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuGoiMon where maBan = ?";
        ArrayList<PhieuGoiMon> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maBan);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuGoiMon phieu = buildPhieuGoiMonFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    public List<PhieuGoiMon> getPhieuGoiMonTheoNhanVien(String maNV) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuGoiMon where maNV = ?";
        ArrayList<PhieuGoiMon> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maNV);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuGoiMon phieu = buildPhieuGoiMonFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    public List<PhieuGoiMon> getPhieuGoiMonTheoNgay(LocalDate ngay) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuGoiMon where convert(date, gioVao) = ?";
        ArrayList<PhieuGoiMon> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setDate(1, java.sql.Date.valueOf(ngay));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuGoiMon phieu = buildPhieuGoiMonFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    public List<PhieuGoiMon> getPhieuGoiMonDangMo() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuGoiMon where gioRa is null";
        ArrayList<PhieuGoiMon> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuGoiMon phieu = buildPhieuGoiMonFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    public boolean capNhatPhieuGoiMon(PhieuGoiMon phieu) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update PhieuGoiMon set maBan = ?, maNV = ?, gioVao = ?, gioRa = ?, tongTienTamTinh = ? where maPhieuGoi = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, phieu.getBan().getMaBan());
            pstm.setString(2, phieu.getNhanVien().getMaNV());
            pstm.setTime(3, phieu.getGioVao() != null ? java.sql.Time.valueOf(phieu.getGioVao()) : null);
            pstm.setTime(4, phieu.getGioRa() != null ? java.sql.Time.valueOf(phieu.getGioRa()) : null);
            pstm.setDouble(5, phieu.getTongTienTamTinh());
            pstm.setString(6, phieu.getMaPhieuGoi());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatTongTienTamTinh(String maPhieuGoi, double tongTien) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update PhieuGoiMon set tongTienTamTinh = ? where maPhieuGoi = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setDouble(1, tongTien);
            pstm.setString(2, maPhieuGoi);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaPhieuGoiMon(String maPhieuGoi) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "delete from PhieuGoiMon where maPhieuGoi = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maPhieuGoi);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean ketThucPhieu(String maPhieuGoi) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update PhieuGoiMon set gioRa = ? where maPhieuGoi = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setTime(1, java.sql.Time.valueOf(LocalTime.now()));
            pstm.setString(2, maPhieuGoi);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
