package com.exception;

public class ProphetException extends RuntimeException{
	private static final long serialVersionUID = 6326269736365777366L;
	private String errorCode;
	public ProphetException() {
		super();
	}

	public ProphetException(String message) {
		super(message);
	}
	public ProphetException(String errorCode,String message) {
		super(message);
		this.errorCode = errorCode;
	}
	public ProphetException(Throwable throwable) {
		super(throwable);
	}

	public ProphetException(String message, Throwable throwable) {
		super(message, throwable);
	}
	public ProphetException(String errorCode,String message, Throwable throwable) {
		super(message, throwable);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
