package com.example.pma.ereader.ui.collection;

import com.example.pma.ereader.model.item.Item;

import java.util.List;

public interface CollectionCallback {

	void onGetSuccess(List<Item> items);

	void onUpdateSuccess();

}
