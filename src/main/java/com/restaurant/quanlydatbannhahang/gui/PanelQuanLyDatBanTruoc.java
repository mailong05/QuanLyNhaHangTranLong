package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.restaurant.quanlydatbannhahang.service.ChiTietPhieuDatBanService;
import com.restaurant.quanlydatbannhahang.service.PhieuDatBanService;
import com.restaurant.quanlydatbannhahang.service.BanService;

import com.restaurant.quanlydatbannhahang.entity.PhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiPhieuDat;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import com.restaurant.quanlydatbannhahang.entity.ChiTietPhieuDatBan;
import java.util.List;

public class PanelQuanLyDatBanTruoc extends javax.swing.JPanel implements MouseListener {

        private PhieuDatBanService pdbService;
        private ChiTietPhieuDatBanService ctpdbService;
        private PanelDatBan panelDatBan; // ← Reference để update UI bàn

        public PanelQuanLyDatBanTruoc() {
                pdbService = new PhieuDatBanService();
                ctpdbService = new ChiTietPhieuDatBanService();
                this.panelDatBan = null; // Mặc định null (có thể set sau via setPanelDatBan)
                initComponents();
                customUI();
                loadDataToTable();
                pushDataToComboBox(cbFilterTrangThai);
                pushDataToComboBox(cbTrangThai);
        }

        /**
         * Setter để set PanelDatBan reference (gọi từ MainForm)
         * 
         * @param panelDatBan Reference tới PanelDatBan để update UI bàn
         */
        public void setPanelDatBan(PanelDatBan panelDatBan) {
                this.panelDatBan = panelDatBan;
        }

        /**
         * PUBLIC METHOD: Reload lại data table phiếu đặt bàn từ DB
         * Gọi khi DatBanTruocDialog hoàn tất (btnDatBan click) để cập nhật data
         */
        public void refreshData() {
                try {
                        loadDataToTable();
                        clearFields();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        private void customUI() {
                // Placeholder cho txtTimKiem
                setupPlaceholder(txtTimKiem, "Nhập mã đặt bàn hoặc SĐT khách");

                // Set giá trị mặc định cho DateTimePicker (ngày giờ hiện tại)
                if (dtpThoiGianDen != null) {
                        dtpThoiGianDen.setDateTimeStrict(LocalDateTime.now());
                }

                // Gắn sự kiện quay về Trang Chủ
                MainForm.attachGoHomeListener(btnTrangChu, this);

                // ========== ENABLE BUTTON KHI CLICK RA NGOÀI TABLE ==========
                this.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                                // Nếu click không phải trên table thì enable button
                                if (evt.getSource() != tableBan && !isMouseOverTable(evt)) {
                                        tableBan.clearSelection();
                                        clearFields(); // Clear dữ liệu các field
                                        btnCapNhat.setEnabled(false); // Enable button
                                }
                        }
                });
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

        private void pushDataToComboBox(JComboBox<String> cb) {
                cb.removeAllItems();
                cb.addItem("-- Tất cả --");

                for (TrangThaiPhieuDat trangThai : TrangThaiPhieuDat.values()) {
                        cb.addItem(trangThai.getDisplayName());
                }
        }

        private void loadDataToTable() {
                ArrayList<PhieuDatBan> listPDB = (ArrayList<PhieuDatBan>) pdbService.getAllPhieuDatBan();
                DefaultTableModel model = (DefaultTableModel) tableBan.getModel();
                model.setRowCount(0);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                for (PhieuDatBan p : listPDB) {

                        if (p.getTrangThai() == TrangThaiPhieuDat.DA_HUY
                                        || p.getTrangThai() == TrangThaiPhieuDat.DA_SU_DUNG)
                                continue;
                        String thoiGian = p.getThoiGianDen().format(formatter);
                        String listMaBan = ctpdbService.getChiTietByMaPhieuDat(p.getMaPhieuDat())
                                        .stream()
                                        .map(ct -> ct.getBan().getMaBan())
                                        .collect(Collectors.joining(", "));

                        model.addRow(new Object[] {
                                        p.getMaPhieuDat(),
                                        p.getKhachHang().getSdt(),
                                        p.getNhanVien().getMaNV(),
                                        listMaBan,
                                        p.getSoLuongNguoi(),
                                        thoiGian,
                                        p.getTrangThai().getDisplayName()
                        });
                }

                // ========== SET CELL ALIGNMENT (căn trái cho tất cả cột) ==========
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(JLabel.LEFT);

                tableBan.getColumnModel().getColumn(4).setCellRenderer(renderer);

                // ========== THÊM MOUSE LISTENER VÀO TABLE ==========
                tableBan.addMouseListener(this);
        }

