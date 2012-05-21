package com.ut.bataapp.activities;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.Toast;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;
import com.ut.bataapp.api.api;
import com.ut.bataapp.fragments.EtappeInformatie;
import com.ut.bataapp.fragments.EtappeLooptijdenFragment;
import com.ut.bataapp.fragments.EtappeRoutesFragment;
import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.objects.Response;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;

/**
 * Activity waar alle informatie van 1 bepaalde etappe wordt getoond. De volgende fragments worden hier geladen:
 * 	- Algemeen
 *  - Routes
 *  - Looptijden
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne van de Venis
 * @version 1.0
 */
public class EtappeActivity extends SherlockFragmentActivity implements OnPageChangeListener {
	/** ID's van de tabbladen */
	public static final int TAB_ALGEMEEN = 0, TAB_ROUTES = 1, TAB_LOOPTIJDEN = 2;
	/** Viewpager, nodig voor het wisselen tussen tabbladen door middel van 'swypen'. */
	ViewPager mPager;
	/** PageIndicator, nodig voor de fancy titels boven de tabbladen */
	PageIndicator mIndicator;
	/** Fragmentadapter, hierin worden de fragments geladen */
	EtappeFragmentAdapter mAdapter;

	/** Variable voor de variabele naam van een fragment */
	public static final String TAB = "tabid";
	/** Variable voor de variabele naam van het etappe id */
	public static final String ID = "index";

	/** De etappe waar info over wordt getoond. */
	private Etappe etappe = null;
	/** ID van de etappe die wordt weergegeven. */
	private int etappe_id;
	/** ID van  het tablad dat wordt getoond als de Activity wordt opgestart*/
	private int mTabId;
	/** De AsyncLoader waarin de gegevens opgehaald. */
	private getEtappe mGetEtappe;
	/** Variabelen om bij te houden uit welke staat deze Activity komt */
	private boolean mRestarted, mConfigChanged;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		etappe_id = ((savedInstanceState == null) ? getIntent().getIntExtra(ID, 0) : savedInstanceState.getInt(ID));
		mTabId = ((savedInstanceState == null) ? getIntent().getIntExtra(TAB, 0) : savedInstanceState.getInt(TAB));
		setTitle("Etappe "+etappe_id);
	}

	/**
	 * Callback-methode resumen activity.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if (!mConfigChanged) {
			mRestarted = true;
			mGetEtappe = new getEtappe(mTabId);
			mGetEtappe.execute();	
		}
	}

	/**
	 * Callback-methode pauseren activity. Alle fragments worden verwijderd.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mConfigChanged = false;
		if (mAdapter != null) {
			mAdapter.deleteAll(getSupportFragmentManager());
			mTabId = ((ViewPager) findViewById(R.id.pager)).getCurrentItem();
		} else
			mGetEtappe.cancel(true);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(ID, etappe_id);
		outState.putInt(TAB, (mPager == null ? 0 : mPager.getCurrentItem()));
	}

	/**
	 * Methode die de etappe oplevert
	 * @return Het team
	 */
	public Etappe getEtappe(){
		return etappe;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Utils.goHome(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class EtappeFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {

		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		ArrayList<String> titels = new ArrayList<String>();

		public EtappeFragmentAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new EtappeInformatie());
			titels.add(getString(R.string.etappe_titel_informatie));
			fragments.add(new EtappeRoutesFragment());
			titels.add(getString(R.string.etappe_titel_routes));
			EtappeLooptijdenFragment lf = new EtappeLooptijdenFragment();
			Bundle info = new Bundle();		
			info.putBoolean("restarted", mRestarted);
			lf.setArguments(info);
			fragments.add(lf);
			mRestarted = false;
			titels.add(getString(R.string.etappe_titel_looptijden));
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

		public void deleteAll(FragmentManager fm) {
			FragmentTransaction ft = fm.beginTransaction();
			for (Fragment fragment: fragments)
				ft.remove(fragment);
			ft.commitAllowingStateLoss();
		}
	}

	/* Klasse voor het binnenhalen van een etappe. Tijdens het laden wordt een spinner weergegeven, vervolgens worden de Etappe in een
	 * ListView getoond.
	 * @author Anne van de Venis
	 * @version 1.0
	 */
	private class getEtappe extends AsyncTask<Void, Void, Void> {
		/** Spinner die wordt getoond tijdens het laden */
		private ProgressDialog progressDialog;
		/** Het resultaat van de api-aanvraag */
		Response<Etappe> response;
		/** ID van het tabblad dat geopend moet worden */
		private int mTabId;

		/**
		 * Constructor van deze AsyncTask
		 * @param tabId ID van het tabblad dat geopend moet worden
		 */
		public getEtappe(int tabId) {
			mTabId = tabId;
		}

		@Override
		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(EtappeActivity.this,  
					getString(R.string.laden_titel), getString(R.string.etappe_laden), true);
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					cancel(true);
					setResult(RESULT_CANCELED);
					finish();
				}
			});
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			if(!isCancelled())
				response = api.getEtappesByID(etappe_id,EtappeActivity.this);
			return null;       
		}

		@Override
		protected void onCancelled() {
			progressDialog.dismiss();
		}

		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(EtappeActivity.this, response)) {
				etappe = response.getResponse();
				setContentView(R.layout.simple_tabs);
				getSupportActionBar().setHomeButtonEnabled(true);
				mAdapter = new EtappeFragmentAdapter(getSupportFragmentManager());
				mPager = (ViewPager)findViewById(R.id.pager);
				mPager.setAdapter(mAdapter);
				mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
				mIndicator.setViewPager(mPager);

				mIndicator.setOnPageChangeListener(EtappeActivity.this);

				mPager.setCurrentItem(mTabId);
				mIndicator.setCurrentItem(mTabId);
			}
			progressDialog.dismiss();

		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(mPager != null) {
			mConfigChanged = true;
			int currentItem = mPager.getCurrentItem(); // huidige tabbladindex  
			mAdapter.deleteAll(getSupportFragmentManager()); // cleanup van alle oude fragments
			mAdapter = new EtappeFragmentAdapter(getSupportFragmentManager());
			mPager.setAdapter(mAdapter);
			mIndicator.notifyDataSetChanged();
			mPager.setCurrentItem(currentItem, false);
		}
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
		case TAB_LOOPTIJDEN:
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			String lookupKey = getResources().getString(R.string.pref_etappe_looptijden_first_start);
			if (prefs.getBoolean(lookupKey, true)) {
				Toast.makeText(this, getResources().getString(R.string.team_looptijden_draai), Toast.LENGTH_LONG).show();
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean(lookupKey, false);
				editor.commit();
			}
			break;
		}
	}
}
