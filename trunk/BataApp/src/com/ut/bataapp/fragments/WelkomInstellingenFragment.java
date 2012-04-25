package com.ut.bataapp.fragments;

import com.ut.bataapp.R;
import com.ut.bataapp.activities.PreferencesActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Fragment voor weergeven instellingen-welkomstscherm.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class WelkomInstellingenFragment extends SherlockFragment implements OnClickListener {	
	// -- LIFECYCLEMETHODEN --
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.welkom_instellingen, container, false);
		((Button) result.findViewById(R.id.welkom_instellingen_button)).setOnClickListener(this);
		return result;
	}
	
	// -- ONCLICKLISTENER --
	
	public void onClick(View v) {
		// start teamoverzicht voor selecteren favoriet:
		Intent intent = new Intent(getActivity(), PreferencesActivity.class);
		intent.putExtra(PreferencesActivity.EXTRA_IN_SETUP, true);
		startActivity(intent);
	}
}