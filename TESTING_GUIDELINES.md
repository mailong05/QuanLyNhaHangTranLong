# Testing Multi-Panel Navigation

## Quick Start - Các bước để test

### Chuẩn bị

1. Compile project: `mvn clean compile`
2. Run application: `mvn exec:java -Dexec.mainClass="com.restaurant.quanlydatbannhahang.main.App"`

### Test Scenario 1: Basic Flow (DatBan → DatMon → LapHoaDon)

**Bước 1: Mở ứng dụng**

- Login vào hệ thống
- Vào menu "Quản lý đặt bàn"

**Bước 2: Chuyển đến PanelQuanLyDatBanTruoc**

- Bấm vào mục "Đặt bàn trước" trong menu
- Xem bảng danh sách các đặt bàn

**Bước 3: Mở PanelDatMon**

- Click button "Chọn Món"
- ✅ Kiểm tra: Mở PanelDatMon với combo box loại món và table danh sách món

**Bước 4: Chọn một số món**

- Chọn từ combo "Loại món ăn" (ví dụ: Khai vị)
- Click "Tìm kiếm" hoặc gõ tên món
- Chọn 2-3 món từ table bên phải
- ✅ Kiểm tra: Các món xuất hiện trong "Phiếu gọi món" (table bên trái)
- ✅ Kiểm tra: Tổng tiền tạm tính được cập nhật

**Bước 5: Nhấn "Thanh toán"**

- Click button "Thanh toán" (bên dưới phải)
- ✅ Kiểm tra: Chuyển sang PanelLapHoaDon
- ✅ Kiểm tra: Title thay đổi thành "LẬP HÓA ĐƠN"

**Bước 6: Xem hóa đơn**

- Xem bảng "Thông tin hóa đơn" có danh sách các món vừa chọn
- ✅ Kiểm tra: Các món và số lượng đúng với lần chọn

**Bước 7: Quay lại từ LapHoaDon**

- Click button "Quay lại" (bên trái dưới cùng)
- ✅ Kiểm tra: Quay lại PanelDatMon
- ✅ Kiểm tra: Title là "ĐẶT MÓN"
- ✅ **QUAN TRỌNG**: Các món trong "Phiếu gọi món" vẫn còn (KHÔNG bị xóa)
- ✅ **QUAN TRỌNG**: Tổng tiền tạm tính vẫn giữ nguyên

---

### Test Scenario 2: Quay lại từ DatMon

**Bước 1: Từ PanelDatMon - Bấm "Quay lại"**

- Từ kết quả Scenario 1 bước 7 (đang ở PanelDatMon)
- Click button "Quay lại" (góc dưới trái)
- ✅ Kiểm tra: Quay lại PanelQuanLyDatBanTruoc
- ✅ Kiểm tra: Title là "TRANG CHỦ" hoặc tên panel trước đó

**Bước 2: Mở lại PanelDatMon**

- Click "Chọn Món" một lần nữa
- ✅ Kiểm tra: **Các món trước vẫn còn trong "Phiếu gọi món"**
- ✅ Kiểm tra: Trạng thái được giữ nguyên

---

### Test Scenario 3: Toàn Flow (Long Journey)

**Dòng chảy đầy đủ:**

```
PanelQuanLyDatBanTruoc → PanelDatMon → PanelLapHoaDon → PanelDatMon → PanelQuanLyDatBanTruoc
```

**Thực hiện:**

1. Bắt đầu ở PanelQuanLyDatBanTruoc
2. Click "Chọn Món" → PanelDatMon
   - Chọn 3 món A, B, C
   - Tổng tiền: 150,000 VND
3. Click "Thanh toán" → PanelLapHoaDon
   - Xem hóa đơn hiển thị 3 món A, B, C
4. Click "Quay lại" → PanelDatMon
   - ✅ Vẫn thấy 3 món A, B, C
   - ✅ Tổng tiền: 150,000 VND
5. Chọn thêm 1 món D
   - ✅ Bảng hiển thị 4 món: A, B, C, D
6. Click "Thanh toán" → PanelLapHoaDon
   - ✅ Xem hóa đơn hiển thị 4 món
7. Click "Quay lại" → PanelDatMon
8. Click "Quay lại" → PanelQuanLyDatBanTruoc
   - ✅ Khi quay lại menu chính

---

### Test Scenario 4: Đóng/Mở panel liên tục

