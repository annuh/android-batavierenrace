package com.ut.bataapp.fragments;

import com.ut.bataapp.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Klasse voor het representeren van een een FoutcodesDialogFragment.
 * In dit DialogFragment worden de foutcodes en afkortingen weergegeven.
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class FoutcodesDialogFragment extends DialogFragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.foutcodes, container, false);
		this.getDialog().setTitle(R.string.ab_foutcodes);
		this.setStyle(STYLE_NORMAL, android.R.style.Theme_Holo);
		return v;
	}

}
