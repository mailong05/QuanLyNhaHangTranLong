package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.TaiKhoanDAO;
import com.restaurant.quanlydatbannhahang.service.AuthService.ValidationResult;

/**

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
}
