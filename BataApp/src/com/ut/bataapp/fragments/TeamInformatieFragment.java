package com.ut.bataapp.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.objects.Team;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TeamInformatieFragment extends SherlockFragment {
	LinearLayout header ;
	LinearLayout mRootLayout;
	
	void setFragmentContentView(View view)
	{
	    LinearLayout.LayoutParams layoutParams = 
	            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 
	                                          ViewGroup.LayoutParams.FILL_PARENT);
	    mRootLayout.removeAllViews();
	    mRootLayout.addView(view, layoutParams);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//context = container;
		//setFragmentContentView(R.layout.team_informatie);
		//header = (LinearLayout) inflater.inflate(R.layout.team_informatie, container, false);
		Team team = ((TeamActivity)this.getActivity()).getTeam();
		getSherlockActivity().getSupportActionBar().setTitle(team.getNaam());     
		View view = inflater.inflate(R.layout.team_informatie, container, false);

		TextView team_naam = (TextView)  view.findViewById(R.id.team_naam);
		team_naam.setText(team.getNaam());

		TextView team_startnummer = (TextView)  view.findViewById(R.id.team_startnummer);
		team_startnummer.setText(Integer.toString(team.getStartnummer()));

		TextView team_startgroep = (TextView) view.findViewById(R.id.team_startgroep);
		team_startgroep.setText(Integer.toString(team.getStartGroep()));

		return view;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		//Log.d("FRAGMENT", "ROTATION");
		//header.addView(header);
		//myText.
		//myText.inflate(context, resource, root)
		//LayoutInflater.from(context).inflate(R.layout.main, this);
		//TextView myText = (TextView)getLayoutInflater().inflate(R.layout.tvtemplate, null);

	}
	
}
