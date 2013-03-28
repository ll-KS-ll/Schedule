package com.schema.bro.ks;

import java.util.LinkedList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Schedule {

	private PriorityList lessons;
	private SharedPreferences data;

	public Schedule(Context context) {
		data = PreferenceManager.getDefaultSharedPreferences(context);
		loadLessons();
	}

	public Schedule(Context context, int weekday) {
		data = PreferenceManager.getDefaultSharedPreferences(context);
		loadLessons(weekday);
	}
	
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

	public void update(int weekday) {
		loadLessons(weekday);
	}

	public Lesson[] getWeekdayLessons() {
		Lesson[] les = new Lesson[lessons.size()];
		for (int n = 0; n < lessons.size(); n++) {
			les[n] = (Lesson) lessons.get(n);
		}
		return les;
	}

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
