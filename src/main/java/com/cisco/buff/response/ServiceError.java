package com.cisco.buff.response;

import java.io.Serializable;

public class ServiceError implements Serializable {

	public ServiceError(String code, String type, String errorMsg) {
		this.code = code;
		this.type = type;
		this.errorMsg = errorMsg;

	}

	private static final long serialVersionUID = 1L;
	protected String code;
	protected String type;
	protected String errorMsg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
