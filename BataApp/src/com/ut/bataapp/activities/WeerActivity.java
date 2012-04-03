package com.ut.bataapp.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ut.bataapp.fragments.WeerAdviesFragment;
import com.ut.bataapp.fragments.WeerBuienradarFragment;
import com.ut.bataapp.fragments.WeerVerwachtingFragment;
import com.ut.bataapp.weer.WeerBuienradarGoogle;
import com.ut.bataapp.weer.WeerException;
import com.ut.bataapp.weer.WeerProvider;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;

/**
 * Activity voor het weergedeelte. Bevat drie tabbladen (fragments): advies, verwachting en buienradar. 
 * Bij starten activity worden de laatste XML-weergegevens (Google en Buienradar) opgehaald.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class WeerActivity extends SherlockFragmentActivity {
    private WeerProvider mWeerProvider;
    private boolean mRefreshOngoing;
    private WeerAdviesFragment mWeerAdviesFragment;
    private WeerVerwachtingFragment mWeerVerwachtingFragment;
	
	/**
     * Callback-methode creëren activity. Layout wordt opgebouwd.
     */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(R.string.dashboard_weer);
        setContentView(R.layout.simple_tabs);
        
        // opbouwen tabbladen:
        FragmentPagerAdapter mAdapter = new WeerFragmentAdapter(getSupportFragmentManager());
		ViewPager mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		PageIndicator mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
		
		mWeerProvider = new WeerBuienradarGoogle(this); 
    }
    
    /**
     * Callback-methode resumen activity. XML-weergegevens (Google en Buienradar) worden opgehaald.
     */
    @Override
	protected void onStart() {
    	super.onStart();
    	Calendar now = Calendar.getInstance();
    	now.add(Calendar.DATE, 1);
    	new RefreshWeerProvider().execute(mWeerProvider, now.getTime());
	}
        
    public boolean refreshOngoing() {
    	return mRefreshOngoing;
    }
    
    public WeerProvider getWeerProvider() {
    	return mWeerProvider;
    }
    
    /* Adapterklasse voor fragments -> pager.
     * Onderdeel van ontwerpproject BataApp.
     * @author Danny Bergsma
     * @version 0.1
     */
    private class WeerFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
		/* Lijst van alle tabs (fragments) in de pager
		 * @invariant fragments != null
		 * @invariant voor alle 0 <= i < fragments.size(): fragments.get(i) != null
		 */
    	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    	/* Lijst van alle titels in de pager
		 * @invariant titels != null
		 * @invariant voor alle 0 <= i < titels.size(): titels.get(i) != null
		 */
		private ArrayList<String> titels = new ArrayList<String>();
		
		/**
		 * Constructor voor deze adapter. Bouwt de adapter op met alle tabs (fragments) en titels.
		 * @param fm manager voor de fragments
		 * @require fm != null
		 */
		public WeerFragmentAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(mWeerAdviesFragment = new WeerAdviesFragment());
			titels.add(getResources().getString(R.string.tabtitel_weer_advies));
			fragments.add(mWeerVerwachtingFragment = new WeerVerwachtingFragment());
			titels.add(getResources().getString(R.string.tabtitel_weer_verwachting));
			fragments.add(new WeerBuienradarFragment());
			titels.add(getResources().getString(R.string.tabtitel_weer_buienradar));
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

		/**
		 * Geeft de titel van het fragment (de tab) op positie position.
		 * @param position positie van fragment (tab)
		 * @return de titel van het fragment (de tab) op positie position
		 * @require 0 <= position < getCount()
		 * @ensure result != null
		 */
		@Override
		public String getTitle(int position) {
			return titels.get(position);
		}
	}
    
    /* Klasse voor het binnenhalen van XML-documenten.
     */
    private class RefreshWeerProvider extends AsyncTask<Object, Void, Object> {  
		private ProgressDialog mProgressDialog;
		
		protected void onPreExecute() {
	    	mRefreshOngoing = true;
			mProgressDialog = ProgressDialog.show(WeerActivity.this, getResources().getString(R.string.bezig_met_laden), 
			  getResources().getString(R.string.ophalen_weergegevens), true);
		}
				
		@Override
		protected Object doInBackground(Object... arg) {
			Object result;
			try {
				((WeerProvider) arg[0]).refresh((Date) arg[1]);
				result = arg[0];
			} catch (WeerException e) {
				result = e;
				cancel(false);
			}
			return result;
		}
		
		@Override  
		protected void onPostExecute(Object result) {
			mWeerAdviesFragment.updateView();
			mWeerVerwachtingFragment.updateView();
			mRefreshOngoing = false;
			mProgressDialog.dismiss();
		}
		
		@Override
		protected void onCancelled(Object result) {
			WeerException e = (WeerException) result;
			mProgressDialog.dismiss();
			mRefreshOngoing = false;
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			setResult(RESULT_CANCELED);
			finish();
		}
	}
}