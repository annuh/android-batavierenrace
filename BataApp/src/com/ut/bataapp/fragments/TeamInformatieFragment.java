package com.ut.bataapp.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.objects.Team;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TeamInformatieFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

}
