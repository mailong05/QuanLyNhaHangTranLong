package com.restaurant.quanlydatbannhahang.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;
import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;
import com.restaurant.quanlydatbannhahang.session.SessionManager;

public class MainForm extends javax.swing.JFrame {

    // 1. BIẾN ĐIỀU KHIỂN LOGIC & TRẠNG THÁI
    private String userRole;
    private TaiKhoan taiKhoan = null;
    private JPanel activePanel = null;
    private JLabel activeSubLabel = null;
    private PanelTrangChu currentTrangChuPanel = null;

    // Lưu trữ các panel để bảo toàn trạng thái khi chuyển đổi
    private PanelQuanLyDatBanTruoc panelQuanLyDatBanTruoc = null;
    private PanelDatMon panelDatMon = null;
    private PanelLapHoaDon panelLapHoaDon = null;
    private JPanel lastVisitedPanel = null; // Theo dõi panel trước đó để quay lại
    private JLabel lastVisitedSubLabel = null; // Lưu menu con để highlight lại

    // 2. ĐỊNH NGHĨA MÀU SẮC & GIAO DIỆN CHUẨN
    private final Color COLOR_MENU_BG = new Color(142, 128, 106);
    private final Color COLOR_MENU_HOVER = new Color(165, 150, 125);
    private final Color COLOR_TEXT_NORMAL = new Color(255, 255, 255);

    // 3. CÁC HÀM KHỞI TẠO (CONSTRUCTORS)
    public MainForm() {
        this("Quản lý");
    }

