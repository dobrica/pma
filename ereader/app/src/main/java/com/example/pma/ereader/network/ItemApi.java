package com.example.pma.ereader.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ItemApi {

	@GET("/items")
	Call<List<ServerItem>> findAll(@Header("Authorization") final String token);

}
