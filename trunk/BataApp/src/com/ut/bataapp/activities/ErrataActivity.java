package com.ut.bataapp.activities;

import android.os.Bundle;
import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;

/**
 * Activity voor het Errata gedeelte. In deze activity wordt de errata_layout.xml layout geladen en getoond.  
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne van de Venis
 * @version 1.0
 */
public class ErrataActivity extends SherlockActivity  {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setHomeButtonEnabled(true);
		setContentView(R.layout.errata_layout);	   
		setTitle(R.string.informatie_titel_errata);
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