package com.ut.bataapp.activities;

import java.util.ArrayList;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.fragments.LayoutFragment;
import com.ut.bataapp.fragments.SponsorInformatieFragment;
import com.ut.bataapp.objects.Sponsor;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;
import com.actionbarsherlock.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class SponsorInformatieActivity extends SherlockFragmentActivity {
	
	static Sponsor sponsor1 = new Sponsor("1", "Susa", "Uitzendbureau", "Heel Nederland", "www.susa.nl", "Studenten uitzendbureau SUSA heeft volop bijbanen, studentenwerk en vakantiewerk. Schrijf je nu in voor een bijbaan die past bij jou!", "sponsor_susa");
	static Sponsor sponsor2 = new Sponsor("2", "Ministerie van Defensie", "Beveiliging", "Heel Nederland", "www.defensie.nl", "Organisatie, nieuws, banen en overzichten van taken, lopende operaties en krijgsmachtonderdelen.", "sponsor_mindef");
	static Sponsor sponsor3 = new Sponsor("3", "Autodrop", "Uitzendbureau", "Heel Nederland", "www.susa.nl", "Nieuw van Autodrop: centre filled! Vloeibare drop en snoep in verschillende smaken… Heb je ze al geproefd? Absurd lekker!", "sponsor_autodrop");
	static Sponsor sponsor4 = new Sponsor("4", "Student Union", "Universiteit Twente", "Enschede", "www.studentunion.utwente.nl", "De Student Union is er om jouw studententijd optimaal te laten verlopen. Door middel van allerlei samenwerkingsverbanden ondersteunen we verenigingen en proberen we zoveel mogelijk kansen voor studenten te creëren.", "sponsor_su");
	static Sponsor sponsor5 = new Sponsor("5", "Eurosport Borne", "Sportwinkel", "Borne (ov)", "www.eurosportborne.nl", "Wij Eurosport leveren clubkleding en materialen aan onder meer verenigingen en evenementen. Shirts, trainingspakken, ballen, doelen, warmloopshirts, trainingsmaterialen, niets is te gek.", "sponsor_eurosport");
	static Sponsor sponsor6 = new Sponsor("6", "Enschede Promotie ", "marketing", "Enschede", "www.uitinenschede.nl", "Enschede Promotie ziet als haar kerntaken het bevorderen van het (dag)toerisme, het profileren van de culturele activiteiten van Enschede en het vermarkten van Enschede als een aantrekkelijke woon- en werkstad. Enschede Promotie ontwikkelt een strategisch (marketing)plan om de stad intern en extern sterker te positioneren.", "sponsor_uitinenschede");
	static Sponsor sponsor7 = new Sponsor("7", "E-matching", "Datingsite", "Heel Nederland", "www.e-matching.nl", "Online dating voor hoger opgeleiden. De datingsite waar hbo'ers en academici een duurzame relatie vinden, bekend van radio en TV (durft ú het aan...).", "sponsor_ematch");
	static Sponsor sponsor8 = new Sponsor("8", "Pentair x-flow", "High tech", "Enschede", "www.x-flow.com", "Pentair X-Flow membranes are used in the field of ultrafiltration in capillary form, and microfiltration and ultrafiltration in tubular form, which makes selective filtration possible. ", "sponsor_pentair");
	static Sponsor sponsor9 = new Sponsor("9", "Grolsch", "Productie bier", "Enschede", "www.grolsch.nl", "Grolsch brouwt uitsluitend bier en richt zich op het premium segment van de markt. Het sterke merk Grolsch staat daarbij centraal. De kracht van het merk vindt zijn oorsprong in 'Vakmanschap is Meesterschap' en in de unieke beugelfles.", "sponsor_grolsch");
	static Sponsor sponsor10 = new Sponsor("10", "Autorent", "Verhuur autos", "Twente", "www.bleekergroep.nl/autorent_twente", "AutoRent Twente is dé auto verhuurder in Oost-Nederland, met vestigingen in Almelo, Enschede, Hengelo en Oldenzaal. AutoRent Twente is onderdeel van AutoRent Europa Service, dat met 54 vestigingen de grootste autoverhuurder van Nederland is.", "sponsor_autorent");
	static Sponsor sponsor11 = new Sponsor("11", "Studentenfiets", "Fietswinkel", "Enschede", "www.studentfiets-enschede.nl", "Studentfiets Enschede is een jong en bloeiend bedrijf. We richten ons op de verkoop van fietsen, speciaal voor studenten. Dat wil zeggen: goedkope, betrouwbare en vooral praktische fietsen! Naast de verkoop van fietsen is het ook mogelijk om alleen onderdelen bij ons te kopen.", "sponsor_studentenfiets");

	ViewPager mPager;
	PageIndicator mIndicator;
	FragmentPagerAdapter mAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {    	
    	super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.simple_tabs);
		
        mAdapter = new SponsorInformatieAdapter(getSupportFragmentManager());
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		
		mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
		
		
		String page = "";
		page = getIntent().getStringExtra("page");
		int pageid = 0;
		pageid = Integer.parseInt(page) -1;
		
		mPager.setCurrentItem(pageid);
		mIndicator.setCurrentItem(pageid);
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				/*
				Intent intent = new Intent(this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
				//Get rid of the slide-in animation, if possible
	            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
	                OverridePendingTransition.invoke(this);
	            }*/
		}
		
		return super.onOptionsItemSelected(item);
	}
    
    class SponsorInformatieAdapter extends FragmentPagerAdapter implements TitleProvider {
		
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		ArrayList<String> titels = new ArrayList<String>();
		
		public SponsorInformatieAdapter(FragmentManager fm) {
			super(fm);
			SponsorInformatieFragment spFrag1 = new SponsorInformatieFragment(SponsorInformatieActivity.sponsor1);
			fragments.add(spFrag1);
			titels.add(SponsorInformatieActivity.sponsor1.getNaam());
			SponsorInformatieFragment spFrag2 = new SponsorInformatieFragment(SponsorInformatieActivity.sponsor2);
			fragments.add(spFrag2);
			titels.add(SponsorInformatieActivity.sponsor2.getNaam());
			SponsorInformatieFragment spFrag3 = new SponsorInformatieFragment(SponsorInformatieActivity.sponsor3);
			fragments.add(spFrag3);
			titels.add(SponsorInformatieActivity.sponsor3.getNaam());
			SponsorInformatieFragment spFrag4 = new SponsorInformatieFragment(SponsorInformatieActivity.sponsor4);
			fragments.add(spFrag4);
			titels.add(SponsorInformatieActivity.sponsor4.getNaam());
			SponsorInformatieFragment spFrag5 = new SponsorInformatieFragment(SponsorInformatieActivity.sponsor5);
			fragments.add(spFrag5);
			titels.add(SponsorInformatieActivity.sponsor5.getNaam());
			SponsorInformatieFragment spFrag6 = new SponsorInformatieFragment(SponsorInformatieActivity.sponsor6);
			fragments.add(spFrag6);
			titels.add(SponsorInformatieActivity.sponsor6.getNaam());
			SponsorInformatieFragment spFrag7 = new SponsorInformatieFragment(SponsorInformatieActivity.sponsor7);
			fragments.add(spFrag7);
			titels.add(SponsorInformatieActivity.sponsor7.getNaam());
			SponsorInformatieFragment spFrag8 = new SponsorInformatieFragment(SponsorInformatieActivity.sponsor8);
			fragments.add(spFrag8);
			titels.add(SponsorInformatieActivity.sponsor8.getNaam());
			SponsorInformatieFragment spFrag9 = new SponsorInformatieFragment(SponsorInformatieActivity.sponsor9);
			fragments.add(spFrag9);
			titels.add(SponsorInformatieActivity.sponsor9.getNaam());
			SponsorInformatieFragment spFrag10 = new SponsorInformatieFragment(SponsorInformatieActivity.sponsor10);
			fragments.add(spFrag10);
			titels.add(SponsorInformatieActivity.sponsor10.getNaam());
			SponsorInformatieFragment spFrag11 = new SponsorInformatieFragment(SponsorInformatieActivity.sponsor11);
			fragments.add(spFrag11);
			titels.add(SponsorInformatieActivity.sponsor11.getNaam());
		}
		
		
		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public String getTitle(int position) {
			return titels.get(position);
		}
	}
}
