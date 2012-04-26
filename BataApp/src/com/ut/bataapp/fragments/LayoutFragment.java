package com.ut.bataapp.fragments;

import android.os.Bundle;
import android.view.*;

import com.actionbarsherlock.app.SherlockFragment;

public class LayoutFragment extends SherlockFragment {	
	private int mLayout;
	
	public LayoutFragment(int layout) {
		mLayout = layout;
	}
			
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	View view = inflater.inflate(mLayout, container, false);
    	return view;
    }
}
