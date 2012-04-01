package com.ut.bataapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.fragments.WeerAdviesFragment.OnRequestWeerProvider;
import com.ut.bataapp.weer.WeerException;

public class WeerVerwachtingFragment extends SherlockFragment {
	private OnRequestWeerProvider mRequestWeerProvider;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mRequestWeerProvider = (OnRequestWeerProvider) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnRequestWeerProvider");
        }
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.weer_verwachting, container, false);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		TextView verwachting = (TextView) getActivity().findViewById(R.id.weer_verwachting);
		try {
			verwachting.setText(mRequestWeerProvider.onRequestWeerProvider().getVerwachting());
		} catch (WeerException e) {
			verwachting.setText(R.string.error_verwachting);
		}
	}
}
