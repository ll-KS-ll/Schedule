package com.schema.bro.widget;

import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;
import com.schema.bro.MainActivity;
import com.schema.bro.R;
import com.schema.bro.ks.Lesson;
import com.schema.bro.ks.Schedule;

public class CardWidget extends AppWidgetProvider {

	public static final String ACTION_UPDATE = "schema_update";
	private static final int updateInterval = 60000;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.d("CardWidget:onReceive", "action: " + action);

		if (action.equals(CardWidget.ACTION_UPDATE)) {
			updateAppWidget(context, AppWidgetManager.getInstance(context), 0);
		}else {
			super.onReceive(context, intent);
		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Log.d("CardWidget:onUpdate", "called, number of instances " + appWidgetIds.length);
		for (int widgetId : appWidgetIds) {
			updateAppWidget(context, appWidgetManager, widgetId);
		}
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		
		Log.d("CardWidget:onEnabled", "Widget enabled!");
		
		Intent intentUpdate = new Intent(context, CardWidget.class);
		intentUpdate.setAction(CardWidget.ACTION_UPDATE);
		PendingIntent pendingIntentAlarm = PendingIntent.getBroadcast(context, 0, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), updateInterval, pendingIntentAlarm);
	}
	
	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);

		Log.d("CardWidget:onDisabled", "Widget disabled!");
		
		Intent intentUpdate = new Intent(context, CardWidget.class);
		intentUpdate.setAction(CardWidget.ACTION_UPDATE);
		PendingIntent pendingIntentAlarm = PendingIntent.getBroadcast(context, 0, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarm.cancel(pendingIntentAlarm);
	}

	private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
		Schedule database = new Schedule(context);

		if(database.isEmpty()){
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_no_lessons);
			Intent intent = new Intent(context, MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			remoteViews.setOnClickPendingIntent(R.id.cardWidget, pendingIntent);
			return;
		}
		
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_card);

		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		remoteViews.setOnClickPendingIntent(R.id.cardWidget, pendingIntent);
		
		Intent intentRefresh = new Intent(context, CardWidget.class);
		intentRefresh.setAction(CardWidget.ACTION_UPDATE);
		PendingIntent pendingIntentRefresh = PendingIntent.getBroadcast(context, 0, intentRefresh, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.refreshButton, pendingIntentRefresh);

		Lesson lesson = database.getCurrentLesson();

		TypedArray images = context.getResources().obtainTypedArray(R.array.imageIDs);

		remoteViews.setImageViewResource(R.id.cardLessonImage, images.getResourceId(lesson.getImage(), -1));
		remoteViews.setTextViewText(R.id.cardLessonText, lesson.getName());
		remoteViews.setTextViewText(R.id.cardTeacher, lesson.getMaster() + " | " + lesson.getRoom());
		remoteViews.setTextViewText(R.id.cardTime, lesson.getStartTime() + "-" + lesson.getEndTime());
		Calendar c = Calendar.getInstance();
		final int currentTime = c.get(Calendar.HOUR_OF_DAY) * 60 + c.get(Calendar.MINUTE);
		final int lessonStartTime = (lesson.getStartHour() * 60 + lesson.getStartMinute());
		if (currentTime <= lessonStartTime)
			remoteViews.setTextViewText(R.id.cardTimeLeft,"Tid kvar: " + lesson.getTimeLeft(false) + "m");
		else
			remoteViews.setTextViewText(R.id.cardTimeLeft,"Tid kvar: " + lesson.getTimeLeft(true) + "m");
		if (currentTime < lessonStartTime){
			remoteViews.setTextViewText(R.id.cardTimeLeft,lesson.getTimeLeft(false) + " min");
			if (Integer.parseInt(lesson.getTimeLeft(false)) > 5 )
				remoteViews.setTextColor(R.id.cardTimeLeft, Color.parseColor("#99CC00"));
			else
				remoteViews.setTextColor(R.id.cardTimeLeft, Color.parseColor("#FF4444"));
		}else{
			remoteViews.setTextViewText(R.id.cardTimeLeft,lesson.getTimeLeft(true) + " min");
			if (Integer.parseInt(lesson.getTimeLeft(true)) > 5 )
				remoteViews.setTextColor(R.id.cardTimeLeft, Color.parseColor("#99CC00"));
			else
				remoteViews.setTextColor(R.id.cardTimeLeft, Color.parseColor("#FF4444"));
		}
		images.recycle();

		ComponentName schemaWidget = new ComponentName(context, CardWidget.class);
		AppWidgetManager.getInstance(context).updateAppWidget(schemaWidget, remoteViews);
		
		Log.i("CardWidget:updateAppWidget", "Widget updated!");
	}

}
