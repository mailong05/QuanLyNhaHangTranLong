package com.restaurant.quanlydatbannhahang.gui;
import com.formdev.flatlaf.FlatLightLaf;
public class UIConfiguration {
    private static boolean isSetup = false;
    public static void setupUI() {
        if (isSetup) {
            return;
        }
        try {
            FlatLightLaf.setup();
            isSetup = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static boolean isUiSetup() {
        return isSetup;
    }
}