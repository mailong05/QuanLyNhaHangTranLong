package com.restaurant.quanlydatbannhahang.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.ChucVu;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiNhanVien;

public class NhanVienDAO {
	private Connection connection;

	public NhanVienDAO() {
		// TODO Auto-generated constructor stub
		this.connection = DatabaseConnection.getConnection();
	}

	public NhanVien buildNhanVienFromResultSet(ResultSet rs) {
		try {
			String chucVuStr = rs.getString("chucVu");
			ChucVu chucVu = ChucVu.valueOf(chucVuStr);

			TrangThaiNhanVien trangThai = rs.getBoolean("trangThai")
					? TrangThaiNhanVien.DANG_LAM_VIEC
					: TrangThaiNhanVien.DA_NGHI_VIEC;

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
