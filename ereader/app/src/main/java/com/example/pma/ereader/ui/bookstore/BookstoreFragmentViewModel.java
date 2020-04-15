package com.example.pma.ereader.ui.bookstore;

import com.example.pma.ereader.model.dummy.DummyContent;
import com.example.pma.ereader.ui.ListFragmentViewModel;

import java.util.List;

public class BookstoreFragmentViewModel extends ListFragmentViewModel {

    private List<DummyContent.DummyItem> items;

    public BookstoreFragmentViewModel() {
        super();
        items = DummyContent.ITEMS;
    }

    public List<DummyContent.DummyItem> getItems() {
        return items;
    }
}
