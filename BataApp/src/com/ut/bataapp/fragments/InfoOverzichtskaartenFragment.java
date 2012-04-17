package com.ut.bataapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.*;

public class InfoOverzichtskaartenFragment extends SherlockFragment {
	
	public void openKaart(int kaart) {
		Intent intent = new Intent(getActivity().getApplicationContext(), AfbeeldingActivity.class);
		intent.putExtra("kaart", kaart);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   View view = inflater.inflate(R.layout.info_overzichtskaarten, container, false);
	   
	   Button herstart_barchem = (Button) view.findViewById(R.id.herstart_barchem);
	   herstart_barchem.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openKaart(0);
           }
       });
	   
	   Button herstart_ulft = (Button) view.findViewById(R.id.herstart_ulft);
	   herstart_ulft.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openKaart(1);
           }
       });
	   
	   Button campus_enschede = (Button) view.findViewById(R.id.campus_enschede);
	   campus_enschede.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openKaart(2);
           }
       });
	   
	   Button stad_enschede = (Button) view.findViewById(R.id.stad_enschede);
	   stad_enschede.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openKaart(3);
           }
       });
	   
	   Button stad_nijmegen = (Button) view.findViewById(R.id.stad_nijmegen);
	   stad_nijmegen.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        	   openKaart(4);
           }
       });
	   
	   return view;
   }

   public static final class OverridePendingTransition {
       public static void invoke(Activity activity) {
           activity.overridePendingTransition(0, 0);
       }
   }
}
