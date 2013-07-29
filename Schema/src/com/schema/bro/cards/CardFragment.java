package com.schema.bro.cards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schema.bro.R;
import com.schema.bro.ks.Lesson;

public class CardFragment extends Fragment{

	private CardLayout cl;
	private Lesson[] lessons;
	//private Lesson nextLesson;
	//private int showNextLesson = -1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.card_holder, container, false);
		cl = (CardLayout) view.findViewById(R.id.card_view);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		showAllCards();
		//showNextLessonCard();
	}

	public void loadCards(Lesson[] lessons){
		this.lessons = lessons;
		showAllCards();
		// checkIfToShowNextLessonCard();
	}
	
	private void showAllCards(){
		if(lessons != null && cl != null){
			clearAllCards();
			for(int n=0; n<lessons.length; n++)
				cl.addCard(lessons[n]);
		}
	}
	
	/** Add a new card
	 <p><b>Not working!</b> */ /*Not used what I know of atm*/
	public void addCard(Lesson lesson){
		//cl.addCard(lesson, database.addLesson(lesson));
	}
	
	public void clearAllCards(){
		cl.removeAllViews();
	}
	
	public int getDay(){
		return getArguments().getInt(CardPagerAdapter.ARG_SECTION_NUMBER);
	}
	
	/*
	/** Not done yet
	public void addCard(String lesson){
		int n = database.addLesson(lesson);
		if(n != -1)
			cl.addCard(database.getLesson(n), n);
	}
	*/
	
	/* Not used?!?! :s
	public void update(){
		int weekday = this.getArguments().getInt(CardPagerAdapter.ARG_SECTION_NUMBER);
		database.update(weekday);
		loadCards();
	}
	*/
	
	/*
	public void loadNextLesson(int showNextLesson, Lesson nextLesson){
		this.showNextLesson = showNextLesson;
		this.nextLesson = nextLesson;
	}
	*/
	
	/*
	private void showNextLessonCard(){
		int checker = showNextLesson;
		if(checker == -1){
			return;
		}
		
		if(checker == 0){
			// before
			cl.addNextLessonCard(nextLesson, false);
		}
		
		if(checker == 1){
			// during
			cl.addNextLessonCard(nextLesson, true);
		}
	}
	*/
	
}
