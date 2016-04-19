package com.fourqt.main;


import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.fourqt.slidingmenu.lib.app.SlidingFragmentActivity;
import com.fourqt.slidingmenu.lib.view.SlidingMenu;
import com.fourqt.view.R;




public class BaseActivity extends SlidingFragmentActivity {

	private int mTitleRes;

	public BaseActivity(int titleRes) {
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		setBehindContentView(R.layout.base_activity);
		
		setTitle(mTitleRes);
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	}
	
	

}
