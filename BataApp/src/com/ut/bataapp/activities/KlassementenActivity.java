package com.ut.bataapp.activities;

import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Response;

/**
 * Activity waar een overzicht van alle klassementen wordt getoond.
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne van de Venis
 * @version 1.0
 */
public class KlassementenActivity extends SherlockFragmentActivity  {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.dashboard_klassement);
		setContentView(R.layout.klassementen);
		getSupportActionBar().setHomeButtonEnabled(true);
		new getKlassementen().execute(); 
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

	// Inner classes

	/* Klasse voor het binnenhalen van alle klassementen. Tijdens het laden wordt een spinner weergegeven, vervolgens wordt voor elk klassement
	 * een button gemaakt.
	 * @author Anne van de Venis
	 * @version 1.0
	 */
	private class getKlassementen extends AsyncTask<Void, Void, Void> {
		/** Het resultaat van de api-aanvraag */
		Response<ArrayList<String>> response = null;
		/** Spinner die wordt getoond tijdens het laden */
		private ProgressDialog progressDialog;  

		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(KlassementenActivity.this,  
					getString(R.string.laden_titel), getString(R.string.klassementen_laden), true);  
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					cancel(true);
					Utils.goHome(KlassementenActivity.this);
				}
			});
		}

		@Override  
		protected Void doInBackground(Void... arg0) {
			if(!isCancelled())
				response = api.getKlassementen();
			return null;       
		}

		@Override  
		protected void onPostExecute(Void result) {
			if (Utils.checkResponse(KlassementenActivity.this, response)) {
				ViewGroup c = (ViewGroup) findViewById(R.id.container_klassementen);
				for(final String klassement: (ArrayList<String>) response.getResponse()) {
					Button button = (Button) LayoutInflater.from(getBaseContext()).inflate(R.drawable.button, c, false);
					button.setText(klassement);
					button.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							Intent intent = new Intent(KlassementenActivity.this, KlassementActivity.class);
							intent.putExtra("index", klassement);
							startActivity(intent);
						}
					});
					c.addView(button);
				}
				progressDialog.dismiss();
			}
		}
	}
}
