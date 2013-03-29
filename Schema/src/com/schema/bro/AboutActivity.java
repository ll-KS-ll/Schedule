package com.schema.bro;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class AboutActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ThemeActivity.setTheme(this);
		
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.about);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			getActionBar().setDisplayHomeAsUpEnabled(true);
	}

}
