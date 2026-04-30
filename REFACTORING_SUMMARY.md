# Restaurant Table Booking & Payment Refactoring - Complete Summary

**Date**: April 30, 2026  
**Status**: ✅ Completed - All files compile without errors

---

## 📋 Overview

Complete refactoring of the table booking, table change, and payment flow to ensure **perfect synchronization** between:

- **RAM (Session)** via `HoaDonDraftSession`
- **Database** via Service layer
- **UI** across multiple panels

---

## 🎯 Tasks Completed

### ✅ Task 1: Enhance Data Layer & Session

#### HoaDonDraftSession.java

**Enhanced `migrateContext(String oldMaBanContext, String newMaBanContext)`**

- Now includes comprehensive javadoc explaining the migration process
- **Step 1**: Migrate dish list via `getMonItems(oldNormalized)` → `setMonItems(newNormalized)`
- **Step 2**: Migrate metadata (maKH, maKM, diemDung) via `setInvoiceMetadata()`
- **Step 3**: Update `currentMaBanContext` if session is active
- **Step 4**: **CRITICAL**: Call `clear(oldMaBanContext)` to prevent data garbage

**Key Improvement**: Explicitly documents the clean-up sequence to prevent RAM leaks

---

### ✅ Task 2: Add Table Routing Flag to PanelDatBan

#### PanelDatBan.java

**New field**: `private String flowOrigin = ""`

- Stores origin panel: `"DAT_MON"` or `"QUAN_LY_DAT_TRUOC"`
- Prevents incorrect callback routing when editing tables

**New Methods**:

```java
public void setFlowOrigin(String origin)     // Set routing destination
public String getFlowOrigin()                 // Get routing destination
```

**Refactored `onButtonDatBanClicked()`**:

- **EDIT MODE Logic**:
  - If `flowOrigin == "DAT_MON"`: Routes to `PanelDatMon.updateMaBanContextForEdit()`
  - If `flowOrigin == "QUAN_LY_DAT_TRUOC"`: Routes to `PanelQuanLyDatBanTruoc.updateMaBanForEdit()`
  - Fallback logic if flowOrigin is unclear
  - Always sets `flowOrigin = ""` after routing to prevent accidental reuse
- **NORMAL MODE Logic**: Opens `LuaChonDatBanDialog` as before

**Table Highlighting** (Already in place):

- Old tables (bàn cũ): Green (`EDIT_MODE_SELECTED_COLOR`)
- New tables (bàn mới): Yellow (selected)

---

### ✅ Task 3: PanelDatMon Table Status Updates

#### PanelDatMon.java - **Logic Already Implemented**

**Flow 1 - Đặt bàn dùng ngay** (When saving):

- `capNhatTrangThaiSauKhiLuu()` sets table status to `DANG_DUNG`
- Triggered on "Lưu tạm" button and panel hide

**Flow 2 - Đặt bàn trước** (When choosing dishes):

- `capNhatTrangThaiSauKhiLuu()` calls `PhieuDatBanService.batDauSuDung(maPhieuDat)`
- Updates PhieuDatBan status from `DANG_CHO` to `DANG_SU_DUNG`
- Sets associated tables to `DANG_DUNG`

**Table Change Logic** via `updateMaBanContextForEdit()`:

- Saves current draft to session
- Calls `HoaDonDraftSession.migrateContext()` to move draft data
- Updates DB invoices from old table to new table
- Calls `capNhatTrangThaiBanSauKhiDoiBan()`:
  - Old tables → `TRONG` (available)
  - New tables → `DANG_DUNG` (in use)

---

### ✅ Task 4: PanelQuanLyDatBanTruoc Table Change Flow

#### PanelQuanLyDatBanTruoc.java

**btnDoiBanActionPerformed() Update**:

- Sets `panelDatBan.setFlowOrigin("QUAN_LY_DAT_TRUOC")`
- Pre-populates current tables via `setSelectedTablesForEdit(oldBanSet)`
- User selects new tables → returns to `PanelQuanLyDatBanTruoc`
- User clicks "Cập nhật" to persist changes via `finishEditBansFromPanelDatBan()`

**Workflow**:

1. User selects reservation → clicks "Đổi bàn"
2. Loads old tables in `PanelDatBan` (green highlight)
3. User selects new tables (yellow highlight)
4. Confirms selection → Back to `PanelQuanLyDatBanTruoc`
5. Clicks "Cập nhật" → Updates DB + Session

---

### ✅ Task 5: PanelLapHoaDon Payment & Cleanup

#### PanelLapHoaDon.java

**Point Deduction Validation** (`btnDungDiemActionPerformed`):

```java
// NEW: Check if customer has enough points BEFORE deducting
if (diemHienCo < diemCanTru) {
    JOptionPane.showMessageDialog(this,
        "Điểm tích lũy không đủ để sử dụng. Cần " + diemCanTru +
        " điểm, hiện có " + diemHienCo + " điểm.");
    return;
}
```

**Point Accumulation Fix** (`congDiemTichLuyChoKhachHang`):

```java
// OLD: Used tongThanhToanLuuTam (temporary value, could be stale)
// NEW: Uses actual invoice total from DB
HoaDon hoaDon = hoaDonService.findHoaDonTheoMa(txtMaHoaDon.getText().trim());
if (hoaDon == null) return;
int diemTang = (int) (hoaDon.getTongThanhToan() / VND_PER_DIEM);
```

**Table Status Update** (`capNhatTrangThaiBan`):

- Parses comma-separated table list: `"B001,B002,B003"`
- Updates each table individually to `TRONG` status

