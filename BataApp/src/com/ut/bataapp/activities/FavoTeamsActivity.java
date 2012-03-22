package com.ut.bataapp.activities;

import java.util.ArrayList;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockListActivity;
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
        	   // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        	}
        	TeamAdapter adapter = new TeamAdapter(FavoTeamsActivity.this, teams);
			setListAdapter(adapter);
        }
    }


}
