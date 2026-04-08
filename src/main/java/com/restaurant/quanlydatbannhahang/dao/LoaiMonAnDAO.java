package com.restaurant.quanlydatbannhahang.dao;

import com.restaurant.quanlydatbannhahang.connectDB.DatabaseConnection;
import com.restaurant.quanlydatbannhahang.entity.LoaiMonAn;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class LoaiMonAnDAO {

    public LoaiMonAnDAO() {
        // LoaiMonAn is an enum-based DAO
    }

    public boolean themLoaiMonAn(LoaiMonAn loai) {
        // LoaiMonAn is an enum, typically just read from database
        // This might not be used for insertion
        return true;
    }

    public LoaiMonAn getLoaiMonAnTheoMa(String maLoai) {
        try {
            return LoaiMonAn.valueOf(maLoai);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public List<LoaiMonAn> getAllLoaiMonAn() {
        ArrayList<LoaiMonAn> dsList = new ArrayList<>();
        try {
            for (LoaiMonAn loai : LoaiMonAn.values()) {
                dsList.add(loai);
            }
            return dsList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsList;
    }

    public boolean capNhatLoaiMonAn(LoaiMonAn loai) {
        // LoaiMonAn is an enum, typically not updated
        return true;
    }

    public boolean xoaLoaiMonAn(String maLoai) {
        // LoaiMonAn is an enum, typically not deleted
        return true;
    }

    public LoaiMonAn getLoaiMonAnTheoTen(String tenLoai) {
        try {
            for (LoaiMonAn loai : LoaiMonAn.values()) {
                if (loai.getDisplayName().equals(tenLoai)) {
                    return loai;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
