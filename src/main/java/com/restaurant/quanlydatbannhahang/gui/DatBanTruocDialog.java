package com.restaurant.quanlydatbannhahang.gui;

import java.time.LocalDateTime;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.restaurant.quanlydatbannhahang.service.PhieuDatBanService;
import com.restaurant.quanlydatbannhahang.session.ReservationSession;
import com.restaurant.quanlydatbannhahang.util.CurrencyUtility;
import com.restaurant.quanlydatbannhahang.util.IDGeneratorHelper;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;

public class DatBanTruocDialog extends javax.swing.JDialog {
    private boolean datBanThanhCong = false;
    private IDGeneratorHelper helper;
    private PhieuDatBanService pdbService;
    private Set<String> selectedTables;
    private PanelDatBan panelDatBan;
    private PanelQuanLyDatBanTruoc panelQuanLyDatBanTruoc;

    public DatBanTruocDialog(java.awt.Frame parent, boolean modal, Set<String> selectedTables,
            PanelDatBan panelDatBan, PanelQuanLyDatBanTruoc panelQuanLyDatBanTruoc) {
        super(parent, modal);
        this.selectedTables = selectedTables;
        this.panelDatBan = panelDatBan;
        this.panelQuanLyDatBanTruoc = panelQuanLyDatBanTruoc;
        helper = new IDGeneratorHelper();
        pdbService = new PhieuDatBanService();
        initComponents();
        this.setLocationRelativeTo(parent);
        if (dtpThoiGianDen != null) {
            dtpThoiGianDen.setDateTimeStrict(LocalDateTime.now());
        }
        dtpThoiGianDen.setEnabled(false);
        fillMaPhieuDat(txtMaPhieuDat);
        fillThoiGianDen();
        updateTienDatCoc();
    }

