package com.restaurant.quanlydatbannhahang.gui;

import com.restaurant.quanlydatbannhahang.entity.ChiTietHoaDon;
import com.restaurant.quanlydatbannhahang.entity.HoaDon;
import com.restaurant.quanlydatbannhahang.entity.MonAn;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiHoaDon;
import com.restaurant.quanlydatbannhahang.service.ChiTietHoaDonService;
import com.restaurant.quanlydatbannhahang.service.HoaDonService;
import com.restaurant.quanlydatbannhahang.util.ImageUtil;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.EmptyBorder;

public class PanelThongKe extends javax.swing.JPanel {

    private HoaDonService hoaDonService;
    private ChiTietHoaDonService chiTietHoaDonService;

    public PanelThongKe() {
        initComponents();
        hoaDonService = new HoaDonService();
        chiTietHoaDonService = new ChiTietHoaDonService();
        customUI();
        loadStatistics();
    }

    private static class TopMonAnItem {
        private String maMon;
        private String tenMon;
        private String imagePath;
        private double donGia;
        private int soLuong;

        TopMonAnItem(MonAn monAn, int soLuong) {
            this.maMon = monAn.getMaMon();
            this.tenMon = monAn.getTenMon();
            this.imagePath = monAn.getUrlHinhAnh();
            this.donGia = monAn.getDonGia();
            this.soLuong = soLuong;
        }
    }

