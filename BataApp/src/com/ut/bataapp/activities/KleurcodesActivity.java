package com.ut.bataapp.activities;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;
import com.ut.bataapp.objects.Bericht;

public class KleurcodesActivity extends SherlockActivity  {
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   String code = getIntent().getStringExtra("index");
	   String code_beschrijving = getIntent().getStringExtra("beschrijving");
	 	   
	   String titel = getString(getResources().getIdentifier("kleurcode_"+code+"_titel", "string", getPackageName()));
	   String beschrijving = String.format(getString(getResources().getIdentifier("kleurcode_"+code+"_beschrijving", "string", getPackageName())), code_beschrijving);
	   String tekst = String.format(getString(getResources().getIdentifier("kleurcode_"+code, "string", getPackageName())), beschrijving);
	   
	   setContentView(R.layout.kleurcode);
	   TextView view_titel = (TextView) findViewById(R.id.kleurcode_titel);
	   view_titel.setText(titel);
	   TextView view_omschrijving = (TextView) findViewById(R.id.kleurcode_omschrijving);
	   view_omschrijving.setText(Html.fromHtml(beschrijving));
	   TextView view_tekst = (TextView) findViewById(R.id.kleurcode_tekst);
	   view_tekst.setText(Html.fromHtml(tekst));
	   setTitle(titel);
	   getSupportActionBar().setHomeButtonEnabled(true);
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