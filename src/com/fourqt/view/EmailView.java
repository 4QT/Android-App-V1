package com.fourqt.view;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fourqt.main.RootActivity;
import com.fourqt.model.CheckLoginResult;
import com.fourqt.model.EmailMessage;
import com.fourqt.model.GetAllLeadListResult;
import com.fourqt.servicerequest.AsyncOperation;
import com.fourqt.servicerequest.ServerCallback;
import com.fourqt.util.CommonContexts;
import com.fourqt.util.GsonSingleton;
import com.fourqt.util.SystemUtil;

public class EmailView extends RootActivity implements ServerCallback {

	private EditText edtEmailId, edtEmailCCId, edtEmailMessage;
	private Spinner edtEmailSubject;
	private String edtSubjectSelected;
	private GetAllLeadListResult resultantObj;
	private String subjectId, messagecontent;

	public EmailView() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email);

		Button mBtnLeft = (Button) findViewById(R.id.btn_left);
		mBtnLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggle();
			}
		});
		resultantObj = GsonSingleton.getInstance().fromJson(
				getIntent().getStringExtra("details"),
				GetAllLeadListResult.class);
		edtEmailId = (EditText) findViewById(R.id.edt_email_id);
		edtEmailCCId = (EditText) findViewById(R.id.edt_email_cc_id);
		edtEmailSubject = (Spinner) findViewById(R.id.edt_email_subject);
		edtEmailMessage = (EditText) findViewById(R.id.edt_email_message);
		Button btn = (Button) findViewById(R.id.btn_email_send);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				sendEmail();
			}
		});
		if (CommonContexts.master.getEmailMessageList().size() > 0) {
			edtEmailMessage.setText(CommonContexts.master.getEmailMessageList()
					.get(0).getMessageBody());
		}
		if (resultantObj != null) {
			edtEmailId.setText(resultantObj.getEmailID());
		}
		subjectAdapter();

	}

	private void CallServer(String url) {
		try {
			if (SystemUtil.haveNetworkConnection(EmailView.this)) {
				
				AsyncOperation as = new AsyncOperation(this,
						CommonContexts.CONNECTION_GET);
				as.execute(url);

			} else {
				Toast.makeText(EmailView.this, R.string.no_network,
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {

		}
	}

	private List<String> getSubject() {
		List<String> floorPrefernce = new ArrayList<String>();
		if (CommonContexts.master.getEmailMessageList() != null
				&& CommonContexts.master.getEmailMessageList().size() > 0) {
			for (EmailMessage fp : CommonContexts.master.getEmailMessageList()) {
				floorPrefernce.add(fp.getSubject());
			}
		}
		return floorPrefernce;
	}

	private void subjectAdapter() {
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getSubject());
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		edtEmailSubject.setAdapter(adapter_state);
		edtEmailSubject
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						edtEmailSubject.setSelection(arg2);
						edtSubjectSelected = edtEmailSubject.getSelectedItem()
								.toString();

						if (CommonContexts.master.getEmailMessageList().size() > 0) {
							for (EmailMessage em : CommonContexts.master
									.getEmailMessageList()) {
								if (edtSubjectSelected.equalsIgnoreCase(em
										.getSubject())) {
									edtEmailMessage.setText(em.getMessageBody());
									subjectId = em.getSubject_Id();
									messagecontent = em.getMessageBody();
								}
							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}

	public void sendEmail() {
//		messagecontent = "Hello Customer";
		try{
		String url = "" + getString(R.string.service_baseurl)
				+ "SaveEmailSendHistory?Token=slead&LoginID="
				+ CommonContexts.clr.getLoginID() + "&EnquiryID="
				+ resultantObj.getEnquiryId() + "&CC&Body=" + URLEncoder.encode(messagecontent, "UTF-8")
				+ "&SubjectId=" + subjectId + "&FileNames";

		CallServer(url);
		}catch(Exception e)
		{
			
		}

		
	}

	@Override
	public void doPostExecute(String serverResponse) throws JSONException {
		CommonContexts.dismissProgressDialog();
		if (serverResponse != null) {
			JSONObject jObject = new JSONObject(serverResponse);
			if (jObject.has("SaveEmailSendHistoryResult")) {
				String res = jObject.getString("");
				if(res.equalsIgnoreCase("1")){
					Toast.makeText(EmailView.this, "Email sent successfully", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	@Override
	public void doPreExecute() {
		CommonContexts.showProgress(EmailView.this, "Please wait....");
	}

}
