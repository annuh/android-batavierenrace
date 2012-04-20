package com.ut.bataapp.activities;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.R;
import com.ut.bataapp.fragments.KlassementFragment;

public class KlassementActivity extends SherlockFragmentActivity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setTitle(getKlassementNaam());
	    KlassementFragment klassement = new KlassementFragment();
	    klassement.setArguments(getIntent().getExtras());
	    setContentView(R.layout.viewport);
	    getSupportFragmentManager().beginTransaction().add(R.id.viewport_content, klassement).commit();
	    
	    //setContentView(R.layout.klassement_activity);
	}
	
	/**
	 * Geeft naam van het klassement terug
	 * @return
	 */
	public String getKlassementNaam() {
		return this.getIntent().getStringExtra("index");
	}
}
