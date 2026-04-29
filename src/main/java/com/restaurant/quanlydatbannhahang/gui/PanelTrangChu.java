package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import com.restaurant.quanlydatbannhahang.service.BanService;

import javax.swing.border.EmptyBorder;

public class PanelTrangChu extends javax.swing.JPanel {

    public PanelTrangChu() {
        initComponents();
        customUI();
    }

    private void loadDuLieu() {
        try {
            BanService banService = new BanService();
            int countBanDangSuDung = (int) banService.getBanDangSuDung().size();
            lblCountBanDatTruoc.setText(String.valueOf(countBanDangSuDung));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi load dữ liệu Trang chủ: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshData() {
        loadDuLieu();
    }

    private void customUI() {
        // 1. Bo góc các Card thống kê
        applyCardStyle(cardBanSuDung, 30);
        applyCardStyle(cardBanDatTruoc, 30);
        applyCardStyle(cardDoanhThu, 30);

        // 2. Thiết lập cho jPanel1 (Panel chứa bảng)
        jPanel1.setBackground(new Color(254, 243, 198));
        applyCardStyle(jPanel1, 40);
        jPanel1.setBorder(new EmptyBorder(25, 25, 25, 25));

        // 3. Tùy chỉnh ScrollPane - XÓA VIỀN VÀ SỬA LỖI GÓC PHẢI
        scrTableHoatDong.setBorder(BorderFactory.createEmptyBorder()); // Xóa viền ngoài hoàn toàn
        scrTableHoatDong.setViewportBorder(null);
        scrTableHoatDong.setOpaque(false);
        scrTableHoatDong.getViewport().setOpaque(false);

        // Sửa lỗi mất màu góc trên bên phải (khu vực giao giữa Header và ScrollBar)
        JPanel corner = new JPanel();
        corner.setBackground(new Color(255, 251, 235)); // Màu trùng với Header
        scrTableHoatDong.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corner);

        // 4. Tùy chỉnh Table
        tblHoatDong.setShowGrid(false);
        tblHoatDong.setIntercellSpacing(new Dimension(0, 0));
        tblHoatDong.setRowHeight(45);
        tblHoatDong.setBorder(BorderFactory.createEmptyBorder()); // Đảm bảo bảng không tự vẽ viền

        // Chiều cao Header
        tblHoatDong.getTableHeader().setPreferredSize(new Dimension(tblHoatDong.getTableHeader().getWidth(), 45));

        // Custom Header Renderer
        tblHoatDong.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                label.setBackground(new Color(255, 251, 235));
                label.setForeground(new Color(148, 134, 111));
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setHorizontalAlignment(JLabel.CENTER);
                // Tạo đường kẻ mảnh phía dưới để phân cách Header và Body
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
                return label;
            }
        });

        // Căn giữa toàn bộ các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblHoatDong.getColumnCount(); i++) {
            tblHoatDong.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void applyCardStyle(JPanel panel, int radius) {
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));

        panel.setUI(new javax.swing.plaf.PanelUI() {
            @Override
            public void update(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(c.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), radius, radius);

                g2.dispose();
            }
        });
    }

    // Không sửa phần dưới, giữ nguyên code tự sinh của design
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlThongKe = new javax.swing.JPanel();
        cardBanSuDung = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblCountBanSuDung = new javax.swing.JLabel();
        cardBanDatTruoc = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblCountBanDatTruoc = new javax.swing.JLabel();
        cardDoanhThu = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblSumDoanhThu = new javax.swing.JLabel();
        pnlBangHoatDong = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        scrTableHoatDong = new javax.swing.JScrollPane();
        tblHoatDong = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(807, 600));
        setMinimumSize(new java.awt.Dimension(807, 600));
        setPreferredSize(new java.awt.Dimension(807, 600));
        setLayout(new java.awt.BorderLayout());

        pnlThongKe.setBackground(new java.awt.Color(255, 251, 233));
        pnlThongKe.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 1, 60));
        pnlThongKe.setForeground(new java.awt.Color(255, 255, 255));
        pnlThongKe.setLayout(new java.awt.GridLayout(1, 3, 60, 0));

        cardBanSuDung.setBackground(new java.awt.Color(5, 223, 114));
        cardBanSuDung.setPreferredSize(new java.awt.Dimension(250, 120));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Bàn đang sử dụng");

        lblCountBanSuDung.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblCountBanSuDung.setForeground(new java.awt.Color(255, 255, 255));
        lblCountBanSuDung.setText("0");

        javax.swing.GroupLayout cardBanSuDungLayout = new javax.swing.GroupLayout(cardBanSuDung);
        cardBanSuDung.setLayout(cardBanSuDungLayout);
        cardBanSuDungLayout.setHorizontalGroup(
                cardBanSuDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(cardBanSuDungLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(cardBanSuDungLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(lblCountBanSuDung))
                                .addContainerGap(140, Short.MAX_VALUE)));
        cardBanSuDungLayout.setVerticalGroup(
                cardBanSuDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(cardBanSuDungLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCountBanSuDung)
                                .addContainerGap(28, Short.MAX_VALUE)));

        pnlThongKe.add(cardBanSuDung);

        cardBanDatTruoc.setBackground(new java.awt.Color(253, 199, 0));
        cardBanDatTruoc.setPreferredSize(new java.awt.Dimension(250, 120));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Bàn đặt trước");

        lblCountBanDatTruoc.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblCountBanDatTruoc.setForeground(new java.awt.Color(255, 255, 255));
        lblCountBanDatTruoc.setText("0");

        javax.swing.GroupLayout cardBanDatTruocLayout = new javax.swing.GroupLayout(cardBanDatTruoc);
        cardBanDatTruoc.setLayout(cardBanDatTruocLayout);
        cardBanDatTruocLayout.setHorizontalGroup(
                cardBanDatTruocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(cardBanDatTruocLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(cardBanDatTruocLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblCountBanDatTruoc)
                                        .addComponent(jLabel2))
                                .addContainerGap(178, Short.MAX_VALUE)));
        cardBanDatTruocLayout.setVerticalGroup(
                cardBanDatTruocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(cardBanDatTruocLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCountBanDatTruoc)
                                .addContainerGap(29, Short.MAX_VALUE)));

        pnlThongKe.add(cardBanDatTruoc);

        cardDoanhThu.setBackground(new java.awt.Color(255, 100, 103));
        cardDoanhThu.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Doanh thu hôm nay");

        lblSumDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblSumDoanhThu.setForeground(new java.awt.Color(255, 255, 255));
        lblSumDoanhThu.setText("0");

        javax.swing.GroupLayout cardDoanhThuLayout = new javax.swing.GroupLayout(cardDoanhThu);
        cardDoanhThu.setLayout(cardDoanhThuLayout);
        cardDoanhThuLayout.setHorizontalGroup(
                cardDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(cardDoanhThuLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(cardDoanhThuLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(lblSumDoanhThu))
                                .addContainerGap(127, Short.MAX_VALUE)));
        cardDoanhThuLayout.setVerticalGroup(
                cardDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(cardDoanhThuLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSumDoanhThu)
                                .addContainerGap(30, Short.MAX_VALUE)));

        pnlThongKe.add(cardDoanhThu);

        add(pnlThongKe, java.awt.BorderLayout.PAGE_START);

        pnlBangHoatDong.setBackground(new java.awt.Color(255, 251, 233));
        pnlBangHoatDong.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        pnlBangHoatDong.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(254, 243, 198));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 30, 30, 20));
        jPanel1.setLayout(new java.awt.BorderLayout(0, 10));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(148, 134, 111));
        jLabel7.setText("Hoạt động gần đây");
        jPanel1.add(jLabel7, java.awt.BorderLayout.PAGE_START);

        scrTableHoatDong.setBorder(null);

        tblHoatDong.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Mã phiếu đặt", "Mã bàn", "Tên khách hàng", "Số điện thoại", "Giờ đến", "Số người", "Trạng thái"
                }) {
            Class<?>[] types = new Class<?>[] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                    java.lang.Object.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false
            };

            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tblHoatDong.setRowHeight(35);
        scrTableHoatDong.setViewportView(tblHoatDong);

        jPanel1.add(scrTableHoatDong, java.awt.BorderLayout.CENTER);

        pnlBangHoatDong.add(jPanel1, java.awt.BorderLayout.CENTER);

        add(pnlBangHoatDong, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cardBanDatTruoc;
    private javax.swing.JPanel cardBanSuDung;
    private javax.swing.JPanel cardDoanhThu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCountBanDatTruoc;
    private javax.swing.JLabel lblCountBanSuDung;
    private javax.swing.JLabel lblSumDoanhThu;
    private javax.swing.JPanel pnlBangHoatDong;
    private javax.swing.JPanel pnlThongKe;
    private javax.swing.JScrollPane scrTableHoatDong;
    private javax.swing.JTable tblHoatDong;
    // End of variables declaration//GEN-END:variables
}
