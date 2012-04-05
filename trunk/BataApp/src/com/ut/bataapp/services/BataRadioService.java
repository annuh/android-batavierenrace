package com.ut.bataapp.services;

import java.io.IOException;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.IBinder;
import android.os.PowerManager;

import com.ut.bataapp.R;
import com.ut.bataapp.activities.BataRadioActivity;

public class BataRadioService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, AudioManager.OnAudioFocusChangeListener {
	public static final String MP_URL = "MP_URL";
	public static final int MAX_RETRIES = 3;
	public static final float VOLUME_NORMAL = 1.0F, VOLUME_QUIET = 0.1F;
	public static final int NOTIFICATION_ID = 1337;
	
	private MediaPlayer mMediaPlayer;
	private byte mRetries;
	private String mUrl;
	private AudioManager mAudioManager;
	private WifiLock mWifiLock;
	
	@Override
	public void onCreate() {
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
	}
	
	private void loadMP() {
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mMediaPlayer.setDataSource(mUrl);
			mWifiLock = ((WifiManager) getSystemService(Context.WIFI_SERVICE)).createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock");
			mWifiLock.acquire();
			mMediaPlayer.prepareAsync();
		} catch (IOException e) {
			stopSelf();
		}
	}
	
	private void initMP() {
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.setOnErrorListener(this);
		mRetries = 0;
		loadMP();
	}
	
	public void onPrepared(MediaPlayer player) {
		int result = mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
			player.start();
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
		if (mRetries++ < MAX_RETRIES) {
			mp.reset();
			loadMP();
		} else
			stopSelf();
		return true;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), BataRadioActivity.class),
		                     PendingIntent.FLAG_UPDATE_CURRENT);
		Notification notification = new Notification();
		notification.tickerText = getResources().getString(R.string.bataradio);
		notification.icon = R.drawable.radio;
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		notification.setLatestEventInfo(getApplicationContext(), getResources().getString(R.string.bataradio), "", pi);
		startForeground(NOTIFICATION_ID, notification);
		mUrl = intent.getExtras().getString(MP_URL);
		initMP();
		return START_REDELIVER_INTENT;
	}
	
	public void onAudioFocusChange(int focusChange) {
	    switch (focusChange) {
	        case AudioManager.AUDIOFOCUS_GAIN:
	            // resume playback
	            if (mMediaPlayer == null) 
	            	initMP();
	            else if (!mMediaPlayer.isPlaying()) 
	            	mMediaPlayer.start();
	            mMediaPlayer.setVolume(VOLUME_NORMAL, VOLUME_NORMAL);
	            break;

	        case AudioManager.AUDIOFOCUS_LOSS:
	            // Lost focus for an unbounded amount of time: stop playback and release media player
	            cleanup();
	            break;

	        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
	            // Lost focus for a short time, but we have to stop
	            // playback. We don't release the media player because playback
	            // is likely to resume
	            if (mMediaPlayer.isPlaying()) 
	            	mMediaPlayer.pause();
	            break;

	        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
	            // Lost focus for a short time, but it's ok to keep playing
	            // at an attenuated level
	            if (mMediaPlayer.isPlaying()) 
	            	mMediaPlayer.setVolume(0.1f, 0.1f);
	            break;
	    }
	}
	
	private void cleanup() {
		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying())
				mMediaPlayer.stop();
	    	mMediaPlayer.release();
	    	mMediaPlayer = null;
	    	if (mWifiLock != null)
	    		mWifiLock.release();
	    }
	}
	
	@Override
	public void onDestroy() {
		cleanup();
	}
}