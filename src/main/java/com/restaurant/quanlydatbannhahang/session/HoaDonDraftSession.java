package com.restaurant.quanlydatbannhahang.session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Luu du lieu tam thoi tu PanelDatMon sang PanelLapHoaDon.
 */
public final class HoaDonDraftSession {

    private static final Map<String, List<DraftMonItem>> monItemsByMaBan = new LinkedHashMap<>();
    private static String currentMaBanContext = "";

    private HoaDonDraftSession() {
    }

    public static String normalizeMaBanContext(String maBanRaw) {
        if (maBanRaw == null || maBanRaw.trim().isEmpty()) {
            return "";
        }

        return Arrays.stream(maBanRaw.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .sorted()
                .distinct()
                .collect(Collectors.joining(","));
    }

    public static String getCurrentMaBanContext() {
        return currentMaBanContext;
    }

    public static void setCurrentMaBanContext(String maBanContext) {
        currentMaBanContext = normalizeMaBanContext(maBanContext);
    }

    public static List<DraftMonItem> getMonItems() {
        return getMonItems(currentMaBanContext);
    }

    public static List<DraftMonItem> getMonItems(String maBanContext) {
        String normalized = normalizeMaBanContext(maBanContext);
        List<DraftMonItem> items = monItemsByMaBan.get(normalized);
        return items == null ? new ArrayList<>() : new ArrayList<>(items);
    }

    public static void setMonItems(List<DraftMonItem> items) {
        setMonItems(currentMaBanContext, items);
    }

    public static void setMonItems(String maBanContext, List<DraftMonItem> items) {
        String normalized = normalizeMaBanContext(maBanContext);
        List<DraftMonItem> copied = (items == null) ? new ArrayList<>() : new ArrayList<>(items);
        monItemsByMaBan.put(normalized, copied);
    }

    public static boolean hasDraft(String maBanContext) {
        String normalized = normalizeMaBanContext(maBanContext);
        List<DraftMonItem> items = monItemsByMaBan.get(normalized);
        return items != null && !items.isEmpty();
    }

    public static void clear(String maBanContext) {
        String normalized = normalizeMaBanContext(maBanContext);
        monItemsByMaBan.remove(normalized);
    }

    public static void clear() {
        monItemsByMaBan.clear();
        currentMaBanContext = "";
    }

    public static class DraftMonItem {
        private final String maMon;
        private final String tenMon;
        private final int soLuong;
        private final double donGia;

        public DraftMonItem(String maMon, String tenMon, int soLuong, double donGia) {
            this.maMon = maMon;
            this.tenMon = tenMon;
            this.soLuong = soLuong;
            this.donGia = donGia;
        }

        public String getMaMon() {
            return maMon;
        }

        public String getTenMon() {
            return tenMon;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public double getDonGia() {
            return donGia;
        }
    }
}
