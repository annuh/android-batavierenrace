package com.ut.bataapp.activities;

import java.util.ArrayList;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
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

public class ColofonActivity extends SherlockFragmentActivity {
   
	public static final String TAB = "tabid";
	
	ViewPager mPager;
	PageIndicator mIndicator;
	ColofonFragmentAdapter mAdapter;
	
	
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.simple_tabs);
		
        mAdapter = new ColofonFragmentAdapter(getSupportFragmentManager());
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
		
		String page = "";
		page = getIntent().getStringExtra("page");
		int pageid = 0;
		if(savedInstanceState != null)
			pageid = savedInstanceState.getInt("tabid");
		else {
			if(page.equals("contact")) { pageid = 0; }
			else if(page.equals("colofon")) { pageid = 1; }
			else if(page.equals("disclaimer")) { pageid = 2; }
		}
		mPager.setCurrentItem(pageid);
		mIndicator.setViewPager(mPager);
		mIndicator.setCurrentItem(pageid);
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				/*
				Intent intent = new Intent(this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
				//Get rid of the slide-in animation, if possible
	            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
	                OverridePendingTransition.invoke(this);
	            }*/
		}
		
		return super.onOptionsItemSelected(item);
	}
    
    class ColofonFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
		
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		ArrayList<String> titels = new ArrayList<String>();
		
		public ColofonFragmentAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new LayoutFragment(R.layout.info_colofon_contactgegevens));
			titels.add("Contact");
			fragments.add(new LayoutFragment(R.layout.info_colofon_colofon));
			titels.add("Colofon");
			fragments.add(new LayoutFragment(R.layout.info_colofon_disclaimer));
			titels.add("Disclaimer");
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
  			mAdapter = new ColofonFragmentAdapter(getSupportFragmentManager());
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
