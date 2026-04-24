package com.restaurant.quanlydatbannhahang.util;

import javax.swing.*;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.lang.reflect.Method;

/**
 * Helper class để load entity list values lên JComboBox
 * 
 * ⚠️ CHỈ DÙNG CHO ENTITY KHÔNG CÓ ENUM
 * Nếu attribute là enum (TrangThaiThue, TrangThaiMonAn, etc.), hãy dùng
 * ComboBoxEnumLoader
 * 
 * Giải quyết vấn đề lặp đi lặp lại khi load dữ liệu từ database vào ComboBox
 * 
 * ===== PHÂN BIỆT CÁC LOADER =====
 * 
 * ComboBoxEnumLoader:
 * - Dùng cho: Enum (TrangThaiThue, TrangThaiMonAn, TrangThaiBan, etc.)
 * - Tự động đọc getDisplayName() từ enum
 * - Ví dụ: ComboBoxEnumLoader.loadTrangThaiThueToComboBox(cbFilter)
 * 
 * ComboBoxEntityLoader (file này):
 * - Dùng cho: Entity không phải enum (KhuVuc, LoaiMonAn, ChucVu, Ban, etc.)
 * - Load dữ liệu từ database qua Service
 * - Ví dụ: ComboBoxEntityLoader.loadKhuVucToComboBox(cbFilter, dsKhuVuc)
 */
public class ComboBoxEntityLoader {

    /**
     * Load entity list lên JComboBox (Generic method)
     * 
     * ⚠️ CHỈ DÙNG CHO ENTITY KHÔNG CÓ ENUM
     * 
     * Yêu cầu: Entity phải có method getter tương ứng (e.g., getTenKhuVuc,
     * getTenMonAn)
     * 
     * @param comboBox         JComboBox cần load dữ liệu
     * @param defaultLabel     Label mặc định (e.g., "Chọn...", "Khu vực")
     * @param entities         Danh sách entity từ database
     * @param getterMethodName Tên method getter để lấy display text (e.g.,
     *                         "getTenKhuVuc", "getTenMonAn")
     */
    public static <T> void loadEntitiesToComboBox(
            JComboBox<String> comboBox,
            String defaultLabel,
            List<T> entities,
            String getterMethodName) {
        // Tạm disable action listeners để tránh trigger nhiều lần khi loading
        java.awt.event.ActionListener[] listeners = comboBox.getActionListeners();
        for (java.awt.event.ActionListener listener : listeners) {
            comboBox.removeActionListener(listener);
        }

        try {
            comboBox.removeAllItems();
            comboBox.addItem(defaultLabel);

            if (entities == null || entities.isEmpty()) {
                return;
            }

            // Dùng Set để tracking các giá trị đã được thêm (tránh lặp)
            Set<String> addedValues = new HashSet<>();

            for (T entity : entities) {
                if (entity != null) {
                    // Lấy method từ class
                    Method getter = entity.getClass().getMethod(getterMethodName);
                    Object value = getter.invoke(entity);

                    if (value != null) {
                        String valueStr = value.toString();

                        // Chỉ thêm vào comboBox nếu giá trị này chưa được thêm
                        if (!addedValues.contains(valueStr)) {
                            comboBox.addItem(valueStr);
                            addedValues.add(valueStr); // Mark as added
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading entities to ComboBox: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Thêm lại action listeners
            for (java.awt.event.ActionListener listener : listeners) {
                comboBox.addActionListener(listener);
            }
        }
    }

    /**
     * Load KhuVuc list lên ComboBox
     * 
     * ⚠️ CHỈ DÙNG CHO ENTITY (KhuVuc không phải enum)
     * 
     * @param comboBox JComboBox cần load dữ liệu
     * @param khuVucs  Danh sách KhuVuc từ KhuVucService.getAllKhuVuc()
     * 
     *                 Ví dụ:
     *                 List<KhuVuc> dsKhuVuc = khuVucService.getAllKhuVuc();
     *                 ComboBoxEntityLoader.loadKhuVucToComboBox(cbFilterKhuVuc,
     *                 dsKhuVuc);
     */
    public static void loadKhuVucToComboBox(
            JComboBox<String> comboBox,
            List<?> khuVucs) {
        loadEntitiesToComboBox(comboBox, "--Tất cả--", khuVucs, "getMaKhuVuc");
    }

    /**
     * Load Ban list lên ComboBox
     * 
     * ⚠️ CHỈ DÙNG CHO ENTITY (Ban không phải enum)
     * 
     * @param comboBox JComboBox cần load dữ liệu
     * @param bans     Danh sách Ban từ BanService
     * 
     *                 Ví dụ:
     *                 List<Ban> dsBan = banService.getAllBan();
     *                 ComboBoxEntityLoader.loadBanToComboBox(cbFilterBan, dsBan);
     */
    public static void loadBanToComboBox(
            JComboBox<String> comboBox,
            List<?> bans) {
        loadEntitiesToComboBox(comboBox, "Bàn", bans, "getMaBan");
    }

    public static void loadDonViTinhToComboBox(
            JComboBox<String> comboBox,
            List<?> monAn) {
        loadEntitiesToComboBox(comboBox, "Đơn vị tính", monAn, "getDonViTinh");
    }
}
