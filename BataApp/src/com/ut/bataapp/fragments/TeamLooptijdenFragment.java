package com.ut.bataapp.fragments;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.activities.TeamsActivity;
import com.ut.bataapp.adapters.LooptijdAdapter;
import com.ut.bataapp.adapters.TeamAdapter;
import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.objects.Team;
import com.ut.bataapp.objects.Looptijd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
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
    	
    	ListView lv = (ListView) view.findViewById(R.id.list_looptijden);		
		lv.setAdapter(new LooptijdAdapter(this.getActivity().getApplicationContext(), team.getLooptijden()));
		OnItemClickListener listener = new OnItemClickListener() {
		    @Override  
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	Intent intent = new Intent(getActivity().getApplicationContext(), EtappeActivity.class);
		        intent.putExtra("index", view.getId());
		        startActivity(intent);
		      }

		};
    	lv.setOnItemClickListener(listener);
		
    	return view;
    }
	

}
