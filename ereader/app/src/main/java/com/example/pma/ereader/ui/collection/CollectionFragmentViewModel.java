package com.example.pma.ereader.ui.collection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CollectionFragmentViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public CollectionFragmentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is collection fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
