package com.ut.bataapp.services;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;

import com.ut.bataapp.R;
import com.ut.bataapp.Utils;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.parser.PloegHandler;

public class BackgroundUpdater extends Service implements OnSharedPreferenceChangeListener {
	private static final int BACKGROUND_UPDATE = 1;
	private static final int MILLIS_IN_MINUTE = 60000;
	private static final int NOTIFICATION_ID_OFFSET = 1000;
		
	private class BackgroundUpdaterHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			System.out.println("Background update ongoing!");
			SharedPreferences keyValues = getSharedPreferences("teams_follow", Context.MODE_PRIVATE);
			Map<String, ?> favoteams = keyValues.getAll();
			for (Map.Entry<String, ?> entry : favoteams.entrySet())
				new DownloadXMLWithUpdateNotification().execute(entry);
			scheduleNextUpdate();
		}
				
		private class DownloadXMLWithUpdateNotification extends AsyncTask<Map.Entry<String, ?>, Void, Map.Entry<String, ?>> {
			@Override
			protected Map.Entry<String, ?> doInBackground(Map.Entry<String, ?>... favoEntry) {
				Map.Entry<String, ?> result = null;
				PloegHandler ploegHandler = new PloegHandler(String.format(mRes.getString(R.string.url_xml_ploeguitslag), favoEntry[0].getKey()));
				try {
					ploegHandler.getInputSource();
					if (ploegHandler.getStatus() == Response.OK_UPDATE)
						result = favoEntry[0];
				} catch (IOException e) {
					e.printStackTrace();
				}
				return result;
		    }
			
			@Override
			protected void onPostExecute(Map.Entry<String, ?> result) {
				if (result != null && mPrefs.getBoolean(mRes.getString(R.string.pref_background_update_notify), mRes.getBoolean(R.bool.pref_background_update_notify_default))) {
					int id = Integer.parseInt(result.getKey());
					String teamNaam = (String) result.getValue();
					NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
					Notification notification = new Notification(R.drawable.favorieten, String.format(mRes.getString(R.string.notification_bu_ticker), teamNaam), System.currentTimeMillis());
					Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
					intent.putExtra("index", id);
					PendingIntent contentIntent = PendingIntent.getActivity(BackgroundUpdater.this, 0, intent, 0);
					if (mPrefs.getBoolean(mRes.getString(R.string.pref_background_update_sound), mRes.getBoolean(R.bool.pref_background_update_sound_default)))
						notification.defaults |= Notification.DEFAULT_SOUND;
					if (mPrefs.getBoolean(mRes.getString(R.string.pref_background_update_vibrate), mRes.getBoolean(R.bool.pref_background_update_vibrate_default)))
						notification.defaults |= Notification.DEFAULT_VIBRATE;
					if (mPrefs.getBoolean(mRes.getString(R.string.pref_background_update_flash), mRes.getBoolean(R.bool.pref_background_update_flash_default))) {
						notification.ledARGB = mRes.getColor(R.color.notification_flashing_color);
						notification.ledOnMS = mRes.getInteger(R.integer.notification_flashing_on_ms);
						notification.ledOffMS = mRes.getInteger(R.integer.notification_flashing_off_ms);
						notification.flags |= Notification.FLAG_SHOW_LIGHTS;
					}
					notification.flags |= Notification.FLAG_AUTO_CANCEL;
					notification.setLatestEventInfo(BackgroundUpdater.this, mRes.getString(R.string.notification_bu_title), teamNaam, contentIntent);
					notificationManager.notify((NOTIFICATION_ID_OFFSET + id), notification);
				}
			}
		}
	}
	
	private SharedPreferences mPrefs;
	private Handler mHandler;
	private Resources mRes;
	
	@Override
	public void onCreate() {
		mRes = getResources();
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		mPrefs.registerOnSharedPreferenceChangeListener(this);
		mHandler = new BackgroundUpdaterHandler();
		scheduleNextUpdate();
	  }

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		mPrefs.unregisterOnSharedPreferenceChangeListener(this);
	}
		
	private void scheduleNextUpdate() {
		Calendar bataDag = Calendar.getInstance();
		bataDag.set(mRes.getInteger(R.integer.batadag_jaar), mRes.getInteger(R.integer.batadag_maand)-1, mRes.getInteger(R.integer.batadag_dag));
		Calendar today = Calendar.getInstance();
		Utils.clearTime(today);
	    int diff = today.compareTo(bataDag);
	    if (diff > 0)
	    	stopSelf();
	    else {
	    	long interval = Long.MIN_VALUE;
	    	/*if (diff < 0)
	    		interval = bataDag.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();*/
	    	interval = Math.max(interval, mPrefs.getInt(mRes.getString(R.string.pref_background_update_interval), mRes.getInteger(R.integer.pref_background_update_interval_default)) * MILLIS_IN_MINUTE);
	    	mHandler.removeMessages(BACKGROUND_UPDATE);
	    	mHandler.sendEmptyMessageDelayed(BACKGROUND_UPDATE, interval);
	    	System.out.println("Next background update scheduled at " + interval + "ms");
	    }
	}
	
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		if (key.equals(mRes.getString(R.string.pref_background_update_interval)))
			scheduleNextUpdate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
