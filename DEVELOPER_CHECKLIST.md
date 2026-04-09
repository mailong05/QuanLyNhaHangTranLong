# ✅ DEVELOPER CHECKLIST - TASK COMPLETION TRACKING

## 👤 LONG - Project Lead (Transaction Layer)

### Phase 1: Setup Infrastructure & Auth

```
□ SessionManager.java - Complete implementation
  □ java.static field: currentTaiKhoan
  □ Methods: getCurrentTaiKhoan(), setCurrentTaiKhoan(), getCurrentNhanVien(), clearSession()
  □ Javadoc comment
  □ Test: Can set and get current user

□ AuthService.java - Complete
  □ Method: login(username, password) → TaiKhoan
  □ Validation: Check empty, trim input
  □ Query: SELECT FROM TaiKhoan JOIN NhanVien
  □ Error handling: SQLException, User not found
  □ Test: Can login with valid credentials

□ Support TaiKhoanDAO (coordinate with TRÍ)
  □ Code review completed
  □ Integration test passed
```

### Phase 2: Phiếu Gọi Món

```
□ ChiTietGoiMonDAO.java
  □ create(ChiTietGoiMon) - OK
  □ getById(id) - OK
  □ getByPhieuId(maPhieu) - Multiple rows
  □ update(ChiTietGoiMon) - OK
  □ delete(id) - OK
  □ Query test: hand-test each SQL

□ ChiTietGoiMonService.java
  □ Validate: quantity > 0, price > 0, valid monAn
  □ calculateTotal() - Sum all items
  □ Search lịch sử gọi món
  □ Test: Multiple items in one phiếu

□ PhieuGoiMonDAO.java
  □ create(PhieuGoiMon) - Get auto-ID
  □ getById(id) - Full object
  □ getByBanId(maBan) - Current phiếu
  □ getByNhanVienId(maNV) - Lịch sử
  □ updateStatus(id, status) - Update trạng thái
  □ Query test: Verify relationships

□ PhieuGoiMonService.java
  □ taoPhieuGoiMon(ban, nhanVien) - Create
  □ themMonAn(phieu, monAn, soLuong) - Add items
  □ Update status workflow (Chờ → Nấu → Xong → Hủy)
  □ getTotalPrice(phieu) - Calculate
  □ Validate: Ban exists, NhanVien exists, MonAn exists
```

### Phase 3: Hóa Đơn (CRITICAL)

```
□ ChiTietHoaDonDAO.java
  □ CRUD complete
  □ Methods: create, getById, getByHoaDonId, update, delete
  □ SQL verified

□ ChiTietHoaDonService.java
  □ Business logic for invoice items
  □ Validate quantity, price
  □ Test calculations

□ HoaDonDAO.java (Complex)
  □ create(HoaDon) - Generate invoice ID
  □ getById(id) - Full details with JOINs
  □ getAll() - List with pagination
  □ getByKhachHangId(maKH) - Customer history
  □ getByNgay(date) - Daily reports
  □ getByTrangThai(status) - Pending, completed
  □ updateStatus(id, newStatus) - Status workflow
  □ Query test: Complex JOINs verified

□ HoaDonService.java (MOST COMPLEX)
  □ taoHoaDon(khachHang, phieuGoiMon, khuyenMai, thue, phuongThucTT)
    □ Validate all inputs (not null)
    □ Check phiếu gọi có chi tiết
    □ Calculate: subtotal → apply discount → apply tax
    □ Create HoaDon + ChiTietHoaDon from PhieuGoiMon
    □ Update Ban status to TRONG_RONG
    □ Update PhieuGoiMon status to DA_THANH_TOAN
    □ Return HoaDon or error message
  □ tinhTienThue(hoaDon, thue) - Apply tax
  □ applyKhuyenMai(hoaDon, khuyenMai) - Apply discount
  □ getHoaDonByKhachHang(maKH) - Customer invoices
  □ refundHoaDon(id) - Cancellation logic
  □ Test: Various scenarios (with/without promotion, with tax, multiple items)
```

