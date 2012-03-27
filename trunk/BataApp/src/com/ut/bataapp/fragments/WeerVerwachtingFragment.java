package com.ut.bataapp.fragments;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ut.bataapp.R;

public class WeerVerwachtingFragment extends SherlockFragment {
		
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	   return inflater.inflate(R.layout.weer_verwachting, container, false);
   }
}
