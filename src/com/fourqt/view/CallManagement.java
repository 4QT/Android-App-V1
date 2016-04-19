package com.fourqt.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fourqt.main.RootActivity;

public class CallManagement extends RootActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_management);
		
		Button mBtnLeft = (Button) findViewById(R.id.btn_left);
		mBtnLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggle();
			}
		});
		TextView txvCall = (TextView)findViewById(R.id.btn_cm_call);
		TextView txvEmail = (TextView)findViewById(R.id.btn_cm_email);
		TextView txvSms = (TextView)findViewById(R.id.btn_cm_sms);
		
		txvCall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri
						.fromParts("tel", "", null));
				startActivity(intent);
			}
		});
		
		txvEmail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
					final Intent emailIntent = new Intent(
							android.content.Intent.ACTION_SEND);
					emailIntent.setType("plain/text");
					emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
							new String[] { "Email" });
					emailIntent.putExtra(android.content.Intent.EXTRA_CC,
							new String[] { "CC" });
					emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
							"");
					emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
							"Email Message");
					startActivity(Intent.createChooser(emailIntent,
							"Send mail..."));
			}
		});
		
		txvSms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage("phonenumber", null, "msg", null, null);
					Toast.makeText(getApplicationContext(), "Message Sent",
							Toast.LENGTH_LONG).show();
				} catch (Exception ex) {
					Toast.makeText(getApplicationContext(),
							ex.getMessage().toString(), Toast.LENGTH_LONG)
							.show();
					ex.printStackTrace();
				}
			}
		});
	}

}