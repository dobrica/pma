package com.example.pma.ereader.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

	@POST("/users/sign-up")
	Call<Void> postUserDetails(@Body User order);

	@POST("/login")
	Call<Void> login(@Body User order);

	@GET("/users/info")
	Call<User> getUserInfo(@Header("Authorization") final String token, @Query("username") final String username);
}
