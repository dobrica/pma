package com.example.pma.ereader.ui.login;

import com.example.pma.ereader.model.login.LoginService;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

	@NonNull
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		if (modelClass.isAssignableFrom(LoginViewModel.class)) {
			return (T) new LoginViewModel(LoginService.getInstance());
		} else {
			throw new IllegalArgumentException("Unknown ViewModel class");
		}
	}
}
