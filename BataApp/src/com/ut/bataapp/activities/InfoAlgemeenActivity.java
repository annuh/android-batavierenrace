package com.ut.bataapp.activities;

import com.ut.bataapp.Utils;
import com.ut.bataapp.fragments.LayoutFragment;

import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.*;
import com.viewpagerindicator.TitleProvider;

import java.util.ArrayList;

/**
 * Activity voor het algemene informatie gedeelte.  
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne van de Venis
 * @version 1.0
 */
public class InfoAlgemeenActivity extends SherlockFragmentActivity {
    // -- INNER CLASSES --
	
	/* Adapterklasse voor fragments -> pager. In deze adapter worden de volgende tabbladen getoond:
	 *  - Belangrijk
	 *  - Bus- en starttijden
	 *  - Slapen
     * Onderdeel van ontwerpproject BataApp.
     * @author Anne van de Venis
     * @version 1.0
     */
	class AlgemeenFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
		/** Hier worden de Fragments opgeslagen die worden getoond. */
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		/** Hier worden de titels van de Fragments opgeslagen. */
		ArrayList<String> titels = new ArrayList<String>();
		
		public AlgemeenFragmentAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new LayoutFragment(R.layout.info_algemeen_watmoetjeweten));
			titels.add(getString(R.string.info_algemeen_watmoetjeweten_titel));
			fragments.add(new LayoutFragment(R.layout.info_algemeen_busenstarttijden));
			titels.add(getString(R.string.info_algemeen_busenstarttijden_titel));
			fragments.add(new LayoutFragment(R.layout.info_algemeen_slapen));
			titels.add(getString(R.string.info_algemeen_slapen_titel));
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
	
	public static final String EXTRA_TAB = "tabid", INSTANTE_STATE_TAB = "tabid";
	public static final int TAB_WATMOETJEWETEN = 0, TAB_BUSTIJDEN = 1, TAB_SLAPEN = 2;
	
	// -- INSTANTIEVARIABELEN --
	
	private ViewPager mPager;
    
	// -- LIFECYCLEMETHODEN --
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.simple_tabs);
    	setTitle(R.string.info_algemeen_titel);
    	getSupportActionBar().setHomeButtonEnabled(true);
    	mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(new AlgemeenFragmentAdapter(getSupportFragmentManager()));
		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
    	indicator.setViewPager(mPager);
        int tabId = ((savedInstanceState == null) ? getIntent().getIntExtra(EXTRA_TAB, TAB_WATMOETJEWETEN) : savedInstanceState.getInt(INSTANTE_STATE_TAB)); 
        mPager.setCurrentItem(tabId, false);
		indicator.setCurrentItem(tabId);
    }
	
    @Override
  	protected void onSaveInstanceState(Bundle outState) {
    	// super niet aangeroepen: ViewPager/Adapter wordt in onCreate() weer opgebouwd
  		outState.putInt(INSTANTE_STATE_TAB, (mPager == null ? TAB_WATMOETJEWETEN : mPager.getCurrentItem()));
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
