package com.example.pma.ereader.model.login;

import com.example.pma.ereader.MyApplication;
import com.example.pma.ereader.model.register.RegisteredUser;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Collections;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

	public Result<LoggedInUser> login(String username, String password) {
		try {
			final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
			SharedPreferences.Editor editor = sharedPref.edit();
			Gson gson = new Gson();
			for (String registeredUser  : sharedPref.getStringSet("Users", Collections.<String>emptySet())) {
				final RegisteredUser existing = gson.fromJson(registeredUser, RegisteredUser.class);
				if(existing.getUserName().equals(username)) {
					editor.putString("UserLoggedIn", registeredUser);
					editor.apply();
					LoggedInUser loggedInUser = new LoggedInUser(username, username);
					return new Result.Success<>(loggedInUser);
				}
			}
			return new Result.Error(new RuntimeException("User not found!"));
		} catch (Exception e) {
			return new Result.Error(new IOException("Error logging in", e));
		}
	}

	public void logout() {
		// TODO: revoke authentication
	}

}
