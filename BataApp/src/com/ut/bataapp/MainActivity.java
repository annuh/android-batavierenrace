package com.ut.bataapp;

import java.util.Calendar;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import com.ut.bataapp.activities.*;
import com.ut.bataapp.objects.MenuItemInfo;


public class MainActivity extends SherlockFragmentActivity {
	
	private final int MENU_SETTINGS = Menu.FIRST;
	private final int MENU_MESSAGE = Menu.FIRST+1;
	private final MenuItemInfo[] mi = new MenuItemInfo[8];
	private static final Class[] mic = {null, KlassementenActivity.class, TeamsActivity.class, EtappesActivity.class, InformatieActivity.class, null, null, WeerActivity.class, null};
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);   
	    
	    Log.e("BugTrace", "Hier komt ie");
	    Display display = getWindowManager().getDefaultDisplay(); 
	    int width = display.getWidth();
	    int height = display.getHeight() - 100;
	    float dim = (float)width/(float)height;
	    
	    String dimension;
	    String bepalend;
	    
	    if(((float)0) <= dim && dim <= ((float)0.5)){
	    	dimension = "2x4";
	    	if(dim<(float)0.4){
	    		bepalend = "w";
	    	} else{
	    		bepalend = "h";
	    	}
	    } else if(((float)0.5) <= dim && dim <= ((float)1.3)){
	    	dimension  = "3x3";
	    	if(dim<=(float)0.75){
	    		bepalend = "w";
	    	} else{
	    		bepalend = "h";
	    	}
	    } else{
	    	dimension  = "4x2";
	    	if(dim<(float)1.6){
	    		bepalend = "w";
	    	} else{
	    		bepalend = "h";
	    	}
	    }
	    
	    Log.i("0000000", " " + width + " " + height + " " + bepalend +" " + dimension);
	    
	    int menu[] = new int[9];
	    
	    if(dimension.equals("3x3")){
	    	setContentView(R.layout.actionbar_styles);
	    	int[] localmenu = {R.id.menu_3x3_1, R.id.menu_3x3_2, R.id.menu_3x3_3, R.id.menu_3x3_4, R.id.menu_3x3_5, R.id.menu_3x3_6, R.id.menu_3x3_7, R.id.menu_3x3_8, R.id.menu_3x3_9};
	    	menu = localmenu;
	    } else if(dimension.equals("4x2")){
	    	setContentView(R.layout.actionbar_styles_42);
	    	int[] localmenu = {R.id.menu_4x2_1, R.id.menu_4x2_2, R.id.menu_4x2_3, R.id.menu_4x2_4, R.id.menu_4x2_5, R.id.menu_4x2_6, R.id.menu_4x2_7, R.id.menu_4x2_8, R.id.menu_4x2_8};
	    	menu = localmenu;
	    } else{
	    	setContentView(R.layout.actionbar_styles_24);
	    	int[] localmenu = {R.id.menu_2x4_1, R.id.menu_2x4_2, R.id.menu_2x4_3, R.id.menu_2x4_4, R.id.menu_2x4_5, R.id.menu_2x4_6, R.id.menu_2x4_7, R.id.menu_2x4_8, R.id.menu_2x4_8};
	    	menu = localmenu;
	    }
	    
	    mi[0] = new MenuItemInfo(menu[0], R.drawable.menu_favoteam, "Favo Team");
	    mi[1] = new MenuItemInfo(menu[1], R.drawable.menu_klassement, "Klassement");
	    mi[2] = new MenuItemInfo(menu[2], R.drawable.menu_klassement, "Teams");
	    mi[3] = new MenuItemInfo(menu[3], R.drawable.menu_etappe, "Etappes");
	    mi[4] = new MenuItemInfo(menu[4], R.drawable.menu_information, "Informatie");
	    mi[5] = new MenuItemInfo(menu[5], R.drawable.menu_jubileum, "Bata XL");
	    mi[6] = new MenuItemInfo(menu[6], R.drawable.menu_radio, "BataRadio");
	    mi[7] = new MenuItemInfo(menu[7], R.drawable.menu_weer, "Weer");
	    
	    
	    
	    if(true){		    
		    
	    	int imagedim;
	    	
	    	if(dimension.equals("2x4") && bepalend.equals("w")){
	    		imagedim = width/2;
	    	} else if(dimension.equals("2x4") && bepalend.equals("h")){
	    		imagedim = height/4;
	    	} else if(dimension.equals("3x3") && bepalend.equals("w")){
	    		imagedim = width/3;
	    	} else if(dimension.equals("3x3") && bepalend.equals("h")){
	    		imagedim = height/3;
	    	} else if(dimension.equals("4x2") && bepalend.equals("w")){
	    		imagedim = width/4;
	    	} else{
	    		imagedim = height/2;
	    	} 
	    	
	    	for(int i = 0; i<mi.length; i++){
	    		Log.e("BugTrace", "Hier komt ie" + mi.length + " voor " +i);
	    		Log.i("00000", mi[i].getLocationIdentifier() + i + "");
			    LinearLayout ll = (LinearLayout) findViewById(mi[i].getLocationIdentifier());
			    ImageView iv = new ImageView(this);
			    Log.e("BugTrace", "voor" + i);
			    iv.setId(1);
			    iv.setImageResource(mi[i].getImageIdentifier());
			    iv.setLayoutParams(new LayoutParams(imagedim-10,imagedim-10));
			    ll.addView(iv);
			    TextView tv = new TextView(this);
			    Log.e("BugTrace", "midden" + i);
			    tv.setText(mi[i].getTitle());
			    tv.setGravity(Gravity.CENTER);
			    ll.addView(tv);
			    ll.setGravity(Gravity.CENTER);
			    ll.setId(i);
			    
			    ll.setOnClickListener(new View.OnClickListener() {
			           @Override
			           public void onClick(View view) {
			               Intent j = new Intent();
			               Log.i("000000000", view.getId() + " ");
			               
			        	   j.setClass(getApplicationContext(), MainActivity.mic[view.getId()]);
			        	   startActivity(j);
			           }
			       });
	    	}
	    	
	    	Log.e("BugTrace", "laastte");
	    
	    }

	    // Aantal dagen
	    Calendar nu = Calendar.getInstance();
		Calendar batadag = Calendar.getInstance();
		batadag.set(2012, 4-1, 28);
		long dagenVerschil = (batadag.getTimeInMillis() - nu.getTimeInMillis())/ (24 * 60 * 60 * 1000);
		TextView viewDagen = (TextView) findViewById(R.id.dashboard_time);
		viewDagen.setText("Het duurt nog " +dagenVerschil + " dagen!");
	}
   
   @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,MENU_SETTINGS,Menu.NONE,"Instellingen")
		    .setIcon(R.drawable.ic_action_settings)
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		menu.add(0,MENU_MESSAGE,Menu.NONE,"Berichten")
		    .setIcon(R.drawable.ic_action_mail)
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		return super.onCreateOptionsMenu(menu);
	}
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
   	switch (item.getItemId()) {
   		
   		case MENU_SETTINGS:
	   		// Launch Preference activity
	   		Intent i = new Intent(this, PreferencesActivity.class);
	   		startActivity(i);
	   		break;
	   		
   		case MENU_MESSAGE:
	   		// Launch Messages activity
   			Intent j = new Intent(getApplicationContext(), MessagesActivity.class);
	   		startActivity(j);
	   		break;

   	}
   	return true;
   }
   
   public static final class OverridePendingTransition {
       public static void invoke(Activity activity) {
           activity.overridePendingTransition(0, 0);
       }
   }
}