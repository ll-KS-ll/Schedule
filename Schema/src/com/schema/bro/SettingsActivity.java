package com.schema.bro;

import com.schema.bro.ks.Customizer;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity{

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Customizer.setTheme(this);
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.settings);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	
}
