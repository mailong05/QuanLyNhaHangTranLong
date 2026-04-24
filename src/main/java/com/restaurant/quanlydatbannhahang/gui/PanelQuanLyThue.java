package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.ThueService;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEnumLoader;
import com.restaurant.quanlydatbannhahang.util.IDGeneratorHelper;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;
import com.restaurant.quanlydatbannhahang.entity.Thue;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiThue;
import java.util.List;

public class PanelQuanLyThue extends javax.swing.JPanel implements MouseListener {

    private ActionListener cbFilterTrangThaiListener;
    private ComboBoxEnumLoader cbEnumLoader;

    public PanelQuanLyThue() {
        initComponents();
        cbEnumLoader = new ComboBoxEnumLoader();
        customUI();
        loadDataToComboBoxes();
        loadDataToTable();
    }

    private void customUI() {
        // Placeholder cho txtTimKiem
        setupPlaceholder(txtTimKiem, "Nhập tên hoặc mã thuế");
        fillTxtMaThue();
        MainForm.attachGoHomeListener(btnTrangChu, this);

        // ========== DESELECT WHEN CLICK OUTSIDE TABLE ==========
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.getSource() != tableThue && !isMouseOverTable(evt)) {
                    tableThue.clearSelection();
                    clearFields();
                }
            }
        });

        // Register mouse listener để populate fields khi click vào row
        tableThue.addMouseListener(this);
        tableThue.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tableThue.getSelectedRow();
                if (row >= 0) {
                    loadDataFromRow(row);
                }
                syncCapNhatButtonState();
            }
        });

        syncCapNhatButtonState();
    }

    private void loadDataFromRow(int rowIndex) {
        try {
            String maThue = (String) tableThue.getValueAt(rowIndex, 0);
            String tenThue = (String) tableThue.getValueAt(rowIndex, 1);
            Object thueSuatObj = tableThue.getValueAt(rowIndex, 2);
            String trangThaiStr = (String) tableThue.getValueAt(rowIndex, 3);

            txtMaThue.setText(maThue);
            txtTenThue.setText(tenThue);
            if (thueSuatObj != null) {
                txtThueSuat.setText(thueSuatObj.toString());
            } else {
                txtThueSuat.setText("");
            }
            cbTrangThai.setSelectedItem(trangThaiStr);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi load dữ liệu từ row: " + e.getMessage());
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

    private void resetPlaceholder(JTextField textField, String placeholder) {
        Color placeholderColor = new Color(153, 153, 153);
        textField.setText(placeholder);
        textField.setForeground(placeholderColor);
    }

    private void loadDataToComboBoxes() {
        try {
            // Save listeners
            ActionListener[] trangThaiListeners = cbFilterTrangThai.getActionListeners();

            // Remove listeners
            for (ActionListener listener : trangThaiListeners) {
                cbFilterTrangThai.removeActionListener(listener);
            }

            // Load TrangThai
            cbEnumLoader.loadTrangThaiThueToComboBox(cbFilterTrangThai);
            cbEnumLoader.loadTrangThaiThueToComboBox(cbTrangThai);

            // Re-add listeners
            for (ActionListener listener : trangThaiListeners) {
                cbFilterTrangThai.addActionListener(listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu filter: " + e.getMessage());
        }
    }

    private void loadDataToTable() {
        loadFilteredData();
    }

    private void fillTxtMaThue() {
        String lastID = IDQueryHelper.getLastID("Thue", "maThue");
        String maThueNew = (lastID == null || lastID.isEmpty()) ? IDGeneratorHelper.generateDefaultID("THUE")
                : IDGeneratorHelper.generateNextIDFromFullID(lastID);
        txtMaThue.setText(maThueNew);
    }

    private void loadFilteredData() {
        try {
            ThueService service = new ThueService();
            List<Thue> list = service.getAllThue();
            String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();

            DefaultTableModel model = (DefaultTableModel) tableThue.getModel();
            model.setRowCount(0);

            for (Thue thue : list) {
                // Apply TrangThai filter
                if (selectedTrangThai != null && !selectedTrangThai.equals("Trạng thái")) {
                    if (thue.getTrangThai() == null
                            || !thue.getTrangThai().getDisplayName().equals(selectedTrangThai)) {
                        continue;
                    }
                }

                model.addRow(new Object[] {
                        thue.getMaThue(),
                        thue.getTenThue(),
                        thue.getThueSuat(),
                        thue.getTrangThai().getDisplayName()
                });
            }
            centerTableColumns(tableThue);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu: " + e.getMessage());
        }
    }

    private void searchByText() {
        try {
            ThueService service = new ThueService();
            List<Thue> list = service.getAllThue();
            String searchText = txtTimKiem.getText().trim().toLowerCase();
            String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();

            DefaultTableModel model = (DefaultTableModel) tableThue.getModel();
            model.setRowCount(0);

            for (Thue thue : list) {
                // Apply TrangThai filter
                if (selectedTrangThai != null && !selectedTrangThai.equals("Trạng thái")) {
                    if (thue.getTrangThai() == null
                            || !thue.getTrangThai().getDisplayName().equals(selectedTrangThai)) {
                        continue;
                    }
                }

                // Apply search text filter
                if (!searchText.isEmpty()) {
                    String maThue = thue.getMaThue() != null ? thue.getMaThue().toLowerCase() : "";
                    String tenThue = thue.getTenThue() != null ? thue.getTenThue().toLowerCase() : "";
                    if (!maThue.contains(searchText) && !tenThue.contains(searchText)) {
                        continue;
                    }
                }

                model.addRow(new Object[] {
                        thue.getMaThue(),
                        thue.getTenThue(),
                        thue.getThueSuat(),
                        thue.getTrangThai().getDisplayName()
                });
            }
            centerTableColumns(tableThue);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm dữ liệu: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtTenThue.setText("");
        txtThueSuat.setText("");
        cbTrangThai.setSelectedIndex(0);
        fillTxtMaThue();
    }

    private void syncCapNhatButtonState() {
        btnCapNhat.setEnabled(tableThue.getSelectedRow() >= 0);
    }

    public void refreshData() {
        clearFields();
        resetPlaceholder(txtTimKiem, "Nhập tên hoặc mã thuế");
        cbFilterTrangThai.setSelectedIndex(0);
        loadDataToComboBoxes();
        loadDataToTable();
        tableThue.clearSelection();
        syncCapNhatButtonState();
    }

    private boolean isMouseOverTable(java.awt.event.MouseEvent evt) {
        java.awt.Point p = evt.getPoint();
        java.awt.Point tablePoint = SwingUtilities.convertPoint(this, p, tableThue);
        return tableThue.getBounds().contains(tablePoint);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlThongTinThue = new javax.swing.JPanel();
        lblMaThue = new javax.swing.JLabel();
        txtMaThue = new javax.swing.JTextField();
        lblTenThue = new javax.swing.JLabel();
        txtTenThue = new javax.swing.JTextField();
        txtThueSuat = new javax.swing.JTextField();
        lblThueSuat = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        cbTrangThai = new javax.swing.JComboBox<>();
        cbFilterTrangThai = new javax.swing.JComboBox<>();
        scrTableThue = new javax.swing.JScrollPane();
        tableThue = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        pnlRightButtons = new javax.swing.JPanel();
        btnXoaTrang = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout(0, 15));

        pnlHeader.setOpaque(false);
        pnlHeader.setLayout(new java.awt.BorderLayout(0, 15));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Quản lý các loại thuế và phí áp dụng cho hóa đơn");
        pnlHeader.add(jLabel1, java.awt.BorderLayout.WEST);

        pnlThongTinThue.setBackground(new java.awt.Color(255, 251, 233));

        lblMaThue.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMaThue.setText("Mã Thuế:");

        txtMaThue.setEditable(false);
        txtMaThue.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaThue.setPreferredSize(new java.awt.Dimension(64, 30));
        txtMaThue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaThueActionPerformed(evt);
            }
        });

        lblTenThue.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTenThue.setText("Tên thuế:");

        txtTenThue.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenThue.setPreferredSize(new java.awt.Dimension(64, 30));

        txtThueSuat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtThueSuat.setPreferredSize(new java.awt.Dimension(64, 30));
        txtThueSuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThueSuatActionPerformed(evt);
            }
        });

        lblThueSuat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblThueSuat.setText("Thuế suất:");

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTrangThai.setText("Trạng thái:");

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        cbTrangThai.setPreferredSize(new java.awt.Dimension(72, 30));
        cbTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTrangThaiActionPerformed(evt);
            }
        });

        cbFilterTrangThai.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbFilterTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterTrangThaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinThueLayout = new javax.swing.GroupLayout(pnlThongTinThue);
        pnlThongTinThue.setLayout(pnlThongTinThueLayout);
        pnlThongTinThueLayout.setHorizontalGroup(
                pnlThongTinThueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinThueLayout.createSequentialGroup()
                                .addGroup(pnlThongTinThueLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlThongTinThueLayout.createSequentialGroup()
                                                .addGroup(pnlThongTinThueLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(lblTenThue, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lblMaThue, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(pnlThongTinThueLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(pnlThongTinThueLayout.createSequentialGroup()
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(txtMaThue,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 364,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                pnlThongTinThueLayout.createSequentialGroup()
                                                                        .addGap(11, 11, 11)
                                                                        .addComponent(txtTenThue,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                365,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(61, 61, 61)
                                                .addGroup(pnlThongTinThueLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblThueSuat)
                                                        .addComponent(lblTrangThai)))
                                        .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 119,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlThongTinThueLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlThongTinThueLayout.createSequentialGroup()
                                                .addComponent(txtTimKiem)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 129,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnlThongTinThueLayout.createSequentialGroup()
                                                .addGroup(pnlThongTinThueLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtThueSuat,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 365,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cbTrangThai,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 109,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(24, Short.MAX_VALUE)))));
        pnlThongTinThueLayout.setVerticalGroup(
                pnlThongTinThueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinThueLayout.createSequentialGroup()
                                .addGroup(pnlThongTinThueLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlThongTinThueLayout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addGroup(pnlThongTinThueLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblThueSuat,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtThueSuat,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(8, 8, 8)
                                                .addGroup(pnlThongTinThueLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(cbTrangThai,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblTrangThai)))
                                        .addGroup(pnlThongTinThueLayout.createSequentialGroup()
                                                .addGroup(pnlThongTinThueLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblMaThue, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtMaThue, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(9, 9, 9)
                                                .addGroup(pnlThongTinThueLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblTenThue,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtTenThue,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinThueLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, 35,
                                                Short.MAX_VALUE))
                                .addContainerGap()));

        pnlHeader.add(pnlThongTinThue, java.awt.BorderLayout.PAGE_END);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        tableThue.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Mã thuế", "Tên thuế", "Thuế suất", "Trạng thái"
                }) {
            Class[] types = new Class[] {
                    java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tableThue.setRowHeight(35);
        scrTableThue.setViewportView(tableThue);

        add(scrTableThue, java.awt.BorderLayout.CENTER);

        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setLayout(new java.awt.BorderLayout());

        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTrangChu.setText("Trang Chủ");
        pnlButton.add(btnTrangChu, java.awt.BorderLayout.WEST);

        pnlRightButtons.setBackground(new java.awt.Color(255, 251, 233));
        pnlRightButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 15, 0));

        btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnXoaTrang);

        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnCapNhat);

        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnXoa);

        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnThem);

        pnlButton.add(pnlRightButtons, java.awt.BorderLayout.EAST);

        add(pnlButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaTrangActionPerformed
        // TODO add your handling code here:
        refreshData();
    }// GEN-LAST:event_btnXoaTrangActionPerformed

    private void centerTableColumns(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
        loadFilteredData();
    }// GEN-LAST:event_cbFilterTrangThaiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaActionPerformed
        String maThue = txtMaThue.getText().trim();
        if (maThue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thuế cần xóa.");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa thuế này không?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            ThueService service = new ThueService();
            service.xoaThue(maThue);
            JOptionPane.showMessageDialog(this, "Xóa thuế thành công.");
            refreshData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Xóa thuế thất bại: " + ex.getMessage());
        }
    }// GEN-LAST:event_btnXoaActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        searchByText();
    }// GEN-LAST:event_btnTimKiemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCapNhatActionPerformed
        try {
            String maThue = txtMaThue.getText().trim();
            String tenThue = txtTenThue.getText().trim();
            String thueSuatText = txtThueSuat.getText().trim();
            String trangThaiDisplay = (String) cbTrangThai.getSelectedItem();
            TrangThaiThue trangThai = ComboBoxEnumLoader.getTrangThaiThueFromDisplay(trangThaiDisplay);

            if (maThue.isEmpty() || tenThue.isEmpty() || thueSuatText.isEmpty() || trangThai == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin thuế.");
                return;
            }

            double thueSuat = Double.parseDouble(thueSuatText);
            Thue thue = new Thue(maThue, tenThue, thueSuat, trangThai);
            ThueService service = new ThueService();
            service.capNhatThue(thue);
            JOptionPane.showMessageDialog(this, "Cập nhật thuế thành công.");
            refreshData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Thuế suất không hợp lệ.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Cập nhật thuế thất bại: " + ex.getMessage());
        }
    }// GEN-LAST:event_btnCapNhatActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
        try {
            String maThue = txtMaThue.getText().trim();
            String tenThue = txtTenThue.getText().trim();
            String thueSuatText = txtThueSuat.getText().trim();
            String trangThaiDisplay = (String) cbTrangThai.getSelectedItem();
            TrangThaiThue trangThai = ComboBoxEnumLoader.getTrangThaiThueFromDisplay(trangThaiDisplay);

            if (maThue.isEmpty() || tenThue.isEmpty() || thueSuatText.isEmpty() || trangThai == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin thuế.");
                return;
            }

            double thueSuat = Double.parseDouble(thueSuatText);
            Thue thue = new Thue(maThue, tenThue, thueSuat, trangThai);
            ThueService service = new ThueService();
            service.themThue(thue);
            JOptionPane.showMessageDialog(this, "Thêm thuế thành công.");
            refreshData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Thuế suất không hợp lệ.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Thêm thuế thất bại: " + ex.getMessage());
        }
    }// GEN-LAST:event_btnThemActionPerformed

    private void txtMaThueActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMaThueActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtMaThueActionPerformed

    private void txtThueSuatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtThueSuatActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtThueSuatActionPerformed

    private void cbTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbTrangThaiActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_cbTrangThaiActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
        searchByText();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cbFilterTrangThai;
    private javax.swing.JComboBox<String> cbTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblMaThue;
    private javax.swing.JLabel lblTenThue;
    private javax.swing.JLabel lblThueSuat;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlRightButtons;
    private javax.swing.JPanel pnlThongTinThue;
    private javax.swing.JScrollPane scrTableThue;
    private javax.swing.JTable tableThue;
    private javax.swing.JTextField txtMaThue;
    private javax.swing.JTextField txtTenThue;
    private javax.swing.JTextField txtThueSuat;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        // Được xử lý tập trung trong selection listener của bảng
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
}
