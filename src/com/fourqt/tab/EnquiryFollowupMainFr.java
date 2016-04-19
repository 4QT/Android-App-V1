package com.fourqt.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.fourqt.fragments.FutureFollowupFr;
import com.fourqt.fragments.NewFollowupFr;
import com.fourqt.fragments.PendingFollowupFr;
import com.fourqt.fragments.TodayFollowUpFr;
import com.fourqt.fragments.TransferredFollowupFr;
import com.fourqt.main.BaseActivity;
import com.fourqt.main.RootActivity;
import com.fourqt.view.R;
import com.viewpagerindicator.TabPageIndicator;

public class EnquiryFollowupMainFr extends RootActivity {

	public EnquiryFollowupMainFr() {
		super();
	}

	private ViewPager pager;
	private TabPageIndicator indicator;

	private static final String[] CONTENT = new String[] {"Today   ","New    ","Transferred","Pending","Future"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_tabs);

		
		Button mBtnLeft = (Button) findViewById(R.id.btn_left);
		mBtnLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggle();
			}
		});
		
		FragmentPagerAdapter adapter = new FollowUpBasedapter(
				getSupportFragmentManager());

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);

	}

	

	class FollowUpBasedapter extends FragmentPagerAdapter {
		public FollowUpBasedapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return TodayFollowUpFr.newInstance(getApplicationContext(),
						pager, indicator);
			case 1:
				return NewFollowupFr.newInstance(getApplicationContext(),
						pager, indicator);
			case 2:
				return TransferredFollowupFr.newInstance(getApplicationContext(),
						pager, indicator);
			case 3:
				return PendingFollowupFr.newInstance(getApplicationContext(),
						pager, indicator);
			case 4:
				return FutureFollowupFr.newInstance(getApplicationContext(),
						pager, indicator);
			
			
			default:
				return TodayFollowUpFr.newInstance(getApplicationContext(),
						pager, indicator);
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length];
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}
	}

}