    public MainForm(String role) {
        this.userRole = (role != null) ? role : "Quản lý";
        this.taiKhoan = null;
        try {
            initComponents();
            initCustomComponents();
        } catch (Exception e) {
            System.out.println("❌ MainForm: Lỗi " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Lỗi khởi tạo MainForm", e);
        }
        initAll();
    }

    public MainForm(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
        this.userRole = (taiKhoan != null && taiKhoan.getNhanVien() != null
                && taiKhoan.getNhanVien().getChucVu() != null)
                        ? taiKhoan.getNhanVien().getChucVu().getDisplayName()
                        : "Quản lý";

        // Lưu thông tin tài khoản vào SessionManager để các module khác truy cập
        SessionManager.setCurrentTaiKhoan(taiKhoan);

        initAll();
    }

    // 4. CÁC HÀM KHỞI TẠO HỆ THỐNG (INIT)
    private void initAll() {
        try {
            initComponents();
            initCustomComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCustomComponents() {
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.9);
        int height = (int) (screenSize.height * 0.9);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setTitle("Hệ thống quản lý đặt bàn nhà hàng");
        panelBody.setLayout(new BorderLayout());

        batSuKienMenu();
        phanQuyenGiaoDien();

        JPanel[] allPanels = {
                panelTrangChu, panelBan, panelKhuVuc, panelNhanVien,
                panelKhachHang, panelHoaDon, panelThue, PanelMonAn,
                PanelKhuyenMai, panelDangXuat
        };

        for (JPanel p : allPanels) {
            if (p != null)
                paintRoundedPanel(p, COLOR_MENU_BG, false);
        }

        updateActivePanel(panelTrangChu);
        lblTenTrang.setText("TRANG CHỦ");
        showTrangChuPanel();
    }

    // 5. LOGIC HỆ THỐNG
    private void phanQuyenGiaoDien() {
        String tenNhanVien = "Nhân viên";
        String chucVu = userRole;
        if (taiKhoan != null && taiKhoan.getNhanVien() != null) {
            tenNhanVien = taiKhoan.getNhanVien().getHoTen();
            chucVu = taiKhoan.getNhanVien().getChucVu().getDisplayName();
        }
        lblTenNhanVien.setText(tenNhanVien);
        lblPhanQuyen.setText(chucVu);
    }

    private void updateActivePanel(JPanel newActive) {
        if (activePanel != null) {
            paintRoundedPanel(activePanel, COLOR_MENU_BG, false);
        }
        activePanel = newActive;
        paintRoundedPanel(activePanel, COLOR_MENU_HOVER, true);
    }

    private void showPanel(JPanel panel) {
        if (panel != null) {
            panelBody.removeAll();
            panelBody.add(panel, BorderLayout.CENTER);
            panelBody.revalidate();
            panelBody.repaint();
        }
    }

    public void showTrangChuPanel() {
        PanelTrangChu panel = new PanelTrangChu();
        currentTrangChuPanel = panel;
        showPanel(panel);
        try {
            panel.refreshData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToTrangChuFromPanel() {
        updateActivePanel(panelTrangChu);
        lblTenTrang.setText("TRANG CHỦ");
        showTrangChuPanel();
        if (activeSubLabel != null) {
            activeSubLabel.setForeground(Color.WHITE);
            activeSubLabel.setFont(activeSubLabel.getFont().deriveFont(Font.PLAIN));
            activeSubLabel = null;
        }
    }

    public void openPanelDatMon() {
        // Nếu panelDatMon chưa được tạo, tạo mới
        if (panelDatMon == null) {
            panelDatMon = new PanelDatMon();
        }
        // Lưu panel hiện tại trước khi chuyển đi, để có thể quay lại
        lastVisitedPanel = panelQuanLyDatBanTruoc;
        // Lưu menu con để highlight lại khi quay lại
        lastVisitedSubLabel = activeSubLabel;
        showPanel(panelDatMon);
        lblTenTrang.setText("ĐẶT MÓN");
    }

    public void openPanelLapHoaDon() {
        // Nếu panelLapHoaDon chưa được tạo, tạo mới
        if (panelLapHoaDon == null) {
            panelLapHoaDon = new PanelLapHoaDon();
        }
        // Lưu panel hiện tại trước khi chuyển đi
        // (panelDatMon sẽ được giữ lại để quay lại)
        showPanel(panelLapHoaDon);
        lblTenTrang.setText("LẬP HÓA ĐƠN");
    }

    public void goBackToPanelDatMon() {
        if (panelDatMon != null) {
            showPanel(panelDatMon);
            lblTenTrang.setText("ĐẶT MÓN");
        }
    }

    public void goBackToPreviousPanel() {
        if (lastVisitedPanel != null) {
            showPanel(lastVisitedPanel);

            // Highlight submenu nếu có
            if (lastVisitedSubLabel != null) {
                if (activeSubLabel != null) {
                    activeSubLabel.setForeground(Color.WHITE);
                    activeSubLabel.setFont(activeSubLabel.getFont().deriveFont(Font.PLAIN));
                }
                activeSubLabel = lastVisitedSubLabel;
                lastVisitedSubLabel.setForeground(new Color(255, 200, 0));
                lastVisitedSubLabel.setFont(lastVisitedSubLabel.getFont().deriveFont(Font.BOLD));
            }

            // Set title dựa trên panel
            if (lastVisitedPanel == panelTrangChu) {
                lblTenTrang.setText("TRANG CHỦ");
            } else if (lastVisitedPanel == panelBan) {
                lblTenTrang.setText("QUẢN LÝ BÀN");
            } else if (lastVisitedPanel == panelKhuVuc) {
                lblTenTrang.setText("KHU VỰC");
            } else if (lastVisitedPanel == panelNhanVien) {
                lblTenTrang.setText("NHÂN VIÊN");
            } else if (lastVisitedPanel == panelKhachHang) {
                lblTenTrang.setText("KHÁCH HÀNG");
            } else if (lastVisitedPanel == panelHoaDon) {
                lblTenTrang.setText("HÓA ĐƠN");
            } else if (lastVisitedPanel == panelThue) {
                lblTenTrang.setText("THUẾ");
            } else if (lastVisitedPanel == PanelMonAn) {
                lblTenTrang.setText("MÓN ĂN");
            } else if (lastVisitedPanel == PanelKhuyenMai) {
                lblTenTrang.setText("KHUYẾN MÃI");
            } else if (lastVisitedPanel == panelQuanLyDatBanTruoc) {
                lblTenTrang.setText("QUẢN LÝ BÀN ĐẶT TRƯỚC");
            }
        } else {
            // Nếu không có panel trước đó, về trang chủ
            goToTrangChuFromPanel();
        }
    }

    private void paintRoundedPanel(JPanel panel, Color color, boolean forceDown) {
        panel.setOpaque(false);
        panel.setBorder(new AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(color);
                g2.fillRoundRect(0, 0, width, height, 15, 15);

                if (panel != panelDangXuat && panel != panelTrangChu) {
                    int size = 6;
                    int gap = 15;
                    g2.setColor(Color.WHITE);

                    if (panel == activePanel || forceDown) {
                        int[] px = { gap, gap + size * 2, gap + size };
                        int[] py = { height / 2 - size / 2, height / 2 - size / 2, height / 2 + size };
                        g2.fillPolygon(px, py, 3);
                    } else {
                        int[] px = { gap, gap, gap + size };
                        int[] py = { height / 2 - size, height / 2 + size, height / 2 };
                        g2.fillPolygon(px, py, 3);
                    }
                }
                g2.dispose();
            }
        });
        panel.repaint();
    }

    // 6. XỬ LÝ SỰ KIỆN MENU
    private void batSuKienMenu() {
        JPanel[] pagePanels = {
                panelTrangChu, panelBan, panelKhuVuc, panelNhanVien,
                panelKhachHang, panelHoaDon, panelThue, PanelMonAn, PanelKhuyenMai
        };

        JPanel[] groupSubPanels = {
                null, groupSubBan, groupSubKhuVuc, groupSubNhanVien,
                groupSubKhachHang, groupSubHoaDon, groupSubThue, groupSubMonAn, groupSubKhuyenMai
        };

        for (JPanel sub : groupSubPanels) {
            if (sub != null) {
                sub.setVisible(false);
                for (java.awt.Component c : sub.getComponents()) {
                    if (c instanceof JLabel) {
                        batSuKienSubMenu((JLabel) c, pagePanels, groupSubPanels);
                    }
                }
            }
        }

        for (int i = 0; i < pagePanels.length; i++) {
            final JPanel pnl = pagePanels[i];
            final JPanel subPnl = groupSubPanels[i];
            if (pnl == null)
                continue;

            pnl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    if (subPnl == null) {
                        updateActivePanel(pnl);
                        lblTenTrang.setText("TRANG CHỦ");
                        showTrangChuPanel();
                        if (activeSubLabel != null) {
                            activeSubLabel.setForeground(Color.WHITE);
                            activeSubLabel.setFont(activeSubLabel.getFont().deriveFont(Font.PLAIN));
                            activeSubLabel = null;
                        }
                        for (JPanel s : groupSubPanels)
                            if (s != null)
                                s.setVisible(false);
                    } else {
                        boolean holdsActive = false;
                        if (activeSubLabel != null) {
                            for (java.awt.Component c : subPnl.getComponents()) {
                                if (c == activeSubLabel) {
                                    holdsActive = true;
                                    break;
                                }
                            }
                        }

                        if (!holdsActive) {
                            boolean isVisible = subPnl.isVisible();
                            for (JPanel s : groupSubPanels)
                                if (s != null && s != subPnl)
                                    s.setVisible(false);
                            subPnl.setVisible(!isVisible);

                            paintRoundedPanel(pnl, subPnl.isVisible() ? COLOR_MENU_HOVER : COLOR_MENU_BG,
                                    subPnl.isVisible());
                            panelMenu.revalidate();
                        }
                    }
                }

                @Override
                public void mouseEntered(MouseEvent evt) {
                    if (pnl != activePanel) {
                        paintRoundedPanel(pnl, COLOR_MENU_HOVER, (subPnl != null && subPnl.isVisible()));
                    }
                }

                @Override
                public void mouseExited(MouseEvent evt) {
                    if (pnl == activePanel) {
                        paintRoundedPanel(pnl, COLOR_MENU_HOVER, true);
                    } else {
                        paintRoundedPanel(pnl, COLOR_MENU_BG, (subPnl != null && subPnl.isVisible()));
                    }
                }
            });
        }

