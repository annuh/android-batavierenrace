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
	private String mType;
	private EtappeRoute route;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mId = (savedInstanceState==null?getIntent().getIntExtra("id",1):savedInstanceState.getInt("id"));
		mType = (savedInstanceState==null?getIntent().getStringExtra("type"):savedInstanceState.getString("type"));
		try {
			setContentView(R.layout.etappe_route_tekst);
			LinearLayout container = (LinearLayout) findViewById(R.id.route_container);
			route = new CSV().parse(this.getResources().getAssets().open("lopersroutetekst/etappe"+mId+".txt"));
		
			this.setTitle(mType+"route "+mId);
			Log.d("Routes",route.getVoorTabelTekst());
			//TextView voor = (TextView) findViewById(R.id.voor_tabel);
			//voor.setText(route.getVoorTabelTekst());
			//TableLayout table = (TableLayout) findViewById(R.id.route_tabel);
			for(int i = 0; i < route.getTabel().size(); i++){
				View item = this.getLayoutInflater().inflate(R.layout.row_route, container, false);
				TextView route_km = (TextView) item.findViewById(R.id.route_km);
				TextView route_omschrijving = (TextView) item.findViewById(R.id.route_omschrijving);
				TextView route_locatie = (TextView) item.findViewById(R.id.route_locatie);
				
				route_km.setText(route.getTabel().get(i)[0]);
				route_omschrijving.setText(route.getTabel().get(i)[1]);
				route_locatie.setText(route.getTabel().get(i)[2]);
				container.addView(item);
				container.addView(getLayoutInflater().inflate(R.drawable.divider, container, false));
			}
			TextView na = (TextView) findViewById(R.id.na_tabel);
			na.setText(route.getNaTabelTekst());
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("id",mId);
		outState.putString("type",mType);
	}

}
