package com.ut.bataapp.activities;

import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class KlassementenActivity extends SherlockFragmentActivity  {

	public ArrayList<String> klassementen;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTitle("Klassement");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.klassementen);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		new getKlassementen().execute(); 
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Utils.goHome(getApplicationContext());
		}

		return super.onOptionsItemSelected(item);
	}

	private class getKlassementen extends AsyncTask<Void, Void, Void> {

		Response<ArrayList<String>> response = null;
		private ProgressDialog progressDialog;  

		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(KlassementenActivity.this,  
					"Bezig met laden", "Klassementen worden opgehaald...", true);  
		}

		@Override  
		protected Void doInBackground(Void... arg0) {
			response = api.getKlassementen();
			return null;       
		}

		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(KlassementenActivity.this, response)) {
				klassementen = response.getResponse();
				ViewGroup c = (ViewGroup) findViewById(R.id.container_klassementen);
				for(final String klassement: (ArrayList<String>) response.getResponse()) {
					Button button = (Button) LayoutInflater.from(getBaseContext()).inflate(R.drawable.button, c, false);
					button.setText(klassement);
					button.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							Intent intent = new Intent(getApplicationContext(), KlassementActivity.class);
							intent.putExtra("index", klassement);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
						}
					});
					c.addView(button);
				}
				progressDialog.dismiss();
			} else {
				Utils.goHome(getApplicationContext());
			}
		}
	}

}
