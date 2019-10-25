package com.revature.watercanappreservems.dto;

public class Message {

	private String message;

	private String Status;

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getMessage() {
		return message;
	}

	public Message(String message) {
		super();
		this.message = message;
	}

	public Message() {
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
