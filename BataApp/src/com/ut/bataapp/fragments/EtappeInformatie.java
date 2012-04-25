package com.ut.bataapp.fragments;

import java.io.IOException;
import java.math.BigDecimal;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.objects.Etappe;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class EtappeInformatie extends SherlockFragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Etappe etappe = ((EtappeActivity) getActivity()).getEtappe();

		View view = inflater.inflate(R.layout.etappe_info, container, false);

		String formatkm = getResources().getString(R.string.km);
		String formatkmu = getResources().getString(R.string.kmu);
		String formatuur = getResources().getString(R.string.uur);
		
		Log.i("debugger", "" + etappe.getId());
		
		int id = etappe.getId();		
		String imageLink = "hoogteverschil/" + id + ".jpg";
		
		
		/**
		 * Informatie
		 */
		TextView etappenummer = (TextView) view.findViewById(R.id.text_etappenummer);
		etappenummer.setText(Integer.toString(etappe.getId()));

		TextView etappevan = (TextView) view.findViewById(R.id.text_etappevan);
		etappevan.setText(etappe.getVan());

		TextView etappenaar = (TextView) view.findViewById(R.id.text_etappenaar);
		etappenaar.setText(etappe.getNaar());

		ImageView hoogteVerschil = (ImageView) view.findViewById(R.id.image_hoogteverschil);
		try {
			hoogteVerschil.setImageDrawable(Drawable.createFromStream(this.getActivity().getAssets().open(imageLink), null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		hoogteVerschil.setScaleType(ScaleType.FIT_XY);
		hoogteVerschil.setMaxHeight(350);
		hoogteVerschil.setMaxWidth(500);
		
		int maxHeight = 0;
		int minHeight = 0;
		
		if(id>0 && id<=etappe.maxHeight.length){
			maxHeight = etappe.maxHeight[id];
		}
		if(id>0 && id<=etappe.minHeight.length){
			minHeight = etappe.minHeight[id];
		}
		float afstandFloat = (float)etappe.getAfstand()/(float)1000;
		
		String afstandString = afstandFloat + "";
		
		TextView minHeightView = (TextView) view.findViewById(R.id.hoogteVerschil_minHeight);
		minHeightView.setText(minHeight + "m");
		TextView maxHeightView = (TextView) view.findViewById(R.id.hoogteVerschil_maxHeight);
		maxHeightView.setText(maxHeight + "m");
		TextView afstandView = (TextView) view.findViewById(R.id.hoogteverschil_afstand);
		afstandView.setText(afstandString + "km");		
		
		TextView etappeafstand = (TextView) view.findViewById(R.id.text_etappeafstand);
		BigDecimal afst = new BigDecimal((etappe.getAfstand()));
		afst = afst.divide(new BigDecimal(1000), 1, BigDecimal.ROUND_FLOOR);
		etappeafstand.setText(String.format(formatkm, afst.toString()));

		TextView etappegeslacht = (TextView) view.findViewById(R.id.text_etappegeslacht);
		String geslacht = (etappe.getGeslacht() == 'H') ? "Man" : "Vrouw"; 	
		etappegeslacht.setText(geslacht);

		TextView etappeomschrijving = (TextView) view.findViewById(R.id.text_etappeomschrijving);
		etappeomschrijving.setText(etappe.getOmschrijving());

		// Tijden:
		TextView opentijd = (TextView) view.findViewById(R.id.text_etappe_opentijd);
		opentijd.setText(etappe.getOpenTijd());

		TextView sluittijd = (TextView) view.findViewById(R.id.text_etappe_sluittijd);
		sluittijd.setText(etappe.getUitersteStartTijd());

		TextView limiettijd = (TextView) view.findViewById(R.id.text_etappe_limiettijd);
		limiettijd.setText(etappe.getLimietTijd());

		TextView unilimiettijd = (TextView) view.findViewById(R.id.text_etappe_unilimiettijd);
		unilimiettijd.setText(etappe.getUniversteitsLimietTijd());

		/**
		 * Recordtijd team
		 */
		TextView recordtijd_team = (TextView) view.findViewById(R.id.recordtijd_team);
		recordtijd_team.setText(etappe.getRecordTeam());

		TextView recordtijd_jaar = (TextView) view.findViewById(R.id.recordtijd_jaar);
		recordtijd_jaar.setText(etappe.getRecordJaar());

		TextView recordtijd_tijd = (TextView) view.findViewById(R.id.recordtijd_tijd);
		recordtijd_tijd.setText(String.format(formatuur, etappe.getRecordTijd()));

		TextView recordtijd_snelheid = (TextView) view.findViewById(R.id.recordtijd_snelheid);
		recordtijd_snelheid.setText(String.format(formatkmu, etappe.getRecordSnelheid()));

		return view;

	}

}
