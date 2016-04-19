package com.fourqt.sliders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.internal.widget.ActionBarView.HomeView;
import com.fourqt.tab.ApprovalMainfr;
import com.fourqt.tab.EnquiryFollowupMainFr;
import com.fourqt.util.CommonContexts;
import com.fourqt.view.Administration;
import com.fourqt.view.CalendarViewApp;
import com.fourqt.view.CallManagement;
import com.fourqt.view.CustomerDetails;
import com.fourqt.view.LoginView;
import com.fourqt.view.R;
import com.fourqt.view.SalesFunnelView;
import com.fourqt.view.SearchEnquiry;

public class SlidingBaseDrawer extends Fragment implements OnClickListener {

	View rootView;
	static int SelectedRID = R.id.btn_slider_home;
	private TextView mBtnHome, mEnquiryFollowup, mNewEnquiry, mSearchEnquiry,
			mCallManagement, mEnquiryCalendar, mLogout,mSalesFunnel,mAdministration,mApproval;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.sliding_drawer, container, false);
		mBtnHome = (TextView) rootView.findViewById(R.id.btn_slider_home);
		mEnquiryCalendar = (TextView) rootView
				.findViewById(R.id.btn_slider_enquirycalendar);

		mEnquiryFollowup = (TextView) rootView
				.findViewById(R.id.btn_slider_enquiryfollowup);
		mNewEnquiry = (TextView) rootView
				.findViewById(R.id.btn_slider_newenquiry);
		mSearchEnquiry = (TextView) rootView
				.findViewById(R.id.btn_slider_searchenquiry);
		mCallManagement = (TextView) rootView
				.findViewById(R.id.btn_slider_callmanagement);
		mLogout = (TextView) rootView.findViewById(R.id.btn_slider_logout);
		mEnquiryCalendar = (TextView)rootView.findViewById(R.id.btn_slider_enquirycalendar);
		mSalesFunnel = (TextView)rootView.findViewById(R.id.btn_slider_salesfunnel);
		mAdministration = (TextView)rootView.findViewById(R.id.btn_slider_administration);
		mApproval = (TextView)rootView.findViewById(R.id.btn_slider_approval);
		
		
		if(CommonContexts.clr != null)
		{
			TextView name = (TextView)rootView.findViewById(R.id.btn_slider_maintext);
			name.setText(CommonContexts.clr.getEmpName());
		}
		return rootView;
	}

	private void SetEventLiseners() {
		mEnquiryCalendar.setOnClickListener(SlidingBaseDrawer.this);
		mBtnHome.setOnClickListener(SlidingBaseDrawer.this);
		mEnquiryFollowup.setOnClickListener(SlidingBaseDrawer.this);
		mNewEnquiry.setOnClickListener(SlidingBaseDrawer.this);
		mSearchEnquiry.setOnClickListener(SlidingBaseDrawer.this);
		mCallManagement.setOnClickListener(SlidingBaseDrawer.this);
		mLogout.setOnClickListener(SlidingBaseDrawer.this);
		mEnquiryCalendar.setOnClickListener(SlidingBaseDrawer.this);
		mSalesFunnel.setOnClickListener(SlidingBaseDrawer.this);
		mAdministration.setOnClickListener(SlidingBaseDrawer.this);
		mApproval.setOnClickListener(SlidingBaseDrawer.this);
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onStart() {
		super.onStart();

		SetEventLiseners();

		// SetMenuColors();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_slider_home:
			SelectedRID = R.id.btn_slider_home;
			Intent intent = new Intent(getActivity(), HomeView.class);
			startActivity(intent);
			break;
		case R.id.btn_slider_logout:
			SelectedRID = R.id.btn_slider_logout;

			final AlertDialog.Builder alert = new AlertDialog.Builder(
					getActivity(), AlertDialog.THEME_HOLO_LIGHT);
			alert.setTitle("Fourqt");
			alert.setMessage("Do you want to logout?");
			alert.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							CommonContexts.clr = null;
							Intent intent = new Intent(getActivity(),
									LoginView.class);
							startActivity(intent);
							getActivity().finish();
						}
					});

			alert.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
						}
					}).show();
			break;
		

		case R.id.btn_slider_enquiryfollowup:
			SelectedRID = R.id.btn_slider_enquiryfollowup;
			Intent enquiryfollowup = new Intent(getActivity(),
					EnquiryFollowupMainFr.class);
			startActivity(enquiryfollowup);
			break;
		case R.id.btn_slider_newenquiry:
			SelectedRID  = R.id.btn_slider_newenquiry;
			Intent newEnquiry = new Intent(getActivity(),CustomerDetails.class);
			startActivity(newEnquiry);
			
		case R.id.btn_slider_enquirycalendar:
			SelectedRID = R.id.btn_slider_enquirycalendar;
			Intent caldr = new Intent(getActivity(),CalendarViewApp.class);
			startActivity(caldr);
			
			break;
			
		case R.id.btn_slider_salesfunnel:
			SelectedRID = R.id.btn_slider_salesfunnel;
			Intent salesFunnel = new Intent(getActivity(),SalesFunnelView.class);
			startActivity(salesFunnel);
			break;
		case R.id.btn_slider_administration:
			SelectedRID = R.id.btn_slider_administration;
			Intent adminstration = new Intent(getActivity(),Administration.class);
			startActivity(adminstration);
			break;
		case R.id.btn_slider_approval:
			SelectedRID = R.id.btn_slider_approval;
			Intent approval = new Intent(getActivity(),ApprovalMainfr.class);
			startActivity(approval);
			break;
		case R.id.btn_slider_callmanagement:
			SelectedRID = R.id.btn_slider_approval;
			Intent callmanagement = new Intent(getActivity(),CallManagement.class);
			startActivity(callmanagement);
			break;
			
		case R.id.btn_slider_searchenquiry:
			SelectedRID = R.id.btn_slider_searchenquiry;
			Intent searchEnq = new Intent(getActivity(),SearchEnquiry.class);
			startActivity(searchEnq);

		}
	}
}
