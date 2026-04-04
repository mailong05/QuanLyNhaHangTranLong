USE QuanLyDatBan;
GO

-- =========================================================
-- 2. CHÈN DỮ LIỆU MẪU (SEED DATA)
-- =========================================================

-- 2.1. BẢNG KHU VỰC (6 Khu vực)
INSERT INTO KhuVuc (maKhuVuc, tenKhuVuc) VALUES 
('KV001', N'Tầng 1 - Sảnh chính'),
('KV002', N'Tầng 2 - Phòng VIP'),
('KV003', N'Tầng 3 - Sân thượng'),
('KV004', N'Sân vườn ngoài trời'),
('KV005', N'Khu vực Quầy Bar'),
('KV006', N'Hầm rượu vang');

-- 2.2. BẢNG KHUYẾN MÃI (Thêm cột trangThai: CON_AP_DUNG/NGUNG_AP_DUNG)
INSERT INTO KhuyenMai (maKM, tenKM, giaTriGiam, ngayBatDau, ngayKetThuc, dieuKienToiThieu, trangThai) VALUES 
('KM001', N'Ưu đãi khai xuân', 50000, '2026-01-01', '2026-02-28', 300000, N'CON_AP_DUNG'),
('KM002', N'Tiệc sinh nhật tháng 4', 100000, '2026-04-01', '2026-04-30', 1000000, N'CON_AP_DUNG'),
('KM003', N'Mừng ngày lễ 30/4', 80000, '2026-04-25', '2026-05-05', 500000, N'CON_AP_DUNG'),
('KM004', N'Happy Hour giảm giá trưa', 30000, '2026-01-01', '2026-12-31', 150000, N'CON_AP_DUNG'),
('KM005', N'Ưu đãi nhóm bạn (>5 người)', 150000, '2026-06-01', '2026-06-30', 1500000, N'NGUNG_AP_DUNG'),
('KM006', N'Giảm giá thành viên VIP', 200000, '2026-01-01', '2026-12-31', 2000000, N'CON_AP_DUNG');

-- 2.3. BẢNG THUẾ (Thêm cột trangThai: CON_AP_DUNG/NGUNG_AP_DUNG)
INSERT INTO Thue (maThue, tenThue, thueSuat, trangThai) VALUES 
('TH001', N'VAT 8%', 0.08, N'CON_AP_DUNG'),
('TH002', N'VAT 10%', 0.1, N'CON_AP_DUNG'),
('TH003', N'Phí phục vụ 5%', 0.05, N'CON_AP_DUNG'),
('TH004', N'Thuế đồ uống có cồn', 0.15, N'CON_AP_DUNG');

-- 2.4. BẢNG KHÁCH HÀNG
-- Cập nhật dữ liệu mẫu KhachHang khớp với CHECK constraint
INSERT INTO KhachHang (maKH, hoTen, sdt, diemTichLuy, loaiThanhVien) VALUES 
('KH001', N'Nguyễn Hoàng Nam', '0901234567', 120, N'BAC'),  
('KH002', N'Lê Thị Tuyết Mai', '0912345678', 550, N'VANG'), 
('KH003', N'Trần Minh Tâm', '0987654321', 25, N'DONG'),     
('KH004', N'Phạm Thu Hà', '0933444555', 1200, N'VIP'),      
('KH005', N'Đặng Văn Hùng', '0966777888', 300, N'BAC'),
('KH006', N'Vũ Bích Ngọc', '0944555666', 800, N'VANG');


-- 2.5. BẢNG NHÂN VIÊN
INSERT INTO NhanVien (maNV, hoTen, sdt, chucVu, ngayVaoLam, luongCoBan, trangThai) VALUES 
('NV001', N'Trịnh Xuân Hùng', '0981112223', N'QUAN_LY', '2024-01-01', 25000000, N'DANG_LAM_VIEC'),
('NV002', N'Lý Mỹ Lan', '0984445556', N'THU_NGAN', '2025-03-15', 9500000, N'DANG_LAM_VIEC'),
('NV003', N'Vũ Đình Phục', '0987778889', N'PHUC_VU', '2025-06-20', 7000000, N'DANG_LAM_VIEC'),
('NV004', N'Phạm Văn Chiến', '0980009991', N'BEP', '2024-12-10', 18000000, N'DANG_LAM_VIEC'),
('NV005', N'Hoàng Thu Ngân', '0982223334', N'THU_NGAN', '2025-07-01', 9000000, N'DANG_LAM_VIEC'),
('NV006', N'Nguyễn Văn Chiến', '0985556667', N'PHUC_VU', '2025-08-15', 7000000, N'DANG_LAM_VIEC'),
('NV007', N'Trần Văn Nam', '0989990001', N'BEP', '2025-01-20', 15000000, N'DANG_LAM_VIEC'),
('NV008', N'Đỗ Hùng Dũng', '0981234567', N'QUAN_LY', '2024-05-05', 20000000, N'DANG_LAM_VIEC');

