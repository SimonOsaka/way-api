package com.zl.way.amap.model;

public class AMapStaticMapResponse {

	private int code;
	private String msg;
	private AMapStaticMapModel aMapStaticMapModel;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public AMapStaticMapModel getaMapStaticMapModel() {
		return aMapStaticMapModel;
	}

	public void setaMapStaticMapModel(AMapStaticMapModel aMapStaticMapModel) {
		this.aMapStaticMapModel = aMapStaticMapModel;
	}
}
