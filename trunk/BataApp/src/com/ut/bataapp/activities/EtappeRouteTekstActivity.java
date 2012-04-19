package com.ut.bataapp.activities;

import java.io.IOException;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.ut.bataapp.R;
import com.ut.bataapp.objects.EtappeRoute;
import com.ut.bataapp.parser.CSV;

public class EtappeRouteTekstActivity extends SherlockActivity{

	private int mId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mId = (savedInstanceState==null?getIntent().getIntExtra("id",1):savedInstanceState.getInt("id"));
		try {
			EtappeRoute route = new CSV().parse(this.getResources().getAssets().open("etappe"+mId+".csv"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setContentView(R.layout.etappe_route_tekst);
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("id",mId);
	}
	
	
	
}