-- 2.6. BẢNG TÀI KHOẢN
INSERT INTO TaiKhoan (username, password, maNV, quyenHan) VALUES 
('admin_hung', '123456', 'NV001', 'MANAGER'),
('thungan_lan', '888888', 'NV002', 'STAFF'),
('phucvu_dinh', '123456', 'NV003', 'STAFF'),
('thungan_hoang', '343242', 'NV005', 'STAFF'),
('phucvu_nguyen', '123456', 'NV006', 'STAFF'),
('admin_do', '123456', 'NV008', 'MANAGER');

-- 2.7. BẢNG MÓN ĂN (30 Món ăn)
INSERT INTO MonAn (maMon, tenMon, donGia, donViTinh, tenLoai, trangThai, urlHinhAnh) VALUES 
('MA001', N'Salad cá ngừ', 85000, N'Dĩa', N'KHAI_VI', N'CON', 'salad_ca_ngu.jpg'),
('MA002', N'Súp cua', 65000, N'Thố', N'KHAI_VI', N'CON', 'sup_cua.jpg'),
('MA003', N'Gỏi ngó sen tôm thịt', 95000, N'Dĩa', N'KHAI_VI', N'CON', 'goi_ngo_sen.jpg'),
('MA004', N'Chả giò hải sản', 110000, N'Phần', N'KHAI_VI', N'CON', 'cha_gio.jpg'),
('MA005', N'Khoai tây chiên', 45000, N'Dĩa', N'KHAI_VI', N'CON', 'khoai_tay_chien.jpg'),
('MA006', N'Bò bít tết Úc', 250000, N'Phần', N'MON_CHINH', N'CON', 'bo_bit_tet.jpg'),
('MA007', N'Bò lúc lắc', 180000, N'Dĩa', N'MON_CHINH', N'CON', 'bo_luc_lac.jpg'),
('MA008', N'Sườn cừu nướng BBQ', 320000, N'Phần', N'MON_CHINH', N'CON', 'suon_cuu.jpg'),
('MA009', N'Bò né phô mai', 150000, N'Phần', N'MON_CHINH', N'CON', 'bo_ne.jpg'),
('MA010', N'Bò xào hành tây', 120000, N'Dĩa', N'MON_CHINH', N'CON', 'bo_xao.jpg'),
('MA016', N'Gà quay chảo', 220000, N'Nửa con', N'MON_CHINH', N'CON', 'ga_quay.jpg'),
('MA017', N'Sườn heo chua ngọt', 140000, N'Dĩa', N'MON_CHINH', N'CON', 'suon_chua_ngot.jpg'),
('MA018', N'Heo quay giòn da', 180000, N'Dĩa', N'MON_CHINH', N'CON', 'heo_quay.jpg'),
('MA019', N'Cánh gà chiên mắm', 110000, N'Phần', N'MON_CHINH', N'CON', 'canh_ga.jpg'),
('MA020', N'Gà ủ muối', 250000, N'Con', N'MON_CHINH', N'CON', 'ga_u_muoi.jpg'),
('MA021', N'Cơm chiên hải sản', 120000, N'Dĩa', N'MON_CHINH', N'CON', 'com_chien_hs.jpg'),
('MA022', N'Cơm chiên dương châu', 100000, N'Dĩa', N'MON_CHINH', N'CON', 'com_chien_dc.jpg'),
('MA023', N'Mì xào giòn', 110000, N'Dĩa', N'MON_CHINH', N'CON', 'mi_xao_gion.jpg'),
('MA024', N'Mì Ý sốt bò băm', 130000, N'Phần', N'MON_CHINH', N'CON', 'mi_y.jpg'),
('MA011', N'Tôm hùm phô mai', 850000, N'Con', N'HAI_SAN', N'CON', 'tom_hum.jpg'),
('MA012', N'Mực hấp gừng', 160000, N'Dĩa', N'HAI_SAN', N'CON', 'muc_hap.jpg'),
('MA013', N'Cua rang me', 450000, N'Con', N'HAI_SAN', N'CON', 'cua_rang_me.jpg'),
('MA014', N'Hàu nướng mỡ hành', 120000, N'Phần', N'HAI_SAN', N'CON', 'hau_nuong.jpg'),
('MA015', N'Nghêu hấp xả', 90000, N'Phần', N'HAI_SAN', N'CON', 'ngheu_hap.jpg'),
('MA025', N'Miến xào cua', 160000, N'Dĩa', N'HAI_SAN', N'CON', 'mien_xao.jpg'),
('MA026', N'Nước ép cam', 45000, N'Ly', N'DO_UONG', N'CON', 'nuoc_cam.jpg'),
('MA027', N'Sinh tố dâu', 55000, N'Ly', N'DO_UONG', N'CON', 'sinh_to_dau.jpg'),
('MA028', N'Bia Heineken', 35000, N'Lon', N'DO_UONG', N'CON', 'bia_heineken.jpg'),
('MA029', N'Trà đá', 5000, N'Ly', N'DO_UONG', N'CON', 'tra_da.jpg'),
('MA030', N'Bánh Flan', 25000, N'Phần', N'TRANG_MIENG', N'CON', 'banh_flan.jpg');



