package com.schema.bro.ks;

import java.text.DecimalFormat;

import com.schema.bro.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

public class TimePickerWidget extends LinearLayout{

	private NumberPicker hourPicker, minutePicker;
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
	
	public TimePickerWidget(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.time_picker_widget, this, true);
	}
	
	public TimePickerWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.time_picker_widget, this, true);
	}
	
	public TimePickerWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.time_picker_widget, this, true);
	}
	
	public void init(int hour, int minute){
		hourPicker = (NumberPicker) findViewById(R.id.time_pick_hour);
		minutePicker = (NumberPicker) findViewById(R.id.time_pick_minute);
		
		hourPicker.setMinValue(6);
		hourPicker.setMaxValue(18);
		hourPicker.setValue(hour);
		hourPicker.setDisplayedValues(displayHour);
		
		minutePicker.setMinValue(0);
		minutePicker.setMaxValue(displayMinutes.length-1);
		String min = String.valueOf(minute);
		for( int i=0; i<displayMinutes.length ; i++ )
		    if(displayMinutes[i].equals(min))
		    	minutePicker.setValue(i);
		minutePicker.setDisplayedValues(displayMinutes);
	}
	
	public void setTitle(String title){
		((TextView) findViewById(R.id.titleTimePicker)).setText(title);
	}
	
	public String getTime(){
		DecimalFormat formatter = new DecimalFormat("00");
		int minutes = Integer.parseInt(displayMinutes[minutePicker.getValue()]);
		String time = formatter.format(hourPicker.getValue()) + ":" + formatter.format(minutes);
		return time;
	}

}
