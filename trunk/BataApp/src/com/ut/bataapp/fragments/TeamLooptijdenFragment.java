package com.ut.bataapp.fragments;

import com.actionbarsherlock.app.SherlockListFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.adapters.LooptijdAdapter;
import com.ut.bataapp.objects.Team;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TeamLooptijdenFragment extends SherlockListFragment {

	Team team;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflater.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        //inflater.inflate(R.layout.listview_looptijden, container);
        setListAdapter(new LooptijdAdapter(this.getActivity().getApplicationContext(), team.getLooptijden()));
        
        View view = inflater.inflate(R.layout.listview_looptijden, null); 
        return view; 
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		team = ((TeamActivity) getActivity()).getTeam();
		getSherlockActivity().getSupportActionBar().setTitle(team.getNaam());
    	//this.setContentView(R.layout.listview_looptijden);
		//getListView().addHeaderView(myHeaderView);

    	//View view = inflater.inflate(R.layout.team_looptijden, container, false);
    	
    	//adapter = new TeamAdapter(TeamsActivity.this, teams);
	    
    	
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
