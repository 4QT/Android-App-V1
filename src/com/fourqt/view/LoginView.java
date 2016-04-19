package com.fourqt.view;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fourqt.model.CheckLoginResult;
import com.fourqt.servicerequest.AsyncOperation;
import com.fourqt.servicerequest.ServerCallback;
import com.fourqt.util.CommonContexts;
import com.fourqt.util.GsonSingleton;
import com.fourqt.util.SystemUtil;
import com.fourqt.view.R;

public class LoginView extends Activity implements ServerCallback {
	
	private EditText edtKey,edtUserName,edtPassword;
	private ImageView imgGo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		edtKey = (EditText)findViewById(R.id.et_key);
		edtUserName = (EditText)findViewById(R.id.et_user);
		edtPassword = (EditText)findViewById(R.id.et_password);
		
		edtKey.setText("slead");
		edtUserName.setText("rajeev.gupta");
		edtPassword.setText("fourqt");
		
		imgGo = (ImageView)findViewById(R.id.gobutton);
		imgGo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(validation())
				{
					String url = ""+getString(R.string.service_login) + "Token=slead&UID="+edtUserName.getText().toString()+"&PWD="+edtPassword.getText().toString()+"";
					CallServer(url);
				}
				
			}
		});
		
	}
	private void CallServer( String url) {
		if (SystemUtil.haveNetworkConnection(LoginView.this)) {
			AsyncOperation as = new AsyncOperation(this, CommonContexts.CONNECTION_GET);
			as.execute(url);
			
		} else {
			Toast.makeText(LoginView.this, R.string.no_network, Toast.LENGTH_SHORT)
					.show();
		}
	}
	 private boolean validation() {
	        boolean value = false;
	        if (edtKey.getText().toString().isEmpty()
	                && edtUserName.getText().toString().isEmpty()
	                && edtPassword.getText().toString().isEmpty()) {
	            Toast.makeText(LoginView.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
	            return false;

	        } else if (edtKey.getText().toString().isEmpty()) {
	            Toast.makeText(LoginView.this, "Please enter Key Number", Toast.LENGTH_SHORT).show();
	            return false;

	        } else if (edtUserName.getText().toString().isEmpty()) {
	            Toast.makeText(LoginView.this, "Please enter User Name", Toast.LENGTH_SHORT).show();
	            return false;

	        } else if (edtPassword.getText().toString().isEmpty()) {
	            Toast.makeText(LoginView.this, "Please enter password", Toast.LENGTH_SHORT).show();
	            return false;
	        } else
	        
	            return true;
	            
	        

	       
	    }
	@Override
	public void doPostExecute(String serverResponse) throws JSONException {
		CommonContexts.dismissProgressDialog();
		if(serverResponse != null)
		{
			JSONObject jObject = new JSONObject(serverResponse);
			if(jObject.has("CheckLoginResult"))
			{
				CheckLoginResult clr = GsonSingleton.getInstance().fromJson(jObject.getString("CheckLoginResult"),CheckLoginResult.class);
				CommonContexts.clr = clr;
				CommonContexts.appkey = edtKey.getText().toString();
				if(clr != null && clr.getLoginID()!= null)
				{
					Intent intent = new Intent(LoginView.this, HomeView.class);
					startActivity(intent);
					finish();
				}
			}
		}
	}
	@Override
	public void doPreExecute() {
		CommonContexts.showProgress(LoginView.this, "Please wait....");
		
	}

}
