package com.ut.bataapp.fragments;

import com.ut.bataapp.R;
import com.ut.bataapp.activities.ColofonActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;

public class InfoColofonFragment extends SherlockFragment {	
	
	public void openColofon(int tab) {
		Intent intent = new Intent(getActivity(), ColofonActivity.class);
		intent.putExtra(ColofonActivity.EXTRA_TAB, tab);
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
        	   openColofon(ColofonActivity.TAB_CONTACT);
           }
       });
	   
	   Button info_colofon = (Button) view.findViewById(R.id.info_colofon);
	   info_colofon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openColofon(ColofonActivity.TAB_COLOFON);
           }
       });
	   
	   Button info_disclaimer = (Button) view.findViewById(R.id.info_disclaimer);
	   info_disclaimer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openColofon(ColofonActivity.TAB_DISCLAIMER);
           }
       });
	   
	   return view;
   }
}
