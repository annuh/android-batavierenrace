package com.ut.bataapp.fragments;

import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.AfbeeldingActivity;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.activities.EtappeRouteTekstActivity;
import com.ut.bataapp.objects.Etappe;

/**
 * Klasse voor het representeren van een een EtappeRoutesFragment.
 * Van een een bepaalde route wordt een overzichtskaart weergegeven met hieronder links naar een textuele looproutebeschrijving en links naar 
 * Google Maps voor de looproute en autoroute.
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class EtappeRoutesFragment extends SherlockFragment {
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	final Etappe etappe = ((EtappeActivity) getActivity()).getEtappe();
    	View view = inflater.inflate(R.layout.etappe_routes, container, false);
    	final String[] auto_maps = getResources().getStringArray(R.array.url_autoroutes);
    	final String[] lopers_maps = getResources().getStringArray(R.array.url_lopersroutes);
    	    	
    	//Kaart lopers route
    	try {
    		ImageView lopers_kaart = (ImageView) view.findViewById(R.id.etappe_routes_lopersroute_kaart);
    		int etappeNummer = etappe.getId();
    		if (etappeNummer == 25) 
    			etappeNummer = 24;
			InputStream input = getResources().getAssets().open("lopersroutekaart/loop"+etappeNummer+".jpg");

	    	lopers_kaart.setImageDrawable(Drawable.createFromStream(input,null));

	    	lopers_kaart.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	            	Intent intent = new Intent(getActivity(),AfbeeldingActivity.class);
	            	intent.putExtra("type","lopersroutekaart");
	            	intent.putExtra("kaart",etappe.getId());
	            	startActivity(intent);
	            }
	        });
		} catch (IOException e) {
		}
    	
    	//Tekst lopers route
    	Button lopers_tekst = (Button) view.findViewById(R.id.loper_tekst_routebeschijving);
    	lopers_tekst.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),EtappeRouteTekstActivity.class);
				intent.putExtra("id",etappe.getId());
				startActivity(intent);
			}
		});
    	
    	//Google maps lopers route
    	Button lopers_map = (Button) view.findViewById(R.id.loper_googlemaps_route);
    	lopers_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent mapLopersroute = new Intent(Intent.ACTION_VIEW);
            	Uri uri0 = Uri.parse(lopers_maps[etappe.getId()-1]);
            	mapLopersroute.setData(uri0);
            	startActivity(Intent.createChooser(mapLopersroute, getString(R.string.etappe_routes_routebeschrijving_maps_looproute)));
            }
        });
    	
    	//Google maps auto route (To check etappe: 5,8,9,21)
		Button autos_map = (Button) view.findViewById(R.id.auto_googlemaps_route);
    	autos_map.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent mapAutoroute = new Intent(Intent.ACTION_VIEW);
				if(!auto_maps[etappe.getId()-1].equals("")){
					Uri uri0 = Uri.parse(auto_maps[etappe.getId()-1]);
					mapAutoroute.setData(uri0); 
					startActivity(Intent.createChooser(mapAutoroute, getString(R.string.etappe_routes_routebeschrijving_maps_autoroute)));
				}
			}
		});
    	if(auto_maps[etappe.getId()-1].equals("")) autos_map.setVisibility(View.GONE);
    	return view;
    }
}
