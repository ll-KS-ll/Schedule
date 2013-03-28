package com.schema.bro;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class ThemeActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		SharedPreferences mPrefs = getSharedPreferences("THEME", 0);
		int themeID = mPrefs.getInt("theme_int", 0);
		super.setTheme(themeID);

		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.theme);
		
		Context context = getApplicationContext();
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings.registerOnSharedPreferenceChangeListener(this);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Log.d("Theme", "click");
		
	}

}
/*
 * public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
 * switch (pos) { case 0: themeID =
 * android.R.style.Theme_Holo_Light_DarkActionBar; break; case 1: themeID =
 * android.R.style.Theme_Holo_Light; break; case 2: themeID =
 * android.R.style.Theme; break; case 3: cardStyleID = R.layout.card; break;
 * case 4: cardStyleID = R.layout.card2; break; } SharedPreferences mPrefs =
 * getSharedPreferences("THEME", 0); SharedPreferences.Editor mEditor =
 * mPrefs.edit(); mEditor.putInt("theme_int", themeID).commit();
 * mEditor.putInt("card_style_int", cardStyleID).commit();
 * 
 * Intent i = getBaseContext().getPackageManager()
 * .getLaunchIntentForPackage(getBaseContext().getPackageName());
 * i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(i); }
 */
