package com.example.pma.ereader.ui.collection;

import android.content.Context;

import com.example.pma.ereader.model.item.Item;
import com.example.pma.ereader.model.item.ItemsRepository;
import com.example.pma.ereader.ui.ListFragmentViewModel;
import com.example.pma.ereader.utility.FileUtility;
import com.github.mertakdut.Reader;
import com.github.mertakdut.exception.ReadingException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CollectionFragmentViewModel extends ListFragmentViewModel {
    private List<Item> items;
    private Context context;

    CollectionFragmentViewModel(Context mContext) {
        super();
        context = mContext;
        items = new ArrayList<>();

        initItemsList();
    }

    public List<Item> getItems() {
        return items;
    }

    private void initItemsList(){
        Reader reader = new Reader();
        List<File> files = FileUtility.getEpubFilesFromAssets(context);
        for (File file: files) {
            Item item = new Item();
            try {
                reader.setInfoContent(file.getPath());
//                private String id; generate id
                String title = reader.getInfoPackage().getMetadata().getTitle();
                if (title != null && !title.equals("")) {
                    item.setTitle(reader.getInfoPackage().getMetadata().getTitle());
                } else { // If title doesn't exist, use fileName instead.
                    item.setTitle(file.getName());
                }
                item.setAuthor(reader.getInfoPackage().getMetadata().getCreator());
                item.setCoverImage(reader.getCoverImage());
                item.setFilePath(file.getPath());
            } catch (ReadingException e) {
                e.printStackTrace();
            }
            if (!items.contains(item) && !item.getFilePath().equals("")){
                items.add(item);
            }
        }

        ItemsRepository.getInstance().setItems(items);
    }
}
