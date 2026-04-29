package com.restaurant.quanlydatbannhahang.session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Luu du lieu tam thoi tu PanelDatMon sang PanelLapHoaDon.
 */
public final class HoaDonDraftSession {

    private static final Map<String, List<DraftMonItem>> monItemsByMaBan = new LinkedHashMap<>();
    private static final Map<String, String> contextByMaBan = new LinkedHashMap<>();
    private static final Map<String, String> maKHByMaBan = new LinkedHashMap<>();
    private static final Map<String, String> maKMByMaBan = new LinkedHashMap<>();
    private static final Map<String, Integer> diemDungByMaBan = new LinkedHashMap<>();
    private static String currentMaBanContext = "";
    private static String currentPhoneNumber = "";
    private static String currentMaPhieuDatContext = "";

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

    public static Set<String> parseMaBanContextToSet(String maBanRaw) {
        String normalized = normalizeMaBanContext(maBanRaw);
        Set<String> maBanSet = new LinkedHashSet<>();
        if (normalized.isEmpty()) {
            return maBanSet;
        }

        for (String maBan : normalized.split(",")) {
            String value = maBan.trim();
            if (!value.isEmpty()) {
                maBanSet.add(value);
            }
        }
        return maBanSet;
    }

    public static boolean isMaBanInContext(String maBanContext, String maBan) {
        if (maBan == null || maBan.trim().isEmpty()) {
            return false;
        }
        return parseMaBanContextToSet(maBanContext).contains(maBan.trim());
    }

    public static String resolveMaBanContext(String maBanContext) {
        String normalized = normalizeMaBanContext(maBanContext);
        if (normalized.isEmpty()) {
            return "";
        }

        if (monItemsByMaBan.containsKey(normalized)) {
            return normalized;
        }

        Set<String> maBanSet = parseMaBanContextToSet(normalized);
        if (maBanSet.size() == 1) {
            String maBan = maBanSet.iterator().next();
            String mappedContext = contextByMaBan.get(maBan);
            if (mappedContext != null && !mappedContext.isEmpty() && monItemsByMaBan.containsKey(mappedContext)) {
                return mappedContext;
            }

            for (String storedContext : monItemsByMaBan.keySet()) {
                if (isMaBanInContext(storedContext, maBan)) {
                    return storedContext;
                }
            }
        }

        return normalized;
    }

    public static String getCurrentMaBanContext() {
        return currentMaBanContext;
    }

    public static void setCurrentMaBanContext(String maBanContext) {
        currentMaBanContext = normalizeMaBanContext(maBanContext);
    }

    public static String getCurrentPhoneNumber() {
        return currentPhoneNumber;
    }

    public static void setCurrentPhoneNumber(String phoneNumber) {
        currentPhoneNumber = phoneNumber == null ? "" : phoneNumber.trim();
    }

    public static void clearCurrentPhoneNumber() {
        currentPhoneNumber = "";
    }

    public static String getCurrentMaPhieuDatContext() {
        return currentMaPhieuDatContext;
    }

    public static void setCurrentMaPhieuDatContext(String maPhieuDat) {
        currentMaPhieuDatContext = maPhieuDat == null ? "" : maPhieuDat.trim();
    }

    public static void clearCurrentMaPhieuDatContext() {
        currentMaPhieuDatContext = "";
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

        Set<String> maBanSet = parseMaBanContextToSet(normalized);
        for (String maBan : maBanSet) {
            contextByMaBan.put(maBan, normalized);
        }
    }

    public static boolean hasDraft(String maBanContext) {
        String normalized = normalizeMaBanContext(maBanContext);
        List<DraftMonItem> items = monItemsByMaBan.get(normalized);
        return items != null && !items.isEmpty();
    }

    public static void clear(String maBanContext) {
        String normalized = normalizeMaBanContext(maBanContext);
        monItemsByMaBan.remove(normalized);

        Set<String> maBanToRemove = new LinkedHashSet<>();
        for (Map.Entry<String, String> entry : contextByMaBan.entrySet()) {
            if (normalized.equals(entry.getValue())) {
                maBanToRemove.add(entry.getKey());
            }
        }
        for (String maBan : maBanToRemove) {
            contextByMaBan.remove(maBan);
            maKHByMaBan.remove(maBan);
            maKMByMaBan.remove(maBan);
            diemDungByMaBan.remove(maBan);
        }
    }

    public static void setInvoiceMetadata(String maBanContext, String maKH, String maKM, int diemDung) {
        String normalized = normalizeMaBanContext(maBanContext);
        if (normalized.isEmpty()) {
            return;
        }
        if (maKH == null) {
            maKHByMaBan.remove(normalized);
        } else {
            maKHByMaBan.put(normalized, maKH);
        }
        if (maKM == null) {
            maKMByMaBan.remove(normalized);
        } else {
            maKMByMaBan.put(normalized, maKM);
        }
        if (diemDung <= 0) {
            diemDungByMaBan.remove(normalized);
        } else {
            diemDungByMaBan.put(normalized, diemDung);
        }
    }

    public static String getMaKH(String maBanContext) {
        String normalized = normalizeMaBanContext(maBanContext);
        return normalized.isEmpty() ? "" : maKHByMaBan.getOrDefault(normalized, "");
    }

    public static String getMaKM(String maBanContext) {
        String normalized = normalizeMaBanContext(maBanContext);
        return normalized.isEmpty() ? "" : maKMByMaBan.getOrDefault(normalized, "");
    }

    public static int getDiemDung(String maBanContext) {
        String normalized = normalizeMaBanContext(maBanContext);
        return normalized.isEmpty() ? 0 : diemDungByMaBan.getOrDefault(normalized, 0);
    }

    public static void clear() {
        monItemsByMaBan.clear();
        contextByMaBan.clear();
        maKHByMaBan.clear();
        maKMByMaBan.clear();
        diemDungByMaBan.clear();
        currentMaBanContext = "";
        currentPhoneNumber = "";
        currentMaPhieuDatContext = "";
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
