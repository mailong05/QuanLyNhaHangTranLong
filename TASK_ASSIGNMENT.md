# PHÂN CÔNG TASK BACKEND - DỰ ÁN QUẢN LÝ ĐẶT BÀN NHÀ HÀNG

**Tổng cộng: 19 Service + 19 DAO + 25 Entity**  
**Thành viên: Long (Trưởng nhóm), May, Trí, Khoa**  
**Độ phức tạp: 4 ⭐⭐⭐⭐ | 3 ⭐⭐⭐ | 2 ⭐⭐ | 5 ⭐⭐⭐⭐⭐**

---

## 📋 PHÂN CÔNG CHI TIẾT

### 1️⃣ LONG (Người Quản Lý - 5 Modules, 25% - 30%)

**Độ khó: ⭐⭐⭐⭐⭐ (Cao nhất)**

#### A. Quản Lý Giao Dịch Hóa Đơn (CRITICAL)

- **Entity**: `HoaDon.java`, `ChiTietHoaDon.java`, `TrangThaiHoaDon.java`
- **DAO**: `HoaDonDAO.java`, `ChiTietHoaDonDAO.java`
- **Service**: `HoaDonService.java`, `ChiTietHoaDonService.java`
- **Yêu cầu**:
  - ✅ CRUD đầy đủ (Create, Read, Update, Delete)
  - ✅ Kiểm tra trạng thái hóa đơn (Chờ, Hoàn tất, Hủy)
  - ✅ Tính toán tổng tiền (bao gồm thuế, khuyến mãi)
  - ✅ Lịch sử hóa đơn (filter theo ngày, khách hàng)
  - ✅ Tích hợp với PhieuGoiMon
  - **Độ phức tạp**: ⭐⭐⭐⭐⭐

#### B. Quản Lý Phiếu Gọi Món (CRITICAL)

- **Entity**: `PhieuGoiMon.java`, `ChiTietGoiMon.java`
- **DAO**: `PhieuGoiMonDAO.java`, `ChiTietGoiMonDAO.java`
- **Service**: `PhieuGoiMonService.java`, `ChiTietGoiMonService.java`
- **Yêu cầu**:
  - ✅ CRUD phiếu gọi món
  - ✅ Quản lý chi tiết gọi món (món ăn + số lượng + giá)
  - ✅ Update trạng thái (Chờ, Đang nấu, Xong, Hủy)
  - ✅ Tính toán tổng tiền phiếu
  - ✅ Liên kết với HoaDon khi thanh toán
  - **Độ phức tạp**: ⭐⭐⭐⭐⭐

#### C. Quản Lý Phiếu Đặt Bàn Trước (CRITICAL)

- **Entity**: `PhieuDatBan.java`, `ChiTietPhieuDatBan.java`, `TrangThaiPhieuDat.java`
- **DAO**: `PhieuDatBanDAO.java`, `ChiTietPhieuDatBanDAO.java`
- **Service**: `PhieuDatBanService.java`, `ChiTietPhieuDatBanService.java`, `DatBanTruocService.java`
- **Yêu cầu**:
  - ✅ CRUD phiếu đặt bàn
  - ✅ Thêm/xóa bàn khỏi phiếu
  - ✅ Kiểm tra bàn trống (không trùng lịch)
  - ✅ Tính phí đặt cọc (nếu có)
  - ✅ Kiểm tra trạng thái phiếu (Chờ xác nhận, Đã xác nhận, Hủy, Hoàn tất)
  - **Độ phức tạp**: ⭐⭐⭐⭐⭐

#### D. Session & Authentication (INFRASTRUCTURE)

- **File**: `SessionManager.java`, `AuthService.java`, `TaiKhoanDAO.java`
- **Yêu cầu**:
  - ✅ Quản lý session người dùng đăng nhập
  - ✅ Xác thực username/password
  - ✅ Lấy thông tin user từ SessionManager
  - ✅ Logout & clear session
  - **Độ phức tạp**: ⭐⭐⭐

#### E. Coordination & Integration Testing

- ✅ Đảm bảo tích hợp giữa các Service
- ✅ Fix bugs liên quan đến transaction
- ✅ Review code từ các thành viên khác

