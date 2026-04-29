package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.entity.HoaDon;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiHoaDon;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    private BanDAO banDAO;
    private NhanVienDAO nhanVienDAO;
    private KhuyenMaiDAO khuyenMaiDAO;
    private ThueDAO thueDAO;

    public HoaDonDAO() {
        this.banDAO = new BanDAO();
        this.nhanVienDAO = new NhanVienDAO();
        this.khuyenMaiDAO = new KhuyenMaiDAO();
        this.thueDAO = new ThueDAO();
    }

    /**
     * Lấy mã hóa đơn cuối cùng
     * 
     * @return Mã hóa đơn cuối cùng (VD: HD050) hoặc null
     */
    public String getLastHoaDonID() {
        return IDQueryHelper.getLastID("HoaDon", "maHD");
    }

    private HoaDon buildHoaDonFromResultSet(ResultSet rs) {
        try {
            String maHD = rs.getString("maHD");
            String maBan = rs.getString("maBan");
            String maNV = rs.getString("maNV");
            String maKM = rs.getString("maKM");
            String maThue = rs.getString("maThue");
            double thueSuat = rs.getDouble("thueSuat");
            double tienThue = rs.getDouble("tienThue");
            double tyLePhiDV = rs.getDouble("tyLePhiDV");
            double tienPhiDV = rs.getDouble("tienPhiDV");
            LocalDate ngayTao = rs.getDate("ngayTao").toLocalDate();
            LocalTime gioVao = rs.getTime("gioVao") != null ? rs.getTime("gioVao").toLocalTime() : null;
            LocalTime gioRa = rs.getTime("gioRa") != null ? rs.getTime("gioRa").toLocalTime() : null;
            double tongTienGoc = rs.getDouble("tongTienGoc");
            double tienGiamGia = rs.getDouble("tienGiamGia");
            double tongThanhToan = rs.getDouble("tongThanhToan");
            String phuongThucTTStr = rs.getString("phuongThucTT");
            String trangThaiStr = rs.getString("trangThaiThanhToan");

            com.restaurant.quanlydatbannhahang.entity.PhuongThucTT phuongThucTT = null;
            if (phuongThucTTStr != null) {
                phuongThucTT = com.restaurant.quanlydatbannhahang.entity.PhuongThucTT.fromDisplayName(phuongThucTTStr);
                if (phuongThucTT == null) {
                    try {
                        phuongThucTT = com.restaurant.quanlydatbannhahang.entity.PhuongThucTT.valueOf(phuongThucTTStr);
                    } catch (IllegalArgumentException ignored) {
                        phuongThucTT = null;
                    }
                }
            }

            Ban ban = banDAO.getBanTheoMa(maBan);
            if (ban == null) {
                ban = new Ban(maBan, 0, "", null, null);
            }

            HoaDon hoaDon = new HoaDon(
                    maHD,
                    ban,
                    nhanVienDAO.getNhanVienTheoMa(maNV),
                    khuyenMaiDAO.getKhuyenMaiTheoMa(maKM),
                    thueDAO.getThueTheoMa(maThue),
                    thueSuat,
                    tienThue,
                    tyLePhiDV,
                    tienPhiDV,
                    ngayTao,
                    gioVao,
                    gioRa,
                    tongTienGoc,
                    tienGiamGia,
                    tongThanhToan,
                    phuongThucTT,
                    TrangThaiHoaDon.valueOf(trangThaiStr));

            return hoaDon;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean themHoaDon(HoaDon hd) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "insert into HoaDon (maHD, maBan, maNV, maKM, maThue, thueSuat, tienThue, tyLePhiDV, tienPhiDV, ngayTao, gioVao, gioRa, tongTienGoc, tienGiamGia, tongThanhToan, phuongThucTT, trangThaiThanhToan) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, hd.getMaHD());
            pstm.setString(2, hd.getBan().getMaBan());
            pstm.setString(3, hd.getNhanVien().getMaNV());
            pstm.setString(4, hd.getKhuyenMai() != null ? hd.getKhuyenMai().getMaKM() : null);
            pstm.setString(5, hd.getThue().getMaThue());
            pstm.setDouble(6, hd.getThueSuat());
            pstm.setDouble(7, hd.getTienThue());
            pstm.setDouble(8, hd.getTyLePhiDV());
            pstm.setDouble(9, hd.getTienPhiDV());
            pstm.setDate(10, java.sql.Date.valueOf(hd.getNgayTao()));
            pstm.setTime(11, hd.getGioVao() != null ? java.sql.Time.valueOf(hd.getGioVao()) : null);
            pstm.setTime(12, hd.getGioRa() != null ? java.sql.Time.valueOf(hd.getGioRa()) : null);
            pstm.setDouble(13, hd.getTongTienGoc());
            pstm.setDouble(14, hd.getTienGiamGia());
            pstm.setDouble(15, hd.getTongThanhToan());
            pstm.setString(16, hd.getPhuongThucTT() != null ? hd.getPhuongThucTT().name() : null);
            pstm.setString(17, hd.getTrangThaiThanhToan().name());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public HoaDon getHoaDonTheoMa(String maHD) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from HoaDon where maHD = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maHD);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return buildHoaDonFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDon> getAllHoaDon() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from HoaDon";
        ArrayList<HoaDon> dsHoaDon = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HoaDon hd = buildHoaDonFromResultSet(rs);
                if (hd != null) {
                    dsHoaDon.add(hd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsHoaDon;
    }

    public List<HoaDon> getHoaDonTheoNgay(LocalDate ngay) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from HoaDon where convert(date, ngayTao) = ?";
        ArrayList<HoaDon> dsHoaDon = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setDate(1, java.sql.Date.valueOf(ngay));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HoaDon hd = buildHoaDonFromResultSet(rs);
                if (hd != null) {
                    dsHoaDon.add(hd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsHoaDon;
    }

    public List<HoaDon> getHoaDonTrongKhoangThoi(LocalDate tuNgay, LocalDate denNgay) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from HoaDon where convert(date, ngayTao) between ? and ?";
        ArrayList<HoaDon> dsHoaDon = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setDate(1, java.sql.Date.valueOf(tuNgay));
            pstm.setDate(2, java.sql.Date.valueOf(denNgay));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HoaDon hd = buildHoaDonFromResultSet(rs);
                if (hd != null) {
                    dsHoaDon.add(hd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsHoaDon;
    }

    public List<HoaDon> getHoaDonTheoNhanVien(String maNV) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select * from HoaDon where maNV = ?";
        ArrayList<HoaDon> dsHoaDon = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maNV);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HoaDon hd = buildHoaDonFromResultSet(rs);
                if (hd != null) {
                    dsHoaDon.add(hd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsHoaDon;
    }

    public List<HoaDon> getHoaDonTheoKhachHang(String maKH) {
        // This would require joining with PhieuDatBan or other related tables
        // For now, returning empty list
        return new ArrayList<>();
    }

    public boolean capNhatHoaDon(HoaDon hd) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "update HoaDon set maBan = ?, maNV = ?, maKM = ?, maThue = ?, thueSuat = ?, tienThue = ?, tyLePhiDV = ?, tienPhiDV = ?, ngayTao = ?, gioVao = ?, gioRa = ?, tongTienGoc = ?, tienGiamGia = ?, tongThanhToan = ?, phuongThucTT = ?, trangThaiThanhToan = ? where maHD = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, hd.getBan().getMaBan());
            pstm.setString(2, hd.getNhanVien().getMaNV());
            pstm.setString(3, hd.getKhuyenMai() != null ? hd.getKhuyenMai().getMaKM() : null);
            pstm.setString(4, hd.getThue().getMaThue());
            pstm.setDouble(5, hd.getThueSuat());
            pstm.setDouble(6, hd.getTienThue());
            pstm.setDouble(7, hd.getTyLePhiDV());
            pstm.setDouble(8, hd.getTienPhiDV());
            pstm.setDate(9, java.sql.Date.valueOf(hd.getNgayTao()));
            pstm.setTime(10, hd.getGioVao() != null ? java.sql.Time.valueOf(hd.getGioVao()) : null);
            pstm.setTime(11, hd.getGioRa() != null ? java.sql.Time.valueOf(hd.getGioRa()) : null);
            pstm.setDouble(12, hd.getTongTienGoc());
            pstm.setDouble(13, hd.getTienGiamGia());
            pstm.setDouble(14, hd.getTongThanhToan());
            pstm.setString(15, hd.getPhuongThucTT() != null ? hd.getPhuongThucTT().name() : null);
            pstm.setString(16, hd.getTrangThaiThanhToan().name());
            pstm.setString(17, hd.getMaHD());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaHoaDon(String maHD) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "delete from HoaDon where maHD = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maHD);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public double tinhTongDoanhThu() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select sum(tongThanhToan) from HoaDon where trangThaiThanhToan = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, TrangThaiHoaDon.DA_THANH_TOAN.name());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double tinhTongDoanhThuTheoNgay(LocalDate ngay) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select sum(tongThanhToan) from HoaDon where convert(date, ngayTao) = ? and trangThaiThanhToan = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setDate(1, java.sql.Date.valueOf(ngay));
            pstm.setString(2, TrangThaiHoaDon.DA_THANH_TOAN.name());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double tinhTongDoanhThuTheoGian(LocalDate tuNgay, LocalDate denNgay) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "select sum(tongThanhToan) from HoaDon where convert(date, ngayTao) between ? and ? and trangThaiThanhToan = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setDate(1, java.sql.Date.valueOf(tuNgay));
            pstm.setDate(2, java.sql.Date.valueOf(denNgay));
            pstm.setString(3, TrangThaiHoaDon.DA_THANH_TOAN.name());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<HoaDon> getHoaDonTheoMaBan(String maBan) {
        Connection connection = DatabaseConnection.getConnection();
        boolean isGroup = maBan != null && maBan.contains(",");
        String sql = isGroup
                ? "select * from HoaDon where maBan = ?"
                : "select * from HoaDon where maBan = ? or maBan like ? or maBan like ? or maBan like ?";
        ArrayList<HoaDon> dsHoaDon = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, maBan);
            if (!isGroup) {
                pstm.setString(2, maBan + ",%");
                pstm.setString(3, "%," + maBan + ",%");
                pstm.setString(4, "%," + maBan);
            }
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HoaDon hd = buildHoaDonFromResultSet(rs);
                if (hd != null) {
                    dsHoaDon.add(hd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsHoaDon;
    }

    public List<HoaDon> getHoaDonTheoTrangThai(TrangThaiHoaDon trangThai) {
        String sql = "select * from HoaDon where trangThaiThanhToan = ?";
        Connection connection = DatabaseConnection.getConnection();

        ArrayList<HoaDon> dsHoaDon = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, trangThai.name());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HoaDon hd = buildHoaDonFromResultSet(rs);
                if (hd != null) {
                    dsHoaDon.add(hd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsHoaDon;
    }
}
