package com.ut.bataapp.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.objects.Etappe;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class EtappeRoutes extends SherlockFragment {
    
	private Etappe etappe;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	etappe = ((EtappeActivity) getActivity()).getEtappe();
    	View view = inflater.inflate(R.layout.etappe_routes, container, false);
    	
    	String [] lopers_maps =  getResources().getStringArray(R.array.looproutes_earth);
    	
    	ImageView lopers_map = (ImageView) view.findViewById(R.id.lopers_maps);
    	lopers_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent mapLopersroute = new Intent(Intent.ACTION_VIEW); 
                Uri uri0 = Uri.parse("http://maps.google.com/maps?f=d&source=s_d&saddr=51.82002+5.8679&daddr=51.82939+5.94184&geocode=FfS1FgMdfIlZAA%3BFY7aFgMdUKpaAA&aq=&sll=51.82939,5.94184&sspn=0.01037,0.027874&vpsrc=0&t=h&hl=nl&mra=ls&ie=UTF8&z=14&dirflg=d"); 
                mapLopersroute.setData(uri0); 
                startActivity(Intent.createChooser(mapLopersroute, "Lopersroute maps"));
            }
        });
    	
    	ImageView lopers_earth = (ImageView) view.findViewById(R.id.lopers_earth);
    	lopers_earth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent mapLopersroute = new Intent(Intent.ACTION_VIEW); 
                Uri uri0 = Uri.parse("http://g.co/maps/bkwev"); 
                mapLopersroute.setData(uri0); 
                startActivity(Intent.createChooser(mapLopersroute, "Lopersroute routebeschrijving"));
            }
        });
    	
    	return view;
    	
    }
    
}
