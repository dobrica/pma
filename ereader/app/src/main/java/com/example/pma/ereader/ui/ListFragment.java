package com.example.pma.ereader.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pma.ereader.ItemDetailActivity;
import com.example.pma.ereader.R;
import com.example.pma.ereader.model.item.Item;

import java.util.List;

public abstract class ListFragment extends Fragment {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ListFragmentViewModel viewModel;

    protected View buildFragment(int resId, LayoutInflater inflater, ViewGroup container, ListFragmentViewModel viewModel) {
        View view = inflater.inflate(resId, container, false);
        this.viewModel = viewModel;

        if (view.findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = view.findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        return view;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new ListFragment.SimpleItemRecyclerViewAdapter( this, viewModel.getItems(), mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final FragmentManager mFragmentManager;
        private final List<Item> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = (Item) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.getTitle());
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mFragmentManager.beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.getTitle());

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ListFragment fragment,
                                      List<Item> items,
                                      boolean twoPane) {
            mValues = items;
            mTwoPane = twoPane;
            mFragmentManager = fragment.getParentFragmentManager();
        }

        @Override
        public ListFragment.SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ListFragment.SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ListFragment.SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mContentView.setText(mValues.get(position).getTitle());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mContentView;
            final TextView mContentView2;

            ViewHolder(View view) {
                super(view);

                mContentView = (TextView) view.findViewById(R.id.content);
                mContentView2 = (TextView) view.findViewById(R.id.content2);

            }
        }
    }
}
