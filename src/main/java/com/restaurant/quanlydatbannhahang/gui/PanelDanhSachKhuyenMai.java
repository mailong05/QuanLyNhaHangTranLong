package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;


public class PanelDanhSachKhuyenMai extends javax.swing.JPanel {

    public PanelDanhSachKhuyenMai() {
        initComponents();
        customUI();
    }

    private void customUI() {
        // 1. Màu nền chủ đạo
        setBackground(new Color(255, 251, 233));
        
        // 2. Tùy chỉnh nút Tìm kiếm
        btnTimKiem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTimKiem.setFocusPainted(false);
        
        // 3. Tùy chỉnh ScrollPane và Bảng
        scrTableKhuyenMai.setBorder(BorderFactory.createLineBorder(new Color(200, 190, 170), 1));
        scrTableKhuyenMai.setOpaque(false);
        scrTableKhuyenMai.getViewport().setOpaque(false);
        
        // Khử góc trắng ScrollBar
        JPanel corner = new JPanel();
        corner.setBackground(new Color(255, 251, 233));
        scrTableKhuyenMai.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corner);

        // 4. Định dạng Table hiện đại
        tableKhuyenMai.setShowGrid(false);
        tableKhuyenMai.setIntercellSpacing(new Dimension(0, 0));
        tableKhuyenMai.setRowHeight(40);
        tableKhuyenMai.setSelectionBackground(new Color(245, 240, 220));

        // Header Table
        tableKhuyenMai.getTableHeader().setPreferredSize(new Dimension(tableKhuyenMai.getTableHeader().getWidth(), 45));
        tableKhuyenMai.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(new Color(255, 251, 233));
                label.setForeground(new Color(148, 134, 111));
                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 210, 190)));
                return label;
            }
        });

        // Căn giữa nội dung các cột trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableKhuyenMai.getColumnCount(); i++) {
            tableKhuyenMai.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // 5. Card style cho khu vực nhập liệu
        applyCardStyle(pnlThongTinKhuyenMai, 20);
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
        pnlThongTinKhuyenMai = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        cbTrangThai = new javax.swing.JComboBox<>();
        scrTableKhuyenMai = new javax.swing.JScrollPane();
        tableKhuyenMai = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        pnlRightButtons = new javax.swing.JPanel();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout(0, 15));

        pnlHeader.setBackground(new java.awt.Color(255, 251, 233));
        pnlHeader.setLayout(new java.awt.BorderLayout(0, 15));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitle.setText("Quản lý các chương trình khuyến mãi và giảm giá  ");
        pnlHeader.add(lblTitle, java.awt.BorderLayout.WEST);

        pnlThongTinKhuyenMai.setBackground(new java.awt.Color(255, 251, 233));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        cbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlThongTinKhuyenMaiLayout = new javax.swing.GroupLayout(pnlThongTinKhuyenMai);
        pnlThongTinKhuyenMai.setLayout(pnlThongTinKhuyenMaiLayout);
        pnlThongTinKhuyenMaiLayout.setHorizontalGroup(
            pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 455, Short.MAX_VALUE)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlThongTinKhuyenMaiLayout.setVerticalGroup(
            pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pnlHeader.add(pnlThongTinKhuyenMai, java.awt.BorderLayout.PAGE_END);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        tableKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã khuyến mãi", "Tên khuyến mãi", "Giá trị giảm", "Ngày bắt đầu", "Ngày kết thúc", "Điều kiện tối thiểu", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.String.class
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
        tableKhuyenMai.setRowHeight(35);
        scrTableKhuyenMai.setViewportView(tableKhuyenMai);

        add(scrTableKhuyenMai, java.awt.BorderLayout.CENTER);

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

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbTrangThai;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlRightButtons;
    private javax.swing.JPanel pnlThongTinKhuyenMai;
    private javax.swing.JScrollPane scrTableKhuyenMai;
    private javax.swing.JTable tableKhuyenMai;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
