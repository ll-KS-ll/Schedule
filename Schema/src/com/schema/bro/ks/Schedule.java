package com.schema.bro.ks;

import java.util.Calendar;
import java.util.LinkedList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Schedule {

	private PriorityList lessons;
	private SharedPreferences data;
	//private Lesson nextLesson;
	
	public Schedule(Context context) {
		data = PreferenceManager.getDefaultSharedPreferences(context);
		//nextLesson = null;
		loadLessons();
	}
	
	/*
	public Schedule(Context context, int weekday) {
		data = PreferenceManager.getDefaultSharedPreferences(context);
		nextLesson = null;
		loadLessons(weekday);
	}
	*/
	
	private void loadLessons(){
		int count = data.getInt("count", 0);
		lessons = new PriorityList();
		for (int n = 0; n < count; n++) {
			String lesson = data.getString("lesson_" + n, "empty");
			if(!lesson.equals("empty")){
				Lesson tempLesson;
				try {
					tempLesson = new Lesson(lesson);
				} catch (Exception e) {
					data.edit().putString("lesson_" + n, "empty").commit();
					Log.e("Error", "Removed lesson_" + n + " due to it failed to load");
					continue;
				}
				lessons.add(tempLesson);
			}
		}
	}

	/*
	private void loadLessons(int weekday){
		int count = data.getInt("count", 0);

		lessons = new PriorityList();

		for (int n = 0; n < count; n++) {
			String lesson = data.getString("lesson_" + n, "empty");
			if(lesson.equals("empty"))
				continue;
			Lesson tempLesson;
			try {
				tempLesson = new Lesson(lesson);
			} catch (Exception e) {
				data.edit().putString("lesson_" + n, "empty").commit();
				Log.e("Error", "Removed lesson_" + n + " due to it failed to load");
				continue;
			}
			int day = tempLesson.getWeekdayValue();
			if (day == weekday) {
				lessons.add(tempLesson);
			}
		}
	}
	*/
	
	public int addLesson(Lesson lesson) {
		return lessons.add(lesson);
	}
	
	public int addLesson(String lesson) {
		Lesson les;
		try {
			les = new Lesson(lesson);
		} catch (Exception e) {
			Log.e("Error", "Lesson failed to load");
			return -1;
		}
		return lessons.add(les);
	}

	public Lesson getLesson(int pos){
		return (Lesson) lessons.get(pos);
	}
	
	/** Not working */
	protected void removeLesson(int pos) {
		data.edit().putInt("count", data.getInt("count", 0) - 1).commit();
		lessons.remove(pos);
		data.edit().remove("lesson_" + pos).commit();
	}

	public void update() {
		int count = data.getInt("count", 0);

		if (count == lessons.size())
			return;

		loadLessons();
	}

	/*
	public void update(int weekday) {
		loadLessons(weekday);
	}
	*/

	/*
	public Lesson[] getWeekdayLessons() {
		Lesson[] les = new Lesson[lessons.size()];
		for (int n = 0; n < lessons.size(); n++) {
			les[n] = (Lesson) lessons.get(n);
		}
		return les;
	}
	*/

	/**Get lesson for a specified day.
	 * 
	 * @param weekday - Calendar API value for the day
	 * @return an array of lessons for the specified day
	 */
	public Lesson[] getLessons(int weekday) {
		int count = 0;
		PriorityList tempLessons = new PriorityList();

		for (int n = 0; n < lessons.size(); n++) {
			Lesson temp = (Lesson) lessons.get(n);
			int day = temp.getWeekdayValue();
			if (day == weekday) {
				tempLessons.add(temp);
				count++;
			}
		}

		Lesson[] les = new Lesson[count];
		for (int n = 0; n < count; n++) {
			les[n] = (Lesson) tempLessons.removeFirst();
		}

		return les;
	}
	
	/** <b>Not tested yet</b>*/
	public Lesson getNextLesson(){
		if(lessons.size() < 2){
			Log.e("Schedule:getNextLesson", "There is less than two lessons added");
			return null;
		}
		
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int minute = Calendar.getInstance().get(Calendar.MINUTE);
		
		for(int n=lessons.size()-1; n >= 0; n--){
			Lesson lesson = (Lesson) lessons.get(n);
			
			if(day == lesson.getWeekdayValue())
				continue;
			
			int curTime = hour * 60 + minute;
			int lesStartTime = lesson.getStartHour() * 60 + lesson.getStartMinute();
			int lesEndTime = lesson.getEndHour() * 60 + lesson.getEndMinute();
			
			if(curTime >= lesStartTime && curTime <= lesEndTime){
				Log.d("Schedule:getNextLesson", "Got current lesson");
				if(n < lessons.size()){
					return getLesson(n);
				}else{
					return getLesson(0);
				}
			}
		}
		Log.e("Schedule:getNextLesson", "Couldn't get next lesson");
		return null;
	}
	
	/** <b>Not tested yet</b>*/
	public Lesson getCurrentLesson(){
		if(isEmpty()){
			Log.e("Schedule:getCurrentLesson", "There is no lessons added");
			return null;
		}
		
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int minute = Calendar.getInstance().get(Calendar.MINUTE);
		
		Lesson[] todayLessons = getLessons(day);
		for(int n = todayLessons.length - 1; n >= 0; n--){
			Lesson lesson = todayLessons[n];
			int curTime = hour * 60 + minute;
			int lesStartTime = lesson.getStartHour() * 60 + lesson.getStartMinute();
			int lesEndTime = lesson.getEndHour() * 60 + lesson.getEndMinute();
			
			if(curTime >= lesStartTime && curTime <= lesEndTime){
				Log.v("Schedule:getCurrentLesson", "Got current lesson");
				return lesson;
			}
		}
		
		Log.d("Schedule:getCurrentLesson", "Couldn't find any current lessons");
		return null;
		//Log.d("Schedule:getCurrentLesson", "Found no current lesson for today, tries tomorrow instead");
		//return getCurrentLesson(day + 1);
	}
	
	/*
	private Lesson getCurrentLesson(int day){
		if(isEmpty()){
			Log.e("Schedule:getCurrentLesson", "There is no lessons added");
			return null;
		}
		
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int minute = Calendar.getInstance().get(Calendar.MINUTE);
		
		Lesson[] todayLessons = getLessons(day);
		for(int n = todayLessons.length - 1; n >= 0; n--){
			Lesson lesson = todayLessons[n];
			int curTime = hour * 60 + minute;
			int lesStartTime = lesson.getStartHour() * 60 + lesson.getStartMinute();
			int lesEndTime = lesson.getEndHour() * 60 + lesson.getEndMinute();
			
			if(curTime >= lesStartTime && curTime <= lesEndTime){
				Log.v("Schedule:getCurrentLesson", "Got current lesson");
				return lesson;
			}
		}
		
		Log.d("Schedule:getCurrentLesson", "Found no current lesson for today, tries tomorrow instead");
		return null;
	}
	*/
	
	/*
	public int showNextLessonCard(){
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int minute = Calendar.getInstance().get(Calendar.MINUTE);
		
		for(int i=lessons.size() - 1; i >= 0; i--){
			Lesson lesson = (Lesson) lessons.get(i);
			
			if(lesson.getWeekdayValue() != day)
				continue;
			
			if(lesson.getStartHour() >= hour){
				if(lesson.getStartMinute() >= minute){
					// Lesson occurs after current time
					nextLesson = lesson;
					return 0;
				}
			}
			
			if(lesson.getEndHour() >= hour){
				if(lesson.getEndMinute() >= minute){
					// Lesson ends after current time
					nextLesson = lesson;
					return 1;
				}
			}
		}
		// No lesson match at all
		nextLesson = null;
		return -1;
	}
	*/
	
	/*
	public Lesson getNextLesson(){
		if(nextLesson == null)
			showNextLessonCard();
		return nextLesson;
	}
	*/
	
	public boolean isEmpty(){
		return lessons.isEmpty();
	}
	
	@SuppressWarnings("rawtypes")
	public static class PriorityList extends LinkedList {

		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		public int add(Comparable object) {
			for (int i = 0; i < size(); i++) {
				if (object.compareTo(get(i)) <= 0) {
					add(i, object);
					return i;
				}
			}
			addLast(object);
			return size();
		}
	}
}
