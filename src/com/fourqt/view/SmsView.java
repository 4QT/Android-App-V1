package com.fourqt.view;

import java.lang.Character.UnicodeBlock;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fourqt.main.RootActivity;
import com.fourqt.model.GetAllLeadListResult;
import com.fourqt.servicerequest.AsyncOperation;
import com.fourqt.servicerequest.ServerCallback;
import com.fourqt.util.CommonContexts;
import com.fourqt.util.GsonSingleton;
import com.fourqt.util.SystemUtil;
import com.fourqt.view.R;

public class SmsView extends RootActivity implements ServerCallback {

	private EditText edtSmsMobile, edtSmsMessage;
	private GetAllLeadListResult resultantObj;

	public SmsView() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms);

		resultantObj = GsonSingleton.getInstance().fromJson(
				getIntent().getStringExtra("details"),
				GetAllLeadListResult.class);
		edtSmsMobile = (EditText) findViewById(R.id.edt_sms_mobile);
		edtSmsMessage = (EditText) findViewById(R.id.edt_sms_message);
		Button btn = (Button) findViewById(R.id.btn_sms_send);

		if(resultantObj!= null)
		{
			edtSmsMobile.setText(resultantObj.getMobileNo());
		}
		
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try{
				String url = ""+getString(R.string.service_baseurl)+"SaveSMSSendHistory?Token=slead&LoginID="
			+CommonContexts.clr.getLoginID()+"&EnquiryID="+resultantObj.getEnquiryId()+"&Number="+edtSmsMobile.getText().toString()
				+"&Message="+URLEncoder.encode(edtSmsMessage.getText().toString(),"UTF-8")+"&SubjectId";
				
				CallServer(url);
				}catch(Exception e)
				{
					
				}
//				String phoneNo = edtSmsMobile.getText().toString();
//				String msg = edtSmsMessage.getText().toString();
//				try {
//					SmsManager smsManager = SmsManager.getDefault();
//					smsManager.sendTextMessage(phoneNo, null, msg, null, null);
//					Toast.makeText(getApplicationContext(), "Message Sent",
//							Toast.LENGTH_LONG).show();
//				} catch (Exception ex) {
//					Toast.makeText(getApplicationContext(),
//							ex.getMessage().toString(), Toast.LENGTH_LONG)
//							.show();
//					ex.printStackTrace();
//				}
			}
		});
	}
	private void CallServer( String url) {
		if (SystemUtil.haveNetworkConnection(SmsView.this)) {
			AsyncOperation as = new AsyncOperation(this, CommonContexts.CONNECTION_GET);
			as.execute(url);
			
		} else {
			Toast.makeText(SmsView.this, R.string.no_network, Toast.LENGTH_SHORT)
					.show();
		}
	}
	@Override
	public void doPostExecute(String serverResponse) throws JSONException {
		CommonContexts.dismissProgressDialog();
		if(serverResponse != null)
		{
			JSONObject jObject = new JSONObject(serverResponse);
			if(jObject.has("SaveSMSSendHistoryResult"))
			{
				String res = jObject.getString("SaveSMSSendHistoryResult");
				if(res.equalsIgnoreCase("1"))
				{
					Toast.makeText(SmsView.this, "Sms sent successfully!!", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	@Override
	public void doPreExecute() {
		CommonContexts.showProgress(SmsView.this, "Please wait....");
	}

}
