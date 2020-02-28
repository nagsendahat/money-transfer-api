package com.moneytransfer.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public EntityNotFoundException(String message, String... args) {
      this.message = String.format(message, args);
	}

	public EntityNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	  public String getMessage() {
	    return this.message;
	  }
}
