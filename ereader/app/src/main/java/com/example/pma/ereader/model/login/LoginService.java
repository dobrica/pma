package com.example.pma.ereader.model.login;

import com.example.pma.ereader.network.NetworkClient;
import com.example.pma.ereader.network.User;
import com.example.pma.ereader.network.UserApi;

import java.util.List;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginService {

	private static volatile LoginService instance;

	public static LoginService getInstance() {
		if (instance == null) {
			instance = new LoginService();
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
						loginCallback.onSuccess(new LoggedInUser(username, username, authHeaders.get(0)));
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
}
