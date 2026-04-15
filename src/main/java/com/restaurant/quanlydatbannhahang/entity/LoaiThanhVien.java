package com.restaurant.quanlydatbannhahang.entity;

public enum LoaiThanhVien {
	VIP("VIP"),
	VANG("VÀNG"),
	BAC("BẠC"),
	DONG("ĐỒNG");

	private final String displayName;

	LoaiThanhVien(String displayName) {
		// TODO Auto-generated constructor stub
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

}
