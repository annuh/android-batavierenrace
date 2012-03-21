package com.ut.bataapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.*;


public class InfoAlgemeenFragment extends SherlockFragment {
	
	
	
	public void openAlgemeen(String page) {
		Intent intent = new Intent(getActivity().getApplicationContext(), InfoAlgemeenActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("page", page);
		startActivity(intent);
	}
	
   /** Called when the activity is first created. */
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   View view = inflater.inflate(R.layout.info_algemeen, container, false);
	   
	   Button info_bustijden = (Button) view.findViewById(R.id.info_bustijden);
	   info_bustijden.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   Log.d("fm","Onclick");
        	   openAlgemeen("bustijden");
           }
       });
	   
	   Button info_slapen = (Button) view.findViewById(R.id.info_slapen);
	   info_slapen.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openAlgemeen("slapen");
           }
       });
	   
	   Button info_watmoetjeweten = (Button) view.findViewById(R.id.info_watmoetjeweten);
	   info_watmoetjeweten.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openAlgemeen("info_watmoetjeweten");
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
