package com.ut.bataapp.fragments;

import java.io.IOException;
import java.io.InputStream;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.activities.AfbeeldingActivity;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.activities.EtappeRouteTekstActivity;
import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.R;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EtappeRoutesFragment extends SherlockFragment {
    
	private Etappe etappe;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	etappe = ((EtappeActivity) getActivity()).getEtappe();
    	View view = inflater.inflate(R.layout.etappe_routes, container, false);
    	
    	final String[] auto_maps = getResources().getStringArray(R.array.url_autoroutes);
    	final String[] lopers_maps = getResources().getStringArray(R.array.url_lopersroutes);
    	

    	
    	//Kaart lopers route
    	try {
    		ImageView lopers_kaart = new ImageView(this.getActivity());
    		InputStream input = null;
    		int etappeNummer = etappe.getId();
    		if(etappeNummer == 25) etappeNummer = 24;
			input = getResources().getAssets().open("lopersroutekaart/loop"+etappeNummer+".jpg");

	    	lopers_kaart.setImageDrawable(Drawable.createFromStream(input,null));
	    	lopers_kaart.setAdjustViewBounds(true);
	    	lopers_kaart.setMaxHeight(200);
	    	lopers_kaart.setMaxWidth(350);
	    	LinearLayout layout = (LinearLayout) view.findViewById(R.id.etappe_route_layout);
	    	layout.addView(lopers_kaart,1);
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
				intent.putExtra("type", "Lopers");
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
            	startActivity(Intent.createChooser(mapLopersroute, "Lopersroute routebeschrijving"));
            }
        });
    	
    	//Google maps auto route (To check etappe: 4,5,8,9,21)
		Button autos_map = (Button) view.findViewById(R.id.auto_googlemaps_route);
    	autos_map.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent mapAutoroute = new Intent(Intent.ACTION_VIEW);
				if(!auto_maps[etappe.getId()-1].equals("")){
					Log.d("Auto route","Etappe nummer: "+etappe.getId());
					Log.d("Auto route","Link: "+auto_maps[etappe.getId()-1]);
					Uri uri0 = Uri.parse(auto_maps[etappe.getId()-1]);
					mapAutoroute.setData(uri0); 
					startActivity(Intent.createChooser(mapAutoroute, "Autoroute routebeschrijving"));
				}
			}
		});
    	if(auto_maps[etappe.getId()-1].equals("")) autos_map.setVisibility(View.GONE);
    	return view;
    }
}
