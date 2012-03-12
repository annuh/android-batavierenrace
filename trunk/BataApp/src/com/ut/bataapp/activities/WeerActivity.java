package com.ut.bataapp.activities;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;


import com.actionbarsherlock.R;


public class WeerActivity extends SherlockFragmentActivity {
	
	
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
