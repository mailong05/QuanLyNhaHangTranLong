package com.restaurant.quanlydatbannhahang.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Duc Tri
 */

public class PanelDatBan extends javax.swing.JPanel {

    public PanelDatBan() {
        initComponents();
        
        // Tối ưu thanh cuộn và loại bỏ viền
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(20);
        jScrollPane1.setBorder(null);

        // Gọi nạp dữ liệu
        testData();
    }

    private void testData() {
        panelDanhSachBan.removeAll();
        
        // Thiết lập Layout cho panel chính là BoxLayout theo chiều dọc (Y_AXIS)
        panelDanhSachBan.setLayout(new javax.swing.BoxLayout(panelDanhSachBan, javax.swing.BoxLayout.Y_AXIS));

        String[] dsKhuVuc = {"Khu vực A", "Khu vực B", "Khu vực C"};

        for (String kv : dsKhuVuc) {
            // 1. Tiêu đề khu vực
            JLabel lblTitle = new JLabel(kv);
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
            lblTitle.setForeground(new Color(142, 128, 106));
            lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
            lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
            panelDanhSachBan.add(lblTitle);

            // 2. Grid chứa bàn (5 cột cố định)
            JPanel pnlGrid = new JPanel(new GridLayout(0, 5, 20, 20));
            pnlGrid.setOpaque(false);
            pnlGrid.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Giả lập 10 bàn mỗi khu vực
            for (int i = 1; i <= 10; i++) {
                String kyTu = kv.substring(kv.length() - 1);
                String maBan = "Bàn " + kyTu + "." + i;
                
                JPanel item;
                if (maBan.equals("Bàn A.7")) {
                    item = createBanItem(maBan, "Đang dùng", new Color(0, 200, 83));
                    setWhiteText(item);
                } else if (i == 3) {
                    item = createBanItem(maBan, "Đã đặt", new Color(255, 137, 4));
                    setWhiteText(item);
                } else {
                    item = createBanItem(maBan, "Trống", Color.WHITE);
                }
                pnlGrid.add(item);
            }

            // Tính toán chiều cao cần thiết cho Grid (mỗi hàng khoảng 180px + 20px gap)
            int rows = (int) Math.ceil(pnlGrid.getComponentCount() / 5.0);
            int prefHeight = (rows * 180) + ((rows - 1) * 20); 
            
            // Ép kích thước để BoxLayout không làm co panel về 0
            pnlGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, prefHeight));
            pnlGrid.setPreferredSize(new Dimension(pnlGrid.getPreferredSize().width, prefHeight));

            panelDanhSachBan.add(pnlGrid);
            
            // Khoảng cách giữa các khu vực
            panelDanhSachBan.add(Box.createRigidArea(new Dimension(0, 30)));
        }

        panelDanhSachBan.revalidate();
        panelDanhSachBan.repaint();
    }

    private JPanel createBanItem(String soBan, String trangThai, Color bgColor) {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(bgColor);
        pnl.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Viền và khoảng cách bên trong cho bàn
        pnl.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
            BorderFactory.createEmptyBorder(25, 10, 25, 10)
        ));

        JLabel lblSoBan = new JLabel(soBan, SwingConstants.CENTER);
        lblSoBan.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel lblTrangThai = new JLabel(trangThai, SwingConstants.CENTER);
        lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel pnlText = new JPanel(new GridLayout(2, 1));
        pnlText.setOpaque(false);
        pnlText.add(lblSoBan);
        pnlText.add(lblTrangThai);

        pnl.add(pnlText, BorderLayout.CENTER);
        return pnl;
    }

    private void setWhiteText(JPanel pnl) {
        for (Component comp : pnl.getComponents()) {
            if (comp instanceof JPanel) {
                for (Component lbl : ((JPanel) comp).getComponents()) {
                    lbl.setForeground(Color.WHITE);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTimKiem = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelDanhSachBan = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        panelTimKiem.setBackground(new java.awt.Color(255, 251, 233));
        panelTimKiem.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 1, 60));
        panelTimKiem.setLayout(new java.awt.GridLayout(1, 0));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setText("Tìm kiếm theo số điện thoại");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        btnTimKiem.setText("Tim Kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "A", "B", "C" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Trống", "Đang dùng", "Đã đặt" }));

        jLabel1.setText("Trống");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(0, 201, 80));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 137, 4));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jLabel2.setText("Đang dùng");

        jLabel3.setText("Đã đặt");

        jLabel4.setText("Khu vực:");

        jLabel5.setText("Trạng thái:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 399, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addGap(4, 4, 4)
                                    .addComponent(jLabel1))))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)))
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTimKiem.add(jPanel3);

        add(panelTimKiem, java.awt.BorderLayout.PAGE_START);

        panelDanhSachBan.setBackground(new java.awt.Color(255, 251, 233));
        panelDanhSachBan.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 60, 1, 40));
        panelDanhSachBan.setLayout(new java.awt.GridLayout(0, 5, 15, 15));
        jScrollPane1.setViewportView(panelDanhSachBan);
        panelDanhSachBan.getAccessibleContext().setAccessibleName("");
        panelDanhSachBan.getAccessibleContext().setAccessibleDescription("");

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel panelDanhSachBan;
    private javax.swing.JPanel panelTimKiem;
    // End of variables declaration//GEN-END:variables
}
