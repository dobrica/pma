package com.example.pma.ereader.ui.collection;

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

public class CollectionsRepository {
	private static volatile CollectionsRepository instance;

	public static CollectionsRepository getInstance() {
		if (instance == null) {
			instance = new CollectionsRepository();
		}
		return instance;
	}

	public void findAll(final CollectionType collectionType, final CollectionCallback collectionCallback) {
		final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
		final String token = defaultSharedPreferences.getString("TOKEN", "");
		final List<Item> items = new ArrayList<>();
		User userInfo;
		try {
			Gson gson = new Gson();
			userInfo = gson.fromJson(defaultSharedPreferences.getString("USER_INFO", ""), User.class);
		} catch (JsonSyntaxException e) {
			return;
		}
		if (userInfo == null) {
			return;
		}
		Call<List<ServerItem>> call = determineFindEndpoint(collectionType, token, userInfo.getId());
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
					collectionCallback.onGetSuccess(items);
				}
			}

			@Override
			public void onFailure(final Call<List<ServerItem>> call, final Throwable t) {

			}
		});
	}

	public void add(final CollectionType collectionType, final String title, final CollectionCallback collectionCallback) {
		final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
		final String token = defaultSharedPreferences.getString("TOKEN", "");
		User userInfo;
		try {
			Gson gson = new Gson();
			userInfo = gson.fromJson(defaultSharedPreferences.getString("USER_INFO", ""), User.class);
		} catch (JsonSyntaxException e) {
			return;
		}
		if (userInfo == null) {
			return;
		}
		final UserItem userItem = UserItem.builder().userId(userInfo.getId()).itemTitle(title.replaceAll("[^a-zA-Z0-9,_-]", "")).build();
		Call<Void> call = determineAddEndpoint(collectionType, token, userItem);

		call.enqueue(new Callback<Void>() {
			@Override
			public void onResponse(final Call<Void> call, final Response<Void> response) {
				if (response.isSuccessful()) {
					collectionCallback.onUpdateSuccess();
				}
			}

			@Override
			public void onFailure(final Call<Void> call, final Throwable t) {

			}
		});
	}

	public void remove(final CollectionType collectionType, final String title, final CollectionCallback collectionCallback) {
		final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
		final String token = defaultSharedPreferences.getString("TOKEN", "");
		User userInfo;
		try {
			Gson gson = new Gson();
			userInfo = gson.fromJson(defaultSharedPreferences.getString("USER_INFO", ""), User.class);
		} catch (JsonSyntaxException e) {
			return;
		}
		if (userInfo == null) {
			return;
		}
		final UserItem userItem = UserItem.builder().userId(userInfo.getId()).itemTitle(title.replaceAll("[^a-zA-Z0-9,_-]", "")).build();
		Call<Void> call = determineRemoveEndpoint(collectionType, token, userItem);

		call.enqueue(new Callback<Void>() {
			@Override
			public void onResponse(final Call<Void> call, final Response<Void> response) {
				if (response.isSuccessful()) {
					collectionCallback.onUpdateSuccess();
				}
			}

			@Override
			public void onFailure(final Call<Void> call, final Throwable t) {

			}
		});
	}

	private Call<List<ServerItem>> determineFindEndpoint(final CollectionType collectionType, final String token, final Long userId) {
		Retrofit retrofit = NetworkClient.getRetrofitClient();
		ItemApi itemApi = retrofit.create(ItemApi.class);
		switch (collectionType) {
		case FAVORITE:
			return itemApi.findFavorites(token, userId);
		case TO_READ:
			return itemApi.findToRead(token, userId);
		case HAVE_READ:
			return itemApi.findHaveRead(token, userId);
		default:
			throw new IllegalArgumentException(String.format("Collection type %s not supported", collectionType.name()));
		}
	}

	private Call<Void> determineAddEndpoint(final CollectionType collectionType, final String token, final UserItem userItem) {
		Retrofit retrofit = NetworkClient.getRetrofitClient();
		ItemApi itemApi = retrofit.create(ItemApi.class);
		switch (collectionType) {
		case FAVORITE:
			return itemApi.addToFavorites(token, userItem);
		case TO_READ:
			return itemApi.addToRead(token, userItem);
		case HAVE_READ:
			return itemApi.addHaveRead(token, userItem);
		default:
			throw new IllegalArgumentException(String.format("Collection type %s not supported", collectionType.name()));
		}
	}

	private Call<Void> determineRemoveEndpoint(final CollectionType collectionType, final String token, final UserItem userItem) {
		Retrofit retrofit = NetworkClient.getRetrofitClient();
		ItemApi itemApi = retrofit.create(ItemApi.class);
		switch (collectionType) {
		case FAVORITE:
			return itemApi.removeFavorite(token, userItem);
		case TO_READ:
			return itemApi.removeToRead(token, userItem);
		case HAVE_READ:
			return itemApi.removeHaveRead(token, userItem);
		default:
			throw new IllegalArgumentException(String.format("Collection type %s not supported", collectionType.name()));
		}
	}

}
