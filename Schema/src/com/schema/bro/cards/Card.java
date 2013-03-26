package com.schema.bro.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schema.bro.R;
import com.schema.bro.ks.Lesson;

public class Card extends LinearLayout {

	private ImageView icon;
	private TextView lessonText, teacherText, startTimeText, endTimeText,
			roomText;
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
		LayoutInflater.from(context).inflate(R.layout.card, this, true);

		icon = (ImageView) findViewById(R.id.cardLessonImage);
		lessonText = (TextView) findViewById(R.id.cardLessonText);
		teacherText = (TextView) findViewById(R.id.cardTeacher);
		startTimeText = (TextView) findViewById(R.id.cardStartTime);
		endTimeText = (TextView) findViewById(R.id.cardEndTime);
		roomText = (TextView) findViewById(R.id.cardRoom);

		icon.setImageResource(imageIDs[0]);
		lessonText.setText(lesson.getName());
		teacherText.setText(lesson.getMaster());
		startTimeText.setText(lesson.getStartTime());
		endTimeText.setText(lesson.getEndTime());
		roomText.setText(lesson.getRoom());
	}

}
