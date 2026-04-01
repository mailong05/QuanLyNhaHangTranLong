package com.restaurant.quanlydatbannhahang.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;

public class NhanVienDAO {
	private Connection connection;

	public NhanVienDAO() {
		// TODO Auto-generated constructor stub
		this.connection = DatabaseConnection.getConnection();
	}

	public NhanVien buildNhanVienFromResultSet(ResultSet rs) {
		try {
			return new NhanVien(
					rs.getString("maNV"),
					rs.getString("hoTen"),
					rs.getString("sdt"),
					rs.getString("chucVu"),
					rs.getDate("ngayVaoLam").toLocalDate(),
					rs.getDouble("luongCoBan"),
					rs.getBoolean("trangThai"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public NhanVien getNhanVienTheoMa(String maNV) {
		String sql = "Select * from NhanVien where maNV = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, maNV);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				return buildNhanVienFromResultSet(rs);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
		return null;
	}

}
