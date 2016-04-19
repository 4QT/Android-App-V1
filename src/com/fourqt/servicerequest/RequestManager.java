package com.fourqt.servicerequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.fourqt.exception.DataAccessException;
import com.fourqt.exception.ServiceException;

/**
 * @author vkumar
 *
 */
@SuppressWarnings("unused")
public class RequestManager {

	private static final String CONNECTION_POST = "POST";
	private static final String CONNECTION_GET = "GET";
	private static HttpURLConnection urlconn = null;
	private Context context;

	public static String getSysRequest(Context context, String url,
			String requestType, String payload) throws ServiceException {
		String response = null;
		try {
			URL urls = new URL(url);
			CookieHandler.setDefault(new CookieManager(null,
					CookiePolicy.ACCEPT_ALL));
			 URLConnection conn = urls.openConnection();
			 urlconn = (HttpURLConnection) conn;
			 
			urlconn.setDoInput(true);
			urlconn.setDoOutput(true);
			urlconn.setAllowUserInteraction(false);
			urlconn.setInstanceFollowRedirects(true);
			urlconn.setRequestMethod(requestType);
			urlconn.setRequestProperty("Content-Type", "application/json");
			urlconn.setReadTimeout(10000);
			urlconn.setConnectTimeout(10000);
			OutputStream os = (urlconn.getOutputStream());
			System.out.println("..................."+os);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					os));
			writer.write(payload);
			writer.close();
			os.close();
			response = readDataFromInputStream(urlconn.getInputStream());
			urlconn.disconnect();
		} catch (Exception e) {

//			System.out.println(""+e.printStackTrace());
			e.printStackTrace();
			throw new ServiceException(e.getMessage(), e);

		}
		return response;
	}

	/**
	 * Initiates the server request and process the request based on the parameters given to
	 * @param url
	 * @param requestType
	 * @param payload
	 * @return Server Response
	 * @throws ServiceException
	 */
	public static String getSysRequest( String url,
			String requestType) throws ServiceException {
		String response = null;
		try {
			URL urls = new URL(url);
			CookieHandler.setDefault(new CookieManager(null,
					CookiePolicy.ACCEPT_ALL));
			 URLConnection conn = urls.openConnection();
			 urlconn = (HttpURLConnection) conn;
			urlconn.setDoInput(true);
			urlconn.setDoOutput(true);
			urlconn.setAllowUserInteraction(false);
			urlconn.setInstanceFollowRedirects(true);
			urlconn.setRequestMethod(requestType);
			urlconn.setRequestProperty("Content-Type", "application/json");
			urlconn.connect();
			response = readDataFromInputStream(urlconn.getInputStream());
			urlconn.disconnect();
		} catch (Exception e) {

			throw new ServiceException(e.getMessage(), e);

		}
		return response;
	}

	public static String makePostRequest(String url, List<NameValuePair> list) {
		HttpResponse response = null;
		String responseStr = null;
		HttpClient httpClient = new DefaultHttpClient();
		// replace with your url
		HttpPost httpPost = new HttpPost(url);

		// Encoding POST data
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list));
		} catch (UnsupportedEncodingException e) {
			// log exception
			e.printStackTrace();
		}

		// making POST request.
		try {
			response = httpClient.execute(httpPost);
			// write response to log
			Log.d("Http Post Response:", response.toString());
			responseStr = EntityUtils.toString(response.getEntity());
			System.out.println(responseStr);
		} catch (ClientProtocolException e) {
			// Log exception
			e.printStackTrace();
		} catch (IOException e) {
			// Log exception
			e.printStackTrace();
		}
		return responseStr;
	}

	private InputStream copyAssets() {
		AssetManager assetManager = context.getAssets();
		String[] files = null;
		InputStream in = null;
		try {
			files = assetManager.list("");
		} catch (IOException e) {
			Log.e("tag", "Failed to get asset file list.", e);
		}
		for (String filename : files) {
			OutputStream out = null;
			try {
				in = assetManager.open(filename);
				int size = in.available();
				byte[] buffer = new byte[size];
				in.read(buffer);
				return in;

			} catch (IOException e) {
				Log.e("tag", "Failed to copy asset file: " + filename, e);
			}
		}
		return in;
	}

	/**
	 * Converts input stream to string.
	 * 
	 * @param is
	 * @return
	 */
	public static String readDataFromInputStream(BufferedReader is) {
		StringBuffer sb = new StringBuffer();

		int ch;
		try {

			String line;
			while ((line = is.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		}
		return sb.toString();
	}

	public static String readDataFromInputStream(InputStream is) {
		StringBuffer sb = null;
		int ch;
		try {
			sb = new StringBuffer();
			while ((ch = is.read()) != -1) {
				sb.append((char) ch);
			}
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		}
		return sb.toString();
	}

}
