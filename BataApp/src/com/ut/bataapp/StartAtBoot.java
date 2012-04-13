package com.ut.bataapp;

import com.ut.bataapp.services.BackgroundUpdater;

import android.content.*;
import android.preference.PreferenceManager;

public class StartAtBoot extends BroadcastReceiver {
	@Override
    public void onReceive(Context context, Intent intent) {
        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getResources().getString(R.string.pref_background_update), context.getResources().getBoolean(R.bool.pref_default_background_update))) {
        	Intent startServiceIntent = new Intent(context, BackgroundUpdater.class);
        	context.startService(startServiceIntent);
        }
    }
}
