package com.example.pma.ereader.model.register;

import com.example.pma.ereader.MyApplication;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class RegisterDataSource {

	private boolean successResult = false;

	public RegisteredUser register(String name, String username, String password, String repeatedPassword) {

		/*Retrofit retrofit = NetworkClient.getRetrofitClient();
		UserApi userApi = retrofit.create(UserApi.class);
		Call call = userApi.postUserDetails(User.builder().fullName(name).username(username).password(password).build());

		call.enqueue(new Callback() {
			@Override
			public void onResponse(Call call, Response response) {
				if (response.isSuccessful()) {
					successResult = true;
					Log.i(this.getClass().getSimpleName(), "Successfully registered new user");
				} else {
					Log.w(this.getClass().getSimpleName(), String.format("Error while registering new user with code %s", response.code()));
				}
			}

			@Override
			public void onFailure(Call call, Throwable t) {
				Log.w(this.getClass().getSimpleName(), "Error while registering new user");
				throw new RuntimeException("Error registering new user", t);
			}
		});
		if (successResult) {
			return new Result.Success<>(new RegisteredUser(name, username));
		}
		return new Result.Error(new IOException("Error logging in", e));*/

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
			return registeredUser;
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(), "Error registering", e);
			return null;
		}
	}
}
