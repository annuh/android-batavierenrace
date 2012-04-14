package com.ut.bataapp.activities;

import java.util.ArrayList;
import java.util.Map;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;
import com.ut.bataapp.adapters.TeamAdapter;
import com.ut.bataapp.objects.Team;

public class FavoTeamsActivity extends SherlockListActivity {

	public final static int DELETE_FAVOTEAM = 1;
	TeamAdapter adapter;
	boolean firstLaunch = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		this.setContentView(R.layout.listview_favo);
		registerForContextMenu(getListView());
		//initLijst();
		//SharedPreferences keyValues = this.getSharedPreferences("teams_follow", Context.MODE_PRIVATE);
		//Map<String, ?> favoteams = keyValues.getAll();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initLijst();
	}
	
	public void initLijst() {
		ArrayList<Team> teams = this.getFavoTeams();
		if(teams.size() < 1) {
			noFavoTeams();
		} else if (teams.size() == 1 && firstLaunch) {
			Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
			//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("index", teams.get(0).getID());
			Log.d("FavoTeamStart",""+teams.get(0).getID());
			startActivity(intent);
			firstLaunch = false;
		} else {
			adapter = new TeamAdapter(FavoTeamsActivity.this, teams);
			setListAdapter(adapter);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
		intent.putExtra("index", v.getId());
		startActivity(intent);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Utils.goHome(this.getApplicationContext());
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.add(Menu.NONE, DELETE_FAVOTEAM, Menu.NONE, "Verwijder team");
		//AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		//int id = info.targetView.getId();
	}

	public boolean onContextItemSelected(android.view.MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		int id = info.targetView.getId();
		switch (item.getItemId()) {
		case DELETE_FAVOTEAM:
			Utils.removeFavoteam(getApplicationContext(), (int) id);
			//startActivity(getIntent()); finish();
			//adapter.notifyDataSetChanged();
			adapter = new TeamAdapter(FavoTeamsActivity.this, getFavoTeams());
			setListAdapter(adapter);
			return true;
		}
		return false;
	} 

	public ArrayList<Team> getFavoTeams() {
		SharedPreferences keyValues = this.getSharedPreferences("teams_follow", Context.MODE_PRIVATE);
		Map<String, ?> favoteams = keyValues.getAll();
		ArrayList<Team> teams = new ArrayList<Team>();
		
		for (Map.Entry<String, ?> entry : favoteams.entrySet()) {
			teams.add(new Team(Integer.parseInt(entry.getKey()), (String) entry.getValue()));
			Log.d("FavoTeam", ""+entry.getKey());
		}
		return teams;
		
	}
	
	public void noFavoTeams() {
		new AlertDialog.Builder(this)
		.setTitle("U heeft geen favoriete teams!")
		.setMessage("Wilt u nu teams toevoegen?")
		.setCancelable(false)
		.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent i = new Intent(getApplicationContext(), TeamsActivity.class);
				startActivity(i);
			}
		})
		.setNegativeButton("Nee", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				FavoTeamsActivity.this.finish();
			}
		}).create().show();
	}


}
