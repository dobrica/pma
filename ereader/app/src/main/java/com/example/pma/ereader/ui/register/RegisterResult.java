package com.example.pma.ereader.ui.register;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
public class RegisterResult {
	@Nullable
	private RegisteredUserView success;
	@Nullable
	private Integer error;

	RegisterResult(@Nullable Integer error) {
		this.error = error;
	}

	RegisterResult(@Nullable RegisteredUserView success) {
		this.success = success;
	}

	@Nullable
	public RegisteredUserView getSuccess() {
		return success;
	}

	@Nullable
	public Integer getError() {
		return error;
	}
}
