package com.restaurant.quanlydatbannhahang.util;

import javax.swing.*;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.lang.reflect.Method;


public class ComboBoxEntityLoader {


    public static <T> void loadEntitiesToComboBox(
            JComboBox<String> comboBox,
            String defaultLabel,
            List<T> entities,
            String getterMethodName) {
        
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

            
            Set<String> addedValues = new HashSet<>();

            for (T entity : entities) {
                if (entity != null) {
                    
                    Method getter = entity.getClass().getMethod(getterMethodName);
                    Object value = getter.invoke(entity);

                    if (value != null) {
                        String valueStr = value.toString();

                        
                        if (!addedValues.contains(valueStr)) {
                            comboBox.addItem(valueStr);
                            addedValues.add(valueStr); 
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading entities to ComboBox: " + e.getMessage());
            e.printStackTrace();
        } finally {
            
            for (java.awt.event.ActionListener listener : listeners) {
                comboBox.addActionListener(listener);
            }
        }
    }


    public static void loadKhuVucToComboBox(
            JComboBox<String> comboBox,
            List<?> khuVucs) {
        loadEntitiesToComboBox(comboBox, "--Tất cả--", khuVucs, "getMaKhuVuc");
    }


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
