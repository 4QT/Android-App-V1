package com.fourqt.servicerequest;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ServiceHandler extends Thread {

	private Handler mHandler;
	private final Context context;
	public static final int SUCCESS = 111;
	public static final int FAILED = 110;
	public static final int ERROR = 112;
	public static final int NETWORK_FAIL = 112;

	String responseString;
	String url;
	List<NameValuePair> list;

	public ServiceHandler(Handler mHandler, Context context, String url,
			List<NameValuePair> list) {
		this.mHandler = mHandler;
		this.context = context;
		this.url = url;
		this.list = list;
	}

	@Override
	public void run() {

		String response = RequestManager.makePostRequest(url, list);
		Message message = mHandler.obtainMessage();
		Bundle b = new Bundle();
		b.putString("data", response);
		message.setData(b);
		mHandler.sendMessage(message);
		System.out.println(response);
	}

	private void sendSuccess() {
		Log.e("CheckProfileThread", ">>>>>>>>>  SUCCESS");
		mHandler.sendEmptyMessage(SUCCESS);
	}

	private void sendFailure() {
		Log.e("CheckProfileThread", ">>>>>>>>>  FAILED");
		mHandler.sendEmptyMessage(FAILED);
	}

	private void sendNetWorkFail() {
		Log.e("CheckProfileThread", ">>>>>>>>>  NETWORK_FAIL");
		mHandler.sendEmptyMessage(NETWORK_FAIL);
	}
}