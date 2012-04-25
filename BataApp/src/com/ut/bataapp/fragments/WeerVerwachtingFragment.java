package com.ut.bataapp.fragments;

import com.ut.bataapp.R;
import com.ut.bataapp.activities.WeerActivity;
import com.ut.bataapp.weer.*;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Fragment voor weergeven verwachting Batadag (per plaats en algemeen).
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class WeerVerwachtingFragment extends SherlockFragment {	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Resources res = getResources();
    	
    	WeerProvider weerProvider = ((WeerActivity) getActivity()).getWeerProvider();
    	boolean hasVerwachting = (weerProvider.getVerwachting(WeerProvider.ENSCHEDE) != null && weerProvider.getVerwachting(WeerProvider.RUURLO) != null &&
    			                  weerProvider.getVerwachting(WeerProvider.NIJMEGEN) != null);
    	View result = inflater.inflate((hasVerwachting ? R.layout.weer_verwachting : R.layout.weer_verwachting_na), container, false);
		
    	((TextView) result.findViewById(R.id.weer_algemene_verwachting)).setText(weerProvider.getAlgemeneVerwachting());
    	if (hasVerwachting) {
    		WeerInfoVerwachting nijmegen = weerProvider.getVerwachting(WeerProvider.NIJMEGEN),
		                        ruurlo = weerProvider.getVerwachting(WeerProvider.RUURLO),
		                        enschede = weerProvider.getVerwachting(WeerProvider.ENSCHEDE);
    		((TextView) result.findViewById(R.id.weer_nijmegen_verwacht_temp)).setText(String.format(res.getString(R.string.weer_format_temp), nijmegen.getMinTemp()));
			((TextView) result.findViewById(R.id.weer_ruurlo_verwacht_temp)).setText(String.format(res.getString(R.string.weer_format_temp), ruurlo.getMaxTemp()));
			((TextView) result.findViewById(R.id.weer_enschede_verwacht_temp)).setText(String.format(res.getString(R.string.weer_format_temp), enschede.getMaxTemp()));
			((ImageView) result.findViewById(R.id.weer_nijmegen_verwacht_icoon)).setImageBitmap(nijmegen.getDesc());
			((ImageView) result.findViewById(R.id.weer_ruurlo_verwacht_icoon)).setImageBitmap(ruurlo.getDesc());
			((ImageView) result.findViewById(R.id.weer_enschede_verwacht_icoon)).setImageBitmap(enschede.getDesc());
    	}
    	
		return result;
	}
}