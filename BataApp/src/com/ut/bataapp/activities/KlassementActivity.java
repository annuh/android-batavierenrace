package com.ut.bataapp.activities;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.R;

public class KlassementActivity extends SherlockFragmentActivity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.klassement_activity);
	}
	
	/**
	 * Geeft naam van het klassement terug
	 * @return
	 */
	public String getKlassementNaam() {
		return this.getIntent().getStringExtra("index");
	}
}
