package com.schema.bro;

import java.util.Calendar;
import java.util.Locale;

import android.app.ActionBar;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.schema.bro.cards.CardFragment;
import com.schema.bro.ks.Lesson;

public class MainActivity extends FragmentActivity implements OnItemSelectedListener  {

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
	private int menuLayout = R.menu.schedule;

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

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		ArrayAdapter<String> spinnerMenu = new ArrayAdapter<String>(
				actionBar.getThemedContext(),
				android.R.layout.simple_list_item_1, getResources()
						.getStringArray(R.array.labelMenu));

		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		if (day == Calendar.TUESDAY)
			mViewPager.setCurrentItem(1);
		else if (day == Calendar.WEDNESDAY)
			mViewPager.setCurrentItem(2);
		else if (day == Calendar.THURSDAY)
			mViewPager.setCurrentItem(3);
		else if (day == Calendar.FRIDAY)
			mViewPager.setCurrentItem(4);

		actionBar.setListNavigationCallbacks(

		spinnerMenu,

		// Provide a listener to be called when an item is selected.
				new ActionBar.OnNavigationListener() {

					public boolean onNavigationItemSelected(int position,

					long id) {

						switch (position) {

						case 0:
							menuLayout = R.menu.schedule;
							break;

						case 1:
							menuLayout = R.menu.nova;
							break;
						default:

							break;

						}
						invalidateOptionsMenu();
						return true;
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(menuLayout, menu);		
		if (menuLayout == R.menu.nova){
			Spinner spinClass;
			MenuItem item = menu.findItem(R.id.classSpinner);
			spinClass = (Spinner)item.getActionView();

			ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources()
					.getStringArray(R.array.novaSpinner));

			spinClass.setAdapter(spinnerAdapter);
			spinClass.setOnItemSelectedListener(this);
		}
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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
			long arg3) {
		
		String url;
	     switch (pos) {
	        case 0:
	            url = "5ECAD1FC-388E-468D-880F-634BA6EF8C9D";
	        	break;
	        case 1:
	        	url = "487830F6-5C23-4A08-BA2F-1D6B82BFD80E";
	        	break;
	        case 2:
	        	url = "8C95EECD-0352-45D2-B22E-5FA4F9D79A67";
	        	break;
	        case 3:
	        	url = "C8FF7C6D-EF3F-42DF-BBF2-31EFACC1EBF0";
	        	break;
	        case 4:
	        	url = "397C258C-0B7F-4449-972A-C2A06785A058";
	        	break;
	        case 5:
	        	url = "3318BC4B-8710-489C-9132-BA862616920F";
	        	break;
	        case 6:
	        	url = "CF215BA3-03E1-4C46-B9D7-91B6AC487C2E";
	        	break;
	        case 7:
	        	url = "AE69DCAF-91EB-46A4-8401-B1D3E2098B76";
	        	break;
	        case 8:
	        	url = "67D76A15-13F9-4B6C-A1ED-03A2F0EDE347";
	        	break;
	        case 9:
	        	url = "D9556FEA-2F72-4141-BE02-0693711F1150";
	        	break;
	        case 10:
	        	url = "4D6D2AAE-7E76-4E3A-A665-5908868564FF";
	        	break;
	        case 11:
	        	url = "2E121AE9-47DC-47CA-A644-CF06A817CD52";
	            break;
	        case 12:
	        	url = "C0B5671A-583C-4F7F-AB79-929C5C7714F4";
	           	break;
	        case 13:
	        	url = "CE3896F5-C9E6-4948-B586-1D38E6EE1A67";
	          	break;
	        case 14:
	        	url = "CC03F674-03DF-4DBD-BC41-652EF65471A7";
	            break;
	        case 15:
	        	url = "35B08DEF-C76D-4066-93BD-0F8237E5683F";
	           	break;
	        case 16:
	        	url = "07179309-3669-44F4-B337-3D288C1A4B00";
	            break;
	        case 17:
	        	url = "D90C7F91-4109-4B10-8F3C-DE6A775A81FE";
	            break;
	        case 18:
	        	url = "5C887E9C-DF62-474D-BB99-B0CB9B7CADF7";
	            break;
	        case 19:
	        	url = "DA6962A5-6225-4987-AE95-FD0ED510AA0E";
	        	break;
	        case 20:
	        	url = "9DB665F9-10A4-45E6-853A-7DCACF25916A";
	            break;
	        case 21:
	        	url = "447643AE-129C-4986-A670-ADEA9A497D09";
	            break;
	        case 22:
	        	url = "8FE5E0F3-B1A1-4EE6-B3F0-E86D327AAD9D";  
	            break;
	        case 23:
	        	url = "575A2DA4-F002-4C72-86E5-D0D2201C0CCB";   
	            break;
	        case 24:
	        	url = "BF91EB9A-0E16-4C1B-B67D-59DA064CDBD8";   
	            break;
	        case 25:
	        	url = "1D5F28F6-F3AE-4FDE-AAA1-C6CFABC34CBC";   
	            break;
	        case 26:
	        	url = "0112DE1E-DBFB-4C46-85FD-B2619ED08DE3";   
	            break;
	        case 27:
	        	url = "BA8AA989-549E-471B-A852-9D15EF558DAC";   
	        	break;
	        case 28:
	        	url = "BFBE085D-A887-4137-AC7D-CBE818B2E9D3";   
	        	break;
	        case 29:
	        	url = "F3E0A9F7-68D5-4868-8A7F-25E66A224F34";   
	            break;
	        case 30:
	        	url = "6D819667-5BE1-48E6-87EA-F1D2D1F7D3B0";   
	            break;
	        case 31:
	        	url = "7CEC979B-60B7-47D3-85D1-8669A8AB3E16";    
	        	break;
	        case 32:
	        	url = "93A935E1-BC35-453B-AE09-47C051A96DC6";   
	        	break;
	        default:
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

}
