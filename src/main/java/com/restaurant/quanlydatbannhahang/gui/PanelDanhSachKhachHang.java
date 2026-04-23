package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.KhachHangService;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEnumLoader;
import com.restaurant.quanlydatbannhahang.entity.KhachHang;
import java.util.List;

public class PanelDanhSachKhachHang extends javax.swing.JPanel {
    private KhachHangService khachHangService;
    private List<KhachHang> allKhachHang;
    private ComboBoxEnumLoader cbEnumLoader;
    /**
     * Creates new form PanelKhachHang
     */
    public PanelDanhSachKhachHang() {
        initComponents();
        khachHangService = new KhachHangService();
        cbEnumLoader = new ComboBoxEnumLoader();
        customUI();
        loadDataToTable();
        loadDataToComboBoxes();
    }

    private void customUI() {
        // Placeholder cho txtTimKiem
        setupPlaceholder(txtTimKiem, "Nhập số điện thoại hoặc tên");

        // 1. Màu nền chủ đạo
        setBackground(new Color(255, 251, 233));

        // 2. Tùy chỉnh hiệu ứng cho các nút bấm
        JButton[] buttons = { btnTrangChu, btnTimKiem };
        for (JButton btn : buttons) {
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        // 3. Tùy chỉnh ScrollPane và Viền bảng
        scrTableKhachHang.setBorder(BorderFactory.createLineBorder(new Color(200, 190, 170), 1));
        scrTableKhachHang.setViewportBorder(null);

        // 4. Tùy chỉnh Table (Bảng Khách Hàng)
        tableKhachHang.setRowHeight(35);

        // Căn giữa nội dung các cột
        centerTableColumns(tableKhachHang);

        // 5. Bo góc cho Panel chứa thông tin nhập liệu
        applyCardStyle(pnlThongTinKhachHang, 20);

        // 6. Gắn sự kiện quay về Trang Chủ
        MainForm.attachGoHomeListener(btnTrangChu, this);
    }

    private void centerTableColumns(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void resetPlaceholder(JTextField textField, String placeholder) {
        Color placeholderColor = new Color(153, 153, 153);
        textField.setText(placeholder);
        textField.setForeground(placeholderColor);
    }

    private void loadDataToTable() {
        allKhachHang = khachHangService.getAllKhachHang();
        DefaultTableModel model = (DefaultTableModel) tableKhachHang.getModel();
        model.setRowCount(0);

        for (KhachHang kh : allKhachHang) {
            model.addRow(new Object[] {
                    kh.getMaKH(),
                    kh.getHoTen(),
                    kh.getSdt(),
                    kh.getDiemTichLuy(),
                    kh.getLoaiThanhVien() != null ? kh.getLoaiThanhVien().getDisplayName() : ""
            });
        }
        centerTableColumns(tableKhachHang);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader2 = new javax.swing.JPanel();
        pnlThongTinKhachHang = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        cbLoaiThanhVien = new javax.swing.JComboBox<>();
        scrTableKhachHang = new javax.swing.JScrollPane();
        tableKhachHang = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        btnXoaTrang = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setFocusable(false);
        setLayout(new java.awt.BorderLayout(0, 10));

        pnlHeader2.setOpaque(false);
        pnlHeader2.setLayout(new java.awt.BorderLayout(0, 15));

        pnlThongTinKhachHang.setBackground(new java.awt.Color(255, 251, 233));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.setText("Tìm kiếm");

        cbLoaiThanhVien.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { }));
        cbLoaiThanhVien.setPreferredSize(new java.awt.Dimension(72, 35));
        cbLoaiThanhVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLoaiThanhVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinKhachHangLayout = new javax.swing.GroupLayout(pnlThongTinKhachHang);
        pnlThongTinKhachHang.setLayout(pnlThongTinKhachHangLayout);
        pnlThongTinKhachHangLayout.setHorizontalGroup(
                pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                pnlThongTinKhachHangLayout.createSequentialGroup()
                                        .addComponent(cbLoaiThanhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 136,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 485,
                                                Short.MAX_VALUE)
                                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 297,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 129,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)));
        pnlThongTinKhachHangLayout.setVerticalGroup(
                pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinKhachHangLayout.createSequentialGroup()
                                .addGroup(pnlThongTinKhachHangLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbLoaiThanhVien, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 11, Short.MAX_VALUE)));

        pnlHeader2.add(pnlThongTinKhachHang, java.awt.BorderLayout.PAGE_END);

        add(pnlHeader2, java.awt.BorderLayout.PAGE_START);

        tableKhachHang.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Mã KH", "Họ tên", "Số điện thoại", "Điểm tích lũy", "Loại thành viên"
                }) {
            Class[] types = new Class[] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class,
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
        tableKhachHang.setRowHeight(35);
        scrTableKhachHang.setViewportView(tableKhachHang);

        add(scrTableKhachHang, java.awt.BorderLayout.CENTER);

        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setLayout(new java.awt.BorderLayout());

        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTrangChu.setText("Trang Chủ");
        btnTrangChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrangChuActionPerformed(evt);
            }
        });
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

    private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTrangChuActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnTrangChuActionPerformed

    private void cbLoaiThanhVienActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbLoaiThanhVienActionPerformed
        filterTable();
    }// GEN-LAST:event_cbLoaiThanhVienActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        searchAndFilter();
    }// GEN-LAST:event_btnTimKiemActionPerformed

    private void searchAndFilter() {
        DefaultTableModel model = (DefaultTableModel) tableKhachHang.getModel();
        model.setRowCount(0);
        String searchText = txtTimKiem.getText().trim().toLowerCase();
        String selectedLoaiThanhVien = (String) cbLoaiThanhVien.getSelectedItem();

        for (KhachHang kh : allKhachHang) {
            // Check filters
            if (selectedLoaiThanhVien != null && !selectedLoaiThanhVien.equals("Loại thành viên")) {
                if (!kh.getLoaiThanhVien().getDisplayName().equals(selectedLoaiThanhVien)) {
                    continue;
                }
            }

            // Check search text
            String tenKH = kh.getHoTen() != null ? kh.getHoTen().toLowerCase() : "";
            String sdtKH = kh.getSdt() != null ? kh.getSdt().trim() : "";
            String maKH = kh.getMaKH() != null ? kh.getMaKH().toLowerCase() : "";
            if (!searchText.isEmpty()
                    && (!tenKH.contains(searchText) && !sdtKH.contains(searchText) && !maKH.contains(searchText))) {
                continue;
            }

            // Add to table
            model.addRow(new Object[] {
                    kh.getMaKH(),
                    kh.getHoTen(),
                    kh.getSdt(),
                    kh.getDiemTichLuy(),
                    kh.getLoaiThanhVien() != null ? kh.getLoaiThanhVien().getDisplayName() : ""
            });
        }
        centerTableColumns(tableKhachHang);
    }

    private void filterTable() {
        String selectedLoaiThanhVien = (String) cbLoaiThanhVien.getSelectedItem();

        if (selectedLoaiThanhVien != null && !selectedLoaiThanhVien.equals("Loại thành viên")) {
            searchAndFilter();
        } else {
            loadDataToTable();
        }
    }

    
    private void loadDataToComboBoxes() {
        try {
            // Save listeners
            ActionListener[] loaiThanhVienListeners = cbLoaiThanhVien.getActionListeners();

            // Remove listeners
            for (ActionListener listener : loaiThanhVienListeners) {
                cbLoaiThanhVien.removeActionListener(listener);
            }

            // Load LoaiThanhVien
            cbLoaiThanhVien.removeAllItems();
            cbEnumLoader.loadLoaiThanhVienToComboBox(cbLoaiThanhVien);
            // Re-add listeners
            for (ActionListener listener : loaiThanhVienListeners) {
                cbLoaiThanhVien.addActionListener(listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu filter: " + e.getMessage());
        }
    }
    
    public void refreshData() {
        resetPlaceholder(txtTimKiem, "Nhập số điện thoại hoặc tên");
        cbLoaiThanhVien.setSelectedIndex(0);
        filterTable();
        tableKhachHang.clearSelection();
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cbLoaiThanhVien;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader2;
    private javax.swing.JPanel pnlThongTinKhachHang;
    private javax.swing.JScrollPane scrTableKhachHang;
    private javax.swing.JTable tableKhachHang;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
