package com.zl.way.amap.model;

public class AMapRegeoResponse {

	private int code;
	private String msg;
	private AMapRegeoModel aMapRegeoModel;

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

	public AMapRegeoModel getaMapRegeoModel() {
		return aMapRegeoModel;
	}

	public void setaMapRegeoModel(AMapRegeoModel aMapRegeoModel) {
		this.aMapRegeoModel = aMapRegeoModel;
	}
}
