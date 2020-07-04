package com.example.pma.ereader.ui;

import android.widget.Adapter;
import androidx.lifecycle.ViewModel;

import com.example.pma.ereader.model.item.Item;
import com.example.pma.ereader.ui.ListFragment.SimpleItemRecyclerViewAdapter;

import java.util.List;

public abstract class ListFragmentViewModel extends ViewModel {

    public abstract List<Item> getItems(SimpleItemRecyclerViewAdapter adapter);

}
