package com.ut.bataapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.objects.Team;

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
		
		TextView team_klassement = (TextView) view.findViewById(R.id.team_klassement);
		team_klassement.setText(team.getKlassement());
		
		TextView team_cum_klassement = (TextView) view.findViewById(R.id.team_cum_klassement);
		team_cum_klassement.setText(team.getCumKlassement()+"*");

		TextView team_totaal_looptijd = (TextView) view.findViewById(R.id.team_totaal_looptijd);
		team_totaal_looptijd.setText(team.getTotaalTijd());
		
		TextView team_totetappe = (TextView) view.findViewById(R.id.team_totetappe);
		team_totetappe.setText(String.format(getString(R.string.team_klassement_totetappe), team.getKlassementTotEtappe()));
		
		return view;
	}	
}