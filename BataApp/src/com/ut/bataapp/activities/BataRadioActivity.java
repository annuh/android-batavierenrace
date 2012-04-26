package com.ut.bataapp.activities;

import com.ut.bataapp.R;
import com.ut.bataapp.Utils;
import com.ut.bataapp.fragments.*;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.*;

import java.util.ArrayList;

/**
 * Activity voor het BataRadio-gedeelte. Bevat drie tabbladen (fragments): livestream, algemene informatie en programmering. 
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class BataRadioActivity extends SherlockFragmentActivity {
	// -- INNER CLASSES --
	
	/* Adapterklasse voor fragments -> pager.
     * Onderdeel van ontwerpproject BataApp.
     * @author Danny Bergsma
     * @version 0.1
     */
    private class BataRadioFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
		/* Lijst van alle tabs (fragments) in de pager
		 * @invariant fragments != null
		 * @invariant voor alle 0 <= i < fragments.size(): fragments.get(i) != null
		 */
    	private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    	/* Lijst van alle titels in de pager
		 * @invariant titels != null
		 * @invariant voor alle 0 <= i < titels.size(): titels.get(i) != null
		 */
		private ArrayList<String> mTitels = new ArrayList<String>();
		
		/**
		 * Constructor voor deze adapter. Bouwt de adapter op met alle tabs (fragments) en titels.
		 * @param fm manager voor de fragments
		 * @require fm != null
		 */
		public BataRadioFragmentAdapter(FragmentManager fm) {
			super(fm);
			Resources res = getResources();
			mFragments.add(new BataRadioLiveFragment());
			mTitels.add(res.getString(R.string.tabtitel_bataradio_live));
			mFragments.add(new BataRadioInfoFragment());
			mTitels.add(res.getString(R.string.tabtitel_bataradio_info));
			mFragments.add(new BataRadioProgrammeringFragment());
			mTitels.add(res.getString(R.string.tabtitel_bataradio_programmering));
		}
		
		/**
		 * Geeft het Fragment op positie position terug.
		 * @param position positie van fragment
		 * @return Fragment op positie position
		 * @require 0 <= position < getCount()
		 * @ensure result != null
		 */
		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		/**
		 * Geeft het aantal fragments terug.
		 * @return aantal fragments
		 * @ensure result >= 0
		 */
		@Override
		public int getCount() {
			return mFragments.size();
		}

		/**
		 * Geeft de titel van het fragment (de tab) op positie position.
		 * @param position positie van fragment (tab)
		 * @return de titel van het fragment (de tab) op positie position
		 * @require 0 <= position < getCount()
		 * @ensure result != null
		 */
		@Override
		public String getTitle(int position) {
			return mTitels.get(position);
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
        setTitle(R.string.dashboard_bataradio);
        
        // opbouwen tabbladen:
        FragmentPagerAdapter mAdapter = new BataRadioFragmentAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		PageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
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
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}