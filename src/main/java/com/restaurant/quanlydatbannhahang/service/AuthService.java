package com.restaurant.quanlydatbannhahang.service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import com.restaurant.quanlydatbannhahang.dao.TaiKhoanDAO;
import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;
public class AuthService {
    private static final String REGEX_USERNAME = "^[a-z_]{3,20}$";
    private static final String REGEX_PASSWORD = "^\\d{6,}$";
    private static final String REGEX_PHONE = "^0[0-9]{9}$";
    private TaiKhoanDAO taiKhoanDAO;
    public AuthService() {
        this.taiKhoanDAO = new TaiKhoanDAO();
    }
    public static ValidationResult validateUsername(String username) {
        if (isEmpty(username)) {
            return new ValidationResult(false, "Tên đăng nhập không được để trống");
        }
        if (username.length() < 3) {
            return new ValidationResult(false, "Tên đăng nhập phải ít nhất 3 ký tự");
        }
        if (username.length() > 20) {
            return new ValidationResult(false, "Tên đăng nhập không được quá 20 ký tự");
        }
        if (username.matches(".*[A-Z].*")) {
            return new ValidationResult(false,
                    "Tên đăng nhập không được chứa chữ hoa. Chỉ dùng chữ thường (a-z) và dấu gạch dưới (_)");
        }
        if (username.contains(" ")) {
            return new ValidationResult(false, "Tên đăng nhập không được chứa dấu cách");
        }
        if (!username.matches(REGEX_USERNAME)) {
            return new ValidationResult(false, "Tên đăng nhập chỉ được chứa chữ thường (a-z) và dấu gạch dưới (_)");
        }
        return new ValidationResult(true, "Tên đăng nhập hợp lệ");
    }
    public static ValidationResult validatePassword(String password) {
        if (isEmpty(password)) {
            return new ValidationResult(false, "Mật khẩu không được để trống");
        }
        if (password.length() < 6) {
            return new ValidationResult(false, "Mật khẩu phải ít nhất 6 ký tự");
        }
        if (!password.matches("^[0-9]+$")) {
            return new ValidationResult(false, "Mật khẩu chỉ được chứa chữ số (0-9)");
        }
        if (!password.matches(REGEX_PASSWORD)) {
            return new ValidationResult(false, "Mật khẩu không hợp lệ");
        }
        return new ValidationResult(true, "Mật khẩu hợp lệ");
    }
    public static ValidationResult validatePhoneNumber(String phoneNumber) {
        if (isEmpty(phoneNumber)) {
            return new ValidationResult(false, "Số điện thoại không được để trống");
        }
        if (!phoneNumber.matches("^[0-9]+$")) {
            return new ValidationResult(false, "Số điện thoại chỉ được chứa chữ số");
        }
        if (phoneNumber.length() != 10) {
            return new ValidationResult(false, "Số điện thoại phải có đúng 10 chữ số");
        }
        if (!phoneNumber.startsWith("0")) {
            return new ValidationResult(false, "Số điện thoại phải bắt đầu bằng 0");
        }
        if (!phoneNumber.matches(REGEX_PHONE)) {
            return new ValidationResult(false, "Số điện thoại không hợp lệ (0XXXXXXXXX)");
        }
        return new ValidationResult(true, "Số điện thoại hợp lệ");
    }
    public static ValidationResult validateConfirmPassword(String password, String confirmPassword) {
        if (isEmpty(confirmPassword)) {
            return new ValidationResult(false, "Xác nhận mật khẩu không được để trống");
        }
        if (!password.equals(confirmPassword)) {
            return new ValidationResult(false, "Mật khẩu xác nhận không khớp với mật khẩu");
        }
        return new ValidationResult(true, "Xác nhận mật khẩu hợp lệ");
    }
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi mã hóa mật khẩu", e);
        }
    }
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        String hash = hashPassword(plainPassword);
        return hash.equals(hashedPassword);
    }
    public TaiKhoan login(String username, String password) {
        ValidationResult userValidation = validateUsername(username);
        if (!userValidation.success) {
            return null;
        }
        ValidationResult passValidation = validatePassword(password);
        if (!passValidation.success) {
            return null;
        }
        TaiKhoan taiKhoan = taiKhoanDAO.findByUsernameAndPassword(username, password);
        if (taiKhoan == null) {
            return null;
        }
        return taiKhoan;
    }
    public boolean checkUsernameExists(String username) {
        return taiKhoanDAO.existUsername(username);
    }
    public TaiKhoan getTaiKhoanByUsername(String username) {
        return taiKhoanDAO.findByUsername(username);
    }
    public static class ValidationResult {
        public boolean success;
        public String message;
        public ValidationResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        @Override
        public String toString() {
            return "ValidationResult{" +
                    "success=" + success +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}