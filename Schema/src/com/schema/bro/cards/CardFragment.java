package com.schema.bro.cards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schema.bro.R;
import com.schema.bro.ks.Lesson;
import com.schema.bro.ks.Schedule;

public class CardFragment extends Fragment{

	public static final String ARG_SECTION_NUMBER = "section_number";
	private CardLayout cl;
	private Schedule database;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.card_holder, container, false);
		
		cl = (CardLayout) view.findViewById(R.id.card_view);
		
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		
		int weekday = this.getArguments().getInt(ARG_SECTION_NUMBER);
		
		database = new Schedule(this.getActivity(), weekday);
		clearAllCards();
		loadCards();
	}

	public void clearAllCards(){
		cl.removeAllViews();
	}
	
	public void loadCards(){
		Lesson[] lessons = database.getWeekdayLessons();
		for(int n=0; n<lessons.length; n++)
			cl.addCard(lessons[n]);
	}
	
}
