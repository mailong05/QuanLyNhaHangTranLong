package com.restaurant.quanlydatbannhahang.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;
import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;

public class MainForm extends javax.swing.JFrame {

    private String userRole;
    private TaiKhoan taiKhoan = null; // Lưu object TaiKhoan
    private JPanel activePanel = null;
    private JLabel activeSubLabel = null;

    // Khởi tạo các Panel chức năng
    private PanelTrangChu pnlTrangChu;
    private PanelDatBan pnlDatBan;
    private PanelKhachHang pnlKhachHang;
    private PanelHoaDon pnlHoaDon;
    private PanelQuanLyBan pnlQuanLyBan;
    private PanelQuanLyMonAn pnlQuanLyMonAn;
    private PanelQuanLyKhuyenMai pnlQuanLyKhuyenMai;
    private PanelQuanLyThue pnlQuanLyThue;

    // Định nghĩa màu sắc chuẩn
    private final Color COLOR_MENU_BG = new Color(142, 128, 106);
    private final Color COLOR_MENU_HOVER = new Color(165, 150, 125);
    private final Color COLOR_TEXT_NORMAL = new Color(255, 255, 255);
    private final Color COLOR_TEXT_ACTIVE = new Color(255, 240, 150);

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
    }

    public MainForm(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
        this.userRole = (taiKhoan != null && taiKhoan.getNhanVien() != null)
                ? taiKhoan.getNhanVien().getChucVu()
                : "Quản lý";
        try {
            initComponents();
            initCustomComponents();
        } catch (Exception e) {
            System.out.println("❌ MainForm: Lỗi " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Lỗi khởi tạo MainForm", e);
        }
    }

    public MainForm() {
        this("Quản lý");
    }

    /**
     * HÀM BỔ TRỢ: Vẽ nền bo góc cho Panel
     */
    private void paintRoundedPanel(JPanel panel, Color color) {
        panel.setOpaque(false);
        panel.setBorder(new AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                
                //BO GÓC Vẽ lấp đầy từ tọa độ (0,0) đến hết chiều rộng và chiều cao của Panel
                g2.fillRoundRect(0, 0, width, height, 15, 15);
                g2.dispose();
            }
        });
        panel.repaint();
    }

    private void replaceImagesWithCorrectPaths() {
        try {
            javax.swing.ImageIcon homeIcon = loadImageOrNull("/images/icon_trangchu.png");
            if (homeIcon != null && lblMenuTrangChu != null) lblMenuTrangChu.setIcon(homeIcon);

            javax.swing.ImageIcon tableIcon = loadImageOrNull("/images/icon_datban.png");
            if (tableIcon != null && lblMenuDatBan != null) lblMenuDatBan.setIcon(tableIcon);

            javax.swing.ImageIcon customerIcon = loadImageOrNull("/images/icon_khachhang.png");
            if (customerIcon != null && lblMenuKhachHang != null) lblMenuKhachHang.setIcon(customerIcon);

            javax.swing.ImageIcon billIcon = loadImageOrNull("/images/icon_hoadon.png");
            if (billIcon != null && lblMenuHoaDon != null) lblMenuHoaDon.setIcon(billIcon);

            javax.swing.ImageIcon managementIcon = loadImageOrNull("/images/icon_quanly.png");
            if (managementIcon != null && lblMenuQuanLy != null) lblMenuQuanLy.setIcon(managementIcon);

            javax.swing.ImageIcon chartIcon = loadImageOrNull("/images/icon_thongke.png");
            if (chartIcon != null && lblMenuThongKe != null) lblMenuThongKe.setIcon(chartIcon);

            javax.swing.ImageIcon logoutIcon = loadImageOrNull("/images/icon_dangxuat.png");
            if (logoutIcon != null && lblMenuDangXuat != null) lblMenuDangXuat.setIcon(logoutIcon);

            javax.swing.ImageIcon accountIcon = loadImageOrNull("/images/icon_account.png");
            if (accountIcon != null && jLabel18 != null) jLabel18.setIcon(accountIcon);

            System.out.println("✓ Đã cập nhật xong toàn bộ icon hệ thống.");
        } catch (Exception e) {
            System.out.println("⚠ Lỗi khi gán icon vào Label: " + e.getMessage());
        }
    }

    private javax.swing.ImageIcon loadImageOrNull(String resourcePath) {
        try {
            java.net.URL imageUrl = getClass().getResource(resourcePath);
            return (imageUrl != null) ? new javax.swing.ImageIcon(imageUrl) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private void phanQuyenGiaoDien() {
        String tenNhanVien = "Nhân viên";
        String chucVu = userRole;
        if (taiKhoan != null && taiKhoan.getNhanVien() != null) {
            tenNhanVien = taiKhoan.getNhanVien().getHoTen();
            chucVu = taiKhoan.getNhanVien().getChucVu();
        }
        lblTenNhanVien.setText(tenNhanVien);
        lblPhanQuyen.setText(chucVu);
        if ("Nhân viên".equals(userRole)) {
            panelQuanLy.setVisible(false);
            panelSubQuanLy.setVisible(false);
            panelThongKe.setVisible(false);
        }
    }

    private void initCustomComponents() {
        replaceImagesWithCorrectPaths();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setTitle("Hệ thống quản lý đặt bàn nhà hàng");
        panelBody.setLayout(new BorderLayout());

        pnlTrangChu = new PanelTrangChu();
        pnlDatBan = new PanelDatBan();
        pnlKhachHang = new PanelKhachHang();
        pnlHoaDon = new PanelHoaDon();
        pnlQuanLyBan = new PanelQuanLyBan();
        pnlQuanLyMonAn = new PanelQuanLyMonAn();
        pnlQuanLyKhuyenMai = new PanelQuanLyKhuyenMai();
        pnlQuanLyThue = new PanelQuanLyThue();

        panelSubQuanLy.setVisible(false);
        batSuKienMenu();
        phanQuyenGiaoDien();

        // Khởi tạo bo góc ban đầu cho các panel chính
        JPanel[] allPanels = {panelTrangChu, panelDatBan, panelKhachHang, panelHoaDon, panelThongKe, panelQuanLy, panelDangXuat};
        for(JPanel p : allPanels) paintRoundedPanel(p, COLOR_MENU_BG);

        updateActivePanel(panelTrangChu);
        lblTenTrang.setText("TRANG CHỦ");
        showPanel(pnlTrangChu);
    }

    private void showPanel(JPanel panel) {
        panelBody.removeAll();
        panelBody.add(panel, BorderLayout.CENTER);
        panelBody.revalidate();
        panelBody.repaint();
    }

    private JPanel createTempPanel(String title) {
        JPanel pnl = new JPanel();
        pnl.setBackground(new Color(255, 251, 233));
        pnl.setLayout(new java.awt.GridBagLayout());
        JLabel lbl = new JLabel("Chức năng [" + title + "] đang phát triển...");
        lbl.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        pnl.add(lbl);
        return pnl;
    }

    private void updateActivePanel(JPanel newActive) {
        if (activePanel != null) {
            paintRoundedPanel(activePanel, COLOR_MENU_BG);
        }
        if (newActive != panelQuanLy && activeSubLabel != null) {
            activeSubLabel.setForeground(COLOR_TEXT_NORMAL);
            activeSubLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            activeSubLabel = null;
        }
        activePanel = newActive;
        paintRoundedPanel(activePanel, COLOR_MENU_HOVER);
    }

    private void batSuKienMenu() {
        JPanel[] pagePanels = { panelTrangChu, panelDatBan, panelKhachHang, panelHoaDon, panelThongKe };
        for (JPanel pnl : pagePanels) {
            pnl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    updateActivePanel(pnl);
                    String title = "";
                    if (pnl.getComponent(0) instanceof JLabel) {
                        title = ((JLabel) pnl.getComponent(0)).getText();
                    }
                    lblTenTrang.setText(title.toUpperCase());
                    if (pnl == panelTrangChu) showPanel(pnlTrangChu);
                    else if (pnl == panelDatBan) showPanel(pnlDatBan);
                    else if (pnl == panelKhachHang) showPanel(pnlKhachHang);
                    else if (pnl == panelHoaDon) showPanel(pnlHoaDon);
                    else showPanel(createTempPanel(title));
                }
                @Override
                public void mouseEntered(MouseEvent evt) {
                    if (pnl != activePanel) paintRoundedPanel(pnl, COLOR_MENU_HOVER);
                }
                @Override
                public void mouseExited(MouseEvent evt) {
                    if (pnl != activePanel) paintRoundedPanel(pnl, COLOR_MENU_BG);
                }
            });
        }

        panelQuanLy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                boolean isVisible = panelSubQuanLy.isVisible();
                panelSubQuanLy.setVisible(!isVisible);
                panelMenu.revalidate();
            }
            @Override
            public void mouseEntered(MouseEvent evt) {
                if (activePanel != panelQuanLy) paintRoundedPanel(panelQuanLy, COLOR_MENU_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                if (activePanel != panelQuanLy) paintRoundedPanel(panelQuanLy, COLOR_MENU_BG);
            }
        });

        attachSubMenuEvents(lblMenuQuanLyBan, "Bàn");
        attachSubMenuEvents(lblMenuQuanLyMonAn, "Món ăn");
        attachSubMenuEvents(lblMenuQuanLyKhuVuc, "Khu vực");
        attachSubMenuEvents(lblMenuQuanLyTaiKhoan, "Tài khoản");
        attachSubMenuEvents(lblMenuQuanLyKhuyenMai, "Khuyến mãi");
        attachSubMenuEvents(lblMenuQuanLyThue, "Thuế");

        panelDangXuat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int opt = JOptionPane.showConfirmDialog(MainForm.this, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (opt == JOptionPane.YES_OPTION) {
                    new LoginForm().setVisible(true);
                    dispose();
                }
            }
            @Override
            public void mouseEntered(MouseEvent evt) {
                paintRoundedPanel(panelDangXuat, new Color(200, 80, 80));
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                paintRoundedPanel(panelDangXuat, COLOR_MENU_BG);
            }
        });
    }

    private void attachSubMenuEvents(JLabel label, String menuName) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (activeSubLabel != null) {
                    activeSubLabel.setForeground(COLOR_TEXT_NORMAL);
                    activeSubLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                }
                activeSubLabel = label;
                activeSubLabel.setForeground(COLOR_TEXT_ACTIVE);
                activeSubLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
                lblTenTrang.setText("QUẢN LÝ " + menuName.toUpperCase());
                updateActivePanel(panelQuanLy);

                switch (menuName) {
                    case "Bàn": showPanel(pnlQuanLyBan); break;
                    case "Món ăn": showPanel(pnlQuanLyMonAn); break;
                    case "Khuyến mãi": showPanel(pnlQuanLyKhuyenMai); break;
                    case "Thuế": showPanel(pnlQuanLyThue); break;
                    default: showPanel(createTempPanel("Quản lý " + menuName)); break;
                }
            }
            @Override
            public void mouseEntered(MouseEvent evt) {
                if (label != activeSubLabel) label.setForeground(COLOR_TEXT_ACTIVE);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                if (label != activeSubLabel) label.setForeground(COLOR_TEXT_NORMAL);
            }
        });
    }

    //từ đây trở xuống không sửa được
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMenu = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        panelTrangChu = new javax.swing.JPanel();
        lblMenuTrangChu = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7));
        panelDatBan = new javax.swing.JPanel();
        lblMenuDatBan = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7));
        panelKhachHang = new javax.swing.JPanel();
        lblMenuKhachHang = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7));
        panelHoaDon = new javax.swing.JPanel();
        lblMenuHoaDon = new javax.swing.JLabel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7));
        panelQuanLy = new javax.swing.JPanel();
        lblMenuQuanLy = new javax.swing.JLabel();
        panelSubQuanLy = new javax.swing.JPanel();
        lblMenuQuanLyBan = new javax.swing.JLabel();
        lblMenuQuanLyMonAn = new javax.swing.JLabel();
        lblMenuQuanLyKhuVuc = new javax.swing.JLabel();
        lblMenuQuanLyTaiKhoan = new javax.swing.JLabel();
        lblMenuQuanLyKhuyenMai = new javax.swing.JLabel();
        lblMenuQuanLyThue = new javax.swing.JLabel();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7));
        panelThongKe = new javax.swing.JPanel();
        lblMenuThongKe = new javax.swing.JLabel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        panelDangXuat = new javax.swing.JPanel();
        lblMenuDangXuat = new javax.swing.JLabel();
        panelMainContent = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        lblTenTrang = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        lblTenNhanVien = new javax.swing.JLabel();
        lblPhanQuyen = new javax.swing.JLabel();
        panelBody = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelMenu.setBackground(new java.awt.Color(142, 128, 106));
        panelMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 5, 10, 5));
        panelMenu.setMaximumSize(new java.awt.Dimension(200, 32767));
        panelMenu.setMinimumSize(new java.awt.Dimension(200, 600));
        panelMenu.setPreferredSize(new java.awt.Dimension(200, 600));
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
        panelTrangChu.add(lblMenuTrangChu);

        panelMenu.add(panelTrangChu);
        panelMenu.add(filler2);

        panelDatBan.setBackground(new java.awt.Color(142, 128, 106));
        panelDatBan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panelDatBan.setAlignmentX(0.0F);
        panelDatBan.setAlignmentY(0.0F);
        panelDatBan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelDatBan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        panelDatBan.setMaximumSize(new java.awt.Dimension(200, 40));
        panelDatBan.setMinimumSize(new java.awt.Dimension(200, 40));
        panelDatBan.setPreferredSize(new java.awt.Dimension(200, 40));
        panelDatBan.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblMenuDatBan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMenuDatBan.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuDatBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_datban.png"))); // NOI18N
        lblMenuDatBan.setText("Đặt bàn");
        panelDatBan.add(lblMenuDatBan);

        panelMenu.add(panelDatBan);
        panelMenu.add(filler1);

        panelKhachHang.setBackground(new java.awt.Color(142, 128, 106));
        panelKhachHang.setAlignmentX(0.0F);
        panelKhachHang.setAlignmentY(0.0F);
        panelKhachHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        panelKhachHang.setMaximumSize(new java.awt.Dimension(200, 40));
        panelKhachHang.setMinimumSize(new java.awt.Dimension(200, 40));
        panelKhachHang.setPreferredSize(new java.awt.Dimension(200, 40));
        panelKhachHang.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblMenuKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMenuKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_khachhang.png"))); // NOI18N
        lblMenuKhachHang.setText("Khách hàng");
        panelKhachHang.add(lblMenuKhachHang);

        panelMenu.add(panelKhachHang);
        panelMenu.add(filler3);

        panelHoaDon.setBackground(new java.awt.Color(142, 128, 106));
        panelHoaDon.setAlignmentX(0.0F);
        panelHoaDon.setAlignmentY(0.0F);
        panelHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelHoaDon.setMaximumSize(new java.awt.Dimension(200, 40));
        panelHoaDon.setMinimumSize(new java.awt.Dimension(200, 40));
        panelHoaDon.setPreferredSize(new java.awt.Dimension(200, 40));
        panelHoaDon.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblMenuHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMenuHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_hoadon.png"))); // NOI18N
        lblMenuHoaDon.setText("Hóa đơn");
        panelHoaDon.add(lblMenuHoaDon);

        panelMenu.add(panelHoaDon);
        panelMenu.add(filler4);

        panelQuanLy.setBackground(new java.awt.Color(142, 128, 106));
        panelQuanLy.setAlignmentX(0.0F);
        panelQuanLy.setAlignmentY(0.0F);
        panelQuanLy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelQuanLy.setMaximumSize(new java.awt.Dimension(200, 40));
        panelQuanLy.setMinimumSize(new java.awt.Dimension(200, 40));
        panelQuanLy.setPreferredSize(new java.awt.Dimension(200, 40));
        panelQuanLy.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblMenuQuanLy.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMenuQuanLy.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuQuanLy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_quanly.png"))); // NOI18N
        lblMenuQuanLy.setText("Quản lý");
        panelQuanLy.add(lblMenuQuanLy);

        panelMenu.add(panelQuanLy);

        panelSubQuanLy.setBackground(new java.awt.Color(142, 128, 106));
        panelSubQuanLy.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        panelSubQuanLy.setAlignmentY(0.0F);
        panelSubQuanLy.setMaximumSize(new java.awt.Dimension(200, 180));
        panelSubQuanLy.setMinimumSize(new java.awt.Dimension(200, 180));
        panelSubQuanLy.setPreferredSize(new java.awt.Dimension(200, 180));
        panelSubQuanLy.setLayout(new javax.swing.BoxLayout(panelSubQuanLy, javax.swing.BoxLayout.Y_AXIS));

        lblMenuQuanLyBan.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblMenuQuanLyBan.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuQuanLyBan.setText("+ Bàn");
        lblMenuQuanLyBan.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        lblMenuQuanLyBan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMenuQuanLyBan.setPreferredSize(new java.awt.Dimension(37, 20));
        panelSubQuanLy.add(lblMenuQuanLyBan);

        lblMenuQuanLyMonAn.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblMenuQuanLyMonAn.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuQuanLyMonAn.setText("+ Món ăn");
        lblMenuQuanLyMonAn.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        lblMenuQuanLyMonAn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSubQuanLy.add(lblMenuQuanLyMonAn);

        lblMenuQuanLyKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblMenuQuanLyKhuVuc.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuQuanLyKhuVuc.setText("+ Khu vực");
        lblMenuQuanLyKhuVuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        lblMenuQuanLyKhuVuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSubQuanLy.add(lblMenuQuanLyKhuVuc);

        lblMenuQuanLyTaiKhoan.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblMenuQuanLyTaiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuQuanLyTaiKhoan.setText("+ Tài khoản");
        lblMenuQuanLyTaiKhoan.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        lblMenuQuanLyTaiKhoan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSubQuanLy.add(lblMenuQuanLyTaiKhoan);

        lblMenuQuanLyKhuyenMai.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblMenuQuanLyKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuQuanLyKhuyenMai.setText("+ Khuyến mãi");
        lblMenuQuanLyKhuyenMai.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        lblMenuQuanLyKhuyenMai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSubQuanLy.add(lblMenuQuanLyKhuyenMai);

        lblMenuQuanLyThue.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblMenuQuanLyThue.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuQuanLyThue.setText("+ Thuế");
        lblMenuQuanLyThue.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        lblMenuQuanLyThue.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSubQuanLy.add(lblMenuQuanLyThue);

        panelMenu.add(panelSubQuanLy);
        panelMenu.add(filler6);

        panelThongKe.setBackground(new java.awt.Color(142, 128, 106));
        panelThongKe.setAlignmentX(0.0F);
        panelThongKe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelThongKe.setMaximumSize(new java.awt.Dimension(200, 40));
        panelThongKe.setMinimumSize(new java.awt.Dimension(200, 40));
        panelThongKe.setPreferredSize(new java.awt.Dimension(200, 40));
        panelThongKe.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblMenuThongKe.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMenuThongKe.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_thongke.png"))); // NOI18N
        lblMenuThongKe.setText("Thống kê");
        panelThongKe.add(lblMenuThongKe);

        panelMenu.add(panelThongKe);
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

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_account.png"))); // NOI18N

        lblTenNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenNhanVien.setText("Nguyễn Văn A");

        lblPhanQuyen.setText("Nhân viên quản lý");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenNhanVien)
                    .addComponent(lblPhanQuyen))
                .addGap(0, 27, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPhanQuyen)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTenNhanVien)
                        .addComponent(jLabel18)))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblTenTrang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 643, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblTenTrang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMainContent.add(panelHeader, java.awt.BorderLayout.NORTH);

        panelBody.setBackground(new java.awt.Color(255, 251, 233));

        javax.swing.GroupLayout panelBodyLayout = new javax.swing.GroupLayout(panelBody);
        panelBody.setLayout(panelBodyLayout);
        panelBodyLayout.setHorizontalGroup(
            panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 981, Short.MAX_VALUE)
        );
        panelBodyLayout.setVerticalGroup(
            panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 562, Short.MAX_VALUE)
        );

        panelMainContent.add(panelBody, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelMainContent, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblMenuDangXuat;
    private javax.swing.JLabel lblMenuDatBan;
    private javax.swing.JLabel lblMenuHoaDon;
    private javax.swing.JLabel lblMenuKhachHang;
    private javax.swing.JLabel lblMenuQuanLy;
    private javax.swing.JLabel lblMenuQuanLyBan;
    private javax.swing.JLabel lblMenuQuanLyKhuVuc;
    private javax.swing.JLabel lblMenuQuanLyKhuyenMai;
    private javax.swing.JLabel lblMenuQuanLyMonAn;
    private javax.swing.JLabel lblMenuQuanLyTaiKhoan;
    private javax.swing.JLabel lblMenuQuanLyThue;
    private javax.swing.JLabel lblMenuThongKe;
    private javax.swing.JLabel lblMenuTrangChu;
    private javax.swing.JLabel lblPhanQuyen;
    private javax.swing.JLabel lblTenNhanVien;
    private javax.swing.JLabel lblTenTrang;
    private javax.swing.JPanel panelBody;
    private javax.swing.JPanel panelDangXuat;
    private javax.swing.JPanel panelDatBan;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelHoaDon;
    private javax.swing.JPanel panelKhachHang;
    private javax.swing.JPanel panelMainContent;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelQuanLy;
    private javax.swing.JPanel panelSubQuanLy;
    private javax.swing.JPanel panelThongKe;
    private javax.swing.JPanel panelTrangChu;
    // End of variables declaration//GEN-END:variables
}



