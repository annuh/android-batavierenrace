package com.ut.bataapp.fragments;



import java.util.ArrayList;
import java.util.Map;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.EtappeChartByTeam;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.activities.TeamsActivity;
import com.ut.bataapp.adapters.TeamAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.objects.Team;
import com.ut.bataapp.objects.Uitslag;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TeamInformatieFragment extends SherlockFragment {
	
	private final int MENU_FOLLOW = Menu.FIRST;
	private Team team;
	private int team_id;
	
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		team_id = this.getActivity().getIntent().getIntExtra("index", 1);
		new getTeam().execute();
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		team = ((TeamActivity)this.getActivity()).getTeam();
		getSherlockActivity().getSupportActionBar().setTitle(team.getNaam());     
    	setHasOptionsMenu(true);
    	
    	View view = inflater.inflate(R.layout.team_fragment, container, false);
    	TextView label_info = (TextView)  view.findViewById(R.id.label_info);
    	label_info.setText(team.getNaam());   
    	    
    	TextView team_startnummer = (TextView)  view.findViewById(R.id.team_startnummer);
    	team_startnummer.setText(Integer.toString(team.getStartnummer()));
    	TextView team_startgroep = (TextView) view.findViewById(R.id.team_startgroep);
    	team_startgroep.setText(Integer.toString(team.getStartGroep()));
    	
    	
    	return view;
    }
    
	public void setTeam(Team team){
		this.team = team;
		((TeamActivity)this.getActivity()).setTeam(team);
	}
	
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {	
    	menu.add("Favo")
	    .setIcon(R.drawable.ic_action_star)
	    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);	
    }
   
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_FOLLOW:
				Toast toast = Toast.makeText(this.getActivity(), "U volgt dit team nu.", Toast.LENGTH_SHORT);
				toast.show();
				SharedPreferences keyValues = this.getActivity().getSharedPreferences("teams_follow", Context.MODE_PRIVATE);
				SharedPreferences.Editor keyValuesEditor = keyValues.edit();
				keyValuesEditor.putInt(team.getNaam(), team.getStartnummer());	
				keyValuesEditor.commit();
			break;		
		}
		return false;
   }
	
	private class getTeam extends AsyncTask<Void, Void, Void> {  
		private ProgressDialog progressDialog;  
		protected void onPreExecute() {  
			//progressDialog = ProgressDialog.show(TeamActivity.this,  
			//  "Bezig met laden", "Teams worden opgehaald...", true);  
		}
		
		@Override  
		protected Void doInBackground(Void... arg0) {  
			setTeam(api.getTeamByID(team_id));
			return null;       
		}
		
		@Override  
		protected void onPostExecute(Void result) {
			//progressDialog.dismiss();
		}
	}
}
