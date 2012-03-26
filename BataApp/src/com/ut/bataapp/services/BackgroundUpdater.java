package com.ut.bataapp.services;

import android.app.AlarmManager;
import android.app.IntentService;
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

	@Override
	protected void onHandleIntent(Intent intent) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(listener);
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
