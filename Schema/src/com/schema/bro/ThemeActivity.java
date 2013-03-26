package com.schema.bro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ThemeActivity extends Activity implements OnItemClickListener {

	List<Map<String, String>> themeList = new ArrayList<Map<String, String>>();

	protected void onCreate(Bundle savedInstanceState) {
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
		themeList.add(createClass("theme", "ANA"));

	}

	private HashMap<String, String> createClass(String key, String name) {
		HashMap<String, String> className = new HashMap<String, String>();
		className.put(key, name);

		return className;
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}
}
