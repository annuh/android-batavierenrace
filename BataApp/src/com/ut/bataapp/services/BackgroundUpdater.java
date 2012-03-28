package com.ut.bataapp.services;

import com.ut.bataapp.R;
import com.ut.bataapp.activities.TeamActivity;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;

public class BackgroundUpdater  extends IntentService {
	
	public BackgroundUpdater() {
		super(BackgroundUpdater.class.getSimpleName());
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onHandleIntent(Intent intent) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(listener);
		
		NotificationManager notificationManager = (NotificationManager) 
				getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.icon,
				"A new notification", System.currentTimeMillis());
		// Hide the notification after its selected
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		Intent tointent = new Intent(this, TeamActivity.class);
		PendingIntent activity = PendingIntent.getActivity(this, 0, tointent, 0);
		notification.setLatestEventInfo(this, "This is the title",
				"This is the text", activity);
		notification.number += 1;
		notificationManager.notify(0, notification);
		
		scheduleNextUpdate();
	}
	
	private void scheduleNextUpdate() {
		Intent intent = new Intent(this, this.getClass());
		PendingIntent pendingIntent =
		    PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int frequentie = prefs.getInt("updatefrequentie", 0);
		
		long currentTimeMillis = System.currentTimeMillis();

		long nextUpdateTimeMillis = currentTimeMillis + frequentie * DateUtils.MINUTE_IN_MILLIS;
		Time nextUpdateTime = new Time();
		nextUpdateTime.set(nextUpdateTimeMillis);
		
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC, nextUpdateTimeMillis, pendingIntent);
	}
	
	OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
		public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
			if(key.equals("updatefrequentie")) {
				Log.d("UPDATER", "UPDATE FREQ GEWIJZIGD");
			}
		}
	};
}
