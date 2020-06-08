package com.example.pma.ereader.model.item;


import android.graphics.Bitmap;
import com.example.pma.ereader.utility.BitmapUtility;

/**
 * Item represents ereader text documents like books, comics, magazines*/
public class Item {
    private String id;
    private String title;
    private String author;
    private byte[] coverImage;
    private String filePath;

    public Item() {
        setFilePath("");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public byte[] getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }

    public Bitmap getCoverImageBitmap() {
        if (getCoverImage() != null) {
            return BitmapUtility.decodeBitmapFromByteArray(getCoverImage(), 1000, 2000);
        }
        return null;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
