package com.ut.bataapp.fragments;

import android.os.Bundle;
import android.view.*;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Klasse voor het representeren van een een LayoutFragment.
 * In dit generieke fragment wordt een bepaalde layout geladen
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class LayoutFragment extends SherlockFragment {	
	/**
	 * ID van de layout die wordt weergegeven
	 * @invariant mLayout != null
	 */
	private int mLayout;

	/**
	 * Constructor van dit fragment
	 * @param layout ID van de layout die in dit fragment wordt weergegeven.
	 */
	public LayoutFragment(int layout) {
		mLayout = layout;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(mLayout, container, false);
		return view;
	}
}
