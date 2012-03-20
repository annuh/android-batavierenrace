package com.ut.bataapp.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TextFragment extends SherlockFragment {
	
	private int layout;
	
	public TextFragment(int layout) {
		this.layout = layout;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setHasOptionsMenu(true);
    	
    	View view = inflater.inflate(layout, container, false);
    	
    	return view;
    }

}
