package com.fourqt.servicerequest;

import org.json.JSONException;

/**
 * @author vkumar
 *
 */
public interface ServerCallback {
	  void doPostExecute(String serverResponse) throws JSONException;
	  void doPreExecute();
}
