package com.fourqt.servicerequest;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;

import com.fourqt.exception.ServiceException;

/**
 * Common Async operation which takes input from individual activities This
 * would act as network bridge
 * 
 * @author Vijay
 * 
 */
public class AsyncOperation extends AsyncTask<String, Void, String> {

	ServerCallback serverCallBack;
	private String requestType, payload;
	private Context mContext;

	public AsyncOperation(Context context, ServerCallback callback,
			String reqType, String payload) {
		this.serverCallBack = callback;
		this.requestType = reqType;
		this.payload = payload;
		this.mContext = context;
	}

	public AsyncOperation(ServerCallback callback, String reqType) {
		this.serverCallBack = callback;
		this.requestType = reqType;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		serverCallBack.doPreExecute();
	}

	@Override
	protected String doInBackground(String... params) {
		String response = null;
		try {

			response = RequestManager.getSysRequest(params[0], requestType);
		} catch (ServiceException e) {

		}
		return response;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		try {
			serverCallBack.doPostExecute(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}