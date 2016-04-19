package com.fourqt.imageloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.fourqt.view.R;

public class ImageLoader {

	// Initialize MemoryCache
	MemoryCache memoryCache = new MemoryCache();

	FileCache fileCache;

	// Create Map (collection) to store image and image url in key value pair
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	ExecutorService executorService;

	// handler to display images in UI thread
	Handler handler = new Handler();

	public ImageLoader(Context context) {

		fileCache = new FileCache(context);

		// Creates a thread pool that reuses a fixed number of
		// threads operating off a shared unbounded queue.
		executorService = Executors.newFixedThreadPool(5);

	}

	// default image show in list (Before online image download)
	final int stub_id = R.drawable.ic_launcher;

	public void DisplayImage(String url, ImageView imageView) {
		if (url.length() <= 6) {
			imageView.setImageResource(stub_id);
			return;
		}
		// Store image and url in Map
		imageViews.put(imageView, url);

		// Check image is stored in MemoryCache Map or not (see
		// MemoryCache.java)
		Bitmap bitmap = memoryCache.get(url);

		if (bitmap != null) {
			// if image is stored in MemoryCache Map then
			// Show image in listview row
			Log.i("SOME_TAG", "IMAGE IS AVAILABLE IN CACHE");

			int width = bitmap.getWidth() ;
			int height = bitmap.getHeight();

			if (width != height) {
				
				if (width > height) {
					Bitmap square_bitmap = Bitmap.createBitmap(bitmap, 0, 0,
							height, height);

					BitmapShader shader = new BitmapShader (bitmap,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
					Paint paint = new Paint();
					paint.setShader(shader);

//					Canvas c = new Canvas(square_bitmap);
//					c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);



					imageView.setImageBitmap(square_bitmap);
				} else {
					Bitmap square_bitmap = Bitmap.createBitmap(bitmap, 0, 0,
							width, width);
					imageView.setImageBitmap(square_bitmap);
				}
			} else {

				imageView.setImageBitmap(bitmap);

			}
		} else {
			// queue Photo to download from url
			Log.i("SOME_TAG", "IMAGE IS NOT AVAILABLE IN CACHE");
			queuePhoto(url, imageView);

			// Before downloading image show default image
			imageView.setImageResource(stub_id);
		}
	}

	public Bitmap getCroppedBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);

		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();

		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		// final Rect rect = new Rect(0, 0, 200, 200);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);

		// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
				bitmap.getWidth() / 2, paint);

//		 canvas.drawCircle(100, 100,100, paint);

		// canvas.drawCircle(50, 50,
		// 50, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		// Bitmap _bmp = Bitmap.createScaledBitmap(output, 100, 100, false);
		// return _bmp;
		return output;
	}

	private void queuePhoto(String url, ImageView imageView) {
		// Store image and url in PhotoToLoad object
		PhotoToLoad p = new PhotoToLoad(url, imageView);

		// pass PhotoToLoad object to PhotosLoader runnable class
		// and submit PhotosLoader runnable to executers to run runnable
		// Submits a PhotosLoader runnable task for execution

		executorService.submit(new PhotosLoader(p));
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			try {
				// Check if image already downloaded
				if (imageViewReused(photoToLoad))
					return;
				// download image from web url
				Bitmap bmp = getBitmap(photoToLoad.url);

				// set image data in Memory Cache
				memoryCache.put(photoToLoad.url, bmp);

				if (imageViewReused(photoToLoad))
					return;

				// Get bitmap to display
				BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);

				// Causes the Runnable bd (BitmapDisplayer) to be added to the
				// message queue.
				// The runnable will be run on the thread to which this handler
				// is attached.
				// BitmapDisplayer run method will call
				handler.post(bd);

			} catch (Throwable th) {
				th.printStackTrace();
			}
		}
	}

	private Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);

		// from SD cache
		// CHECK : if trying to decode file which not exist in cache return null
		Bitmap b = decodeFile(f);
		if (b != null)
			return b;

		// Download image file from web
		try {

			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();

			// Constructs a new FileOutputStream that writes to file
			// if file not exist then it will create file
			OutputStream os = new FileOutputStream(f);

			// See Utils class CopyStream method
			// It will each pixel from input stream and
			// write pixels to output stream (file)
			StreamUtils.CopyStream(is, os);

			os.close();
			conn.disconnect();

			// Now file created and going to resize file with defined height
			// Decodes image and scales it to reduce memory consumption
			bitmap = decodeFile(f);

			return bitmap;

		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError)
				memoryCache.clear();
			return null;
		}
	}

	// Decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(File f) {

		try {

			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			FileInputStream stream1 = new FileInputStream(f);
			BitmapFactory.decodeStream(stream1, null, o);
			stream1.close();

			// Find the correct scale value. It should be the power of 2.

			// Set width/height of recreated image
			final int REQUIRED_SIZE = 85;

			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with current scale values
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			FileInputStream stream2 = new FileInputStream(f);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
			stream2.close();
			return bitmap;

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	boolean imageViewReused(PhotoToLoad photoToLoad) {

		String tag = imageViews.get(photoToLoad.imageView);
		// Check url is already exist in imageViews MAP
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run() {
			if (imageViewReused(photoToLoad))
				return;

			// Show bitmap on UI
			if (bitmap != null) {
				Log.i("SOME_TAG", "INVALIDATING BM");

				int width = bitmap.getWidth() ;
				int height = bitmap.getHeight();

				if (width != height) {
					/*
					 * Bitmap square_bitmap = Bitmap.createBitmap(bitmap, 0, 0,
					 * width, width);
					 * photoToLoad.imageView.setImageBitmap(square_bitmap);
					 */
					if (width > height) {
						Bitmap square_bitmap = Bitmap.createBitmap(bitmap, 0,
								0, height, height);
						// Bitmap b = getCroppedBitmap(square_bitmap);
						BitmapShader shader = new BitmapShader (bitmap,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
						Paint paint = new Paint();
						paint.setShader(shader);

						Canvas c = new Canvas(square_bitmap);
						c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);
						photoToLoad.imageView.setImageBitmap(square_bitmap);
					} else {
						Bitmap square_bitmap = Bitmap.createBitmap(bitmap, 0,
								0, width, width);
						// Bitmap b = getCroppedBitmap(square_bitmap);
						photoToLoad.imageView.setImageBitmap(square_bitmap);
					}

					// Bitmap square_bitmap = Bitmap.createBitmap(bitmap,
					// width/2, height/2, 50, 50);
					// Bitmap b = getCroppedBitmap(square_bitmap);
					// photoToLoad.imageView.setImageBitmap(b);
				} else {

					// Bitmap b = getCroppedBitmap(bitmap);

					photoToLoad.imageView.setImageBitmap(bitmap);

				}
			} else
				photoToLoad.imageView.setImageResource(stub_id);
		}
	}

	public void clearCache() {
		// Clear cache directory downloaded images and stored data in maps
		memoryCache.clear();
		fileCache.clear();
	}

}
