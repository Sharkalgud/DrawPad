package com.example.drawer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class PaintActivity extends Activity {
	private BluetoothAdapter BA;
	private Set<BluetoothDevice>pairedDevices;
	private ListView lv;
	private BluetoothSocket BS;
	private OutputStream os;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		BrushView view= new BrushView(this, os);
		setContentView(view);
		Log.w("Pulse","beep");
		addContentView(view.btnEraseAll, view.params);
		BA= BluetoothAdapter.getDefaultAdapter();
		pairedDevices= BA.getBondedDevices();
		Log.w("devices teathered",pairedDevices.size()+"  ");
		if(pairedDevices.size()==0)
		{
			if(!BA.isEnabled())
			{
				Intent turnOn= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(turnOn,0);
				//Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_LONG).show();
			}
			Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			startActivityForResult(getVisible, 0);
			try {
				Thread.sleep(25000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		ArrayList list= new ArrayList();
		for(BluetoothDevice bt : pairedDevices)
		{
			try {
				BS= bt.createRfcommSocketToServiceRecord(bt.getUuids()[0].getUuid());
				Log.w("ldsakjfasd;lfjsaerow",bt.getUuids()[0].getUuid().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				os= BS.getOutputStream();
				Log.w("output stream", "output stream");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.w("asdf;lk","OutputStream Recieved");
		view.setOutputStream(os);
		//ConnectThread connect= new ConnectThread(device);


	}
	@Override
	protected void onPause()
	{
		super.onPause();
		finish();
	}
}

