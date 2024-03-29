package com.ut.bataapp;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
	/**
	 * Constante voor id van de menuknop Instellingen
	 */
	private final int MENU_SETTINGS = Menu.FIRST;
	/**
	 * Constante voor id van de menuknop Berichten
	 */
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
		getSupportActionBar().setHomeButtonEnabled(true);
		setupC2DM();
		
		// Klik op Etappes
		Button btn_routes = (Button) findViewById(R.id.dashboard_etappes);
		btn_routes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), EtappesActivity.class);
				startActivity(i);
			}
		});

		// Klik op Favorieten, bij 1 team ga gelijk naar dat team
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

		// aantal dagen tot Bata:
		
		Resources res = getResources();
		Calendar nu = Calendar.getInstance();
		Calendar bataDag = Calendar.getInstance();
    	bataDag.set(res.getInteger(R.integer.batadag_jaar), (res.getInteger(R.integer.batadag_maand)-1), res.getInteger(R.integer.batadag_dag));
		short dagenVerschil = Utils.diffDays(nu, bataDag);
		String weergeven;
    	if (dagenVerschil > 1)
    		weergeven = String.format(res.getString(R.string.main_batadag_duurt_nog_dagen), dagenVerschil);
    	else if (dagenVerschil == 1)
    		weergeven = res.getString(R.string.main_batadag_duurt_nog_dag);
    	else if (dagenVerschil == 0)
    		weergeven = res.getString(R.string.main_batadag_vandaag);
    	else
    		weergeven = res.getString(R.string.main_batadag_geweest);
    	((TextView) findViewById(R.id.dashboard_time)).setText(weergeven);
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
		// starten background updater:
		Intent startServiceIntent = new Intent(this, BackgroundUpdater.class);
		startService(startServiceIntent);
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
			return true;
		case MENU_MESSAGE:
			// Launch Messages activity
			Intent j = new Intent(getApplicationContext(), BerichtenActivity.class);
			startActivity(j);
			return true;

		}
		return true;
	}

	public static final class OverridePendingTransition {
		public static void invoke(Activity activity) {
			activity.overridePendingTransition(0, 0);
		}
	}

	/**
	 * Methode die controleert of dit toestal al aangemeld voor de C2DM notificaties
	 */
	public void setupC2DM() {		
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
