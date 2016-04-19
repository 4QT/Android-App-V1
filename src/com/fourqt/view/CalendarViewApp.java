package com.fourqt.view;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fourqt.main.RootActivity;
import com.fourqt.model.GetAllLeadListResult;
import com.fourqt.util.CommonContexts;
import com.fourqt.view.R;

public class CalendarViewApp extends RootActivity {

	public CalendarViewApp() {
		super();
	}

	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_calendar);

		Button mBtnLeft = (Button) findViewById(R.id.btn_left);
		mBtnLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggle();
			}
		});
		
		HashSet<Date> events = new HashSet<Date>();
		events.add(new Date());

		CalendarView cv = ((CalendarView) findViewById(R.id.calendar_view));
		cv.updateCalendar(events);

		// assign event handler
		cv.setEventHandler(new CalendarView.EventHandler() {
			@Override
			public void onDayLongPress(Date date) {

			}
		});

		listView = (ListView) findViewById(R.id.list_calendar);
		TodayFollowUpAdapter sa = new TodayFollowUpAdapter(
				CalendarViewApp.this, CommonContexts.listFollowup);
		listView.setAdapter(sa);
	}

	class TodayFollowUpAdapter extends BaseAdapter {
		private Context mContext;
		private LayoutInflater mLayoutInflater;
		private List<GetAllLeadListResult> mDisplayedListEn;
		private View mView;
		int mHeight, mWidth;
		private boolean isLanguageEng = true;

		public TodayFollowUpAdapter(Context context,
				List<GetAllLeadListResult> mList) {
			mContext = context;
			mDisplayedListEn = mList;
			mLayoutInflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			return mDisplayedListEn.size();
		}

		@Override
		public Object getItem(int position) {
			return mDisplayedListEn.get(position);
		}

		@Override
		public long getItemId(int position) {

			return 0;
		}

		private class ViewHolder {
			TextView mUserName, mDescr, mDate;
			RelativeLayout relative_layout;
			ImageView mFollowup, mCall, mEmail, mSms;
		}

		@SuppressLint("ResourceAsColor")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = new View(mContext);
				convertView = mLayoutInflater.inflate(
						R.layout.today_followup_adapter, null);
				holder = new ViewHolder();
				holder.mUserName = (TextView) convertView
						.findViewById(R.id.txv_followup_name);
				holder.mDescr = (TextView) convertView
						.findViewById(R.id.txv_followup_desc);
				holder.mDate = (TextView) convertView
						.findViewById(R.id.txv_followup_date);

				holder.mFollowup = (ImageView) convertView
						.findViewById(R.id.img_followup_folloup);
				holder.mCall = (ImageView) convertView
						.findViewById(R.id.img_followup_call);
				holder.mEmail = (ImageView) convertView
						.findViewById(R.id.img_followup_email);
				holder.mSms = (ImageView) convertView
						.findViewById(R.id.img_followup_sms);

				holder.relative_layout = (RelativeLayout) convertView
						.findViewById(R.id.relative_layout);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.mUserName.setText(mDisplayedListEn.get(position)
					.getCustomerName());
			holder.mDate.setText(mDisplayedListEn.get(position)
					.getEnquiryDate());
			holder.mDescr.setText(mDisplayedListEn.get(position).getEmailID());

			// holder.relative_layout.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View arg0) {
			// Intent intent = new Intent(mContext,CustomerDetails.class);
			// intent.putExtra("details",
			// GsonSingleton.getInstance().toJson(mDisplayedListEn.get(position)));
			// startActivity(intent);
			// }
			// });

			holder.mCall.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri
							.fromParts("tel", mDisplayedListEn.get(position)
									.getMobileNo(), null));
					startActivity(intent);

				}
			});

			holder.mSms.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, SmsView.class);
					startActivity(intent);
				}
			});
			holder.mFollowup.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, FollowupDetails.class);
					startActivity(intent);

				}
			});
			return convertView;
		}

	}
}
