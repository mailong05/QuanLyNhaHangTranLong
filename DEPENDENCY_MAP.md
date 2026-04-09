# DEPENDENCY & INTEGRATION MAP

## 📐 ARCHITECTURE LAYERS

```
┌─────────────────────────────────────────────┐
│           GUI (Frontend)                     │
│  ❌ Không phải công việc backend            │
└─────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────┐
│        SERVICE LAYER (Business Logic)        │
│  AuthService, HoaDonService, BanService...  │
│  👤 LONG, MAY, TRÍ, KHOA phát triển         │
└─────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────┐
│          DAO LAYER (Data Access)             │
│  HoaDonDAO, BanDAO, NhanVienDAO...          │
│  👤 LONG, MAY, TRÍ, KHOA phát triển         │
└─────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────┐
│          DATABASE (SQL Server)               │
│  Tables: Ban, HoaDon, NhanVien, ...         │
│  ❌ Schema đã sẵn (không modify)            │
└─────────────────────────────────────────────┘
```

---

## 🔗 MODULE DEPENDENCIES

### Group 1: LONG (Giao Dịch - Nhân Viên)

```
┌─────────────────────────────────────────────────┐
│ 🔒 CRITICAL PATH (Phải làm trước)              │
├─────────────────────────────────────────────────┤
│ 1. TaiKhoanDAO/Service (LONG + TRÍ)            │
│    └─ Input: Username, Password                │
│    └─ Output: TaiKhoan object                  │
│                                                 │
│ 2. NhanVienDAO/Service (TRÍ)                   │
│    └─ Dependency: TaiKhoan                     │
│    └─ Output: NhanVien object                  │
│                                                 │
│ 3. SessionManager (LONG)                       │
│    └─ Dependency: TaiKhoan, NhanVien           │
│    └─ Output: Current user session             │
│                                                 │
│ 4. AuthService (LONG)                          │
│    └─ Dependency: TaiKhoanDAO, SessionManager  │
│    └─ Output: Login/Logout                     │
└─────────────────────────────────────────────────┘
```

### Group 2: MAY (Không Gian - Bàn Ăn)

```
┌─────────────────────────────────────────────────┐
│ 🏢 INFRASTRUCTURE (Setup cơ sở dữ liệu)        │
├─────────────────────────────────────────────────┤
│ 1. KhuVucDAO/Service (MAY)                     │
│    └─ Input: Tên khu vực                       │
│    └─ Output: KhuVuc object                    │
│                                                 │
│ 2. BanDAO/Service (MAY)                        │
│    └─ Dependency: KhuVuc                       │
│    └─ Output: Ban object                       │
│                                                 │
│ 3. LoaiMonAnDAO/Service (MAY)                  │
│    └─ Input: Tên loại                          │
│    └─ Output: LoaiMonAn object                 │
│                                                 │
│ 4. MonAnDAO/Service (MAY)                      │
│    └─ Dependency: LoaiMonAn, Ban               │
│    └─ Output: MonAn object                     │
└─────────────────────────────────────────────────┘
```

### Group 3: TRÍ (Quản Lý Nhân Công)

```
┌─────────────────────────────────────────────────┐
│ 👥 HUMAN RESOURCES (Nhân sự)                   │
├─────────────────────────────────────────────────┤
│ 1. ChucVuDAO/Service (TRÍ)                     │
│    └─ Input: Tên chức vụ                       │
│    └─ Output: ChucVu object                    │
│                                                 │
│ 2. QuyenHanDAO/Service (TRÍ)                   │
│    └─ Input: Tên quyền                         │
│    └─ Output: QuyenHan object                  │
│                                                 │
│ 3. NhanVienDAO/Service (TRÍ)                   │
│    └─ Dependency: ChucVu                       │
│    └─ Output: NhanVien object                  │
│                                                 │
│ 4. TaiKhoanDAO/Service (TRÍ)                   │
│    └─ Dependency: NhanVien                     │
│    └─ Output: TaiKhoan object (Username/Pass)  │
└─────────────────────────────────────────────────┘
```

