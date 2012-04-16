package com.ut.bataapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
	   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	   
	   ImageView sponsor1 = (ImageView) findViewById(R.id.sponsor1);
	   sponsor1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   
        	   Intent intent = new Intent(getApplicationContext(), SponsorInformatieActivity.class);
	       		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	       		intent.putExtra("page", "1");
	       		startActivity(intent);
           }
       }); 
	   
	   ImageView sponsor2 = (ImageView) findViewById(R.id.sponsor2);
	   sponsor2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   
        	   Intent intent = new Intent(getApplicationContext(), SponsorInformatieActivity.class);
	       		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	       		intent.putExtra("page", "2");
	       		startActivity(intent);
           }
       });
	   
	   ImageView sponsor3 = (ImageView) findViewById(R.id.sponsor3);
	   sponsor3.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   
        	   Intent intent = new Intent(getApplicationContext(), SponsorInformatieActivity.class);
	       		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	       		intent.putExtra("page", "3");
	       		startActivity(intent);
           }
       });
	   
	   ImageView sponsor4 = (ImageView) findViewById(R.id.sponsor4);
	   sponsor4.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   
        	   Intent intent = new Intent(getApplicationContext(), SponsorInformatieActivity.class);
	       		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	       		intent.putExtra("page", "4");
	       		startActivity(intent);
           }
       });
	   
	   ImageView sponsor5 = (ImageView) findViewById(R.id.sponsor5);
	   sponsor5.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   
        	   Intent intent = new Intent(getApplicationContext(), SponsorInformatieActivity.class);
	       		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	       		intent.putExtra("page", "5");
	       		startActivity(intent);
           }
       });
	   
	   ImageView sponsor6 = (ImageView) findViewById(R.id.sponsor6);
	   sponsor6.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   
        	   Intent intent = new Intent(getApplicationContext(), SponsorInformatieActivity.class);
	       		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	       		intent.putExtra("page", "6");
	       		startActivity(intent);
           }
       });
	   
	   ImageView sponsor7 = (ImageView) findViewById(R.id.sponsor7);
	   sponsor7.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   
        	   Intent intent = new Intent(getApplicationContext(), SponsorInformatieActivity.class);
	       		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	       		intent.putExtra("page", "7");
	       		startActivity(intent);
           }
       });
	   
	   ImageView sponsor8 = (ImageView) findViewById(R.id.sponsor8);
	   sponsor8.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   
        	   Intent intent = new Intent(getApplicationContext(), SponsorInformatieActivity.class);
	       		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	       		intent.putExtra("page", "8");
	       		startActivity(intent);
           }
       });
	   
	   ImageView sponsor9 = (ImageView) findViewById(R.id.sponsor9);
	   sponsor9.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   
        	   Intent intent = new Intent(getApplicationContext(), SponsorInformatieActivity.class);
	       		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	       		intent.putExtra("page", "9");
	       		startActivity(intent);
           }
       });
	   
	   ImageView sponsor10 = (ImageView) findViewById(R.id.sponsor10);
	   sponsor10.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   
        	   Intent intent = new Intent(getApplicationContext(), SponsorInformatieActivity.class);
	       		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	       		intent.putExtra("page", "10");
	       		startActivity(intent);
           }
       });
	   
	   ImageView sponsor11 = (ImageView) findViewById(R.id.sponsor11);
	   sponsor11.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   
        	   Intent intent = new Intent(getApplicationContext(), SponsorInformatieActivity.class);
	       		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	       		intent.putExtra("page", "11");
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
