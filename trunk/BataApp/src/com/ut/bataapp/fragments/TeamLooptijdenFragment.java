package com.ut.bataapp.fragments;

import com.actionbarsherlock.app.SherlockListFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.adapters.LooptijdAdapter;
import com.ut.bataapp.objects.Team;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class TeamLooptijdenFragment extends SherlockListFragment {
	Team team;
	RelativeLayout header;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//inflater.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		//inflater.inflate(R.layout.listview_looptijden, container);
		if(team == null) {
			Log.d("Fragment", "NULL");
		} else {
			Log.d("Fragment", "AAAH");
		}
		//header = (RelativeLayout) container.findViewById(R.id.team_looptijden_header);
		team = ((TeamActivity) getActivity()).getTeam();
		getSherlockActivity().getSupportActionBar().setTitle(team.getNaam());
		setListAdapter(new LooptijdAdapter(this.getActivity().getApplicationContext(), team.getLooptijden()));
		//setListAdapter(null);
		View view = inflater.inflate(R.layout.listview_team_looptijden, null); 
		return view; 
		//return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Team team = ((TeamActivity)this.getActivity()).getTeam();
		getSherlockActivity().getSupportActionBar().setTitle(team.getNaam());    
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity().getApplicationContext(), EtappeActivity.class);
		intent.putExtra("index", v.getId());
		startActivity(intent);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Log.d("FRAGMENT", "ROTATION");
		RelativeLayout head = (RelativeLayout) getView().findViewById(R.id.team_looptijden_header);
		head.removeAllViews();
		makeHeaderPortrait(head);
		
	}
	
	public void makeHeaderPortrait(RelativeLayout header) {
		RelativeLayout h = (RelativeLayout)getLayoutInflater(this.getArguments()).inflate(R.layout.listview_team_looptijden_header, null);
		header.addView(h);
		
		/*
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		//layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		TextView etappe = new TextView(this.getActivity().getApplicationContext());
		etappe.setText("TEST");
		etappe.setLayoutParams( layoutParams );
		//RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		//layout.addRule(RelativeLayout.ALIGN_WITH_PARENT_TOP);
		//parentView.addView(linearLayout, relativeParams);
		header.addView(etappe);*/
	}


}