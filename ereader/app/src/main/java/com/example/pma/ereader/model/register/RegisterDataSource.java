package com.example.pma.ereader.model.register;

import com.example.pma.ereader.model.login.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class RegisterDataSource {

	public static List<RegisteredUser> PREDEFINED_USERS = Arrays.asList(RegisteredUser.builder().fullName("User1").userName("user@one.com").build(),
		RegisteredUser.builder().fullName("User2").userName("user@two.com").build(),
		RegisteredUser.builder().fullName("User3").userName("user@three.com").build());

	public Result<RegisteredUser> register(String name, String username, String password, String repeatedPassword) {
		try {
			// TODO: handle loggedInUser authentication
			RegisteredUser registeredUser = new RegisteredUser(name, username);
			return new Result.Success<>(registeredUser);
		} catch (Exception e) {
			return new Result.Error(new IOException("Error logging in", e));
		}
	}

	public void logout() {
		// TODO: revoke authentication
	}
}
