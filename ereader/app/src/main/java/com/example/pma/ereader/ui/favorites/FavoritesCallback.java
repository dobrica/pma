package com.example.pma.ereader.ui.favorites;

import com.example.pma.ereader.model.item.Item;

import java.util.List;

public interface FavoritesCallback {

	void onGetFavoritesSuccess(List<Item> items);

	void onAddSuccess();

}
