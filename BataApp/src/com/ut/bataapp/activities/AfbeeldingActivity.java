package com.ut.bataapp.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.R;
import com.ut.bataapp.Utils;

import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;

public class AfbeeldingActivity extends SherlockActivity {
	
	public static String[][] overzichtskaarten = {{"herstart_barchem.jpg", "Herstart Barchem"}, {"herstart_ulft.jpg", "Herstart Ulft"}, {"campus_enschede.jpg", "Campus Enschede"}, {"stad_enschede.jpg", "Enschede stad"}, {"stad_nijmegen.jpg", "Start Nijmegen"}};
	public static String[][] hoogteverschillen = {};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		String type = getIntent().getStringExtra("type");
		String locatie = "";
		String titel = "";
		
		
		if(type.equals("overzichtskaart")){
			int kaart = getIntent().getIntExtra("kaart", 0);
			if(kaart<0 || kaart>(overzichtskaarten.length - 1)){
				kaart = 0;
			}
			locatie = overzichtskaarten[kaart][0];
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
		if(type.equals("Lopersroutekaart")){
			webview.setMinimumWidth(LayoutParams.WRAP_CONTENT);
		}
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