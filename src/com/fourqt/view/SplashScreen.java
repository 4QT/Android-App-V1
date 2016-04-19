package com.fourqt.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fourqt.util.CommonContexts;
import com.fourqt.view.R;


public class SplashScreen extends Activity {
	@SuppressWarnings("unused")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);


		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {

				} catch (Exception e) {
				} finally {
					if (CommonContexts.clr != null && CommonContexts.clr.getLoginID()!= null) {
						Intent intent = new Intent(getApplicationContext(),
								HomeView.class);
						startActivity(intent);
						finish();
					} else {
						
						Intent intent = new Intent(getApplicationContext(),
								LoginView.class);
						startActivity(intent);
						finish();
					}

				}
			}
		};
		timer.start();
	}

}
