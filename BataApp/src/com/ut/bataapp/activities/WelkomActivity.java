package com.ut.bataapp.activities;

import com.ut.bataapp.Utils;
import com.ut.bataapp.fragments.*;

import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import java.util.ArrayList;

/**
 * Activity voor welkomstscherm. Bevat twee tabbladen (fragments): favorieten selecteren en instellingen. 
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class WelkomActivity extends SherlockFragmentActivity implements OnPageChangeListener {
    // -- INNER CLASSES --
	
	/* Adapterklasse voor fragments -> pager.
     * Onderdeel van ontwerpproject BataApp.
     * @author Danny Bergsma
     * @version 0.1
     */
    private class WelkomFragmentAdapter extends FragmentPagerAdapter {    	
    	/* Lijst van alle tabs (fragments) in de pager
		 * @invariant fragments != null
		 * @invariant voor alle 0 <= i < fragments.size(): fragments.get(i) != null
		 */
    	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		
		/**
		 * Constructor voor deze adapter. Bouwt de adapter op met alle tabs (fragments) en titels.
		 * @param fm manager voor de fragments
		 * @require fm != null
		 */
		public WelkomFragmentAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new WelkomFavorietenSelecterenFragment());
			fragments.add(new WelkomInstellingenFragment());
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
			return fragments.get(position);
		}

		/**
		 * Geeft het aantal fragments terug.
		 * @return aantal fragments
		 * @ensure result >= 0
		 */
		@Override
		public int getCount() {
			return fragments.size();
		}
	}

    // -- CONSTANTEN --
    
    /* tabindices voor respectievelijk favorieten selecteren- en instellingenfragment */
    private static final int TAB_INDEX_FAVORIETEN = 0, TAB_INDEX_INSTELLINGEN = 1;	
  	public static final String INSTANTE_STATE_TAB = "tabid";
    
    // -- INSTANTIEVARIABELEN --
    
    /* Referentie naar vorige/volgende-knop.
     * @invariant vorige != null && volgende != null
     */
    private Button mVorige, mVolgende; 
    /* Referentie naar gebruikte viewpager.
     * @invariant mPager != null
     */
    private ViewPager mPager;
   
    // -- LIFECYCLEMETHODEN --
	
	/**
     * Callback-methode creëren activity.
     */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.welkom_title);
        setContentView(R.layout.welkom);
        getSupportActionBar().setHomeButtonEnabled(true);
        
        mVorige = (Button) findViewById(R.id.welkom_button_vorige);
        mVolgende = (Button) findViewById(R.id.welkom_button_volgende);
        
        ViewPager pager = (mPager = (ViewPager) findViewById(R.id.welkom_pager));
        pager.setAdapter(new WelkomFragmentAdapter(getSupportFragmentManager()));
        pager.setOnPageChangeListener(this);
        
        if (savedInstanceState != null)
			pager.setCurrentItem(savedInstanceState.getInt(INSTANTE_STATE_TAB), false);
        
        mVorige.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mPager.setCurrentItem(TAB_INDEX_FAVORIETEN);
			}
        });
        
        mVolgende.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				switch (mPager.getCurrentItem()) {
				case TAB_INDEX_FAVORIETEN:
					mPager.setCurrentItem(TAB_INDEX_INSTELLINGEN);
					break;
				case TAB_INDEX_INSTELLINGEN:
					Utils.goHome(WelkomActivity.this);
					break;
				}
			}
        });
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
    
	// -- ONPAGECHANGELISTENER --

	public void onPageScrollStateChanged(int arg0) {
		// doe niets
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// doe niets
	}

	public void onPageSelected(int arg0) {
		switch (mPager.getCurrentItem()) {
		case TAB_INDEX_FAVORIETEN:
			mVorige.setVisibility(View.INVISIBLE);
			mVolgende.setText(R.string.welkom_button_volgende_tekst);
			break;
		case TAB_INDEX_INSTELLINGEN:
			mVorige.setVisibility(View.VISIBLE);
			mVolgende.setText(R.string.welkom_button_voltooien_tekst);
			break;
		}
	}
}