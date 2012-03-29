package com.ut.bataapp.activities;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.MainActivity;
import com.ut.bataapp.MainActivity.OverridePendingTransition;
import com.ut.bataapp.adapters.BerichtAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Bericht;


public class BerichtenActivity extends SherlockListActivity  {
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	   setTitle("Berichten");
	   super.onCreate(savedInstanceState);
	   ArrayList<Bericht> values = api.getBerichten();
	   setListAdapter(new BerichtAdapter(BerichtenActivity.this, values));
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
