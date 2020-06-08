package com.example.pma.ereader.ui.login;

import com.example.pma.ereader.model.login.LoggedInUser;
import com.example.pma.ereader.model.login.LoginRepository;
import com.example.pma.ereader.R;
import android.util.Patterns;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

	private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
	private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
	private LoginRepository loginRepository;

	LoginViewModel(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}

	public LiveData<LoginFormState> getLoginFormState() {
		return loginFormState;
	}

	public LiveData<LoginResult> getLoginResult() {
		return loginResult;
	}

	public void login(String username, String password) {
		// can be launched in a separate asynchronous job
		LoggedInUser result = loginRepository.login(username, password);

		if (result != null) {
			loginResult.setValue(new LoginResult(new LoggedInUserView(result.getDisplayName())));
		} else {
			loginResult.setValue(new LoginResult(R.string.login_failed));
		}
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

	// A placeholder username validation check
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

	// A placeholder password validation check
	private boolean isPasswordValid(String password) {
		return password != null && password.trim().length() > 5;
	}
}
