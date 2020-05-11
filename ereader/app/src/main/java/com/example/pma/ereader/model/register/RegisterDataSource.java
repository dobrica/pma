package com.example.pma.ereader.model.register;

import com.example.pma.ereader.MyApplication;
import com.example.pma.ereader.model.login.Result;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class RegisterDataSource {

	public Result<RegisteredUser> register(String name, String username, String password, String repeatedPassword) {
		try {
			RegisteredUser registeredUser = new RegisteredUser(name, username);
			final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
			SharedPreferences.Editor editor = sharedPref.edit();
			final Set<String> existingUsers = sharedPref.getStringSet("Users", Collections.<String>emptySet());
			editor.remove("Users");
			editor.apply();
			existingUsers.add(new Gson().toJson(registeredUser));
			editor.putStringSet("Users", existingUsers);
			editor.apply();
			final Set<String> nakon = sharedPref.getStringSet("Users", Collections.<String>emptySet());
			return new Result.Success<>(registeredUser);
		} catch (Exception e) {
			return new Result.Error(new IOException("Error logging in", e));
		}
	}
}
