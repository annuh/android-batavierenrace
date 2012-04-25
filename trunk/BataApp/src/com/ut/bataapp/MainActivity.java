package com.ut.bataapp;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.c2dm.C2DMessaging;
import com.ut.bataapp.activities.BataRadioActivity;
import com.ut.bataapp.activities.BerichtenActivity;
import com.ut.bataapp.activities.EtappesActivity;
import com.ut.bataapp.activities.FavoTeamsActivity;
import com.ut.bataapp.activities.InformatieActivity;
import com.ut.bataapp.activities.KlassementenActivity;
import com.ut.bataapp.activities.LustrumActivity;
import com.ut.bataapp.activities.PreferencesActivity;
import com.ut.bataapp.activities.SponsorActivity;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.activities.TeamsActivity;
import com.ut.bataapp.activities.WeerActivity;
import com.ut.bataapp.activities.WelkomActivity;
import com.ut.bataapp.objects.Team;
import com.ut.bataapp.services.BackgroundUpdater;

public class MainActivity extends SherlockFragmentActivity {	
	private final int MENU_SETTINGS = Menu.FIRST;
	private final int MENU_MESSAGE = Menu.FIRST+1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// workaround voor C2DM <-> AsyncTask bug:
		try {
			Class.forName("android.os.AsyncTask");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		setContentView(R.layout.main);
		setupC2DM();
		//register();
		Button btn_routes = (Button) findViewById(R.id.dashboard_etappes);
		btn_routes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), EtappesActivity.class);
				startActivity(i);
			}
		});

		Button btn_favorieten = (Button) findViewById(R.id.dashboard_favorieten);
		btn_favorieten.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ArrayList<Team> favoteams = Utils.getFavoTeams(MainActivity.this);
				Intent i;
				if(favoteams.size() == 1){
					i = new Intent(getApplicationContext(), TeamActivity.class);
					i.putExtra("index", favoteams.get(0).getID());
				} else {
					i = new Intent(getApplicationContext(), FavoTeamsActivity.class);
				}
				startActivity(i);
			}
		});

		Button btn_teams = (Button) findViewById(R.id.dashboard_teams);
		btn_teams.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), TeamsActivity.class);
				startActivity(i);
			}
		});
		Button btn_jubileum = (Button) findViewById(R.id.dashboard_jubileum);
		btn_jubileum.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), LustrumActivity.class);
				startActivity(i);
			}
		});
		
		
		Button btn_klassement = (Button) findViewById(R.id.dashboard_klassement);
		btn_klassement.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), KlassementenActivity.class);
				startActivity(i);
			}
		});

		Button btn_weer = (Button) findViewById(R.id.dashboard_weer);
		btn_weer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), WeerActivity.class);
				startActivity(i);
			}
		});

		Button btn_informatie = (Button) findViewById(R.id.dashboard_informatie);
		btn_informatie.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), InformatieActivity.class);
				startActivity(i);
			}
		});

		Button btn_sponsor = (Button) findViewById(R.id.dashboard_sponsor);
		btn_sponsor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), SponsorActivity.class);
				startActivity(i);
			}
		});

		Button btn_bataradio = (Button) findViewById(R.id.dashboard_bataradio);
		btn_bataradio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), BataRadioActivity.class);
				startActivity(i);
			}
		});

		// Aantal dagen
		Calendar nu = Calendar.getInstance();
		Calendar batadag = Calendar.getInstance();
		batadag.set(2012, 4-1, 28);
		long dagenVerschil = (batadag.getTimeInMillis() - nu.getTimeInMillis())/ (24 * 60 * 60 * 1000);

		TextView viewDagen = (TextView) findViewById(R.id.dashboard_time);
		String weergeven = "";
		if(dagenVerschil>0){
			weergeven = "Het duurt nog " +dagenVerschil + " dagen!";
		} else if(dagenVerschil==0){
			weergeven = "Batavierenrace XL";
		} else{
			weergeven = "Tot volgend jaar!";
		}
		viewDagen.setText(weergeven);

		// starten background updater:
		Intent startServiceIntent = new Intent(this, BackgroundUpdater.class);
		startService(startServiceIntent);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String lookupKey = getResources().getString(R.string.pref_first_start);
		if (prefs.getBoolean(lookupKey, true)) {
			Intent intent = new Intent(this, WelkomActivity.class);
			startActivity(intent);
			SharedPreferences.Editor editor = prefs.edit();
    		editor.putBoolean(lookupKey, false);
    		editor.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,MENU_MESSAGE,Menu.NONE,R.string.ab_berichten)
		.setIcon(R.drawable.ic_action_mail)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		menu.add(0,MENU_SETTINGS,Menu.NONE,R.string.ab_instellingen)
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
		/*Log.d("C2DM", "START");
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
	   Log.d("C2DM", "Goed fout");*/
		if (Build.VERSION.SDK_INT >= 8) {
			String registrationId = C2DMessaging.getRegistrationId(this);
			if(registrationId != null && !"".equals(registrationId)){
				Log.i("GenericNotifier", "Already registered. registrationId is " + registrationId);
			}else{
				Log.i("GenericNotifier", "No existing registrationId. Registering..");
				C2DMessaging.register(this, "batabericht@gmail.com");
			}
		}

	}

}
