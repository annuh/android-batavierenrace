package com.ut.bataapp.activities;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.SeparatedListAdapter;
import com.ut.bataapp.Utils;
import com.ut.bataapp.adapters.BerichtAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Bericht;
import com.ut.bataapp.objects.Response;

public class BerichtenActivity extends SherlockListActivity  {

	private ArrayList<Bericht> pushberichten;
	private ArrayList<Bericht> nieuwsberichten;
	private BerichtAdapter adapter_push = null;
	private BerichtAdapter adapter_nieuws = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("Berichten");
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.listview_berichten);
		new getBerichten().execute();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.d("Listview",""+position);
		if(position <= pushberichten.size()) {
			switch(pushberichten.get(position-1).getCode()) {
			case Bericht.GEEL:
				break;
			case Bericht.GROEN:
				break;
			case Bericht.ROOD:
				break;
			case Bericht.WEER:
				Intent intent = new Intent(this, WeerActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		}
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

	public void makeList() {
		pushberichten = Utils.getBerichten(this);
		adapter_push = new BerichtAdapter(this, pushberichten);
		adapter_nieuws = new BerichtAdapter(this, nieuwsberichten);

		SeparatedListAdapter adapter = new SeparatedListAdapter(this);
		adapter.addSection("Notificaties", adapter_push);
		adapter.addSection("Nieuws", adapter_nieuws);

		setListAdapter(adapter);



	}

	private class getBerichten extends AsyncTask<Void, Void, Void> {
		Response<ArrayList<Bericht>> response;
		private ProgressDialog progressDialog;  
		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(BerichtenActivity.this,  
					getString(R.string.laden_titel), getString(R.string.berichten_laden), true);
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					cancel(true);
					Utils.goHome(BerichtenActivity.this);
				}
			});
		}

		@Override  
		protected Void doInBackground(Void... arg0) {
			if(!isCancelled())
				response = api.getBerichten();
			return null;
		}

		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(BerichtenActivity.this, response)) {
				nieuwsberichten = response.getResponse();
				makeList();
				
				progressDialog.dismiss();

			}
		}
	}
}
