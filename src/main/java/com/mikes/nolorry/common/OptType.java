package com.mikes.nolorry.common;

public enum OptType {

	LOGIN("登录", 1), LOGOUT("注销", 2), ADD("添加", 3), UPDATE("修改", 4), DELETE("刪除", 5), QUERY("查看", 6);

	private String name;
	private int value;

	OptType(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public int value() {
		return this.value;
	}

}
