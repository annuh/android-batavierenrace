package com.ut.bataapp;

import java.util.Calendar;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.c2dm.C2DMessaging;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ut.bataapp.activities.*;
import com.ut.bataapp.services.C2DMReceiver;


public class MainActivity extends SherlockFragmentActivity {	
	
	private final int MENU_SETTINGS = Menu.FIRST;
	private final int MENU_MESSAGE = Menu.FIRST+1;
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   
	   this.getSupportActionBar().setTitle("Batavierenrace");
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.actionbar_styles);
	   setupC2DM();
	   //register();
	   Button btn_routes = (Button) findViewById(R.id.dashboard_etappes);
	   btn_routes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // Launching News Feed Screen
               Intent i = new Intent(getApplicationContext(), EtappesActivity.class);
               startActivity(i);
           }
       });
	   
	   
	   Button btn_favorieten = (Button) findViewById(R.id.dashboard_favorieten);
	   btn_favorieten.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // Launching News Feed Screen
               Intent i = new Intent(getApplicationContext(), FavoTeamsActivity.class);
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
	   
	   Button btn_klassement = (Button) findViewById(R.id.dashboard_klassement);
	   btn_klassement.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // Launching News Feed Screen
               Intent i = new Intent(getApplicationContext(), KlassementenActivity.class);
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
	   
	   Button btn_informatie = (Button) findViewById(R.id.dashboard_informatie);
	   btn_informatie.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // Launching News Feed Screen
               Intent i = new Intent(getApplicationContext(), InformatieActivity.class);
               startActivity(i);
           }
       });
	   
	   // Aantal dagen
	   Calendar nu = Calendar.getInstance();
	   Calendar batadag = Calendar.getInstance();
	   batadag.set(2012, 4-1, 28);
	   long dagenVerschil = (batadag.getTimeInMillis() - nu.getTimeInMillis())/ (24 * 60 * 60 * 1000);
	   TextView viewDagen = (TextView) findViewById(R.id.dashboard_time);
	   viewDagen.setText("Het duurt nog " +dagenVerschil + " dagen!");
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,MENU_MESSAGE,Menu.NONE,"Berichten")
		 .setIcon(R.drawable.ic_action_mail)
		 .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		   
		menu.add(0,MENU_SETTINGS,Menu.NONE,"Instellingen")
		 .setIcon(R.drawable.ic_action_settings)
		 .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		return super.onCreateOptionsMenu(menu);
   }
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                
                case MENU_SETTINGS:
                        // Launch Preference activity
                        Intent i = new Intent(this, PreferencesActivity.class);
                        startActivity(i);
                        break;
                        
                case MENU_MESSAGE:
                        // Launch Messages activity
                        Intent j = new Intent(getApplicationContext(), BerichtenActivity.class);
                        startActivity(j);
                        break;

        }
        return true;
   }
   
   public static final class OverridePendingTransition {
       public static void invoke(Activity activity) {
           activity.overridePendingTransition(0, 0);
       }
   }
   
   public void setupC2DM() {
	   Log.d("C2DM", "START");
;	   SharedPreferences prefs = getSharedPreferences(C2DMessaging.PREFERENCE, Context.MODE_PRIVATE);
	   if ((Build.VERSION.SDK_INT >= 8) && (C2DMessaging.shouldRegisterForPush(getApplicationContext()))) {
		   Log.d("C2DM", "Moet registreren");
			C2DMReceiver.refreshAppC2DMRegistrationState(getApplicationContext(), true);
	   }

	   if (prefs.contains("dm_registration")) {
		   Log.d("C2DM", "Contains reg");
		   return;
	   } 	
	   if (Build.VERSION.SDK_INT >= 8) {
		   Log.d("C2DM", "Registeren");
			Editor e = prefs.edit();
			e.putString("dm_registration", "");
			e.commit();
			C2DMessaging.setRegisterForPush(getApplicationContext(), true);
			C2DMReceiver.refreshAppC2DMRegistrationState(getApplicationContext(), true);
		}
	   Log.d("C2DM", "Goed fout");
   }
   
   public void register() {
		Log.w("C2DM", "start registration process");
		Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
		intent.putExtra("app",
				PendingIntent.getBroadcast(this, 0, new Intent(), 0));
		// Sender currently not used
		intent.putExtra("sender", "batabericht@gmail.com");
		startService(intent);
	}

	public void showRegistrationId() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String string = prefs.getString("authentication", "n/a");
		Toast.makeText(this, string, Toast.LENGTH_LONG).show();
		Log.d("C2DM RegId", string);

	}
   
}
