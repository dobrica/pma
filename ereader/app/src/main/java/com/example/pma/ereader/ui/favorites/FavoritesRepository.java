package com.example.pma.ereader.ui.favorites;

import com.example.pma.ereader.MyApplication;
import com.example.pma.ereader.model.item.Item;
import com.example.pma.ereader.network.ItemApi;
import com.example.pma.ereader.network.NetworkClient;
import com.example.pma.ereader.network.ServerItem;
import com.example.pma.ereader.network.User;
import com.example.pma.ereader.network.UserItem;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FavoritesRepository {
	private static volatile FavoritesRepository instance;

	public static FavoritesRepository getInstance() {
		if (instance == null) {
			instance = new FavoritesRepository();
		}
		return instance;
	}

	void getFavorites(final FavoritesCallback favoritesCallback) {
		final String token = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext()).getString("TOKEN", "");
		final List<Item> items = new ArrayList<>();
		Retrofit retrofit = NetworkClient.getRetrofitClient();
		ItemApi itemApi = retrofit.create(ItemApi.class);
		Call<List<ServerItem>> call = itemApi.findFavorites(token, 1L);

		call.enqueue(new Callback<List<ServerItem>>() {
			@Override
			public void onResponse(final Call<List<ServerItem>> call, final Response<List<ServerItem>> response) {
				if (response.isSuccessful() && response.body() != null) {
					final List<ServerItem> serverItems = response.body();
					for (ServerItem si : serverItems) {
						Item i = new Item();
						i.setId(String.valueOf(si.getId()));
						i.setTitle(si.getTitle());
						i.setCoverImage(Base64.decode(si.getBase64EncodedImage(), android.util.Base64.DEFAULT));
						items.add(i);
					}
					favoritesCallback.onGetFavoritesSuccess(items);
				}
			}

			@Override
			public void onFailure(final Call<List<ServerItem>> call, final Throwable t) {

			}
		});
	}

	public void addToFavorites(final String title, final FavoritesCallback favoritesCallback) {
		final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
		final String token = defaultSharedPreferences.getString("TOKEN", "");
		User userInfo;
		try {
			Gson gson = new Gson();
			userInfo = gson.fromJson(defaultSharedPreferences.getString("USER_INFO", ""), User.class);
		} catch (JsonSyntaxException e) {
			return;
		}
		Retrofit retrofit = NetworkClient.getRetrofitClient();
		ItemApi itemApi = retrofit.create(ItemApi.class);
		Call<Void> call = itemApi.addToFavorites(token, UserItem.builder()
			.userId(userInfo.getId())
			.itemTitle(title.replaceAll("[^a-zA-Z0-9,_-]", ""))
			.build());

		call.enqueue(new Callback<Void>() {
			@Override
			public void onResponse(final Call<Void> call, final Response<Void> response) {
				if (response.isSuccessful()) {
					favoritesCallback.onUpdateSuccess();
				}
			}

			@Override
			public void onFailure(final Call<Void> call, final Throwable t) {

			}
		});
	}


	public void removeFavorite(final String title, final FavoritesCallback favoritesCallback) {
		final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
		final String token = defaultSharedPreferences.getString("TOKEN", "");
		User userInfo;
		try {
			Gson gson = new Gson();
			userInfo = gson.fromJson(defaultSharedPreferences.getString("USER_INFO", ""), User.class);
		} catch (JsonSyntaxException e) {
			return;
		}
		if(userInfo == null) {
			return;
		}
		Retrofit retrofit = NetworkClient.getRetrofitClient();
		ItemApi itemApi = retrofit.create(ItemApi.class);
		Call<Void> call = itemApi.removeFavorite(token, UserItem.builder()
			.userId(userInfo.getId())
			.itemTitle(title.replaceAll("[^a-zA-Z0-9,_-]", ""))
			.build());

		call.enqueue(new Callback<Void>() {
			@Override
			public void onResponse(final Call<Void> call, final Response<Void> response) {
				if (response.isSuccessful()) {
					favoritesCallback.onUpdateSuccess();
				}
			}

			@Override
			public void onFailure(final Call<Void> call, final Throwable t) {

			}
		});
	}

}
