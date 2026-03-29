USE master;
GO

-- =========================================================
-- 0. TẠO DATABASE (Reset lại từ đầu)
-- =========================================================
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'QuanLyDatBan')
BEGIN
    ALTER DATABASE QuanLyDatBan SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE QuanLyDatBan;
END
GO

CREATE DATABASE QuanLyDatBan;
GO

USE QuanLyDatBan;
GO

-- =========================================================
-- 1. TẠO CẤU TRÚC BẢNG (CREATE TABLES)
-- =========================================================

-- BẢNG ĐỘC LẬP
CREATE TABLE KhuVuc (
    maKhuVuc VARCHAR(20) PRIMARY KEY,
    tenKhuVuc NVARCHAR(100) NOT NULL
);

CREATE TABLE LoaiMonAn (
    maLoai VARCHAR(20) PRIMARY KEY,
    tenLoai NVARCHAR(100) NOT NULL
);

CREATE TABLE KhuyenMai (
    maKM VARCHAR(20) PRIMARY KEY,
    tenKM NVARCHAR(100) NOT NULL,
    giaTriGiam FLOAT NOT NULL CHECK (giaTriGiam > 0),
    ngayBatDau DATE NOT NULL,
    ngayKetThuc DATE NOT NULL,
    dieuKienToiThieu FLOAT CHECK (dieuKienToiThieu >= 0),
    CONSTRAINT CHK_NgayKM CHECK (ngayKetThuc >= ngayBatDau)
);

CREATE TABLE Thue (
    maThue VARCHAR(20) PRIMARY KEY,
    tenThue NVARCHAR(100) NOT NULL,
    thueSuat FLOAT NOT NULL CHECK (thueSuat >= 0)
);

CREATE TABLE KhachHang (
    maKH VARCHAR(20) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    sdt VARCHAR(15) NOT NULL,
    diemTichLuy INT DEFAULT 0 CHECK (diemTichLuy >= 0),
    loaiThanhVien NVARCHAR(50)
);

CREATE TABLE NhanVien (
    maNV VARCHAR(20) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    sdt VARCHAR(15) NOT NULL,
    chucVu NVARCHAR(50) NOT NULL,
    ngayVaoLam DATE NOT NULL,
    luongCoBan FLOAT NOT NULL CHECK (luongCoBan > 0),
    trangThai BIT DEFAULT 1
);

-- BẢNG PHỤ THUỘC CẤP 1
CREATE TABLE BanAn (
    maBan VARCHAR(20) PRIMARY KEY,
    soGhe INT NOT NULL CHECK (soGhe > 0),
    viTri NVARCHAR(100) NOT NULL,
    maKhuVuc VARCHAR(20) NOT NULL,
    trangThai BIT DEFAULT 1,
    CONSTRAINT FK_BanAn_KhuVuc FOREIGN KEY (maKhuVuc) REFERENCES KhuVuc(maKhuVuc) ON DELETE CASCADE
);

CREATE TABLE MonAn (
    maMon VARCHAR(20) PRIMARY KEY,
    tenMon NVARCHAR(100) NOT NULL,
    donGia FLOAT NOT NULL CHECK (donGia > 0),
    donViTinh NVARCHAR(50) NOT NULL,
    maLoai VARCHAR(20) NOT NULL,
    trangThai BIT DEFAULT 1,
    uriHinhAnh VARCHAR(255),
    CONSTRAINT FK_MonAn_LoaiMonAn FOREIGN KEY (maLoai) REFERENCES LoaiMonAn(maLoai) ON DELETE CASCADE
);

CREATE TABLE TaiKhoan (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    maNV VARCHAR(20) UNIQUE NOT NULL,
    quyenHan VARCHAR(20) NOT NULL,
    CONSTRAINT FK_TaiKhoan_NhanVien FOREIGN KEY (maNV) REFERENCES NhanVien(maNV) ON DELETE CASCADE
);

