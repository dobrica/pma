package com.example.pma.ereader.ui;

import androidx.lifecycle.ViewModel;

import com.example.pma.ereader.model.dummy.DummyContent;

import java.util.List;

public abstract class ListFragmentViewModel extends ViewModel {

    public abstract List<DummyContent.DummyItem> getItems();

}
