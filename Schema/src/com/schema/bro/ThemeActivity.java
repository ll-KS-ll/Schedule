package com.schema.bro;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class ThemeActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	private int themeID, cardStyleID;
	private final static String THEME_ID_KEY = "theme_int";
	private final static String CARD_ID_KEY = "card_style_int";
	private SharedPreferences mPrefs;
	private OnSharedPreferenceChangeListener listener;
	
	/* Create a settings menu instead. Theme and card will be two ListPreferences.
	 * Way easier to handle! And still looks good.
	 * Put about in there too.
	 * 
	 *  Visual example
	 *  
	 *  <cat>Schema</cat>
	 *  	<listpref>Start activity</listpref>
	 *  	<checkpref>Notifikation</checkpref>
	 *  	<checkpref>Notifikations ljud</checkpref>
	 *  <cat>Utseende</cat>
	 *  	<listpref>App tema</listpref>
	 *  	<listpref>Kort layout</listpref>
	 *  	<listpref>Widget layout</listpref>
	 *  <cat>Om</cat>
	 *  	<pref>Skapare
	 *  		Filip Brolund
	 *  		Victor Karlsson Sehlin
	 *  		</pref>
	 *  	<pref>Version
	 *  		1.0
	 *  		</pref>
	 *  	<pref>Kontakt
	 *  		fbrolund@gmail.com
	 *  		karlssonsehlin@gmail.com
	 *  		</pref>
	*/
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		ThemeActivity.setTheme(this);
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.theme);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		themeID = mPrefs.getInt(THEME_ID_KEY, 0);
		cardStyleID = mPrefs.getInt(CARD_ID_KEY, 0);
	}

	@Override
	protected void onResume() {
		super.onResume();
		listener = this;
		mPrefs.registerOnSharedPreferenceChangeListener(listener);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mPrefs.unregisterOnSharedPreferenceChangeListener(listener);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if(key.equals("light_dark_actionbar")){
			themeID = R.style.LightDarkActionBar;
		}else if(key.equals("light")){
			themeID = R.style.Light;
		}else if(key.equals("dark")){
			themeID = R.style.Dark;
		}else if(key.equals("green")){
			themeID = R.style.Green;
		}else if(key.equals("red")){
			themeID = R.style.Red;
		}else if(key.equals("card_default")){
			cardStyleID = R.layout.card;
		}else if(key.equals("card_corner")){
			cardStyleID = R.layout.card2;
		}
		
		Editor mEditor = mPrefs.edit();
		mEditor.putInt(THEME_ID_KEY, themeID).commit();
		mEditor.putInt(CARD_ID_KEY, cardStyleID).commit();
	}

	/** Set the chosen app theme as theme for this activity
	 @param context - The activity to set theme on, usually this
	**/
	public static void setTheme(Context context){
		final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		final int themeID = mPrefs.getInt(THEME_ID_KEY, 0);
		context.setTheme(themeID);
	}
	
}