### Phase 4: Phiếu Đặt Bàn Trước (Advanced)

```
□ ChiTietPhieuDatBanDAO.java
  □ CRUD operations
  □ Query: List bàn trong phiếu

□ ChiTietPhieuDatBanService.java
  □ Add/remove tables from reservation
  □ Validate table availability

□ PhieuDatBanDAO.java
  □ CRUD complete
  □ getByStatus(status) - Filter by state
  □ getBetweenDates(start, end) - Date range
  □ searchByCustomer(phone) - Customer lookup
  □ checkBanAvailable(maBan, datetime) - Availability check

□ PhieuDatBanService.java (from earlier code)
  □ taoPhieuDatBanMoi() - Create with validation
  □ addBanToPhieu() - Add tables to reservation
  □ checkConflict() - Prevent double-booking
  □ Notifications: Email/SMS when confirmed
  □ Test: Overlapping reservations should fail
```

### Integration & Testing

```
□ All 4 transaction modules tested together
  □ Scenario: Create phiếu gọi → Create hóa đơn → Verify DB
  □ Scenario: Create phiếu đặt bàn → Customer arrives → Auto bill
□ Performance tested with 1000+ records
□ Code review all code → APPROVED
□ Documentation complete
□ Team training completed
```

---

## 👤 MAY - Infrastructure (Space Management)

### Phase 1: Database Setup

```
□ KhuVucDAO.java
  □ create(KhuVuc) - Add new area
  □ getById(maKhuVuc) - Get area details
  □ getAll() - List all areas
  □ update(KhuVuc) - Edit area
  □ delete(maKhuVuc) - Remove area (check if tables exist)
  □ Validation: Check no tables before delete
  □ Test: CRUD operations pass

□ KhuVucService.java
  □ getAllActiveAreas() - For dropdown
  □ validate(KhuVuc) - Validation logic
  □ deleteIfEmpty(maKhuVuc) - Safe delete
  □ Test: Cannot delete area with tables
```

### Phase 2: Ban Management (Core)

```
□ BanDAO.java (Important)
  □ create(Ban) - Add new table
  □ getById(maBan) - Table details
  □ getAll() - All tables
  □ getByKhuVuc(maKhuVuc) - Tables in area
  □ getByTrangThai(status) - Filter by status
  □ update(Ban) - Update details
  □ updateStatus(maBan, newStatus) - Change status
  □ delete(maBan) - Remove table
  □ Query: Test all filters

□ BanService.java
  □ taoMoi(Ban) - Create table
    □ Validate: maBan unique, khuVuc exists
    □ Auto-assign trạng thái = TRONG_RONG
  □ suaBan(Ban) - Update table
  □ getAllBanTrong() - Available tables
  □ updateTrangThai(maBan, status) - Status change
    □ Workflow: TRONG_RONG → CO_KHACH → GOI_DOI → TRONG_RONG
  □ cancelTable(maBan) - Clear table
  □ Test: Cannot delete table with reservation
```

### Phase 3: Loại Món & Món Ăn

```
□ LoaiMonAnDAO.java
  □ create(LoaiMonAn) - New category
  □ getById(maLoai) - Category details
  □ getAll() - All categories
  □ update(LoaiMonAn) - Edit
  □ delete(maLoai) - Remove (if no dishes)
  □ Query test: Category relationships

□ LoaiMonAnService.java
  □ getAllCategories() - For menu
  □ validate(LoaiMonAn) - Category validation
  □ deleteIfEmpty(maLoai) - Safe delete
  □ Test: Cannot delete category with dishes

□ MonAnDAO.java (Complex relationships)
  □ create(MonAn) - Add dish
  □ getById(maMonAn) - Dish details
  □ getAll() - All dishes
  □ getByLoai(maLoai) - Dishes in category
  □ getByTrangThai(status) - Active/inactive
  □ update(MonAn) - Update details
  □ updateGia(maMonAn, giaNew) - Update price
  □ delete(maMonAn) - Remove dish
  □ Query test: JOINs with LoaiMonAn, Ban (if applicable)

□ MonAnService.java
  □ taoMoiMonAn(MonAn) - Create dish
    □ Validate: monAn name unique, loại exists, price > 0
  □ suaMonAn(MonAn) - Update dish
  □ getAllMonAnHoatDong() - Active dishes for menu
  □ updateTrangThai(id, status) - Enable/disable
  □ timKiemMonAn(keyword) - Search by name
  □ Test: Cannot add dish with invalid category
```

