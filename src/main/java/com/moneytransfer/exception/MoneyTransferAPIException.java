package com.moneytransfer.exception;

public class MoneyTransferAPIException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public MoneyTransferAPIException(String message, String... args) {
	    this.message = String.format(message, args);
	}

	public MoneyTransferAPIException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	  public String getMessage() {
	    return this.message;
	  }
}
