package com.ut.bataapp.fragments;

import java.util.ArrayList;
import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.objects.Sponsor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SponsorInformatieFragment extends SherlockFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ArrayList<Sponsor> sponsors = Sponsor.getSponsors();
		
		Bundle b = this.getArguments();
		int page = b.getInt("page");		
		Sponsor sponsor = sponsors.get(page);
		
		getSherlockActivity().getSupportActionBar().setTitle("Sponsor");     
		View view = inflater.inflate(R.layout.sponsor_item, container, false);
		
		TextView sponsor_titel = (TextView)  view.findViewById(R.id.sponsor_titel);
		sponsor_titel.setText(sponsor.getNaam());

		TextView sponsor_naam = (TextView)  view.findViewById(R.id.sponsor_naam);
		sponsor_naam.setText(sponsor.getNaam());
		
		TextView sponsor_branche = (TextView)  view.findViewById(R.id.sponsor_branche);
		sponsor_branche.setText(sponsor.getBrache());
		
		TextView sponsor_regio = (TextView)  view.findViewById(R.id.sponsor_regio);
		sponsor_regio.setText(sponsor.getRegio());
		
		TextView sponsor_link = (TextView)  view.findViewById(R.id.sponsor_link);
		sponsor_link.setText(sponsor.getLink());

		TextView sponsor_omschrijving = (TextView) view.findViewById(R.id.sponsor_omschrijving);
		sponsor_omschrijving.setText(sponsor.getOmschrijving());
		
		ImageView sponsor_plaatje = (ImageView) view.findViewById(R.id.sponsor_plaatje_3);
		String link = getActivity().getPackageName() + ":drawable/" + sponsor.getAfbeeldingLink();		
		int id = this.getResources().getIdentifier(link, null, null);
		sponsor_plaatje.setImageResource(id);
		
		return view;
	}

}
