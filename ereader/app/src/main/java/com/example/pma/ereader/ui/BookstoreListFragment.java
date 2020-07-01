package com.example.pma.ereader.ui;

import com.example.pma.ereader.R;
import com.example.pma.ereader.model.item.Item;
import com.example.pma.ereader.ui.bookstore.BookstoreCallback;
import com.example.pma.ereader.ui.bookstore.BookstoreRepository;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import lombok.SneakyThrows;

public class BookstoreListFragment extends Fragment {

	protected View buildFragment(int resId, LayoutInflater inflater, ViewGroup container) {
		View view = inflater.inflate(resId, container, false);

		View recyclerView = view.findViewById(R.id.bookstore_list);
		assert recyclerView != null;
		setupRecyclerView((RecyclerView) recyclerView);

		return view;
	}

	private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
		recyclerView.setAdapter(new BookstoreListFragment.SimpleItemRecyclerViewAdapter());
	}

	public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

		private List<Item> mValues = new ArrayList<>();

		SimpleItemRecyclerViewAdapter() {
			getItems();
		}

		@Override
		public BookstoreListFragment.SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookstore_list_content, parent, false);
			final ProgressBar downloadProgressBar = view.findViewById(R.id.loading_download);

			final ViewHolder viewHolder = new ViewHolder(view);
			final View downloadButton = view.findViewById(R.id.download);
			downloadButton.setOnClickListener(new OnClickListener() {
				@SneakyThrows
				@Override
				public void onClick(final View v) {
					if (View.GONE == downloadProgressBar.getVisibility()) {
						downloadProgressBar.setVisibility(View.VISIBLE);
					}
					final BookstoreRepository bookstoreRepository = BookstoreRepository.getInstance();
					Log.i("", "Downloading " + mValues.get(viewHolder.getAdapterPosition()).getTitle());
					bookstoreRepository.download(mValues.get(viewHolder.getAdapterPosition()).getTitle(), new BookstoreCallback() {
						@Override
						public void onSuccess(final List<Item> items) {
							//ignore
						}

						@Override
						public void onDownloadSuccess() {
							if (View.VISIBLE == downloadProgressBar.getVisibility()) {
								downloadProgressBar.setVisibility(View.GONE);
							}
						}
					});
				}
			});
			return viewHolder;
		}

		@Override
		public void onBindViewHolder(final BookstoreListFragment.SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
			holder.mContentView.setText(mValues.get(position).getTitle());
			holder.imageView.setImageBitmap(mValues.get(position).getCoverImageBitmap());
			holder.itemView.setTag(mValues.get(position));
		}

		@Override
		public int getItemCount() {
			return mValues.size();
		}

		private void getItems() {
			final BookstoreRepository bookstoreRepository = BookstoreRepository.getInstance();
			bookstoreRepository.getBookstoreItems(new BookstoreCallback() {
				@Override
				public void onSuccess(final List<Item> serverItems) {
					mValues = serverItems;
					SimpleItemRecyclerViewAdapter.super.notifyDataSetChanged();
				}

				@Override
				public void onDownloadSuccess() {
					//ignore
				}
			});
		}

		static class ViewHolder extends RecyclerView.ViewHolder {
			final TextView mContentView;
			final ImageView imageView;

			ViewHolder(View view) {
				super(view);
				mContentView = view.findViewById(R.id.content);
				imageView = view.findViewById(R.id.image);
			}
		}
	}
}