### Group 4: KHOA (Tùy Chỉnh Kinh Doanh)

```
┌─────────────────────────────────────────────────┐
│ 💼 BUSINESS CUSTOMIZATION                      │
├─────────────────────────────────────────────────┤
│ 1. KhachHangDAO/Service (KHOA)                 │
│    └─ Input: Tên, SĐT                          │
│    └─ Output: KhachHang object                 │
│                                                 │
│ 2. KhuyenMaiDAO/Service (KHOA)                 │
│    └─ Input: Tên, % giảm, Ngày                 │
│    └─ Output: KhuyenMai object                 │
│                                                 │
│ 3. ThueDAO/Service (KHOA)                      │
│    └─ Input: Tên, % thuế                       │
│    └─ Output: Thue object                      │
│                                                 │
│ 4. PhuongThucTTDAO/Service (KHOA)              │
│    └─ Input: Tên PT (Tiền Mặt, Thẻ...)        │
│    └─ Output: PhuongThucTT object              │
└─────────────────────────────────────────────────┘
```

### Group 5: LONG (Giao Dịch Chính)

```
┌─────────────────────────────────────────────────┐
│ 💰 TRANSACTIONS (Lõi hệ thống)                 │
├─────────────────────────────────────────────────┤
│ 1. PhieuGoiMonDAO/Service (LONG)               │
│    └─ Dependency: MonAn, Ban, NhanVien         │
│    └─ Output: PhieuGoiMon object               │
│                                                 │
│ 2. HoaDonDAO/Service (LONG)                    │
│    └─ Dependency: KhachHang, KhuyenMai, Thue   │
│    └─ Output: HoaDon object + Chi tiết         │
│                                                 │
│ 3. PhieuDatBanDAO/Service (LONG)               │
│    └─ Dependency: KhachHang, Ban, NhanVien     │
│    └─ Output: PhieuDatBan object               │
└─────────────────────────────────────────────────┘
```

---

## 📊 EXECUTION ORDER

### ⏱️ Phase 1 (Tuần 1-2): Infrastructure

```
🟩 MAY:  KhuVucDAO → BanDAO, LoaiMonAnDAO
🟪 TRÍ:  ChucVuDAO → QuyenHanDAO → NhanVienDAO → TaiKhoanDAO
🟦 LONG: SessionManager, AuthService
🟨 KHOA: KhachHangDAO, KhuyenMaiDAO, ThueDAO, PhuongThucTTDAO

✅ Condition: Tất cả DAO + Basic Service xong
```

### ⏱️ Phase 2 (Tuần 2-3): Service Layer

```
🟩 MAY:  BanService, LoaiMonAnService, MonAnService, KhuVucService
🟪 TRÍ:  ChucVuService, QuyenHanService, NhanVienService
🟦 LONG: AuthService (hoàn chỉnh)
🟨 KHOA: KhachHangService, KhuyenMaiService, ThueService, PhuongThucTTService

✅ Condition: Business logic, validation, error handling xong
```

### ⏱️ Phase 3 (Tuần 3-4): Transaction Handling

```
🟦 LONG: PhieuGoiMonService → HoaDonService → PhieuDatBanService
         Tính toán tiền, integrate với các service khác

🟩 MAY:  Support LONG - test tích hợp Ban
🟪 TRÍ:  Support LONG - test tích hợp NhanVien
🟨 KHOA: Support LONG - test tích hợp KhachHang, KhuyenMai, Thue

✅ Condition: Hóa đơn, Phiếu gọi, Phiếu đặt bàn hoạt động liền mạch
```

### ⏱️ Phase 4 (Tuần 4-5): Testing & Optimization

```
👥 TẬT CẢ:
  - Unit testing cho từng module
  - Integration testing
  - Performance testing
  - Bug fixes

✅ Condition: Build SUCCESS, 0 compiler errors, all tests pass
```

