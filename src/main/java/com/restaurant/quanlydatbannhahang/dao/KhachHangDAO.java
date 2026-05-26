package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.KhachHang;
import com.restaurant.quanlydatbannhahang.entity.LoaiThanhVien;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    public KhachHangDAO() {
    }

    public String getLastKhachHangID() {
        return IDQueryHelper.getLastID("KhachHang", "maKH");
    }

    private KhachHang buildKhachHangFromResultSet(ResultSet rs) {
        try {
            String maKH = rs.getString("maKH");
            String hoTen = rs.getString("hoTen");
            String sdt = rs.getString("sdt");
            int diemTichLuy = rs.getInt("diemTichLuy");
            String loaiThanhVien = rs.getString("loaiThanhVien");
            return new KhachHang(maKH, hoTen, sdt, diemTichLuy, LoaiThanhVien.valueOf(loaiThanhVien));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean themKhachHang(KhachHang kh) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "insert into KhachHang (maKH, hoTen, sdt, diemTichLuy, loaiThanhVien) values (?,?,?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, kh.getMaKH());
            pstm.setString(2, kh.getHoTen());
            pstm.setString(3, kh.getSdt());
            pstm.setInt(4, kh.getDiemTichLuy());
            pstm.setString(5, kh.getLoaiThanhVien().name());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public KhachHang getKhachHangTheoMa(String maKH) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from KhachHang where maKH = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maKH);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildKhachHangFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public KhachHang getKhachHangTheoSDT(String sdt) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from KhachHang where sdt = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, sdt);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildKhachHangFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<KhachHang> getAllKhachHang() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from KhachHang";
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhachHang kh = buildKhachHangFromResultSet(rs);
                if (kh != null) {
                    dsKhachHang.add(kh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }

    public boolean capNhatKhachHang(KhachHang kh) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update KhachHang set hoTen = ?, sdt = ?, diemTichLuy = ?, loaiThanhVien = ? where maKH = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, kh.getHoTen());
            pstm.setString(2, kh.getSdt());
            pstm.setInt(3, kh.getDiemTichLuy());
            pstm.setString(4, kh.getLoaiThanhVien().name());
            pstm.setString(5, kh.getMaKH());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaKhachHang(String maKH) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "delete from KhachHang where maKH = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maKH);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<KhachHang> timKhachHangTheoTen(String hoTen) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from KhachHang where hoTen like ?";
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, "%" + hoTen + "%");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhachHang kh = buildKhachHangFromResultSet(rs);
                if (kh != null) {
                    dsKhachHang.add(kh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }

    public List<KhachHang> getKhachHangVIP() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from KhachHang where loaiThanhVien = ?";
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, "VIP");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhachHang kh = buildKhachHangFromResultSet(rs);
                if (kh != null) {
                    dsKhachHang.add(kh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }

    public List<Object[]> getTopKhachHangThongKe(LocalDate startDate) {
        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Object[]> result = new ArrayList<>();
        String sql;
        
        if (startDate == null) {
            sql = "SELECT TOP 10 kh.maKH, kh.hoTen, kh.sdt, kh.diemTichLuy, kh.loaiThanhVien, " +
                  "COUNT(hd.maHD) as soHoaDon, SUM(hd.tongThanhToan) as tongChi " +
                  "FROM KhachHang kh " +
                  "JOIN PhieuDatBan pdb ON kh.maKH = pdb.maKH " +
                  "JOIN HoaDon hd ON pdb.maPhieuDat = hd.maPhieuDat AND hd.trangThaiThanhToan = 'DA_THANH_TOAN' " +
                  "GROUP BY kh.maKH, kh.hoTen, kh.sdt, kh.diemTichLuy, kh.loaiThanhVien " +
                  "ORDER BY tongChi DESC, soHoaDon DESC";
        } else {
            sql = "SELECT TOP 10 kh.maKH, kh.hoTen, kh.sdt, kh.diemTichLuy, kh.loaiThanhVien, " +
                  "COUNT(hd.maHD) as soHoaDon, SUM(hd.tongThanhToan) as tongChi " +
                  "FROM KhachHang kh " +
                  "JOIN PhieuDatBan pdb ON kh.maKH = pdb.maKH " +
                  "JOIN HoaDon hd ON pdb.maPhieuDat = hd.maPhieuDat " +
                  "  AND hd.trangThaiThanhToan = 'DA_THANH_TOAN' " +
                  "  AND CAST(hd.ngayTao AS DATE) >= ? " +
                  "GROUP BY kh.maKH, kh.hoTen, kh.sdt, kh.diemTichLuy, kh.loaiThanhVien " +
                  "ORDER BY tongChi DESC, soHoaDon DESC";
        }

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            if (startDate != null) {
                pstm.setDate(1, java.sql.Date.valueOf(startDate));
            }
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhachHang kh = buildKhachHangFromResultSet(rs);
                Integer soHoaDon = rs.getInt("soHoaDon");
                Double tongChi = rs.getDouble("tongChi");

                if (rs.wasNull()) {
                    tongChi = 0.0;
                }

                result.add(new Object[] { kh, soHoaDon, tongChi });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public boolean kiemTraKhachHangCoLichSuDat(String maKH) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT COUNT(*) FROM PhieuDatBan WHERE maKH = ?";
        
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maKH);
            
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}