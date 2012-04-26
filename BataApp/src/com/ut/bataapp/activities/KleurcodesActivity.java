package com.ut.bataapp.activities;

import android.os.Bundle;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;

public class KleurcodesActivity extends SherlockActivity  {
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.kleurcodes);
	   setTitle("Kleurcodes");
	   getSupportActionBar().setHomeButtonEnabled(true);
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