        panelDangXuat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Xác nhận đăng xuất?", "Xác nhận",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    // 1. Đóng cửa sổ chính hiện tại
                    dispose();
                    // 2. Khởi tạo và hiển thị lại màn hình Đăng nhập
                    java.awt.EventQueue.invokeLater(() -> {
                        new LoginForm().setVisible(true);
                    });
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                paintRoundedPanel(panelDangXuat, new Color(200, 80, 80), false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                paintRoundedPanel(panelDangXuat, COLOR_MENU_BG, false);
            }
        });
    }

    private void batSuKienSubMenu(JLabel lbl, JPanel[] pages, JPanel[] groups) {
        lbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                // 1. Cập nhật UI cho Label được chọn
                if (activeSubLabel != null) {
                    activeSubLabel.setForeground(Color.WHITE);
                    activeSubLabel.setFont(activeSubLabel.getFont().deriveFont(Font.PLAIN));
                }
                activeSubLabel = lbl;
                lbl.setForeground(Color.YELLOW);
                lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));

                // 2. LOGIC TÌM TÊN MENU CHA ĐỂ GHÉP TIÊU ĐỀ
                String subText = lbl.getText().replace("+", "").trim();
                String mainMenuText = "";
                JPanel parentGroup = (JPanel) lbl.getParent();

                for (int i = 0; i < groups.length; i++) {
                    if (groups[i] == parentGroup) {
                        updateActivePanel(pages[i]); // Active menu cha

                        // Duyệt các component trong menu cha để lấy text của Label chính (Bàn, Nhân
                        // viên...)
                        for (java.awt.Component comp : pages[i].getComponents()) {
                            if (comp instanceof JLabel) {
                                mainMenuText = ((JLabel) comp).getText().trim();
                                break;
                            }
                        }
                        break;
                    }
                }
                // Thiết lập tiêu đề: CẬP NHẬT + BÀN -> CẬP NHẬT BÀN
                lblTenTrang.setText((subText + " " + mainMenuText).toUpperCase());

                // 3. LOGIC CHUYỂN PANEL (Giữ nguyên các instance của bạn)
                if (lbl == subLapPhieuDatBan) {
                    showPanel(new PanelDatBan());
                } else if (lbl == subThongKeDoanhThu) {
                    showPanel(new PanelThongKe());
                } else if (lbl == subQuanLyThue) {
                    showPanel(new PanelQuanLyThue());
                } else if (lbl == subQuanLyBan) {
                    showPanel(new PanelQuanLyBan());
                } else if (lbl == subQuanLyKhachHang) {
                    showPanel(new PanelQuanLyKhachHang());
                } else if (lbl == subQuanLyKhuyenMai) {
                    showPanel(new PanelQuanLyKhuyenMai());
                } else if (lbl == subQuanLyKhuVuc) {
                    showPanel(new PanelQuanLyKhuVuc());
                } else if (lbl == subQuanLyNhanVien) {
                    showPanel(new PanelQuanLyNhanVien());
                } else if (lbl == subQuanLyMonAn) {
                    showPanel(new PanelQuanLyMonAn());
                } else if (lbl == subDanhSachBan) {
                    showPanel(new PanelDanhSachBan());
                } else if (lbl == subQuanLyDatBanTruoc) {
                    // Lưu instance để reuse (không tạo mới)
                    if (panelQuanLyDatBanTruoc == null) {
                        panelQuanLyDatBanTruoc = new PanelQuanLyDatBanTruoc();
                    }
                    lastVisitedSubLabel = lbl;
                    showPanel(panelQuanLyDatBanTruoc);
                } else if (lbl == subDanhSachKhuVuc) {
                    showPanel(new PanelDanhSachKhuVuc());
                } else if (lbl == subDanhSachNhanVien) {
                    showPanel(new PanelDanhSachNhanVien());
                } else if (lbl == subDanhSachKhachHang) {
                    showPanel(new PanelDanhSachKhachHang());
                } else if (lbl == subDanhSachKhuyenMai) {
                    showPanel(new PanelDanhSachKhuyenMai());
                } else if (lbl == subDanhSachThue) {
                    showPanel(new PanelDanhSachThue());
                } else if (lbl == subDanhSachMonAn) {
                    showPanel(new PanelDanhSachMonAn());
                } else if (lbl == subLichSuHoaDon) {
                    showPanel(new PanelLichSuHoaDon());
                } else if (lbl == subQuanLyTaiKhoan) {
                    showPanel(new PanelQuanLyTaiKhoan());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (lbl != activeSubLabel)
                    lbl.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (lbl != activeSubLabel)
                    lbl.setForeground(Color.WHITE);
            }
        });
    }

    // từ đây trở xuống không sửa được
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMenu = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5),
                new java.awt.Dimension(0, 5));
        panelTrangChu = new javax.swing.JPanel();
        lblMenuTrangChu = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7),
                new java.awt.Dimension(0, 7));
        panelBan = new javax.swing.JPanel();
        lblBan = new javax.swing.JLabel();
        groupSubBan = new javax.swing.JPanel();
        subDanhSachBan = new javax.swing.JLabel();
        subQuanLyBan = new javax.swing.JLabel();
        subLapPhieuDatBan = new javax.swing.JLabel();
        subQuanLyDatBanTruoc = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7),
                new java.awt.Dimension(0, 7));
        panelKhuVuc = new javax.swing.JPanel();
        lblKhuVuc = new javax.swing.JLabel();
        groupSubKhuVuc = new javax.swing.JPanel();
        subDanhSachKhuVuc = new javax.swing.JLabel();
        subQuanLyKhuVuc = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7),
                new java.awt.Dimension(0, 7));
        panelNhanVien = new javax.swing.JPanel();
        lblNhanVien = new javax.swing.JLabel();
        groupSubNhanVien = new javax.swing.JPanel();
        subDanhSachNhanVien = new javax.swing.JLabel();
        subQuanLyNhanVien = new javax.swing.JLabel();
        subQuanLyTaiKhoan = new javax.swing.JLabel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7),
                new java.awt.Dimension(0, 7));
        panelKhachHang = new javax.swing.JPanel();
        lblKhachHang = new javax.swing.JLabel();
        groupSubKhachHang = new javax.swing.JPanel();
        subDanhSachKhachHang = new javax.swing.JLabel();
        subQuanLyKhachHang = new javax.swing.JLabel();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7),
                new java.awt.Dimension(0, 7));
        panelHoaDon = new javax.swing.JPanel();
        lblHoaDon = new javax.swing.JLabel();
        groupSubHoaDon = new javax.swing.JPanel();
        subLichSuHoaDon = new javax.swing.JLabel();
        subThongKeDoanhThu = new javax.swing.JLabel();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7),
                new java.awt.Dimension(0, 7));
        panelThue = new javax.swing.JPanel();
        lblThue = new javax.swing.JLabel();
        groupSubThue = new javax.swing.JPanel();
        subDanhSachThue = new javax.swing.JLabel();
        subQuanLyThue = new javax.swing.JLabel();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7),
                new java.awt.Dimension(0, 7));
        PanelMonAn = new javax.swing.JPanel();
        lblMonAn = new javax.swing.JLabel();
        groupSubMonAn = new javax.swing.JPanel();
        subDanhSachMonAn = new javax.swing.JLabel();
        subQuanLyMonAn = new javax.swing.JLabel();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7),
                new java.awt.Dimension(0, 7));
        PanelKhuyenMai = new javax.swing.JPanel();
        lblKhuyenMai = new javax.swing.JLabel();
        groupSubKhuyenMai = new javax.swing.JPanel();
        subDanhSachKhuyenMai = new javax.swing.JLabel();
        subQuanLyKhuyenMai = new javax.swing.JLabel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0),
                new java.awt.Dimension(32767, 32767));
        panelDangXuat = new javax.swing.JPanel();
        lblMenuDangXuat = new javax.swing.JLabel();
        panelMainContent = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        lblTenTrang = new javax.swing.JLabel();
        panelTaiKhoan = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        lblTenNhanVien = new javax.swing.JLabel();
        lblPhanQuyen = new javax.swing.JLabel();
        panelBody = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelMenu.setBackground(new java.awt.Color(142, 128, 106));
        panelMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 5, 10, 5));
        panelMenu.setMaximumSize(new java.awt.Dimension(200, 32767));
        panelMenu.setMinimumSize(new java.awt.Dimension(200, 600));
        panelMenu.setPreferredSize(new java.awt.Dimension(220, 600));
        panelMenu.setLayout(new javax.swing.BoxLayout(panelMenu, javax.swing.BoxLayout.Y_AXIS));

        jPanel4.setBackground(new java.awt.Color(142, 128, 106));
        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 8, 1, 1));
        jPanel4.setAlignmentX(0.0F);
        jPanel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jPanel4.setMaximumSize(new java.awt.Dimension(200, 70));
        jPanel4.setMinimumSize(new java.awt.Dimension(200, 70));
        jPanel4.setPreferredSize(new java.awt.Dimension(200, 60));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Trần Long");
        jLabel15.setMaximumSize(new java.awt.Dimension(150, 32));
        jPanel4.add(jLabel15);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Restaurant");
        jPanel4.add(jLabel16);

        panelMenu.add(jPanel4);
        panelMenu.add(filler7);

        panelTrangChu.setBackground(new java.awt.Color(142, 128, 106));
        panelTrangChu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panelTrangChu.setAlignmentX(0.0F);
        panelTrangChu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelTrangChu.setMaximumSize(new java.awt.Dimension(200, 40));
        panelTrangChu.setMinimumSize(new java.awt.Dimension(200, 40));
        panelTrangChu.setPreferredSize(new java.awt.Dimension(200, 40));
        panelTrangChu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblMenuTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMenuTrangChu.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuTrangChu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_trangchu.png"))); // NOI18N
        lblMenuTrangChu.setText("Trang chủ");
        lblMenuTrangChu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 1));
        lblMenuTrangChu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMenuTrangChuMouseClicked(evt);
            }
        });
        panelTrangChu.add(lblMenuTrangChu);

        panelMenu.add(panelTrangChu);
        panelMenu.add(filler2);

        panelBan.setBackground(new java.awt.Color(142, 128, 106));
        panelBan.setAlignmentX(0.0F);
        panelBan.setAlignmentY(0.0F);
        panelBan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelBan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        panelBan.setMaximumSize(new java.awt.Dimension(200, 40));
        panelBan.setMinimumSize(new java.awt.Dimension(200, 40));
        panelBan.setPreferredSize(new java.awt.Dimension(200, 40));
        panelBan.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblBan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblBan.setForeground(new java.awt.Color(255, 255, 255));
        lblBan.setText("Bàn");
        lblBan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 35, 1, 1));
        panelBan.add(lblBan);

        panelMenu.add(panelBan);

        groupSubBan.setBackground(new java.awt.Color(142, 128, 106));
        groupSubBan.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 40, 0, 1));
        groupSubBan.setAlignmentX(0.0F);
        groupSubBan.setAlignmentY(0.0F);
        groupSubBan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        groupSubBan.setPreferredSize(new java.awt.Dimension(190, 120));
        groupSubBan.setLayout(new javax.swing.BoxLayout(groupSubBan, javax.swing.BoxLayout.Y_AXIS));

        subDanhSachBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subDanhSachBan.setForeground(new java.awt.Color(255, 255, 255));
        subDanhSachBan.setText("+ Danh sách");
        subDanhSachBan.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubBan.add(subDanhSachBan);

        subQuanLyBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subQuanLyBan.setForeground(new java.awt.Color(255, 255, 255));
        subQuanLyBan.setText("+ Quản lý");
        subQuanLyBan.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubBan.add(subQuanLyBan);

        subLapPhieuDatBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subLapPhieuDatBan.setForeground(new java.awt.Color(255, 255, 255));
        subLapPhieuDatBan.setText("+ Lập phiếu đặt bàn");
        subLapPhieuDatBan.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubBan.add(subLapPhieuDatBan);

        subQuanLyDatBanTruoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subQuanLyDatBanTruoc.setForeground(new java.awt.Color(255, 255, 255));
        subQuanLyDatBanTruoc.setText("+ Quản lý bàn đặt trước");
        subQuanLyDatBanTruoc.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubBan.add(subQuanLyDatBanTruoc);

        panelMenu.add(groupSubBan);
        panelMenu.add(filler1);

        panelKhuVuc.setBackground(new java.awt.Color(142, 128, 106));
        panelKhuVuc.setAlignmentX(0.0F);
        panelKhuVuc.setAlignmentY(0.0F);
        panelKhuVuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        panelKhuVuc.setMaximumSize(new java.awt.Dimension(200, 40));
        panelKhuVuc.setMinimumSize(new java.awt.Dimension(200, 40));
        panelKhuVuc.setPreferredSize(new java.awt.Dimension(200, 40));
        panelKhuVuc.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblKhuVuc.setForeground(new java.awt.Color(255, 255, 255));
        lblKhuVuc.setText("Khu vực");
        lblKhuVuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 35, 1, 1));
        panelKhuVuc.add(lblKhuVuc);

        panelMenu.add(panelKhuVuc);

        groupSubKhuVuc.setBackground(new java.awt.Color(142, 128, 106));
        groupSubKhuVuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 40, 0, 1));
        groupSubKhuVuc.setAlignmentY(0.0F);
        groupSubKhuVuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        groupSubKhuVuc.setPreferredSize(new java.awt.Dimension(190, 60));
        groupSubKhuVuc.setLayout(new javax.swing.BoxLayout(groupSubKhuVuc, javax.swing.BoxLayout.Y_AXIS));

        subDanhSachKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subDanhSachKhuVuc.setForeground(new java.awt.Color(255, 255, 255));
        subDanhSachKhuVuc.setText("+ Danh sách");
        subDanhSachKhuVuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubKhuVuc.add(subDanhSachKhuVuc);

        subQuanLyKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subQuanLyKhuVuc.setForeground(new java.awt.Color(255, 255, 255));
        subQuanLyKhuVuc.setText("+ Quản lý");
        subQuanLyKhuVuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubKhuVuc.add(subQuanLyKhuVuc);

        panelMenu.add(groupSubKhuVuc);
        panelMenu.add(filler3);

        panelNhanVien.setBackground(new java.awt.Color(142, 128, 106));
        panelNhanVien.setAlignmentX(0.0F);
        panelNhanVien.setAlignmentY(0.0F);
        panelNhanVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelNhanVien.setMaximumSize(new java.awt.Dimension(200, 40));
        panelNhanVien.setMinimumSize(new java.awt.Dimension(200, 40));
        panelNhanVien.setPreferredSize(new java.awt.Dimension(200, 40));
        panelNhanVien.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        lblNhanVien.setText("Nhân viên");
        lblNhanVien.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 35, 1, 1));
        lblNhanVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelNhanVien.add(lblNhanVien);

        panelMenu.add(panelNhanVien);

        groupSubNhanVien.setBackground(new java.awt.Color(142, 128, 106));
        groupSubNhanVien.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 40, 0, 1));
        groupSubNhanVien.setAlignmentY(0.0F);
        groupSubNhanVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        groupSubNhanVien.setPreferredSize(new java.awt.Dimension(190, 90));
        groupSubNhanVien.setLayout(new javax.swing.BoxLayout(groupSubNhanVien, javax.swing.BoxLayout.Y_AXIS));

        subDanhSachNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subDanhSachNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        subDanhSachNhanVien.setText("+ Danh sách");
        subDanhSachNhanVien.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubNhanVien.add(subDanhSachNhanVien);

        subQuanLyNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subQuanLyNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        subQuanLyNhanVien.setText("+ Quản lý nhân viên");
        subQuanLyNhanVien.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubNhanVien.add(subQuanLyNhanVien);

        subQuanLyTaiKhoan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subQuanLyTaiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        subQuanLyTaiKhoan.setText("+ Quản lý tài khoản");
        subQuanLyTaiKhoan.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubNhanVien.add(subQuanLyTaiKhoan);

        panelMenu.add(groupSubNhanVien);
        panelMenu.add(filler4);

        panelKhachHang.setBackground(new java.awt.Color(142, 128, 106));
        panelKhachHang.setAlignmentX(0.0F);
        panelKhachHang.setAlignmentY(0.0F);
        panelKhachHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelKhachHang.setMaximumSize(new java.awt.Dimension(200, 40));
        panelKhachHang.setMinimumSize(new java.awt.Dimension(200, 40));
        panelKhachHang.setPreferredSize(new java.awt.Dimension(200, 40));
        panelKhachHang.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        lblKhachHang.setText("Khách hàng");
        lblKhachHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 35, 1, 1));
        panelKhachHang.add(lblKhachHang);

        panelMenu.add(panelKhachHang);

        groupSubKhachHang.setBackground(new java.awt.Color(142, 128, 106));
        groupSubKhachHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 40, 0, 1));
        groupSubKhachHang.setAlignmentY(0.0F);
        groupSubKhachHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        groupSubKhachHang.setPreferredSize(new java.awt.Dimension(190, 60));
        groupSubKhachHang.setLayout(new javax.swing.BoxLayout(groupSubKhachHang, javax.swing.BoxLayout.Y_AXIS));

        subDanhSachKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subDanhSachKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        subDanhSachKhachHang.setText("+ Danh sách");
        subDanhSachKhachHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubKhachHang.add(subDanhSachKhachHang);

        subQuanLyKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subQuanLyKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        subQuanLyKhachHang.setText("+ Quản lý");
        subQuanLyKhachHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubKhachHang.add(subQuanLyKhachHang);

        panelMenu.add(groupSubKhachHang);
        panelMenu.add(filler6);

        panelHoaDon.setBackground(new java.awt.Color(142, 128, 106));
        panelHoaDon.setAlignmentX(0.0F);
        panelHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelHoaDon.setMaximumSize(new java.awt.Dimension(200, 40));
        panelHoaDon.setMinimumSize(new java.awt.Dimension(200, 40));
        panelHoaDon.setPreferredSize(new java.awt.Dimension(200, 40));
        panelHoaDon.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        lblHoaDon.setText("Hóa đơn");
        lblHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 35, 1, 1));
        panelHoaDon.add(lblHoaDon);

        panelMenu.add(panelHoaDon);

        groupSubHoaDon.setBackground(new java.awt.Color(142, 128, 106));
        groupSubHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 40, 0, 1));
        groupSubHoaDon.setAlignmentY(0.0F);
        groupSubHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        groupSubHoaDon.setPreferredSize(new java.awt.Dimension(200, 60));
        groupSubHoaDon.setLayout(new javax.swing.BoxLayout(groupSubHoaDon, javax.swing.BoxLayout.Y_AXIS));

        subLichSuHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subLichSuHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        subLichSuHoaDon.setText("+ Lịch sử hóa đơn");
        subLichSuHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubHoaDon.add(subLichSuHoaDon);

        subThongKeDoanhThu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subThongKeDoanhThu.setForeground(new java.awt.Color(255, 255, 255));
        subThongKeDoanhThu.setText("+ Thống kê doanh thu");
        subThongKeDoanhThu.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        subThongKeDoanhThu.setPreferredSize(new java.awt.Dimension(190, 30));
        groupSubHoaDon.add(subThongKeDoanhThu);

        panelMenu.add(groupSubHoaDon);
        panelMenu.add(filler8);

        panelThue.setBackground(new java.awt.Color(142, 128, 106));
        panelThue.setAlignmentX(0.0F);
        panelThue.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelThue.setMaximumSize(new java.awt.Dimension(190, 40));
        panelThue.setMinimumSize(new java.awt.Dimension(190, 40));
        panelThue.setPreferredSize(new java.awt.Dimension(190, 40));
        panelThue.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblThue.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblThue.setForeground(new java.awt.Color(255, 255, 255));
        lblThue.setText("Thuế");
        lblThue.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 35, 1, 1));
        panelThue.add(lblThue);

        panelMenu.add(panelThue);

        groupSubThue.setBackground(new java.awt.Color(142, 128, 106));
        groupSubThue.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 40, 0, 1));
        groupSubThue.setAlignmentY(0.0F);
        groupSubThue.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        groupSubThue.setPreferredSize(new java.awt.Dimension(190, 60));
        groupSubThue.setLayout(new javax.swing.BoxLayout(groupSubThue, javax.swing.BoxLayout.Y_AXIS));

        subDanhSachThue.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subDanhSachThue.setForeground(new java.awt.Color(255, 255, 255));
        subDanhSachThue.setText("+ Danh sách");
        subDanhSachThue.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubThue.add(subDanhSachThue);

        subQuanLyThue.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subQuanLyThue.setForeground(new java.awt.Color(255, 255, 255));
        subQuanLyThue.setText("+ Quản lý");
        subQuanLyThue.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubThue.add(subQuanLyThue);

        panelMenu.add(groupSubThue);
        panelMenu.add(filler10);

        PanelMonAn.setBackground(new java.awt.Color(142, 128, 106));
        PanelMonAn.setAlignmentX(0.0F);
        PanelMonAn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PanelMonAn.setMaximumSize(new java.awt.Dimension(200, 40));
        PanelMonAn.setMinimumSize(new java.awt.Dimension(200, 40));
        PanelMonAn.setPreferredSize(new java.awt.Dimension(200, 40));
        PanelMonAn.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblMonAn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMonAn.setForeground(new java.awt.Color(255, 255, 255));
        lblMonAn.setText("Món Ăn");
        lblMonAn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 35, 1, 1));
        PanelMonAn.add(lblMonAn);

        panelMenu.add(PanelMonAn);

        groupSubMonAn.setBackground(new java.awt.Color(142, 128, 106));
        groupSubMonAn.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 40, 0, 1));
        groupSubMonAn.setAlignmentY(0.0F);
        groupSubMonAn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        groupSubMonAn.setPreferredSize(new java.awt.Dimension(190, 60));
        groupSubMonAn.setLayout(new javax.swing.BoxLayout(groupSubMonAn, javax.swing.BoxLayout.Y_AXIS));

        subDanhSachMonAn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subDanhSachMonAn.setForeground(new java.awt.Color(255, 255, 255));
        subDanhSachMonAn.setText("+ Danh sách");
        subDanhSachMonAn.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubMonAn.add(subDanhSachMonAn);

        subQuanLyMonAn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subQuanLyMonAn.setForeground(new java.awt.Color(255, 255, 255));
        subQuanLyMonAn.setText("+ Quản lý");
        subQuanLyMonAn.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubMonAn.add(subQuanLyMonAn);

        panelMenu.add(groupSubMonAn);
        panelMenu.add(filler9);

        PanelKhuyenMai.setBackground(new java.awt.Color(142, 128, 106));
        PanelKhuyenMai.setAlignmentX(0.0F);
        PanelKhuyenMai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PanelKhuyenMai.setMaximumSize(new java.awt.Dimension(200, 40));
        PanelKhuyenMai.setMinimumSize(new java.awt.Dimension(200, 40));
        PanelKhuyenMai.setPreferredSize(new java.awt.Dimension(200, 40));
        PanelKhuyenMai.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        lblKhuyenMai.setText("Khuyến mãi");
        lblKhuyenMai.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 35, 1, 1));
        PanelKhuyenMai.add(lblKhuyenMai);

        panelMenu.add(PanelKhuyenMai);

        groupSubKhuyenMai.setBackground(new java.awt.Color(142, 128, 106));
        groupSubKhuyenMai.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 40, 0, 1));
        groupSubKhuyenMai.setAlignmentY(0.0F);
        groupSubKhuyenMai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        groupSubKhuyenMai.setPreferredSize(new java.awt.Dimension(190, 60));
        groupSubKhuyenMai.setLayout(new javax.swing.BoxLayout(groupSubKhuyenMai, javax.swing.BoxLayout.Y_AXIS));

        subDanhSachKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subDanhSachKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        subDanhSachKhuyenMai.setText("+ Danh sách");
        subDanhSachKhuyenMai.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubKhuyenMai.add(subDanhSachKhuyenMai);

        subQuanLyKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        subQuanLyKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        subQuanLyKhuyenMai.setText("+ Quản lý");
        subQuanLyKhuyenMai.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        groupSubKhuyenMai.add(subQuanLyKhuyenMai);

        panelMenu.add(groupSubKhuyenMai);
        panelMenu.add(filler5);

        panelDangXuat.setBackground(new java.awt.Color(142, 128, 106));
        panelDangXuat.setAlignmentX(0.0F);
        panelDangXuat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelDangXuat.setMaximumSize(new java.awt.Dimension(200, 40));
        panelDangXuat.setMinimumSize(new java.awt.Dimension(200, 40));
        panelDangXuat.setPreferredSize(new java.awt.Dimension(200, 40));
        panelDangXuat.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblMenuDangXuat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMenuDangXuat.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_dangxuat.png"))); // NOI18N
        lblMenuDangXuat.setText("Đăng xuất");
        panelDangXuat.add(lblMenuDangXuat);

        panelMenu.add(panelDangXuat);

        getContentPane().add(panelMenu, java.awt.BorderLayout.WEST);

        panelMainContent.setLayout(new java.awt.BorderLayout());

        panelHeader.setBackground(new java.awt.Color(255, 255, 255));
        panelHeader.setPreferredSize(new java.awt.Dimension(909, 60));

        lblTenTrang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTenTrang.setText("TRANG CHỦ");

        panelTaiKhoan.setBackground(new java.awt.Color(255, 255, 255));
        panelTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelTaiKhoanMouseClicked(evt);
            }
        });

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_account.png"))); // NOI18N

        lblTenNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenNhanVien.setText("Nguyễn Văn A");

        lblPhanQuyen.setText("Nhân viên quản lý");

        javax.swing.GroupLayout panelTaiKhoanLayout = new javax.swing.GroupLayout(panelTaiKhoan);
        panelTaiKhoan.setLayout(panelTaiKhoanLayout);
        panelTaiKhoanLayout.setHorizontalGroup(
                panelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelTaiKhoanLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 43,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelTaiKhoanLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblTenNhanVien)
                                        .addComponent(lblPhanQuyen))
                                .addGap(0, 27, Short.MAX_VALUE)));
        panelTaiKhoanLayout.setVerticalGroup(
                panelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelTaiKhoanLayout.createSequentialGroup()
                                .addGroup(panelTaiKhoanLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblPhanQuyen)
                                        .addGroup(panelTaiKhoanLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lblTenNhanVien)
                                                .addComponent(jLabel18)))
                                .addGap(0, 8, Short.MAX_VALUE)));

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
                panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelHeaderLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(lblTenTrang)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 623,
                                        Short.MAX_VALUE)
                                .addComponent(panelTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)));
        panelHeaderLayout.setVerticalGroup(
                panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelHeaderLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panelTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(panelHeaderLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblTenTrang)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        panelMainContent.add(panelHeader, java.awt.BorderLayout.NORTH);

        panelBody.setBackground(new java.awt.Color(255, 251, 233));

        javax.swing.GroupLayout panelBodyLayout = new javax.swing.GroupLayout(panelBody);
        panelBody.setLayout(panelBodyLayout);
        panelBodyLayout.setHorizontalGroup(
                panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 961, Short.MAX_VALUE));
        panelBodyLayout.setVerticalGroup(
                panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1109, Short.MAX_VALUE));

        panelMainContent.add(panelBody, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelMainContent, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_panelTaiKhoanMouseClicked
        // 1. Khởi tạo Dialog
        TaiKhoanDialog popup = new TaiKhoanDialog(this, true);

        // 2. Lấy vị trí và kích thước của cụm tài khoản (thay panelHeaderTaiKhoan bằng
        // tên biến của bạn)
        java.awt.Point location = panelTaiKhoan.getLocationOnScreen();
        int componentWidth = panelTaiKhoan.getWidth();
        int componentHeight = panelTaiKhoan.getHeight();

        // 3. Tính toán vị trí X (Căn lề phải)
        // Lấy tọa độ X của cụm + chiều rộng của nó - chiều rộng của Popup
        int x = location.x + componentWidth - popup.getPreferredSize().width;

        // 4. Tính toán vị trí Y (Ngay dưới chữ)
        int y = location.y + componentHeight;

        // 5. Đặt vị trí và hiển thị
        popup.setLocation(x, y);
        popup.setVisible(true);
    }// GEN-LAST:event_panelTaiKhoanMouseClicked

    private void lblMenuTrangChuMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lblMenuTrangChuMouseClicked
        // TODO add your handling code here:
        new PanelTrangChu().setVisible(true);
    }// GEN-LAST:event_lblMenuTrangChuMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelKhuyenMai;
    private javax.swing.JPanel PanelMonAn;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JPanel groupSubBan;
    private javax.swing.JPanel groupSubHoaDon;
    private javax.swing.JPanel groupSubKhachHang;
    private javax.swing.JPanel groupSubKhuVuc;
    private javax.swing.JPanel groupSubKhuyenMai;
    private javax.swing.JPanel groupSubMonAn;
    private javax.swing.JPanel groupSubNhanVien;
    private javax.swing.JPanel groupSubThue;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblBan;
    private javax.swing.JLabel lblHoaDon;
    private javax.swing.JLabel lblKhachHang;
    private javax.swing.JLabel lblKhuVuc;
    private javax.swing.JLabel lblKhuyenMai;
    private javax.swing.JLabel lblMenuDangXuat;
    private javax.swing.JLabel lblMenuTrangChu;
    private javax.swing.JLabel lblMonAn;
    private javax.swing.JLabel lblNhanVien;
    private javax.swing.JLabel lblPhanQuyen;
    private javax.swing.JLabel lblTenNhanVien;
    private javax.swing.JLabel lblTenTrang;
    private javax.swing.JLabel lblThue;
    private javax.swing.JPanel panelBan;
    private javax.swing.JPanel panelBody;
    private javax.swing.JPanel panelDangXuat;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelHoaDon;
    private javax.swing.JPanel panelKhachHang;
    private javax.swing.JPanel panelKhuVuc;
    private javax.swing.JPanel panelMainContent;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelNhanVien;
    private javax.swing.JPanel panelTaiKhoan;
    private javax.swing.JPanel panelThue;
    private javax.swing.JPanel panelTrangChu;
    private javax.swing.JLabel subDanhSachBan;
    private javax.swing.JLabel subDanhSachKhachHang;
    private javax.swing.JLabel subDanhSachKhuVuc;
    private javax.swing.JLabel subDanhSachKhuyenMai;
    private javax.swing.JLabel subDanhSachMonAn;
    private javax.swing.JLabel subDanhSachNhanVien;
    private javax.swing.JLabel subDanhSachThue;
    private javax.swing.JLabel subLapPhieuDatBan;
    private javax.swing.JLabel subLichSuHoaDon;
    private javax.swing.JLabel subQuanLyBan;
    private javax.swing.JLabel subQuanLyDatBanTruoc;
    private javax.swing.JLabel subQuanLyKhachHang;
    private javax.swing.JLabel subQuanLyKhuVuc;
    private javax.swing.JLabel subQuanLyKhuyenMai;
    private javax.swing.JLabel subQuanLyMonAn;
    private javax.swing.JLabel subQuanLyNhanVien;
    private javax.swing.JLabel subQuanLyTaiKhoan;
    private javax.swing.JLabel subQuanLyThue;
    private javax.swing.JLabel subThongKeDoanhThu;
    // End of variables declaration//GEN-END:variables
}
