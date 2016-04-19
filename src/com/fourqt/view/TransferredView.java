package com.fourqt.view;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.fourqt.main.RootActivity;
import com.fourqt.model.EnquirySource;
import com.fourqt.model.GetAllLeadListResult;
import com.fourqt.model.GetPreSaleEmployeeListResult;
import com.fourqt.model.ProjectLocation;
import com.fourqt.model.ProjectUnitType;
import com.fourqt.servicerequest.AsyncOperation;
import com.fourqt.servicerequest.ServerCallback;
import com.fourqt.util.CommonContexts;
import com.fourqt.util.GsonSingleton;
import com.fourqt.util.SystemUtil;

public class TransferredView extends RootActivity implements ServerCallback {
	private RadioGroup mRadioGroup;
	private RadioButton mRadioBtnFollowup, mRadioBtnTransfer, mRadioBtnSucess,
			mRadioBtnDummy;
	private Button save, reset;
	private GetAllLeadListResult resultantObj;
	private Spinner SpnrEmployee;
	private EditText edtRemarks;
	private boolean isEmplyeeSelected = false;
	private boolean isSaved = false;
	private String employeeSelected;
	private String employeeSelectedInt;
	private List<GetPreSaleEmployeeListResult> listBudget = new ArrayList<GetPreSaleEmployeeListResult>();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.transfered);
		Button mBtnLeft = (Button) findViewById(R.id.btn_left);
		mBtnLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggle();
			}
		});
		save = (Button) findViewById(R.id.btn_followup_save);
		reset = (Button) findViewById(R.id.btn_followup_reset);
		resultantObj = GsonSingleton.getInstance().fromJson(
				getIntent().getStringExtra("details"),
				GetAllLeadListResult.class);
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		mRadioBtnFollowup = (RadioButton) findViewById(R.id.radio_followup_follwoup);
		mRadioBtnSucess = (RadioButton) findViewById(R.id.radio_followup_successfull);
		mRadioBtnTransfer = (RadioButton) findViewById(R.id.radio_followup_transfer);
		mRadioBtnDummy = (RadioButton) findViewById(R.id.radio_followup_dump);
		mRadioGroup.check(R.id.radio_followup_transfer);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				if (checkedId == R.id.radio_followup_follwoup) {
					Intent intent = new Intent(TransferredView.this,
							FollowupDetails.class);
					intent.putExtra("details", GsonSingleton.getInstance()
							.toJson(resultantObj));
					startActivity(intent);
				} else if (checkedId == R.id.radio_followup_successfull) {
					Intent intent = new Intent(TransferredView.this,
							SuccessfullView.class);
					intent.putExtra("details", GsonSingleton.getInstance()
							.toJson(resultantObj));
					startActivity(intent);
				} else if (checkedId == R.id.radio_followup_transfer) {

				} else if (checkedId == R.id.radio_followup_dump) {
					Intent intent = new Intent(TransferredView.this,
							DumpView.class);
					intent.putExtra("details", GsonSingleton.getInstance()
							.toJson(resultantObj));
					startActivity(intent);
				}
			}
		});
		SpnrEmployee = (Spinner) findViewById(R.id.spinner_transf_employeename);
		edtRemarks = (EditText) findViewById(R.id.edt_transf_remarks);

		isEmplyeeSelected = true;
		isSaved = false;
		String url = "" + getString(R.string.service_baseurl)
				+ "GetPreSaleEmployeeList?Token=slead&LoginID="
				+ CommonContexts.clr.getLoginID() + "";
		CallServer(url);
		employeeAdapter();
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				isEmplyeeSelected = false;
				isSaved = true;
				try{
				String url = "" + getString(R.string.service_baseurl)
						+ "TransferEnquiry?Token=slead&LoginID="
						+ CommonContexts.clr.getLoginID() + "&EnquiryIDs="
						+ resultantObj.getEnquiryId() + "&TransferToID="
						+ employeeSelectedInt + "&Remark="
						+ URLEncoder.encode(edtRemarks.getText().toString(), "UTF-8") + "";
				CallServer(url);
				}catch(Exception e)
				{
					
				}
			}
		});
	}

	private void CallServer(String url) {
		if (SystemUtil.haveNetworkConnection(TransferredView.this)) {
			AsyncOperation as = new AsyncOperation(this,
					CommonContexts.CONNECTION_GET);
			as.execute(url);

		} else {
			Toast.makeText(TransferredView.this, R.string.no_network,
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void doPostExecute(String serverResponse) throws JSONException {
		CommonContexts.dismissProgressDialog();
		if (serverResponse != null) {
			if (isEmplyeeSelected) {
				JSONObject jOb = new JSONObject(serverResponse);

				if (jOb.has("GetPreSaleEmployeeListResult")) {
					JSONArray jBudget = jOb
							.getJSONArray("GetPreSaleEmployeeListResult");
					if (jBudget != null && jBudget.length() > 0) {
						for (int i = 0; i < jBudget.length(); i++) {
							String res = jBudget.getString(i);
							GetPreSaleEmployeeListResult budget = GsonSingleton
									.getInstance().fromJson(res,
											GetPreSaleEmployeeListResult.class);
							listBudget.add(budget);
						}
						if(listBudget != null && listBudget.size()>0)
							employeeSelectedInt = listBudget.get(0).getLoginID();
						employeeAdapter();
					}
				}

			} else if (isSaved) {
				JSONObject jObje = new JSONObject(serverResponse);
				if(jObje.has("TransferEnquiryResult"))
				{
					String res = jObje.getString("TransferEnquiryResult");
					if(res != null && res.equalsIgnoreCase("1"))
					{
						Toast.makeText(TransferredView.this, "Transferred Successfully!!", Toast.LENGTH_LONG);
						
					}
				}
			}
		}
	}

	@Override
	public void doPreExecute() {
		CommonContexts.showProgress(TransferredView.this, "Please wait..");
	}

	private void employeeAdapter() {
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getEmployeesList());
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		SpnrEmployee.setAdapter(adapter_state);
		SpnrEmployee
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						SpnrEmployee.setSelection(arg2);
						employeeSelected = SpnrEmployee.getSelectedItem()
								.toString();
						
						for(GetPreSaleEmployeeListResult gsel: listBudget)
						{
							if(gsel.getUserName().equalsIgnoreCase(employeeSelected))
							{
								employeeSelectedInt = gsel.getLoginID();
								break;
							}
							
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}

	private List<String> getEmployeesList() {
		List<String> floorPrefernce = new ArrayList<String>();
		if (listBudget != null && listBudget.size() > 0) {
			for (GetPreSaleEmployeeListResult fp : listBudget) {
				floorPrefernce.add(fp.getUserName());
			}
		}
		return floorPrefernce;
	}

}
