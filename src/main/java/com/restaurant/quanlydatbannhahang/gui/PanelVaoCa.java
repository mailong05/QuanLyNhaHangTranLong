package com.restaurant.quanlydatbannhahang.gui;

import com.restaurant.quanlydatbannhahang.entity.CaLamViec;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiCa;
import com.restaurant.quanlydatbannhahang.service.CaLamViecService;
import com.restaurant.quanlydatbannhahang.session.SessionManager;
import com.restaurant.quanlydatbannhahang.util.CurrencyUtility;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PanelVaoCa extends JPanel {
    private static final long[] DENOMINATIONS = { 1000, 2000, 5000, 10000, 20000, 50000, 100000, 200000, 500000 };
    private static final String[] LABELS = {
            "Số tờ mệnh giá 1.000", "Số tờ mệnh giá 2.000", "Số tờ mệnh giá 5.000",
            "Số tờ mệnh giá 10.000", "Số tờ mệnh giá 20.000", "Số tờ mệnh giá 50.000",
            "Số tờ mệnh giá 100.000", "Số tờ mệnh giá 200.000", "Số tờ mệnh giá 500.000"
    };

    private JLabel lblNhanVien;
    private JLabel lblThoiGianVaoCa;
    private JLabel lblTongTienDauCa;
    private JTextField[] txtDenomCounts;
    private JButton btnVaoCa;
    private CaLamViecService caLamViecService;

    public PanelVaoCa() {
        this.caLamViecService = new CaLamViecService();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 20));
        setBackground(Color.WHITE);

        JPanel header = new JPanel(new GridLayout(0, 1, 0, 8));
        header.setBackground(Color.WHITE);

        JLabel title = new JLabel("THÔNG TIN ĐẦU CA", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(44, 62, 80));
        header.add(title);

        NhanVien nhanVien = SessionManager.getCurrentNhanVien();
        String tenNhanVien = nhanVien != null ? nhanVien.getHoTen() : "Không xác định";
        lblNhanVien = new JLabel("Nhân viên: " + tenNhanVien, SwingConstants.CENTER);
        lblNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        header.add(lblNhanVien);

        lblThoiGianVaoCa = new JLabel("Thời gian: " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")), SwingConstants.CENTER);
        lblThoiGianVaoCa.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        header.add(lblThoiGianVaoCa);

        add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 20, 8, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        txtDenomCounts = new JTextField[DENOMINATIONS.length];
        for (int i = 0; i < DENOMINATIONS.length; i++) {
            gbc.gridy = i;
            gbc.gridx = 0;
            gbc.weightx = 0;
            JLabel lbl = new JLabel(LABELS[i] + ":");
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            formPanel.add(lbl, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1;
            JTextField txtCount = new JTextField("0");
            txtCount.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            txtCount.getDocument().addDocumentListener(new SimpleDocumentListener(this::updateTongTienDauCa));
            txtDenomCounts[i] = txtCount;
            formPanel.add(txtCount, gbc);
        }

        gbc.gridy = DENOMINATIONS.length;
        gbc.gridx = 0;
        gbc.weightx = 0;
        JLabel lblTong = new JLabel("Tổng tiền đầu ca:");
        lblTong.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formPanel.add(lblTong, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        lblTongTienDauCa = new JLabel(CurrencyUtility.formatVND(0));
        lblTongTienDauCa.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formPanel.add(lblTongTienDauCa, gbc);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(Color.WHITE);
        centerWrapper.add(formPanel);
        add(centerWrapper, BorderLayout.CENTER);

        btnVaoCa = new JButton("VÀO CA");
        btnVaoCa.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnVaoCa.setBackground(new Color(33, 150, 243));
        btnVaoCa.setForeground(Color.WHITE);
        btnVaoCa.setFocusPainted(false);
        btnVaoCa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVaoCa.addActionListener(e -> vaoCa());

        JButton btnThoat = new JButton("THOÁT");
        btnThoat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnThoat.setBackground(new Color(231, 76, 60));
        btnThoat.setForeground(Color.WHITE);
        btnThoat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnThoat.setFocusPainted(false);
        btnThoat.addActionListener(e -> {
            int res = JOptionPane.showConfirmDialog(this,
                    "Bạn có muốn thoát chương trình?",
                    "Xác nhận thoát",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            
            if (res == JOptionPane.YES_OPTION) {
                java.awt.Window w = SwingUtilities.getWindowAncestor(this);
                if (w instanceof java.awt.Frame) {
                    java.awt.Frame frame = (java.awt.Frame) w;
                    frame.dispatchEvent(new java.awt.event.WindowEvent(frame, java.awt.event.WindowEvent.WINDOW_CLOSING));
                } else {
                    System.exit(0);
                }
            }
        });

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(Color.WHITE);
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.setOpaque(false);
        left.add(btnThoat);
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
        center.setOpaque(false);
        center.add(btnVaoCa);
        footer.add(left, BorderLayout.WEST);
        footer.add(center, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
        updateTongTienDauCa();
    }

    private void updateTongTienDauCa() {
        lblTongTienDauCa.setText(CurrencyUtility.formatVND(calculateTotalCountValue()));
    }

    private long calculateTotalCountValue() {
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
            if (count < 0) {
                return -1;
            }
            return count;
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private void vaoCa() {
        try {
            NhanVien nhanVien = SessionManager.getCurrentNhanVien();
            if (nhanVien == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên. Vui lòng đăng nhập lại.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            long total = 0;
            for (int i = 0; i < DENOMINATIONS.length; i++) {
                int count = parseCount(txtDenomCounts[i].getText().trim());
                if (count < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Vui lòng nhập số tờ hợp lệ cho " + LABELS[i] + ".",
                            "Lỗi dữ liệu", JOptionPane.WARNING_MESSAGE);
                    txtDenomCounts[i].requestFocus();
                    return;
                }
                total += (long) count * DENOMINATIONS[i];
            }

            if (total <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ít nhất một tờ tiền để vào ca.",
                        "Lỗi dữ liệu", JOptionPane.WARNING_MESSAGE);
                return;
            }

            CaLamViec ca = new CaLamViec();
            ca.setMaCa(caLamViecService.generateNextMaCa());
            ca.setNhanVien(nhanVien);
            ca.setThoiGianVaoCa(LocalDateTime.now());
            ca.setTienDauCa(total);
            ca.setTrangThai(TrangThaiCa.DANG_LAM_VIEC);
            ca.setGhiChu(null);

            caLamViecService.themCaLamViec(ca);
            SessionManager.setCurrentCaLamViec(ca);

            JOptionPane.showMessageDialog(this, "Bắt đầu ca thành công.", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            java.awt.Frame parentFrame = (java.awt.Frame) SwingUtilities.getWindowAncestor(this);
            if (parentFrame instanceof MainForm) {
                MainForm mainForm = (MainForm) parentFrame;
                mainForm.setMenuEnabled(true);
                mainForm.goToTrangChuFromPanel();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi vào ca: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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
