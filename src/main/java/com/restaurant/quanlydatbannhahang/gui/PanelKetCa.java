package com.restaurant.quanlydatbannhahang.gui;

import com.restaurant.quanlydatbannhahang.entity.CaLamViec;
import com.restaurant.quanlydatbannhahang.service.CaLamViecService;
import com.restaurant.quanlydatbannhahang.service.HoaDonService;
import com.restaurant.quanlydatbannhahang.session.SessionManager;
import com.restaurant.quanlydatbannhahang.util.CurrencyUtility;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PanelKetCa extends JPanel {
    private static final long[] DENOMINATIONS = { 1000, 2000, 5000, 10000, 20000, 50000, 100000, 200000, 500000 };
    private static final String[] LABELS = {
            "Số tờ mệnh giá 1.000", "Số tờ mệnh giá 2.000", "Số tờ mệnh giá 5.000",
            "Số tờ mệnh giá 10.000", "Số tờ mệnh giá 20.000", "Số tờ mệnh giá 50.000",
            "Số tờ mệnh giá 100.000", "Số tờ mệnh giá 200.000", "Số tờ mệnh giá 500.000"
    };

    private JLabel lblTienDauCa;
    private JLabel lblDoanhThuTienMat;
    private JLabel lblTongTienHeThong;
    private JLabel lblChenhLech;
    private JLabel lblTienKetCa;
    private JTextField[] txtDenomCounts;
    private JTextArea txtGhiChu;
    private JButton btnXacNhanKetCa;
    private JButton btnHuy;
    private CaLamViecService caLamViecService;
    private HoaDonService hoaDonService;
    private CaLamViec currentCa;
    private double tongTienHeThong;

    public PanelKetCa() {
        this.caLamViecService = new CaLamViecService();
        this.hoaDonService = new HoaDonService();
        this.currentCa = SessionManager.getCurrentCaLamViec();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 20));
        setBackground(Color.WHITE);

        JLabel title = new JLabel("KẾT THÚC CA", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(44, 62, 80));
        add(title, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 16, 8, 16);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        if (currentCa == null) {
            JLabel lblError = new JLabel("Không tìm thấy ca làm việc đang hoạt động.");
            lblError.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            lblError.setForeground(Color.RED);
            infoPanel.add(lblError, gbc);
            add(infoPanel, BorderLayout.CENTER);
            return;
        }

        JLabel lblNhanVien = new JLabel("Nhân viên: " + currentCa.getNhanVien().getHoTen());
        lblNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        infoPanel.add(lblNhanVien, gbc);

        JLabel lblThoiGian = new JLabel("Thời gian vào ca: " + currentCa.getThoiGianVaoCa()
                .format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")));
        lblThoiGian.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 1;
        infoPanel.add(lblThoiGian, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        JLabel lblTienDauText = new JLabel("Tiền đầu ca:");
        lblTienDauText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(lblTienDauText, gbc);

        lblTienDauCa = new JLabel(CurrencyUtility.formatVND(currentCa.getTienDauCa()));
        lblTienDauCa.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 1;
        infoPanel.add(lblTienDauCa, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblDoanhThuText = new JLabel("Tổng doanh thu tiền mặt trong ca:");
        lblDoanhThuText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(lblDoanhThuText, gbc);

        double doanhThuTienMat = hoaDonService.getTongDoanhThuTienMatTheoCa(currentCa.getThoiGianVaoCa());
        lblDoanhThuTienMat = new JLabel(CurrencyUtility.formatVND(doanhThuTienMat));
        lblDoanhThuTienMat.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 1;
        infoPanel.add(lblDoanhThuTienMat, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblTongHeThongText = new JLabel("Tổng tiền hệ thống tính toán:");
        lblTongHeThongText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(lblTongHeThongText, gbc);

        tongTienHeThong = currentCa.getTienDauCa() + doanhThuTienMat;
        lblTongTienHeThong = new JLabel(CurrencyUtility.formatVND(tongTienHeThong));
        lblTongTienHeThong.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 1;
        infoPanel.add(lblTongTienHeThong, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JLabel lblGhi = new JLabel("Số tờ thực tế trong két");
        lblGhi.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblGhi.setForeground(new Color(52, 73, 94));
        infoPanel.add(lblGhi, gbc);

        txtDenomCounts = new JTextField[DENOMINATIONS.length];
        for (int i = 0; i < DENOMINATIONS.length; i++) {
            gbc.gridy = 6 + i;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            JLabel lbl = new JLabel(LABELS[i] + ":");
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            infoPanel.add(lbl, gbc);

            gbc.gridx = 1;
            txtDenomCounts[i] = new JTextField("0");
            txtDenomCounts[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            txtDenomCounts[i].getDocument().addDocumentListener(new SimpleDocumentListener(this::updateChenhLech));
            infoPanel.add(txtDenomCounts[i], gbc);
        }

        gbc.gridy = 6 + DENOMINATIONS.length;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        JLabel lblTienThucTe = new JLabel("Tổng tiền thực tế:");
        lblTienThucTe.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(lblTienThucTe, gbc);

        lblTienKetCa = new JLabel(CurrencyUtility.formatVND(0));
        lblTienKetCa.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 1;
        infoPanel.add(lblTienKetCa, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblChenhLechText = new JLabel("Chênh lệch:");
        lblChenhLechText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(lblChenhLechText, gbc);

        lblChenhLech = new JLabel(CurrencyUtility.formatVND(0));
        lblChenhLech.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 1;
        infoPanel.add(lblChenhLech, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblGhiChuText = new JLabel("Ghi chú:");
        lblGhiChuText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(lblGhiChuText, gbc);

        txtGhiChu = new JTextArea(4, 20);
        txtGhiChu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setWrapStyleWord(true);
        JScrollPane scrollGhiChu = new JScrollPane(txtGhiChu);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        infoPanel.add(scrollGhiChu, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnXacNhanKetCa = new JButton("Xác nhận kết ca");
        btnXacNhanKetCa.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnXacNhanKetCa.setBackground(new Color(46, 204, 113));
        btnXacNhanKetCa.setForeground(Color.WHITE);
        btnXacNhanKetCa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnXacNhanKetCa.addActionListener(e -> ketCa());
        buttonPanel.add(btnXacNhanKetCa);

        btnHuy = new JButton("Hủy");
        btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnHuy.setBackground(new Color(231, 76, 60));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnHuy.addActionListener(e -> huy());
        buttonPanel.add(btnHuy);

        add(buttonPanel, BorderLayout.SOUTH);

        updateChenhLech();
    }

    private void updateChenhLech() {
        long actual = calculateActualCash();
        lblTienKetCa.setText(CurrencyUtility.formatVND(actual));
        double diff = actual - tongTienHeThong;
        lblChenhLech.setText(CurrencyUtility.formatVND(diff));
        if (diff == 0) {
            lblChenhLech.setForeground(Color.BLACK);
        } else if (diff > 0) {
            lblChenhLech.setForeground(new Color(39, 174, 96));
        } else {
            lblChenhLech.setForeground(new Color(192, 57, 43));
        }
    }

    private long calculateActualCash() {
        long total = 0;
        for (int i = 0; i < DENOMINATIONS.length; i++) {
            int count = parseCount(txtDenomCounts[i].getText().trim());
            if (count < 0) {
                continue;
            }
            total += (long) count * DENOMINATIONS[i];
        }
        return total;
    }

    private int parseCount(String text) {
        if (text.isEmpty()) {
            return 0;
        }
        try {
            int count = Integer.parseInt(text);
            return Math.max(count, 0);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private void ketCa() {
        try {
            if (currentCa == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy ca làm việc để kết.", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            long actual = calculateActualCash();
            for (int i = 0; i < DENOMINATIONS.length; i++) {
                if (parseCount(txtDenomCounts[i].getText().trim()) < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Vui lòng nhập số tờ hợp lệ cho " + LABELS[i] + ".",
                            "Lỗi dữ liệu", JOptionPane.WARNING_MESSAGE);
                    txtDenomCounts[i].requestFocus();
                    return;
                }
            }

            double diff = actual - tongTienHeThong;
            if (diff != 0 && txtGhiChu.getText().trim().isBlank()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng ghi chú lý do chênh lệch tiền khi tổng thực tế không khớp với hệ thống.",
                        "Lỗi dữ liệu", JOptionPane.WARNING_MESSAGE);
                txtGhiChu.requestFocus();
                return;
            }

            currentCa.setThoiGianKetCa(LocalDateTime.now());
            currentCa.setTienKetCa((double) actual);
            currentCa.setTrangThai(com.restaurant.quanlydatbannhahang.entity.TrangThaiCa.DA_KET_CA);
            currentCa.setGhiChu(txtGhiChu.getText().trim());
            caLamViecService.capNhatCaLamViec(currentCa);
            SessionManager.setCurrentCaLamViec(null);
            SessionManager.clearSession();
            JOptionPane.showMessageDialog(this, "Kết ca thành công. Hệ thống sẽ trở về màn hình đăng nhập.",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            java.awt.Frame parentFrame = (java.awt.Frame) SwingUtilities.getWindowAncestor(this);
            if (parentFrame != null) {
                parentFrame.dispose();
            }
            java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi kết ca: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void huy() {
        java.awt.Frame parentFrame = (java.awt.Frame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame instanceof MainForm) {
            MainForm mainForm = (MainForm) parentFrame;
            mainForm.goToTrangChuFromPanel();
        }
    }

    private static class SimpleDocumentListener implements DocumentListener {
        private final Runnable callback;

        public SimpleDocumentListener(Runnable callback) {
            this.callback = callback;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            callback.run();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            callback.run();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            callback.run();
        }
    }
}
