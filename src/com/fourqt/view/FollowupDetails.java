package com.fourqt.view;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONException;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fourqt.main.RootActivity;
import com.fourqt.model.EnquiryStage;
import com.fourqt.model.GetAllLeadListResult;
import com.fourqt.model.MainFollowupType;
import com.fourqt.model.SubFollowUpType;
import com.fourqt.servicerequest.AsyncOperation;
import com.fourqt.servicerequest.ServerCallback;
import com.fourqt.util.CommonContexts;
import com.fourqt.util.GsonSingleton;
import com.fourqt.util.SystemUtil;

public class FollowupDetails extends RootActivity implements ServerCallback{

	private Spinner mResponseType, mSubResponseType, mStage;
	private EditText mMeetDateTime,
	mMeetingPlace,mEdtMeetingAddress, mEdtDateTime, mEdtDateOfClose,
			mEdtRemarks, mSiteVisitDate;
	private EditText mSiteVisitAddress, mSiteVisitDone, mMeetingDoneDuration;
	private ImageView mImgCall, mImgSms, mImgEmail;
	private TextView mTxvName, mTxvDatetime, mTxvAcres, mTxvLacs;
	private String selectedStage;
	private static String dobDate;
	public static String DATEPICKER = "DatePicker";

	private GetAllLeadListResult resultantObj;
	private LinearLayout mLinlayMeetingReschedule, mLinlayMeetingDone,
			mLinlaySiteVisitSchedule, mLinlaySiteVisitDone;
	private int mYear, mMonth, mDay, mHour, mMinute;
	private String sResponseTypeSelected,sSubResTypeSelected,sSelectedStageId;
	private RadioGroup mRadioGroup;
	private RadioButton mRadioBtnFollowup,mRadioBtnTransfer,mRadioBtnSucess,mRadioBtnDummy;
	private Button save,reset;

