package com.schema.bro;

import com.schema.bro.ks.Lesson;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TimePicker;

public class LessonActivity extends Activity implements OnTimeSetListener {

	private ListView list;
	private final String[] matrix = { "_id", "name", "value" };
	private final String[] columns = { "name", "value" };
	private final int[] layouts = { R.id.text1, R.id.text2 };
	private SimpleCursorAdapter data;
	private MatrixCursor cursor;
	private EditText edit_name;
	@SuppressWarnings("unused")
	private static final String TAG = "DialogActivity";
	private static final int DIALOG1 = 0, DIALOG2 = 1, DIALOG3 = 2;
	private static final int TEXT_ID = 0;
	private boolean isStartTime = false;

	String room, teacher, startTime, endTime, otherInfo;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lesson_activity);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		startTime = endTime = "08:20";
		room = teacher = "";

		cursor = new MatrixCursor(matrix);
		cursor.addRow(new Object[] { 0, "Startar:", startTime });
		cursor.addRow(new Object[] { 1, "Slutar:", endTime });
		cursor.addRow(new Object[] { 2, "Sal:", room });
		cursor.addRow(new Object[] { 3, "Lärare:", teacher });
		cursor.addRow(new Object[] { 4, "Övrigt:", otherInfo });

		data = new SimpleCursorAdapter(this, R.layout.two_item_list_item,
				cursor, columns, layouts, 0);

		list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(data);
		list.setOnItemClickListener(onListClick);
		edit_name = (EditText) findViewById(R.id.editText1);
	}

	public void UpdateListView() {

		cursor.close();
		cursor = null;

		cursor = new MatrixCursor(matrix);
		cursor.addRow(new Object[] { 0, "Startar:", startTime });
		cursor.addRow(new Object[] { 1, "Slutar:", endTime });
		cursor.addRow(new Object[] { 2, "Sal:", room });
		cursor.addRow(new Object[] { 3, "Lärare:", teacher });
		cursor.addRow(new Object[] { 4, "Övrigt:", otherInfo });

		data.changeCursor(cursor);

	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		if (isStartTime == true)
			startTime = String.valueOf(hourOfDay + ":" + minute);
		else
			endTime = String.valueOf(hourOfDay + ":" + minute);
		UpdateListView();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			String name = edit_name.getText().toString();
			String data = Lesson.convertToString("Måndag", startTime, endTime, name, room, teacher);
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			int n = prefs.getInt("count", 0) + 1;
			prefs.edit().putString("lesson_" + n, data).commit();
			prefs.edit().putInt("count", n).commit();
			finish();
			break;
		case R.id.button2:
			finish();
			break;
		}
	}

	private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
		@SuppressWarnings("deprecation")
		public void onItemClick(AdapterView<?> parent, View view, int pos,
				long id) {

			switch (pos) {
			case 0:
				isStartTime = true;
				DialogFragment newFragment1 = new TimePickerFragment();
				newFragment1.show(getFragmentManager(), "timePicker");
				break;
			case 1:
				isStartTime = false;
				DialogFragment newFragment2 = new TimePickerFragment();
				newFragment2.show(getFragmentManager(), "timePicker");
				break;
			case 2:
				showDialog(DIALOG1);
				break;
			case 3:
				showDialog(DIALOG2);
				break;
			case 4:
				showDialog(DIALOG3);
				break;
			}
		}
	};

	/**
	 * Called to create a dialog to be shown.
	 */
	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DIALOG1:
			return createExampleDialog("Ange salens namn:", DIALOG1);
		case DIALOG2:
			return createExampleDialog("Ange lärarens namn:", DIALOG2);
		case DIALOG3:
			return createExampleDialog("Övrig information:", DIALOG3);
		default:
			return null;
		}
	}

	/**
	 * If a dialog has already been created, this is called to reset the dialog
	 * before showing it a 2nd time. Optional.
	 */
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {

		// Clear the input box.
		EditText text = (EditText) dialog.findViewById(TEXT_ID);
		text.setText("");
	}

	/**
	 * Create and return an example alert dialog with an edit text box.
	 */
	private Dialog createExampleDialog(String message, final int id) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setTitle("Hello User");
		builder.setMessage(message);

		// Use an EditText view to get user input.
		final EditText input = new EditText(this);
		input.setId(TEXT_ID);
		input.setSingleLine();
		input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });
		builder.setView(input);

		builder.setPositiveButton("Klar",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						if (id == DIALOG1)
							room = input.getText().toString();
						if (id == DIALOG2)
							teacher = input.getText().toString();
						if (id == DIALOG3)
							otherInfo = input.getText().toString();
						UpdateListView();
						return;
					}
				});

		builder.setNegativeButton("Avbryt",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});

		return builder.create();
	}

}