package com.fourqt.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fourqt.view.R;
import com.viewpagerindicator.TabPageIndicator;

public class ConstructionApprovalFr extends Fragment {

	private Context mContext;
	private ViewPager mPager;
	private View mView;
	private ListView listView;
	int width,height;
	public static ConstructionApprovalFr newInstance(Context context, ViewPager pager,
			TabPageIndicator pageIndicatory) {
		ConstructionApprovalFr fragment = new ConstructionApprovalFr();
		fragment.mContext = context;
		fragment.mPager = pager;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mContext = getActivity();
		mView = inflater.inflate(R.layout.poapproval, null);
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		height = (metrics.heightPixels)/4;
		width = metrics.widthPixels;
		
		
		
		return mView;
	}
}
