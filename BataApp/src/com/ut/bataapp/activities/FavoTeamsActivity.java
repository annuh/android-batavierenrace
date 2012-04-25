package com.ut.bataapp.activities;

import java.util.ArrayList;
import android.content.Intent;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getSupportActionBar().setTitle(R.string.dashboard_favorieten);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		this.setContentView(R.layout.listview_favo);
		registerForContextMenu(getListView());
	}

	@Override
	protected void onResume() {
		super.onResume();
		initLijst();
	}

	/**
	 * Bouwt de lijst met favorieten
	 */
	public void initLijst() {
		ArrayList<Team> teams = Utils.getFavoTeams(FavoTeamsActivity.this);
		if(teams.size() < 1) {
			Utils.noFavoTeams(FavoTeamsActivity.this);
		} else {
			TeamAdapter adapter = new TeamAdapter(FavoTeamsActivity.this, teams);
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
			Utils.goHome(this);
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.add(Menu.NONE, DELETE_FAVOTEAM, Menu.NONE, getString(R.string.favo_verwijderteam));
	}

	public boolean onContextItemSelected(android.view.MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		int id = info.targetView.getId();
		switch (item.getItemId()) {
		case DELETE_FAVOTEAM:
			Utils.removeFavoteam(getApplicationContext(), (int) id);
			initLijst();
			return true;
		}
		return false;
	}
}