**Complete Cleanup Sequence** (`generatePdfInvoice`):

```
MUST FOLLOW THIS ORDER:
1️⃣  Generate & Save PDF (triggers this whole sequence)
2️⃣  Save currentMaBan & currentMaPhieuDat (before clearing)
3️⃣  thucHienLuuHoaDon(DA_THANH_TOAN) - Persist invoice
4️⃣  capNhatTrangThaiBan(currentMaBan) - Free up tables
5️⃣  Update PhieuDatBan status (if exists) - Mark reservation as used
6️⃣  congDiemTichLuyChoKhachHang() - Award loyalty points
7️⃣  HoaDonDraftSession.clear() - FINALLY clear RAM
8️⃣  refreshDraftData() - Reset UI
9️⃣  Navigate back to PanelDatBan
```

---

## 🔄 Flow Diagrams

### Table Change Flow (DAT_MON → QUAN_LY_DAT_TRUOC)

```
User in PanelDatMon clicks "Đổi bàn"
    ↓
PanelDatMon saves draft → calls MainForm.startEditBanFromDatMon()
    ↓
MainForm sets flowOrigin = "DAT_MON" → switches to PanelDatBan
    ↓
PanelDatBan loads old tables (green) → user selects new tables (yellow)
    ↓
User confirms → onButtonDatBanClicked() checks flowOrigin = "DAT_MON"
    ↓
Calls panelDatMon.updateMaBanContextForEdit(newTables)
    ↓
✅ Migration happens:
   - HoaDonDraftSession.migrateContext(oldContext, newContext)
   - Update DB invoices to new table
   - Update old tables → TRONG, new tables → DANG_DUNG
    ↓
Returns to PanelDatMon
```

### Advance Reservation Change Flow (QUAN_LY_DAT_TRUOC)

```
User in PanelQuanLyDatBanTruoc selects reservation → clicks "Đổi bàn"
    ↓
btnDoiBanActionPerformed() sets flowOrigin = "QUAN_LY_DAT_TRUOC"
    ↓
Switches to PanelDatBan with old tables (green highlight)
    ↓
User selects new tables (yellow) → confirms
    ↓
onButtonDatBanClicked() checks flowOrigin = "QUAN_LY_DAT_TRUOC"
    ↓
Calls panelQuanLyDatBanTruoc.updateMaBanForEdit(newTables)
    ↓
Returns to PanelQuanLyDatBanTruoc
    ↓
User clicks "Cập nhật" → Updates DB + saves to session
```

### Payment & Cleanup Flow

```
User in PanelLapHoaDon clicks "In hóa đơn"
    ↓
generatePdfInvoice() saves PDF successfully
    ↓
✅ CLEANUP SEQUENCE BEGINS:
1. Capture currentMaBan & currentMaPhieuDat
2. thucHienLuuHoaDon(DA_THANH_TOAN) - Save to DB
3. capNhatTrangThaiBan(currentMaBan) - Set tables to TRONG
4. Update PhieuDatBan → DA_SU_DUNG
5. congDiemTichLuyChoKhachHang() - Award points
6. HoaDonDraftSession.clear() - Clear RAM
7. refreshDraftData() - Empty UI
    ↓
Navigate to PanelDatBan (ready for next order)
```

---

## 📊 Data Synchronization Matrix

| State                  | RAM (Session) | Database                        | UI                    |
| ---------------------- | ------------- | ------------------------------- | --------------------- |
| **Before Draft Save**  | ✓ Populated   | ✗ Empty                         | ✓ Showing data        |
| **After Draft Save**   | ✓ Updated     | ✓ Draft saved (CHUA_THANH_TOAN) | ✓ Same                |
| **After Table Change** | ✓ Migrated    | ✓ Invoice table updated         | ✓ New table displayed |
| **After Payment**      | ✗ Cleared     | ✓ Invoice saved (DA_THANH_TOAN) | ✓ Reset empty         |

---

## ✅ Validation Checklist

- [x] All 5 files compile without errors
- [x] HoaDonDraftSession migration includes explicit cleanup
- [x] PanelDatBan has flowOrigin routing flag
- [x] MainForm sets flowOrigin when calling PanelDatBan
- [x] PanelQuanLyDatBanTruoc sets flowOrigin before edit mode
- [x] PanelDatMon has table change logic (already implemented)
- [x] Point deduction validates available points before deducting
- [x] Point accumulation uses DB invoice total, not temporary value
- [x] Payment cleanup sequence follows strict order
- [x] Session is cleared ONLY after all DB updates succeed

---

## 🛠️ Files Modified

1. **HoaDonDraftSession.java** - Enhanced migrateContext() documentation
2. **PanelDatBan.java** - Added flowOrigin field & routing logic
3. **MainForm.java** - Set flowOrigin in startEditBanFromDatMon()
4. **PanelQuanLyDatBanTruoc.java** - Set flowOrigin in btnDoiBanActionPerformed()
5. **PanelLapHoaDon.java** - Enhanced payment cleanup sequence + point validation

---

## 🚀 Next Steps

1. **Test in dev environment**:
   - Test table change flow (DAT_MON)
   - Test advance reservation change (QUAN_LY_DAT_TRUOC)
   - Test payment & cleanup sequence

2. **Monitor logs** for:
   - Migration timing between old and new contexts
   - Table status updates in DB
   - Point calculations

3. **Potential enhancements**:
   - Add transaction boundaries to payment sequence
   - Add rollback logic if cleanup fails
   - Log each step of migration for debugging

---

**End of Refactoring Summary**
