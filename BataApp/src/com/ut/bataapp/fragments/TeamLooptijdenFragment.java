package com.ut.bataapp.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.objects.Team;
import com.ut.bataapp.objects.Uitslag;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TeamLooptijdenFragment extends SherlockFragment {
	
	private Team team;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		team = ((TeamActivity) getActivity()).getTeam();
		getSherlockActivity().getSupportActionBar().setTitle(team.getNaam());     
    	setHasOptionsMenu(true);
    	
    	View view = inflater.inflate(R.layout.team_looptijden, container, false);
    	
    	Log.d("# Uitslagen", String.valueOf(team.getLooptijden().size()));

    	TableLayout table = (TableLayout) view.findViewById(R.id.table_team_looptijden);
    	
	    for(Uitslag uitslag : team.getLooptijden()) {
	    	Log.d("# Uitslagen", String.valueOf(team.getLooptijden().size()));
	    	TableRow tr = new TableRow(this.getActivity());
		    	//tr.setLayoutParams(new LayoutParams(
		          //             LayoutParams.MATCH_PARENT ,
		            //           LayoutParams.WRAP_CONTENT));
		    
		    TextView etappe = new TextView(this.getActivity());
		    	etappe.setLayoutParams(new TableRow.LayoutParams(
                    50, LayoutParams.WRAP_CONTENT));
	        TextView tijd = new TextView(this.getActivity());
	        	tijd.setLayoutParams(new TableRow.LayoutParams(
	                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	        	
	    	etappe.setText(String.valueOf(uitslag.getEtappe().getId()));
	    	tijd.setText(uitslag.getTijd());
	    	
	    	tr.addView(etappe);
	    	tr.addView(tijd);
		             
	    	table.addView(tr, new TableLayout.LayoutParams(
                      LayoutParams.MATCH_PARENT,
                      LayoutParams.WRAP_CONTENT));
	    }
	    	
    	return view;
    }

}
