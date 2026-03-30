package com.restaurant.quanlydatbannhahang.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;

public class MainForm extends javax.swing.JFrame {

    private String userRole;
    private TaiKhoan taiKhoan = null; // Lưu object TaiKhoan
    private JPanel activePanel = null;
    private JLabel activeSubLabel = null;

    // Khởi tạo các Panel chức năng
    private PanelTrangChu pnlTrangChu;

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
        this.taiKhoan = taiKhoan; // Set TaiKhoan TRƯỚC
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

    private void initCustomComponents() {
        // 0. Fix missing images
        replaceImagesWithCorrectPaths();

        // 1. Cấu hình cửa sổ
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setTitle("Hệ thống quản lý đặt bàn nhà hàng");

        // 2. Thiết lập Layout cho vùng nội dung (Quan trọng)
        // Đảm bảo panelBody dùng BorderLayout để các trang con lấp đầy diện tích
        panelBody.setLayout(new BorderLayout());

        // 3. Khởi tạo các Panel chức năng
        pnlTrangChu = new PanelTrangChu();

        // 4. Thiết lập trạng thái menu ban đầu
        panelSubQuanLy.setVisible(false);
        batSuKienMenu();
        phanQuyenGiaoDien();

        // 5. Hiển thị mặc định: Trang chủ
        updateActivePanel(panelTrangChu);
        jLabel13.setText("TRANG CHỦ");
        showPanel(pnlTrangChu); // Đưa trang chủ vào panelBody
    }

    // Helper method to load images safely
    private javax.swing.ImageIcon loadImageOrNull(String resourcePath) {
        try {
            java.net.URL imageUrl = getClass().getResource(resourcePath);
            if (imageUrl != null) {
                return new javax.swing.ImageIcon(imageUrl);
            } else {
                System.out.println("⚠ Image không tìm thấy: " + resourcePath);
                return null;
            }
        } catch (Exception e) {
            System.out.println("⚠ Lỗi load image " + resourcePath + ": " + e.getMessage());
            return null;
        }
    }

    // Method to replace images with actual files
    private void replaceImagesWithCorrectPaths() {
        try {
            // Map old paths to new paths
            javax.swing.ImageIcon homeIcon = loadImageOrNull("/images/icon_trangchu.png");
            if (homeIcon != null && jLabel1 != null) {
                jLabel1.setIcon(homeIcon);
            }

            javax.swing.ImageIcon tableIcon = loadImageOrNull("/images/icon_datban.png");
            if (tableIcon != null && jLabel3 != null) {
                jLabel3.setIcon(tableIcon);
            }

            javax.swing.ImageIcon userIcon = loadImageOrNull("/images/logo-user.png");
            if (userIcon != null && jLabel4 != null) {
                jLabel4.setIcon(userIcon);
            }

            javax.swing.ImageIcon billIcon = loadImageOrNull("/images/icon_hoadon.png");
            if (billIcon != null && jLabel10 != null) {
                jLabel10.setIcon(billIcon);
            }

            javax.swing.ImageIcon settingsIcon = loadImageOrNull("/images/icon_quanly.png");
            if (settingsIcon != null && jLabel11 != null) {
                jLabel11.setIcon(settingsIcon);
            }

            javax.swing.ImageIcon chartIcon = loadImageOrNull("/images/logo-chart.png");
            if (chartIcon != null && jLabel12 != null) {
                jLabel12.setIcon(chartIcon);
            }

            javax.swing.ImageIcon logoutIcon = loadImageOrNull("/images/logo-logout.png");
            if (logoutIcon != null && jLabel14 != null) {
                jLabel14.setIcon(logoutIcon);
            }

            javax.swing.ImageIcon accountIcon = loadImageOrNull("/images/logo-user.png");
            if (accountIcon != null && jLabel18 != null) {
                jLabel18.setIcon(accountIcon);
            }

            System.out.println("✓ Đã load xong các image");
        } catch (Exception e) {
            System.out.println("⚠ Lỗi load image: " + e.getMessage());
        }
    }

    // Hàm này dùng để nạp các trang con vào vùng trống panelBody
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

    private void phanQuyenGiaoDien() {
        // Lấy tên nhân viên từ TaiKhoan object
        String tenNhanVien = "Nhân viên";
        String chucVu = userRole;

        if (taiKhoan != null && taiKhoan.getNhanVien() != null) {
            tenNhanVien = taiKhoan.getNhanVien().getHoTen();
            chucVu = taiKhoan.getNhanVien().getChucVu();
        }

        jLabel17.setText(tenNhanVien);
        jLabel19.setText(chucVu);

        if ("Nhân viên".equals(userRole)) {
            panelQuanLy.setVisible(false);
            panelSubQuanLy.setVisible(false);
            panelThongKe.setVisible(false);
        }
    }

