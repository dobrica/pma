package com.example.pma.ereader.ui.register;

import com.example.pma.ereader.model.register.RegisterService;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RegisterViewModelFactory implements ViewModelProvider.Factory {

	@NonNull
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
			return (T) new RegisterViewModel(RegisterService.getInstance());
		} else {
			throw new IllegalArgumentException("Unknown ViewModel class");
		}
	}
}
