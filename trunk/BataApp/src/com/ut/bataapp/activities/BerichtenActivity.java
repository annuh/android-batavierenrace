package com.ut.bataapp.activities;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
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

	/**
	 * ArrayList die alle pushberichten bijhoudt.
	 */
	private ArrayList<Bericht> pushberichten = new ArrayList<Bericht>();
	/**
	 * ArrayList die alle nieuwsberichten (uit nieuws.xml) bijhoudt.
	 */
	private ArrayList<Bericht> nieuwsberichten = new ArrayList<Bericht>();
	/**
	 * Adapter voor de ListView voor pushberichten.
	 */
	private BerichtAdapter adapter_push = null;
	/**
	 * Adapter voor ListView voor nieuwsberichten
	 */
	private BerichtAdapter adapter_nieuws = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.listview_berichten);
		setTitle(R.string.ab_berichten);
		getSupportActionBar().setHomeButtonEnabled(true);
		new getBerichten().execute();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		if(position <= pushberichten.size()) {
			switch(pushberichten.get(position-1).getCode()) {
				case Bericht.GEEL:
					Intent intent4 = new Intent(this, KleurcodesActivity.class);
					intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent4.putExtra("index", "geel");
					intent4.putExtra("beschrijving", pushberichten.get(position-1).getTitel());
					startActivity(intent4);
					break;
				case Bericht.GROEN:{
					Intent intent2 = new Intent(this, KleurcodesActivity.class);
					intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent2.putExtra("index", "groen");
					intent2.putExtra("beschrijving", pushberichten.get(position-1).getTitel());
					startActivity(intent2);
					break;
				}
				case Bericht.ROOD:{
					Intent intent3 = new Intent(this, KleurcodesActivity.class);
					intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent3.putExtra("index", "rood");
					intent3.putExtra("beschrijving", pushberichten.get(position-1).getTitel());
					startActivity(intent3);
					break;
				}
				case Bericht.WEER:{
					Intent intent = new Intent(this, WeerActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					break;
				}
			}
		} else {
			String url = String.format(getString(R.string.url_nieuws), String.valueOf(nieuwsberichten.get(position - 2 - pushberichten.size() ).getId()));
			Log.d("URL",url);
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			startActivity(i);
		}
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

	/**
	 * Maakt de lijst met berichten
	 */
	public void makeList() {
		pushberichten = Utils.getBerichten(this);
		
		adapter_push = new BerichtAdapter(this, pushberichten);
		adapter_nieuws = new BerichtAdapter(this, nieuwsberichten);

		SeparatedListAdapter adapter = new SeparatedListAdapter(this);
		adapter.addSection("Notificaties", adapter_push);
		adapter.addSection("Nieuws", adapter_nieuws);

		setListAdapter(adapter);
	}

	/**
	 * Inner class die berichten van de API vraagt.
	 * @author Anne
	 *
	 */
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
				getListView().setEmptyView(findViewById(R.id.listview_leeg));
				progressDialog.dismiss();
			}
		}
	}
}
