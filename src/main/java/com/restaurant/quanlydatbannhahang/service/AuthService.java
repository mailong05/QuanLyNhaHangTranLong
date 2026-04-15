package com.restaurant.quanlydatbannhahang.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import com.restaurant.quanlydatbannhahang.dao.TaiKhoanDAO;
import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;

public class AuthService {

    // Regex patterns
    private static final String REGEX_USERNAME = "^[a-z_]{3,20}$";
    private static final String REGEX_PASSWORD = "^\\d{6,}$";
    private static final String REGEX_PHONE = "^0[0-9]{9}$";

    private TaiKhoanDAO taiKhoanDAO;

    public AuthService() {
        this.taiKhoanDAO = new TaiKhoanDAO();
    }

    /**
     * Validate username
     * - Chỉ chứa chữ cái thường (a-z) và dấu gạch dưới (_)
     * - Độ dài: 3-20 ký tự
     * 
     * @param username Tên đăng nhập cần kiểm tra
     * @return ValidationResult với success và message
     */
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

    /**
     * Validate password
     * - Độ dài: ít nhất 6 ký tự
     * 
     * @param password Mật khẩu cần kiểm tra
     * @return ValidationResult với success và message
     */
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

    /**
     * Validate phone number
     * - Định dạng: 0XXXXXXXXX (0 ở đầu + 9 chữ số)
     * 
     * @param phoneNumber Số điện thoại cần kiểm tra
     * @return ValidationResult với success và message
     */
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

    /**
     * Validate confirm password (kiểm tra 2 mật khẩu có giống nhau không)
     * 
     * @param password        Mật khẩu lần 1
     * @param confirmPassword Mật khẩu lần 2
     * @return ValidationResult với success và message
     */
    public static ValidationResult validateConfirmPassword(String password, String confirmPassword) {
        if (isEmpty(confirmPassword)) {
            return new ValidationResult(false, "Xác nhận mật khẩu không được để trống");
        }

        if (!password.equals(confirmPassword)) {
            return new ValidationResult(false, "Mật khẩu xác nhận không khớp với mật khẩu");
        }

        return new ValidationResult(true, "Xác nhận mật khẩu hợp lệ");
    }

    /**
     * Kiểm tra chuỗi có rỗng không (null hoặc empty)
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Mã hóa mật khẩu sử dụng SHA-256
     * 
     * @param password Mật khẩu plaintext
     * @return Mật khẩu đã mã hóa (Base64 encoded)
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi mã hóa mật khẩu", e);
        }
    }

    /**
     * Kiểm tra mật khẩu plaintext có khớp với mật khẩu đã mã hóa không
     * 
     * @param plainPassword  Mật khẩu plaintext
     * @param hashedPassword Mật khẩu đã mã hóa
     * @return true nếu khớp, false nếu không
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        String hash = hashPassword(plainPassword);
        return hash.equals(hashedPassword);
    }

    /**
     * Login - Kiểm tra username và password
     * Trước tiên validate dữ liệu, rồi kiểm tra database
     */
    public TaiKhoan login(String username, String password) {
        // Validate username
        ValidationResult userValidation = validateUsername(username);
        if (!userValidation.success) {
            return null;
        }

        // Validate password
        ValidationResult passValidation = validatePassword(password);
        if (!passValidation.success) {
            return null;
        }

        // Kiểm tra database
        // ✅ Gửi password plaintext (vì DB hiện tại chứa plaintext, không phải hash)
        TaiKhoan taiKhoan = taiKhoanDAO.findByUsernameAndPassword(username, password);

        if (taiKhoan == null) {
            return null;
        }

        return taiKhoan;
    }

    /**
     * Kiểm tra tài khoản tồn tại
     */
    public boolean checkUsernameExists(String username) {
        return taiKhoanDAO.existUsername(username);
    }

    /**
     * Lấy thông tin tài khoản theo username
     */
    public TaiKhoan getTaiKhoanByUsername(String username) {
        return taiKhoanDAO.findByUsername(username);
    }

    /**
     * ValidationResult - Lớp chứa kết quả validation
     * - success: true/false
     * - message: thông báo chi tiết
     */
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
