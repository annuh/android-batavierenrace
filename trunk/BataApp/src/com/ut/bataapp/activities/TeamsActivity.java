package com.ut.bataapp.activities;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.R;
import com.ut.bataapp.MainActivity;
import com.ut.bataapp.MainActivity.OverridePendingTransition;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Team;

public class TeamsActivity extends SherlockListActivity  {
	
	 private final int MENU_SEARCH = Menu.FIRST;
	 private final int MENU_SORT = Menu.FIRST + 1;
	 private ArrayList<Team> teams = null;
	 
	 public void setTeams(ArrayList<Team> arrayList){
		 this.teams=arrayList;
	 }
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   setTitle("Teams");
	   super.onCreate(savedInstanceState);
	   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	   
	   new getTeams().execute();  
	  
	  

	   
	   
   }
   
   @Override
   public void onListItemClick(ListView l, View v, int position, long id) {
	   Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
       intent.putExtra("index", position);
       startActivity(intent);
   }
   
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {	
 	   menu.add(0,MENU_SEARCH,Menu.NONE, R.string.ab_zoeken)
 	   	.setIcon(R.drawable.ic_action_search)
 	   	.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
 	   menu.add(0,MENU_SORT,Menu.NONE, R.string.ab_sorteren)
 	   	.setIcon(R.drawable.ic_action_search)
 	   	.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
	
	private class getTeams extends AsyncTask<Void, Void, Void> {  
		private ProgressDialog progressDialog;  
		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(TeamsActivity.this,  
			  "Bezig met laden", "Teams worden opgehaald...", true);  
		}
		
		@Override  
		protected Void doInBackground(Void... arg0) {  
			teams = api.getTeams();
			return null;       
		}
		
		@Override  
		protected void onPostExecute(Void result) {
			String[] values = new String[teams.size()];
		       for(int i = 0; i<teams.size(); i++){
		    	   values[i] = teams.get(i).getNaam();
		       }
		       
		       setListAdapter(new ArrayAdapter<String>(TeamsActivity.this, android.R.layout.simple_list_item_1, values));
		         progressDialog.dismiss(); 
		}
	}  
}
