USE master;
GO

-- =========================================================
-- 0. TẠO DATABASE
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
-- 1. BẢNG KHU VỰC
-- =========================================================
CREATE TABLE KhuVuc (
    maKhuVuc VARCHAR(20) PRIMARY KEY,
    tenKhuVuc NVARCHAR(100) NOT NULL
);

-- =========================================================
-- 2. BẢNG KHUYẾN MÃI & THUẾ (Đã thêm trangThai theo Mermaid)
-- =========================================================
CREATE TABLE KhuyenMai (
    maKM VARCHAR(20) PRIMARY KEY,
    tenKM NVARCHAR(100) NOT NULL,
    giaTriGiam FLOAT NOT NULL,
    ngayBatDau DATE NOT NULL,
    ngayKetThuc DATE NOT NULL,
    dieuKienToiThieu FLOAT DEFAULT 0,
    trangThai NVARCHAR(50) NOT NULL
        CHECK (trangThai IN (N'CON_AP_DUNG', N'NGUNG_AP_DUNG')) -- Khớp Enum sơ đồ
);

CREATE TABLE Thue (
    maThue VARCHAR(20) PRIMARY KEY,
    tenThue NVARCHAR(100) NOT NULL,
    thueSuat FLOAT NOT NULL CHECK (thueSuat >= 0),
    trangThai NVARCHAR(50) NOT NULL
        CHECK (trangThai IN (N'CON_AP_DUNG', N'NGUNG_AP_DUNG')) -- Khớp Enum sơ đồ
);

-- =========================================================
-- 3. BẢNG KHÁCH HÀNG
-- =========================================================
CREATE TABLE KhachHang (
    maKH VARCHAR(20) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    sdt VARCHAR(15) NOT NULL,
    diemTichLuy INT DEFAULT 0,
    loaiThanhVien NVARCHAR(50)
);

-- =========================================================
-- 4. BẢNG NHÂN VIÊN & TÀI KHOẢN
-- =========================================================
CREATE TABLE NhanVien (
    maNV VARCHAR(20) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    sdt VARCHAR(15),
    chucVu NVARCHAR(50) NOT NULL 
        CHECK (chucVu IN (N'QUAN_LY', N'BEP', N'THU_NGAN', N'PHUC_VU')),
    ngayVaoLam DATE NOT NULL,
    luongCoBan FLOAT NOT NULL,
    trangThai NVARCHAR(50) NOT NULL
        CHECK (trangThai IN (N'DANG_LAM_VIEC', N'DA_NGHI_VIEC'))
);

CREATE TABLE TaiKhoan (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    maNV VARCHAR(20) UNIQUE NOT NULL,
    quyenHan NVARCHAR(50), 
    CONSTRAINT FK_TaiKhoan_NhanVien FOREIGN KEY (maNV) REFERENCES NhanVien(maNV) ON DELETE CASCADE
);

-- =========================================================
-- 5. BẢNG MÓN ĂN (Đã cập nhật tenLoai khớp Enum LoaiMonAn)
-- =========================================================
CREATE TABLE MonAn (
    maMon VARCHAR(20) PRIMARY KEY,
    tenMon NVARCHAR(100) NOT NULL,
    donGia FLOAT NOT NULL,
    donViTinh NVARCHAR(50),
    tenLoai NVARCHAR(50) NOT NULL
        CHECK (tenLoai IN (N'KHAI_VI', N'MON_CHINH', N'HAI_SAN', N'DO_UONG', N'TRANG_MIENG')), -- Khớp Enum sơ đồ
    trangThai NVARCHAR(50) NOT NULL
        CHECK (trangThai IN (N'CON', N'HET')), -- Khớp Enum sơ đồ
    urlHinhAnh VARCHAR(255)
);

-- =========================================================
-- 6. BẢNG BÀN ĂN
-- =========================================================
CREATE TABLE BanAn (
    maBan VARCHAR(20) PRIMARY KEY,
    soGhe INT CHECK (soGhe > 0),
    viTri NVARCHAR(100),
    maKhuVuc VARCHAR(20),
    trangThai NVARCHAR(50) NOT NULL
        CHECK (trangThai IN (N'TRONG', N'DANG_DUNG', N'DA_DAT')),
    CONSTRAINT FK_BanAn_KhuVuc FOREIGN KEY (maKhuVuc) REFERENCES KhuVuc(maKhuVuc)
);

