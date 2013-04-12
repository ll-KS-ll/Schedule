package com.schema.bro.share;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.schema.bro.R;
import com.schema.bro.ShareActivity;
import com.schema.bro.ks.Schedule;

public class ShareSchedule extends DialogFragment implements OnItemClickListener{
	
	private BluetoothAdapter mBluetoothAdapter;
	private static final int REQUEST_ENABLE_BT = 42;
	private boolean connected;
	private ConnectThread connect;
	private Context context;
	private View view; 
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private LinkedList<String> addresses;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	    connected = false;
	    
	    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
	    addresses = new LinkedList<String>();
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    view = inflater.inflate(R.layout.share_dialog, null);
	    listView = (ListView) view.findViewById(R.id.shareBluetoothDeviceList);
	    listView.setAdapter(adapter);
	    listView.setOnItemClickListener(this);
	    
	    builder.setView(view).setTitle("Dela schema till...")
	           .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   mBluetoothAdapter.cancelDiscovery();
	               }
	           });      
	    return builder.create();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		if(mBluetoothAdapter == null)
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (mBluetoothAdapter.isEnabled()) {
			if(!connected)
				connectBluetooth();
		} else {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
	}

	private void connectBluetooth() {
		connected = true;
		
		context = this.getActivity();
		
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
			// Loop through paired devices
			for (BluetoothDevice device : pairedDevices) {
				adapter.add(device.getName());
				addresses.add(device.getAddress());
				adapter.notifyDataSetChanged();
			}
		}

		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		getActivity().registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
		
		mBluetoothAdapter.startDiscovery();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_OK) {
			if(!connected)
				connectBluetooth();
		}else if(resultCode == Activity.RESULT_CANCELED){
			Toast toast = Toast.makeText(getActivity(), "Bluetooth krävs för att kunna dela schema", Toast.LENGTH_SHORT);
			toast.show();
			dismiss();
		}
	}
	
	// Create a BroadcastReceiver for ACTION_FOUND
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				adapter.add(device.getName());
				addresses.add(device.getAddress());
				adapter.notifyDataSetChanged();
			}
		}
	};
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mBluetoothAdapter.cancelDiscovery();
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(addresses.get(position));
		
		final TextView tv = (TextView) this.view.findViewById(R.id.shareDialogTextInfo);
    	tv.setText("Försöker koppla till " + device.getName());
    	listView.setVisibility(View.GONE);
		
		connect = new ConnectThread(device);
		connect.start();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mBluetoothAdapter.cancelDiscovery();
		getActivity().unregisterReceiver(mReceiver);
		if(connect != null)
			connect.cancel();
	}
	
	public void onConnected(){
		mBluetoothAdapter.cancelDiscovery();
		
		view.post(new Runnable() {
	        public void run() {
	        	final TextView tv = (TextView) view.findViewById(R.id.shareDialogTextInfo);
	        	tv.setText("Skickar schema...");
	        }
		});
	}
	
	private class ConnectThread extends Thread {
	    
		private final BluetoothSocket mmSocket;
		private ManageBluetoothConnection connectionManager;
		
	    public ConnectThread(BluetoothDevice device) {
	        // Use a temporary object that is later assigned to mmSocket,
	        // because mmSocket is final
	        BluetoothSocket tmp = null;
	 
	        // Get a BluetoothSocket to connect with the given BluetoothDevice
	        try {
	            // MY_UUID is the app's UUID string, also used by the server code
	            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(ShareActivity.UUID));
	        } catch (IOException e) { }
	        mmSocket = tmp;
	    }
	 
	    public void run() {
	        // Cancel discovery because it will slow down the connection
	    	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	    	mBluetoothAdapter.cancelDiscovery();
	 
	        try {
	            // Connect the device through the socket. This will block
	            // until it succeeds or throws an exception
	            mmSocket.connect();
	        } catch (IOException connectException) {
	            // Unable to connect; close the socket and get out!
	            try {
	                mmSocket.close();
	            } catch (IOException closeException) { }
	            return;
	        }
	 
	       Log.d("Schema Bluetooth", "Connected!!");
	       onConnected();
	       
	       connectionManager = new ManageBluetoothConnection(context, mmSocket);
	       Schedule database = new Schedule(context);
	       connectionManager.write(database.getSchedule());
	       
	       this.cancel();
	       dismiss();
	    }
	 
	    /** Will cancel an in-progress connection, and close the socket */
		public void cancel() {
	        try {
	            mmSocket.close();
	            if(connectionManager != null)
	            	connectionManager.cancel();
	        } catch (IOException e) { }
	    }
	}

}