**Mục đích:** Kiểm tra rằng panel không bị reset

**Bước:**

1. Vào PanelDatMon, chọn 2 món
2. Quay lại PanelQuanLyDatBanTruoc
3. Vào menu khác (ví dụ: "Quản lý khách hàng")
4. Quay lại "Đặt bàn trước" → PanelQuanLyDatBanTruoc
5. Click "Chọn Món" → PanelDatMon
   - ✅ Kiểm tra: 2 món vẫn còn ở "Phiếu gọi món"

---

## Các điểm cần kiểm tra (Checklist)

### Bảo toàn dữ liệu ✓

- [ ] Khi quay lại từ LapHoaDon → DatMon, các món vẫn còn
- [ ] Khi quay lại từ DatMon → DatBan → DatMon lại, các món vẫn còn
- [ ] Tổng tiền tạm tính không bị reset

### Navigation Flow ✓

- [ ] DatBan → DatMon: Mở đúng panel
- [ ] DatMon → LapHoaDon: Chuyển sang đúng panel
- [ ] LapHoaDon → DatMon: Quay lại đúng panel
- [ ] DatMon → DatBan: Quay lại đúng panel trước

### Title Update ✓

- [ ] Title hiển thị "ĐẶT MÓN" khi ở PanelDatMon
- [ ] Title hiển thị "LẬP HÓA ĐƠN" khi ở PanelLapHoaDon
- [ ] Title hiển thị đúng khi quay lại

### UI Elements ✓

- [ ] Các button "Chọn Món" có thể click
- [ ] Button "Thanh toán" có thể click ở PanelDatMon
- [ ] Button "Quay lại" có thể click ở PanelDatMon
- [ ] Button "Quay lại" có thể click ở PanelLapHoaDon

---

## Các vấn đề có thể gặp

### Vấn đề 1: "CompilationException" khi chạy

**Nguyên nhân:** Các file chưa được compile đúng cách
**Giải pháp:**

```bash
mvn clean compile
```

### Vấn đề 2: Panel hiển thị trắng/không có nội dung

**Nguyên nhân:** Panel không được khởi tạo đúng
**Giải pháp:**

- Kiểm tra xem `initComponents()` đã được gọi
- Kiểm tra xem `customUI()` được gọi trong constructor

### Vấn đề 3: Data bị reset khi quay lại

**Nguyên nhân:** Panel instance không được lưu trữ đúng
**Giải pháp:**

- Kiểm tra `MainForm.panelDatMon` không null
- Kiểm tra `showPanel()` không tạo instance mới

### Vấn đề 4: Button không hoạt động

**Nguyên nhân:** Action listener không được gán
**Giải pháp:**

- Kiểm tra `customUI()` được gọi
- Kiểm tra `addActionListener()` được khai báo

---

## Debug Tips

### Để xem debug info, thêm log vào handler:

```java
private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {
    System.out.println("DEBUG: btnThanhToan clicked");
    System.out.println("DEBUG: panelDatMon instance: " + panelDatMon);

    java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
    System.out.println("DEBUG: Parent frame: " + parentFrame);

    if (parentFrame instanceof MainForm) {
        System.out.println("DEBUG: Opening PanelLapHoaDon");
        ((MainForm) parentFrame).openPanelLapHoaDon();
    }
}
```

### Kiểm tra state của panel:

```java
// Thêm vào MainForm.java
public void printPanelState() {
    System.out.println("=== Panel State ===");
    System.out.println("panelDatMon: " + (panelDatMon != null ? "CREATED" : "null"));
    System.out.println("panelLapHoaDon: " + (panelLapHoaDon != null ? "CREATED" : "null"));
    System.out.println("lastVisitedPanel: " + lastVisitedPanel);
    System.out.println("Active Panel: " + activePanel);
}
```

---

## Kết quả mong đợi

Sau khi test xong, bạn sẽ có:

- ✅ Multi-panel navigation hoạt động mượt mà
- ✅ Dữ liệu được bảo toàn khi chuyển đổi panelx
- ✅ UX tốt hơn (không phải chọn món lại khi quay lại)
- ✅ Áp dụng được cho nhiều workflow tương tự khác

---

**Lưu ý:** Nếu bạn gặp bất kỳ vấn đề nào, hãy kiểm tra:

1. Đã compile lại project chưa?
2. Đã khởi động lại application chưa?
3. Đã xem console log để hiểu lỗi chính xác không?
