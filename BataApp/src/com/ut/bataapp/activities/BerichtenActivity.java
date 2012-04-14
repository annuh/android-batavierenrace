package com.ut.bataapp.activities;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.MainActivity;
import com.ut.bataapp.Utils;
import com.ut.bataapp.MainActivity.OverridePendingTransition;
import com.ut.bataapp.adapters.BerichtAdapter;
import com.ut.bataapp.adapters.KlassementAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Bericht;
import com.ut.bataapp.objects.Klassement;
import com.ut.bataapp.objects.Response;


public class BerichtenActivity extends SherlockListActivity  {
	private ArrayList<Bericht> values;
	private BerichtAdapter ba = null;
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	   setTitle("Berichten");
	   super.onCreate(savedInstanceState);
	   new getBerichten().execute();
   }
   
   	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Utils.goHome(getApplicationContext());
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}
   	private class getBerichten extends AsyncTask<Void, Void, Void> {
		Response<ArrayList<Bericht>> response;
		private ProgressDialog progressDialog;  
		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(BerichtenActivity.this,  
					"Bezig met laden", "Berichten worden opgehaald...", true);  
		}

		@Override  
		protected Void doInBackground(Void... arg0) {
			response = api.getBerichten();
			return null;
		}

		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(BerichtenActivity.this, response)) {
				values = response.getResponse();
				ba = new BerichtAdapter(BerichtenActivity.this, values);
				setListAdapter(ba);
				progressDialog.dismiss();
			}
			

		}
	}
}
