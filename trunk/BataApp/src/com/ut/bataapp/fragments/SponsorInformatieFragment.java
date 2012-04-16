package com.ut.bataapp.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.objects.Sponsor;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SponsorInformatieFragment extends SherlockFragment {

	private Sponsor sponsor;
	
	public SponsorInformatieFragment(Sponsor sponsor){
		this.sponsor = sponsor;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getSherlockActivity().getSupportActionBar().setTitle("Sponsor");     
		View view = inflater.inflate(R.layout.sponsor_item, container, false);
		
		ImageView sponsor_plaatje = (ImageView) view.findViewById(R.id.sponsor_plaatje_3);
		String link = getActivity().getPackageName() + ":drawable/" + sponsor.getAfbeeldingLink();		
		int id = this.getResources().getIdentifier(link, null, null);
		sponsor_plaatje.setImageResource(id);		
		
		TextView sponsor_titel = (TextView)  view.findViewById(R.id.sponsor_titel);
		sponsor_titel.setText(sponsor.getNaam());

		TextView sponsor_naam = (TextView)  view.findViewById(R.id.sponsor_naam);
		sponsor_naam.setText(Html.fromHtml("<b>Naam:</b> " + sponsor.getNaam()));
		
		TextView sponsor_branche = (TextView)  view.findViewById(R.id.sponsor_branche);
		sponsor_branche.setText(Html.fromHtml("<b>Branche:</b> " + sponsor.getBrache()));
		
		TextView sponsor_regio = (TextView)  view.findViewById(R.id.sponsor_regio);
		sponsor_regio.setText(Html.fromHtml("<b>Regio:</b> " + sponsor.getRegio()));
		
		TextView sponsor_link = (TextView)  view.findViewById(R.id.sponsor_link);
		sponsor_link.setText(Html.fromHtml("<b>Link:</b> " + sponsor.getLink()));

		TextView sponsor_omschrijving = (TextView) view.findViewById(R.id.sponsor_omschrijving);
		sponsor_omschrijving.setText(Html.fromHtml(sponsor.getOmschrijving()));
		
		return view;
	}

}
