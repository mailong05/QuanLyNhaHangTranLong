package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.NhanVienService;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import java.util.List;
import java.time.LocalDate;

public class PanelQuanLyNhanVien extends javax.swing.JPanel implements MouseListener {

        private ActionListener cbFilterChucVuListener;

        public PanelQuanLyNhanVien() {
                initComponents();
                customUI();
                loadDataToComboBoxes();
                loadDataToTable();
        }

        private void customUI() {
                // Placeholder cho txtTimKiem
                setupPlaceholder(txtTimKiem, "Nhập tên hoặc số điện thoại");

                // Set giá trị mặc định cho DatePicker (ngày hôm nay)
                if (dpNgayVaoLam != null) {
                        dpNgayVaoLam.setDate(LocalDate.now());
                }

                // ========== DESELECT WHEN CLICK OUTSIDE TABLE ==========
                this.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                                if (evt.getSource() != tableKhuVuc && !isMouseOverTable(evt)) {
                                        tableKhuVuc.clearSelection();
                                        clearFields();
                                }
                        }
                });

                // Register mouse listener để populate fields khi click vào row
                tableKhuVuc.addMouseListener(this);
        }

        private void loadDataToComboBoxes() {
                try {
                        // Save listeners
                        ActionListener[] chucVuListeners = cbFilterChucVu.getActionListeners();

                        // Remove listeners
                        for (ActionListener listener : chucVuListeners) {
                                cbFilterChucVu.removeActionListener(listener);
                        }

                        // Load ChucVu
                        cbFilterChucVu.removeAllItems();
                        cbFilterChucVu.addItem("-- Tất cả --");
                        cbChucVu.removeAllItems();
                        cbChucVu.addItem("Quản lý");
                        cbChucVu.addItem("Nhân viên");
                        cbChucVu.addItem("Đầu bếp");

                        cbFilterChucVu.addItem("Quản lý");
                        cbFilterChucVu.addItem("Nhân viên");
                        cbFilterChucVu.addItem("Đầu bếp");

                        // Re-add listeners
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

                        DefaultTableModel model = (DefaultTableModel) tableKhuVuc.getModel();
                        model.setRowCount(0);

                        for (NhanVien nv : list) {
                                // Apply ChucVu filter
                                if (selectedChucVu != null && !selectedChucVu.equals("-- Tất cả --")) {
                                        if (nv.getChucVu() == null || !nv.getChucVu().equals(selectedChucVu)) {
                                                continue;
                                        }
                                }

                                model.addRow(new Object[] {
                                                nv.getMaNV(),
                                                nv.getHoTen(),
                                                nv.getChucVu(),
                                                nv.getNgayVaoLam(),
                                                nv.getSdt(),
                                                nv.getLuongCoBan(),
                                                nv.getTrangThai() != null ? nv.getTrangThai().getDisplayName() : ""
                                });
                        }
                        centerTableColumns(tableKhuVuc);
                } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu: " + e.getMessage());
                }
        }

        private void searchByText() {
                try {
                        NhanVienService service = new NhanVienService();
                        List<NhanVien> list = service.getAllNhanVien();
                        String searchText = txtTimKiem.getText().trim().toLowerCase();
                        String selectedChucVu = (String) cbFilterChucVu.getSelectedItem();

                        DefaultTableModel model = (DefaultTableModel) tableKhuVuc.getModel();
                        model.setRowCount(0);

                        for (NhanVien nv : list) {
                                // Apply ChucVu filter
                                if (selectedChucVu != null && !selectedChucVu.equals("-- Tất cả --")) {
                                        if (nv.getChucVu() == null || !nv.getChucVu().equals(selectedChucVu)) {
                                                continue;
                                        }
                                }

                                // Apply search text filter
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
                                                nv.getChucVu(),
                                                nv.getNgayVaoLam(),
                                                nv.getSdt(),
                                                nv.getLuongCoBan(),
                                                nv.getTrangThai() != null ? nv.getTrangThai().getDisplayName() : ""
                                });
                        }
                        centerTableColumns(tableKhuVuc);
                } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm dữ liệu: " + e.getMessage());
                }
        }

        private void loadDataFromRow(int rowIndex) {
                try {
                        String maNhanVien = (String) tableKhuVuc.getValueAt(rowIndex, 0);
                        String hoTen = (String) tableKhuVuc.getValueAt(rowIndex, 1);
                        String chucVu = (String) tableKhuVuc.getValueAt(rowIndex, 2);
                        Object ngayVaoLamObj = tableKhuVuc.getValueAt(rowIndex, 3);
                        String sdt = (String) tableKhuVuc.getValueAt(rowIndex, 4);
                        Object luongObj = tableKhuVuc.getValueAt(rowIndex, 5);

                        txtMaNhanVien.setText(maNhanVien);
                        txtHoTen.setText(hoTen);
                        cbChucVu.setSelectedItem(chucVu);
                        txtSoDienThoai.setText(sdt);
                        if (luongObj != null) {
                                txtLuongCoBan.setText(luongObj.toString());
                        } else {
                                txtLuongCoBan.setText("");
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi khi load dữ liệu từ row: " + e.getMessage());
                }
        }

        private void clearFields() {
                txtMaNhanVien.setText("");
                txtHoTen.setText("");
                txtSoDienThoai.setText("");
                txtLuongCoBan.setText("");
                cbChucVu.setSelectedIndex(0);
                if (dpNgayVaoLam != null) {
                        dpNgayVaoLam.setDate(LocalDate.now());
                }
        }

        private boolean isMouseOverTable(java.awt.event.MouseEvent evt) {
                java.awt.Point p = evt.getPoint();
                java.awt.Point tablePoint = SwingUtilities.convertPoint(this, p, tableKhuVuc);
                return tableKhuVuc.getBounds().contains(tablePoint);
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

        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
        scrTableKhuVuc = new javax.swing.JScrollPane();
        tableKhuVuc = new javax.swing.JTable();
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

        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitle.setText("Quản lý thông tin nhân viên trong hệ thống");
        pnlHeader.add(lblTitle, java.awt.BorderLayout.WEST);

        pnlThongTinNhanVien.setBackground(new java.awt.Color(255, 251, 233));

        lblMaNhanVien.setText("Mã nhân viên:");
        lblMaNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtMaNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaNhanVien.setPreferredSize(new java.awt.Dimension(64, 30));
        txtMaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNhanVienActionPerformed(evt);
            }
        });

        lblHoTen.setText("Họ tên:");
        lblHoTen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtHoTen.setPreferredSize(new java.awt.Dimension(64, 30));

        lblChucVu.setText("Chức vụ:");
        lblChucVu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblNgayVaoLam.setText("Ngày vào làm:");
        lblNgayVaoLam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblSoDienThoai.setText("Số điện thoại:");
        lblSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoDienThoai.setPreferredSize(new java.awt.Dimension(64, 30));
        txtSoDienThoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoDienThoaiActionPerformed(evt);
            }
        });

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        lblLuongCoBan.setText("Lương cơ bản:");
        lblLuongCoBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtLuongCoBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblTrangThai.setText("Trạng thái:");
        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        cbChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quản lý", "Thu ngân", "Phục vụ", "Bếp", " " }));
        cbChucVu.setPreferredSize(new java.awt.Dimension(72, 30));

        cbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang làm việc", " " }));
        cbTrangThai.setMaximumSize(new java.awt.Dimension(32767, 30));
        cbTrangThai.setMinimumSize(new java.awt.Dimension(72, 30));
        cbTrangThai.setPreferredSize(new java.awt.Dimension(72, 30));

        cbFilterChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
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
                .addComponent(cbFilterChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                        .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSoDienThoai)
                            .addComponent(lblTrangThai))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                                .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(lblChucVu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                        .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaNhanVien)
                            .addComponent(lblHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                            .addComponent(txtMaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(24, 24, 24)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                        .addComponent(lblLuongCoBan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtLuongCoBan, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                        .addComponent(lblNgayVaoLam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dpNgayVaoLam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(279, Short.MAX_VALUE))
        );
        pnlThongTinNhanVienLayout.setVerticalGroup(
            pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNgayVaoLam)
                    .addComponent(dpNgayVaoLam, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblLuongCoBan)
                        .addComponent(txtLuongCoBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSoDienThoai))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTrangThai)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFilterChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlHeader.add(pnlThongTinNhanVien, java.awt.BorderLayout.PAGE_END);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        tableKhuVuc.setModel(new javax.swing.table.DefaultTableModel(
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
        tableKhuVuc.setRowHeight(35);
        scrTableKhuVuc.setViewportView(tableKhuVuc);

        add(scrTableKhuVuc, java.awt.BorderLayout.CENTER);

        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setLayout(new java.awt.BorderLayout());

        btnTrangChu.setText("Trang Chủ");
        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlButton.add(btnTrangChu, java.awt.BorderLayout.WEST);

        pnlRightButtons.setBackground(new java.awt.Color(255, 251, 233));
        pnlRightButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 15, 0));

        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnXoaTrang);

        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnCapNhat);

        btnXoa.setText("Xóa");
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnXoa);

        btnThem.setText("Thêm");
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnThem);

        pnlButton.add(pnlRightButtons, java.awt.BorderLayout.EAST);

        add(pnlButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaTrangActionPerformed

        private void centerTableColumns(JTable table) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for (int i = 0; i < table.getColumnCount(); i++) {
                        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }
        }

        private void cbFilterChucVuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterChucVuActionPerformed
                loadFilteredData();
        }// GEN-LAST:event_cbFilterChucVuActionPerformed

        private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_btnXoaActionPerformed

        private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCapNhatActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_btnCapNhatActionPerformed

        private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_btnThemActionPerformed

        private void txtMaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMaNhanVienActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtMaNhanVienActionPerformed

        private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
                searchByText();
        }// GEN-LAST:event_btnTimKiemActionPerformed

        private void txtSoDienThoaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtSoDienThoaiActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtSoDienThoaiActionPerformed

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
    private javax.swing.JScrollPane scrTableKhuVuc;
    private javax.swing.JTable tableKhuVuc;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtLuongCoBan;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
        @Override
        public void mouseClicked(MouseEvent e) {
                if (e.getSource() == tableKhuVuc) {
                        int row = tableKhuVuc.getSelectedRow();
                        if (row >= 0) {
                                loadDataFromRow(row);
                        }
                }
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
