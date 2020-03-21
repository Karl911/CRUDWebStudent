package com.kfa.bank.utils;

public class SizeUnit {
	
	private String size;
	private EnumUnit unit;
	
	public SizeUnit (String size, EnumUnit unit) {
		this.size = size;
		this.unit = unit;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public EnumUnit getUnit() {
		return unit;
	}

	public void setUnit(EnumUnit unit) {
		this.unit = unit;
	}
}
