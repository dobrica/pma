package com.example.pma.ereader.ui.collection;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CollectionFragmentViewModelFactory implements ViewModelProvider.Factory {

    private Context mContext;

    CollectionFragmentViewModelFactory(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CollectionFragmentViewModel(mContext);
    }
}
