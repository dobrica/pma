package com.example.pma.ereader.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    private MutableLiveData<String> mText2;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText2 = new MutableLiveData<>();
        mText.setValue("Set Font:");
        mText2.setValue("Set Brightness:");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getText2() {
        return mText2;
    }
}
