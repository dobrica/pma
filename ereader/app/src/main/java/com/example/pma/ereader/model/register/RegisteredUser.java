package com.example.pma.ereader.model.register;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class RegisteredUser {

	private String userId;
	private String fullName;
	private String userName;

	public RegisteredUser(String userId, String fullName, String userName) {
		this.userId = userId;
		this.fullName = fullName;
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}
}

