package com.example.pma.ereader.ui.favorites;

import com.example.pma.ereader.model.item.Item;
import com.example.pma.ereader.ui.ListFragment.SimpleItemRecyclerViewAdapter;
import com.example.pma.ereader.ui.ListFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class FavoritesFragmentViewModel extends ListFragmentViewModel {
	private List<Item> items;
	private Context context;

	FavoritesFragmentViewModel(Context mContext) {
		super();
		context = mContext;
		items = new ArrayList<>();
	}

	public List<Item> getItems(final SimpleItemRecyclerViewAdapter adapter) {
		items.clear();
		final FavoritesRepository favoritesRepository = FavoritesRepository.getInstance();
		favoritesRepository.getFavorites(new FavoritesCallback() {
			@Override
			public void onGetFavoritesSuccess(final List<Item> favorites) {
				items.addAll(favorites);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onUpdateSuccess() {
				//ignore
			}
		});
		return items;
	}
}
