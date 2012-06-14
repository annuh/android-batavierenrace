package com.ut.bataapp.fragments;

import com.ut.bataapp.R;
import com.ut.bataapp.activities.ColofonActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Klasse voor het representeren van een een InfoColofonFragment.
 * In dit fragment staan links naar verschillende tabbladen van de Colofon-pagina, namelijk
 * - Contact
 * - Colofon
 * - Disclaimer
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class InfoColofonFragment extends SherlockFragment {	

	/**
	 * Deze methode start ColofonActivity waarbij een specifiek tabblad als eerst wordt weergegeven.
	 * @param tab ID van het tablad dat als eerst moet worden weergegeven.
	 */
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
