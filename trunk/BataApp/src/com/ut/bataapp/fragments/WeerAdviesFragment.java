package com.ut.bataapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.WeerActivity;
import com.ut.bataapp.weer.WeerInfo;
import com.ut.bataapp.weer.WeerProvider;

public class WeerAdviesFragment extends SherlockFragment {	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	return inflater.inflate(R.layout.weer_advies, container, false);
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	if (!((WeerActivity) getActivity()).refreshOngoing())
    		updateView();
    }
    	
    public void updateView() {
    	WeerProvider weerProvider = ((WeerActivity) getActivity()).getWeerProvider();
		WeerInfo nijmegen = weerProvider.getHuidig(WeerProvider.NIJMEGEN),
				 ruurlo = weerProvider.getHuidig(WeerProvider.RUURLO),
				 enschede = weerProvider.getHuidig(WeerProvider.ENSCHEDE);
		((TextView) getActivity().findViewById(R.id.nijmegen_huidig_temp)).setText(String.format(getResources().getString(R.string.format_temp), nijmegen.getTemp()));
		((TextView) getActivity().findViewById(R.id.ruurlo_huidig_temp)).setText(String.format(getResources().getString(R.string.format_temp), ruurlo.getTemp()));
		((TextView) getActivity().findViewById(R.id.enschede_huidig_temp)).setText(String.format(getResources().getString(R.string.format_temp), enschede.getTemp()));
		((ImageView) getActivity().findViewById(R.id.nijmegen_huidig_icoon)).setImageBitmap(nijmegen.getDesc());
		((ImageView) getActivity().findViewById(R.id.ruurlo_huidig_icoon)).setImageBitmap(ruurlo.getDesc());
		((ImageView) getActivity().findViewById(R.id.enschede_huidig_icoon)).setImageBitmap(enschede.getDesc());
		
		byte max = weerProvider.getMax();
		((TextView) getActivity().findViewById(R.id.verwachte_max_temp)).setText(String.format(getResources().getString(R.string.format_temp), max));
		
		int boundary1 = getResources().getInteger(R.integer.boundary1),
		    boundary2 = getResources().getInteger(R.integer.boundary2),
		    boundary3 = getResources().getInteger(R.integer.boundary3);
		TextView advies = (TextView) getActivity().findViewById(R.id.weer_advies);
		if (max < boundary1)
			advies.setText(R.string.advies1);
		else if (max < boundary2)
			advies.setText(R.string.advies2);
		else if (max < boundary3)
			advies.setText(R.string.advies3);
		else
			advies.setText(R.string.advies4);
    }
}