### Testing & QA

```
□ All 4 modules tested together
  □ Create khuVuc → Create ban in area → Create monAn → Serve
□ UI integration tested
  □ Dropdown loại món loads correctly
  □ Ban list updates when status changes
□ Database constraints verified
□ Documentation: API contracts for each method
```

---

## 👤 TRÍ - Human Resources

### Phase 1: Chức Vụ & Quyền

```
□ ChucVuDAO.java
  □ create(ChucVu) - New role
  □ getById(maChucVu) - Role details
  □ getAll() - All roles
  □ update(ChucVu) - Edit role
  □ delete(maChucVu) - Remove role (if no employees)
  □ Query test: verify relationships

□ ChucVuService.java
  □ validate(ChucVu) - Role validation
  □ deleteIfEmpty(maChucVu) - Safe delete
  □ getChucVuList() - For dropdowns
  □ Test: Cannot delete role with employees

□ QuyenHanDAO.java
  □ create(QuyenHan) - New permission
  □ getById(maQuyenHan) - Permission details
  □ getAll() - All permissions
  □ getByChucVu(maChucVu) - Permissions for role
  □ update(QuyenHan) - Edit permission
  □ delete(maQuyenHan) - Remove permission
  □ Query test: Many-to-many relationships

□ QuyenHanService.java
  □ getQuyenCuaChucVu(maChucVu) - Get all permissions
  □ hasQuyenHan(nhanVien, quyenCan) - Permission check
  □ addQuyenToChucVu(maChucVu, maQuyenHan) - Assign permission
  □ removeQuyenFromChucVu(maChucVu, maQuyenHan) - Revoke permission
  □ Test: Role-based access control works
```

### Phase 2: Nhân Viên Management

```
□ NhanVienDAO.java (Complex)
  □ create(NhanVien) - Add employee
    □ Include: ho, ten, sdt, email, ngayBatDau, chucVu
  □ getById(maNhanVien) - Employee details
  □ getAll() - All employees
  □ getByChucVu(maChucVu) - Employees with role
  □ getByTrangThai(status) - Active/inactive
  □ getBetweenDates(start, end) - Hired between dates
  □ update(NhanVien) - Update employee
  □ updateStatus(id, status) - Change status
  □ delete(maNhanVien) - Remove (soft delete recommended)
  □ Query test: Complex JOINs with ChucVu

□ NhanVienService.java
  □ taoMoiNhanVien(NhanVien) - Create employee
    □ Validate: email unique, sdt format valid, chucVu exists
    □ Auto-assign: ngayBatDau = today, trạng thái = Hoạt động
  □ suaNhanVien(NhanVien) - Update employee
  □ getNhanVienByChucVu(maChucVu) - Filter by role
  □ timKiemNhanVien(keyword) - Search by name/phone
  □ updateTrangThai(id, status) - Status change (Hoạt động, Nghỉ, Tạm dừng)
  □ changeChucVu(maNhanVien, maChucVuNew) - Promote/transfer
  □ Test: Cannot delete NV with transactions
```

### Phase 3: Tài Khoản (Coordinate with LONG)

```
□ TaiKhoanDAO.java
  □ create(TaiKhoan) - New account
  □ getById(maTaiKhoan) - Account details
  □ getByUsername(username) - Login lookup
  □ getAll() - All accounts
  □ update(TaiKhoan) - Update account
  □ delete(maTaiKhoan) - Remove account
  □ Query test: JOINs with NhanVien

□ TaiKhoanService.java
  □ taoMoiTaiKhoan(username, password, nhanVien) - Create account
    □ Validate: username unique, password >= 6 chars
    □ Hash password: use BCrypt or SHA256 (NOT plaintext!)
  □ suaNhanVien() - Update account
  □ doiMatKhau(username, oldPass, newPass) - Change password
  □ resetMatKhau(username) - Admin reset
  □ verifyTaiKhoan(username, password) - For login (calls AuthService)
  □ deactivate(username) - Disable account
  □ Test: Password hashing works, cannot guess plain password
```

