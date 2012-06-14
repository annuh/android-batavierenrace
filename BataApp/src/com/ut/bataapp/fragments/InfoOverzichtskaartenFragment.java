package com.ut.bataapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.*;

/**
 * Klasse voor het representeren van een een InfoOverzichtskaartenFragment.
 * In dit fragment staan links naar de overzichtskaarten van de Batavierenrac. 
 * Er staan links naar de volgende overzichtskaarten:
 * - Herstart Barchem
 * - Herstart Ulft
 * - Campus Enschede
 * - Stad Enschede
 * - Stad Nijmegen
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class InfoOverzichtskaartenFragment extends SherlockFragment {
	
	/**
	 * Start AfbeeldingsActivity en toont een kaart.
	 * @param kaart ID van de kaart die moet worden weergegeven
	 */
	public void openKaart(int kaart) {
		Intent intent = new Intent(getActivity(), AfbeeldingActivity.class);
		intent.putExtra(AfbeeldingActivity.TYPE, "overzichtskaart");
		intent.putExtra("kaart", kaart);
		startActivity(intent);
	}

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   View view = inflater.inflate(R.layout.info_overzichtskaarten, container, false);
	   
	   Button herstart_barchem = (Button) view.findViewById(R.id.herstart_barchem);
	   herstart_barchem.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openKaart(AfbeeldingActivity.ID_HERSTART_BARCHEM);
           }
       });
	   
	   Button herstart_ulft = (Button) view.findViewById(R.id.herstart_ulft);
	   herstart_ulft.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openKaart(AfbeeldingActivity.ID_HERSTART_ULFT);
           }
       });
	   
	   Button campus_enschede = (Button) view.findViewById(R.id.campus_enschede);
	   campus_enschede.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openKaart(AfbeeldingActivity.ID_CAMPUS_ENSCHEDE);
           }
       });
	   
	   Button stad_enschede = (Button) view.findViewById(R.id.stad_enschede);
	   stad_enschede.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openKaart(AfbeeldingActivity.ID_STAD_ENSCHEDE);
           }
       });
	   
	   Button stad_nijmegen = (Button) view.findViewById(R.id.stad_nijmegen);
	   stad_nijmegen.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openKaart(AfbeeldingActivity.ID_STAD_NIJMEGEN);
           }
       });
	   
	   return view;
   }

}
