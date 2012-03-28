package com.ut.bataapp.activities;

import java.util.ArrayList;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.MainActivity;
import com.ut.bataapp.MainActivity.OverridePendingTransition;
import com.ut.bataapp.adapters.TeamAdapter;
import com.ut.bataapp.objects.Team;

public class FavoTeamsActivity extends SherlockListActivity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences keyValues = this.getSharedPreferences("teams_follow", Context.MODE_PRIVATE);
        Map<String, ?> favoteams = keyValues.getAll();
        if(favoteams.size() < 1) {
        	new AlertDialog.Builder(this)
        	 .setTitle("U heeft geen favoriete teams!")
        	 .setMessage("Wilt u nu teams toevoegen?")
        	       .setCancelable(false)
        	       .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	        	   Intent i = new Intent(getApplicationContext(), TeamsActivity.class);
        	               startActivity(i);
        	           }
        	       })
        	       .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	        	   FavoTeamsActivity.this.finish();
        	           }
        	       }).create();
        } else {
        	ArrayList<Team> teams = new ArrayList<Team>();
        	for (Map.Entry<String, ?> entry : favoteams.entrySet()) {
        		teams.add(new Team((Integer) entry.getValue(),0,entry.getKey()));
        	}
        	TeamAdapter adapter = new TeamAdapter(FavoTeamsActivity.this, teams);
			setListAdapter(adapter);
        }
    }
	
	   @Override
	   public void onListItemClick(ListView l, View v, int position, long id) {
		   Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
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


}