### Integration Testing

```
□ All HR modules integrated
  □ Create chucVu → Create NV → Create TaiKhoan → Can login
  □ Change quyền → User sees new menu options
□ Tested: Cannot delete role/position with employees using it
□ Documentation: HR module API
```

---

## 👤 KHOA - Business Customization

### Phase 1: Khách Hàng

```
□ KhachHangDAO.java
  □ create(KhachHang) - Add customer
  □ getById(maKhachHang) - Customer details
  □ getAll() - All customers
  □ getBySoDienThoai(sdt) - Lookup by phone
  □ getByEmail(email) - Lookup by email (if exists)
  □ update(KhachHang) - Update customer
  □ delete(maKhachHang) - Remove customer (optional)
  □ Query test: Indexes on sdt, email for fast lookup

□ KhachHangService.java
  □ taoMoiKhachHang(KhachHang) - Register customer
    □ Validate: sdt format, email format, tên not empty
    □ Check: sdt không trùng
  □ suaThongTin(KhachHang) - Update customer
  □ timKiemKhachHang(keyword) - Search by name/phone
  □ getOrCreateKhachHang(sdt) - Get existing or create new
    □ Used when creating invoice
  □ Test: Cannot create duplicate phone numbers
```

### Phase 2: Khuyến Mãi

```
□ KhuyenMaiDAO.java
  □ create(KhuyenMai) - New promotion
  □ getById(maKhuyenMai) - Details
  □ getAll() - All promotions
  □ getActive() - Current active promotions
  □ getBetweenDates(start, end) - Date range
  □ update(KhuyenMai) - Edit promotion
  □ delete(maKhuyenMai) - Remove promotion
  □ Query test: Date range queries

□ KhuyenMaiService.java
  □ taoMoiKhuyenMai(KhuyenMai) - Create promotion
    □ Validate: ngayBatDau < ngayKetThuc, giaTri > 0
    □ Check: Type (% or fixed amount)
  □ suaKhuyenMai(KhuyenMai) - Update promotion
  □ getKhuyenMaiHienHanh() - Current active (for display)
  □ tinhTienGiam(totalPrice, khuyenMai) - Calculate discount
    □ If toàn %: discount = totalPrice * % / 100
    □ If fixed: discount = fixed amount (cap at totalPrice)
  □ Test: Discount within start-end dates only
```

### Phase 3: Thuế

```
□ ThueDAO.java
  □ create(Thue) - New tax
  □ getById(maThue) - Tax details
  □ getAll() - All taxes
  □ getActive() - Active taxes
  □ update(Thue) - Edit tax
  □ delete(maThue) - Remove tax
  □ Query test: Status queries

□ ThueService.java
  □ taoMoiThue(Thue) - Create tax
    □ Validate: 0 <= % <= 100
  □ suaThue(Thue) - Update tax
  □ getThueSuDung() - Currently applied taxes
  □ tinhTienThue(amount, thue) - Calculate tax
    □ taxAmount = amount * % / 100
  □ Test: Tax calculation accuracy (handle decimals)
```

### Phase 4: Phương Thức Thanh Toán

```
□ PhuongThucTTDAO.java
  □ create(PhuongThucTT) - New payment method
  □ getById(maPhuongThuc) - Method details
  □ getAll() - All methods
  □ getActive() - Active methods
  □ update(PhuongThucTT) - Edit method
  □ delete(maPhuongThuc) - Remove method
  □ Query test: Simple CRUD

□ PhuongThucTTService.java
  □ taoMoiPhuongThuc(PhuongThucTT) - Create method
  □ suaPhuongThuc(PhuongThucTT) - Update
  □ getDanhSachPhuongThuc() - For dropdown at checkout
  □ getMoTaPhuongThuc(maPhuongThuc) - Description
  □ Test: Payment methods load in UI
```

