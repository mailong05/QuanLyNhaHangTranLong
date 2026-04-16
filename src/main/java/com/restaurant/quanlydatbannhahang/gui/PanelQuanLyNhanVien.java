package com.restaurant.quanlydatbannhahang.gui;

import com.restaurant.quanlydatbannhahang.entity.ChucVu;
import com.restaurant.quanlydatbannhahang.entity.NhanVien;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiNhanVien;
import com.restaurant.quanlydatbannhahang.service.NhanVienService;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.DecimalFormat;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * QUẢN LÝ NHÂN VIÊN Chức năng: Thêm, Xóa, Cập nhật, Tìm kiếm và Lọc dữ liệu nhân viên.
 */
public class PanelQuanLyNhanVien extends javax.swing.JPanel {

    // --- KHAI BÁO BIẾN ---
    private final NhanVienService nhanVienService = new NhanVienService();
    private DefaultTableModel modelNhanVien;
    private final DecimalFormat df = new DecimalFormat("#,###");

    // --- KHỞI TẠO GIAO DIỆN ---
    public PanelQuanLyNhanVien() {
        initComponents();
        customUI();
        initEvents();
    }

    private void customUI() {
        modelNhanVien = (DefaultTableModel) tableKhuVuc.getModel();

        // Định dạng hiển thị tiền tệ cho cột Lương
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableKhuVuc.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);

        setupPlaceholder(txtTimKiem, "Nhập tên hoặc số điện thoại");

