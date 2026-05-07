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
import com.restaurant.quanlydatbannhahang.entity.PhuongThucTT;
import com.restaurant.quanlydatbannhahang.entity.QuyenHan;
import com.restaurant.quanlydatbannhahang.entity.ChucVu;
import java.lang.reflect.Method;























public class ComboBoxEnumLoader {

    














    public static <E extends Enum<E>> void loadEnumToComboBox(
            JComboBox<String> comboBox,
            String defaultLabel,
            Class<E> enumClass) {
        
        java.awt.event.ActionListener[] listeners = comboBox.getActionListeners();
        for (java.awt.event.ActionListener listener : listeners) {
            comboBox.removeActionListener(listener);
        }

        try {
            comboBox.removeAllItems();
            comboBox.addItem(defaultLabel);

            
            Method getDisplayName = enumClass.getMethod("getDisplayName");

            for (E enumValue : enumClass.getEnumConstants()) {
                String displayValue = (String) getDisplayName.invoke(enumValue);
                comboBox.addItem(displayValue);
            }
        } catch (Exception e) {
            System.err.println("Error loading enum to ComboBox: " + e.getMessage());
            e.printStackTrace();
        } finally {
            
            for (java.awt.event.ActionListener listener : listeners) {
                comboBox.addActionListener(listener);
            }
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

    







    public static void loadLoaiMonAnToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Loại món ăn", LoaiMonAn.class);
    }

    







    public static void loadChucVuToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Chức vụ", ChucVu.class);
    }

    


    public static LoaiMonAn getLoaiMonAnFromDisplay(String displayText) {
        for (LoaiMonAn loai : LoaiMonAn.values()) {
            if (loai.getDisplayName().equals(displayText)) {
                return loai;
            }
        }
        return null;
    }

    


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
    
    public static void loadPTTTToComboBox(JComboBox<String> comboBox) {
        loadEnumToComboBox(comboBox, "Phương thức thanh toán", PhuongThucTT.class);
    }
    
    public static PhuongThucTT getPTTTFromDisplay(String displayText) {
        for (PhuongThucTT qh : PhuongThucTT.values()) {
            if (qh.getDisplayName().equals(displayText)) {
                return qh;
            }
        }
        return null;
    }
}
