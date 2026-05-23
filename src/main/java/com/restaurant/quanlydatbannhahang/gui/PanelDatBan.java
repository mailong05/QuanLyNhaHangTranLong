package com.restaurant.quanlydatbannhahang.gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.restaurant.quanlydatbannhahang.service.BanService;
import com.restaurant.quanlydatbannhahang.service.KhuVucService;
import com.restaurant.quanlydatbannhahang.service.PhieuDatBanService;
import com.restaurant.quanlydatbannhahang.service.ChiTietPhieuDatBanService;
import com.restaurant.quanlydatbannhahang.session.HoaDonDraftSession;
import com.restaurant.quanlydatbannhahang.session.ReservationSession;
import com.restaurant.quanlydatbannhahang.session.SessionManager;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.entity.KhuVuc;
import com.restaurant.quanlydatbannhahang.entity.PhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import com.restaurant.quanlydatbannhahang.entity.ChiTietPhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiPhieuDat;

public class PanelDatBan extends javax.swing.JPanel {
    private Set<String> selectedTables;
    private Set<String> originalTablesForEdit;
    private Map<String, JPanel> tableCards;
    private Map<String, TrangThaiBan> tableTrangThai;
    private Map<String, JLabel> tableLabelStatus;
    private JButton btnTrangChu;
    private JButton btnDatBanDungNgay;
    private JButton btnDatBanTruoc;
    private JButton btnDoiBan;
    private JButton btnXoaTrang;
    private JButton btnGopBan;
    private boolean isMerging = false;
    private boolean isSwitching = false;
    private String selectedKhuVuc = null;
    private String selectedTrangThai = null;
    private PanelQuanLyDatBanTruoc panelQuanLyDatBanTruoc = null;
    private PanelDatMon panelDatMon = null;
    private boolean editMode = false;
    private boolean reservationMode = false;
    private Set<String> availableBanForReservation = new HashSet<>();
    private boolean billMode = false;
    private String flowOrigin = "";
    private static final Color EDIT_MODE_SELECTED_COLOR = new Color(51, 153, 255); // Xánh dương (bàn cũ giữ lại)
    private static final Color EDIT_MODE_NEW_COLOR = new Color(255, 255, 150); // Vàng nhạt (bàn mới chọn)
    private static final Color EDIT_MODE_VACATING_COLOR = Color.WHITE; // Trắng (bàn cũ bỏ chọn)
    private BanService banService;

    public PanelDatBan() {
        selectedTables = new HashSet<>();
        originalTablesForEdit = new HashSet<>();
        tableCards = new HashMap<>();
        tableTrangThai = new HashMap<>();
        tableLabelStatus = new HashMap<>();
        banService = new BanService();
        initComponents();
        customUI();
        setUpDatBanButton();
        scrSoDoBan.getViewport().setBackground(new java.awt.Color(255, 251, 233));
        loadDataToComboBoxes();
        loadSoDoBanFromDatabase();
        updateAllTableStatusFromDatabase();
        this.addHierarchyListener(new java.awt.event.HierarchyListener() {
            @Override
            public void hierarchyChanged(java.awt.event.HierarchyEvent e) {
                if ((e.getChangeFlags() & java.awt.event.HierarchyEvent.SHOWING_CHANGED) != 0) {
                    if (isShowing()) {
                        syncButtonVisibilityAndState();
                        if (!editMode) {
                            refreshData();
                        } else {
                            panelSoDoBan.revalidate();
                            panelSoDoBan.repaint();
                            PanelDatBan.this.revalidate();
                            PanelDatBan.this.repaint();
                        }
                    }  else {
                        flowOrigin = "";
                        isMerging = false;
                        isSwitching = false;
                        editMode = false;
                        reservationMode = false;
                        billMode = false;
                        selectedTables.clear();
                        originalTablesForEdit.clear();
                        availableBanForReservation.clear();
                        panelQuanLyDatBanTruoc = null;
                        panelDatMon = null;

                        ReservationSession.setTempSelectedDateTime(null); 
                        
                        System.out.println("logg: Đã clear toàn bộ state và Session thời gian!");

                    }
                }
            }

            // private void isEnableBtnGopBan() {
            // // TODO Auto-generated method stub
            // if (flowOrigin.isBlank()) {
            // return;
            // }
            // if (flowOrigin.equals("GOP_BAN")) {
            // btnDoiBan.setEnabled(true);
            // }
            // }
        });

    }

    private void syncButtonVisibilityAndState() {
        System.out.println(flowOrigin);
        if (flowOrigin.equals("DAT_MON")) {
            btnGopBan.setEnabled(true);
            btnDoiBan.setEnabled(false);
        } else if (flowOrigin.equals("QUAN_LY_DAT_TRUOC")) {
            btnDoiBan.setEnabled(true);
            btnGopBan.setEnabled(false);
        } else {
            btnDoiBan.setEnabled(false);
            btnGopBan.setEnabled(false);
        }

        if (btnDoiBan.getParent() != null) {
            btnDoiBan.getParent().revalidate();
            btnDoiBan.getParent().repaint();
        }
    }

    public void setSelectedTablesForEdit(Set<String> tablesToSelect, LocalDateTime thoiGianPhieu) {
        this.editMode = true;
        this.billMode = false;
        this.reservationMode = false;

        if (thoiGianPhieu != null) {
            ReservationSession.setTempSelectedDateTime(thoiGianPhieu);
        }

        this.originalTablesForEdit.clear();
        this.selectedTables.clear();
        if (tablesToSelect != null) {
            this.originalTablesForEdit.addAll(tablesToSelect);
            this.selectedTables.addAll(tablesToSelect);
        }

        if (btnDoiBan != null)
            btnDoiBan.setEnabled(true);

        if (btnGopBan != null)
            btnGopBan.setEnabled(true);


        loadSoDoBanFromDatabase();
    }

    public void setSelectedTablesForEdit(Set<String> tablesToSelect) {
        this.setSelectedTablesForEdit(tablesToSelect, null);
    }

    public void setPanelQuanLyDatBanTruoc(PanelQuanLyDatBanTruoc panelQuanLyDatBanTruoc) {
        this.panelQuanLyDatBanTruoc = panelQuanLyDatBanTruoc;
        this.panelDatMon = null;
    }

    public void setPanelDatMon(PanelDatMon panelDatMon) {
        this.panelDatMon = panelDatMon;
        this.panelQuanLyDatBanTruoc = null;
    }

