package com.ut.bataapp.services;

import java.util.ArrayList;
import java.util.Map;

import com.ut.bataapp.R;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.objects.Team;

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

	@Override
	protected void onHandleIntent(Intent intent) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(listener);
		
		//ArrayList<Team> teams = new ArrayList<Team>();
		SharedPreferences keyValues = this.getSharedPreferences("teams_follow", Context.MODE_PRIVATE);
        Map<String, ?> favoteams = keyValues.getAll();
    	for (Map.Entry<String, ?> entry : favoteams.entrySet()) {
    		//if(api.getTeamByID((Integer) entry.getValue()).getStatus() == Response.OK_UPDATE) {
    		//	makeNotification((Integer) entry.getValue(), entry.getKey());
    		//}
    	}
		
		scheduleNextUpdate();
	}
	
	@SuppressWarnings({ "deprecation", "unused" })
	private void makeNotification(int id, String naam) {
		NotificationManager notificationManager = (NotificationManager) 
				getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.icon,
				"A new notification", System.currentTimeMillis());
		// Hide the notification after its selected
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.number += 1;
		
		Intent tointent = new Intent(this, TeamActivity.class);
		tointent.putExtra("index", id);
		PendingIntent activity = PendingIntent.getActivity(this, 0, tointent, 0);
		notification.setLatestEventInfo(this, "Batavierenrace",
				"Nieuwe update!", activity);
		
		notificationManager.notify(0, notification);
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
