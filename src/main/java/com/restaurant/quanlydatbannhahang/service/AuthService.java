package com.restaurant.quanlydatbannhahang.service;

import java.util.regex.Pattern;

import com.restaurant.quanlydatbannhahang.dao.TaiKhoanDAO;
import com.restaurant.quanlydatbannhahang.entity.TaiKhoan;

public class AuthService {
    private TaiKhoanDAO taiKhoanDAO;
    private static final String MATKHAU_PATTERN = "^\\d{6}$";
    private static final Pattern matKhauPattern = Pattern.compile(MATKHAU_PATTERN); 
    public AuthService() {
        this.taiKhoanDAO = new TaiKhoanDAO();
    }

    
    public TaiKhoan login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println(" Tên đăng nhập không được để trống!");
            return null;
        }
        

        if (password == null || password.trim().isEmpty()) {
            System.out.println(" Mật khẩu không được để trống!");
            return null;
        }
        
        if(!matKhauPattern.matcher(password).matches()) {
        	return null;
        }
        
        
        
        TaiKhoan taiKhoan = taiKhoanDAO.findByUsernameAndPassword(username, password);

        if (taiKhoan == null) {
            return null;
        }

        
        return taiKhoan;
    }

    /**
     * Kiểm tra tài khoản tồn tại
     */
    public boolean checkUsernameExists(String username) {
        return taiKhoanDAO.existUsername(username);
    }

    /**
     * Lấy thông tin tài khoản theo username
     */
    public TaiKhoan getTaiKhoanByUsername(String username) {
        return taiKhoanDAO.findByUsername(username);
    }
}
