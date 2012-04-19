package com.ut.bataapp.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.R;
import com.ut.bataapp.Utils;

import android.webkit.WebView;

public class AfbeeldingActivity extends SherlockActivity {
	
	public static String[][] overzichtskaarten = {{"herstart_barchem.jpg", "Herstart Barchem"}, {"herstart_ulft.jpg", "Herstart Ulft"}, {"campus_enschede.jpg", "Campus Enschede"}, {"stad_enschede.jpg", "Enschede stad"}, {"stad_nijmegen.jpg", "Nijmegen stad"}};
	public static String[][] hoogteverschillen = {};
	private Boolean[] loopKaartRotatie = {false,true,true,true,true,false,true};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
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
			locatie = (type+"/"+kaart+".jpg");
			titel = type+" "+kaart;
			if(kaart<7 && loopKaartRotatie[kaart-1]){
				this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
		}
		getSupportActionBar().setTitle(titel);
		setContentView(R.layout.afbeelding);
		WebView webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.loadUrl("file:///android_asset/" + locatie);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Utils.goHome(getApplicationContext());
				break;
		}
		return super.onOptionsItemSelected(item);
	}

}