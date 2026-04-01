package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.LoaiMonAn;
import java.sql.Connection;
import java.util.List;

public class LoaiMonAnDAO {
    private Connection connection;

    public LoaiMonAnDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean themLoaiMonAn(LoaiMonAn loai) {
        return false;
    }

    public LoaiMonAn getLoaiMonAnTheoMa(String maLoai) {
        return null;
    }

    public List<LoaiMonAn> getAllLoaiMonAn() {
        return null;
    }

    public boolean capNhatLoaiMonAn(LoaiMonAn loai) {
        return false;
    }

    public boolean xoaLoaiMonAn(String maLoai) {
        return false;
    }

    public LoaiMonAn getLoaiMonAnTheoTen(String tenLoai) {
        return null;
    }
}