    public void setFlowOrigin(String origin) {
        this.flowOrigin = (origin != null) ? origin : "";
    }

    public String getFlowOrigin() {
        return this.flowOrigin;
    }

    public void setBillMode(boolean billMode) {
        this.billMode = billMode;
        this.editMode = false;
        selectedTables.clear();
        if (btnDatBanDungNgay != null) {
            btnDatBanDungNgay.setEnabled(!billMode);
        }
        if (btnDatBanTruoc != null) {
            btnDatBanTruoc.setEnabled(!billMode);
        }
        if (btnDoiBan != null) {
            btnDoiBan.setEnabled(false);
        }
        if (btnGopBan != null) {
            btnGopBan.setEnabled(false);
        }
        refreshData();
    }

    public boolean isBillMode() {
        return this.billMode;
    }

    public void refreshData() {
        try {
            this.editMode = false;
            selectedTables.clear();
            originalTablesForEdit.clear();
            tableCards.clear();
            tableTrangThai.clear();
            tableLabelStatus.clear();
            loadSoDoBanFromDatabase();
            syncButtonVisibilityAndState();
            updateAllTableStatusFromDatabase();
            this.revalidate();
            this.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void customUI() {
        setupPlaceholder(txtTimKiem, "Nhập mã bàn");
    }

    private void loadDataToComboBoxes() {
        try {
            KhuVucService khuVucService = new KhuVucService();
            java.util.List<KhuVuc> khuVucList = khuVucService.getAllKhuVuc();
            cbFilterKhuVuc.removeAllItems();
            cbFilterKhuVuc.addItem("Khu vực");
            for (KhuVuc kv : khuVucList) {
                cbFilterKhuVuc.addItem(kv.getMaKhuVuc());
            }
            cbFilterTrangThai.removeAllItems();
            cbFilterTrangThai.addItem("Trạng thái");
            for (TrangThaiBan trangThai : TrangThaiBan.values()) {
                cbFilterTrangThai.addItem(trangThai.getDisplayName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu filter: " + e.getMessage());
        }
    }

    private void setUpDatBanButton() {
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
        btnDatBanDungNgay = new JButton("Đặt bàn dùng ngay");
        btnDatBanDungNgay.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnDatBanDungNgay.setBackground(new Color(5, 223, 114));
        btnDatBanDungNgay.setForeground(Color.BLACK);
        btnDatBanDungNgay.setFocusPainted(false);
        btnDatBanDungNgay.setOpaque(true);
        btnDatBanDungNgay.setBorder(BorderFactory.createEmptyBorder());
        btnDatBanDungNgay.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDatBanDungNgay.setPreferredSize(new Dimension(150, 50));
        btnDatBanDungNgay.addActionListener(e -> onButtonDatBanDungNgayClicked());

        btnDatBanTruoc = new JButton("Đặt bàn trước");
        btnDatBanTruoc.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnDatBanTruoc.setBackground(new Color(255, 193, 7));
        btnDatBanTruoc.setForeground(Color.BLACK);
        btnDatBanTruoc.setFocusPainted(false);
        btnDatBanTruoc.setOpaque(true);
        btnDatBanTruoc.setBorder(BorderFactory.createEmptyBorder());
        btnDatBanTruoc.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDatBanTruoc.setPreferredSize(new Dimension(150, 50));
        btnDatBanTruoc.addActionListener(e -> onButtonDatBanTruocClicked());

        btnDoiBan = new JButton("Đổi bàn");
        btnDoiBan.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnDoiBan.setBackground(new Color(255, 87, 34));
        btnDoiBan.setForeground(Color.WHITE);
        btnDoiBan.setFocusPainted(false);
        btnDoiBan.setOpaque(true);
        btnDoiBan.setBorder(BorderFactory.createEmptyBorder());
        btnDoiBan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDoiBan.setPreferredSize(new Dimension(150, 50));
        btnDoiBan.addActionListener(e -> onButtonDoiBanClicked());
        btnDoiBan.setEnabled(false);

        // Nút Xóa trắng / Refresh trạng thái
        btnXoaTrang = new JButton("Làm mới");
        btnXoaTrang.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnXoaTrang.setBackground(new Color(200, 200, 200));
        btnXoaTrang.setForeground(Color.BLACK);
        btnXoaTrang.setFocusPainted(false);
        btnXoaTrang.setOpaque(true);
        btnXoaTrang.setBorder(BorderFactory.createEmptyBorder());
        btnXoaTrang.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnXoaTrang.setPreferredSize(new Dimension(120, 40));
        btnXoaTrang.addActionListener(e -> {
            // Refresh lại trạng thái từ DB
            try {
                updateAllTableStatusFromDatabase();
                // Reset bất kỳ mode tạm thời nào
                isMerging = false;
                isSwitching = false;
                editMode = false;
                selectedTables.clear();
                originalTablesForEdit.clear();
                repaintAllUI();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Nút Gộp bàn: toggle chế độ gộp
        btnGopBan = new JButton("Gộp bàn");
        btnGopBan.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGopBan.setBackground(new Color(33, 150, 243));
        btnGopBan.setForeground(Color.WHITE);
        btnGopBan.setFocusPainted(false);
        btnGopBan.setOpaque(true);
        btnGopBan.setBorder(BorderFactory.createEmptyBorder());
        btnGopBan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGopBan.setPreferredSize(new Dimension(150, 50));
        btnGopBan.addActionListener(e -> {
            // Toggle merge mode
            if (!isMerging) {
                // Bước 1: Vào chế độ Gộp
                if (!editMode) {
                    JOptionPane.showMessageDialog(this,
                            "Vui lòng vào chế độ Đổi bàn trước để bắt đầu gộp bàn.",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                isMerging = true;
                isSwitching = false;
                btnGopBan.setText("Hoàn tất gộp");
                repaintAllUI();
                JOptionPane.showMessageDialog(this,
                        "Đã vào chế độ Gộp bàn. Chọn bàn trống để gộp, nhấn 'Hoàn tất gộp' khi xong.",
                        "Chế độ Gộp bàn", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Bước 2: Thực hiện gộp bàn
                isMerging = false;
                btnGopBan.setText("Gộp bàn");

                if (selectedTables.isEmpty() || selectedTables.size() <= originalTablesForEdit.size()) {
                    JOptionPane.showMessageDialog(this,
                            "Vui lòng chọn ít nhất một bàn mới để gộp.",
                            "Thông báo", JOptionPane.WARNING_MESSAGE);
                    repaintAllUI();
                    return;
                }

                // Thực hiện merge flow
                try {
                    String message = "Xác nhận gộp bàn: " + String.join(", ", selectedTables) + "?";
                    int result = JOptionPane.showConfirmDialog(
                            this,
                            message,
                            "Xác nhận gộp bàn",
                            JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        flowOrigin = "GOP_BAN";
                        executeMergeTableFlow();
                        selectedTables.clear();
                        originalTablesForEdit.clear();
                        repaintAllUI();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Lỗi gộp bàn: " + ex.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new java.awt.Color(255, 251, 233));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 20, 60));
        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftButtonPanel.setBackground(new java.awt.Color(255, 251, 233));
        leftButtonPanel.add(btnTrangChu);
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightButtonPanel.setBackground(new java.awt.Color(255, 251, 233));
        rightButtonPanel.add(btnDatBanDungNgay);
        rightButtonPanel.add(btnDatBanTruoc);
        rightButtonPanel.add(btnGopBan);
        rightButtonPanel.add(btnDoiBan);
        rightButtonPanel.add(btnXoaTrang);
        bottomPanel.add(leftButtonPanel, BorderLayout.WEST);
        bottomPanel.add(rightButtonPanel, BorderLayout.EAST);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }


    private void loadSoDoBanFromDatabase() {
        panelSoDoBan.removeAll();
        panelSoDoBan.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        tableCards.clear();
        tableTrangThai.clear();
        tableLabelStatus.clear();
        try {
            LocalDateTime targetTime = null;

            if (ReservationSession.getTempSelectedDateTime() != null) {
                targetTime = ReservationSession.getTempSelectedDateTime();
            }

            else if (!originalTablesForEdit.isEmpty()) {
                // Trong chế độ edit, ReservationSession đã được set bởi
                // setSelectedTablesForEdit
                // Nếu vẫn null, dùng thời gian hiện tại
                targetTime = LocalDateTime.now();
            }
            // Normal/Dùng ngay -> dùng thời gian hiện tại
            else {
                targetTime = LocalDateTime.now();
            }

            // ========== BƯỚC 2: CHUẨN BỊ DỮ LIỆU XUNG ĐỘT ==========
            // Lấy danh sách bàn "Trong" (trống) tại targetTime
            PhieuDatBanService phieuService = new PhieuDatBanService();
            java.util.List<String> availableBans = phieuService.getDanhSachBanTrongTheoThoiGian(targetTime);
            // Giả định: availableBans chứa danh sách các bàn TRỐNG vào thời điểm targetTime

            BanService banService = new BanService();
            java.util.List<Ban> allBan = banService.getAllBan();

            // ========== BƯỚC 3: VÒNG LẶP ÁNH XẠ TRẠNG THÁI VỚI LOGIC "GIẢI OAN" ==========
            Map<String, java.util.List<Ban>> banByKhuVucForDisplay = new TreeMap<>();

            for (Ban ban : allBan) {
                // *** HIỂN THỊ TƯỜNG MINH - LOGIC "GIẢI OAN" ***
                // Lấy trạng thái gốc từ DB

                TrangThaiBan displayStatus;

                // 1. Ưu tiên cao nhất: Bàn đang phục vụ (DANG_DUNG) - Cái này là vật lý, không
                // thể chối cãi
                if (ban.getTrangThai() == TrangThaiBan.DANG_DUNG) {
                    displayStatus = TrangThaiBan.DANG_DUNG;
                }
                // 2. Nếu bàn nằm trong danh sách TRỐNG tại targetTime -> Nó phải là TRONG
                else if (availableBans.contains(ban.getMaBan())) {
                    displayStatus = TrangThaiBan.TRONG;
                }
                // 3. Nếu không phải 2 trường hợp trên -> Nó bị đặt lịch tại thời điểm
                // targetTime -> DA_DAT
                else {
                    displayStatus = TrangThaiBan.DA_DAT;
                }

                // *** LOGIC bảo vệ bàn gốc (cho flow Đổi/Gộp) ***
                // Bàn gốc đang được sửa luôn giữ nguyên trạng thái gốc, không bị override
                boolean isOriginal = (originalTablesForEdit != null && originalTablesForEdit.contains(ban.getMaBan()));
                if (isOriginal) {
                    displayStatus = ban.getTrangThai(); // Giữ nguyên trạng thái gốc của bàn đang sửa
                }

                // ========== BƯỚC 4: RENDERING (VẼ UI) ==========
                // Áp dụng các filter: cbFilterKhuVuc và cbFilterTrangThai

                // Filter theo Khu vực
                if (selectedKhuVuc != null && !selectedKhuVuc.isEmpty() && !selectedKhuVuc.equals("Khu vực")) {
                    if (!ban.getKhuVuc().getMaKhuVuc().equals(selectedKhuVuc)) {
                        continue; // Bỏ qua nếu không khớp khu vực được chọn
                    }
                }

                // Filter theo Trạng thái HIỂN THỊ (displayStatus, không phải trạng thái DB gốc)
                if (selectedTrangThai != null && !selectedTrangThai.isEmpty()
                        && !selectedTrangThai.equals("Trạng thái")) {
                    if (!displayStatus.getDisplayName().equals(selectedTrangThai)) {
                        continue; // Bỏ qua nếu không khớp trạng thái được lọc
                    }
                }

                // Filter chế độ Tính tiền (Hóa đơn): chỉ hiển thị bàn DANG_DUNG
                if (billMode && displayStatus != TrangThaiBan.DANG_DUNG) {
                    continue;
                }

                // Nếu vượt qua tất cả filter, thêm vào danh sách hiển thị
                String maKV = ban.getKhuVuc().getMaKhuVuc();
                banByKhuVucForDisplay.putIfAbsent(maKV, new ArrayList<>());
                banByKhuVucForDisplay.get(maKV).add(ban);
            }

            // Vẽ giao diện UI
            JPanel wrapperPanel = new JPanel();
            wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
            wrapperPanel.setBackground(new java.awt.Color(255, 251, 233));
            wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            for (String khuVuc : banByKhuVucForDisplay.keySet()) {
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

                for (Ban ban : banByKhuVucForDisplay.get(khuVuc)) {
                    // Tính lại displayStatus cho mỗi bàn
                    TrangThaiBan displayStatus = ban.getTrangThai();

                    // Logic "Giải oan"
                    if (ban.getTrangThai() == TrangThaiBan.DA_DAT) {
                        if (availableBans.contains(ban.getMaBan())) {
                            displayStatus = TrangThaiBan.TRONG; // Giải oan cho bàn này!
                        }
                    }

                    // Logic bảo vệ bàn gốc
                    boolean isOriginal = (originalTablesForEdit != null
                            && originalTablesForEdit.contains(ban.getMaBan()));
                    if (isOriginal) {
                        displayStatus = ban.getTrangThai();
                    }

                    // Tạo card với displayStatus (trạng thái đã ánh xạ)
                    pnlTables.add(createTableCard(ban.getMaBan(), displayStatus));
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
        setTableCardBackground(maBan, card, trangThai);
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
        String statusLabel = (reservationMode && availableBanForReservation.contains(maBan))
                ? TrangThaiBan.TRONG.getDisplayName()
                : trangThai.getDisplayName();
        JLabel lblStatus = new JLabel(statusLabel, SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblStatus.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.weighty = 0.4;
        card.add(lblStatus, gbc);
        tableLabelStatus.put(maBan, lblStatus);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toggleTableSelection(maBan, card);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!selectedTables.contains(maBan) && tableTrangThai.get(maBan) != TrangThaiBan.DANG_DUNG) {
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
        TrangThaiBan trangThai = tableTrangThai.get(maBan);
        if (trangThai == null) {
            return;
        }

        if (editMode) {
            // *** LOGIC GỘP BÀN: KHÔNG CHO PHÉP BỎ CHỌN BÀN GỐC ***
            if (isMerging) {
                if (selectedTables.contains(maBan)) {
                    // Nếu là bàn gốc (originalTablesForEdit), cấm bỏ chọn
                    if (originalTablesForEdit.contains(maBan)) {
                        JOptionPane.showMessageDialog(this,
                                "Bàn này đang phục vụ khách. Không thể bỏ chọn khi gộp bàn.",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    // Nếu là bàn mới chọn, cho phép bỏ chọn
                    selectedTables.remove(maBan);
                    setTableCardBackground(maBan, card, trangThai);
                } else {
                    // Chỉ cho phép chọn bàn TRỐNG (khả dụng)
                    if (trangThai == TrangThaiBan.TRONG) {
                        selectedTables.add(maBan);
                        setTableCardBackground(maBan, card, trangThai);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Chỉ có thể chọn bàn đang trống khi gộp bàn.",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                card.revalidate();
                card.repaint();
                return;
            }

            // *** LOGIC ĐỔI BÀN: BÌNH THƯỜNG ***
            if (selectedTables.contains(maBan)) {
                // Cho phép xóa khỏi Set (bỏ chọn)
                selectedTables.remove(maBan);
                setTableCardBackground(maBan, card, trangThai);
            } else {
                // Kiểm tra bàn khả dụng theo sessionTime
                LocalDateTime sessionTime = ReservationSession.getTempSelectedDateTime();
                if (sessionTime != null) {
                    try {
                        PhieuDatBanService phieuService = new PhieuDatBanService();
                        java.util.List<String> availableBans = phieuService
                                .getDanhSachBanTrongTheoThoiGian(sessionTime);
                        // Điều kiện chọn: Bàn phải nằm trong danh sách "Trong" của ngày đó HOẶC là bàn
                        // thuộc originalTablesForEdit
                        if (availableBans.contains(maBan) || originalTablesForEdit.contains(maBan)) {
                            selectedTables.add(maBan);
                            setTableCardBackground(maBan, card, trangThai);
                        } else {
                            JOptionPane.showMessageDialog(this,
                                    "Bàn này đã có khách đặt vào ngày đã chọn. Vui lòng chọn bàn khác.");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi kiểm tra bàn khả dụng: " + e.getMessage());
                    }
                } else {
                    // Nếu không có sessionTime, cho phép chọn bàn TRONG hoặc bàn thuộc
                    // originalTablesForEdit
                    if (trangThai == TrangThaiBan.TRONG || originalTablesForEdit.contains(maBan)) {
                        selectedTables.add(maBan);
                        setTableCardBackground(maBan, card, trangThai);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Chỉ có thể chọn bàn đang trống hoặc bàn đã được đặt trước đó.");
                    }
                }
            }
            card.revalidate();
            card.repaint();
            return;
        }

        if (billMode) {
            if (trangThai == TrangThaiBan.DANG_DUNG) {
                PhieuDatBanService phieuService = new PhieuDatBanService();
                PhieuDatBan activePhieu = phieuService.getActivePhieuDatByBan(maBan);
                if (activePhieu == null) {
                    JOptionPane.showMessageDialog(this,
                            "Không tìm thấy phiếu đặt/bàn đang sử dụng cho bàn " + maBan,
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String resolvedMaBan = HoaDonDraftSession.resolveMaBanContext(maBan);
                HoaDonDraftSession.setCurrentMaPhieuDatContext(activePhieu.getMaPhieuDat());
                HoaDonDraftSession.setCurrentMaBanContext(resolvedMaBan);
                java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
                if (parentFrame instanceof MainForm) {
                    ((MainForm) parentFrame).openPanelLapHoaDonFromBillMode(maBan, activePhieu.getMaPhieuDat());
                }
            }
            return;
        }
        if (reservationMode && availableBanForReservation.contains(maBan)) {
            toggleSelectionCard(maBan, card);
            return;
        }
        if (trangThai == TrangThaiBan.DA_DAT) {
            JOptionPane.showMessageDialog(this,
                    "Nếu muốn làm trống bàn vui lòng chỉnh sửa bên quản lý phiếu đặt bàn",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (trangThai == TrangThaiBan.DANG_DUNG) {
            showDangDungOptions(maBan);
            return;
        }
        toggleSelectionCard(maBan, card);
    }

    private void toggleSelectionCard(String maBan, JPanel card) {
        TrangThaiBan trangThai = tableTrangThai.get(maBan);
        if (trangThai == null) {
            return;
        }

        if (selectedTables.contains(maBan)) {
            selectedTables.remove(maBan);
            setTableCardBackground(maBan, card, trangThai);
        } else {
            selectedTables.add(maBan);
            card.setBackground(EDIT_MODE_NEW_COLOR);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 200, 0), 2, true),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        }
        card.revalidate();
        card.repaint();
    }

    private TrangThaiBan getDisplayStatusForTable(Ban ban, java.util.List<String> availableBans) {
        // 1. Logic bảo vệ bàn gốc (flow Đổi/Gộp)
        boolean isOriginal = (originalTablesForEdit != null && originalTablesForEdit.contains(ban.getMaBan()));
        if (isOriginal) {
            return ban.getTrangThai();
        }

        // 2. Logic "Giải oan" và hiển thị tường minh
        // Nếu trong DB là DA_DAT, nhưng nó nằm trong danh sách bàn trống
        // (availableBans) -> TRONG
        if (ban.getTrangThai() == TrangThaiBan.DA_DAT && availableBans.contains(ban.getMaBan())) {
            return TrangThaiBan.TRONG;
        }

        // 3. Mặc định trả về trạng thái DB
        return ban.getTrangThai();
    }

    private void setTableCardBackground(String maBan, JPanel card, TrangThaiBan trangThai) {
        // *** HIỂN THỊ KHÁC BIỆT KHI MERGE VS SWITCH ***
        if (isMerging) {
            if (selectedTables.contains(maBan)) {
                if (originalTablesForEdit.contains(maBan)) {
                    // Bàn gốc: Xanh dương, viền dày đặc
                    card.setBackground(EDIT_MODE_SELECTED_COLOR);
                    card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(0, 123, 255), 4, true),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                } else {
                    // Bàn mới chọn để gộp: Vàng nhạt, viền dày
                    card.setBackground(EDIT_MODE_NEW_COLOR);
                    card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(200, 180, 0), 3, true),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                }
            } else {
                // Bàn khác: Xám nhạt, không thể chọn
                card.setBackground(new Color(240, 240, 240));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            }
            return;
        }

        if (isSwitching) {
            if (selectedTables.contains(maBan)) {
                // nếu được chọn thì hiển thị như chế độ edit
                if (originalTablesForEdit.contains(maBan)) {
                    // Bàn gốc: Xanh nhạt, có thể bỏ chọn
                    card.setBackground(new Color(173, 216, 230));
                    card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(0, 150, 255), 2, true),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                } else {
                    card.setBackground(EDIT_MODE_NEW_COLOR);
                    card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(200, 180, 0), 2, true),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                }
            } else {
                // Bàn không được chọn -> làm mờ để phân biệt
                card.setBackground(new Color(245, 245, 245));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(210, 210, 210), 1, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            }
            return;
        }
        // Ưu tiên thứ 1: editMode == true
        if (editMode) {
            // Nếu selectedTables có maBan
            if (selectedTables.contains(maBan)) {
                // Nằm trong originalTablesForEdit -> Màu Xanh dương (Giữ lại)
                if (originalTablesForEdit.contains(maBan)) {
                    card.setBackground(EDIT_MODE_SELECTED_COLOR);
                    card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(0, 123, 255), 3, true),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                } else {
                    // KHÔNG nằm trong originalTablesForEdit -> Màu Vàng nhạt (Chọn thêm)
                    card.setBackground(EDIT_MODE_NEW_COLOR);
                    card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(200, 180, 0), 2, true),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                }
            } else {
                // selectedTables KHÔNG có maBan
                // Nằm trong originalTablesForEdit -> Màu Trắng (Báo hiệu sẽ giải phóng/đổi đi)
                if (originalTablesForEdit.contains(maBan)) {
                    card.setBackground(EDIT_MODE_VACATING_COLOR);
                    card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                } else {
                    // KHÔNG nằm trong originalTablesForEdit -> Màu theo trạng thái DB
                    applyDefaultStatusColor(card, trangThai);
                }
            }
            return;
        }

        // Ưu tiên thứ 2: reservationMode == true
        if (reservationMode) {
            if (selectedTables.contains(maBan)) {
                // Bàn trong selectedTables -> Màu Vàng nhạt
                card.setBackground(EDIT_MODE_NEW_COLOR);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(255, 200, 0), 2, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            } else if (availableBanForReservation.contains(maBan)) {
                // Bàn khả dụng -> Màu Trắng
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            } else {
                // Bàn khác -> Màu theo trạng thái DB
                applyDefaultStatusColor(card, trangThai);
            }
            return;
        }

        // Mặc định: Hiển thị theo TrangThaiBan thực tế
        applyDefaultStatusColor(card, trangThai);
    }

    private void applyDefaultStatusColor(JPanel card, TrangThaiBan trangThai) {
        if (trangThai == TrangThaiBan.TRONG) {
            card.setBackground(Color.WHITE);
        } else if (trangThai == TrangThaiBan.DANG_DUNG) {
            card.setBackground(new Color(0, 200, 100));
        } else if (trangThai == TrangThaiBan.DA_DAT) {
            card.setBackground(new Color(255, 200, 0));
        }
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }

    private void showDangDungOptions(String maBan) {
        Object[] options = { "Làm trống", "Chọn món", "Hủy" };
        int choice = JOptionPane.showOptionDialog(
                this,
                "Bàn " + maBan + " đang ở trạng thái Đang dùng.",
                "Chọn thao tác",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (choice == 0) {
            BanService banService = new BanService();
            try {
                banService.giaiphongBan(maBan);
                HoaDonDraftSession.clear(maBan);
                updateBanStatusUI(maBan, TrangThaiBan.TRONG);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Không thể làm trống bàn: " + ex.getMessage());
            }
            return;
        }
        if (choice == 1) {
            java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
            if (parentFrame instanceof MainForm) {
                HoaDonDraftSession.clearCurrentPhoneNumber();
                HoaDonDraftSession.clearCurrentMaPhieuDatContext();
                ((MainForm) parentFrame).openPanelDatMon(maBan);
            }
        }
    }

    public void updateBanStatusUI(String maBan, TrangThaiBan newTrangThai) {
        JPanel card = tableCards.get(maBan);
        if (card == null) {
            throw new IllegalStateException("Không tìm thấy card cho bàn: " + maBan);
        }
        tableTrangThai.put(maBan, newTrangThai);
        if (newTrangThai == TrangThaiBan.TRONG) {
            card.setBackground(Color.WHITE);
        } else if (newTrangThai == TrangThaiBan.DANG_DUNG) {
            card.setBackground(new Color(0, 200, 100));
        } else if (newTrangThai == TrangThaiBan.DA_DAT) {
            card.setBackground(new Color(255, 200, 0));
        }
        JLabel lblStatus = tableLabelStatus.get(maBan);
        if (lblStatus != null) {
            lblStatus.setText(newTrangThai.getDisplayName());
        }
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        card.revalidate();
        card.repaint();
    }

    private void resetAllTablesUI() {
        selectedTables.clear();
        originalTablesForEdit.clear();
        for (String maBan : tableCards.keySet()) {
            TrangThaiBan trangThai = tableTrangThai.get(maBan);
            if (trangThai != null) {
                updateBanStatusUI(maBan, trangThai);
            }
        }
    }

    private void repaintAllUI() {
        panelSoDoBan.revalidate();
        panelSoDoBan.repaint();
        this.revalidate();
        this.repaint();
    }

    private void resetReservationMode() {
        reservationMode = false;
        availableBanForReservation.clear();
        if (btnDatBanTruoc != null) {
            btnDatBanTruoc.setText("Đặt bàn trước");
        }
    }

    private void enterReservationMode(LocalDateTime thoiGianDen, java.util.List<String> availableBans) {
        reservationMode = true;
        availableBanForReservation.clear();
        ReservationSession.setTempSelectedDateTime(thoiGianDen);
        availableBanForReservation.addAll(availableBans);
        selectedTables.clear();
        if (btnDatBanTruoc != null) {
            btnDatBanTruoc.setText("Hoàn tất đặt bàn");
        }
    }

    public void forceRepaintUI() {
        repaintAllUI();
    }

    public void updateAllTableStatusFromDatabase() {
        try {
            // Cần lấy lại targetTime để tính availableBans
            LocalDateTime targetTime = (ReservationSession.getTempSelectedDateTime() != null)
                    ? ReservationSession.getTempSelectedDateTime()
                    : LocalDateTime.now();
            PhieuDatBanService phieuService = new PhieuDatBanService();
            java.util.List<String> availableBans = phieuService.getDanhSachBanTrongTheoThoiGian(targetTime);

            java.util.List<Ban> allBan = banService.getAllBan();
            for (Ban ban : allBan) {
                String maBan = ban.getMaBan();
                JPanel card = tableCards.get(maBan);
                if (card == null)
                    continue;

                // Dùng hàm helper vừa tạo
                TrangThaiBan displayStatus = getDisplayStatusForTable(ban, availableBans);

                // Cập nhật UI
                setTableCardBackground(maBan, card, displayStatus);
                JLabel lblStatus = tableLabelStatus.get(maBan);
                if (lblStatus != null) {
                    lblStatus.setText(displayStatus.getDisplayName());
                }
                tableTrangThai.put(maBan, displayStatus);
            }
            panelSoDoBan.revalidate();
            panelSoDoBan.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onButtonDatBanDungNgayClicked() {
        if (selectedTables.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một bàn!");
            return;
        }
        String selectedBanList = String.join(", ", selectedTables);
        String message = "Có xác nhận đặt bàn dùng ngay: " + selectedBanList + "?";
        int result = JOptionPane.showConfirmDialog(
                this,
                message,
                "Xác nhận đặt bàn dùng ngay",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            // Logic đặt bàn dùng ngay - tạo phiếu đặt và chuyển trực tiếp đến đặt món
            try {
                if (selectedTables.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một bàn để đặt ngay!");
                    return;
                }
                if (SessionManager.getCurrentNhanVien() == null) {
                    JOptionPane.showMessageDialog(this,
                            "Không tìm thấy tài khoản nhân viên. Vui lòng đăng nhập lại trước khi đặt bàn.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String maNV = SessionManager.getCurrentNhanVien().getMaNV();
                PhieuDatBanService phieuDatBanService = new PhieuDatBanService();
                Set<String> selectedBanSet = new HashSet<>(selectedTables);
                String maPhieuDat = phieuDatBanService.taoPhieuDatDungNgay(selectedBanSet, maNV);
                if (maPhieuDat == null || maPhieuDat.isEmpty()) {
                    throw new IllegalStateException("Không thể tạo phiếu đặt dùng ngay.");
                }
                HoaDonDraftSession.setCurrentMaPhieuDatContext(maPhieuDat);
                String maBanContext = String.join(",", selectedTables);
                HoaDonDraftSession.setCurrentMaBanContext(maBanContext);
                HoaDonDraftSession.luuGioVao(maBanContext, LocalDateTime.now());
                this.updateAllTableStatusFromDatabase();
                resetAllTablesUI();
                repaintAllUI();
                java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
                if (parentFrame instanceof MainForm) {
                    ((MainForm) parentFrame).openPanelDatMon(maBanContext);
                }
            } catch (Exception ex) {
                String errorMessage = ex.getMessage() != null ? ex.getMessage()
                        : "Lỗi không xác định khi đặt bàn dùng ngay.";
                JOptionPane.showMessageDialog(this, "Lỗi đặt bàn dùng ngay: " + errorMessage,
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onButtonDatBanTruocClicked() {
        if (!reservationMode) {
            DateTimePicker dateTimePicker = new DateTimePicker();
            dateTimePicker.setDateTimeStrict(LocalDateTime.now());
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.add(new JLabel("Chọn thời gian đặt trước:"), BorderLayout.NORTH);
            panel.add(dateTimePicker, BorderLayout.CENTER);
            int option = JOptionPane.showConfirmDialog(this, panel,
                    "Chọn thời gian đặt trước", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option != JOptionPane.OK_OPTION) {
                return;
            }
            LocalDateTime selectedDateTime = dateTimePicker.getDateTimeStrict();
         // Lấy thời gian hiện tại và thời gian chọn, cắt bỏ phần giây/mili-giây để so sánh công bằng
            LocalDateTime now = LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES);
            LocalDateTime selected = selectedDateTime.truncatedTo(java.time.temporal.ChronoUnit.MINUTES);
            if (selectedDateTime == null || selected.isBefore(now)) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn thời gian hợp lệ trong tương lai.");
                return;
            }
            try {
                PhieuDatBanService phieuService = new PhieuDatBanService();
                java.util.List<String> availableBans = phieuService.getDanhSachBanTrongTheoThoiGian(selectedDateTime);
                if (availableBans.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Hiện không có bàn nào trống vào thời gian đã chọn. Vui lòng chọn thời gian khác.");
                    return;
                }
                enterReservationMode(selectedDateTime, availableBans);
                loadSoDoBanFromDatabase();
                JOptionPane.showMessageDialog(this,
                        "Đã lọc các bàn có thể chọn!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi kiểm tra bàn trống: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        if (selectedTables.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một bàn để tiếp tục đặt trước!");
            return;
        }
        String selectedBanList = String.join(", ", selectedTables);
        String message = "Xác nhận đặt bàn trước cho: " + selectedBanList + "?";
        int result = JOptionPane.showConfirmDialog(
                this,
                message,
                "Xác nhận đặt bàn trước",
                JOptionPane.YES_NO_OPTION);
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        DatBanTruocDialog dialog = new DatBanTruocDialog(parentFrame, true, selectedTables, this,
                panelQuanLyDatBanTruoc);
        dialog.setVisible(true);
        resetReservationMode();
        this.updateAllTableStatusFromDatabase();
        resetAllTablesUI();
        repaintAllUI();
    }

    private void onButtonDoiBanClicked() {
        if (selectedTables.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một bàn!");
            return;
        }
        // Enter switching mode visually
        isSwitching = true;
        isMerging = false;
        editMode = true;
        repaintAllUI();
        String selectedBanList = String.join(", ", selectedTables);
        String message = "Có xác nhận đổi bàn: " + selectedBanList + "?";
        int result = JOptionPane.showConfirmDialog(
                this,
                message,
                "Xác nhận đổi bàn",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                // *** DISPATCH LOGIC: Gộp hoặc Đổi ***
                if (flowOrigin.equals("GOP_BAN")) {
                    executeMergeTableFlow();
                } else if (flowOrigin.equals("DAT_MON") || flowOrigin.isEmpty() ||
                        flowOrigin.equals("QUAN_LY_DAT_TRUOC")) {
                    executeSwitchTableFlow();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xử lý đổi/gộp bàn: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
            this.editMode = false;
            isSwitching = false;
            isMerging = false;
        }
    }

    /**
     * Thực hiện logic GỘP BÀN: Chỉ THÊM bàn mới, KHÔNG xóa bàn cũ
     */
    private void executeMergeTableFlow() {
        if (panelDatMon == null && panelQuanLyDatBanTruoc == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy panel callback cho gộp bàn.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String currentMaPhieuDat = HoaDonDraftSession.getCurrentMaPhieuDatContext();
        if (currentMaPhieuDat == null || currentMaPhieuDat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu đặt hiện tại.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Xác định bàn mới: Bàn có trong selectedTables nhưng KHÔNG nằm trong
            // originalTablesForEdit
            Set<String> newTables = new HashSet<>(selectedTables);
            newTables.removeAll(originalTablesForEdit);

            if (newTables.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn mới để gộp.",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // CHỈ THÊM bàn mới vào phiếu (KHÔNG XÓA)
            ChiTietPhieuDatBanService ctpService = new ChiTietPhieuDatBanService();
            BanService banService = new BanService();
            PhieuDatBanService pService = new PhieuDatBanService();

            for (String newBan : newTables) {
                Ban ban = banService.getBanTheoMa(newBan);
                PhieuDatBan phieu = pService.getPhieuDatBanTheoMa(currentMaPhieuDat);

                if (ban != null && phieu != null) {
                    ChiTietPhieuDatBan newChiTiet = new ChiTietPhieuDatBan();
                    newChiTiet.setPhieuDatBan(phieu);
                    newChiTiet.setBan(ban);
                    ctpService.themChiTietPhieuDatBan(newChiTiet);

                    // Cập nhật trạng thái bàn mới thành DANG_DUNG (phục vụ)
                    banService.capNhatTrangThaiBan(newBan, TrangThaiBan.DANG_DUNG);
                }
            }

            // Cập nhật context
            String newContext = HoaDonDraftSession
                    .resolveMaBanContext(String.join(",", selectedTables));
            HoaDonDraftSession.setCurrentMaBanContext(newContext);

            // Cập nhật UI và quay lại
            if (panelDatMon != null) {
                panelDatMon.updateMaBanContextForEdit(new HashSet<>(selectedTables));
            } else if (panelQuanLyDatBanTruoc != null) {
                panelQuanLyDatBanTruoc.updateMaBanForEdit(new HashSet<>(selectedTables));
            }

            this.updateAllTableStatusFromDatabase();
            resetAllTablesUI();
            repaintAllUI();

            java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
            if (parentFrame instanceof MainForm) {
                if (panelDatMon != null) {
                    ((MainForm) parentFrame).goBackToPanelDatMon();
                } else {
                    ((MainForm) parentFrame).switchToQuanLyDatBanTruocTab();
                }
            }

            JOptionPane.showMessageDialog(this, "Gộp bàn thành công!",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi gộp bàn: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            this.editMode = false;
            this.isMerging = false;
        }
    }

    /**
     * Thực hiện logic ĐỔI BÀN: XÓA bàn cũ + THÊM bàn mới
     */
    private void executeSwitchTableFlow() {
        if (panelDatMon == null && panelQuanLyDatBanTruoc == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy panel callback.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Flow DAT_MON: Chỉ update context (KHÔNG xóa/thêm vào DB)
            if (flowOrigin.equals("DAT_MON") || flowOrigin.isEmpty()) {
                if (panelDatMon != null) {
                    panelDatMon.updateMaBanContextForEdit(new HashSet<>(selectedTables));
                    this.updateAllTableStatusFromDatabase();
                    java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities
                            .getWindowAncestor(this);
                    if (parentFrame instanceof MainForm) {
                        ((MainForm) parentFrame).goBackToPanelDatMon();
                    }
                    this.editMode = false;
                    this.panelDatMon = null;
                    isSwitching = false;
                    return;
                }
            }

            // Flow QUAN_LY_DAT_TRUOC: Xóa bàn cũ, thêm bàn mới
            if (flowOrigin.equals("QUAN_LY_DAT_TRUOC")) {
                if (panelQuanLyDatBanTruoc != null) {
                    String currentMaPhieuDat = HoaDonDraftSession.getCurrentMaPhieuDatContext();
                    if (currentMaPhieuDat != null && !currentMaPhieuDat.isEmpty()) {
                        ChiTietPhieuDatBanService ctpService = new ChiTietPhieuDatBanService();
                        BanService banService = new BanService();
                        PhieuDatBanService pService = new PhieuDatBanService();

                        // Xác định bàn cũ: Bàn nằm trong originalTablesForEdit nhưng KHÔNG trong
                        // selectedTables
                        Set<String> oldTables = new HashSet<>(originalTablesForEdit);
                        oldTables.removeAll(selectedTables);

                        // Xác định bàn mới: Bàn nằm trong selectedTables nhưng KHÔNG trong
                        // originalTablesForEdit
                        Set<String> newTables = new HashSet<>(selectedTables);
                        newTables.removeAll(originalTablesForEdit);

                        // XÓA bàn cũ
                        for (String oldBan : oldTables) {
                            ctpService.xoaChiTietPhieuDatBan(currentMaPhieuDat, oldBan);
                            banService.capNhatTrangThaiBan(oldBan, TrangThaiBan.TRONG);
                        }

                        // THÊM bàn mới
                        for (String newBan : newTables) {
                            Ban ban = banService.getBanTheoMa(newBan);
                            PhieuDatBan phieu = pService.getPhieuDatBanTheoMa(currentMaPhieuDat);

                            if (ban != null && phieu != null) {
                                ChiTietPhieuDatBan newChiTiet = new ChiTietPhieuDatBan();
                                newChiTiet.setPhieuDatBan(phieu);
                                newChiTiet.setBan(ban);
                                ctpService.themChiTietPhieuDatBan(newChiTiet);

                                // Cập nhật trạng thái bàn mới theo trạng thái phiếu
                                if (phieu.getTrangThai() == TrangThaiPhieuDat.DA_SU_DUNG) {
                                    banService.capNhatTrangThaiBan(newBan, TrangThaiBan.DANG_DUNG);
                                } else if (phieu.getTrangThai() == TrangThaiPhieuDat.DANG_CHO) {
                                    banService.capNhatTrangThaiBan(newBan, TrangThaiBan.DA_DAT);
                                }
                            }
                        }
                    }

                    panelQuanLyDatBanTruoc.updateMaBanForEdit(new HashSet<>(selectedTables));
                    this.updateAllTableStatusFromDatabase();
                    resetAllTablesUI();
                    repaintAllUI();
                    java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities
                            .getWindowAncestor(this);
                    if (parentFrame instanceof MainForm) {
                        ((MainForm) parentFrame).switchToQuanLyDatBanTruocTab();
                    }
                    this.editMode = false;
                    isSwitching = false;
                    return;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi đổi bàn: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            this.editMode = false;
            isSwitching = false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
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
        pnlStatusBanDuocChonTuPhieu = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
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
        cbFilterKhuVuc.setModel(new javax.swing.DefaultComboBoxModel<>());
        cbFilterKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterKhuVucActionPerformed(evt);
            }
        });
        cbFilterTrangThai.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] {}));
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
        pnlStatusServing.setBackground(new java.awt.Color(0, 153, 0));
        javax.swing.GroupLayout pnlStatusServingLayout = new javax.swing.GroupLayout(pnlStatusServing);
        pnlStatusServing.setLayout(pnlStatusServingLayout);
        pnlStatusServingLayout.setHorizontalGroup(
                pnlStatusServingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE));
        pnlStatusServingLayout.setVerticalGroup(
                pnlStatusServingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE));
        pnlStatusReserved.setBackground(new java.awt.Color(255, 204, 0));
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
        pnlStatusBanDuocChonTuPhieu.setBackground(new java.awt.Color(51, 153, 255));
        javax.swing.GroupLayout pnlStatusBanDuocChonTuPhieuLayout = new javax.swing.GroupLayout(
                pnlStatusBanDuocChonTuPhieu);
        pnlStatusBanDuocChonTuPhieu.setLayout(pnlStatusBanDuocChonTuPhieuLayout);
        pnlStatusBanDuocChonTuPhieuLayout.setHorizontalGroup(
                pnlStatusBanDuocChonTuPhieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 22, Short.MAX_VALUE));
        pnlStatusBanDuocChonTuPhieuLayout.setVerticalGroup(
                pnlStatusBanDuocChonTuPhieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE));
        jLabel6.setText("Bàn trong phiếu");
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
                                .addComponent(pnlStatusBanDuocChonTuPhieu, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)));
        panelTimKiemLayout.setVerticalGroup(
                panelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelTimKiemLayout.createSequentialGroup()
                                .addContainerGap(38, Short.MAX_VALUE)
                                .addGroup(panelTimKiemLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelTimKiemLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 37,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 37,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTimKiemLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(cbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel5))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTimKiemLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(cbFilterKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel4)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelTimKiemLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(panelTimKiemLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(pnlStatusTrong, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(pnlStatusServing, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(pnlStatusReserved, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(pnlStatusBanDuocChonTuPhieu,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel3))
                                .addGap(11, 11, 11)));
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
        if (selected != null && selected.equals("Trạng thái")) {
            selectedTrangThai = null;
        } else {
            selectedTrangThai = selected;
        }
        loadSoDoBanFromDatabase();
    }// GEN-LAST:event_cbFilterTrangThaiActionPerformed

    private void setupPlaceholder(JTextField textField, String placeholder) {
        Color placeholderColor = new Color(153, 153, 153);
        Color textColor = new Color(0, 0, 0);
        textField.setText(placeholder);
        textField.setForeground(placeholderColor);
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(textColor);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(placeholderColor);
                }
            }
        });
    }

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTimKiemActionPerformed
    }// GEN-LAST:event_txtTimKiemActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtSearchActionPerformed
    }// GEN-LAST:event_txtSearchActionPerformed

    private void cbFilterKhuVucActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterKhuVucActionPerformed
        String selected = (String) cbFilterKhuVuc.getSelectedItem();
        if (selected != null && selected.equals("Khu vực")) {
            selectedKhuVuc = null;
        } else {
            selectedKhuVuc = selected;
        }
        loadSoDoBanFromDatabase();
    }// GEN-LAST:event_cbFilterKhuVucActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSearchActionPerformed
        String searchText = txtTimKiem.getText().trim();
        if (searchText.isEmpty() || searchText.equals("Nhập mã bàn")) {
            selectedKhuVuc = null;
            selectedTrangThai = null;
            cbFilterKhuVuc.setSelectedItem("-- Tất cả --");
            cbFilterTrangThai.setSelectedItem("-- Tất cả --");
            loadSoDoBanFromDatabase();
            return;
        }
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
     // Variables declaration - do not modify//GEN-BEGIN:variables

    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbFilterKhuVuc;
    private javax.swing.JComboBox<String> cbFilterTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelSoDoBan;
    private javax.swing.JPanel panelTimKiem;
    private javax.swing.JPanel pnlStatusBanDuocChonTuPhieu;
    private javax.swing.JPanel pnlStatusReserved;
    private javax.swing.JPanel pnlStatusServing;
    private javax.swing.JPanel pnlStatusTrong;
    private javax.swing.JScrollPane scrSoDoBan;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}