# Combobox Filter Logic and String Comparison Issues Report

## Overview

This report identifies all combobox filter selections, comparisons, and string comparison issues across 10 GUI panel files. The primary issue is comparing String values from UI comboboxes with enum `getDisplayName()` values.

---

## 1. PanelDanhSachBan.java

### Issue 1a: Filter by Trạng Thái (Status) - Enum Display Name Comparison

**Location:** [searchAndFilter() method](PanelDanhSachBan.java#L260-L310)

**Code (Lines 260-275):**

```java
private void searchAndFilter() {
    DefaultTableModel model = (DefaultTableModel) tableBan.getModel();
    model.setRowCount(0);
    String searchText = txtTimKiem.getText().trim().toLowerCase();
    String selectedKhuVuc = (String) cbFilterKhuVuc.getSelectedItem();
    String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();

    for (Ban ban : allBans) {
        // Check filters
        if (selectedKhuVuc != null && !selectedKhuVuc.equals("Khu vực")) {
            if (ban.getKhuVuc() == null || !ban.getKhuVuc().getMaKhuVuc().equals(selectedKhuVuc)) {
                continue;
            }
        }

        if (selectedTrangThai != null && !selectedTrangThai.equals("Trạng thái")) {
            if (!ban.getTrangThai().getDisplayName().equals(selectedTrangThai)) {  // ❌ LINE 275
                continue;
            }
        }
```

**Problem:**

- Line 275: Compares `selectedTrangThai` (String from combobox) with `ban.getTrangThai().getDisplayName()`
- The combobox contains enum display names (loaded by `ComboBoxEnumLoader.loadTrangThaiBanToComboBox()`)
- This comparison should work IF the combobox is properly loaded, but it's fragile

**Details:**

- ComboBox loading: `ComboBoxEnumLoader.loadTrangThaiBanToComboBox(cbFilterTrangThai)` (Line 36)
- Comparison line: `if (!ban.getTrangThai().getDisplayName().equals(selectedTrangThai))` (Line 275)

### Issue 1b: Table Display Using toString() Instead of getDisplayName()

**Location:** [loadDataToTable() method](PanelDanhSachBan.java#L105-L120)

**Code (Lines 105-120):**

```java
private void loadDataToTable() {
    try {
        allBans = banService.getAllBan();
        DefaultTableModel model = (DefaultTableModel) tableBan.getModel();
        model.setRowCount(0);

        for (Ban ban : allBans) {
            model.addRow(new Object[] {
                    ban.getMaBan(),
                    ban.getSoGhe(),
                    ban.getViTri(),
                    ban.getKhuVuc() != null ? ban.getKhuVuc().getMaKhuVuc() : "",
                    ban.getTrangThai() != null ? ban.getTrangThai().toString() : ""  // ❌ LINE 113
            });
        }
```

**Problem:**

- Line 113: Uses `ban.getTrangThai().toString()` instead of `.getDisplayName()`
- `toString()` returns enum name (e.g., "CON_SU_DUNG") not display name (e.g., "Còn sử dụng")
- Table displays raw enum names instead of user-friendly display names
- **Inconsistent with filter comparison** which uses `getDisplayName()`

---

## 2. PanelDanhSachThue.java

### Issue 2a: Filter by Trạng Thái - Enum Display Name Comparison

**Location:** [searchAndFilter() method](PanelDanhSachThue.java#L130-L165)

**Code (Lines 130-145):**

```java
private void searchAndFilter() {
    DefaultTableModel model = (DefaultTableModel) tableThue.getModel();
    model.setRowCount(0);
    String searchText = txtTimKiem.getText().trim().toLowerCase();
    String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();

    for (Thue thue : allThue) {
        // Check filters
        if (selectedTrangThai != null && !selectedTrangThai.equals("Trạng thái")) {
            if (!thue.getTrangThai().getDisplayName().equals(selectedTrangThai)) {  // ❌ LINE 140
                continue;
            }
        }
```

**Problem:**

- Line 140: Compares `selectedTrangThai` (String from combobox) with `thue.getTrangThai().getDisplayName()`
- ComboBox is loaded by: `ComboBoxEnumLoader.loadTrangThaiThueToComboBox(cbFilterTrangThai)` (Line 23)
- Same issue as Ban: fragile String comparison

### Issue 2b: Table Display Inconsistency

**Location:** [loadDataToTable() method](PanelDanhSachThue.java#L93-L107)

**Code (Lines 93-107):**

```java
private void loadDataToTable() {
    try {
        allThue = thueService.getAllThue();
        DefaultTableModel model = (DefaultTableModel) tableThue.getModel();
        model.setRowCount(0);

        for (Thue thue : allThue) {
            model.addRow(new Object[] {
                    thue.getMaThue(),
                    thue.getTenThue(),
                    thue.getThueSuat(),
                    thue.getTrangThai() == TrangThaiThue.CON_AP_DUNG ? "Còn áp dụng" : "Ngưng áp dụng"  // ❌ LINE 104
            });
        }
```

**Problem:**

- Line 104: Uses hardcoded String comparison and hardcoded display values
- Inconsistent with filter logic that uses `getDisplayName()`
- If enum display names change, this hardcoded logic won't update

---

## 3. PanelDanhSachKhuyenMai.java

### Issue 3a: Filter by Trạng Thái - Enum Display Name Comparison

**Location:** [searchAndFilter() method](PanelDanhSachKhuyenMai.java#L181-L215)

**Code (Lines 181-195):**

```java
private void searchAndFilter() {
    DefaultTableModel model = (DefaultTableModel) tableKhuyenMai.getModel();
    model.setRowCount(0);
    String searchText = txtTimKiem.getText().trim().toLowerCase();
    String selectedTrangThai = (String) cbFilterTrangThai.getSelectedItem();

    for (KhuyenMai km : allKhuyenMai) {
        // Check filters
        if (selectedTrangThai != null && !selectedTrangThai.equals("Trạng thái")) {
            if (!km.getTrangThai().getDisplayName().equals(selectedTrangThai)) {  // ❌ LINE 190
                continue;
            }
        }
```

**Problem:**

- Line 190: Compares `selectedTrangThai` (String from combobox) with `km.getTrangThai().getDisplayName()`
- ComboBox loading: `ComboBoxEnumLoader.loadTrangThaiKhuyenMaiToComboBox(cbFilterTrangThai)` (Line 27)
- Same pattern as Ban and Thue

### Issue 3b: Table Display Uses Enum Display Name Correctly

**Location:** [loadDataToTable() method](PanelDanhSachKhuyenMai.java#L66-L82)

**Code (Lines 66-82):**

```java
private void loadDataToTable() {
    try {
        allKhuyenMai = khuyenMaiService.getAllKhuyenMai();
        DefaultTableModel model = (DefaultTableModel) tableKhuyenMai.getModel();
        model.setRowCount(0);

        for (KhuyenMai km : allKhuyenMai) {
            model.addRow(new Object[] {
                    km.getMaKM(),
                    km.getTenKM(),
                    km.getGiaTriGiam(),
                    km.getNgayBatDau(),
                    km.getNgayKetThuc(),
                    km.getDieuKienToiThieu(),
                    km.getTrangThai().getDisplayName()  // ✓ CORRECT: Uses getDisplayName()
            });
        }
```

**Note:** This one is correct! Uses `getDisplayName()` in both table display and filter comparison.

---

## 4. PanelDanhSachNhanVien.java

### Issue 4a: Filter by Chức Vụ (Position) - Enum Display Name Comparison

**Location:** [searchAndFilter() method](PanelDanhSachNhanVien.java#L230-L265)

**Code (Lines 230-250):**

```java
private void searchAndFilter() {
    DefaultTableModel model = (DefaultTableModel) tableKhuVuc.getModel();
    model.setRowCount(0);
    String searchText = txtTimKiem.getText().trim().toLowerCase();
    String selectedChucVu = (String) cbFilterChucVu.getSelectedItem();

    for (NhanVien nv : allNhanVien) {
        // Check filters
        if (selectedChucVu != null && !selectedChucVu.equals("Chức vụ")) {
            if (!nv.getChucVu().getDisplayName().equals(selectedChucVu)) {  // ❌ LINE 240
                continue;
            }
        }
```

**Problem:**

- Line 240: Compares `selectedChucVu` (String from combobox) with `nv.getChucVu().getDisplayName()`
- ComboBox loading: `ComboBoxEnumLoader.loadChucVuToComboBox(cbFilterChucVu)` (Line 24)
- Same fragile String comparison pattern

### Issue 4b: Table Display Uses Enum Display Name Correctly

**Location:** [loadDataToTable() method](PanelDanhSachNhanVien.java#L102-L120)

**Code (Lines 102-120):**

```java
private void loadDataToTable() {
    try {
        allNhanVien = nhanVienService.getAllNhanVien();
        DefaultTableModel model = (DefaultTableModel) tableKhuVuc.getModel();
        model.setRowCount(0);

        for (NhanVien nv : allNhanVien) {
            model.addRow(new Object[] {
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getSdt(),
                    nv.getChucVu().getDisplayName(),  // ✓ CORRECT: Uses getDisplayName()
                    nv.getNgayVaoLam(),
                    nv.getLuongCoBan(),
                    nv.getTrangThai().getDisplayName()  // ✓ CORRECT: Uses getDisplayName()
            });
        }
```

**Note:** Table display is consistent and correct!

---

## 5. PanelDanhSachMonAn.java

### Issue 5a: Filter by Loại Món Ăn - Enum Display Name Comparison

**Location:** [searchAndFilter() method](PanelDanhSachMonAn.java#L265-L310)

**Code (Lines 265-285):**

```java
private void searchAndFilter() {
    DefaultTableModel model = (DefaultTableModel) tableMonAn.getModel();
    model.setRowCount(0);
    String searchText = txtTimKiem.getText().trim().toLowerCase();
    String selectedLoaiMonAn = (String) cbFilterLoaiMonAn.getSelectedItem();
    String selectedDonVi = (String) cbFilterDonViTinh.getSelectedItem();

    for (MonAn monAn : allMonAn) {
        // Check filters
        if (selectedLoaiMonAn != null && !selectedLoaiMonAn.equals("Item 1")) {
            if (monAn.getTenLoai() == null || !monAn.getTenLoai().getDisplayName().equals(selectedLoaiMonAn)) {  // ❌ LINE 274
                continue;
            }
        }

        if (selectedDonVi != null && !selectedDonVi.equals("Item 1")) {
            if (!monAn.getDonViTinh().equals(selectedDonVi)) {  // ❌ LINE 279 - Direct string comparison
                continue;
            }
        }
```

**Problems:**

- Line 274: Compares `selectedLoaiMonAn` (String) with `monAn.getTenLoai().getDisplayName()`
- Line 279: Compares `selectedDonVi` (String) directly with `monAn.getDonViTinh()` - different comparison pattern
- ComboBox placeholder text "Item 1" appears to be placeholder, not actual enum value

### Issue 5b: Table Display Inconsistency

**Location:** [loadDataToTable() method](PanelDanhSachMonAn.java#L83-L105)

**Code (Lines 83-105):**

```java
private void loadDataToTable() {
    try {
        allMonAn = monAnService.getAllMonAn();
        DefaultTableModel model = (DefaultTableModel) tableMonAn.getModel();
        model.setRowCount(0);

        for (MonAn monAn : allMonAn) {
            model.addRow(new Object[] {
                    "",
                    monAn.getMaMon(),
                    monAn.getTenMon(),
                    monAn.getDonGia(),
                    monAn.getDonViTinh(),
                    monAn.getTenLoai() != null ? monAn.getTenLoai().getDisplayName() : "",  // ✓ Uses getDisplayName()
                    monAn.kiemTraConHang() ? "Còn" : "Ngưng sử dụng"
            });
        }
```

---

## 6. PanelDanhSachKhuVuc.java

### Status: ✓ NO COMBOBOX FILTERS

This file has no enum-based filters. Only search by text (name/code).

---

## 7. PanelQuanLyBan.java

### Status: ⚠️ EMPTY FILTER IMPLEMENTATIONS

ComboBox filters exist but action listener methods are not implemented:

**Location:** [cbFilterKhuVucActionPerformed() and cbFilterTrangThaiActionPerformed()](PanelQuanLyBan.java#L460-L470)

**Code:**

```java
private void cbFilterKhuVucActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterKhuVucActionPerformed
    // TODO add your handling code here:
}// GEN-LAST:event_cbFilterKhuVucActionPerformed

private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
    // TODO add your handling code here:
}// GEN-LAST:event_cbFilterTrangThaiActionPerformed
```

**Problem:**

- ComboBoxes are defined but filter logic is not implemented (TODO)
- Table shows enum display names correctly but filters don't work

---

## 8. PanelQuanLyKhuVuc.java

### Status: ✓ NO COMBOBOX FILTERS

This file has no enum-based filters. It's a management panel with add/update/delete operations.

---

## 9. PanelQuanLyKhuyenMai.java

### Status: ⚠️ EMPTY FILTER IMPLEMENTATION

ComboBox filter exists but action listener method is not implemented:

**Location:** [cbFilterTrangThaiActionPerformed()](PanelQuanLyKhuyenMai.java#L504-L507)

**Code:**

```java
private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
    // TODO add your handling code here:
}// GEN-LAST:event_cbFilterTrangThaiActionPerformed
```

**Problem:**

- ComboBox is loaded by: `ComboBoxEnumLoader.loadTrangThaiKhuyenMaiToComboBox(cbFilterTrangThai)` (Line 26)
- Filter logic not implemented (TODO)

---

## 10. PanelQuanLyThue.java

### Status: ⚠️ EMPTY FILTER IMPLEMENTATION

ComboBox filter exists but action listener method is not implemented:

**Location:** [cbFilterTrangThaiActionPerformed()](PanelQuanLyThue.java#L369-L372)

**Code:**

```java
private void cbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbFilterTrangThaiActionPerformed
    // TODO add your handling code here:
}// GEN-LAST:event_cbFilterTrangThaiActionPerformed
```

**Problem:**

- ComboBox is set with hardcoded default model: `new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" })`
- Filter logic not implemented (TODO)
- ComboBox is not properly loaded with enum values

---

## Summary Table

| File                   | Issue Type                      | Line    | Problem                                                 | Severity  |
| ---------------------- | ------------------------------- | ------- | ------------------------------------------------------- | --------- |
| PanelDanhSachBan       | String Comparison               | 275     | `.getDisplayName().equals(String)`                      | 🔴 Medium |
| PanelDanhSachBan       | Enum toString vs getDisplayName | 113     | Uses `.toString()` instead of `.getDisplayName()`       | 🔴 High   |
| PanelDanhSachThue      | String Comparison               | 140     | `.getDisplayName().equals(String)`                      | 🔴 Medium |
| PanelDanhSachThue      | Hardcoded Comparison            | 104     | Hardcoded string comparison instead of getDisplayName() | 🔴 High   |
| PanelDanhSachKhuyenMai | String Comparison               | 190     | `.getDisplayName().equals(String)`                      | 🔴 Medium |
| PanelDanhSachMonAn     | String Comparison               | 274     | `.getDisplayName().equals(String)`                      | 🔴 Medium |
| PanelDanhSachMonAn     | Inconsistent Comparison         | 279     | Mixed comparison patterns (toString vs getDisplayName)  | 🟡 Low    |
| PanelDanhSachNhanVien  | String Comparison               | 240     | `.getDisplayName().equals(String)`                      | 🔴 Medium |
| PanelQuanLyBan         | Not Implemented                 | 463,468 | ComboBox filters not implemented (TODO)                 | 🟡 Low    |
| PanelQuanLyKhuyenMai   | Not Implemented                 | 504     | ComboBox filter not implemented (TODO)                  | 🟡 Low    |
| PanelQuanLyThue        | Not Implemented + Wrong Model   | 149,369 | ComboBox has hardcoded items, filter not implemented    | 🔴 High   |

---

## Root Causes

1. **String Comparison Pattern**: All filter methods directly compare String values from combobox with enum `.getDisplayName()`. While this works if the combobox is properly loaded with display names, it's fragile and doesn't use enum comparisons.

2. **Inconsistency Between Display and Filter**: Some files use `.toString()` in table display but `.getDisplayName()` in filters, causing mismatches.

3. **Hardcoded Display Names**: Some files hardcode enum display names instead of using the enum's `getDisplayName()` method, making maintenance difficult.

4. **Incomplete Implementation**: Some manage panels have empty filter implementations (TODO).

5. **Wrong ComboBox Model**: Some files have hardcoded "Item 1", "Item 2" etc. instead of properly loaded enum values.

---

## Recommendations

1. **Use Enum Comparison Instead of String Comparison**: Convert String from combobox back to enum for comparison
2. **Standardize Display Logic**: Always use `enum.getDisplayName()` for both table display and filter logic
3. **Complete Missing Implementations**: Implement the TODO filter methods
4. **Fix ComboBox Models**: Ensure all ComboBoxes are properly loaded with enum values, not hardcoded strings
5. **Consider Refactoring**: Create a utility method for consistent enum-string conversion
