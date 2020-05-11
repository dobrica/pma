package com.example.pma.ereader.ui.register;

import com.example.pma.ereader.R;
import com.example.pma.ereader.model.login.Result;
import com.example.pma.ereader.model.register.RegisterRepository;
import com.example.pma.ereader.model.register.RegisteredUser;

import android.util.Patterns;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {

	private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
	private MutableLiveData<RegisterResult> loginResult = new MutableLiveData<>();
	private RegisterRepository registerRepository;

	RegisterViewModel(RegisterRepository registerRepository) {
		this.registerRepository = registerRepository;
	}

	public LiveData<RegisterFormState> getRegisterFormState() {
		return registerFormState;
	}

	public LiveData<RegisterResult> getRegisterResult() {
		return loginResult;
	}

	public void register(String name, String username, String password, String repeatedPassword) {
		Result<RegisteredUser> result = registerRepository.register(name, username, password, repeatedPassword);

		if (result instanceof Result.Success) {
			RegisteredUser data = ((Result.Success<RegisteredUser>) result).getData();
			loginResult.setValue(new RegisterResult(new RegisteredUserView(data.getFullName())));
		} else {
			loginResult.setValue(new RegisterResult(R.string.registration_failed));
		}
	}

	public void registerDataChanged(String name, String username, String password, String repeatedPassword) {
		if (!isNameValid(name)) {
			registerFormState.setValue(new RegisterFormState(R.string.invalid_name, null, null, null));
		} else if (!isUserNameValid(username)) {
			registerFormState.setValue(new RegisterFormState(null, R.string.invalid_username, null, null));
		} else if (!isPasswordValid(password)) {
			registerFormState.setValue(new RegisterFormState(null, null,  R.string.invalid_password, null));
		} else if (!doPasswordsMatch(password, repeatedPassword)) {
			registerFormState.setValue(new RegisterFormState(null, null, null, R.string.invalid_password_match));
		} else {
			registerFormState.setValue(new RegisterFormState(true));
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

	private boolean isNameValid(String name) {
		if (name == null) {
			return false;
		}
		return !name.trim().isEmpty();
	}

	private boolean isPasswordValid(String password) {
		return password != null && password.trim().length() > 5;
	}

	private boolean doPasswordsMatch(String password, String repeatedPassword) {
		return password.equals(repeatedPassword);
	}
}
