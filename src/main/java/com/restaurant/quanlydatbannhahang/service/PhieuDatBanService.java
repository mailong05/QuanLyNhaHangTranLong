package com.restaurant.quanlydatbannhahang.service;

import com.restaurant.quanlydatbannhahang.dao.PhieuDatBanDAO;
import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.dao.ChiTietPhieuDatBanDAO;
import com.restaurant.quanlydatbannhahang.entity.PhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.ChiTietPhieuDatBan;
import com.restaurant.quanlydatbannhahang.entity.KhachHang;
import com.restaurant.quanlydatbannhahang.entity.LoaiThanhVien;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiPhieuDat;
import com.restaurant.quanlydatbannhahang.session.SessionManager;
import com.restaurant.quanlydatbannhahang.entity.TrangThaiBan;
import com.restaurant.quanlydatbannhahang.entity.Ban;
import com.restaurant.quanlydatbannhahang.util.IDGeneratorHelper;
import com.restaurant.quanlydatbannhahang.util.IDQueryHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class PhieuDatBanService {
    private PhieuDatBanDAO phieuDatBanDAO;
    private ChiTietPhieuDatBanDAO chiTietPhieuDatBanDAO;
    private BanService banService;
    private NhanVienService nhanVienService;

    private static final String PHONE_PATTERN = "^0[1-9]\\d{8}$";
    private static final String HOTEN_PATTERN = "^[\\p{Lu}][\\p{Ll}]*(\\s[\\p{Lu}][\\p{Ll}]*)*$";
    private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);
    private static final Pattern hoTenPattern = Pattern.compile(HOTEN_PATTERN);

    public PhieuDatBanService() {
        this.phieuDatBanDAO = new PhieuDatBanDAO();
        this.chiTietPhieuDatBanDAO = new ChiTietPhieuDatBanDAO();
        this.banService = new BanService();
        this.nhanVienService = new NhanVienService();
    }

    /**
     * Business Logic: Tạo phiếu đặt bàn tạm thời cho khách dùng ngay (Vãng lai)
     * maKH sẽ được để trống (null) theo thiết kế DB mới.
     */
    public String taoPhieuDatDungNgay(Set<String> dsMaBan, String maNV) {
        if (dsMaBan == null || dsMaBan.isEmpty()) {
            throw new IllegalArgumentException("Phải chọn ít nhất một bàn để bắt đầu phục vụ");
        }

        // 1. Tạo đối tượng phiếu đặt mới
        String lastID = phieuDatBanDAO.getLastPhieuDatBanID();
        String maPhieuMoi = IDGeneratorHelper.generateNextIDFromFullID(lastID);
        
        PhieuDatBan phieu = new PhieuDatBan();
        phieu.setMaPhieuDat(maPhieuMoi);
        phieu.setKhachHang(null); // ĐỂ NULL: Khách dùng ngay chưa có thông tin
        phieu.setNhanVien(nhanVienService.getNhanVienTheoMa(maNV));
        phieu.setThoiGianDen(LocalDateTime.now());
        phieu.setSoLuongNguoi(0); // Sẽ cập nhật sau nếu cần
        phieu.setTrangThai(TrangThaiPhieuDat.DANG_SU_DUNG);

        // 2. Lưu vào DB
        if (!phieuDatBanDAO.themPhieuDatBan(phieu)) {
            throw new RuntimeException("Không thể khởi tạo phiếu đặt bàn cho khách");
        }

        // 3. Liên kết bàn và cập nhật trạng thái vật lý của bàn
        for (String maBan : dsMaBan) {
            ChiTietPhieuDatBan ct = new ChiTietPhieuDatBan();
            ct.setPhieuDatBan(phieu);
            ct.setBan(banService.getBanTheoMa(maBan));
            chiTietPhieuDatBanDAO.themChiTietPhieuDatBan(ct);

            // Chuyển bàn sang trạng thái ĐANG_DUNG
            banService.capNhatTrangThaiBan(maBan, TrangThaiBan.DANG_DUNG);
        }

        return maPhieuMoi;
    }

    /**
     * Logic validate linh hoạt: 
     * - Nếu DANG_CHO (Đặt trước): Bắt buộc có KhachHang.
     * - Nếu DANG_SU_DUNG (Dùng ngay): KhachHang có thể NULL.
     */
    public void validatePhieuDatBan(PhieuDatBan phieu) {
        if (phieu == null) {
            throw new IllegalArgumentException("Dữ liệu phiếu đặt không được để trống");
        }

        // Chỉ validate khách hàng nếu trạng thái là ĐẶT TRƯỚC hoặc nếu đối tượng khách đã được gán
        if (phieu.getTrangThai() == TrangThaiPhieuDat.DANG_CHO) {
            if (phieu.getKhachHang() == null) {
                throw new IllegalArgumentException("Luồng đặt trước yêu cầu thông tin khách hàng");
            }
            
            String hoTen = phieu.getKhachHang().getHoTen();
            String sdt = phieu.getKhachHang().getSdt();

            if (hoTen == null || hoTen.isBlank()) {
                throw new IllegalArgumentException("Họ tên khách hàng không được để trống");
            }
            if (!hoTenPattern.matcher(hoTen).matches()) {
                throw new IllegalArgumentException("Tên phải viết hoa chữ cái đầu (VD: Nguyễn Văn A)");
            }

            if (sdt == null || sdt.isBlank()) {
                throw new IllegalArgumentException("Số điện thoại không được để trống");
            }
            if (!phonePattern.matcher(sdt).matches()) {
                throw new IllegalArgumentException("Số điện thoại phải đủ 10 số và bắt đầu bằng số 0");
            }
            
            if (phieu.getThoiGianDen() == null || phieu.getThoiGianDen().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("Thời gian đến phải ở tương lai");
            }
        }

        // Validate số lượng người (Chung cho mọi loại phiếu)
        if (phieu.getSoLuongNguoi() < 0) {
            throw new IllegalArgumentException("Số lượng người không được là số âm");
        }
    }

    /**
     * Cập nhật mã khách hàng (Khi nhân viên tìm thấy khách qua SĐT tại quầy thanh toán)
     */
    public void capNhatKhachHangChoPhieu(String maPhieu, String maKH) {
        if (maPhieu == null || maPhieu.isBlank()) {
            throw new IllegalArgumentException("Mã phiếu không được để trống");
        }
        
        boolean success = phieuDatBanDAO.capNhatKhachHangChoPhieu(maPhieu, maKH);
        if (!success) {
            throw new RuntimeException("Cập nhật khách hàng vào phiếu thất bại");
        }
    }

    /**
     * Khi thanh toán thành công: Giải phóng bàn và đóng phiếu
     */
    public void hoanTatPhieuDatBan(String maPhieu) {
        // 1. Đổi trạng thái phiếu sang DA_SU_DUNG
        phieuDatBanDAO.capNhatTrangThaiPhieu(maPhieu, TrangThaiPhieuDat.DA_SU_DUNG);

        // 2. Lấy danh sách bàn liên quan và giải phóng về TRONG
        List<ChiTietPhieuDatBan> chiTiets = chiTietPhieuDatBanDAO.getChiTietByMaPhieuDat(maPhieu);
        for (ChiTietPhieuDatBan ct : chiTiets) {
            banService.capNhatTrangThaiBan(ct.getBan().getMaBan(), TrangThaiBan.TRONG);
        }
    }

    public PhieuDatBan getPhieuDatBanTheoMa(String maPhieu) {
        if (maPhieu == null || maPhieu.isBlank()) {
            throw new IllegalArgumentException("Mã phiếu không được rỗng");
        }
        return phieuDatBanDAO.getPhieuDatBanTheoMa(maPhieu);
    }

    public void themPhieuDatBan(PhieuDatBan phieuDatBan) {
        validatePhieuDatBan(phieuDatBan); // Gọi validate đã refactor
        if (!phieuDatBanDAO.themPhieuDatBan(phieuDatBan)) {
            throw new RuntimeException("Thêm phiếu đặt bàn thất bại");
        }
    }
    
    public String getLastPhieuDatBanID() {
        return phieuDatBanDAO.getLastPhieuDatBanID();
    }

	public String themPhieuDatBan(String maPDB, String tenKhachHang, String soDienThoai, int soLuongNguoi,
			LocalDateTime thoiGianDen, String ghiChu, Set<String> selectedTables) {
		// TODO Auto-generated method stub
		KhachHangService khService = new KhachHangService();
	    KhachHang kh = khService.getKhachHangTheoSDT(soDienThoai);
	    
	    if (kh == null) {
	        // Nếu khách chưa tồn tại, tạo mới khách hàng với thông tin cơ bản
	       String lastID = IDQueryHelper.getLastID("KhachHang", "maKH");
	       String newID = IDGeneratorHelper.generateNextIDFromFullID(lastID);
	        kh = new KhachHang(newID, tenKhachHang, soDienThoai, 0, LoaiThanhVien.DONG);
	        khService.themKhachHang(kh);
	    }

	    // 2. Khởi tạo đối tượng PhieuDatBan
	    PhieuDatBan phieu = new PhieuDatBan();
	    phieu.setMaPhieuDat(maPDB);
	    phieu.setKhachHang(kh); // Gán thực thể khách hàng vào phiếu
	    phieu.setNhanVien(SessionManager.getCurrentNhanVien()); // Lấy NV đang đăng nhập
	    phieu.setThoiGianDen(thoiGianDen);
	    phieu.setSoLuongNguoi(soLuongNguoi);
	    phieu.setGhiChu(ghiChu);
	    phieu.setTrangThai(TrangThaiPhieuDat.DANG_CHO); // Trạng thái đặt trước

	    // 3. Validate dữ liệu phiếu trước khi lưu
	    validatePhieuDatBan(phieu);

	    // 4. Lưu phiếu vào Database thông qua DAO
	    boolean checkPDB = phieuDatBanDAO.themPhieuDatBan(phieu);
	    if (!checkPDB) {
	        throw new RuntimeException("Lỗi: Không thể lưu phiếu đặt bàn vào hệ thống.");
	    }

	    // 5. Lưu chi tiết phiếu (liên kết bàn) và cập nhật trạng thái bàn vật lý
	    for (String maBan : selectedTables) {
	        // A. Thêm dòng vào ChiTietPhieuDatBan
	        ChiTietPhieuDatBan ct = new ChiTietPhieuDatBan();
	        ct.setPhieuDatBan(phieu);
	        ct.setBan(banService.getBanTheoMa(maBan));
	        ct.setGhiChu("");
	        chiTietPhieuDatBanDAO.themChiTietPhieuDatBan(ct);

	        // B. Cập nhật trạng thái bàn thành DA_DAT (Màu vàng)
	        banService.capNhatTrangThaiBan(maBan, TrangThaiBan.DA_DAT);
	    }

	    return "Đặt bàn thành công cho khách hàng " + tenKhachHang;
	}
	
	public void capNhatTrangThaiPhieu(String maPDB, TrangThaiPhieuDat trangThai) {
		phieuDatBanDAO.capNhatTrangThaiPhieu(maPDB, trangThai);
	}

	public void capNhatPhieuDatBan(PhieuDatBan phieu) {
		// TODO Auto-generated method stub
		validatePhieuDatBan(phieu);
		phieuDatBanDAO.capNhatPhieuDatBan(phieu);	
	}

	public void xoaPhieuDatBan(String maPDB) {
		// TODO Auto-generated method stub
		if(maPDB == null || maPDB.isBlank()) {
			throw new IllegalArgumentException("Không thể xóa với mã phiếu đặt bàn trống");
		}
		phieuDatBanDAO.xoaPhieuDatBan(maPDB);
	}

	public ArrayList<PhieuDatBan> getAllPhieuDatBan() {
		// TODO Auto-generated method stub
		return (ArrayList<PhieuDatBan>) phieuDatBanDAO.getAllPhieuDatBan();
		
	}

	public void batDauSuDung(String maPhieu) {
        PhieuDatBan phieu = getPhieuDatBanTheoMa(maPhieu);
        if (phieu != null && phieu.getTrangThai() == TrangThaiPhieuDat.DANG_CHO) {
            capNhatTrangThaiPhieu(maPhieu, TrangThaiPhieuDat.DANG_SU_DUNG);
        } else {
            throw new IllegalStateException("Không thể bắt đầu sử dụng. Phiếu phải ở trạng thái Đang chờ");
        }
}

	public List<PhieuDatBan> getDanhSachHoatDongGanDay() {
	 return	phieuDatBanDAO.getHoatDongGanDay();
	}

	
}