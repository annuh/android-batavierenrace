package com.ut.bataapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.ErrataActivity;
import com.ut.bataapp.activities.InfoAlgemeenActivity;

/**
 * Klasse voor het representeren van een een InfoAlgemeenFragment.
 * In dit fragment staan links naar de volgende fragments: 
 * - Algemene informatie
 * - Tijden
 * - Slapen informatie
 * - Wat moet je weten
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class InfoAlgemeenFragment extends SherlockFragment {
	
	/**
	 * In deze methode wordt InfoAlgemeenActivity gestart waar een bepaald tabblad standaard wordt weergegeven
	 * @param tab Het ID van het tabblad uit InfoAlgemeenActivity dat standaard weergegeven wordt.
	 */
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

		Button info_errata = (Button) view.findViewById(R.id.info_errata);
		info_errata.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ErrataActivity.class);
				startActivity(intent);
			}
		});

		return view;
	}
}
