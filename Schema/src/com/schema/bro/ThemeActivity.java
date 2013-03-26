package com.schema.bro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ThemeActivity extends Activity implements OnItemClickListener {

	List<Map<String, String>> themeList = new ArrayList<Map<String, String>>();
	private int themeID;

	protected void onCreate(Bundle savedInstanceState) {
		SharedPreferences mPrefs = getSharedPreferences("THEME", 0);
		themeID = mPrefs.getInt("theme_int", 0);
		super.setTheme(themeID);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.theme_activity);

		initList();

		ListView lv = (ListView) findViewById(R.id.themeList);

		SimpleAdapter simpleAdpt = new SimpleAdapter(this, themeList,
				android.R.layout.simple_list_item_1, new String[] { "theme" },
				new int[] { android.R.id.text1 });

		lv.setAdapter(simpleAdpt);
		lv.setOnItemClickListener(this);
	}

	private void initList() {
		// We populate the classes

		themeList.add(createClass("theme", "Holo Dark"));
		themeList.add(createClass("theme", "Holo Light"));
		themeList.add(createClass("theme", "Mobilens tema"));

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
			themeID = android.R.style.Theme_Holo_Light_DarkActionBar;
			break;
		case 1:
			themeID = android.R.style.Theme_Holo_Light;
			break;
		case 2:
			themeID = android.R.style.Theme;
			break;
		}
		SharedPreferences mPrefs = getSharedPreferences("THEME", 0);
		SharedPreferences.Editor mEditor = mPrefs.edit();
		mEditor.putInt("theme_int", themeID).commit();

		Intent i = getBaseContext().getPackageManager()
				.getLaunchIntentForPackage(getBaseContext().getPackageName());
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}
