package com.ut.bataapp.activities;

import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;
import com.ut.bataapp.adapters.EtappeAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.objects.Response;

public class EtappesActivity extends SherlockListActivity  {
	
	private ArrayList<Etappe> etappes = null;
	private EtappeAdapter adapter = null;
	
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   setTitle(R.string.dashboard_etappes);
	   super.onCreate(savedInstanceState);
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
				Utils.goHome(this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

   private class getEtappes extends AsyncTask<Void, Void, Void> {  
		private ProgressDialog progressDialog;  
		Response<ArrayList<Etappe>> response;
		
		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(EtappesActivity.this,  
			  getString(R.string.laden_titel), getString(R.string.etappes_laden), true);
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					cancel(true);
					Utils.goHome(EtappesActivity.this);
				}
			});
		}
		
		@Override
		protected Void doInBackground(Void... arg0) { 
			if(!isCancelled())
				response = api.getEtappes();
			return null;       
		}
		
		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(EtappesActivity.this, response)){
				etappes = response.getResponse();
				adapter = new EtappeAdapter(EtappesActivity.this, etappes);
				setListAdapter(adapter);
				progressDialog.dismiss();
				getListView().setEmptyView(findViewById(R.id.listview_leeg));
			}
			
		}
	}

}
