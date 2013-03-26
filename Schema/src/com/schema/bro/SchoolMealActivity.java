package com.schema.bro;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;


public class SchoolMealActivity extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		SharedPreferences mPrefs = getSharedPreferences("THEME", 0);
		int themeID = mPrefs.getInt("theme_int", 0);
		super.setTheme(themeID);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.school_meal_activity);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		WebView mywebview = (WebView) findViewById(R.id.webview);
		mywebview.loadUrl("http://meny.dinskolmat.se/brogardsgymnasiet/");
		
		mywebview.getSettings().setJavaScriptEnabled(true);
		mywebview.setInitialScale(100);
	}

}
