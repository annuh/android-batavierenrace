package com.ut.bataapp.activities;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.R;
import com.ut.bataapp.Utils;
import com.ut.bataapp.fragments.KlassementFragment;

public class KlassementActivity extends SherlockFragmentActivity  {
	public static final String INSTANCE_STATE_INDEX = "index";
	
	private String mIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mIndex = ((savedInstanceState == null) ? getIntent().getStringExtra("index") : savedInstanceState.getString(INSTANCE_STATE_INDEX));
	    setTitle(mIndex);
	    getSupportActionBar().setHomeButtonEnabled(true);
	    KlassementFragment klassement = new KlassementFragment();
	    Bundle args = new Bundle();
	    args.putString("index", mIndex);
	    klassement.setArguments(args);
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
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//super.onSaveInstanceState(outState);
		outState.putString(INSTANCE_STATE_INDEX, mIndex);
	}
}
