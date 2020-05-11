package com.example.pma.ereader.model.register;

import com.example.pma.ereader.model.login.LoggedInUser;
import com.example.pma.ereader.model.login.Result;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class RegisterRepository {

	private static volatile RegisterRepository instance;

	private RegisterDataSource dataSource;

	private RegisteredUser user = null;

	// private constructor : singleton access
	private RegisterRepository(RegisterDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public static RegisterRepository getInstance(RegisterDataSource dataSource) {
		if (instance == null) {
			instance = new RegisterRepository(dataSource);
		}
		return instance;
	}

	private void setLoggedInUser(RegisteredUser user) {
		this.user = user;
	}

	public Result<RegisteredUser> register(String name, String username, String password, String repeatedPassword) {
		Result<RegisteredUser> result = dataSource.register(name, username, password, repeatedPassword);
		if (result instanceof Result.Success) {
			setLoggedInUser(((Result.Success<RegisteredUser>) result).getData());
		}
		return result;
	}
}
