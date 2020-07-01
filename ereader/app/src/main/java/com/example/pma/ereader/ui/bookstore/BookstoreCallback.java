package com.example.pma.ereader.ui.bookstore;

import com.example.pma.ereader.model.item.Item;
import com.example.pma.ereader.model.register.RegisteredUser;

import java.util.List;

public interface BookstoreCallback {

	void onSuccess(List<Item> items);

	void onDownloadSuccess();


}
