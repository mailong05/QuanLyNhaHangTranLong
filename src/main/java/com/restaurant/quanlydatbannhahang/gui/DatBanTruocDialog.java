package com.restaurant.quanlydatbannhahang.gui;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

import com.restaurant.quanlydatbannhahang.service.PhieuDatBanService;
import com.restaurant.quanlydatbannhahang.service.DatBanTruocService;

public class DatBanTruocDialog extends javax.swing.JDialog {

    /**
     * Creates new form TaiKhoanDialog
     */
    private boolean datBanThanhCong = false;

    public DatBanTruocDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent); // Hiển thị dialog ở giữa màn hình
    }

    public boolean isDatBanThanhCong() {
        return datBanThanhCong;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("THÔNG TIN ĐẶT BÀN TRƯỚC");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 5, -1, -1));

        lblMaPhieuDat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMaPhieuDat.setForeground(new java.awt.Color(0, 0, 0));
        lblMaPhieuDat.setText("Mã phiếu đặt:");
        jPanel2.add(lblMaPhieuDat, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, 20));

        txtMaPhieuDat.setEditable(false);
        txtMaPhieuDat.setBackground(new java.awt.Color(255, 255, 255));
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

        lblSoDienThoai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSoDienThoai.setForeground(new java.awt.Color(0, 0, 0));
        lblSoDienThoai.setText("Số điện thoại:");
        jPanel2.add(lblSoDienThoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, 20));

        lblThoiGianDen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblThoiGianDen.setForeground(new java.awt.Color(0, 0, 0));
        lblThoiGianDen.setText("Thời gian đến:");
        jPanel2.add(lblThoiGianDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, -1, -1));
        jPanel2.add(dtpThoiGianDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, -1, -1));

        lblSoLuongNguoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSoLuongNguoi.setForeground(new java.awt.Color(0, 0, 0));
        lblSoLuongNguoi.setText("Số lượng người:");
        jPanel2.add(lblSoLuongNguoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, -1));

        lblGhiChu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblGhiChu.setForeground(new java.awt.Color(0, 0, 0));
        lblGhiChu.setText("Ghi chú:");
        jPanel2.add(lblGhiChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, -1, -1));

        btnDatBan.setText("Đặt bàn");
        btnDatBan.setBackground(new java.awt.Color(204, 204, 204));
        btnDatBan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
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

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, -1, -1));

        txtTenKhachHang.setBackground(new java.awt.Color(255, 255, 255));
        txtTenKhachHang.setForeground(new java.awt.Color(0, 0, 0));
        txtTenKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKhachHangActionPerformed(evt);
            }
        });
        jPanel2.add(txtTenKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 230, -1));

        lblTenKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenKhachHang.setForeground(new java.awt.Color(0, 0, 0));
        lblTenKhachHang.setText("Tên khách hàng:");
        jPanel2.add(lblTenKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 430));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTenKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKhachHangActionPerformed

    private void btnDatBanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDatBanActionPerformed
        try {
            String soDienThoai = txtSoDienThoai.getText().trim();
            int soLuongNguoi = (int) spSoLuong.getValue();
            LocalDateTime thoiGianDen = dtpThoiGianDen.getDateTimePermissive();
            String ghiChu = txtGhiChu.getText().trim();

            // Gọi service để validate và lưu
            PhieuDatBanService service = new PhieuDatBanService();
            String maPhieuDat = service.taoPhieuDatBanMoi(soDienThoai, soLuongNguoi, thoiGianDen, ghiChu);

            datBanThanhCong = true;

            JOptionPane.showMessageDialog(this,
                    "Đặt bàn thành công!\nMã phiếu: " + maPhieuDat, "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

            // Đóng dialog này
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
    }// GEN-LAST:event_btnDatBanActionPerformed

    private void txtMaPhieuDatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMaPhieuDatActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtMaPhieuDatActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DatBanTruocDialog.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DatBanTruocDialog.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DatBanTruocDialog.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DatBanTruocDialog.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DatBanTruocDialog dialog = new DatBanTruocDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JSpinner spSoLuong;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaPhieuDat;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenKhachHang;
    // End of variables declaration//GEN-END:variables
}
