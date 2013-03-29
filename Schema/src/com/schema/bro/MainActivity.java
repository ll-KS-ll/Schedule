package com.schema.bro;

import java.util.Calendar;
import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.schema.bro.cards.CardFragment;
import com.schema.bro.ks.Lesson;

public class MainActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		SharedPreferences mPrefs = getSharedPreferences("THEME", 0);
		int themeID = mPrefs.getInt("theme_int", 0);
		super.setTheme(themeID);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);

		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		// Not visible pages to keep in memory
		mViewPager.setOffscreenPageLimit(4);

		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		if (day == Calendar.TUESDAY)
			mViewPager.setCurrentItem(1);
		else if (day == Calendar.WEDNESDAY)
			mViewPager.setCurrentItem(2);
		else if (day == Calendar.THURSDAY)
			mViewPager.setCurrentItem(3);
		else if (day == Calendar.FRIDAY)
			mViewPager.setCurrentItem(4);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.items, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add:
			intent = new Intent(this, LessonActivity.class);
			intent.putExtra("edit", false);
			intent.putExtra("day", mViewPager.getCurrentItem());
			break;
		case R.id.theme:
			intent = new Intent(this, ThemeActivity.class);
			break;
		case R.id.about:
			intent = new Intent(this, AboutActivity.class);
			break;
		case R.id.share:
			return super.onOptionsItemSelected(item);
			// break;
		case R.id.nova_software:
			intent = new Intent(this, NovaSoftwareListActivity.class);
			break;
		case R.id.school_meal:
			intent = new Intent(this, SchoolMealActivity.class);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		return true;
	}

	public void update(){
		for(int n=0; n < mSectionsPagerAdapter.getCount(); n++){
			CardFragment fragment = mSectionsPagerAdapter.getFragment(n);
			fragment.update();
		}
	}
	
	public void addLesson(String lesson, int day){
		CardFragment fragment = mSectionsPagerAdapter.getFragment(day);
		fragment.addCard(lesson);
	}
	
	public void addLesson(Lesson lesson, int day){
		CardFragment fragment = mSectionsPagerAdapter.getFragment(day);
		fragment.addCard(lesson);
	}
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private static final int NUMBER_OF_PAGES = 5;
		private final CardFragment[] fragments = new CardFragment[NUMBER_OF_PAGES];

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			for (int n = 0; n < NUMBER_OF_PAGES; n++) {
				CardFragment fragment = new CardFragment();
				Bundle args = new Bundle();
				args.putInt(CardFragment.ARG_SECTION_NUMBER, n);
				fragment.setArguments(args);
				fragments[n] = fragment;
			}
		}

		public CardFragment getFragment(int position){
			return fragments[position];
		}
		
		@Override
		public Fragment getItem(int position) {
			return fragments[position];
		}

		@Override
		public int getCount() {
			return NUMBER_OF_PAGES;
		}

		public String getDay(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1);
			case 1:
				return getString(R.string.title_section2);
			case 2:
				return getString(R.string.title_section3);
			case 3:
				return getString(R.string.title_section4);
			case 4:
				return getString(R.string.title_section5);
			}
			return null;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(
						Locale.getDefault());
			case 1:
				return getString(R.string.title_section2).toUpperCase(
						Locale.getDefault());
			case 2:
				return getString(R.string.title_section3).toUpperCase(
						Locale.getDefault());
			case 3:
				return getString(R.string.title_section4).toUpperCase(
						Locale.getDefault());
			case 4:
				return getString(R.string.title_section5).toUpperCase(
						Locale.getDefault());
			}
			return null;
		}
	}

}
