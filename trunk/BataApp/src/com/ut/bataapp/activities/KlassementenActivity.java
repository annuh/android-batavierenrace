package com.ut.bataapp.activities;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.MainActivity;
import com.ut.bataapp.Utils;
import com.ut.bataapp.MainActivity.OverridePendingTransition;
import com.ut.bataapp.adapters.TeamAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.objects.Team;

public class KlassementenActivity extends SherlockFragmentActivity  {
	
	public Response klassementen;
	
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
			if(Utils.checkResponse(getApplicationContext(), response)) {
				LinearLayout c = (LinearLayout) findViewById(R.id.container_klassementen);
				for(final String klassement: response.getResponse()) {

					Button button = (Button)getLayoutInflater().inflate(R.drawable.button, null);
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
					///getLayoutInflater().from(getBaseContext()).inflate(R.layout.layout_to_load, parent, false);
					//TextView myText = (TextView)getLayoutInflater().inflate(R.layout.tvtemplate, null);
/*loadedView = LayoutInflater.from(getBaseContext()).inflate(R.layout.layout_to_load, parent, false);

}*/
					
				}
				
				progressDialog.dismiss();
			} else {
				Utils.goHome(getApplicationContext());
			}
		}
	}
   
}
