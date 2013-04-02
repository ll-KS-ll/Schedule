package com.schema.bro.schoolmeal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.schema.bro.R;


public class SchoolMealFragment extends Fragment{

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.school_meal_fragment,
				container, false);

		final WebView mywebview = (WebView) rootView.findViewById(R.id.webview);
		mywebview.loadUrl("http://meny.dinskolmat.se/brogardsgymnasiet/");
		
		mywebview.getSettings().setJavaScriptEnabled(true);
		mywebview.setInitialScale(100);

		return rootView;
	}
	
}
