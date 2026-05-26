# 📋 TEST CHECKLIST: Hệ thống Quản lý Nhà hàng

**Ngày tạo:** 26/05/2026  
**Phiên bản:** v1.0  
**Status:** [ ] Chưa bắt đầu [ ] Đang thực hiện [ ] Hoàn thành

---

## I. QUẢN LÝ CA LÀM VIỆC (Shift Management)

### Đăng nhập

- [ ] **TC-CA-01** - Nhập username/password hợp lệ, click "Đăng nhập"
  - Expected: Hiện thông báo thành công. DB: Ghi log đăng nhập. SessionManager lưu TaiKhoan.
  - Kết quả: \***\*\_pass\_\_\*\***

- [ ] **TC-CA-02** - Nhập username sai hoặc password sai
  - Expected: Thông báo lỗi "Tên đăng nhập hoặc mật khẩu không đúng". Password field được xóa, focus về field password.
  - Kết quả: \***\*\pass\_\_\*\***

- [ ] **TC-CA-03** - Nhập username với ký tự đặc biệt ('; DROP TABLE--)
  - Expected: Thông báo lỗi hoặc chuyển hướng chính xác. DB: Không bị SQL Injection.
  - Kết quả: \***\*\_pass\_\_\*\***

- [ ] **TC-CA-04** - Nhập password rỗng, click "Đăng nhập"
  - Expected: Thông báo "Vui lòng nhập mật khẩu". Focus về field password.
  - Kết quả: \***\*\_pass\_\_\*\***

- [ ] **TC-CA-05** - Gửi form đăng nhập đúp liên tiếp trong 1 giây
  - Expected: Chỉ xử lý 1 request. Không hiện 2 dialog thông báo.
  - Kết quả: \***\*\_\no test\_\_\*\***

### Bắt đầu Ca

- [ ] **TC-CA-06** - Đăng nhập thành công → MainForm hiện "BẮT ĐẦU CA" → Nhập tiền đầu ca từng mệnh giá
  - Expected: Tổng tiền tự động cập nhật. SessionManager = null lúc này.
  - Kết quả: \***\*\_\pass\_\_\*\***

- [ ] **TC-CA-07** - Nhập tiền đầu ca = 0 VND, click "VÀO CA"
  - Expected: Thông báo "Vui lòng nhập ít nhất một tờ tiền để vào ca." Không cho vào ca.
  - Kết quả: \***\*\_\pass\_\_\*\***

- [ ] **TC-CA-08** - Nhập -5 tờ mệnh giá 1000 VND
  - Expected: Thông báo lỗi hoặc tự động chuyển thành 0. Không cho vào ca với số âm.
  - Kết quả: \***\*\_\pass\_\_\*\***

- [ ] **TC-CA-09** - Nhập text vào field mệnh giá (VD: "abc")
  - Expected: Thông báo lỗi "Vui lòng nhập số hợp lệ". Field highlight.
  - Kết quả: \***\*\_\pass\_\_\*\***

- [ ] **TC-CA-10** - Nhập số quá lớn (999999999999 tờ mệnh giá 500k)
  - Expected: Warning hoặc lỗi. DB: Không tạo CaLamViec với giá trị vượt quá INT/BIGINT.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-CA-11** - Nhập tiền đầu ca hợp lệ, click "VÀO CA"
  - Expected: Menu được enable. Chuyển sang màn hình Trang chủ. DB: Tạo record CaLamViec với trangThai='DANG_LAM_VIEC', tienDauCa=<input>. SessionManager.getCurrentCaLamViec() ≠ null.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-CA-12** - Click "THOÁT" khi chưa vào ca
  - Expected: Hỏi xác nhận "Bạn có muốn thoát chương trình?". Click "Không" quay lại. Click "Có" thoát app.
  - Kết quả: \***\*\_\_\_\*\***

### Kết Thúc Ca

- [ ] **TC-CA-13** - Nhân viên trong ca, click "KẾT CA" menu
  - Expected: Hiện panel PanelKetCa với thông tin tiền đầu ca, doanh thu tiền mặt, tổng tiền hệ thống.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-CA-14** - Doanh thu tiền mặt: 1.000.000 VND, Tiền đầu ca: 500.000 VND, Nhập tiền thực tế: 1.500.000 VND
  - Expected: Chênh lệch = 0. Label chênh lệch màu đen. DB: cập nhật CaLamViec.tienKetCa=1500000, chenhLech=0.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-CA-15** - Tiền thực tế > Tổng tiền hệ thống 500.000 VND
  - Expected: Chênh lệch hiện màu xanh (dư tiền). Yêu cầu ghi chú. Nếu không ghi chú → không cho kết ca.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-CA-16** - Tiền thực tế < Tổng tiền hệ thống 200.000 VND
  - Expected: Chênh lệch hiện màu đỏ (thiếu tiền). Yêu cầu ghi chú. Nếu chênh lệch ≠ 0 mà không ghi chú → thông báo lỗi.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-CA-17** - Nhập số tiền thực tế = "không có từng này VND"
  - Expected: Thông báo lỗi. Không cho kết ca.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-CA-18** - Nhập chênh lệch rồi click "Xác nhận kết ca"
  - Expected: Thông báo "Kết ca thành công". App quay về LoginForm. DB: CaLamViec.trangThai='DA_KET_CA', tienKetCa, ghiChu, thoiGianKetCa được ghi. SessionManager.clearSession().
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-CA-19** - Click "Hủy" ở panel kết ca
  - Expected: Quay lại Trang chủ. Ca vẫn đang mở (không kết).
  - Kết quả: \***\*\_\_\_\*\***

### Chặn Tắt App

- [ ] **TC-CA-20** - Nhân viên trong ca, click X để đóng MainForm
  - Expected: Thông báo "Bạn đang trong ca làm việc. Vui lòng kết ca trước khi đóng cửa sổ." Không đóng app.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-CA-21** - Đã kết ca, click X để đóng MainForm
  - Expected: App đóng bình thường. DB: Không có record CaLamViec mở.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-CA-22** - Nhân viên A vào ca, ghi tiền đầu ca = 1M. Nhân viên B login (khác account)
  - Expected: Hiện "BẮT ĐẦU CA". SessionManager chỉ lưu 1 ca hiện tại. DB: Tạo CaLamViec riêng cho B.
  - Kết quả: \***\*\_\_\_\*\***

---

## II. QUẢN LÝ ĐẶT BÀN & SƠ ĐỒ BÀN

### Sơ Đồ Bàn

- [ ] **TC-DB-01** - Vào ca → click "Đặt bàn dùng ngay"
  - Expected: Hiện sơ đồ bàn với từng bàn có trạng thái (TRỐNG=xanh, DANG_DUNG=đỏ, DA_DAT=vàng). DB: Load tất cả bàn từ BanAn, hiển thị trangThai.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DB-02** - Click filter khu vực → Chọn "Khu A"
  - Expected: Chỉ hiển thị bàn thuộc khu A. Các bàn khu khác ẩn.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DB-03** - Click filter trạng thái → Chọn "TRỐNG"
  - Expected: Chỉ hiển thị bàn TRỐNG.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DB-04** - Vô tình click 2 lần liên tiếp bàn "B1"
  - Expected: Chỉ chọn lần đầu. Lần 2 bị bỏ chọn. State của B1 = unselected.
  - Kết quả: \***\*\_\_\_\*\***

### Đặt Bàn Dùng Ngay

- [ ] **TC-DB-05** - Chọn 1 bàn TRỐNG (B1), click "Đặt bàn dùng ngay"
  - Expected: Hiện dialog tạo PhieuDatBan. Input: SĐT khách hàng, số người. Bàn B1 tạm lock (disable không được click).
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DB-06** - Nhập SĐT = "0912345", số người = 5, click "Tạo"
  - Expected: Phiếu được tạo. Bàn B1 chuyển sang DANG_DUNG (màu đỏ). DB: PhieuDatBan.trangThai='DANG_CHO' hoặc 'DA_SU_DUNG', BanAn.trangThai='DANG_DUNG'.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DB-07** - Nhập số người = 0
  - Expected: Thông báo "Số người phải > 0". Không cho tạo phiếu.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DB-08** - Nhập số người = "abc" hoặc "-10"
  - Expected: Thông báo lỗi. Không cho tạo phiếu.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DB-09** - Nhập SĐT = "invalid_format"
  - Expected: Thông báo lỗi SĐT. Hoặc chỉ chấp nhận số 10 chữ số.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DB-10** - Bàn B1 đang DANG_DUNG, cố tình click chọn B1 để đặt lại
  - Expected: Thông báo "Bàn này đang được sử dụng" hoặc không cho chọn.
  - Kết quả: \***\*\_\_\_\*\***

### Đặt Bàn Trước

- [ ] **TC-DB-11** - Click "Đặt bàn trước" → Chọn bàn B2, nhập SĐT, số người, thời gian đến hôm nay
  - Expected: Hiện dialog. Lịch chọn thời gian (DateTimePicker). Input tiền đặt cọc.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DB-12** - Chọn 2 bàn B2, B3 cùng thời gian 18:00 ngày hôm nay, nhập tiền cọc = 500k
  - Expected: Phiếu được tạo. DB: PhieuDatBan.trangThai='DANG_CHO', ChiTietPhieuDatBan có 2 dòng (B2, B3).
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DB-13** - Chọn cùng bàn B2, thời gian 18:00 hôm nay (đã có phiếu đặt), click "Đặt"
  - Expected: Thông báo "Bàn B2 đã được đặt trong khung giờ này. Vui lòng chọn bàn khác hoặc thời gian khác." Không cho tạo phiếu trùng.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DB-14** - Hủy phiếu đặt cũ (B2, 18:00 hôm nay), rồi cố tình đặt lại B2 cùng thời gian
  - Expected: Cho phép đặt (vì phiếu cũ bị hủy). DB: PhieuDatBan.trangThai='DA_HUY' (old), PhieuDatBan mới với status='DANG_CHO'.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DB-15** - Nhập tiền cọc = 0 VND
  - Expected: Cho phép (không bắt buộc cọc), hoặc thông báo warning. Phiếu được tạo với tienDatCoc=0.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DB-16** - Nhập thời gian đến = ngày hôm qua
  - Expected: Thông báo "Thời gian không được ở quá khứ". Không cho tạo phiếu.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DB-17** - Chọn bàn, nhập SĐT khách = "0912345678", kiểm tra xem khách này đã là VIP chưa
  - Expected: Nếu KhachHang.loaiThanhVien='VIP', hệ thống tự động ghi chú "Khách VIP" ở phiếu.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DB-18** - Tạo phiếu đặt hôm nay 17:00, để hết giờ (không checkin)
  - Expected: Hệ thống tự động gọi pdbService.tuDongHuyPhieuQuaHan(). DB: PhieuDatBan.trangThai='DA_HUY', BanAn.trangThai quay về TRONG.
  - Kết quả: \***\*\_\_\_\*\***

---

## III. THAO TÁC BÀN NÂNG CAO

### Gộp Bàn (Merge Tables)

- [ ] **TC-GOP-01** - Bàn B1 (DANG_DUNG, 2 người, 500k cọc, VIP), B2 (DANG_DUNG, 3 người, 0 cọc, Regular)
  - Expected: Hiển thị sơ đồ bàn. Chuẩn bị chọn 2 bàn.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOP-02** - Click "Gộp bàn" → Chọn B1, B2 → Click "Gộp bàn" (button)
  - Expected: Tạo phiếu MASTER từ B1 (bàn chọn đầu tiên), Số người = 2+3=5, Tiền cọc = 500k+0=500k, VIP status = VIP. B1 B2 lock, ghi chú "Gộp từ B1, B2".
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOP-03** - Phiếu MASTER được tạo. Kiểm tra B1, B2
  - Expected: DB: B1, B2 trangThai='TRONG' (giải phóng). PhieuDatBan cũ của B1, B2 bị soft delete (trangThai='DA_HUY', không xóa). Phiếu MASTER khác mã.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOP-04** - Gộp B1, B2 thành phiếu MASTER, click "Gọi món" (từ B1 hoặc B2 trong list)
  - Expected: Chỉ mở gọi món từ phiếu MASTER, không cho gọi từ phiếu cũ của B1, B2.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOP-05** - Click "Gộp bàn", chỉ chọn B1 → Click "Gộp bàn"
  - Expected: Thông báo "Vui lòng chọn ít nhất 2 bàn để gộp". Không tạo phiếu.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOP-06** - Chọn B1 (DANG_DUNG), B2 (TRONG), Click "Gộp bàn"
  - Expected: Thông báo "Tất cả bàn phải có trạng thái DANG_DUNG để gộp". Không gộp.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOP-07** - Gộp B1 (2 người), B2 (3 người), B3 (4 người)
  - Expected: DB: Phiếu MASTER.soLuongNguoi = 2+3+4 = 9.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOP-08** - B1 có khách VIP (Anh A, điểm = 100), B2 có khách Regular (Chị B)
  - Expected: DB: Phiếu MASTER lấy khách từ B1 (bàn chính). Nếu B1 không có khách, lấy từ B2. Điểm VIP = 100 được kế thừa.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOP-09** - Gộp B1, B2 → Phiếu cũ PhieuDatBan_B1, PhieuDatBan_B2
  - Expected: DB: Đảm bảo soft delete (trangThai='DA_HUY'). Không xóa record (để audit trail).
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOP-10** - Phiếu MASTER đã có 5 món, gộp xong, click "Gọi thêm" → Thêm 2 món
  - Expected: Thêm 2 món vào phiếu MASTER. ChiTietPhieuDatBan += 2 dòng. Tổng tính tiền cập nhật.
  - Kết quả: \***\*\_\_\_\*\***

### Đổi Bàn (Switch Tables)

- [ ] **TC-DOI-01** - Bàn B1 (DANG_DUNG, 3 người, 5 món, 1.5M tiền), B3 (TRONG)
  - Expected: Hiển thị sơ đồ bàn.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DOI-02** - Click "Đặt bàn" → Chọn B1 → Click "Đổi bàn" → Chọn B3 → Click "Đổi"
  - Expected: Thông báo "Đã chuyển B1 → B3 thành công". B1 chuyển TRONG, B3 chuyển DANG_DUNG.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DOI-03** - Đổi B1 → B3, kiểm tra dữ liệu
  - Expected: DB: B3.trangThai='DANG_DUNG'. ChiTietPhieuDatBan.maBan từ B1 → B3 (update hoặc tạo mới). B1.trangThai='TRONG'. Phiếu vẫn là 1 (không tạo phiếu mới).
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DOI-04** - Phiếu B1 ghi "14:30", đổi sang B3, thời gian vào vẫn là 14:30
  - Expected: DB: ChiTietPhieuDatBan.thoiGianDen vẫn = 14:30. Không reset.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DOI-05** - B1 (DANG_DUNG), cố tình đổi sang B2 (DANG_DUNG)
  - Expected: Thông báo "Bàn đích phải trống". Không cho đổi.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DOI-06** - B1 (TRONG hoặc DA_DAT), click "Đổi bàn" → Chọn B3
  - Expected: Thông báo "Bàn hiện tại phải ở trạng thái DANG_DUNG". Không cho đổi.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DOI-07** - B1 (DANG_DUNG), click "Đổi bàn" → Click "Đổi" mà không chọn bàn đích
  - Expected: Thông báo "Vui lòng chọn bàn đích".
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DOI-08** - Bàn B1 có 5 người, B3 sức chứa 4 chỗ
  - Expected: Thông báo warning "Bàn đích có sức chứa không đủ (4 < 5)". Hỏi "Tiếp tục?" - Nếu có → thực hiện. Nếu không → hủy.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DOI-09** - Phiếu B1 có tiền cọc = 500k, đổi sang B3
  - Expected: DB: Tiền cọc vẫn = 500k (được kế thừa).
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-DOI-10** - Đổi B1 → B3, rồi click "Gọi thêm" → Thêm 3 món
  - Expected: 3 món được thêm vào phiếu (bàn B3). ChiTietPhieuDatBan.maBan = B3. Không bị nhầm về B1.
  - Kết quả: \***\*\_\_\_\*\***

---

## IV. GỌI MÓN & LẬP HÓA ĐƠN

### Gọi Món

- [ ] **TC-GOI-01** - Phiếu B1 được tạo (3 người), click "Gọi món"
  - Expected: Hiện panel PanelDatMon. Danh sách món ăn từ CSDL. Chọn từng món, số lượng.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOI-02** - Nhập từ khóa "cơm" vào ô tìm kiếm → Enter
  - Expected: Chỉ hiển thị món có chứa "cơm" (case-insensitive).
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOI-03** - Chọn: Cơm gà (50k x 2), Nước cam (30k x 1)
  - Expected: Hiển thị danh sách chọn. Tổng tiền = 50k*2 + 30k*1 = 130k.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOI-04** - Chọn món, nhập số lượng = "abc"
  - Expected: Thông báo lỗi hoặc tự động chuyển thành 1. Không cho tính giá sai.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOI-05** - Nhập số lượng = -5
  - Expected: Thông báo lỗi hoặc set về 0. Tổng tiền không tính dòng này.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOI-06** - Chọn món, nhập số lượng = 0
  - Expected: Dòng này không được thêm vào hóa đơn (hoặc tự xóa).
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOI-07** - Click chọn "Cơm gà", sau đó click "Xóa"
  - Expected: Dòng "Cơm gà" bị xóa. Tổng tiền recalc.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOI-08** - Chọn "Cơm gà x2", click cell số lượng, sửa thành 3
  - Expected: Tổng tiền cập nhật (từ 100k → 150k).
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOI-09** - Click "Lưu & tiếp tục"
  - Expected: Dữ liệu được lưu vào HoaDonDraftSession. Có thể rời panel, quay lại sẽ thấy dữ liệu lại.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOI-10** - Click "Clear" hoặc "Hủy"
  - Expected: Dữ liệu nháp bị xóa. Quay lại panel danh sách trống.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-GOI-11** - Phiếu B1 có 3 món, click "Gọi thêm" → Thêm 2 món
  - Expected: Phiếu giữ nguyên 3 món cũ. Thêm 2 món mới. Tổng = 5 món.
  - Kết quả: \***\*\_\_\_\*\***

### Lập Hóa Đơn

- [ ] **TC-LHD-01** - Hoàn tất gọi món, click "Lập hóa đơn"
  - Expected: Hiện panel PanelLapHoaDon. Hiển thị: Mã bàn, Tên nhân viên, Danh sách món, Tổng tiền.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-02** - Tìm SĐT khách "0912345678" → Click chọn khách từ list
  - Expected: Tên khách, điểm tích lũy, loại thành viên được fill.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-03** - SĐT không tồn tại, click "Tạo tài khoản" → Nhập tên, địa chỉ
  - Expected: KhachHang mới được tạo. Hiển thị ở list.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-04** - Chọn khách VIP (có 100 điểm), chọn Khuyến mãi "Giảm 10% cho VIP" (hạn sử dụng: 31/05/2026)
  - Expected: Khuyến mãi được apply. Tính lại tổng tiền = tổng \* 90%.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-05** - KM "Giảm 10%" hết hạn (ngày hôm qua), cố tình chọn
  - Expected: Thông báo "Khuyến mãi này đã hết hạn". Không cho apply. Combo Khuyến mãi không cho select.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-06** - Khách thường, hóa đơn 1.000.000 VND
  - Expected: DB: KhachHang.diemTichLuy += 1.000.000 / 100.000 = 10 điểm (nếu quy luật là 10k = 1 điểm).
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-07** - Khách VIP có 100 điểm, click "Dùng điểm" → Sử dụng 50 điểm
  - Expected: Tính lại tổng tiền (giảm 50 \* VND_PER_DIEM). Điểm còn lại = 50. DB: KhachHang.diemTichLuy = 50.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-08** - Khách có 30 điểm, cố tình dùng 100 điểm
  - Expected: Thông báo "Số điểm không đủ. Điểm hiện có: 30." Không cho dùng.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-09** - Chọn Phương thức TT = "Tiền mặt"
  - Expected: Hiển thị ở form. DB: HoaDon.phuongThucTT = 'TIEN_MAT'.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-10** - Chọn "Chuyển khoản" hoặc "Thẻ tín dụng"
  - Expected: Hiển thị. DB: Ghi phuongThucTT tương ứng.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-11** - Tổng tiền đơn hàng: 10.000.000 VND. Thuế VAT 10%
  - Expected: Tổng tiền VAT = 1.000.000. Tổng thanh toán = 11.000.000.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-12** - Phí dịch vụ 5%
  - Expected: Phí = tổng \* 5% = 500.000 (nếu tổng = 10M). Tổng thanh toán += 500.000.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-13** - Tổng món: 10M, KM: -1M, VAT: 1M, Phí: 500k
  - Expected: Tổng thanh toán = 10M - 1M + 1M + 500k = 10.5M.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-14** - Phiếu có tiền cọc = 500k, tổng thanh toán = 10M
  - Expected: Tổng còn phải trả = 10M - 500k = 9.5M. Hiển thị "Tiền đã cọc: 500k".
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-15** - Click "In hóa đơn" → Chọn vị trí lưu PDF
  - Expected: PDF được tạo với đầy đủ thông tin hóa đơn (tên khách, list món, tính tiền, TT, ngày giờ). File lưu thành công.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-16** - Click "Thanh toán" → Tổng thanh toán = 9.5M, nhập tiền khách = 10M
  - Expected: Tiền thối = 500k. Thông báo "Thanh toán thành công".
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-17** - Tổng thanh toán = 9.5M, nhập tiền khách = 9M
  - Expected: Thông báo "Tiền khách không đủ. Còn thiếu: 500k". Không cho thanh toán.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-18** - Sau khi thanh toán thành công
  - Expected: DB: HoaDon.trangThaiThanhToan='DA_THANH_TOAN', thoiGianThanhToan=NOW(). BanAn.trangThai='TRONG' (bàn được giải phóng). KhachHang.diemTichLuy được cập nhật.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-19** - Click "Quay lại" sau khi thanh toán
  - Expected: Quay về sơ đồ bàn (bàn được thanh toán đã chuyển TRONG).
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-LHD-20** - Đang nhập thông tin hóa đơn, để 5 phút không hoạt động
  - Expected: Hệ thống tự lưu nháp (nếu có tính năng). SessionManager/HoaDonDraftSession giữ dữ liệu.
  - Kết quả: \***\*\_\_\_\*\***

---

## V. EDGE CASES & NGOẠI LỆ CHUNG

### Data Integrity & Concurrency

- [ ] **TC-EDGE-01** - Nhân viên A đang gọi món bàn B1, nhân viên B đóng bàn B1 (khác client). Kiểm tra DB
  - Expected: DB: ChiTietPhieuDatBan.maBan vẫn = B1 (không bị xung đột). HoaDon được tạo từ dữ liệu mới nhất.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-EDGE-02** - 2 nhân viên cùng click "Lập hóa đơn" từ phiếu B1 cùng lúc
  - Expected: Chỉ 1 hóa đơn được tạo. Hóa đơn thứ 2 được thông báo "Phiếu này đã được đóng. Vui lòng refresh."
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-EDGE-03** - Nhân viên A click "Gộp bàn" (B1, B2), cùng lúc nhân viên B click "Đổi bàn" (B1 → B3)
  - Expected: Hệ thống xử lý tuần tự (Lock). Một trong 2 thao tác fail với thông báo "Dữ liệu đã bị thay đổi. Vui lòng refresh."
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-EDGE-04** - Nhân viên đang lập hóa đơn, mất kết nối DB trong 3 giây
  - Expected: Timeout message hoặc retry. Không crash app. Dữ liệu nháp vẫn được giữ.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-EDGE-05** - Mở 100 client POS cùng lúc, tất cả vào ca
  - Expected: DB: Connection pool (max=20 default) được quản lý. Những client thứ 21+ được queue hoặc thông báo "Quá tải kết nối". Không deadlock.
  - Kết quả: \***\*\_\_\_\*\***

### Security & Injection

- [ ] **TC-EDGE-06** - Tìm kiếm tên khách = "'; DROP TABLE HoaDon; --"
  - Expected: DB: Parameterized Query được sử dụng. Không execute DROP. Hiển thị "Không tìm thấy khách".
  - Kết quả: \***\*\_\_\_\*\***

### Performance & Memory

- [ ] **TC-EDGE-07** - Mở/đóng panel Gọi món 100 lần liên tiếp
  - Expected: App vẫn chạy mượt. Memory usage không tăng vô hạn.
  - Kết quả: \***\*\_\_\_\*\***

### Data Type & Values

- [ ] **TC-EDGE-08** - Tổng hóa đơn = 999.999.999.999.999 VND
  - Expected: DB: Giá trị được lưu đúng (kiểm tra DECIMAL(18,2) hoặc tương đương). Không overflow.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-EDGE-09** - Nhập tên khách = "Nguyễn Thị Mỹ Linh & Co, Ltd." (chứa &, dấu phẩy)
  - Expected: DB: Được lưu đúng. Hiển thị đúng khi in hóa đơn. Không bị lỗi charset.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-EDGE-10** - Phiếu không có khách hàng (KhachHang = null), lập hóa đơn
  - Expected: DB: Cho phép (khách vãng lai). HoaDon.maKH = null hoặc "KHONG_XAC_DINH". Không crash.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-EDGE-11** - Nhập tính tiền cọc = -500.000 VND
  - Expected: Thông báo lỗi hoặc tự động convert thành 0. Không lưu âm.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-EDGE-12** - Tính toán (10M _ 0.1) + (5M _ 0.05). Kiểm tra sai số round-off
  - Expected: DB: Sử dụng DECIMAL, không phải FLOAT. Kết quả chính xác = 1.25M.
  - Kết quả: \***\*\_\_\_\*\***

### DateTime & Timezone

- [ ] **TC-EDGE-13** - Tạo phiếu đặt bàn lúc 23:55 ngày 31/05/2026, thời gian đến = 00:30 ngày 01/06/2026
  - Expected: DB: Lưu đúng ngày tháng. Không bị nhầm thành cùng ngày hoặc ngày sai.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-EDGE-14** - Đăng nhập, để 30 phút không hoạt động, cố tình gọi món
  - Expected: Session hết hạn. Redirect về LoginForm. Không crash.
  - Kết quả: \***\*\_\_\_\*\***

### Recovery & Resilience

- [ ] **TC-EDGE-15** - Nhân viên đang nhập thông tin hóa đơn, app crash (kill process)
  - Expected: Mở lại app → Dữ liệu nháp được recover từ HoaDonDraftSession (nếu có tính năng auto-save).
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-EDGE-16** - Dung lượng ổ cứng còn < 1MB, cố ghi dữ liệu
  - Expected: Thông báo "Không đủ dung lượng. Vui lòng xóa dữ liệu cũ." Không tạo record không hoàn chỉnh.
  - Kết quả: \***\*\_\_\_\*\***

### UI/UX Edge Cases

- [ ] **TC-EDGE-17** - User gõ "Thanh toán" 10 lần liên tiếp trong 1 giây
  - Expected: Chỉ tạo 1 hóa đơn. Lần 2-10 bị ignore hoặc thông báo "Đang xử lý...". Button disable tạm thời.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-EDGE-18** - Nhập tên khách = "Khách VIP 😊🎉"
  - Expected: DB: Nếu charset=UTF8MB4, lưu đúng. Nếu charset=UTF8, có thể lỗi. Kiểm tra cấu hình.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-EDGE-19** - Nhập ghi chú = 10.000 ký tự
  - Expected: DB: Nếu column định nghĩa VARCHAR(255), cắt. Nếu TEXT, lưu toàn bộ. Thông báo warning nếu cần.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-EDGE-20** - Chọn ngày 29/02/2026 (không phải năm nhuận)
  - Expected: Thông báo lỗi "Ngày không hợp lệ". Không cho chọn.
  - Kết quả: \***\*\_\_\_\*\***

---

## VI. QUẢN LÝ BÀNG & KHU VỰC (Module Management)

### Quản Lý Bàn

- [ ] **TC-QL-BAN-01** - Menu Quản lý Bàn (chỉ Quản lý mới có quyền)
  - Expected: Nhân viên lễ tân không thấy menu. Quản lý nhìn thấy.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-QL-BAN-02** - Nhập mã bàn mới "B99", số ghế = 4, vị trí = "Góc cửa sổ", khu vực = "Khu A"
  - Expected: Bàn B99 được thêm. DB: BanAn.maBan='B99', soGhe=4, viTri='Góc cửa sổ', maKhuVuc='KVA', trangThai='TRONG'.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-QL-BAN-03** - Sửa bàn B1: số ghế từ 4 → 6, vị trí từ "A" → "B"
  - Expected: DB: BanAn.soGhe=6, viTri='B'. Không ảnh hưởng phiếu đang dùng.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-QL-BAN-04** - Xóa bàn B99 (không có phiếu)
  - Expected: DB: BanAn bị xóa. Kiểm tra FK constraint nếu có.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-QL-BAN-05** - Xóa bàn B1 (đang có phiếu đang dùng)
  - Expected: Thông báo "Không thể xóa bàn đang được sử dụng." Không xóa.
  - Kết quả: \***\*\_\_\_\*\***

### Quản Lý Khu Vực

- [ ] **TC-QL-KV-01** - Thêm khu vực "Khu B" (mã tự động KVB)
  - Expected: DB: KhuVuc.maKhuVuc='KVB', tenKhuVuc='Khu B'.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-QL-KV-02** - Sửa khu vực "Khu A" → "Khu Hàng Ngoài"
  - Expected: DB: KhuVuc.tenKhuVuc='Khu Hàng Ngoài'. Bàn thuộc khu A vẫn giữ maKhuVuc='KVA'.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-QL-KV-03** - Xóa khu vực "Khu B" (không có bàn)
  - Expected: DB: Xóa thành công.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-QL-KV-04** - Xóa khu vực "Khu A" (có bàn B1, B2, B3)
  - Expected: Thông báo "Không thể xóa khu vực có bàn. Vui lòng chuyển bàn sang khu vực khác trước." Không xóa.
  - Kết quả: \***\*\_\_\_\*\***

### Quản Lý Nhân Viên

- [ ] **TC-QL-NV-01** - Thêm nhân viên "Trần Văn A", SĐT "0987654321", Chức vụ "Lễ tân", Lương "5.000.000"
  - Expected: DB: NhanVien mới được tạo. Ghi trangThai='DANG_LAM_VIEC'.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-QL-NV-02** - Nhân viên chỉ show DANG_LAM_VIEC, Quản lý thấy tất cả (bao gồm NGHI_VIEC, NGHI_HUU)
  - Expected: UI: Filter/List khác nhau theo quyền.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-QL-NV-03** - Sửa lương nhân viên từ 5M → 6M
  - Expected: DB: NhanVien.luongCoBan=6000000. Không ảnh hưởng lương cũ (nếu cần audit).
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-QL-NV-04** - Xóa (hoặc Kết thúc) nhân viên Trần Văn A
  - Expected: DB: NhanVien.trangThai='NGHI_VIEC'. Nhân viên vẫn có record (soft delete).
  - Kết quả: \***\*\_\_\_\*\***

### Quản Lý Thực Đơn

- [ ] **TC-QL-THUC-01** - Thêm món ăn "Cơm Tấm" (50k), Loại "Cơm", Upload hình
  - Expected: DB: MonAn mới. Hình được lưu (file path hoặc base64).
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-QL-THUC-02** - Sửa giá "Cơm Tấm" từ 50k → 55k
  - Expected: DB: MonAn.donGia=55000. Không ảnh hưởng hóa đơn cũ.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-QL-THUC-03** - Xóa (Khóa) món "Cơm Tấm"
  - Expected: DB: MonAn.trangThai='HET'. Không thể chọn ở PanelDatMon. Hóa đơn cũ vẫn hiển thị.
  - Kết quả: \***\*\_\_\_\*\***

### Quản Lý Khuyến Mãi

- [ ] **TC-QL-KM-01** - Thêm KM "Giảm 10% Buổi Trưa" (hạn từ 01/06 - 30/06/2026), Loại "Phần trăm", Giá trị=10%
  - Expected: DB: KhuyenMai mới. Ghi hanSuDung.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-QL-KM-02** - Sửa KM: Hạn sử dụng từ 30/06 → 31/07
  - Expected: DB: hanSuDung cập nhật. KM vẫn có thể apply tới ngày 31/07.
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-QL-KM-03** - Khóa KM "Hết hạn"
  - Expected: DB: KhuyenMai.trangThai='KHONG_CON_SU_DUNG'. Không thể select ở form hóa đơn.
  - Kết quả: \***\*\_\_\_\*\***

### Quản Lý Thuế

- [ ] **TC-QL-THUE-01** - Xem danh sách thuế hiện có (VAT 10%, Phí dịch vụ 5%)
  - Expected: Hiển thị đầy đủ. Mỗi thuế ghi: Mã, Tên, Tỷ lệ (%), Loại (VAT/Phí).
  - Kết quả: \***\*\_\_\_\*\***

- [ ] **TC-QL-THUE-02** - Sửa tỷ lệ VAT từ 10% → 8%
  - Expected: DB: Thue.tyLe=8. Hóa đơn mới sẽ tính VAT 8%.
  - Kết quả: \***\*\_\_\_\*\***

---

## VI. CHECKLIST REPORT & SIGN-OFF

### Final Checklist

- [ ] ✅ Tất cả TC-CA (Ca làm việc) - **22 test cases**
  - Status: [ ] PASS [ ] FAIL
  - Failed cases: ****\*\*****\_\_\_****\*\*****

- [ ] ✅ Tất cả TC-DB (Đặt bàn) - **18 test cases**
  - Status: [ ] PASS [ ] FAIL
  - Failed cases: ****\*\*****\_\_\_****\*\*****

- [ ] ✅ Tất cả TC-GOP (Gộp bàn) - **10 test cases**
  - Status: [ ] PASS [ ] FAIL
  - Failed cases: ****\*\*****\_\_\_****\*\*****

- [ ] ✅ Tất cả TC-DOI (Đổi bàn) - **10 test cases**
  - Status: [ ] PASS [ ] FAIL
  - Failed cases: ****\*\*****\_\_\_****\*\*****

- [ ] ✅ Tất cả TC-GOI (Gọi món) - **11 test cases**
  - Status: [ ] PASS [ ] FAIL
  - Failed cases: ****\*\*****\_\_\_****\*\*****

- [ ] ✅ Tất cả TC-LHD (Lập hóa đơn) - **20 test cases**
  - Status: [ ] PASS [ ] FAIL
  - Failed cases: ****\*\*****\_\_\_****\*\*****

- [ ] ✅ Tất cả TC-EDGE (Edge Cases) - **20 test cases**
  - Status: [ ] PASS [ ] FAIL
  - Failed cases: ****\*\*****\_\_\_****\*\*****

- [ ] ✅ Quản Lý Bàn & Khu Vực - **9 test cases**
  - Status: [ ] PASS [ ] FAIL
  - Failed cases: ****\*\*****\_\_\_****\*\*****

- [ ] ✅ Quản Lý Nhân Viên - **4 test cases**
  - Status: [ ] PASS [ ] FAIL
  - Failed cases: ****\*\*****\_\_\_****\*\*****

- [ ] ✅ Quản Lý Thực Đơn - **3 test cases**
  - Status: [ ] PASS [ ] FAIL
  - Failed cases: ****\*\*****\_\_\_****\*\*****

- [ ] ✅ Quản Lý Khuyến Mãi & Thuế - **6 test cases**
  - Status: [ ] PASS [ ] FAIL
  - Failed cases: ****\*\*****\_\_\_****\*\*****

### Security & Performance

- [ ] ⚠️ Không có lỗi SQL Injection
  - Status: [ ] YES [ ] NO
  - Notes: ****\*\*****\_\_\_****\*\*****

- [ ] ⚠️ Không có memory leak (sau 8 giờ test)
  - Status: [ ] YES [ ] NO
  - Notes: ****\*\*****\_\_\_****\*\*****

- [ ] ⚠️ Dữ liệu DB đúng sau mỗi giao dịch
  - Status: [ ] YES [ ] NO
  - Notes: ****\*\*****\_\_\_****\*\*****

- [ ] ⚠️ App không crash trong 8 giờ hoạt động
  - Status: [ ] YES [ ] NO
  - Notes: ****\*\*****\_\_\_****\*\*****

- [ ] ⚠️ Connection pool quản lý đúng (< 100 client)
  - Status: [ ] YES [ ] NO
  - Notes: ****\*\*****\_\_\_****\*\*****

- [ ] ⚠️ Concurrency lock hoạt động (2+ client cùng lúc)
  - Status: [ ] YES [ ] NO
  - Notes: ****\*\*****\_\_\_****\*\*****

### Final Sign-Off

- [ ] 🎯 Ready for Defense
  - QA Engineer: **\*\*\*\***\_**\*\*\*\*** Ngày: **_/_**/**\_\_**
  - Project Lead: **\*\*\*\***\_**\*\*\*\*** Ngày: **_/_**/**\_\_**
  - Development Lead: **\*\*\*\***\_**\*\*\*\*** Ngày: **_/_**/**\_\_**

---

## NOTES & ISSUES TRACKING

### Critical Issues (Phải fix trước khi bảo vệ)

| ID      | Description | Priority | Status             | Fixed Date |
| ------- | ----------- | -------- | ------------------ | ---------- |
| BUG-001 |             | [ ] HIGH | [ ] OPEN [ ] FIXED |            |
| BUG-002 |             | [ ] HIGH | [ ] OPEN [ ] FIXED |            |

### Major Issues (Nên fix, nhưng có thể tolerate)

| ID      | Description | Priority   | Status             | Fixed Date |
| ------- | ----------- | ---------- | ------------------ | ---------- |
| BUG-010 |             | [ ] MEDIUM | [ ] OPEN [ ] FIXED |            |
| BUG-011 |             | [ ] MEDIUM | [ ] OPEN [ ] FIXED |            |

### Minor Issues (Low priority, có thể fix sau)

| ID      | Description | Priority | Status             | Fixed Date |
| ------- | ----------- | -------- | ------------------ | ---------- |
| BUG-020 |             | [ ] LOW  | [ ] OPEN [ ] FIXED |            |
| BUG-021 |             | [ ] LOW  | [ ] OPEN [ ] FIXED |            |

---

## EVIDENCE & ATTACHMENTS

- [ ] Screenshot: ****\*\*****\_\_\_****\*\*****
- [ ] Video: ****\*\*****\_\_\_****\*\*****
- [ ] Log file: ****\*\*****\_\_\_****\*\*****
- [ ] SQL query: ****\*\*****\_\_\_****\*\*****
- [ ] Test data: ****\*\*****\_\_\_****\*\*****

---

**Hướng dẫn sử dụng:**

1. Tích vào [ ] để mark khi hoàn thành từng test case.
2. Ghi kết quả chi tiết vào cột "Kết quả".
3. Nếu FAIL, ghi lại bug ID vào "Failed cases".
4. Sau cùng, điền vào phần "Final Sign-Off" khi toàn bộ xong.
5. Lưu file và gửi cho Lead Review trước khi submit bảo vệ.
