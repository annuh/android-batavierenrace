package com.ut.bataapp.activities;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.R;
import com.ut.bataapp.Utils;
import com.ut.bataapp.fragments.KlassementFragment;

/**
 * Activity waar een bepaald klassement wordt getoond. Een lijst met alle teams wordt opgehaald, gesorteerd op plaats in het klassement 
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne van de Venis
 * @version 1.0
 */
public class KlassementActivity extends SherlockFragmentActivity  {
	/** Naam van variabele waar naam van het klassement wordt opgeslagen. */
	public static final String INSTANCE_STATE_INDEX = "index";
	/** Naam van het klassement */
	private String mIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mIndex = ((savedInstanceState == null) ? getIntent().getStringExtra(INSTANCE_STATE_INDEX) : savedInstanceState.getString(INSTANCE_STATE_INDEX));
	    setTitle(mIndex);
	    getSupportActionBar().setHomeButtonEnabled(true);
	    KlassementFragment klassement = new KlassementFragment();
	    Bundle args = new Bundle();
	    args.putString(INSTANCE_STATE_INDEX, mIndex);
	    klassement.setArguments(args);
	    setContentView(R.layout.viewport);
	    getSupportFragmentManager().beginTransaction().add(R.id.viewport_content, klassement).commit();
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
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(INSTANCE_STATE_INDEX, mIndex);
	}
}
