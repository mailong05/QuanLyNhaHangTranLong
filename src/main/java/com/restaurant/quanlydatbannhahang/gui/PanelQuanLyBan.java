package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.BanService;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import java.util.List;

public class PanelQuanLyBan extends javax.swing.JPanel {

    public PanelQuanLyBan() {
        initComponents();
        customUI();
        loadDataToTable();
    }

    private void customUI() {
        // Placeholder cho txtTimKiem
        setupPlaceholder(txtTimKiem, "Nhập mã bàn");
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

    // Từ đây không chỉnh sửa bên dưới
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        pnlThongTinBan = new javax.swing.JPanel();
        lblMaBan = new javax.swing.JLabel();
        txtMaBan = new javax.swing.JTextField();
        lblSoGhe = new javax.swing.JLabel();
        txtSoGhe = new javax.swing.JTextField();
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
        scrTableBan = new javax.swing.JScrollPane();
        tableBan = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        pnlRightButtons = new javax.swing.JPanel();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout(0, 10));

        pnlHeader.setOpaque(false);
        pnlHeader.setLayout(new java.awt.BorderLayout(0, 15));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitle.setText("Quản lý thông tin bàn ăn trong nhà hàng");
        pnlHeader.add(lblTitle, java.awt.BorderLayout.WEST);

        pnlThongTinBan.setBackground(new java.awt.Color(255, 251, 233));

        lblMaBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMaBan.setText("Mã bàn:");

        txtMaBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaBan.setMinimumSize(new java.awt.Dimension(64, 35));
        txtMaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaBanActionPerformed(evt);
            }
        });

        lblSoGhe.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSoGhe.setText("Số ghế:");

        txtSoGhe.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoGhe.setMinimumSize(new java.awt.Dimension(64, 35));
        txtSoGhe.setPreferredSize(new java.awt.Dimension(64, 35));

        txtViTri.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtViTri.setPreferredSize(new java.awt.Dimension(64, 35));
        txtViTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtViTriActionPerformed(evt);
            }
        });

        lblViTri.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViTri.setText("Vị trí:");

        lblKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblKhuVuc.setText("Khu vực:");

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTrangThai.setText("Trạng thái:");

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

        cbTrangThai.setMinimumSize(new java.awt.Dimension(72, 35));
        cbTrangThai.setName(""); // NOI18N
        cbTrangThai.setPreferredSize(new java.awt.Dimension(72, 35));

        cbKhuVuc.setPreferredSize(new java.awt.Dimension(72, 35));

        cbFilterKhuVuc.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbFilterKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterKhuVucActionPerformed(evt);
            }
        });

        cbFilterTrangThai.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
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
                                .addGroup(pnlThongTinBanLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                                .addComponent(lblMaBan)
                                                .addGap(33, 33, 33)
                                                .addComponent(txtMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 390,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(56, 56, 56)
                                                .addComponent(lblViTri))
                                        .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                                .addComponent(lblSoGhe, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(33, 33, 33)
                                                .addComponent(txtSoGhe, javax.swing.GroupLayout.PREFERRED_SIZE, 390,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(56, 56, 56)
                                                .addComponent(lblKhuVuc))
                                        .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                                .addComponent(cbFilterKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(29, 29, 29)
                                                .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(26, 26, 26)
                                .addGroup(pnlThongTinBanLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                pnlThongTinBanLayout.createSequentialGroup()
                                                        .addComponent(txtTimKiem)
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(btnTimKiem,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 140,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                                .addGroup(pnlThongTinBanLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(txtViTri, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                390, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                                                .addComponent(cbKhuVuc,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 135,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)
                                                                .addComponent(lblTrangThai)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(cbTrangThai,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 132,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addContainerGap(51, Short.MAX_VALUE)))));
        pnlThongTinBanLayout.setVerticalGroup(
                pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                .addGroup(pnlThongTinBanLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(lblViTri, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtViTri, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblMaBan, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtMaBan, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlThongTinBanLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                pnlThongTinBanLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblSoGhe, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtSoGhe, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                pnlThongTinBanLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblKhuVuc)
                                                        .addComponent(cbKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnlThongTinBanLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lblTrangThai)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlThongTinBanLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(pnlThongTinBanLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cbFilterKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbFilterTrangThai))
                                .addGap(0, 0, Short.MAX_VALUE)));

        pnlHeader.add(pnlThongTinBan, java.awt.BorderLayout.PAGE_END);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        tableBan.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Mã bàn", "Số ghế", "Vị trí", "Mã khu vực", "Trạng thái"
                }) {
            Class[] types = new Class[] {
                    java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class,
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
        tableBan.setRowHeight(35);
        scrTableBan.setViewportView(tableBan);

        add(scrTableBan, java.awt.BorderLayout.CENTER);

        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setPreferredSize(new java.awt.Dimension(100, 27));
        pnlButton.setLayout(new java.awt.BorderLayout(0, 5));

        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTrangChu.setText("Trang Chủ");
        btnTrangChu.setPreferredSize(new java.awt.Dimension(100, 27));
        pnlButton.add(btnTrangChu, java.awt.BorderLayout.WEST);

        pnlRightButtons.setBackground(new java.awt.Color(255, 251, 233));
        pnlRightButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 15, 0));

        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setPreferredSize(new java.awt.Dimension(90, 27));
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
            BanService service = new BanService();
            List<Ban> list = service.getAllBan();

            DefaultTableModel model = (DefaultTableModel) tableBan.getModel();
            model.setRowCount(0);

            for (Ban ban : list) {
                model.addRow(new Object[] {
                        ban.getMaBan(),
                        ban.getSoGhe(),
                        ban.getViTri(),
                        ban.getKhuVuc().getMaKhuVuc(),
                        ban.getTrangThai().getDisplayName()
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

    private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_cbFilterTrangThaiActionPerformed

    private void cbFilterKhuVucActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterKhuVucActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_cbFilterKhuVucActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnXoaActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnCapNhatActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnThemActionPerformed

    private void txtMaBanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMaBanActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtMaBanActionPerformed

    private void txtViTriActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtViTriActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtViTriActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnTimKiemActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtTimKiemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoa;
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
    private javax.swing.JTable tableBan;
    private javax.swing.JTextField txtMaBan;
    private javax.swing.JTextField txtSoGhe;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtViTri;
    // End of variables declaration//GEN-END:variables
}
