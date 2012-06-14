package com.ut.bataapp.activities;

import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;

import com.ut.bataapp.R;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;

/**
 * Activity waarin een afbeelding kan worden getoond. Het type van de afbeelding (overzichtskaart of hoogteverschil) wordt
 * als een Bundle, met key "type", worden toegevoegd aan de Intent van deze Activity.
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class AfbeeldingActivity extends SherlockActivity {
	
	public static final int
		ID_STAD_NIJMEGEN = 0,
		ID_HERSTART_ULFT = 1,
		ID_HERSTART_BARCHEM = 2,
		ID_STAD_ENSCHEDE = 3,
		ID_CAMPUS_ENSCHEDE = 4;
	public static final String TYPE = "type";
	
	public static String[][] overzichtskaarten = {{"stad_nijmegen.jpg", "Start Nijmegen"}, {"herstart_ulft.jpg", "Herstart Ulft"}, {"herstart_barchem.jpg", "Herstart Barchem"},  {"stad_enschede.jpg", "Stad Enschede"}, {"campus_enschede.jpg", "Campus Enschede"}};
	public static String[][] hoogteverschillen = {};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		String type = getIntent().getStringExtra(TYPE);
		String locatie = "";
		String titel = "";
		
		
		if(type.equals("overzichtskaart")){
			int kaart = getIntent().getIntExtra("kaart", 0);
			if(kaart<0 || kaart>(overzichtskaarten.length - 1)){
				kaart = 0;
			}
			locatie = "overzichtkaart/" + overzichtskaarten[kaart][0];
			titel = overzichtskaarten[kaart][1];
		} else if(type.equals("hoogteverschil")){
			int kaart = getIntent().getIntExtra("hoogteverschil", 0);
			if(kaart<0 || kaart>(hoogteverschillen.length - 1)){
				kaart = 0;
			}
			locatie = hoogteverschillen[kaart][0];
			titel = hoogteverschillen[kaart][1];
		} else{
			int kaart = getIntent().getIntExtra("kaart",0);
			titel = "Lopersroute etappe " + kaart;
			if(kaart==25) kaart = 24;
			locatie = (type+"/loop"+kaart+".jpg");	
		}
		getSupportActionBar().setTitle(titel);
		setContentView(R.layout.afbeelding);
		WebView webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.loadUrl("file:///android_asset/" + locatie);	
		if(type.equals("Lopersroutekaart"))
			webview.setMinimumWidth(LayoutParams.WRAP_CONTENT);
    }

	@Override
	protected void onPause() {
		super.onPause();
		((WebView) findViewById(R.id.webview)).onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		((WebView) findViewById(R.id.webview)).onResume();
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