package com.ut.bataapp.activities;

import java.util.ArrayList;
import java.util.Map;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		this.setContentView(R.layout.listview_team);
		registerForContextMenu(getListView());

		SharedPreferences keyValues = this.getSharedPreferences("teams_follow", Context.MODE_PRIVATE);
		Map<String, ?> favoteams = keyValues.getAll();
		if(favoteams.size() < 1) {
			noFavoTeams();

		} else if (favoteams.size() == 1) {
			Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
			int id = 0;
			for (Map.Entry<String, ?> entry : favoteams.entrySet()) {
				id = (Integer) entry.getValue();
			}
			intent.putExtra("index", id);
			startActivity(intent);

		} else {
			ArrayList<Team> teams = new ArrayList<Team>();
			for (Map.Entry<String, ?> entry : favoteams.entrySet()) {
				teams.add(new Team((Integer) entry.getValue(),0,entry.getKey()));
			}
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
			startActivity(getIntent()); finish();
			adapter.notifyDataSetChanged();
			return true;
		}
		return false;
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
		}).create();
	}


}
