package com.restaurant.quanlydatbannhahang.gui;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.restaurant.quanlydatbannhahang.service.BanService;
import com.restaurant.quanlydatbannhahang.service.KhuVucService;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.entity.KhuVuc;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;

public class PanelDatBan extends javax.swing.JPanel {

    private Set<String> selectedTables;
    private Map<String, JPanel> tableCards;
    private Map<String, TrangThaiBan> tableTrangThai; // Lưu trạng thái của từng bàn
    private Map<String, JLabel> tableLabelStatus; // Lưu reference của label trạng thái
    private JButton btnDatBan;
    private String selectedKhuVuc = null;
    private String selectedTrangThai = null;

    public PanelDatBan() {
        selectedTables = new HashSet<>();
        tableCards = new HashMap<>();
        tableTrangThai = new HashMap<>();
        tableLabelStatus = new HashMap<>();
        initComponents();
        customUI();
        setUpDatBanButton();
        scrSoDoBan.getViewport().setBackground(new java.awt.Color(255, 251, 233));
        loadDataToComboBoxes();
        loadSoDoBanFromDatabase();
    }

    private void customUI() {
        // Placeholder cho txtTimKiem
        setupPlaceholder(txtTimKiem, "Nhập mã bàn");
    }

    private void loadDataToComboBoxes() {
        try {
            // Load cbFilterKhuVuc từ database
            KhuVucService khuVucService = new KhuVucService();
            java.util.List<KhuVuc> khuVucList = khuVucService.getAllKhuVuc();

            cbFilterKhuVuc.removeAllItems();
            cbFilterKhuVuc.addItem("-- Tất cả --");
            for (KhuVuc kv : khuVucList) {
                cbFilterKhuVuc.addItem(kv.getMaKhuVuc());
            }

            // Load cbFilterTrangThai từ enum TrangThaiBan
            cbFilterTrangThai.removeAllItems();
            cbFilterTrangThai.addItem("-- Tất cả --");
            for (TrangThaiBan trangThai : TrangThaiBan.values()) {
                cbFilterTrangThai.addItem(trangThai.getDisplayName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu filter: " + e.getMessage());
        }
    }

    private void setUpDatBanButton() {
        // Button Trang chủ - bên trái
        JButton btnTrangChu = new JButton("Trang chủ");
        btnTrangChu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnTrangChu.setBackground(new Color(200, 200, 200));
        btnTrangChu.setForeground(Color.BLACK);
        btnTrangChu.setFocusPainted(false);
        btnTrangChu.setOpaque(true);
        btnTrangChu.setBorder(BorderFactory.createEmptyBorder());
        btnTrangChu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTrangChu.setPreferredSize(new Dimension(150, 50));
        btnTrangChu.addActionListener(e -> {
            java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
            if (parentFrame instanceof MainForm) {
                ((MainForm) parentFrame).goToTrangChuFromPanel();
            }
        });

        // Button Đặt bàn - bên phải
        btnDatBan = new JButton("Đặt bàn");
        btnDatBan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDatBan.setBackground(new Color(5, 223, 114));
        btnDatBan.setForeground(Color.BLACK);
        btnDatBan.setFocusPainted(false);
        btnDatBan.setOpaque(true);
        btnDatBan.setBorder(BorderFactory.createEmptyBorder());
        btnDatBan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDatBan.setPreferredSize(new Dimension(150, 50));
        btnDatBan.addActionListener(e -> onButtonDatBanClicked());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new java.awt.Color(255, 251, 233));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 20, 60));

        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftButtonPanel.setBackground(new java.awt.Color(255, 251, 233));
        leftButtonPanel.add(btnTrangChu);

        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightButtonPanel.setBackground(new java.awt.Color(255, 251, 233));
        rightButtonPanel.add(btnDatBan);