**📌 Tổng công việc**: ~2500 LOC (lines of code)

---

### 2️⃣ MAY (Thành Viên - 4 Modules, 25%)

**Độ khó: ⭐⭐⭐ (Trung bình)**

#### A. Quản Lý Bàn (BAN)

- **Entity**: `Ban.java`, `TrangThaiBan.java`
- **DAO**: `BanDAO.java`
- **Service**: `BanService.java`
- **Yêu cầu**:
  - ✅ CRUD bàn (Thêm, Sửa, Xóa, Lấy danh sách)
  - ✅ Kiểm tra trạng thái bàn (Trống, Đã có khách, Đã gọi đợi, Bảo trì)
  - ✅ Filter bàn theo khu vực
  - ✅ Lấy danh sách bàn trống tại thời điểm nhất định
  - ✅ Cập nhật trạng thái bàn
  - **Độ phức tạp**: ⭐⭐⭐

#### B. Quản Lý Khu Vực (KHU VỰC)

- **Entity**: `KhuVuc.java`
- **DAO**: `KhuVucDAO.java`
- **Service**: `KhuVucService.java`
- **Yêu cầu**:
  - ✅ CRUD khu vực
  - ✅ Lấy danh sách bàn trong khu vực
  - ✅ Kiểm tra khu vực tồn tại trước khi xóa
  - **Độ phức tạp**: ⭐⭐

#### C. Quản Lý Loại Món Ăn (LOẠI MÓN ĂN)

- **Entity**: `LoaiMonAn.java`
- **DAO**: `LoaiMonAnDAO.java`
- **Service**: `LoaiMonAnService.java`
- **Yêu cầu**:
  - ✅ CRUD loại món ăn
  - ✅ Lấy danh sách món ăn theo loại
  - ✅ Kiểm tra loại tồn tại trước khi xóa
  - **Độ phức tạp**: ⭐⭐

#### D. Quản Lý Món Ăn (MÓN ĂN)

- **Entity**: `MonAn.java`, `TrangThaiMonAn.java`
- **DAO**: `MonAnDAO.java`
- **Service**: `MonAnService.java`
- **Yêu cầu**:
  - ✅ CRUD món ăn
  - ✅ Filter theo loại, theo trạng thái
  - ✅ Lấy danh sách món ăn hoạt động
  - ✅ Update giá món ăn
  - ✅ Quản lý trạng thái (Hoạt động, Ngừng bán)
  - **Độ phức tạp**: ⭐⭐⭐

**📌 Tổng công việc**: ~1500 LOC

---

### 3️⃣ TRÍ (Thành Viên - 4 Modules, 23%)

**Độ khó: ⭐⭐⭐⭐ (Trên trung bình)**

#### A. Quản Lý Nhân Viên (NHÂN VIÊN)

- **Entity**: `NhanVien.java`, `TrangThaiNhanVien.java`
- **DAO**: `NhanVienDAO.java`
- **Service**: `NhanVienService.java`
- **Yêu cầu**:
  - ✅ CRUD nhân viên
  - ✅ Kiểm tra email/CMND không trùng
  - ✅ Liên kết nhân viên với chức vụ
  - ✅ Quản lý trạng thái (Hoạt động, Nghỉ việc, Tạm dừng)
  - ✅ Lấy danh sách nhân viên theo chức vụ
  - **Độ phức tạp**: ⭐⭐⭐⭐

#### B. Quản Lý Chức Vụ (CHỨC VỤ)

- **Entity**: `ChucVu.java`
- **DAO**: `ChucVuDAO.java`
- **Service**: `ChucVuService.java`
- **Yêu cầu**:
  - ✅ CRUD chức vụ
  - ✅ Liên kết quyền hạn với chức vụ (Many-to-Many)
  - ✅ Kiểm tra chức vụ tồn tại trước khi xóa
  - **Độ phức tạp**: ⭐⭐⭐

#### C. Quản Lý Quyền Hạn (QUYỀN HẠN)

