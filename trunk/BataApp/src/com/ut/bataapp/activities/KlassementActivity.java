package com.ut.bataapp.activities;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.R;
import com.ut.bataapp.Utils;
import com.ut.bataapp.fragments.KlassementFragment;

public class KlassementActivity extends SherlockFragmentActivity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setTitle(getKlassementNaam());
	    getSupportActionBar().setDisplayShowHomeEnabled(true);
	    KlassementFragment klassement = new KlassementFragment();
	    klassement.setArguments(getIntent().getExtras());
	    setContentView(R.layout.viewport);
	    getSupportFragmentManager().beginTransaction().add(R.id.viewport_content, klassement).commit();
	    
	    //setContentView(R.layout.klassement_activity);
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
	
	/**
	 * Geeft naam van het klassement terug
	 * @return
	 */
	public String getKlassementNaam() {
		return this.getIntent().getStringExtra("index");
	}
}