        bottomPanel.add(leftButtonPanel, BorderLayout.WEST);
        bottomPanel.add(rightButtonPanel, BorderLayout.EAST);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadSoDoBanFromDatabase() {
        panelSoDoBan.removeAll();
        // Dùng JPanel với layout manager để giữ kích cỡ ban đầu
        panelSoDoBan.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        tableCards.clear();
        tableTrangThai.clear();
        tableLabelStatus.clear();
        selectedTables.clear();

        try {
            BanService banService = new BanService();
            java.util.List<Ban> allBan = banService.getAllBan();

            Map<String, java.util.List<Ban>> banByKhuVuc = new TreeMap<>();
            for (Ban ban : allBan) {
                // Áp dụng filter khuVuc
                if (selectedKhuVuc != null && !selectedKhuVuc.isEmpty() && !selectedKhuVuc.equals("-- Tất cả --")) {
                    if (!ban.getKhuVuc().getMaKhuVuc().equals(selectedKhuVuc)) {
                        continue;
                    }
                }

                // Áp dụng filter trangThai
                if (selectedTrangThai != null && !selectedTrangThai.isEmpty()
                        && !selectedTrangThai.equals("-- Tất cả --")) {
                    if (!ban.getTrangThai().getDisplayName().equals(selectedTrangThai)) {
                        continue;
                    }
                }

                String maKV = ban.getKhuVuc().getMaKhuVuc();
                banByKhuVuc.putIfAbsent(maKV, new ArrayList<>());
                banByKhuVuc.get(maKV).add(ban);
            }

            // Tạo wrapper panel với BoxLayout để wrap các group
            JPanel wrapperPanel = new JPanel();
            wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
            wrapperPanel.setBackground(new java.awt.Color(255, 251, 233));
            wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            for (String khuVuc : banByKhuVuc.keySet()) {
                JPanel pnlGroup = new JPanel();
                pnlGroup.setLayout(new BorderLayout());
                pnlGroup.setBackground(new java.awt.Color(255, 251, 233));
                pnlGroup.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
                pnlGroup.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

                JLabel lblTitle = new JLabel("Khu vực " + khuVuc);
                lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
                lblTitle.setForeground(new Color(153, 153, 102));
                lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
                pnlGroup.add(lblTitle, BorderLayout.NORTH);

                JPanel pnlTables = new JPanel(new GridLayout(0, 5, 20, 20));
                pnlTables.setBackground(new java.awt.Color(255, 251, 233));
                pnlTables.setPreferredSize(new Dimension(1200, 200));

                for (Ban ban : banByKhuVuc.get(khuVuc)) {
                    pnlTables.add(createTableCard(ban.getMaBan(), ban.getTrangThai()));
                }

                pnlGroup.add(pnlTables, BorderLayout.CENTER);
                wrapperPanel.add(pnlGroup);
            }

            panelSoDoBan.add(wrapperPanel);
            panelSoDoBan.revalidate();
            panelSoDoBan.repaint();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load bàn từ database: " + e.getMessage());
        }
    }