- **Entity**: `QuyenHan.java`
- **DAO**: `QuyenHanDAO.java`
- **Service**: `QuyenHanService.java`
- **Yêu cầu**:
  - ✅ CRUD quyền hạn
  - ✅ Lấy danh sách quyền của chức vụ
  - ✅ Kiểm tra quyền user trước khi thực hiện action
  - **Độ phức tạp**: ⭐⭐⭐

#### D. Quản Lý Tài Khoản (TÀI KHOẢN)

- **Entity**: `TaiKhoan.java`
- **DAO**: `TaiKhoanDAO.java`
- **Yêu cầu**:
  - ✅ CRUD tài khoản (Create, Read, Update)
  - ✅ Kiểm tra username không trùng
  - ✅ Hash mật khẩu trước khi lưu (không lưu plaintext)
  - ✅ Đổi mật khẩu
  - ✅ Reset mật khẩu
  - ✅ Kích hoạt/vô hiệu hóa tài khoản
  - **Độ phức tạp**: ⭐⭐⭐⭐

**📌 Tổng công việc**: ~1800 LOC

---

### 4️⃣ KHOA (Thành Viên - 4 Modules, 22%)

**Độ khó: ⭐⭐⭐ (Trung bình)**

#### A. Quản Lý Khách Hàng (KHÁCH HÀNG)

- **Entity**: `KhachHang.java`
- **DAO**: `KhachHangDAO.java`
- **Service**: `KhachHangService.java`
- **Yêu cầu**:
  - ✅ CRUD khách hàng
  - ✅ Kiểm tra số điện thoại không trùng
  - ✅ Kiểm tra email không trùng (nếu có)
  - ✅ Tìm kiếm khách hàng theo số điện thoại/tên
  - ✅ Lấy khách hàng lặp lại (regular customers)
  - ✅ Quản lý điểm tích lũy (nếu có)
  - **Độ phức tạp**: ⭐⭐⭐

#### B. Quản Lý Khuyến Mãi (KHUYẾN MÃI)

- **Entity**: `KhuyenMai.java`, `TrangThaiKhuyenMai.java`
- **DAO**: `KhuyenMaiDAO.java`
- **Service**: `KhuyenMaiService.java`
- **Yêu cầu**:
  - ✅ CRUD khuyến mãi
  - ✅ Kiểm tra ngày bắt đầu < ngày kết thúc
  - ✅ Kiểm tra khuyến mãi còn hạn hay không
  - ✅ Tính toán giá trị khuyến mãi (% hoặc tiền cố định)
  - ✅ Lấy danh sách khuyến mãi đang diễn ra
  - ✅ Trạng thái (Hoạt động, Sắp diễn ra, Đã kết thúc)
  - **Độ phức tạp**: ⭐⭐⭐⭐

#### C. Quản Lý Thuế (THUẾ)

- **Entity**: `Thue.java`, `TrangThaiThue.java`
- **DAO**: `ThueDAO.java`
- **Service**: `ThueService.java`
- **Yêu cầu**:
  - ✅ CRUD thuế
  - ✅ Kiểm tra % thuế hợp lệ (0-100)
  - ✅ Lấy thuế hoạt động
  - ✅ Tính tiền thuế = (Tổng tiền \* % thuế)
  - ✅ Trạng thái thuế (Hoạt động, Ngừng sử dụng)
  - **Độ phức tạp**: ⭐⭐

#### D. Quản Lý Phương Thức Thanh Toán (PHUƠNG THỨC TT)

- **Entity**: `PhuongThucTT.java`
- **DAO**: `PhuongThucTTDAO.java`
- **Service**: `PhuongThucTTService.java`
- **Yêu cầu**:
  - ✅ CRUD phương thức thanh toán
  - ✅ Lấy danh sách phương thức hoạt động
  - ✅ Hỗ trợ nhiều loại (Tiền mặt, Chuyển khoản, Thẻ, E-wallet)
  - **Độ phức tạp**: ⭐⭐

**📌 Tổng công việc**: ~1400 LOC

---

## 📊 THỐNG KÊ PHÂN CÔNG

