package com.ut.bataapp.activities;

import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.MainActivity;
import com.ut.bataapp.MainActivity.OverridePendingTransition;
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
	   this.setContentView(R.layout.listview_etappes);
	   new getEtappes().execute();  
   }
   
   @Override
   public void onListItemClick(ListView l, View v, int position, long id) {
	  
	   Intent intent = new Intent(getApplicationContext(), EtappeActivity.class);
       intent.putExtra("index", v.getId());
       startActivity(intent);
   }
   
   @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent intent = new Intent(this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
				//Get rid of the slide-in animation, if possible
	            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
	                OverridePendingTransition.invoke(this);
	            }
		}
		
		return super.onOptionsItemSelected(item);
	}

   private class getEtappes extends AsyncTask<Void, Void, Void> {  
		private ProgressDialog progressDialog;  
		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(EtappesActivity.this,  
			  "Bezig met laden", "Etappes worden opgehaald...", true);  
		}
		
		@SuppressWarnings("unchecked")
		@Override
		protected Void doInBackground(Void... arg0) { 
			etappes = api.getEtappes().getResponse();
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
