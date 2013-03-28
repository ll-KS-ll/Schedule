package com.schema.bro.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.schema.bro.MainActivity;
import com.schema.bro.R;
import com.schema.bro.ks.Lesson;

public class LessonDialog extends DialogFragment implements OnClickListener{
	
	private View view;
	private TimePickerWidget startTimePicker, endTimePicker;
	private Spinner spinny;
	private int val = 0;
	private static final int[] imageIDs = { R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
			R.drawable.pic4, R.drawable.pic5, R.drawable.pic6, R.drawable.pic7,
			R.drawable.pic8, R.drawable.pic9, R.drawable.pic10,
			R.drawable.pic11, R.drawable.pic12, R.drawable.pic13,
			R.drawable.pic14, R.drawable.pic15, R.drawable.pic16,
			R.drawable.pic17, };
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.lesson_dialog, null);
		startTimePicker = (TimePickerWidget) view.findViewById(R.id.startTime);
		startTimePicker.init(8, 20);
		startTimePicker.setTitle("Startar");
		endTimePicker = (TimePickerWidget) view.findViewById(R.id.endTime);
		endTimePicker.init(8, 0);
		endTimePicker.setTitle("Slutar");
		
		final ImageButton nextBtn = (ImageButton) view.findViewById(R.id.nextImageButton);
		final ImageView prevBtn = (ImageView) view.findViewById(R.id.prevImageButton);
		nextBtn.setOnClickListener(this);
		prevBtn.setOnClickListener(this);
		
		spinny = (Spinner) view.findViewById(R.id.weekSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
			       this.getActivity(), R.array.weekdays, R.layout.spinner_item);
		spinny.setAdapter(adapter);
		spinny.setSelection(2); // Load current day
		
		if(getTag().equals("edit"))
			((TextView) view.findViewById(R.id.titleLessonDialog)).setText("Ändra lektion");
		else
			((TextView) view.findViewById(R.id.titleLessonDialog)).setText("Lägg till lektion");
		
		builder.setView(view)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								//mListener.onDialogPositiveClick(getTag(), hour, minute);
								addLesson();
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								//mListener.onDialogNegativeClick();
							}
						});

		return builder.create();
	}
	
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.prevImageButton:
			val--;
			if (val < 0)
				val = imageIDs.length - 1;
			break;
		case R.id.nextImageButton:
			val++;
			if (val > imageIDs.length - 1)
				val = 0;
			break;
		}
		((ImageView) view.findViewById(R.id.lessonImage)).setImageResource(imageIDs[val]);
	}
	
	public void addLesson(){
		final EditText edit_name = (EditText) view.findViewById(R.id.editName);
		final EditText edit_teacher = (EditText) view.findViewById(R.id.editTeacher);
		final EditText edit_room = (EditText) view.findViewById(R.id.editRoom);
		
		//String day = days[dayVal];
		String name = edit_name.getText().toString();
		String teacher = edit_teacher.getText().toString();
		String room = edit_room.getText().toString();
		String startTime = startTimePicker.getTime();
		String endTime = endTimePicker.getTime();
		String day = spinny.getSelectedItem().toString();
		
		if(name == null || name.equals(""))
			name = "Lektions namn";
		
		if(teacher == null || teacher.equals(""))
			teacher = " ";
		
		if(room == null || room.equals(""))
			room = " ";

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		int n = prefs.getInt("count", 0);
		
		String data = Lesson.convertToString(day, startTime, endTime, name, room, teacher, val, n);
		Log.v("LessonDialog:addLesson", "Lesson data: " + data);
		prefs.edit().putString("lesson_" + n, data).commit();
		prefs.edit().putInt("count", n + 1).commit();
		
		MainActivity activity = (MainActivity)getActivity();
		//activity.addLesson(data, dayVal); Not working yet
		activity.update();
	}

}
