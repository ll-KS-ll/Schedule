package com.schema.bro.cards;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schema.bro.R;
import com.schema.bro.ks.Lesson;
import com.schema.bro.ks.Schedule;

public class CardLayoutFragment extends Fragment{

	private CardPagerAdapter adapter;
	private ViewPager pager;
	private Schedule database;
	private boolean tabletLayout = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.card_layout, container, false);
		database = new Schedule(getActivity());
		pager = (ViewPager) rootView.findViewById(R.id.pager);
		if(pager == null)
			tabletLayout = true;
		
		if(!tabletLayout){
			adapter = new CardPagerAdapter(getFragmentManager());
			pager.setAdapter(adapter);
			pager.setOffscreenPageLimit(4);
		
			int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
			if (day == Calendar.TUESDAY)
				pager.setCurrentItem(1);
			else if (day == Calendar.WEDNESDAY)
				pager.setCurrentItem(2);
			else if (day == Calendar.THURSDAY)
				pager.setCurrentItem(3);
			else if (day == Calendar.FRIDAY)
				pager.setCurrentItem(4);
		}
		return rootView;
	}
	
	public void onResume(){
		super.onResume();
		
		database.update();
		
		if(tabletLayout){
			CardFragment[] cardFragments = new CardFragment[5];
			
			cardFragments[0] = (CardFragment) getFragmentManager().findFragmentById(R.id.cardFragment1);
			cardFragments[1] = (CardFragment) getFragmentManager().findFragmentById(R.id.cardFragment2);
			cardFragments[2] = (CardFragment) getFragmentManager().findFragmentById(R.id.cardFragment3);
			cardFragments[3] = (CardFragment) getFragmentManager().findFragmentById(R.id.cardFragment4);
			cardFragments[4] = (CardFragment) getFragmentManager().findFragmentById(R.id.cardFragment5);			
			for(int n=0; n<5; n++)
				cardFragments[n].loadCards(database.getLessons(n+2));
		}else{
			for(int n=0; n<5; n++){
				Lesson[] lessons = database.getLessons(n+2);
				adapter.getFragment(n).loadCards(lessons);
			}
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if(tabletLayout && !getActivity().isFinishing()){
			CardFragment[] cardFragments = new CardFragment[5];
			
			cardFragments[0] = (CardFragment) getFragmentManager().findFragmentById(R.id.cardFragment1);
			cardFragments[1] = (CardFragment) getFragmentManager().findFragmentById(R.id.cardFragment2);
			cardFragments[2] = (CardFragment) getFragmentManager().findFragmentById(R.id.cardFragment3);
			cardFragments[3] = (CardFragment) getFragmentManager().findFragmentById(R.id.cardFragment4);
			cardFragments[4] = (CardFragment) getFragmentManager().findFragmentById(R.id.cardFragment5);			
			for(int n=0; n<5; n++)
				getFragmentManager().beginTransaction().remove(cardFragments[n]).commitAllowingStateLoss();
		}
	}

	public int getSelectedDay(){
		if(tabletLayout)
			return 0;
		else	
			return pager.getCurrentItem();
	}
	
}
