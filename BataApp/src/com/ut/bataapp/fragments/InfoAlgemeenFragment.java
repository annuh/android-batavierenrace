package com.ut.bataapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.InfoAlgemeenActivity;

public class InfoAlgemeenFragment extends SherlockFragment {	
	public void openAlgemeen(int tab) {
		Intent intent = new Intent(getActivity().getApplicationContext(), InfoAlgemeenActivity.class);
		intent.putExtra(InfoAlgemeenActivity.EXTRA_TAB, tab);
		startActivity(intent);
	}
	
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   View view = inflater.inflate(R.layout.info_algemeen, container, false);
	   
	   Button info_bustijden = (Button) view.findViewById(R.id.info_bustijden);
	   info_bustijden.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openAlgemeen(InfoAlgemeenActivity.TAB_BUSTIJDEN);
           }
       });
	   
	   Button info_slapen = (Button) view.findViewById(R.id.info_slapen);
	   info_slapen.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openAlgemeen(InfoAlgemeenActivity.TAB_SLAPEN);
           }
       });
	   
	   Button info_watmoetjeweten = (Button) view.findViewById(R.id.info_watmoetjeweten);
	   info_watmoetjeweten.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openAlgemeen(InfoAlgemeenActivity.TAB_WATMOETJEWETEN);
           }
       });
	   
	   return view;
   }
}
