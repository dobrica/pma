package com.example.pma.ereader.ui.haveread;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HaveReadFragmentViewModelFactory implements ViewModelProvider.Factory {

	private Context mContext;

	HaveReadFragmentViewModelFactory(Context context) {
		mContext = context;
	}

	@NonNull
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return (T) new HaveReadFragmentViewModel(mContext);
	}
}