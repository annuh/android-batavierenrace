package com.ut.bataapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import com.ut.bataapp.activities.TeamsActivity;
import com.ut.bataapp.objects.Bericht;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.objects.Team;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class Utils {
	/* aantal milliseconden in een dag */
	private static final int MILLIS_IN_DAY = 60 * 60 * 24 * 1000;
	/* converterwaarden voor fahrenheit -> celsius */
	public static final byte DIFF_CF = 32;
	public static final float FACTOR_CF  = 1.8F;
	
	public static void noData(final Context context){
		new AlertDialog.Builder(context)
     	 .setTitle("Fout!")
     	 .setMessage("Controleer uw internetverbinding.")
		   .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		       public void onClick(DialogInterface dialog, int id) {
		    	   Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
		           context.startActivity(i);
		       }
		   }).create().show();
	}

	public static void goHome(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	public static boolean checkResponse(Context context, Response<?> response) {
		Log.d("Response code",String.valueOf(response.getStatus()));
		switch (response.getStatus()) {
			case Response.NOK_NO_DATA:
				Utils.noData(context);
				return false;
			case Response.NOK_OLD_DATA:
				Toast.makeText(context, "Kan geen data ophalen, data is mogelijk verouderd", Toast.LENGTH_LONG).show();
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
	public static void removeFavoteam(Context context, int id) {
		Log.d("FavoTeams", "DELETE: "+String.valueOf(id));
		SharedPreferences keyValues1 = context.getSharedPreferences("teams_follow", Context.MODE_PRIVATE);
		SharedPreferences.Editor keyValuesEditor1 = keyValues1.edit();
		keyValuesEditor1.remove(String.valueOf(id));
		keyValuesEditor1.commit();

		Toast toast1 = Toast.makeText(context, "U volgt dit team nu niet meer.", Toast.LENGTH_SHORT);
		toast1.show();
	}
	
	public static void addFavoTeam(Context context, Team team){
		Log.d("FavoTeams", "ADD: "+String.valueOf(team.getStartGroep()));
		SharedPreferences keyValues = context.getSharedPreferences("teams_follow", Context.MODE_PRIVATE);
		SharedPreferences.Editor keyValuesEditor = keyValues.edit();
		keyValuesEditor.putString(String.valueOf(team.getID()), team.getNaam());
		keyValuesEditor.commit();		
		Toast toast = Toast.makeText(context, "U volgt dit team nu.", Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public static ArrayList<Team> getFavoTeams(Context context) {
		SharedPreferences keyValues = context.getSharedPreferences("teams_follow", Context.MODE_PRIVATE);
		Map<String, ?> favoteams = keyValues.getAll();
		ArrayList<Team> teams = new ArrayList<Team>();

		for (Map.Entry<String, ?> entry : favoteams.entrySet()) {
			teams.add(new Team(Integer.parseInt(entry.getKey()), (String) entry.getValue()));
			Log.d("FavoTeam", ""+entry.getKey());
		}
		return teams;
	}
	
	public static void noFavoTeams(final Context context) {
		new AlertDialog.Builder(context)
		.setTitle("U heeft geen favoriete teams!")
		.setMessage("Wilt u nu teams toevoegen?")
		.setCancelable(false)
		.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent i = new Intent(context, TeamsActivity.class);
				context.startActivity(i);
			}
		})
		.setNegativeButton("Nee", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Utils.goHome(context);
			}
		}).create().show();
	}
	
	public static String stripNonDigits(final String input){
	    final StringBuilder sb = new StringBuilder();
	    for(int i = 0; i < input.length(); i++){
	        final char c = input.charAt(i);
	        if(c > 47 && c < 58){
	            sb.append(c);
	        }
	    }
	    return sb.toString();
	}
	
	/**
	 * Ontdoet cal van de tijdelementen.
	 * @param cal datum die van tijdelementen moet worden ontdaan
	 * @require cal != null
	 */
	public static void clearTime(Calendar cal) {
		 cal.set(Calendar.HOUR_OF_DAY, 0);
		 cal.set(Calendar.MINUTE, 0);
		 cal.set(Calendar.SECOND, 0);
		 cal.set(Calendar.MILLISECOND, 0);
	}
	
	/**
	 * Geeft het aantal dagen terug dat ligt tussen nu en date. Is negatief wanneer date in het verleden ligt.
	 * @param date datum waarvan bepaald moet worden over hoeveel dagen het de desbetreffende dag is
	 * @require date != null
	 * @return aantal dagen tussen nu en date
	 */
	public static short diffDays(Calendar date) {
		Calendar now = Calendar.getInstance();
	    clearTime(now);
	    clearTime(date);
		return (short) ((date.getTimeInMillis() - now.getTimeInMillis()) / MILLIS_IN_DAY);
	}
	
	/**
	 * Converteert temperatuur in fahrenheit f naar (hele) graden celsius.
	 * @param f temperatuur in fahrenheit
	 * @return temperatuur in (hele) graden celsius
	 */
	public static byte convertFtoC(byte f) {
		return (byte) Math.round((f-DIFF_CF) / FACTOR_CF);
	}

	public static void addBericht(Context context, String type, String bericht) {
		SharedPreferences keyValues = context.getSharedPreferences("push_berichten", Context.MODE_PRIVATE);
		SharedPreferences.Editor keyValuesEditor = keyValues.edit();
		keyValuesEditor.putString(new java.util.Date().toString() , type+bericht);
		keyValuesEditor.commit();
	}
	
	
	public static ArrayList<Bericht> getBerichten(Context context) {
		SharedPreferences keyValues = context.getSharedPreferences("push_berichten", Context.MODE_PRIVATE);
		Map<String, ?> pushberichten = keyValues.getAll();
		ArrayList<Bericht> berichten = new ArrayList<Bericht>();

		for (Map.Entry<String, ?> entry : pushberichten.entrySet()) {
			Log.d("Bericht",(String)entry.getValue());
			Bericht bericht = new Bericht();
			bericht.setDatum(entry.getKey());
			int code;
			switch(((String) entry.getValue()).charAt(0)) {
			case 'y':
				code = Bericht.GEEL;
				break;
			case 'g':
				code = Bericht.GROEN;
				break;
			case 'r':
				code = Bericht.ROOD;
				break;
			case 'w':
				code = Bericht.WEER;
				break;
			default:
				code = Bericht.CUSTOM;
			}
			bericht.setCode(code);
			bericht.setTitel(((String) entry.getValue()).substring(1));
			bericht.setBericht(entry.getKey());
			berichten.add(bericht);
		}
		return berichten;
		
	}
}
