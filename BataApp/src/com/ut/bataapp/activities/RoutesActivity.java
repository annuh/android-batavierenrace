package com.ut.bataapp.activities;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


import com.actionbarsherlock.R;
import com.ut.bataapp.MainActivity;
import com.ut.bataapp.MainActivity.OverridePendingTransition;


public class RoutesActivity extends SherlockFragmentActivity  {
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   setTitle("Routes");
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.routes_fragment);
	   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
   }
   
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Settings")
		    .setIcon(R.drawable.ic_action_settings)
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);	
		return super.onCreateOptionsMenu(menu);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent intent = new Intent(this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
				//Get rid of the slide-in animation, if possible
	            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
	                OverridePendingTransition.invoke(this);
	            }
		}
		
		return super.onOptionsItemSelected(item);
	}

}
