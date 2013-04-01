package com.schema.bro;

import com.schema.bro.ks.Customizer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


public class SchoolMealActivity extends Activity{

	@SuppressLint("SetJavaScriptEnabled")
	protected void onCreate(Bundle savedInstanceState) {
		Customizer.setTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.school_meal_activity);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		WebView mywebview = (WebView) findViewById(R.id.webview);
		mywebview.loadUrl("http://meny.dinskolmat.se/brogardsgymnasiet/");
		
		mywebview.getSettings().setJavaScriptEnabled(true);
		mywebview.setInitialScale(100);
	}

}
