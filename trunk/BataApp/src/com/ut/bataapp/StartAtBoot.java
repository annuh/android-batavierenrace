package com.ut.bataapp;

import com.ut.bataapp.services.BackgroundUpdater;

import android.content.*;
import android.content.res.Resources;
import android.preference.PreferenceManager;

/**
 * Broadcastreceiver voor boot completed-action.
 * Start background update service op.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class StartAtBoot extends BroadcastReceiver {
	@Override
    public void onReceive(Context context, Intent intent) {
		Resources res = context.getResources();
        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(res.getString(R.string.pref_background_update), res.getBoolean(R.bool.pref_background_update_default))) {
        	Intent startServiceIntent = new Intent(context, BackgroundUpdater.class);
        	context.startService(startServiceIntent);
        }
    }
}