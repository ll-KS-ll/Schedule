package com.schema.bro;

import java.text.DecimalFormat;
import java.util.StringTokenizer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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
import com.schema.bro.ks.Lesson;
import com.schema.bro.timepicker.CustomTimePicker;

public class LessonActivity extends Activity implements
		CustomTimePicker.NoticeDialogListener {

	private ListView list;
	private final String[] matrix = { "_id", "name", "value" };
	private final String[] columns = { "name", "value" };
	private final int[] layouts = { R.id.text1, R.id.text2 };
	private SimpleCursorAdapter data;
	private MatrixCursor cursor;
	private EditText edit_name;
	private static final int DIALOG1 = 0, DIALOG2 = 1;
	private static final int TEXT_ID = 0;
	private ImageView image;
	private int val = 0, ID = -1;
	private String day, name;
	private boolean edit = false;
	private AlertDialog dayDialog;
	private String room, teacher, startTime, endTime;
	private static final String[] items = { "Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag" };
	private static final int[] imageIDs = { R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
			R.drawable.pic4, R.drawable.pic5, R.drawable.pic6, R.drawable.pic7,
			R.drawable.pic8, R.drawable.pic9, R.drawable.pic10,
			R.drawable.pic11, R.drawable.pic12, R.drawable.pic13,
			R.drawable.pic14, R.drawable.pic15, R.drawable.pic16,
			R.drawable.pic17, };
	

	protected void onCreate(Bundle savedInstanceState) {
		ThemeActivity.setTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lesson_activity);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		startTime = endTime = "08:00";
		room = teacher = " ";
		day = "Måndag";

		// Load data
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			edit = extras.getBoolean("edit", false);
			if (edit) {
				Lesson lesson = new Lesson(extras.getString("lesson"));
				day = lesson.getWeekday();
				startTime = lesson.getStartTime();
				endTime = lesson.getEndTime();
				name = lesson.getName();
				room = lesson.getRoom();
				teacher = lesson.getMaster();
				val = lesson.getImage();
				ID = lesson.getID();
				this.getActionBar();
			} else {
				day = extras.getString("day", "Måndag");
			}
		} else {
			Log.e("LessonActivity:onCreate", "No extras");
		}

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
		edit_name.setText(name);

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
		if (edit)
			menu.add("Ta bort").setIcon(R.drawable.ic_action_remove_light)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals("Ta bort")) {
			removeLesson();
			finish();
			return true;
		}

		switch (item.getItemId()) {
		case R.id.done:
			if (edit)
				editLesson();
			else
				createLesson();
			finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	public void editLesson() {
		String name = edit_name.getText().toString();
		if(name == null || name.equals(""))
			name = "Lektions namn";
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String data = Lesson.convertToString(day, startTime, endTime, name,
				room, teacher, val, ID);
		prefs.edit().putString("lesson_" + ID, data).commit();
	}

	public void createLesson() {
		String name = edit_name.getText().toString();
		if(name == null || name.equals(""))
			name = "Lektions namn";
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		int n = prefs.getInt("count", 0);
		String data = Lesson.convertToString(day, startTime, endTime, name,
				room, teacher, val, n);
		prefs.edit().putString("lesson_" + n, data).commit();
		prefs.edit().putInt("count", n + 1).commit();
	}

	public void removeLesson() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		prefs.edit().putString("lesson_" + ID, "empty").commit();
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
				DialogFragment startTimeFragement = new CustomTimePicker();
				startTimeFragement.show(getFragmentManager(), "start");
				break;
			case 2:
				DialogFragment endTimeFragement = new CustomTimePicker();
				endTimeFragement.show(getFragmentManager(), "end");
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
		input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(30) });
		if (id == DIALOG1)
			input.setText(room);
		else if (id == DIALOG2)
			input.setText(teacher);

		builder.setView(input);
		builder.setPositiveButton("Klar",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						if (id == DIALOG1)
							room = input.getText().toString().trim();
						else if (id == DIALOG2)
							teacher = input.getText().toString().trim();
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

	public int getHour(String tag){
		StringTokenizer token;	
		if(tag.equals("start"))
			token = new StringTokenizer(startTime, ":");
		else 
			token = new StringTokenizer(endTime, ":");
		String hour = token.nextToken();
		return Integer.parseInt(hour);
	}

	public int getMinute(String tag) {
		StringTokenizer token;	
		if(tag.equals("start"))
			token = new StringTokenizer(startTime, ":");
		else 
			token = new StringTokenizer(endTime, ":");
		token.nextToken(); // Hour
		String minute = token.nextToken();
		return Integer.parseInt(minute);
	}

	@Override
	public void onDialogPositiveClick(String tag, int hour, int minutes) {
		DecimalFormat formatter = new DecimalFormat("00");
		if (tag.equals("start")) {
			startTime = formatter.format(hour) + ":"
					+ formatter.format(minutes);
		} else if (tag.equals("end")) {
			endTime = formatter.format(hour) + ":" + formatter.format(minutes);
		}
		UpdateListView();
	}

	@Override
	public void onDialogNegativeClick() {
		// Do nothing
	}

}
