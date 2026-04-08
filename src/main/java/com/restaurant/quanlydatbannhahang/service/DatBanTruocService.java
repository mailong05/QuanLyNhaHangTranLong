package com.restaurant.quanlydatbannhahang.service;

import java.util.regex.Pattern;

/**
 * Service để validate dữ liệu đặt bàn
 */
public class DatBanTruocService {

    // Regex patterns
    private static final String PHONE_PATTERN = "^0[1-9]\\d{8}$";
    private static final String SOLUONG_PATTERN = "^[1-9]\\d*$"; // dùng cách này khỏi cần kiểm tra kí tự lạ

    private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);
    private static final Pattern SOLUONGPattern = Pattern.compile(SOLUONG_PATTERN);

    /**
     * Kiểm tra định dạng số điện thoại (10 chữ số)
     * 
     * @param soDienThoai - số điện thoại cần kiểm tra
     * @return true nếu hợp lệ
     */
    public static boolean isValidSoDienThoai(String soDienThoai) {
        if (soDienThoai == null || soDienThoai.trim().isEmpty()) {
            return false;
        }
        String phone = soDienThoai.trim();
        return phonePattern.matcher(phone).matches();
    }

    /**
     * Kiểm tra định dạng số lượng người
     * 
     * @param soLuong - số lượng cần kiểm tra
     * @return true nếu hợp lệ
     */
    public static boolean isValidSoLuong(String soLuong) {
        if (soLuong == null || soLuong.trim().isEmpty()) {
            return false;
        }
        return SOLUONGPattern.matcher(soLuong.trim()).matches();
    }

    /**
     * Validate toàn bộ dữ liệu đặt bàn trước
     * 
     * @return null nếu hợp lệ, hoặc thông báo lỗi
     */
    public static String validatePhieuDatBan(String soDienThoai, String soLuong) {
        if (soDienThoai == null || soDienThoai.trim().isEmpty()) {
            return "Vui lòng nhập số điện thoại!";
        }

        if (!isValidSoDienThoai(soDienThoai)) {
            return "Số điện thoại phải có 10 chữ số  và bắt đầu là 0!";
        }

        if (soLuong == null || soLuong.trim().isEmpty()) {
            return "Vui lòng nhập số lượng người!";
        }

        if (!isValidSoLuong(soLuong)) {
            return "Số lượng người phải là số dương!";
        }

        try {
            int quantity = Integer.parseInt(soLuong.trim());
            if (quantity > 100) {
                return "Số lượng người không thể vượt quá 100!";
            }
        } catch (NumberFormatException e) {
            return "Số lượng người không hợp lệ!";
        }

        return null; // Null = hợp lệ. Dùng cho JOptionPanel.showMessage
    }
}
