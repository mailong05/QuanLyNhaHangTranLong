package com.restaurant.quanlydatbannhahang.gui;

import javax.swing.*;
import java.awt.*;

public class PanelDatBan extends javax.swing.JPanel {

    /**
     * Constructor
     */
    public PanelDatBan() {
        initComponents();
        // Cần chỉnh lại JScrollPane để Viewport có màu nền trùng với app
        scrSoDoBan.getViewport().setBackground(new java.awt.Color(255, 251, 233));
        loadSoDoBan(""); // Mặc định hiện tất cả khu vực như hình mẫu
    }

    public void loadSoDoBan(String khuVucFilter) {
        // 1. Xóa bàn cũ
        panelSoDoBan.removeAll();

        // 2. Sử dụng BoxLayout theo trục Y để xếp các "Cụm khu vực" từ trên xuống
        panelSoDoBan.setLayout(new BoxLayout(panelSoDoBan, BoxLayout.Y_AXIS));

        // 3. Danh sách các khu vực cần hiển thị
        String[] khuVucs = {"A", "B", "C"};

        for (String kv : khuVucs) {
            // Nếu có lọc khu vực mà không trùng thì bỏ qua
            if (!khuVucFilter.isEmpty() && !khuVucFilter.equals("Tất cả") && !khuVucFilter.equals(kv)) {
                continue;
            }

            // --- TẠO CỤM KHU VỰC ---
            JPanel pnlGroup = new JPanel();
            pnlGroup.setLayout(new BorderLayout());
            pnlGroup.setBackground(new java.awt.Color(255, 251, 233));
            pnlGroup.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            // Tiêu đề khu vực (Khu vực A, Khu vực B...)
            JLabel lblTitle = new JLabel("Khu vực " + kv);
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
            lblTitle.setForeground(new Color(153, 153, 102)); // Màu xám nâu giống mẫu
            lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            pnlGroup.add(lblTitle, BorderLayout.NORTH);

            // Panel chứa các bàn (Grid 5 cột)
            JPanel pnlTables = new JPanel(new GridLayout(0, 5, 20, 20));
            pnlTables.setBackground(new java.awt.Color(255, 251, 233));

            for (int i = 1; i <= 10; i++) {
                String maBan = kv + "." + String.format("%03d", i);
                String trangThai = "Trống";
                pnlTables.add(createTableCard(maBan, trangThai));
            }

            pnlGroup.add(pnlTables, BorderLayout.CENTER);
            panelSoDoBan.add(pnlGroup);
        }

        // 4. Vẽ lại giao diện
        panelSoDoBan.revalidate();
        panelSoDoBan.repaint();
    }

    /**
     * Tạo Card bàn với nội dung CĂN GIỮA TUYỆT ĐỐI và BO GÓC
     */
    private JPanel createTableCard(String name, String status) {
        // Sử dụng JPanel với GridBagLayout để căn giữa nội dung hoàn hảo
        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(150, 120));
        card.setBackground(Color.WHITE);
        
        // Tạo viền bo góc nhẹ và màu nhạt giống mẫu
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Label Tên bàn ---
        JLabel lblName = new JLabel(name, SwingConstants.CENTER);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblName.setForeground(Color.BLACK);
        gbc.gridy = 0;
        gbc.weighty = 0.6;
        card.add(lblName, gbc);

