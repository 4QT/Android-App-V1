package com.fourqt.view;

import org.json.JSONException;

import android.os.Bundle;

import com.fourqt.main.RootActivity;
import com.fourqt.servicerequest.ServerCallback;

public class SearchEnquiry extends RootActivity implements ServerCallback{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchenquiry_view);
	}
	@Override
	public void doPostExecute(String serverResponse) throws JSONException {
		
	}
	@Override
	public void doPreExecute() {
		
	}
}
