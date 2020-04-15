package com.example.pma.ereader.ui.bookstore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.pma.ereader.R;
import com.example.pma.ereader.ui.ListFragment;

public class BookstoreFragment extends ListFragment {

    private BookstoreFragmentViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BookstoreFragmentViewModel.class);
        return buildFragment(R.layout.fragment_bookstore, inflater, container, viewModel);
    }

}
