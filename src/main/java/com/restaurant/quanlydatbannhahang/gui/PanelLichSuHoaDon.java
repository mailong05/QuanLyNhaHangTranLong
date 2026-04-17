package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEnumLoader;
import java.time.LocalDate;

public class PanelLichSuHoaDon extends javax.swing.JPanel {

    public PanelLichSuHoaDon() {
        initComponents();
        customUI();
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
        }

        // 4. Gắn sự kiện quay về Trang Chủ
        MainForm.attachGoHomeListener(btnTrangChu, this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 251, 233));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.setPreferredSize(new java.awt.Dimension(64, 35));
        txtTimKiem.addActionListener(this::txtTimKiemActionPerformed);
        jPanel1.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(591, 0, 390, -1));

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.setPreferredSize(new java.awt.Dimension(100, 35));
        btnTimKiem.addActionListener(this::btnTimKiemActionPerformed);
        jPanel1.add(btnTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(996, 0, -1, -1));

        cbFilterTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đã thanh toán", "Chưa thanh toán", "Đã Hủy", " " }));
        cbFilterTrangThai.setPreferredSize(new java.awt.Dimension(150, 22));
        cbFilterTrangThai.addActionListener(this::cbFilterTrangThaiActionPerformed);
        jPanel1.add(cbFilterTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 30));
        jPanel1.add(dpNgayTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, -1, 30));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 251, 233));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableLichSuHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã bàn", "Mã khuyến mãi", "Mã thuế", "Ngày tạo", "Giờ vào", "Giờ ra", "Tổng tiền gốc", "Tiền giảm giá", "Tổng thanh toán", "Phương thức thanh toán", "Trạng thái thanh toán"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
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

        jPanel2.add(scrTableLichSuHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1096, 500));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 1096, 500));

        btnTrangChu.setText("Trang chủ");
        btnTrangChu.addActionListener(this::btnTrangChuActionPerformed);
        add(btnTrangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 583, 90, 30));

        btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.addActionListener(this::btnXoaTrangActionPerformed);
        add(btnXoaTrang, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 580, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaTrangActionPerformed

    private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTrangChuActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnTrangChuActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnTimKiemActionPerformed

    private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_cbFilterTrangThaiActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtTimKiemActionPerformed

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