-- BẢNG PHỤ THUỘC CẤP 2
CREATE TABLE PhieuDatBan (
    maPhieuDat VARCHAR(20) PRIMARY KEY,
    maKH VARCHAR(20) NOT NULL,
    maBan VARCHAR(20) NOT NULL,
    thoiGianDen DATETIME NOT NULL,
    soLuongNguoi INT NOT NULL CHECK (soLuongNguoi > 0),
    ghiChu NVARCHAR(255),
    trangThai BIT,
    CONSTRAINT FK_PhieuDatBan_KhachHang FOREIGN KEY (maKH) REFERENCES KhachHang(maKH) ON DELETE CASCADE,
    CONSTRAINT FK_PhieuDatBan_BanAn FOREIGN KEY (maBan) REFERENCES BanAn(maBan) ON DELETE CASCADE
);

CREATE TABLE PhieuGoiMon (
    maPhieuGoi VARCHAR(20) PRIMARY KEY,
    maBan VARCHAR(20) NOT NULL,
    maNV VARCHAR(20) NOT NULL,
    gioVao TIME NOT NULL,
    gioRa TIME,
    CONSTRAINT FK_PhieuGoiMon_BanAn FOREIGN KEY (maBan) REFERENCES BanAn(maBan) ON DELETE CASCADE,
    CONSTRAINT FK_PhieuGoiMon_NhanVien FOREIGN KEY (maNV) REFERENCES NhanVien(maNV) ON DELETE CASCADE
);

-- BẢNG PHỤ THUỘC CẤP 3
CREATE TABLE ChiTietGoiMon (
    maPhieuGoi VARCHAR(20) NOT NULL,
    maMon VARCHAR(20) NOT NULL,
    soLuong INT NOT NULL CHECK (soLuong > 0),
    donGiaLuuTru FLOAT NOT NULL CHECK (donGiaLuuTru >= 0),
    thanhTien FLOAT,
    ghiChu NVARCHAR(255),
    PRIMARY KEY (maPhieuGoi, maMon),
    CONSTRAINT FK_CTGM_PhieuGoiMon FOREIGN KEY (maPhieuGoi) REFERENCES PhieuGoiMon(maPhieuGoi) ON DELETE CASCADE,
    CONSTRAINT FK_CTGM_MonAn FOREIGN KEY (maMon) REFERENCES MonAn(maMon) ON DELETE CASCADE
);

-- BẢNG PHỤ THUỘC CẤP 4 (HÓA ĐƠN)
CREATE TABLE HoaDon (
    maHD VARCHAR(20) PRIMARY KEY,
    maPhieuGoi VARCHAR(20) NOT NULL UNIQUE,
    maNV VARCHAR(20) NOT NULL,
    maKM VARCHAR(20),
    maThue VARCHAR(20) NOT NULL, -- ĐÃ BỔ SUNG CỘT THUẾ (Bắt buộc phải có)
    ngayTao DATE NOT NULL,
    tongTienGoc FLOAT NOT NULL CHECK (tongTienGoc >= 0),
    tienGiamGia FLOAT DEFAULT 0 CHECK (tienGiamGia >= 0),
    tongThanhToan FLOAT NOT NULL CHECK (tongThanhToan >= 0),
    phuongThucTT NVARCHAR(50),
    CONSTRAINT FK_HoaDon_PhieuGoiMon FOREIGN KEY (maPhieuGoi) REFERENCES PhieuGoiMon(maPhieuGoi),
    CONSTRAINT FK_HoaDon_NhanVien FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
    CONSTRAINT FK_HoaDon_KhuyenMai FOREIGN KEY (maKM) REFERENCES KhuyenMai(maKM),
    CONSTRAINT FK_HoaDon_Thue FOREIGN KEY (maThue) REFERENCES Thue(maThue) -- ĐÃ BỔ SUNG KHÓA NGOẠI
);
GO

