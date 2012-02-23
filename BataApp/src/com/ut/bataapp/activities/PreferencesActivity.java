package com.ut.bataapp.activities;

import com.ut.bataapp.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferencesActivity extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.ThemeHolo);
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);
	    setContentView(R.layout.preferences_styles);
	}
}