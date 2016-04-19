package com.fourqt.view;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.fourqt.main.RootActivity;
import com.fourqt.model.GetAllLeadListResult;
import com.fourqt.servicerequest.AsyncOperation;
import com.fourqt.servicerequest.ServerCallback;
import com.fourqt.util.CommonContexts;
import com.fourqt.util.GsonSingleton;
import com.fourqt.util.SystemUtil;

public class SuccessfullView extends RootActivity implements ServerCallback{
	private RadioGroup mRadioGroup;
	private RadioButton mRadioBtnFollowup,mRadioBtnTransfer,mRadioBtnSucess,mRadioBtnDummy;
	private Button save, reset;
	private GetAllLeadListResult resultantObj;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.successfull_view);
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
		save = (Button)findViewById(R.id.btn_followup_save);
		reset = (Button)findViewById(R.id.btn_followup_reset);
		mRadioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
		mRadioBtnFollowup = (RadioButton)findViewById(R.id.radio_followup_follwoup);
		mRadioBtnSucess = (RadioButton)findViewById(R.id.radio_followup_successfull);
		mRadioBtnTransfer = (RadioButton)findViewById(R.id.radio_followup_transfer);
		mRadioBtnDummy = (RadioButton)findViewById(R.id.radio_followup_dump);
		mRadioGroup.check(R.id.radio_followup_successfull);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId ) {
				if(checkedId == R.id.radio_followup_follwoup)
				{
					Intent intent = new Intent(SuccessfullView.this,FollowupDetails.class);
					intent.putExtra("details", GsonSingleton.getInstance()
							.toJson(resultantObj));
					startActivity(intent);
				}else if(checkedId == R.id.radio_followup_successfull){
					
				}else if(checkedId == R.id.radio_followup_transfer)
				{
					Intent intent = new Intent(SuccessfullView.this,TransferredView.class);
					intent.putExtra("details", GsonSingleton.getInstance()
							.toJson(resultantObj));
					startActivity(intent);
					
				}else if(checkedId == R.id.radio_followup_dump)
				{
					Intent intent = new Intent(SuccessfullView.this,DumpView.class);
					intent.putExtra("details", GsonSingleton.getInstance()
							.toJson(resultantObj));
					startActivity(intent);
				}
			}
		});
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url = ""+getString(R.string.service_baseurl)+"SaveSuccessEnquiry?Token=sLead&LoginID="+CommonContexts.clr.getLoginID()
						+"&EnquiryID="+resultantObj.getEnquiryId()+"&Remarks={Remarks}&Stage_Id=&CommID={CommID}&SuccessProjectId={SuccessProjectId}&SuccessUnittypeId={SuccessUnittypeId}&Tower={Tower}&Floor={Floor}&UnitNo={UnitNo}";
				CallServer(url);
			}
		});
	}
	private void CallServer( String url) {
		if (SystemUtil.haveNetworkConnection(SuccessfullView.this)) {
			AsyncOperation as = new AsyncOperation(this, CommonContexts.CONNECTION_GET);
			as.execute(url);
			
		} else {
			Toast.makeText(SuccessfullView.this, R.string.no_network, Toast.LENGTH_SHORT)
					.show();
		}
	}
	@Override
	public void doPostExecute(String serverResponse) throws JSONException {
		
	}
	@Override
	public void doPreExecute() {
		
	}

}
