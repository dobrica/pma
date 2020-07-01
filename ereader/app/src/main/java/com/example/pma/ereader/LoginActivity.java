package com.example.pma.ereader;

import com.example.pma.ereader.model.register.RegisteredUser;
import com.example.pma.ereader.ui.login.LoggedInUserView;
import com.example.pma.ereader.ui.login.LoginFormState;
import com.example.pma.ereader.ui.login.LoginResult;
import com.example.pma.ereader.ui.login.LoginViewModel;
import com.example.pma.ereader.ui.login.LoginViewModelFactory;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends AppCompatActivity {

	public static List<RegisteredUser> PREDEFINED_USERS = Arrays.asList(
		RegisteredUser.builder().fullName("User1").userName("user@one.com").build(),
		RegisteredUser.builder().fullName("User2").userName("user@two.com").build(),
		RegisteredUser.builder().fullName("User3").userName("user@three.com").build());

	private LoginViewModel loginViewModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getSupportActionBar().hide();
		prepareDummyUsers();
		setContentView(R.layout.activity_login);
		loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);

		final EditText usernameEditText = findViewById(R.id.username);
		final EditText passwordEditText = findViewById(R.id.reg_password);
		final Button loginButton = findViewById(R.id.login);
		final Button registerButton = findViewById(R.id.register);
		final Button goOfflineButton = findViewById(R.id.offline);
		goOfflineButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putString("TOKEN", "OFFLINE");
				editor.apply();
				setResult(Activity.RESULT_OK);
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				//Complete and destroy login activity once successful
				finish();
			}
		});
		registerButton.setEnabled(true);
		final ProgressBar loadingProgressBar = findViewById(R.id.loading);

		loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
			@Override
			public void onChanged(@Nullable LoginFormState loginFormState) {
				if (loginFormState == null) {
					return;
				}
				loginButton.setEnabled(loginFormState.isDataValid());
				if (loginFormState.getUsernameError() != null) {
					usernameEditText.setError(getString(loginFormState.getUsernameError()));
				}
				if (loginFormState.getPasswordError() != null) {
					passwordEditText.setError(getString(loginFormState.getPasswordError()));
				}
			}
		});

		loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
			@Override
			public void onChanged(@Nullable LoginResult loginResult) {
				if (loginResult == null) {
					return;
				}
				loadingProgressBar.setVisibility(View.GONE);
				if (loginResult.getError() != null) {
					showLoginFailed(loginResult.getError());
				}
				if (loginResult.getSuccess() != null) {
					updateUiWithUser(loginResult.getSuccess());
				}
				setResult(Activity.RESULT_OK);
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				//Complete and destroy login activity once successful
				finish();
			}
		});

		TextWatcher afterTextChangedListener = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// ignore
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// ignore
			}

			@Override
			public void afterTextChanged(Editable s) {
				loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
					passwordEditText.getText().toString());
			}
		};
		usernameEditText.addTextChangedListener(afterTextChangedListener);
		passwordEditText.addTextChangedListener(afterTextChangedListener);
		passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					loginViewModel.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
				}
				return false;
			}
		});

		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadingProgressBar.setVisibility(View.VISIBLE);
				loginViewModel.login(usernameEditText.getText().toString(),
					passwordEditText.getText().toString());
			}
		});
		registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});

	}

	private void updateUiWithUser(LoggedInUserView model) {
		Toast.makeText(getApplicationContext(), getString(R.string.welcome) + model.getDisplayName(), Toast.LENGTH_LONG).show();
	}

	private void showLoginFailed(@StringRes Integer errorString) {
		Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onBackPressed() {
		finishAffinity();
		finish();
	}

	public void prepareDummyUsers() {
		final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
		if (sharedPref.getStringSet("Users", Collections.<String>emptySet()).isEmpty()) {
			final Set<String> users = new HashSet<>();
			Gson gson = new Gson();
			for (RegisteredUser registeredUser : PREDEFINED_USERS) {
				users.add(gson.toJson(registeredUser));
			}
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putStringSet("Users", users);
			editor.apply();
		}
	}
}
