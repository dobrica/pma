package com.example.pma.ereader.ui.bookstore;

import com.example.pma.ereader.MyApplication;
import com.example.pma.ereader.model.item.Item;
import com.example.pma.ereader.network.ItemApi;
import com.example.pma.ereader.network.NetworkClient;
import com.example.pma.ereader.network.ServerItem;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookstoreRepository {

	private static volatile BookstoreRepository instance;

	public static BookstoreRepository getInstance() {
		if (instance == null) {
			instance = new BookstoreRepository();
		}
		return instance;
	}

	public void getBookstoreItems(final BookstoreCallback bookstoreCallback) {
		final String token = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext()).getString("TOKEN", "");
		final List<Item> items = new ArrayList<>();
		Retrofit retrofit = NetworkClient.getRetrofitClient();
		ItemApi itemApi = retrofit.create(ItemApi.class);
		Call<List<ServerItem>> call = itemApi.findAll(token);

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
					bookstoreCallback.onSuccess(items);
				}
			}

			@Override
			public void onFailure(final Call<List<ServerItem>> call, final Throwable t) {

			}
		});
	}

	public void download(final String title, final BookstoreCallback bookstoreCallback) {
		final String token = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext()).getString("TOKEN", "");
		Retrofit retrofit = NetworkClient.getRetrofitClient();
		ItemApi itemApi = retrofit.create(ItemApi.class);
		final Call<ResponseBody> download = itemApi.download(token, title);

		download.enqueue(new Callback<ResponseBody>() {
			@Override
			@SuppressLint("StaticFieldLeak")
			public void onResponse(final Call<ResponseBody> call, final Response<ResponseBody> response) {
				if (response.isSuccessful() && response.body() != null) {
					new AsyncTask<Void, Void, Void>() {
						@Override
						protected Void doInBackground(final Void... voids) {
							try {
								final File filesDir = MyApplication.getAppContext().getFilesDir();
								File file = new File(filesDir, title.concat(".epub"));
								FileOutputStream fileOutputStream = new FileOutputStream(file);
								final byte[] bytes = response.body().bytes();
								IOUtils.write(bytes, fileOutputStream);
								Thread.sleep(2000);
								bookstoreCallback.onDownloadSuccess();
							} catch (Exception ex) {
								Log.e("", "Error downloading file", ex);
							}
							return null;
						}
					}.execute();
				}
			}

			@Override
			public void onFailure(final Call<ResponseBody> call, final Throwable t) {

			}
		});

	}
}
