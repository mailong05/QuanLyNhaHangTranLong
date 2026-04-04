package com.restaurant.quanlydatbannhahang.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

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
        lblMaKhuyenMai = new javax.swing.JLabel();
        txtMaKhuyenMai = new javax.swing.JTextField();
        lblTenKhuyenMai = new javax.swing.JLabel();
        txtTenKhuyenMai = new javax.swing.JTextField();
        txtNgayBatDau = new javax.swing.JTextField();
        lblNgayBatDau = new javax.swing.JLabel();
        txtNgayKetThuc = new javax.swing.JTextField();
        lblNgayKetThuc = new javax.swing.JLabel();
        lblGiaTriGiam = new javax.swing.JLabel();
        txtGiaTriGiam = new javax.swing.JTextField();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        lblDieuKienToiThieu = new javax.swing.JLabel();
        txtDieuKienToiThieu = new javax.swing.JTextField();
        lblTrangThai = new javax.swing.JLabel();
        txtTrangThai = new javax.swing.JTextField();
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

        lblMaKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMaKhuyenMai.setText("Mã món:");

        txtMaKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKhuyenMaiActionPerformed(evt);
            }
        });

        lblTenKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTenKhuyenMai.setText("Tên món:");

        txtTenKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKhuyenMaiActionPerformed(evt);
            }
        });

        txtNgayBatDau.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNgayBatDau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayBatDauActionPerformed(evt);
            }
        });

        lblNgayBatDau.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNgayBatDau.setText("Mã loại:");

        txtNgayKetThuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblNgayKetThuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNgayKetThuc.setText("Trạng thái:");

        lblGiaTriGiam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblGiaTriGiam.setText("Đơn giá:");

        txtGiaTriGiam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGiaTriGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaTriGiamActionPerformed(evt);
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

        lblDieuKienToiThieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDieuKienToiThieu.setText("Hình ảnh:");

        txtDieuKienToiThieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTrangThai.setText("Đơn vị tính:");

        txtTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrangThaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinKhuyenMaiLayout = new javax.swing.GroupLayout(pnlThongTinKhuyenMai);
        pnlThongTinKhuyenMai.setLayout(pnlThongTinKhuyenMaiLayout);
        pnlThongTinKhuyenMaiLayout.setHorizontalGroup(
            pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                        .addComponent(lblGiaTriGiam)
                        .addGap(32, 32, 32)
                        .addComponent(txtGiaTriGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaKhuyenMai)
                            .addComponent(lblTenKhuyenMai))
                        .addGap(27, 27, 27)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                        .addComponent(lblTrangThai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(53, 53, 53)
                .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNgayBatDau)
                    .addComponent(lblNgayKetThuc)
                    .addComponent(lblDieuKienToiThieu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDieuKienToiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 52, Short.MAX_VALUE))
            .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                .addComponent(txtTimKiem)
                .addGap(24, 24, 24)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlThongTinKhuyenMaiLayout.setVerticalGroup(
            pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNgayKetThuc))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDieuKienToiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDieuKienToiThieu)))
                    .addGroup(pnlThongTinKhuyenMaiLayout.createSequentialGroup()
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTenKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblGiaTriGiam)
                            .addComponent(txtGiaTriGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTrangThai))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        pnlHeader.add(pnlThongTinKhuyenMai, java.awt.BorderLayout.PAGE_END);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        tableMonAn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã món", "Tên món", "Đơn giá", "Đơn vị tính", "Mã loại", "Trạng thái", "Hình ảnh"
            }
        ));
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

    private void txtMaKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKhuyenMaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKhuyenMaiActionPerformed

    private void txtNgayBatDauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayBatDauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayBatDauActionPerformed

    private void txtGiaTriGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaTriGiamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaTriGiamActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrangThaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrangThaiActionPerformed

    private void txtTenKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKhuyenMaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKhuyenMaiActionPerformed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblDieuKienToiThieu;
    private javax.swing.JLabel lblGiaTriGiam;
    private javax.swing.JLabel lblMaKhuyenMai;
    private javax.swing.JLabel lblNgayBatDau;
    private javax.swing.JLabel lblNgayKetThuc;
    private javax.swing.JLabel lblTenKhuyenMai;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlRightButtons;
    private javax.swing.JPanel pnlThongTinKhuyenMai;
    private javax.swing.JScrollPane scrTableMonAn;
    private javax.swing.JTable tableMonAn;
    private javax.swing.JTextField txtDieuKienToiThieu;
    private javax.swing.JTextField txtGiaTriGiam;
    private javax.swing.JTextField txtMaKhuyenMai;
    private javax.swing.JTextField txtNgayBatDau;
    private javax.swing.JTextField txtNgayKetThuc;
    private javax.swing.JTextField txtTenKhuyenMai;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTrangThai;
    // End of variables declaration//GEN-END:variables
}