        private boolean isMouseOverTable(java.awt.event.MouseEvent evt) {
                java.awt.Point p = evt.getPoint();
                java.awt.Point tablePoint = javax.swing.SwingUtilities.convertPoint(this, p, tableBan);
                return tableBan.getBounds().contains(tablePoint);
        }

        private void clearFields() {
                txtSoDienThoai.setText("");
                txtMaNhanVien.setText("");
                txtMaBan.setText("");
                spSoNguoi.setValue(0);
                dtpThoiGianDen.setDateTimeStrict(LocalDateTime.now());
                cbTrangThai.setSelectedIndex(0); // Reset ComboBox
                
        }

        // Từ đây không chỉnh sửa bên dưới
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
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
        lblMaPhieuDat = new javax.swing.JLabel();
        txtMaPhieuDat = new javax.swing.JTextField();
        lblMaKhachHang = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        lblThoiGianDen = new javax.swing.JLabel();
        lblGhiChu = new javax.swing.JLabel();
        lblMaNhanVien = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        lblSoLuong = new javax.swing.JLabel();
        dtpThoiGianDen = new com.github.lgooddatepicker.components.DateTimePicker();
        cbTrangThai = new javax.swing.JComboBox<>();
        txtMaBan = new javax.swing.JTextField();
        lblMaBan = new javax.swing.JLabel();
        spSoNguoi = new javax.swing.JSpinner();
        cbFilterTrangThai = new javax.swing.JComboBox<>();
        scrTableBan = new javax.swing.JScrollPane();
        tableBan = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        pnlRightButtons = new javax.swing.JPanel();
        btnXoaTrang = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnChonMon = new javax.swing.JButton();
        btnChonBan = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout(0, 10));

        pnlHeader.setOpaque(false);
        pnlHeader.setLayout(new java.awt.BorderLayout(0, 15));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitle.setText("Quản lý thông tin bàn ăn trong nhà hàng");
        pnlHeader.add(lblTitle, java.awt.BorderLayout.WEST);

        pnlThongTinBan.setBackground(new java.awt.Color(255, 251, 233));

        lblMaPhieuDat.setText("Mã phiếu đặt:");
        lblMaPhieuDat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtMaPhieuDat.setEditable(false);
        txtMaPhieuDat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaPhieuDat.setFocusable(false);
        txtMaPhieuDat.setPreferredSize(new java.awt.Dimension(0, 35));
        txtMaPhieuDat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaPhieuDatActionPerformed(evt);
            }
        });

        lblMaKhachHang.setText("Số điện thoại");
        lblMaKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoDienThoai.setPreferredSize(new java.awt.Dimension(0, 35));

        lblThoiGianDen.setText("Thời gian đến:");
        lblThoiGianDen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblGhiChu.setText("Trạng thái:");
        lblGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblMaNhanVien.setText("Mã nhân viên:");
        lblMaNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtMaNhanVien.setEditable(false);
        txtMaNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaNhanVien.setPreferredSize(new java.awt.Dimension(0, 35));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        lblSoLuong.setText("Số người:");
        lblSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        dtpThoiGianDen.setPreferredSize(new java.awt.Dimension(228, 35));

        cbTrangThai.setPreferredSize(new java.awt.Dimension(72, 35));

        txtMaBan.setEditable(false);
        txtMaBan.setPreferredSize(new java.awt.Dimension(0, 35));
        txtMaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaBanActionPerformed(evt);
            }
        });

        lblMaBan.setText("Mã bàn:");
        lblMaBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

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
                        .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinBanLayout.createSequentialGroup()
                                .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                        .addComponent(lblMaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(31, 31, 31))
                                    .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                        .addComponent(lblMaBan)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtMaBan, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)))
                            .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMaPhieuDat)
                                    .addComponent(lblMaKhachHang))
                                .addGap(33, 33, 33)
                                .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                                    .addComponent(txtMaPhieuDat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(69, 69, 69)
                        .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblThoiGianDen, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGhiChu)
                            .addComponent(lblSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinBanLayout.createSequentialGroup()
                        .addComponent(txtTimKiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                        .addComponent(dtpThoiGianDen, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(180, Short.MAX_VALUE))
                    .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                        .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbTrangThai, 0, 127, Short.MAX_VALUE)
                            .addComponent(spSoNguoi))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnlThongTinBanLayout.setVerticalGroup(
            pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                        .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblThoiGianDen, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtpThoiGianDen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblGhiChu)
                                .addComponent(cbTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                        .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaPhieuDat, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaPhieuDat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSoLuong)
                        .addComponent(spSoNguoi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblMaNhanVien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaBan)
                    .addComponent(txtMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlHeader.add(pnlThongTinBan, java.awt.BorderLayout.PAGE_END);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        tableBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phiếu đặt", "Số điện thoại", "Mã nhân viên", "Mã bàn", "Số người", "Thời gian đến", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class
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
        tableBan.setRowHeight(35);
        scrTableBan.setViewportView(tableBan);

        add(scrTableBan, java.awt.BorderLayout.CENTER);

        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setPreferredSize(new java.awt.Dimension(100, 27));
        pnlButton.setLayout(new java.awt.BorderLayout(0, 5));

        btnTrangChu.setText("Trang Chủ");
        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTrangChu.setPreferredSize(new java.awt.Dimension(100, 27));
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

        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCapNhat.setPreferredSize(new java.awt.Dimension(90, 27));
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

        btnChonMon.setText("Chọn món");
        btnChonMon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnChonMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonMonActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnChonMon);

        btnChonBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnChonBan.setText("Chọn bàn");
        btnChonBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonBanActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnChonBan);

        pnlButton.add(pnlRightButtons, java.awt.BorderLayout.EAST);

        add(pnlButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangActionPerformed
        // TODO add your handling code here:
    	refreshData();
    	 cbFilterTrangThai.setSelectedIndex(0);
    	 txtTimKiem.setText("");
    }//GEN-LAST:event_btnXoaTrangActionPerformed

        private void btnChonBanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChonBanActionPerformed
                try {
                        String maPDB = txtMaPhieuDat.getText().trim();
                        if (maPDB.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu đặt bàn trước khi chọn bàn",
                                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                                return;
                        }

                        PhieuDatBan phieu = pdbService.getPhieuDatBanTheoMa(maPDB);
                        if (phieu == null) {
                                JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu đặt bàn", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Lấy bàn hiện tại từ DB
                        Set<String> oldBanSet = getTableSetFromDB(maPDB);

                        // Pre-populate PanelDatBan với selectedTables hiện tại
                        if (panelDatBan != null) {
                                // Thiết lập callback: khi user click "Đặt bàn" ở PanelDatBan sẽ gọi
                                // finishEditBansFromPanelDatBan()
                                panelDatBan.setPanelQuanLyDatBanTruoc(this);

                                // Pre-populate bàn hiện tại và highlight
                                panelDatBan.setSelectedTablesForEdit(oldBanSet);

                                // Lưu maPDB để callback sau khi user xong chọn bàn
                                storeMaPDBForEditMode(maPDB);

                                // Hiển thị nhắc nhở và tự động chuyển sang tab PanelDatBan khi YES

                                int response = JOptionPane.showConfirmDialog(this,
                                                "Bạn chắn chắc muốn chọn lại bàn?", "Nhắc nhở",
                                                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                                if (response == JOptionPane.YES_OPTION) {
                                        // Auto-switch sang tab PanelDatBan
                                        MainForm mainForm = (MainForm) SwingUtilities.getWindowAncestor(this);
                                        if (mainForm != null) {
                                                mainForm.switchToPanelDatBan();
                                        }
                                }
                        } else {
                                JOptionPane.showMessageDialog(this, "PanelDatBan không khả dụng", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                        }

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi chọn bàn: " + e.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }// GEN-LAST:event_btnChonBanActionPerformed

        // ========== FIELD CHO EDIT MODE ==========
        private String maPDBEditMode = null;
        private Set<String> newSelectedTablesForEdit = null; // Lưu danh sách bàn mới được chọn từ PanelDatBan

        private void storeMaPDBForEditMode(String maPDB) {
                this.maPDBEditMode = maPDB;
        }

        /**
         * Helper method: Lấy danh sách bàn hiện tại từ DB
         * Truy vấn ChiTietPhieuDatBan theo maPDB và trích xuất Set<String> mã bàn
         * 
         * @param maPDB Mã phiếu đặt bàn
         * @return Set danh sách mã bàn (rỗng nếu không có)
         */
        private Set<String> getTableSetFromDB(String maPDB) {
                Set<String> banSet = new HashSet<>();
                List<ChiTietPhieuDatBan> chiTietList = ctpdbService.getChiTietByMaPhieuDat(maPDB);
                if (chiTietList != null) {
                        for (ChiTietPhieuDatBan ct : chiTietList) {
                                banSet.add(ct.getBan().getMaBan());
                        }
                }
                return banSet;
        }

        /**
         * Cập nhật txtMaBan khi user chọn xong bàn trong PanelDatBan (edit mode)
         * Nhân viên sẽ thấy danh sách bàn mới đã chọn trong txtMaBan
         * Sau đó bấm btnCapNhat để lưu vào DB
         * 
         * @param newSelectedTables Danh sách bàn mới được chọn từ PanelDatBan
         */
        public void updateMaBanForEdit(Set<String> newSelectedTables) {
                if (newSelectedTables != null && !newSelectedTables.isEmpty()) {
                        String banList = String.join(", ", newSelectedTables);
                        txtMaBan.setText(banList);
                        // Lưu danh sách bàn mới để btnCapNhat sử dụng
                        this.newSelectedTablesForEdit = new HashSet<>(newSelectedTables);
                        storeMaPDBForEditMode(txtMaPhieuDat.getText().trim());
                }
        }

        /**
         * Callback method: Được gọi từ PanelDatBan sau khi user finish selecting tables
         * Update ChiTietPhieuDatBan từ PanelDatBan.selectedTables
         */
        public void finishEditBansFromPanelDatBan(Set<String> newSelectedTables) {
                if (maPDBEditMode == null || maPDBEditMode.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu cần chỉnh sửa", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                try {
                        // Lấy bàn hiện tại từ DB
                        Set<String> oldBanSet = getTableSetFromDB(maPDBEditMode);

                        // Cập nhật ChiTietPhieuDatBan (thêm/xóa bàn)
                        updateChiTietPhieuDatBan(maPDBEditMode, oldBanSet, newSelectedTables);

                        // Clear maPDB edit mode
                        maPDBEditMode = null;

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật bàn: " + e.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        /**
         * Helper method: Xử lý thêm/xóa ChiTietPhieuDatBan
         * Delegate to service layer - Service handles validation + DB logic
         * GUI chỉ handle exception + display notification
         * 
         * @throws IllegalArgumentException từ service nếu validation fail
         */
        private void updateChiTietPhieuDatBan(String maPDB, Set<String> oldBanSet, Set<String> newBanSet)
                        throws IllegalArgumentException {
                // 1. Gọi service - nếu lỗi sẽ throw exception
                ctpdbService.updateBanInPhieu(maPDB, oldBanSet, newBanSet);

                // 2. Cập nhật UI bàn từ DB (sau khi thêm/xóa hoàn tất)
                if (panelDatBan != null) {
                        panelDatBan.updateAllTableStatusFromPhieuData();
                }

                // 3. Hiển thị thông báo thành công
                JOptionPane.showMessageDialog(this, "Cập nhật bàn thành công", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
        }

        private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
                // TODO add your handling code here:
                filterAction();
        }// GEN-LAST:event_cbFilterTrangThaiActionPerformed

        @SuppressWarnings("unchecked")
        private void filterAction() {
                String selectedStatus = (String) cbFilterTrangThai.getSelectedItem();

                // Lấy TableRowSorter
                TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) tableBan.getRowSorter();

                if (sorter == null) {
                        sorter = new TableRowSorter<>((DefaultTableModel) tableBan.getModel());
                        tableBan.setRowSorter(sorter);
                }

                if (selectedStatus == null || selectedStatus.equals("-- Tất cả --")) {
                        sorter.setRowFilter(null);
                } else {
                        RowFilter<DefaultTableModel, Integer> filter = RowFilter.regexFilter("^" + selectedStatus + "$",
                                        6);
                        sorter.setRowFilter(filter);
                }
        }

        private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCapNhatActionPerformed
                try {
                        String maPDB = txtMaPhieuDat.getText().trim();
                        if (maPDB.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu đặt bàn để cập nhật",
                                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                                return;
                        }

                        PhieuDatBan phieu = pdbService.getPhieuDatBanTheoMa(maPDB);
                        if (phieu == null) {
                                JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu đặt bàn", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        phieu.setSoLuongNguoi((Integer) spSoNguoi.getValue());
                        phieu.setThoiGianDen(dtpThoiGianDen.getDateTimeStrict());

                        String trangThaiStr = (String) cbTrangThai.getSelectedItem();
                        TrangThaiPhieuDat trangThai = TrangThaiPhieuDat.fromDisplayName(trangThaiStr);
                        phieu.setTrangThai(trangThai);

                        pdbService.capNhatPhieuDatBan(phieu);

                        // ========== LOGIC EDIT MODE: Cập nhật bàn nếu danh sách bàn mới được chọn
                        // ==========
                        Set<String> newBanSet = null;

                        // Lấy danh sách bàn mới từ field (khi edit mode) hoặc parse từ txtMaBan (khi
                        // update phiếu bình thường)
                        if (newSelectedTablesForEdit != null && !newSelectedTablesForEdit.isEmpty()) {
                                // Edit mode: dùng danh sách bàn đã được chọn từ PanelDatBan
                                newBanSet = new HashSet<>(newSelectedTablesForEdit);
                                newSelectedTablesForEdit = null; // Reset sau khi sử dụng
                        } else {
                                // Normal mode: parse từ txtMaBan
                                String maBanText = txtMaBan.getText().trim();
                                if (!maBanText.isEmpty()) {
                                        newBanSet = new HashSet<>();
                                        for (String ban : maBanText.split(",")) {
                                                String banTrim = ban.trim();
                                                if (!banTrim.isEmpty()) {
                                                        newBanSet.add(banTrim);
                                                }
                                        }
                                }
                        }

                        if (newBanSet != null && !newBanSet.isEmpty()) {

                                // Lấy bàn hiện tại từ DB
                                Set<String> oldBanSet = getTableSetFromDB(maPDB);

                                if (!oldBanSet.equals(newBanSet)) {
                                        updateChiTietPhieuDatBan(maPDB, oldBanSet, newBanSet);
                                }
                        }

                        // Reload toàn bộ UI từ DB để cập nhật trạng thái bàn theo phiếu mới
                        if (panelDatBan != null) {
                                panelDatBan.refreshData();
                        }

                        JOptionPane.showMessageDialog(this, "Cập nhật phiếu đặt bàn thành công", "Thành công",
                                        JOptionPane.INFORMATION_MESSAGE);

                        loadDataToTable();
                        clearFields();

                        btnCapNhat.setEnabled(false);

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật: " + e.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }// GEN-LAST:event_btnCapNhatActionPerformed

        private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaActionPerformed
                try {
                        String maPDB = txtMaPhieuDat.getText().trim();
                        if (maPDB.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu đặt bàn để xóa", "Thông báo",
                                                JOptionPane.WARNING_MESSAGE);
                                return;
                        }

                        int result = JOptionPane.showConfirmDialog(this,
                                        "Bạn có chắc chắn muốn xóa phiếu đặt bàn " + maPDB + " không?",
                                        "Xác nhận xóa",
                                        JOptionPane.YES_NO_OPTION);

                        if (result == JOptionPane.YES_OPTION) {
                                // QUAN TRỌNG: Lấy chi tiết phiếu TRƯỚC khi xóa

                                // Xóa tất cả chi tiết phiếu TRƯỚC (để tránh foreign key constraint)
                                ctpdbService.xoaAllChiTietByMaPhieuDat(maPDB);

                                // Sau đó xóa phiếu
                                pdbService.xoaPhieuDatBan(maPDB);

                                // Cập nhật UI bàn từ DB (bàn trong phiếu xóa sẽ thành TRONG)
                                if (panelDatBan != null) {
                                        panelDatBan.updateAllTableStatusFromPhieuData();
                                }

                                JOptionPane.showMessageDialog(this, "Xóa phiếu đặt bàn thành công", "Thành công",
                                                JOptionPane.INFORMATION_MESSAGE);

                                loadDataToTable();
                                clearFields();
                                btnCapNhat.setEnabled(false);
                        }

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + e.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }// GEN-LAST:event_btnXoaActionPerformed

        private void txtMaPhieuDatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMaPhieuDatActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtMaPhieuDatActionPerformed

        private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
                try {
                        String searchText = txtTimKiem.getText().trim();

                        if (searchText.isEmpty() || searchText.equals("Nhập mã đặt bàn hoặc SĐT khách")) {
                                if (tableBan.getRowSorter() instanceof TableRowSorter) {
                                        ((TableRowSorter<?>) tableBan.getRowSorter()).setRowFilter(null);
                                } else {
                                        loadDataToTable();
                                }
                                return;
                        }

                        String pattern = "(?i).*" + java.util.regex.Pattern.quote(searchText) + ".*";
                        javax.swing.RowFilter<java.lang.Object, java.lang.Object> filter = javax.swing.RowFilter
                                        .orFilter(
                                                        java.util.Arrays.asList(
                                                                        javax.swing.RowFilter.regexFilter(pattern, 0),
                                                                        javax.swing.RowFilter.regexFilter(pattern, 1)));

                        if (!(tableBan.getRowSorter() instanceof TableRowSorter)) {
                                DefaultTableModel model = (DefaultTableModel) tableBan.getModel();
                                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
                                tableBan.setRowSorter(sorter);
                                sorter.setRowFilter(filter);
                        } else {
                                ((TableRowSorter<?>) tableBan.getRowSorter()).setRowFilter(filter);
                        }

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }// GEN-LAST:event_btnTimKiemActionPerformed

        private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTimKiemActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtTimKiemActionPerformed

        private void btnChonMonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChonMonActionPerformed
                // TODO add your handling code here:
                chonMonAction();
        }// GEN-LAST:event_btnChonMonActionPerformed

        private void chonMonAction() {
                java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
                if (parentFrame instanceof MainForm) {
                        ((MainForm) parentFrame).openPanelDatMon();
                }
        }

        private void pushDataToFieldsFromRow(int rowIndex) {
                String maPDB = (String) tableBan.getValueAt(rowIndex, 0);
                String sdt = (String) tableBan.getValueAt(rowIndex, 1);
                String maNV = (String) tableBan.getValueAt(rowIndex, 2);
                String maBan = (String) tableBan.getValueAt(rowIndex, 3);
                Integer soNguoi = (Integer) tableBan.getValueAt(rowIndex, 4);
                String trangThai = (String) tableBan.getValueAt(rowIndex, 6);

                String thoiGianDenStr = (String) tableBan.getValueAt(rowIndex, 5);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime thoiGianDen = LocalDateTime.parse(thoiGianDenStr, formatter);

                txtMaPhieuDat.setText(maPDB);
                txtSoDienThoai.setText(sdt);
                txtMaNhanVien.setText(maNV);
                txtMaBan.setText(maBan);
                dtpThoiGianDen.setDateTimeStrict(thoiGianDen);
                spSoNguoi.setValue(soNguoi);
                cbTrangThai.setSelectedItem(trangThai);
        }

        private void txtMaBanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMaBanActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtMaBanActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnChonBan;
    private javax.swing.JButton btnChonMon;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cbFilterTrangThai;
    private javax.swing.JComboBox<String> cbTrangThai;
    private com.github.lgooddatepicker.components.DateTimePicker dtpThoiGianDen;
    private javax.swing.JLabel lblGhiChu;
    private javax.swing.JLabel lblMaBan;
    private javax.swing.JLabel lblMaKhachHang;
    private javax.swing.JLabel lblMaNhanVien;
    private javax.swing.JLabel lblMaPhieuDat;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblThoiGianDen;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlRightButtons;
    private javax.swing.JPanel pnlThongTinBan;
    private javax.swing.JScrollPane scrTableBan;
    private javax.swing.JSpinner spSoNguoi;
    private javax.swing.JTable tableBan;
    private javax.swing.JTextField txtMaBan;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtMaPhieuDat;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

        @Override
        public void mouseClicked(MouseEvent e) {
                int rowSelected = tableBan.getSelectedRow();
                if (rowSelected >= 0) {
                        pushDataToFieldsFromRow(rowSelected);
                        btnCapNhat.setEnabled(true);
                }
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

        @Override
        public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

        }
}
