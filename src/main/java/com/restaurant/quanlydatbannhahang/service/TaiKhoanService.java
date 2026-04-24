package com.restaurant.quanlydatbannhahang.service;

import java.sql.PreparedStatement;
import java.util.List;

import com.restaurant.quanlydatbannhahang.dao.TaiKhoanDAO;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.entity.QuyenHan;
import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;
import com.restaurant.quanlydatbannhahang.service.AuthService.ValidationResult;

/**
 * 
 * Khác AuthService chỉ handle authentication (login, validate)
 */
public class TaiKhoanService {

    private static TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    /**
     * Cập nhật mật khẩu cho tài khoản
     * Có validate password + username inside
     * 
     * @param username        Username của tài khoản
     * @param newPassword     Mật khẩu mới (plaintext - không hash)
     * @param confirmPassword Xác nhận mật khẩu mới
     * @return ValidationResult với success + message
     */
    public static ValidationResult updatePassword(String username, String newPassword, String confirmPassword) {
        ValidationResult validationPassword = AuthService.validatePassword(newPassword);
        if (!validationPassword.success) {
            return validationPassword;
        }

        ValidationResult validationConfirm = AuthService.validateConfirmPassword(newPassword, confirmPassword);
        if (!validationConfirm.success) {
            return validationConfirm;
        }

        if (username == null || username.isEmpty()) {
            return new ValidationResult(false, "Tên đăng nhập không được để trống");
        }

        boolean updateSuccess = taiKhoanDAO.updatePassword(username, newPassword);
        if (updateSuccess) {
            return new ValidationResult(true, "Cập nhật mật khẩu thành công");
        } else {
            return new ValidationResult(false, "Lỗi cập nhật mật khẩu từ cơ sở dữ liệu. Vui lòng thử lại!");
        }
    }

    public List<TaiKhoan> getAllTaiKhoan() {
        // TODO Auto-generated method stub
        return taiKhoanDAO.getAllTaiKhoan();
    }

    public String getPasswordByUsername(String username, String maNV) {
        if (username == null || maNV == null)
            throw new IllegalArgumentException("username null hoặc maNV null");
        if (username.isEmpty() || maNV.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập username, maNV đầy đủ để thực hiện truy vấn");
        }
        if (taiKhoanDAO.getPasswordByUsernameAndID(username, maNV) == null) {
            throw new RuntimeException("Không tìm thấy thông tin mật khẩu của tài khoản");
        }

        return taiKhoanDAO.getPasswordByUsernameAndID(username, maNV);
    }

    public boolean themTaiKhoan(String username, String password, String maNV, QuyenHan quyenHan) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên tài khoản không được để trống");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống");
        }
        if (maNV == null || maNV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống");
        }
        if (quyenHan == null) {
            throw new IllegalArgumentException("Quyền hạn không hợp lệ");
        }

        TaiKhoan tk = new TaiKhoan();
        tk.setUsername(username);
        tk.setPassword(password);
        tk.setNhanVien(new NhanVien(maNV, null, null, null, null, 0, null));
        tk.setQuyenHan(quyenHan);
        return taiKhoanDAO.themTaiKhoan(tk);
    }

    public boolean capNhatTaiKhoan(String username, String maNV, QuyenHan quyenHan) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên tài khoản không được để trống");
        }
        if (maNV == null || maNV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống");
        }
        if (quyenHan == null) {
            throw new IllegalArgumentException("Quyền hạn không hợp lệ");
        }

        TaiKhoan tk = new TaiKhoan();
        tk.setUsername(username);
        tk.setNhanVien(new NhanVien(maNV, null, null, null, null, 0, null));
        tk.setQuyenHan(quyenHan);
        return taiKhoanDAO.capNhatTaiKhoan(tk);
    }

    public boolean xoaTaiKhoan(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên tài khoản không được để trống");
        }
        return taiKhoanDAO.xoaTaiKhoan(username);
    }

    public boolean resetMatKhauMacDinh(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên tài khoản không được để trống");
        }
        return taiKhoanDAO.updatePassword(username, "123456");
    }
}
