package com.schema.bro;

import android.app.Activity;
import android.os.Bundle;


public class ThemeActivity extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.theme_activity);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}

}
