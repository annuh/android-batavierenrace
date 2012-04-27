package com.ut.bataapp.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;
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
    // -- INNER CLASSES --
	
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
			fragments.add(new WeerAdviesFragment());
			titels.add(mRes.getString(R.string.tabtitel_weer_advies));
			fragments.add(new WeerVerwachtingFragment());
			titels.add(mRes.getString(R.string.tabtitel_weer_verwachting));
			fragments.add(new WeerBuienradarFragment());
			titels.add(mRes.getString(R.string.tabtitel_weer_buienradar));
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
		
		/**
		 * Verwijdert alle fragments van deze adapter uit fm.
		 * @param fm manager voor de fragments, waaruit alle fragments zullen worden verwijderd
		 * @require fm != null
		 */
		public void deleteAll(FragmentManager fm) {
			FragmentTransaction ft = fm.beginTransaction();
			for (Fragment fragment: fragments)
				ft.remove(fragment);
			ft.commit();
		}
	}
	
	/* Klasse voor het binnenhalen van laatste gegevens weerprovider. Laat een ProgressDialog zien, die geannuleerd kan worden.
     * Wanneer geen gegevens kunnen worden binnengehaald, wordt teruggekeerd naar vorige activity. In het andere geval worden
     * alle tabbladen opgebouwd.
     * @author Danny Bergsma
     * @version 0.1
     */
    private class RefreshWeerProvider extends AsyncTask<Date, Void, WeerException> {  
    	/* de ProgressDialog die getoond wordt tijdens ophalen */
    	private ProgressDialog mProgressDialog;
		
    	@Override
		protected void onPreExecute() {
			ProgressDialog progressDialog = (mProgressDialog = ProgressDialog.show(WeerActivity.this, mRes.getString(R.string.bezig_met_laden), 
			                                                   mRes.getString(R.string.weer_ophalen_weergegevens), true));
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() { 
				public void onCancel(DialogInterface dialog) {
					cancel(true);
					setResult(RESULT_CANCELED);
					finish();
				}
			});
		}
				
		protected WeerException doInBackground(Date... arg) {
			WeerException result = null;
			if (!isCancelled())
				try {
					mWeerProvider.refresh(arg[0]);
				} catch (WeerException e) {
					result = e;
				}
			return result;
		}
		
		@Override
		protected void onCancelled() {
			mProgressDialog.dismiss();
		}
		
		@Override  
		protected void onPostExecute(WeerException result) {
	        if (result == null) {
	        	// opbouwen tabbladen:
	            setContentView(R.layout.simple_tabs);
	            getSupportActionBar().setHomeButtonEnabled(true);
	            mAdapter = new WeerFragmentAdapter(getSupportFragmentManager());
				ViewPager pager = (ViewPager) findViewById(R.id.pager);
				pager.setAdapter(mAdapter);
				PageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
				indicator.setViewPager(pager);
				indicator.notifyDataSetChanged();
				pager.setCurrentItem(mTabIndex, false);
				indicator.setCurrentItem(mTabIndex);
	        }
			mProgressDialog.dismiss();
			if (result != null) {
				Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_LONG).show();
				setResult(RESULT_CANCELED);
				finish();
			}			
		}
    }
	
    // -- CONSTANTEN --
    
    /* lookup-key voor tabindex in savedInstanceState-Bundle */
    private static final String INSTANCE_STATE_TAB_INDEX = "tabIndex";
    
    // -- INSTANTIEVARIABELEN --
    
    /* De weerprovider die gebruikt wordt voor weergegevens.
     * @invariant mWeerProvider != null
     */
	private WeerProvider mWeerProvider;
	/* Referentie naar resources voor deze app.
	 * @invariant mRes != null
	 */
    private Resources mRes;
    /* adapter voor fragments -> pager */
    private WeerFragmentAdapter mAdapter;
    /* index van laatst geopende tab */
    private int mTabIndex;
    /* AsyncTask voor binnenhalen laatste data weerprovider */
    private RefreshWeerProvider mRefreshWeerProvider;
    
    // -- LIFECYCLEMETHODEN --
	
	/**
     * Callback-methode creëren activity.
     */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.dashboard_weer);
        mWeerProvider = new WeerBuienradarGoogle(this);
        mRes = getResources();
        if (savedInstanceState != null)
        	mTabIndex = savedInstanceState.getInt(INSTANCE_STATE_TAB_INDEX);
    }
	
	/**
     * Callback-methode starten activity. XML-weergegevens (Google en Buienradar) worden opgehaald.
     */
    @Override
	protected void onResume() {
    	super.onResume();
    	Resources res = mRes;
    	Calendar bataDag = Calendar.getInstance();
    	bataDag.set(res.getInteger(R.integer.batadag_jaar), (res.getInteger(R.integer.batadag_maand)-1), res.getInteger(R.integer.batadag_dag));
    	mRefreshWeerProvider = new RefreshWeerProvider();
    	mRefreshWeerProvider.execute(bataDag.getTime());
	}
    
    /**
     * Callback-methode pauseren activity. Alle fragments worden verwijderd.
     */
    @Override
    protected void onPause() {
    	super.onPause();
    	if (mAdapter != null) {
			mAdapter.deleteAll(getSupportFragmentManager());
			mTabIndex = ((ViewPager) findViewById(R.id.pager)).getCurrentItem();
		} else
			mRefreshWeerProvider.cancel(true);
    }

    /**
     * Opslaan tabindex (voor gebruik in onCreate()).
     */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		if (pager != null)
			outState.putInt(INSTANCE_STATE_TAB_INDEX, pager.getCurrentItem());
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
    
    // -- GETTERS --
    
	/**
	 * Geeft weerprovider voor deze activity terug.
	 * @return weerprovider voor deze activity
	 * @ensure result != null
	 */
    public WeerProvider getWeerProvider() {
    	return mWeerProvider;
    }
}