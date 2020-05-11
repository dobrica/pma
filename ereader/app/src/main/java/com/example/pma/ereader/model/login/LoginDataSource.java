package com.example.pma.ereader.model.login;

import com.example.pma.ereader.LoginActivity;
import com.example.pma.ereader.MyApplication;
import com.example.pma.ereader.model.register.RegisterDataSource;
import com.example.pma.ereader.model.register.RegisteredUser;
import com.google.gson.Gson;

import java.io.IOException;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;
import androidx.annotation.StringRes;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

	public Result<LoggedInUser> login(String username, String password) {
		try {
			for (RegisteredUser registeredUser  : RegisterDataSource.PREDEFINED_USERS) {
				if(registeredUser.getUserName().equals(username)) {
					LoggedInUser loggedInUser = new LoggedInUser(username, username);
					Gson gson = new Gson();
					final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putString("UserLoggedIn", gson.toJson(registeredUser));
					editor.apply();
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
