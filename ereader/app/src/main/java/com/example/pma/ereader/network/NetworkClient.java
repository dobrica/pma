package com.example.pma.ereader.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
	public static final String BASE_URL = "http://sbpma-env.eba-nckb6gxc.us-east-2.elasticbeanstalk.com";
	public static Retrofit retrofit;

	public static Retrofit getRetrofitClient() {
		if (retrofit == null) {
			retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		}
		return retrofit;
	}
}