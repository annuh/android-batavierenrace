package com.ut.bataapp.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.R;
import com.ut.bataapp.MainActivity;
import com.ut.bataapp.Utils;
import com.ut.bataapp.MainActivity.OverridePendingTransition;
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