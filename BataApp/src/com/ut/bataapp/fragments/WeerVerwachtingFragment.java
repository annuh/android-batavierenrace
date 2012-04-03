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

public class WeerVerwachtingFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.weer_verwachting, container, false);
	}
	
	@Override
    public void onStart() {
    	super.onStart();
    	if (!((WeerActivity) getActivity()).refreshOngoing())
    		updateView();
    }
	
	public void updateView() {
    	WeerProvider weerProvider = ((WeerActivity) getActivity()).getWeerProvider();
		WeerInfo nijmegen = weerProvider.getVerwachting(WeerProvider.NIJMEGEN),
				 ruurlo = weerProvider.getVerwachting(WeerProvider.RUURLO),
				 enschede = weerProvider.getVerwachting(WeerProvider.ENSCHEDE);
		((TextView) getActivity().findViewById(R.id.nijmegen_verwacht_temp)).setText(String.format(getResources().getString(R.string.format_temp), nijmegen.getTemp()));
		((TextView) getActivity().findViewById(R.id.ruurlo_verwacht_temp)).setText(String.format(getResources().getString(R.string.format_temp), ruurlo.getTemp()));
		((TextView) getActivity().findViewById(R.id.enschede_verwacht_temp)).setText(String.format(getResources().getString(R.string.format_temp), enschede.getTemp()));
		((ImageView) getActivity().findViewById(R.id.nijmegen_verwacht_icoon)).setImageBitmap(nijmegen.getDesc());
		((ImageView) getActivity().findViewById(R.id.ruurlo_verwacht_icoon)).setImageBitmap(ruurlo.getDesc());
		((ImageView) getActivity().findViewById(R.id.enschede_verwacht_icoon)).setImageBitmap(enschede.getDesc());
		((TextView) getActivity().findViewById(R.id.algemene_verwachting)).setText(weerProvider.getAlgemeneVerwachting());
    }
}