package com.schema.bro;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.schema.bro.cards.CardPagerFragment;
import com.schema.bro.ks.Customizer;
import com.schema.bro.nova.NovaOnItemSelectedListener;
import com.schema.bro.nova.NovaPagerFragment;

public class MainActivity extends FragmentActivity implements ActionBar.OnNavigationListener {

	public static final int SCHEDULE = 0;
	public static final int NOVA = 1;
	public static final int SCHOOL_MEAL = 2;
	public static final int LAST_USED = 3;
	public static final String START_NAVIAGTION_STATE = "start_navigation_state";
	public static final String LAST_USED_STATE = "last_used_navigation_state";
	
	private int state;
	private Fragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Customizer.setTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		state = Integer.parseInt(prefs.getString(START_NAVIAGTION_STATE, "0")); // Set state
		if(state == LAST_USED)
			state = prefs.getInt(LAST_USED_STATE, SCHEDULE);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		ArrayAdapter<String> spinnerMenu = new ArrayAdapter<String>(actionBar.getThemedContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.labelMenu));
		actionBar.setListNavigationCallbacks(spinnerMenu, this);
		actionBar.setSelectedNavigationItem(state);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int temp = Integer.parseInt(prefs.getString(START_NAVIAGTION_STATE, "0"));
		if(temp == LAST_USED){
			prefs.edit().putInt(LAST_USED_STATE, state).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(state == NOVA){
			getMenuInflater().inflate(R.menu.nova, menu);
			Spinner spinClass;
			MenuItem item = menu.findItem(R.id.classSpinner);
			spinClass = (Spinner) item.getActionView();

			ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
					getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, getResources()
							.getStringArray(R.array.novaSpinner));

			spinClass.setAdapter(spinnerAdapter);
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			int pos = prefs.getInt("class_spinner_pos", 0);
			spinClass.setSelection(pos);
			if(fragment != null)
				spinClass.setOnItemSelectedListener(new NovaOnItemSelectedListener(fragment));
		}else{
			getMenuInflater().inflate(R.menu.schedule, menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.add:
			intent = new Intent(this, LessonActivity.class);
			intent.putExtra("edit", false);
			CardPagerFragment frag = (CardPagerFragment) fragment;
			intent.putExtra("day", frag.getSelectedDay());
			break;
		case R.id.share:
			return super.onOptionsItemSelected(item);
			// break;
		case R.id.school_meal:
			intent = new Intent(this, SchoolMealActivity.class);
			break;
		case R.id.settings:
			intent = new Intent(this, SettingsActivity.class);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		return true;
	}
	
	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		switch(position){
		case SCHEDULE:
			fragment = new CardPagerFragment();
			break;
		case NOVA:
			fragment = new NovaPagerFragment();
			break;
		case SCHOOL_MEAL:
			// Change to school meal fragment!
			fragment = new CardPagerFragment();
			break;
		default: return true;
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
		state = getActionBar().getSelectedNavigationIndex();
		invalidateOptionsMenu();
		return true;
	}
	
	

}
