/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.entity.LoaiMonAn;
import com.restaurant.quanlydatbannhahang.entity.MonAn;
import com.restaurant.quanlydatbannhahang.service.MonAnService;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEnumLoader;
import com.restaurant.quanlydatbannhahang.util.ImageUtil;

/**
 *
 * @author nguye
 */
public class PanelDatMon extends javax.swing.JPanel {

    private static final int TABLE_IMAGE_SIZE = 72;
    private static final int TABLE_IMAGE_ROW_HEIGHT = 84;
    private static final int TABLE_IMAGE_VERTICAL_PADDING = 4;

    private MonAnService monAnService;
    private List<MonAn> allMonAn;

    /**
     * Creates new form PanelDatMon
     */
    public PanelDatMon() {
        initComponents();
        monAnService = new MonAnService();
        customUI();
        loadDataToComboBoxes();
        loadDataToTable();
    }

    private void customUI() {
        // Placeholder cho txtTimKiem
        setupPlaceholder(txtTimKiem, "Nhập tên món ăn");

        // Thêm action listeners cho các button
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnQuayLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLaiActionPerformed(evt);
            }
        });
    }

    private void loadDataToComboBoxes() {
        try {
            ActionListener[] loaiMonListeners = cbFilterLoaiMonAn.getActionListeners();

            for (ActionListener listener : loaiMonListeners) {
                cbFilterLoaiMonAn.removeActionListener(listener);
            }

            cbFilterLoaiMonAn.removeAllItems();
            cbFilterLoaiMonAn.addItem("Loại món ăn");
            ComboBoxEnumLoader.loadLoaiMonAnToComboBox(cbFilterLoaiMonAn);

            for (ActionListener listener : loaiMonListeners) {
                cbFilterLoaiMonAn.addActionListener(listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu filter: " + e.getMessage());
        }
    }

    private List<MonAn> ensureMonAnDataLoaded() {
        if (allMonAn == null) {
            allMonAn = monAnService.getAllMonAn();
        }
        return allMonAn;
    }

    private void loadDataToTable() {
        try {
            List<MonAn> monAnList = ensureMonAnDataLoaded();
            String selectedLoai = (String) cbFilterLoaiMonAn.getSelectedItem();
            String searchText = txtTimKiem.getText().trim().toLowerCase();

            DefaultTableModel model = (DefaultTableModel) tableDanhSachMonAn.getModel();
            model.setRowCount(0);

            for (MonAn monAn : monAnList) {
                if (selectedLoai != null && !selectedLoai.isEmpty() && !selectedLoai.equals("Loại món ăn")) {
                    if (monAn.getTenLoai() == null || !monAn.getTenLoai().getDisplayName().equals(selectedLoai)) {
                        continue;
                    }
                }

                if (!searchText.isEmpty()) {
                    String maMon = monAn.getMaMon() != null ? monAn.getMaMon().toLowerCase() : "";
                    String tenMon = monAn.getTenMon() != null ? monAn.getTenMon().toLowerCase() : "";
                    if (!maMon.contains(searchText) && !tenMon.contains(searchText)) {
                        continue;
                    }
                }

                model.addRow(new Object[] {
                        ImageUtil.loadImageIcon(monAn.getUrlHinhAnh(), TABLE_IMAGE_SIZE),
                        monAn.getMaMon(),
                        monAn.getTenMon(),
                        monAn.getDonViTinh(),
                        monAn.getDonGia()
                });
            }

            centerTableColumns(tableDanhSachMonAn);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu món ăn: " + e.getMessage());
        }
    }

    private void searchAndFilter() {
        loadDataToTable();
    }

    private void filterTable() {
        loadDataToTable();
    }

    private void centerTableColumns(JTable table) {
        ImageRenderer imageRenderer = new ImageRenderer();
        table.getColumnModel().getColumn(0).setCellRenderer(imageRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.setRowHeight(TABLE_IMAGE_ROW_HEIGHT);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private static class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof ImageIcon) {
                JLabel label = new JLabel((ImageIcon) value);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                label.setOpaque(true);
                label.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                label.setBorder(BorderFactory.createEmptyBorder(TABLE_IMAGE_VERTICAL_PADDING, 0,
                        TABLE_IMAGE_VERTICAL_PADDING, 0));
                return label;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblPhieuGoiMon = new javax.swing.JLabel();
        scrTablePhieuGoiMon = new javax.swing.JScrollPane();
        tablePhieuGoiMon = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblTongTienTamTinh = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        cbFilterLoaiMonAn = new javax.swing.JComboBox<>();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        scrDanhSachMonAn = new javax.swing.JScrollPane();
        tableDanhSachMonAn = new javax.swing.JTable();
        btnDoiBan = new javax.swing.JButton();
        btnChonMon = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btnThanhToan = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnQuayLai = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 251, 233));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 251, 233));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 251, 233));

        lblPhieuGoiMon.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblPhieuGoiMon.setText("Phiếu gọi món");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(lblPhieuGoiMon)
                                .addContainerGap(384, Short.MAX_VALUE)));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblPhieuGoiMon)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 509, -1));

        tablePhieuGoiMon.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Tên món ăn", "Số lượng", "Đơn giá", "Thành tiền"
                }) {
            Class<?>[] types = new Class<?>[] {
                    java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false
            };

            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        scrTablePhieuGoiMon.setViewportView(tablePhieuGoiMon);

        jPanel2.add(scrTablePhieuGoiMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 510, 480));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tổng tiền tạm tính:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, -1, -1));

        lblTongTienTamTinh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTongTienTamTinh.setForeground(new java.awt.Color(255, 0, 0));
        lblTongTienTamTinh.setText("0 VND");
        jPanel2.add(lblTongTienTamTinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 0, 530, 590));

        jPanel3.setBackground(new java.awt.Color(255, 251, 233));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 251, 233));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 510, Short.MAX_VALUE));
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE));

        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 510, 0));

        jPanel4.setBackground(new java.awt.Color(255, 251, 233));
        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 0, 5));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbFilterLoaiMonAn.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbFilterLoaiMonAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterLoaiMonAnActionPerformed(evt);
            }
        });
        jPanel4.add(cbFilterLoaiMonAn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 130, 30));

        txtTimKiem.setPreferredSize(new java.awt.Dimension(320, 22));
        jPanel4.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 190, 30));

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        jPanel4.add(btnTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 0, 90, 30));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 580, 40));

        scrDanhSachMonAn.setBackground(new java.awt.Color(255, 251, 233));

        tableDanhSachMonAn.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Hình ảnh", "Mã món ăn", "Tên món ăn", "Đơn vị tính", "Đơn giá"
                }) {
            Class<?>[] types = new Class<?>[] {
                    java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                    java.lang.Double.class
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
        scrDanhSachMonAn.setViewportView(tableDanhSachMonAn);

        jPanel3.add(scrDanhSachMonAn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 560, 480));

        btnDoiBan.setText("Đổi bàn");
        btnDoiBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiBanActionPerformed(evt);
            }
        });
        jPanel3.add(btnDoiBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 90, 30));

        btnChonMon.setText("Chọn");
        btnChonMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonMonActionPerformed(evt);
            }
        });
        jPanel3.add(btnChonMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 550, 90, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 600, 590));

        jPanel7.setBackground(new java.awt.Color(255, 251, 233));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1110, Short.MAX_VALUE));
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 60, Short.MAX_VALUE));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 520, 1110, 60));

        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });
        jPanel1.add(btnThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 620, 134, 33));

        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });
        jPanel1.add(btnLuu, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 620, 89, 33));

        btnQuayLai.setText("Quay lại");
        btnQuayLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLaiActionPerformed(evt);
            }
        });
        jPanel1.add(btnQuayLai, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 620, 97, 36));

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        searchAndFilter();
    }// GEN-LAST:event_btnTimKiemActionPerformed

    private void cbFilterLoaiMonAnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterLoaiMonAnActionPerformed
        filterTable();
    }// GEN-LAST:event_cbFilterLoaiMonAnActionPerformed

    private void btnDoiBanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDoiBanActionPerformed
        JOptionPane.showMessageDialog(this, "Chức năng đổi bàn đang được phát triển.");
    }// GEN-LAST:event_btnDoiBanActionPerformed

    private void btnChonMonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChonMonActionPerformed
        JOptionPane.showMessageDialog(this, "Chức năng chọn món đang được phát triển.");
    }// GEN-LAST:event_btnChonMonActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLuuActionPerformed
        JOptionPane.showMessageDialog(this, "Chức năng lưu đang được phát triển.");
    }// GEN-LAST:event_btnLuuActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentFrame instanceof MainForm) {
            ((MainForm) parentFrame).openPanelLapHoaDon();
        }
    }// GEN-LAST:event_btnThanhToanActionPerformed

    private void btnQuayLaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnQuayLaiActionPerformed
        // TODO add your handling code here:
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentFrame instanceof MainForm) {
            ((MainForm) parentFrame).goBackToPreviousPanel();
        }
    }// GEN-LAST:event_btnQuayLaiActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonMon;
    private javax.swing.JButton btnDoiBan;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnQuayLai;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cbFilterLoaiMonAn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lblPhieuGoiMon;
    private javax.swing.JLabel lblTongTienTamTinh;
    private javax.swing.JScrollPane scrDanhSachMonAn;
    private javax.swing.JScrollPane scrTablePhieuGoiMon;
    private javax.swing.JTable tableDanhSachMonAn;
    private javax.swing.JTable tablePhieuGoiMon;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
