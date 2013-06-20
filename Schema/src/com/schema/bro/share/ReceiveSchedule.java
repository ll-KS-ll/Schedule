package com.schema.bro.share;

import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.schema.bro.R;
import com.schema.bro.ShareActivity;
import com.schema.bro.ks.TextLoaderAnimator;

public class ReceiveSchedule extends DialogFragment{
	
	private Context context;
	private BluetoothAdapter mBluetoothAdapter;
	private AcceptThread connect;
	private static final int REQUEST_ENABLE_BT = 7;
	private View view;
	private TextLoaderAnimator animator;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    view = inflater.inflate(R.layout.receive_dialog, null);
	    
	    builder.setView(view).setTitle("Ta emot schema")
	           .setNegativeButton("Avsluta", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   if(connect != null)
	            		   connect.cancel();
	            	   mBluetoothAdapter.cancelDiscovery();
	               }
	           });     
	    
	    final ImageView imageView = (ImageView) view.findViewById(R.id.recieveDialogTextInfo);
		animator = new TextLoaderAnimator(getActivity(), imageView, "Väntar på enhet");
		
	    return builder.create();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		getDialog().setCanceledOnTouchOutside(false);
		
		animator.start();
		
		if(mBluetoothAdapter == null)
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if(mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivityForResult(discoverableIntent, REQUEST_ENABLE_BT);
		}else{
			connectBluetooth();
		}
			
	}
	
	private void connectBluetooth() {
		context = this.getActivity();
		connect = new AcceptThread();
		connect.start();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_CANCELED) {
			Toast toast = Toast.makeText(getActivity(), "Eneheten behöver vara synlig för att ta emot scehman", Toast.LENGTH_SHORT);
			toast.show();
			dismiss();
		}else{
			connectBluetooth();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(connect != null){
			connect.cancel();
			try {
				connect.join();
			} catch (InterruptedException e) { }
		}
	}
	
	public void onConnected(){
		view.post(new Runnable() {
	        public void run() {
	        	animator.changeText("Tar emot data");
	        }
		});
	}
	
	public void onSent(){
		view.post(new Runnable() {
	        public void run() {
	        	getDialog().setTitle("Schema mottaget");
	        	animator.changeText("Nytt schema tillagt!");
	        	animator.stop();
	        }
		});
	}
	
	private class AcceptThread extends Thread {
	    
		private final BluetoothServerSocket mmServerSocket;
		private ManageBluetoothConnection connectionManager;
		
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
	            	
	            	onConnected();
	            	connectionManager = new ManageBluetoothConnection(context, socket);
	                connectionManager.start();
	                
	                try {
						Thread.sleep(10);
					} catch (InterruptedException e) { }
	                
	                while(connectionManager.isAlive()){
	                	try {
							Thread.sleep(5);
						} catch (InterruptedException e) { }
	                }
	                
	                try {
						mmServerSocket.close();
					} catch (IOException e) { }
	                
	                try {
	     	    	   Thread.sleep(100);
	     	       } catch (InterruptedException e) { }
	                
	                onSent();
	                break;
	            }
	        }
	        
	    }
	 
	    /** Will cancel the listening socket, and cause the thread to finish */
		public void cancel() {
	        try {
	            mmServerSocket.close();
	            if(connectionManager != null)
	            	connectionManager.cancel();
	        } catch (IOException e) { }
	    }
	}
}
