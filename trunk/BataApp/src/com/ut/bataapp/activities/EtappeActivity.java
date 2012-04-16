package com.ut.bataapp.activities;

import java.util.ArrayList;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.R;
import com.ut.bataapp.Utils;
import com.ut.bataapp.api.api;
import com.ut.bataapp.fragments.EtappeLooptijdenFragment;
import com.ut.bataapp.fragments.EtappeInformatie;
import com.ut.bataapp.fragments.EtappeRoutes;
import com.ut.bataapp.objects.Etappe;
import com.ut.bataapp.objects.Response;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;

public class EtappeActivity extends SherlockFragmentActivity {

	ViewPager mPager;
	PageIndicator mIndicator;
	FragmentPagerAdapter mAdapter;
	private Etappe etappe = null;
	private int etappe_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		etappe_id = this.getIntent().getIntExtra("index", 0);
		setTitle("Etappe "+etappe_id);
		new getEtappe().execute();
	}

	public Etappe getEtappe(){
		return etappe;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Utils.goHome(getApplicationContext());
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	class EtappeFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {

		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		ArrayList<String> titels = new ArrayList<String>();

		public EtappeFragmentAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new EtappeInformatie());
			titels.add("Informatie");
			fragments.add(new EtappeRoutes());
			titels.add("Routes");
			fragments.add(new EtappeLooptijdenFragment());
			titels.add("Looptijden");
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

	private class getEtappe extends AsyncTask<Void, Void, Void> {  
		private ProgressDialog progressDialog;
		Response<Etappe> response;
		
		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(EtappeActivity.this,  
					"Bezig met laden", "Etappe wordt opgehaald...", true);  
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			response = api.getEtappesByID(etappe_id,EtappeActivity.this);
			return null;       
		}

		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(EtappeActivity.this, response)) {
				etappe = response.getResponse();
				setContentView(R.layout.simple_tabs);
				mAdapter = new EtappeFragmentAdapter(getSupportFragmentManager());
				mPager = (ViewPager)findViewById(R.id.pager);
				mPager.setAdapter(mAdapter);
				mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
				mIndicator.setViewPager(mPager);
				progressDialog.dismiss();
			}
			
		}
	}
}