    private void batSuKienMenu() {
        // --- 1. NHÓM PANEL CHUYỂN TRANG CHÍNH ---
        JPanel[] pagePanels = { panelTrangChu, panelDatBan, panelKhachHang, panelHoaDon, panelThongKe };
        for (JPanel pnl : pagePanels) {
            pnl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    updateActivePanel(pnl);

                    String title = "";
                    // Lấy text của Label đầu tiên trong Panel để hiển thị lên Header
                    if (pnl.getComponent(0) instanceof JLabel) {
                        title = ((JLabel) pnl.getComponent(0)).getText();
                    }
                    jLabel13.setText(title.toUpperCase());

                    if (pnl == panelTrangChu) {
                        showPanel(pnlTrangChu);
                    } else {
                        showPanel(createTempPanel(title));
                    }
                }

                @Override
                public void mouseEntered(MouseEvent evt) {
                    if (pnl != activePanel)
                        pnl.setBackground(COLOR_MENU_HOVER);
                }

                @Override
                public void mouseExited(MouseEvent evt) {
                    if (pnl != activePanel)
                        pnl.setBackground(COLOR_MENU_BG);
                }
            });
        }

        // --- 2. PANEL QUẢN LÝ (Menu cha - Click để xổ menu con) ---
        panelQuanLy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                boolean isVisible = panelSubQuanLy.isVisible();
                panelSubQuanLy.setVisible(!isVisible);
                panelMenu.revalidate();
            }

            @Override
            public void mouseEntered(MouseEvent evt) {
                if (activePanel != panelQuanLy)
                    panelQuanLy.setBackground(COLOR_MENU_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                if (activePanel != panelQuanLy)
                    panelQuanLy.setBackground(COLOR_MENU_BG);
            }
        });

        // --- 3. GÁN SỰ KIỆN CHO SUB-MENU ---
        attachSubMenuEvents(jLabel2, "Bàn");
        attachSubMenuEvents(jLabel5, "Món ăn");
        attachSubMenuEvents(jLabel6, "Khu vực");
        attachSubMenuEvents(jLabel7, "Tài khoản");
        attachSubMenuEvents(jLabel8, "Khuyến mãi");
        attachSubMenuEvents(jLabel9, "Thuế");

        // --- 4. ĐĂNG XUẤT ---
        panelDangXuat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int opt = JOptionPane.showConfirmDialog(MainForm.this,
                        "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (opt == JOptionPane.YES_OPTION) {
                    // Quay lại LoginForm thay vì thoát
                    LoginForm loginForm = new LoginForm();
                    loginForm.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                    loginForm.setVisible(true);
                    dispose(); // Đóng MainForm
                }
            }

            @Override
            public void mouseEntered(MouseEvent evt) {
                panelDangXuat.setBackground(new Color(200, 80, 80));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                panelDangXuat.setBackground(COLOR_MENU_BG);
            }
        });
    }

    private void updateActivePanel(JPanel newActive) {
        if (activePanel != null) {
            activePanel.setBackground(COLOR_MENU_BG);
        }

        if (newActive != panelQuanLy && activeSubLabel != null) {
            activeSubLabel.setForeground(COLOR_TEXT_NORMAL);
            activeSubLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            activeSubLabel = null;
        }

        activePanel = newActive;
        activePanel.setBackground(COLOR_MENU_HOVER);
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

                jLabel13.setText("QUẢN LÝ " + menuName.toUpperCase());
                updateActivePanel(panelQuanLy);

                showPanel(createTempPanel("Quản lý " + menuName));
            }

            @Override
            public void mouseEntered(MouseEvent evt) {
                if (label != activeSubLabel)
                    label.setForeground(COLOR_TEXT_ACTIVE);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                if (label != activeSubLabel)
                    label.setForeground(COLOR_TEXT_NORMAL);
            }
        });
    }

    // [Giữ nguyên phần initComponents bên dưới]
    // từ đây trở xuống không sửa được
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMenu = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        panelTrangChu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelDatBan = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panelKhachHang = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        panelHoaDon = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        panelQuanLy = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        panelSubQuanLy = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        panelThongKe = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        panelDangXuat = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        panelMainContent = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        panelBody = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelMenu.setBackground(new java.awt.Color(142, 128, 106));
        panelMenu.setMaximumSize(new java.awt.Dimension(200, 600));
        panelMenu.setMinimumSize(new java.awt.Dimension(200, 600));
        panelMenu.setPreferredSize(new java.awt.Dimension(200, 600));
        panelMenu.setLayout(new javax.swing.BoxLayout(panelMenu, javax.swing.BoxLayout.Y_AXIS));

        jPanel4.setBackground(new java.awt.Color(142, 128, 106));
        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 1, 1));
        jPanel4.setAlignmentX(0.0F);
        jPanel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jPanel4.setMaximumSize(new java.awt.Dimension(200, 70));
        jPanel4.setMinimumSize(new java.awt.Dimension(200, 70));
        jPanel4.setPreferredSize(new java.awt.Dimension(200, 60));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Trần Long");
        jLabel15.setMaximumSize(new java.awt.Dimension(150, 27));
        jPanel4.add(jLabel15);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Restaurant");
        jPanel4.add(jLabel16);

        panelMenu.add(jPanel4);

        panelTrangChu.setBackground(new java.awt.Color(142, 128, 106));
        panelTrangChu.setAlignmentX(0.0F);
        panelTrangChu.setAlignmentY(0.0F);
        panelTrangChu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelTrangChu.setMaximumSize(new java.awt.Dimension(200, 40));
        panelTrangChu.setMinimumSize(new java.awt.Dimension(200, 40));
        panelTrangChu.setPreferredSize(new java.awt.Dimension(200, 40));
        panelTrangChu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        // Image loading will be handled in replaceImagesWithCorrectPaths()
        // jLabel1.setIcon(new
        // javax.swing.ImageIcon(getClass().getResource("/images/home_app_logo_25dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png")));
        // // NOI18N
        jLabel1.setText("Trang chủ");
        panelTrangChu.add(jLabel1);

        panelMenu.add(panelTrangChu);

        panelDatBan.setBackground(new java.awt.Color(142, 128, 106));
        panelDatBan.setAlignmentX(0.0F);
        panelDatBan.setAlignmentY(0.0F);
        panelDatBan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelDatBan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        panelDatBan.setMaximumSize(new java.awt.Dimension(200, 40));
        panelDatBan.setMinimumSize(new java.awt.Dimension(200, 40));
        panelDatBan.setPreferredSize(new java.awt.Dimension(200, 40));
        panelDatBan.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        // Image loading will be handled in replaceImagesWithCorrectPaths()
        // jLabel3.setIcon(new
        // javax.swing.ImageIcon(getClass().getResource("/images/icon_datban.png"))); //
        // NOI18N
        jLabel3.setText("Đặt bàn");
        panelDatBan.add(jLabel3);

        panelMenu.add(panelDatBan);

        panelKhachHang.setBackground(new java.awt.Color(142, 128, 106));
        panelKhachHang.setAlignmentX(0.0F);
        panelKhachHang.setAlignmentY(0.0F);
        panelKhachHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        panelKhachHang.setMaximumSize(new java.awt.Dimension(200, 40));
        panelKhachHang.setMinimumSize(new java.awt.Dimension(200, 40));
        panelKhachHang.setPreferredSize(new java.awt.Dimension(200, 40));
        panelKhachHang.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        // Image loading will be handled in replaceImagesWithCorrectPaths()
        // jLabel4.setIcon(new
        // javax.swing.ImageIcon(getClass().getResource("/images/group_25dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png")));
        // // NOI18N
        jLabel4.setText("Khách hàng");
        panelKhachHang.add(jLabel4);

        panelMenu.add(panelKhachHang);

        panelHoaDon.setBackground(new java.awt.Color(142, 128, 106));
        panelHoaDon.setAlignmentX(0.0F);
        panelHoaDon.setAlignmentY(0.0F);
        panelHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelHoaDon.setMaximumSize(new java.awt.Dimension(200, 40));
        panelHoaDon.setMinimumSize(new java.awt.Dimension(200, 40));
        panelHoaDon.setPreferredSize(new java.awt.Dimension(200, 40));
        panelHoaDon.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        // Image loading will be handled in replaceImagesWithCorrectPaths()
        // jLabel10.setIcon(new
        // javax.swing.ImageIcon(getClass().getResource("/images/receipt_long_25dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png")));
        // // NOI18N
        jLabel10.setText("Hóa đơn");
        panelHoaDon.add(jLabel10);

        panelMenu.add(panelHoaDon);

        panelQuanLy.setBackground(new java.awt.Color(142, 128, 106));
        panelQuanLy.setAlignmentX(0.0F);
        panelQuanLy.setAlignmentY(0.0F);
        panelQuanLy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelQuanLy.setMaximumSize(new java.awt.Dimension(200, 40));
        panelQuanLy.setMinimumSize(new java.awt.Dimension(200, 40));
        panelQuanLy.setPreferredSize(new java.awt.Dimension(200, 40));
        panelQuanLy.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        // Image loading will be handled in replaceImagesWithCorrectPaths()
        // jLabel11.setIcon(new
        // javax.swing.ImageIcon(getClass().getResource("/images/settings_25dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png")));
        // // NOI18N
        jLabel11.setText("Quản lý");
        panelQuanLy.add(jLabel11);

        panelMenu.add(panelQuanLy);

        panelSubQuanLy.setBackground(new java.awt.Color(142, 128, 106));
        panelSubQuanLy.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 25, 1, 1));
        panelSubQuanLy.setAlignmentY(0.0F);
        panelSubQuanLy.setMaximumSize(new java.awt.Dimension(200, 180));
        panelSubQuanLy.setMinimumSize(new java.awt.Dimension(200, 180));
        panelSubQuanLy.setPreferredSize(new java.awt.Dimension(200, 180));
        panelSubQuanLy.setLayout(new javax.swing.BoxLayout(panelSubQuanLy, javax.swing.BoxLayout.Y_AXIS));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("+ Bàn");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.setPreferredSize(new java.awt.Dimension(37, 20));
        panelSubQuanLy.add(jLabel2);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("+ Món ăn");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSubQuanLy.add(jLabel5);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("+ Khu vực");
        jLabel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSubQuanLy.add(jLabel6);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("+ Tài khoản");
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSubQuanLy.add(jLabel7);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("+ Khuyến mãi");
        jLabel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSubQuanLy.add(jLabel8);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("+ Thuế");
        jLabel9.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 5, 1));
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSubQuanLy.add(jLabel9);

        panelMenu.add(panelSubQuanLy);

        panelThongKe.setBackground(new java.awt.Color(142, 128, 106));
        panelThongKe.setAlignmentX(0.0F);
        panelThongKe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelThongKe.setMaximumSize(new java.awt.Dimension(200, 40));
        panelThongKe.setMinimumSize(new java.awt.Dimension(200, 40));
        panelThongKe.setPreferredSize(new java.awt.Dimension(200, 40));
        panelThongKe.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        // Image loading will be handled in replaceImagesWithCorrectPaths()
        // jLabel12.setIcon(new
        // javax.swing.ImageIcon(getClass().getResource("/images/finance_25dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png")));
        // // NOI18N
        jLabel12.setText("Thống kê");
        panelThongKe.add(jLabel12);

        panelMenu.add(panelThongKe);

        panelDangXuat.setBackground(new java.awt.Color(142, 128, 106));
        panelDangXuat.setAlignmentX(0.0F);
        panelDangXuat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelDangXuat.setMaximumSize(new java.awt.Dimension(200, 40));
        panelDangXuat.setMinimumSize(new java.awt.Dimension(200, 40));
        panelDangXuat.setPreferredSize(new java.awt.Dimension(200, 40));
        panelDangXuat.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        // Image loading will be handled in replaceImagesWithCorrectPaths()
        // jLabel14.setIcon(new
        // javax.swing.ImageIcon(getClass().getResource("/images/logout_25dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png")));
        // // NOI18N
        jLabel14.setText("Đăng xuất");
        panelDangXuat.add(jLabel14);

        panelMenu.add(panelDangXuat);

        getContentPane().add(panelMenu, java.awt.BorderLayout.WEST);

        panelMainContent.setLayout(new java.awt.BorderLayout());

        panelHeader.setBackground(new java.awt.Color(255, 255, 255));
        panelHeader.setPreferredSize(new java.awt.Dimension(909, 90));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("TRANG CHỦ");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(200, 60));

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        // Image loading will be handled in replaceImagesWithCorrectPaths()
        // jLabel18.setIcon(new
        // javax.swing.ImageIcon(getClass().getResource("/images/account_circle_40dp_000000_FILL0_wght400_GRAD0_opsz40.png")));
        // // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("Nguyễn Văn A");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel19.setText("Nhân viên quản lý");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 43,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel17)
                                        .addComponent(jLabel19))
                                .addGap(0, 27, Short.MAX_VALUE)));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel17)
                                                .addGap(0, 0, 0)
                                                .addComponent(jLabel19)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
                panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelHeaderLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 469,
                                        Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)));
        panelHeaderLayout.setVerticalGroup(
                panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelHeaderLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(panelHeaderLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        panelMainContent.add(panelHeader, java.awt.BorderLayout.NORTH);

        panelBody.setBackground(new java.awt.Color(255, 251, 233));

//        javax.swing.GroupLayout panelBodyLayout = new javax.swing.GroupLayout(panelBody);
//        panelBody.setLayout(panelBodyLayout);
//        panelBodyLayout.setHorizontalGroup(
//                panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGap(0, 0, Short.MAX_VALUE));
//        panelBodyLayout.setVerticalGroup(
//                panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGap(0, 600, Short.MAX_VALUE));

        panelMainContent.add(panelBody, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelMainContent, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
     // Variables declaration - do not modify//GEN-BEGIN:variables

    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
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
