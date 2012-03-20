package com.ut.bataapp.activities;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.R;
import android.webkit.WebView;

public class AfbeeldingActivity extends SherlockActivity {
	
	String kaart;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Herstart Barchem");
		setContentView(R.layout.afbeelding);
		WebView webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.loadUrl("file:///android_asset/herstart_barchem.jpg");
    }

}