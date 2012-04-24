package com.ut.bataapp.fragments;

import com.ut.bataapp.R;
import com.ut.bataapp.activities.WeerActivity;
import com.ut.bataapp.weer.*;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Fragment voor weergeven huidige weersituatie, verwachte maximumtemperatuur en (indien beschikbaar) advies op basis daarvan.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class WeerAdviesFragment extends SherlockFragment {	    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	Resources res = getResources();
    	
    	WeerProvider weerProvider = ((WeerActivity) getActivity()).getWeerProvider();
    	boolean hasVerwachting = (weerProvider.getVerwachting(WeerProvider.ENSCHEDE) != null);
    	
    	View result = inflater.inflate((hasVerwachting ? R.layout.weer_advies : R.layout.weer_advies_na), container, false);
    	
    	WeerInfoHuidig nijmegen = weerProvider.getHuidig(WeerProvider.NIJMEGEN),
			           ruurlo = weerProvider.getHuidig(WeerProvider.RUURLO),
			           enschede = weerProvider.getHuidig(WeerProvider.ENSCHEDE);
    	
		((TextView) result.findViewById(R.id.weer_nijmegen_huidig_temp)).setText(String.format(res.getString(R.string.weer_format_temp), nijmegen.getTemp()));
		((TextView) result.findViewById(R.id.weer_ruurlo_huidig_temp)).setText(String.format(res.getString(R.string.weer_format_temp), ruurlo.getTemp()));
		((TextView) result.findViewById(R.id.weer_enschede_huidig_temp)).setText(String.format(res.getString(R.string.weer_format_temp), enschede.getTemp()));
		((ImageView) result.findViewById(R.id.weer_nijmegen_huidig_icoon)).setImageBitmap(nijmegen.getDesc());
		((ImageView) result.findViewById(R.id.weer_ruurlo_huidig_icoon)).setImageBitmap(ruurlo.getDesc());
		((ImageView) result.findViewById(R.id.weer_enschede_huidig_icoon)).setImageBitmap(enschede.getDesc());
		
		if (hasVerwachting) {
			byte max = weerProvider.getMax();
			TextView verwachteMaxTemp = (TextView) result.findViewById(R.id.weer_verwachte_max_temp);
			verwachteMaxTemp.setText(String.format(res.getString(R.string.weer_format_temp), max));

			int boundary1 = res.getInteger(R.integer.weer_boundary1),
			    boundary2 = res.getInteger(R.integer.weer_boundary2),
			    boundary3 = res.getInteger(R.integer.weer_boundary3);
			TextView advies = (TextView) result.findViewById(R.id.weer_advies);
			if (max < boundary1)
				advies.setText(R.string.weer_advies1);
			else if (max < boundary2)
				advies.setText(R.string.weer_advies2);
			else if (max < boundary3)
				advies.setText(R.string.weer_advies3);
			else {
				advies.setText(R.string.weer_advies4);
				advies.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD), Typeface.BOLD);
				verwachteMaxTemp.setTextColor(res.getColor(R.color.red));
			}
		}
	    	
    	return result;
    }
}