package com.ut.bataapp.activities;


import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockListActivity;
import com.ut.bataapp.adapters.EtappeAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Etappe;


public class EtappesActivity extends SherlockListActivity  {
	
	private ArrayList<Etappe> etappes = null;
	private EtappeAdapter adapter = null;
	
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   setTitle("Etappes");
  
	   super.onCreate(savedInstanceState);
	   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	   this.getListView().setFastScrollEnabled(true);
	   new getEtappes().execute();  
   }
   
   @Override
   public void onListItemClick(ListView l, View v, int position, long id) {
	   Intent intent = new Intent(getApplicationContext(), EtappeActivity.class);
       intent.putExtra("index", position);
       startActivity(intent);
   }

   private class getEtappes extends AsyncTask<Void, Void, Void> {  
		private ProgressDialog progressDialog;  
		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(EtappesActivity.this,  
			  "Bezig met laden", "Teams worden opgehaald...", true);  
		}
		
		@Override  
		protected Void doInBackground(Void... arg0) {  
			etappes = api.getEtappes();
			return null;       
		}
		
		@Override  
		protected void onPostExecute(Void result) {
			adapter = new EtappeAdapter(EtappesActivity.this, etappes);
			setListAdapter(adapter);
			progressDialog.dismiss();
		}
	}

}