-- =========================================================
-- 2. CHÈN DỮ LIỆU MẪU (SEED DATA)
-- =========================================================

INSERT INTO KhuVuc (maKhuVuc, tenKhuVuc) VALUES 
('KV01', N'Tầng 1 - Sảnh chính'), ('KV02', N'Tầng 2 - Phòng VIP'),
('KV03', N'Tầng 3 - Ban công'), ('KV04', N'Sân vườn ngoài trời');

INSERT INTO LoaiMonAn (maLoai, tenLoai) VALUES 
('LMA01', N'Khai vị'), ('LMA02', N'Món Bò & Cừu'), ('LMA03', N'Hải sản'),
('LMA04', N'Món Gà & Heo'), ('LMA05', N'Cơm & Mì'), ('LMA06', N'Đồ uống & Tráng miệng');

INSERT INTO KhuyenMai (maKM, tenKM, giaTriGiam, ngayBatDau, ngayKetThuc, dieuKienToiThieu) VALUES 
('KM01', N'Giảm giá thành viên mới', 50000, '2025-01-01', '2026-12-31', 200000),
('KM02', N'Mừng sinh nhật quán', 100000, '2026-05-01', '2026-05-31', 500000),
('KM03', N'Lễ Tình Nhân', 80000, '2026-02-10', '2026-02-15', 300000),
('KM04', N'Ngày hội gia đình', 150000, '2026-06-25', '2026-06-30', 800000),
('KM05', N'Trưa hè sôi động', 30000, '2026-06-01', '2026-08-31', 150000);

INSERT INTO Thue (maThue, tenThue, thueSuat) VALUES 
('VAT8', N'Thuế GTGT 8%', 0.08), ('VAT10', N'Thuế GTGT 10%', 0.1);

INSERT INTO KhachHang (maKH, hoTen, sdt, diemTichLuy, loaiThanhVien) VALUES 
('KH001', N'Nguyễn Văn An', '0901111222', 150, N'Bạc'), ('KH002', N'Trần Thị Bình', '0902222333', 500, N'Vàng'),
('KH003', N'Lê Minh Cường', '0903333444', 1200, N'VIP'), ('KH004', N'Phạm Thu Dung', '0904444555', 50, N'Đồng'),
('KH005', N'Hoàng Trọng Ân', '0905555666', 300, N'Bạc'), ('KH006', N'Vũ Bích Ngọc', '0906666777', 800, N'Vàng'),
('KH007', N'Đặng Thái Sơn', '0907777888', 2100, N'VIP'), ('KH008', N'Bùi Thị Nhàn', '0908888999', 10, N'Đồng'),
('KH009', N'Đỗ Hữu Hùng', '0909999000', 450, N'Bạc'), ('KH010', N'Hồ Thanh Trúc', '0910000111', 600, N'Vàng'),
('KH011', N'Ngô Hùng Dũng', '0911111222', 1800, N'VIP'), ('KH012', N'Dương Thị Lệ', '0912222333', 90, N'Đồng'),
('KH013', N'Lý Hải Long', '0913333444', 320, N'Bạc'), ('KH014', N'Đinh Hữu Lộc', '0914444555', 750, N'Vàng'),
('KH015', N'Trương Mỹ Lan', '0915555666', 2500, N'VIP');

