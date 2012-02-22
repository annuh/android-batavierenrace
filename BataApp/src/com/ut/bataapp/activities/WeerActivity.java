package com.ut.bataapp.activities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.R;


public class WeerActivity extends FragmentActivity {
	
	
	private final int MENU_SETTINGS = Menu.FIRST;
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.weer_styles);
	  
	  /* HttpClient hc = new DefaultHttpClient();
       HttpPost post = new HttpPost("http://www.meteovista.be/Go/ExternalWidgetsNew/RainWidgetContent?gid=135&sizeType=1&defaultSettings=False");

       try{
    	   HttpResponse rp = hc.execute(post);
		   if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		       String str = EntityUtils.toString(rp.getEntity());
		       Log.e("STRING:",str);
		   }
       } catch (Exception ex) {
    	   
       }
       
       //http://mijn.buienradar.nl/lokalebuienradar.aspx?voor=1&lat=51.9646995&x=1&y=1&lng=6.2937736&overname=2&zoom=8&naam=doetinchem&size=2&map=1
	  //WebView engine = (WebView) findViewById(R.id.weer_buienradar);  	  
	  //engine.loadUrl("http://www.meteovista.be/Go/ExternalWidgetsNew/RainWidgetContent?gid=135&sizeType=1&defaultSettings=False");*/

   }
   
   

}
