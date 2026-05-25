package com.restaurant.quanlydatbannhahang.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.NhanVienService;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import java.util.List;
public class PanelDanhSachNhanVien extends javax.swing.JPanel {
    private NhanVienService nhanVienService;
    private List<NhanVien> allNhanVien;
    public PanelDanhSachNhanVien() {
        initComponents();
        nhanVienService = new NhanVienService();
        customUI();
        loadDataToComboBoxes();
        loadDataToTable();
    }
    private void customUI() {
        setupPlaceholder(txtTimKiem, "Nhập tên hoặc số điện thoại");
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.getSource() != tableNhanVien && !isMouseOverTable(evt)) {
                    tableNhanVien.clearSelection();
                    refreshData();
                }
            }
        });
        tableNhanVien.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                    if (value != null && value instanceof Number) {
                            value = com.restaurant.quanlydatbannhahang.util.CurrencyUtility
                                            .formatVND(((Number) value).doubleValue());
                    }
                    setHorizontalAlignment(JLabel.CENTER);
                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                                    column);
            }
    });
        MainForm.attachGoHomeListener(btnTrangChu, this);
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
    private boolean isMouseOverTable(java.awt.event.MouseEvent evt) {
        java.awt.Point p = evt.getPoint();
        java.awt.Point tablePoint = SwingUtilities.convertPoint(this, p, tableNhanVien);
        return tableNhanVien.getBounds().contains(tablePoint);
    }
    private void loadDataToComboBoxes() {
        try {
            ActionListener[] chucVuListeners = cbFilterChucVu.getActionListeners();
            for (ActionListener listener : chucVuListeners) {
                cbFilterChucVu.removeActionListener(listener);
            }
            cbFilterChucVu.removeAllItems();
            cbFilterChucVu.addItem("Chức vụ");
            for (com.restaurant.quanlydatbannhahang.entity.ChucVu chucVu : com.restaurant.quanlydatbannhahang.entity.ChucVu
                    .values()) {
                cbFilterChucVu.addItem(chucVu.getDisplayName());
            }
            for ( ActionListener listener : chucVuListeners) {
                cbFilterChucVu.addActionListener(listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Loi load du lieu filter: " + e.getMessage());
        }
    }
    private void loadDataToTable() {
        try {
            allNhanVien = nhanVienService.getAllNhanVien();
            DefaultTableModel model = (DefaultTableModel) tableNhanVien.getModel();
            model.setRowCount(0);
            for (NhanVien nv : allNhanVien) {
                model.addRow(new Object[] {
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getSdt(),
                        nv.getChucVu().getDisplayName(),
                        nv.getNgayVaoLam(),
                        nv.getLuongCoBan(),
                        nv.getTrangThai().getDisplayName()
                });
            }
            centerTableColumns(tableNhanVien);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void centerTableColumns(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (i != 5) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlHeader = new javax.swing.JPanel();
        pnlThongTinNhanVien = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        cbFilterChucVu = new javax.swing.JComboBox<>();
        scrTableNhanVien = new javax.swing.JScrollPane();
        tableNhanVien = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        btnXoaTrang = new javax.swing.JButton();
        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout(0, 10));
        pnlHeader.setOpaque(false);
        pnlHeader.setLayout(new java.awt.BorderLayout(0, 15));
        pnlThongTinNhanVien.setBackground(new java.awt.Color(255, 251, 233));
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
        cbFilterChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chức vụ", "Quản lý", "Thu ngân", "Phục vụ", "Bếp", " " }));
        cbFilterChucVu.setPreferredSize(new java.awt.Dimension(72, 35));
        cbFilterChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterChucVuActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout pnlThongTinNhanVienLayout = new javax.swing.GroupLayout(pnlThongTinNhanVien);
        pnlThongTinNhanVien.setLayout(pnlThongTinNhanVienLayout);
        pnlThongTinNhanVienLayout.setHorizontalGroup(
            pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinNhanVienLayout.createSequentialGroup()
                .addComponent(cbFilterChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 452, Short.MAX_VALUE)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlThongTinNhanVienLayout.setVerticalGroup(
            pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbFilterChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        pnlHeader.add(pnlThongTinNhanVien, java.awt.BorderLayout.PAGE_END);
        add(pnlHeader, java.awt.BorderLayout.PAGE_START);
        tableNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Mã nhân viên", "Họ tên", "Số điện thoại", "Chức vụ", "Ngày vào làm", "Lương cơ bản", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.String.class
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
        tableNhanVien.setRowHeight(35);
        scrTableNhanVien.setViewportView(tableNhanVien);
        add(scrTableNhanVien, java.awt.BorderLayout.CENTER);
        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setLayout(new java.awt.BorderLayout());
        btnTrangChu.setText("Trang Chủ");
        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        pnlButton.add(btnTrangChu, java.awt.BorderLayout.WEST);
        btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });
        pnlButton.add(btnXoaTrang, java.awt.BorderLayout.EAST);
        add(pnlButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents
    private void btnXoaTrangActionPerformed( ActionEvent evt) {
        refreshData();
    }
    private void btnTimKiemActionPerformed( ActionEvent evt) {
        searchByText();
    }
    private void cbFilterChucVuActionPerformed( ActionEvent evt) {
        filterTable();
    }
    private void txtTimKiemActionPerformed( ActionEvent evt) {
        searchByText();
    }
    private void filterByComboBoxes() {
        DefaultTableModel model = (DefaultTableModel) tableNhanVien.getModel();
        model.setRowCount(0);
        String selectedChucVu = (String) cbFilterChucVu.getSelectedItem();
        for (NhanVien nv : allNhanVien) {
            if (selectedChucVu != null && !selectedChucVu.equals("Chức vụ")) {
                if (!nv.getChucVu().getDisplayName().equals(selectedChucVu)) {
                    continue;
                }
            }
            model.addRow(new Object[] {
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getSdt(),
                    nv.getChucVu().getDisplayName(),
                    nv.getNgayVaoLam(),
                    nv.getLuongCoBan(),
                    nv.getTrangThai().getDisplayName()
            });
        }
        centerTableColumns(tableNhanVien);
    }
    private void searchByText() {
        DefaultTableModel model = (DefaultTableModel) tableNhanVien.getModel();
        model.setRowCount(0);
        String searchText = txtTimKiem.getText().trim().toLowerCase();
        String selectedChucVu = (String) cbFilterChucVu.getSelectedItem();
        for (NhanVien nv : allNhanVien) {
            if (selectedChucVu != null && !selectedChucVu.equals("Chức vụ")) {
                if (!nv.getChucVu().getDisplayName().equals(selectedChucVu)) {
                    continue;
                }
            }
            String tenNV = nv.getHoTen() != null ? nv.getHoTen().toLowerCase() : "";
            String sdtNV = nv.getSdt() != null ? nv.getSdt().trim() : "";
            String maNV = nv.getMaNV() != null ? nv.getMaNV().toLowerCase() : "";
            if (!searchText.isEmpty()
                    && !tenNV.contains(searchText)
                    && !sdtNV.contains(searchText)
                    && !maNV.contains(searchText)) {
                continue;
            }
            model.addRow(new Object[] {
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getSdt(),
                    nv.getChucVu().getDisplayName(),
                    nv.getNgayVaoLam(),
                    nv.getLuongCoBan(),
                    nv.getTrangThai().getDisplayName()
            });
        }
        centerTableColumns(tableNhanVien);
    }
    private void filterTable() {
        filterByComboBoxes();
    }
    public void refreshData() {
        resetPlaceholder(txtTimKiem, "Nhập tên hoặc số điện thoại");
        cbFilterChucVu.setSelectedIndex(0);
        filterByComboBoxes();
        loadDataToTable();
        tableNhanVien.clearSelection();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cbFilterChucVu;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlThongTinNhanVien;
    private javax.swing.JScrollPane scrTableNhanVien;
    private javax.swing.JTable tableNhanVien;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}