package com.ut.bataapp;

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.R;
import com.ut.bataapp.activities.PreferencesActivity;
import com.ut.bataapp.activities.WeerActivity;


public class MainActivity extends FragmentActivity {
	
	private static int THEME = R.style.Theme_BataApp;
	private final int MENU_SETTINGS = Menu.FIRST;
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   setTheme(THEME);
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.actionbar_styles);
       
	   Button btn_routes = (Button) findViewById(R.id.dashboard_etappes);
	   btn_routes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // Launching News Feed Screen
               Intent i = new Intent(getApplicationContext(), RoutesActivity.class);
               startActivity(i);
           }
       });
	   
	   Button btn_teams = (Button) findViewById(R.id.dashboard_teams);
	   btn_teams.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // Launching News Feed Screen
               Intent i = new Intent(getApplicationContext(), TeamsActivity.class);
               startActivity(i);
           }
       });
	   
	   Button btn_weer = (Button) findViewById(R.id.dashboard_weer);
	   btn_weer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // Launching News Feed Screen
               Intent i = new Intent(getApplicationContext(), WeerActivity.class);
               startActivity(i);
           }
       });
	   
	   // Aantal dagen
	   Calendar nu = Calendar.getInstance();
	   Calendar batadag = Calendar.getInstance();
	   batadag.set(2012, 4, 28);
	   long dagenVerschil = (batadag.getTimeInMillis() - nu.getTimeInMillis())/ (24 * 60 * 60 * 1000);
	   TextView viewDagen = (TextView) findViewById(R.id.dashboard_time);
	   viewDagen.setText("Het duurt nog "+dagenVerschil+" dagen!");
   }
   
   @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,MENU_SETTINGS,Menu.NONE,"Settings")
		    .setIcon(R.drawable.ic_action_edit)
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		
		return super.onCreateOptionsMenu(menu);
	}
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
   	switch (item.getItemId()) {
   	// We have only one menu option
   	case MENU_SETTINGS:
   		// Launch Preference activity
   		Intent i = new Intent(this, PreferencesActivity.class);
   		startActivity(i);
   		break;

   	}
   	return true;
   }
}
