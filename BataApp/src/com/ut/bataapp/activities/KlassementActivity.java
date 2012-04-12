package com.ut.bataapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.R;
import com.ut.bataapp.Utils;
import com.ut.bataapp.adapters.KlassementAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Klassement;
import com.ut.bataapp.objects.Response;

public class KlassementActivity extends SherlockListActivity  {

	private final int MENU_SORT_NAAM = Menu.FIRST + 1;
	private final int MENU_SORT_PLAATS = Menu.FIRST + 2;
	private String naam;
	private Klassement klassement;
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

		@Override  
		protected Void doInBackground(Void... arg0) {
			response = api.getKlassementByNaam(naam);
			return null;
		}

		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(getApplicationContext(), response)) {
				klassement = (Klassement) response.getResponse();
				adapter = new KlassementAdapter(KlassementActivity.this, klassement.getUitslag());
				setListAdapter(adapter);
				progressDialog.dismiss();
			}
			

		}
	}
}
