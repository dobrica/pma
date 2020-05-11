package com.example.pma.ereader.ui.register;

import com.example.pma.ereader.model.login.LoginDataSource;
import com.example.pma.ereader.model.login.LoginRepository;
import com.example.pma.ereader.model.register.RegisterDataSource;
import com.example.pma.ereader.model.register.RegisterRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class RegisterViewModelFactory implements ViewModelProvider.Factory {

	@NonNull
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
			return (T) new RegisterViewModel(RegisterRepository.getInstance(new RegisterDataSource()));
		} else {
			throw new IllegalArgumentException("Unknown ViewModel class");
		}
	}
}
