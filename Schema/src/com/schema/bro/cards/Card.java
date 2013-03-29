package com.schema.bro.cards;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schema.bro.LessonActivity;
import com.schema.bro.R;
import com.schema.bro.ks.Lesson;

public class Card extends LinearLayout implements OnClickListener{

	private ImageView icon;
	private TextView lessonText, teacherText, startTimeText, endTimeText, roomText;
	private String lessonData = "empty";
	private int ID = -1;
	
	int[] imageIDs = { R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
			R.drawable.pic4, R.drawable.pic5, R.drawable.pic6, R.drawable.pic7,
			R.drawable.pic8, R.drawable.pic9, R.drawable.pic10,
			R.drawable.pic11, R.drawable.pic12, R.drawable.pic13,
			R.drawable.pic14, R.drawable.pic15, R.drawable.pic16,
			R.drawable.pic17, };

	public Card(Context context) {
		super(context);
	}

	public Card(Context context, Lesson lesson) {
		super(context);
		SharedPreferences mPrefs = context.getSharedPreferences("THEME", 0);
		int cardStyleID = mPrefs.getInt("card_style_int", 0);
		if (cardStyleID == 0)
			cardStyleID = R.layout.card;
		LayoutInflater.from(context).inflate(cardStyleID, this, true);

		lessonData = lesson.toString();
		ID = lesson.getID();
		
		icon = (ImageView) findViewById(R.id.cardLessonImage);
		lessonText = (TextView) findViewById(R.id.cardLessonText);
		teacherText = (TextView) findViewById(R.id.cardTeacher);
		startTimeText = (TextView) findViewById(R.id.cardStartTime);
		endTimeText = (TextView) findViewById(R.id.cardEndTime);
		roomText = (TextView) findViewById(R.id.cardRoom);

		icon.setImageResource(imageIDs[lesson.getImage()]);
		lessonText.setText(lesson.getName());
		teacherText.setText(lesson.getMaster());
		startTimeText.setText(lesson.getStartTime());
		endTimeText.setText(lesson.getEndTime());
		roomText.setText(lesson.getRoom());
		
		setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getContext(), LessonActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("edit", true);
		intent.putExtra("lesson_key", "lesson_" + ID);
		intent.putExtra("lesson", lessonData);
		this.getContext().startActivity(intent);
	}

}
