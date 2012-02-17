package com.ut.bataapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.R;


public class RoutesActivity extends FragmentActivity  {
	
	private static int THEME = R.style.Theme_BataApp;
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   setTheme(THEME);
	   setTitle("Routes");
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.routes_fragment);
	   getSupportActionBar().setDisplayShowHomeEnabled(true);
   }
   
   
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Settings")
		    .setIcon(R.drawable.ic_action_edit)
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);	
		return super.onCreateOptionsMenu(menu);
		
	}

}
