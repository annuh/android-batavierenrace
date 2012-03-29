package com.ut.bataapp.fragments;

import com.actionbarsherlock.app.SherlockListFragment;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.adapters.LooptijdAdapter;
import com.ut.bataapp.objects.Team;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class TeamLooptijdenFragment extends SherlockListFragment {
	
	private Team team;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		team = ((TeamActivity) getActivity()).getTeam();
		getSherlockActivity().getSupportActionBar().setTitle(team.getNaam());
    	
    	//View view = inflater.inflate(R.layout.team_looptijden, container, false);
    	
    	//adapter = new TeamAdapter(TeamsActivity.this, teams);
	    setListAdapter(new LooptijdAdapter(this.getActivity().getApplicationContext(), team.getLooptijden()));
    	
    	//ListView lv = (ListView) view.findViewById(R.id.list_looptijden);		
		//lv.setAdapter(new LooptijdAdapter(this.getActivity().getApplicationContext(), team.getLooptijden()));
		/*OnItemClickListener listener = new OnItemClickListener() {
		    @Override  
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	
		      }

		};
    	//lv.setOnItemClickListener(listener);
		
    	//return view;*/
    }
	
	@Override
	   public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity().getApplicationContext(), EtappeActivity.class);
        intent.putExtra("index", v.getId());
        startActivity(intent);
	   }
	

}
