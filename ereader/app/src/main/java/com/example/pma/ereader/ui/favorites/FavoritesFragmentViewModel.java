package com.example.pma.ereader.ui.favorites;

import com.example.pma.ereader.model.item.Item;
import com.example.pma.ereader.ui.ListFragment.SimpleItemRecyclerViewAdapter;
import com.example.pma.ereader.ui.ListFragmentViewModel;
import com.example.pma.ereader.ui.collection.CollectionCallback;
import com.example.pma.ereader.ui.collection.CollectionType;
import com.example.pma.ereader.ui.collection.CollectionsRepository;

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
		final CollectionsRepository collectionsRepository = CollectionsRepository.getInstance();
		collectionsRepository.findAll(CollectionType.FAVORITE, new CollectionCallback() {
			@Override
			public void onGetSuccess(final List<Item> favorites) {
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
