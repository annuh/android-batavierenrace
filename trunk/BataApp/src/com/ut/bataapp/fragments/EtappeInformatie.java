package com.ut.bataapp.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.objects.Etappe;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class EtappeInformatie extends SherlockFragment {
    
	private Etappe etappe;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	etappe = ((EtappeActivity) getActivity()).getEtappe();
    	View view = inflater.inflate(R.layout.etappe_info, container, false);
    	
    	TextView routeafstand = (TextView) view.findViewById(R.id.text_routeafstand);
    	routeafstand.setText(Integer.toString(etappe.getAfstand()));
    	
    	ImageView hoogte = (ImageView) view.findViewById(R.id.image_hoogteverschil);
    	
    	TextView routegeslacht = (TextView) view.findViewById(R.id.text_routegeslacht);
    	String geslacht = (etappe.getGeslacht() == 'M') ? "Man" : "Vrouw"; 	
    	routegeslacht.setText(geslacht);
    	
    	TextView recordtijd_team = (TextView) view.findViewById(R.id.recordtijd_team);
    	recordtijd_team.setText(etappe.getRecordTeam());
    	
    	TextView recordtijd_tijd = (TextView) view.findViewById(R.id.recordtijd_tijd);
    	recordtijd_tijd.setText(etappe.getRecordTijd());
    	
    	return view;
    	
    }
    
}