INSERT INTO NhanVien (maNV, hoTen, sdt, chucVu, ngayVaoLam, luongCoBan, trangThai) VALUES 
('NV001', N'Lê Đại Hành', '0981000111', N'Quản lý', '2024-01-01', 20000000, 1),
('NV002', N'Nguyễn Thị Bếp', '0982000222', N'Bếp trưởng', '2024-02-15', 18000000, 1),
('NV003', N'Trần Văn Thu', '0983000333', N'Thu ngân', '2024-05-10', 9000000, 1),
('NV004', N'Phạm Phục Vụ', '0984000444', N'Phục vụ', '2024-06-01', 7500000, 1),
('NV005', N'Hoàng Chạy Bàn', '0985000555', N'Phục vụ', '2024-06-15', 7500000, 1),
('NV006', N'Vũ Thu Ngân', '0986000666', N'Thu ngân', '2024-07-20', 9000000, 1),
('NV007', N'Đặng Bếp Phó', '0987000777', N'Bếp phó', '2024-08-05', 15000000, 1),
('NV008', N'Bùi Lễ Tân', '0988000888', N'Lễ tân', '2025-01-10', 8000000, 1),
('NV009', N'Đỗ Phục Vụ Hai', '0989000999', N'Phục vụ', '2025-03-01', 7500000, 1),
('NV010', N'Hồ Phục Vụ Ba', '0980111222', N'Phục vụ', '2025-04-12', 7500000, 1),
('NV011', N'Ngô Tạp Vụ', '0980222333', N'Tạp vụ', '2025-05-20', 6000000, 1),
('NV012', N'Dương Bảo Vệ', '0980333444', N'Bảo vệ', '2025-06-01', 6500000, 1);

INSERT INTO TaiKhoan (username, password, maNV, quyenHan) VALUES 
('admin_hanh', '123456', 'NV001', 'ADMIN'), ('bep_truong', '123456', 'NV002', 'USER'),
('thungan_thu', '123456', 'NV003', 'USER'), ('phucvu_pham', '123456', 'NV004', 'USER'),
('phucvu_hoang', '123456', 'NV005', 'USER'), ('thungan_vu', '123456', 'NV006', 'USER'),
('beppho_dang', '123456', 'NV007', 'USER'), ('letan_bui', '123456', 'NV008', 'USER'),
('phucvu_do', '123456', 'NV009', 'USER'), ('phucvu_ho', '123456', 'NV010', 'USER'),
('tapvu_ngo', '123456', 'NV011', 'USER'), ('baove_duong', '123456', 'NV012', 'USER');

INSERT INTO BanAn (maBan, soGhe, viTri, maKhuVuc, trangThai) VALUES 
('B01', 4, N'Góc trái', 'KV01', 1), ('B02', 4, N'Giữa sảnh', 'KV01', 1),
('B03', 6, N'Góc phải', 'KV01', 1), ('B04', 2, N'Cạnh cửa sổ', 'KV01', 1),
('B05', 8, N'Phòng Đào', 'KV02', 1), ('B06', 10, N'Phòng Cúc', 'KV02', 1),
('B07', 12, N'Phòng Trúc', 'KV02', 1), ('B08', 6, N'Phòng Mai', 'KV02', 1),
('B09', 4, N'Ban công trái', 'KV03', 1), ('B10', 4, N'Ban công phải', 'KV03', 1),
('B11', 2, N'View đường phố', 'KV03', 1), ('B12', 6, N'Giữa vườn', 'KV04', 1),
('B13', 8, N'Gần hồ cá', 'KV04', 1), ('B14', 4, N'Gốc cây đa', 'KV04', 1),
('B15', 10, N'Khu BBQ', 'KV04', 1);

