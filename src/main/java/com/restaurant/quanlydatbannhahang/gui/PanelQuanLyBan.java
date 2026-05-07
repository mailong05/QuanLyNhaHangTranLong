package com.restaurant.quanlydatbannhahang.gui;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.BanService;
import com.restaurant.quanlydatbannhahang.service.KhuVucService;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEnumLoader;
import com.restaurant.quanlydatbannhahang.util.IDGeneratorHelper;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.entity.KhuVuc;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class PanelQuanLyBan extends javax.swing.JPanel implements MouseListener {
    private ActionListener cbFilterKhuVucListener;
    private ActionListener cbFilterTrangThaiListener;
    private BanService banService;
    private KhuVucService khuVucService;
    private IDGeneratorHelper idGenerateHelper;
    private IDQueryHelper idQueryHelper;
    public PanelQuanLyBan() {
        initComponents();
        banService = new BanService();
        khuVucService = new KhuVucService();
        idGenerateHelper = new IDGeneratorHelper();
        idQueryHelper = new IDQueryHelper();
        customUI();
        loadDataToComboBoxes();
        loadDataToTable();
        fillTxtMaBan(txtMaBan);
    }
    private void customUI() {
        setupPlaceholder(txtTimKiem, "Nhập mã bàn");
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.getSource() != tableBan && !isMouseOverTable(evt)) {
                    tableBan.clearSelection();
                    clearFields();
                    fillTxtMaBan(txtMaBan);
                }
            }
        });
        tableBan.addMouseListener(this);
        tableBan.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tableBan.getSelectedRow();
                if (row >= 0) {
                    loadDataFromRow(row);
                }
                syncCapNhatButtonState();
            }
        });
        syncCapNhatButtonState();
    }
    private void setupPlaceholder(JTextField textField, String placeholder) {
        Color placeholderColor = new Color(153, 153, 153);
        Color textColor = new Color(0, 0, 0);
        textField.setText(placeholder);
        textField.setForeground(placeholderColor);
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(textColor);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
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
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        pnlThongTinBan = new javax.swing.JPanel();
        lblMaBan = new javax.swing.JLabel();
        txtMaBan = new javax.swing.JTextField();
        lblSoGhe = new javax.swing.JLabel();
        txtViTri = new javax.swing.JTextField();
        lblViTri = new javax.swing.JLabel();
        lblKhuVuc = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        cbTrangThai = new javax.swing.JComboBox<>();
        cbKhuVuc = new javax.swing.JComboBox<>();
        cbFilterKhuVuc = new javax.swing.JComboBox<>();
        cbFilterTrangThai = new javax.swing.JComboBox<>();
        spSoGhe = new javax.swing.JSpinner();
        scrTableBan = new javax.swing.JScrollPane();
        tableBan = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        pnlRightButtons = new javax.swing.JPanel();
        btnXoaTrang = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout(0, 10));
        pnlHeader.setOpaque(false);
        pnlHeader.setLayout(new java.awt.BorderLayout(0, 15));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 18));
        lblTitle.setText("Quản lý thông tin bàn ăn trong nhà hàng");
        pnlHeader.add(lblTitle, java.awt.BorderLayout.WEST);
        pnlThongTinBan.setBackground(new java.awt.Color(255, 251, 233));
        lblMaBan.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblMaBan.setText("Mã bàn:");
        txtMaBan.setEditable(false);
        txtMaBan.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtMaBan.setMinimumSize(new java.awt.Dimension(64, 35));
        txtMaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaBanActionPerformed(evt);
            }
        });
        lblSoGhe.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblSoGhe.setText("Số ghế:");
        txtViTri.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtViTri.setPreferredSize(new java.awt.Dimension(64, 35));
        txtViTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtViTriActionPerformed(evt);
            }
        });
        lblViTri.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblViTri.setText("Vị trí:");
        lblKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblKhuVuc.setText("Khu vực:");
        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblTrangThai.setText("Trạng thái:");
        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        cbTrangThai.setEnabled(false);
        cbTrangThai.setMinimumSize(new java.awt.Dimension(72, 35));
        cbTrangThai.setName("");
        cbTrangThai.setPreferredSize(new java.awt.Dimension(72, 35));
        cbKhuVuc.setPreferredSize(new java.awt.Dimension(72, 35));
        cbFilterKhuVuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbFilterKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterKhuVucActionPerformed(evt);
            }
        });
        cbFilterTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
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
                .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                        .addComponent(cbFilterKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                        .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaBan)
                            .addComponent(lblSoGhe, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spSoGhe)
                            .addComponent(txtMaBan, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE))
                        .addGap(56, 56, 56)
                        .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblViTri)
                            .addComponent(lblKhuVuc, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(26, 26, 26)
                .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinBanLayout.createSequentialGroup()
                        .addComponent(txtTimKiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                        .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtViTri, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                .addComponent(cbKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTrangThai)
                                .addGap(18, 18, 18)
                                .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(51, Short.MAX_VALUE))))
        );
        pnlThongTinBanLayout.setVerticalGroup(
            pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lblViTri, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtViTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSoGhe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblKhuVuc)
                        .addComponent(cbKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTrangThai))
                    .addComponent(spSoGhe, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbFilterKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFilterTrangThai))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlHeader.add(pnlThongTinBan, java.awt.BorderLayout.PAGE_END);
        add(pnlHeader, java.awt.BorderLayout.PAGE_START);
        tableBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Mã bàn", "Số ghế", "Vị trí", "Mã khu vực", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableBan.setRowHeight(35);
        scrTableBan.setViewportView(tableBan);
        add(scrTableBan, java.awt.BorderLayout.CENTER);
        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setPreferredSize(new java.awt.Dimension(100, 27));
        pnlButton.setLayout(new java.awt.BorderLayout(0, 5));
        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnTrangChu.setText("Trang Chủ");
        btnTrangChu.setPreferredSize(new java.awt.Dimension(100, 27));
        btnTrangChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrangChuActionPerformed(evt);
            }
        });
        pnlButton.add(btnTrangChu, java.awt.BorderLayout.WEST);
        pnlRightButtons.setBackground(new java.awt.Color(255, 251, 233));
        pnlRightButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 15, 0));
        btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnXoaTrang);
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setPreferredSize(new java.awt.Dimension(90, 27));
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnCapNhat);
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnXoa);
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14));
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
    private void fillTxtMaBan(JTextField txtMaBan) {
        String lastMaBan = idQueryHelper.getLastID("BanAn", "maBan");
        String newMaBan = (lastMaBan == null || lastMaBan.isEmpty()) ? idGenerateHelper.generateDefaultID("B")
                : idGenerateHelper.generateNextIDFromFullID(lastMaBan);
        txtMaBan.setText(newMaBan);
    }
    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaTrangActionPerformed
        refreshData();
    }// GEN-LAST:event_btnXoaTrangActionPerformed
    private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTrangChuActionPerformed
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentFrame instanceof MainForm) {
            ((MainForm) parentFrame).goToTrangChuFromPanel();
        }
    }// GEN-LAST:event_btnTrangChuActionPerformed
    private void loadDataToComboBoxes() {
        try {
            ActionListener[] khuVucListeners = cbFilterKhuVuc.getActionListeners();
            ActionListener[] trangThaiListeners = cbFilterTrangThai.getActionListeners();
            for (ActionListener listener : khuVucListeners) {
                cbFilterKhuVuc.removeActionListener(listener);
            }
            for (ActionListener listener : trangThaiListeners) {
                cbFilterTrangThai.removeActionListener(listener);
            }
            cbFilterKhuVuc.removeAllItems();
            cbKhuVuc.removeAllItems();
            cbFilterKhuVuc.addItem("Khu vực");
            List<KhuVuc> dsKhuVuc = khuVucService.getAllKhuVuc();
            for (KhuVuc kv : dsKhuVuc) {
                cbFilterKhuVuc.addItem(kv.getMaKhuVuc());
                cbKhuVuc.addItem(kv.getMaKhuVuc());
            }
            cbFilterTrangThai.removeAllItems();
            cbTrangThai.removeAllItems();
            cbFilterTrangThai.addItem("Trạng thái");
            for (TrangThaiBan trangThai : TrangThaiBan
                    .values()) {
                cbFilterTrangThai.addItem(trangThai.getDisplayName());
                cbTrangThai.addItem(trangThai.getDisplayName());
            }
            for (ActionListener listener : khuVucListeners) {
                cbFilterKhuVuc.addActionListener(listener);
            }
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
    private void loadFilteredData() {
        try {
            List<Ban> list = banService.getAllBan();
            String selectedKhuVuc = (String) cbFilterKhuVuc.getSelectedItem();
            String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();
            DefaultTableModel model = (DefaultTableModel) tableBan.getModel();
            model.setRowCount(0);
            for (Ban ban : list) {
                if (selectedKhuVuc != null && !selectedKhuVuc.equals("Khu vực")) {
                    if (ban.getKhuVuc() == null || !ban.getKhuVuc().getMaKhuVuc().equals(selectedKhuVuc)) {
                        continue;
                    }
                }
                if (selectedTrangThai != null && !selectedTrangThai.equals("Trạng thái")) {
                    if (ban.getTrangThai() == null || !ban.getTrangThai().getDisplayName().equals(selectedTrangThai)) {
                        continue;
                    }
                }
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
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu: " + e.getMessage());
        }
    }
    private void centerTableColumns(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    private void searchByText() {
        try {
            List<Ban> list = banService.getAllBan();
            String searchText = txtTimKiem.getText().trim().toLowerCase();
            String selectedKhuVuc = (String) cbFilterKhuVuc.getSelectedItem();
            String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();
            DefaultTableModel model = (DefaultTableModel) tableBan.getModel();
            model.setRowCount(0);
            for (Ban ban : list) {
                if (selectedKhuVuc != null && !selectedKhuVuc.equals("Khu vực")) {
                    if (ban.getKhuVuc() == null || !ban.getKhuVuc().getMaKhuVuc().equals(selectedKhuVuc)) {
                        continue;
                    }
                }
                if (selectedTrangThai != null && !selectedTrangThai.equals("Trạng thái")) {
                    if (ban.getTrangThai() == null || !ban.getTrangThai().getDisplayName().equals(selectedTrangThai)) {
                        continue;
                    }
                }
                if (!searchText.isEmpty()) {
                    String maBan = ban.getMaBan() != null ? ban.getMaBan().toLowerCase() : "";
                    if (!maBan.contains(searchText)) {
                        continue;
                    }
                }
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
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm dữ liệu: " + e.getMessage());
        }
    }
    private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
        loadFilteredData();
    }// GEN-LAST:event_cbFilterTrangThaiActionPerformed
    private void cbFilterKhuVucActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterKhuVucActionPerformed
        loadFilteredData();
    }// GEN-LAST:event_cbFilterKhuVucActionPerformed
    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaActionPerformed
        xoaAction();
    }// GEN-LAST:event_btnXoaActionPerformed
    private void xoaAction() {
        try {
            String maBan = txtMaBan.getText().trim();
            if (maBan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu đặt bàn để xóa", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            int result = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa phiếu đặt bàn " + maBan + " không?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                banService.xoaBan(maBan);
                JOptionPane.showMessageDialog(this, "Xóa bàn " + maBan +" thành công", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                loadDataToTable();
                refreshData();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCapNhatActionPerformed
        try {
            String maBan = txtMaBan.getText().trim();
            String soGheText = spSoGhe.getValue().toString();
            String viTri = txtViTri.getText().trim();
            String maKhuVuc = (String) cbKhuVuc.getSelectedItem();
            String trangThaiDisplay = (String) cbTrangThai.getSelectedItem();
            TrangThaiBan trangThai = ComboBoxEnumLoader.getTrangThaiBanFromDisplay(trangThaiDisplay);
            if (maBan.isEmpty() || soGheText.isEmpty() || viTri.isEmpty() || maKhuVuc == null || trangThai == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bàn.");
                return;
            }
            int soGhe = Integer.parseInt(soGheText);
            KhuVuc khuVuc = khuVucService.getKhuVucTheoMa(maKhuVuc);
            Ban ban = new Ban(maBan, soGhe, viTri, khuVuc, trangThai);
            banService.capNhatBan(ban);
            JOptionPane.showMessageDialog(this, "Cập nhật bàn thành công.");
            refreshData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số ghế không hợp lệ.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Cập nhật bàn thất bại: " + ex.getMessage());
        }
    }// GEN-LAST:event_btnCapNhatActionPerformed
    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
        try {
            String maBan = txtMaBan.getText().trim();
            String soGheText = spSoGhe.getValue().toString();
            String viTri = txtViTri.getText().trim();
            String maKhuVuc = (String) cbKhuVuc.getSelectedItem();
            String trangThaiDisplay = (String) cbTrangThai.getSelectedItem();
            TrangThaiBan trangThai = ComboBoxEnumLoader.getTrangThaiBanFromDisplay(trangThaiDisplay);
            if (maBan.isEmpty() || soGheText.isEmpty() || viTri.isEmpty() || maKhuVuc == null || trangThai == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bàn.");
                return;
            }
            int soGhe = Integer.parseInt(soGheText);
            KhuVuc khuVuc = khuVucService.getKhuVucTheoMa(maKhuVuc);
            Ban ban = new Ban(maBan, soGhe, viTri, khuVuc, trangThai);
            banService.themBan(ban);
            JOptionPane.showMessageDialog(this, "Thêm bàn thành công.");
            refreshData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số ghế không hợp lệ.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Thêm bàn thất bại: " + ex.getMessage());
        }
    }// GEN-LAST:event_btnThemActionPerformed
    private void txtMaBanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMaBanActionPerformed
    }// GEN-LAST:event_txtMaBanActionPerformed
    private void txtViTriActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtViTriActionPerformed
    }// GEN-LAST:event_txtViTriActionPerformed
    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        searchByText();
    }// GEN-LAST:event_btnTimKiemActionPerformed
    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTimKiemActionPerformed
        searchByText();
    }// GEN-LAST:event_txtTimKiemActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cbFilterKhuVuc;
    private javax.swing.JComboBox<String> cbFilterTrangThai;
    private javax.swing.JComboBox<String> cbKhuVuc;
    private javax.swing.JComboBox<String> cbTrangThai;
    private javax.swing.JLabel lblKhuVuc;
    private javax.swing.JLabel lblMaBan;
    private javax.swing.JLabel lblSoGhe;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JLabel lblViTri;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlRightButtons;
    private javax.swing.JPanel pnlThongTinBan;
    private javax.swing.JScrollPane scrTableBan;
    private javax.swing.JSpinner spSoGhe;
    private javax.swing.JTable tableBan;
    private javax.swing.JTextField txtMaBan;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtViTri;
    // End of variables declaration//GEN-END:variables
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    private void loadDataFromRow(int rowIndex) {
        try {
            String maBan = (String) tableBan.getValueAt(rowIndex, 0);
            Integer soGhe = (Integer) tableBan.getValueAt(rowIndex, 1);
            String viTri = (String) tableBan.getValueAt(rowIndex, 2);
            String maKhuVuc = (String) tableBan.getValueAt(rowIndex, 3);
            String trangThaiStr = (String) tableBan.getValueAt(rowIndex, 4);
            txtMaBan.setText(maBan);
            spSoGhe.setValue(soGhe != null ? (int) soGhe : 0);
            txtViTri.setText(viTri);
            cbKhuVuc.setSelectedItem(maKhuVuc);
            cbTrangThai.setSelectedItem(trangThaiStr);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi load dữ liệu từ row: " + e.getMessage());
        }
    }
    private void clearFields() {
        spSoGhe.setValue(0);
        txtViTri.setText("");
        cbKhuVuc.setSelectedIndex(0);
        cbTrangThai.setSelectedIndex(0);
    }
    private void syncCapNhatButtonState() {
        btnCapNhat.setEnabled(tableBan.getSelectedRow() >= 0);
        btnXoa.setEnabled(tableBan.getSelectedRow() >= 0);
    }
    public void refreshData() {
        clearFields();
        fillTxtMaBan(txtMaBan);
        resetPlaceholder(txtTimKiem, "Nhập mã bàn");
        cbFilterKhuVuc.setSelectedIndex(0);
        cbFilterTrangThai.setSelectedIndex(0);
        loadDataToComboBoxes();
        loadDataToTable();
        tableBan.clearSelection();
        syncCapNhatButtonState();
    }
    private boolean isMouseOverTable(java.awt.event.MouseEvent evt) {
        java.awt.Point p = evt.getPoint();
        java.awt.Point tablePoint = SwingUtilities.convertPoint(this, p, tableBan);
        return tableBan.getBounds().contains(tablePoint);
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
}