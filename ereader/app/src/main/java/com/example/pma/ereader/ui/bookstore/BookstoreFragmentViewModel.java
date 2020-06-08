package com.example.pma.ereader.ui.bookstore;

import com.example.pma.ereader.model.item.Item;
import com.example.pma.ereader.ui.ListFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookstoreFragmentViewModel extends ListFragmentViewModel {

    private List<Item> items;

    public BookstoreFragmentViewModel() {
        super();
        items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }
}
