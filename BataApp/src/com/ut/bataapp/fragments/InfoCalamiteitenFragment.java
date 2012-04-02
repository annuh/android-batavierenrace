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
        
    	LinearLayout call = (LinearLayout) view.findViewById(R.id.bel_organisatie);
    	call.setOnClickListener(
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