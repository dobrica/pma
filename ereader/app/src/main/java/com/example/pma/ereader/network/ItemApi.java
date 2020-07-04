package com.example.pma.ereader.network;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface ItemApi {

	@GET("/items")
	Call<List<ServerItem>> findAll(@Header("Authorization") final String token);

	@GET("/items/download")
	@Streaming
	Call<ResponseBody> download(@Header("Authorization") final String token, @Query("title") final String title);

	@GET("/users/favorites/{userId}")
	Call<List<ServerItem>> findFavorites(@Header("Authorization") final String token, @Path("userId") final Long userId);

	@POST("/users/favorites/add")
	Call<Void> addToFavorites(@Header("Authorization") final String token, @Body final UserItem userItem);

	@POST("/users/favorites/remove")
	Call<Void> removeFavorite(@Header("Authorization") final String token, @Body final UserItem userItem);

	@GET("/users/to-read/{userId}")
	Call<List<ServerItem>> findToRead(@Header("Authorization") final String token, @Path("userId") final Long userId);

	@POST("/users/to-read/add")
	Call<Void> addToRead(@Header("Authorization") final String token, @Body final UserItem userItem);

	@POST("/users/to-read/remove")
	Call<Void> removeToRead(@Header("Authorization") final String token, @Body final UserItem userItem);

	@GET("/users/have-read/{userId}")
	Call<List<ServerItem>> findHaveRead(@Header("Authorization") final String token, @Path("userId") final Long userId);

	@POST("/users/have-read/add")
	Call<Void> addHaveRead(@Header("Authorization") final String token, @Body final UserItem userItem);

	@POST("/users/have-read/remove")
	Call<Void> removeHaveRead(@Header("Authorization") final String token, @Body final UserItem userItem);

}
