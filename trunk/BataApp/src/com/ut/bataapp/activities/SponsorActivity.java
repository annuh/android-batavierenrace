package com.ut.bataapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;

public class SponsorActivity extends SherlockFragmentActivity  {
	
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   setTitle("Sponsor");
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.sponsor);
	   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	   
	   ImageView sponsor1 = (ImageView) findViewById(R.id.sponsor1);
	   sponsor1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   
        	   Intent intent = new Intent(getApplicationContext(), SponsorItemActivity.class);
	       		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	       		intent.putExtra("page", "sponsor1");
	       		startActivity(intent);
           }
       }); 
	   
	   
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
