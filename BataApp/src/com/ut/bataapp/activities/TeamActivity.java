package com.ut.bataapp.activities;

import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;
import com.ut.bataapp.api.api;
import com.ut.bataapp.fragments.KlassementFragment;
import com.ut.bataapp.fragments.TeamInformatieFragment;
import com.ut.bataapp.fragments.TeamLooptijdenFragment;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.objects.Team;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;

/**
 * Activity waar alle informatie van 1 bepaald team wordt getoond. De volgende fragments worden hier geladen:
 * 	- Informatie
 *  - Etappeuitslag
 *  - Klassement
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne van de Venis
 * @version 1.0
 */
public class TeamActivity extends SherlockFragmentActivity implements OnPageChangeListener {
	/** ID's van de tabbladen */
	public static final int ETAPPEUITSLAG_TAB = 1, KLASSEMENT_TAB = 2;
	/** Variable voor de variabele naam van een fragment */
	public static final String TAB = "tabid";
	/** Variable voor de variabele naam van het team id */
	public static final String ID = "index";

	/** ID van de knop bovenin om een team te volgen */
	private static final int MENU_FOLLOW = Menu.FIRST;
	/** ID van de knop bovenin om een team niet meer te volgen */
	private static final int MENU_UNFOLLOW = Menu.FIRST + 1;

	/** Viewpager, nodig voor het wisselen tussen tabbladen door middel van 'swypen'. */
	private ViewPager mPager;
	/** PageIndicator, nodig voor de fancy titels boven de tabbladen */
	private PageIndicator mIndicator;
	/** Fragmentadapter, hierin worden de fragments geladen */
	private TeamFragmentAdapter mAdapter;
	/** Het team-object van het team dat geladen wordt */
	private Team mTeam;
	/** ID van het team dat geladen wordt */
	private int mTeamID;
	/** AsyncTask waarin de gegevens worden ophaald */
	private getTeam mGetTeam;
	/** ID van het tabblad dat wordt geopenend als de Activity wordt gemaakt */
	private int mTabId;
	/** Variabelen om bij te houden uit welke staat deze Activity komt */
	private boolean mRestarted, mConfigChanged;

	/**
	 * Methode die het team oplevert
	 * @return Het team
	 */
	public Team getTeam(){
		return mTeam;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTeamID = ((savedInstanceState == null) ? getIntent().getIntExtra(ID, 0) : savedInstanceState.getInt(ID));
		mTabId = ((savedInstanceState == null) ? getIntent().getIntExtra(TAB, 0) : savedInstanceState.getInt(TAB));
	}

	/**
	 * Callback-methode resumen activity.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if (!mConfigChanged) {
			mRestarted = true;
			mGetTeam = new getTeam(mTabId);
			mGetTeam.execute();	
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
			mGetTeam.cancel(true);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(ID, mTeamID);
		outState.putInt(TAB, (mPager == null ? 0 : mPager.getCurrentItem()));
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if(mTeam != null) {
			SharedPreferences keyValues = this.getSharedPreferences("teams_follow", Context.MODE_PRIVATE);
			MenuItem menuItem_volg = menu.findItem(MENU_FOLLOW);
			MenuItem menuItem_delete = menu.findItem(MENU_UNFOLLOW);
			if(keyValues.contains(String.valueOf(mTeam.getStartnummer()))) {
				menuItem_volg.setVisible(false);
				menuItem_delete.setVisible(true);
			} else {
				menuItem_volg.setVisible(true);
				menuItem_delete.setVisible(false);
			}
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,MENU_UNFOLLOW,2, R.string.ab_verwijderen)
		.setIcon(R.drawable.ic_action_delete)
		.setVisible(false)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		menu.add(0,MENU_FOLLOW,2, R.string.ab_volgen)
		.setIcon(R.drawable.ic_action_star)
		.setVisible(false)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Utils.goHome(this);
			return true;
		case MENU_FOLLOW:
			Utils.addFavoTeam(getApplicationContext(), mTeam);
			invalidateOptionsMenu();
			break;
		case MENU_UNFOLLOW:
			Utils.removeFavoteam(getApplicationContext(), mTeam.getID());
			invalidateOptionsMenu();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	
	class TeamFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		ArrayList<String> titels = new ArrayList<String>();

		public TeamFragmentAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new TeamInformatieFragment());
			titels.add(getString(R.string.team_titel_informatie));
			fragments.add(new TeamLooptijdenFragment());
			titels.add(getString(R.string.team_titel_looptijden));

			KlassementFragment kf = new KlassementFragment();
			Bundle info = new Bundle();
			info.putString("index",mTeam.getKlassement());
			info.putInt("init", Integer.parseInt(mTeam.getCumKlassement()));
			info.putBoolean("inViewpager", true);
			info.putBoolean("restarted", mRestarted);
			kf.setArguments(info);
			fragments.add(kf);
			mRestarted = false;
			titels.add(getString(R.string.team_titel_klassement));
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

	/* Klasse voor het binnenhalen van een team. Tijdens het laden wordt een spinner weergegeven, vervolgens wordt het team in een
	 * ListView getoond.
	 * @author Anne van de Venis
	 * @version 1.0
	 */
	private class getTeam extends AsyncTask<Void, Void, Void> {  
		/** Spinner die wordt getoond tijdens het laden */
		private ProgressDialog progressDialog;
		/** Het resultaat van de api-aanvraag */
		Response<Team> response;
		/** ID van het tabblad dat geopend moet worden */
		private int mTabId;

		/**
		 * Constructor van deze AsyncTask
		 * @param tabId ID van het tabblad dat geopend moet worden
		 */
		public getTeam(int tabId) {
			mTabId = tabId;
		}

		@Override
		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(TeamActivity.this,  
					getString(R.string.laden_titel), getString(R.string.team_laden), true);
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
				response = api.getTeamByID(mTeamID);
			return null;
		}

		@Override
		protected void onCancelled() {
			progressDialog.dismiss();
		}

		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(TeamActivity.this, response)) {
				setContentView(R.layout.simple_tabs);
				getSupportActionBar().setHomeButtonEnabled(true);
				mTeam = response.getResponse();
				mAdapter = new TeamFragmentAdapter(getSupportFragmentManager());

				mPager = (ViewPager)findViewById(R.id.pager);
				mPager.setAdapter(mAdapter);

				mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
				mIndicator.setViewPager(mPager);
				mIndicator.setOnPageChangeListener(TeamActivity.this);
				invalidateOptionsMenu();

				mPager.setCurrentItem(mTabId, false);
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
			mAdapter = new TeamFragmentAdapter(getSupportFragmentManager());
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
		case ETAPPEUITSLAG_TAB:
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			String lookupKey = getResources().getString(R.string.pref_team_looptijden_first_start);
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