package com.ut.bataapp.fragments;

import java.net.URI;
import java.net.URISyntaxException;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.activities.EtappeRouteTekstActivity;
import com.ut.bataapp.objects.Etappe;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class EtappeRoutesFragment extends SherlockFragment {
    
	private Etappe etappe;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	etappe = ((EtappeActivity) getActivity()).getEtappe();
    	View view = inflater.inflate(R.layout.etappe_routes, container, false);
    	
    	final String[] auto_maps = getResources().getStringArray(R.array.url_autoroutes);
    	
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
    	
    	ImageView lopers_image = (ImageView) view.findViewById(R.id.lopers_image);
    	lopers_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent mapLopersroute = new Intent(Intent.ACTION_VIEW); 
                Uri uri0 = Uri.parse("http://g.co/maps/bkwev"); 
                mapLopersroute.setData(uri0); 
                startActivity(Intent.createChooser(mapLopersroute, "Lopersroute routebeschrijving"));
            }
        });
    	
    	ImageView lopers_tekst = (ImageView) view.findViewById(R.id.lopers_tekst);
    	lopers_tekst.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),EtappeRouteTekstActivity.class);
				intent.putExtra("id",etappe.getId());
				startActivity(intent);
			}
		});
    	
    	ImageView autos_map = (ImageView) view.findViewById(R.id.autos_maps);
    	autos_map.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent mapAutoroute = new Intent(Intent.ACTION_VIEW);
				if(auto_maps[etappe.getId()-1].equals("")){
					Toast.makeText(getActivity(), "Van deze etappe is geen auto route", Toast.LENGTH_LONG).show();
				}else{
					Uri uri0 = Uri.parse(auto_maps[etappe.getId()-1]);
					mapAutoroute.setData(uri0); 
					startActivity(Intent.createChooser(mapAutoroute, "Autoroute routebeschrijving"));
				}
			}
		});
    	
    	return view;
    	
    }
}
