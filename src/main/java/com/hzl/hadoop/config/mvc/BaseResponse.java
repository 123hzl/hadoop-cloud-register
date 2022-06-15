package com.hzl.hadoop.config.mvc;

/**
 * description
 * 自定义请求响应
 * 适配ant desgin pro
 * @author hzl 2022/06/14 5:02 PM
 */
public class BaseResponse<T> {

	/**
	 * 请求状态，true表示成功，false表示失败
	 */
	private Boolean status;
	/**
	 * 请求状态，true表示成功，false表示失败
	 */
	private Boolean success;

	/**
	 * 返回的数据
	 */
	private T data;

	/**
	 * 错误信息
	 */
	private String errorMessage;
	/**
	 * 错误信息编码
	 */
	private String errorCode;

	/**	设置提示框样式
	     silent: 0
	     message.warn: 1
	     message.error: 2
	     notification: 4
	     page: 9 page
	 */
	private Integer showType;

	/**
	 * 访问的host
	 * */
	private String host;

	/**
	* 链路id
	* */
	private String traceId;


	public BaseResponse(T data) {
		this.status = true;
		this.success = true;
		this.data = data;
	}

	public BaseResponse(T data, Boolean status, String errorMessage) {
		this.status = status;
		this.data = data;
		this.success = status;
		this.errorMessage = errorMessage;
		this.showType=2;
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

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
}
