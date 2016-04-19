package com.fourqt.main;

import android.os.Bundle;

import com.fourqt.view.R;


public class RootActivity extends BaseActivity {

	public RootActivity() {
		super(R.string.app_name);
	}
	 int onStartCount = 0;

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        onStartCount = 1;
	        if (savedInstanceState == null) // 1st time
	        {
	            this.overridePendingTransition(R.anim.anim_slide_in_left,
	                    R.anim.anim_slide_out_left);
	        } else // already created so reverse animation
	        { 
	            onStartCount = 2;
	        }
	    }

	    @Override
	    protected void onStart() {
	        super.onStart();
	        if (onStartCount > 1) {
	            this.overridePendingTransition(R.anim.anim_slide_in_right,
	                    R.anim.anim_slide_out_right);

	        } else if (onStartCount == 1) {
	            onStartCount++;
	        }

	    }
}
