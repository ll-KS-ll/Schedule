package com.schema.bro.dialog;

import com.schema.bro.LessonActivity;
import com.schema.bro.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

public class CustomTimePicker extends DialogFragment implements OnValueChangeListener{

	private int hour, minute;
	private NoticeDialogListener mListener;
	private static final String[] displayMinutes = {
		"00", "05", "10", "15", 
		"20", "25", "30", "35", 
		"40", "45", "50", "55"
	};
	private static final String[] displayHour = {
		"06", "07", "08", "09", "10", 
		"11", "12", "13", "14", "15", 
		"16", "17", "18"
	};
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.time_picker, null);
		NumberPicker hourPicker = (NumberPicker) view.findViewById(R.id.time_pick_hour);
		NumberPicker minutePicker = (NumberPicker) view.findViewById(R.id.time_pick_minute);
		
		LessonActivity parentActivity = (LessonActivity)getActivity();
		
		hourPicker.setMinValue(6);
		hourPicker.setMaxValue(18);
		hour = parentActivity.getHour(getTag());
		hourPicker.setValue(hour);
		hourPicker.setDisplayedValues(displayHour);
		hourPicker.setOnValueChangedListener(this);
		
		minutePicker.setMinValue(0);
		minutePicker.setMaxValue(displayMinutes.length-1);
		minute = parentActivity.getMinute(getTag());
		String min = String.valueOf(minute);
		for( int i=0; i<displayMinutes.length ; i++ )
		    if(displayMinutes[i].equals(min))
		    	minutePicker.setValue(i);
		minutePicker.setDisplayedValues(displayMinutes);
		minutePicker.setOnValueChangedListener(this);
		
	
		
		String title;
		if(getTag().equals("start"))
			title = "Ange start tid";
		else
			title = "Ange slut tid";
		
		builder.setTitle(title).setView(view)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								mListener.onDialogPositiveClick(getTag(), hour, minute);
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								mListener.onDialogNegativeClick();
							}
						});

		return builder.create();
	}
	
	/*
	 * The activity that creates an instance of this dialog fragment must
	 * implement this interface in order to receive event callbacks. Each method
	 * passes the DialogFragment in case the host needs to query it.
	 */
	public interface NoticeDialogListener {
		public void onDialogPositiveClick(String tag, int hour, int minutes);
		public void onDialogNegativeClick();
	}

	// Override the Fragment.onAttach() method to instantiate the
	// NoticeDialogListener
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the
			// host
			mListener = (NoticeDialogListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		switch(picker.getId()){
		case R.id.time_pick_hour:
			hour = newVal;
			break;
		case R.id.time_pick_minute:
			minute = Integer.parseInt(displayMinutes[newVal]);
			break;
		default:
			Log.d("TimePickerFragment:onValueChange", "Picker has wrong ID");
		}
	}

	
}

