package com.ut.bataapp.fragments;

import java.util.Calendar;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;
import android.widget.Button;
import android.widget.TextView;

import com.ut.bataapp.MainActivity;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.*;


public class InfoOverzichtskaartenFragment extends SherlockFragment {
	
	
	
	public void openKaart(String kaart) {
		Intent intent = new Intent(getActivity().getApplicationContext(), AfbeeldingActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
   /** Called when the activity is first created. */
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   View view = inflater.inflate(R.layout.info_overzichtskaarten, container, false);
	   
	   Button herstart_barchem = (Button) view.findViewById(R.id.herstart_barchem);
	   herstart_barchem.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   Log.d("fm","Onclick");
               openKaart("herstart_barchem");
           }
       });
	   
	   Button herstart_ulft = (Button) view.findViewById(R.id.herstart_ulft);
	   herstart_ulft.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openKaart("herstart_barchem");
           }
       });
	   
	   Button campus_enschede = (Button) view.findViewById(R.id.campus_enschede);
	   campus_enschede.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openKaart("herstart_barchem");
           }
       });
	   
	   Button stad_enschede = (Button) view.findViewById(R.id.stad_enschede);
	   stad_enschede.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openKaart("herstart_barchem");
           }
       });
	   
	   Button stad_nijmegen = (Button) view.findViewById(R.id.stad_nijmegen);
	   stad_nijmegen.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openKaart("herstart_barchem");
           }
       });
	   
	   return view;
   }

   public static final class OverridePendingTransition {
       public static void invoke(Activity activity) {
           activity.overridePendingTransition(0, 0);
       }
   }
}
