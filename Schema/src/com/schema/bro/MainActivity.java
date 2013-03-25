package com.schema.bro;

import java.util.Calendar;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.schema.bro.cards.CardLayout;
import com.schema.bro.ks.Lesson;
import com.schema.bro.ks.Schedule;

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
	Schedule database;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(mSectionsPagerAdapter);

		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		if (day == Calendar.TUESDAY)
			mViewPager.setCurrentItem(1);
		else if (day == Calendar.WEDNESDAY)
			mViewPager.setCurrentItem(2);
		else if (day == Calendar.THURSDAY)
			mViewPager.setCurrentItem(3);
		else if (day == Calendar.FRIDAY)
			mViewPager.setCurrentItem(4);

		database = new Schedule(this);

		mViewPager.getCurrentItem();
	}

	@Override
	protected void onResume() {
		super.onResume();
		database.update();
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
			break;
		case R.id.theme:
			intent = new Intent(this, ThemeActivity.class);
			break;
		case R.id.about:
			intent = new Intent(this, AboutActivity.class);
			break;
		case R.id.share:

			break;
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

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter  implements ViewPager.OnPageChangeListener {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			
			return fragment;
		}

		@Override
		public int getCount() {
			return 5;
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

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int day) {
			
			Lesson[] lessons = database.get(day);

			CardLayout card = (CardLayout) findViewById(R.id.card_holder);
			/*
			if (card != null){
				card.removeAllViews();
				for(int n=0; n < lessons.length; n++)
					card.addCard(lessons[n]);
			}else
				Log.e("MainActivity", "Null stuff");
			*/	
			if (card != null)
				card.addCard(lessons[0]);
			else
				Log.e("MainActivity", "Null stuff");
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {

		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			return inflater.inflate(R.layout.card_holder, container, false);
		}
	}

}
