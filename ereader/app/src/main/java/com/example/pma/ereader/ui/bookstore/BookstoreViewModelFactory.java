package com.example.pma.ereader.ui.bookstore;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BookstoreViewModelFactory implements ViewModelProvider.Factory {

	@NonNull
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		if (modelClass.isAssignableFrom(BookstoreFragmentViewModel.class)) {
			return (T) new BookstoreFragmentViewModel();
		} else {
			throw new IllegalArgumentException("Unknown ViewModel class");
		}
	}
}
