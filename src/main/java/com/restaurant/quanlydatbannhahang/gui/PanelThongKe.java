package com.restaurant.quanlydatbannhahang.gui;

import com.restaurant.quanlydatbannhahang.entity.ChiTietHoaDon;
import com.restaurant.quanlydatbannhahang.entity.HoaDon;
import com.restaurant.quanlydatbannhahang.entity.MonAn;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiHoaDon;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.service.ChiTietHoaDonService;
import com.restaurant.quanlydatbannhahang.service.HoaDonService;
import com.restaurant.quanlydatbannhahang.util.ImageUtil;
import com.restaurant.quanlydatbannhahang.session.SessionManager;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.EmptyBorder;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class PanelThongKe extends javax.swing.JPanel {

    private HoaDonService hoaDonService;
    private ChiTietHoaDonService chiTietHoaDonService;

    public PanelThongKe() {
        initComponents();
        hoaDonService = new HoaDonService();
        chiTietHoaDonService = new ChiTietHoaDonService();
        customUI();
        setupImageRenderer();
        loadStatistics();
    }

    private void setupImageRenderer() {
        // Custom renderer cho tất cả các cột với background color
        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                // Đặt nền cho tất cả cells
                setOpaque(true);
                setBackground(new Color(255, 251, 235));
                setForeground(Color.BLACK);

                // Xử lý hình ảnh ở cột 1
                if (column == 1 && value instanceof ImageIcon) {
                    JLabel label = new JLabel((ImageIcon) value);
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setVerticalAlignment(JLabel.CENTER);
                    label.setOpaque(true);
                    label.setBackground(new Color(255, 251, 235));
                    return label;
                }

                // Các cột khác
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (comp instanceof JLabel) {
                    JLabel label = (JLabel) comp;
                    label.setHorizontalAlignment(JLabel.CENTER);
                }
                return comp;
            }
        };

        // Áp dụng renderer cho tất cả các cột
        for (int i = 0; i < tableTopMonAn.getColumnCount(); i++) {
            tableTopMonAn.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }

        tableTopMonAn.getColumnModel().getColumn(1).setPreferredWidth(60);
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
        scrTableTopMonAn = new javax.swing.JScrollPane();
        tableTopMonAn = new javax.swing.JTable();
        lblTitleTable = new javax.swing.JLabel();
        cbThoiGianThongKe = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 251, 233));
        setLayout(new java.awt.BorderLayout(10, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

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

        pnlContainerTable.setBackground(new java.awt.Color(255, 251, 233));
        pnlContainerTable.setLayout(new java.awt.BorderLayout());

        tableTopMonAn.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Top", "Hình ảnh", "Tên món ăn", "Đơn giá", "Số lượng"
                }) {
            Class[] types = new Class[] {
                    java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.Double.class,
                    java.lang.Integer.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        scrTableTopMonAn.setViewportView(tableTopMonAn);

        pnlContainerTable.add(scrTableTopMonAn, java.awt.BorderLayout.CENTER);

        lblTitleTable.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitleTable.setText("Top 10 món ăn bán chạy nhất");
        pnlContainerTable.add(lblTitleTable, java.awt.BorderLayout.PAGE_START);

        cbThoiGianThongKe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ngày", "Tuần", "Tháng" }));
        cbThoiGianThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbThoiGianThongKeActionPerformed(evt);
            }
        });

        // Panel chứa combobox
        javax.swing.JPanel pnlTimeFilter = new javax.swing.JPanel();
        pnlTimeFilter.setBackground(new java.awt.Color(255, 251, 233));
        pnlTimeFilter.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        pnlTimeFilter.add(new javax.swing.JLabel("Thống kê:"));
        pnlTimeFilter.add(cbThoiGianThongKe);

        // Container cho header (combobox + cards)
        javax.swing.JPanel pnlHeader = new javax.swing.JPanel();
        pnlHeader.setBackground(new java.awt.Color(255, 251, 233));
        pnlHeader.setLayout(new java.awt.BorderLayout());
        pnlHeader.add(pnlTimeFilter, java.awt.BorderLayout.NORTH);
        pnlHeader.add(jPanel1, java.awt.BorderLayout.CENTER);

        // BorderLayout chính
        add(pnlHeader, java.awt.BorderLayout.NORTH);
        add(pnlContainerTable, java.awt.BorderLayout.CENTER);
        add(jPanel6, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void cbThoiGianThongKeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbThoiGianThongKeActionPerformed
        String selectedOption = (String) cbThoiGianThongKe.getSelectedItem();
        if (selectedOption == null) {
            return;
        }

        try {
            List<HoaDon> dsHoaDon = hoaDonService.getAllHoaDon();
            java.time.LocalDate today = java.time.LocalDate.now();
            java.time.LocalDate startDate = null;

            switch (selectedOption) {
                case "Ngày":
                    startDate = today;
                    break;
                case "Tuần":
                    startDate = today
                            .with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
                    break;
                case "Tháng":
                    startDate = today.withDayOfMonth(1);
                    break;
            }

            // Lọc hóa đơn theo khoảng thời gian
            List<HoaDon> filteredHoaDon = new java.util.ArrayList<>();
            for (HoaDon hd : dsHoaDon) {
                if (hd != null && hd.getNgayTao() != null && !hd.getNgayTao().isBefore(startDate)
                        && hd.getTrangThaiThanhToan() == TrangThaiHoaDon.DA_THANH_TOAN) {
                    filteredHoaDon.add(hd);
                }
            }

            // Recalculate statistics
            double tongDoanhThu = 0;
            int tongHoaDon = 0;
            Map<String, TopMonAnItem> topMonMap = new java.util.HashMap<>();

            for (HoaDon hd : filteredHoaDon) {
                tongHoaDon++;
                tongDoanhThu += hd.getTongThanhToan();

                List<ChiTietHoaDon> dsChiTiet = chiTietHoaDonService.getChiTietByMaHD(hd.getMaHD());
                for (ChiTietHoaDon ct : dsChiTiet) {
                    if (ct != null && ct.getMonAn() != null) {
                        MonAn monAn = ct.getMonAn();
                        TopMonAnItem item = topMonMap.get(monAn.getMaMon());
                        if (item == null) {
                            topMonMap.put(monAn.getMaMon(), new TopMonAnItem(monAn, ct.getSoLuong()));
                        } else {
                            item.soLuong += ct.getSoLuong();
                        }
                    }
                }
            }

            double doanhThuTrungBinh = tongHoaDon == 0 ? 0 : tongDoanhThu / tongHoaDon;
            java.text.DecimalFormat moneyFormat = new java.text.DecimalFormat("#,##0.00");

            lblTongDoanhThu.setText(moneyFormat.format(tongDoanhThu) + " VND");
            lblTongSoHoaDon.setText(String.valueOf(tongHoaDon));
            lblDoanhThuTrungBinh.setText(moneyFormat.format(doanhThuTrungBinh) + " VND");

            // Update table
            List<TopMonAnItem> topList = new java.util.ArrayList<>(topMonMap.values());
            topList.sort(java.util.Comparator.comparingInt((TopMonAnItem i) -> i.soLuong).reversed());

            DefaultTableModel model = (DefaultTableModel) tableTopMonAn.getModel();
            model.setRowCount(0);
            int top = 1;
            for (TopMonAnItem item : topList) {
                if (top > 10)
                    break;
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
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật thống kê: " + ex.getMessage());
        }
    }// GEN-LAST:event_cbThoiGianThongKeActionPerformed

    private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTrangChuActionPerformed
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentFrame instanceof MainForm) {
            ((MainForm) parentFrame).goToTrangChuFromPanel();
        }
    }// GEN-LAST:event_btnTrangChuActionPerformed

    private void btnInThongKeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnInThongKeActionPerformed
        try {
            generateStatisticsReport();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tạo báo cáo: " + ex.getMessage());
            ex.printStackTrace();
        }
    }// GEN-LAST:event_btnInThongKeActionPerformed

    private void generateStatisticsReport() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu báo cáo thống kê PDF");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
        fileChooser.setSelectedFile(new File("ThongKe_" + java.time.LocalDate.now() + ".pdf"));

        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File saveFile = fileChooser.getSelectedFile();
        if (!saveFile.getName().toLowerCase().endsWith(".pdf")) {
            saveFile = new File(saveFile.getParentFile(), saveFile.getName() + ".pdf");
        }

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(org.apache.pdfbox.pdmodel.common.PDRectangle.A4);
            document.addPage(page);

            // Load font Unicode
            PDType0Font fontUnicode = PDType0Font.load(document, new File("fonts/Arial.ttf"));

            try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                float pageWidth = org.apache.pdfbox.pdmodel.common.PDRectangle.A4.getWidth();
                float currentY = 770;
                DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");

                // A. PHẦN TIÊU ĐỀ (HEADER)
                content.setFont(fontUnicode, 18);
                content.beginText();
                content.newLineAtOffset(50, currentY);
                content.showText("NHÀ HÀNG TRẦN LONG");
                content.endText();

                currentY -= 25;
                content.setFont(fontUnicode, 14);
                content.beginText();
                content.newLineAtOffset(50, currentY);
                content.showText("BÁO CÁO THỐNG KÊ DOANH THU");
                content.endText();

                currentY -= 20;
                content.setFont(fontUnicode, 10);
                String thoiGian = (String) cbThoiGianThongKe.getSelectedItem();
                content.beginText();
                content.newLineAtOffset(50, currentY);
                content.showText("Thời gian thống kê: " + getThoiGianDisplay(thoiGian));
                content.endText();

                currentY -= 15;
                java.time.LocalDateTime now = java.time.LocalDateTime.now();
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter
                        .ofPattern("dd/MM/yyyy HH:mm:ss");
                content.beginText();
                content.newLineAtOffset(50, currentY);
                content.showText("Ngày lập báo cáo: " + now.format(formatter));
                content.endText();

                currentY -= 15;
                NhanVien nhanVien = SessionManager.getCurrentNhanVien();
                String tenNhanVien = nhanVien != null ? nhanVien.getHoTen() : "[Chưa xác định]";
                content.beginText();
                content.newLineAtOffset(50, currentY);
                content.showText("Người lập: " + tenNhanVien);
                content.endText();

                // B. PHẦN TÓM TẮT (SUMMARY)
                currentY -= 30;
                content.setFont(fontUnicode, 12);
                content.beginText();
                content.newLineAtOffset(50, currentY);
                content.showText("=== TÓM TẮT THỐNG KÊ ===");
                content.endText();

                currentY -= 18;
                content.setFont(fontUnicode, 11);
                String tongDoanhThu = lblTongDoanhThu.getText();
                content.beginText();
                content.newLineAtOffset(50, currentY);
                content.showText("• Tổng doanh thu: " + tongDoanhThu);
                content.endText();

                currentY -= 15;
                String tongHoaDon = lblTongSoHoaDon.getText();
                content.beginText();
                content.newLineAtOffset(50, currentY);
                content.showText("• Tổng số hóa đơn: " + tongHoaDon);
                content.endText();

                currentY -= 15;
                String doanhThuTrungBinh = lblDoanhThuTrungBinh.getText();
                content.beginText();
                content.newLineAtOffset(50, currentY);
                content.showText("• Doanh thu trung bình: " + doanhThuTrungBinh);
                content.endText();

                // C. PHẦN CHI TIẾT (TOP MÓN ĂN BÁN CHẠY)
                currentY -= 30;
                content.setFont(fontUnicode, 12);
                content.beginText();
                content.newLineAtOffset(50, currentY);
                content.showText("=== TOP 10 MÓN ĂN BÁN CHẠY NHẤT ===");
                content.endText();

                currentY -= 20;
                // Header bảng
                content.setFont(fontUnicode, 10);
                content.beginText();
                content.newLineAtOffset(50, currentY);
                content.showText("STT");
                content.newLineAtOffset(40, 0);
                content.showText("Tên món ăn");
                content.newLineAtOffset(150, 0);
                content.showText("Đơn giá");
                content.newLineAtOffset(80, 0);
                content.showText("Số lượng");
                content.newLineAtOffset(70, 0);
                content.showText("Thành tiền");
                content.endText();

                // Vẽ đường kẻ
                content.setLineWidth(1f);
                currentY -= 3;
                content.moveTo(50, currentY);
                content.lineTo(pageWidth - 50, currentY);
                content.stroke();

                // Dữ liệu bảng
                currentY -= 15;
                content.setFont(fontUnicode, 9);
                DefaultTableModel model = (DefaultTableModel) tableTopMonAn.getModel();

                for (int i = 0; i < model.getRowCount(); i++) {
                    Object stt = model.getValueAt(i, 0);
                    Object tenMon = model.getValueAt(i, 2);
                    Object donGia = model.getValueAt(i, 3);
                    Object soLuong = model.getValueAt(i, 4);

                    double giaDbl = Double.parseDouble(donGia.toString());
                    int soLuongInt = Integer.parseInt(soLuong.toString());
                    double thanhTien = giaDbl * soLuongInt;

                    content.beginText();
                    content.newLineAtOffset(50, currentY);
                    content.showText(stt.toString());
                    content.newLineAtOffset(40, 0);
                    content.showText(tenMon.toString());
                    content.newLineAtOffset(150, 0);
                    content.showText(moneyFormat.format(giaDbl));
                    content.newLineAtOffset(80, 0);
                    content.showText(soLuongInt + "");
                    content.newLineAtOffset(70, 0);
                    content.showText(moneyFormat.format(thanhTien));
                    content.endText();

                    currentY -= 12;
                }

                // D. PHẦN KÝ TÊN (FOOTER)
                currentY -= 20;
                content.setFont(fontUnicode, 10);
                content.beginText();
                content.newLineAtOffset(50, currentY);
                content.showText("Người lập biểu");
                content.newLineAtOffset(200, 0);
                content.showText("Quản lý/Chủ nhà hàng");
                content.endText();

                currentY -= 30;
                content.beginText();
                content.newLineAtOffset(50, currentY);
                content.showText("(Ký và ghi rõ họ tên)");
                content.newLineAtOffset(200, 0);
                content.showText("(Ký và ghi rõ họ tên)");
                content.endText();
            }

            document.save(saveFile);
            JOptionPane.showMessageDialog(this, "Báo cáo thống kê đã được lưu tại:\n" + saveFile.getAbsolutePath());
        }
    }

    private String getThoiGianDisplay(String option) {
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");

        switch (option) {
            case "Ngày":
                return "Ngày " + today.format(formatter);
            case "Tuần":
                java.time.LocalDate monday = today
                        .with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
                java.time.LocalDate sunday = today
                        .with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));
                return "Tuần từ " + monday.format(formatter) + " đến " + sunday.format(formatter);
            case "Tháng":
                return "Tháng " + today.getMonthValue() + "/" + today.getYear();
            default:
                return "Không xác định";
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInThongKe;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JPanel carDoanhThuTrungBinh;
    private javax.swing.JPanel carTongDoanhThu;
    private javax.swing.JPanel cardTongHoaDon;
    private javax.swing.JComboBox<String> cbThoiGianThongKe;
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
    private javax.swing.JScrollPane scrTableTopMonAn;
    private javax.swing.JTable tableTopMonAn;
    // End of variables declaration//GEN-END:variables
}
