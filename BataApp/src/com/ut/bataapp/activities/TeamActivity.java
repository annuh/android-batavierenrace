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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

public class TeamActivity extends SherlockFragmentActivity {
	public static final int ETAPPEUITSLAG_TAB = 1;
	public static final int KLASSEMENT_TAB = 2;
	
	public static final String TAB = "tabid";
	public static final String ID = "index";
	
	public static final String OPEN_ACTION = "com.ut.bataapp.intent.action.OPEN_TEAM";
	
	private static final int MENU_FOLLOW = Menu.FIRST;
	private static final int MENU_UNFOLLOW = Menu.FIRST + 1;

	private ViewPager mPager;
	private PageIndicator mIndicator;
	private TeamFragmentAdapter mAdapter;
	private Team mTeam;
	private int mTeamID;

	public Team getTeam(){
		return mTeam;
	}

	public String getKlassement() {
		String r = mTeam.getLooptijden().get(0).getKlassement();
		if(r.equals("A") || r.equals("Algemeen Klassement")){
			return "Algemeen Klassement";
		} else if(r.equals("U") || r.equals("Universiteit Klassement")){
			return "Algemeen Klassement";
		}
		return r;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTeamID = ((getIntent() != null) ? getIntent().getIntExtra("index", 0) : savedInstanceState.getInt("teamid"));
		Log.d("Teamid", "teamid: " + mTeamID);
		new getTeam((getIntent() != null) ? getIntent().getIntExtra("tabid", 0) : savedInstanceState.getInt("tabid")).execute();		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("teamid", mTeamID);
		outState.putInt("tabid", (mPager == null ? 0 : mPager.getCurrentItem()));
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
		menu.add(0,MENU_UNFOLLOW,Menu.NONE, R.string.ab_verwijderen)
		.setIcon(R.drawable.ic_action_delete)
		.setVisible(false)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		menu.add(0,MENU_FOLLOW,Menu.NONE, R.string.ab_volgen)
		.setIcon(R.drawable.ic_action_star)
		.setVisible(false)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Utils.goHome(getApplicationContext());
			break;
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
			Log.d("Klas", mTeam.getKlassement());
			KlassementFragment kf = new KlassementFragment();
			Bundle info = new Bundle();
			info.putString("index",mTeam.getKlassement());
			info.putInt("init", Integer.parseInt(mTeam.getCumKlassement()));
			info.putBoolean("inViewpager", true);
			kf.setArguments(info);
			fragments.add(kf);
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
			ft.commit();
		}
	}

	private class getTeam extends AsyncTask<Void, Void, Void> {  

		private ProgressDialog progressDialog;
		Response<Team> response;
		private int mTabId;
		
		public getTeam(int tabId) {
			mTabId = tabId;
		}
		
		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(TeamActivity.this,  
					getString(R.string.laden_titel), getString(R.string.team_laden), true);
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					cancel(true);
					Utils.goHome(TeamActivity.this);
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
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(TeamActivity.this, response)) {
				setContentView(R.layout.simple_tabs);
				mTeam = response.getResponse();
				mAdapter = new TeamFragmentAdapter(getSupportFragmentManager());

				mPager = (ViewPager)findViewById(R.id.pager);
				mPager.setAdapter(mAdapter);

				mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
				mIndicator.setViewPager(mPager);
				invalidateOptionsMenu();
				
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
			int currentItem = mPager.getCurrentItem(); // huidige tabbladindex  
			mAdapter.deleteAll(getSupportFragmentManager()); // cleanup van alle oude fragments
			mAdapter = new TeamFragmentAdapter(getSupportFragmentManager());
			mPager.setAdapter(mAdapter);
			mIndicator.notifyDataSetChanged();
			mPager.setCurrentItem(currentItem, false);
			Log.d("Pager", "NIET NULL");
		}
		Log.d("Pager", "NULL");
	}
}