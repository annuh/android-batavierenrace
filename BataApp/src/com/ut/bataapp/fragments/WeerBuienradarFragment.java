package com.ut.bataapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;

public class WeerBuienradarFragment extends SherlockFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.weer_buienradar, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		WebView mWebView = (WebView) getActivity().findViewById(R.id.webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		String summary = "<iframe src=\"http://mijn.buienradar.nl/lokalebuienradar.aspx?voor=1&lat=52.1046995&x=1&y=1&lng=6.2937736&overname=2&zoom=8&naam=doetinchem&size=2&map=1\" scrolling=no width=256 height=256 frameborder=no></iframe>";
		mWebView.loadData(summary, "text/html", null);
	}
}