package com.example.pma.ereader.ui.login;

import com.example.pma.ereader.MyApplication;
import com.example.pma.ereader.R;
import com.example.pma.ereader.model.login.LoggedInUser;
import com.example.pma.ereader.model.login.LoginCallback;
import com.example.pma.ereader.model.login.LoginService;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Patterns;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

	private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
	private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
	private LoginService loginService;

	LoginViewModel(LoginService loginService) {
		this.loginService = loginService;
	}

	public LiveData<LoginFormState> getLoginFormState() {
		return loginFormState;
	}

	public LiveData<LoginResult> getLoginResult() {
		return loginResult;
	}

	public void login(String username, String password) {
		loginService.login(username, password, new LoginCallback() {
			@Override
			public void onSuccess(final LoggedInUser loggedInUser) {
				if (loggedInUser.getToken() != null) {
					final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putString("TOKEN", loggedInUser.getToken());
					editor.apply();
					loginResult.setValue(new LoginResult(new LoggedInUserView(loggedInUser.getDisplayName())));
				} else {
					loginResult.setValue(new LoginResult(R.string.login_failed));
				}
			}

			@Override
			public void onError(final Throwable throwable) {
				loginResult.setValue(new LoginResult(R.string.login_failed));
			}
		});
	}

	public void loginDataChanged(String username, String password) {
		if (!isUserNameValid(username)) {
			loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
		} else if (!isPasswordValid(password)) {
			loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
		} else {
			loginFormState.setValue(new LoginFormState(true));
		}
	}

	private boolean isUserNameValid(String username) {
		if (username == null) {
			return false;
		}
		if (username.contains("@")) {
			return Patterns.EMAIL_ADDRESS.matcher(username).matches();
		} else {
			return !username.trim().isEmpty();
		}
	}

	private boolean isPasswordValid(String password) {
		return password != null && password.trim().length() > 5;
	}
}
