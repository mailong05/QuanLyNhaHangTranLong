package com.restaurant.quanlydatbannhahang.gui;

// UIConfiguration để setup FlatLaf L&F

import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import com.restaurant.quanlydatbannhahang.service.BanService;
import com.restaurant.quanlydatbannhahang.session.HoaDonDraftSession;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

public class LuaChonDatBanDialog extends javax.swing.JDialog {

    /**
     * Creates new form TaiKhoanDialog
     */
    private Set<String> selectedTables; // ← Lưu các bàn đã chọn (không static)
    private PanelDatBan panelDatBan; // ← Lưu reference PanelDatBan để update UI
    private PanelQuanLyDatBanTruoc panelQuanLyDatBanTruoc; // ← Lưu reference để refresh data

    public LuaChonDatBanDialog(java.awt.Frame parent, boolean modal, Set<String> selectedTables,
            PanelDatBan panelDatBan, PanelQuanLyDatBanTruoc panelQuanLyDatBanTruoc) {
        super(parent, modal);
        this.selectedTables = selectedTables; // ← Nhận selectedTables từ PanelDatBan
        this.panelDatBan = panelDatBan; // ← Nhận PanelDatBan để update UI
        this.panelQuanLyDatBanTruoc = panelQuanLyDatBanTruoc; // ← Nhận PanelQuanLyDatBanTruoc
        initComponents();
        this.setLocationRelativeTo(parent);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnDatBanTruoc = new javax.swing.JButton();
        btnDatBanDungNgay = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("VUI LÒNG CHỌN PHƯƠNG THỨC ĐẶT BÀN");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, 50));

        btnDatBanTruoc.setBackground(new java.awt.Color(204, 204, 204));
        btnDatBanTruoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDatBanTruoc.setForeground(new java.awt.Color(0, 0, 0));
        btnDatBanTruoc.setText("ĐẶT BÀN TRƯỚC");
        btnDatBanTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDatBanTruocActionPerformed(evt);
            }
        });
        jPanel2.add(btnDatBanTruoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 180, 40));

        btnDatBanDungNgay.setBackground(new java.awt.Color(204, 204, 204));
        btnDatBanDungNgay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDatBanDungNgay.setForeground(new java.awt.Color(0, 0, 0));
        btnDatBanDungNgay.setText("ĐẶT BÀN DÙNG NGAY");
        btnDatBanDungNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDatBanDungNgayActionPerformed(evt);
            }
        });
        jPanel2.add(btnDatBanDungNgay, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 90, 190, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 200));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDatBanDungNgayActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDatBanDungNgayActionPerformed
        HoaDonDraftSession.clearCurrentPhoneNumber();
        HoaDonDraftSession.clearCurrentMaPhieuDatContext();

        if (selectedTables != null && !selectedTables.isEmpty()) {
            BanService banService = new BanService();
            for (String maBan : selectedTables) {
                try {
                    banService.capNhatTrangThaiBan(maBan, TrangThaiBan.DANG_DUNG);
                    if (panelDatBan != null) {
                        panelDatBan.updateBanStatusUI(maBan, TrangThaiBan.DANG_DUNG);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể cập nhật trạng thái bàn " + maBan + ": " + e.getMessage(), "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        this.dispose();
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentFrame instanceof MainForm) {
            String maBanContext = selectedTables == null ? ""
                    : selectedTables.stream().sorted().collect(Collectors.joining(","));
            ((MainForm) parentFrame).openPanelDatMon(maBanContext);
        }

    }// GEN-LAST:event_btnDatBanDungNgayActionPerformed

    private void btnDatBanTruocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDatBanTruocActionPerformed
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        DatBanTruocDialog dialog = new DatBanTruocDialog(parentFrame, true, selectedTables, panelDatBan,
                panelQuanLyDatBanTruoc);
        dialog.setVisible(true);

        // Sau khi dialog đóng, kiểm tra xem đặt bàn có thành công không
        if (dialog.isDatBanThanhCong()) {
            // Nếu thành công, đóng luôn dialog lựa chọn
            this.dispose();
        }
        // Nếu không thành công (người dùng bấn X), vẫn giữ dialog lựa chọn mở
    }// GEN-LAST:event_btnDatBanTruocActionPerformed

    /**
     * @param args the command line arguments
     */
    /**
     * @param args the command line arguments
     * 
     *             NOTE: Main method chỉ dùng cho testing. Trong thực tế, dialog
     *             được gọi từ PanelDatBan
     */
    public static void main(String args[]) {
        // ========== SETUP UI (FlatLaf) TRƯỚC TIÊN ==========
        UIConfiguration.setupUI();

        // ========== SAU ĐÓ MỚI KHỞI TẠO DIALOG ==========
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                java.util.Set<String> testTables = new java.util.HashSet<>();
                testTables.add("B001");
                testTables.add("B002");

                LuaChonDatBanDialog dialog = new LuaChonDatBanDialog(new javax.swing.JFrame(), true, testTables, null,
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
    private javax.swing.JButton btnDatBanDungNgay;
    private javax.swing.JButton btnDatBanTruoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
