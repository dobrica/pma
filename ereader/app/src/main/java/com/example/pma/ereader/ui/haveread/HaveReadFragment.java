package com.example.pma.ereader.ui.haveread;

import com.example.pma.ereader.R;
import com.example.pma.ereader.ui.ListFragment;
import com.example.pma.ereader.ui.favorites.FavoritesFragmentViewModel;
import com.example.pma.ereader.ui.favorites.FavoritesFragmentViewModelFactory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

public class HaveReadFragment extends ListFragment {

	private HaveReadFragmentViewModel viewModel;

	public View onCreateView(@NonNull LayoutInflater inflater,
		ViewGroup container, Bundle savedInstanceState) {
		viewModel = new ViewModelProvider(this,
			new HaveReadFragmentViewModelFactory(getContext())).get(HaveReadFragmentViewModel.class);
		return buildFragment(R.layout.fragment_have_read, inflater, container, viewModel);
	}

}