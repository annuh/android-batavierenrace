package com.ut.bataapp.activities;

import java.io.IOException;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.R;
import com.ut.bataapp.Utils;
import com.ut.bataapp.objects.EtappeRoute;
import com.ut.bataapp.parser.CSV;

/**
 * Activity voor het tonen van de looproutes. Er wordt een .txt bestand geladen waarin de routes, volgens bepaalde stijlregels, zijn opgeslagen.  
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne van de Venis
 * @version 1.0
 */
public class EtappeRouteTekstActivity extends SherlockActivity{

	/** De etappe waarvoor de looproute moet worden opgehaald. */
	private int mId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mId = (savedInstanceState==null?getIntent().getIntExtra("id",1):savedInstanceState.getInt("id"));
		
		try {
			setContentView(R.layout.etappe_route_tekst);
			getSupportActionBar().setHomeButtonEnabled(true);
			LinearLayout container = (LinearLayout) findViewById(R.id.route_container);
			EtappeRoute route = new CSV().parse(this.getResources().getAssets().open("lopersroutetekst/etappe"+mId+".txt"));
		
			this.setTitle(getString(R.string.etappe_routes_looproute) + " " + mId);
			
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
		outState.putInt("id",mId);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Utils.goHome(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