	public FollowupDetails() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.followup_detais);
		Button mBtnLeft = (Button) findViewById(R.id.btn_left);
		mBtnLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggle();
			}
		});
		
		save = (Button)findViewById(R.id.btn_followup_save);
		reset = (Button)findViewById(R.id.btn_followup_reset);
		
		mLinlayMeetingReschedule = (LinearLayout) findViewById(R.id.meeting_reschedule);
		mLinlayMeetingDone = (LinearLayout) findViewById(R.id.meeting_done);
		mLinlaySiteVisitSchedule = (LinearLayout) findViewById(R.id.sitevisit_schedule_reschedule);
		mLinlaySiteVisitDone = (LinearLayout) findViewById(R.id.sitvisit_done);

		mResponseType = (Spinner) findViewById(R.id.spinner_responsetype);
		mSubResponseType = (Spinner) findViewById(R.id.spinner_subresponsetype);
		mStage = (Spinner) findViewById(R.id.spinner_stage);

		mMeetDateTime = (EditText) findViewById(R.id.edt_followup_meeting_date);
		mMeetDateTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				popUpTime(mMeetDateTime);
				popUpDateTime(mMeetDateTime);
				
			}
		});
		mMeetingPlace = (EditText) findViewById(R.id.edt_followup_meeting_place);
		mEdtMeetingAddress = (EditText) findViewById(R.id.edt_followup_meetingaddress);

		mSiteVisitDate = (EditText) findViewById(R.id.edt_sitevisitdate);
		mSiteVisitDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				popUpTime(mSiteVisitDate);
				popUpDateTime(mSiteVisitDate);
			}
		});
		mSiteVisitAddress = (EditText) findViewById(R.id.edt_followup_sitevisit_address);
		mSiteVisitDone = (EditText) findViewById(R.id.edt_followup_sitevisit_duration);
		mMeetingDoneDuration = (EditText) findViewById(R.id.edt_followup_meetingdone_duration);

		mImgCall = (ImageView) findViewById(R.id.img_followup_call);
		mImgSms = (ImageView) findViewById(R.id.img_followup_sms);
		mImgEmail = (ImageView) findViewById(R.id.img_followup_email);

		mTxvName = (TextView) findViewById(R.id.txv_followup_nameofuser);
		mTxvAcres = (TextView) findViewById(R.id.txv_followup_acres);
		mTxvDatetime = (TextView) findViewById(R.id.txv_followup_datetime);
		mTxvLacs = (TextView) findViewById(R.id.txv_followup_lacs);

		mEdtMeetingAddress = (EditText) findViewById(R.id.edt_followup_meetingaddress);
		mEdtDateTime = (EditText) findViewById(R.id.edt_followup_datetime);
		mEdtDateOfClose = (EditText) findViewById(R.id.edt_followup_dataof_closing);
		mEdtRemarks = (EditText) findViewById(R.id.edt_follwup_entercomments);

		resultantObj = GsonSingleton.getInstance().fromJson(
				getIntent().getStringExtra("details"),
				GetAllLeadListResult.class);
		if (resultantObj != null) {
			mTxvName.setText(resultantObj.getCustomerName());
			mTxvDatetime.setText(resultantObj.getEnquiryDate());
			mTxvLacs.setText(resultantObj.getMax_Budget());
			mTxvAcres.setText(resultantObj.getSource());
		}
		mLinlayMeetingDone.setVisibility(View.GONE);
		mLinlayMeetingReschedule.setVisibility(View.GONE);
		mLinlaySiteVisitDone.setVisibility(View.GONE);
		mLinlaySiteVisitSchedule.setVisibility(View.GONE);
		mEdtDateTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				popUpTime(mEdtDateTime);
				popUpDateTime(mEdtDateTime);
				
			}
		});
		mEdtDateOfClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				popUpDateTime(mEdtDateOfClose);
			}
		});
		
		mRadioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
		mRadioBtnFollowup = (RadioButton)findViewById(R.id.radio_followup_follwoup);
		mRadioBtnSucess = (RadioButton)findViewById(R.id.radio_followup_successfull);
		mRadioBtnTransfer = (RadioButton)findViewById(R.id.radio_followup_transfer);
		mRadioBtnDummy = (RadioButton)findViewById(R.id.radio_followup_dump);
		mRadioGroup.check(R.id.radio_followup_follwoup);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId ) {
				if(checkedId == R.id.radio_followup_follwoup)
				{
					
				}else if(checkedId == R.id.radio_followup_successfull){
					Intent intent = new Intent(FollowupDetails.this,SuccessfullView.class);
					intent.putExtra("details", GsonSingleton.getInstance()
							.toJson(resultantObj));
					startActivity(intent);
				}else if(checkedId == R.id.radio_followup_transfer)
				{
					Intent intent = new Intent(FollowupDetails.this,TransferredView.class);
					intent.putExtra("details", GsonSingleton.getInstance()
							.toJson(resultantObj));
					startActivity(intent);
				}else if(checkedId == R.id.radio_followup_dump)
				{
					Intent intent = new Intent(FollowupDetails.this,DumpView.class);
					intent.putExtra("details", GsonSingleton.getInstance()
							.toJson(resultantObj));
					startActivity(intent);
				}
			}
		});
		mainResponseAdapter();
		subResponseAdapter();
		stageAdapter();
		
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String mdt="0",duration="0",address="Bangalore";
				 if (selectedStage.equalsIgnoreCase("Meeting Rescheduled")
						|| selectedStage.equalsIgnoreCase("Meeting Scheduled")) {
					mdt = mMeetDateTime.getText().toString();
					address = mMeetingPlace.getText().toString();
				}  else if (selectedStage.equalsIgnoreCase("Meeting done")) {
					duration = mMeetingDoneDuration.getText().toString();

				} else if (selectedStage.equalsIgnoreCase("SV Scheduled")) {
					mdt = mSiteVisitDate.getText().toString();
					address = mSiteVisitAddress.getText().toString();
				} else if (selectedStage.equalsIgnoreCase("SV Rescheduled")) {
					mdt = mSiteVisitDate.getText().toString();
					address = mSiteVisitAddress.getText().toString();
				}  else if (selectedStage.equalsIgnoreCase("SV Done")) {
					duration = mSiteVisitDone.getText().toString();
				}
				 try{
				String url = ""+getString(R.string.service_baseurl)+"AddFollowUp?Token=slead&LoginID="+CommonContexts.clr.getLoginID()
						+"&EnquiryID="+resultantObj.getEnquiryId()
						+"&Remarks="+mEdtRemarks.getText().toString()+"&Stage_Id="+sSelectedStageId+"&CommID=1&NextFollowupDate="+resultantObj.getNextFollowupDate()
						+"&Time={Time}&TimeFormat={TimeFormat}&LastResponseID={LastResponseID}&TimeFrameId="+mEdtDateOfClose.getText().toString()+"&" +
						"MeetingDatetime="+mdt+"&MeetingAddress="+URLEncoder.encode(address, "UTF-8")+"&MeetingDuration="+duration+"";
				CallServer(url);
				 }catch(Exception e)
				 {
					 e.printStackTrace();
				 }
			}
		});
	}

	private List<String> getStage() {
		List<String> stage = new ArrayList<String>();
		if (CommonContexts.master.getEnquiryStageList() != null
				&& CommonContexts.master.getEnquiryStageList().size() > 0) {
			for (EnquiryStage fp : CommonContexts.master.getEnquiryStageList()) {
				stage.add(fp.getStage_Name());
			}
		}
		return stage;
	}
	private List<String> getMainResponseType() {
		List<String> stage = new ArrayList<String>();
		if (CommonContexts.master.getMainFollowupTypeList() != null
				&& CommonContexts.master.getMainFollowupTypeList().size() > 0) {
			for (MainFollowupType fp : CommonContexts.master.getMainFollowupTypeList()) {
				stage.add(fp.getMainFollowupTypeName());
			}
		}
		return stage;
	}
	private List<String> getSubResponseType() {
		List<String> stage = new ArrayList<String>();
		if (CommonContexts.master.getSubFollowUpTypeList() != null
				&& CommonContexts.master.getSubFollowUpTypeList().size() > 0) {
			for (SubFollowUpType fp : CommonContexts.master.getSubFollowUpTypeList()) {
				stage.add(fp.getFollowupType());
			}
		}
		return stage;
	}
	private void subResponseAdapter() {
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getSubResponseType());
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSubResponseType.setAdapter(adapter_state);
		mSubResponseType
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						mSubResponseType.setSelection(arg2);
						sSubResTypeSelected= mSubResponseType
								.getSelectedItem().toString();

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}
	private void mainResponseAdapter() {
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getMainResponseType());
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mResponseType.setAdapter(adapter_state);
		mResponseType
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						mResponseType.setSelection(arg2);
						sResponseTypeSelected = mResponseType
								.getSelectedItem().toString();

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}
	private void stageAdapter() {
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getStage());
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mStage.setAdapter(adapter_state);
		mStage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				mStage.setSelection(arg2);
				selectedStage = mStage.getSelectedItem().toString();

				for(EnquiryStage stage: CommonContexts.master.getEnquiryStageList())
				{
					if(stage.getStage_Name().equalsIgnoreCase(selectedStage))
					{
						sSelectedStageId = stage.getStage_Id();
						break;
					}
				}
				
				if (selectedStage.equalsIgnoreCase("Meeting Rescheduled")
						|| selectedStage.equalsIgnoreCase("Meeting Scheduled")) {
					mLinlayMeetingDone.setVisibility(View.GONE);
					mLinlayMeetingReschedule.setVisibility(View.VISIBLE);
					mLinlaySiteVisitDone.setVisibility(View.GONE);
					mLinlaySiteVisitSchedule.setVisibility(View.GONE);
				} else if (selectedStage.equalsIgnoreCase("Meeting Cancelled")) {
					mLinlayMeetingDone.setVisibility(View.GONE);
					mLinlayMeetingReschedule.setVisibility(View.GONE);
					mLinlaySiteVisitDone.setVisibility(View.GONE);
					mLinlaySiteVisitSchedule.setVisibility(View.GONE);

				} else if (selectedStage.equalsIgnoreCase("Meeting done")) {
					mLinlayMeetingDone.setVisibility(View.VISIBLE);
					mLinlayMeetingReschedule.setVisibility(View.GONE);
					mLinlaySiteVisitDone.setVisibility(View.GONE);
					mLinlaySiteVisitSchedule.setVisibility(View.GONE);

				} else if (selectedStage.equalsIgnoreCase("SV Scheduled")) {
					mLinlayMeetingDone.setVisibility(View.GONE);
					mLinlayMeetingReschedule.setVisibility(View.GONE);
					mLinlaySiteVisitDone.setVisibility(View.GONE);
					mLinlaySiteVisitSchedule.setVisibility(View.VISIBLE);
				} else if (selectedStage.equalsIgnoreCase("SV Rescheduled")) {
					mLinlayMeetingDone.setVisibility(View.GONE);
					mLinlayMeetingReschedule.setVisibility(View.GONE);
					mLinlaySiteVisitDone.setVisibility(View.GONE);
					mLinlaySiteVisitSchedule.setVisibility(View.VISIBLE);
				} else if (selectedStage.equalsIgnoreCase("SV Cancelled")) {
					mLinlayMeetingDone.setVisibility(View.GONE);
					mLinlayMeetingReschedule.setVisibility(View.GONE);
					mLinlaySiteVisitDone.setVisibility(View.GONE);
					mLinlaySiteVisitSchedule.setVisibility(View.GONE);
				} else if (selectedStage.equalsIgnoreCase("SV Done")) {
					mLinlayMeetingDone.setVisibility(View.GONE);
					mLinlayMeetingReschedule.setVisibility(View.GONE);
					mLinlaySiteVisitDone.setVisibility(View.VISIBLE);
					mLinlaySiteVisitSchedule.setVisibility(View.GONE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	private void popUpDateTime(final EditText mEdtText) {
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog datePickerDialog = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						mEdtText.setText(year + "-"+(monthOfYear + 1)+"-"+dayOfMonth );
					}
				}, mYear, mMonth, mDay);
		datePickerDialog.show();

	}

	private void popUpTime(final EditText mEditText) {
		final Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		TimePickerDialog timePickerDialog = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {
					int count =0;
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						if(count ==1){
							mEditText.getText().append(" "+hourOfDay + ":" + minute);
						}
						count ++;
					}
				}, mHour, mMinute, false);
		timePickerDialog.show();
	}
	
	private void CallServer( String url) {
		if (SystemUtil.haveNetworkConnection(FollowupDetails.this)) {
			AsyncOperation as = new AsyncOperation(this, CommonContexts.CONNECTION_GET);
			as.execute(url);
			
		} else {
			Toast.makeText(FollowupDetails.this, R.string.no_network, Toast.LENGTH_SHORT)
					.show();
		}
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
		CommonContexts.showProgress(FollowupDetails.this, "Please wait..");
	}

}
