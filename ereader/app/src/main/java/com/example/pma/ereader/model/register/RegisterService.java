package com.example.pma.ereader.model.register;

import com.example.pma.ereader.network.NetworkClient;
import com.example.pma.ereader.network.User;
import com.example.pma.ereader.network.UserApi;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterService {

	private static volatile RegisterService instance;

	public static RegisterService getInstance() {
		if (instance == null) {
			instance = new RegisterService();
		}
		return instance;
	}

	public void register(String name, String username, String password, final RegisterCallback registerCallback) {
		Retrofit retrofit = NetworkClient.getRetrofitClient();
		UserApi userApi = retrofit.create(UserApi.class);
		Call call = userApi.postUserDetails(User.builder().fullName(name).username(username).password(password).build());

		call.enqueue(new Callback() {
			@Override
			public void onResponse(Call call, Response response) {
				if (response.isSuccessful()) {
					Log.i(this.getClass().getSimpleName(), "Successfully registered new user");
					registerCallback.onSuccess(new RegisteredUser(name, username));
				} else {
					Log.w(this.getClass().getSimpleName(), String.format("Error while registering new user with code %s", response.code()));
					registerCallback.onSuccess(null);
				}
			}

			@Override
			public void onFailure(Call call, Throwable t) {
				throw new RuntimeException("Error registering new user", t);
			}
		});
	}
}
