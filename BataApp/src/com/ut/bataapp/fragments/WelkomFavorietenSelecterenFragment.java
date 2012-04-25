package com.ut.bataapp.fragments;

import com.ut.bataapp.R;
import com.ut.bataapp.activities.TeamsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Fragment voor weergeven favorieten selecteren-welkomstscherm.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class WelkomFavorietenSelecterenFragment extends SherlockFragment implements OnClickListener {	
	// -- LIFECYCLEMETHODEN --
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.welkom_favorieten_selecteren, container, false);
		((Button) result.findViewById(R.id.welkom_favorieten_selecteren_button)).setOnClickListener(this);
		return result;
	}
	
	// -- ONCLICKLISTENER --
	
	public void onClick(View v) {
		// start teamoverzicht voor selecteren favoriet:
		Intent intent = new Intent(getActivity(), TeamsActivity.class);
		intent.putExtra(TeamsActivity.EXTRA_IN_SETUP, true);
		startActivity(intent);
	}
}