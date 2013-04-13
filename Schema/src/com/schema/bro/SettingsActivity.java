package com.schema.bro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import com.schema.bro.ks.Customizer;
import com.schema.bro.ks.Schedule;

/** This class is for sure not compatible with Android < 11! */
public class SettingsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Customizer.setTheme(this);
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.settings);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			getActionBar().setDisplayHomeAsUpEnabled(true);

		Preference myPref = (Preference) findPreference("clear");
		myPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
					DialogFragment dialog = new ClearDialogFragment();
					dialog.show(getFragmentManager(), "Clear");
				}
				return false;
			}

		});
	}

	public static class ClearDialogFragment extends DialogFragment {
	    
		@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage("Är du säker på att du vill ta bort alla lektioner?")
	               .setPositiveButton("Rensa", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   	Schedule database = new Schedule(getActivity());
	       					database.clear();
	                   }
	               })
	               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User cancelled the dialog
	                   }
	               });
	        return builder.create();
	    }
	}
	
}
