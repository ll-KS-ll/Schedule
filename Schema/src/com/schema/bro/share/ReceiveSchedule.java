package com.schema.bro.share;

import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.schema.bro.R;
import com.schema.bro.ShareActivity;

public class ReceiveSchedule extends DialogFragment{
	
    // Server listen, waits on client, wait for pair, discoverable, recive
    // Client connect, starts connection, try pair, discover, give
	
	
	private BluetoothAdapter mBluetoothAdapter;
	private AcceptThread connect;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View view = inflater.inflate(R.layout.receive_dialog, null);
	    
	    builder.setView(view).setTitle("Väntar på schema...")
	           .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   if(connect != null)
	            		   connect.cancel();
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

		if(mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}else{
			connectBluetooth();
		}
			
	}
	
	private void connectBluetooth() {
		connect = new AcceptThread();
		connect.start();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_CANCELED) {
			connectBluetooth();
		}else if(resultCode == Activity.RESULT_CANCELED){
			Toast toast = Toast.makeText(getActivity(), "Enehetn behöver vara synlig för att ta emot scehman", Toast.LENGTH_SHORT);
			toast.show();
			dismiss();
		}
	}
	
	private class AcceptThread extends Thread {
	    
		private final BluetoothServerSocket mmServerSocket;
		
	    public AcceptThread() {
	        // Use a temporary object that is later assigned to mmServerSocket,
	        // because mmServerSocket is final
	        BluetoothServerSocket tmp = null;
	        try {
	            // MY_UUID is the app's UUID string, also used by the client code
	        	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	        	tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("Schema", UUID.fromString(ShareActivity.UUID));
	        } catch (IOException e) { }
	        mmServerSocket = tmp;
	    }
	    
	    public void run() {
	        BluetoothSocket socket = null;
	        // Keep listening until exception occurs or a socket is returned
	        while (true) {
	            try {
	                socket = mmServerSocket.accept();
	            } catch (IOException e) {
	                break;
	            }
	            // If a connection was accepted
	            if (socket != null) {
	                // Do work to manage the connection (in a separate thread)
	            	Log.e("Schema Bluetooth", "Connected!!");
	                //ManageBluetoothConnection connectionManager = new ManageBluetoothConnection(socket);
	                //connectionManager.run();
	                
	                try {
						mmServerSocket.close();
					} catch (IOException e) { }
	                break;
	            }
	        }
	    }
	 
	    /** Will cancel the listening socket, and cause the thread to finish */
		public void cancel() {
	        try {
	            mmServerSocket.close();
	        } catch (IOException e) { }
	    }
	}
}
