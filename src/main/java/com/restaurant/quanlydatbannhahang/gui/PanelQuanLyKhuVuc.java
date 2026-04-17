package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.KhuVucService;
import com.restaurant.quanlydatbannhahang.entity.KhuVuc;
import java.util.List;

public class PanelQuanLyKhuVuc extends javax.swing.JPanel implements MouseListener {

    public PanelQuanLyKhuVuc() {
        initComponents();
        customUI();
        loadDataToTable();
    }

    private void customUI() {
        // Placeholder cho txtTimKiem
        setupPlaceholder(txtTimKiem, "Nhập tên hoặc mã khu vực");

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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
        lblTitle.setText("Quản lý thông tin khu vực trong nhà hàng");
        pnlHeader.add(lblTitle, java.awt.BorderLayout.WEST);

        pnlThongTinKhuVuc.setBackground(new java.awt.Color(255, 251, 233));

        lblMaKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMaKhuVuc.setText("Mã khu vực:");

        txtMaKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaKhuVuc.setPreferredSize(new java.awt.Dimension(64, 35));
        txtMaKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKhuVucActionPerformed(evt);
            }
        });

        lblTenKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTenKhuVuc.setText("Tên khu vực:");

        txtTenKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenKhuVuc.setPreferredSize(new java.awt.Dimension(64, 35));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.setPreferredSize(new java.awt.Dimension(64, 35));

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.setText("Tìm kiếm");

        javax.swing.GroupLayout pnlThongTinKhuVucLayout = new javax.swing.GroupLayout(pnlThongTinKhuVuc);
        pnlThongTinKhuVuc.setLayout(pnlThongTinKhuVucLayout);
        pnlThongTinKhuVucLayout.setHorizontalGroup(
            pnlThongTinKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKhuVucLayout.createSequentialGroup()
                .addGroup(pnlThongTinKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinKhuVucLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinKhuVucLayout.createSequentialGroup()
                        .addGroup(pnlThongTinKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenKhuVuc)
                            .addComponent(lblMaKhuVuc))
                        .addGap(25, 25, 25)
                        .addGroup(pnlThongTinKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 505, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlThongTinKhuVucLayout.setVerticalGroup(
            pnlThongTinKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKhuVucLayout.createSequentialGroup()
                .addGroup(pnlThongTinKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTenKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        pnlHeader.add(pnlThongTinKhuVuc, java.awt.BorderLayout.PAGE_END);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        tableKhuVuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã khu vực", "Tên khu vực"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
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

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaTrangActionPerformed

    private void loadDataToComboBoxes() {
        // No combobox filters in this panel
    }

    private void loadDataToTable() {
        loadFilteredData();
    }

    private void loadFilteredData() {
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

    private void searchByText() {
        try {
            KhuVucService service = new KhuVucService();
            List<KhuVuc> list = service.getAllKhuVuc();
            String searchText = txtTimKiem.getText().trim().toLowerCase();

            DefaultTableModel model = (DefaultTableModel) tableKhuVuc.getModel();
            model.setRowCount(0);

            for (KhuVuc kv : list) {
                if (!searchText.isEmpty()) {
                    String maKhuVuc = kv.getMaKhuVuc() != null ? kv.getMaKhuVuc().toLowerCase() : "";
                    String tenKhuVuc = kv.getTenKhuVuc() != null ? kv.getTenKhuVuc().toLowerCase() : "";
                    if (!maKhuVuc.contains(searchText) && !tenKhuVuc.contains(searchText)) {
                        continue;
                    }
                }

                model.addRow(new Object[] {
                        kv.getMaKhuVuc(),
                        kv.getTenKhuVuc()
                });
            }
            centerTableColumns(tableKhuVuc);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm dữ liệu: " + e.getMessage());
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

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
        searchByText();
    }

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
        searchByText();
    }

    private void loadDataFromRow(int rowIndex) {
        try {
            String maKhuVuc = (String) tableKhuVuc.getValueAt(rowIndex, 0);
            String tenKhuVuc = (String) tableKhuVuc.getValueAt(rowIndex, 1);

            txtMaKhuVuc.setText(maKhuVuc);
            txtTenKhuVuc.setText(tenKhuVuc);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi load dữ liệu từ row: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtMaKhuVuc.setText("");
        txtTenKhuVuc.setText("");
    }

    private boolean isMouseOverTable(java.awt.event.MouseEvent evt) {
        java.awt.Point p = evt.getPoint();
        java.awt.Point tablePoint = SwingUtilities.convertPoint(this, p, tableKhuVuc);
        return tableKhuVuc.getBounds().contains(tablePoint);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTrang;
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
