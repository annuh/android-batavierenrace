package com.ut.bataapp.fragments;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockListFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.Utils;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.activities.TeamsActivity;
import com.ut.bataapp.adapters.EtappeLooptijdAdapter;
import com.ut.bataapp.adapters.KlassementAdapter;
import com.ut.bataapp.adapters.LooptijdAdapter;
import com.ut.bataapp.adapters.TeamAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Looptijd;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.objects.Team;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class EtappeLooptijdenFragment extends SherlockListFragment {

	ArrayList<Looptijd> looptijden;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		inflater.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		//setListAdapter(new EtappeLooptijdAdapter(this.getActivity().getApplicationContext(), looptijden));

		View view = inflater.inflate(R.layout.listview_etappe_looptijden, null); 
		
		
		return view;

	}

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new getEtappeLooptijden().execute();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity().getApplicationContext(), EtappeActivity.class);
		intent.putExtra("index", v.getId());
		startActivity(intent);
	}
	
	private class getEtappeLooptijden extends AsyncTask<Void, Void, Void> {
		Response response;
		private ProgressDialog progressDialog;  
		protected void onPreExecute() {  
			//progressDialog = ProgressDialog.show(TeamsActivity.this,  
			//  "Bezig met laden", "Teams worden opgehaald...", true);  
		}
		
		@SuppressWarnings("unchecked")
		@Override  
		protected Void doInBackground(Void... arg0) {
			response = (Response) api.getUitslagenVanEtappe(((EtappeActivity) getActivity()).getEtappe().getId());
			return null;
		}
		
		@SuppressWarnings("unchecked")
		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(getActivity().getApplicationContext(), response)) {
				looptijden = (ArrayList<Looptijd>) response.getResponse();

				setListAdapter(new EtappeLooptijdAdapter(getActivity().getApplicationContext(), looptijden));
				//progressDialog.dismiss();
			}
			
		
		}
	}


}