    public boolean isDatBanThanhCong() {
        return datBanThanhCong;
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
        jLabel1 = new javax.swing.JLabel();
        lblMaPhieuDat = new javax.swing.JLabel();
        txtMaPhieuDat = new javax.swing.JTextField();
        txtSoDienThoai = new javax.swing.JTextField();
        lblSoDienThoai = new javax.swing.JLabel();
        lblThoiGianDen = new javax.swing.JLabel();
        dtpThoiGianDen = new com.github.lgooddatepicker.components.DateTimePicker();
        lblSoLuongNguoi = new javax.swing.JLabel();
        lblGhiChu = new javax.swing.JLabel();
        btnDatBan = new javax.swing.JButton();
        spSoLuong = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        txtTenKhachHang = new javax.swing.JTextField();
        lblTenKhachHang = new javax.swing.JLabel();
        lblTienDatCoc = new javax.swing.JLabel();
        txtTienDatCoc = new javax.swing.JTextField();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("THÔNG TIN ĐẶT BÀN TRƯỚC");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 5, -1, -1));
        lblMaPhieuDat.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblMaPhieuDat.setForeground(new java.awt.Color(0, 0, 0));
        lblMaPhieuDat.setText("Mã phiếu đặt:");
        jPanel2.add(lblMaPhieuDat, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, 20));
        txtMaPhieuDat.setEditable(false);
        txtMaPhieuDat.setBackground(new java.awt.Color(255, 255, 255));
        txtMaPhieuDat.setFocusable(false);
        txtMaPhieuDat.setForeground(new java.awt.Color(0, 0, 0));
        txtMaPhieuDat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaPhieuDatActionPerformed(evt);
            }
        });
        jPanel2.add(txtMaPhieuDat, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 230, -1));
        txtSoDienThoai.setBackground(new java.awt.Color(255, 255, 255));
        txtSoDienThoai.setForeground(new java.awt.Color(0, 0, 0));
        jPanel2.add(txtSoDienThoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 230, -1));
        lblSoDienThoai.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblSoDienThoai.setForeground(new java.awt.Color(0, 0, 0));
        lblSoDienThoai.setText("Số điện thoại:");
        jPanel2.add(lblSoDienThoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, 20));
        lblThoiGianDen.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblThoiGianDen.setForeground(new java.awt.Color(0, 0, 0));
        lblThoiGianDen.setText("Thời gian đến:");
        jPanel2.add(lblThoiGianDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, -1, -1));
        jPanel2.add(dtpThoiGianDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 230, -1));
        lblSoLuongNguoi.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblSoLuongNguoi.setForeground(new java.awt.Color(0, 0, 0));
        lblSoLuongNguoi.setText("Số lượng người:");
        jPanel2.add(lblSoLuongNguoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, -1));
        lblGhiChu.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblGhiChu.setForeground(new java.awt.Color(0, 0, 0));
        lblGhiChu.setText("Ghi chú:");
        jPanel2.add(lblGhiChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, -1, -1));
        btnDatBan.setText("Đặt bàn");
        btnDatBan.setBackground(new java.awt.Color(204, 204, 204));
        btnDatBan.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnDatBan.setForeground(new java.awt.Color(0, 0, 0));
        btnDatBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDatBanActionPerformed(evt);
            }
        });
        jPanel2.add(btnDatBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 380, 330, 40));
        jPanel2.add(spSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 230, -1));
        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        txtGhiChu.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(txtGhiChu);
        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, -1, -1));
        txtTenKhachHang.setBackground(new java.awt.Color(255, 255, 255));
        txtTenKhachHang.setForeground(new java.awt.Color(0, 0, 0));
        txtTenKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKhachHangActionPerformed(evt);
            }
        });
        jPanel2.add(txtTenKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 230, -1));
        lblTenKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblTenKhachHang.setForeground(new java.awt.Color(0, 0, 0));
        lblTenKhachHang.setText("Tên khách hàng:");
        jPanel2.add(lblTenKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, -1, -1));
        lblTienDatCoc.setText("Tiền đặt cọc: ");
        lblTienDatCoc.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblTienDatCoc.setForeground(new java.awt.Color(0, 0, 0));
        jPanel2.add(lblTienDatCoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, -1, -1));
        jPanel2.add(txtTienDatCoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 230, -1));
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 430));
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTenKhachHangActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void fillMaPhieuDat(JTextField txtMaKhuVuc) {
        String lastID = IDQueryHelper.getLastID("PhieuDatBan", "maPhieuDat");
        String maPDBNew = (lastID == null || lastID.isEmpty()) ? IDGeneratorHelper.generateDefaultID("PD")
                : IDGeneratorHelper.generateNextIDFromFullID(lastID);
        txtMaPhieuDat.setText(maPDBNew);
    }
    
    private void fillThoiGianDen() {
		dtpThoiGianDen.setDateTimeStrict(ReservationSession.getTempSelectedDateTime());
	}

    private void btnDatBanActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String maPDB = txtMaPhieuDat.getText().trim();
            String soDienThoai = txtSoDienThoai.getText().trim();
            String tenKhachHang = txtTenKhachHang.getText().trim();
            String ghiChu = txtGhiChu.getText().trim();
            int soLuongNguoi = (int) spSoLuong.getValue();
            LocalDateTime thoiGianDen = dtpThoiGianDen.getDateTimeStrict();
            java.util.Set<String> invalidTables = new java.util.HashSet<>();
            java.util.List<String> availableTables = pdbService.getDanhSachBanTrongTheoThoiGian(thoiGianDen);
            for (String maBan : selectedTables) {
                if (!availableTables.contains(maBan)) {
                    invalidTables.add(maBan);
                }
            }
            if (!invalidTables.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không thể đặt trước các bàn sau vào ngày đã chọn: "
                                + String.join(", ", invalidTables)
                                + "\nVui lòng chọn lại bàn khác hoặc ngày khác.",
                        "Xung đột đặt bàn",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String maPhieuDat = pdbService.themPhieuDatBan(maPDB, tenKhachHang, soDienThoai, soLuongNguoi,
                    thoiGianDen, ghiChu, selectedTables);
            if (panelDatBan != null) {
                panelDatBan.updateAllTableStatusFromDatabase();
            }
            if (panelQuanLyDatBanTruoc != null) {
                panelQuanLyDatBanTruoc.refreshData();
            }
            datBanThanhCong = true;
            JOptionPane.showMessageDialog(this,
                    "Đặt bàn thành công!\nMã phiếu: " + maPhieuDat, "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(), "Lỗi xác thực",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void txtMaPhieuDatActionPerformed(java.awt.event.ActionEvent evt) {
    }

    public static void main(String args[]) {
        UIConfiguration.setupUI();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                java.util.Set<String> testTables = new java.util.HashSet<>();
                testTables.add("B001");
                testTables.add("B002");
                DatBanTruocDialog dialog = new DatBanTruocDialog(new javax.swing.JFrame(), true, testTables, null,
                        null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDatBan;
    private com.github.lgooddatepicker.components.DateTimePicker dtpThoiGianDen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGhiChu;
    private javax.swing.JLabel lblMaPhieuDat;
    private javax.swing.JLabel lblSoDienThoai;
    private javax.swing.JLabel lblSoLuongNguoi;
    private javax.swing.JLabel lblTenKhachHang;
    private javax.swing.JLabel lblThoiGianDen;
    private javax.swing.JLabel lblTienDatCoc;
    private javax.swing.JSpinner spSoLuong;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaPhieuDat;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTienDatCoc;

    // End of variables declaration//GEN-END:variables
    private void updateTienDatCoc() {
        if (selectedTables != null && !selectedTables.isEmpty()) {
            double tienDatCoc = 100000.0 * selectedTables.size();
            txtTienDatCoc.setText(CurrencyUtility.formatVND(tienDatCoc));
            txtTienDatCoc.setEditable(false);
        } else {
            txtTienDatCoc.setText("0");
            txtTienDatCoc.setEditable(false);
        }
    }
}