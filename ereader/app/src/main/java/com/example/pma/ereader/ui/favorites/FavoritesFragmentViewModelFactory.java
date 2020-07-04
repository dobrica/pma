package com.example.pma.ereader.ui.favorites;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FavoritesFragmentViewModelFactory implements ViewModelProvider.Factory {

	private Context mContext;

	FavoritesFragmentViewModelFactory(Context context) {
		mContext = context;
	}

	@NonNull
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return (T) new FavoritesFragmentViewModel(mContext);
	}
}
