package com.example.pma.ereader.ui.toread;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ToReadFragmentViewModelFactory implements ViewModelProvider.Factory {

	private Context mContext;

	ToReadFragmentViewModelFactory(Context context) {
		mContext = context;
	}

	@NonNull
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return (T) new ToReadFragmentViewModel(mContext);
	}
}
