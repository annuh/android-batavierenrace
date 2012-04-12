package com.ut.bataapp.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.R;
import com.ut.bataapp.MainActivity;
import com.ut.bataapp.MainActivity.OverridePendingTransition;
import com.ut.bataapp.Utils;
import com.ut.bataapp.adapters.KlassementAdapter;
import com.ut.bataapp.adapters.TeamAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Klassement.KlassementInfo;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.objects.Team;

public class KlassementActivity extends SherlockListActivity  {

	private final int MENU_SORT_NAAM = Menu.FIRST + 1;
	private final int MENU_SORT_PLAATS = Menu.FIRST + 2;
	private Response klassement = null;
	private String naam;
	private KlassementAdapter adapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTitle("Klassement");
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		this.getListView().setFastScrollEnabled(true);
		naam = this.getIntent().getStringExtra("index");
		this.setContentView(R.layout.listview_klassement);
		new getKlassement().execute();  	   
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
		intent.putExtra("index", v.getId());
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {	

		SubMenu subMenu1 = menu.addSubMenu(R.string.ab_sorteren);
		subMenu1.add(0,MENU_SORT_NAAM,Menu.NONE, "Naam");
		subMenu1.add(0,MENU_SORT_PLAATS,Menu.NONE, "Plaats");

		subMenu1.getItem()
		.setIcon(R.drawable.ic_action_sort)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("Keyboard", String.valueOf(item.getItemId()));
		switch (item.getItemId()) {
		case android.R.id.home:
			Utils.goHome(getApplicationContext());
			break;
		/*	
		case MENU_SORT_NAAM:
			Collections.sort( (ArrayList<Team>) teams.getResponse(),new Comparator<Team>() {
				public int compare(Team arg0, Team arg1) {
					return arg0.getNaam().compareTo(arg1.getNaam());
				}
			});
			adapter = new TeamAdapter(TeamsActivity.this, (ArrayList<Team>) teams.getResponse());
			setListAdapter(adapter);
			adapter.notifyDataSetChanged();
			break;
		case MENU_SORT_START:
			Collections.sort((ArrayList<Team>) teams.getResponse(),new Comparator<Team>() {
				public int compare(Team arg0, Team arg1) {
					return (arg0.getStartnummer()<arg1.getStartnummer() ? -1 : (arg0.getStartnummer()==arg1.getStartnummer() ? 0 : 1));
				}
			});
			adapter = new TeamAdapter(TeamsActivity.this, (ArrayList<Team>) teams.getResponse());
			setListAdapter(adapter);
			adapter.notifyDataSetChanged();
		 */
		}
		return super.onOptionsItemSelected(item);
	}


	private class getKlassement extends AsyncTask<Void, Void, Void> {
		Response response;
		private ProgressDialog progressDialog;  
		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(KlassementActivity.this,  
					"Bezig met laden", "Klassement wordt opgehaald...", true);  
		}

		@SuppressWarnings("unchecked")
		@Override  
		protected Void doInBackground(Void... arg0) {
			response = (Response) api.getKlassementByNaam(naam);
			return null;
		}

		@SuppressWarnings("unchecked")
		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(getApplicationContext(), response)) {
				adapter = new KlassementAdapter(KlassementActivity.this, (ArrayList<KlassementInfo>) response.getResponse());
				setListAdapter(adapter);
				progressDialog.dismiss();
			}
			

		}
	}
}
