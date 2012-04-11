package com.ut.bataapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.actionbarsherlock.app.SherlockActivity;
import com.ut.bataapp.MainActivity;

public class BataActivity extends SherlockActivity {


	public void noData(){
		new AlertDialog.Builder(this)
     	 .setTitle("U heeft geen favoriete teams!")
     	 .setMessage("Wilt u nu teams toevoegen?")
		   .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
		       public void onClick(DialogInterface dialog, int id) {
		    	   Intent i = new Intent(getApplicationContext(), MainActivity.class);
		           startActivity(i);
		       }
		   }).create();
	}
}
