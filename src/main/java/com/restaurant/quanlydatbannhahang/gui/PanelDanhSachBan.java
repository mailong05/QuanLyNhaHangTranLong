package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class PanelDanhSachBan extends javax.swing.JPanel {

    public PanelDanhSachBan() {
        initComponents();
        customUI();
    }

    private void customUI() {
        // 1. Màu nền chủ đạo
        setBackground(new Color(255, 251, 233));
        

        // 3. Tùy chỉnh ScrollPane (Giống PanelTrangChu)
        // Thay Border Empty bằng Border màu nâu/xám nhạt để tạo khung cho bảng
        scrTableBan.setBorder(BorderFactory.createLineBorder(new Color(200, 190, 170), 1)); 
        scrTableBan.setViewportBorder(null);
        scrTableBan.setOpaque(false);
        scrTableBan.getViewport().setOpaque(false);

        // Sửa lỗi góc phải ScrollBar
        JPanel corner = new JPanel();
        corner.setBackground(new Color(255, 251, 235)); 
        scrTableBan.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corner);

        // 4. Tùy chỉnh Table
        tableBan.setShowGrid(false);
        tableBan.setIntercellSpacing(new Dimension(0, 0));
        tableBan.setRowHeight(45); 
        tableBan.setSelectionBackground(new Color(245, 240, 220));
        
        // Header Table
        tableBan.getTableHeader().setPreferredSize(new Dimension(tableBan.getTableHeader().getWidth(), 45));
        tableBan.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(new Color(255, 251, 235)); 
                label.setForeground(new Color(148, 134, 111));
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
                return label;
            }
        });

        // Căn giữa các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableBan.getColumnCount(); i++) {
            tableBan.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // 5. Thêm hiệu ứng bo góc cho container chứa bảng (nếu cần bao bọc bảng trong 1 tấm card)
        // Hiện tại table đang add thẳng vào Center của PanelQuanLyBan
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
    
    //Từ đây không chỉnh sửa bên dưới
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        pnlThongTinBan = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        cbKhuVuc = new javax.swing.JComboBox<>();
        cbTrangThai = new javax.swing.JComboBox<>();
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

        pnlThongTinBan.setBackground(new java.awt.Color(255, 251, 233));

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

        cbKhuVuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlThongTinBanLayout = new javax.swing.GroupLayout(pnlThongTinBan);
        pnlThongTinBan.setLayout(pnlThongTinBanLayout);
        pnlThongTinBanLayout.setHorizontalGroup(
            pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 218, Short.MAX_VALUE)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlThongTinBanLayout.setVerticalGroup(
            pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinBanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(cbKhuVuc)
                    .addComponent(cbTrangThai)))
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

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbKhuVuc;
    private javax.swing.JComboBox<String> cbTrangThai;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlRightButtons;
    private javax.swing.JPanel pnlThongTinBan;
    private javax.swing.JScrollPane scrTableBan;
    private javax.swing.JTable tableBan;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
