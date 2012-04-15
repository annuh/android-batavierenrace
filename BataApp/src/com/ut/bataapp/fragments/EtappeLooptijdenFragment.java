package com.ut.bataapp.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.view.Window;
import com.ut.bataapp.R;
import com.ut.bataapp.SeparatedListAdapter;
import com.ut.bataapp.Utils;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.adapters.EtappeLooptijdAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Looptijd;
import com.ut.bataapp.objects.Response;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class EtappeLooptijdenFragment extends SherlockListFragment {

	private ArrayList<ArrayList<Looptijd>> looptijden;
	private EtappeLooptijdAdapter adapter_uni = null;
	private EtappeLooptijdAdapter adapter_alg = null;
	private final int MENU_SORT_NAAM = Menu.FIRST + 1;
	private final int MENU_SORT_STAND = Menu.FIRST + 2;
	TextView loading;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		inflater.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		View view = inflater.inflate(R.layout.listview_etappe_looptijden, null);
		return view;
	}

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		//loading = findViewById(R.id.loading);
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(adapter_uni == null) {
			new getEtappeLooptijden().execute();
		} else {
			makeList();
		}
	}


	@Override
	public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
		SubMenu subMenu1 = menu.addSubMenu(R.string.ab_sorteren);
		subMenu1.add(0,MENU_SORT_NAAM,Menu.NONE, "Naam");
		subMenu1.add(0,MENU_SORT_STAND,Menu.NONE, "Uitslag");

		subMenu1.getItem()
		.setIcon(R.drawable.ic_action_sort)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_SORT_NAAM:
			sortList(looptijden.get(0), "naam");
			sortList(looptijden.get(1), "naam");			
			makeList();

			break;
		case MENU_SORT_STAND:
			sortList(looptijden.get(0), "stand");
			sortList(looptijden.get(1), "stand");			
			makeList();

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity().getApplicationContext(), TeamActivity.class);
		intent.putExtra("index", v.getId());
		startActivity(intent);
	}

	private class getEtappeLooptijden extends AsyncTask<Void, Void, Void> {
		Response<ArrayList<ArrayList<Looptijd>>> response;

		@Override  
		protected Void doInBackground(Void... arg0) {
			response = api.getUitslagenVanEtappe(((EtappeActivity) getActivity()).getEtappe().getId());
			return null;
		}

		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(getActivity().getApplicationContext(), response)) {
				looptijden = response.getResponse();
				makeList();
			}
		}
	}

	/**
	 * Maakt de listview
	 */
	public void makeList() {
		getView().findViewById(R.id.loading).setVisibility(View.GONE);
		adapter_uni = new EtappeLooptijdAdapter(getActivity().getApplicationContext(), looptijden.get(0));
		adapter_alg = new EtappeLooptijdAdapter(getActivity().getApplicationContext(), looptijden.get(1));
						
		SeparatedListAdapter adapter = new SeparatedListAdapter(getActivity().getApplicationContext());  
        adapter.addSection("Universiteitsklassement", adapter_uni);  
        adapter.addSection("Algemeen klassement", adapter_alg);
        
		setListAdapter(adapter);
	}
	
	
	public void sortList(ArrayList<Looptijd> list, final String sort) {
		Collections.sort(list,new Comparator<Looptijd>() {
			public int compare(Looptijd arg0, Looptijd arg1) {
				if(sort.equals("naam"))
					return arg0.getTeamNaam().compareTo(arg1.getTeamNaam());
				else if(sort.equals("stand"))
					return (arg0.getEtappeStand()<arg1.getEtappeStand() ? -1 : (arg0.getEtappeStand()==arg1.getEtappeStand() ? 0 : 1));
				else
					return 0;
			}
		});
	}

}
