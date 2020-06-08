package com.example.pma.ereader.model.item;

import java.util.ArrayList;
import java.util.List;

public class ItemsRepository {
    private static volatile ItemsRepository instance;
    private List<Item> items;

    private ItemsRepository() {
        items = new ArrayList<>();
    }

    public static ItemsRepository getInstance() {
        if (instance == null) {
            instance = new ItemsRepository();
        }
        return instance;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Item getItemByTitle(String title) {
        for (Item item : items) {
            if (item.getTitle().equals(title)) {
                return item;
            }
        }
        return null;
    }
}
