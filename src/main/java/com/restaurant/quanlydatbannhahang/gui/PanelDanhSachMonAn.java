package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.MonAnService;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEntityLoader;
import com.restaurant.quanlydatbannhahang.entity.MonAn;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiMonAn;
import java.util.List;

/**
 *
 * @author nguye
 */
public class PanelDanhSachMonAn extends javax.swing.JPanel {
    private MonAnService monAnService;
    private List<MonAn> allMonAn;

    /**
     * Creates new form PanelQuanLyMonAn
     */
    public PanelDanhSachMonAn() {
        initComponents();        
        monAnService = new MonAnService();
        customUI();
        loadDataToTable();
    }

    private void customUI() {
        // Placeholder cho txtTimKiem
        setupPlaceholder(txtTimKiem, "Nhập tên món ăn");

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

    private void loadDataToTable() {
        try {
            allMonAn = monAnService.getAllMonAn();
            DefaultTableModel model = (DefaultTableModel) tableMonAn.getModel();
            model.setRowCount(0);

            for (MonAn monAn : allMonAn) {
                model.addRow(new Object[] {
                        "",
                        monAn.getMaMon(),
                        monAn.getTenMon(),
                        monAn.getDonGia(),
                        monAn.getDonViTinh(),
                        monAn.getTenLoai() != null ? monAn.getTenLoai().getDisplayName() : "",
                        monAn.kiemTraConHang() ? "Còn" : "Ngưng sử dụng"
                });
            }
            centerTableColumns(tableMonAn);
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
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlThongTinKhuyenMai = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        cbFilterLoaiMonAn = new javax.swing.JComboBox<>();
        cbFilterDonViTinh = new javax.swing.JComboBox<>();
        scrTableMonAn = new javax.swing.JScrollPane();
        tableMonAn = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout(0, 15));

        pnlHeader.setOpaque(false);
        pnlHeader.setLayout(new java.awt.BorderLayout(0, 15));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Quản lý thông tin món ăn ");
        pnlHeader.add(jLabel1, java.awt.BorderLayout.WEST);

        pnlThongTinKhuyenMai.setBackground(new java.awt.Color(255, 251, 233));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        cbFilterLoaiMonAn.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbFilterLoaiMonAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterLoaiMonAnActionPerformed(evt);
            }
        });

        cbFilterDonViTinh.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlThongTinKhuyenMaiLayout = new javax.swing.GroupLayout(pnlThongTinKhuyenMai);
        pnlThongTinKhuyenMai.setLayout(pnlThongTinKhuyenMaiLayout);
        pnlThongTinKhuyenMaiLayout.setHorizontalGroup(
                pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                                .addComponent(cbFilterLoaiMonAn, javax.swing.GroupLayout.PREFERRED_SIZE, 139,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(cbFilterDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 145,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 308,
                                        Short.MAX_VALUE)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 294,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 123,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)));
        pnlThongTinKhuyenMaiLayout.setVerticalGroup(
                pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                                .addGroup(pnlThongTinKhuyenMaiLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                                .addGroup(pnlThongTinKhuyenMaiLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(cbFilterLoaiMonAn))
                                        .addComponent(cbFilterDonViTinh))
                                .addContainerGap()));

        pnlHeader.add(pnlThongTinKhuyenMai, java.awt.BorderLayout.PAGE_END);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        scrTableMonAn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrTableMonAnMouseClicked(evt);
            }
        });

        tableMonAn.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Hình ảnh", "Mã món", "Tên món", "Đơn giá", "Đơn vị tính", "Loại món ăn", "Trạng thái"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tableMonAn.setRowHeight(35);
        scrTableMonAn.setViewportView(tableMonAn);

        add(scrTableMonAn, java.awt.BorderLayout.CENTER);

        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setLayout(new java.awt.BorderLayout());

        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTrangChu.setText("Trang Chủ");
        pnlButton.add(btnTrangChu, java.awt.BorderLayout.WEST);

        add(pnlButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void cbFilterLoaiMonAnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterLoaiMonAnActionPerformed
        filterTable();
    }// GEN-LAST:event_cbFilterLoaiMonAnActionPerformed

    private void scrTableMonAnMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_scrTableMonAnMouseClicked
        // TODO add your handling code here:
    }// GEN-LAST:event_scrTableMonAnMouseClicked

    private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
        filterTable();
    }// GEN-LAST:event_cbFilterTrangThaiActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        searchAndFilter();
    }// GEN-LAST:event_btnTimKiemActionPerformed

    private void searchAndFilter() {
        DefaultTableModel model = (DefaultTableModel) tableMonAn.getModel();
        model.setRowCount(0);
        String searchText = txtTimKiem.getText().trim().toLowerCase();
        String selectedLoaiMonAn = (String) cbFilterLoaiMonAn.getSelectedItem();
        String selectedDonVi = (String) cbFilterDonViTinh.getSelectedItem();

        for (MonAn monAn : allMonAn) {
            // Check filters
            if (selectedLoaiMonAn != null && !selectedLoaiMonAn.equals("Item 1")) {
                if (monAn.getTenLoai() == null || !monAn.getTenLoai().getDisplayName().equals(selectedLoaiMonAn)) {
                    continue;
                }
            }

            if (selectedDonVi != null && !selectedDonVi.equals("Item 1")) {
                if (!monAn.getDonViTinh().equals(selectedDonVi)) {
                    continue;
                }
            }

            // Check search text
            String tenMonAn = monAn.getTenMon() != null ? monAn.getTenMon().toLowerCase() : "";
            String maMonAn = monAn.getMaMon() != null ? monAn.getMaMon().toLowerCase() : "";
            if (!searchText.isEmpty() && (!tenMonAn.contains(searchText) && !maMonAn.contains(searchText))) {
                continue;
            }

            // Add to table
            model.addRow(new Object[] {
                    "",
                    monAn.getMaMon(),
                    monAn.getTenMon(),
                    monAn.getDonGia(),
                    monAn.getDonViTinh(),
                    monAn.getTenLoai() != null ? monAn.getTenLoai().getDisplayName() : "",
                    monAn.kiemTraConHang() ? "Còn" : "Ngưng sử dụng"
            });
        }
        centerTableColumns(tableMonAn);
    }

    private void filterTable() {
        String selectedLoaiMonAn = (String) cbFilterLoaiMonAn.getSelectedItem();
        String selectedDonVi = (String) cbFilterDonViTinh.getSelectedItem();

        if ((selectedLoaiMonAn != null && !selectedLoaiMonAn.equals("Item 1")) ||
                (selectedDonVi != null && !selectedDonVi.equals("Item 1"))) {
            searchAndFilter();
        } else {
            loadDataToTable();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JComboBox<String> cbFilterDonViTinh;
    private javax.swing.JComboBox<String> cbFilterLoaiMonAn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlThongTinKhuyenMai;
    private javax.swing.JScrollPane scrTableMonAn;
    private javax.swing.JTable tableMonAn;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
