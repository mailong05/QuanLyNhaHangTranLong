package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.MonAnService;
import com.restaurant.quanlydatbannhahang.entity.MonAn;
import java.util.List;

/**
 *
 * @author nguye
 */
public class PanelQuanLyMonAn extends javax.swing.JPanel {

    /**
     * Creates new form PanelQuanLyMonAn
     */
    public PanelQuanLyMonAn() {
        initComponents();
        customUI();
        loadDataToTable();
    }

    private void customUI() {
        // Placeholder cho txtTimKiem
        setupPlaceholder(txtTimKiem, "Nhập tên món ăn");
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
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlThongTinKhuyenMai = new javax.swing.JPanel();
        lblMaMon = new javax.swing.JLabel();
        txtMaMon = new javax.swing.JTextField();
        lblTenMon = new javax.swing.JLabel();
        txtTenMon = new javax.swing.JTextField();
        lblLoaiMon = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        lblDonGia = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        lblHinhAnh = new javax.swing.JLabel();
        lblDonViTinh = new javax.swing.JLabel();
        cbDonViTinh = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnChonFileAnh = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        cbFilterLoaiMonAn = new javax.swing.JComboBox<>();
        scrTableMonAn = new javax.swing.JScrollPane();
        tableMonAn = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        pnlRightButtons = new javax.swing.JPanel();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout(0, 15));

        pnlHeader.setOpaque(false);
        pnlHeader.setLayout(new java.awt.BorderLayout(0, 15));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Quản lý thông tin món ăn trong nhà hàng");
        pnlHeader.add(jLabel1, java.awt.BorderLayout.WEST);

        pnlThongTinKhuyenMai.setBackground(new java.awt.Color(255, 251, 233));

        lblMaMon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMaMon.setText("Mã món:");