---

## 🔀 CALL FLOW PATTERN

### Scenario: Tạo Hóa Đơn (Create Invoice)

```
GUI (MainForm/PanelThanhToan)
  ↓
HoaDonService.taohoaDon(khachHang, phieuGoiMon, khuyenMai, thue, phuongThucTT)
  ↓
  ├─ Validation
  │  ├─ Check null (khachHang, phieuGoiMon)
  │  ├─ Check giá trị hợp lệ
  │  └─ Check phiếu gọi có chi tiết không
  ├─ Calculate
  │  ├─ Tổng tiền = PhieuGoiMon.getTongTien()
  │  ├─ Tiền khuyến mãi = Thue.tinhTien()
  │  ├─ Tiền thuế = Thue.tinhTien(totalAfterDiscount)
  │  └─ Thành tiền = Total - Discount + Tax
  ├─ HoaDonDAO.create(hoaDon)
  │  ├─ INSERT INTO HoaDon
  │  ├─ INSERT INTO ChiTietHoaDon (từ PhieuGoiMon)
  │  └─ Return HoaDon object
  └─ Update trạng thái
     └─ Ban.updateTrangThai(Ban.TRONG_RONG)
     └─ PhieuGoiMon.updateTrangThai(PhieuGoiMon.DA_THANH_TOAN)
```

---

## ⚠️ CRITICAL PATHS - Phải Làm Đúng

### Must-Have (#1 Priority)

| Task                         | Owner | Deadline | Status |
| ---------------------------- | ----- | -------- | ------ |
| SessionManager + AuthService | LONG  | Day 1    | ⏳     |
| TaiKhoanDAO + NhanVienDAO    | TRÍ   | Day 2    | ⏳     |
| BanDAO + KhuVucDAO           | MAY   | Day 2    | ⏳     |
| KhachHangDAO                 | KHOA  | Day 2    | ⏳     |

### Should-Have (#2 Priority)

| Task                 | Owner  | Deadline | Status |
| -------------------- | ------ | -------- | ------ |
| Tất cả Service layer | Tất cả | Day 14   | ⏳     |
| Integration testing  | LONG   | Day 21   | ⏳     |

### Nice-to-Have (#3 Priority)

| Task                     | Owner  | Deadline | Status |
| ------------------------ | ------ | -------- | ------ |
| Performance optimization | LONG   | Day 28   | ⏳     |
| API documentation        | Tất cả | Day 35   | ⏳     |

---

## 🚀 GIT BRANCH STRATEGY

```
main (protected)
  ├─ develop (integration branch)
  │   ├─ feature/long-transaction
  │   ├─ feature/may-ban-management
  │   ├─ feature/tri-employee-management
  │   └─ feature/khoa-customer-management
  └─ hotfix/* (emergency fixes)
```

### Branching Rules

1. **Tạo branch từ develop**: `git checkout -b feature/module-name`
2. **Commit message**: `[MODULE] action: description`
   - Ví dụ: `[HOADAN] feat: implement create invoice logic`
3. **Push trước hết ca**: Để người khác review
4. **Pull Request**: LONG approve trước khi merge vào develop

---

## 📞 COMMUNICATION PROTOCOL

### Daily Standup (9 AM)

- [ ] What did you complete yesterday?
- [ ] What will you do today?
- [ ] Any blockers?

### Code Review (Before Merge)

- [ ] LONG reviews code
- [ ] Check naming convention
- [ ] Check error handling
- [ ] Check SQL injection prevention
- [ ] Approve → Merge

### Problem Escalation

1. Try to fix 30 mins
2. Ask team on Slack/Group chat
3. Escalate to LONG if still stuck
4. Pair programming if needed

---

**Last Updated**: 2026-04-08  
**Document**: DEPENDENCY & INTEGRATION MAP  
**Status**: 📋 Ready for implementation
