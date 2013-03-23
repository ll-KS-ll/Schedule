package com.schema.bro.ks;

import java.util.LinkedList;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Schedule {

	private PriorityList lessons;
	private SharedPreferences data;

	public Schedule(Context context) {
		data = PreferenceManager.getDefaultSharedPreferences(context);
		int count = data.getInt("count", 0);
		lessons = new PriorityList();
		for(int n=0; n<count;n++){
			lessons.add(new Lesson(data.getString("lesson" + n, "empty")));
		}
	}
	
	public void addLesson(){
		data.edit().putInt("count", data.getInt("count", 0) + 1);
	}
	
	public void removeLesson(){
		data.edit().putInt("count", data.getInt("count", 0) - 1);
	}
	
	@SuppressWarnings("rawtypes")
	public static class PriorityList extends LinkedList{
		
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		public void add(Comparable object){
			for(int i=0; i<size(); i++){
				if(object.compareTo(get(i)) <= 0){
					add(i, object);
					return;
				}
			}
			addLast(object);
		}
	}
}