        txtMaMon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaMon.setPreferredSize(new java.awt.Dimension(64, 35));
        txtMaMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaMonActionPerformed(evt);
            }
        });

        lblTenMon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTenMon.setText("Tên món:");

        txtTenMon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenMon.setPreferredSize(new java.awt.Dimension(64, 35));
        txtTenMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenMonActionPerformed(evt);
            }
        });

        lblLoaiMon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblLoaiMon.setText("Loại món:");

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTrangThai.setText("Trạng thái:");

        lblDonGia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDonGia.setText("Đơn giá:");

        txtDonGia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDonGia.setPreferredSize(new java.awt.Dimension(64, 35));
        txtDonGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonGiaActionPerformed(evt);
            }
        });

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        lblHinhAnh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblHinhAnh.setText("Hình ảnh:");

        lblDonViTinh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDonViTinh.setText("Đơn vị tính:");

        cbDonViTinh.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbDonViTinh.setPreferredSize(new java.awt.Dimension(72, 35));
        cbDonViTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDonViTinhActionPerformed(evt);
            }
        });

        jComboBox1.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setPreferredSize(new java.awt.Dimension(72, 35));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        btnChonFileAnh.setText("Chọn ảnh");
        btnChonFileAnh.setPreferredSize(new java.awt.Dimension(82, 35));
        btnChonFileAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonFileAnhActionPerformed(evt);
            }
        });

        jComboBox2.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.setPreferredSize(new java.awt.Dimension(72, 35));

        cbFilterLoaiMonAn.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbFilterLoaiMonAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterLoaiMonAnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinKhuyenMaiLayout = new javax.swing.GroupLayout(pnlThongTinKhuyenMai);
        pnlThongTinKhuyenMai.setLayout(pnlThongTinKhuyenMaiLayout);
        pnlThongTinKhuyenMaiLayout.setHorizontalGroup(
                pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                                .addGroup(pnlThongTinKhuyenMaiLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                                                .addComponent(lblDonGia)
                                                .addGap(32, 32, 32)
                                                .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 390,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                                                .addGroup(pnlThongTinKhuyenMaiLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblMaMon)
                                                        .addComponent(lblTenMon))
                                                .addGap(27, 27, 27)
                                                .addGroup(pnlThongTinKhuyenMaiLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtTenMon, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                390, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtMaMon, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                390, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                                                .addComponent(lblDonViTinh)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cbDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 101,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(cbFilterLoaiMonAn, javax.swing.GroupLayout.PREFERRED_SIZE, 141,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(53, 53, 53)
                                .addGroup(pnlThongTinKhuyenMaiLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblLoaiMon)
                                        .addComponent(lblTrangThai)
                                        .addComponent(lblHinhAnh))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlThongTinKhuyenMaiLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                                                .addComponent(txtTimKiem)
                                                .addGap(24, 24, 24)
                                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 123,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())
                                        .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                                                .addGroup(pnlThongTinKhuyenMaiLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(jComboBox2, 0, 110, Short.MAX_VALUE)
                                                        .addComponent(btnChonFileAnh,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jComboBox1, 0,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(0, 334, Short.MAX_VALUE)))));
        pnlThongTinKhuyenMaiLayout.setVerticalGroup(
                pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                                .addGroup(pnlThongTinKhuyenMaiLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addGroup(pnlThongTinKhuyenMaiLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblLoaiMon,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jComboBox1,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(pnlThongTinKhuyenMaiLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblTrangThai)
                                                        .addComponent(jComboBox2,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(pnlThongTinKhuyenMaiLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblHinhAnh)
                                                        .addComponent(btnChonFileAnh,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                                                .addGroup(pnlThongTinKhuyenMaiLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblMaMon, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtMaMon, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(13, 13, 13)
                                                .addGroup(pnlThongTinKhuyenMaiLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblTenMon, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtTenMon, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(pnlThongTinKhuyenMaiLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblDonGia))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(pnlThongTinKhuyenMaiLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblDonViTinh)
                                                        .addComponent(cbDonViTinh,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlThongTinKhuyenMaiLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlThongTinKhuyenMaiLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(cbFilterLoaiMonAn, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
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
            Class<?>[] types = new Class<?>[] {
                    java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class,
                    java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false
            };

            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

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

        pnlRightButtons.setBackground(new java.awt.Color(255, 251, 233));
        pnlRightButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 15, 0));

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

    private void loadDataToTable() {
        try {
            MonAnService service = new MonAnService();
            List<MonAn> list = service.getAllMonAn();

            DefaultTableModel model = (DefaultTableModel) tableMonAn.getModel();
            model.setRowCount(0);

            for (MonAn monan : list) {
                model.addRow(new Object[] {
                        null, // Hình ảnh - để null vì xử lý hình ảnh phức tạp hơn
                        monan.getMaMon(),
                        monan.getTenMon(),
                        monan.getDonGia(),
                        monan.getDonViTinh(),
                        monan.getTenLoai(),
                        monan.getTrangThai().getDisplayName()
                });
            }
            centerTableColumns(tableMonAn);
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

    private void cbFilterLoaiMonAnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterLoaiMonAnActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_cbFilterLoaiMonAnActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnXoaActionPerformed

    private void txtMaMonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMaMonActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtMaMonActionPerformed

    private void txtDonGiaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtDonGiaActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtDonGiaActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnTimKiemActionPerformed

    private void txtTenMonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTenMonActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtTenMonActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnCapNhatActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnThemActionPerformed

    private void scrTableMonAnMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_scrTableMonAnMouseClicked
        // TODO add your handling code here:
    }// GEN-LAST:event_scrTableMonAnMouseClicked

    private void btnChonFileAnhActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChonFileAnhActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnChonFileAnhActionPerformed

    private void cbDonViTinhActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbDonViTinhActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_cbDonViTinhActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jComboBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnChonFileAnh;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbDonViTinh;
    private javax.swing.JComboBox<String> cbFilterLoaiMonAn;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblDonGia;
    private javax.swing.JLabel lblDonViTinh;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JLabel lblLoaiMon;
    private javax.swing.JLabel lblMaMon;
    private javax.swing.JLabel lblTenMon;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlRightButtons;
    private javax.swing.JPanel pnlThongTinKhuyenMai;
    private javax.swing.JScrollPane scrTableMonAn;
    private javax.swing.JTable tableMonAn;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaMon;
    private javax.swing.JTextField txtTenMon;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
