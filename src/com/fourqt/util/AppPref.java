package com.fourqt.util;

import android.content.Context;

import java.util.HashSet;
import java.util.Set;


/**
 * Responsible for Application shared preference
 * @author vkumar
 *
 */
public class AppPref {
	private static final String APP_PREF = "APP_PREF";
	public static final String PROFILE = "PROFILE";
	public static final int CONTEXT_MODE = Context.MODE_ENABLE_WRITE_AHEAD_LOGGING;
	
	public static void updatePref(Context context, String pref, String value) {
		context.getSharedPreferences(APP_PREF, CONTEXT_MODE).edit()
				.putString(pref, value).commit();
	}

	public static void updatePref(Context context, String pref, int value) {
		context.getSharedPreferences(APP_PREF, CONTEXT_MODE).edit()
				.putInt(pref, value).commit();
	}
	
	public static void updatePref(Context context, String pref, boolean value) {
		context.getSharedPreferences(APP_PREF, CONTEXT_MODE).edit()
				.putBoolean(pref, value).commit();
	}
	public static void updatePref(Context context, String pref, HashSet<String> value) {
		context.getSharedPreferences(APP_PREF, CONTEXT_MODE).edit()
				.putStringSet(pref, value).commit();
	}
	public static boolean getPrefBoolean(Context context, String pref) {
		return context.getSharedPreferences(APP_PREF, CONTEXT_MODE)
				.getBoolean(pref, false);
	}
	public static int getPrefInt(Context context, String pref) {
		return context.getSharedPreferences(APP_PREF,CONTEXT_MODE)
				.getInt(pref, 0);
	}

	public static String getPref(Context context, String pref) {
		return context.getSharedPreferences(APP_PREF, CONTEXT_MODE)
				.getString(pref, null);
	}
	public static Set<String> getPrefSet(Context context, String pref) {
		return context.getSharedPreferences(APP_PREF, CONTEXT_MODE)
				.getStringSet(pref, null);
	}
}
