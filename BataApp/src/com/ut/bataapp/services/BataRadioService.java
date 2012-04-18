package com.ut.bataapp.services;

import com.ut.bataapp.R;
import com.ut.bataapp.activities.BataRadioActivity;

import android.app.*;
import android.content.*;
import android.content.res.Resources;
import android.media.*;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.*;

import java.io.IOException;

/**
 * Service voor afspelen van BataRadio.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class BataRadioService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener {	
	// -- INNER CLASSES --
	
	/* Klasse voor het opnieuw proberen te verbinden met de stream.
	 * Onderdeel van ontwerpproject BataApp.
	 * @author Danny Bergsma
	 * @version 0.1 
	 */
	private class RetryHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (mMediaPlayer != null) {
				mMediaPlayer.reset();
				loadMP();
			}
		}
	}
	
	// -- CONSTANTEN --
	
	/** naam van Extra voor af te spelen URL */
	public static final String MP_URL = "MP_URL";
	/** maximaal aantal herhaalpogingen bij error */
	public static final int MAX_RETRIES = 3;
	/** factoren voor normaal/zacht volume */
	public static final float VOLUME_NORMAL = 1.0F, VOLUME_QUIET = 0.1F;
	/** id voor notificatie van deze service */
	public static final int NOTIFICATION_ID = 337;
	/** broadcastaction bij stoppen BataRadioService */
	public static final String BATARADIO_SERVICE_STOPPED = "com.ut.bataapp.BATARADIO_SERVICE_STOPPED";
	/** aantal milliseconden in een seconde */
	public static final int MILLIS_IN_SECOND = 1000;
	/* naam van wifilock */
	private static final String WIFILOCK_NAME = "brsLock";
	/* id voor retry-message */
	private static final int RETRY = 1;
	
	// -- INSTANTIEVARIABELEN --
	
	/* mediaspeler die gebruikt wordt om stream af te spelen */
	private MediaPlayer mMediaPlayer;
	/* Huidig aantal gedane herhaalpogingen.
	 * @invariant 0 <= mRetries <= 3
	 */
	private byte mRetries;
	/* URL van stream */
	private String mUrl;
	/* referentie naar wifilock (om ervoor te zorgen dat wifi niet naar slaapmodus gaat) */
	private WifiLock mWifiLock;
	/* referentie naar status bar notificatie */
	private Notification mNotification;
	/* referentie naar intent die BataRadio-activity opstart */
	private PendingIntent mPendingIntent;
	/* referentie naar app-resources */
	private Resources mRes;
	/* of service blijft proberen stream te laden, ongeacht hoevaak het in onError() terechtkomt */
	private boolean mKeepRetrying;
	/* handler om een retry te plannen */
	private Handler mRetryHandler;
	
	// -- HULPMETHODEN --
	
	/* Start de mediaspeler (async). Laat notificatie hiervan zien in status bar. */
	private void loadMP() {
		// optimalisaties:
		Resources res = mRes;
		Notification notification = mNotification;
		MediaPlayer mediaPlayer = mMediaPlayer;
		
		notification.tickerText = res.getString(R.string.notification_bataradio_buffering);
		notification.setLatestEventInfo(getApplicationContext(), res.getString(R.string.notification_bataradio_title), 
		                                res.getString(R.string.notification_bataradio_buffering), mPendingIntent);
		startForeground(NOTIFICATION_ID, notification);
		
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mediaPlayer.setDataSource(mUrl);
			mediaPlayer.prepareAsync();
		} catch (IOException e) {
			stopSelf();
		}
	}
	
	/* Initialiseert en start de mediaspeler (async). Acquiret wifilock. */
	private void initMP() {
		MediaPlayer mediaPlayer = (mMediaPlayer = new MediaPlayer());
		mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setOnCompletionListener(this);
		mRetries = 0;
		mWifiLock = ((WifiManager) getSystemService(Context.WIFI_SERVICE)).createWifiLock(WifiManager.WIFI_MODE_FULL, WIFILOCK_NAME);
		mWifiLock.acquire();
		loadMP();
	}
	
	/* Geeft mediaspeler en wifilock vrij. */
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
	
	// -- LIFECYCLEMETHODEN --
	
	@Override
	public void onCreate() {
		mRes = getResources();
		mKeepRetrying = false;
		mRetryHandler = new RetryHandler();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), BataRadioActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
		Notification notification = (mNotification = new Notification());
		notification.icon = R.drawable.radio;
		notification.flags |= Notification.FLAG_ONGOING_EVENT;	
		
		mUrl = intent.getExtras().getString(MP_URL);
		initMP();
		
		return START_REDELIVER_INTENT;
	}
	
	@Override
	public void onDestroy() {
		cleanup();
		sendBroadcast(new Intent(BATARADIO_SERVICE_STOPPED));
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	// -- CALLBACKMETHODEN --
		
	public void onPrepared(MediaPlayer player) { // wanneer buffering afgerond is
		int result = ((AudioManager) getSystemService(Context.AUDIO_SERVICE)).requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			// optimalisaties:
			Resources res = mRes; 
			Notification notification = mNotification;
			
			stopForeground(true); // haal oude notificatie weg
			// nieuwe notificatie:
			notification.tickerText = res.getString(R.string.notification_bataradio_playing);
			notification.setLatestEventInfo(getApplicationContext(), res.getString(R.string.notification_bataradio_title), 
			                                res.getString(R.string.notification_bataradio_playing), mPendingIntent);
			startForeground(NOTIFICATION_ID, notification);
			
			mKeepRetrying = true;
			player.start();
		}
    }

    public boolean onError(MediaPlayer mp, int what, int extra) { // bij error tijdens afspelen
    	if (mKeepRetrying) {
    		mRetryHandler.sendEmptyMessageDelayed(RETRY, mRes.getInteger(R.integer.bataradio_retry_every_seconds) * MILLIS_IN_SECOND);
    	} else if (mRetries++ < MAX_RETRIES) {
			mp.reset();
			loadMP();
		} else
			stopSelf();
		return true;
	}
	
	public void onAudioFocusChange(int focusChange) { // wanneer audio focus verandert
	    switch (focusChange) {
	        case AudioManager.AUDIOFOCUS_GAIN:
	            // resume playback:
	            if (mMediaPlayer == null) {
	            	stopForeground(true);
	            	initMP();
	            } else if (!mMediaPlayer.isPlaying()) 
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
	            	mMediaPlayer.setVolume(VOLUME_QUIET, VOLUME_QUIET);
	            break;
	    }
	}

	@Override
	public void onCompletion(MediaPlayer mp) { // wanneer internetverbinding onderbroken is geweest
		mMediaPlayer.reset();
		stopForeground(true); // haal oude notificatie weg
		loadMP();
	}	
}