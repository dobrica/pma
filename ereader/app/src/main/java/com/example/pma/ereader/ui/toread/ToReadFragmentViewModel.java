package com.example.pma.ereader.ui.toread;

import com.example.pma.ereader.model.item.Item;
import com.example.pma.ereader.ui.ListFragment.SimpleItemRecyclerViewAdapter;
import com.example.pma.ereader.ui.ListFragmentViewModel;
import com.example.pma.ereader.ui.collection.CollectionCallback;
import com.example.pma.ereader.ui.collection.CollectionType;
import com.example.pma.ereader.ui.collection.CollectionsRepository;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class ToReadFragmentViewModel extends ListFragmentViewModel {
	private List<Item> items;
	private Context context;

	ToReadFragmentViewModel(Context mContext) {
		super();
		context = mContext;
		items = new ArrayList<>();
	}

	public List<Item> getItems(final SimpleItemRecyclerViewAdapter adapter) {
		items.clear();
		final CollectionsRepository collectionsRepository = CollectionsRepository.getInstance();
		collectionsRepository.findAll(CollectionType.TO_READ, new CollectionCallback() {
			@Override
			public void onGetSuccess(final List<Item> toReadItems) {
				items.addAll(toReadItems);
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
