package com.schema.bro.ks;

import java.util.StringTokenizer;

public class Lesson implements Comparable<Lesson>{

	private String weekday;
	private String startTime;
	private String endTime;
	private String name;
	private String room;
	private String master;
	private int weekdayVal;
	private int startHour;
	private int startMinute;
	private int endHour;
	private int endMinute;
	
	public final static String SEPARATOR = "|";

	public Lesson(String lessonString){
		if(lessonString.equals("empty"))
			return;
		// Decode string
		StringTokenizer token = new StringTokenizer(lessonString, SEPARATOR);
		weekday = token.nextToken();
		startTime = token.nextToken();
		endTime = token.nextToken();
		name = token.nextToken();
		room = token.nextToken();
		master = token.nextToken();
		
		init();
	}
	
	public Lesson(String weekday, String startTime, String endTime, String name, String room,
			String master) {

		this.weekday = weekday;
		this.startTime = startTime;
		this.endTime = endTime;
		this.name = name;
		this.room = room;
		this.master = master;
		
		init();
	}
	
	private void init(){
		weekdayVal = setWeekdayValue(weekday);
		startHour = setHour(startTime);
		startMinute = setMinute(startTime);
		endHour = setHour(endTime);
		endMinute = setMinute(endTime);
	}
	
	private int setWeekdayValue(String weekday){
		if(weekday.equals("Måndag"))
			return 0;
		else if(weekday.equals("Tisdag"))
			return 1;
		else if(weekday.equals("Onsdag"))
			return 2;
		else if(weekday.equals("Torsdag"))
			return 3;
		else if(weekday.equals("Fredag"))
			return 4;
		else if(weekday.equals("Lördag")
				|| weekday.equals("Söndag"))
			return 6;
		
		return -1;
	}
	
	private int setHour(String time){
		StringTokenizer token = new StringTokenizer(time, ":");
		String hour = token.nextToken();
		return Integer.parseInt(hour);
	}
	
	private int setMinute(String time){
		StringTokenizer token = new StringTokenizer(time, ":");
		token.nextToken(); // Hour
		String minute = token.nextToken();
		return Integer.parseInt(minute);
	}
	
	public String getName() {
		return name;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getRoom() {
		return room;
	}

	public String getMaster() {
		return master;
	}
	
	public int getStartHour() {
		return startHour;
	}

	public int getStartMinute() {
		return startMinute;
	}
	
	public int getEndHour() {
		return endHour;
	}

	public int getEndMinute() {
		return endMinute;
	}
	
	public int getWeekdayValue() {
		return weekdayVal;
	}


	public void setWeekday(String weekday){
		this.weekday = weekday;
		init();
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
		init();
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
		init();
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public void setRoom(String room) {
		this.room = room;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public void setHour(int startHour) {
		this.startHour = startHour;
	}

	public void setMinute(int startMinute) {
		this.startMinute = startMinute;
	}
	
	public static String convertToString(String weekday, String startTime, String endTime, String name, String room,
			String master){
		return weekday + SEPARATOR + startTime + SEPARATOR + endTime
				+ SEPARATOR + name + SEPARATOR + room + SEPARATOR + master;
	}
	
	public String toString() {
		return weekday + SEPARATOR + startTime + SEPARATOR + endTime
				+ SEPARATOR + name + SEPARATOR + room + SEPARATOR + master;
	}

	@Override
	public int compareTo(Lesson lesson) {
		// Day
		if(weekdayVal > lesson.getWeekdayValue())
			return -1;
		if(weekdayVal < lesson.getWeekdayValue())
			return 1;
        // Hour
		if(startHour > lesson.getStartHour())
			return -2;
		if(startHour < lesson.getStartHour())
			return 2;
		// Minute
		if(startMinute > lesson.getStartMinute())
			return -2;
		if(startMinute < lesson.getStartMinute())
			return 2;
		// Equal
		return 0;
	}
	
	@Override
	public boolean equals(Object lesson) {
		Lesson les = (Lesson) lesson;
		String otherLes = les.toString();
		if(toString().equals(otherLes))
			return true;
		return false;
	}

}
