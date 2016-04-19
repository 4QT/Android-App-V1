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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.fourqt.main.RootActivity;
import com.fourqt.model.DumpReason;
import com.fourqt.model.GetAllLeadListResult;
import com.fourqt.model.GetPreSaleEmployeeListResult;
import com.fourqt.servicerequest.AsyncOperation;
import com.fourqt.servicerequest.ServerCallback;
import com.fourqt.util.CommonContexts;
import com.fourqt.util.GsonSingleton;
import com.fourqt.util.SystemUtil;

public class DumpView extends RootActivity implements ServerCallback{
	private RadioGroup mRadioGroup;
	private RadioButton mRadioBtnFollowup,mRadioBtnTransfer,mRadioBtnSucess,mRadioBtnDummy;
	private Button save,reset;
	private GetAllLeadListResult resultantObj;
	private Spinner SpnrDump;
	private EditText edtRemarks;
	private String dumpSelected;
	private String dumpSelectedInt;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dump_view);
		Button mBtnLeft = (Button) findViewById(R.id.btn_left);
		mBtnLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggle();
			}
		});
		save = (Button)findViewById(R.id.btn_followup_save);
		reset = (Button)findViewById(R.id.btn_followup_reset);
		resultantObj = GsonSingleton.getInstance().fromJson(
				getIntent().getStringExtra("details"),
				GetAllLeadListResult.class);
		mRadioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
		mRadioBtnFollowup = (RadioButton)findViewById(R.id.radio_followup_follwoup);
		mRadioBtnSucess = (RadioButton)findViewById(R.id.radio_followup_successfull);
		mRadioBtnTransfer = (RadioButton)findViewById(R.id.radio_followup_transfer);
		mRadioBtnDummy = (RadioButton)findViewById(R.id.radio_followup_dump);
		mRadioGroup.check(R.id.radio_followup_dump);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId ) {
				if(checkedId == R.id.radio_followup_follwoup)
				{
					Intent intent = new Intent(DumpView.this,FollowupDetails.class);
					intent.putExtra("details", GsonSingleton.getInstance()
							.toJson(resultantObj));
					startActivity(intent);
				}else if(checkedId == R.id.radio_followup_successfull){
					Intent intent = new Intent(DumpView.this,SuccessfullView.class);
					intent.putExtra("details", GsonSingleton.getInstance()
							.toJson(resultantObj));
					startActivity(intent);
				}else if(checkedId == R.id.radio_followup_transfer)
				{
					Intent intent = new Intent(DumpView.this,TransferredView.class);
					intent.putExtra("details", GsonSingleton.getInstance()
							.toJson(resultantObj));
					startActivity(intent);
					
				}else if(checkedId == R.id.radio_followup_dump)
				{
				}
			}
		});
		SpnrDump = (Spinner) findViewById(R.id.spinner_dump_reason);
		edtRemarks = (EditText) findViewById(R.id.edt_dump_reason);
		dumpAdapter();
		dumpSelectedInt = CommonContexts.master.getDumpReasonList().get(0).getDumpID();
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				try{
				String url = ""+getString(R.string.service_baseurl)+"SaveDumpEnquiry?Token=sLead&LoginID="
				+CommonContexts.clr.getLoginID()+"&EnquiryID="+resultantObj.getEnquiryId()
				+"&Remarks="+URLEncoder.encode(edtRemarks.getText().toString(), "UTF-8")+"&Stage_Id&CommID=1&DumpReasonId="+URLEncoder.encode(dumpSelectedInt, "UTF-8")+"";
				CallServer(url);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
	}
	private void dumpAdapter() {
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getDumpList());
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		SpnrDump.setAdapter(adapter_state);
		SpnrDump
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						SpnrDump.setSelection(arg2);
						dumpSelected = SpnrDump.getSelectedItem()
								.toString();
						for(DumpReason dr : CommonContexts.master.getDumpReasonList())
						{
							if(dr.getDumpName().equalsIgnoreCase(dumpSelected))
							{
								dumpSelectedInt = dr.getDumpID();
								break;
							}
							
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}

	private List<String> getDumpList() {
		List<String> floorPrefernce = new ArrayList<String>();
		if (CommonContexts.master.getDumpReasonList() != null && CommonContexts.master.getDumpReasonList().size() > 0) {
			for (DumpReason fp : CommonContexts.master.getDumpReasonList()) {
				floorPrefernce.add(fp.getDumpName());
			}
		}
		return floorPrefernce;
	}
	private void CallServer( String url) {
		if (SystemUtil.haveNetworkConnection(DumpView.this)) {
			AsyncOperation as = new AsyncOperation(this, CommonContexts.CONNECTION_GET);
			as.execute(url);
			
		} else {
			Toast.makeText(DumpView.this, R.string.no_network, Toast.LENGTH_SHORT)
					.show();
		}
	}
	@Override
	public void doPostExecute(String serverResponse) throws JSONException {
		CommonContexts.dismissProgressDialog();
		if(serverResponse != null)
		{
			JSONObject jObje = new JSONObject(serverResponse);
			if(jObje.has("SaveDumpEnquiryResult"))
			{
				String res = jObje.getString("SaveDumpEnquiryResult");
				if(res != null && res.equalsIgnoreCase("0"))
				{
					Toast.makeText(DumpView.this, "Updated Successfully!!", Toast.LENGTH_LONG);
					
				}
			}
		}
	}
	@Override
	public void doPreExecute() {
		CommonContexts.showProgress(DumpView.this, "Please wait..");	
	}

}
