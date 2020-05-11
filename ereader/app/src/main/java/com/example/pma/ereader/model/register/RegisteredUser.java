package com.example.pma.ereader.model.register;

import java.util.UUID;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class RegisteredUser {

	private String userId;
	private String fullName;
	private String userName;

	private RegisteredUser() {

	}

	RegisteredUser(String fullName, String userName) {
		this.userId = UUID.randomUUID().toString();
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

	public static Builder builder() {
		return new Builder(new RegisteredUser());
	}

	public static class Builder {

		private RegisteredUser instance;

		private Builder(final RegisteredUser instance) {
			this.instance = instance;
		}

		public Builder fullName(String fullName) {
			instance.fullName = fullName;
			return this;
		}

		public Builder userName(String userName) {
			instance.userName = userName;
			return this;
		}

		public RegisteredUser build() {
			return new RegisteredUser(instance.getFullName(), instance.getUserName());
		}
	}
}