INSERT INTO MonAn (maMon, tenMon, donGia, donViTinh, maLoai, trangThai, uriHinhAnh) VALUES 
('MA01', N'Salad cá ngừ', 85000, N'Dĩa', 'LMA01', 1, 'salad_ca_ngu.jpg'),
('MA02', N'Súp cua', 65000, N'Thố', 'LMA01', 1, 'sup_cua.jpg'),
('MA03', N'Gỏi ngó sen tôm thịt', 95000, N'Dĩa', 'LMA01', 1, 'goi_ngo_sen.jpg'),
('MA04', N'Chả giò hải sản', 110000, N'Phần', 'LMA01', 1, 'cha_gio.jpg'),
('MA05', N'Khoai tây chiên', 45000, N'Dĩa', 'LMA01', 1, 'khoai_tay_chien.jpg'),
('MA06', N'Bò bít tết Úc', 250000, N'Phần', 'LMA02', 1, 'bo_bit_tet.jpg'),
('MA07', N'Bò lúc lắc', 180000, N'Dĩa', 'LMA02', 1, 'bo_luc_lac.jpg'),
('MA08', N'Sườn cừu nướng BBQ', 320000, N'Phần', 'LMA02', 1, 'suon_cuu.jpg'),
('MA09', N'Bò né phô mai', 150000, N'Phần', 'LMA02', 1, 'bo_ne.jpg'),
('MA10', N'Bò xào hành tây', 120000, N'Dĩa', 'LMA02', 1, 'bo_xao.jpg'),
('MA11', N'Tôm hùm phô mai', 850000, N'Con', 'LMA03', 1, 'tom_hum.jpg'),
('MA12', N'Mực hấp gừng', 160000, N'Dĩa', 'LMA03', 1, 'muc_hap.jpg'),
('MA13', N'Cua rang me', 450000, N'Con', 'LMA03', 1, 'cua_rang_me.jpg'),
('MA14', N'Hàu nướng mỡ hành', 120000, N'Phần', 'LMA03', 1, 'hau_nuong.jpg'),
('MA15', N'Nghêu hấp xả', 90000, N'Thố', 'LMA03', 1, 'ngheu_hap.jpg'),
('MA16', N'Gà quay chảo', 220000, N'Nửa con', 'LMA04', 1, 'ga_quay.jpg'),
('MA17', N'Sườn heo chua ngọt', 140000, N'Dĩa', 'LMA04', 1, 'suon_chua_ngot.jpg'),
('MA18', N'Heo quay giòn da', 180000, N'Dĩa', 'LMA04', 1, 'heo_quay.jpg'),
('MA19', N'Cánh gà chiên mắm', 110000, N'Phần', 'LMA04', 1, 'canh_ga.jpg'),
('MA20', N'Gà ủ muối', 250000, N'Con', 'LMA04', 1, 'ga_u_muoi.jpg'),
('MA21', N'Cơm chiên hải sản', 120000, N'Dĩa', 'LMA05', 1, 'com_chien_hs.jpg'),
('MA22', N'Cơm chiên dương châu', 100000, N'Dĩa', 'LMA05', 1, 'com_chien_dc.jpg'),
('MA23', N'Mì xào giòn', 110000, N'Dĩa', 'LMA05', 1, 'mi_xao_gion.jpg'),
('MA24', N'Mì Ý sốt bò băm', 130000, N'Phần', 'LMA05', 1, 'mi_y.jpg'),
('MA25', N'Miến xào cua', 160000, N'Dĩa', 'LMA05', 1, 'mien_xao.jpg'),
('MA26', N'Nước ép cam', 45000, N'Ly', 'LMA06', 1, 'nuoc_cam.jpg'),
('MA27', N'Sinh tố dâu', 55000, N'Ly', 'LMA06', 1, 'sinh_to_dau.jpg'),
('MA28', N'Bia Heineken', 35000, N'Lon', 'LMA06', 1, 'bia_heineken.jpg'),
('MA29', N'Trà đá', 5000, N'Ly', 'LMA06', 1, 'tra_da.jpg'),
('MA30', N'Bánh Flan', 25000, N'Phần', 'LMA06', 1, 'banh_flan.jpg');