| Thành Viên | Modules | Services | DAOs   | Entities | LOC (~ dòng) | Độ Khó     | % Công Việc |
| ---------- | ------- | -------- | ------ | -------- | ------------ | ---------- | ----------- |
| **LONG**   | 5       | 7        | 8      | 9        | ~2500        | ⭐⭐⭐⭐⭐ | **30%**     |
| **MAY**    | 4       | 4        | 4      | 6        | ~1500        | ⭐⭐⭐     | **25%**     |
| **TRÍ**    | 4       | 4        | 4      | 5        | ~1800        | ⭐⭐⭐⭐   | **26%**     |
| **KHOA**   | 4       | 4        | 4      | 5        | ~1400        | ⭐⭐⭐     | **19%**     |
| **TOTAL**  | **17**  | **19**   | **20** | **25**   | ~7200        | —          | **100%**    |

---

## 🎯 TIMELINE ĐỀ XUẤT

### Phase 1: Xây Dựng Cơ Sở (Tuần 1-2)

- **LONG**: Setup SessionManager, AuthService, TaiKhoanDAO
- **MAY**: Xây dựng BanDAO, KhuVucDAO, LoaiMonAnDAO
- **TRÍ**: Xây dựng ChucVuDAO, QuyenHanDAO, NhanVienDAO
- **KHOA**: Xây dựng KhachHangDAO, KhuyenMaiDAO, ThueDAO

### Phase 2: Xây Dựng Service (Tuần 2-3)

- Mỗi thành viên xây dựng Service tương ứng
- Implement CRUD, validation, business logic

### Phase 3: Tích Hợp Giao Dịch (Tuần 3-4)

- **LONG**: Hoàn chỉnh HoaDonService, PhieuGoiMonService, PhieuDatBanService
- Các thành viên khác: Testing, support, fix bugs

### Phase 4: Testing & Optimization (Tuần 4-5)

- Unit testing
- Integration testing
- Performance optimization

---

## 📝 HƯỚNG DẪN PHÁT TRIỂN

### Cấu Trúc Mỗi Module

```
Entity.java (Model - Getter/Setter)
    ↓
EntityDAO.java (Database operations - CRUD)
    ↓
EntityService.java (Business logic - Validation)
    ↓
GUI (UI - Gọi Service)
```

### Naming Convention

- **Entity**: `PassCase` (HoaDon, NhanVien, BanAn)
- **DAO**: `EntityDAO` (HoaDonDAO, NhanVienDAO)
- **Service**: `EntityService` (HoaDonService, NhanVienService)
- **Method**: `camelCase` (getAll, getById, create, update, delete, search)

### Error Handling

```java
try {
    // Operation
} catch (SQLException e) {
    throw new RuntimeException("Lỗi: " + e.getMessage());
} catch (Exception e) {
    throw new RuntimeException("Lỗi không xác định");
}
```

### Validation Example

```java
public String validate() {
    if (ten.isEmpty()) return "Tên không được để trống";
    if (ten.length() > 100) return "Tên không vượt quá 100 ký tự";
    return null; // Valid
}
```

---

## 🔗 DEPENDENCIES GIỮA MODULES

```
TaiKhoan → NhanVien → ChucVu → QuyenHan (LONG + TRÍ)
                   ↓
              PhieuDatBan → Ban → KhuVuc (TRÍ + MAY)
                        ↓
                   KhachHang (KHOA)
                        ↓
PhieuGoiMon → MonAn → LoaiMonAn → Ban (LONG + MAY)
       ↓
    HoaDon → KhuyenMai + Thue + PhuongThucTT (LONG + KHOA)
```

---

## 📌 NOTES

- ✅ **LONG** là người approve code review
- ✅ Mỗi developer commit code trước khi hết ca
- ✅ Code phải chạy test trước khi merge
- ✅ Naming convention phải thống nhất
- ✅ Comment code tiếng Anh (để các dev khác hiểu)
- ✅ Không modify database schema của nhau tùy tiện

---

## 🚀 BƯỚC TIẾP THEO

1. **LONG**: Tạo base structure, setup SessionManager
2. **Tất cả**: Pull code mới nhất từ LONG
3. **Mỗi người**: Bắt đầu với DAO của module riêng
4. **Hàng ngày**: Sync code, report progress đến LONG
