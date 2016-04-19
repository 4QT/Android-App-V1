package com.fourqt.util;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.fourqt.model.CheckLoginResult;
import com.fourqt.model.GetAllLeadListResult;
import com.fourqt.model.ListAllEnquiryMastersResult;
import com.fourqt.view.R;

public class CommonContexts {

	public static String CONNECTION_GET = "GET";
	public static String CONNECTION_POST = "POST";
	public static long pickupTime = 0;
	public static ProgressDialog mProgressDialog;
	public static String deviceID = null;
	public static CheckLoginResult clr= null;
	public static String appkey = null;
	public static List<GetAllLeadListResult> listFollowup= new ArrayList<GetAllLeadListResult>();
	public static ListAllEnquiryMastersResult master = new ListAllEnquiryMastersResult();

	public static void showProgress(Context context, String message) {
		if (mProgressDialog != null && !mProgressDialog.isShowing()) {
			return;
		}
		mProgressDialog = new ProgressDialog(context,
				AlertDialog.THEME_HOLO_LIGHT);
		mProgressDialog.setMessage(message);
		mProgressDialog.show();

	}

	public static void dismissProgressDialog() {
		if (mProgressDialog != null) {
			if (mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
				mProgressDialog = null;
			}
		}
	}

	public static void showAletDialog(Context mContext,String message,String titles) {

		final Dialog alert = new Dialog(mContext);
		alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
		alert.setContentView(R.layout.alertdialog);
		TextView title = (TextView) alert.findViewById(R.id.txv_title);
		TextView messg = (TextView) alert.findViewById(R.id.txv_messg);
		Button mBtn = (Button) alert.findViewById(R.id.btn_alert);
		title.setText(titles);
		messg.setText(message);
		mBtn.setText("Ok");
		mBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert.cancel();
			}
		});
		alert.show();
	}
	
	public static String getIMEI(Context mContext)
	{
		TelephonyManager telephonyManager = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
	
}
