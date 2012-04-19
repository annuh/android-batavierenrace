package com.ut.bataapp;

import com.ut.bataapp.services.BackgroundUpdater;

import android.content.*;

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
		Intent startServiceIntent = new Intent(context, BackgroundUpdater.class);
        context.startService(startServiceIntent);
    }
}