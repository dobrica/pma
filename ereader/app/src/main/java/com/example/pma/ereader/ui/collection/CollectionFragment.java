package com.example.pma.ereader.ui.collection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.pma.ereader.R;
import com.example.pma.ereader.ui.ListFragment;

public class CollectionFragment extends ListFragment {

    private CollectionFragmentViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this,
                new CollectionFragmentViewModelFactory(getContext())).get(CollectionFragmentViewModel.class);
        return buildFragment(R.layout.fragment_collection, inflater, container, viewModel);
    }

}
