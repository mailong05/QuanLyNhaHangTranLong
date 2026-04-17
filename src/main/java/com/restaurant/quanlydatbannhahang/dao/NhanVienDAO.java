package com.restaurant.quanlydatbannhahang.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.ChucVu;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiNhanVien;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;

public class NhanVienDAO {

	public NhanVienDAO() {
	}

	/**
	 * Lấy mã nhân viên cuối cùng
	 * 
	 * @return Mã nhân viên cuối cùng (VD: NV015) hoặc null
	 */
	public String getLastNhanVienID() {
		return IDQueryHelper.getLastID("NhanVien", "maNV");
	}

	/**
	 * Build NhanVien from ResultSet - package-private so other DAOs can use
	 * internally
	 */
	NhanVien buildNhanVienFromResultSet(ResultSet rs) {
		try {
			String chucVuStr = rs.getString("chucVu");
			ChucVu chucVu = ChucVu.valueOf(chucVuStr);

			TrangThaiNhanVien trangThai = TrangThaiNhanVien.valueOf(rs.getString("trangThai"));
					

			return new NhanVien(
					rs.getString("maNV"),
					rs.getString("hoTen"),
					rs.getString("sdt"),
					chucVu,
					rs.getDate("ngayVaoLam").toLocalDate(),
					rs.getDouble("luongCoBan"),
					trangThai);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public NhanVien getNhanVienTheoMa(String maNV) {
		Connection connection = DatabaseConnection.getConnection();
		String sql = "Select * from NhanVien where maNV = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, maNV);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				return buildNhanVienFromResultSet(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public java.util.List<NhanVien> getAllNhanVien() {
		Connection connection = DatabaseConnection.getConnection();
		java.util.List<NhanVien> list = new java.util.ArrayList<>();
		String sql = "Select * from NhanVien";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				NhanVien nv = buildNhanVienFromResultSet(rs);
				if (nv != null) {
					list.add(nv);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean themNhanVien(NhanVien nhanVien) {
		Connection connection = DatabaseConnection.getConnection();
		String sql = "INSERT INTO NhanVien (maNV, hoTen, sdt, chucVu, ngayVaoLam, luongCoBan, trangThai) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, nhanVien.getMaNV());
			pstm.setString(2, nhanVien.getHoTen());
			pstm.setString(3, nhanVien.getSdt());
			pstm.setString(4, nhanVien.getChucVu().name());
			pstm.setDate(5, java.sql.Date.valueOf(nhanVien.getNgayVaoLam()));
			pstm.setDouble(6, nhanVien.getLuongCoBan());
			pstm.setBoolean(7, nhanVien.getTrangThai() == TrangThaiNhanVien.DANG_LAM_VIEC);
			return pstm.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean capNhatNhanVien(NhanVien nhanVien) {
		Connection connection = DatabaseConnection.getConnection();
		String sql = "UPDATE NhanVien SET hoTen = ?, sdt = ?, chucVu = ?, ngayVaoLam = ?, luongCoBan = ?, trangThai = ? "
				+ "WHERE maNV = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, nhanVien.getHoTen());
			pstm.setString(2, nhanVien.getSdt());
			pstm.setString(3, nhanVien.getChucVu().name());
			pstm.setDate(4, java.sql.Date.valueOf(nhanVien.getNgayVaoLam()));
			pstm.setDouble(5, nhanVien.getLuongCoBan());
			pstm.setBoolean(6, nhanVien.getTrangThai() == TrangThaiNhanVien.DANG_LAM_VIEC);
			pstm.setString(7, nhanVien.getMaNV());
			return pstm.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean xoaNhanVien(String maNV) {
		Connection connection = DatabaseConnection.getConnection();
		String sql = "DELETE FROM NhanVien WHERE maNV = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, maNV);
			return pstm.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public java.util.List<NhanVien> getNhanVienDangLamViec() {
		Connection connection = DatabaseConnection.getConnection();
		java.util.List<NhanVien> list = new java.util.ArrayList<>();
		String sql = "Select * from NhanVien where trangThai = 1";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				NhanVien nv = buildNhanVienFromResultSet(rs);
				if (nv != null) {
					list.add(nv);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
