package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class PanelQuanLyKhuVuc extends javax.swing.JPanel {

    public PanelQuanLyKhuVuc() {
        initComponents();
        customUI();
    }

    private void customUI() {
        // 1. Màu nền chủ đạo
        setBackground(new Color(255, 251, 233));
        
        // 2. Tùy chỉnh hiệu ứng cho các nút bấm
        btnTrangChu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnThem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnXoa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCapNhat.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 3. Tùy chỉnh ScrollPane và Viền bảng
        // Tạo viền màu nâu nhạt mảnh bao quanh bảng
        scrTableKhuVuc.setBorder(BorderFactory.createLineBorder(new Color(200, 190, 170), 1)); 
        scrTableKhuVuc.setViewportBorder(null);
        scrTableKhuVuc.setOpaque(false);
        scrTableKhuVuc.getViewport().setOpaque(false);

        // Sửa lỗi góc phải ScrollBar (khử ô vuông trắng)
        JPanel corner = new JPanel();
        corner.setBackground(new Color(255, 251, 235)); 
        scrTableKhuVuc.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corner);

        // 4. Tùy chỉnh Table (Bảng Khu Vực)
        tableKhuVuc.setShowGrid(false);
        tableKhuVuc.setIntercellSpacing(new Dimension(0, 0));
        tableKhuVuc.setRowHeight(45); 
        tableKhuVuc.setSelectionBackground(new Color(245, 240, 220));
        
        // Header Table (Tiêu đề bảng)
        tableKhuVuc.getTableHeader().setPreferredSize(new Dimension(tableKhuVuc.getTableHeader().getWidth(), 45));
        tableKhuVuc.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(new Color(255, 251, 235)); 
                label.setForeground(new Color(148, 134, 111));
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setHorizontalAlignment(JLabel.CENTER);
                // Đường kẻ mảnh dưới chân tiêu đề
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
                return label;
            }
        });

        // Căn giữa nội dung các cột trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableKhuVuc.getColumnCount(); i++) {
            tableKhuVuc.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
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
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addGap(0, 504, Short.MAX_VALUE)))
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

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtMaKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKhuVucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKhuVucActionPerformed


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
