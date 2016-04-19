package com.fourqt.imageloader;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

public class FileCache {

	/*private File cacheDir;

	public FileCache(Context context) {

		// Find the dir at SDCARD to save cached images

		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			// if SDCARD is mounted (SDCARD is present on device and mounted)
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"ZaptheQ");
		} else {
			// if checking on simulator the create cache dir in your application
			// context
			cacheDir = context.getCacheDir();
		}

		if (!cacheDir.exists()) {
			// create cache dir in your application context
			cacheDir.mkdirs();
		}
	}

	public File getFile(String url) {
		// Identify images by hashcode or encode by URLEncoder.encode.
		String filename = String.valueOf(url.hashCode());

		File f = new File(cacheDir, filename);
		return f;

	}

	public void clear() {
		// list all files inside cache directory
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		// delete all cache directory files
		for (File f : files)
			f.delete();
	}

}*/
	private File cacheDir;
	private File nomediaFile;
	String NOMEDIA = " .nomedia";
	public FileCache(Context context) {
		// Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			cacheDir = new File(Environment.getExternalStorageDirectory()
					+ "/Temple");
			if (cacheDir.mkdir()) {
				nomediaFile = new File(
						Environment.getExternalStorageDirectory() + "/Temple/"
								+ NOMEDIA);
				if (!nomediaFile.exists()) {
					try {
						nomediaFile.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else {
			cacheDir = context.getCacheDir();
		}
		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}

	public File getFile(String url) {
		// I identify images by hashcode. Not a perfect solution, good for the
		// demo.
		// String filename=String.valueOf(url.hashCode());
		// Another possible solution (thanks to grantland)
		@SuppressWarnings("deprecation")
		String filename = URLEncoder.encode(url);
		File f = new File(cacheDir, filename);
		return f;

	}

	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}
}
