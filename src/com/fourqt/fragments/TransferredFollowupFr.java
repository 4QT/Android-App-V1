package com.fourqt.fragments;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fourqt.fragments.TodayFollowUpFr.TodayFollowUpAdapter;
import com.fourqt.model.GetAllLeadListResult;
import com.fourqt.util.CommonContexts;
import com.fourqt.view.R;
import com.viewpagerindicator.TabPageIndicator;

public class TransferredFollowupFr extends Fragment {

	private Context mContext;
	private ViewPager mPager;
	private View mView;
	private ListView listView;
	int width, height;
	private List<GetAllLeadListResult> mListFollowup= new ArrayList<GetAllLeadListResult>();
	public static TransferredFollowupFr newInstance(Context context,
			ViewPager pager, TabPageIndicator pageIndicatory) {
		TransferredFollowupFr fragment = new TransferredFollowupFr();
		fragment.mContext = context;
		fragment.mPager = pager;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mContext = getActivity();
		mView = inflater.inflate(R.layout.today_followup_fr, null);
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		height = (metrics.heightPixels) / 4;
		width = metrics.widthPixels;

		listView = (ListView) mView.findViewById(R.id.list_todayfollowup);

		for(GetAllLeadListResult list : CommonContexts.listFollowup)
		{
			if(list.getStatus().equalsIgnoreCase("Today"))
				mListFollowup.add(list);
		}
		
		if(mListFollowup != null && mListFollowup.size()>0)
		{
		TodayFollowUpAdapter sa = new TodayFollowUpAdapter(mContext,
				mListFollowup);
		listView.setAdapter(sa);
		}
		return mView;
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
			mHeight = height;
			mWidth = width;
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
			LinearLayout lin_layout;
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

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.mUserName.setText(mDisplayedListEn.get(position)
					.getCustomerName());
			holder.mDate.setText(mDisplayedListEn.get(position)
					.getEnquiryDate());
			holder.mDescr.setText(mDisplayedListEn.get(position).getEmailID());
			return convertView;
		}

	}

}
