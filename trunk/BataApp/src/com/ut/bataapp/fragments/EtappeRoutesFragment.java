package com.ut.bataapp.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.activities.AfbeeldingActivity;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.activities.EtappeRouteTekstActivity;
import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.R;
import android.content.Intent;
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
    	

    	
    	//Kaart lopers route
    	ImageView lopers_kaart = new ImageView(this.getActivity());
    	lopers_kaart.setImageResource(R.drawable.kaartvanetappe);
    	lopers_kaart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    	lopers_kaart.setAdjustViewBounds(true);
    	lopers_kaart.setMaxHeight(200);
    	lopers_kaart.setMaxWidth(200);
    	LinearLayout layout = (LinearLayout) view.findViewById(R.id.etappe_route_layout);
    	layout.addView(lopers_kaart,1);
    	
    	
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
            	Toast.makeText(getActivity(), "coming soon"+'\u2122', Toast.LENGTH_SHORT).show();
            }
        });
    	
    	//Google maps auto route
		Button autos_map = (Button) view.findViewById(R.id.auto_googlemaps_route);
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
    	return view;
    	
    }
}
