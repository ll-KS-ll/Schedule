package com.schema.bro.widget;

import java.util.Timer;
import java.util.TimerTask;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.Log;
import android.widget.RemoteViews;
import com.schema.bro.MainActivity;
import com.schema.bro.R;
import com.schema.bro.ks.Lesson;
import com.schema.bro.ks.Schedule;

public class CardWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new LessonCard(context, appWidgetManager), 1, 60000);
	}

	private class LessonCard extends TimerTask {
		private RemoteViews remoteViews;
		private AppWidgetManager appWidgetManager;
		private ComponentName thisWidget;
		private Schedule database;
		private Lesson lesson;
		private TypedArray images;
		private Context context;
		private PendingIntent pendingIntent;
		@SuppressWarnings("unused")
		private SharedPreferences mPrefs;
		@SuppressWarnings("unused")
		private int cardStyleID, daysWithLessons = 0;

		public LessonCard(Context context, AppWidgetManager appWidgetManager) {
			this.context = context;
			database = new Schedule(context);
			this.appWidgetManager = appWidgetManager;
			thisWidget = new ComponentName(context, CardWidget.class);
			Intent intent = new Intent(context, MainActivity.class);
			pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			mPrefs = context.getSharedPreferences("THEME", 0);
		}

		/*
		public int getDay() {
			int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
			if (day == Calendar.MONDAY)
				day = 0;
			else if (day == Calendar.TUESDAY)
				day = 1;
			else if (day == Calendar.WEDNESDAY)
				day = 2;
			else if (day == Calendar.THURSDAY)
				day = 3;
			else if (day == Calendar.FRIDAY)
				day = 4;
			return day;
		}
		*/

		@SuppressLint("Recycle")
		public void updateCard() {
			if (database.isEmpty())
				remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_no_lessons);
			else {
				/* Filip Brolund
				cardStyleID = mPrefs.getInt("card_style_int", 0);
				if (cardStyleID == 0)
					cardStyleID = R.layout.card;
				*/
				
				//lesson = database.getNextLesson();
				lesson = database.getCurrentLesson();
				if(lesson == null){
					Log.e("CardWidget", "Lesson is null");
					remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_no_lessons);
					remoteViews.setOnClickPendingIntent(R.id.cardWidget, pendingIntent);
					return;
				}
				
				remoteViews = new RemoteViews(context.getPackageName(),R.layout.card_tablerow);
				
				images = context.getResources().obtainTypedArray(R.array.imageIDs);

				remoteViews.setImageViewResource(R.id.cardLessonImage,
						images.getResourceId(lesson.getImage(), -1));
				remoteViews.setTextViewText(R.id.cardLessonText,
						lesson.getName());
				remoteViews.setTextViewText(R.id.cardTeacher,
						lesson.getMaster());
				remoteViews.setTextViewText(R.id.cardStartTime,
						lesson.getStartTime());
				remoteViews.setTextViewText(R.id.cardEndTime,
						lesson.getEndTime());
				remoteViews.setTextViewText(R.id.cardRoom, lesson.getRoom());
			}
			remoteViews.setOnClickPendingIntent(R.id.cardWidget, pendingIntent);
		}

		/*
		public Lesson getCurrentLesson() {
				database = new Schedule(context, getDay());
				Calendar c = Calendar.getInstance();
				int currentTime = c.get(Calendar.HOUR_OF_DAY) * 60 + c.get(Calendar.MINUTE);
				int compareTime = 10000; 
				Lesson lesson = null;
				for (int i = 0; i < database.getWeekdayLessons().length; i++) {
					int time = (database.getLesson(i).getEndHour() * 60
							+ database.getLesson(i).getEndMinute() - currentTime);
					if (time > 0)
						if (time < compareTime) {
							compareTime = time;
							lesson = database.getLesson(i);
						}
				}
				if (lesson == null){
					int day;
					for (int l = 1; l < 6; l++){
						if (l+getDay() > 4)
							day = l-1;
						else
							day = getDay() + l;
						database = new Schedule(context, day);
						int lessonCount = database.getWeekdayLessons().length;
						if (lessonCount > 0){
							lesson = database.getLesson(lessonCount-1);
							break;
						}
					}
				}//else
					//remoteViews.setTextViewText(R.id.cardTimeTo, null);	
			return lesson;
		}
		*/
		
		@Override
		public void run() {
			updateCard();
			appWidgetManager.updateAppWidget(thisWidget, remoteViews);
		}

	}
}