-- 2.8. BẢNG BÀN ĂN
INSERT INTO BanAn (maBan, soGhe, viTri, maKhuVuc, trangThai) VALUES 
('B001', 4, N'Cạnh cửa sổ', 'KV001', N'TRONG'),
('B002', 2, N'Góc sảnh', 'KV001', N'DANG_DUNG'),
('B003', 10, N'Phòng Đào', 'KV002', N'DA_DAT'),
('B004', 6, N'Ban công trái', 'KV003', N'TRONG'),
('B005', 8, N'Gần hồ cá', 'KV004', N'TRONG'),
('B006', 4, N'Quầy Bar 1', 'KV005', N'DANG_DUNG'),
('B007', 12, N'Phòng Trúc lớn', 'KV002', N'DA_DAT'),
('B008', 2, N'Gầm cầu thang', 'KV001', N'TRONG');

-- 2.9. PHIẾU ĐẶT BÀN
INSERT INTO PhieuDatBan (maPhieuDat, maKH, maNV, thoiGianDen, soLuongNguoi, ghiChu, trangThai) VALUES 
('PD001', 'KH001', 'NV003', '2026-04-04 19:00:00', 4, N'Sinh nhật', N'DA_XAC_NHAN'),
('PD002', 'KH002', 'NV002', '2026-04-05 18:30:00', 10, N'Tiệc công ty', N'CHO_XAC_NHAN'),
('PD003', 'KH004', 'NV003', '2026-04-10 20:00:00', 2, N'Kỷ niệm ngày cưới', N'DA_XAC_NHAN'),
('PD004', 'KH005', 'NV002', '2026-04-12 11:30:00', 6, N'Ăn trưa gia đình', N'DA_XAC_NHAN');

-- 2.10. CHI TIẾT PHIẾU ĐẶT BÀN
INSERT INTO ChiTietPhieuDatBan (maPhieuDat, maBan, ghiChu) VALUES 
('PD001', 'B001', N'Trang trí nến'),
('PD002', 'B003', N'Phòng máy lạnh'),
('PD003', 'B008', N'Góc riêng tư'),
('PD004', 'B005', N'Gần khu vui chơi');

-- 2.11. BẢNG HÓA ĐƠN
INSERT INTO HoaDon (maHD, maBan, maNV, maKM, maThue, ngayTao, gioVao, gioRa, trangThaiThanhToan, phuongThucTT, tienGiamGia) VALUES 
('HD001', 'B002', 'NV002', NULL, 'TH001', '2026-04-04', '11:00:00', '12:30:00', N'DA_THANH_TOAN', N'TIEN_MAT', 0),
('HD002', 'B001', 'NV002', 'KM002', 'TH002', '2026-04-04', '18:00:00', NULL, N'CHUA_THANH_TOAN', NULL, 100000),
('HD003', 'B006', 'NV005', 'KM004', 'TH003', '2026-04-04', '19:30:00', '21:00:00', N'DA_THANH_TOAN', N'THE', 30000),
('HD004', 'B002', 'NV005', NULL, 'TH002', '2026-04-04', '14:00:00', '15:00:00', N'DA_HUY', NULL, 0);

-- 2.12. CHI TIẾT HÓA ĐƠN
INSERT INTO ChiTietHoaDon (maHD, maMon, soLuong, donGiaLuuTru, ghiChu) VALUES 
('HD001', 'MA001', 2, 85000, N''),
('HD001', 'MA004', 1, 110000, N''),
('HD002', 'MA003', 4, 95000, N'Chín vừa'),
('HD002', 'MA006', 4, 250000, N''),
('HD003', 'MA010', 1, 120000, N'Phục vụ tại quầy'),
('HD003', 'MA007', 2, 180000, N''),
('HD004', 'MA012', 1, 160000, N'Hủy do khách đổi ý'),
('HD002', 'MA008', 2, 320000, N'Hấp kỹ');

-- =========================================================
-- 3. CẬP NHẬT TỰ ĐỘNG TỔNG TIỀN (CTE)
-- =========================================================
GO
WITH SumCTE AS (
    SELECT maHD, SUM(thanhTien) as SumGoc
    FROM ChiTietHoaDon
    GROUP BY maHD
)
UPDATE H
SET H.tongTienGoc = S.SumGoc,
    H.tongThanhToan = CASE 
        WHEN H.trangThaiThanhToan = N'DA_HUY' THEN 0 
        ELSE (S.SumGoc - H.tienGiamGia) * (1 + T.thueSuat) 
    END
FROM HoaDon H
JOIN SumCTE S ON H.maHD = S.maHD
JOIN Thue T ON H.maThue = T.maThue;
GO

-- XEM KẾT QUẢ
SELECT maHD, tongTienGoc, tienGiamGia, tongThanhToan, trangThaiThanhToan FROM HoaDon;