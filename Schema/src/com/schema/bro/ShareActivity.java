package com.schema.bro;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.schema.bro.ks.Customizer;
import com.schema.bro.share.ReceiveSchedule;
import com.schema.bro.share.ShareSchedule;

public class ShareActivity extends FragmentActivity {

	public static final String UUID = "7e60cd00-e14c-4503-baf0-064b39f45b08";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Customizer.setTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_activity);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Toast toast = Toast.makeText(this, "Bluetooth stöds inte på denna enhet och det krävs för att dela scheman", Toast.LENGTH_LONG);
			toast.show();
			finish();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClick(View view){
		switch(view.getId()){
		case R.id.shareBluetoothShare:
			DialogFragment shareFragment = new ShareSchedule();
			shareFragment.show(getSupportFragmentManager(), "Share");
			break;
		case R.id.shareBluetoothReceive:
			DialogFragment reciveFragment = new ReceiveSchedule();
			reciveFragment.show(getSupportFragmentManager(), "Receive");
			break;
		}
	}
	
}
