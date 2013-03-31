package com.schema.bro.nova;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class NovaPagerAdapter extends FragmentStatePagerAdapter{

	public static final String ARG_SECTION_NUMBER = "section_number";
	private static final int NUMBER_OF_PAGES = 5;
	protected final NovaFragment[] fragments = new NovaFragment[NUMBER_OF_PAGES];
	
	public NovaPagerAdapter(FragmentManager fm) {
		super(fm);
		for (int n = 0; n < getCount(); n++) {
			NovaFragment fragment = new NovaFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, n);
			fragment.setArguments(args);
			fragments[n] = fragment;
		}
	}
	
	public NovaFragment getFragment(int position){
		return fragments[position];
	}
	
	@Override
	public NovaFragment getItem(int position) {
		return fragments[position];
	}

	@Override
	public int getCount() {
		return NUMBER_OF_PAGES;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "Måndag";
		case 1:
			return "Tisdag";
		case 2:
			return "Onsdag";
		case 3:
			return "Torsdag";
		case 4:
			return "Fredag";
		}
		return null;
	}
}
