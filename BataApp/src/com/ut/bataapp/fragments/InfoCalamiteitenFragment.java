package com.ut.bataapp.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        
        return view;
    }

}