-- =========================================================
-- 7. PHIẾU ĐẶT BÀN
-- =========================================================
CREATE TABLE PhieuDatBan (
    maPhieuDat VARCHAR(20) PRIMARY KEY,
    maKH VARCHAR(20) NOT NULL,
    maNV VARCHAR(20) NOT NULL,
    thoiGianDen DATETIME NOT NULL,
    soLuongNguoi INT,
    ghiChu NVARCHAR(255),
    trangThai NVARCHAR(50) NOT NULL
        CHECK (trangThai IN (N'CHO_XAC_NHAN', N'DA_XAC_NHAN', N'DA_DEN', N'DA_HUY')),
    CONSTRAINT FK_PDB_KhachHang FOREIGN KEY (maKH) REFERENCES KhachHang(maKH),
    CONSTRAINT FK_PDB_NhanVien FOREIGN KEY (maNV) REFERENCES NhanVien(maNV)
);

CREATE TABLE ChiTietPhieuDatBan (
    maPhieuDat VARCHAR(20) NOT NULL,
    maBan VARCHAR(20) NOT NULL,
    ghiChu NVARCHAR(255),
    PRIMARY KEY (maPhieuDat, maBan),
    CONSTRAINT FK_CTPDB_PhieuDat FOREIGN KEY (maPhieuDat) REFERENCES PhieuDatBan(maPhieuDat),
    CONSTRAINT FK_CTPDB_BanAn FOREIGN KEY (maBan) REFERENCES BanAn(maBan)
);

-- =========================================================
-- 8. HÓA ĐƠN
-- =========================================================
CREATE TABLE HoaDon (
    maHD VARCHAR(20) PRIMARY KEY,
    maBan VARCHAR(20) NOT NULL,
    maNV VARCHAR(20) NOT NULL,
    maKM VARCHAR(20),
    maThue VARCHAR(20) NOT NULL,
    ngayTao DATETIME DEFAULT GETDATE(),
    gioVao TIME,
    gioRa TIME,
    tongTienGoc FLOAT DEFAULT 0,
    tienGiamGia FLOAT DEFAULT 0,
    tongThanhToan FLOAT DEFAULT 0,
    phuongThucTT NVARCHAR(50)
        CHECK (phuongThucTT IN (N'TIEN_MAT', N'CHUYEN_KHOAN', N'THE')),
    trangThaiThanhToan NVARCHAR(50) NOT NULL
        CHECK (trangThaiThanhToan IN (N'CHUA_THANH_TOAN', N'DA_THANH_TOAN', N'DA_HUY')),
    CONSTRAINT FK_HD_BanAn FOREIGN KEY (maBan) REFERENCES BanAn(maBan),
    CONSTRAINT FK_HD_NhanVien FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
    CONSTRAINT FK_HD_KhuyenMai FOREIGN KEY (maKM) REFERENCES KhuyenMai(maKM),
    CONSTRAINT FK_HD_Thue FOREIGN KEY (maThue) REFERENCES Thue(maThue)
);

-- =========================================================
-- 9. CHI TIẾT HÓA ĐƠN
-- =========================================================
CREATE TABLE ChiTietHoaDon (
    maHD VARCHAR(20) NOT NULL,
    maMon VARCHAR(20) NOT NULL,
    soLuong INT NOT NULL CHECK (soLuong > 0),
    donGiaLuuTru FLOAT NOT NULL,
    ghiChu NVARCHAR(255),
    thanhTien AS (soLuong * donGiaLuuTru),
    PRIMARY KEY (maHD, maMon),
    CONSTRAINT FK_CTHD_HoaDon FOREIGN KEY (maHD) REFERENCES HoaDon(maHD) ON DELETE CASCADE,
    CONSTRAINT FK_CTHD_MonAn FOREIGN KEY (maMon) REFERENCES MonAn(maMon)
);
GO