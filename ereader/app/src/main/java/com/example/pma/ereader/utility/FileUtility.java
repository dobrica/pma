package com.example.pma.ereader.utility;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUtility {

    public static List<File> getEpubFilesFromAssets(Context context) {
        List<File> files = new ArrayList<>();
        String[] paths = new String[0];
        try {
            paths = context.getAssets().list("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String fileName : Objects.requireNonNull(paths)) {
            if (fileName.endsWith(".epub")) {
                File f = getFileFromAssets(context, fileName);
                files.add(f);
            }
        }

        return files;
    }

    private static File getFileFromAssets(Context context, String fileName) {

        File file = new File(context.getCacheDir() + "/" + fileName);

        if (!file.exists()) try {

            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer);
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return file;
    }

}
