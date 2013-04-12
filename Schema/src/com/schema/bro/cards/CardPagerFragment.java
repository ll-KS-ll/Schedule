package com.schema.bro.cards;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schema.bro.R;
import com.schema.bro.ks.Lesson;
import com.schema.bro.ks.Schedule;

public class CardPagerFragment extends Fragment{

	private CardPagerAdapter adapter;
	private ViewPager pager;
	private Schedule database;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.pager_layout, container, false);
		adapter = new CardPagerAdapter(getFragmentManager());
		
		database = new Schedule(getActivity());
		
		pager = (ViewPager) rootView.findViewById(R.id.pager);
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
		
		Log.v("CardPagerFragment", "day: " + day);
		
		return rootView;
	}
	
	public void onResume(){
		super.onResume();
		
		database.update();
		
		for(int n=0; n<5; n++){
			Lesson[] lessons = database.getLessons(n+2);
			adapter.getFragment(n).loadCards(lessons);
		}
	}
	
	public int getSelectedDay(){
		return pager.getCurrentItem();
	}
	
}
