package com.example.pma.ereader.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import android.content.Context;
import android.util.Log;

public class FileUtility {

	private static final String FILENAME_PATTERN = "[^a-zA-Z0-9,_-]";
	private static final String EXTENSTION = ".epub";

	public static List<File> getLocalEpubFiles(Context context) {
		List<File> files = new ArrayList<>();
		String[] paths = getUserDirectory(context).list();

		for (String fileName : Objects.requireNonNull(paths)) {
			if (fileName.endsWith(EXTENSTION)) {
				File f = getDownloadedFile(context, fileName);
				if (f != null) {
					files.add(f);
				}
			}
		}

		return files;
	}

	public static boolean deleteLocalEpubFile(Context context, String fileName) {
		final File downloadedFile = getDownloadedFile(context, fileName.replaceAll(FILENAME_PATTERN, "").concat(EXTENSTION));
		if (downloadedFile != null && downloadedFile.exists()) {
			final boolean deleted = downloadedFile.delete();
			if (deleted) {
				Log.i("", String.format("File %s deleted", fileName));
			} else {
				Log.w("", String.format("File %s not deleted", fileName));
			}
			return deleted;
		}
		return false;
	}

	public static boolean fileAlreadyExistsContext(Context context, String fileName) {
		return !Arrays.asList(Objects.requireNonNull(context.getFilesDir()
			.list((dir, name) -> name.contains(fileName.replaceAll(FILENAME_PATTERN, "").concat(EXTENSTION))))).isEmpty();
	}

	private static File getDownloadedFile(Context context, String fileName) {
		final File[] files = getUserDirectory(context).listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.getPath().contains(fileName)) {
					return f;
				}
			}
		}
		return null;
	}

	private static File getUserDirectory(final Context context) {
		return new File(context.getFilesDir().getPath() + "/" + TokenUtils.getUsernameFromToken().hashCode());
	}

}
