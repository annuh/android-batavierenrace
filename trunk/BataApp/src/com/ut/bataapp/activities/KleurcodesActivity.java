package com.ut.bataapp.activities;

import android.os.Bundle;
import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;

public class KleurcodesActivity extends SherlockFragmentActivity  {
	
	 @Override
   public void onCreate(Bundle savedInstanceState) {
	   setTitle("Kleurcodes");
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.kleurcodes);
	   getSupportActionBar().setDisplayShowHomeEnabled(true);
	   
   }
   
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Utils.goHome(getApplicationContext());
		}
		
		return super.onOptionsItemSelected(item);
	}
   
}
