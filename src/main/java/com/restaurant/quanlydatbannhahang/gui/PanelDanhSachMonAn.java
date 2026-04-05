package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author nguye
 */
public class PanelDanhSachMonAn extends javax.swing.JPanel {

    /**
     * Creates new form PanelQuanLyMonAn
     */
    public PanelDanhSachMonAn() {
        initComponents();
        customUI();
    }

    private void customUI() {
        // 1. Màu nền chủ đạo
        setBackground(new Color(255, 251, 233));
        
        // 2. Tùy chỉnh các nút bấm
        JButton[] buttons = {btnTimKiem, btnThem, btnXoa, btnCapNhat, btnTrangChu};
        for (JButton btn : buttons) {
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setFocusPainted(false);
        }
        
        // 3. Tùy chỉnh ScrollPane và Bảng
        scrTableMonAn.setBorder(BorderFactory.createLineBorder(new Color(200, 190, 170), 1));
        scrTableMonAn.setOpaque(false);
        scrTableMonAn.getViewport().setOpaque(false);
        
        // Khử góc trắng ScrollBar
        JPanel corner = new JPanel();
        corner.setBackground(new Color(255, 251, 233));
        scrTableMonAn.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corner);

        // 4. Định dạng Table hiện đại
        tableMonAn.setShowGrid(false);
        tableMonAn.setIntercellSpacing(new Dimension(0, 0));
        tableMonAn.setRowHeight(40);
        tableMonAn.setSelectionBackground(new Color(245, 240, 220));

        // Header Table
        tableMonAn.getTableHeader().setPreferredSize(new Dimension(tableMonAn.getTableHeader().getWidth(), 45));
        tableMonAn.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
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
        for (int i = 0; i < tableMonAn.getColumnCount(); i++) {
            tableMonAn.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // 5. Bo góc cho khu vực nhập liệu
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
        jLabel1 = new javax.swing.JLabel();
        pnlThongTinKhuyenMai = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
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
        jLabel1.setText("Quản lý thông tin món ăn ");
        pnlHeader.add(jLabel1, java.awt.BorderLayout.WEST);

        pnlThongTinKhuyenMai.setBackground(new java.awt.Color(255, 251, 233));

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinKhuyenMaiLayout = new javax.swing.GroupLayout(pnlThongTinKhuyenMai);
        pnlThongTinKhuyenMai.setLayout(pnlThongTinKhuyenMaiLayout);
        pnlThongTinKhuyenMaiLayout.setHorizontalGroup(
            pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 452, Short.MAX_VALUE)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlThongTinKhuyenMaiLayout.setVerticalGroup(
            pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        pnlHeader.add(pnlThongTinKhuyenMai, java.awt.BorderLayout.PAGE_END);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        scrTableMonAn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrTableMonAnMouseClicked(evt);
            }
        });

        tableMonAn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã món", "Tên món", "Đơn giá", "Đơn vị tính", "Mã loại", "Trạng thái", "Hình ảnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
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

    private void scrTableMonAnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrTableMonAnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_scrTableMonAnMouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlRightButtons;
    private javax.swing.JPanel pnlThongTinKhuyenMai;
    private javax.swing.JScrollPane scrTableMonAn;
    private javax.swing.JTable tableMonAn;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
