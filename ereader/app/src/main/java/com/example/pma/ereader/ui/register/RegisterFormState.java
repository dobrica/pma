package com.example.pma.ereader.ui.register;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
public class RegisterFormState {
	@Nullable
	private Integer fullNameError;
	@Nullable
	private Integer usernameError;
	@Nullable
	private Integer passwordError;
	@Nullable
	private Integer passwordMatchError;
	private boolean isDataValid;

	RegisterFormState(@Nullable Integer fullNameError, @Nullable Integer usernameError, @Nullable Integer passwordError,
		@Nullable final Integer passwordMatchError) {
		this.fullNameError = fullNameError;
		this.usernameError = usernameError;
		this.passwordError = passwordError;
		this.passwordMatchError = passwordMatchError;
		this.isDataValid = false;
	}

	RegisterFormState(boolean isDataValid) {
		this.fullNameError = null;
		this.usernameError = null;
		this.passwordError = null;
		this.passwordMatchError = null;
		this.isDataValid = isDataValid;
	}

	@Nullable
	public Integer getFullNameError() {
		return fullNameError;
	}

	@Nullable
	public Integer getUsernameError() {
		return usernameError;
	}

	@Nullable
	public Integer getPasswordError() {
		return passwordError;
	}

	@Nullable
	public Integer getPasswordMatchError() {
		return passwordMatchError;
	}

	public boolean isDataValid() {
		return isDataValid;
	}
}
