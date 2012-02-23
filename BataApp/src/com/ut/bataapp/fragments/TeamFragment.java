package com.ut.bataapp.fragments;

//import com.actionbarsherlock.sample.shakespeare.Shakespeare;
import java.util.ArrayList;

import com.ut.bataapp.R;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.objects.Team;
import com.ut.bataapp.objects.Uitslag;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.view.SubMenu;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TeamFragment extends Fragment {
	
	private final int MENU_FOLLOW = Menu.FIRST;
    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static TeamFragment newInstance(int index) {
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        
        TeamFragment f = new TeamFragment();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	setHasOptionsMenu(true);
    	if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }
    	
    	Uitslag uitslag1 = new Uitslag(new Team(1,1,"Bla"), new Etappe(1,'M'), "0:12:12", "");
    	Uitslag uitslag2 = new Uitslag(new Team(1,1,"Bla"), new Etappe(1,'M'), "0:12:12", "");
    	ArrayList<Uitslag> uitslagen = new ArrayList<Uitslag>();
    	uitslagen.add(uitslag1);
    	uitslagen.add(uitslag2);
    	
    	int id = getArguments().getInt("index", 0) + 1;
    	
    	Team team = api.getTeamByID(id);
    	
    	View view = inflater.inflate(R.layout.team_fragment, container, false);
    	TextView label_info = (TextView)  view.findViewById(R.id.label_info);
    	label_info.setText(team.getNaam());
    	    
    	TextView team_startnummer = (TextView)  view.findViewById(R.id.team_startnummer);
    	team_startnummer.setText(Integer.toString(team.getStartnummer()));
    	TextView team_startgroep = (TextView) view.findViewById(R.id.team_startgroep);
    	team_startgroep.setText(Integer.toString(team.getStartGroep()));
    	
    	TableLayout table = (TableLayout)view.findViewById(R.id.table_team_uitslag);
 	    	for(Uitslag uitslag: uitslagen){
	    		TableRow tr = new TableRow(this.getActivity());
		    		TextView tv1 = new TextView(view.getContext());
	    			tv1.setText(Integer.toString(uitslag.getEtappe().getId()));
	    			tv1.setLayoutParams(new TableRow.LayoutParams(
	                         50, LayoutParams.WRAP_CONTENT));
	    			
	    			TextView tv2 = new TextView(view.getContext());
	    			tv2.setText(uitslag.getTijd());
	    			tv2.setLayoutParams(new TableRow.LayoutParams(
	                        LayoutParams.FILL_PARENT,
	                        LayoutParams.WRAP_CONTENT));
	    			tr.addView(tv1);
	    			tr.addView(tv2);
    			table.addView(tr, new TableLayout.LayoutParams(
	    				LayoutParams.FILL_PARENT,
	    				LayoutParams.WRAP_CONTENT));
	    	}
    	return view;
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {	
    	
    	menu.add(0,MENU_FOLLOW,Menu.NONE,"Volg dit team")
    	.setIcon(R.drawable.ic_action_star)
    	.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_FOLLOW:
				Toast toast = Toast.makeText(this.getActivity(), "U volgt dit team nu.", Toast.LENGTH_SHORT);
				toast.show();
			break;
		
			
		}
		return false;
   }
}
