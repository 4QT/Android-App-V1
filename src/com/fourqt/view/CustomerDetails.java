package com.fourqt.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fourqt.main.RootActivity;
import com.fourqt.model.City;
import com.fourqt.model.EnquiryFrom;
import com.fourqt.model.EnquirySource;
import com.fourqt.model.EnquiryType;
import com.fourqt.model.GetAllLeadListResult;
import com.fourqt.model.Location;
import com.fourqt.model.ProjectLocation;
import com.fourqt.model.ProjectUnitType;
import com.fourqt.model.PropertyType;
import com.fourqt.servicerequest.AsyncOperation;
import com.fourqt.servicerequest.ServerCallback;
import com.fourqt.util.CommonContexts;
import com.fourqt.util.GsonSingleton;
import com.fourqt.util.SystemUtil;

public class CustomerDetails extends RootActivity implements ServerCallback {

	private EditText edtMobileNumber, edtFullName, edtEmailAddress;
	private Spinner edtSource, edtEnquiryType, edtEnquiryForm, edtCity,
			edtLocation, edtPropertyType, edtProject, edtUnitType;

	private String edtSourceSelected, edtEnquiryTypeSelected,
			edtEnquiryFormSelected, edtCitySelected, edtLocationSelected,
			edtPropertyTypeSelected, edtProjectSelected, edtUnitTypeSelected,selectedBoundType;
	private GetAllLeadListResult resultantObj;
	
	private RadioGroup radioGroup1;
	private RadioButton radioInBound,radioOutBound;
	private int radioSelectedIndex;
	private Button save,reset;
	private SeekBar mSeekBarBudget;
	private TextView minBud;
	int step = 1,max = 180,min = 60;
	public CustomerDetails() {
		super();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.customer_details);
		
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

		save = (Button)findViewById(R.id.btn_save);
		reset = (Button)findViewById(R.id.btn_reset);
		
		edtMobileNumber = (EditText) findViewById(R.id.edt_mobile_number);
		edtFullName = (EditText) findViewById(R.id.edt_fullname);
		edtEmailAddress = (EditText) findViewById(R.id.edt_emailaddress);
		edtSource = (Spinner) findViewById(R.id.edt_source);
		edtEnquiryType = (Spinner) findViewById(R.id.edt_enquirytype);
		edtEnquiryForm = (Spinner) findViewById(R.id.edt_enquiryform);
		edtCity = (Spinner) findViewById(R.id.edt_city);
		edtLocation = (Spinner) findViewById(R.id.edt_location);
		edtPropertyType = (Spinner) findViewById(R.id.edt_property);
		edtProject = (Spinner) findViewById(R.id.edt_project);
		edtUnitType = (Spinner) findViewById(R.id.edt_unittype);

		if (resultantObj != null) {
			edtMobileNumber.setText(resultantObj.getMobileNo());
			edtFullName.setText(resultantObj.getCustomerName());
			edtEmailAddress.setText(resultantObj.getEmailID());
		}

