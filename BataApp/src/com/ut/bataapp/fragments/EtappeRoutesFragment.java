package com.ut.bataapp.fragments;

import java.net.URI;
import java.net.URISyntaxException;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.AfbeeldingActivity;
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
    	
    	//Google maps lopers route
    	/*ImageView lopers_map = (ImageView) view.findViewById(R.id.lopers_maps);
    	lopers_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Toast.makeText(getActivity(), "coming soon"+'\u2122', Toast.LENGTH_SHORT).show();
            }
        });
    	
    	//Kaart lopers route
    	ImageView lopers_image = (ImageView) view.findViewById(R.id.lopers_image);
    	lopers_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent intent = new Intent(getActivity(),AfbeeldingActivity.class);
            	intent.putExtra("type","lopersroute");
            	intent.putExtra("kaart",etappe.getId());
            	startActivity(intent);
            }
        });
    	
    	//Tekst lopers route
    	ImageView lopers_tekst = (ImageView) view.findViewById(R.id.lopers_tekst);
    	lopers_tekst.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),EtappeRouteTekstActivity.class);
				intent.putExtra("id",etappe.getId());
				intent.putExtra("type", "Lopers");
				startActivity(intent);
			}
		});
    	
    	//Google maps auto route
    	ImageView autos_map = (ImageView) view.findViewById(R.id.autos_maps);
    	autos_map.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent mapAutoroute = new Intent(Intent.ACTION_VIEW);
				if(auto_maps[etappe.getId()-1].equals("")){
					Toast.makeText(getActivity(), "Van deze etappe is geen auto route", Toast.LENGTH_SHORT).show();
				}else{
					Uri uri0 = Uri.parse(auto_maps[etappe.getId()-1]);
					mapAutoroute.setData(uri0); 
					startActivity(Intent.createChooser(mapAutoroute, "Autoroute routebeschrijving"));
				}
			}
		});
    	
    	//Kaart auto route
    	ImageView autos_image = (ImageView) view.findViewById(R.id.autos_image);
    	autos_image.setOnClickListener(new View.OnClickListener(){
    		@Override
    		public void onClick(View view){
    			Toast.makeText(getActivity(), "coming soon"+'\u2122', Toast.LENGTH_SHORT).show();
    			/**
    			Intent intent = new Intent(getActivity(),AfbeeldingActivity.class);
    			intent.putExtra("type","autoroute");
    			intent.putExtra("kaart",etappe.getId());
    			startActivity(intent);
    			
    		}
    	});

    	//Tekst auto route
    	ImageView autos_tekst = (ImageView) view.findViewById(R.id.autos_tekst);
    	autos_tekst.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),EtappeRouteTekstActivity.class);
				intent.putExtra("id",etappe.getId());
				intent.putExtra("type","Auto");
				startActivity(intent);
			}
		});
    	*/
    	return view;
    	
    }
}
