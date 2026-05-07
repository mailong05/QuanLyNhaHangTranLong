package com.restaurant.quanlydatbannhahang.gui;
import com.restaurant.quanlydatbannhahang.service.PhieuDatBanService;
import com.restaurant.quanlydatbannhahang.session.HoaDonDraftSession;
import com.restaurant.quanlydatbannhahang.session.SessionManager;
import java.util.Set;
import java.util.stream.Collectors;
public class LuaChonDatBanDialog extends javax.swing.JDialog {
    private Set<String> selectedTables;
    private PanelDatBan panelDatBan;
    private PanelQuanLyDatBanTruoc panelQuanLyDatBanTruoc;
    private PhieuDatBanService phieuDatBanService;
    public LuaChonDatBanDialog(java.awt.Frame parent, boolean modal, Set<String> selectedTables,
            PanelDatBan panelDatBan, PanelQuanLyDatBanTruoc panelQuanLyDatBanTruoc) {
        super(parent, modal);
        this.selectedTables = selectedTables;
        this.panelDatBan = panelDatBan;
        this.panelQuanLyDatBanTruoc = panelQuanLyDatBanTruoc;
        this.phieuDatBanService = new PhieuDatBanService();
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
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("VUI LÒNG CHỌN PHƯƠNG THỨC ĐẶT BÀN");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, 50));
        btnDatBanTruoc.setBackground(new java.awt.Color(204, 204, 204));
        btnDatBanTruoc.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnDatBanTruoc.setForeground(new java.awt.Color(0, 0, 0));
        btnDatBanTruoc.setText("ĐẶT BÀN TRƯỚC");
        btnDatBanTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDatBanTruocActionPerformed(evt);
            }
        });
        jPanel2.add(btnDatBanTruoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 180, 40));
        btnDatBanDungNgay.setBackground(new java.awt.Color(204, 204, 204));
        btnDatBanDungNgay.setFont(new java.awt.Font("Segoe UI", 1, 14));
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
        try {
            HoaDonDraftSession.clearCurrentPhoneNumber();
            HoaDonDraftSession.clearCurrentMaPhieuDatContext();
            Set<String> dsMaBan = selectedTables;
            String maNV = SessionManager.getCurrentNhanVien().getMaNV();
            String maPhieuMoi = phieuDatBanService.taoPhieuDatDungNgay(dsMaBan, maNV);
            System.out.println("Tạo thành công PDB " + maPhieuMoi);
            HoaDonDraftSession.setCurrentMaPhieuDatContext(maPhieuMoi);
            HoaDonDraftSession.setCurrentMaBanContext(String.join(", ", dsMaBan));
            this.dispose();
            java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
            if (parentFrame instanceof MainForm) {
                String maBanContext = selectedTables == null ? ""
                        : selectedTables.stream().sorted().collect(Collectors.joining(","));
                ((MainForm) parentFrame).openPanelDatMon(maBanContext);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// GEN-LAST:event_btnDatBanDungNgayActionPerformed
    private void btnDatBanTruocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDatBanTruocActionPerformed
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        DatBanTruocDialog dialog = new DatBanTruocDialog(parentFrame, true, selectedTables, panelDatBan,
                panelQuanLyDatBanTruoc);
        dialog.setVisible(true);
        if (dialog.isDatBanThanhCong()) {
            this.dispose();
        }
    }// GEN-LAST:event_btnDatBanTruocActionPerformed
    public static void main(String args[]) {
        UIConfiguration.setupUI();
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