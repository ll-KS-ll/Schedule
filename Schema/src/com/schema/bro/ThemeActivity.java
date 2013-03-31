package com.schema.bro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ThemeActivity extends Activity implements OnItemClickListener {

	List<Map<String, String>> themeList = new ArrayList<Map<String, String>>();
	private int themeID, cardStyleID;
	private final static String THEME_ID_KEY = "theme_int";
	private final static String CARD_ID_KEY = "card_style_int";
	private final static String PREFS_KEY_THEME = "THEME";
	private SharedPreferences mPrefs;
	
	protected void onCreate(Bundle savedInstanceState) {
		ThemeActivity.setTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.theme_activity);

		mPrefs = getSharedPreferences(PREFS_KEY_THEME, 0);
		initList();
		final ListView lv = (ListView) findViewById(R.id.themeList);

		SimpleAdapter simpleAdpt = new SimpleAdapter(this, themeList,
				android.R.layout.simple_list_item_1, new String[] { "theme" },
				new int[] { android.R.id.text1 });
		lv.setAdapter(simpleAdpt);
		lv.setOnItemClickListener(this);
	}

	private void initList() {
		// We populate the classes
		themeList.add(createClass("theme", "Light with Dark ActionBar  (Tema)"));
		themeList.add(createClass("theme", "Light  (Tema)"));
		themeList.add(createClass("theme", "Dark  (Tema)"));
		themeList.add(createClass("theme", "Green  (Tema)"));
		themeList.add(createClass("theme", "Red  (Tema)"));
		themeList.add(createClass("theme", "Standard  (Kort layout)"));
		themeList.add(createClass("theme", "Tid i hörn  (Kort layout)"));
	}

	private HashMap<String, String> createClass(String key, String name) {
		HashMap<String, String> className = new HashMap<String, String>();
		className.put(key, name);
		return className;
	}

	public void onItemClick(AdapterView<?> parent, View view, int pos,
			long id) {
		switch (pos) {
		case 0:
			//themeID = android.R.style.Theme_Holo_Light_DarkActionBar;
			themeID = R.style.LightDarkActionBar;
			break;
		case 1:
			//themeID = android.R.style.Theme_Holo_Light;
			themeID = R.style.Light;
			break;
		case 2:
			//themeID = android.R.style.Theme;
			themeID = R.style.Dark;
			break;
		case 3:
			//themeID = android.R.style.Theme;
			themeID = R.style.Green;
			break;
		case 4:
			//themeID = android.R.style.Theme;
			themeID = R.style.Red;
			break;
		case 5:
			cardStyleID = R.layout.card;
			break;
		case 6:
			cardStyleID = R.layout.card2;
			break;
		}
		
		Editor mEditor = mPrefs.edit();
		mEditor.putInt(THEME_ID_KEY, themeID).commit();
		mEditor.putInt(CARD_ID_KEY, cardStyleID).commit();
		
		Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
	/** Set the chosen app theme as theme for this activity
	 @param context - The activity to set theme on, usually this
	**/
	public static void setTheme(Context context){
		final SharedPreferences mPrefs = context.getSharedPreferences(PREFS_KEY_THEME, 0);
		final int themeID = mPrefs.getInt(THEME_ID_KEY, 0);
		context.setTheme(themeID);
	}
}
