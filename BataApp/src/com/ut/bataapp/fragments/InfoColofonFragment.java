package com.ut.bataapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.*;

public class InfoColofonFragment extends SherlockFragment {	
	
	public void openColofon(String page) {
		Intent intent = new Intent(getActivity().getApplicationContext(), ColofonActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("page", page);
		startActivity(intent);
	}
	
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   View view = inflater.inflate(R.layout.info_colofon, container, false);
	   
	   Button info_contact = (Button) view.findViewById(R.id.info_contact);
	   info_contact.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openColofon("contact");
           }
       });
	   
	   Button info_colofon = (Button) view.findViewById(R.id.info_colofon);
	   info_colofon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openColofon("colofon");
           }
       });
	   
	   Button info_disclaimer = (Button) view.findViewById(R.id.info_disclaimer);
	   info_disclaimer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openColofon("disclaimer");
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
