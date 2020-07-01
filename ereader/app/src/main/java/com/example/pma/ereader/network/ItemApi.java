package com.example.pma.ereader.network;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface ItemApi {

	@GET("/items")
	Call<List<ServerItem>> findAll(@Header("Authorization") final String token);

	@GET("/items/download")
	@Streaming
	Call<ResponseBody> download(@Header("Authorization") final String token, @Query("title") final String title);

}
