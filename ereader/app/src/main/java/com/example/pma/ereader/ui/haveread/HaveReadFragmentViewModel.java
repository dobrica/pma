package com.example.pma.ereader.ui.haveread;

import com.example.pma.ereader.model.item.Item;
import com.example.pma.ereader.ui.ListFragment.SimpleItemRecyclerViewAdapter;
import com.example.pma.ereader.ui.ListFragmentViewModel;
import com.example.pma.ereader.ui.collection.CollectionCallback;
import com.example.pma.ereader.ui.collection.CollectionType;
import com.example.pma.ereader.ui.collection.CollectionsRepository;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class HaveReadFragmentViewModel extends ListFragmentViewModel {
	private List<Item> items;
	private Context context;

	HaveReadFragmentViewModel(Context mContext) {
		super();
		context = mContext;
		items = new ArrayList<>();
	}

	public List<Item> getItems(final SimpleItemRecyclerViewAdapter adapter) {
		items.clear();
		final CollectionsRepository collectionsRepository = CollectionsRepository.getInstance();
		collectionsRepository.findAll(CollectionType.HAVE_READ, new CollectionCallback() {
			@Override
			public void onGetSuccess(final List<Item> haveReadItems) {
				items.addAll(haveReadItems);
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
