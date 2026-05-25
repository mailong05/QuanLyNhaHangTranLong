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
public class UIComboBoxHelper {
    public static <E extends Enum<E>> void loadEnumToComboBox(
            JComboBox<String> comboBox,
            String defaultLabel,
            Class<E> enumClass) {
        comboBox.removeAllItems();
        comboBox.addItem(defaultLabel);
        try {
            Method getDisplayName = enumClass.getMethod("getDisplayName");
            for (E enumValue : enumClass.getEnumConstants()) {
                String displayValue = (String) getDisplayName.invoke(enumValue);
                comboBox.addItem(displayValue);
            }
        } catch (Exception e) {
            throw new RuntimeException("Không thể load enum vào ComboBox: " + e.getMessage(), e);
        }
    }
    public static void loadTrangThaiThueToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiThue.class);
    }
    public static void loadTrangThaiMonAnToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiMonAn.class);
    }
    public static void loadTrangThaiBanToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiBan.class);
    }
    public static void loadTrangThaiKhuyenMaiToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiKhuyenMai.class);
    }
    public static void loadTrangThaiHoaDonToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiHoaDon.class);
    }
    public static void loadTrangThaiPhieuDatToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiPhieuDat.class);
    }
    public static void loadTrangThaiNhanVienToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiNhanVien.class);
    }
    public static TrangThaiThue getTrangThaiThueFromDisplay(String displayText) {
        for (TrangThaiThue trangThai : TrangThaiThue.values()) {
            if (trangThai.getDisplayName().equals(displayText)) {
                return trangThai;
            }
        }
        return null;
    }
    public static TrangThaiMonAn getTrangThaiMonAnFromDisplay(String displayText) {
        for (TrangThaiMonAn trangThai : TrangThaiMonAn.values()) {
            if (trangThai.getDisplayName().equals(displayText)) {
                return trangThai;
            }
        }
        return null;
    }
    public static TrangThaiBan getTrangThaiBanFromDisplay(String displayText) {
        for (TrangThaiBan trangThai : TrangThaiBan.values()) {
            if (trangThai.getDisplayName().equals(displayText)) {
                return trangThai;
            }
        }
        return null;
    }
    public static TrangThaiKhuyenMai getTrangThaiKhuyenMaiFromDisplay(String displayText) {
        for (TrangThaiKhuyenMai trangThai : TrangThaiKhuyenMai.values()) {
            if (trangThai.getDisplayName().equals(displayText)) {
                return trangThai;
            }
        }
        return null;
    }
    public static TrangThaiHoaDon getTrangThaiHoaDonFromDisplay(String displayText) {
        for (TrangThaiHoaDon trangThai : TrangThaiHoaDon.values()) {
            if (trangThai.getDisplayName().equals(displayText)) {
                return trangThai;
            }
        }
        return null;
    }
    public static TrangThaiPhieuDat getTrangThaiPhieuDatFromDisplay(String displayText) {
        for (TrangThaiPhieuDat trangThai : TrangThaiPhieuDat.values()) {
            if (trangThai.getDisplayName().equals(displayText)) {
                return trangThai;
            }
        }
        return null;
    }
    public static TrangThaiNhanVien getTrangThaiNhanVienFromDisplay(String displayText) {
        for (TrangThaiNhanVien trangThai : TrangThaiNhanVien.values()) {
            if (trangThai.getDisplayName().equals(displayText)) {
                return trangThai;
            }
        }
        return null;
    }
}