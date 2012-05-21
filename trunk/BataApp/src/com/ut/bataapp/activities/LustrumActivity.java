package com.ut.bataapp.activities;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.R;
import com.ut.bataapp.Utils;

/**
 * Activity voor het tonen van informatie over de Batavierenrace XL.  
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne van de Venis
 * @version 1.0
 */
public class LustrumActivity extends SherlockFragmentActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.lustrum_titel);
		getSupportActionBar().setHomeButtonEnabled(true);
		setContentView(R.layout.lustrum);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Utils.goHome(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