        // Định dạng dấu phẩy trực tiếp khi nhập lương
        txtLuongCoBan.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                format();
            }

            public void removeUpdate(DocumentEvent e) {
                format();
            }

            public void changedUpdate(DocumentEvent e) {
                format();
            }

            private void format() {
                SwingUtilities.invokeLater(() -> {
                    String text = txtLuongCoBan.getText().replace(",", "");
                    if (!text.isEmpty() && text.matches("\\d+")) {
                        String formatted = df.format(Double.parseDouble(text));
                        if (!txtLuongCoBan.getText().equals(formatted)) {
                            txtLuongCoBan.setText(formatted);
                        }
                    }
                });
            }
        });

        // Đổ dữ liệu từ Enum vào các ComboBox
        cbChucVu.removeAllItems();
        for (ChucVu cv : ChucVu.values()) {
            cbChucVu.addItem(cv.getDisplayName());
        }

        cbTrangThai.removeAllItems();
        for (TrangThaiNhanVien tt : TrangThaiNhanVien.values()) {
            cbTrangThai.addItem(tt.getDisplayName());
        }

        cbFilterChucVu.removeAllItems();
        cbFilterChucVu.addItem("Tất cả chức vụ");
        for (ChucVu cv : ChucVu.values()) {
            cbFilterChucVu.addItem(cv.getDisplayName());
        }

        if (dpNgayVaoLam != null) {
            dpNgayVaoLam.setDate(LocalDate.now());
        }
        loadTableData(nhanVienService.getAllNhanVien());
    }

    // --- QUẢN LÝ SỰ KIỆN HỆ THỐNG ---
    private void initEvents() {
        tableKhuVuc.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fillFormFromSelectedRow();
            }
        });
    }

    private void fillFormFromSelectedRow() {
        int row = tableKhuVuc.getSelectedRow();
        if (row != -1) {
            txtMaNhanVien.setText(modelNhanVien.getValueAt(row, 0).toString());
            txtHoTen.setText(modelNhanVien.getValueAt(row, 1).toString());
            txtSoDienThoai.setText(modelNhanVien.getValueAt(row, 2).toString());
            cbChucVu.setSelectedItem(modelNhanVien.getValueAt(row, 3).toString());

            Object ngayObj = modelNhanVien.getValueAt(row, 4);
            if (ngayObj instanceof LocalDate) {
                dpNgayVaoLam.setDate((LocalDate) ngayObj);
            }

            txtLuongCoBan.setText(modelNhanVien.getValueAt(row, 5).toString());
            cbTrangThai.setSelectedItem(modelNhanVien.getValueAt(row, 6).toString());

            txtMaNhanVien.setEditable(false);
            txtMaNhanVien.setEnabled(false);
        }
    }

    // --- XỬ LÝ NGHIỆP VỤ (CRUD & FILTER) ---
    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {
        NhanVien nv = layDuLieuTuForm();
        if (nv != null) {
            try {
                if (nhanVienService.themNhanVien(nv)) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    loadTableData(nhanVienService.getAllNhanVien());
                    xoaTrangForm();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {
        NhanVien nv = layDuLieuTuForm();
        if (nv != null) {
            try {
                if (nhanVienService.capNhatNhanVien(nv)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    loadTableData(nhanVienService.getAllNhanVien());
                    xoaTrangForm();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {
        int row = tableKhuVuc.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maNV = modelNhanVien.getValueAt(row, 0).toString();
        String trangThai = modelNhanVien.getValueAt(row, 6).toString();

        // Ràng buộc nghiệp vụ: Chỉ xóa khi đã nghỉ việc
        if (!trangThai.equals(TrangThaiNhanVien.DA_NGHI_VIEC.getDisplayName())) {
            JOptionPane.showMessageDialog(this,
                    "Chỉ có thể xóa nhân viên đã nghỉ việc.\nVui lòng cập nhật trạng thái nhân viên trước!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Xác nhận xóa vĩnh viễn
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "CẢNH BÁO: Xóa nhân viên sẽ mất toàn bộ dữ liệu vĩnh viễn!\nBạn có chắc chắn muốn xóa nhân viên " + maNV + " không?",
                "Xác nhận xóa dữ liệu",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (nhanVienService.xoaNhanVien(maNV)) {
                    JOptionPane.showMessageDialog(this, "Đã xóa thành công dữ liệu nhân viên!");
                    loadTableData(nhanVienService.getAllNhanVien());
                    xoaTrangForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
        String keyword = txtTimKiem.getText();
        if (keyword.equals("Nhập tên hoặc số điện thoại") || keyword.isEmpty()) {
            loadTableData(nhanVienService.getAllNhanVien());
        } else {
            loadTableData(nhanVienService.timKiemNhanVien(keyword));
        }
    }

    private void cbFilterChucVuActionPerformed(java.awt.event.ActionEvent evt) {
        String selected = (String) cbFilterChucVu.getSelectedItem();
        if (selected == null || selected.equals("Tất cả chức vụ")) {
            loadTableData(nhanVienService.getAllNhanVien());
        } else {
            for (ChucVu cv : ChucVu.values()) {
                if (cv.getDisplayName().equals(selected)) {
                    loadTableData(nhanVienService.filterTheoChucVu(cv.name()));
                    break;
                }
            }
        }
    }

    // --- HÀM HỖ TRỢ (HELPERS) ---
    private void loadTableData(List<NhanVien> list) {
        modelNhanVien.setRowCount(0);
        for (NhanVien nv : list) {
            modelNhanVien.addRow(new Object[]{
                nv.getMaNV(), nv.getHoTen(), nv.getSdt(),
                nv.getChucVu().getDisplayName(), nv.getNgayVaoLam(),
                df.format(nv.getLuongCoBan()), nv.getTrangThai().getDisplayName()
            });
        }
    }

    private NhanVien layDuLieuTuForm() {
        try {
            String ma = txtMaNhanVien.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            String sdt = txtSoDienThoai.getText().trim();

            if (ma.isEmpty() || hoTen.isEmpty() || sdt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return null;
            }

            String luongStr = txtLuongCoBan.getText().trim().replace(",", "");
            double luong = Double.parseDouble(luongStr);
            if (luong < 0) {
                JOptionPane.showMessageDialog(this, "Lương không được là số âm!");
                return null;
            }

            LocalDate ngayVao = dpNgayVaoLam.getDate() != null ? dpNgayVaoLam.getDate() : LocalDate.now();

            ChucVu cvSelected = ChucVu.PHUC_VU;
            String tenCV = (String) cbChucVu.getSelectedItem();
            for (ChucVu v : ChucVu.values()) {
                if (v.getDisplayName().equals(tenCV)) {
                    cvSelected = v;
                    break;
                }
            }

            TrangThaiNhanVien ttSelected = TrangThaiNhanVien.DANG_LAM_VIEC;
            String tenTT = (String) cbTrangThai.getSelectedItem();
            for (TrangThaiNhanVien t : TrangThaiNhanVien.values()) {
                if (t.getDisplayName().equals(tenTT)) {
                    ttSelected = t;
                    break;
                }
            }

            return new NhanVien(ma, hoTen, sdt, cvSelected, ngayVao, luong, ttSelected);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lương phải là số!");
            txtLuongCoBan.requestFocus();
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            return null;
        }
    }

    private void xoaTrangForm() {
        txtMaNhanVien.setText("");
        txtHoTen.setText("");
        txtSoDienThoai.setText("");
        txtLuongCoBan.setText("");
        dpNgayVaoLam.setDate(LocalDate.now());
        cbChucVu.setSelectedIndex(0);
        cbTrangThai.setSelectedIndex(0);
        txtMaNhanVien.setEditable(true);
        txtMaNhanVien.setEnabled(true);
        txtMaNhanVien.requestFocus();
    }

    private void setupPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(new Color(153, 153, 153));
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(new Color(153, 153, 153));
                }
            }
        });
    }

    private void applyCardStyle(JPanel panel, int radius) {
        panel.setOpaque(false);
        panel.setUI(new javax.swing.plaf.PanelUI() {
            @Override
            public void update(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), radius, radius);
                g2.dispose();
            }
        });
    }

    private void txtSoDienThoaiActionPerformed(java.awt.event.ActionEvent evt) {
        cbTrangThai.requestFocus();
    }

    private void txtMaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {
        txtHoTen.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        pnlThongTinNhanVien = new javax.swing.JPanel();
        lblMaNhanVien = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        lblHoTen = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        lblChucVu = new javax.swing.JLabel();
        lblNgayVaoLam = new javax.swing.JLabel();
        lblSoDienThoai = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        lblLuongCoBan = new javax.swing.JLabel();
        txtLuongCoBan = new javax.swing.JTextField();
        lblTrangThai = new javax.swing.JLabel();
        cbChucVu = new javax.swing.JComboBox<>();
        dpNgayVaoLam = new com.github.lgooddatepicker.components.DatePicker();
        cbTrangThai = new javax.swing.JComboBox<>();
        cbFilterChucVu = new javax.swing.JComboBox<>();
        scrTableKhuVuc = new javax.swing.JScrollPane();
        tableKhuVuc = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        pnlRightButtons = new javax.swing.JPanel();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setLayout(new java.awt.BorderLayout(0, 10));

        pnlHeader.setOpaque(false);
        pnlHeader.setLayout(new java.awt.BorderLayout(0, 15));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitle.setText("Quản lý thông tin nhân viên trong hệ thống");
        pnlHeader.add(lblTitle, java.awt.BorderLayout.WEST);

        pnlThongTinNhanVien.setBackground(new java.awt.Color(255, 251, 233));

        lblMaNhanVien.setText("Mã nhân viên:");
        lblMaNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtMaNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaNhanVien.setPreferredSize(new java.awt.Dimension(64, 30));
        txtMaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNhanVienActionPerformed(evt);
            }
        });

        lblHoTen.setText("Họ tên:");
        lblHoTen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtHoTen.setPreferredSize(new java.awt.Dimension(64, 30));

        lblChucVu.setText("Chức vụ:");
        lblChucVu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblNgayVaoLam.setText("Ngày vào làm:");
        lblNgayVaoLam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblSoDienThoai.setText("Số điện thoại:");
        lblSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoDienThoai.setPreferredSize(new java.awt.Dimension(64, 30));
        txtSoDienThoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoDienThoaiActionPerformed(evt);
            }
        });

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        lblLuongCoBan.setText("Lương cơ bản:");
        lblLuongCoBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtLuongCoBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblTrangThai.setText("Trạng thái:");
        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        cbChucVu.setPreferredSize(new java.awt.Dimension(72, 30));

        cbTrangThai.setMaximumSize(new java.awt.Dimension(32767, 30));
        cbTrangThai.setMinimumSize(new java.awt.Dimension(72, 30));
        cbTrangThai.setPreferredSize(new java.awt.Dimension(72, 30));

        cbFilterChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterChucVuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinNhanVienLayout = new javax.swing.GroupLayout(pnlThongTinNhanVien);
        pnlThongTinNhanVien.setLayout(pnlThongTinNhanVienLayout);
        pnlThongTinNhanVienLayout.setHorizontalGroup(
            pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                .addComponent(cbFilterChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                        .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSoDienThoai)
                            .addComponent(lblTrangThai))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                                .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(lblChucVu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                        .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaNhanVien)
                            .addComponent(lblHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(24, 24, 24)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                        .addComponent(lblLuongCoBan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtLuongCoBan, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                        .addComponent(lblNgayVaoLam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dpNgayVaoLam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(274, Short.MAX_VALUE))
        );
        pnlThongTinNhanVienLayout.setVerticalGroup(
            pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNgayVaoLam)
                    .addComponent(dpNgayVaoLam, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblLuongCoBan)
                        .addComponent(txtLuongCoBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSoDienThoai))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTrangThai)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFilterChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlHeader.add(pnlThongTinNhanVien, java.awt.BorderLayout.PAGE_END);

        add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        tableKhuVuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Họ tên", "Số điện thoại", "Chức vụ", "Ngày vào làm", "Lương cơ bản", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableKhuVuc.setRowHeight(35);
        scrTableKhuVuc.setViewportView(tableKhuVuc);

        add(scrTableKhuVuc, java.awt.BorderLayout.CENTER);

        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setLayout(new java.awt.BorderLayout());

        btnTrangChu.setText("Trang Chủ");
        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlButton.add(btnTrangChu, java.awt.BorderLayout.WEST);

        pnlRightButtons.setBackground(new java.awt.Color(255, 251, 233));
        pnlRightButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 15, 0));

        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnCapNhat);

        btnXoa.setText("Xóa");
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnXoa);

        btnThem.setText("Thêm");
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnThem);

        pnlButton.add(pnlRightButtons, java.awt.BorderLayout.EAST);

        add(pnlButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbChucVu;
    private javax.swing.JComboBox<String> cbFilterChucVu;
    private javax.swing.JComboBox<String> cbTrangThai;
    private com.github.lgooddatepicker.components.DatePicker dpNgayVaoLam;
    private javax.swing.JLabel lblChucVu;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblLuongCoBan;
    private javax.swing.JLabel lblMaNhanVien;
    private javax.swing.JLabel lblNgayVaoLam;
    private javax.swing.JLabel lblSoDienThoai;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlRightButtons;
    private javax.swing.JPanel pnlThongTinNhanVien;
    private javax.swing.JScrollPane scrTableKhuVuc;
    private javax.swing.JTable tableKhuVuc;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtLuongCoBan;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
