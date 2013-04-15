package com.schema.bro.ks;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.schema.bro.R;

/** Fancy class for handling all app customization*/
public class Customizer {

	/**
	 * Set the chosen app theme as theme for this activity
	 * @param context - The activity to set theme on, usually this
	 **/
	public static void setTheme(Context context) {
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		int themeID = Integer.parseInt(prefs.getString("app_theme", "0"));

		switch (themeID) {
		case 0:
			themeID = R.style.LightDarkActionBar;
			break;
		case 1:
			themeID = R.style.Light;
			break;
		case 2:
			themeID = R.style.Dark;
			break;
		case 3:
			themeID = R.style.Green;
			break;
		case 4:
			themeID = R.style.Red;
			break;
		}

		context.setTheme(themeID);
	}

	/**
	 * Set the chosen card style for these cards
	 * @param context - The context of the card
	 **/
	public static int getCardStyle(Context context){
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		final int cardID = Integer.parseInt(prefs.getString("card_layout", "0"));

		switch (cardID) {
		case 0:
			return R.layout.card;
		case 1:
			return R.layout.card2;
		default: 
				return R.layout.card;
		}
		
	}
	
	/**<b>Not done yet!</b>
	 * <p>
	 * Set the chosen card style for these cards
	 * @param context - Some fancy context
	 **/
	public static int getWidgetStyle(Context context){
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		final int widgetID = Integer.parseInt(prefs.getString("widget_layout", "0"));

		switch (widgetID) {
		case 0:
			// Change to widget layout resource
			return R.layout.card;
		case 1:
			// Change to widget layout resource
			return R.layout.card2;
		default: 
			// Change to widget layout resource	
			return R.layout.card;
		}
		
	}
}
