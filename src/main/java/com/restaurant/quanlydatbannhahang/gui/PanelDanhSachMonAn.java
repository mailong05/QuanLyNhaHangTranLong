package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.MonAnService;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEnumLoader;
import com.restaurant.quanlydatbannhahang.util.ImageUtil;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import com.restaurant.quanlydatbannhahang.entity.MonAn;

/**
 *
 * @author nguye
 */
public class PanelDanhSachMonAn extends javax.swing.JPanel {
    private static final int TABLE_IMAGE_SIZE = 72;
    private static final int TABLE_IMAGE_ROW_HEIGHT = 84;
    private static final int TABLE_IMAGE_VERTICAL_PADDING = 4;

    private MonAnService monAnService;
    private List<MonAn> allMonAn;
    private boolean imagePreloadStarted;

    /**
     * Creates new form PanelQuanLyMonAn
     */
    public PanelDanhSachMonAn() {
        initComponents();
        monAnService = new MonAnService();
        customUI();
        loadDataToComboBoxes();
        loadDataToTable();
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
        cbFilterTrangThai = new javax.swing.JComboBox<>();
        scrTableMonAn = new javax.swing.JScrollPane();
        tableMonAn = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        btnXoaTrang = new javax.swing.JButton();

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

        cbFilterLoaiMonAn.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbFilterLoaiMonAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterLoaiMonAnActionPerformed(evt);
            }
        });

        cbFilterTrangThai.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
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
                                .addComponent(cbFilterLoaiMonAn, javax.swing.GroupLayout.PREFERRED_SIZE, 139,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 145,
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
                                        .addComponent(cbFilterTrangThai))
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

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
        searchAndFilter();
    }// GEN-LAST:event_txtTimKiemActionPerformed

    private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
        filterTable();
    }// GEN-LAST:event_cbFilterTrangThaiActionPerformed

    private void customUI() {
        // Placeholder cho txtTimKiem
        setupPlaceholder(txtTimKiem, "Nhập tên món ăn");

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.getSource() != tableMonAn && !isMouseOverTable(evt)) {
                    tableMonAn.clearSelection();
                    refreshData();
                }
            }
        });

        // Gắn sự kiện quay về Trang Chủ
        MainForm.attachGoHomeListener(btnTrangChu, this);
    }

    private void loadDataToComboBoxes() {
        try {
            // Load LoaiMonAn enum
            ActionListener[] loaiMonAnListener = cbFilterLoaiMonAn.getActionListeners();
            ActionListener[] trangThaiListener = cbFilterTrangThai.getActionListeners();

            // Remove listeners
            for (ActionListener listener : loaiMonAnListener) {
                cbFilterLoaiMonAn.removeActionListener(listener);
            }

            for (ActionListener listener : trangThaiListener) {
                cbFilterTrangThai.removeActionListener(listener);
            }

            cbFilterTrangThai.removeAllItems();
            cbFilterTrangThai.addItem("Trạng thái");
            ComboBoxEnumLoader.loadTrangThaiMonAnToComboBox(cbFilterTrangThai);

            cbFilterLoaiMonAn.removeAllItems();
            cbFilterLoaiMonAn.addItem("Loại món ăn");
            ComboBoxEnumLoader.loadLoaiMonAnToComboBox(cbFilterLoaiMonAn);

            for (java.awt.event.ActionListener listener : loaiMonAnListener) {
                cbFilterLoaiMonAn.addActionListener(listener);
            }

            for (java.awt.event.ActionListener listener : trangThaiListener) {
                cbFilterTrangThai.addActionListener(listener);
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

    private void preloadImagesInBackground(List<MonAn> monAnList) {
        if (imagePreloadStarted || monAnList == null || monAnList.isEmpty()) {
            return;
        }

        List<String> imagePaths = new ArrayList<>();
        for (MonAn monAn : monAnList) {
            if (monAn != null && monAn.getUrlHinhAnh() != null && !monAn.getUrlHinhAnh().trim().isEmpty()) {
                imagePaths.add(monAn.getUrlHinhAnh());
            }
        }

        // Warm-up một phần ảnh đầu tiên để giảm delay render lần đầu.
        ImageUtil.preloadFirstN(imagePaths, TABLE_IMAGE_SIZE, 20);
        ImageUtil.preloadImagesAsync(imagePaths, TABLE_IMAGE_SIZE);
        imagePreloadStarted = true;
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
            List<MonAn> monAnList = ensureMonAnDataLoaded();
            preloadImagesInBackground(monAnList);
            DefaultTableModel model = (DefaultTableModel) tableMonAn.getModel();
            model.setRowCount(0);

            for (MonAn monAn : monAnList) {
                model.addRow(new Object[] {
                        ImageUtil.loadImageIcon(monAn.getUrlHinhAnh(), TABLE_IMAGE_SIZE),
                        monAn.getMaMon(),
                        monAn.getTenMon(),
                        monAn.getDonGia(),
                        monAn.getDonViTinh(),
                        monAn.getTenLoai() != null ? monAn.getTenLoai().getDisplayName() : "",
                        monAn.getTrangThai() != null ? monAn.getTrangThai().getDisplayName() : "Còn"
                });
            }
            centerTableColumns(tableMonAn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void centerTableColumns(JTable table) {
        // Column 0 là hình ảnh - dùng ImageRenderer
        ImageRenderer imageRenderer = new ImageRenderer();
        table.getColumnModel().getColumn(0).setCellRenderer(imageRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.setRowHeight(TABLE_IMAGE_ROW_HEIGHT);

        // Các column khác căn giữa
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    // Image loading is now handled by ImageUtil class
    @Deprecated
    private ImageIcon loadImageIcon(String imagePath) {
        return ImageUtil.loadImageIcon(imagePath, TABLE_IMAGE_SIZE);
    }

    // Custom renderer for displaying images in table cells
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

    private void filterTable() {
        List<MonAn> monAnList = ensureMonAnDataLoaded();
        DefaultTableModel model = (DefaultTableModel) tableMonAn.getModel();
        model.setRowCount(0);

        String selectedLoaiMonAn = (String) cbFilterLoaiMonAn.getSelectedItem();
        String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();

        for (MonAn monAn : monAnList) {
            // Check LoaiMonAn filter
            if (selectedLoaiMonAn != null && !selectedLoaiMonAn.isEmpty() && !selectedLoaiMonAn.equals("Loại món ăn")) {
                if (monAn.getTenLoai() == null || !monAn.getTenLoai().getDisplayName().equals(selectedLoaiMonAn)) {
                    continue;
                }
            }

            // Check TrangThai filter
            if (selectedTrangThai != null && !selectedTrangThai.isEmpty() && !selectedTrangThai.equals("Trạng thái")) {
                if (monAn.getTrangThai() == null || !monAn.getTrangThai().getDisplayName().equals(selectedTrangThai)) {
                    continue;
                }
            }

            model.addRow(new Object[] {
                    ImageUtil.loadImageIcon(monAn.getUrlHinhAnh(), TABLE_IMAGE_SIZE),
                    monAn.getMaMon(),
                    monAn.getTenMon(),
                    monAn.getDonGia(),
                    monAn.getDonViTinh(),
                    monAn.getTenLoai() != null ? monAn.getTenLoai().getDisplayName() : "",
                    monAn.getTrangThai() != null ? monAn.getTrangThai().getDisplayName() : ""
            });
        }
        centerTableColumns(tableMonAn);
    }

    private void searchAndFilter() {
        List<MonAn> monAnList = ensureMonAnDataLoaded();
        DefaultTableModel model = (DefaultTableModel) tableMonAn.getModel();
        model.setRowCount(0);
        String searchText = txtTimKiem.getText().trim().toLowerCase();
        String selectedLoaiMonAn = (String) cbFilterLoaiMonAn.getSelectedItem();
        String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();

        for (MonAn monAn : monAnList) {
            // Check LoaiMonAn filter
            if (selectedLoaiMonAn != null && !selectedLoaiMonAn.isEmpty() && !selectedLoaiMonAn.equals("Loại món ăn")) {
                if (monAn.getTenLoai() == null || !monAn.getTenLoai().getDisplayName().equals(selectedLoaiMonAn)) {
                    continue;
                }
            }

            // Check TrangThai filter
            if (selectedTrangThai != null && !selectedTrangThai.isEmpty() && !selectedTrangThai.equals("Trạng thái")) {
                if (monAn.getTrangThai() == null || !monAn.getTrangThai().getDisplayName().equals(selectedTrangThai)) {
                    continue;
                }
            }

            // Check search text
            if (!searchText.isEmpty()) {
                String tenMonAn = monAn.getTenMon() != null ? monAn.getTenMon().toLowerCase() : "";
                String maMon = monAn.getMaMon() != null ? monAn.getMaMon().toLowerCase() : "";
                if (!tenMonAn.contains(searchText) && !maMon.contains(searchText)) {
                    continue;
                }
            }

            model.addRow(new Object[] {
                    ImageUtil.loadImageIcon(monAn.getUrlHinhAnh(), TABLE_IMAGE_SIZE),
                    monAn.getMaMon(),
                    monAn.getTenMon(),
                    monAn.getDonGia(),
                    monAn.getDonViTinh(),
                    monAn.getTenLoai() != null ? monAn.getTenLoai().getDisplayName() : "",
                    monAn.getTrangThai() != null ? monAn.getTrangThai().getDisplayName() : ""
            });
        }
        centerTableColumns(tableMonAn);
    }

    public void refreshData() {
        txtTimKiem.setText("");
        cbFilterLoaiMonAn.setSelectedIndex(0);
        loadDataToComboBoxes();
        loadDataToTable();
    }

    private void resetPlaceholder(JTextField textField, String placeholder) {
        Color placeholderColor = new Color(153, 153, 153);
        textField.setText(placeholder);
        textField.setForeground(placeholderColor);
    }

    private boolean isMouseOverTable(java.awt.event.MouseEvent evt) {
        java.awt.Point p = evt.getPoint();
        java.awt.Point tablePoint = SwingUtilities.convertPoint(this, p, tableMonAn);
        return tableMonAn.getBounds().contains(tablePoint);
    }

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaTrangActionPerformed
        refreshData();
        resetPlaceholder(txtTimKiem, "Nhập tên món ăn");
    }// GEN-LAST:event_btnXoaTrangActionPerformed

    private void cbFilterLoaiMonAnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterLoaiMonAnActionPerformed
        filterTable();
    }// GEN-LAST:event_cbFilterLoaiMonAnActionPerformed

    private void scrTableMonAnMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_scrTableMonAnMouseClicked
        // TODO add your handling code here:
    }// GEN-LAST:event_scrTableMonAnMouseClicked

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        searchAndFilter();
    }// GEN-LAST:event_btnTimKiemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cbFilterLoaiMonAn;
    private javax.swing.JComboBox<String> cbFilterTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlThongTinKhuyenMai;
    private javax.swing.JScrollPane scrTableMonAn;
    private javax.swing.JTable tableMonAn;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
