package com.schema.bro;

import java.text.DecimalFormat;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TimePicker;

import com.schema.bro.ks.Lesson;

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
	private static final int DIALOG1 = 0, DIALOG2 = 1;
	private static final int TEXT_ID = 0;
	private boolean isStartTime = false;
	private ImageView image;
	private int val = 0;
	private String day;
	private AlertDialog dayDialog;

	int[] imageIDs = { R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
			R.drawable.pic4, R.drawable.pic5, R.drawable.pic6, R.drawable.pic7,
			R.drawable.pic8, R.drawable.pic9, R.drawable.pic10,
			R.drawable.pic11, R.drawable.pic12, R.drawable.pic13,
			R.drawable.pic14, R.drawable.pic15, R.drawable.pic16,
			R.drawable.pic17, };

	private String[] items = { "Måndag", "Tisdag", "Onsdag", "Torsdag",
			"Fredag" };

	String room, teacher, startTime, endTime;

	protected void onCreate(Bundle savedInstanceState) {
		SharedPreferences mPrefs = getSharedPreferences("THEME", 0);
		int themeID = mPrefs.getInt("theme_boolean", 0);
		super.setTheme(themeID);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lesson_activity);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		startTime = endTime = "08:00";
		room = teacher = " ";
		day = "Måndag";

		cursor = new MatrixCursor(matrix);
		cursor.addRow(new Object[] { 0, "Dag:", day });
		cursor.addRow(new Object[] { 1, "Startar:", startTime });
		cursor.addRow(new Object[] { 2, "Slutar:", endTime });
		cursor.addRow(new Object[] { 3, "Sal:", room });
		cursor.addRow(new Object[] { 4, "Lärare:", teacher });

		data = new SimpleCursorAdapter(this, R.layout.two_item_list_item,
				cursor, columns, layouts, 0);

		list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(data);
		list.setOnItemClickListener(onListClick);
		list.setScrollContainer(false);
		edit_name = (EditText) findViewById(R.id.lessonText);

		image = (ImageView) findViewById(R.id.lessonImage);
		image.setImageResource(imageIDs[val]);

		AlertDialog.Builder builder = new AlertDialog.Builder(
				LessonActivity.this);
		builder.setTitle("Ange dag");
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				day = items[which];
				UpdateListView();
			}
		});
		builder.setCancelable(false);
		dayDialog = builder.create();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.done, menu);
		return true;
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
		image.setImageResource(imageIDs[val]);
	}

	public void UpdateListView() {

		cursor.close();
		cursor = null;

		cursor = new MatrixCursor(matrix);
		cursor.addRow(new Object[] { 0, "Dag:", day });
		cursor.addRow(new Object[] { 1, "Startar:", startTime });
		cursor.addRow(new Object[] { 2, "Slutar:", endTime });
		cursor.addRow(new Object[] { 3, "Sal:", room });
		cursor.addRow(new Object[] { 4, "Lärare:", teacher });

		data.changeCursor(cursor);

	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		DecimalFormat formatter = new DecimalFormat("00");
		if (isStartTime == true)
			startTime = formatter.format(hourOfDay) + ":"
					+ formatter.format(minute);
		else
			endTime = formatter.format(hourOfDay) + ":"
					+ formatter.format(minute);
		UpdateListView();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.done:
			saveLesson();
			finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	public void saveLesson() {
		String name = edit_name.getText().toString();
		String data = Lesson.convertToString(day, startTime, endTime,
				name, room, teacher, val);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		int n = prefs.getInt("count", 0);
		prefs.edit().putString("lesson_" + n, data).commit();
		prefs.edit().putInt("count", n + 1).commit();
		Log.d("LessonACtivity", "Count: " + n + 1);
		Log.d("LessonACtivity", "Lesson: " + data);
	}

	private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
		@SuppressWarnings("deprecation")
		public void onItemClick(AdapterView<?> parent, View view, int pos,
				long id) {

			switch (pos) {
 			case 0:
				dayDialog.show();
 				break;
 			case 1:
				isStartTime = true;
				DialogFragment startTimeFragement = new TimePickerFragment();
				startTimeFragement.show(getFragmentManager(), "timePicker");
 				break;
 			case 2:
				isStartTime = false;
				DialogFragment endTimeFragement = new TimePickerFragment();
				endTimeFragement.show(getFragmentManager(), "timePicker");
 				break;
 			case 3:
				showDialog(DIALOG1);
				break;
			case 4:
 				showDialog(DIALOG2);
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
		text.setText(" ");
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
		input.setText(" ");

		builder.setView(input);
		builder.setPositiveButton("Klar",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						if (id == DIALOG1)
							room = input.getText().toString();
						else if (id == DIALOG2)
							teacher = input.getText().toString();
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
