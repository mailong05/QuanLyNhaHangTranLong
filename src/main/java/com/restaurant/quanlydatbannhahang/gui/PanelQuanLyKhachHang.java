package com.restaurant.quanlydatbannhahang.gui;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.restaurant.quanlydatbannhahang.service.KhachHangService;
import com.restaurant.quanlydatbannhahang.util.ComboBoxEnumLoader;
import com.restaurant.quanlydatbannhahang.util.IDGeneratorHelper;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;
import com.restaurant.quanlydatbannhahang.entity.KhachHang;
import java.util.List;
public class PanelQuanLyKhachHang extends javax.swing.JPanel implements MouseListener {
    public PanelQuanLyKhachHang() {
        initComponents();
        customUI();
        loadDataToComboBoxes();
        loadDataToTable();
        fillMaKH();
    }
    private void customUI() {
        setBackground(new Color(255, 251, 233));
        JButton[] buttons = { btnTrangChu, btnThem, btnXoa, btnCapNhat, btnTimKiem };
        for (JButton btn : buttons) {
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        setupPlaceholder(txtTimKiem, "Nhập số điện thoại hoặc tên");
        scrTableKhachHang.setBorder(BorderFactory.createLineBorder(new Color(200, 190, 170), 1));
        scrTableKhachHang.setViewportBorder(null);
        tableKhachHang.setRowHeight(35);
        centerTableColumns(tableKhachHang);
        applyCardStyle(pnlThongTinKhachHang, 20);
        MainForm.attachGoHomeListener(btnTrangChu, this);
        tableKhachHang.addMouseListener(this);
        tableKhachHang.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tableKhachHang.getSelectedRow();
                if (row >= 0) {
                    loadDataFromRow(row);
                }
                syncButtonState();
            }
        });
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.getSource() != tableKhachHang && !isMouseOverTable(evt)) {
                    tableKhachHang.clearSelection();
                    refreshData();
                }
            }
        });
        syncButtonState();
    }
    private void loadDataToComboBoxes() {
        try {
            ActionListener[] loaiThanhVienListeners = cbFilterLoaiThanhVien.getActionListeners();
            for (ActionListener listener : loaiThanhVienListeners) {
                cbFilterLoaiThanhVien.removeActionListener(listener);
            }
            cbFilterLoaiThanhVien.removeAllItems();
            cbFilterLoaiThanhVien.addItem("Loại thành viên");
            ComboBoxEnumLoader.loadLoaiThanhVienToComboBox(cbFilterLoaiThanhVien);
            cbLoaiThanhVien.removeAllItems();
            ComboBoxEnumLoader.loadLoaiThanhVienToComboBox(cbLoaiThanhVien);
            cbLoaiThanhVien.setSelectedIndex(4);
            for (ActionListener listener : loaiThanhVienListeners) {
                cbFilterLoaiThanhVien.addActionListener(listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu filter: " + e.getMessage());
        }
    }
    private void loadDataToTable() {
        loadFilteredData();
    }
    private void loadFilteredData() {
        try {
            KhachHangService service = new KhachHangService();
            List<KhachHang> list = service.getAllKhachHang();
            String selectedLoaiThanhVien = (String) cbFilterLoaiThanhVien.getSelectedItem();
            DefaultTableModel model = (DefaultTableModel) tableKhachHang.getModel();
            model.setRowCount(0);
            for (KhachHang kh : list) {
                if (selectedLoaiThanhVien != null && !selectedLoaiThanhVien.equals("Loại thành viên")) {
                    if (kh.getLoaiThanhVien() == null
                            || !kh.getLoaiThanhVien().getDisplayName().equals(selectedLoaiThanhVien)) {
                        continue;
                    }
                }
                model.addRow(new Object[] {
                        kh.getMaKH(),
                        kh.getHoTen(),
                        kh.getSdt(),
                        kh.getDiemTichLuy(),
                        kh.getLoaiThanhVien().getDisplayName()
                });
            }
            centerTableColumns(tableKhachHang);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu: " + e.getMessage());
        }
    }
    private void searchByText() {
        try {
            KhachHangService service = new KhachHangService();
            List<KhachHang> list = service.getAllKhachHang();
            String searchText = txtTimKiem.getText().trim().toLowerCase();
            String selectedLoaiThanhVien = (String) cbFilterLoaiThanhVien.getSelectedItem();
            DefaultTableModel model = (DefaultTableModel) tableKhachHang.getModel();
            model.setRowCount(0);
            for (KhachHang kh : list) {
                if (selectedLoaiThanhVien != null && !selectedLoaiThanhVien.equals("Loại thành viên")) {
                    if (kh.getLoaiThanhVien() == null
                            || !kh.getLoaiThanhVien().getDisplayName().equals(selectedLoaiThanhVien)) {
                        continue;
                    }
                }
                if (!searchText.isEmpty()) {
                    String maKH = kh.getMaKH() != null ? kh.getMaKH().toLowerCase() : "";
                    String hoTen = kh.getHoTen() != null ? kh.getHoTen().toLowerCase() : "";
                    String sdt = kh.getSdt() != null ? kh.getSdt().toLowerCase() : "";
                    if (!maKH.contains(searchText) && !hoTen.contains(searchText) && !sdt.contains(searchText)) {
                        continue;
                    }
                }
                model.addRow(new Object[] {
                        kh.getMaKH(),
                        kh.getHoTen(),
                        kh.getSdt(),
                        kh.getDiemTichLuy(),
                        kh.getLoaiThanhVien().getDisplayName()
                });
            }
            centerTableColumns(tableKhachHang);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm dữ liệu: " + e.getMessage());
        }
    }
    private void loadDataFromRow(int rowIndex) {
        try {
            String maKH = (String) tableKhachHang.getValueAt(rowIndex, 0);
            String hoTen = (String) tableKhachHang.getValueAt(rowIndex, 1);
            String sdt = (String) tableKhachHang.getValueAt(rowIndex, 2);
            Object diemObj = tableKhachHang.getValueAt(rowIndex, 3);
            String loaiThanhVien = (String) tableKhachHang.getValueAt(rowIndex, 4);
            txtMaKhachHang.setText(maKH);
            txtHoTen.setText(hoTen);
            txtSoDienThoai.setText(sdt);
            if (diemObj != null) {
                txtDiemTichLuy.setText(diemObj.toString());
            } else {
                txtDiemTichLuy.setText("0");
            }
            cbLoaiThanhVien.setSelectedItem(loaiThanhVien);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi load dữ liệu từ row: " + e.getMessage());
        }
    }
    private void clearFields() {
        txtHoTen.setText("");
        txtSoDienThoai.setText("");
        txtDiemTichLuy.setText("0");
    }
    private void syncButtonState() {
        btnCapNhat.setEnabled(tableKhachHang.getSelectedRow() >= 0);
        btnXoa.setEnabled(tableKhachHang.getSelectedRow() >= 0);
        btnThem.setEnabled(tableKhachHang.getSelectedRow() == -1);
    }
    public void refreshData() {
        clearFields();
        resetPlaceholder(txtTimKiem, "Nhập số điện thoại hoặc tên");
        txtDiemTichLuy.setText("0");
        cbLoaiThanhVien.setSelectedIndex(4);
        cbFilterLoaiThanhVien.setSelectedIndex(0);
        fillMaKH();
        loadDataToComboBoxes();
        loadDataToTable();
        tableKhachHang.clearSelection();
        syncButtonState();
    }
    private void fillMaKH() {
    	 String lastID = IDQueryHelper.getLastID("KhachHang", "maKH");
         String maKHNew = (lastID == null || lastID.isEmpty()) ? IDGeneratorHelper.generateDefaultID("KH")
                 : IDGeneratorHelper.generateNextIDFromFullID(lastID);
         txtMaKhachHang.setText(maKHNew);
	}
	private void resetPlaceholder(JTextField textField, String placeholder) {
        Color placeholderColor = new Color(153, 153, 153);
        textField.setText(placeholder);
        textField.setForeground(placeholderColor);
    }
    private boolean isMouseOverTable(java.awt.event.MouseEvent evt) {
        java.awt.Point p = evt.getPoint();
        java.awt.Point tablePoint = SwingUtilities.convertPoint(this, p, tableKhachHang);
        return tableKhachHang.getBounds().contains(tablePoint);
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
    private void centerTableColumns(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlHeader2 = new javax.swing.JPanel();
        pnlThongTinKhachHang = new javax.swing.JPanel();
        lblMaKhachHang = new javax.swing.JLabel();
        txtMaKhachHang = new javax.swing.JTextField();
        lblHoTen = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        txtDiemTichLuy = new javax.swing.JTextField();
        lblDiemTichLuy = new javax.swing.JLabel();
        lblLoaiThanhVien = new javax.swing.JLabel();
        lblSoDienThoai = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        cbLoaiThanhVien = new javax.swing.JComboBox<>();
        cbFilterLoaiThanhVien = new javax.swing.JComboBox<>();
        lblTitle = new javax.swing.JLabel();
        scrTableKhachHang = new javax.swing.JScrollPane();
        tableKhachHang = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        pnlRightButtons = new javax.swing.JPanel();
        btnXoaTrang = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        setBackground(new java.awt.Color(255, 251, 233));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 60, 20, 60));
        setFocusable(false);
        setLayout(new java.awt.BorderLayout(0, 10));
        pnlHeader2.setOpaque(false);
        pnlHeader2.setLayout(new java.awt.BorderLayout(0, 15));
        pnlThongTinKhachHang.setBackground(new java.awt.Color(255, 251, 233));
        lblMaKhachHang.setText("Mã khách hàng:");
        lblMaKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtMaKhachHang.setEditable(false);
        txtMaKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtMaKhachHang.setFocusable(false);
        txtMaKhachHang.setPreferredSize(new java.awt.Dimension(64, 35));
        txtMaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKhachHangActionPerformed(evt);
            }
        });
        lblHoTen.setText("Họ tên:");
        lblHoTen.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtHoTen.setPreferredSize(new java.awt.Dimension(64, 35));
        txtDiemTichLuy.setEditable(false);
        txtDiemTichLuy.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtDiemTichLuy.setText("0");
        txtDiemTichLuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiemTichLuyActionPerformed(evt);
            }
        });
        lblDiemTichLuy.setText("Điểm tích lũy:");
        lblDiemTichLuy.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblLoaiThanhVien.setText("Loại thành viên:");
        lblLoaiThanhVien.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblSoDienThoai.setText("Số điện thoại:");
        lblSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtSoDienThoai.setPreferredSize(new java.awt.Dimension(64, 35));
        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtTimKiem.setPreferredSize(new java.awt.Dimension(64, 35));
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        cbLoaiThanhVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        cbLoaiThanhVien.setPreferredSize(new java.awt.Dimension(72, 35));
        cbFilterLoaiThanhVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        cbFilterLoaiThanhVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterLoaiThanhVienActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout pnlThongTinKhachHangLayout = new javax.swing.GroupLayout(pnlThongTinKhachHang);
        pnlThongTinKhachHang.setLayout(pnlThongTinKhachHangLayout);
        pnlThongTinKhachHangLayout.setHorizontalGroup(
            pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKhachHangLayout.createSequentialGroup()
                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlThongTinKhachHangLayout.createSequentialGroup()
                        .addComponent(lblSoDienThoai)
                        .addGap(18, 18, 18)
                        .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinKhachHangLayout.createSequentialGroup()
                        .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlThongTinKhachHangLayout.createSequentialGroup()
                                .addComponent(lblMaKhachHang)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlThongTinKhachHangLayout.createSequentialGroup()
                                .addComponent(lblHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(42, 42, 42)
                        .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDiemTichLuy)
                            .addComponent(lblLoaiThanhVien))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbLoaiThanhVien, 0, 109, Short.MAX_VALUE)
                            .addComponent(txtDiemTichLuy))))
                .addGap(0, 297, Short.MAX_VALUE))
            .addGroup(pnlThongTinKhachHangLayout.createSequentialGroup()
                .addComponent(cbFilterLoaiThanhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlThongTinKhachHangLayout.setVerticalGroup(
            pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKhachHangLayout.createSequentialGroup()
                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinKhachHangLayout.createSequentialGroup()
                        .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLoaiThanhVien)
                            .addComponent(cbLoaiThanhVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDiemTichLuy, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDiemTichLuy, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSoDienThoai)
                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbFilterLoaiThanhVien, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
        );
        pnlHeader2.add(pnlThongTinKhachHang, java.awt.BorderLayout.PAGE_END);
        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 18));
        lblTitle.setText("Quản lý thông tin khách hàng và tích điểm");
        pnlHeader2.add(lblTitle, java.awt.BorderLayout.CENTER);
        add(pnlHeader2, java.awt.BorderLayout.PAGE_START);
        tableKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Mã KH", "Họ tên", "Số điện thoại", "Điểm tích lũy", "Loại thành viên"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableKhachHang.setRowHeight(35);
        scrTableKhachHang.setViewportView(tableKhachHang);
        add(scrTableKhachHang, java.awt.BorderLayout.CENTER);
        pnlButton.setBackground(new java.awt.Color(255, 251, 233));
        pnlButton.setLayout(new java.awt.BorderLayout());
        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnTrangChu.setText("Trang Chủ");
        pnlButton.add(btnTrangChu, java.awt.BorderLayout.WEST);
        pnlRightButtons.setBackground(new java.awt.Color(255, 251, 233));
        pnlRightButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 15, 0));
        btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnXoaTrang);
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnCapNhat);
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnXoa);
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        pnlRightButtons.add(btnThem);
        pnlButton.add(pnlRightButtons, java.awt.BorderLayout.EAST);
        add(pnlButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents
    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {
        refreshData();
    }
    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
        searchByText();
    }
    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {
        String maKH = txtMaKhachHang.getText().trim();
        if (maKH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa.");
            return;
        }
        try {
            KhachHangService service = new KhachHangService();
            
            if (service.kiemTraKhachHangCoLichSuDat(maKH)) {
                JOptionPane.showMessageDialog(this, 
                    "Không thể xóa khách hàng này vì họ đã có lịch sử đặt bàn trong hệ thống!\n" +
                    "Hệ thống bắt buộc phải giữ lại thông tin để đối soát báo cáo doanh thu và điểm tích lũy.", 
                    "Lỗi ràng buộc dữ liệu", 
                    JOptionPane.ERROR_MESSAGE);
                return; 
            }
            
            int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa tài khoản khách hàng này khỏi hệ thống?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                service.xoaKhachHang(maKH);
                JOptionPane.showMessageDialog(this, "Xóa thông tin khách hàng thành công!");
                refreshData();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống khi xóa: " + e.getMessage());
        }
    }
    private void cbFilterLoaiThanhVienActionPerformed(java.awt.event.ActionEvent evt) {
        loadFilteredData();
    }
    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String maKH = txtMaKhachHang.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            String sdt = txtSoDienThoai.getText().trim();
            String diemText = txtDiemTichLuy.getText().trim();
            String loaiDisplay = (String) cbLoaiThanhVien.getSelectedItem();
            com.restaurant.quanlydatbannhahang.entity.LoaiThanhVien loaiThanhVien = ComboBoxEnumLoader
                    .getLoaiThanhVienFromDisplay(loaiDisplay);
            if (maKH.isEmpty() || hoTen.isEmpty() || sdt.isEmpty() || loaiThanhVien == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin khách hàng.");
                return;
            }
            int diemTichLuy = diemText.isEmpty() ? 0 : Integer.parseInt(diemText);
            KhachHang khachHang = new KhachHang(maKH, hoTen, sdt, diemTichLuy, loaiThanhVien);
            KhachHangService service = new KhachHangService();
            try {
            	service.capNhatKhachHang(khachHang);
				JOptionPane.showMessageDialog(this, "Cập nhật thành công khách hàng có mã "+ khachHang.getMaKH(),"Thông báo", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Lỗi cập nhật: "+ e.getMessage(),"Lỗi", JOptionPane.ERROR_MESSAGE);
			}
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Điểm tích lũy không hợp lệ.");
        }
    }
    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String maKH = txtMaKhachHang.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            String sdt = txtSoDienThoai.getText().trim();
            String loaiDisplay = (String) cbLoaiThanhVien.getSelectedItem();
            com.restaurant.quanlydatbannhahang.entity.LoaiThanhVien loaiThanhVien = ComboBoxEnumLoader
                    .getLoaiThanhVienFromDisplay(loaiDisplay);
            if (maKH.isEmpty() || hoTen.isEmpty() || sdt.isEmpty() || loaiThanhVien == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin khách hàng.");
                return;
            }
            KhachHang khachHang = new KhachHang(maKH, hoTen, sdt, 0, loaiThanhVien);
            KhachHangService service = new KhachHangService();
            if (service.themKhachHang(khachHang)) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công.");
                refreshData();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không thể thêm khách hàng: " + ex.getMessage());
        }
    }
    private void txtDiemTichLuyActionPerformed(java.awt.event.ActionEvent evt) {
    }
    private void txtMaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {
    }
    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
        searchByText();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cbFilterLoaiThanhVien;
    private javax.swing.JComboBox<String> cbLoaiThanhVien;
    private javax.swing.JLabel lblDiemTichLuy;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblLoaiThanhVien;
    private javax.swing.JLabel lblMaKhachHang;
    private javax.swing.JLabel lblSoDienThoai;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlHeader2;
    private javax.swing.JPanel pnlRightButtons;
    private javax.swing.JPanel pnlThongTinKhachHang;
    private javax.swing.JScrollPane scrTableKhachHang;
    private javax.swing.JTable tableKhachHang;
    private javax.swing.JTextField txtDiemTichLuy;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaKhachHang;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
}