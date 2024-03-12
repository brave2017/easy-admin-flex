package com.easy.common.exception;


import com.easy.common.utils.MessageUtils;

/**
 * 自定义异常
 *
 * @author Maw
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    private int code;
	private String msg;

	public ServiceException(int code) {
		this.code = code;
		this.msg = MessageUtils.getMessage(code);
	}

	public ServiceException(int code, String... params) {
		this.code = code;
		this.msg = MessageUtils.getMessage(code, params);
	}

	public ServiceException(int code, Throwable e) {
		super(e);
		this.code = code;
		this.msg = MessageUtils.getMessage(code);
	}

	public ServiceException(int code, Throwable e, String... params) {
		super(e);
		this.code = code;
		this.msg = MessageUtils.getMessage(code, params);
	}

	public ServiceException(String msg) {
		super(msg);
		this.code = ErrorCode.INTERNAL_SERVER_ERROR;
		this.msg = msg;
	}

	public ServiceException(String msg, Throwable e) {
		super(msg, e);
		this.code = ErrorCode.INTERNAL_SERVER_ERROR;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
