package com.ut.bataapp.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.ut.bataapp.R;
import com.ut.bataapp.Utils;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.activities.TeamsActivity;
import com.ut.bataapp.adapters.LooptijdAdapter;
import com.ut.bataapp.adapters.TeamAdapter;
import com.ut.bataapp.objects.Team;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class TeamLooptijdenFragment extends SherlockListFragment {
	
	private final int MENU_FOUTCODES = Menu.FIRST + 2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Team team = ((TeamActivity) getActivity()).getTeam();
		setHasOptionsMenu(true);
		getSherlockActivity().getSupportActionBar().setTitle(team.getNaam());
		setListAdapter(new LooptijdAdapter(this.getActivity().getApplicationContext(), team.getLooptijden()));
		View view = inflater.inflate(R.layout.listview_team_looptijden, null); 
		return view; 

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case MENU_FOUTCODES:
			FragmentTransaction ft = getFragmentManager().beginTransaction();
		    DialogFragment newFragment = new FoutcodesDialogFragment();
		    newFragment.show(ft, "Foutcodes");			
			break;
		
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
		menu.add(0,MENU_FOUTCODES,Menu.NONE, R.string.ab_foutcodes)
		.setIcon(R.drawable.ic_action_foutcodes)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
		RelativeLayout h = (RelativeLayout)getLayoutInflater(this.getArguments()).inflate(R.layout.listview_team_looptijden_header, null);
		head.addView(h);

	}
}