### Integration Testing

```
□ All 4 modules integrated
  □ Create invoice: Choose khách → Apply khuyến mãi → Add thuế → Choose PT thanh toán
□ Calculation verification
  □ 100k → 20% discount = 80k → 10% tax = 80k + 8k = 88k
□ Documentation: Business logic APIs
```

---

## 🎯 OVERALL PROJECT CHECKLIST

### Pre-Development

- [x] Project structure created
- [x] Maven configured
- [x] Database connected
- [x] .gitignore setup
- [x] Task assignment distributed

### Development (Ongoing)

- [ ] Phase 1: Infrastructure (By Day 5)
- [ ] Phase 2: Service Layer (By Day 12)
- [ ] Phase 3: Transactions (By Day 19)
- [ ] Phase 4: Testing (By Day 28)

### Code Quality

- [ ] No compiler errors
- [ ] No undefined symbols
- [ ] All SQL queries tested
- [ ] Try-catch blocks present
- [ ] Naming conventions followed
- [ ] Comments for complex logic
- [ ] No hardcoded values
- [ ] PreparedStatement used (prevent SQL injection)

### Testing Checklist

- [ ] Unit tests: CRUD operations
- [ ] Unit tests: Validation logic
- [ ] Unit tests: Calculation logic
- [ ] Integration tests: Module to module
- [ ] Database tests: Data integrity
- [ ] UI tests: GUI → Service → DAO chain
- [ ] Regression tests: Old features still work

### Documentation

- [ ] API contracts documented
- [ ] Database schema documented (if changed)
- [ ] Error codes documented
- [ ] Deployment notes written
- [ ] Installation guide prepared

### Deployment

- [ ] Code merged to main
- [ ] Build successful: `mvn clean package`
- [ ] JAR generated: target/QuanLyDatBanNhaHang-1.0-SNAPSHOT.jar
- [ ] Application runs: `mvn exec:java`
- [ ] Database migrated
- [ ] Initial data loaded
- [ ] UAT complete

---

## 📈 PROGRESS TRACKING

### Week 1

```
LONG:  ████░░░░░░░░░░░░ 20% (Auth setup)
MAY:   ████░░░░░░░░░░░░ 20% (DAO foundation)
TRÍ:   ████░░░░░░░░░░░░ 20% (HR foundation)
KHOA:  ████░░░░░░░░░░░░ 20% (Business setup)
```

### Week 2

```
LONG:  ████████░░░░░░░░ 40% (Transaction DAOs)
MAY:   ████████░░░░░░░░ 40% (All DAOs)
TRÍ:   ████████░░░░░░░░ 40% (All DAOs)
KHOA:  ████████░░░░░░░░ 40% (All DAOs)
```

### Week 3

```
LONG:  ███████████░░░░░░ 60% (Service layer)
MAY:   ███████████░░░░░░ 60% (Services + Testing)
TRÍ:   ███████████░░░░░░ 60% (Services + Testing)
KHOA:  ███████████░░░░░░ 60% (Services + Testing)
```

### Week 4

```
LONG:  ████████████████░ 90% (Integration)
MAY:   ███████████████░░ 85% (Bug fixes)
TRÍ:   ███████████████░░ 85% (Bug fixes)
KHOA:  ███████████████░░ 85% (Bug fixes)
```

### Week 5

```
LONG:  ██████████████████ 100% (Complete + Review)
MAY:   ██████████████████ 100% (Complete + Testing)
TRÍ:   ██████████████████ 100% (Complete + Testing)
KHOA:  ██████████████████ 100% (Complete + Testing)
```

---

**Generated**: 2026-04-08  
**Project**: QuanLyNhaHangTranLong v1.0  
**Team**: Long, May, Trí, Khoa  
**Status**: 🟢 Ready to start
