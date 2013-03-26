package com.schema.bro;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;


public class AboutActivity extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		SharedPreferences mPrefs = getSharedPreferences("THEME", 0);
		int themeID = mPrefs.getInt("theme_int", 0);
		super.setTheme(themeID);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}

}