INSERT INTO PhieuDatBan (maPhieuDat, maKH, maBan, thoiGianDen, soLuongNguoi, ghiChu, trangThai) VALUES 
('PDB01', 'KH001', 'B01', '2026-03-30 19:00:00', 4, N'Gia đình', 1),
('PDB02', 'KH002', 'B05', '2026-03-30 18:30:00', 8, N'Tiếp khách', 1),
('PDB03', 'KH003', 'B12', '2026-03-31 20:00:00', 6, N'Sinh nhật', 1),
('PDB04', 'KH004', 'B04', '2026-03-31 19:30:00', 2, N'Hẹn hò', 0),
('PDB05', 'KH005', 'B08', '2026-04-01 11:30:00', 5, N'Ăn trưa', 1),
('PDB06', 'KH006', 'B15', '2026-04-01 18:00:00', 10, N'Tiệc công ty', 1),
('PDB07', 'KH007', 'B06', '2026-04-02 19:00:00', 9, N'', 1),
('PDB08', 'KH008', 'B11', '2026-04-02 20:30:00', 2, N'Góc khuất', 1),
('PDB09', 'KH009', 'B02', '2026-04-03 12:00:00', 4, N'', 0),
('PDB10', 'KH010', 'B13', '2026-04-03 18:30:00', 7, N'Có trẻ em', 1);

INSERT INTO PhieuGoiMon (maPhieuGoi, maBan, maNV, gioVao, gioRa) VALUES 
('PGM01', 'B01', 'NV004', '18:00', '19:30'), ('PGM02', 'B05', 'NV005', '18:30', '20:00'),
('PGM03', 'B12', 'NV009', '19:00', '21:00'), ('PGM04', 'B08', 'NV010', '11:00', '12:30'),
('PGM05', 'B15', 'NV004', '18:00', '22:00'), ('PGM06', 'B06', 'NV005', '19:00', '21:30'),
('PGM07', 'B11', 'NV009', '20:00', '21:30'), ('PGM08', 'B13', 'NV010', '18:30', '20:30'),
('PGM09', 'B02', 'NV004', '12:00', '13:00'), ('PGM10', 'B03', 'NV005', '19:00', NULL),
('PGM11', 'B07', 'NV009', '18:30', '20:30'), ('PGM12', 'B09', 'NV010', '19:30', '21:00'),
('PGM13', 'B14', 'NV004', '17:30', '19:00'), ('PGM14', 'B10', 'NV005', '18:00', '19:30'),
('PGM15', 'B01', 'NV009', '20:00', '21:30');

INSERT INTO ChiTietGoiMon (maPhieuGoi, maMon, soLuong, donGiaLuuTru, thanhTien, ghiChu) VALUES 
('PGM01', 'MA01', 1, 85000, 85000, N''), ('PGM01', 'MA06', 2, 250000, 500000, N'Chín vừa'),
('PGM02', 'MA11', 1, 850000, 850000, N''), ('PGM02', 'MA28', 4, 35000, 140000, N'Ướp lạnh'),
('PGM03', 'MA16', 1, 220000, 220000, N''), ('PGM03', 'MA21', 1, 120000, 120000, N''), ('PGM03', 'MA26', 2, 45000, 90000, N'Ít đường'),
('PGM04', 'MA22', 2, 100000, 200000, N''), ('PGM04', 'MA29', 2, 5000, 10000, N''),
('PGM05', 'MA08', 2, 320000, 640000, N''), ('PGM05', 'MA13', 1, 450000, 450000, N''), ('PGM05', 'MA28', 10, 35000, 350000, N''),
('PGM06', 'MA07', 2, 180000, 360000, N''), ('PGM06', 'MA24', 2, 130000, 260000, N''),
('PGM07', 'MA09', 1, 150000, 150000, N''), ('PGM07', 'MA30', 2, 25000, 50000, N''),
('PGM08', 'MA17', 1, 140000, 140000, N''), ('PGM08', 'MA27', 3, 55000, 165000, N''),
('PGM09', 'MA23', 2, 110000, 220000, N''),
('PGM10', 'MA12', 1, 160000, 160000, N''), ('PGM10', 'MA15', 1, 90000, 90000, N''),
('PGM11', 'MA18', 2, 180000, 360000, N''),
('PGM12', 'MA02', 2, 65000, 130000, N''), ('PGM12', 'MA19', 1, 110000, 110000, N''),
('PGM13', 'MA05', 1, 45000, 45000, N''), ('PGM14', 'MA10', 1, 120000, 120000, N''),
('PGM15', 'MA04', 1, 110000, 110000, N'');

