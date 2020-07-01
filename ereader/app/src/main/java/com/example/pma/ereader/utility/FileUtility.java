package com.example.pma.ereader.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.FileUtils;

import android.content.Context;

public class FileUtility {

	public static List<File> getLocalEpubFiles(Context context) {
		List<File> files = new ArrayList<>();
		String[] paths = context.getFilesDir().list();

		for (String fileName : Objects.requireNonNull(paths)) {
			if (fileName.endsWith(".epub")) {
				File f = getDownloadedFiles(context, fileName);
				files.add(f);
			}
		}

		return files;
	}

	private static File getDownloadedFiles(Context context, String fileName) {

		File file = new File(context.getCacheDir() + "/" + fileName);

		if (!file.exists()) {
			try {
				final File[] files = context.getFilesDir().listFiles();
				for (File f : files) {
					if (f.getName().equals(fileName)) {
						InputStream is = FileUtils.openInputStream(f);
						int size = is.available();
						byte[] buffer = new byte[size];
						is.read(buffer);
						is.close();

						FileOutputStream fos = new FileOutputStream(file);
						fos.write(buffer);
						fos.close();
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return file;
	}

}
