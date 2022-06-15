package com.hzl.hadoop.config.mvc;

import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * description
 * 自定义请求响应
 *
 * @author hzl 2022/06/14 5:02 PM
 */
public class BaseResponse<T>{

	/**
	 * 请求状态，true表示成功，false表示失败
	 */
	private Boolean status;

	/**
	 * 返回的数据
	 */
	private T data;

	public BaseResponse(T data) {
		this.status = true;
		this.data = data;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
