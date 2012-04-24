package com.ut.bataapp.fragments;

import com.ut.bataapp.R;

import android.os.Bundle;
import android.view.*;
import android.webkit.WebView;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Fragment voor weergeven buienradar (in WebView).
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class WeerBuienradarFragment extends SherlockFragment {
	// -- CONSTANTEN --
	
	/* Buienradar-data die in webview zal worden geladen */
	public static final String LOAD_DATA = "<iframe src=\"http://mijn.buienradar.nl/lokalebuienradar.aspx?voor=1&lat=52.1046995&x=1&y=1&lng=6.2937736&overname=2&zoom=8&naam=doetinchem&size=2&map=1\" scrolling=no width=256 height=256 frameborder=no></iframe>";
	/* mimetype van data */
	public static final String MIME_TYPE = "text/html";
	
	// -- LIFECYCLEMETHODEN --
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.weer_buienradar, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		WebView webView = (WebView) getActivity().findViewById(R.id.weer_webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadData(LOAD_DATA, MIME_TYPE, null);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		((WebView) getActivity().findViewById(R.id.weer_webview)).onPause();
	}
}