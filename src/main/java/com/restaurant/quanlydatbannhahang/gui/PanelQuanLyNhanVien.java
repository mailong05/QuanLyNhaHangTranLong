package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.NhanVienService;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEnumLoader;
import com.restaurant.quanlydatbannhahang.util.CurrencyUtility;
import com.restaurant.quanlydatbannhahang.util.IDGeneratorHelper;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;
import com.restaurant.quanlydatbannhahang.entity.ChucVu;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiNhanVien;
import java.util.List;
import java.time.LocalDate;

public class PanelQuanLyNhanVien extends javax.swing.JPanel implements MouseListener {
        public PanelQuanLyNhanVien() {
                initComponents();
                customUI();
                loadDataToComboBoxes();
                loadDataToTable();
        }

        private void customUI() {
            setupPlaceholder(txtTimKiem, "Nhập tên hoặc số điện thoại");
            fillTxtMaNhanVien();
            MainForm.attachGoHomeListener(btnTrangChu, this);
            if (dpNgayVaoLam != null) {
                    dpNgayVaoLam.setDate(LocalDate.now());
            }
            this.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mousePressed(java.awt.event.MouseEvent evt) {
                            if (evt.getSource() != tableNhanVien && !isMouseOverTable(evt)) {
                                    tableNhanVien.clearSelection();
                                    clearFields();
                                    fillTxtMaNhanVien();
                                    syncButtonState();
                            }
                    }
            });
            tableNhanVien.addMouseListener(this);
            tableNhanVien.getSelectionModel().addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                            int row = tableNhanVien.getSelectedRow();
                            if (row >= 0) {
                                    loadDataFromRow(row);
                            }
                            syncButtonState();
                    }
            });
            
            txtLuongCoBan.addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent evt) {
                            String currentText = txtLuongCoBan.getText();
                            if (!currentText.isEmpty()) {
                                    txtLuongCoBan.setText(currentText.replaceAll("[^\\d.]", ""));
                            }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent evt) {
                            String text = txtLuongCoBan.getText();
                            if (!text.isEmpty()) {
                                    double value = com.restaurant.quanlydatbannhahang.util.CurrencyUtility
                                                    .parseVND(text);
                                    txtLuongCoBan.setText(com.restaurant.quanlydatbannhahang.util.CurrencyUtility
                                                    .formatVND(value));
                            }
                    }
            });

            DefaultTableCellRenderer masterNhanVienRenderer = new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value,
                                    boolean isSelected, boolean hasFocus, int row, int column) {
                            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                            
                            int modelRow = table.convertRowIndexToModel(row);
                            Object statusValue = table.getModel().getValueAt(modelRow, 6); 
                            String status = statusValue != null ? statusValue.toString() : "";
                            String activeStatusStr = com.restaurant.quanlydatbannhahang.entity.TrangThaiNhanVien.DANG_LAM_VIEC.getDisplayName();
                            
                            if (!activeStatusStr.equals(status)) {
                                    c.setForeground(new Color(153, 153, 153));
                                    c.setFont(c.getFont().deriveFont(Font.ITALIC));
                            } else {
                                    if (!isSelected) {
                                            c.setForeground(Color.BLACK);
                                    }
                                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                            }
                            
                            if (column == 5) {
                                    if (value != null && value instanceof Number) {
                                            setText(com.restaurant.quanlydatbannhahang.util.CurrencyUtility
                                                            .formatVND(((Number) value).doubleValue()));
                                    }
                                    setHorizontalAlignment(JLabel.RIGHT); 
                            } else {
                                    setHorizontalAlignment(JLabel.CENTER); 
                            }
                            return c;
                    }
            };
            
            for (int i = 0; i < tableNhanVien.getColumnCount(); i++) {
                    tableNhanVien.getColumnModel().getColumn(i).setCellRenderer(masterNhanVienRenderer);
            }

            syncButtonState();
    }

        private void loadDataToComboBoxes() {
                try {
                        ActionListener[] chucVuListeners = cbFilterChucVu.getActionListeners();
                        for (ActionListener listener : chucVuListeners) {
                                cbFilterChucVu.removeActionListener(listener);
                        }
                        cbFilterChucVu.removeAllItems();
                        ComboBoxEnumLoader.loadChucVuToComboBox(cbFilterChucVu);
                        ComboBoxEnumLoader.loadTrangThaiNhanVienToComboBox(cbTrangThai);
                        cbChucVu.removeAllItems();
                        ComboBoxEnumLoader.loadChucVuToComboBox(cbChucVu);
                        for (ActionListener listener : chucVuListeners) {
                                cbFilterChucVu.addActionListener(listener);
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
                    NhanVienService service = new NhanVienService();
                    List<NhanVien> list = service.getAllNhanVien();
                    String selectedChucVu = (String) cbFilterChucVu.getSelectedItem();
                    DefaultTableModel model = (DefaultTableModel) tableNhanVien.getModel();
                    model.setRowCount(0);
                    for (NhanVien nv : list) {
                            
                            if (selectedChucVu != null && !selectedChucVu.equals("Chức vụ")) {
                                    if (nv.getChucVu() == null
                                                    || !nv.getChucVu().getDisplayName().equals(selectedChucVu)) {
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
                                            nv.getTrangThai() != null ? nv.getTrangThai().getDisplayName() : ""
                            });
                    }
            } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu: " + e.getMessage());
            }
    }

        private void searchByText() {
            try {
                    NhanVienService service = new NhanVienService();
                    List<NhanVien> list = service.getAllNhanVien();
                    String searchText = getActualSearchText();
                    String selectedChucVu = (String) cbFilterChucVu.getSelectedItem();
                    DefaultTableModel model = (DefaultTableModel) tableNhanVien.getModel();
                    model.setRowCount(0);
                    for (NhanVien nv : list) {
                            
                            if (selectedChucVu != null && !selectedChucVu.equals("Chức vụ")) {
                                    if (nv.getChucVu() == null
                                                    || !nv.getChucVu().getDisplayName().equals(selectedChucVu)) {
                                            continue;
                                    }
                            }
                            if (!searchText.isEmpty()) {
                                    String maNV = nv.getMaNV() != null ? nv.getMaNV().toLowerCase()
                                                    : "";
                                    String hoTen = nv.getHoTen() != null ? nv.getHoTen().toLowerCase() : "";
                                    String sdt = nv.getSdt() != null ? nv.getSdt().toLowerCase() : "";
                                    if (!maNV.contains(searchText) && !hoTen.contains(searchText)
                                                    && !sdt.contains(searchText)) {
                                            continue;
                                    }
                            }
                            model.addRow(new Object[] {
                                            nv.getMaNV(),
                                            nv.getHoTen(),
                                            nv.getSdt(),
                                            nv.getChucVu() != null ? nv.getChucVu().getDisplayName() : "",
                                            nv.getNgayVaoLam(),
                                            nv.getLuongCoBan(),
                                            nv.getTrangThai() != null ? nv.getTrangThai().getDisplayName() : ""
                            });
                    }
            } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm dữ liệu: " + e.getMessage());
            }
    }

        private String getActualSearchText() {
                String text = txtTimKiem.getText().trim();
                if (text.equals("Nhập tên hoặc số điện thoại")) {
                        return "";
                }
                return text.toLowerCase();
        }

        private void applyFilterAndSearch() {
                if (!getActualSearchText().isEmpty()) {
                        searchByText();
                } else {
                        loadFilteredData();
                }
        }

        private void loadDataFromRow(int rowIndex) {
                try {
                        String maNhanVien = tableNhanVien.getValueAt(rowIndex, 0).toString();
                        String hoTen = tableNhanVien.getValueAt(rowIndex, 1).toString();
                        String sdt = tableNhanVien.getValueAt(rowIndex, 2).toString();
                        String chucVu = tableNhanVien.getValueAt(rowIndex, 3).toString();
                        Object ngayVaoLamObj = tableNhanVien.getValueAt(rowIndex, 4);
                        LocalDate ngayVaoLam = LocalDate.parse(ngayVaoLamObj.toString());
                        Object luongObj = tableNhanVien.getValueAt(rowIndex, 5);
                        double luong = 0;
                        if (luongObj instanceof Number) {
                                luong = ((Number) luongObj).doubleValue();
                        }
                        String trangThai = tableNhanVien.getValueAt(rowIndex, 6).toString();
                        txtMaNhanVien.setText(maNhanVien);
                        txtHoTen.setText(hoTen);
                        cbChucVu.setSelectedItem(chucVu);
                        txtSoDienThoai.setText(sdt);
                        dpNgayVaoLam.setDate(ngayVaoLam);
                        txtLuongCoBan.setText(CurrencyUtility.formatVND(luong));
                        cbTrangThai.setSelectedItem(trangThai);
                } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("Lỗi chọn dòng: " + e.getMessage());
                }
        }

        private void clearFields() {
                txtHoTen.setText("");
                txtSoDienThoai.setText("");
                txtLuongCoBan.setText("");
                cbChucVu.setSelectedIndex(0);
                cbTrangThai.setSelectedIndex(0);
                if (dpNgayVaoLam != null) {
                        dpNgayVaoLam.setDate(LocalDate.now());
                }
        }

        private void fillTxtMaNhanVien() {
                String lastID = IDQueryHelper.getLastID("NhanVien", "maNV");
                String maNVNew = (lastID == null || lastID.isEmpty()) ? IDGeneratorHelper.generateDefaultID("NV")
                                : IDGeneratorHelper.generateNextIDFromFullID(lastID);
                txtMaNhanVien.setText(maNVNew);
        }

        public void refreshData() {
                clearFields();
                fillTxtMaNhanVien();
                resetPlaceholder(txtTimKiem, "Nhập tên hoặc số điện thoại");
                cbFilterChucVu.setSelectedIndex(0);
                loadDataToComboBoxes();
                loadDataToTable();
                tableNhanVien.clearSelection();
                syncButtonState();
        }
  

        private void syncButtonState() {
                btnCapNhat.setEnabled(tableNhanVien.getSelectedRow() >= 0);
                btnXoa.setEnabled(tableNhanVien.getSelectedRow() >= 0);
                btnThem.setEnabled(tableNhanVien.getSelectedRow() == -1);

        }

        private boolean isMouseOverTable(java.awt.event.MouseEvent evt) {
                java.awt.Point p = evt.getPoint();
                java.awt.Point tablePoint = SwingUtilities.convertPoint(this, p, tableNhanVien);
                return tableNhanVien.getBounds().contains(tablePoint);
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
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {
                pnlHeader = new javax.swing.JPanel();
                lblTitle = new javax.swing.JLabel();
                pnlThongTinNhanVien = new javax.swing.JPanel();
                lblMaNhanVien = new javax.swing.JLabel();
                txtMaNhanVien = new javax.swing.JTextField();
                lblHoTen = new javax.swing.JLabel();
                txtHoTen = new javax.swing.JTextField();
                lblChucVu = new javax.swing.JLabel();
                lblNgayVaoLam = new javax.swing.JLabel();
                lblSoDienThoai = new javax.swing.JLabel();
                txtSoDienThoai = new javax.swing.JTextField();
                txtTimKiem = new javax.swing.JTextField();
                btnTimKiem = new javax.swing.JButton();
                lblLuongCoBan = new javax.swing.JLabel();
                txtLuongCoBan = new javax.swing.JTextField();
                lblTrangThai = new javax.swing.JLabel();
                cbChucVu = new javax.swing.JComboBox<>();
                dpNgayVaoLam = new com.github.lgooddatepicker.components.DatePicker();
                cbTrangThai = new javax.swing.JComboBox<>();
                cbFilterChucVu = new javax.swing.JComboBox<>();
                scrNhanVien = new javax.swing.JScrollPane();
                tableNhanVien = new javax.swing.JTable();
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
                lblTitle.setText("Quản lý thông tin nhân viên trong hệ thống");
                pnlHeader.add(lblTitle, java.awt.BorderLayout.WEST);
                pnlThongTinNhanVien.setBackground(new java.awt.Color(255, 251, 233));
                lblMaNhanVien.setText("Mã nhân viên:");
                lblMaNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14));
                txtMaNhanVien.setEditable(false);
                txtMaNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14));
                txtMaNhanVien.setFocusable(false);
                txtMaNhanVien.setPreferredSize(new java.awt.Dimension(64, 30));
                txtMaNhanVien.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtMaNhanVienActionPerformed(evt);
                        }
                });
                lblHoTen.setText("Họ tên:");
                lblHoTen.setFont(new java.awt.Font("Segoe UI", 0, 14));
                txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 14));
                txtHoTen.setPreferredSize(new java.awt.Dimension(64, 30));
                lblChucVu.setText("Chức vụ:");
                lblChucVu.setFont(new java.awt.Font("Segoe UI", 0, 14));
                lblNgayVaoLam.setText("Ngày vào làm:");
                lblNgayVaoLam.setFont(new java.awt.Font("Segoe UI", 0, 14));
                lblSoDienThoai.setText("Số điện thoại:");
                lblSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 14));
                txtSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 14));
                txtSoDienThoai.setPreferredSize(new java.awt.Dimension(64, 30));
                txtSoDienThoai.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtSoDienThoaiActionPerformed(evt);
                        }
                });
                txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14));
                btnTimKiem.setText("Tìm kiếm");
                btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14));
                btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnTimKiemActionPerformed(evt);
                        }
                });
                lblLuongCoBan.setText("Lương cơ bản:");
                lblLuongCoBan.setFont(new java.awt.Font("Segoe UI", 0, 14));
                txtLuongCoBan.setFont(new java.awt.Font("Segoe UI", 0, 14));
                lblTrangThai.setText("Trạng thái:");
                lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14));
                cbChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Quản lý", "Thu ngân", "Phục vụ", "Bếp", " " }));
                cbChucVu.setPreferredSize(new java.awt.Dimension(72, 30));
                cbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang làm việc", " " }));
                cbTrangThai.setMaximumSize(new java.awt.Dimension(32767, 30));
                cbTrangThai.setMinimumSize(new java.awt.Dimension(72, 30));
                cbTrangThai.setPreferredSize(new java.awt.Dimension(72, 30));
                cbFilterChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
                cbFilterChucVu.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cbFilterChucVuActionPerformed(evt);
                        }
                });
                javax.swing.GroupLayout pnlThongTinNhanVienLayout = new javax.swing.GroupLayout(pnlThongTinNhanVien);
                pnlThongTinNhanVien.setLayout(pnlThongTinNhanVienLayout);
                pnlThongTinNhanVienLayout.setHorizontalGroup(
                                pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                                                                .addComponent(cbFilterChucVu,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                118,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(txtTimKiem,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                288,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(btnTimKiem,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                138,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                                                                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(pnlThongTinNhanVienLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(pnlThongTinNhanVienLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(lblSoDienThoai)
                                                                                                                .addComponent(lblTrangThai))
                                                                                                .addGap(18, 18, 18)
                                                                                                .addGroup(pnlThongTinNhanVienLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                .addGroup(pnlThongTinNhanVienLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(cbTrangThai,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                126,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGap(61, 61, 61)
                                                                                                                                .addComponent(lblChucVu)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(cbChucVu,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                141,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addComponent(txtSoDienThoai,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                387,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addGroup(pnlThongTinNhanVienLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(pnlThongTinNhanVienLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(lblMaNhanVien)
                                                                                                                .addComponent(lblHoTen,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                51,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(15, 15, 15)
                                                                                                .addGroup(pnlThongTinNhanVienLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(txtHoTen,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                387,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(txtMaNhanVien,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))))
                                                                .addGap(24, 24, 24)
                                                                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(pnlThongTinNhanVienLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(lblLuongCoBan)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(txtLuongCoBan,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                144,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(pnlThongTinNhanVienLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(lblNgayVaoLam)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(dpNgayVaoLam,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap(279, Short.MAX_VALUE)));
                pnlThongTinNhanVienLayout.setVerticalGroup(
                                pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                                                                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(lblMaNhanVien,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                26,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(txtMaNhanVien,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(lblNgayVaoLam)
                                                                                .addComponent(dpNgayVaoLam,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                26,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(pnlThongTinNhanVienLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(txtHoTen,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(lblLuongCoBan)
                                                                                                .addComponent(txtLuongCoBan,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addComponent(lblHoTen,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                26,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(txtSoDienThoai,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(lblSoDienThoai))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                pnlThongTinNhanVienLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(cbChucVu,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                30,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(lblChucVu,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                23,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(pnlThongTinNhanVienLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(cbTrangThai,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(lblTrangThai)))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                24, Short.MAX_VALUE)
                                                                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(txtTimKiem,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                35,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(btnTimKiem,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                35,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(cbFilterChucVu,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                35,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap()));
                pnlHeader.add(pnlThongTinNhanVien, java.awt.BorderLayout.PAGE_END);
                add(pnlHeader, java.awt.BorderLayout.PAGE_START);
                tableNhanVien.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                },
                                new String[] {
                                                "Mã nhân viên", "Họ tên", "Số điện thoại", "Chức vụ", "Ngày vào làm",
                                                "Lương cơ bản", "Trạng thái"
                                }) {
                        Class[] types = new Class[] {
                                        java.lang.String.class, java.lang.String.class, java.lang.String.class,
                                        java.lang.String.class, java.lang.Object.class, java.lang.Double.class,
                                        java.lang.String.class
                        };
                        boolean[] canEdit = new boolean[] {
                                        false, false, false, false, false, false, false
                        };

                        public Class getColumnClass(int columnIndex) {
                                return types[columnIndex];
                        }

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit[columnIndex];
                        }
                });
                tableNhanVien.setRowHeight(35);
                scrNhanVien.setViewportView(tableNhanVien);
                add(scrNhanVien, java.awt.BorderLayout.CENTER);
                pnlButton.setBackground(new java.awt.Color(255, 251, 233));
                pnlButton.setLayout(new java.awt.BorderLayout());
                btnTrangChu.setText("Trang Chủ");
                btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14));
                pnlButton.add(btnTrangChu, java.awt.BorderLayout.WEST);
                pnlRightButtons.setBackground(new java.awt.Color(255, 251, 233));
                pnlRightButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 15, 0));
                btnXoaTrang.setText("Xóa trắng");
                btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 0, 14));
                btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnXoaTrangActionPerformed(evt);
                        }
                });
                pnlRightButtons.add(btnXoaTrang);
                btnCapNhat.setText("Cập nhật");
                btnCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 14));
                btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnCapNhatActionPerformed(evt);
                        }
                });
                pnlRightButtons.add(btnCapNhat);
                btnXoa.setText("Xóa");
                btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14));
                btnXoa.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnXoaActionPerformed(evt);
                        }
                });
                pnlRightButtons.add(btnXoa);
                btnThem.setText("Thêm");
                btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14));
                btnThem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnThemActionPerformed(evt);
                        }
                });
                pnlRightButtons.add(btnThem);
                pnlButton.add(pnlRightButtons, java.awt.BorderLayout.EAST);
                add(pnlButton, java.awt.BorderLayout.PAGE_END);
        }// </editor-fold>//GEN-END:initComponents

        private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {
                refreshData();
        }

       
        private void cbFilterChucVuActionPerformed(java.awt.event.ActionEvent evt) {
                applyFilterAndSearch();
        }

        private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {
                String maNV = txtMaNhanVien.getText().trim();
                if (maNV.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để cho nghỉ việc.");
                        return;
                }
                com.restaurant.quanlydatbannhahang.entity.NhanVien currentNhanVien = com.restaurant.quanlydatbannhahang.session.SessionManager
                                .getCurrentNhanVien();
                if (currentNhanVien != null && currentNhanVien.getMaNV().equals(maNV)) {
                        JOptionPane.showMessageDialog(this, "Bạn không thể tự cho chính mình nghỉ việc!",
                                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                }
               
                try {
                    NhanVienService service = new NhanVienService();
                    
                    if (service.kiemTraNhanVienCoRangBuocActive(maNV)) {
                            JOptionPane.showMessageDialog(this, 
                                    "Không thể cho nhân viên này nghỉ việc vì:\n" +
                                    "- Họ đang trong một ca làm việc chưa kết ca.\n" +
                                    "- Hoặc họ đang chịu trách nhiệm phiếu đặt bàn ở trạng thái 'Đang chờ'.\n\n" +
                                    "Vui lòng kiểm tra lại tình trạng kết ca hoặc bàn giao phiếu đặt trước!", 
                                    "Lỗi ràng buộc nghiệp vụ", 
                                    JOptionPane.ERROR_MESSAGE);
                            return; 
                    }
                    
                    int choice = JOptionPane.showConfirmDialog(this,
                                    "Bạn có chắc chắn muốn cho nhân viên này nghỉ việc không?",
                                    "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (choice != JOptionPane.YES_OPTION) {
                            return;
                    }
                    
                    service.xoaNhanVien(maNV);
                    JOptionPane.showMessageDialog(this,
                                    "Đã cập nhật trạng thái nhân viên sang nghỉ việc thành công.");
                    refreshData();
            } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                                    "Cập nhật trạng thái nhân viên thất bại: " + ex.getMessage());
            }
        }

        private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {
                try {
                        String maNV = txtMaNhanVien.getText().trim();
                        String hoTen = txtHoTen.getText().trim();
                        String sdt = txtSoDienThoai.getText().trim();
                        double luongCoBan = com.restaurant.quanlydatbannhahang.util.CurrencyUtility
                                        .parseVND(txtLuongCoBan.getText().trim());
                        String chucVuDisplay = (String) cbChucVu.getSelectedItem();
                        String trangThaiDisplay = (String) cbTrangThai.getSelectedItem();
                        ChucVu chucVu = ComboBoxEnumLoader.getChucVuFromDisplay(chucVuDisplay);
                        TrangThaiNhanVien trangThai = ComboBoxEnumLoader
                                        .getTrangThaiNhanVienFromDisplay(trangThaiDisplay);
                        LocalDate ngayVaoLam = dpNgayVaoLam != null ? dpNgayVaoLam.getDate() : LocalDate.now();
                        if (maNV.isEmpty() || hoTen.isEmpty() || sdt.isEmpty() || luongCoBan <= 0 || chucVu == null
                                        || trangThai == null || ngayVaoLam == null) {
                                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin nhân viên.");
                                return;
                        }
                        NhanVien nhanVien = new NhanVien(maNV, hoTen, sdt, chucVu, ngayVaoLam, luongCoBan, trangThai);
                        NhanVienService service = new NhanVienService();
                        service.capNhatNhanVien(nhanVien);
                        JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công.");
                        refreshData();
                } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Lương cơ bản không hợp lệ.");
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thất bại: " + ex.getMessage());
                }
        }

        private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {
                try {
                        String maNV = txtMaNhanVien.getText().trim();
                        String hoTen = txtHoTen.getText().trim();
                        String sdt = txtSoDienThoai.getText().trim();
                        String luongText = txtLuongCoBan.getText().trim();
                        String chucVuStr = (String) cbChucVu.getSelectedItem();
                        ChucVu chucVu = ComboBoxEnumLoader
                                        .getChucVuFromDisplay(chucVuStr);
                        LocalDate ngayVaoLam = dpNgayVaoLam.getDate();
                        String trangThaiStr = (String) cbTrangThai.getSelectedItem();
                        TrangThaiNhanVien trangThai = TrangThaiNhanVien.fromDisplayName(trangThaiStr);
                        double luongCoBan = com.restaurant.quanlydatbannhahang.util.CurrencyUtility.parseVND(luongText);
                        NhanVien nhanVien = new NhanVien(maNV, hoTen, sdt, chucVu, ngayVaoLam, luongCoBan, trangThai);
                        NhanVienService service = new NhanVienService();
                        service.themNhanVien(nhanVien);
                        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công.");
                        refreshData();
                } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Lương cơ bản không hợp lệ.");
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại: " + ex.getMessage());
                }
        }

        private void txtMaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {
        }

        private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
                applyFilterAndSearch();
        }

        private void txtSoDienThoaiActionPerformed(java.awt.event.ActionEvent evt) {
        }

        private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
                applyFilterAndSearch();
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnCapNhat;
        private javax.swing.JButton btnThem;
        private javax.swing.JButton btnTimKiem;
        private javax.swing.JButton btnTrangChu;
        private javax.swing.JButton btnXoa;
        private javax.swing.JButton btnXoaTrang;
        private javax.swing.JComboBox<String> cbChucVu;
        private javax.swing.JComboBox<String> cbFilterChucVu;
        private javax.swing.JComboBox<String> cbTrangThai;
        private com.github.lgooddatepicker.components.DatePicker dpNgayVaoLam;
        private javax.swing.JLabel lblChucVu;
        private javax.swing.JLabel lblHoTen;
        private javax.swing.JLabel lblLuongCoBan;
        private javax.swing.JLabel lblMaNhanVien;
        private javax.swing.JLabel lblNgayVaoLam;
        private javax.swing.JLabel lblSoDienThoai;
        private javax.swing.JLabel lblTitle;
        private javax.swing.JLabel lblTrangThai;
        private javax.swing.JPanel pnlButton;
        private javax.swing.JPanel pnlHeader;
        private javax.swing.JPanel pnlRightButtons;
        private javax.swing.JPanel pnlThongTinNhanVien;
        private javax.swing.JScrollPane scrNhanVien;
        private javax.swing.JTable tableNhanVien;
        private javax.swing.JTextField txtHoTen;
        private javax.swing.JTextField txtLuongCoBan;
        private javax.swing.JTextField txtMaNhanVien;
        private javax.swing.JTextField txtSoDienThoai;
        private javax.swing.JTextField txtTimKiem;

        // End of variables declaration//GEN-END:variables
        @Override
        public void mouseClicked(MouseEvent e) {
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