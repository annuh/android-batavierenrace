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
import com.ut.bataapp.fragments.InfoAlgemeenFragment;
import com.ut.bataapp.fragments.InfoCalamiteitenFragment;
import com.ut.bataapp.fragments.InfoColofonFragment;
import com.ut.bataapp.fragments.InfoOverzichtskaartenFragment;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;

/**
 * Activity voor het informatie gedeelte. De volgende fragments worden hier geladen:
 * 	- Algemeen
 *  - Calamiteiten
 *  - Overzichtskaart
 *  - Colofon  
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne van de Venis
 * @version 1.0
 */
public class InformatieActivity extends SherlockFragmentActivity {
	
	// -- INNER CLASSES --
	
	/* Adapterklasse voor fragments -> pager. In deze adapter worden de volgende tabbladen getoond:
	 * 	- Algemeen
	 *  - Calamiteiten
	 *  - Overzichtskaart
	 *  - Colofon  
     * Onderdeel van ontwerpproject BataApp.
     * @author Anne van de Venis
     * @version 1.0
     */
	class InformatieFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
		/** Hier worden de Fragments opgeslagen die worden getoond. */
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		/** Hier worden de titels van de Fragments opgeslagen. */
		ArrayList<String> titels = new ArrayList<String>();

		public InformatieFragmentAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new InfoAlgemeenFragment());
			titels.add(getString(R.string.info_algemeen_titel));
			fragments.add(new InfoCalamiteitenFragment());
			titels.add(getString(R.string.info_calamiteiten_titel));
			fragments.add(new InfoOverzichtskaartenFragment());
			titels.add(getString(R.string.info_overzichtskaart_titel));
			fragments.add(new InfoColofonFragment());
			titels.add(getString(R.string.info_colofon_titel));
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
	
	public static final String INSTANTE_STATE_TAB = "tabid";
	
	// -- INSTANTIEVARIABELEN --
	
	private ViewPager mPager;

	// -- LIFECYCLEMETHODEN --
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_tabs);
		getSupportActionBar().setHomeButtonEnabled(true);
		setTitle(R.string.dashboard_informatie);
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(new InformatieFragmentAdapter(getSupportFragmentManager()));
		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(mPager);
		if (savedInstanceState != null) {
			int tabId = savedInstanceState.getInt(INSTANTE_STATE_TAB);
			mPager.setCurrentItem(tabId, false);
			indicator.setCurrentItem(tabId);
		}
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
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
