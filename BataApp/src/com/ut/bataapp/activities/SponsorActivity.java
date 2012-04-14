package com.ut.bataapp.activities;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.MainActivity;
import com.ut.bataapp.Utils;
import com.ut.bataapp.MainActivity.OverridePendingTransition;
import com.ut.bataapp.api.api;

public class SponsorActivity extends SherlockFragmentActivity  {
	
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   setTitle("Sponsor");
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.sponsor);
	   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	   
	   
	   
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
