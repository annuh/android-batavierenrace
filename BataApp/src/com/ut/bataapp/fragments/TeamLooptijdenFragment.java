package com.ut.bataapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.adapters.LooptijdAdapter;
import com.ut.bataapp.objects.Team;

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
}