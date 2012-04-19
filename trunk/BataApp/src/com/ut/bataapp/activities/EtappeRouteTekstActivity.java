package com.ut.bataapp.activities;

import java.io.IOException;
import java.util.ArrayList;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
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
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		mId = (savedInstanceState==null?getIntent().getIntExtra("id",1):savedInstanceState.getInt("id"));
		mType = (savedInstanceState==null?getIntent().getStringExtra("type"):savedInstanceState.getString("type"));
		try {
			route = new CSV().parse(this.getResources().getAssets().open("etappe"+mId+".txt"));
			setContentView(R.layout.etappe_route_tekst);
			this.setTitle(mType+"route "+mId);
			Log.d("Routes",route.getVoorTabelTekst());
			TextView voor = (TextView) findViewById(R.id.voor_tabel);
			voor.setText(route.getVoorTabelTekst());
			TableLayout table = (TableLayout) findViewById(R.id.route_tabel);
			for(int i=0;i<route.getTabel().size();i++){
				TextView col1 = new TextView(this);
				col1.setText(route.getTabel().get(i)[0]);
				col1.setPadding(0, 0, 5, 0);
				TextView col2 = new TextView(this);
				col2.setText(route.getTabel().get(i)[1]);
				TextView col3 = new TextView(this);
				col3.setText(route.getTabel().get(i)[2]);
				col3.setGravity(Gravity.RIGHT);
				col3.setPadding(3,0,5,0);
				TableRow row = new TableRow(this);
				row.addView(col1);row.addView(col2);row.addView(col3);
				table.addView(row);
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
