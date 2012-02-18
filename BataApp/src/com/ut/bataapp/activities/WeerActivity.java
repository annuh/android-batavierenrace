package com.ut.bataapp.activities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.R;


public class WeerActivity extends FragmentActivity {
	
	private static int THEME = R.style.Theme_BataApp;
	private final int MENU_SETTINGS = Menu.FIRST;
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   setTheme(THEME);
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.weer_styles);
	   URL myFileUrl = null;
	   ImageView imageView =  (ImageView) findViewById(R.id.buienradar);
	   try {
	       myFileUrl= new URL("http://mijn.buienradar.nl/lokalebuienradar.aspx?voor=1&lat=51.9646995&x=1&y=1&lng=6.2937736&overname=2&zoom=8&naam=doetinchem&size=2&map=1");
	  } catch (MalformedURLException e) {
	       // TODO Auto-generated catch block
	       e.printStackTrace();
	  }
	  try {
		  Bitmap bmImg = BitmapFactory.decodeStream(myFileUrl.openConnection().getInputStream());
		  imageView.setImageBitmap(bmImg);
	  } catch (IOException e) {
	       // TODO Auto-generated catch block
	       e.printStackTrace();
	  }
	     
       //http://mijn.buienradar.nl/lokalebuienradar.aspx?voor=1&lat=51.9646995&x=1&y=1&lng=6.2937736&overname=2&zoom=8&naam=doetinchem&size=2&map=1

   }
   
   @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,MENU_SETTINGS,Menu.NONE,"Settings")
		    .setIcon(R.drawable.ic_action_edit)
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		
		return super.onCreateOptionsMenu(menu);
	}
   

}
