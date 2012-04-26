package com.ut.bataapp.fragments;

import com.ut.bataapp.R;

import android.content.Context;
import android.os.Bundle;
import android.view.*;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Klasse voor het representeren van een een BataRadio-programmeringfragment. Retourneert simpelweg de bijbehorende layout/view.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class BataRadioProgrammeringFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		inflater.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		return inflater.inflate(R.layout.bataradio_programmering, null);
	}
	
	/*@Override
    public void onSaveInstanceState(Bundle outState) { // workaround voor bug in FragmentManagerImpl.saveFragmentBasicState()
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }*/
}