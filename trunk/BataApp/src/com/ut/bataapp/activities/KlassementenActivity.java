package com.ut.bataapp.activities;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.MainActivity;
import com.ut.bataapp.MainActivity.OverridePendingTransition;
import com.ut.bataapp.api.api;

public class KlassementenActivity extends SherlockFragmentActivity  {
	
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   setTitle("Klassement");
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.klassementen);
	   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	   
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