    private void loadStatistics() {
        try {
            List<HoaDon> dsHoaDon = hoaDonService.getAllHoaDon();
            double tongDoanhThu = 0;
            int tongHoaDonDaThanhToan = 0;

            Map<String, TopMonAnItem> topMonMap = new HashMap<>();

            for (HoaDon hd : dsHoaDon) {
                if (hd == null || hd.getTrangThaiThanhToan() != TrangThaiHoaDon.DA_THANH_TOAN) {
                    continue;
                }

                tongHoaDonDaThanhToan++;
                tongDoanhThu += hd.getTongThanhToan();

                List<ChiTietHoaDon> dsChiTiet = chiTietHoaDonService.getChiTietByMaHD(hd.getMaHD());
                for (ChiTietHoaDon ct : dsChiTiet) {
                    if (ct == null || ct.getMonAn() == null) {
                        continue;
                    }

                    MonAn monAn = ct.getMonAn();
                    TopMonAnItem item = topMonMap.get(monAn.getMaMon());
                    if (item == null) {
                        topMonMap.put(monAn.getMaMon(), new TopMonAnItem(monAn, ct.getSoLuong()));
                    } else {
                        item.soLuong += ct.getSoLuong();
                    }
                }
            }

            double doanhThuTrungBinh = tongHoaDonDaThanhToan == 0 ? 0 : tongDoanhThu / tongHoaDonDaThanhToan;
            DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");

            lblTongDoanhThu.setText(moneyFormat.format(tongDoanhThu) + " VND");
            lblTongSoHoaDon.setText(String.valueOf(tongHoaDonDaThanhToan));
            lblDoanhThuTrungBinh.setText(moneyFormat.format(doanhThuTrungBinh) + " VND");

            List<TopMonAnItem> topList = new ArrayList<>(topMonMap.values());
            topList.sort(Comparator.comparingInt((TopMonAnItem i) -> i.soLuong).reversed());

            DefaultTableModel model = (DefaultTableModel) tableTopMonAn.getModel();
            model.setRowCount(0);
            int top = 1;
            for (TopMonAnItem item : topList) {
                if (top > 10) {
                    break;
                }
                model.addRow(new Object[] {
                        top,
                        ImageUtil.loadImageIcon(item.imagePath, 48),
                        item.tenMon,
                        item.donGia,
                        item.soLuong
                });
                top++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu thống kê: " + ex.getMessage());
        }
    }

    private void customUI() {
        // 1. Bo góc các Card thống kê
        applyCardStyle(carTongDoanhThu, 30);
        applyCardStyle(cardTongHoaDon, 30);
        applyCardStyle(carDoanhThuTrungBinh, 30);

        // 2. Thiết lập cho Panel chứa bảng
        pnlContainerTable.setBackground(new Color(254, 243, 198));
        applyCardStyle(pnlContainerTable, 40);
        pnlContainerTable.setBorder(new EmptyBorder(25, 25, 25, 25));

        // Đảm bảo tiêu đề bảng
        pnlHeaderTable.setOpaque(false);
        lblTitleTable.setForeground(new Color(148, 134, 111));
        lblTitleTable.setFont(new Font("Segoe UI", Font.BOLD, 20));

        // 3. Tùy chỉnh ScrollPane
        scrTableTopMonAn.setBorder(BorderFactory.createEmptyBorder());
        scrTableTopMonAn.setViewportBorder(null);
        scrTableTopMonAn.setOpaque(false);
        scrTableTopMonAn.getViewport().setOpaque(false);

        // Sửa lỗi mất màu góc trên bên phải
        JPanel corner = new JPanel();
        corner.setBackground(new Color(255, 251, 235));
        scrTableTopMonAn.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corner);

        // 4. Tùy chỉnh Table
        tableTopMonAn.setShowGrid(false);
        tableTopMonAn.setIntercellSpacing(new Dimension(0, 0));
        tableTopMonAn.setRowHeight(45);
        tableTopMonAn.setBorder(BorderFactory.createEmptyBorder());

        // Chiều cao Header
        tableTopMonAn.getTableHeader().setPreferredSize(new Dimension(tableTopMonAn.getTableHeader().getWidth(), 45));

        // Custom Header Renderer
        tableTopMonAn.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                label.setBackground(new Color(255, 251, 235));
                label.setForeground(new Color(148, 134, 111));
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
                return label;
            }
        });

        // Căn giữa toàn bộ các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableTopMonAn.getColumnCount(); i++) {
            tableTopMonAn.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Tùy chỉnh nút bấm
        btnTrangChu.setFocusPainted(false);
        btnInThongKe.setFocusPainted(false);
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

    // Không sửa từ đây xuống dưới
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        carTongDoanhThu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTongDoanhThu = new javax.swing.JLabel();
        cardTongHoaDon = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblTongSoHoaDon = new javax.swing.JLabel();
        carDoanhThuTrungBinh = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblDoanhThuTrungBinh = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        btnInThongKe = new javax.swing.JButton();
        pnlContainerTable = new javax.swing.JPanel();
        pnlHeaderTable = new javax.swing.JPanel();
        lblTitleTable = new javax.swing.JLabel();
        scrTableTopMonAn = new javax.swing.JScrollPane();
        tableTopMonAn = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout(0, 10));

        jPanel1.setBackground(new java.awt.Color(255, 251, 233));
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 150));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0, 60, 0));

        carTongDoanhThu.setBackground(new java.awt.Color(153, 255, 153));
        carTongDoanhThu.setPreferredSize(new java.awt.Dimension(150, 150));
        carTongDoanhThu.setLayout(new java.awt.GridLayout(2, 1, 0, 10));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tổng doanh thu");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        carTongDoanhThu.add(jLabel1);

        lblTongDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTongDoanhThu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTongDoanhThu.setText("0 VND");
        lblTongDoanhThu.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        carTongDoanhThu.add(lblTongDoanhThu);

        jPanel1.add(carTongDoanhThu);

        cardTongHoaDon.setBackground(new java.awt.Color(102, 204, 255));
        cardTongHoaDon.setPreferredSize(new java.awt.Dimension(150, 150));
        cardTongHoaDon.setLayout(new java.awt.GridLayout(2, 1, 0, 10));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tổng số hóa đơn");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        cardTongHoaDon.add(jLabel2);

        lblTongSoHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTongSoHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTongSoHoaDon.setText("0");
        lblTongSoHoaDon.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        cardTongHoaDon.add(lblTongSoHoaDon);

        jPanel1.add(cardTongHoaDon);

        carDoanhThuTrungBinh.setBackground(new java.awt.Color(255, 255, 153));
        carDoanhThuTrungBinh.setPreferredSize(new java.awt.Dimension(150, 150));
        carDoanhThuTrungBinh.setLayout(new java.awt.GridLayout(2, 1, 0, 10));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Doanh thu trung bình");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        carDoanhThuTrungBinh.add(jLabel3);

        lblDoanhThuTrungBinh.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblDoanhThuTrungBinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDoanhThuTrungBinh.setText("0 VND");
        lblDoanhThuTrungBinh.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        carDoanhThuTrungBinh.add(lblDoanhThuTrungBinh);

        jPanel1.add(carDoanhThuTrungBinh);

        add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel6.setBackground(new java.awt.Color(255, 251, 233));
        jPanel6.setPreferredSize(new java.awt.Dimension(1022, 30));
        jPanel6.setLayout(new java.awt.BorderLayout());

        btnTrangChu.setText("Trang chủ");
        btnTrangChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrangChuActionPerformed(evt);
            }
        });
        jPanel6.add(btnTrangChu, java.awt.BorderLayout.WEST);

        btnInThongKe.setText("In thống kê");
        btnInThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInThongKeActionPerformed(evt);
            }
        });
        jPanel6.add(btnInThongKe, java.awt.BorderLayout.EAST);

        add(jPanel6, java.awt.BorderLayout.SOUTH);

        pnlContainerTable.setBackground(new java.awt.Color(255, 251, 233));
        pnlContainerTable.setPreferredSize(new java.awt.Dimension(1022, 200));
        pnlContainerTable.setLayout(new java.awt.BorderLayout(0, 10));

        pnlHeaderTable.setBackground(new java.awt.Color(255, 251, 233));

        lblTitleTable.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitleTable.setText("Top 10 món ăn bán chạy nhất");

        javax.swing.GroupLayout pnlHeaderTableLayout = new javax.swing.GroupLayout(pnlHeaderTable);
        pnlHeaderTable.setLayout(pnlHeaderTableLayout);
        pnlHeaderTableLayout.setHorizontalGroup(
                pnlHeaderTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderTableLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblTitleTable)
                                .addContainerGap(774, Short.MAX_VALUE)));
        pnlHeaderTableLayout.setVerticalGroup(
                pnlHeaderTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlHeaderTableLayout.createSequentialGroup()
                                .addComponent(lblTitleTable)
                                .addGap(0, 0, Short.MAX_VALUE)));

        pnlContainerTable.add(pnlHeaderTable, java.awt.BorderLayout.PAGE_START);

        tableTopMonAn.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Top", "Hình ảnh", "Tên món ăn", "Đơn giá", "Số lượng"
                }) {
            Class<?>[] types = new Class<?>[] {
                    java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.Double.class,
                    java.lang.Integer.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false
            };

            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        scrTableTopMonAn.setViewportView(tableTopMonAn);

        pnlContainerTable.add(scrTableTopMonAn, java.awt.BorderLayout.CENTER);

        add(pnlContainerTable, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTrangChuActionPerformed
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentFrame instanceof MainForm) {
            ((MainForm) parentFrame).goToTrangChuFromPanel();
        }
    }// GEN-LAST:event_btnTrangChuActionPerformed

    private void btnInThongKeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnInThongKeActionPerformed
        JOptionPane.showMessageDialog(this, "Chức năng in thống kê đang được phát triển.");
    }// GEN-LAST:event_btnInThongKeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInThongKe;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JPanel carDoanhThuTrungBinh;
    private javax.swing.JPanel carTongDoanhThu;
    private javax.swing.JPanel cardTongHoaDon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblDoanhThuTrungBinh;
    private javax.swing.JLabel lblTitleTable;
    private javax.swing.JLabel lblTongDoanhThu;
    private javax.swing.JLabel lblTongSoHoaDon;
    private javax.swing.JPanel pnlContainerTable;
    private javax.swing.JPanel pnlHeaderTable;
    private javax.swing.JScrollPane scrTableTopMonAn;
    private javax.swing.JTable tableTopMonAn;
    // End of variables declaration//GEN-END:variables
}
