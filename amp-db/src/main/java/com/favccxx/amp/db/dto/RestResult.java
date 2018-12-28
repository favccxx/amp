package com.favccxx.amp.db.dto;

import java.io.Serializable;

import com.favccxx.amp.db.constants.RestState;

/**
 * REST响应数据
 * 
 * @author favccxx
 *
 */
public class RestResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 状态吗
	 */
	private int status;

	/**
	 * 返回数据
	 */
	private Object data;

	/**
	 * 消息
	 */
	private String message = "SUCCESS";


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



	public RestResult(int status) {
		this.status = status;
	}

	public RestResult(int status, Object data) {
		this.status = status;
		this.data = data;
	}

	public RestResult(int status, Object data, String message) {
		this.status = status;
		this.data = data;
		this.message = message;
	}



	public static RestResult ok() {
		return new RestResult(RestState.SUCCESS.getState());
	}
	
	public static RestResult ok(Object data) {
		return new RestResult(RestState.SUCCESS.getState(), data);
	}
	
	public static RestResult ok(Object data, String message) {
		return new RestResult(RestState.SUCCESS.getState(), data, message);
	}
	
	public static RestResult error() {
		return new RestResult(RestState.ERROR.getState(), "", "error");
	}
	
	public static RestResult error(String error) {
		return new RestResult(RestState.ERROR.getState(), "", error);
	}
}
