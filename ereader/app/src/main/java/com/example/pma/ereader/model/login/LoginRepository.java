package com.example.pma.ereader.model.login;

import com.example.pma.ereader.MyApplication;
import com.example.pma.ereader.network.NetworkClient;
import com.example.pma.ereader.network.User;
import com.example.pma.ereader.network.UserApi;
import com.google.gson.Gson;

import java.util.List;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginRepository {

	private static volatile LoginRepository instance;

	public static LoginRepository getInstance() {
		if (instance == null) {
			instance = new LoginRepository();
		}
		return instance;
	}

	public void login(String username, String password, final LoginCallback loginCallback) {

		Retrofit retrofit = NetworkClient.getRetrofitClient();
		UserApi userApi = retrofit.create(UserApi.class);
		Call call = userApi.login(User.builder().username(username).password(password).build());

		call.enqueue(new Callback() {
			@Override
			public void onResponse(Call call, Response response) {
				if (response.isSuccessful()) {
					final List<String> authHeaders = response.headers().values("Authorization");
					if (!authHeaders.isEmpty()) {
						final String token = authHeaders.get(0);
						getUserInfo(userApi, token, username);
						loginCallback.onSuccess(new LoggedInUser(username, username, token));
					} else {
						loginCallback.onSuccess(new LoggedInUser(username, username, null));
					}
				} else {
					Log.w(this.getClass().getSimpleName(), String.format("Error while logging in with code %s", response.code()));
					loginCallback.onSuccess(new LoggedInUser(username, username, null));
				}
			}

			@Override
			public void onFailure(Call call, Throwable t) {
				throw new RuntimeException("Error logging in", t);
			}
		});
	}

	private void getUserInfo(final UserApi userApi, final String token, final String username) {
		Call<User> userInfoCall = userApi.getUserInfo(token, username);
		userInfoCall.enqueue(new Callback<User>() {
			@Override
			public void onResponse(final Call<User> call, final Response<User> response) {
				if (response.isSuccessful() && response.body() != null) {
					final User user = response.body();
					Gson gson = new Gson();
					final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putString("USER_INFO", gson.toJson(user));
					editor.apply();
				}
			}

			@Override
			public void onFailure(final Call<User> call, final Throwable t) {

			}
		});

	}
}