        // --- Label Trạng thái ---
        JLabel lblStatus = new JLabel(status, SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblStatus.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.weighty = 0.4;
        card.add(lblStatus, gbc);

        // Hiệu ứng hover và click
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(null, "Bạn đã chọn bàn: " + name);
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(new Color(153, 153, 102), 2, true));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
            }
        });

        return card;
    }

    // từ đây trở xuống không sửa
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        panelTimKiem = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        cbFilterKhuVuc = new javax.swing.JComboBox<>();
        cbFilterTrangThai = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        pnlStatusTrong = new javax.swing.JPanel();
        pnlStatusServing = new javax.swing.JPanel();
        pnlStatusReserved = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        scrSoDoBan = new javax.swing.JScrollPane();
        panelSoDoBan = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 251, 233));
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 1, 60));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        panelTimKiem.setBackground(new java.awt.Color(255, 255, 255));

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnSearch.setText("Tim Kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        cbFilterKhuVuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "A", "B", "C" }));
        cbFilterKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterKhuVucActionPerformed(evt);
            }
        });

        cbFilterTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Trống", "Đang dùng", "Đã đặt" }));

        jLabel1.setText("Trống");

        pnlStatusTrong.setBackground(new java.awt.Color(255, 255, 255));
        pnlStatusTrong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

        javax.swing.GroupLayout pnlStatusTrongLayout = new javax.swing.GroupLayout(pnlStatusTrong);
        pnlStatusTrong.setLayout(pnlStatusTrongLayout);
        pnlStatusTrongLayout.setHorizontalGroup(
            pnlStatusTrongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );
        pnlStatusTrongLayout.setVerticalGroup(
            pnlStatusTrongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        pnlStatusServing.setBackground(new java.awt.Color(0, 201, 80));

        javax.swing.GroupLayout pnlStatusServingLayout = new javax.swing.GroupLayout(pnlStatusServing);
        pnlStatusServing.setLayout(pnlStatusServingLayout);
        pnlStatusServingLayout.setHorizontalGroup(
            pnlStatusServingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        pnlStatusServingLayout.setVerticalGroup(
            pnlStatusServingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlStatusReserved.setBackground(new java.awt.Color(255, 137, 4));

        javax.swing.GroupLayout pnlStatusReservedLayout = new javax.swing.GroupLayout(pnlStatusReserved);
        pnlStatusReserved.setLayout(pnlStatusReservedLayout);
        pnlStatusReservedLayout.setHorizontalGroup(
            pnlStatusReservedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        pnlStatusReservedLayout.setVerticalGroup(
            pnlStatusReservedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jLabel2.setText("Đang dùng");

        jLabel3.setText("Đã đặt");

        jLabel4.setText("Khu vực:");

        jLabel5.setText("Trạng thái:");

        javax.swing.GroupLayout panelTimKiemLayout = new javax.swing.GroupLayout(panelTimKiem);
        panelTimKiem.setLayout(panelTimKiemLayout);
        panelTimKiemLayout.setHorizontalGroup(
            panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTimKiemLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTimKiemLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbFilterKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 342, Short.MAX_VALUE)
                        .addComponent(pnlStatusTrong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlStatusServing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlStatusReserved, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelTimKiemLayout.createSequentialGroup()
                        .addComponent(txtSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37))
        );
        panelTimKiemLayout.setVerticalGroup(
            panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTimKiemLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlStatusTrong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(pnlStatusReserved, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(pnlStatusServing, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelTimKiemLayout.createSequentialGroup()
                                    .addGap(4, 4, 4)
                                    .addComponent(jLabel1))))
                        .addGroup(panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbFilterKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)))
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(panelTimKiem);

        add(jPanel2, java.awt.BorderLayout.NORTH);

        scrSoDoBan.setBorder(null);

        panelSoDoBan.setBackground(new java.awt.Color(255, 251, 233));
        panelSoDoBan.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 60, 20, 40));

        javax.swing.GroupLayout panelSoDoBanLayout = new javax.swing.GroupLayout(panelSoDoBan);
        panelSoDoBan.setLayout(panelSoDoBanLayout);
        panelSoDoBanLayout.setHorizontalGroup(
            panelSoDoBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1091, Short.MAX_VALUE)
        );
        panelSoDoBanLayout.setVerticalGroup(
            panelSoDoBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );

        scrSoDoBan.setViewportView(panelSoDoBan);

        add(scrSoDoBan, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void cbFilterKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterKhuVucActionPerformed
        String selected = cbFilterKhuVuc.getSelectedItem().toString();
        if (selected.equals("Tất cả")) {
            loadSoDoBan(""); // Hiển thị hết
        } else {
            loadSoDoBan(selected); // Lọc theo A, B hoặc C
        }
    }//GEN-LAST:event_cbFilterKhuVucActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    //Không sửa bên dưới
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbFilterKhuVuc;
    private javax.swing.JComboBox<String> cbFilterTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelSoDoBan;
    private javax.swing.JPanel panelTimKiem;
    private javax.swing.JPanel pnlStatusReserved;
    private javax.swing.JPanel pnlStatusServing;
    private javax.swing.JPanel pnlStatusTrong;
    private javax.swing.JScrollPane scrSoDoBan;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
