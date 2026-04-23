package com.restaurant.quanlydatbannhahang.util;

import javax.swing.*;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiThue;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiMonAn;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiKhuyenMai;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiHoaDon;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiPhieuDat;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiNhanVien;
import com.restaurant.quanlydatbannhahang.entity.LoaiMonAn;
import com.restaurant.quanlydatbannhahang.entity.LoaiThanhVien;
import com.restaurant.quanlydatbannhahang.entity.QuyenHan;
import com.restaurant.quanlydatbannhahang.entity.ChucVu;
import java.lang.reflect.Method;

/**
 * Helper class để load enum values lên JComboBox
 * 
 * ⚠️ CHỈ DÙNG CHO ENUM
 * Nếu cần load entity (KhuVuc, LoaiMonAn, etc.), hãy dùng ComboBoxEntityLoader
 * 
 * Hỗ trợ các enum có method getDisplayName()
 * 
 * ===== PHÂN BIỆT CÁC LOADER =====
 * 
 * ComboBoxEnumLoader (file này):
 * - Dùng cho: Enum (TrangThaiThue, TrangThaiMonAn, TrangThaiBan, etc.)
 * - Tự động đọc getDisplayName() từ enum constants
 * - Ví dụ: ComboBoxEnumLoader.loadTrangThaiThueToComboBox(cbFilter)
 * 
 * ComboBoxEntityLoader:
 * - Dùng cho: Entity không phải enum (KhuVuc, LoaiMonAn, ChucVu, Ban, etc.)
 * - Load dữ liệu từ database qua Service
 * - Ví dụ: ComboBoxEntityLoader.loadKhuVucToComboBox(cbFilter, dsKhuVuc)
 * 
 * Giải quyết vấn đề lặp đi lặp lại trong việc load dữ liệu lên JComboBox filter
 */
public class ComboBoxEnumLoader {

    /**
     * Load enum values lên JComboBox (Generic method)
     * 
     * ⚠️ CHỈ DÙNG CHO ENUM (không phải entity)
     * 
     * Yêu cầu: Enum phải có method getDisplayName()
     * 
     * @param comboBox     JComboBox cần load dữ liệu
     * @param defaultLabel Label mặc định (e.g., "Trạng thái", "Chọn")
     * @param enumClass    Class của enum (e.g., TrangThaiThue.class)
     * 
     *                     Ví dụ:
     *                     ComboBoxEnumLoader.loadEnumToComboBox(cbFilter, "Trạng
     *                     thái", TrangThaiThue.class)
     */
    public static <E extends Enum<E>> void loadEnumToComboBox(
            JComboBox<String> comboBox,
            String defaultLabel,
            Class<E> enumClass) {
        // Tạm disable action listeners để tránh trigger nhiều lần khi loading
        java.awt.event.ActionListener[] listeners = comboBox.getActionListeners();
        for (java.awt.event.ActionListener listener : listeners) {
            comboBox.removeActionListener(listener);
        }

        try {
            comboBox.removeAllItems();
            comboBox.addItem(defaultLabel);

            // Lấy method getDisplayName()
            Method getDisplayName = enumClass.getMethod("getDisplayName");

            for (E enumValue : enumClass.getEnumConstants()) {
                String displayValue = (String) getDisplayName.invoke(enumValue);
                comboBox.addItem(displayValue);
            }
        } catch (Exception e) {
            System.err.println("Error loading enum to ComboBox: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Thêm lại action listeners
            for (java.awt.event.ActionListener listener : listeners) {
                comboBox.addActionListener(listener);
            }
        }
    }

    /**
     * Load TrangThaiThue lên ComboBox
     * 
     * ⚠️ CHỈ DÙNG CHO ENUM (không phải entity)
     * 
     * Ví dụ:
     * ComboBoxEnumLoader.loadTrangThaiThueToComboBox(cbFilterTrangThai)
     */
    public static void loadTrangThaiThueToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiThue.class);
    }

    /**
     * Load TrangThaiMonAn lên ComboBox
     * 
     * ⚠️ CHỈ DÙNG CHO ENUM (không phải entity)
     */
    public static void loadTrangThaiMonAnToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiMonAn.class);
    }

    /**
     * Load TrangThaiBan lên ComboBox
     * 
     * ⚠️ CHỈ DÙNG CHO ENUM (không phải entity)
     */
    public static void loadTrangThaiBanToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiBan.class);
    }

    /**
     * Load TrangThaiKhuyenMai lên ComboBox
     * 
     * ⚠️ CHỈ DÙNG CHO ENUM (không phải entity)
     */
    public static void loadTrangThaiKhuyenMaiToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiKhuyenMai.class);
    }

    /**
     * Load TrangThaiHoaDon lên ComboBox
     * 
     * ⚠️ CHỈ DÙNG CHO ENUM (không phải entity)
     */
    public static void loadTrangThaiHoaDonToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiHoaDon.class);
    }

    /**
     * Load TrangThaiPhieuDat lên ComboBox
     * 
     * ⚠️ CHỈ DÙNG CHO ENUM (không phải entity)
     */
    public static void loadTrangThaiPhieuDatToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Trạng thái", TrangThaiPhieuDat.class);
    }

    /**
     * Load TrangThaiNhanVien lên ComboBox
     * 
     * ⚠️ CHỈ DÙNG CHO ENUM (không phải entity)
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

    /**
     * Load LoaiMonAn lên ComboBox
     * 
     * ⚠️ CHỈ DÙNG CHO ENUM (không phải entity)
     * 
     * Ví dụ:
     * ComboBoxEnumLoader.loadLoaiMonAnToComboBox(cbFilterLoaiMonAn)
     */
    public static void loadLoaiMonAnToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Loại món ăn", LoaiMonAn.class);
    }

    /**
     * Load ChucVu lên ComboBox
     * 
     * ⚠️ CHỈ DÙNG CHO ENUM (không phải entity)
     * 
     * Ví dụ:
     * ComboBoxEnumLoader.loadChucVuToComboBox(cbFilterChucVu)
     */
    public static void loadChucVuToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Chức vụ", ChucVu.class);
    }

    /**
     * Lấy enum value từ display text của LoaiMonAn
     */
    public static LoaiMonAn getLoaiMonAnFromDisplay(String displayText) {
        for (LoaiMonAn loai : LoaiMonAn.values()) {
            if (loai.getDisplayName().equals(displayText)) {
                return loai;
            }
        }
        return null;
    }

    /**
     * Lấy enum value từ display text của ChucVu
     */
    public static ChucVu getChucVuFromDisplay(String displayText) {
        for (ChucVu chucVu : ChucVu.values()) {
            if (chucVu.getDisplayName().equals(displayText)) {
                return chucVu;
            }
        }
        return null;
    }
    
    public static void loadLoaiThanhVienToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Loại thành viên", LoaiThanhVien.class);
    }
    
    public static LoaiThanhVien getLoaiThanhVienFromDisplay(String displayText) {
        for (LoaiThanhVien ltv : LoaiThanhVien.values()) {
            if (ltv.getDisplayName().equals(displayText)) {
                return ltv;
            }
        }
        return null;
    }
    
    public static void loadQuyenHanToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Quyền hạn", QuyenHan.class);
    }
    
    public static QuyenHan getQuyenHanFromDisplay(String displayText) {
        for (QuyenHan qh : QuyenHan.values()) {
            if (qh.getDisplayName().equals(displayText)) {
                return qh;
            }
        }
        return null;
    }
}