    private JPanel createTableCard(String maBan, TrangThaiBan trangThai) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(150, 120));

        // Set background theo trạng thái bàn
        if (trangThai == TrangThaiBan.TRONG) {
            card.setBackground(Color.WHITE);
        } else if (trangThai == TrangThaiBan.DANG_DUNG) {
            card.setBackground(new Color(0, 200, 100)); // Xanh lá - bàn đang dùng
        } else if (trangThai == TrangThaiBan.DA_DAT) {
            card.setBackground(new Color(255, 200, 0)); // Vàng/cam - bàn đã đặt
        }

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        tableCards.put(maBan, card);
        tableTrangThai.put(maBan, trangThai);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblName = new JLabel(maBan, SwingConstants.CENTER);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblName.setForeground(Color.BLACK);
        gbc.gridy = 0;
        gbc.weighty = 0.6;
        card.add(lblName, gbc);

        JLabel lblStatus = new JLabel(trangThai.getDisplayName(), SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblStatus.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.weighty = 0.4;
        card.add(lblStatus, gbc);

        // Lưu reference của label trạng thái
        tableLabelStatus.put(maBan, lblStatus);

        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toggleTableSelection(maBan, card);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!selectedTables.contains(maBan) && trangThai != TrangThaiBan.DANG_DUNG) {
                    card.setBorder(BorderFactory.createLineBorder(new Color(153, 153, 102), 2, true));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!selectedTables.contains(maBan)) {
                    card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
                }
            }
        });

        return card;
    }

    private void toggleTableSelection(String maBan, JPanel card) {
        // Lấy trạng thái hiện tại từ map
        TrangThaiBan trangThai = tableTrangThai.get(maBan);
        if (trangThai == null) {
            return;
        }

        // Xử lý bàn đã đặt
        if (trangThai == TrangThaiBan.DA_DAT) {
            JOptionPane.showMessageDialog(this,
                    "Nếu muốn làm trống bàn vui lòng chỉnh sửa bên quản lý phiếu đặt bàn",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Xử lý bàn đang dùng
        if (trangThai == TrangThaiBan.DANG_DUNG) {
            if (selectedTables.contains(maBan)) {
                int result = JOptionPane.showConfirmDialog(this,
                        "Bàn này đang được sử dụng. Bạn muốn chuyển trạng thái bàn thành Trống?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    selectedTables.remove(maBan);
                    tableTrangThai.put(maBan, TrangThaiBan.TRONG);

                    BanService banService = new BanService();
                    banService.capNhatTrangThaiBan(maBan, TrangThaiBan.TRONG);

                    JLabel lblStatus = tableLabelStatus.get(maBan);
                    if (lblStatus != null) {
                        lblStatus.setText(TrangThaiBan.TRONG.getDisplayName());
                    }
                    card.setBackground(Color.WHITE);
                    card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    card.revalidate();
                    card.repaint();
                }
            } else {
                int result = JOptionPane.showConfirmDialog(this,
                        "Bàn này đang được sử dụng. Bạn muốn chuyển trạng thái bàn thành Trống?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    selectedTables.remove(maBan);
                    // Cập nhật trạng thái bàn thành TRONG
                    tableTrangThai.put(maBan, TrangThaiBan.TRONG);
                    // Cập nhật trạng thái bàn lên database
                    BanService banService = new BanService();
                    banService.capNhatTrangThaiBan(maBan, TrangThaiBan.TRONG);
                    // Cập nhật label trạng thái ngay lập tức
                    JLabel lblStatus = tableLabelStatus.get(maBan);
                    if (lblStatus != null) {
                        lblStatus.setText(TrangThaiBan.TRONG.getDisplayName());
                    }
                    card.setBackground(Color.WHITE); // Đổi thành Trống
                    card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    card.revalidate();
                    card.repaint();
                }
            }
            return;
        }

        // Logic chọn/hủy chọn cho bàn Trống
        if (selectedTables.contains(maBan)) {
            selectedTables.remove(maBan);
            // Khôi phục màu gốc
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        } else {
            selectedTables.add(maBan);
            card.setBackground(new Color(255, 255, 100)); // Vàng nhạt - được chọn
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 200, 0), 2, true),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        }
        card.revalidate();
        card.repaint();
    }

    private void onButtonDatBanClicked() {
        if (selectedTables.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một bàn!");
            return;
        }

        String selectedBanList = String.join(", ", selectedTables);
        String message = "Có xác nhận đặt bàn: " + selectedBanList + "?";

        int result = JOptionPane.showConfirmDialog(
                this,
                message,
                "Xác nhận đặt bàn",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
            LuaChonDatBanDialog dialog = new LuaChonDatBanDialog(parentFrame, true);
            dialog.setVisible(true);

            selectedTables.clear();
            for (String maBan : tableCards.keySet()) {
                JPanel card = tableCards.get(maBan);
                TrangThaiBan trangThai = tableTrangThai.get(maBan);

                // Restore background theo trạng thái
                if (trangThai == TrangThaiBan.TRONG) {
                    card.setBackground(Color.WHITE);
                } else if (trangThai == TrangThaiBan.DANG_DUNG) {
                    card.setBackground(new Color(0, 200, 100));
                } else if (trangThai == TrangThaiBan.DA_DAT) {
                    card.setBackground(new Color(255, 200, 0));
                }

                // Restore label trạng thái
                JLabel lblStatus = tableLabelStatus.get(maBan);
                if (lblStatus != null) {
                    lblStatus.setText(trangThai.getDisplayName());
                }

                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                card.revalidate();
                card.repaint();
            }
        }
    }

    // từ đây trở xuống không sửa
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        panelTimKiem = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
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

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
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

        cbFilterTrangThai.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Trống", "Đang dùng", "Đã đặt" }));
        cbFilterTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterTrangThaiActionPerformed(evt);
            }
        });

        jLabel1.setText("Trống");

        pnlStatusTrong.setBackground(new java.awt.Color(255, 255, 255));
        pnlStatusTrong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

        javax.swing.GroupLayout pnlStatusTrongLayout = new javax.swing.GroupLayout(pnlStatusTrong);
        pnlStatusTrong.setLayout(pnlStatusTrongLayout);
        pnlStatusTrongLayout.setHorizontalGroup(
                pnlStatusTrongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 16, Short.MAX_VALUE));
        pnlStatusTrongLayout.setVerticalGroup(
                pnlStatusTrongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 16, Short.MAX_VALUE));

        pnlStatusServing.setBackground(new java.awt.Color(0, 201, 80));

        javax.swing.GroupLayout pnlStatusServingLayout = new javax.swing.GroupLayout(pnlStatusServing);
        pnlStatusServing.setLayout(pnlStatusServingLayout);
        pnlStatusServingLayout.setHorizontalGroup(
                pnlStatusServingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE));
        pnlStatusServingLayout.setVerticalGroup(
                pnlStatusServingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE));

        pnlStatusReserved.setBackground(new java.awt.Color(255, 137, 4));

        javax.swing.GroupLayout pnlStatusReservedLayout = new javax.swing.GroupLayout(pnlStatusReserved);
        pnlStatusReserved.setLayout(pnlStatusReservedLayout);
        pnlStatusReservedLayout.setHorizontalGroup(
                pnlStatusReservedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE));
        pnlStatusReservedLayout.setVerticalGroup(
                pnlStatusReservedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE));

        jLabel2.setText("Đang dùng");

        jLabel3.setText("Đã đặt");

        jLabel4.setText("Khu vực:");

        jLabel5.setText("Trạng thái:");

        javax.swing.GroupLayout panelTimKiemLayout = new javax.swing.GroupLayout(panelTimKiem);
        panelTimKiem.setLayout(panelTimKiemLayout);
        panelTimKiemLayout.setHorizontalGroup(
                panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTimKiemLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbFilterKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 99,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 64,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 100,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 298,
                                        Short.MAX_VALUE)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 271,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 103,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTimKiemLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pnlStatusTrong, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(pnlStatusServing, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addGap(24, 24, 24)
                                .addComponent(pnlStatusReserved, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)));
        panelTimKiemLayout.setVerticalGroup(
                panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelTimKiemLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(panelTimKiemLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel3)
                                        .addGroup(panelTimKiemLayout.createSequentialGroup()
                                                .addGroup(panelTimKiemLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(panelTimKiemLayout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(txtTimKiem,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 37,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(btnSearch,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 37,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                panelTimKiemLayout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(cbFilterTrangThai,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                37,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel5))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                panelTimKiemLayout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(cbFilterKhuVuc,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                37,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel4)))
                                                .addGap(18, 18, 18)
                                                .addGroup(panelTimKiemLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(pnlStatusTrong,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(pnlStatusServing,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(pnlStatusReserved,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel2,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel1,
                                                                javax.swing.GroupLayout.Alignment.TRAILING))))
                                .addContainerGap(9, Short.MAX_VALUE)));

        jPanel2.add(panelTimKiem);

        add(jPanel2, java.awt.BorderLayout.NORTH);

        scrSoDoBan.setBorder(null);

        panelSoDoBan.setBackground(new java.awt.Color(255, 251, 233));
        panelSoDoBan.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 60, 20, 40));

        javax.swing.GroupLayout panelSoDoBanLayout = new javax.swing.GroupLayout(panelSoDoBan);
        panelSoDoBan.setLayout(panelSoDoBanLayout);
        panelSoDoBanLayout.setHorizontalGroup(
                panelSoDoBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1103, Short.MAX_VALUE));
        panelSoDoBanLayout.setVerticalGroup(
                panelSoDoBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 496, Short.MAX_VALUE));

        scrSoDoBan.setViewportView(panelSoDoBan);

        add(scrSoDoBan, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
        String selected = (String) cbFilterTrangThai.getSelectedItem();
        if (selected != null && selected.equals("-- Tất cả --")) {
            selectedTrangThai = null;
        } else {
            selectedTrangThai = selected;
        }
        loadSoDoBanFromDatabase();
    }// GEN-LAST:event_cbFilterTrangThaiActionPerformed

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

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtTimKiemActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtSearchActionPerformed

    private void cbFilterKhuVucActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterKhuVucActionPerformed
        String selected = (String) cbFilterKhuVuc.getSelectedItem();
        if (selected != null && selected.equals("-- Tất cả --")) {
            selectedKhuVuc = null;
        } else {
            selectedKhuVuc = selected;
        }
        loadSoDoBanFromDatabase();
    }// GEN-LAST:event_cbFilterKhuVucActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSearchActionPerformed
        String searchText = txtTimKiem.getText().trim();

        // Nếu search text là placeholder hoặc trống thì load toàn bộ
        if (searchText.isEmpty() || searchText.equals("Nhập mã bàn")) {
            // Reset filter status
            selectedKhuVuc = null;
            selectedTrangThai = null;
            cbFilterKhuVuc.setSelectedItem("-- Tất cả --");
            cbFilterTrangThai.setSelectedItem("-- Tất cả --");
            loadSoDoBanFromDatabase();
            return;
        }

        // Search bàn theo mã
        panelSoDoBan.removeAll();
        panelSoDoBan.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        tableCards.clear();
        tableTrangThai.clear();
        tableLabelStatus.clear();
        selectedTables.clear();

        try {
            BanService banService = new BanService();
            java.util.List<Ban> allBan = banService.getAllBan();

            Map<String, java.util.List<Ban>> banByKhuVuc = new TreeMap<>();
            for (Ban ban : allBan) {
                // Filter theo từ khóa tìm kiếm (case-insensitive)
                if (!ban.getMaBan().toLowerCase().contains(searchText.toLowerCase())) {
                    continue;
                }

                String maKV = ban.getKhuVuc().getMaKhuVuc();
                banByKhuVuc.putIfAbsent(maKV, new ArrayList<>());
                banByKhuVuc.get(maKV).add(ban);
            }

            if (banByKhuVuc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy bàn: " + searchText, "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JPanel wrapperPanel = new JPanel();
                wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
                wrapperPanel.setBackground(new java.awt.Color(255, 251, 233));
                wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                for (String khuVuc : banByKhuVuc.keySet()) {
                    JPanel pnlGroup = new JPanel();
                    pnlGroup.setLayout(new BorderLayout());
                    pnlGroup.setBackground(new java.awt.Color(255, 251, 233));
                    pnlGroup.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
                    pnlGroup.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

                    JLabel lblTitle = new JLabel("Khu vực " + khuVuc);
                    lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    lblTitle.setForeground(new Color(153, 153, 102));
                    lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
                    pnlGroup.add(lblTitle, BorderLayout.NORTH);

                    JPanel pnlTables = new JPanel(new GridLayout(0, 5, 20, 20));
                    pnlTables.setBackground(new java.awt.Color(255, 251, 233));
                    pnlTables.setPreferredSize(new Dimension(1200, 200));

                    for (Ban ban : banByKhuVuc.get(khuVuc)) {
                        pnlTables.add(createTableCard(ban.getMaBan(), ban.getTrangThai()));
                    }

                    pnlGroup.add(pnlTables, BorderLayout.CENTER);
                    wrapperPanel.add(pnlGroup);
                }

                panelSoDoBan.add(wrapperPanel);
            }

            panelSoDoBan.revalidate();
            panelSoDoBan.repaint();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm: " + e.getMessage());
        }
    }// GEN-LAST:event_btnSearchActionPerformed

    // Không sửa bên dưới
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
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
