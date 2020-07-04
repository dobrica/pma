package com.example.pma.ereader.ui.favorites;

import com.example.pma.ereader.R;
import com.example.pma.ereader.ui.ListFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

public class FavoritesFragment extends ListFragment {

	private FavoritesFragmentViewModel viewModel;

	public View onCreateView(@NonNull LayoutInflater inflater,
		ViewGroup container, Bundle savedInstanceState) {
		viewModel = new ViewModelProvider(this,
			new FavoritesFragmentViewModelFactory(getContext())).get(FavoritesFragmentViewModel.class);
		return buildFragment(R.layout.fragment_favorites, inflater, container, viewModel);
	}

}
