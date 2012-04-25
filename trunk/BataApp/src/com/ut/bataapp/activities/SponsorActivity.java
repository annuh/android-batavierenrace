package com.ut.bataapp.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;
import com.ut.bataapp.activities.SponsorInformatieActivity.SponsorInformatieAdapter;
import com.ut.bataapp.objects.Sponsor;

public class SponsorActivity extends SherlockFragmentActivity  {
	
	 @Override
   public void onCreate(Bundle savedInstanceState) {
	   setTitle("Sponsor");
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.sponsor);
	   getSupportActionBar().setDisplayShowHomeEnabled(true);
	   
	   ArrayList<Sponsor> sponsors = Sponsor.getSponsors();	   	   
	   ViewGroup c = (ViewGroup) findViewById(R.id.container_sponsor);
	   
	   for(int i = 0; i<sponsors.size(); i++) {
		   final int j = i;
		   Log.i("debugger", "hiero" + j);
		   Sponsor sponsor = sponsors.get(i);
		   Button button = (Button) LayoutInflater.from(getBaseContext()).inflate(R.drawable.button, c, false);
		   button.setText(sponsor.getNaam());
		   button.setOnClickListener(new View.OnClickListener() {
			   public void onClick(View v) {
				   
				   Intent intent = new Intent(getApplicationContext(), SponsorInformatieActivity.class);
				   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		       	   intent.putExtra("page", j);
				   startActivity(intent);
			   }
		   });
		   
		   c.addView(button);
	   }
	   
   }
   
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Utils.goHome(this);
		}
		
		return super.onOptionsItemSelected(item);
	}
   
}
