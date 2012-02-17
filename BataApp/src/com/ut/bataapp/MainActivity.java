package com.ut.bataapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.R;


public class MainActivity extends FragmentActivity {
	
	private static int THEME = R.style.Theme_BataApp;
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   setTheme(THEME);
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.actionbar_styles);
       
	   Button btn_routes = (Button) findViewById(R.id.dashboard_routes);
	   btn_routes.setOnClickListener(new View.OnClickListener() {
		   
           @Override
           public void onClick(View view) {
               // Launching News Feed Screen
               Intent i = new Intent(getApplicationContext(), RoutesActivity.class);
               startActivity(i);
           }
       });
   }
   
   @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Settings")
		    .setIcon(R.drawable.ic_action_edit)
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		
		return super.onCreateOptionsMenu(menu);
	}
}
