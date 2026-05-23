package com.restaurant.quanlydatbannhahang.gui;

import com.restaurant.quanlydatbannhahang.entity.ChiTietHoaDon;
import com.restaurant.quanlydatbannhahang.entity.HoaDon;
import com.restaurant.quanlydatbannhahang.entity.KhachHang;
import com.restaurant.quanlydatbannhahang.entity.KhuyenMai;
import com.restaurant.quanlydatbannhahang.entity.MonAn;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.entity.PhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.PhuongThucTT;
import com.restaurant.quanlydatbannhahang.entity.Thue;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiHoaDon;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiPhieuDat;
import com.restaurant.quanlydatbannhahang.session.HoaDonDraftSession;
import com.restaurant.quanlydatbannhahang.session.SessionManager;
import com.restaurant.quanlydatbannhahang.service.BanService;
import com.restaurant.quanlydatbannhahang.service.ChiTietHoaDonService;
import com.restaurant.quanlydatbannhahang.service.HoaDonService;
import com.restaurant.quanlydatbannhahang.service.KhachHangService;
import com.restaurant.quanlydatbannhahang.service.KhuyenMaiService;
import com.restaurant.quanlydatbannhahang.service.MonAnService;
import com.restaurant.quanlydatbannhahang.service.NhanVienService;
import com.restaurant.quanlydatbannhahang.service.PhieuDatBanService;
import com.restaurant.quanlydatbannhahang.service.ThueService;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEnumLoader;
import com.restaurant.quanlydatbannhahang.util.CurrencyUtility;
import com.restaurant.quanlydatbannhahang.util.IDGeneratorHelper;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class PanelLapHoaDon extends javax.swing.JPanel {
    private KhachHangService khachHangService;
    private KhuyenMaiService khuyenMaiService;
    private ThueService thueService;
    private HoaDonService hoaDonService;
    private ChiTietHoaDonService chiTietHoaDonService;
    private BanService banService;
    private NhanVienService nhanVienService;
    private MonAnService monAnService;
    private PhieuDatBanService phieuDatBanService;
    private List<KhachHang> allKhachHang;
    private List<MonAn> allMonAn = new ArrayList<>();
    private final List<KhachHang> searchedKhachHang = new ArrayList<>();
    private final Map<String, KhuyenMai> khuyenMaiByDisplay = new LinkedHashMap<>();
    private final Map<String, Boolean> khuyenMaiEligible = new LinkedHashMap<>();
    private KhachHang selectedKhachHang = null;
    private int diemDaApDung = 0;
    private static final int VND_PER_DIEM = 1000;
    private static final String NO_PROMO_DISPLAY = "Không áp dụng";
    private static final String MA_THUE_VAT_MAC_DINH = "TH001";
    private static final String MA_PHI_DICH_VU_MAC_DINH = "TH003";
    private boolean loadingKhuyenMai = false;

    public PanelLapHoaDon() {
        initComponents();
        khachHangService = new KhachHangService();
        khuyenMaiService = new KhuyenMaiService();
        thueService = new ThueService();
        hoaDonService = new HoaDonService();
        chiTietHoaDonService = new ChiTietHoaDonService();
        banService = new BanService();
        nhanVienService = new NhanVienService();
        monAnService = new MonAnService();
        phieuDatBanService = new PhieuDatBanService();
        allKhachHang = new ArrayList<>();
        allMonAn = monAnService.getAllMonAn();
        customUI();
        loadKhachHangToTable();
        loadKhuyenMaiToComboBox();
        loadHoaDonDraft();
        this.addHierarchyListener(new java.awt.event.HierarchyListener() {
            @Override
            public void hierarchyChanged(java.awt.event.HierarchyEvent e) {
                if ((e.getChangeFlags() & java.awt.event.HierarchyEvent.SHOWING_CHANGED) != 0) {
                    if (!isShowing()) {
                        performAutoSaveOnHide();
                    }
                }
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        panelTrungTam = new javax.swing.JPanel();
        panelThongTinKhachHang = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        scrTableKhachHang = new javax.swing.JScrollPane();
        tableThongTinKhachHang = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        btnDungDiem = new javax.swing.JButton();
        btnTaoTaiKhoan = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        panelThongTinHoaDon = new javax.swing.JPanel();
        lblMaHoaDon = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        lblMaNhanVien = new javax.swing.JLabel();
        lblMaBan = new javax.swing.JLabel();
        txtMaBan = new javax.swing.JTextField();
        txtTenNhanVien = new javax.swing.JTextField();
        lblTenKhachHang = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        txtNgayTao = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbKhuyenMai = new javax.swing.JComboBox<>();
        cbPTTT = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        scrTableThongTinHoaDon = new javax.swing.JScrollPane();
        tableThongTinHoaDon = new javax.swing.JTable();
        lblTongThanhToan = new javax.swing.JLabel();
        lblTongTienGiamGia = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        lblPhiDichVu = new javax.swing.JLabel();
        lblThue = new javax.swing.JLabel();
        lblTienDatCocDaTru = new javax.swing.JLabel();
        panelButton = new javax.swing.JPanel();
        btnInHoaDon = new javax.swing.JButton();
        btnTrangChu = new javax.swing.JButton();
        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelTrungTam.setBackground(new java.awt.Color(255, 251, 233));
        panelTrungTam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelThongTinKhachHang.setBackground(new java.awt.Color(255, 251, 233));
        panelThongTinKhachHang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelThongTinKhachHang.setPreferredSize(new java.awt.Dimension(560, 628));
        panelThongTinKhachHang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18));
        jLabel2.setText("Thông tin khách hàng");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 15, 0, 0));
        panelThongTinKhachHang.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 537, -1));
        jPanel4.setBackground(new java.awt.Color(255, 251, 233));
        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel5.setBackground(new java.awt.Color(255, 251, 233));
        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 15, 0, 15));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        jPanel5.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 0, 220, 27));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        jPanel5.add(btnTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, -1, -1));
        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, -1));
        jPanel6.setBackground(new java.awt.Color(255, 251, 233));
        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 15, 0, 15));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        tableThongTinKhachHang.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Tên khách hàng", "Điểm tích lũy", "Loại thành viên"
                }) {
            Class[] types = new Class[] {
                    java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        scrTableKhachHang.setViewportView(tableThongTinKhachHang);
        jPanel6.add(scrTableKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 550));
        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 37, 320, -1));
        panelThongTinKhachHang.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 36, 320, 590));
        jPanel7.setBackground(new java.awt.Color(255, 251, 233));
        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 15, 10, 15));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        btnDungDiem.setText("Dùng điểm");
        btnDungDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDungDiemActionPerformed(evt);
            }
        });
        jPanel7.add(btnDungDiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 90, 30));
        btnTaoTaiKhoan.setText("Tạo tài khoản");
        btnTaoTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoTaiKhoanActionPerformed(evt);
            }
        });
        jPanel7.add(btnTaoTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, 30));
        panelThongTinKhachHang.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, 320, 40));
        panelTrungTam.add(panelThongTinKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 680));
        jPanel2.setBackground(new java.awt.Color(255, 251, 233));
        jPanel2.setPreferredSize(new java.awt.Dimension(560, 628));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.setBackground(new java.awt.Color(255, 251, 233));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18));
        jLabel1.setText("Thông tin hóa đơn");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 15, 0, 0));
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 537, -1));
        jPanel8.setBackground(new java.awt.Color(255, 251, 233));
        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 15, 20, 15));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelThongTinHoaDon.setBackground(new java.awt.Color(255, 251, 233));
        lblMaHoaDon.setText("Mã hóa đơn:");
        lblNgayTao.setText("Ngày tạo:");
        txtMaHoaDon.setEditable(false);
        lblMaNhanVien.setText("Tên nhân viên:");
        lblMaBan.setText("Mã bàn:");
        txtMaBan.setEditable(false);
        txtTenNhanVien.setEditable(false);
        lblTenKhachHang.setText("Tên khách hàng:");
        txtTenKhachHang.setEditable(false);
        txtNgayTao.setEditable(false);
        jLabel3.setText("Khuyến mãi được áp dụng:");
        cbKhuyenMai.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbKhuyenMai.setEnabled(false);
        cbKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKhuyenMaiActionPerformed(evt);
            }
        });
        cbPTTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        ComboBoxEnumLoader.loadPTTTToComboBox(cbPTTT);
        jLabel4.setText("Phương thức thanh toán:");
        javax.swing.GroupLayout panelThongTinHoaDonLayout = new javax.swing.GroupLayout(panelThongTinHoaDon);
        panelThongTinHoaDon.setLayout(panelThongTinHoaDonLayout);
        panelThongTinHoaDonLayout.setHorizontalGroup(
                panelThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelThongTinHoaDonLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelThongTinHoaDonLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblMaHoaDon)
                                        .addComponent(lblNgayTao)
                                        .addComponent(lblMaBan))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelThongTinHoaDonLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtMaBan)
                                        .addComponent(txtMaHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 228,
                                                Short.MAX_VALUE)
                                        .addComponent(txtNgayTao))
                                .addGap(45, 45, 45)
                                .addGroup(panelThongTinHoaDonLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelThongTinHoaDonLayout.createSequentialGroup()
                                                .addComponent(lblMaNhanVien)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74,
                                                        Short.MAX_VALUE)
                                                .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelThongTinHoaDonLayout
                                                .createSequentialGroup()
                                                .addGroup(panelThongTinHoaDonLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblTenKhachHang)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel4))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(panelThongTinHoaDonLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(cbPTTT, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(panelThongTinHoaDonLayout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        false)
                                                                .addComponent(cbKhuyenMai, 0, 210, Short.MAX_VALUE)
                                                                .addComponent(txtTenKhachHang)))))
                                .addGap(6, 6, 6)));
        panelThongTinHoaDonLayout.setVerticalGroup(
                panelThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelThongTinHoaDonLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelThongTinHoaDonLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblMaHoaDon)
                                        .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblMaNhanVien)
                                        .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelThongTinHoaDonLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNgayTao)
                                        .addComponent(lblTenKhachHang)
                                        .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelThongTinHoaDonLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblMaBan)
                                        .addComponent(txtMaBan, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3)
                                        .addComponent(cbKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                                .addGroup(panelThongTinHoaDonLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cbPTTT, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))));
        jPanel8.add(panelThongTinHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 0, 720, 120));
        tableThongTinHoaDon.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Tên món", "Số lượng", "Đơn giá", "Thành tiền"
                }) {
            Class[] types = new Class[] {
                    java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        scrTableThongTinHoaDon.setViewportView(tableThongTinHoaDon);
        jPanel8.add(scrTableThongTinHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 710, 370));
        jPanel3.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 26, 760, 510));
        lblTongThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTongThanhToan.setForeground(new java.awt.Color(255, 51, 51));
        lblTongThanhToan.setText("Tổng tiền cần thanh toán: ");
        jPanel3.add(lblTongThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 650, -1, -1));
        lblTongTienGiamGia.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTongTienGiamGia.setText("Tổng tiền giảm giá (Tích lũy + Khuyến mãi): ");
        jPanel3.add(lblTongTienGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 570, -1, 40));
        lblTongTien.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTongTien.setText("Tổng tiền: ");
        jPanel3.add(lblTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 530, -1, -1));
        lblPhiDichVu.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblPhiDichVu.setText("Phí dịch vụ: ");
        jPanel3.add(lblPhiDichVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 550, -1, 30));
        lblThue.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblThue.setText("Thuế VAT: ");
        jPanel3.add(lblThue, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 600, -1, 30));
        lblTienDatCocDaTru.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTienDatCocDaTru.setText("Tiền đặt cọc đã trừ:");
        jPanel3.add(lblTienDatCocDaTru, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 620, 360, 30));
        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 680));
        panelTrungTam.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 0, 780, 680));
        panelButton.setBackground(new java.awt.Color(255, 251, 233));
        btnInHoaDon.setText("In hóa đơn");
        btnInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHoaDonActionPerformed(evt);
            }
        });
        btnTrangChu.setText("Quay lại");
        javax.swing.GroupLayout panelButtonLayout = new javax.swing.GroupLayout(panelButton);
        panelButton.setLayout(panelButtonLayout);
        panelButtonLayout.setHorizontalGroup(
                panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelButtonLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnTrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 105,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 948,
                                        Short.MAX_VALUE)
                                .addComponent(btnInHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 96,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)));
        panelButtonLayout.setVerticalGroup(
                panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelButtonLayout.createSequentialGroup()
                                .addComponent(btnTrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelButtonLayout.createSequentialGroup()
                                .addContainerGap(8, Short.MAX_VALUE)
                                .addComponent(btnInHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));
        panelTrungTam.add(panelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(-1, 690, 1170, -1));
        add(panelTrungTam, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, 740));
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTimKiemActionPerformed
        searchKhachHang();
    }// GEN-LAST:event_txtTimKiemActionPerformed

    private void btnDungDiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDungDiemActionPerformed
        if (selectedKhachHang == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng trước khi dùng điểm.");
            return;
        }
        double tongTien = tinhTongTienMonAn();
        double tienPhiDichVu = apDungPhiDichVu(MA_PHI_DICH_VU_MAC_DINH, tongTien);
        double tongSauPhiDichVu = tongTien + tienPhiDichVu;
        KhuyenMai km = getSelectedKhuyenMai();
        double giamKhuyenMai = 0;
        if (km != null) {
            giamKhuyenMai = Math.min(km.getGiaTriGiam(), tongSauPhiDichVu);
        }
        double coSoTinhVAT = Math.max(0, tongSauPhiDichVu - giamKhuyenMai);
        double tienThueVat = apDungThue(MA_THUE_VAT_MAC_DINH, coSoTinhVAT);
        double tongThanhToan = coSoTinhVAT + tienThueVat;
        int diemCanTru = (int) Math.ceil(tongThanhToan / VND_PER_DIEM);
        int diemHienCo = selectedKhachHang.getDiemTichLuy();
        if (diemHienCo == 0) {
            JOptionPane.showMessageDialog(this,
                    "Điểm tích lũy không đủ để sử dụng");
            return;
        }
        int diemConLai = Math.max(diemHienCo - diemCanTru, 0);
        boolean success = khachHangService.suDungDiemTichLuy(selectedKhachHang.getMaKH(), diemCanTru);
        if (!success) {
            JOptionPane.showMessageDialog(this, "Không thể sử dụng điểm tích lũy.");
            return;
        } else {
            JOptionPane.showMessageDialog(this, "Đã áp dụng điểm tích lũy cho hóa đơn.");
        }
        selectedKhachHang.setDiemTichLuy(diemConLai);
        diemDaApDung = (diemHienCo > diemCanTru) ? diemCanTru : diemHienCo;
        updateTongTienSummary();
        int row = tableThongTinKhachHang.getSelectedRow();
        if (row >= 0) {
            int modelRow = tableThongTinKhachHang.convertRowIndexToModel(row);
            if (modelRow >= 0 && modelRow < tableThongTinKhachHang.getRowCount()) {
                tableThongTinKhachHang.setValueAt(selectedKhachHang.getDiemTichLuy(), row, 1);
            }
        }
        JOptionPane.showMessageDialog(this, "Đã trừ " + diemDaApDung + " điểm tích lũy khỏi khách hàng.");
    }// GEN-LAST:event_btnDungDiemActionPerformed

    private void cbKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbKhuyenMaiActionPerformed
        if (loadingKhuyenMai) {
            return;
        }
        Object selected = cbKhuyenMai.getSelectedItem();
        if (selected != null) {
            cbKhuyenMai.setToolTipText(selected.toString());
        }
        updateTongTienSummary();
    }// GEN-LAST:event_cbKhuyenMaiActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        searchKhachHang();
    }// GEN-LAST:event_btnTimKiemActionPerformed

    private void performAutoSaveOnHide() {
        String context = HoaDonDraftSession.getCurrentMaBanContext();
        String pdbContext = HoaDonDraftSession.getCurrentMaPhieuDatContext();
        if (context != null && !context.isBlank() && tableThongTinHoaDon.getRowCount() > 0) {
            try {
                saveHoaDonDraftToSession();
                thucHienLuuHoaDon(TrangThaiHoaDon.CHUA_THANH_TOAN);
                System.out.println("Hệ thống: Tự động lưu hóa đơn nháp cho phiếu đặt bàn " + pdbContext);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void btnTaoTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTaoTaiKhoanActionPerformed
        Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentFrame instanceof MainForm) {
            ((MainForm) parentFrame).openPanelQuanLyKhachHang();
        }
    }// GEN-LAST:event_btnTaoTaiKhoanActionPerformed

    private void btnInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnInHoaDonActionPerformed
        if (tableThongTinHoaDon.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa có dữ liệu món ăn để in hóa đơn.");
            return;
        }
        String maBanContext = txtMaBan.getText().trim();
        if (maBanContext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn trước khi in hóa đơn.");
            return;
        }
        try {
            generatePdfInvoice();
            Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
            if (parentFrame instanceof MainForm) {
                ((MainForm) parentFrame).openPanelDatBan();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xử lý hóa đơn: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }// GEN-LAST:event_btnInHoaDonActionPerformed

    private void customUI() {
        setupPlaceholder(txtTimKiem, "Nhập số điện thoại khách hàng");
        btnTrangChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrangChuActionPerformed(evt);
            }
        });
        cbKhuyenMai.addItemListener(evt -> {
            if (!loadingKhuyenMai && evt.getStateChange() == ItemEvent.SELECTED) {
                updateTongTienSummary();
            }
        });
        tableThongTinKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tableThongTinKhachHang.getSelectedRow();
                if (row >= 0) {
                    int modelRow = tableThongTinKhachHang.convertRowIndexToModel(row);
                    if (modelRow >= 0 && modelRow < searchedKhachHang.size()) {
                        selectedKhachHang = searchedKhachHang.get(modelRow);
                        txtTenKhachHang
                                .setText(selectedKhachHang.getHoTen() != null ? selectedKhachHang.getHoTen() : "");
                        diemDaApDung = 0;
                        updateTongTienSummary();
                    }
                }
            }
        });
        centerTableColumns(tableThongTinKhachHang);
        centerTableColumns(tableThongTinHoaDon);
        DefaultTableCellRenderer currencyRenderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof Double) {
                    setText(com.restaurant.quanlydatbannhahang.util.CurrencyUtility.formatVND((Double) value));
                } else {
                    super.setValue(value);
                }
            }
        };
        currencyRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableThongTinHoaDon.getColumnModel().getColumn(2).setCellRenderer(currencyRenderer);
        tableThongTinHoaDon.getColumnModel().getColumn(3).setCellRenderer(currencyRenderer);
        initHoaDonHeader();
        updateTongTienSummary();
    }

    private void initHoaDonHeader() {
        txtNgayTao.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        NhanVien currentNhanVien = SessionManager.getCurrentNhanVien();
        txtTenNhanVien.setText(currentNhanVien != null ? currentNhanVien.getHoTen() : "");
        txtMaBan.setText(HoaDonDraftSession.getCurrentMaBanContext());
        String currentMaPhieuDatContext = HoaDonDraftSession.getCurrentMaPhieuDatContext();
        HoaDon hdExisting = null;
        if (currentMaPhieuDatContext != null && !currentMaPhieuDatContext.isBlank()) {
            try {
                hdExisting = hoaDonService.getHoaDonTheoMaPDB(currentMaPhieuDatContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (hdExisting != null) {
            txtMaHoaDon.setText(hdExisting.getMaHD());
            System.out.println("DEBUG: Phát hiện hóa đơn nháp tồn tại. Tái sử dụng mã HD: " + hdExisting.getMaHD());
        } else {
        try {
            String lastId = hoaDonService.getLastHoaDonID();
            String newId = (lastId == null || lastId.isEmpty()) ? IDGeneratorHelper.generateDefaultID("HD")
                    : IDGeneratorHelper.generateNextIDFromFullID(lastId);
            txtMaHoaDon.setText(newId != null ? newId : IDGeneratorHelper.generateDefaultID("HD"));
        } catch (Exception ex) {
            txtMaHoaDon.setText(IDGeneratorHelper.generateDefaultID("HD"));
        	}
        }
   }

    private void loadHoaDonDraft() {
        DefaultTableModel model = (DefaultTableModel) tableThongTinHoaDon.getModel();
        model.setRowCount(0);
        List<HoaDonDraftSession.DraftMonItem> items = HoaDonDraftSession
                .getMonItems(HoaDonDraftSession.getCurrentMaBanContext());
        for (HoaDonDraftSession.DraftMonItem item : items) {
            double thanhTien = item.getSoLuong() * item.getDonGia();
            model.addRow(new Object[] {
                    item.getTenMon(),
                    item.getSoLuong(),
                    item.getDonGia(),
                    thanhTien
            });
        }
        centerTableColumns(tableThongTinHoaDon);
        DefaultTableCellRenderer currencyRenderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof Double) {
                    setText(com.restaurant.quanlydatbannhahang.util.CurrencyUtility.formatVND((Double) value));
                } else {
                    super.setValue(value);
                }
            }
        };
        currencyRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableThongTinHoaDon.getColumnModel().getColumn(2).setCellRenderer(currencyRenderer);
        tableThongTinHoaDon.getColumnModel().getColumn(3).setCellRenderer(currencyRenderer);
        updateTongTienSummary();
    }

    public void refreshDraftData() {
        initHoaDonHeader();
        loadHoaDonDraft();
        loadKhuyenMaiToComboBox();
        selectedKhachHang = null;
        cbPTTT.setSelectedIndex(1);
        populateCustomerInfoFromReservation();
        updateTongTienSummary();
    }

    private void populateCustomerInfoFromReservation() {
        String maBanContext = HoaDonDraftSession.getCurrentMaBanContext();
        String maKH = HoaDonDraftSession.getMaKH(maBanContext);
        String soDienThoai = HoaDonDraftSession.getCurrentPhoneNumber();
        DefaultTableModel model = (DefaultTableModel) tableThongTinKhachHang.getModel();
        model.setRowCount(0);
        searchedKhachHang.clear();
        selectedKhachHang = null;
        diemDaApDung = HoaDonDraftSession.getDiemDung(maBanContext);
        if (maKH != null && !maKH.trim().isEmpty()) {
            try {
                KhachHang khachHang = khachHangService.getKhachHangTheoMa(maKH);
          
                if (khachHang != null) {
                    selectedKhachHang = khachHang;
                    searchedKhachHang.add(khachHang);
                    txtTenKhachHang.setText(khachHang.getHoTen() != null ? khachHang.getHoTen() : "");
                    txtTimKiem.setText(khachHang.getSdt() != null ? khachHang.getSdt() : "");
                    model.addRow(new Object[] {
                            khachHang.getHoTen(),
                            khachHang.getDiemTichLuy(),
                            khachHang.getLoaiThanhVien() != null ? khachHang.getLoaiThanhVien().getDisplayName() : ""
                    });
                } else {
                    txtTimKiem.setText(soDienThoai != null ? soDienThoai : "");
                    txtTenKhachHang.setText("Khách vãng lai");
                }
            } catch (Exception ex) {
                txtTimKiem.setText(soDienThoai != null ? soDienThoai : "");
                txtTenKhachHang.setText("Khách vãng lai");
            }
        } else if (soDienThoai != null && !soDienThoai.trim().isEmpty()) {
            String phone = soDienThoai.trim();
            txtTimKiem.setText(phone);
            try {
                KhachHang khachHang = khachHangService.getKhachHangTheoSDT(phone);
                if (khachHang != null) {
                    selectedKhachHang = khachHang;
                    searchedKhachHang.add(khachHang);
                    txtTenKhachHang.setText(khachHang.getHoTen() != null ? khachHang.getHoTen() : "");
                    model.addRow(new Object[] {
                            khachHang.getHoTen(),
                            khachHang.getDiemTichLuy(),
                            khachHang.getLoaiThanhVien() != null ? khachHang.getLoaiThanhVien().getDisplayName() : ""
                    });
                } else {
                    txtTenKhachHang.setText("Khách vãng lai");
                }
            } catch (Exception ex) {
                txtTenKhachHang.setText("Khách vãng lai");
            }
        } else {
            txtTimKiem.setText("");
            txtTenKhachHang.setText("Khách vãng lai");
        }
        String maKM = HoaDonDraftSession.getMaKM(maBanContext);
        restoreSelectedKhuyenMai(maKM);
        centerTableColumns(tableThongTinKhachHang);
    }

    private void loadKhachHangToTable() {
        try {
            allKhachHang = khachHangService.getAllKhachHang();
            DefaultTableModel model = (DefaultTableModel) tableThongTinKhachHang.getModel();
            model.setRowCount(0);
            searchedKhachHang.clear();
            centerTableColumns(tableThongTinKhachHang);
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi tải khách hàng: " + ex.getMessage());
        }
    }

    private void loadKhuyenMaiToComboBox() {
        loadingKhuyenMai = true;
        try {
            cbKhuyenMai.removeAllItems();
            khuyenMaiByDisplay.clear();
            khuyenMaiEligible.clear();
            cbKhuyenMai.addItem(NO_PROMO_DISPLAY);
            List<KhuyenMai> dsKhuyenMai = khuyenMaiService.getKhuyenMaiHoatDong();
            for (KhuyenMai km : dsKhuyenMai) {
                String display = km.getMaKM() + " - " + km.getTenKM();
                khuyenMaiByDisplay.put(display, km);
                khuyenMaiEligible.put(display, Boolean.FALSE);
                cbKhuyenMai.addItem(display);
            }
            cbKhuyenMai.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
                JLabel renderer = new JLabel();
                renderer.setOpaque(true);
                renderer.setText(value == null ? "" : value.toString());
                if (isSelected) {
                    renderer.setBackground(list.getSelectionBackground());
                    renderer.setForeground(list.getSelectionForeground());
                } else {
                    renderer.setBackground(list.getBackground());
                    Boolean eligible = khuyenMaiEligible.get(value);
                    renderer.setForeground(eligible != null && !eligible
                            ? new java.awt.Color(130, 130, 130)
                            : list.getForeground());
                }
                return renderer;
            });
        } catch (Exception ex) {
            cbKhuyenMai.removeAllItems();
            cbKhuyenMai.addItem(NO_PROMO_DISPLAY);
        } finally {
            loadingKhuyenMai = false;
            updateKhuyenMaiEligibility();
            updateTongTienSummary();
        }
    }

    private void updateKhuyenMaiEligibility() {
        double tongTien = tinhTongTienMonAn();
        double tienPhiDichVu = apDungPhiDichVu(MA_PHI_DICH_VU_MAC_DINH, tongTien);
        double tongCanhCuoc = Math.max(0, tongTien + tienPhiDichVu);
        String bestDisplay = NO_PROMO_DISPLAY;
        double bestDiscount = 0;
        for (Map.Entry<String, KhuyenMai> entry : khuyenMaiByDisplay.entrySet()) {
            String display = entry.getKey();
            KhuyenMai km = entry.getValue();
            boolean eligible = km != null && tongCanhCuoc >= km.getDieuKienToiThieu();
            khuyenMaiEligible.put(display, eligible);
            if (eligible && km.getGiaTriGiam() > bestDiscount) {
                bestDiscount = km.getGiaTriGiam();
                bestDisplay = display;
            }
        }
        Object selected = cbKhuyenMai.getSelectedItem();
        if (selected == null || !selected.equals(NO_PROMO_DISPLAY)) {
            Boolean currentEligible = selected == null ? null : khuyenMaiEligible.get(selected.toString());
            if (currentEligible == null || !currentEligible || !bestDisplay.equals(NO_PROMO_DISPLAY)) {
                cbKhuyenMai.setSelectedItem(bestDisplay);
            }
        } else if (!bestDisplay.equals(NO_PROMO_DISPLAY)) {
            cbKhuyenMai.setSelectedItem(bestDisplay);
        }
    }

    private KhuyenMai getSelectedKhuyenMai() {
        Object selected = cbKhuyenMai.getSelectedItem();
        if (selected == null || NO_PROMO_DISPLAY.equals(selected.toString())) {
            return null;
        }
        KhuyenMai km = khuyenMaiByDisplay.get(selected.toString());
        Boolean eligible = khuyenMaiEligible.get(selected.toString());
        return km != null && Boolean.TRUE.equals(eligible) ? km : null;
    }

    private void searchKhachHang() {
        allKhachHang = khachHangService.getAllKhachHang();
        String key = txtTimKiem.getText().trim().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) tableThongTinKhachHang.getModel();
        model.setRowCount(0);
        searchedKhachHang.clear();
        selectedKhachHang = null;
        txtTenKhachHang.setText("Khách vãng lai");
        if (key.isEmpty() || key.equals("nhập số điện thoại khách hàng")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        for (KhachHang kh : allKhachHang) {
            String sdt = kh.getSdt() != null ? kh.getSdt().toLowerCase() : "";
            if (sdt.contains(key)) {
                searchedKhachHang.add(kh);
                model.addRow(new Object[] {
                        kh.getHoTen(),
                        kh.getDiemTichLuy(),
                        kh.getLoaiThanhVien() != null ? kh.getLoaiThanhVien().getDisplayName() : ""
                });
            }
        }
        if (model.getRowCount() == 0) {
            txtTenKhachHang.setText("Khách vãng lai");
            javax.swing.JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng phù hợp.");
        } else {
            selectedKhachHang = searchedKhachHang.get(0);
            txtTenKhachHang.setText(selectedKhachHang.getHoTen() != null ? selectedKhachHang.getHoTen() : "");
            if (tableThongTinKhachHang.getRowCount() > 0) {
                tableThongTinKhachHang.setRowSelectionInterval(0, 0);
            }
        }
        centerTableColumns(tableThongTinKhachHang);
    }

    private void centerTableColumns(javax.swing.JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void generatePdfInvoice() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu PDF hóa đơn");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
        fileChooser.setSelectedFile(new File("HoaDon_" + txtMaHoaDon.getText().trim().replaceAll("\\W+", "") + ".pdf"));
        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File saveFile = fileChooser.getSelectedFile();
        if (!saveFile.getName().toLowerCase().endsWith(".pdf")) {
            saveFile = new File(saveFile.getParentFile(), saveFile.getName() + ".pdf");
        }
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDType0Font fontUnicode = PDType0Font.load(document, new File("fonts/Arial.ttf"));
            try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                content.beginText();
                content.setFont(fontUnicode, 20);
                content.newLineAtOffset(50, 780);
                content.showText("NHÀ HÀNG TRẦN LONG");
                content.endText();
                content.setFont(fontUnicode, 11);
                content.beginText();
                content.newLineAtOffset(50, 750);
                content.showText("Hóa đơn: " + txtMaHoaDon.getText());
                content.newLineAtOffset(0, -15);
                content.showText("Mã bàn: " + txtMaBan.getText());
                content.newLineAtOffset(0, -15);
                content.showText(
                        "Họ tên nhân viên: " + SessionManager.getCurrentNhanVien().getHoTen());
                content.newLineAtOffset(0, -15);
                content.showText("Họ tên khách hàng: " + txtTenKhachHang.getText());
                content.newLineAtOffset(0, -15);
                content.showText("Ngày tạo: " + txtNgayTao.getText());
                content.newLineAtOffset(0, -15);
                content.showText("Phương thức thanh toán: " + cbPTTT.getSelectedItem().toString());
                content.newLineAtOffset(0, -15);
                content.endText();
                float currentY = 620;
                content.setFont(fontUnicode, 12);
                content.beginText();
                content.newLineAtOffset(50, currentY);
                content.showText("Tên món");
                content.newLineAtOffset(250, 0);
                content.showText("SL");
                content.newLineAtOffset(50, 0);
                content.showText("Đơn giá");
                content.newLineAtOffset(100, 0);
                content.showText("Thành tiền");
                content.endText();
                content.setLineWidth(1f);
                content.moveTo(50, currentY - 5);
                content.lineTo(550, currentY - 5);
                content.stroke();
                currentY -= 25;
                content.setFont(fontUnicode, 10);
                DefaultTableModel model = (DefaultTableModel) tableThongTinHoaDon.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    content.beginText();
                    content.newLineAtOffset(50, currentY);
                    content.showText(String.valueOf(model.getValueAt(i, 0)));
                    content.newLineAtOffset(250, 0);
                    content.showText(String.valueOf(model.getValueAt(i, 1)));
                    content.newLineAtOffset(50, 0);
                    double donGia = (Double) model.getValueAt(i, 2);
                    content.showText(CurrencyUtility.formatVND(donGia));
                    content.newLineAtOffset(100, 0);
                    double thanhTien = (Double) model.getValueAt(i, 3);
                    content.showText(CurrencyUtility.formatVND(thanhTien));
                    content.endText();
                    currentY -= 20;
                }
                currentY -= 20;
                content.setLineWidth(1f);
                content.moveTo(50, currentY + 10);
                content.lineTo(550, currentY + 10);
                content.stroke();
                String rawTongMon = CurrencyUtility.formatVND(
                        CurrencyUtility.parseVND(lblTongTien.getText()));
                String rawPhiDV = CurrencyUtility
                        .formatVND(CurrencyUtility
                                .parseVND(lblPhiDichVu.getText()));
                String rawGiamGia = CurrencyUtility
                        .formatVND(CurrencyUtility
                                .parseVND(lblTongTienGiamGia.getText()));
                String rawThue = CurrencyUtility
                        .formatVND(CurrencyUtility
                                .parseVND(lblThue.getText()));
                String rawTienDatCoc = CurrencyUtility
                        .formatVND(CurrencyUtility
                                .parseVND(lblTienDatCocDaTru.getText()));
                String rawFinal = CurrencyUtility
                        .formatVND(CurrencyUtility
                                .parseVND(lblTongThanhToan.getText()));
                content.setFont(fontUnicode, 11);
                float labelX = 50;
                float valueX = 150;
                String[][] summary = {
                        { "Tổng tiền món:", rawTongMon },
                        { "Phí dịch vụ:", rawPhiDV },
                        { "Giảm giá:", rawGiamGia },
                        { "Thuế (VAT):", rawThue },
                        { "Tiền đặt cọc đã trừ:", rawTienDatCoc }
                };
                for (String[] line : summary) {
                    content.beginText();
                    content.newLineAtOffset(labelX, currentY);
                    content.showText(line[0]);
                    content.newLineAtOffset(valueX, 0);
                    content.showText(line[1]);
                    content.endText();
                    currentY -= 18;
                }
                content.setFont(fontUnicode, 13);
                content.beginText();
                content.newLineAtOffset(labelX, currentY - 10);
                content.showText("TỔNG CỘNG:");
                content.newLineAtOffset(valueX, 0);
                content.showText(rawFinal);
                content.endText();
            }
            document.save(saveFile);
        }
        JOptionPane.showMessageDialog(this, "Hóa đơn đã được lưu PDF tại: " + saveFile.getAbsolutePath());
        try {
            String currentMaBan = txtMaBan.getText().trim();
            String currentMaPhieuDat = HoaDonDraftSession.getCurrentMaPhieuDatContext();
            String normalizedMaBan = HoaDonDraftSession.normalizeMaBanContext(currentMaBan);
            if(selectedKhachHang != null) {
            	System.out.println("PanelLapHoaDon: "+ selectedKhachHang.getMaKH());
            	phieuDatBanService.capNhatKhachHangChoPhieu(currentMaPhieuDat, selectedKhachHang.getMaKH());
            }
            thucHienLuuHoaDon(TrangThaiHoaDon.DA_THANH_TOAN);
            capNhatTrangThaiBan(currentMaBan);
            
           
            if (!currentMaPhieuDat.isEmpty()) {
                phieuDatBanService.capNhatTrangThaiPhieu(currentMaPhieuDat, TrangThaiPhieuDat.DA_SU_DUNG);
            }
            congDiemTichLuyChoKhachHang();
            if (!normalizedMaBan.isEmpty()) {
                HoaDonDraftSession.clear(normalizedMaBan);
            }
            HoaDonDraftSession.setCurrentMaBanContext("");
            HoaDonDraftSession.clearCurrentMaPhieuDatContext();
            HoaDonDraftSession.clearCurrentPhoneNumber();
            refreshDraftData();
            java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
            if (parentFrame instanceof MainForm) {
                ((MainForm) parentFrame).openPanelDatBan();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi hoàn tất thanh toán: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void congDiemTichLuyChoKhachHang() {
        KhachHang kh = selectedKhachHang;
        if (kh == null) {
            String sdt = txtTimKiem.getText().trim();
            if (sdt.isBlank()) {
                return;
            }
            try {
                kh = khachHangService.getKhachHangTheoSDT(sdt);
            } catch (IllegalArgumentException ex) {
                return;
            }
        }
        HoaDon hoaDon = hoaDonService.findHoaDonTheoMa(txtMaHoaDon.getText().trim());
        if (hoaDon == null) {
            return;
        }
        int diemTang = (int) (hoaDon.getTongThanhToan() / VND_PER_DIEM);
        if (diemTang <= 0) {
            return;
        }
        try {
            khachHangService.capNhatDiemTichLuy(kh.getMaKH(), diemTang);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật điểm tích lũy: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void capNhatTrangThaiBan(String maBanContext) {
        if (maBanContext == null || maBanContext.isBlank()) {
            return;
        }
        try {
            String[] danhSachMaBan = maBanContext.split(",");
            for (String maBan : danhSachMaBan) {
                String maBanTrim = maBan.trim();
                if (!maBanTrim.isEmpty()) {
                    boolean hasReservationToday = phieuDatBanService.hasReservationToday(maBanTrim);
                    if (hasReservationToday) {
                        banService.capNhatTrangThaiBan(maBanTrim, TrangThaiBan.DA_DAT);
                    } else {
                        banService.capNhatTrangThaiBan(maBanTrim, TrangThaiBan.TRONG);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Không thể cập nhật trạng thái bàn sau khi thanh toán: " + e.getMessage(), e);
        }
    }

    private void thucHienLuuHoaDon(TrangThaiHoaDon trangThai) throws Exception {
        String maHD = txtMaHoaDon.getText().trim();
        String maBan = txtMaBan.getText().trim();
        if (maBan == null || maBan.isBlank()) {
            throw new IllegalArgumentException("Vui lòng chọn bàn trước khi lưu hóa đơn.");
        }
        String normalizedMaBan = HoaDonDraftSession.normalizeMaBanContext(maBan);
        if (normalizedMaBan.isEmpty()) {
            throw new IllegalArgumentException("Mã bàn không hợp lệ.");
        }
        NhanVien nhanVien = SessionManager.getCurrentNhanVien();
        KhuyenMai khuyenMai = getSelectedKhuyenMai();
        Thue thueVAT = thueService.getThueTheoMa(MA_THUE_VAT_MAC_DINH);
        Thue thuePhiDV = thueService.getThueTheoMa(MA_PHI_DICH_VU_MAC_DINH);
        double tongTien = tinhTongTienMonAn();
        double tienPhiDV = apDungPhiDichVu(MA_PHI_DICH_VU_MAC_DINH, tongTien);
        double tongSauPhiDV = tongTien + tienPhiDV;
        double giamKhuyenMai = khuyenMai != null ? Math.min(khuyenMai.getGiaTriGiam(), tongSauPhiDV) : 0;
        double giamDiem = (double) diemDaApDung * VND_PER_DIEM;
        double tongGiamGia = giamKhuyenMai + giamDiem;
        double coSoTinhVAT = Math.max(0, tongSauPhiDV - tongGiamGia);
        double tienThueVAT = apDungThue(MA_THUE_VAT_MAC_DINH, coSoTinhVAT);
        double tongThanhToan = coSoTinhVAT + tienThueVAT;
        if (HoaDonDraftSession.getCurrentMaBanContext() == null
                || HoaDonDraftSession.getCurrentMaBanContext().isBlank()) {
            System.out.println("DEBUG: Mã bàn context rỗng, bỏ qua lưu hóa đơn.");
            return;
        }
        String currentMaPhieuDatContext = HoaDonDraftSession.getCurrentMaPhieuDatContext();
        if (currentMaPhieuDatContext == null || currentMaPhieuDatContext.isBlank()) {
            System.out.println("DEBUG: Mã phiếu đặt context rỗng, chỉ lưu nháp vào RAM, không lưu vào Database.");
            return;
        }
        PhieuDatBan phieuDatBan = phieuDatBanService.getPhieuDatBanTheoMa(currentMaPhieuDatContext);
        if (phieuDatBan == null) {
            System.out.println("DEBUG: Không tìm thấy PhiếuDatBan với mã " + currentMaPhieuDatContext
                    + ", chỉ lưu nháp vào RAM, không lưu vào Database.");
            return;
        }
        
        LocalDateTime gioVaoCuaBan = HoaDonDraftSession.getGioVao(normalizedMaBan);
        LocalDateTime gioVao = (gioVaoCuaBan != null) ? gioVaoCuaBan : LocalDateTime.now();
        LocalDateTime gioRa = LocalDateTime.now();


        
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHD(maHD);
        hoaDon.setPhieuDatBan(phieuDatBan);
        hoaDon.setNhanVien(nhanVien);
        hoaDon.setKhuyenMai(khuyenMai);
        hoaDon.setThue(thueVAT);
        hoaDon.setThueSuat(thueVAT != null ? thueVAT.getThueSuat() : 0);
        hoaDon.setTienThue(tienThueVAT);
        hoaDon.setTyLePhiDV(thuePhiDV != null ? thuePhiDV.getThueSuat() : 0);
        hoaDon.setTienPhiDV(tienPhiDV);
        hoaDon.setNgayTao(java.time.LocalDate.now());
        hoaDon.setGioVao(gioVao.toLocalTime());
        hoaDon.setGioRa(trangThai == TrangThaiHoaDon.DA_THANH_TOAN ? gioRa.toLocalTime() : null);
        hoaDon.setTongTienGoc(tongTien);
        hoaDon.setTienGiamGia(tongGiamGia);
        hoaDon.setTongThanhToan(tongThanhToan);
        hoaDon.setTrangThaiThanhToan(trangThai);
        if (trangThai == TrangThaiHoaDon.DA_THANH_TOAN) {
            Object selectedItem = cbPTTT.getSelectedItem();
            if (selectedItem != null) {
                PhuongThucTT pttt = PhuongThucTT.fromDisplayName(selectedItem.toString());
                hoaDon.setPhuongThucTT(pttt);
            }
        }
        if (hoaDonService.findHoaDonTheoMa(maHD) != null) {
            hoaDonService.capNhatHoaDon(hoaDon);
        } else {
            hoaDonService.themHoaDon(hoaDon);
        }
        if (trangThai == TrangThaiHoaDon.DA_THANH_TOAN) {
            hoaDonService.capNhatTrangThaiHoaDon(maHD, TrangThaiHoaDon.DA_THANH_TOAN);
        }
        List<HoaDonDraftSession.DraftMonItem> draftItems = HoaDonDraftSession
                .getMonItems(HoaDonDraftSession.getCurrentMaBanContext());
        chiTietHoaDonService.xoaAllChiTietByMaHD(maHD);
        for (HoaDonDraftSession.DraftMonItem item : draftItems) {
            MonAn monAn = monAnService.getMonAnTheoMa(item.getMaMon());
            if (monAn != null) {
                ChiTietHoaDon chiTiet = new ChiTietHoaDon(hoaDon, monAn, item.getSoLuong(), item.getDonGia(), "");
                chiTietHoaDonService.themChiTietHoaDon(chiTiet);
            }
        }
    }

    private void saveHoaDonDraftToSession() {
        String maBanContext = HoaDonDraftSession.getCurrentMaBanContext();
        if (maBanContext == null || maBanContext.isBlank()) {
            return;
        }
        List<HoaDonDraftSession.DraftMonItem> draftItems = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tableThongTinHoaDon.getModel();
        for (int row = 0; row < model.getRowCount(); row++) {
            String tenMon = String.valueOf(model.getValueAt(row, 0));
            int soLuong = Integer.parseInt(String.valueOf(model.getValueAt(row, 1)));
            Object donGiaObj = model.getValueAt(row, 2);
            double donGia = 0;
            if (donGiaObj instanceof Number) {
                donGia = ((Number) donGiaObj).doubleValue();
            }
            String maMon = findMaMonByTenMon(tenMon);
            draftItems.add(new HoaDonDraftSession.DraftMonItem(maMon, tenMon, soLuong, donGia));
        }
        String soDienThoai = txtTimKiem.getText() != null ? txtTimKiem.getText().trim() : "";
        if (!soDienThoai.isEmpty() && !soDienThoai.equalsIgnoreCase("nhập số điện thoại khách hàng")) {
            HoaDonDraftSession.setCurrentPhoneNumber(soDienThoai);
        }
        String maKH = selectedKhachHang != null ? selectedKhachHang.getMaKH() : null;
        KhuyenMai selectedKM = getSelectedKhuyenMai();
        String maKM = selectedKM != null ? selectedKM.getMaKM() : null;
        int diemDung = diemDaApDung;
        HoaDonDraftSession.setCurrentMaBanContext(maBanContext);
        HoaDonDraftSession.setInvoiceMetadata(maBanContext, maKH, maKM, diemDung);
        if (draftItems.isEmpty()) {
            HoaDonDraftSession.clear(maBanContext);
        } else {
            HoaDonDraftSession.setMonItems(maBanContext, draftItems);
        }
    }

    private void restoreSelectedKhuyenMai(String maKM) {
        if (maKM == null || maKM.isBlank()) {
            cbKhuyenMai.setSelectedItem(NO_PROMO_DISPLAY);
            return;
        }
        for (Map.Entry<String, KhuyenMai> entry : khuyenMaiByDisplay.entrySet()) {
            KhuyenMai km = entry.getValue();
            if (km != null && maKM.equals(km.getMaKM())) {
                cbKhuyenMai.setSelectedItem(entry.getKey());
                return;
            }
        }
        cbKhuyenMai.setSelectedItem(NO_PROMO_DISPLAY);
    }

    private String findMaMonByTenMon(String tenMon) {
        if (tenMon == null || tenMon.isBlank()) {
            return "";
        }
        for (MonAn monAn : allMonAn) {
            if (monAn != null && tenMon.equals(monAn.getTenMon())) {
                return monAn.getMaMon();
            }
        }
        return "";
    }

    private double normalizeThueSuat(double thueSuatRaw) {
        if (thueSuatRaw <= 0) {
            return 0;
        }
        return thueSuatRaw > 1 ? thueSuatRaw / 100.0 : thueSuatRaw;
    }

    private double layTyLeThue(String maThue) {
        if (maThue == null || maThue.trim().isEmpty()) {
            return 0;
        }
        try {
            Thue thue = thueService.getThueTheoMa(maThue);
            return thue == null ? 0 : normalizeThueSuat(thue.getThueSuat());
        } catch (Exception ex) {
            return 0;
        }
    }

    private double apDungPhiDichVu(String maThueDichVu) {
        return layTyLeThue(maThueDichVu);
    }

    private double apDungPhiDichVu(String maThueDichVu, double tongTien) {
        double tyLePhiDV = apDungPhiDichVu(maThueDichVu);
        return Math.max(0, tongTien) * tyLePhiDV;
    }

    private double apDungThue(String maThue) {
        return layTyLeThue(maThue);
    }

    private double apDungThue(String maThue, double coSoTinhThue) {
        double tyLeThue = apDungThue(maThue);
        return Math.max(0, coSoTinhThue) * tyLeThue;
    }

    private double tinhTongTienMonAn() {
        double tong = 0;
        DefaultTableModel model = (DefaultTableModel) tableThongTinHoaDon.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            Object value = model.getValueAt(i, 3);
            if (value instanceof Number) {
                tong += ((Number) value).doubleValue();
            }
        }
        return tong;
    }

    private void updateTongTienSummary() {
        updateKhuyenMaiEligibility();
        double tongTien = tinhTongTienMonAn();
        double tienPhiDichVu = apDungPhiDichVu(MA_PHI_DICH_VU_MAC_DINH, tongTien);
        double tongSauPhiDichVu = tongTien + tienPhiDichVu;
        double giamKhuyenMai = 0;
        KhuyenMai km = getSelectedKhuyenMai();
        if (km != null) {
            giamKhuyenMai = Math.min(km.getGiaTriGiam(), tongSauPhiDichVu);
        }
        double giamDiem = Math.min((double) diemDaApDung * VND_PER_DIEM,
                Math.max(0, tongSauPhiDichVu - giamKhuyenMai));
        double tongGiamGia = giamKhuyenMai + giamDiem;
        double coSoTinhVAT = Math.max(0, tongSauPhiDichVu - tongGiamGia);
        double tienThueVat = apDungThue(MA_THUE_VAT_MAC_DINH, coSoTinhVAT);
        double tongThanhToan = coSoTinhVAT + tienThueVat;
        double tienDatCocDaTru = 0;
        String currentMaPhieuDat = HoaDonDraftSession.getCurrentMaPhieuDatContext();
        if (currentMaPhieuDat != null && !currentMaPhieuDat.isBlank()) {
            try {
                PhieuDatBan phieu = phieuDatBanService.getPhieuDatBanTheoMa(currentMaPhieuDat);
                if (phieu != null) {
                    tienDatCocDaTru = phieu.getTienDatCoc();
                    tongThanhToan = Math.max(0, tongThanhToan - tienDatCocDaTru);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        lblTongTien
                .setText("Tổng tiền: " + CurrencyUtility.formatVND(tongTien));
        lblPhiDichVu.setText(
                "Phí dịch vụ: " + CurrencyUtility.formatVND(tienPhiDichVu));
        lblTongTienGiamGia.setText("Tổng tiền giảm giá (Tích lũy + Khuyến mãi): "
                + CurrencyUtility.formatVND(tongGiamGia));
        lblThue.setText("Thuế VAT: " + CurrencyUtility.formatVND(tienThueVat));
        if (tienDatCocDaTru > 0) {
            lblTienDatCocDaTru.setText("Tiền đặt cọc đã trừ: "
                    + CurrencyUtility.formatVND(tienDatCocDaTru));
        } else {
            lblTienDatCocDaTru.setText("Tiền đặt cọc đã trừ: 0");
        }
        lblTongThanhToan.setText("Tổng tiền cần thanh toán: "
                + CurrencyUtility.formatVND(tongThanhToan));
    }

    private void setupPlaceholder(javax.swing.JTextField textField, String placeholder) {
        java.awt.Color placeholderColor = new java.awt.Color(153, 153, 153);
        java.awt.Color textColor = new java.awt.Color(0, 0, 0);
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

    private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentFrame instanceof MainForm) {
            ((MainForm) parentFrame).goBackToPanelDatMon();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDungDiem;
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnTaoTaiKhoan;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JComboBox<String> cbKhuyenMai;
    private javax.swing.JComboBox<String> cbPTTT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lblMaBan;
    private javax.swing.JLabel lblMaHoaDon;
    private javax.swing.JLabel lblMaNhanVien;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblPhiDichVu;
    private javax.swing.JLabel lblTenKhachHang;
    private javax.swing.JLabel lblThue;
    private javax.swing.JLabel lblTienDatCocDaTru;
    private javax.swing.JLabel lblTongThanhToan;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTongTienGiamGia;
    private javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelThongTinHoaDon;
    private javax.swing.JPanel panelThongTinKhachHang;
    private javax.swing.JPanel panelTrungTam;
    private javax.swing.JScrollPane scrTableKhachHang;
    private javax.swing.JScrollPane scrTableThongTinHoaDon;
    private javax.swing.JTable tableThongTinHoaDon;
    private javax.swing.JTable tableThongTinKhachHang;
    private javax.swing.JTextField txtMaBan;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTenNhanVien;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}