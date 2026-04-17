package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.KhuyenMaiService;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEnumLoader;
import com.restaurant.quanlydatbannhahang.entity.KhuyenMai;
import java.util.List;

public class PanelDanhSachKhuyenMai extends javax.swing.JPanel {
    private KhuyenMaiService khuyenMaiService;
    private List<KhuyenMai> allKhuyenMai;

    public PanelDanhSachKhuyenMai() {
        initComponents();
        khuyenMaiService = new KhuyenMaiService();
        customUI();
        loadDataToTable();
    }

    private void customUI() {
        // Placeholder cho txtTimKiem
        setupPlaceholder(txtTimKiem, "Nhập tên hoặc mã khuyến mãi");

        // Load enum trạng thái lên ComboBox
        ComboBoxEnumLoader.loadTrangThaiKhuyenMaiToComboBox(cbFilterTrangThai);

        // Gắn sự kiện quay về Trang Chủ
        MainForm.attachGoHomeListener(btnTrangChu, this);
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

    private void loadDataToTable() {
        try {
            allKhuyenMai = khuyenMaiService.getAllKhuyenMai();
            DefaultTableModel model = (DefaultTableModel) tableKhuyenMai.getModel();
            model.setRowCount(0);

            for (KhuyenMai km : allKhuyenMai) {
                model.addRow(new Object[] {
                        km.getMaKM(),
                        km.getTenKM(),
                        km.getGiaTriGiam(),
                        km.getNgayBatDau(),
                        km.getNgayKetThuc(),
                        km.getDieuKienToiThieu(),
                        km.getTrangThai().getDisplayName()
                });
            }
            centerTableColumns(tableKhuyenMai);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        pnlThongTinKhuyenMai = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        cbFilterTrangThai = new javax.swing.JComboBox<>();
        scrTableKhuyenMai = new javax.swing.JScrollPane();
        tableKhuyenMai = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        btnXoaTrang = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout(0, 15));

        pnlHeader.setBackground(new java.awt.Color(255, 251, 233));
        pnlHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitle.setText("Quản lý các chương trình khuyến mãi và giảm giá  ");
        pnlHeader.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlThongTinKhuyenMai.setBackground(new java.awt.Color(255, 251, 233));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        cbFilterTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trạng thái", "Còn áp dụng", "Ngưng áp dụng", " " }));
        cbFilterTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterTrangThaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinKhuyenMaiLayout = new javax.swing.GroupLayout(pnlThongTinKhuyenMai);
        pnlThongTinKhuyenMai.setLayout(pnlThongTinKhuyenMaiLayout);
        pnlThongTinKhuyenMaiLayout.setHorizontalGroup(
            pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 448, Short.MAX_VALUE)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlThongTinKhuyenMaiLayout.setVerticalGroup(
            pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                            .addGap(0, 1, Short.MAX_VALUE)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                    .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        pnlHeader.add(pnlThongTinKhuyenMai, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, -1, -1));

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        tableKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã khuyến mãi", "Tên khuyến mãi", "Giá trị giảm", "Ngày bắt đầu", "Ngày kết thúc", "Điều kiện tối thiểu", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableKhuyenMai.setRowHeight(35);
        scrTableKhuyenMai.setViewportView(tableKhuyenMai);

        add(scrTableKhuyenMai, java.awt.BorderLayout.CENTER);

        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setLayout(new java.awt.BorderLayout());

        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTrangChu.setText("Trang Chủ");
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

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaTrangActionPerformed

    private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
        filterTable();
    }// GEN-LAST:event_cbFilterTrangThaiActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        searchAndFilter();
    }// GEN-LAST:event_btnTimKiemActionPerformed

    private void searchAndFilter() {
        DefaultTableModel model = (DefaultTableModel) tableKhuyenMai.getModel();
        model.setRowCount(0);
        String searchText = txtTimKiem.getText().trim().toLowerCase();
        String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();

        for (KhuyenMai km : allKhuyenMai) {
            // Check filters
            if (selectedTrangThai != null && !selectedTrangThai.equals("Trạng thái")) {
                if (!km.getTrangThai().getDisplayName().equals(selectedTrangThai)) {
                    continue;
                }
            }

            // Check search text
            String tenKM = km.getTenKM() != null ? km.getTenKM().toLowerCase() : "";
            String maKM = km.getMaKM() != null ? km.getMaKM().toLowerCase() : "";
            if (!searchText.isEmpty() && (!tenKM.contains(searchText) && !maKM.contains(searchText))) {
                continue;
            }

            // Add to table
            model.addRow(new Object[] {
                    km.getMaKM(),
                    km.getTenKM(),
                    km.getGiaTriGiam(),
                    km.getNgayBatDau(),
                    km.getNgayKetThuc(),
                    km.getDieuKienToiThieu(),
                    km.getTrangThai().getDisplayName()
            });
        }
        centerTableColumns(tableKhuyenMai);
    }

    private void filterTable() {
        String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();

        if (selectedTrangThai != null && !selectedTrangThai.equals("Trạng thái")) {
            searchAndFilter();
        } else {
            loadDataToTable();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cbFilterTrangThai;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlThongTinKhuyenMai;
    private javax.swing.JScrollPane scrTableKhuyenMai;
    private javax.swing.JTable tableKhuyenMai;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
