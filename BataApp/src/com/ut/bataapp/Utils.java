package com.ut.bataapp;

import com.ut.bataapp.objects.Response;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Utils {

	public static void old_data(Context context){
		Toast.makeText(context, "Geen nieuwe data", Toast.LENGTH_LONG).show();
	}
	
	public static void noData(final Context context){
		new AlertDialog.Builder(context)
     	 .setTitle("U heeft geen favoriete teams!")
     	 .setMessage("Wilt u nu teams toevoegen?")
		   .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
		       public void onClick(DialogInterface dialog, int id) {
		    	   Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
		           context.startActivity(i);
		       }
		   }).create();
	}

	
	public static void goHome(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	public static boolean checkResponse(Context context, Response response) {
		switch (response.getStatus()) {
			case Response.NOK_NO_DATA:
				Utils.noData(context);
				return false;
			case Response.NOK_OLD_DATA:
				return true;
			case Response.OK_NO_UPDATE:
				return true;
			case Response.OK_UPDATE:
				return true;
			default:
				return false;
		}
		
	}
	
	/**
	 * Favo teams
	 */	
	public static void removeFavoteam(int id) {
		Log.d("FavoTeams", String.valueOf(id));
	}
	
	public static void addFavoTeam(int id){
		Log.d("FavoTeams", String.valueOf(id));
	}
}
