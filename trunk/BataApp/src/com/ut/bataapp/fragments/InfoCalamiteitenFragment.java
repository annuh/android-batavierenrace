package com.ut.bataapp.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class InfoCalamiteitenFragment extends SherlockFragment {
        
    public InfoCalamiteitenFragment() {
        setRetainInstance(true);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.info_contact_fragment, container, false);
    	
    	LinearLayout call_112 = (LinearLayout) view.findViewById(R.id.bel_112);
    	call_112.setOnClickListener(
    	            new View.OnClickListener() {    
    	                @Override
    	                public void onClick(View v) {
    	                	Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 112"));
    	                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	                    startActivity(intent);
    	                }
    	            }
    	        );
        
    	LinearLayout call_org = (LinearLayout) view.findViewById(R.id.bel_organisatie);
    	call_org.setOnClickListener(
    	            new View.OnClickListener() {    
    	                @Override
    	                public void onClick(View v) {
    	                	Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 0031534895331"));
    	                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	                    startActivity(intent);
    	                }
    	            }
    	        );
    	LinearLayout call_org2 = (LinearLayout) view.findViewById(R.id.bel_organisatie2);
    	call_org2.setOnClickListener(
    	            new View.OnClickListener() {    
    	                @Override
    	                public void onClick(View v) {
    	                	Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 0031534895331"));
    	                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	                    startActivity(intent);
    	                }
    	            }
    	        );
    	
    	
        return view;
    }

}