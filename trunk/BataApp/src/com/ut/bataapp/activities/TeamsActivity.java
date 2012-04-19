package com.ut.bataapp.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.R;
import com.ut.bataapp.Utils;
import com.ut.bataapp.adapters.TeamAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.objects.Team;

public class TeamsActivity extends SherlockListActivity  {

	private final int MENU_SEARCH = Menu.FIRST;
	private final int MENU_SORT_NAAM = Menu.FIRST + 1;
	private final int MENU_SORT_START = Menu.FIRST + 2;
	private static ArrayList<Team> teams = null;
	private String filterText = null;
	private TeamAdapter adapter = null;
	private char sortNaam = 'D';
	private char sortStartnummer = 'D';
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTitle("Teams");

		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		this.getListView().setFastScrollEnabled(true);
		this.setContentView(R.layout.listview_team);
		new getTeams().execute();	   
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
		intent.putExtra("index", v.getId());
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {	
		menu.add(0,MENU_SEARCH,Menu.NONE, R.string.ab_zoeken)
		.setIcon(R.drawable.ic_action_search)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		SubMenu subMenu1 = menu.addSubMenu(R.string.ab_sorteren);
		subMenu1.add(0,MENU_SORT_NAAM,Menu.NONE, "Naam");
		subMenu1.add(0,MENU_SORT_START,Menu.NONE, "Startnummer");

		subMenu1.getItem()
		.setIcon(R.drawable.ic_action_sort)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	@Override
	public boolean onSearchRequested() {
		this.findViewById(MENU_SEARCH).performClick();
		return false;
		//return super.onSearchRequested();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Utils.goHome(getApplicationContext());
			break;
		case MENU_SEARCH:
			item.setActionView(R.layout.search_box);
			EditText filterEdit = (EditText) item.getActionView().findViewById(R.id.search_box);
			filterEdit.setText(filterText);
			filterEdit.addTextChangedListener(filterTextWatcher);
			setKeyboardFocus(filterEdit);
			break;
		case MENU_SORT_NAAM:
			sortNaam(null);
			break;
		case MENU_SORT_START:
			sortStartnummer(null);

		}
		return super.onOptionsItemSelected(item);
	}

	public static void setKeyboardFocus(final EditText primaryTextField) {
		(new Handler()).postDelayed(new Runnable() {
			public void run() {
				primaryTextField.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
				primaryTextField.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0));
			}
		}, 100);
	}

	private class getTeams extends AsyncTask<Void, Void, Void> {  

		private ProgressDialog progressDialog;
		private Response<ArrayList<Team>> response;

		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(TeamsActivity.this,  
					"Bezig met laden", "Teams worden opgehaald...", true);
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					cancel(true);
					Utils.goHome(TeamsActivity.this);
				}
			});
		}

		@Override  
		protected Void doInBackground(Void... arg0) {
			if(!isCancelled())
				response = api.getTeams();
			return null;       
		}

		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(TeamsActivity.this, response)) {
				teams = response.getResponse();
				sortStartnummer(null);
				progressDialog.dismiss();
				response = null;
			}
		}
	}

	private TextWatcher filterTextWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) {
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			filterText = s.toString();
			adapter.getFilter().filter(s);
		}
	};
	
	public void sortStartnummer(View v) {
		resetArrows();
		if(sortStartnummer == 'D') {
			Collections.sort((ArrayList<Team>) teams,new Comparator<Team>() {
				public int compare(Team arg0, Team arg1) {
					return (arg0.getStartnummer()<arg1.getStartnummer() ? -1 : (arg0.getStartnummer()==arg1.getStartnummer() ? 0 : 1));
				}
			});
			sortStartnummer = 'A';
			sortNaam = 'D';
		} else {
			Collections.sort((ArrayList<Team>) teams,new Comparator<Team>() {
				public int compare(Team arg0, Team arg1) {
					return (arg1.getStartnummer()<arg0.getStartnummer() ? -1 : (arg1.getStartnummer()==arg0.getStartnummer() ? 0 : 1));
				}
			});
			sortStartnummer = 'D';
			sortNaam = 'D';
		}
		int i = this.getResources().getIdentifier("sort_"+sortStartnummer, "string", this.getPackageName());
		((TextView) this.findViewById(R.id.teams_header_startnummer)).setText(this.getText(R.string.teams_header_startnummer) +" "+ getText(i));

		
		adapter = new TeamAdapter(TeamsActivity.this, (ArrayList<Team>) teams);
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	public void sortNaam(View v) {
		resetArrows();
		if(sortNaam == 'D') {
			Collections.sort( (ArrayList<Team>) teams,new Comparator<Team>() {
				public int compare(Team arg0, Team arg1) {
					return arg0.getNaam().compareTo(arg1.getNaam());
				}
			});
			sortNaam = 'A';
			sortStartnummer = 'D';
		} else {
			Collections.sort( (ArrayList<Team>) teams,new Comparator<Team>() {
				public int compare(Team arg0, Team arg1) {
					return arg1.getNaam().compareTo(arg0.getNaam());
				}
			});
			sortNaam = 'D';
			sortStartnummer = 'D';
		}
		int i = this.getResources().getIdentifier("sort_"+sortNaam, "string", this.getPackageName());
		((TextView) this.findViewById(R.id.teams_header_naam)).setText(this.getText(R.string.teams_header_naam) +" "+ getText(i));
		
		adapter = new TeamAdapter(TeamsActivity.this, (ArrayList<Team>) teams);
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	public void resetArrows() {
		((TextView) this.findViewById(R.id.teams_header_startnummer)).setText(this.getText(R.string.teams_header_startnummer));
		((TextView) this.findViewById(R.id.teams_header_naam)).setText(this.getText(R.string.teams_header_naam));
	}
}
