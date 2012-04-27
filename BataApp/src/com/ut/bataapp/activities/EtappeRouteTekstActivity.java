package com.ut.bataapp.activities;

import java.io.IOException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.ut.bataapp.R;
import com.ut.bataapp.objects.EtappeRoute;
import com.ut.bataapp.parser.CSV;

public class EtappeRouteTekstActivity extends SherlockActivity{

	private int mId;
	private EtappeRoute route;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mId = (savedInstanceState==null?getIntent().getIntExtra("id",1):savedInstanceState.getInt("id"));
		
		try {
			setContentView(R.layout.etappe_route_tekst);
			getSupportActionBar().setHomeButtonEnabled(true);
			LinearLayout container = (LinearLayout) findViewById(R.id.route_container);
			route = new CSV().parse(this.getResources().getAssets().open("lopersroutetekst/etappe"+mId+".txt"));
		
			this.setTitle("Looproute "+mId);
			
			View item = this.getLayoutInflater().inflate(R.layout.row_route, container, false);
			TextView icon = (TextView) item.findViewById(R.id.route_km);
			icon.setText(" ");
			icon.setBackgroundResource(R.drawable.start);
			TextView route_omschrijving = (TextView) item.findViewById(R.id.route_omschrijving);
			route_omschrijving.setText(route.getTabel().get(0)[1]);
			
			container.addView(item);
			container.addView(getLayoutInflater().inflate(R.drawable.divider, container, false));
			
			for(int i = 1; i < route.getTabel().size(); i++){
				item = this.getLayoutInflater().inflate(R.layout.row_route, container, false);
				TextView route_km = (TextView) item.findViewById(R.id.route_km);
				route_omschrijving = (TextView) item.findViewById(R.id.route_omschrijving);
				TextView route_locatie = (TextView) item.findViewById(R.id.route_locatie);
				Log.d("Tekst","Dit is de regel: "+route.getTabel().get(i)[1]);
				route_km.setText(route.getTabel().get(i)[0]);
				route_omschrijving.setText(route.getTabel().get(i)[1]);
				route_locatie.setText(route.getTabel().get(i)[2]);
				if(container.getOrientation()==LinearLayout.VERTICAL){
					if(route.getTabel().get(i)[2].equals("-")) route_locatie.setVisibility(View.GONE);
				}
				container.addView(item);
				container.addView(getLayoutInflater().inflate(R.drawable.divider, container, false));
			}
			item = this.getLayoutInflater().inflate(R.layout.row_route, container, false);
			icon = (TextView) item.findViewById(R.id.route_km);
			icon.setText(" ");
			icon.setBackgroundResource(R.drawable.finish);
			route_omschrijving = (TextView) item.findViewById(R.id.route_omschrijving);
			route_omschrijving.setText(route.getNaTabelTekst());
			container.addView(item);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//super.onSaveInstanceState(outState);
		outState.putInt("id",mId);
	}

}