-- ĐÃ BỔ SUNG CỘT maThue TRONG LỆNH INSERT
INSERT INTO HoaDon (maHD, maPhieuGoi, maNV, maKM, maThue, ngayTao, tongTienGoc, tienGiamGia, tongThanhToan, phuongThucTT) VALUES 
('HD01', 'PGM01', 'NV003', NULL, 'VAT8', '2026-03-25', 0, 0, 0, N'Tiền mặt'),
('HD02', 'PGM02', 'NV006', 'KM01', 'VAT10', '2026-03-25', 0, 50000, 0, N'Chuyển khoản'),
('HD03', 'PGM03', 'NV003', NULL, 'VAT8', '2026-03-26', 0, 0, 0, N'Thẻ tín dụng'),
('HD04', 'PGM04', 'NV006', NULL, 'VAT8', '2026-03-26', 0, 0, 0, N'Tiền mặt'),
('HD05', 'PGM05', 'NV003', 'KM04', 'VAT10', '2026-03-27', 0, 150000, 0, N'Chuyển khoản'),
('HD06', 'PGM06', 'NV006', 'KM02', 'VAT10', '2026-03-27', 0, 100000, 0, N'Thẻ tín dụng'),
('HD07', 'PGM07', 'NV003', NULL, 'VAT8', '2026-03-28', 0, 0, 0, N'Tiền mặt'),
('HD08', 'PGM08', 'NV006', NULL, 'VAT8', '2026-03-28', 0, 0, 0, N'Chuyển khoản'),
('HD09', 'PGM09', 'NV003', 'KM05', 'VAT8', '2026-03-29', 0, 30000, 0, N'Tiền mặt'),
('HD10', 'PGM10', 'NV006', NULL, 'VAT10', '2026-03-29', 0, 0, 0, N'Chuyển khoản'),
('HD11', 'PGM11', 'NV003', NULL, 'VAT8', '2026-03-30', 0, 0, 0, N'Tiền mặt'),
('HD12', 'PGM12', 'NV006', NULL, 'VAT10', '2026-03-30', 0, 0, 0, N'Thẻ tín dụng'),
('HD13', 'PGM13', 'NV003', NULL, 'VAT8', '2026-03-31', 0, 0, 0, N'Tiền mặt'),
('HD14', 'PGM14', 'NV006', NULL, 'VAT10', '2026-03-31', 0, 0, 0, N'Chuyển khoản'),
('HD15', 'PGM15', 'NV003', NULL, 'VAT8', '2026-04-01', 0, 0, 0, N'Tiền mặt');

-- =================================================================================
-- 3. CẬP NHẬT TỰ ĐỘNG THÔNG MINH BẰNG CTE (Bao gồm tính % Thuế)
-- =================================================================================
WITH TongTienCTE AS (
    SELECT maPhieuGoi, ISNULL(SUM(thanhTien), 0) AS TongTien
    FROM ChiTietGoiMon
    GROUP BY maPhieuGoi
)
UPDATE H
SET H.tongTienGoc = T.TongTien,
    H.tongThanhToan = CASE 
        WHEN (T.TongTien - H.tienGiamGia) < 0 THEN 0 
        -- Công thức: (Tổng tiền gốc - Tiền giảm) + Thuế
        ELSE (T.TongTien - H.tienGiamGia) * (1 + ISNULL(Th.thueSuat, 0)) 
    END
FROM HoaDon H
JOIN TongTienCTE T ON H.maPhieuGoi = T.maPhieuGoi
JOIN Thue Th ON H.maThue = Th.maThue;
GO