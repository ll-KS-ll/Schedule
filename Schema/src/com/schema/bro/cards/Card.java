package com.schema.bro.cards;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schema.bro.LessonActivity;
import com.schema.bro.R;
import com.schema.bro.ks.Customizer;
import com.schema.bro.ks.Lesson;

public class Card extends LinearLayout implements OnClickListener{

	private ImageView icon;
	private TextView lessonText, teacherText, startTimeText, endTimeText, roomText;
	private String lessonData = "empty";
	private int ID = -1;
	private TypedArray images;
	

	public Card(Context context) {
		super(context);
	}

	@SuppressLint("Recycle")
	public Card(Context context, Lesson lesson) {
		super(context);
		
		LayoutInflater.from(context).inflate(Customizer.getCardStyle(context), this, true);

		lessonData = lesson.toString();
		ID = lesson.getID();
		
		icon = (ImageView) findViewById(R.id.cardLessonImage);
		lessonText = (TextView) findViewById(R.id.cardLessonText);
		teacherText = (TextView) findViewById(R.id.cardTeacher);
		startTimeText = (TextView) findViewById(R.id.cardStartTime);
		endTimeText = (TextView) findViewById(R.id.cardEndTime);
		roomText = (TextView) findViewById(R.id.cardRoom);

		images = getResources().obtainTypedArray(R.array.imageIDs);
		icon.setImageResource(images.getResourceId(lesson.getImage(), -1));
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
