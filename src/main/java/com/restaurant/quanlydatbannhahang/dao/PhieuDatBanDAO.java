package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.entity.ChiTietPhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.KhachHang;
import com.restaurant.quanlydatbannhahang.entity.PhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiPhieuDat;
import com.restaurant.quanlydatbannhahang.service.BanService;
import com.restaurant.quanlydatbannhahang.service.ChiTietPhieuDatBanService;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PhieuDatBanDAO {
    private KhachHangDAO khachHangDAO;
    private NhanVienDAO nhanVienDAO;

    public PhieuDatBanDAO() {
        this.khachHangDAO = new KhachHangDAO();
        this.nhanVienDAO = new NhanVienDAO();
    }

    public String getLastPhieuDatBanID() {
        return IDQueryHelper.getLastID("PhieuDatBan", "maPhieuDat");
    }

    private PhieuDatBan buildPhieuDatBanFromResultSet(ResultSet rs) {
        try {
            String maPhieuDat = rs.getString("maPhieuDat");
            String maKH = rs.getString("maKH");
            String maNV = rs.getString("maNV");
            LocalDateTime ngayLapPhieu = rs.getTimestamp("ngayLapPhieu").toLocalDateTime();
            LocalDateTime thoiGianDen = rs.getTimestamp("thoiGianDen").toLocalDateTime();
            int soLuongNguoi = rs.getInt("soLuongNguoi");
            String ghiChu = rs.getString("ghiChu");
            String trangThaiStr = rs.getString("trangThai");
            double tienDatCoc = rs.getDouble("tienDatCoc");
            PhieuDatBan phieu = new PhieuDatBan();
            phieu.setMaPhieuDat(maPhieuDat);
            if (maKH != null && !maKH.isBlank()) {
                phieu.setKhachHang(khachHangDAO.getKhachHangTheoMa(maKH));
            }
            phieu.setNhanVien(nhanVienDAO.getNhanVienTheoMa(maNV));
            phieu.setNgayLapPhieu(ngayLapPhieu);
            phieu.setThoiGianDen(thoiGianDen);
            phieu.setSoLuongNguoi(soLuongNguoi);
            phieu.setGhiChu(ghiChu);
            phieu.setTrangThai(TrangThaiPhieuDat.valueOf(trangThaiStr));
            phieu.setTienDatCoc(tienDatCoc);
            return phieu;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean themPhieuDatBan(PhieuDatBan phieu) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "insert into PhieuDatBan (maPhieuDat, maKH, maNV, ngayLapPhieu, thoiGianDen, soLuongNguoi, ghiChu, trangThai, tienDatCoc) values (?,?,?,?,?,?,?,?,?)";
        try {
            if (phieu.getNhanVien() == null || phieu.getNhanVien().getMaNV() == null) {
                System.err.println("Lỗi: Mã nhân viên không được NULL");
                return false;
            }
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, phieu.getMaPhieuDat());
            if (phieu.getKhachHang() == null || phieu.getKhachHang().getMaKH() == null) {
                pstm.setNull(2, java.sql.Types.VARCHAR);
            } else {
                pstm.setString(2, phieu.getKhachHang().getMaKH());
            }
            pstm.setString(3, phieu.getNhanVien().getMaNV());
            pstm.setTimestamp(4, java.sql.Timestamp
                    .valueOf(phieu.getNgayLapPhieu() != null ? phieu.getNgayLapPhieu() : LocalDateTime.now()));
            pstm.setTimestamp(5, java.sql.Timestamp.valueOf(phieu.getThoiGianDen()));
            pstm.setInt(6, phieu.getSoLuongNguoi());
            pstm.setString(7, phieu.getGhiChu());
            pstm.setString(8, phieu.getTrangThai().name());
            pstm.setDouble(9, phieu.getTienDatCoc());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Lỗi khi insert PhieuDatBan - Mã: " + (phieu != null ? phieu.getMaPhieuDat() : "null"));
            if (e.getMessage() != null && e.getMessage().contains("FOREIGN KEY")) {
                System.err.println(
                        "FOREIGN KEY violation - Kiểm tra maKH hoặc maNV có tồn tại trong bảng KhachHang/NhanVien");
            }
            e.printStackTrace();
        }
        return false;
    }

    public PhieuDatBan getPhieuDatBanTheoMa(String maPhieuDat) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuDatBan where maPhieuDat = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maPhieuDat);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildPhieuDatBanFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PhieuDatBan> getAllPhieuDatBan() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuDatBan";
        ArrayList<PhieuDatBan> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuDatBan phieu = buildPhieuDatBanFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    public List<PhieuDatBan> getHoatDongGanDay() {
        List<PhieuDatBan> list = new ArrayList<>();
        Connection con = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM PhieuDatBan " +
                "WHERE CAST(ngayLapPhieu AS DATE) = CAST(GETDATE() AS DATE) " +
                "ORDER BY ngayLapPhieu DESC";
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuDatBan pdb = buildPhieuDatBanFromResultSet(rs);
                list.add(pdb);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<PhieuDatBan> getPhieuDatBanTheoNgay(LocalDate ngay) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuDatBan where convert(date, thoiGianDen) = ?";
        ArrayList<PhieuDatBan> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setDate(1, java.sql.Date.valueOf(ngay));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuDatBan phieu = buildPhieuDatBanFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    public List<PhieuDatBan> getPhieuDatBanTheoKhachHang(String maKH) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuDatBan where maKH = ?";
        ArrayList<PhieuDatBan> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maKH);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuDatBan phieu = buildPhieuDatBanFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    public List<PhieuDatBan> getPhieuDatBanTheoBan(String maBan) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select pdb.* from PhieuDatBan pdb " +
                "inner join ChiTietPhieuDatBan ctpdb on pdb.maPhieuDat = ctpdb.maPhieuDat " +
                "where ctpdb.maBan = ?";
        ArrayList<PhieuDatBan> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maBan);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuDatBan phieu = buildPhieuDatBanFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    public List<String> getDanhSachBanTrongTheoThoiGian(LocalDateTime targetTime) {
        List<String> dsBanTrong = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        
        java.time.LocalDate targetDate = targetTime.toLocalDate();
        java.time.LocalDate today = java.time.LocalDate.now();
        
        String sql;
        if (targetDate.equals(today)) {
            sql = "SELECT maBan FROM BanAn WHERE maBan NOT IN (" +
                  "    SELECT ctpdb.maBan FROM ChiTietPhieuDatBan ctpdb " +
                  "    INNER JOIN PhieuDatBan pdb ON ctpdb.maPhieuDat = pdb.maPhieuDat " +
                  "    WHERE CONVERT(date, pdb.thoiGianDen) = ? " +
                  "    AND pdb.trangThai = N'DANG_CHO'" +
                  ") AND trangThai != N'DANG_DUNG'";
        } else {
              sql = "SELECT maBan FROM BanAn WHERE maBan NOT IN (" +
                  "    SELECT ctpdb.maBan FROM ChiTietPhieuDatBan ctpdb " +
                  "    INNER JOIN PhieuDatBan pdb ON ctpdb.maPhieuDat = pdb.maPhieuDat " +
                  "    WHERE CONVERT(date, pdb.thoiGianDen) = ? " +
                  "    AND pdb.trangThai = N'DANG_CHO'" +
                  ")";
        }
                     
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setDate(1, java.sql.Date.valueOf(targetDate));
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                dsBanTrong.add(rs.getString("maBan"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsBanTrong;
    }

    public List<Ban> getAllBan() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from Ban";
        ArrayList<Ban> dsBan = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Ban ban = new Ban();
                ban.setMaBan(rs.getString("maBan"));
                dsBan.add(ban);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsBan;
    }

    public boolean capNhatPhieuDatBan(PhieuDatBan phieu) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update PhieuDatBan set maKH = ?, maNV = ?, thoiGianDen = ?, soLuongNguoi = ?, ghiChu = ?, trangThai = ? where maPhieuDat = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            String maKH = (phieu.getKhachHang() != null) ? phieu.getKhachHang().getMaKH() : null;
            pstm.setString(1, maKH);
            pstm.setString(2, phieu.getNhanVien().getMaNV());
            pstm.setTimestamp(3, java.sql.Timestamp.valueOf(phieu.getThoiGianDen()));
            pstm.setInt(4, phieu.getSoLuongNguoi());
            pstm.setString(5, phieu.getGhiChu());
            pstm.setString(6, phieu.getTrangThai().name());
            pstm.setString(7, phieu.getMaPhieuDat());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatTrangThaiPhieu(String maPhieuDat, TrangThaiPhieuDat trangThai) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update PhieuDatBan set trangThai = ? where maPhieuDat = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, trangThai.name());
            pstm.setString(2, maPhieuDat);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaPhieuDatBan(String maPhieuDat) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "delete from PhieuDatBan where maPhieuDat = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maPhieuDat);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<PhieuDatBan> getPhieuDatBanTheoTrangThai(TrangThaiPhieuDat trangThai) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from PhieuDatBan where trangThai = ?";
        ArrayList<PhieuDatBan> dsPhieu = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, trangThai.name());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PhieuDatBan phieu = buildPhieuDatBanFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    public boolean capNhatKhachHangChoPhieu(String maPhieu, String maKH) {
    	String sql = "update PhieuDatBan set maKH = ? where maPhieuDat = ?";
    	KhachHang kh = khachHangDAO.getKhachHangTheoMa(maKH);
    	Connection con = DatabaseConnection.getConnection();
    	if(kh!= null) {
    		try {
				PreparedStatement pstm = con.prepareStatement(sql);
				pstm.setString(1, maKH);
				pstm.setString(2, maPhieu);
				return pstm.executeUpdate() > 0;
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
        return false;
    }
    
    public boolean hasReservationToday(String maBan) {
        String sql = "SELECT COUNT(*) FROM PhieuDatBan pdb " +
                     "JOIN ChiTietPhieuDatBan ct ON pdb.maPhieuDat = ct.maPhieuDat " +
                     "WHERE ct.maBan = ? AND pdb.trangThai = 'DANG_CHO' " +
                     "AND CAST(pdb.thoiGianDen AS DATE) = CAST(GETDATE() AS DATE)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maBan);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean tuDongHuyPhieuQuaHan() {
        boolean coPhieuBiHuy = false;
        try {
            List<PhieuDatBan> danhSachCho = getDanhSachPhieuDatBanTheoTrangThai(TrangThaiPhieuDat.DANG_CHO);
            LocalDateTime now = LocalDateTime.now();

            BanService banService = new BanService();
            ChiTietPhieuDatBanService ctpdbService = new ChiTietPhieuDatBanService();

            for (PhieuDatBan phieu : danhSachCho) {
                LocalDateTime thoiGianHetHan = phieu.getThoiGianDen().plusMinutes(30);

                if (now.isAfter(thoiGianHetHan)) {
                    capNhatTrangThaiPhieu(phieu.getMaPhieuDat(), TrangThaiPhieuDat.DA_HUY);

                    List<ChiTietPhieuDatBan> chiTietList = ctpdbService.getChiTietByMaPhieuDat(phieu.getMaPhieuDat());
                    banService.capNhatTrangThaiBanTrongChiTietPDB(chiTietList, TrangThaiBan.TRONG);
                    
                    coPhieuBiHuy = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coPhieuBiHuy; 
    }
    

    public List<PhieuDatBan> getDanhSachPhieuDatBanTheoTrangThai(TrangThaiPhieuDat trangThai) {
        List<PhieuDatBan> dsPhieu = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();

        String sql = "SELECT * FROM PhieuDatBan WHERE trangThai = ?";
        
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            
             pstm.setString(1, trangThai.name()); 
             ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                PhieuDatBan phieu = buildPhieuDatBanFromResultSet(rs);
                if (phieu != null) {
                    dsPhieu.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsPhieu;
    }

    
    public boolean kiemTraBanDaDuocDatTrongNgay(String maBan, LocalDate ngay, String maPhieuDatNgoaiLe) {
        String sql = "SELECT COUNT(*) FROM PhieuDatBan pdb " +
                     "JOIN ChiTietPhieuDatBan ct ON pdb.maPhieuDat = ct.maPhieuDat " +
                     "WHERE ct.maBan = ? AND CAST(pdb.thoiGianDen AS DATE) = ? " +
                     "AND pdb.trangThai = 'DANG_CHO' AND pdb.maPhieuDat != ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maBan);
            stmt.setDate(2, java.sql.Date.valueOf(ngay));
            stmt.setString(3, maPhieuDatNgoaiLe);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean capNhatTrangThaiBanTheoPhieuDatHomNay() {
        String sqlClean = "UPDATE BanAn " +
                          "SET trangThai = 'TRONG' " +
                          "WHERE trangThai = 'DA_DAT' " +
                          "AND maBan NOT IN (" +
                          "    SELECT DISTINCT ct.maBan " +
                          "    FROM PhieuDatBan pdb " +
                          "    JOIN ChiTietPhieuDatBan ct ON pdb.maPhieuDat = ct.maPhieuDat " +
                          "    WHERE CAST(pdb.thoiGianDen AS DATE) = CAST(GETDATE() AS DATE) " +
                          "    AND pdb.trangThai = 'DANG_CHO'" +
                          ")";
                          
        String sqlApply = "UPDATE BanAn " +
                          "SET trangThai = 'DA_DAT' " +
                          "WHERE maBan IN (" +
                          "    SELECT DISTINCT ct.maBan " +
                          "    FROM PhieuDatBan pdb " +
                          "    JOIN ChiTietPhieuDatBan ct ON pdb.maPhieuDat = ct.maPhieuDat " +
                          "    WHERE CAST(pdb.thoiGianDen AS DATE) = CAST(GETDATE() AS DATE) " +
                          "    AND pdb.trangThai = 'DANG_CHO'" +
                          ") AND trangThai = 'TRONG'"; 
                          
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); 
            try (PreparedStatement pstmClean = conn.prepareStatement(sqlClean);
                 PreparedStatement pstmApply = conn.prepareStatement(sqlApply)) {
                 
                pstmClean.executeUpdate(); 
                pstmApply.executeUpdate(); 
                
                conn.commit();
                return true;
            } catch (Exception e) {
                conn.rollback(); 
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

	public boolean capNhatThoiGianDen(String maPhieuDat, LocalDateTime thoiGianDen) {
		 Connection connection = DatabaseConnection.getConnection();
	        String sql = "update PhieuDatBan set thoiGianDen = ? where maPhieuDat = ?";
	        try {
	            PreparedStatement pstm = connection.prepareStatement(sql);
	           
	            pstm.setTimestamp(1, java.sql.Timestamp.valueOf(thoiGianDen));
	            pstm.setString(2, maPhieuDat);
	            return pstm.executeUpdate() > 0;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false;
	}
	
	
	public boolean kiemTraBanCoPhieuDangSuDung(String maBan) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT COUNT(*) FROM ChiTietPhieuDatBan ct " +
                     "INNER JOIN PhieuDatBan p ON ct.maPhieuDat = p.maPhieuDat " +
                     "WHERE ct.maBan = ? AND p.trangThai = N'DANG_SU_DUNG'";
        
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, maBan);
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