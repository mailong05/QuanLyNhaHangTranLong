package com.restaurant.quanlydatbannhahang.gui;

import com.formdev.flatlaf.FlatLightLaf;

/**
 * UIConfiguration - Thiết lập Look and Feel cho toàn ứng dụng
 * Gọi setupUI() một lần duy nhất ở main() trước khi khởi tạo bất kỳ component
 * nào
 */
public class UIConfiguration {
    private static boolean isSetup = false; // Đảm bảo setup chỉ 1 lần

    /**
     * Setup FlatLaf - PHẢI gọi lần đầu tiên trong main()
     * Trước khi tạo bất kỳ GUI component nào
     */
    public static void setupUI() {
        // Tránh setup nhiều lần
        if (isSetup) {
            return;
        }

        try {
            // Setup FlatLight Look and Feel
            FlatLightLaf.setup();
            isSetup = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Kiểm tra UI đã setup chưa
     */
    public static boolean isUiSetup() {
        return isSetup;
    }
}
