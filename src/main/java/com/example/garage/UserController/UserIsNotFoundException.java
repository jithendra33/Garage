package com.example.garage.UserController;

public class UserIsNotFoundException  extends RuntimeException{
	
	private String message;

	public UserIsNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
