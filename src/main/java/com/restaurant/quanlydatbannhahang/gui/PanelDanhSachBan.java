package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import javax.swing.*;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.BanService;
import com.restaurant.quanlydatbannhahang.service.KhuVucService;

import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.entity.KhuVuc;

import java.util.List;

public class PanelDanhSachBan extends javax.swing.JPanel {
    private BanService banService;
    private List<Ban> allBans;
    private KhuVucService khuVucService;

    public PanelDanhSachBan() {
        initComponents();
        banService = new BanService();
        khuVucService = new KhuVucService();
        customUI();
        loadDataToComboBoxes();
        loadDataToTable();
    }

    private void customUI() {
        // Placeholder cho txtTimKiem
        setupPlaceholder(txtTimKiem, "Nhập mã bàn hoặc mã khu vực");

        // Gan su kien quay ve Trang Chu
        MainForm.attachGoHomeListener(btnTrangChu, this);
    }

    private void loadDataToComboBoxes() {
        try {
            // Save listeners
            java.awt.event.ActionListener[] khuVucListeners = cbFilterKhuVuc.getActionListeners();
            java.awt.event.ActionListener[] trangThaiListeners = cbFilterTrangThai.getActionListeners();

            // Remove listeners
            for (java.awt.event.ActionListener listener : khuVucListeners) {
                cbFilterKhuVuc.removeActionListener(listener);
            }
            for (java.awt.event.ActionListener listener : trangThaiListeners) {
                cbFilterTrangThai.removeActionListener(listener);
            }

            // Load KhuVuc tu database
            cbFilterKhuVuc.removeAllItems();
            cbFilterKhuVuc.addItem("Khu vực");
            List<KhuVuc> dsKhuVuc = khuVucService.getAllKhuVuc();
            for (KhuVuc kv : dsKhuVuc) {
                cbFilterKhuVuc.addItem(kv.getMaKhuVuc());
            }

            // Load TrangThaiBan tu enum
            cbFilterTrangThai.removeAllItems();
            cbFilterTrangThai.addItem("Trạng thái");
            for (com.restaurant.quanlydatbannhahang.entity.TrangThaiBan trangThai : com.restaurant.quanlydatbannhahang.entity.TrangThaiBan
                    .values()) {
                cbFilterTrangThai.addItem(trangThai.getDisplayName());
            }

            // Re-add listeners
            for (java.awt.event.ActionListener listener : khuVucListeners) {
                cbFilterKhuVuc.addActionListener(listener);
            }
            for (java.awt.event.ActionListener listener : trangThaiListeners) {
                cbFilterTrangThai.addActionListener(listener);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Loi load du lieu filter: " + e.getMessage());
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

    private void applyCardStyle(JPanel panel, int radius) {
        panel.setOpaque(false);
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

    private void resetPlaceholder(JTextField textField, String placeholder) {
        Color placeholderColor = new Color(153, 153, 153);
        textField.setText(placeholder);
        textField.setForeground(placeholderColor);
    }

    private void loadDataToTable() {
        try {
            allBans = banService.getAllBan();
            DefaultTableModel model = (DefaultTableModel) tableBan.getModel();
            model.setRowCount(0);

            for (Ban ban : allBans) {
                model.addRow(new Object[] {
                        ban.getMaBan(),
                        ban.getSoGhe(),
                        ban.getViTri(),
                        ban.getKhuVuc() != null ? ban.getKhuVuc().getMaKhuVuc() : "",
                        ban.getTrangThai() != null ? ban.getTrangThai().getDisplayName() : ""
                });
            }
            centerTableColumns(tableBan);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void centerTableColumns(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    // Từ đây không chỉnh sửa bên dưới
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        pnlThongTinBan = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        cbFilterKhuVuc = new javax.swing.JComboBox<>();
        cbFilterTrangThai = new javax.swing.JComboBox<>();
        scrTableBan = new javax.swing.JScrollPane();
        tableBan = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        btnXoaTrang = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout(0, 10));

        pnlHeader.setOpaque(false);
        pnlHeader.setLayout(new java.awt.BorderLayout(0, 15));

        pnlThongTinBan.setBackground(new java.awt.Color(255, 251, 233));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.setPreferredSize(new java.awt.Dimension(64, 35));
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        cbFilterKhuVuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Khu vực", "A", "B", "C" }));
        cbFilterKhuVuc.setPreferredSize(new java.awt.Dimension(72, 35));
        cbFilterKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterKhuVucActionPerformed(evt);
            }
        });

        cbFilterTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Trạng thái", "Trống", "Đang sử dụng", "Đang chờ", " " }));
        cbFilterTrangThai.setPreferredSize(new java.awt.Dimension(72, 35));
        cbFilterTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterTrangThaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinBanLayout = new javax.swing.GroupLayout(pnlThongTinBan);
        pnlThongTinBan.setLayout(pnlThongTinBanLayout);
        pnlThongTinBanLayout.setHorizontalGroup(
                pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                .addComponent(cbFilterKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 132,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 132,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 348,
                                        Short.MAX_VALUE)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 280,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 134,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)));
        pnlThongTinBanLayout.setVerticalGroup(
                pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                .addGroup(pnlThongTinBanLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinBanLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(cbFilterKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addGroup(pnlThongTinBanLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btnTimKiem,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtTimKiem,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap()));

        pnlHeader.add(pnlThongTinBan, java.awt.BorderLayout.PAGE_END);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        tableBan.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Mã bàn", "Số ghế", "Vị trí", "Mã khu vực", "Trạng thái"
                }) {
            Class[] types = new Class[] {
                    java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class,
                    java.lang.String.class
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
        tableBan.setRowHeight(35);
        scrTableBan.setViewportView(tableBan);

        add(scrTableBan, java.awt.BorderLayout.CENTER);

        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setPreferredSize(new java.awt.Dimension(100, 27));
        pnlButton.setLayout(new java.awt.BorderLayout(0, 5));

        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTrangChu.setText("Trang Chủ");
        btnTrangChu.setPreferredSize(new java.awt.Dimension(100, 27));
        pnlButton.add(btnTrangChu, java.awt.BorderLayout.WEST);

        btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });
        pnlButton.add(btnXoaTrang, java.awt.BorderLayout.EAST);

        add(pnlButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaTrangActionPerformed
        // TODO add your handling code here:
        refreshData();
    }// GEN-LAST:event_btnXoaTrangActionPerformed

    private void cbFilterKhuVucActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterKhuVucActionPerformed
        filterTable();
    }// GEN-LAST:event_cbFilterKhuVucActionPerformed

