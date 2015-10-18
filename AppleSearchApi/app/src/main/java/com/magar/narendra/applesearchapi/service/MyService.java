package com.magar.narendra.applesearchapi.service;

import java.io.IOException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service 
{
	private static final String TAG = "MyService";
	MediaPlayer player;
	public String url="";
	public String s="";
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	
	@Override
	public void onCreate() 
	{



		  PhoneStateListener phoneStateListener = new PhoneStateListener()
	        {
	            @Override
	            public void onCallStateChanged(int state, String incomingNumber) {
	                if (state == TelephonyManager.CALL_STATE_RINGING) 
	                {

	                	player.pause();
	                	}

	                else if(state == TelephonyManager.CALL_STATE_IDLE) 
	                {

	                	player.start();
	                } else if(state == TelephonyManager.CALL_STATE_OFFHOOK)
	                {

	                	
	                }
	                super.onCallStateChanged(state, incomingNumber);
	            }
	        };
	        
	        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
	        if(mgr != null) 
	        {
	            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	        }


	}

	@Override
	public void onDestroy() 
	{
		player.stop();

		SharedPreferences prefs = getSharedPreferences("URLP", MyService.MODE_PRIVATE);
		prefs.edit().putBoolean("play", false).commit();

	}
	
	@Override
	public void onStart(Intent intent, int startid) 
	{
		super.onStart(intent, startid);
		Toast.makeText(this, "Streaming Please wait..", Toast.LENGTH_SHORT).show();


		ConnectivityManager cn=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nf=cn.getActiveNetworkInfo();
		if(nf != null && nf.isConnected()==true )
		{




			player=new MediaPlayer();
			try {
				SharedPreferences prefs = getSharedPreferences("URLP", MODE_PRIVATE);
				url = prefs.getString("URL", null);
				player.setAudioStreamType(AudioManager.STREAM_MUSIC);
				player.setDataSource(url);
				player.prepare();
				player.start();

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
				player.release();
				player = new MediaPlayer();
				player.reset();
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(player.isPlaying())
			{
				SharedPreferences prefs = getSharedPreferences("URLP", MyService.MODE_PRIVATE);
				prefs.edit().putBoolean("play", true).commit();



			}


			player.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
				@Override
				public void onCompletion(MediaPlayer mp)
				{
					SharedPreferences prefs = getSharedPreferences("URLP", MyService.MODE_PRIVATE);
					prefs.edit().putBoolean("play", false).commit();

				}
			});

			player.setLooping(false);
		}


		
		
	}


	
}
