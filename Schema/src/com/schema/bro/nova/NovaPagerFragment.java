package com.schema.bro.nova;

import java.util.Calendar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schema.bro.R;

public class NovaPagerFragment extends Fragment{

	private NovaPagerAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.pager_layout, container, false);

		adapter = new NovaPagerAdapter(getFragmentManager());
		
		final ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
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

		return rootView;
	}
	
	public void setClassURL(String classURL){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		prefs.edit().putString("class_url", classURL).commit();
		for(int n=0; n<5; n++){
			NovaFragment novaFragment = adapter.getFragment(n);
			if(novaFragment != null){
				//novaFragment.setClassURL(classURL);
				//novaFragment.setImageView();
			}
		}
	}

}
