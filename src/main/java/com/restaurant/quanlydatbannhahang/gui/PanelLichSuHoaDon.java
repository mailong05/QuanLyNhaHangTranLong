package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEnumLoader;
import com.restaurant.quanlydatbannhahang.service.HoaDonService;
import com.restaurant.quanlydatbannhahang.entity.HoaDon;
import com.restaurant.quanlydatbannhahang.entity.KhuVuc;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiHoaDon;

import java.time.LocalDate;
import java.util.List;
import java.text.DecimalFormat;

public class PanelLichSuHoaDon extends javax.swing.JPanel {
    private HoaDonService hoaDonService;
    private List<HoaDon> allHoaDon;

    public PanelLichSuHoaDon() {
        initComponents();
        hoaDonService = new HoaDonService();
        customUI();
        loadDataToComboBoxes();
        loadDataToTable();

    }

    private void loadDataToComboBoxes() {
        // TODO Auto-generated method stub
        try {
            // Save listeners
            ActionListener[] trangThaiListeners = cbFilterTrangThai.getActionListeners();

            // Remove listeners

            for (ActionListener listener : trangThaiListeners) {
                cbFilterTrangThai.removeActionListener(listener);
            }

            // Load TrangThaiBan
            cbFilterTrangThai.removeAllItems();
            cbFilterTrangThai.addItem("Trạng thái");
            for (TrangThaiHoaDon trangThai : TrangThaiHoaDon
                    .values()) {
                cbFilterTrangThai.addItem(trangThai.getDisplayName());
            }

            // Re-add listeners

            for (ActionListener listener : trangThaiListeners) {
                cbFilterTrangThai.addActionListener(listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu filter: " + e.getMessage());
        }
    }

    private void customUI() {
        // Placeholder cho txtTimKiem
        setupPlaceholder(txtTimKiem, "Nhập mã hóa đơn");

        // Load enum trạng thái hóa đơn lên ComboBox
        ComboBoxEnumLoader.loadTrangThaiHoaDonToComboBox(cbFilterTrangThai);

        // 1. Tùy chỉnh thanh tìm kiếm và ComboBox
        cbFilterTrangThai.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTimKiem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTimKiem.setFocusPainted(false);

        // 2. Tùy chỉnh ScrollPane
        scrTableLichSuHoaDon.setBorder(BorderFactory.createLineBorder(new Color(220, 210, 190), 1));
        scrTableLichSuHoaDon.setOpaque(false);
        scrTableLichSuHoaDon.getViewport().setOpaque(false);

        // Khử góc trắng ScrollBar
        JPanel corner = new JPanel();
        corner.setBackground(new Color(255, 251, 233));
        scrTableLichSuHoaDon.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corner);

        // 3. Tùy chỉnh Bảng (Table)
        tableLichSuHoaDon.setShowGrid(false);
        tableLichSuHoaDon.setIntercellSpacing(new Dimension(0, 0));
        tableLichSuHoaDon.setRowHeight(40);
        tableLichSuHoaDon.setSelectionBackground(new Color(245, 240, 220));
        tableLichSuHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Header Table
        tableLichSuHoaDon.getTableHeader()
                .setPreferredSize(new Dimension(tableLichSuHoaDon.getTableHeader().getWidth(), 45));
        tableLichSuHoaDon.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                label.setBackground(new Color(255, 251, 233));
                label.setForeground(new Color(148, 134, 111));
                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 210, 190)));
                return label;
            }
        });

        // Căn giữa nội dung cho tất cả các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableLichSuHoaDon.getColumnCount(); i++) {
            tableLichSuHoaDon.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set giá trị mặc định cho DatePicker (ngày hôm nay)
        if (dpNgayTao != null) {
            dpNgayTao.setDate(LocalDate.now());
            dpNgayTao.addDateChangeListener(event -> loadFilteredData());
        }

        // 4. Click ngoài bảng thì clear selection và refresh lại dữ liệu
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.getSource() != tableLichSuHoaDon && !isMouseOverTable(evt)) {
                    tableLichSuHoaDon.clearSelection();
                    refreshData();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        cbFilterTrangThai = new javax.swing.JComboBox<>();
        dpNgayTao = new com.github.lgooddatepicker.components.DatePicker();
        jPanel2 = new javax.swing.JPanel();
        scrTableLichSuHoaDon = new javax.swing.JScrollPane();
        tableLichSuHoaDon = new javax.swing.JTable();
        btnTrangChu = new javax.swing.JButton();
        btnXoaTrang = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 10, 0));
        setLayout(new java.awt.BorderLayout(0, 10));

        jPanel1.setBackground(new java.awt.Color(255, 251, 233));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.X_AXIS));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 60, 10, 60));

        cbFilterTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        cbFilterTrangThai.setPreferredSize(new java.awt.Dimension(150, 35));
        cbFilterTrangThai.setMaximumSize(new java.awt.Dimension(150, 35));
        cbFilterTrangThai.addActionListener(this::cbFilterTrangThaiActionPerformed);
        jPanel1.add(cbFilterTrangThai);

        jPanel1.add(Box.createHorizontalStrut(20));

        dpNgayTao.setPreferredSize(new java.awt.Dimension(180, 35));
        dpNgayTao.setMaximumSize(new java.awt.Dimension(180, 35));
        jPanel1.add(dpNgayTao);

        jPanel1.add(Box.createHorizontalGlue());

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.setPreferredSize(new java.awt.Dimension(300, 35));
        txtTimKiem.setMaximumSize(new java.awt.Dimension(300, 35));
        txtTimKiem.addActionListener(this::txtTimKiemActionPerformed);
        jPanel1.add(txtTimKiem);

        jPanel1.add(Box.createHorizontalStrut(10));

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.setPreferredSize(new java.awt.Dimension(100, 35));
        btnTimKiem.setMaximumSize(new java.awt.Dimension(100, 35));
        btnTimKiem.addActionListener(this::btnTimKiemActionPerformed);
        jPanel1.add(btnTimKiem);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 251, 233));
        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 60, 0, 60));

        tableLichSuHoaDon.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Mã hóa đơn", "Mã bàn", "Mã khuyến mãi", "Mã thuế", "Ngày tạo", "Giờ vào", "Giờ ra",
                        "Tổng tiền gốc", "Tiền giảm giá", "Tổng thanh toán", "Phương thức thanh toán",
                        "Trạng thái thanh toán"
                }) {
            Class[] types = new Class[] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                    java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tableLichSuHoaDon.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        scrTableLichSuHoaDon.setViewportView(tableLichSuHoaDon);
        if (tableLichSuHoaDon.getColumnModel().getColumnCount() > 0) {
            tableLichSuHoaDon.getColumnModel().getColumn(0).setPreferredWidth(150);
            tableLichSuHoaDon.getColumnModel().getColumn(1).setPreferredWidth(150);
            tableLichSuHoaDon.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableLichSuHoaDon.getColumnModel().getColumn(3).setPreferredWidth(150);
            tableLichSuHoaDon.getColumnModel().getColumn(4).setPreferredWidth(150);
            tableLichSuHoaDon.getColumnModel().getColumn(5).setPreferredWidth(150);
            tableLichSuHoaDon.getColumnModel().getColumn(6).setPreferredWidth(150);
            tableLichSuHoaDon.getColumnModel().getColumn(7).setPreferredWidth(200);
            tableLichSuHoaDon.getColumnModel().getColumn(8).setPreferredWidth(200);
            tableLichSuHoaDon.getColumnModel().getColumn(9).setPreferredWidth(200);
            tableLichSuHoaDon.getColumnModel().getColumn(10).setPreferredWidth(200);
            tableLichSuHoaDon.getColumnModel().getColumn(11).setPreferredWidth(200);
        }

        jPanel2.add(scrTableLichSuHoaDon, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);

        javax.swing.JPanel pnlButton = new javax.swing.JPanel();
        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setLayout(new java.awt.BorderLayout(0, 5));
        pnlButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 60, 10, 60));

        btnTrangChu.setText("Trang chủ");
        btnTrangChu.addActionListener(this::btnTrangChuActionPerformed);
        pnlButton.add(btnTrangChu, java.awt.BorderLayout.WEST);

        btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.addActionListener(this::btnXoaTrangActionPerformed);
        pnlButton.add(btnXoaTrang, java.awt.BorderLayout.EAST);

        add(pnlButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaTrangActionPerformed
        refreshData();
    }// GEN-LAST:event_btnXoaTrangActionPerformed

    private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTrangChuActionPerformed
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentFrame instanceof MainForm) {
            ((MainForm) parentFrame).goToTrangChuFromPanel();
        }
    }// GEN-LAST:event_btnTrangChuActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        searchByText();
    }// GEN-LAST:event_btnTimKiemActionPerformed

    private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
        loadFilteredData();
    }// GEN-LAST:event_cbFilterTrangThaiActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTimKiemActionPerformed
        searchByText();
    }// GEN-LAST:event_txtTimKiemActionPerformed

    private void loadDataToTable() {
        loadFilteredData();
    }

    private void loadFilteredData() {
        try {
            allHoaDon = hoaDonService.getAllHoaDon();
            String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();

            DefaultTableModel model = (DefaultTableModel) tableLichSuHoaDon.getModel();
            model.setRowCount(0);

            for (HoaDon hd : allHoaDon) {
                // Apply TrangThai filter
                if (selectedTrangThai != null && !selectedTrangThai.isEmpty()) {
                    if (hd.getTrangThaiThanhToan() == null
                            || !hd.getTrangThaiThanhToan().getDisplayName().equals(selectedTrangThai)) {
                        continue;
                    }
                }

                if (dpNgayTao != null && dpNgayTao.getDate() != null) {
                    LocalDate selectedDate = dpNgayTao.getDate();
                    if (hd.getNgayTao() == null || !hd.getNgayTao().isEqual(selectedDate)) {
                        continue;
                    }
                }

                model.addRow(new Object[] {
                        hd.getMaHD(),
                        hd.getBan() != null ? hd.getBan().getMaBan() : "",
                        hd.getKhuyenMai() != null ? hd.getKhuyenMai().getMaKM() : "",
                        hd.getThue() != null ? hd.getThue().getMaThue() : "",
                        hd.getNgayTao(),
                        hd.getGioVao(),
                        hd.getGioRa(),
                        hd.getTongTienGoc(),
                        hd.getTienGiamGia(),
                        hd.getTongThanhToan(),
                        hd.getPhuongThucTT() != null ? hd.getPhuongThucTT().getDisplayName() : "",
                        hd.getTrangThaiThanhToan() != null ? hd.getTrangThaiThanhToan().getDisplayName() : ""
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu: " + e.getMessage());
        }
    }

    private void searchByText() {
        try {
            String searchText = txtTimKiem.getText().trim().toLowerCase();
            String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();

            DefaultTableModel model = (DefaultTableModel) tableLichSuHoaDon.getModel();
            model.setRowCount(0);

            for (HoaDon hd : allHoaDon) {
                // Apply TrangThai filter
                if (selectedTrangThai != null && !selectedTrangThai.isEmpty()) {
                    if (hd.getTrangThaiThanhToan() == null
                            || !hd.getTrangThaiThanhToan().getDisplayName().equals(selectedTrangThai)) {
                        continue;
                    }
                }

                if (dpNgayTao != null && dpNgayTao.getDate() != null) {
                    LocalDate selectedDate = dpNgayTao.getDate();
                    if (hd.getNgayTao() == null || !hd.getNgayTao().isEqual(selectedDate)) {
                        continue;
                    }
                }

                // Apply text filter (search by invoice ID)
                if (!searchText.isEmpty()) {
                    String maHD = hd.getMaHD() != null ? hd.getMaHD().toLowerCase() : "";
                    if (!maHD.contains(searchText)) {
                        continue;
                    }
                }

                model.addRow(new Object[] {
                        hd.getMaHD(),
                        hd.getBan() != null ? hd.getBan().getMaBan() : "",
                        hd.getKhuyenMai() != null ? hd.getKhuyenMai().getMaKM() : "",
                        hd.getThue() != null ? hd.getThue().getMaThue() : "",
                        hd.getNgayTao(),
                        hd.getGioVao(),
                        hd.getGioRa(),
                        hd.getTongTienGoc(),
                        hd.getTienGiamGia(),
                        hd.getTongThanhToan(),
                        hd.getPhuongThucTT() != null ? hd.getPhuongThucTT().getDisplayName() : "",
                        hd.getTrangThaiThanhToan() != null ? hd.getTrangThaiThanhToan().getDisplayName() : ""
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm dữ liệu: " + e.getMessage());
        }
    }

    public void refreshData() {
        cbFilterTrangThai.setSelectedIndex(0);
        if (dpNgayTao != null) {
            dpNgayTao.setDate(LocalDate.now());
        }
        loadDataToTable();
        setupPlaceholder(txtTimKiem, "Nhập mã hóa đơn");
    }

    /**
     * Tao placeholder cho TextField
     * Khi focus vao, placeholder bien mat
     * Khi focus out va trong, placeholder xuat hien lai
     */
    private void setupPlaceholder(JTextField textField, String placeholder) {
        Color placeholderColor = new Color(153, 153, 153);
        Color textColor = new Color(0, 0, 0);

        // Set text mac dinh va mau
        textField.setText(placeholder);
        textField.setForeground(placeholderColor);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                // Khi focus vao, neu la placeholder thi xoa
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(textColor);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                // Khi focus out, neu trong thi hien thi placeholder
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(placeholderColor);
                }
            }
        });
    }

    private boolean isMouseOverTable(java.awt.event.MouseEvent evt) {
        java.awt.Point p = evt.getPoint();
        java.awt.Point tablePoint = SwingUtilities.convertPoint(this, p, tableLichSuHoaDon);
        return tableLichSuHoaDon.getBounds().contains(tablePoint);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cbFilterTrangThai;
    private com.github.lgooddatepicker.components.DatePicker dpNgayTao;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane scrTableLichSuHoaDon;
    private javax.swing.JTable tableLichSuHoaDon;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
