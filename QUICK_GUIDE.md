# CHIẾN LƯỢC PHÁT TRIỂN BACKEND - QUICK GUIDE

## 👥 PHÂN CÔNG TẮT GỌN

### 🟦 **LONG** (30% - Trưởng nhóm)

```
Module: Giao Dịch (Hóa Đơn, Phiếu Gọi, Phiếu Đặt Bàn) + Auth
Files: HoaDonDAO/Service, PhieuGoiMonDAO/Service, PhieuDatBanDAO/Service
       SessionManager, AuthService, TaiKhoanDAO
Độ khó: ⭐⭐⭐⭐⭐ (Cao nhất)
LOC: ~2500 dòng
```

### 🟩 **MAY** (25% - Quản Lý Không Gian)

```
Modules: Bàn, Khu Vực, Loại Món, Món Ăn
Files: BanDAO/Service, KhuVucDAO/Service, LoaiMonAnDAO/Service, MonAnDAO/Service
Độ khó: ⭐⭐⭐ (Trung bình)
LOC: ~1500 dòng
```

### 🟪 **TRÍ** (26% - Quản Lý Nhân Viên & Quyền)

```
Modules: Nhân Viên, Chức Vụ, Quyền Hạn, Tài Khoản
Files: NhanVienDAO/Service, ChucVuDAO/Service, QuyenHanDAO/Service, TaiKhoanDAO/Service
Độ khó: ⭐⭐⭐⭐ (Trên trung bình)
LOC: ~1800 dòng
```

### 🟨 **KHOA** (19% - Tùy Chỉnh Kinh Doanh)

```
Modules: Khách Hàng, Khuyến Mãi, Thuế, Phương Thức TT
Files: KhachHangDAO/Service, KhuyenMaiDAO/Service, ThueDAO/Service, PhuongThucTTDAO/Service
Độ khó: ⭐⭐⭐ (Trung bình)
LOC: ~1400 dòng
```

---

## ✅ CHECKLIST ĐỐI VỚI MỖI MODULE

### DAO Layer

- [ ] Xây dựng class Entity (Model)
- [ ] Implement CRUD: `create()`, `getById()`, `getAll()`, `update()`, `delete()`
- [ ] Implement search methods (nếu cần)
- [ ] SQL queries sạch, có comment
- [ ] Try-catch với SQLException

### Service Layer

- [ ] Implement business logic methods
- [ ] Validate input (null checks, format checks)
- [ ] Gọi DAO thích hợp
- [ ] Xử lý exceptions
- [ ] Error messages rõ ràng

### Testing

- [ ] Unit test CRUD
- [ ] Test validation
- [ ] Test edge cases (empty, null, duplicate)
- [ ] Integration test với các module liên quan

---

## 🔄 GIT WORKFLOW

```bash
# 1. Tạo branch riêng
git checkout -b feature/module-name

# 2. Commit từng phần
git add .
git commit -m "feat: implement BanDAO CRUD"

# 3. Push khi xong feature
git push origin feature/module-name

# 4. Create Pull Request
# → LONG review → Merge

# 5. Update local main
git checkout main
git pull origin main
```

---

## 📞 CONTACT & COORDINATION

| Thành Viên | Module                                   | Contact |
| ---------- | ---------------------------------------- | ------- |
| **LONG**   | Hóa Đơn, Phiếu Gọi, Phiếu Đặt Bàn, Auth  | Lead    |
| **MAY**    | Bàn, Khu Vực, Loại Món, Món Ăn           | Backend |
| **TRÍ**    | Nhân Viên, Chức Vụ, Quyền Hạn, Tài Khoản | Backend |
| **KHOA**   | Khách Hàng, Khuyến Mãi, Thuế, PT TT      | Backend |

---

## 📅 TIMELINE

| Tuần | Milestone                 | Người Phụ Trách |
| ---- | ------------------------- | --------------- |
| 1-2  | Base structure + DAO      | Tất cả          |
| 2-3  | Service layer             | Tất cả          |
| 3-4  | Integration + Transaction | LONG            |
| 4-5  | Testing & Bug fix         | Tất cả          |

---

## 🎓 CODE EXAMPLES

### DAO Pattern

```java
public class BanDAO {
    public Ban create(Ban ban) throws SQLException {
        String sql = "INSERT INTO Ban(maBan, tenBan, maKhuVuc) VALUES(?, ?, ?)";
        try (PreparedStatement pstm = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, ban.getMaBan());
            pstm.setString(2, ban.getTenBan());
            pstm.setString(3, ban.getKhuVuc().getMaKhuVuc());
            pstm.executeUpdate();
            return ban;
        } catch (SQLException e) {
            throw new SQLException("Lỗi: " + e.getMessage());
        }
    }
}
```

### Service Pattern

```java
public class BanService {
    private BanDAO banDAO = new BanDAO();

    public String taoMoi(Ban ban) {
        // Validate
        if (ban.getMaBan().isEmpty()) return "Mã bàn không được để trống";

        // Check exists
        if (banDAO.getById(ban.getMaBan()) != null) {
            return "Mã bàn đã tồn tại";
        }

        // Create
        try {
            banDAO.create(ban);
            return null; // Success
        } catch (SQLException e) {
            return "Lỗi thêm bàn: " + e.getMessage();
        }
    }
}
```

---

## 🚨 IMPORTANT NOTES

1. **Không hardcode** thông tin database
2. **Luôn validate** input trước khi process
3. **Sử dụng PreparedStatement** để tránh SQL injection
4. **Đóng connection** sau khi dùng (try-with-resources)
5. **Log errors** để debug dễ hơn
6. **Test thường xuyên** không chỉ lúc xong
7. **Comment code** tiếng Anh để team khác hiểu

---

## 📊 PROGRESS TRACKING

```
LONG:     [████████████████░░░░░] 60% (Phase 3)
MAY:      [████████████░░░░░░░░░] 40% (Phase 2)
TRÍ:      [████████████░░░░░░░░░] 40% (Phase 2)
KHOA:     [████████░░░░░░░░░░░░░] 30% (Phase 1)

Overall: [████████████░░░░░░░░░░] 40% (On Track) ✅
```

---

**Last Updated**: 2026-04-08  
**Project**: QuanLyNhaHangTranLong (Restaurant Management System)
