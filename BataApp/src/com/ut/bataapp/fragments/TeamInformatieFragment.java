package com.ut.bataapp.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.objects.Team;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TeamInformatieFragment extends SherlockFragment {
	
	private Team team;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		team = ((TeamActivity)this.getActivity()).getTeam();
		getSherlockActivity().getSupportActionBar().setTitle(team.getNaam());     
    	setHasOptionsMenu(true);
    	Log.d("FragmentView","Hello1");
    	View view = inflater.inflate(R.layout.team_informatie, container, false);
    	
    	TextView team_naam = (TextView)  view.findViewById(R.id.team_naam);
    	team_naam.setText(team.getNaam());
    	    
    	TextView team_startnummer = (TextView)  view.findViewById(R.id.team_startnummer);
    	team_startnummer.setText(Integer.toString(team.getStartnummer()));
    	
    	TextView team_startgroep = (TextView) view.findViewById(R.id.team_startgroep);
    	team_startgroep.setText(Integer.toString(team.getStartGroep()));
    	
    	return view;
    }

}
