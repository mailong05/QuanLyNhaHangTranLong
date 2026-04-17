package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import javax.swing.*;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.KhuVucService;
import com.restaurant.quanlydatbannhahang.entity.KhuVuc;
import java.util.List;

public class PanelQuanLyKhuVuc extends javax.swing.JPanel {

    public PanelQuanLyKhuVuc() {
        initComponents();
        customUI();
        loadDataToTable();
    }

    private void customUI() {
        // Placeholder cho txtTimKiem
        setupPlaceholder(txtTimKiem, "Nhập tên hoặc mã khu vực");
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        pnlThongTinKhuVuc = new javax.swing.JPanel();
        lblMaKhuVuc = new javax.swing.JLabel();
        txtMaKhuVuc = new javax.swing.JTextField();
        lblTenKhuVuc = new javax.swing.JLabel();
        txtTenKhuVuc = new javax.swing.JTextField();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        scrTableKhuVuc = new javax.swing.JScrollPane();
        tableKhuVuc = new javax.swing.JTable();
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
        lblTitle.setText("Quản lý thông tin khu vực trong nhà hàng");
        pnlHeader.add(lblTitle, java.awt.BorderLayout.WEST);

        pnlThongTinKhuVuc.setBackground(new java.awt.Color(255, 251, 233));

        lblMaKhuVuc.setText("Mã khu vực:");
        lblMaKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtMaKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaKhuVuc.setPreferredSize(new java.awt.Dimension(64, 35));
        txtMaKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKhuVucActionPerformed(evt);
            }
        });

        lblTenKhuVuc.setText("Tên khu vực:");
        lblTenKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTenKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenKhuVuc.setPreferredSize(new java.awt.Dimension(64, 35));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.setPreferredSize(new java.awt.Dimension(64, 35));

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout pnlThongTinKhuVucLayout = new javax.swing.GroupLayout(pnlThongTinKhuVuc);
        pnlThongTinKhuVuc.setLayout(pnlThongTinKhuVucLayout);
        pnlThongTinKhuVucLayout.setHorizontalGroup(
                pnlThongTinKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinKhuVucLayout.createSequentialGroup()
                                .addGroup(pnlThongTinKhuVucLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlThongTinKhuVucLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 286,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 123,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnlThongTinKhuVucLayout.createSequentialGroup()
                                                .addGroup(pnlThongTinKhuVucLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblTenKhuVuc)
                                                        .addComponent(lblMaKhuVuc))
                                                .addGap(25, 25, 25)
                                                .addGroup(pnlThongTinKhuVucLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtTenKhuVuc,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 390,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtMaKhuVuc,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 390,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 505, Short.MAX_VALUE)))
                                .addContainerGap()));
        pnlThongTinKhuVucLayout.setVerticalGroup(
                pnlThongTinKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinKhuVucLayout.createSequentialGroup()
                                .addGroup(pnlThongTinKhuVucLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtMaKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblMaKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlThongTinKhuVucLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtTenKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTenKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlThongTinKhuVucLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)));

        pnlHeader.add(pnlThongTinKhuVuc, java.awt.BorderLayout.PAGE_END);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        tableKhuVuc.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Mã khu vực", "Tên khu vực"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tableKhuVuc.setRowHeight(35);
        scrTableKhuVuc.setViewportView(tableKhuVuc);

        add(scrTableKhuVuc, java.awt.BorderLayout.CENTER);

        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setLayout(new java.awt.BorderLayout());

        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTrangChu.setText("Trang Chủ");
        pnlButton.add(btnTrangChu, java.awt.BorderLayout.WEST);

        pnlRightButtons.setBackground(new java.awt.Color(255, 251, 233));
        pnlRightButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 15, 0));

        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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

    private void loadDataToTable() {
        try {
            KhuVucService service = new KhuVucService();
            List<KhuVuc> list = service.getAllKhuVuc();

            DefaultTableModel model = (DefaultTableModel) tableKhuVuc.getModel();
            model.setRowCount(0);

            for (KhuVuc kv : list) {
                model.addRow(new Object[] {
                        kv.getMaKhuVuc(),
                        kv.getTenKhuVuc()
                });
            }
            centerTableColumns(tableKhuVuc);
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

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnXoaActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnCapNhatActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnThemActionPerformed

    private void txtMaKhuVucActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMaKhuVucActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtMaKhuVucActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel lblMaKhuVuc;
    private javax.swing.JLabel lblTenKhuVuc;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlRightButtons;
    private javax.swing.JPanel pnlThongTinKhuVuc;
    private javax.swing.JScrollPane scrTableKhuVuc;
    private javax.swing.JTable tableKhuVuc;
    private javax.swing.JTextField txtMaKhuVuc;
    private javax.swing.JTextField txtTenKhuVuc;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
