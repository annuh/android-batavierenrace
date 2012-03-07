package com.ut.bataapp.activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.R;
import com.ut.bataapp.MainActivity;
import com.ut.bataapp.MainActivity.OverridePendingTransition;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Bericht;

public class MessagesActivity extends FragmentActivity  {
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   setTitle("Messages");
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.messages_fragment);
	   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	   
	   LinearLayout layout = (LinearLayout) findViewById(R.id.messages_lijst);	   
	   Bericht[] berichten = api.getBerichten();
	   
	   for(int i = 0; i<berichten.length; i++){
		   
		   TextView child = new TextView(this);
		   child.setText(berichten[i].getAfzender());
		   layout.addView(child);
		   
	   }
	   
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
