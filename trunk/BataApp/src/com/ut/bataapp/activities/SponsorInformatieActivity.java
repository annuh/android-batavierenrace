package com.ut.bataapp.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;
import com.ut.bataapp.fragments.SponsorInformatieFragment;
import com.ut.bataapp.objects.Sponsor;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;

public class SponsorInformatieActivity extends SherlockFragmentActivity {
	// -- INNER CLASSES --

	class SponsorInformatieAdapter extends FragmentPagerAdapter implements TitleProvider {
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		ArrayList<String> titels = new ArrayList<String>();

		public SponsorInformatieAdapter(FragmentManager fm) {
			super(fm);			
			ArrayList<Sponsor> sponsors = Sponsor.getSponsors();
			for(int i = 0; i< sponsors.size(); i++){
				SponsorInformatieFragment spFrag = new SponsorInformatieFragment();
				Bundle b = new Bundle();
				b.putInt(INSTANTE_STATE_TAB, i);
				spFrag.setArguments(b);
				fragments.add(spFrag);
				titels.add(sponsors.get(i).getNaam());
			}
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

	// -- CONSTANTEN --
	/** Naam van de variabele waar het huidige tabblad is opgeslagen */
	public static final String INSTANTE_STATE_TAB = "tabid";

	// -- INSTANTIEVARIABELEN --

	private ViewPager mPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {    	
		super.onCreate(savedInstanceState);
		getSupportActionBar().setHomeButtonEnabled(true);
		setContentView(R.layout.simple_tabs);

		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(new SponsorInformatieAdapter(getSupportFragmentManager()));

		PageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
		indicator.setViewPager(mPager);

		int pageid = (savedInstanceState == null ? getIntent().getIntExtra(INSTANTE_STATE_TAB, 0) : savedInstanceState.getInt(INSTANTE_STATE_TAB));

		mPager.setCurrentItem(pageid);
		indicator.setCurrentItem(pageid);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// super niet aangeroepen: ViewPager/Adapter wordt in onCreate() weer opgebouwd
		outState.putInt(INSTANTE_STATE_TAB, (mPager == null ? 0 : mPager.getCurrentItem()));
	}

	// -- CALLBACKMETHODEN --

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Utils.goHome(this);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
