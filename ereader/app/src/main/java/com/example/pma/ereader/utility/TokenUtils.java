package com.example.pma.ereader.utility;

import com.auth0.android.jwt.JWT;
import com.example.pma.ereader.MyApplication;

import android.preference.PreferenceManager;

public class TokenUtils {

	public static String getUsernameFromToken() {

		final String token = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext()).getString("TOKEN", "");
		if(token.contains("Bearer ")) {
			return new JWT(token.replace("Bearer ", "")).getSubject();
		}
		return token.replace("OFFLINE ", "");
	}

}
