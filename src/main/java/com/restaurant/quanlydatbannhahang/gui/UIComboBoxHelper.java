package com.restaurant.quanlydatbannhahang.gui;

import javax.swing.*;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiThue;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiMonAn;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiKhuyenMai;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiHoaDon;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiPhieuDat;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiNhanVien;
import java.lang.reflect.Method;

/**
 * Helper class để load enum values lên JComboBox
 * Hỗ trợ các enum có method getDisplayName()
 */
public class UIComboBoxHelper {

    /**
     * Load enum values lên JComboBox
     * Yêu cầu: Enum phải có method getDisplayName()
     * 
     * @param comboBox     JComboBox cần load dữ liệu
     * @param defaultLabel Label mặc định (e.g., "Trạng thái", "Chọn loại")
     * @param enumClass    Class của enum
     */
    public static <E extends Enum<E>> void loadEnumToComboBox(
            JComboBox<String> comboBox,
            String defaultLabel,
            Class<E> enumClass) {
        comboBox.removeAllItems();
        comboBox.addItem(defaultLabel);

        try {
            // Lấy method getDisplayName()
            Method getDisplayName = enumClass.getMethod("getDisplayName");

            for (E enumValue : enumClass.getEnumConstants()) {
                String displayValue = (String) getDisplayName.invoke(enumValue);
                comboBox.addItem(displayValue);
            }
        } catch (Exception e) {
            throw new RuntimeException("Không thể load enum vào ComboBox: " + e.getMessage(), e);
        }
    }

    /**
     * Load TrangThaiThue lên ComboBox
     */
    public static void loadTrangThaiThueToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiThue.class);
    }

    /**
     * Load TrangThaiMonAn lên ComboBox
     */
    public static void loadTrangThaiMonAnToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiMonAn.class);
    }

    /**
     * Load TrangThaiBan lên ComboBox
     */
    public static void loadTrangThaiBanToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiBan.class);
    }

    /**
     * Load TrangThaiKhuyenMai lên ComboBox
     */
    public static void loadTrangThaiKhuyenMaiToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiKhuyenMai.class);
    }

    /**
     * Load TrangThaiHoaDon lên ComboBox
     */
    public static void loadTrangThaiHoaDonToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiHoaDon.class);
    }

    /**
     * Load TrangThaiPhieuDat lên ComboBox
     */
    public static void loadTrangThaiPhieuDatToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiPhieuDat.class);
    }

    /**
     * Load TrangThaiNhanVien lên ComboBox
     */
    public static void loadTrangThaiNhanVienToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiNhanVien.class);
    }

    /**
     * Lấy enum value từ display text của TrangThaiThue
     */
    public static TrangThaiThue getTrangThaiThueFromDisplay(String displayText) {
        for (TrangThaiThue trangThai : TrangThaiThue.values()) {
            if (trangThai.getDisplayName().equals(displayText)) {
                return trangThai;
            }
        }
        return null;
    }

    /**
     * Lấy enum value từ display text của TrangThaiMonAn
     */
    public static TrangThaiMonAn getTrangThaiMonAnFromDisplay(String displayText) {
        for (TrangThaiMonAn trangThai : TrangThaiMonAn.values()) {
            if (trangThai.getDisplayName().equals(displayText)) {
                return trangThai;
            }
        }
        return null;
    }

    /**
     * Lấy enum value từ display text của TrangThaiBan
     */
    public static TrangThaiBan getTrangThaiBanFromDisplay(String displayText) {
        for (TrangThaiBan trangThai : TrangThaiBan.values()) {
            if (trangThai.getDisplayName().equals(displayText)) {
                return trangThai;
            }
        }
        return null;
    }

    /**
     * Lấy enum value từ display text của TrangThaiKhuyenMai
     */
    public static TrangThaiKhuyenMai getTrangThaiKhuyenMaiFromDisplay(String displayText) {
        for (TrangThaiKhuyenMai trangThai : TrangThaiKhuyenMai.values()) {
            if (trangThai.getDisplayName().equals(displayText)) {
                return trangThai;
            }
        }
        return null;
    }

    /**
     * Lấy enum value từ display text của TrangThaiHoaDon
     */
    public static TrangThaiHoaDon getTrangThaiHoaDonFromDisplay(String displayText) {
        for (TrangThaiHoaDon trangThai : TrangThaiHoaDon.values()) {
            if (trangThai.getDisplayName().equals(displayText)) {
                return trangThai;
            }
        }
        return null;
    }

    /**
     * Lấy enum value từ display text của TrangThaiPhieuDat
     */
    public static TrangThaiPhieuDat getTrangThaiPhieuDatFromDisplay(String displayText) {
        for (TrangThaiPhieuDat trangThai : TrangThaiPhieuDat.values()) {
            if (trangThai.getDisplayName().equals(displayText)) {
                return trangThai;
            }
        }
        return null;
    }

    /**
     * Lấy enum value từ display text của TrangThaiNhanVien
     */
    public static TrangThaiNhanVien getTrangThaiNhanVienFromDisplay(String displayText) {
        for (TrangThaiNhanVien trangThai : TrangThaiNhanVien.values()) {
            if (trangThai.getDisplayName().equals(displayText)) {
                return trangThai;
            }
        }
        return null;
    }
}
