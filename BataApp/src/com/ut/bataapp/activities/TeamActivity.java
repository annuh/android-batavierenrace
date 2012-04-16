package com.ut.bataapp.activities;

import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.R;
import com.ut.bataapp.api.api;
import com.ut.bataapp.fragments.TeamInformatieFragment;
import com.ut.bataapp.fragments.TeamLooptijdenFragment;
import com.ut.bataapp.objects.Response;
import com.ut.bataapp.objects.Team;
import com.ut.bataapp.Utils;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;
import com.viewpagerindicator.PageIndicator;
import android.support.v4.app.LoaderManager;

public class TeamActivity extends SherlockFragmentActivity implements LoaderManager.LoaderCallbacks<Response<Team>> {

	ViewPager mPager;
	PageIndicator mIndicator;
	FragmentPagerAdapter mAdapter;
	private final int MENU_FOLLOW = Menu.FIRST;
	private final int MENU_UNFOLLOW = Menu.FIRST + 1;
	Team team = null;
	static int team_id;

	public Team getTeam(){
		return team;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		team_id = this.getIntent().getIntExtra("index", 0);
		Log.d("Teamid",""+team_id);
		getSupportLoaderManager().initLoader(0, null, this);//.forceLoad();


		//getSupportLoaderManager().getLoader(0).startLoading();
		//new getTeam().execute();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if(team != null) {
			team.getNaam();
			SharedPreferences keyValues = this.getSharedPreferences("teams_follow", Context.MODE_PRIVATE);
			MenuItem menuItem_volg = menu.findItem(MENU_FOLLOW);
			MenuItem menuItem_delete = menu.findItem(MENU_UNFOLLOW);
			if(keyValues.contains(String.valueOf(team.getStartnummer()))) {
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
			Utils.addFavoTeam(getApplicationContext(), team);
			invalidateOptionsMenu();

			break;
		case MENU_UNFOLLOW:
			Utils.removeFavoteam(getApplicationContext(), team.getID());
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
			titels.add("Informatie");
			fragments.add(new TeamLooptijdenFragment());
			titels.add("Routetijden");
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

	private class getTeam extends AsyncTask<Void, Void, Void> {  

		private ProgressDialog progressDialog;
		Response<Team> response;

		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(TeamActivity.this,  
					"Bezig met laden", "Team wordt opgehaald...", true);
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
				response = api.getTeamByID(team_id);
			return null;
		}

		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(TeamActivity.this, response)) {
				setContentView(R.layout.simple_tabs);
				team = response.getResponse();
				mAdapter = new TeamFragmentAdapter(getSupportFragmentManager());

				mPager = (ViewPager)findViewById(R.id.pager);
				mPager.setAdapter(mAdapter);

				mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
				mIndicator.setViewPager(mPager);
				invalidateOptionsMenu();
				progressDialog.dismiss();
			}

		}
	}

	public static class AppListLoader extends AsyncTaskLoader<Response<Team>> {
		Response<Team> response;
		public AppListLoader(Context context) {
			super(context);
		}

		@Override public Response<Team> loadInBackground() {
			response = api.getTeamByID(team_id);
			return response;
		}

		@Override public void deliverResult(Response<Team> response) {
			if (isReset()) {
				return;
			}

			this.response = response;
			super.deliverResult(response);
		}

		@Override protected void onStartLoading() {
			if (response != null) {
				deliverResult(response);
			}
			if (takeContentChanged() || response == null) {
				forceLoad();
			}
		}

		@Override
		protected void onStopLoading() {
			// Attempt to cancel the current load task if possible.
			cancelLoad();
		}

		@Override
		protected void onReset() {
			super.onReset();
			// Ensure the loader is stopped
			onStopLoading();
			response = null;
		}
	}

	private ProgressDialog progressDialog;

	@Override
	public Loader<Response<Team>> onCreateLoader(int arg0, Bundle arg1) {
		progressDialog = ProgressDialog.show(TeamActivity.this,  
				"Bezig met laden", "Team wordt opgehaald...", true);
		progressDialog.setCancelable(true);
		progressDialog.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		return new AppListLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<Response<Team>> loader, Response<Team> response) {
		//Log.d("Loader", "Klaar");
		if(Utils.checkResponse(TeamActivity.this, response)) {
			team = response.getResponse();
			setContentView(R.layout.simple_tabs);
			mAdapter = new TeamFragmentAdapter(getSupportFragmentManager());
	
			mPager = (ViewPager)findViewById(R.id.pager);
			mPager.setAdapter(mAdapter);
	
			mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
			mIndicator.setViewPager(mPager);
			invalidateOptionsMenu();
			progressDialog.dismiss();
		}
	}

	@Override
	public void onLoaderReset(Loader<Response<Team>> arg0) {		
		//Utils.goHome(this);
	}

}
