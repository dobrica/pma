package com.example.pma.ereader.ui.account;

import com.example.pma.ereader.LoginActivity;
import com.example.pma.ereader.R;
import com.example.pma.ereader.network.NetworkClient;
import com.example.pma.ereader.network.User;
import com.example.pma.ereader.network.UserApi;
import com.example.pma.ereader.utility.TokenUtils;

import org.apache.commons.codec.binary.StringUtils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AccountFragment extends Fragment {

	private static final String NOT_AVAILABLE = "N/A";

	private AccountViewModel accountViewModel;

	public View onCreateView(@NonNull LayoutInflater inflater,
		ViewGroup container, Bundle savedInstanceState) {
		accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
		View root = inflater.inflate(R.layout.fragment_account, container, false);

		final TextView userNameTextView = root.findViewById(R.id.account_username);
		final TextView nameTextView = root.findViewById(R.id.account_name);
		final TextView idTextView = root.findViewById(R.id.account_id);

		final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("TOKEN", "");

		if(!token.isEmpty() && !token.contains("OFFLINE")) {
			Retrofit retrofit = NetworkClient.getRetrofitClient();
			UserApi userApi = retrofit.create(UserApi.class);
			Call call = userApi.getUserInfo(token, TokenUtils.getUsernameFromToken());

			call.enqueue(new Callback<User>() {
				@Override
				public void onResponse(final Call<User> call, final Response<User> response) {
					if (response.isSuccessful()) {
						final User user = response.body();
						Resources res = getResources();
						userNameTextView.setText(res.getString(R.string.account_username, user.getUsername()));
						nameTextView.setText(res.getString(R.string.account_name, user.getFullName()));
						idTextView.setText(res.getString(R.string.account_user_id, String.valueOf(user.getId())));
					}
				}

				@Override
				public void onFailure(final Call call, final Throwable t) {
					// do nothing
				}
			});
		} else {
			Resources res = getResources();
			userNameTextView.setText(res.getString(R.string.account_username, TokenUtils.getUsernameFromToken()));
			nameTextView.setText(res.getString(R.string.account_name, NOT_AVAILABLE));
			idTextView.setText(res.getString(R.string.account_user_id, NOT_AVAILABLE));
			Button button = root.findViewById(R.id.logout);
			button.setText(R.string.leave_offline);
		}

		logOut(root);
		return root;
	}

	private void logOut(final View root) {
		Button button = root.findViewById(R.id.logout);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.remove("TOKEN");
				editor.remove("USER_INFO");
				editor.apply();
				Toast.makeText(getContext(), "Successfully logged out", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
		});
	}
}