    private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
        filterTable();
    }// GEN-LAST:event_cbFilterTrangThaiActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        searchByText();
    }// GEN-LAST:event_btnTimKiemActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTimKiemActionPerformed
        searchByText();
    }// GEN-LAST:event_txtTimKiemActionPerformed

    private void filterByComboBoxes() {
        DefaultTableModel model = (DefaultTableModel) tableBan.getModel();
        model.setRowCount(0);
        String selectedKhuVuc = (String) cbFilterKhuVuc.getSelectedItem();
        String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();

        for (Ban ban : allBans) {
            // Check KhuVuc filter
            if (selectedKhuVuc != null && !selectedKhuVuc.equals("Khu vực")) {
                if (ban.getKhuVuc() == null || !ban.getKhuVuc().getMaKhuVuc().equals(selectedKhuVuc)) {
                    continue;
                }
            }

            // Check TrangThai filter
            if (selectedTrangThai != null && !selectedTrangThai.equals("Trạng thái")) {
                if (ban.getTrangThai() == null || !ban.getTrangThai().getDisplayName().equals(selectedTrangThai)) {
                    continue;
                }
            }

            // Add to table
            model.addRow(new Object[] {
                    ban.getMaBan(),
                    ban.getSoGhe(),
                    ban.getViTri(),
                    ban.getKhuVuc() != null ? ban.getKhuVuc().getMaKhuVuc() : "",
                    ban.getTrangThai() != null ? ban.getTrangThai().getDisplayName() : ""
            });
        }
        centerTableColumns(tableBan);
    }

    private void searchByText() {
        DefaultTableModel model = (DefaultTableModel) tableBan.getModel();
        model.setRowCount(0);
        String searchText = txtTimKiem.getText().trim().toLowerCase();
        String selectedKhuVuc = (String) cbFilterKhuVuc.getSelectedItem();
        String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();

        for (Ban ban : allBans) {
            // Check KhuVuc filter
            if (selectedKhuVuc != null && !selectedKhuVuc.equals("Khu vực")) {
                if (ban.getKhuVuc() == null || !ban.getKhuVuc().getMaKhuVuc().equals(selectedKhuVuc)) {
                    continue;
                }
            }

            // Check TrangThai filter
            if (selectedTrangThai != null && !selectedTrangThai.equals("Trạng thái")) {
                if (ban.getTrangThai() == null || !ban.getTrangThai().getDisplayName().equals(selectedTrangThai)) {
                    continue;
                }
            }

            // Check search text
            String maBan = ban.getMaBan() != null ? ban.getMaBan().toLowerCase() : "";
            if (!searchText.isEmpty() && (!maBan.contains(searchText))) {
                continue;
            }

            // Add to table
            model.addRow(new Object[] {
                    ban.getMaBan(),
                    ban.getSoGhe(),
                    ban.getViTri(),
                    ban.getKhuVuc() != null ? ban.getKhuVuc().getMaKhuVuc() : "",
                    ban.getTrangThai() != null ? ban.getTrangThai().getDisplayName() : ""
            });
        }
        centerTableColumns(tableBan);
    }

    private void filterTable() {
        filterByComboBoxes();
    }

    public void refreshData() {
        resetPlaceholder(txtTimKiem, "Nhập mã bàn hoặc mã khu vực");
        cbFilterKhuVuc.setSelectedIndex(0);
        cbFilterTrangThai.setSelectedIndex(0);
        filterByComboBoxes();
        loadDataToTable();
        tableBan.clearSelection();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cbFilterKhuVuc;
    private javax.swing.JComboBox<String> cbFilterTrangThai;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlThongTinBan;
    private javax.swing.JScrollPane scrTableBan;
    private javax.swing.JTable tableBan;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
