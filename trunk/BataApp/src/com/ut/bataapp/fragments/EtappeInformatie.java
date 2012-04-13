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
    	
    	TextView etappenummer = (TextView) view.findViewById(R.id.text_etappenummer);
    	etappenummer.setText(Integer.toString(etappe.getId()));
    	
    	TextView etappevan = (TextView) view.findViewById(R.id.text_etappevan);
    	etappevan.setText(etappe.getVan());
    	
    	TextView etappenaar = (TextView) view.findViewById(R.id.text_etappenaar);
    	etappenaar.setText(etappe.getNaar());
    	
    	TextView etappeafstand = (TextView) view.findViewById(R.id.text_etappeafstand);
    	etappeafstand.setText(Integer.toString(etappe.getAfstand()));
    	
    	//ImageView hoogte = (ImageView) view.findViewById(R.id.image_hoogteverschil);
    	
    	TextView etappegeslacht = (TextView) view.findViewById(R.id.text_etappegeslacht);
    	String geslacht = (etappe.getGeslacht() == 'H') ? "Man" : "Vrouw"; 	
    	etappegeslacht.setText(geslacht);
    	
    	TextView etappeomschrijving = (TextView) view.findViewById(R.id.text_etappeomschrijving);
    	etappeomschrijving.setText(etappe.getOmschrijving());
    	
    	// Tijden:
    	TextView opentijd = (TextView) view.findViewById(R.id.text_etappe_opentijd);
    	//opentijd.setText(etappe);
    	
    	TextView sluittijd = (TextView) view.findViewById(R.id.text_etappe_sluittijd);
    	sluittijd.setText(etappe.getNaar());
    	
    	TextView limiettijd = (TextView) view.findViewById(R.id.text_etappe_limiettijd);
    	limiettijd.setText(etappe.getNaar());
    	
    	TextView unilimiettijd = (TextView) view.findViewById(R.id.text_etappe_unilimiettijd);
    	unilimiettijd.setText(etappe.getNaar());
    	
    	
    	
    	TextView recordtijd_team = (TextView) view.findViewById(R.id.recordtijd_team);
    	recordtijd_team.setText(etappe.getRecordTeam());
    	
    	TextView recordtijd_jaar = (TextView) view.findViewById(R.id.recordtijd_jaar);
    	recordtijd_jaar.setText(etappe.getRecordJaar());
    	
    	TextView recordtijd_tijd = (TextView) view.findViewById(R.id.recordtijd_tijd);
    	recordtijd_tijd.setText(etappe.getRecordTijd());
    	
    	TextView recordtijd_snelheid = (TextView) view.findViewById(R.id.recordtijd_snelheid);
    	recordtijd_snelheid.setText(etappe.getRecordSnelheid());
    	
    	return view;
    	
    }
    
}
