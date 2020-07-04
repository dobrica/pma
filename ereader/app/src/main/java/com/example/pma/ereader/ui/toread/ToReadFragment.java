package com.example.pma.ereader.ui.toread;

import com.example.pma.ereader.R;
import com.example.pma.ereader.ui.ListFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

public class ToReadFragment extends ListFragment {

	private ToReadFragmentViewModel viewModel;

	public View onCreateView(@NonNull LayoutInflater inflater,
		ViewGroup container, Bundle savedInstanceState) {
		viewModel = new ViewModelProvider(this,
			new ToReadFragmentViewModelFactory(getContext())).get(ToReadFragmentViewModel.class);
		return buildFragment(R.layout.fragment_to_read, inflater, container, viewModel);
	}

}
