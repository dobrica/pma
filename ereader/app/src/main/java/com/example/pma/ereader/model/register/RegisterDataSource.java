package com.example.pma.ereader.model.register;

import com.example.pma.ereader.model.login.Result;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class RegisterDataSource {

	public Result<RegisteredUser> register(String name, String username, String password, String repeatedPassword) {
		try {
			// TODO: handle loggedInUser authentication
			RegisteredUser registeredUser = new RegisteredUser(username, name, username);
			return new Result.Success<>(registeredUser);
		} catch (Exception e) {
			return new Result.Error(new IOException("Error logging in", e));
		}
	}

	public void logout() {
		// TODO: revoke authentication
	}
}
