package com.example.pma.ereader.model.login;

import com.example.pma.ereader.MyApplication;
import com.example.pma.ereader.model.register.RegisteredUser;
import com.google.gson.Gson;

import java.util.Collections;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

	public LoggedInUser login(String username, String password) {
		try {
			final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
			SharedPreferences.Editor editor = sharedPref.edit();
			Gson gson = new Gson();
			for (String registeredUser : sharedPref.getStringSet("Users", Collections.<String>emptySet())) {
				final RegisteredUser existing = gson.fromJson(registeredUser, RegisteredUser.class);
				if (existing.getUserName().equals(username)) {
					editor.putString("UserLoggedIn", registeredUser);
					editor.apply();
					return new LoggedInUser(username, username);
				}
			}
			return null;
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(), "Error logging in", e);
			return null;
		}
	}

	public void logout() {
		// TODO: revoke authentication
	}

}
