package com.example.pma.ereader.ui.content;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pma.ereader.R;
import com.example.pma.ereader.model.TableOfContents;

public class ContentFragment extends Fragment {

    private ContentViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContentViewModel.class);
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        TextView tv = view.findViewById(R.id.book_content);
        tv.setTextSize(20);
        String toc = "";
        for (String s: TableOfContents.items){
            toc += "\n" + s;
        }
        tv.setText(toc);
        view.setVerticalScrollBarEnabled(true);
        return view;
    }

}
