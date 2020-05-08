package com.example.pma.ereader.model.login;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

	public Result<LoggedInUser> login(String username, String password) {
		try {
			// TODO: handle loggedInUser authentication
			LoggedInUser loggedInUser = new LoggedInUser(username, username);
			return new Result.Success<>(loggedInUser);
		} catch (Exception e) {
			return new Result.Error(new IOException("Error logging in", e));
		}
	}

	public void logout() {
		// TODO: revoke authentication
	}
}
