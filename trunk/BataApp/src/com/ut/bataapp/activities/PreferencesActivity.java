package com.ut.bataapp.activities;

import com.ut.bataapp.*;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;

/**
 * Instellingenactivity. Je kunt meegeven of deze activity wel/niet (default) vanuit setup wordt gestart.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class PreferencesActivity extends SherlockPreferenceActivity {
	// -- CONSTANTEN --
	
	/** Extra-lookup key voor aangeven of deze activity wel/niet vanuit setup wordt gestart */
	public static final String EXTRA_IN_SETUP = "inSetup";
	/* savedInstanceState-lookup key voor aangeven of deze activity wel/niet vanuit setup wordt gestart */
	private static final String INSTANCE_STATE_IN_SETUP = "inSetup";
	
	// -- INSTANTIEVARIABELEN --
	
	/* geeft aan of deze activity gestart is vanuit setup */
	private boolean mInSetup;

	// -- LIFECYCLEMETHODEN --
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mInSetup = (getIntent() == null ? savedInstanceState.getBoolean(INSTANCE_STATE_IN_SETUP) : getIntent().getBooleanExtra(EXTRA_IN_SETUP, false));
	    addPreferencesFromResource(R.xml.preferences);
	    setContentView(R.layout.preferences_styles);
	    setTitle(R.string.title_prefs);
	    getSupportActionBar().setHomeButtonEnabled(true);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(INSTANCE_STATE_IN_SETUP, mInSetup);
	}
	
	// -- CALLBACKMETHODEN --
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (mInSetup) {
				// ga terug naar setupscreen:
				setResult(RESULT_OK);
				finish();
			} else
				Utils.goHome(this);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}