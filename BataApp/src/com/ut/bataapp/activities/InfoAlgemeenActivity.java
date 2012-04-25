package com.ut.bataapp.activities;

import java.util.ArrayList;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;
import com.ut.bataapp.activities.TeamActivity.TeamFragmentAdapter;
import com.ut.bataapp.fragments.LayoutFragment;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;
import com.actionbarsherlock.R;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class InfoAlgemeenActivity extends SherlockFragmentActivity {
   
	public static final String TAB = "tabid";
	
	ViewPager mPager;
	PageIndicator mIndicator;
	AlgemeenFragmentAdapter mAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.simple_tabs);
        setTitle(R.string.dashboard_informatie);
        
        mAdapter = new AlgemeenFragmentAdapter(getSupportFragmentManager());
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mIndicator = (TabPageIndicator)findViewById(R.id.indicator);

		String page = "";
		page = getIntent().getStringExtra("page");
		int pageid = 0;
		if(savedInstanceState != null)
			pageid = savedInstanceState.getInt("tabid");
		else {
		//(getIntent() != null) ? getIntent().getIntExtra("tabid", 0) : savedInstanceState.getInt("tabid")
			
			if(page.equals("watmoetjeweten")) { pageid = 0; }
			else if(page.equals("bustijden")) { pageid = 1; }
			else if(page.equals("slapen")) { pageid = 2; }
		}
		mPager.setCurrentItem(pageid);
		mIndicator.setViewPager(mPager);
		mIndicator.setCurrentItem(pageid);
		
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
    
    class AlgemeenFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
		
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		ArrayList<String> titels = new ArrayList<String>();
		
		public AlgemeenFragmentAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new LayoutFragment(R.layout.info_algemeen_watmoetjeweten));
			titels.add("Belangrijk");
			fragments.add(new LayoutFragment(R.layout.info_algemeen_busenstarttijden));
			titels.add("Tijden");
			fragments.add(new LayoutFragment(R.layout.info_algemeen_slapen));
			titels.add("Slapen");
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
    
    @Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(mPager != null) {
			int currentItem = mPager.getCurrentItem(); // huidige tabbladindex  
			mAdapter.deleteAll(getSupportFragmentManager()); // cleanup van alle oude fragments
			mAdapter = new AlgemeenFragmentAdapter(getSupportFragmentManager());
			mPager.setAdapter(mAdapter);
			mIndicator.notifyDataSetChanged();
			mPager.setCurrentItem(currentItem, false);
		}
		Log.d("Pager", "NULL");
	}
    
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("tabid", (mPager == null ? 0 : mPager.getCurrentItem()));
	}
}