		sourceAdapter();
		enquiryTypeAdapter();
		enquiryFormAdapter();
		cityAdapter();
		locationAdapter();
		propertypeAdapter();
		projectLocationAdapter();
		radioGroup1 = (RadioGroup)findViewById(R.id.radioGroup1);
		radioInBound = (RadioButton)findViewById(R.id.radio_inbound);
		radioOutBound = (RadioButton)findViewById(R.id.radio_outbound);
		radioGroup1.check(R.id.radio_inbound);
		radioSelectedIndex = radioGroup1.getCheckedRadioButtonId();
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId ) {
				if(checkedId == R.id.radio_inbound)
				{
					selectedBoundType = "Inbound";
				}else if(checkedId == R.id.radio_outbound){
					selectedBoundType = "Outbound";
				}
			}
		});
		
		mSeekBarBudget = (SeekBar)findViewById(R.id.seekBar1_budget);
		minBud = (TextView)findViewById(R.id.budget_min);
		mSeekBarBudget.setMax( (max - min) / step );
		mSeekBarBudget.setOnSeekBarChangeListener(
			    new OnSeekBarChangeListener()
			    {
			        @Override
			        public void onStopTrackingTouch(SeekBar seekBar) {}

			        @Override
			        public void onStartTrackingTouch(SeekBar seekBar) {}

			        @Override
			        public void onProgressChanged(SeekBar seekBar, int progress, 
			            boolean fromUser) 
			        {
			            double value = min + (progress * step);
			            minBud.setText(""+value);

			        }
			    }
			);
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String url =""+getString(R.string.service_baseurl)+"SaveNewLead?Token=sLead&LoginID="+CommonContexts.clr.getLoginID()+"&ThroughSource="+edtSourceSelected
						+"&FirstName="+resultantObj.getFirstName()+"&ISDCode="+resultantObj.getISDCode()+"&MobileNo="+resultantObj.getMobileNo()
						+"&EmailID="+resultantObj.getEmailID()+"&EnqType="+edtEnquiryTypeSelected+"&EnquiryFrom="+edtEnquiryFormSelected
						+"&BoundType="+selectedBoundType+"&MinBudget=0&MaxBudget="+minBud.getText()+"&City_Id="+edtCitySelected+"&Zone_Ids=&" +
								"Location_Ids="+edtLocationSelected+"&Locality_Ids="+edtLocationSelected
						+"&PropertyType_Ids="+edtPropertyTypeSelected+"&Project_UnitType_Ids=";
				CallServer(url);
				
			}
		});

	}
	private void CallServer( String url) {
		if (SystemUtil.haveNetworkConnection(CustomerDetails.this)) {
			AsyncOperation as = new AsyncOperation(this, CommonContexts.CONNECTION_GET);
			as.execute(url);
			
		} else {
			Toast.makeText(CustomerDetails.this, R.string.no_network, Toast.LENGTH_SHORT)
					.show();
		}
	}
	private void sourceAdapter() {
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getFloorPreference());
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		edtSource.setAdapter(adapter_state);
		edtSource
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						edtSource.setSelection(arg2);
						edtSourceSelected = edtSource.getSelectedItem()
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}

	private void enquiryTypeAdapter() {
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getEnquiryType());
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		edtEnquiryType.setAdapter(adapter_state);
		edtEnquiryType
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						edtEnquiryType.setSelection(arg2);
						edtEnquiryTypeSelected = edtEnquiryType
								.getSelectedItem().toString();

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}

	private void enquiryFormAdapter() {
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getEnquiryForm());
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		edtEnquiryForm.setAdapter(adapter_state);
		edtEnquiryForm
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						edtEnquiryForm.setSelection(arg2);
						edtEnquiryFormSelected = edtEnquiryForm
								.getSelectedItem().toString();

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}

	private void cityAdapter() {
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getCity());
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		edtCity.setAdapter(adapter_state);
		edtCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				edtCity.setSelection(arg2);
				edtCitySelected = edtCity.getSelectedItem().toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	private void locationAdapter() {
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getLocation());
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		edtLocation.setAdapter(adapter_state);
		edtLocation
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						edtLocation.setSelection(arg2);
						edtLocationSelected = edtLocation.getSelectedItem()
								.toString();

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}

	private void propertypeAdapter() {
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getPropertyType());
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		edtPropertyType.setAdapter(adapter_state);
		edtPropertyType
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						edtPropertyType.setSelection(arg2);
						edtPropertyTypeSelected = edtPropertyType
								.getSelectedItem().toString();

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}
	private void projectLocationAdapter() {
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getProjectLocationList());
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		edtProject.setAdapter(adapter_state);
		edtProject
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						edtProject.setSelection(arg2);
						edtProjectSelected = edtProject.getSelectedItem().toString();
						projectUnitTypeAdapter(edtProjectSelected);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}
	
	private void projectUnitTypeAdapter(String pojectSelected) {
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getProjectUnitType());
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		edtUnitType.setAdapter(adapter_state);
		edtUnitType
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						edtUnitType.setSelection(arg2);
						edtUnitTypeSelected = edtUnitType.getSelectedItem().toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}

	private List<String> getFloorPreference() {
		List<String> floorPrefernce = new ArrayList<String>();
		if (CommonContexts.master.getEnquirySourceList() != null
				&& CommonContexts.master.getEnquirySourceList().size() > 0) {
			for (EnquirySource fp : CommonContexts.master
					.getEnquirySourceList()) {
				floorPrefernce.add(fp.getEnquirySourceName());
			}
		}
		return floorPrefernce;
	}

	private List<String> getEnquiryType() {
		List<String> floorPrefernce = new ArrayList<String>();
		if (CommonContexts.master.getEnquiryTypeList() != null
				&& CommonContexts.master.getEnquiryTypeList().size() > 0) {
			for (EnquiryType fp : CommonContexts.master.getEnquiryTypeList()) {
				floorPrefernce.add(fp.getEnquiryTypeName());
			}
		}
		return floorPrefernce;
	}

	private List<String> getEnquiryForm() {
		List<String> floorPrefernce = new ArrayList<String>();
		if (CommonContexts.master.getEnquiryFormList() != null
				&& CommonContexts.master.getEnquiryFormList().size() > 0) {
			for (EnquiryFrom fp : CommonContexts.master.getEnquiryFormList()) {
				floorPrefernce.add(fp.getEnquiryFrom_Name());
			}
		}
		return floorPrefernce;
	}

	private List<String> getCity() {
		List<String> floorPrefernce = new ArrayList<String>();
		if (CommonContexts.master.getCityList() != null
				&& CommonContexts.master.getCityList().size() > 0) {
			for (City fp : CommonContexts.master.getCityList()) {
				floorPrefernce.add(fp.getCity_Name());
			}
		}
		return floorPrefernce;
	}

	private List<String> getLocation() {
		List<String> floorPrefernce = new ArrayList<String>();
		if (CommonContexts.master.getLocationList() != null
				&& CommonContexts.master.getLocationList().size() > 0) {
			for (Location fp : CommonContexts.master.getLocationList()) {
				floorPrefernce.add(fp.getLocation_Name());
			}
		}
		return floorPrefernce;
	}

	private List<String> getPropertyType() {
		List<String> floorPrefernce = new ArrayList<String>();
		if (CommonContexts.master.getPropertyTypeList() != null
				&& CommonContexts.master.getPropertyTypeList().size() > 0) {
			for (PropertyType fp : CommonContexts.master.getPropertyTypeList()) {
				floorPrefernce.add(fp.getPropertyType_Name());
			}
		}
		return floorPrefernce;
	}
	private List<String> getProjectLocationList() {
		List<String> floorPrefernce = new ArrayList<String>();
		if (CommonContexts.master.getProjectLocationList() != null
				&& CommonContexts.master.getProjectLocationList().size() > 0) {
			for (ProjectLocation fp : CommonContexts.master.getProjectLocationList()) {
				floorPrefernce.add(fp.getProjectName());
			}
		}
		return floorPrefernce;
	}
	private List<String> getProjectUnitType() {
		List<String> floorPrefernce = new ArrayList<String>();
		if (CommonContexts.master.getProjectUnitType() != null
				&& CommonContexts.master.getProjectUnitType().size() > 0) {
			for (ProjectUnitType fp : CommonContexts.master.getProjectUnitType()) {
				floorPrefernce.add(fp.getProject_Name());
			}
		}
		return floorPrefernce;
	}

	@Override
	public void doPostExecute(String serverResponse) throws JSONException {
		CommonContexts.dismissProgressDialog();
		if(serverResponse != null)
		{
			
		}
	}

	@Override
	public void doPreExecute() {
		CommonContexts.showProgress(CustomerDetails.this, "Please wait..");
	}

}
